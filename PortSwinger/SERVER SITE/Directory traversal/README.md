# Directory traversal

## What is directory traversal?

- Directory traversal (path traversal), là một lỗ hổng bảo mật web cho phép kẻ tấn công đọc các tệp tùy ý trên máy chủ bao gồm source code , dữ liệu , thông tin nhạy cảm ,...

- Trong một số trường hợp , kẻ tấn công có thể ghi vào các tệp tùy ý trên máy chủ, cho phép chúng sửa đổi dữ liệu, hành vi của ứng dụng và kiểm soát hệ thống .

##  Reading arbitrary files via directory traversal

- Một ứng dụng hiển thị hình ảnh và lưu trữ hình ảnh trên thư mục */var/www/images/*.

- Khi truy cập một hình ảnh bất kì bạn sẽ được cung cấp một url có dạng *https://host/loadImage?filename=x.jpg* .

- Điều này có thể ấn chứa rủi ro nếu không có bất kì biện pháp bảo vệ nào , giá trị **filename** có thể được lợi dụng để đọc các tệp tùy ý .

- Lab :
	- Lab: File path traversal, simple case.([exploit](exploit/lab1.py))

## Common obstacles to exploiting file path traversal vulnerabilities

- Một số ứng dụng có thể triển khai một số biện pháp bảo vệ cơ bản tuy nhiên có thể bị bỏ qua một số kĩ thuật dưới đây .
	
	+ Sử dụng đường dẫn tuyệt đối . Lab: File path traversal, traversal sequences blocked with absolute path bypass.([exploit](exploit/lab2.py))

	+ Sử dụng kí tự lồng nhau (....//, ....\/). Lab: File path traversal, traversal sequences stripped non-recursively.([exploit](exploit/lab3.py))

	+ Encode các kí tự đặc biệt (%2e%2e%2f,  , ..). Lab: File path traversal, traversal sequences stripped with superfluous URL-decode.([exploit](exploit/lab4.py))

	+ Nếu web yêu cầu truyền vào đường dẫn dự kiến để xem tệp thì có thể bao gồm payload trong đường dẫn trên (filename=/var/www/images/../../../etc/passwd). Lab: File path traversal, validation of start of path. ([exploit](exploit/lab5.py))

	+ Nếu web yêu cầu tệp với phần mở rộng dự kiến có thể bypass bằng nullbyte (%00). Lab: File path traversal, validation of file extension with null byte bypass. ([exploit](exploit/lab6.py))


## How to prevent a directory traversal attack

- Cách hiệu quả nhất để ngăn chặn path traversal là tránh chuyển hoàn toàn đầu vào do người dùng cung cấp tới các API hệ thống tệp.

- Nếu cần phải truyền đầu vào do người dùng kiểm soát :

	+ Xác thực đầu vào bằng các whitelist , chỉ cho phép các nội dung hợp lệ và giới hạn các kí tự đầu vào (số , chữ).
	+ Chuẩn hóa đường dẫn , bắt đầu với thư mục dự kiến.