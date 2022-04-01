# DOM-based XSS.

## What is DOM-based cross-site scripting?

- Các lỗ hổng DOM-base XSS thường phát sinh khi JavaScript lấy dữ liệu từ một nguồn có thể kiểm soát được của kẻ tấn công, chẳng hạn như URL và chuyển nó đến một bộ lưu hỗ trợ thực thi mã động, chẳng hạn như eval() hoặc innerHTML. Điều này cho phép những kẻ tấn công thực thi JavaScript độc hại, thường cho phép chúng chiếm đoạt tài khoản của người dùng khác.


## How to test for DOM-based cross-site scripting.

### Testing HTML sinks.

### Testing JavaScript execution sinks.

### Testing for DOM XSS using DOM Invader.

## Exploiting DOM XSS with different sources and sinks.

- Một trang web dễ bị tấn công DOM-base XSS nếu có một đường dẫn thực thi mà qua đó dữ liệu có thể truyền từ source đến sinks . Trong thực tế, các source và sinks khác nhau có các thuộc tính và hành vi khác nhau có thể ảnh hưởng đến khả năng khai thác và xác định những kỹ thuật nào là cần thiết. 

- VD : document.write('... <script>alert(document.domain)</script> ...')

- innerHTML() sink không chấp nhận các phần tử script trên bất kỳ trình duyệt hiện đại nào, cũng như svg onload() sẽ không kích hoạt. Điều này có nghĩa là bạn sẽ cần sử dụng các phần tử thay thế như img hoặc iframe. Các trình xử lý sự kiện như onload() và onerror() có thể được sử dụng cùng với các phần tử này

- Lab : 
	+ Lab: DOM XSS in document.write sink using source location.search [exploit](exploit/lab9.txt)
	+ Lab: DOM XSS in document.write sink using source location.search inside a select element.[exploit](exploit/lab10.txt)
	+ Lab: DOM XSS in innerHTML sink using source location.search [expolit](exploit/lab11.txt) 

### Sources and sinks in third-party dependencies.

- Các ứng dụng web hiện đại thường được xây dựng bằng cách sử dụng một số thư viện và khuôn khổ của bên thứ ba, thường cung cấp các chức năng và khả năng bổ sung cho các nhà phát triển. Điều quan trọng cần nhớ là một số trong số này cũng là nguồn tiềm năng cho DOM-base XSS.

### DOM XSS in jQuery.

- Nếu một thư viện như jQuery đang được sử dụng, hãy để ý sinks có thể thay đổi các phần tử DOM trên trang. Ví dụ, attr()hàm của jQuery có thể thay đổi các thuộc tính của các phần tử DOM. Nếu dữ liệu được đọc từ một nguồn do người dùng kiểm soát như URL, sau đó được chuyển đến attr() , thì có thể thao túng giá trị được gửi để gây ra XSS.

- VD: 
	```
	$(function() {
		$('#backLink').attr("href",(new URLSearchParams(window.location.search)).get('returnUrl'));
	});
	```
- Bạn có thể khai thác điều này bằng cách sửa đổi URL để location.search chứa URL JS độc hại. Sau khi JS của trang áp dụng URL độc hại này vào liên kết quay lại href, việc nhấp vào liên kết quay lại sẽ thực thi nó:                       *?returnUrl=javascript:alert(document.domain)*

- Lab :
	+ Lab: DOM XSS in jQuery anchor *href* attribute sink using *location.search* source. [expoit](exploit/lap12.txt)

- Một điểm khác tiềm năng cần chú ý là $()chức năng bộ chọn của jQuery, có thể được sử dụng để đưa các đối tượng độc hại vào DOM.
```
$(window).on('hashchange', function() {
	var element = $(location.hash);
	element[0].scrollIntoView();
}); 
```
- Để thực sự khai thác lỗ hổng cổ điển này, bạn sẽ cần tìm cách kích hoạt một hashchangesự kiện mà không cần sự tương tác của người dùng. Một trong những cách đơn giản nhất để làm điều này là cung cấp khai thác của bạn thông qua iframe:
```
<iframe src="https://vulnerable-website.com#" onload="this.src+='<img src=1 onerror=alert(1)>'">
```
- Lab: DOM XSS in jQuery selector sink using a hashchange event.[exploit](lab13.txt) 

### DOM XSS in AngularJS.

- Nếu một framework như AngularJS được sử dụng, có thể thực thi JavaScript mà không có <> và event. Khi một trang web sử dụng method *ng-app* trên một phần tử HTML, nó sẽ được xử lý bởi AngularJS. Trong trường hợp này, AngularJS sẽ thực thi JavaScript bên trong *{{}}* có thể xuất hiện trực tiếp trong HTML hoặc các thuộc tính bên trong.

- Lab:
	+ Lab: DOM XSS in AngularJS expression with angle brackets and double quotes HTML-encoded.[exploit](exploit/lab14.txt)


## DOM XSS combined with reflected and stored data.

- Trong lỗ hổng reflected+DOM , máy chủ xử lý dữ liệu từ yêu cầu và gửi dữ liệu vào phản hồi. Dữ liệu được phản ánh có thể được đặt vào một chuỗi JavaScript theo nghĩa đen hoặc một mục dữ liệu trong DOM, chẳng hạn như trường biểu mẫu. Sau đó, một tập lệnh trên trang sẽ xử lý dữ liệu được phản ánh theo cách không an toàn, cuối cùng ghi nó vào một sink nguy hiểm.

- Lab:
	+ Lab: Reflected DOM XSS.[exploit](exploit/lab15.txt)

- Trong lỗ hổng stored + DOM, máy chủ nhận dữ liệu từ một yêu cầu, lưu trữ và sau đó đưa dữ liệu vào một phản hồi sau đó. Một tập lệnh trong phản hồi chứa phần sink, sau đó xử lý dữ liệu theo cách không an toàn.

- Lab:
	Lab: Stored DOM XSS. [exploit](exploit/lab16.txt)

## Which sinks can lead to DOM-XSS vulnerabilities?

```
document.write() : Phương thức write() sẽ in một đoạn mã HTML, Javascript hoặc một đoạn văn bản ra màn hình cho người sử dụng. 
document.writeln() : Phương thức writeln() sẽ in một đoạn mã HTML, Javascript hoặc một đoạn văn bản ra màn hình cho người sử dụng. 
document.domain : Thuộc tính domain() trả về tên miền của máy chủ .Thuộc tính domain trả về null nếu tài liệu được tạo trong bộ nhớ.
element.innerHTML: Thuộc tính innerHTML() đặt hoặc trả về nội dung HTML của một phần tử.
element.outerHTML : Thuộc tính outerHTML() đặt hoặc trả về phần tử HTML, bao gồm các thuộc tính, thẻ bắt đầu và thẻ kết thúc.
element.insertAdjacentHTML : Phương thức insertAdjacentHTML() này sẽ chèn một văn bản dưới dạng HTML, vào một vị trí được chỉ định.
element.onevent
```

```
add()
after()
append()
animate()
insertAfter()
insertBefore()
before()
html()
prepend()
replaceAll()
replaceWith()
wrap()
wrapInner()
wrapAll()
has()
constructor()
init()
index()
jQuery.parseHTML()
$.parseHTML()
```

## How to prevent DOM-XSS vulnerabilities.