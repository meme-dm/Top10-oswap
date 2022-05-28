# HTTP request smuggling.

## What is HTTP request smuggling?

- HTTP request smuggling là một kỹ thuật can thiệp vào cách một trang web xử lý các chuỗi yêu cầu HTTP, nhận được từ một hoặc nhiều người dùng. 
- Các lỗ hổng request smuggling thường rất nghiêm trọng, nó cho phép kẻ tấn công vượt qua các kiểm soát bảo mật, truy cập trái phép vào dữ liệu nhạy cảm và xâm phạm các người dùng ứng dụng khác.

## What happens in an HTTP request smuggling attack?

- Các ứng dụng web thường sử dụng các chuỗi máy chủ HTTP giữa người dùng và hệ thống back-end. Người dùng gửi yêu cầu đến máy chủ front-end và máy chủ này chuyển tiếp yêu cầu đến một hoặc nhiều máy chủ back-end.

- Khi máy chủ front-end chuyển tiếp các yêu cầu HTTP đến một máy chủ back-end, nó thường gửi một số yêu cầu qua cùng một kết nối mạng back-end. Các yêu cầu HTTP được gửi lần lượt và máy chủ phân tích cú pháp các tiêu đề HTTP để xác định nơi một yêu cầu kết thúc và yêu cầu tiếp theo bắt đầu.

- Trong tình huống này, điều quan trọng là hệ thống front-end và back-end phải nhất quán ranh giới giữa các yêu cầu. Nếu không, kẻ tấn công có thể gửi một yêu cầu không rõ ràng được hệ thống front-end và back-end hiểu sai. Ví dụ, kẻ tấn công khiến một phần của yêu cầu front-end của họ được back-end hiểu là phần bắt đầu của yêu cầu tiếp theo.

## How do HTTP request smuggling vulnerabilities arise?

- Hầu hết các lỗ hổng HTTP request smuggling phát sinh do đặc tả HTTP cung cấp hai cách khác nhau để chỉ định nơi yêu cầu kết thúc: *Content-Length* và *Transfer-Encoding*.

- *Content-Length* : chỉ định độ dài của nội dung request bằng byte.

```
POST /search HTTP/1.1
Host: normal-website.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 11

q=smuggling
```
- *Transfer-Encoding* : chỉ định rằng nội dung request sử dụng *chunked*. Điều này có nghĩa là nội dung thư chứa một hoặc nhiều phần dữ liệu. Mỗi chunk bao gồm kích thước tính bằng byte (hệ thập lục phân), theo sau là một dòng mới, tiếp theo là nội dung chunk, và kết thúc bằng một đoạn có kích thước bằng không.

```
POST /search HTTP/1.1
Host: normal-website.com
Content-Type: application/x-www-form-urlencoded
Transfer-Encoding: chunked

b
q=smuggling
0
```

- Vì đặc tả HTTP cung cấp hai phương thức khác nhau để chỉ định độ dài của thông điệp HTTP, nên một thông báo có thể sử dụng cả hai phương thức cùng một lúc, sao cho chúng xung đột với nhau. Đặc tả HTTP cố gắng ngăn chặn sự cố này bằng cách nêu rõ rằng nếu cả *Content-Length* và *Transfer-Encoding* đều có mặt, thì *Content-Length* sẽ được bỏ qua. Trong tình huống này, các vấn đề có thể phát sinh vì hai lý do:

	+ Một số máy chủ không hỗ trợ *Transfer-Encoding* trong các yêu cầu.
	+ Một số máy chủ hỗ trợ *Transfer-Encoding* có thể không xử lý được nếu tiêu đề bị xáo trộn theo một cách nào đó.

- Nếu máy chủ front-end và back-end hoạt động khác nhau liên quan đến *Transfer-Encoding*, thì chúng có thể không thống nhất về ranh giới giữa các yêu cầu liên tiếp, dẫn đến yêu cầu tồn tại lỗ hổng bảo mật.

## How to perform an HTTP request smuggling attack.

