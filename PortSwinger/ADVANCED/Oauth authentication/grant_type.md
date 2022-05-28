# OAuth grant types.

## What is an OAuth grant type?

- **OAuth grant type** xác định trình tự chính xác của các bước liên quan đến quy trình OAuth. Nó cũng ảnh hưởng đến cách ứng dụng khách giao tiếp với dịch vụ OAuth ở mỗi giai đoạn, bao gồm cả cách gửi mã thông báo truy cập.

- Dịch vụ OAuth phải được định cấu hình để hỗ trợ một loại *grant type* cụ thể trước khi ứng dụng khách có thể bắt đầu quy trình tương ứng.

## OAuth scopes.

- Đối với bất kỳ OAuth grant type nào, ứng dụng khách phải chỉ định dữ liệu nào nó muốn truy cập và loại hoạt động nào nó muốn thực hiện. Nó thực hiện điều này bằng cách sử dụng tham số*scope* của yêu cầu ủy quyền mà nó gửi đến dịch vụ OAuth.

- Đối với OAuth cơ bản, phạm vi mà ứng dụng khách có thể yêu cầu quyền truy cập là duy nhất cho mỗi dịch vụ OAuth. Vì tên của phạm vi chỉ là một chuỗi văn bản tùy ý, định dạng có thể khác nhau giữa các nhà cung cấp.

```
scope=contacts
scope=contacts.read
scope=contact-list-r
scope=https://oauth-authorization-server.com/auth/scopes/user/contacts.readonly
```

## Authorization code grant type.

- Ứng dụng khách và dịch vụ OAuth trước tiên sử dụng chuyển hướng để trao đổi một loạt các yêu cầu HTTP dựa trên trình duyệt để bắt đầu luồng. Sau đó, ứng dụng khách trao đổi mã token với dịch vụ OAuth để nhận "access token", mã này họ có thể sử dụng để thực hiện các lệnh gọi API để tìm nạp dữ liệu người dùng có liên quan.

- Tất cả giao tiếp diễn ra từ việc trao đổi mã thông báo trở đi sẽ được gửi từ máy chủ đến máy chủ qua kênh an toàn,do đó người dùng không nhìn thấy được. Kênh bảo mật này được thiết lập khi ứng dụng khách đăng ký lần đầu với dịch vụ OAuth. Tại thời điểm này, một *client_secret* cũng được tạo ra, mà ứng dụng khách phải sử dụng để xác thực chính nó khi gửi các yêu cầu từ máy chủ đến máy chủ.

1. Authorization request.

- Ứng dụng khách gửi yêu cầu đến */authorization* của dịch vụ OAuth để yêu cầu cấp quyền truy cập vào dữ liệu người dùng cụ thể.
```
GET /authorization?client_id=12345&redirect_uri=https://client-app.com/callback&response_type=code&scope=openid%20profile&state=ae13d489bd00e3c24 HTTP/1.1
Host: oauth-authorization-server.com
```
	+  client_id : Tham số bắt buộc chứa mã định danh duy nhất của ứng dụng khách. Giá trị này được tạo khi ứng dụng khách đăng ký với dịch vụ OAuth.
	
	+ redirect_uri : URI mà trình duyệt của người dùng sẽ được chuyển hướng đến khi gửi mã ủy quyền đến ứng dụng khách.

	+ response_type : Xác định loại phản hồi mà ứng dụng khách đang mong đợi và nó muốn bắt đầu luồng nào. Đối với loại cấp mã ủy quyền, giá trị là code.

	+ scope : Được sử dụng để chỉ định tập hợp con dữ liệu của người dùng mà ứng dụng khách muốn truy cập.

	+ state : Lưu trữ một giá trị duy nhất, không thể đoán, được gắn với phiên hiện tại trên ứng dụng khách. Dịch vụ OAuth sẽ trả về giá trị chính xác này trong phản hồi, cùng với mã ủy quyền.

2. User login and consent.

- Khi máy chủ ủy quyền nhận được yêu cầu ban đầu, nó sẽ chuyển hướng người dùng đến trang đăng nhập, nơi họ sẽ được đăng nhập vào tài khoản của họ với nhà cung cấp OAuth.

- Sau đó, chúng sẽ được hiển thị với một danh sách dữ liệu mà ứng dụng khách muốn truy cập. Điều này dựa trên các phạm vi được xác định trong yêu cầu ủy quyền.

- Điều quan trọng cần lưu ý là sau khi người dùng đã phê duyệt phạm vi nhất định cho ứng dụng khách, bước này sẽ được hoàn thành tự động miễn là người dùng vẫn có phiên hợp lệ với dịch vụ OAuth. Nói cách khác, lần đầu tiên người dùng chọn ""Log in with social media", họ sẽ cần đăng nhập theo cách thủ công, nhưng nếu họ truy cập lại ứng dụng khách sau đó, họ thường sẽ có thể đăng nhập lại bằng một một cú nhấp chuột.

3. Authorization code grant.

- Nếu người dùng đồng ý với quyền truy cập được yêu cầu, trình duyệt của họ sẽ được chuyển hướng đến */callback* đã được chỉ định trong *redirect_uri*. Kết quả GET sẽ chứa mã ủy quyền dưới dạng tham số truy vấn. Tùy thuộc vào cấu hình, nó cũng có thể gửi *state* có cùng giá trị như trong yêu cầu ủy quyền. 

