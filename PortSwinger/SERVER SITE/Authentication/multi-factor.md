# Vulnerabilities in multi-factor authentication

## Two-factor authentication tokens

- Mã xác minh thường được người dùng đọc từ một thiết bị vật lý nào đó. Nhiều trang web bảo mật cao hiện cung cấp cho người dùng một thiết bị chuyên dụng cho mục đích này. Ngoài mục đích được xây dựng để bảo mật, các thiết bị chuyên dụng này còn có lợi thế là tạo mã xác minh trực tiếp. 

- Mặt khác, một số trang web gửi mã xác minh đến điện thoại di động của người dùng dưới dạng tin nhắn văn bản, tuy nhiên nó vẫn dễ bị lạm dụng. Thứ nhất, mã đang được truyền qua SMS điều này tạo ra khả năng mã bị chặn. Ngoài ra còn có nguy cơ tráo đổi SIM, theo đó kẻ gian lấy được thẻ SIM có số điện thoại của nạn nhân. Sau đó, kẻ tấn công sẽ nhận được tất cả các tin nhắn SMS được gửi đến nạn nhân, bao gồm cả tin nhắn chứa mã xác minh của họ.

1. Bypassing two-factor authentication.
	
	- Đôi khi, việc triển khai xác thực hai yếu tố còn thiếu sót đến mức có thể bị bỏ qua hoàn toàn.

	- Đầu tiên người dùng được nhắc nhập mật khẩu, sau đó nhập mã xác minh trên một trang riêng biệt, thì người dùng thực sự ở trạng thái "đã đăng nhập" trước khi họ nhập mã xác minh. Trong trường hợp này, bạn nên thử nghiệm để xem liệu bạn có thể trực tiếp chuyển đến các trang "đăng nhập" sau khi hoàn thành bước xác thực đầu tiên hay không. Đôi khi, bạn sẽ thấy rằng một trang web không thực sự kiểm tra xem bạn đã hoàn thành bước thứ hai hay chưa trước khi tải trang.

	- Lab:
		- Lab: 2FA simple bypass.([exploit](exploit/lab7.txt))

2. Flawed two-factor verification logic.

	- Đôi khi logic sai sót trong xác thực hai yếu tố có nghĩa là sau khi người dùng đã hoàn thành bước đăng nhập ban đầu, trang web không xác minh đầy đủ rằng chính người dùng đó đang hoàn thành bước thứ hai.
	- Khi bạn xác thực thành công bước đầu , web se tạo cho bạn một cookie liên quan đến tài khoản để tạo mã xác thực ,điều này có thể bị lợi dụng để thay đổi giá trị cookie và yêu cầu web tạo mã xác thực cho tài khoản nạn nhân.
	- Sau đó kẻ tấn công có thể dò đoán mã xác minh vì nó sẽ cho phép chúng đăng nhập vào tài khoản của nạn nhân hoàn toàn dựa trên tên người dùng của họ. Họ thậm chí sẽ không cần biết mật khẩu của nạn nhân.

	- Lab:
		- Lab: 2FA broken logic.([exploit](exploit/lab8.py))


3. Brute-forcing 2FA verification codes.

