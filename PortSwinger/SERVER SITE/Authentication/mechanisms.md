# Vulnerabilities in other authentication mechanisms

## Keeping users logged in

- Một tính năng phổ biến là tùy chọn để duy trì trạng thái đăng nhập ngay cả sau khi đóng phiên trình duyệt.
- Chức năng này thường được triển khai bằng cách tạo một loại mã thông báo "remember me", sau đó được lưu trữ trong một cookie liên tục trong toàn bộ quá trình đăng nhập nên cookie này là không thể đoán được. Tuy nhiên, một số trang web tạo cookie này một cách dễ đoán và kẻ tấn công có thể dễ dàng suy ra từ đó để tấn công các tài khoản khác ví dụ như brute-force cookie.
- Một số trang web sử dụng các thuật toán mã hóa yếu hay triển khai không đúng cách các mã hóa mạnh cũng là nguy cơ để lợi dụng.

- Lab :
	- Lab: Brute-forcing a stay-logged-in cookie.([exploit](exploit/lab11.py))
	- Lab: Offline password cracking.([exploit](exploit/lab12.py))

## Resetting user passwords

1. Sending passwords by email.

	- Một số trang web tạo mật khẩu mới và gửi mật khẩu này cho người dùng qua email.
	- Việc gửi mật khẩu liên tục qua các kênh không an toàn là điều nên tránh. Trong trường hợp này, bảo mật dựa vào mật khẩu được tạo sẽ hết hạn sau một thời gian rất ngắn hoặc người dùng thay đổi lại mật khẩu của họ ngay lập tức. Nếu không, cách tiếp cận này rất dễ bị tấn công bởi kẻ trung gian.
	- Email thường không được coi là an toàn vì hộp thư đến không thực sự được thiết kế để lưu trữ an toàn thông tin bí mật. Nhiều người dùng cũng tự động đồng bộ hóa hộp thư đến của họ giữa nhiều thiết bị trên các kênh không an toàn. 

2. Resetting passwords using a URL.

	- Một phương pháp đặt lại mật khẩu mạnh mẽ hơn là gửi một URL duy nhất cho người dùng để đưa họ đến trang đặt lại mật khẩu.Tuy nhiên trong trường hợp truyền tham số dễ đoán ngay trên url có thể gây nguy hiểm.
	- Cách triển khai tốt hơn của quá trình này là tạo mã thông báo entropy cao, khó đoán và tạo URL đặt lại dựa trên đó. Trong trường hợp tốt nhất, URL này sẽ không cung cấp gợi ý về mật khẩu của người dùng nào đang được đặt lại.
	- Tuy nhiên, một số trang web cũng không thể xác thực lại mã thông báo . Trong trường hợp này, kẻ tấn công có thể chỉ cần đặt mật khẩu lại từ tài khoản của chính họ, xóa mã thông báo và tận dụng trang này để đặt lại mật khẩu của người dùng tùy ý.
	- Nếu URL trong email đặt lại được tạo động, thì URL này cũng có thể dễ bị lợi dụng. Trong trường hợp này, kẻ tấn công có thể đánh cắp mã thông báo của người dùng khác và sử dụng nó để thay đổi mật khẩu của họ.

	- Lab:
		- Lab: Password reset broken logic.([exploit](exploit/lab13.txt))
		- Lab: Password reset poisoning via middleware.([exploit](exploit/lab14.py))


## Changing user passwords

- Chức năng thay đổi mật khẩu có thể đặc biệt nguy hiểm nếu nó cho phép kẻ tấn công truy cập trực tiếp mà không cần đăng nhập với tư cách là người dùng nạn nhân. 
*Ví dụ*: nếu tên người dùng được cung cấp trong trường ẩn, kẻ tấn công có thể chỉnh sửa giá trị này trong yêu cầu để nhắm mục tiêu người dùng tùy ý. Điều này có thể bị lợi dụng để liệt kê tên người dùng và mật khẩu brute-force.

- Lab:
	- Lab: Password brute-force via password change.([exploit](exploit/lab15.py))