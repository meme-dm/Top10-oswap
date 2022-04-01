# Testing for WebSockets security vulnerabilities.

## WebSockets.

- WebSockets là một giao thức truyền thông song công, hai chiều được khởi tạo qua HTTP. Chúng thường được sử dụng trong các ứng dụng web hiện đại để truyền dữ liệu và lưu lượng truy cập không đồng bộ khác.

- Với HTTP, máy khách gửi một yêu cầu và máy chủ trả về một phản hồi. Thông thường, phản hồi xảy ra ngay lập tức và giao dịch hoàn tất. Ngay cả khi kết nối mạng vẫn mở, điều này sẽ được sử dụng cho một giao dịch riêng biệt của một yêu cầu và một phản hồi.
- Kết nối WebSocket được khởi tạo qua HTTP và thường tồn tại lâu dài. Tin nhắn có thể được gửi theo một trong hai hướng bất kỳ lúc nào và không mang tính chất giao dịch. Kết nối thường sẽ ở trạng thái mở và không hoạt động cho đến khi máy khách hoặc máy chủ sẵn sàng gửi tin nhắn.

=> WebSockets đặc biệt hữu ích trong các trường hợp yêu cầu độ trễ thấp hoặc thông báo do máy chủ khởi tạo, chẳng hạn như nguồn cấp dữ liệu tài chính theo thời gian thực.

## Manipulating WebSocket traffic.

### Intercepting and modifying WebSocket messages.

- Bạn có thể sử dụng Burp Proxy để chặn và sửa đổi các thông báo WebSocket, như sau:

	+ Cấu hình trình duyệt để Burp Suite làm máy chủ proxy.
	+ Sử dụng ứng dụng và tìm kiếm các mục xuất hiện trong tab lịch sử WebSockets của Burp Proxy.
	+ Tab intercept của Burp Proxy (turn on).
	+ Khi một message WebSocket được gửi từ browser hoặc server , nó sẽ hiển thị trong tab Intercept.

### Replaying and generating new WebSocket messages.
### Manipulating WebSocket connections.

- Có nhiều tình huống khác nhau mà việc thao tác bắt tay WebSocket có thể cần thiết:

	+ Nó có thể cho phép bạn tiếp cận nhiều bề mặt tấn công hơn.
	+ Một số cuộc tấn công có thể khiến kết nối của bạn bị ngắt, vì vậy bạn cần thiết lập một kết nối mới.
	+ Mã thông báo hoặc dữ liệu khác trong yêu cầu bắt tay ban đầu có thể đã cũ và cần cập nhật.

- Bạn có thể thao tác bắt tay WebSocket bằng Burp Repeater:

	+ Trong Burp Repeater, nhấn vào biểu tượng bút chì bên cạnh URL WebSocket >  Sao chép một WebSocket đã kết nối hoặc kết nối lại với một WebSocket đã ngắt kết nối.
	+ Khi nhấn vào "connect", Burp sẽ cố gắng thực hiện bắt tay đã định cấu hình và hiển thị kết quả. Nếu kết nối WebSocket mới được thiết lập thành công, thì bạn có thể sử dụng kết nối này để gửi tin nhắn mới trong Burp Repeater.

## WebSockets security vulnerabilities.


- Về nguyên tắc, trên thực tế, bất kỳ lỗ hổng bảo mật web nào cũng có thể xảy ra trên WebSockets:

	+ Đầu vào do người dùng cung cấp được truyền tới máy chủ có thể được xử lý theo những cách không an toàn, dẫn đến các lỗ hổng như SQLi  ,chèn thực thể bên ngoài XML.
	+ Một số lỗ hổng bảo mật được tiếp cận thông qua WebSockets có thể chỉ có thể được phát hiện bằng các kỹ thuật ngoài băng tần (OAST) .
	+ Nếu dữ liệu do kẻ tấn công kiểm soát được truyền qua WebSockets đến những người dùng ứng dụng khác, thì nó có thể dẫn đến XSS hoặc các lỗ hổng phía client.

### Manipulating WebSocket messages to exploit vulnerabilities.

- Phần lớn các lỗ hổng dựa trên đầu vào ảnh hưởng đến WebSocket có thể được tìm thấy và khai thác bằng cách giả mạo nội dung của các thông báo WebSocket .

VD:
Request : {"message":"Hello Carlos"}
Response : <td>Hello Carlos</td>
Exploit : {"message":"<img src=1 onerror='alert(1)'>"}

- Lab:
	+ Lab: Manipulating WebSocket messages to exploit vulnerabilities.[exploit](exploit/lab1.txt)

### Manipulating the WebSocket handshake to exploit vulnerabilities.

- Một số lỗ hổng WebSockets chỉ có thể được tìm thấy và khai thác bằng cách thao tác bắt tay WebSocket.

	+ Tin vào các header không an toàn như X-Forwarded-For.
	+ Lỗi trong cơ chế xử lý phiên, vì ngữ cảnh phiên mà thông điệp WebSocket được xử lý thường được xác định bởi ngữ cảnh phiên của thông báo bắt tay.
	+ Tấn công bởi các tiêu đề HTTP tùy chỉnh được ứng dụng sử dụng.

- Lab:
	+ Lab: Manipulating the WebSocket handshake to exploit vulnerabilities.[exploit](exploit/lab2.txt)



### Using cross-site WebSockets to exploit vulnerabilities.


## How to secure a WebSocket connection.