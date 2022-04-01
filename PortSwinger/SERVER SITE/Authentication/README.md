# Authentication vulnerabilities

## Xác thực.

- Xác thực là quá trình dùng để xác minh danh tính người dùng hay khách hàng , đảm bảo rằng người xác thực chính là họ. Xác thực là một phần không thể thiều trong bảo mật web .

- Các cơ chế xác thực :
	1. *Knowledge factors* , những cái bạn biết như mật khẩu , câu hỏi bảo mật.
	2. *Possession factors* , những cái bạn có như điện thoại , mã xác thực.
	3. *Inherence factors* , những thức như hành vi hay sinh trắc học (vân tay , khuôn mặt, ...).

- Sự khác biệt giữa xác thực và ủy quyền :
	- Xác thực có nghĩa là xác nhận danh tính của riêng bạn, trong khi ủy quyền có nghĩa là cấp quyền truy cập vào hệ thống. ... 
	- Nói một cách đơn giản, xác thực là quá trình xác minh bạn là ai, trong khi ủy quyền là quá trình xác minh những gì bạn có quyền truy cập vào.

## Nguyên nhân phát sinh các lỗ hổng xác thực.

- Các cơ chế xác thực yếu vì chúng không bảo vệ được đầy đủ trước các cuộc tấn công brute-force.
- Các lỗ hổng logic hoặc mã hóa kém trong quá trình triển khai cho phép kẻ tấn công bỏ qua hoàn toàn các cơ chế xác thực. Điều này đôi khi được gọi là "Broken authentication".

## Tác động.

- Cho phép bỏ qua xác thực và kẻ tấn công có thể truy cập vào tất cả các tài nguyên mà tài khoản bị tấn công được phép truy cập từ các tài khoản đặc quyền thấp đến các tài khoản với đặc quyền cao như quản trị viên .

## Các lỗ hổng.

1. [Các lỗ hổng trong đăng nhập dựa trên mật khẩu](brute-force.md).
2. [Các lỗ hổng trong xác thực đa yếu tố](multi-factor.md).
3. [Vulnerabilities in other authentication mechanisms](mechanisms.md).