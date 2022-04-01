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

	- Lab.

		- Lab: Username enumeration via different responses.([exploit](exploit/lab1.py))
		- Lab: Username enumeration via subtly different responses.([exploit](exploit/lab2.py))
		- Lab: Username enumeration via response timing.([exploit](exploit/lab3.py))


5. Flawed brute-force protection.

	- Về mặt logic, bảo vệ brute-force xoay quanh việc cố gắng làm cho nó phức tạp nhất có thể để tự động hóa quá trình và làm chậm tốc độ mà kẻ tấn công có thể cố gắng đăng nhập. Hai cách phổ biến nhất để ngăn chặn các cuộc tấn công brute-force là:
		- Khóa tài khoản mà người dùng từ xa đang cố gắng truy cập nếu họ thực hiện quá nhiều lần đăng nhập không thành công.
		- Chặn địa chỉ IP của người dùng từ xa nếu họ thực hiện quá nhiều lần đăng nhập liên tiếp.
	- Lab:
		- Lab: Broken brute-force protection, IP block.([exploit](exploit/lab4.py))

6. Account locking.

	- Việc khóa tài khoản cung cấp một lớp bảo vệ nhất định chống lại hành vi brute-force có mục tiêu đối với một tài khoản cụ thể. Tuy nhiên, cách tiếp cận này không ngăn chặn được đầy đủ các cuộc tấn công brute-force trong đó kẻ tấn công chỉ đang cố gắng giành quyền truy cập vào bất kỳ tài khoản ngẫu nhiên nào mà chúng có thể.
	- Các cách bỏ qua lớp bảo vệ này :
		- Thiết lập danh sách tên người dùng có khả năng hợp lệ. Điều này có thể thông qua liệt kê tên người dùng hoặc đơn giản là dựa trên danh sách các tên người dùng phổ biến.
		- Quyết định một danh sách rất nhỏ các mật khẩu mà bạn nghĩ rằng ít nhất một người dùng có thể có. Điều quan trọng, số lượng mật khẩu bạn chọn không được vượt quá số lần đăng nhập được phép.
		- Thử từng mật khẩu đã chọn với từng tên người dùng ứng viên. Bằng cách này, bạn có thể cố gắng cưỡng chế mọi tài khoản mà không cần kích hoạt khóa tài khoản. Bạn chỉ cần một người dùng duy nhất sử dụng một trong ba mật khẩu để xâm phạm tài khoản.

	- Lab:
		- Lab: Username enumeration via account lock.([exploit](exploit/lab5.py))

7. User rate limiting.

	- Một cách khác mà các trang web cố gắng ngăn chặn các cuộc tấn công brute-force là thông qua giới hạn tỷ lệ người dùng. Trong trường hợp này, việc thực hiện quá nhiều yêu cầu đăng nhập trong một khoảng thời gian ngắn khiến địa chỉ IP của bạn bị chặn. Thông thường, chỉ có thể bỏ chặn IP bằng một trong các cách sau:
		- Tự động sau một khoảng thời gian nhất định.
		- Do quản trị viên bỏ chặn.
		- Do người dùng sau khi hoàn thành CAPTCHA thành công.
	- Giới hạn tỷ lệ người dùng đôi khi được ưu tiên hơn là khóa tài khoản do ít bị liệt kê tên người dùng và các cuộc tấn công từ chối dịch vụ. Tuy nhiên, nó vẫn không hoàn toàn an toàn. 
	- Vì giới hạn dựa trên tỷ lệ yêu cầu HTTP được gửi từ địa chỉ IP của người dùng, đôi khi cũng có thể vượt qua lớp bảo vệ này nếu bạn có thể tìm ra cách đoán nhiều mật khẩu với một yêu cầu duy nhất.

	- Lab: 
		- Lab: Broken brute-force protection, multiple credentials per request.([exploit](exploit/lab6.py))