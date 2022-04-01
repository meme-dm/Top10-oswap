# Cross-site request forgery (CSRF).

## What is CSRF?

- Cross-site request forgery (aka CSRF) là một lỗ hổng bảo mật web cho phép kẻ tấn công khiến người dùng thực hiện các hành động mà họ không có ý định thực hiện. 
- Nó cho phép kẻ tấn công phá vỡ một phần same origin policy, được thiết kế để ngăn các trang web khác nhau can thiệp vào nhau.

## What is the impact of a CSRF attack?

- Một cuộc tấn công CSRF thành công, kẻ tấn công khiến người dùng nạn nhân thực hiện một hành động không chủ ý. 

- VD :  + Thay đổi địa chỉ email.
		+ Thay đổi mật khẩu của họ hoặc thực hiện chuyển tiền. 

- Tùy thuộc vào bản chất của hành động, kẻ tấn công có thể có toàn quyền kiểm soát tài khoản của người dùng. Đặc biệt đối với những người dùng cấp cao kẻ tấn công có thể kiểm soát hoàn toàn dữ liệu .

## How does CSRF work?

- 3 điều kiện để csrf xảy ra  :
	+ Tồn tại một hành động đặc quyền mà kẻ tấn công có thể lợi dụng (sửa đổi , thêm , xóa,..).
	+ Xác thực các hành động dựa trên cookie.
	+ Không chứa các mã ngẫu nhiên để xác định hành động .

- VD :
```
POST /email/change HTTP/1.1
Host: vulnerable-website.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 30
Cookie: session=yvthwsztyeQkAPzeQ5gHgTvlyxHfsAfE

email=wiener@normal-user.com
```
=> Hành động thay đổi email, xác thực chỉ dựa trêm cookie, các tham số dễ đoán.

=> Payload:
```
<html>
    <body>
        <form action="https://vulnerable-website.com/email/change" method="POST">
            <input type="hidden" name="email" value="pwned@evil-user.net" />
        </form>
        <script>
            document.forms[0].submit();
        </script>
    </body>
</html>
```

=> Khi người dùng truy cập web của kẻ tấn công > 1 yêu cầu HTTP đc kích hoạt > trình duyệt nạn nhân sẽ tự đưa cookie (trong th nạn nhân đã đăng nhập ) vào yêu cầu của kẻ tấn công và xử lí bình thường.

## How to construct a CSRF attack.

- Tạo CSRF POC with Burp Suite Pro.

- Lab:
	+ Lab: CSRF vulnerability with no defenses.[exploit](exploit/lab1.txt)

## How to deliver a CSRF exploit.

-  Có thể có nhiều cách thực hiện CSRF, thông thường attacker sẽ đặt mã độc trên một web khác và lôi kéo nạn nhân truy cập vào . Hay để lại mã độc ngay trên bình luận của web site , một số hành động dùng GET thậm chí chỉ cần cung cấp một url để thực hiện tấn công.

- VD : *<img src="https://vulnerable-website.com/email/change?email=pwned@evil-user.net">*

- [XSS and CSRF](xss_vs_csrf.md).

## Preventing CSRF attacks.

1. Sử dụng CSRF tokens.

	- CSRF Tokens là một giá trị duy nhất, bí mật, không thể đoán trước, được tạo bởi ứng dụng phía máy chủ và được truyền đến máy khách. Khi yêu cầu sau đó được thực hiện, ứng dụng phía máy chủ xác thực rằng yêu cầu có bao gồm mã thông báo mong đợi và từ chối yêu cầu nếu mã thông báo bị thiếu hoặc không hợp lệ.

	- VD : *<input type="hidden" name="csrf-token" value="CIwNZNlR4XbisJF39I8yWnWX9wX4WFoz" />*

	- Các yêu cầu : 
		+ Không thể đoán trước với entropy cao.
		+ Bị ràng buộc với phiên của người dùng.
		+ Được xác nhận nghiêm ngặt trong mọi trường hợp trước khi hành động liên quan được thực hiện.
2. Samesite.

	- SameSite có thể được sử dụng để kiểm soát xem cookie có được gửi trong các yêu cầu trên nhiều trang web hay không và như thế nào. Bằng cách đặt thuộc tính trên cookie phiên, ứng dụng có thể ngăn hành vi mặc định của trình duyệt là tự động thêm cookie vào các yêu cầu bất kể chúng bắt nguồn từ đâu.

	- SameSite = Strict : ko thêm cookie vào bất kì request nào đến từ web khác .
	- SameSite = Lax : thêm cookie vào request với 2 đk (method GET, yêu cầu từ điều hướng cao cấp nhất của người dùng).

## Common CSRF vulnerabilities.

Hầu hết các lỗ hổng CSRF phát sinh do sai lầm trong quá trình xác thực mã thông báo CSRF.

### Validation of CSRF token depends on request method.

- Một số ứng dụng xác thực đúng mã thông báo khi yêu cầu sử dụng phương thức POST nhưng bỏ qua xác thực khi phương thức GET được sử dụng.

