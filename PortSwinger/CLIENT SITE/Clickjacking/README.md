# Clickjacking (UI redressing).

## What is clickjacking?

- Clickjacking là một cuộc tấn công dựa trên giao diện, trong đó người dùng bị lừa nhấp vào nội dung có thể hành động trên một trang web ẩn bằng cách nhấp vào một số nội dung khác trong trang web giả mạo.

 - Kỹ thuật này phụ thuộc vào việc kết hợp một trang web vô hình có chứa một nút hoặc liên kết ẩn, chẳng hạn như trong iframe. Iframe được phủ lên trên nội dung trang web nạn nhân.

 - Cuộc tấn công này khác với cuộc tấn công CSRF ở chỗ người dùng được yêu cầu thực hiện một hành động chẳng hạn như nhấp vào nút trong khi cuộc tấn công CSRF phụ thuộc vào việc giả mạo toàn bộ yêu cầu mà người dùng không biết hoặc không có thông tin đầu vào.

## How to construct a basic clickjacking attack.

- Các cuộc tấn công clickjacking sử dụng CSS để tạo và thao tác các lớp. Kẻ tấn công kết hợp trang web mục tiêu như một lớp iframe phủ trên trang web mồi nhử.

```
<head>
	<style>
		#target_website {
			position:relative;
			width:128px;
			height:128px;
			opacity:0.00001;
			z-index:2;
			}
		#decoy_website {
			position:absolute;
			width:300px;
			height:400px;
			z-index:1;
			}
	</style>
</head>
...
<body>
	<div id="decoy_website">
	...decoy web content here...
	</div>
	<iframe id="target_website" src="https://vulnerable-website.com">
	</iframe>
</body>
```

- Lab:
	+ Lab: Basic clickjacking with CSRF token protection.[exploit](exploit/lab1.txt)

## Clickjacking with prefilled form input.

- Một số trang web gửi biểu mẫu bằng cách sử dụng các tham số GET . Khi các giá trị GET tạo thành một phần của URL thì URL mục tiêu có thể được sửa đổi để kết hợp các giá trị do kẻ tấn công lựa chọn.

- Lab:
	+ Lab: Clickjacking with form input data prefilled from a URL parameter.[exploit](exploit/lab2.txt)

## Frame busting scripts.

- Một biện pháp bảo vệ phía máy khách phổ biến được thực hiện thông qua trình duyệt web là sử dụng các tập lệnh chặn khung hoặc ngắt khung.
	+ Kiểm tra và thực thi cửa sổ ứng dụng hiện tại là cửa sổ chính hoặc cửa sổ trên cùng.
	+ Hiển thị tất cả các khung hình.
	+ Ngăn nhấp chuột vào các khung vô hình.
	+ Chặn và gắn cờ các cuộc tấn công clickjacking tiềm ẩn cho người dùng.

- Một giải pháp hiệu quả cho kẻ tấn công chống lại kẻ phá khung là sử dụng *sandbox* thuộc tính iframe HTML5. Khi điều này được đặt với *allow-forms* hoặc *allow-scripts* dẫn đến giá trị *allow-top-navigation* bị bỏ qua thì tập lệnh khung có thể bị vô hiệu hóa vì iframe không thể kiểm tra xem nó có phải là cửa sổ trên cùng hay không.
**<iframe id="victim_website" src="https://victim-website.com" sandbox="allow-forms"></iframe>**

- Lab:
	+ Lab: Lab: Clickjacking with a frame buster script.[exploit](exploit/lab3.txt)


## Combining clickjacking with a DOM XSS attack.

- Cuộc tấn công kết hợp với DOM-Base XSS giả định rằng kẻ tấn công đã xác định được cách khai thác XSS. Sau đó, khai thác XSS được kết hợp với URL đích của khung nội tuyến để người dùng nhấp vào nút hoặc liên kết và do đó thực hiện cuộc tấn công DOM XSS.

- Lab:
	+ Lab: Exploiting clickjacking vulnerability to trigger DOM-based XSS.[exploit](exploit/lab4.txt)

## Multistep clickjacking.

- Việc kẻ tấn công thao túng các đầu vào cho một trang web mục tiêu có thể yêu cầu nhiều hành động.

- Lab:
	+ Lab: Multistep clickjacking.[exploit](exploit/lab5.txt)

## How to prevent clickjacking attacks.

- Clickjacking là một hành vi từ phía trình duyệt và sự thành công của nó hay cách khác phụ thuộc vào chức năng của trình duyệt và sự phù hợp với các tiêu chuẩn web hiện hành.

### X-Frame-Options.

	-  Kiểm soát việc sử dụng iframe hoặc các đối tượng để việc đưa trang web vào khung có thể bị cấm.
	> X-Frame-Options: deny

	- Việc đóng khung có thể bị hạn chế ở cùng nguồn gốc với trang web.
	> X-Frame-Options: sameorigin

### Content Security Policy (CSP).

	- Chỉ cho phép các iframe từ cùng 1 miền :
	>Content-Security-Policy: frame-ancestors 'self';

	- Việc đóng khung có thể bị hạn chế.
	> Content-Security-Policy: frame-ancestors normal-website.com;