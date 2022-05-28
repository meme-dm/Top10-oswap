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

- Một số ứng dụng thực thi các kiểm soát truy cập ở lớp nền tảng bằng cách hạn chế quyền truy cập vào các URL và phương thức HTTP cụ thể dựa trên vai trò của người dùng.

- VD:

> DENY: POST, /admin/deleteUser, managers

- Từ chối quyền truy cập với method 'POST' của nhóm người dùng 'managers' vào /admin/deleteUser.
- Một số HTTP header có thể đc dùng để ghi đè URL (X-Original-URL, X-Rewrite-URL). 
- Nếu một trang web sử dụng các kiểm soát nghiêm ngặt để hạn chế quyền truy cập dựa trên URL, nhưng ứng dụng cho phép URL được ghi đè thông qua tiêu đề yêu cầu, thì có thể bỏ qua các kiểm soát truy cập. (X-Original-URL: /admin/deleteUser)

- Lab:
	+ Lab: URL-based access control can be circumvented.[exploit](lab5.py)

-  Một số trang web có thể chấp nhận các phương HTTP thay thế khi thực hiện một hành động. Nếu kẻ tấn công có thể sử dụng GET (hoặc một phương thức khác) để thực hiện các hành động trên một URL bị hạn chế, thì chúng có thể phá vỡ kiểm soát truy cập được triển khai ở lớp nền tảng.

- Lab:
	+ Lab: Method-based access control can be circumvented.[exploit](lab6.py)

### Horizontal privilege escalation.

- Sự leo thang đặc quyền theo chiều ngang phát sinh khi người dùng có thể có quyền truy cập vào tài nguyên thuộc về người dùng khác.
- Các cuộc tấn công leo thang đặc quyền theo chiều ngang có thể sử dụng các loại phương pháp khai thác tương tự như leo thang đặc quyền theo chiều dọc.

- VD:

> https://insecure-website.com/myaccount?id=123

- Chỉ cần thay đổi id là có thể truy cập vào tài nguyên của người dùng khác.

- Lab:
	+ Lab: User ID controlled by request parameter.[exploit](lab7.py)

- Trong một số ứng dụng, tham số có thể khai thác không có giá trị có thể dự đoán được. 
Ví dụ: thay vì một số tăng dần, một ứng dụng có thể sử dụng số nhận dạng duy nhất GUID để xác định người dùng làm cho kẻ tấn công không đoán được mã định danh cho người dùng khác. Tuy nhiên, các GUID của người dùng khác có thể được tiết lộ trong ứng dụng nơi người dùng được tham chiếu, chẳng hạn như tin nhắn hoặc bài đánh giá của người dùng.

- Lab:
	+ Lab: User ID controlled by request parameter, with unpredictable user IDs.[exploit](lab8.py)

- Trong một số trường hợp, ứng dụng phát hiện khi nào người dùng không được phép truy cập tài nguyên và trả về chuyển hướng đến trang đăng nhập. Tuy nhiên, phản hồi có chứa chuyển hướng vẫn có thể bao gồm một số dữ liệu nhạy cảm thuộc về người dùng được nhắm mục tiêu, vì vậy cuộc tấn công vẫn thành công.

- Lab:
	+ Lab: User ID controlled by request parameter with data leakage in redirect.[exploit](lab9.py)

### Horizontal to vertical privilege escalation.

- Một cuộc tấn công leo thang đặc quyền theo chiều ngang có thể được chuyển thành một cuộc tấn công leo thang đặc quyền theo chiều dọc, bằng cách làm ảnh hưởng đến người dùng có đặc quyền hơn. 
- Ví dụ: leo thang theo chiều ngang có thể cho phép kẻ tấn công đặt lại hoặc lấy mật khẩu của người dùng khác. Nếu kẻ tấn công nhắm mục tiêu người dùng quản trị và xâm phạm tài khoản của họ, thì họ có thể có quyền truy cập quản trị và do đó thực hiện leo thang đặc quyền theo chiều dọc.

- Lab :
	+ Lab: User ID controlled by request parameter with password disclosure.[exploit](lab10.py)


### Insecure direct object references.

- Tham chiếu đối tượng trực tiếp không an toàn (IDOR) là một danh mục con của các lỗ hổng kiểm soát truy cập. 
- IDOR phát sinh khi một ứng dụng sử dụng đầu vào do người dùng cung cấp để truy cập trực tiếp vào các đối tượng và kẻ tấn công có thể sửa đổi đầu vào để có được quyền truy cập trái phép. 

- Lab:
	+ Lab: Insecure direct object references.[exploit](lab11.py)

- [Insecure direct object references (IDOR)](IDOR.md)

### Access control vulnerabilities in multi-step processes.

- Nhiều trang web thực hiện các chức năng quan trọng qua một loạt các bước. Điều này thường được thực hiện khi cần nắm bắt nhiều đầu vào hoặc tùy chọn khác nhau hoặc khi người dùng cần xem xét và xác nhận thông tin chi tiết trước khi thực hiện hành động. 

- Đôi khi, một trang web sẽ thực hiện các biện pháp kiểm soát truy cập nghiêm ngặt đối với một số bước này, nhưng lại bỏ qua các bước khác. 

- Lab:
	+ Lab: Multi-step process with no access control on one step.[exploit](lab12.py)

### Referer-based access control.

- Một số trang web kiểm soát quyền truy cập dựa trên header *Referer* được gửi trong yêu cầu HTTP. *Referer* thường được các trình duyệt thêm vào các yêu cầu để cho biết trang mà từ đó một yêu cầu đã được bắt đầu.

- *Referer* có thể bị kiểm soát hoàn toàn bởi kẻ tấn công, chúng có thể giả mạo các yêu cầu trực tiếp đến các trang con nhạy cảm, cung cấp *Referer*bắt buộc và do đó có được quyền truy cập trái phép.

- Lab:
	+ Lab: Referer-based access control.[exploit](lab13.py)

### Location-based access control.

- Một số trang web thực thi các kiểm soát truy cập đối với tài nguyên dựa trên vị trí địa lý của người dùng. 

## How to prevent access control vulnerabilities.

- Các lỗ hổng kiểm soát truy cập thường có thể được ngăn chặn bằng cách :

	+ Không bao giờ chỉ dựa vào obfuscation để kiểm soát truy cập.
	+ Trừ khi một tài nguyên được thiết kế để có thể truy cập công khai, hãy từ chối quyền truy cập theo mặc định.
	+ Bất cứ khi nào có thể, hãy sử dụng một cơ chế toàn ứng dụng để thực thi các biện pháp kiểm soát truy cập.
	+ Ở cấp độ mã, bắt buộc các nhà phát triển phải khai báo quyền truy cập được phép cho mỗi tài nguyên và từ chối quyền truy cập theo mặc định.
	+ Kiểm tra kỹ lưỡng và kiểm tra các biện pháp kiểm soát truy cập để đảm bảo chúng đang hoạt động như thiết kế.