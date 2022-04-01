# File upload vulnerabilities

## What are file upload vulnerabilities?

- Lỗ hổng tải lên tệp xảy ra khi máy chủ web cho phép người dùng tải tệp lên tệp mà không xác nhận đầy đủ những thứ như tên, loại, nội dung hoặc kích thước của chúng. Không thực thi đúng các hạn chế đối với những điều này có thể có nghĩa là ngay cả một chức năng tải lên hình ảnh cơ bản cũng có thể được sử dụng để tải lên các tệp tùy ý và có khả năng nguy hiểm, dẫn đến RCE nếu một web shell được tải lên. 

## What is the impact of file upload vulnerabilities?

- Tác động của các lỗ hổng tải lên tệp thường phụ thuộc vào hai yếu tố chính:

	+ Các thành của tệp mà trang web không thể xác thực đúng cách, cho dù đó là kích thước, loại, nội dung, v.v.
	+ Những hạn chế được áp dụng cho tệp sau khi nó đã được tải lên thành công không hợp lý.

- Trong trường hợp xấu nhất, loại tệp không được xác thực đúng cách và cấu hình máy chủ cho phép một số loại tệp nhất định được thực thi dưới dạng mã từ đó kẻ tấn công có thể lợi dụng để tải lên một web shell và điều khiển hệ thống.

- Nếu tên tệp không được xác thực đúng cách, điều này có thể cho phép kẻ tấn công ghi đè các tệp quan trọng chỉ bằng cách tải lên tệp có cùng tên , hay việc máy chủ mắc lỗi directory travesal điều này có nghĩa là những kẻ tấn công thậm chí có thể tải tệp lên các vị trí khác nhau trên hệ thống.

- Không giới hạn kích thước của tệp ​​cũng có thể khiến web bị tấn công từ chối dịch vụ (DoS).

## How do file upload vulnerabilities arise?

- Các blacklist hoạt động không đúng hay bị thiếu sót.
- Tin tưởng vào filetype của tệp được upload.
- Các biện pháp xác thực không nhất quán.

## How do web servers handle requests for static files?

- Quá trình xử lý các tệp tĩnh này phần lớn vẫn giống nhau. Máy chủ phân tích cú pháp đường dẫn trong yêu cầu để xác định phần mở rộng tệp. Sau đó, nó sử dụng điều này để xác định loại tệp đang được yêu cầu, thường bằng cách so sánh nó với danh sách các ánh xạ được cấu hình sẵn giữa extensions and MIME types. Điều gì xảy ra tiếp theo phụ thuộc vào loại tệp và cấu hình của máy chủ:

	+ Nếu loại tệp này không thực thi được, chẳng hạn như hình ảnh hoặc trang HTML tĩnh, máy chủ có thể chỉ gửi nội dung của tệp đến máy khách trong một phản hồi HTTP.
	+ Nếu loại tệp có thể thực thi được, chẳng hạn như tệp PHP và máy chủ được cấu hình để thực thi các tệp thuộc loại này, nó sẽ gán các biến dựa trên tiêu đề và tham số trong yêu cầu HTTP trước khi chạy tập lệnh. Kết quả đầu ra sau đó có thể được gửi đến máy khách trong một phản hồi HTTP.
	+ Nếu loại tệp có thể thực thi, nhưng máy chủ không được định cấu hình để thực thi các tệp thuộc loại này, nó sẽ thường phản hồi với lỗi. Tuy nhiên, trong một số trường hợp, nội dung của tệp vẫn có thể được cung cấp cho khách hàng dưới dạng văn bản thuần túy. Những cấu hình sai như vậy đôi khi có thể bị lợi dụng để làm rò rỉ mã nguồn và các thông tin nhạy cảm khác.

## Exploiting unrestricted file uploads to deploy a web shell

-  Tình huống xấu nhất có thể xảy ra là khi một trang web cho phép bạn tải lên các tập lệnh phía máy chủ, chẳng hạn như tệp PHP, Java hoặc Python và cũng được định cấu hình để thực thi chúng dưới dạng mã. Điều này làm cho việc tạo web shell của riêng bạn trên máy chủ trở nên đơn giản.

- Lab :
	+ Lab: Remote code execution via web shell upload.([exploit](exploit/lab1.php))

## Exploiting flawed validation of file uploads

1. Flawed file type validation
	- Các trang web sử dụng *multipart/form-data* để upload file .
	- Biểu mẫu bao gồm *Content-Type*, tiêu đề này cho máy chủ biết kiểu MIME của dữ liệu được gửi.Nó hoàn toàn có thể bị lợi dụng và sửa đổi vì thế trong trường hợp website dùng nó để xác thực rất có khả năng sẽ bị tấn công.

	-Lab:
		+ Lab: Web shell upload via Content-Type restriction bypass.([exploit](exploit/lab2.txt))


