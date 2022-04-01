# Access control vulnerabilities and privilege escalation.

## What is access control?

- Access control (or authorization) là việc áp dụng các ràng buộc đối với ai (hoặc cái gì) có thể thực hiện các hành động hoặc truy cập tài nguyên mà họ yêu cầu. Trong ngữ cảnh của các ứng dụng web, kiểm soát truy cập phụ thuộc vào xác thực và quản lý phiên:
	+ Authentication xác định người dùng và xác nhận rằng họ là chính họ.
	+ Session management xác định các yêu cầu HTTP đang được thực hiện bởi chính người dùng đó.
	+ Access Control xác định xem người dùng có được phép thực hiện hành động mà họ đang cố gắng thực hiện hay không.

- Broken access controls là một lỗ hổng bảo mật thường gặp và nghiêm trọng.

- Các loại access control :
	+ Vertical access controls.(dọc)
	+ Horizontal access controls.(ngang)
	+ Context-dependent access controls.(ngữ cảnh)

- Mô hình bảo mật kiểm soát truy cập là một định nghĩa được xác định chính thức về một tập hợp các quy tắc kiểm soát truy cập độc lập với công nghệ hoặc nền tảng triển khai.
	+ Programmatic access control.
	+ Discretionary access control (DAC).
	+ Mandatory access control (MAC).
	+ Role-based access control (RBAC).

### Vertical access controls.

- Kiểm soát truy cập theo chiều dọc là cơ chế hạn chế quyền truy cập vào chức năng nhạy cảm không có sẵn cho các kiểu người dùng khác.

### Horizontal access controls.

- Kiểm soát truy cập theo chiều ngang là cơ chế hạn chế quyền truy cập vào tài nguyên đối với những người dùng được phép truy cập cụ thể vào các tài nguyên đó.

### Context-dependent access controls.

- Kiểm soát truy cập phụ thuộc vào ngữ cảnh hạn chế quyền truy cập vào chức năng và tài nguyên dựa trên trạng thái của ứng dụng hoặc tương tác của người dùng với nó.
- Các kiểm soát truy cập phụ thuộc vào ngữ cảnh ngăn người dùng thực hiện các hành động không đúng thứ tự. 


## Examples of broken access controls.

### Vertical privilege escalation.

- Nếu người dùng có thể có quyền truy cập vào chức năng mà họ không được phép truy cập thì đây là sự leo thang đặc quyền theo chiều dọc.

#### Unprotected functionality.

- Ở mức cơ bản nhất, sự leo thang đặc quyền theo chiều dọc phát sinh khi ứng dụng không thực thi bất kỳ biện pháp bảo vệ nào đối với chức năng nhạy cảm. Thông thường người dùng có thể sử dụng các chức năng quản trị khi chỉ cần duyệt đến URL tương ứng.

- Lab :
	+ Lab: Unprotected admin functionality.[exploit](exploit/lab1.py)

- Trong một số trường hợp, chức năng nhạy cảm không được bảo vệ mạnh mẽ nhưng được che giấu bằng cách cung cấp cho nó một URL khó đoán : được gọi là bảo mật bằng cách che khuất. Chỉ ẩn chức năng nhạy cảm không cung cấp khả năng kiểm soát truy cập hiệu quả vì người dùng vẫn có thể phát hiện ra URL theo nhiều cách khác nhau.

- Lab :
	+ Lab: Unprotected admin functionality with unpredictable URL.[exploit](exploit/lab2.py)


#### Parameter-based access control methods.

- Một số ứng dụng xác định quyền truy cập hoặc vai trò của người dùng khi đăng nhập, sau đó lưu trữ thông tin này ở vị trí người dùng có thể kiểm soát, chẳng hạn như trường ẩn, cookie hoặc tham số chuỗi truy vấn đặt trước. Ứng dụng đưa ra các quyết định kiểm soát truy cập tiếp theo dựa trên giá trị đã gửi.

- Cách này không an toàn vì người dùng có thể chỉ cần sửa đổi giá trị và có quyền truy cập vào chức năng mà họ không được phép.

- Lab:
	+ Lab: User role controlled by request parameter.[exploit](exploit/lab3.py)
	+ Lab: User role can be modified in user profile.[exploit](exploit/lab4.py)

#### Broken access control resulting from platform misconfiguration.


### Horizontal privilege escalation.

### Horizontal to vertical privilege escalation.

### Insecure direct object references.

### Access control vulnerabilities in multi-step processes.


### Referer-based access control.


### Location-based access control.

## How to prevent access control vulnerabilities.


