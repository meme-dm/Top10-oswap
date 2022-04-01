# Information disclosure vulnerabilities

## What is information disclosure?

- Information disclosure (aka information leakage), là khi website vô tình để lộ những thông tin nhạy cảm cho người dùng. Tùy thuộc vào ngữ cảnh, các trang web có thể làm rò rỉ các loại thông tin  bao gồm:
	+ Dữ liệu về những người dùng khác, chẳng hạn như tên người dùng hoặc thông tin tài chính.
	+ Dữ liệu kinh doanh hoặc thương mại nhạy cảm.
	+ Chi tiết kỹ thuật về trang web và cơ sở hạ tầng của nó.

-  Một số thông tin có vẻ như không mấy quan trọng, nhưng nó có thể là điểm khởi đầu cho việc lộ diện tấn công bổ sung, có thể chứa các lỗ hổng khác. Kiến thức mà bạn có thể thu thập thậm chí có thể cung cấp phần còn thiếu khi cố gắng xây dựng các cuộc tấn công phức tạp, mức độ nghiêm trọng cao.

- Thông tin nhạy cảm có thể bị rò rỉ một cách bất cẩn cho người dùng đang dùng trang web một cách bình thường. Tuy nhiên, phổ biến kẻ tấn công cần phải tiết lộ thông tin bằng cách tương tác với trang web theo những cách không mong muốn hoặc độc hại. Sau đó, họ sẽ nghiên cứu cẩn thận các phản hồi của trang web để thử và xác định các hành vi khác.

### What are some examples of information disclosure?

- Tiết lộ tên của các thư mục ẩn, cấu trúc của chúng và nội dung của chúng thông qua tệp robots.txt hoặc danh sách thư mục.
- Cung cấp quyền truy cập vào các tệp mã nguồn thông qua các bản sao lưu .
- Để lộ tên cột hoặc bảng cơ sở dữ liệu trong thông báo lỗi
- Khóa API, IP, thông tin đăng nhập cơ sở dữ liệu, v.v. trong mã nguồn
- Gợi ý về sự tồn tại hoặc không có tài nguyên, tên người dùng, v.v. thông qua sự khác biệt nhỏ trong hành vi ứng dụng.

- [How to find and exploit information disclosure vulnerabilities](find.md)

## How do information disclosure vulnerabilities arise?

- Không thể xóa nội dung nội bộ khỏi nội dung công khai.
- Cấu hình trang web không an toàn và các công nghệ liên quan.
- Thiết kế và hành vi sai trái của ứng dụng. Ví dụ: nếu một trang web trả về các phản hồi riêng biệt khi các trạng thái lỗi khác nhau xảy ra, điều này cũng có thể cho phép kẻ tấn công liệt kê dữ liệu nhạy cảm , chẳng hạn như thông tin đăng nhập hợp lệ của người dùng.

## What is the impact of information disclosure vulnerabilities?

Các lỗ hổng tiết lộ thông tin có thể có cả tác động trực tiếp và gián tiếp tùy thuộc vào mục đích của trang web và do đó, kẻ tấn công có thể lấy được thông tin nào. Trong một số trường hợp, chỉ riêng hành vi tiết lộ thông tin nhạy cảm cũng có thể gây ảnh hưởng lớn đến các bên bị ảnh hưởng.

Mặt khác, thông tin kỹ thuật bị rò rỉ, chẳng hạn như cấu trúc thư mục hoặc các khuôn khổ của bên thứ ba đang được sử dụng, có thể ít hoặc không có tác động trực tiếp. Tuy nhiên, đối với những kẻ xấu, đây có thể là thông tin quan trọng cần thiết để xây dựng bất kỳ khai thác nào khác. Mức độ nghiêm trọng trong trường hợp này phụ thuộc vào những gì kẻ tấn công có thể làm với thông tin này.

### How to assess the severity of information disclosure vulnerabilities.

Mặc dù tác động cuối cùng có thể rất nghiêm trọng, nhưng chỉ trong những trường hợp cụ thể, việc công bố thông tin tự nó là một vấn đề có mức độ nghiêm trọng cao. Trong quá trình thử nghiệm, việc tiết lộ thông tin kỹ thuật nói riêng thường chỉ được quan tâm nếu bạn có thể chứng minh cách kẻ tấn công có thể làm điều gì đó có hại với nó.

Bạn nên tập trung vào tác động và khả năng khai thác của information disclosure , chứ không chỉ là sự hiện diện của việc công bố thông tin như một vấn đề độc lập. Ngoại lệ rõ ràng cho điều này là khi thông tin bị rò rỉ rất nhạy cảm nên nó cần được chú ý theo đúng nghĩa của nó.

## Exploiting information disclosure.

## How to prevent information disclosure vulnerabilities.

- Đảm bảo rằng tất cả mọi người tham gia tạo trang web đều nhận thức được đầy đủ thông tin nào được coi là nhạy cảm. 
- Kiểm tra bất kỳ mã nào có thể tiết lộ thông tin tiềm năng .
- Sử dụng các thông báo lỗi chung chung càng nhiều càng tốt. Không cung cấp cho những kẻ tấn công manh mối về hành vi ứng dụng một cách không cần thiết.
- Kiểm tra kỹ xem mọi tính năng gỡ lỗi hoặc chẩn đoán có bị tắt trong môi trường sản xuất hay không.
- Đảm bảo rằng bạn hiểu đầy đủ cài đặt cấu hình và hàm ý bảo mật của bất kỳ công nghệ bên thứ ba nào mà bạn triển khai. Dành thời gian để điều tra và tắt bất kỳ tính năng và cài đặt nào mà bạn không thực sự cần.