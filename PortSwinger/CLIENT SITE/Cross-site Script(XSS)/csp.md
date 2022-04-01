# Content security policy.

## What is CSP (content security policy)?

- CSP là một lớp bảo mật được thêm vào mục đích để phát hiện và ngăn chặn một số loại tấn công thường gặp, bao gồm cả cuộc tấn công XSS (Cross Site Scripting) và tấn công data injection.

- Header HTTP : Content-Security-Policy


## Mitigating XSS attacks using CSP.

- Bật csp : *<meta http-equiv="Content-Security-Policy" content="default-src 'self'; img-src https://*; child-src 'none';">*

- Tên directive:
•	script-src: chỉ định nguồn (nơi) load các tài nguyên javascript
•	style-src: chỉ định nguồn (nơi) load các tài nguyên css
•	image-src: chỉ định nguồn (nơi) load các tài nguyên image
•	font-src: chỉ định nguồn (nơi) load các tài nguyên font
•	frame-src: chỉ định nguồn (nơi) load các tài nguyên frame
Các giá trị của CSP directive:
•	* : Là wildcard, hay còn có nghĩa là all
•	self: chỉ định domain đang truy cập
•	none: không cho phép bất kỳ nguồn nào
•	http://www.domain.com : cho phép tải resource từ domain được chỉ định, cái này khác hoàn toàn với domain.com
•	domain.com: cho phép tải resource từ domain chỉ định, không cho phép từ http://www.domain.com, subdomain.domain.com, cdn.domain.com, …
•	* .domain.com: cho phép load resource từ subdomain của domain domain.com
•	https: cho phép load từ những trang https

- Lab :
	+ 

## Mitigating dangling markup attacks using CSP.


## Bypassing CSP with policy injection.

## Protecting against clickjacking using CSP.