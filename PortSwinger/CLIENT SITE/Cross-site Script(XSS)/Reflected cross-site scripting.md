#  Reflected cross-site scripting

##  What is reflected cross-site scripting?

- Reflected XSS , phát sinh khi một ứng dụng nhận được dữ liệu trong một yêu cầu HTTP và bao gồm dữ liệu đó trong phản hồi tức thì theo cách không an toàn.

- VD :
URL : https://insecure-website.com/search?term=gift
Result : <p>You searched for: gift</p>
Exploit : https://insecure-website.com/search?term=<script>/*+Bad+stuff+here...+*/</script>
Result exploit : <p>You searched for: <script>/* Bad stuff here... */</script></p>

- Lab:
	+ Lab: Reflected XSS into HTML context with nothing encoded.[exploit](exploit/lab1.py)

## Impact of reflected XSS attacks.

- Tác hại :
	+ Thực hiện bất kì hành động nào trong ứng dụng mà người dùng có thể thực hiện .
	+ Xem bất kì thông tin nào mà người dùng có thể sửa đổi.
	+ Sửa đổi bất kỳ thông tin nào mà người dùng có thể sửa đổi.
	+ Bắt đầu tương tác với người dùng khác , bao gồm cả các cuộc tấn công bắt nguồn từ nạn nhân đầu tiên.

### Exploiting cross-site scripting vulnerabilities.

- Cách hữu dụng để chứng minh trang web bị xss là làm cho nó hiển thị một thông báo (alert()) trên trình duyệt - điều này chứng tỏ câu lệnh js được thực thi.

1. Exploiting cross-site scripting to steal cookies.

	- Đánh cắp cookie là cách thông dụng để khai thác lỗi XSS, vì hầu hết website dùng cookie để xử lí phiên. Ta có thể lấy cookie của nạn nhân và mạo danh họ truy cập hệ thống.

	- Hạn chế :
		+ Nạn nhân không đăng nhập.
		+ Nhiều ứng dụng ẩn cookie (HttpOnly).
		+ Các phiên bị ràng buộc bởi yếu tố thứ 3 (IP,...).
		+ Phiên hết hạn.

	- Lab :
		+ Lab: Exploiting cross-site scripting to steal cookies.[exploit](exploit/lab2.txt)
2. Exploiting cross-site scripting to capture passwords.

	- Ngày nay, nhiều người dùng có trình quản lý mật khẩu tự động điền mật khẩu của họ. Tận dụng điều này bằng cách tạo một đầu vào mật khẩu, đọc mật khẩu được điền tự động và gửi mật khẩu đó đến miền mà attacker kiểm soát .

	- Nhược điểm chính của kỹ thuật này là nó chỉ hoạt động trên những người dùng có trình quản lý mật khẩu thực hiện tự động điền mật khẩu. (Tất nhiên, nếu người dùng chưa lưu mật khẩu, bạn vẫn có thể cố lấy mật khẩu của họ thông qua một cuộc tấn công lừa đảo tại chỗ, nhưng không hoàn toàn giống như vậy.)

	- Lab:
		+ Lab: Exploiting cross-site scripting to capture passwords.[exploit](exploit/lab3.txt)

3. Exploiting cross-site scripting to perform CSRF.

	- Bất cứ điều gì người dùng hợp pháp có thể làm trên một trang web, bạn cũng có thể làm với XSS. Tùy thuộc vào trang web bạn đang nhắm mục tiêu, bạn có thể khiến nạn nhân gửi tin nhắn, chấp nhận yêu cầu kết bạn, cam kết một cửa sau vào kho lưu trữ mã nguồn hoặc chuyển một số Bitcoin.
	
	- Một số trang web cho phép người dùng đã đăng nhập thay đổi địa chỉ email của họ mà không cần nhập lại mật khẩu. Nếu bạn đã tìm thấy lỗ hổng XSS, bạn có thể kích hoạt chức năng này để thay đổi địa chỉ email của nạn nhân thành địa chỉ email mà bạn kiểm soát, sau đó kích hoạt đặt lại mật khẩu để có quyền truy cập vào tài khoản.
	
	- Lab:
		+ Lab: Exploiting XSS to perform CSRF.[exploit](exploit/lab4.txt)


## Reflected XSS in different contexts.

- Có nhiều loại kịch bản Reflected XSS khác nhau. Vị trí của dữ liệu được phản ánh trong phản hồi của ứng dụng xác định loại trọng tải nào được yêu cầu để khai thác nó và cũng có thể ảnh hưởng đến tác động của lỗ hổng bảo mật.

- Ngoài ra, nếu ứng dụng thực hiện bất kỳ quá trình xác thực hoặc xử lý nào khác đối với dữ liệu đã gửi trước khi nó được phản ánh, điều này nói chung sẽ ảnh hưởng đến payload XSS cần thiết.

- [Cross-site scripting contexts](XSS_contexts.md).

## How to find and test for reflected XSS vulnerabilities.

- Kiểm tra mọi điểm đầu vào. Kiểm tra riêng từng điểm nhập dữ liệu trong các yêu cầu HTTP của ứng dụng. Điều này bao gồm các tham số hoặc dữ liệu khác trong chuỗi truy vấn URL và nội dung thư cũng như đường dẫn tệp URL. Nó cũng bao gồm các tiêu đề HTTP, mặc dù hành vi giống XSS chỉ có thể được kích hoạt thông qua một số tiêu đề HTTP nhất định có thể không khai thác được trong thực tế.

- Gửi các giá trị chữ và số ngẫu nhiên. Đối với mỗi điểm nhập, hãy gửi một giá trị ngẫu nhiên duy nhất và xác định xem giá trị đó có được phản ánh trong phản hồi hay không.

- Xác định bối cảnh phản ánh. Đối với mỗi vị trí trong phản hồi nơi giá trị ngẫu nhiên được phản ánh, hãy xác định ngữ cảnh của nó. Điều này có thể ở dạng văn bản giữa các thẻ HTML, trong thuộc tính thẻ có thể được trích dẫn, trong chuỗi JavaScript, v.v.

- Kiểm tra tải trọng thay thế. Nếu tải trọng XSS đã bị ứng dụng sửa đổi hoặc bị chặn hoàn toàn, thì bạn sẽ cần phải kiểm tra tải trọng và kỹ thuật thay thế có thể cung cấp một cuộc tấn công XSS hoạt động dựa trên ngữ cảnh của phản ánh và loại xác thực đầu vào đang được thực hiện.

- Kiểm tra cuộc tấn công trong trình duyệt. Cuối cùng, nếu bạn thành công trong việc tìm kiếm một trọng tải dường như hoạt động trong Burp Repeater, hãy chuyển cuộc tấn công sang một trình duyệt thực.