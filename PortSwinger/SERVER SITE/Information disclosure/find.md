# How to find and exploit information disclosure vulnerabilities.

## How to test for information disclosure vulnerabilities.

- Tránh tập trung quá hẹp vào một lỗ hổng cụ thể. Dữ liệu nhạy cảm có thể bị rò rỉ ở mọi nơi, vì vậy điều quan trọng là không bỏ lỡ bất kỳ thứ gì có thể hữu ích sau này. Bạn thường sẽ tìm thấy dữ liệu nhạy cảm trong khi thử nghiệm thứ khác. Một kỹ năng quan trọng là có thể nhận ra thông tin thú vị bất cứ khi nào và bất cứ nơi nào bạn bắt gặp nó.

- Fuzzing.
- Using Burp Scanner.
- Using Burp's engagement tools.
- Engineering informative responses.

## Common sources of information disclosure.

### Files for web crawlers.

- Các tệp/robots.txt và /sitemap.xml giúp thu thập thông tin điều hướng trang web. Các tệp này thường liệt kê các thư mục cụ thể ngăn các bot thu thập thông tin, vì chúng có thể chứa thông tin nhạy cảm.

### Directory listings.

- Máy chủ web có thể được cấu hình để tự động liệt kê nội dung của các thư mục. Điều này có thể cho phép kẻ tấn công nhanh chóng xác định các tài nguyên tại một con đường nhất định tiến hành phân tích và tấn công trực tiếp các tài nguyên đó. Làm tăng khả năng hiển thị các tệp nhạy cảm trong thư mục hạn chế truy cập.

- Directory listings không nhất thiết là một lỗ hổng bảo mật. Tuy nhiên, việc rò rỉ sự tồn tại và vị trí của các tài nguyên nhạy cảm rõ ràng là một vấn đề.

### Developer comments.

- Những bình luận của developer trong quá trình phát triển bị bỏ quên hay không ý thức dầy đủ về các tác động bảo mật. Đôi khi, những bình luận này chứa thông tin hữu ích cho kẻ tấn công. Ví dụ: gợi ý về sự tồn tại của các thư mục ẩn hoặc cung cấp manh mối logic ứng dụng.

### Error messages.

- Một trong những nguyên nhân phổ biến nhất của việc lộ thông tin là các thông báo lỗi dài dòng. Theo nguyên tắc chung, bạn nên chú ý đến tất cả các thông báo lỗi mà bạn gặp phải trong quá trình kiểm tra.

	+ Tiết lộ thông tin về đầu vào hoặc kiểu dữ liệu nào được mong đợi từ một tham số nhất định. 
	+ Cung cấp thông tin về các công nghệ đang được sử dụng bởi trang web.
	+ Có thể tiết lộ các framework mã nguồn mở mà ứng dụng đang dùng. 
	+ Sự khác biệt giữa các thông báo lỗi cũng có thể tiết lộ các hành vi ứng dụng khác nhau đang xảy ra.

- Lab :
	+ Lab: Information disclosure in error messages.[exploit](exploit/lab1.py)

### Debugging data.

- Nhiều trang web tạo ra các bản ghi và thông báo lỗi tùy chỉnh có chứa lượng lớn thông tin về hành vi của ứng dụng. Mặc dù thông tin này hữu ích trong quá trình phát triển, nhưng nó cũng cực kỳ hữu ích đối với kẻ tấn công nếu nó bị rò rỉ trong môi trường sản xuất.

- Thông báo gỡ lỗi đôi khi có thể chứa thông tin quan trọng để phát triển một cuộc tấn công, bao gồm:

	+ Giá trị các biến key session có thể được thao tác thông qua đầu vào của người dùng.
	+ Tên máy chủ và thông tin đăng nhập cho các thành phần back-end.
	+ Tên tệp và thư mục trên máy chủ.
	+ Các khóa sử dụng để mã hóa dữ liệu được truyền qua máy khách.

- Thông tin debug đôi khi được ghi vào một tệp riêng biệt.

- Lab:
	+ Lab: Information disclosure on debug page.[exploit](exploit/lab2.py) 

### User account pages.

### Source code disclosure via backup files.

- Có được quyền truy cập mã nguồn giúp kẻ tấn công hiểu được hành vi của ứng dụng và tiến hành các cuộc tấn công mức độ nghiêm trọng cao dễ dàng hơn nhiều. Dữ liệu nhạy cảm đôi khi thậm chí còn được mã hóa cứng trong mã nguồn. Các ví dụ điển hình về điều này bao gồm các khóa API và thông tin đăng nhập để truy cập các thành phần back-end.

- Nếu bạn có thể xác định rằng một công nghệ mã nguồn mở cụ thể đang được sử dụng, điều này giúp bạn dễ dàng truy cập vào một lượng mã nguồn hạn chế.

- Một số website lưu trữ các file backup cho phép kẻ tấn công có thể truy cập mã nguồn.

- Lab:
	+ Lab: Source code disclosure via backup files.[exploit](exploit/lab3.txt)

### Information disclosure due to insecure configuration.

- Các trang web đôi khi dễ bị tấn công do cấu hình không đúng. Điều này đặc biệt phổ biến do việc sử dụng rộng rãi các công nghệ của bên thứ ba, có rất nhiều tùy chọn cấu hình mà những người triển khai chúng không nhất thiết phải hiểu rõ.
- Trong các trường hợp khác, các nhà phát triển có thể quên tắt các tùy chọn gỡ lỗi khác nhau trong môi trường sản xuất. Ví dụ: TRACE phương thức HTTP được thiết kế cho mục đích chẩn đoán. Nếu được bật, máy chủ web sẽ phản hồi các yêu cầu sử dụng TRACE bằng cách lặp lại phản hồi yêu cầu chính xác đã nhận được. Hành vi này thường vô hại, nhưng đôi khi dẫn đến tiết lộ thông tin, chẳng hạn như tên của tiêu đề xác thực nội bộ có thể được thêm vào các yêu cầu bởi reverse proxy.

- Lab:
	+ Lab: Authentication bypass via information disclosure.[exploit](exploit/lab4.py)

### Version control history.

- Hầu như tất cả các trang web được phát triển bằng cách sử dụng một số dạng hệ thống kiểm soát phiên bản (GIT). Theo mặc định, một dự án Git lưu trữ tại thư mục .git. Đôi khi, các trang web để lộ thư mục này trong môi trường sản xuất , bạn có thể truy cập nó bằng cách chỉ cần duyệt đến /.git .

- Có thể không cung cấp mã nguồn đầy đủ, nhưng so sánh sự khác biệt sẽ cho phép bạn đọc các đoạn mã nhỏ. Như với bất kỳ mã nguồn nào, bạn cũng có thể tìm thấy dữ liệu nhạy cảm được mã hóa cứng trong một số dòng đã thay đổi.

- Lab:
	+ Lab: Information disclosure in version control history.[exploit](exploit/lab5.txt)