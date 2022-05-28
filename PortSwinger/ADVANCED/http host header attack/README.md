# HTTP Host header attacks.

## What is the HTTP Host header?

- HTTP Host Header, là header bắt buộc kể từ HTTP/ 1.1 nó chỉ định tên miền mà người dùng muốn truy cập.
```
GET /web-security HTTP/1.1
Host: portswigger.net
``` 

## What is the purpose of the HTTP Host header?

- Mục đích của HTTP Host Header là giúp xác định thành phần back-end nào mà máy khách muốn giao tiếp. Nếu các yêu cầu không chứa Host Header hoặc nếu Host Header không đúng định dạng, điều này có thể dẫn đến sự cố khi định tuyến các yêu cầu đến ứng dụng mong muốn.

- Mỗi địa chỉ IP sẽ chỉ lưu trữ nội dung cho một miền duy nhất. Ngày nay, phần lớn là do xu hướng ngày càng phát triển đối với các giải pháp dựa trên đám mây và thuê ngoài phần lớn kiến ​​trúc liên quan, việc nhiều trang web và ứng dụng có thể truy cập tại cùng một địa chỉ IP là điều thường thấy. Cách tiếp cận này cũng đã gia tăng phổ biến một phần là do cạn kiệt địa chỉ IPv4.

- Khi nhiều ứng dụng có thể truy cập được qua cùng một địa chỉ IP, đây thường là kết quả của một trong các trường hợp sau: 

### Virtual hosting.

- Khi một máy chủ web lưu trữ nhiều trang web hoặc ứng dụng. Đây có thể là nhiều trang web với một chủ sở hữu duy nhất, nhưng cũng có thể các trang web có các chủ sở hữu khác nhau được lưu trữ trên một nền tảng được chia sẻ duy nhất.

- Trong cả hai trường hợp, mặc dù mỗi trang web riêng biệt này sẽ có một tên miền khác nhau, nhưng tất cả chúng đều chia sẻ một địa chỉ IP chung với máy chủ. Các trang web được lưu trữ theo cách này trên một máy chủ duy nhất được gọi là "Virtual hosting".

### Routing traffic via an intermediary.

- Khi các trang web được lưu trữ trên các máy chủ back-end riêng biệt, nhưng tất cả lưu lượng truy cập giữa máy khách và máy chủ được định tuyến thông qua một hệ thống trung gian. Đây có thể là simple load balancer hoặc một reverse proxy server nào đó. Thiết lập này đặc biệt phổ biến trong các trường hợp khách hàng truy cập trang web qua mạng phân phối nội dung (CDN).

- Trong trường hợp này, mặc dù các trang web được lưu trữ trên các máy chủ back-end riêng biệt, tất cả các tên miền của chúng đều phân giải thành một địa chỉ IP của thành phần trung gian.

### How does the HTTP Host header solve this problem?

- Trong cả hai trường hợp này, *HTTP Host Header* được dựa vào để chỉ định người nhận dự định. Khi trình duyệt gửi yêu cầu, URL đích sẽ chuyển thành địa chỉ IP của một máy chủ cụ thể. Khi máy chủ này nhận được yêu cầu, nó sẽ tham chiếu đến Host Header để xác định back-end dự định và chuyển tiếp yêu cầu cho phù hợp.

## What is an HTTP Host header attack?

- Các cuộc tấn công *HTTP Host header*,nhắm vào các trang web dễ bị tấn công xử lý giá trị của *HTTP Host header* theo cách không an toàn. Nếu máy chủ hoàn toàn tin tưởng vào Host Header và không validate hoặc escape tiêu đề đó một cách hợp lệ, kẻ tấn công có thể sử dụng đầu vào này để đưa các payload có hại vào thao tác hành vi phía server. Các cuộc tấn công liên quan đến việc đưa một payload trực tiếp vào *HTTP Host header* thường được gọi là các cuộc tấn công "HTTP Host header attacks".

- Ví dụ: Khi họ cần biết tên miền hiện tại để tạo một URL tuyệt đối có trong email, họ có thể sử dụng cách truy xuất tên miền từ tiêu đề Máy chủ lưu trữ:
```
<a href="https://_SERVER['HOST']/support">Contact support</a>
```

## How do HTTP Host header vulnerabilities arise?

- Các lỗ hổng *HTTP Host Header* thường phát sinh do giả định sai sót rằng tiêu đề không thể kiểm soát được bởi người dùng. Điều này tạo ra sự tin tưởng ngầm vào Host Header và dẫn đến việc xác thực không đầy đủ hoặc thoát khỏi giá trị của nó.

- Ngay cả khi Host Header được xử lý an toàn hơn, tùy thuộc vào cấu hình của máy chủ xử lý các yêu cầu đến, Máy chủ có thể bị ghi đè bằng cách chèn các tiêu đề khác.

- Trên thực tế, nhiều lỗ hổng trong số này phát sinh không phải do mã hóa không an toàn mà do cấu hình không an toàn của một hoặc nhiều thành phần trong cơ sở hạ tầng liên quan. Các vấn đề cấu hình này có thể xảy ra do các trang web tích hợp công nghệ của bên thứ ba vào kiến ​​trúc của chúng mà không nhất thiết phải hiểu các tùy chọn cấu hình và ý nghĩa bảo mật của chúng.

- Lab:
	+ Lab: Routing-based SSRF.[exploit](exploit/lab5.py)

## [Exploiting HTTP Host header vulnerabilities.](Exploit_HHHA.md)

## How to prevent HTTP Host header attacks.

