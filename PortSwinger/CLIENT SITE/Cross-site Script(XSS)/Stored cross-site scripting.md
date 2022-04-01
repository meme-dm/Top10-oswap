# Stored XSS

## What is stored cross-site scripting?

- Tập lệnh liên trang được lưu trữ (aka second-order or persistent XSS) phát sinh khi một ứng dụng nhận dữ liệu từ một nguồn không đáng tin cậy và bao gồm dữ liệu đó trong các phản hồi HTTP sau này của nó theo cách không an toàn.

- VD:
	Post comment : 
	```
	POST /post/comment HTTP/1.1
	Host: vulnerable-website.com
	Content-Length: 100
	postId=3&comment=This+post+was+extremely+helpful.&name=Carlos+Montoya&email=carlos%40normal-user.net
	```
	Display comment : <p>This post was extremely helpful.</p>

	Exploit post comment : comment=%3Cscript%3E%2F*%2BBad%2Bstuff%2Bhere...%2B*%2F%3C%2Fscript%3E
	Display exploit : <p><script>/* Bad stuff here... */</script></p>

- Lab :
	+ Lab: Stored XSS into HTML context with nothing encoded.[exploit](lab5.txt)

## Impact of stored XSS attacks.

- Nếu kẻ tấn công có thể kiểm soát một tập lệnh được thực thi trong trình duyệt của nạn nhân, thì chúng thường có thể xâm phạm hoàn toàn người dùng đó.

- Về khả năng khai thác, sự khác biệt chính giữa Reflected XSS  và Store XSS là việc cho phép các cuộc tấn công độc lập trong chính ứng dụng. Kẻ tấn công không cần phải tìm một cách bên ngoài để lôi kéo người dùng khác thực hiện một yêu cầu cụ thể có chứa hoạt động khai thác của họ. Thay vào đó, kẻ tấn công đặt phần khai thác của chúng vào chính ứng dụng và chỉ cần đợi người dùng gặp nó. 

- Ngoài ra các cuộc tấn công Store XSS đảm bảo rằng nạn nhân đang trong phiên đăng nhập của mình.

### Exploiting cross-site scripting to steal cookies.

- Lab:
	+ Lab: Exploiting cross-site scripting to steal cookies.[exploit](exploit/lab6.txt)

### Exploiting cross-site scripting to capture passwords.

- Lab: 
	+ Lab: Exploiting cross-site scripting to capture passwords.[exploit](exploit/lab7.txt)

### Exploiting cross-site scripting to perform CSRF.

- Lab:
	+ Lab: Exploiting XSS to perform CSRF.[exploit](exploit/lab8.txt)


## Stored XSS in different contexts.

- [Cross-site scripting contexts](XSS_contexts.md).

## How to find and test for stored XSS vulnerabilitie.

- Bước đầu tiên trong việc kiểm tra các lỗ hổng XSS được lưu trữ là xác định các liên kết giữa các điểm vào và ra, theo đó dữ liệu được gửi đến một điểm vào được phát ra từ một điểm thoát.

- Làm việc có hệ thống thông qua các điểm nhập dữ liệu, gửi một giá trị cụ thể vào từng điểm và theo dõi phản hồi của ứng dụng để phát hiện các trường hợp xuất hiện giá trị đã gửi. Có thể đặc biệt chú ý đến các chức năng ứng dụng có liên quan, chẳng hạn như nhận xét trên các bài đăng trên blog.