```
GET /callback?code=a1b2c3d4e5f6g7h8&state=ae13d489bd00e3c24 HTTP/1.1
Host: client-app.com
```

4. Access token request.

- Sau khi ứng dụng khách nhận được mã ủy quyền, ứng dụng cần đổi mã đó để lấy mã thông báo truy cập. Để thực hiện việc này, nó sẽ gửi một yêu cầu POST đến */token*. Tất cả thông tin liên lạc từ thời điểm này trở đi diễn ra trong một kênh an toàn và do đó, kẻ tấn công thường không thể quan sát hoặc kiểm soát được.

```
POST /token HTTP/1.1
Host: oauth-authorization-server.com
…
client_id=12345&client_secret=SECRET&redirect_uri=https://client-app.com/callback&grant_type=authorization_code&code=a1b2c3d4e5f6g7h8
```
	+ client_secret : Ứng dụng khách phải tự xác thực bằng cách bao gồm khóa bí mật mà nó đã được chỉ định khi đăng ký với dịch vụ OAuth.
	+ grant_type : Được sử dụng để đảm bảo điểm cuối biết loại grant type mà ứng dụng khách muốn sử dụng (authorization_code).

5. Access token grant.

```
{
    "access_token": "z0y9x8w7v6u5",
    "token_type": "Bearer",
    "expires_in": 3600,
    "scope": "openid profile",
    …
}
```

6. API call.

- Bây giờ ứng dụng khách có mã truy cập, cuối cùng nó có thể tìm nạp dữ liệu của người dùng từ máy chủ tài nguyên. Để làm điều này, nó thực hiện gọi tới  */userinfo*. Mã thông báo truy cập được gửi trong *Authorization: Bearer* tiêu đề để chứng minh rằng ứng dụng khách có quyền truy cập vào dữ liệu này.

```
GET /userinfo HTTP/1.1
Host: oauth-resource-server.com
Authorization: Bearer z0y9x8w7v6u5
```

7. Resource grant.

- Máy chủ tài nguyên phải xác minh rằng mã thông báo hợp lệ và nó thuộc về ứng dụng khách hiện tại. Nếu vậy, nó sẽ phản hồi bằng cách gửi tài nguyên được yêu cầu tức là dữ liệu của người dùng dựa trên phạm vi của mã thông báo truy cập.

```
{
    "username":"carlos",
    "email":"carlos@carlos-montoya.net",
    …
}
```

## Implicit grant type.

- Thay vì trước tiên lấy mã ủy quyền và sau đó đổi nó lấy mã thông báo truy cập, ứng dụng khách sẽ nhận mã thông báo truy cập ngay sau khi người dùng đồng ý.

- Khi sử dụng Implicit grant type, tất cả giao tiếp diễn ra thông qua chuyển hướng của trình duyệt - không có kênh trở lại an toàn như trong luồng mã ủy quyền. Điều này có nghĩa là mã thông báo truy cập nhạy cảm và dữ liệu của người dùng dễ bị tấn công hơn.

1. Authorization request.

- Luồng ngầm định bắt đầu theo cùng một cách với luồng mã ủy quyền. Sự khác biệt chính duy nhất là *response_type=token*. 
```
GET /authorization?client_id=12345&redirect_uri=https://client-app.com/callback&response_type=token&scope=openid%20profile&state=ae13d489bd00e3c24 HTTP/1.1
Host: oauth-authorization-server.com
```

2. User login and consent.

-Người dùng đăng nhập và quyết định có đồng ý với các quyền được yêu cầu hay không.

3. Access token grant.

- Nếu người dùng đồng ý với quyền truy cập được yêu cầu. Dịch vụ OAuth sẽ chuyển hướng trình duyệt của người dùng đến nơi *redirect_uri* chỉ định. Tuy nhiên, thay vì gửi tham số truy vấn có chứa mã ủy quyền, nó sẽ gửi mã thông báo truy cập và dữ liệu dành riêng cho mã thông báo khác dưới dạng URL.
```
GET /callback#access_token=z0y9x8w7v6u5&token_type=Bearer&expires_in=5000&scope=openid%20profile&state=ae13d489bd00e3c24 HTTP/1.1
Host: client-app.com
```
4. API call.

- Sau khi ứng dụng khách đã trích xuất thành công mã thông báo truy cập từ URL, nó có thể sử dụng để thực hiện các lệnh gọi API tới */userinfo*.
```
GET /userinfo HTTP/1.1
Host: oauth-resource-server.com
Authorization: Bearer z0y9x8w7v6u5
```
5. Resource grant.

- Máy chủ tài nguyên xác minh rằng mã thông báo hợp lệ và nó thuộc về ứng dụng khách hiện tại. Nếu vậy, nó sẽ phản hồi bằng cách gửi tài nguyên được yêu cầu tức là dữ liệu của người dùng dựa trên phạm vi được liên kết với mã thông báo truy cập.
```
{
    "username":"carlos",
    "email":"carlos@carlos-montoya.net"
}
```