- Các cuộc tấn công request smuggling liên quan đến việc đặt cả *Content-Length* và *Transfer-Encoding* vào một yêu cầu HTTP duy nhất và thao tác các yêu cầu này để các máy chủ front-end và back-end xử lý theo cách khác nhau.

### CL.TE vulnerabilities.

- Hệ thống front-end sử dụng *Content-Length* và back-end sử dụng *Transfer-Encoding*.
```
POST / HTTP/1.1
Host: vulnerable-website.com
Content-Length: 13
Transfer-Encoding: chunked

0

SMUGGLED
```
- Máy chủ front-end xử lý *Content-Length* và xác định nội dung yêu cầu dài 13 byte, tính đến cuối *SMUGGLED*. Yêu cầu này được chuyển tiếp đến máy chủ back-end.

- Máy chủ back-end xử lý *Transfer-Encoding*, nó xử lý đoạn đầu tiên, cho biết là có độ dài bằng 0, và như vậy được coi là kết thúc yêu cầu. Các byte sau SMUGGLED, không được xử lý và máy chủ back-end sẽ coi chúng là phần bắt đầu của yêu cầu tiếp theo trong chuỗi.

- Lab: 
	+ Lab: HTTP request smuggling, basic CL.TE vulnerability.[exploit](exploit/lab1.txt)

### TE.CL vulnerabilities.

- Máy chủ front-end sử dụng *Transfer-Encoding* và máy chủ *back-end* sử dụng *Content-Length*.

```
POST / HTTP/1.1
Host: vulnerable-website.com
Content-Length: 3
Transfer-Encoding: chunked

8
SMUGGLED
0
```

- Máy chủ front-end xử lý *Transfer-Encoding*,nó xử lý đoạn đầu tiên, được cho là dài 8 byte, tính đến đầu dòng sau SMUGGLED. Nó xử lý đoạn thứ hai, được cho biết là có độ dài bằng 0, và như vậy được coi là kết thúc yêu cầu. Yêu cầu này được chuyển tiếp đến máy chủ back-end.

- Máy chủ back-end xử lý *Content-Length* và xác định rằng nội dung yêu cầu dài 3 byte, tính đến đầu dòng sau 8. Các byte sau, bắt đầu bằng SMUGGLED, không được xử lý và máy chủ back-end sẽ coi chúng là phần bắt đầu của yêu cầu tiếp theo trong chuỗi.

- Lab:
	+ Lab: HTTP request smuggling, basic TE.CL vulnerability.[exploit](exploit/lab2.txt)

### TE.TE behavior: obfuscating the TE header.

- Máy chủ front-end và back-end đều hỗ trợ *Transfer-Encoding*, nhưng một trong các máy chủ có thể không xử lý được nó bằng cách làm xáo trộn tiêu đề.

```
Transfer-Encoding: xchunked

Transfer-Encoding : chunked

Transfer-Encoding: chunked
Transfer-Encoding: x

Transfer-Encoding:[tab]chunked

[space]Transfer-Encoding: chunked

X: X[\n]Transfer-Encoding: chunked

Transfer-Encoding
: chunked
``` 

- Mỗi kỹ thuật này liên quan đến một sự khác biệt nhỏ so với đặc tả HTTP. Để phát hiện ra lỗ hổng TE.TE, cần phải tìm một số biến thể của *Transfer-Encoding* sao cho chỉ một trong các máy chủ front-end hoặc back-end xử lý nó, trong khi máy chủ khác bỏ qua nó.

- Tùy thuộc vào việc nó là máy chủ front-end hay back-end có thể không xử lý được *Transfer-Encoding*, phần còn lại của cuộc tấn công sẽ có dạng tương tự như đối với các lỗ hổng CL.TE hoặc TE.CL.

- Lab:
	+ Lab: HTTP request smuggling, obfuscating the TE header.[exploit](exploit/lab3.txt)

## [How to identify HTTP request smuggling vulnerabilities.](find.md)

## [How to exploit HTTP request smuggling vulnerabilities.](exploit.md)

## Advanced HTTP request smuggling.

## How to prevent HTTP request smuggling vulnerabilities.