- Trong tình huống này, kẻ tấn công có thể chuyển sang phương thức GET để bỏ qua xác thực và thực hiện một cuộc tấn công CSRF.

- Lab :
	+ Lab: CSRF where token validation depends on request method.[exploit](exploit/lab2.txt)


### Validation of CSRF token depends on token being present.

- Một số ứng dụng xác thực chính xác mã thông báo khi nó hiện diện nhưng bỏ qua xác thực nếu mã thông báo bị bỏ qua. Vì vậy kẻ tấn công chỉ cần xóa tokens để thực hiện tấn công CSRF.

- Lab:
	+ Lab: CSRF where token validation depends on token being present.[exploit](exploit/lab3.txt)

### CSRF token is not tied to the user session.

- Một số ứng dụng không xác thực rằng mã thông báo thuộc cùng một phiên với người dùng đang đưa ra yêu cầu. Thay vào đó, ứng dụng duy trì một nhóm mã thông báo toàn cầu mà nó đã phát hành và chấp nhận bất kỳ mã thông báo nào xuất hiện trong nhóm này.

- Trong tình huống này, kẻ tấn công có thể đăng nhập vào ứng dụng bằng tài khoản của chính họ, lấy mã thông báo hợp lệ và sau đó cung cấp mã thông báo đó cho người dùng nạn nhân trong cuộc tấn công CSRF của chúng.

- Lab : 
	+ Lab: CSRF where token is not tied to user session.[exploit](exploit/lab4.txt)

### CSRF token is tied to a non-session cookie.

- Một số ứng dụng liên kết mã thông báo CSRF với một cookie, nhưng không gắn với cùng một cookie được sử dụng để theo dõi phiên. Điều này có thể dễ dàng xảy ra khi một ứng dụng sử dụng hai khuôn khổ khác nhau, một để xử lý phiên và một để bảo vệ CSRF, không được tích hợp với nhau.

- Nếu trang web chứa bất kỳ hành vi nào cho phép kẻ tấn công đặt cookie trong trình duyệt của nạn nhân, thì một cuộc tấn công có thể xảy ra. Kẻ tấn công có thể đăng nhập vào ứng dụng bằng tài khoản của chính họ, lấy mã thông báo hợp lệ và cookie được liên kết, tận dụng hành vi cài đặt cookie để đặt cookie của chúng vào trình duyệt của nạn nhân và cung cấp mã thông báo của chúng cho nạn nhân trong cuộc tấn công CSRF của chúng.

- Lab:
	+ Lab: CSRF where token is tied to non-session cookie.[exploit](exploit/lab5.txt)

### CSRF token is simply duplicated in a cookie.

- Một số ứng dụng không duy trì bất kỳ bản ghi phía máy chủ nào về các CSRF token, mà thay vào đó sao chép token trong một cookie và một tham số. Khi yêu cầu tiếp theo được xác thực, ứng dụng chỉ cần xác minh rằng token được gửi trong tham số khớp với giá trị trong cookie. Điều này đôi khi được gọi là biện pháp bảo vệ "double submit" chống lại CSRF và được ủng hộ.

- Trong tình huống này, kẻ tấn công có thể thực hiện lại cuộc tấn công CSRF nếu trang web có bất kỳ chức năng cài đặt cookie nào.
- Kẻ tấn công không cần lấy tokens hợp lệ của riêng chúng. Họ chỉ cần tạo ra một mã thông báo, tận dụng hành vi thiết lập cookie để đặt cookie của họ vào trình duyệt của nạn nhân và cung cấp token của họ cho nạn nhân trong cuộc tấn công CSRF của chúng.

- Lab :
	+ Lab: CSRF where token is duplicated in cookie.[exploit](exploit/lab6.txt)

## Referer-based defenses against CSRF.

-  Một số ứng dụng sử dụng tiêu đề HTTP *Referer* để cố gắng bảo vệ chống lại các cuộc tấn công CSRF, bằng cách xác minh rằng yêu cầu bắt nguồn từ miền riêng của ứng dụng

### Validation of Referer depends on header being present.

- Kẻ tấn công có thể khai thác CSRF của họ theo cách khiến trình duyệt của nạn nhân bỏ *Referer* header trong response.

```<meta name="referrer" content="never">```

- Lab:
	+ Lab: CSRF where Referer validation depends on header being present.[exploit](exploit/lab7.txt)

### Validation of Referer can be circumvented.

- Nếu ứng dụng chỉ đơn giản xác nhận rằng *Referer* có chứa tên miền riêng của nó, thì kẻ tấn công có thể đặt giá trị bắt buộc ở nơi khác trong URL.

- VD : *http://attacker-website.com/csrf-attack?vulnerable-website.com*

- Bạn có thể ghi đè hành vi này bằng cách đảm bảo rằng phản hồi chứa phần khai thác của bạn có header *Referrer-Policy: unsafe-url*.

- Lab:
	+ Lab: CSRF with broken Referer validation.[exploit](exploit/lab8.txt)