2. Preventing file execution in user-accessible directories
	- Trong các thư mục lưu trữ tệp mà người dùng tải lên có thể được kiểm soát chặt chẽ và chỉ thực thi các tệp cho phép . Tuy nhiên , không phải thư mục nào trên hệ thống cũng bị giới hạn vậy nên kẻ tấn công hoàn toàn có thể tải lên tệp tới một thư mục khác để bỏ qua các hạn chế.

	-Lab:
		- Lab: Web shell upload via path traversal.([exploit](exploit/lab3.txt))

3. Insufficient blacklisting of dangerous file types

	- Overriding the server configuration.
		+ Hầu hết các máy chủ không cho phép thực thi mã từ phía client tải lên trừ khi được cấu hình.
		+ Vì thế nếu không được xác thực kĩ càng các tệp này hoàn toàn có thể bị ghi đè bằng các tệp độc hại do người dùng tải lên .

		+ Lab: Web shell upload via extension blacklist bypass.([exploit](exploit/.htaccess))

	- Obfuscating file extensions.
		+ Các extentions của tệp hoàn toàn có thể được giả mạo để bỏ qua blacklist hay các cơ chế kiểm soát bằng các cách sau :
			+ Cung cấp nhiều extentions . Tùy thuộc vào thuật toán được sử dụng để phân tích cú pháp tên tệp, tệp *exploit.php.jpg* có thể được hiểu là tệp PHP hoặc hình ảnh JPG .
			+ Thêm ký tự ở cuối.
			+ Sử dụng mã hóa URL (hoặc mã hóa URL kép) cho các dấu chấm và các dấu gạch chéo .
			+ Thêm dấu chấm phẩy hoặc ký tự byte rỗng được mã hóa URL trước phần mở rộng tệp.
			+ Sử dụng các ký tự unicode nhiều byte, có thể được chuyển đổi thành byte và dấu chấm rỗng sau khi chuyển đổi hoặc chuẩn hóa unicode. 

		+ Lab: Web shell upload via obfuscated file extension.([exploit](exploit/lab5.php%00.jpg))

	- Flawed validation of the file's contents.

		+ Thay vì tin tưởng vào content-type thì một số web xác thực bằng cách kiểm tra nội dung của tệp được tải lên.
		+ Một số loại tệp thường bắt đầu và kết thúc với các byte đặc trưng vì thế website có thể dựa vào đó để nhận biết các loại tệp cho phép. Tuy nhiên , điều này vẫn có thể lợi dụng để tạo ra các tệp hợp lệ chứa mã độc hại để qua mặt cơ chế xác thực. 

		+ Lab: Remote code execution via polyglot web shell upload.([exploit](exploit/lab6))

	- Exploiting file upload race conditions.

		+ Một số trang web tải tệp trực tiếp lên hệ thống tệp chính và sau đó xóa lại nếu tệp không vượt qua xác thực. Loại hành vi này là điển hình trong các trang web dựa vào phần mềm chống vi-rút và những thứ tương tự để kiểm tra phần mềm độc hại. Quá trình này có thể chỉ mất vài mili giây, nhưng trong thời gian ngắn mà tệp tồn tại trên máy chủ, kẻ tấn công vẫn có thể thực thi nó.
		+ Race condition có thể xảy ra trong các chức năng cho phép bạn tải tệp lên bằng cách cung cấp URL. Trong trường hợp này, máy chủ phải tìm nạp tệp qua internet và tạo bản sao cục bộ trước khi có thể thực hiện bất kỳ xác thực nào.
		+ Để thực hiện các cuộc tấn công như thế này dễ dàng hơn, bạn có thể cố gắng kéo dài khoảng thời gian cần thiết để xử lý tệp, bằng cách kéo dài tên thư mục. Hay tải lên một tệp lớn với payload đầu tiên và các byte tùy ý.

		+ Lab: Web shell upload via race condition.([exploit](exploit/lab7))


## How to prevent file upload vulnerabilities

- Kiểm tra phần mở rộng tệp với danh sách trắng gồm các phần mở rộng được phép thay vì danh sách đen gồm các phần mở rộng bị cấm. 
- Đảm bảo rằng tên tệp không chứa bất kỳ chuỗi con nào có thể được hiểu là thư mục hoặc chuỗi truyền tải ( ../).
- Đổi tên tệp đã tải lên để tránh va chạm có thể khiến tệp hiện có bị ghi đè.
- Không tải tệp lên hệ thống tệp vĩnh viễn của máy chủ cho đến khi chúng đã được xác thực hoàn toàn.
- Hãy sử dụng một framework đã thiết lập để xử lý trước quá trình tải lên tệp thay vì cố gắng viết các cơ chế xác thực của riêng bạn.