# Vulnerabilities in password-based login

## Brute-force attacks.

- Tấn công brute-force là khi kẻ tấn công sử dụng hệ thống thử và sai để cố gắng đoán thông tin đăng nhập hợp lệ của người dùng. Các cuộc tấn công này thường được tự động hóa bằng cách sử dụng danh sách từ gồm tên người dùng và mật khẩu. 
- Brute-force không phải lúc nào cũng chỉ là một trường hợp phỏng đoán hoàn toàn ngẫu nhiên về tên người dùng và mật khẩu. Bằng cách sử dụng logic cơ bản hoặc kiến ​​thức công khai, những kẻ tấn công có thể tinh chỉnh các cuộc tấn công brute-force để đưa ra các phỏng đoán có cơ sở hơn nhiều. 

1. Brute-forcing usernames.

- Liệt kê các username, hay các mẫu định sẵn của tài khoản , các tên đăng nhập hay dùng dễ đoán.
- Kiểm tra trên web hay trong phản hồi có chứa các username công khai hay không.

2. Brute-forcing passwords.

- Cố gắng đoán các mẫu mật khẩu mà người dùng hay sử dụng để vượt qua các chính sách mật khẩu và dễ nhớ .

3. Username enumeration.

- Liệt kê tên người dùng là khi kẻ tấn công có thể quan sát những thay đổi trong hành vi của trang web để xác định xem tên người dùng nhất định có hợp lệ hay không.
- Các khác biệt bạn cần chú ý .
	- Mã trạng thái : Trong một cuộc tấn công brute-force, mã trạng thái HTTP trả về có thể giống nhau đối với phần lớn các phỏng đoán ,nếu một phỏng đoán trả về một mã trạng thái khác thì có khả năng nó đúng. Cách tốt nhất là các trang web luôn trả về cùng một mã trạng thái bất kể kết quả như thế nào.
	- Thông báo lỗi : Đôi khi thông báo lỗi trả về khác nhau tùy thuộc vào việc cả tên người dùng và mật khẩu đều không chính xác hay chỉ có một trong hai không chính xác. Cách tốt nhất là các trang web nên sử dụng các thông báo giống nhau, chung chung trong các trường hợp.
	- Thời gian phản hồi : Nếu hầu hết các yêu cầu được xử lý với thời gian phản hồi tương tự, bất kỳ yêu cầu nào khác với điều này cho thấy có điều gì đó khác biệt đang xảy ra và nó là một dấu hiệu để nhận biết bạn đoán đúng hay sai.

4. Lab.

- Lab: Username enumeration via different responses.([exploit](exploit/lab1.py))
- Lab: Username enumeration via subtly different responses.([exploit](exploit/lab2.py))
- Lab: Username enumeration via response timing.([exploit](exploit/lab3.py))
