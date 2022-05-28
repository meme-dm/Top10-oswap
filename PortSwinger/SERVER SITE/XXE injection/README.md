# XML external entity (XXE) injection.

## What is XML external entity injection?

- XML external entity injection (XXE) là một lỗ hổng bảo mật web cho phép kẻ tấn công can thiệp vào quá trình xử lý dữ liệu XML của ứng dụng. Nó thường cho phép kẻ tấn công xem các tệp trên hệ thống tệp của máy chủ ứng dụng và tương tác với bất kỳ hệ thống bên ngoài nào mà chính ứng dụng có thể truy cập.

- Trong một số tình huống, kẻ tấn công có thể leo thang một cuộc tấn công XXE để xâm phạm máy chủ bên dưới hoặc cơ sở hạ tầng phụ trợ khác, bằng cách tận dụng lỗ hổng XXE để thực hiện các cuộc tấn công giả mạo yêu cầu phía máy chủ (SSRF).

## How do XXE vulnerabilities arise?

- Một số ứng dụng sử dụng định dạng XML để truyền dữ liệu giữa trình duyệt và máy chủ. Các ứng dụng thực hiện điều này hầu như luôn sử dụng thư viện chuẩn hoặc API để xử lý dữ liệu XML trên máy chủ. Các lỗ hổng XXE phát sinh do đặc tả XML chứa nhiều tính năng nguy hiểm tiềm ẩn khác nhau và trình phân tích cú pháp tiêu chuẩn hỗ trợ các tính năng này ngay cả khi chúng không được ứng dụng sử dụng.

- XML external entity là một loại thực thể XML tùy chỉnh có các giá trị đã xác định được tải từ bên ngoài DTD mà chúng được khai báo. Các thực thể bên ngoài đặc biệt thú vị từ góc độ bảo mật vì chúng cho phép một thực thể được xác định dựa trên nội dung của đường dẫn tệp hoặc URL.

## What are the types of XXE attacks?

- Có nhiều kiểu tấn công XXE:

	+ Exploiting XXE to retrieve files , trong đó một thực thể bên ngoài được xác định có chứa nội dung của tệp và được trả lại trong phản hồi của ứng dụng.
	+ Exploiting XXE to perform SSRF attacks , trong đó một thực thể bên ngoài được xác định dựa trên URL đến hệ thống back-end.
	+ Exploiting blind XXE exfiltrate data out-of-band , nơi dữ liệu nhạy cảm được truyền từ máy chủ ứng dụng đến hệ thống mà kẻ tấn công kiểm soát.
	+ Exploiting blind XXE to retrieve data via error messages , nơi kẻ tấn công có thể kích hoạt thông báo lỗi phân tích cú pháp chứa dữ liệu nhạy cảm.

## Exploiting XXE to retrieve files.

- Để thực hiện một cuộc tấn công XXE truy xuất một tệp tùy ý từ hệ thống tệp của máy chủ, bạn cần sửa đổi XML đã gửi theo hai cách:

	+ Chỉnh sửa một phần tử *DOCTYPE* xác định một thực thể bên ngoài chứa đường dẫn đến tệp.
	+ Chỉnh sửa giá trị dữ liệu trong XML được trả về trong phản hồi của ứng dụng, để sử dụng thực thể bên ngoài đã xác định.

