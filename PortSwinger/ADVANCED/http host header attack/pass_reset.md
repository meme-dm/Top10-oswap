# Password reset poisoning.

- Đầu độc đặt lại mật khẩu là một kỹ thuật theo đó kẻ tấn công thao túng một trang web dễ bị tấn công để tạo liên kết đặt lại mật khẩu trỏ đến một miền dưới sự kiểm soát của chúng. Hành vi này có thể được tận dụng để đánh cắp các mã thông báo bí mật cần thiết để đặt lại mật khẩu của người dùng tùy ý và cuối cùng, xâm phạm tài khoản của họ.

## How does a password reset work?

- Nhiều website cho phép người dùng đặt lại mật khẩu nếu họ quên. Có một số cách để thực hiện việc này. Một trong những cách tiếp cận phổ biến nhất diễn ra như sau:

	+ Người dùng nhập tên người dùng hoặc địa chỉ email của họ và gửi yêu cầu đặt lại mật khẩu.
	+ Trang web kiểm tra xem người dùng này có tồn tại hay không và sau đó tạo mã thông báo tạm thời, duy nhất, có entropy cao, mã này liên kết với tài khoản của người dùng.
	+ Trang web sẽ gửi một email đến người dùng có chứa liên kết để đặt lại mật khẩu của họ. Mã thông báo đặt lại duy nhất của người dùng được bao gồm dưới dạng tham số truy vấn trong URL tương ứng:

	> https://normal-website.com/reset?token=0a1b2c3d4e5f6g7h8i9j

	+ Khi người dùng truy cập URL này, trang web sẽ kiểm tra xem mã thông báo được cung cấp có hợp lệ hay không và sử dụng mã này để xác định tài khoản nào đang được đặt lại. Nếu mọi thứ như mong đợi, người dùng được cung cấp tùy chọn để nhập mật khẩu mới. Cuối cùng, mã thông báo bị hủy.

- Tính bảo mật của nó dựa trên nguyên tắc rằng chỉ người dùng dự định mới có quyền truy cập vào hộp thư đến email của họ và mã token là duy nhất. Đầu độc đặt lại mật khẩu là một phương pháp đánh cắp mã thông báo này để thay đổi mật khẩu của người dùng khác.

## How to construct a password reset poisoning attack.

- Kẻ tấn công lấy được địa chỉ email hoặc tên người dùng của nạn nhân, gửi yêu cầu đặt lại mật khẩu của nạn nhân. Khi gửi biểu mẫu, họ chặn yêu cầu HTTP và sửa đổi *HTTP Host Header* để nó trỏ đến miền mà họ kiểm soát.
- Nạn nhân nhận được một email đặt lại mật khẩu, chứa một liên kết thông thường để đặt lại mật khẩu của họ và quan trọng là chứa một mã thông báo đặt lại mật khẩu hợp lệ được liên kết với tài khoản của họ. Tuy nhiên, tên miền trong URL trỏ đến máy chủ của kẻ tấn công: https://evil-user.net/reset?token=0a1b2c3d4e5f6g7h8i9j
- Nếu nạn nhân nhấp vào liên kết này, mã thông báo đặt lại mật khẩu sẽ được gửi đến máy chủ của kẻ tấn công.
- Kẻ tấn công hiện có thể truy cập trang web dễ bị tấn công và cung cấp mã thông báo bị đánh cắp của nạn nhân. Sau đó, họ sẽ có thể đặt lại mật khẩu của người dùng và xâm phạm tài khoản.

Ngay cả khi bạn không thể kiểm soát liên kết đặt lại mật khẩu, đôi khi bạn có thể sử dụng tiêu đề Máy chủ lưu trữ để đưa HTML vào các email nhạy cảm. Lưu ý rằng các ứng dụng email thường không thực thi JavaScript, nhưng các kỹ thuật chèn HTML khác như các cuộc tấn công dangling markup vẫn có thể áp dụng.

- Lab:
	+ Lab: Basic password reset poisoning.[exploit](exploit/lab1.py)
	+ Lab: Password reset poisoning via dangling markup.[exploit](exploit/lab2.py)