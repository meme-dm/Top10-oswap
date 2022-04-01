# Cross-site scripting.

## What is cross-site scripting (XSS)?

-  Cross-site scripting (aka XSS), là một lỗ hổng bảo mật web cho cho phép kẻ tấn công xâm phạm các tương tác mà người dùng có với một ứng dụng dễ bị tấn công .
- Nó cho phép attacker tấn công phá vỡ chính sách nguồn gốc giống nhau , chính sách này được thiết kế để tách các trang web khác nhau khỏi nhau.
- XSS thường cho phép attacker tấn công giả dạng user để thực hiện các hành động mà user có thể truy cập.


## How does XSS work?  

- XSS hoạy động bằng cách khiến một website trả về javascript độc hại cho người dùng . Khi mã độc thực thi bên trong trình duyệt của nạn nhân , kẻ tấn công hoàn toàn có thể xâm phạm sự tương tác của chúng với ứng dụng.

## XSS proof of concept.

- Để có thể xác nhận các lỗ hổng XSS , chúng ta thường sử dụng alert() để bật lên một thông báo chứng tỏ lệnh javascript được thực thi .
- Tuy nhiên gần đây , một số phiên bản chrome ngăn không hiển thị alert() vì vậy để giải quyết ta có thể dùng print() để thay thế .  

## What are the types of XSS attacks?

1. [Reflected XSS](Reflected cross-site scripting.md), nơi tập lệnh độc hại đến từ yêu cầu HTTP hiện tại.
2. [Stored XSS](Stored cross-site scripting.md), nơi tập lệnh độc hại đến từ cơ sở dữ liệu của trang web.
3. [DOM-based XSS](DOM-based cross-site scripting.md), nơi lỗ hổng bảo mật tồn tại trong mã phía máy khách chứ không phải mã phía máy chủ.

##  What can XSS be used for?

- Mạo danh hoặc giả dạng người dùng nạn nhân.
- Thực hiện bất kỳ hành động nào mà người dùng có thể thực hiện.
- Đọc bất kỳ dữ liệu nào mà người dùng có thể truy cập.
- Nắm bắt thông tin đăng nhập của người dùng.
- Thực hiện thay đổi bề mặt ảo của trang web.
- Chèn chức năng trojan vào trang web.

## Impact of XSS vulnerabilities.

- Nếu người dùng bị xâm phạm có các đặc quyền nâng cao trong ứng dụng, thì tác động nói chung sẽ rất nghiêm trọng, cho phép kẻ tấn công có toàn quyền kiểm soát ứng dụng dễ bị tấn công và xâm phạm tất cả người dùng và dữ liệu của họ.

## How to find and test for XSS vulnerabilities.

## Content security policy.

- [CSP](csp.md)

## Dangling markup injection.

- 

## How to prevent XSS attacks.

## Common questions about cross-site scripting.

