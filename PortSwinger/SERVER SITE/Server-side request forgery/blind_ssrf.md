# Blind SSRF vulnerabilities

## What is blind SSRF?

- Blind SSRF phát sinh khi một ứng dụng có thể được tạo ra để đưa ra một yêu cầu HTTP back-end tới một URL được cung cấp, nhưng phản hồi từ yêu cầu back-end không được trả lại trong phản hồi front-end của ứng dụng.

## What is the impact of blind SSRF vulnerabilities?

- Tác động của Blind SSRF thường thấp hơn các lỗ hổng SSRF được thông báo đầy đủ vì tính chất một chiều của chúng. Chúng không thể bị khai thác một cách bình thường để lấy dữ liệu nhạy cảm từ các hệ thống back-end, mặc dù trong một số tình huống, chúng có thể được khai thác để thực thi mã từ xa.

## How to find and exploit blind SSRF vulnerabilities.

- Cách đáng tin cậy nhất để phát hiện các lỗ hổng Blind SSRF là sử dụng các kỹ thuật ngoài băng tần (OAST). Điều này liên quan đến việc cố gắng kích hoạt một yêu cầu HTTP đến một hệ thống bên ngoài mà bạn kiểm soát và giám sát các tương tác mạng với hệ thống đó.

> Thông thường khi kiểm tra lỗ hổng SSRF là quan sát yêu cầu DNS mà không có HTTP. Điều này thường xảy ra do ứng dụng đã cố gắng thực hiện một yêu cầu HTTP đến miền (gây ra yêu cầu DNS), nhưng yêu cầu HTTP thực tế đã bị chặn bởi bộ lọc cấp mạng. Cơ sở hạ tầng cho phép lưu lượng DNS đi là điều tương đối phổ biến, vì điều này là cần thiết cho nhiều mục đích, nhưng lại chặn các kết nối HTTP đến các điểm đến không mong muốn.

- Lab:
	+ Lab: Blind SSRF with out-of-band detection.[exploit](lab6.py)

- Khi Blind SSRF kích hoạt các yêu cầu HTTP ngoài băng tần, bản thân nó không cung cấp một tuyến đường dẫn đến khả năng khai thác. Vì bạn không thể xem phản hồi từ yêu cầu back-end, nên không thể sử dụng hành vi này để khám phá nội dung trên các hệ thống máy chủ. 
- Tuy nhiên, nó vẫn có thể được tận dụng để thăm dò các lỗ hổng khác trên chính máy chủ hoặc trên các hệ thống back-end khác. Bạn có thể quét không gian địa chỉ IP nội bộ , gửi tải trọng được thiết kế để phát hiện các lỗ hổng. Nếu những tải trọng đó cũng sử dụng các kỹ thuật ngoài băng tần, thì bạn có thể phát hiện ra lỗ hổng nghiêm trọng trên một máy chủ nội bộ chưa được vá.

- Lab:
	+ Phòng thí nghiệm: SSRF mù với khai thác Shellshock.[exploit](lab7.py)

- Một cách khác để khai thác các lỗ hổng Blind SSRF là khiến ứng dụng kết nối với hệ thống dưới sự kiểm soát của kẻ tấn công và trả lại phản hồi độc hại cho máy khách HTTP tạo kết nối. Nếu bạn có thể khai thác một lỗ hổng nghiêm trọng phía máy khách trong quá trình triển khai HTTP của máy chủ, bạn có thể thực hiện mã từ xa trong cơ sở hạ tầng ứng dụng.