Ví dụ: Giả sử một ứng dụng mua sắm kiểm tra tình trạng còn hàng của sản phẩm bằng cách gửi XML sau đến máy chủ:
```
<?xml version="1.0" encoding="UTF-8"?>
<stockCheck><productId>381</productId></stockCheck>
```
Ứng dụng không thực hiện biện pháp bảo vệ cụ thể nào chống lại các cuộc tấn công XXE, vì vậy bạn có thể khai thác lỗ hổng XXE để truy xuất tệp /etc/passwd:
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE foo [ <!ENTITY xxe SYSTEM "file:///etc/passwd"> ]>
<stockCheck><productId>&xxe;</productId></stockCheck>
```

- Lab: 
	+ Lab: Exploiting XXE using external entities to retrieve files.[exploit](exploit/lab1.py)

## Exploiting XXE to perform SSRF attacks.

- Ngoài việc truy xuất dữ liệu nhạy cảm, tác động khác của XXE là chúng có thể được sử dụng để thực hiện giả mạo yêu cầu phía máy chủ (SSRF). Đây là một lỗ hổng nghiêm trọng tiềm ẩn trong đó ứng dụng phía máy chủ có thể được tạo ra để thực hiện các yêu cầu HTTP đến bất kỳ URL nào mà máy chủ có thể truy cập.

- Để khai thác lỗ hổng XXE để thực hiện tấn công SSRF , bạn cần xác định một thực thể XML bên ngoài bằng cách sử dụng URL mà bạn muốn nhắm mục tiêu và sử dụng thực thể đã xác định trong một giá trị dữ liệu. Nếu bạn có thể sử dụng thực thể đã xác định trong một giá trị dữ liệu được trả về trong phản hồi của ứng dụng, thì bạn sẽ có thể xem phản hồi từ URL trong phản hồi của ứng dụng và do đó có được tương tác hai chiều với hệ thống back-end. Nếu không, bạn sẽ chỉ có thể thực hiện các cuộc tấn công SSRF blind SSRF.

> <!DOCTYPE foo [ <!ENTITY xxe SYSTEM "http://internal.vulnerable-website.com/"> ]>

- Lab:
	+ Lab: Exploiting XXE to perform SSRF attacks.[exploit](exploit/lab2.py)

## Blind XXE vulnerabilities.

-  Khi ứng dụng không trả về giá trị của bất kỳ thực thể bên ngoài nào đã xác định trong các phản hồi của nó và do đó không thể truy xuất trực tiếp các tệp phía máy chủ.

- Các lỗ hổng Blind XXE vẫn có thể được phát hiện và khai thác. Đôi khi, bạn có thể sử dụng các kỹ thuật ngoài băng tần để tìm ra các lỗ hổng và khai thác chúng để lấy dữ liệu. Và đôi khi bạn có thể kích hoạt lỗi phân tích cú pháp XML dẫn đến tiết lộ dữ liệu nhạy cảm trong các thông báo lỗi.

- [Blind XXE exploit.](Blind_XXE.md) 

## Finding hidden attack surface for XXE injection.

### XInclude attacks.

- Một số ứng dụng nhận dữ liệu do máy khách gửi, nhúng dữ liệu đó ở phía máy chủ vào tài liệu XML, sau đó phân tích cú pháp tài liệu. 

- Trong trường hợp này, bạn không thể kiểm soát toàn bộ tài liệu XML và do đó không thể xác định hoặc sửa đổi một *DOCTYPE*. Tuy nhiên, bạn có thể sử dụng *XInclude* để thay thế. 

- *XInclude* là một phần của đặc tả XML cho phép một tài liệu XML được xây dựng từ các tài liệu con. Bạn có thể thực hiện một cuộc tấn công *XInclude* tại bất kỳ giá trị dữ liệu nào trong tài liệu XML, do đó, cuộc tấn công có thể được thực hiện trong các tình huống mà bạn chỉ kiểm soát một mục dữ liệu duy nhất được đặt vào tài liệu XML phía máy chủ.

```
<foo xmlns:xi="http://www.w3.org/2001/XInclude">
<xi:include parse="text" href="file:///etc/passwd"/></foo>
```

- Lab:
	+ Lab: Exploiting XInclude to retrieve files.[exploit](exploit/lab8.py)

### XXE attacks via file upload.

- Một số ứng dụng cho phép người dùng tải lên các tệp sau đó được xử lý phía máy chủ. Một số định dạng tệp phổ biến sử dụng XML hoặc chứa các thành phần con XML. Ví dụ về các định dạng dựa trên XML là các định dạng tài liệu văn phòng như DOCX và các định dạng hình ảnh như SVG.

Ví dụ: một ứng dụng có thể cho phép người dùng tải lên hình ảnh và xử lý hoặc xác thực những hình ảnh này trên máy chủ sau khi chúng được tải lên. Ngay cả khi ứng dụng mong đợi nhận được định dạng như PNG hoặc JPEG, thư viện xử lý hình ảnh đang được sử dụng có thể hỗ trợ hình ảnh SVG. Vì định dạng SVG sử dụng XML, kẻ tấn công có thể gửi một hình ảnh SVG độc hại và do đó có thể tiếp cận bề mặt tấn công ẩn cho các lỗ hổng XXE.

- Lab: 
	+ Lab: Exploiting XXE via image file upload.[exploit](exploit/lab9)

### XXE attacks via modified content type.

- Hầu hết các yêu cầu POST sử dụng kiểu nội dung mặc định được tạo bởi các biểu mẫu HTML, chẳng hạn như application/x-www-form-urlencoded. Một số trang web mong đợi nhận được yêu cầu ở định dạng này nhưng sẽ dung nạp các loại nội dung khác, bao gồm cả XML.

Ví dụ: nếu một yêu cầu thông thường chứa những điều sau:
```
POST /action HTTP/1.0
Content-Type: application/x-www-form-urlencoded
Content-Length: 7

foo=bar
```
```
POST /action HTTP/1.0
Content-Type: text/xml
Content-Length: 52

<?xml version="1.0" encoding="UTF-8"?><foo>bar</foo>
```

## How to find and test for XXE vulnerabilities.

- Kiểm tra khả năng truy xuất tệp bằng cách xác định một thực thể bên ngoài dựa trên một tệp hệ điều hành nổi tiếng và sử dụng thực thể đó trong dữ liệu được trả về trong phản hồi của ứng dụng.

- Kiểm tra các lỗ hổng XXE mù bằng cách xác định một thực thể bên ngoài dựa trên URL của một hệ thống mà bạn kiểm soát và giám sát các tương tác với hệ thống đó.

- Kiểm tra xem có dễ bị đưa vào dữ liệu không phải XML do người dùng cung cấp trong tài liệu XML phía máy chủ hay không bằng cách sử dụng cuộc tấn công *XInclude* để cố gắng truy xuất tệp hệ điều hành nổi tiếng.


## How to prevent XXE vulnerabilities.

- Hầu như tất cả các lỗ hổng XXE đều phát sinh do thư viện phân tích cú pháp XML của ứng dụng hỗ trợ các tính năng XML tiềm ẩn nguy hiểm mà ứng dụng không cần hoặc có ý định sử dụng. Cách dễ nhất và hiệu quả nhất để ngăn chặn các cuộc tấn công XXE là vô hiệu hóa các tính năng đó.

Nói chung, chỉ cần vô hiệu hóa phân giải của các thực thể bên ngoài và vô hiệu hóa hỗ trợ cho *XInclude*. 