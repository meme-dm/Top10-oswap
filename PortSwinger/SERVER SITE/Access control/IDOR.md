# Insecure direct object references (IDOR).

## What are insecure direct object references (IDOR)?

- Tham chiếu đối tượng trực tiếp không an toàn (IDOR) là một loại lỗ hổng kiểm soát truy cập phát sinh khi ứng dụng sử dụng đầu vào do người dùng cung cấp để truy cập trực tiếp các đối tượng. 
- Các lỗ hổng IDOR thường liên quan đến việc leo thang đặc quyền theo chiều ngang, nhưng chúng cũng có thể phát sinh liên quan đến leo thang đặc quyền theo chiều dọc.

## IDOR examples.

### IDOR vulnerability with direct reference to database objects.

- Ưeb sử dụng URL sau để truy cập trang tài khoản khách hàng, bằng cách truy xuất thông tin từ cơ sở dữ liệu:

> https://insecure-website.com/customer_account?customer_number=132355

- Nếu không có biện pháp kiểm soát nào khác, kẻ tấn công có thể chỉ cần sửa đổi *customer_number* , bỏ qua các kiểm soát truy cập để xem tài nguyên của các khách hàng khác. (Leo thang đặc quyền theo chiều ngang)

- Kẻ tấn công có thể thực hiện leo thang đặc quyền theo chiều ngang và chiều dọc bằng cách thay đổi người dùng thành một người có đặc quyền admin trong khi bỏ qua các kiểm soát truy cập. Các khả năng khác bao gồm khai thác rò rỉ mật khẩu hoặc sửa đổi các thông số sau khi kẻ tấn công đã truy cập vào trang tài khoản của người dùng.

### IDOR vulnerability with direct reference to static files.

- Các lỗ hổng IDOR thường phát sinh khi các tài nguyên nhạy cảm nằm trong các tệp tĩnh trên hệ thống tệp phía máy chủ. 

- Ví dụ: một trang web có thể lưu các bản ghi tin nhắn trò chuyện bằng cách sử dụng tên tệp tăng dần và cho phép người dùng truy xuất các bản ghi này bằng cách truy cập vào một URL như sau:

https://insecure-website.com/static/12144.txt

Trong tình huống này, kẻ tấn công có thể chỉ cần sửa đổi tên tệp để truy xuất bản ghi do người dùng khác tạo và có khả năng lấy được thông tin đăng nhập của người dùng và dữ liệu nhạy cảm khác.

- Lab:
	+ Lab: Insecure direct object references.[exploit](lab11.py)