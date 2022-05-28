# OAuth 2.0 authentication vulnerabilities.

## What is OAuth?

- OAuth là khung ủy quyền thường được sử dụng cho phép các trang web và ứng dụng web yêu cầu quyền truy cập vào tài khoản của người dùng trên một ứng dụng khác. OAuth cho phép người dùng cấp quyền truy cập này mà không để lộ thông tin đăng nhập của họ cho ứng dụng yêu cầu. Điều này có nghĩa là người dùng có thể kiểm soát dữ liệu nào họ muốn chia sẻ thay vì phải giao toàn quyền kiểm soát tài khoản của mình cho bên thứ ba.

- Quy trình OAuth cơ bản được sử dụng rộng rãi để tích hợp chức năng của bên thứ ba yêu cầu quyền truy cập vào dữ liệu nhất định từ tài khoản của người dùng. 

## How does OAuth 2.0 work?

- OAuth 2.0 ban đầu được phát triển như một cách chia sẻ quyền truy cập vào dữ liệu cụ thể giữa các ứng dụng. Nó hoạt động bằng cách xác định một loạt các tương tác giữa ba bên riêng biệt, cụ thể là ứng dụng khách, chủ sở hữu tài nguyên và nhà cung cấp dịch vụ OAuth.

- Ứng dụng khách - Trang web hoặc ứng dụng web muốn truy cập dữ liệu của người dùng.
- Chủ sở hữu tài nguyên - Người dùng có dữ liệu mà ứng dụng khách muốn truy cập.
- Nhà cung cấp dịch vụ OAuth - Trang web hoặc ứng dụng kiểm soát dữ liệu của người dùng và quyền truy cập vào dữ liệu đó. Họ hỗ trợ OAuth bằng cách cung cấp API để tương tác với cả máy chủ ủy quyền và máy chủ tài nguyên.

- Có nhiều cách khác nhau để có thể triển khai quy trình OAuth thực tế. Chúng được gọi là "flows" hoặc "grant types".

	+ Ứng dụng khách yêu cầu quyền truy cập vào một tập hợp con dữ liệu của người dùng, chỉ định loại tài trợ nào họ muốn sử dụng và loại quyền truy cập họ muốn.
	+ Người dùng đăng nhập vào dịch vụ OAuth và đồng ý với quyền truy cập được yêu cầu.
	+ Ứng dụng khách nhận được mã thông báo truy cập duy nhất.
	+ Ứng dụng khách sử dụng mã thông báo truy cập này để thực hiện các lệnh gọi API tìm nạp dữ liệu có liên quan từ máy chủ tài nguyên.

- [OAuth grant types.](grant_type.md)

### OAuth authentication.

- Xác thực OAuth thường được triển khai như sau:

	+ Người dùng chọn tùy chọn để đăng nhập bằng tài khoản mạng xã hội của họ. Sau đó, ứng dụng khách sử dụng dịch vụ OAuth của trang web truyền thông xã hội để yêu cầu quyền truy cập vào một số dữ liệu mà nó có thể sử dụng để xác định người dùng.
	+ Sau khi nhận được mã thông báo truy cập, ứng dụng khách yêu cầu dữ liệu này từ máy chủ tài nguyên, thường là */userinfo*.
	+ Sau khi nhận được dữ liệu, ứng dụng khách sẽ sử dụng nó thay cho tên người dùng để đăng nhập người dùng. Mã thông báo truy cập mà nó nhận được từ máy chủ ủy quyền thường được sử dụng thay vì mật khẩu truyền thống.

- Lab:
	+ Lab: Authentication bypass via OAuth implicit flow.[exploit](exploit/lab1.py)

## How do OAuth authentication vulnerabilities arise?

- Các lỗ hổng xác thực OAuth phát sinh một phần do đặc điểm kỹ thuật OAuth tương đối mơ hồ và linh hoạt theo thiết kế. Mặc dù có một số thành phần bắt buộc cần thiết cho chức năng cơ bản, nhưng phần lớn việc triển khai là hoàn toàn tùy chọn. Điều này bao gồm nhiều cài đặt cấu hình cần thiết để giữ an toàn cho dữ liệu của người dùng.

- Một trong những vấn đề quan trọng khác với OAuth là thiếu các tính năng bảo mật tích hợp chung. Bảo mật gần như hoàn toàn dựa vào các nhà phát triển sử dụng kết hợp các tùy chọn cấu hình phù hợp và thực hiện các biện pháp bảo mật bổ sung của riêng họ, chẳng hạn như xác thực đầu vào mạnh mẽ.

## Identifying OAuth authentication.

- Nếu bạn thấy tùy chọn đăng nhập bằng tài khoản của mình từ một trang web khác, thì đây là dấu hiệu rõ ràng cho thấy OAuth đang được sử dụng.

- Bất kể loại cấp OAuth nào đang được sử dụng, yêu cầu đầu tiên của luồng sẽ luôn tới */authorization* chứa một số tham số truy vấn được sử dụng riêng cho OAuth. Đặc biệt, hãy để ý đến *client_id*, *redirect_uri* và *response_type*.

```
GET /authorization?client_id=12345&redirect_uri=https://client-app.com/callback&response_type=token&scope=openid%20profile&state=ae13d489bd00e3c24 HTTP/1.1
Host: oauth-authorization-server.com
```
## Recon.

- Nếu dịch vụ OAuth bên ngoài được sử dụng, bạn sẽ có thể xác định nhà cung cấp cụ thể từ tên máy chủ mà yêu cầu ủy quyền được gửi đến. Vì các dịch vụ này cung cấp API công khai, nên thường có sẵn tài liệu chi tiết sẽ cho bạn biết tất cả các loại thông tin hữu ích, chẳng hạn như tên chính xác của các điểm cuối và các tùy chọn cấu hình nào đang được sử dụng.

- Khi bạn biết tên máy chủ của máy chủ ủy quyền, bạn nên thử gửi *GET* đến các điểm cuối tiêu chuẩn sau:

>/.well-known/oauth-authorization-server
>/.well-known/openid-configuration

- Các tệp này thường sẽ trả về tệp cấu hình JSON.

## Exploiting OAuth authentication vulnerabilities.

### Vulnerabilities in the OAuth client application.

#### Improper implementation of the implicit grant type.

#### Flawed CSRF protection.


### Leaking authorization codes and access tokens.

#### Flawed redirect_uri validation.

#### Stealing codes and access tokens via a proxy page.

### Flawed scope validation.

#### Scope upgrade: authorization code flow.

#### Scope upgrade: implicit flow.


### Unverified user registration.

## Extending OAuth with OpenID Connect.

## Preventing OAuth authentication vulnerabilities
