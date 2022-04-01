# CORS and the Access-Control-Allow-Origin response header

## What is the Access-Control-Allow-Origin response header?

- *Access-Control-Allow-Origin* header trong phản hồi từ một trang web cho một yêu cầu bắt nguồn từ một trang web khác và xác định nguồn gốc được phép của yêu cầu. Trình duyệt web so sánh *Access-Control-Allow-Origin* với nguồn gốc của trang web yêu cầu và cho phép truy cập vào phản hồi nếu chúng khớp.

## Implementing simple cross-origin resource sharing.

- Đặc tả chia sẻ tài nguyên nguồn gốc chéo (CORS) quy định nội dung tiêu đề được trao đổi giữa máy chủ web và trình duyệt hạn chế nguồn gốc cho các yêu cầu tài nguyên web bên ngoài miền gốc. Access-Control-Allow-Origin được máy chủ trả về khi một trang web yêu cầu tài nguyên cross-site, với Origin header được trình duyệt thêm vào.

- Đặc điểm kỹ thuật của Access-Control-Allow-Origin cho phép nhiều nguồn gốc hoặc null, * . Tuy nhiên, không có trình duyệt nào hỗ trợ nhiều nguồn gốc và có những hạn chế đối với việc sử dụng ký tự đại diện * .

## Handling cross-origin resource requests with credentials.

- Hành vi mặc định của các yêu cầu tài nguyên có nguồn gốc chéo là yêu cầu được chuyển mà không có thông tin xác thực như cookie và Authorization header. Tuy nhiên, máy chủ tên miền chéo có thể cho phép đọc phản hồi khi thông tin xác thực được chuyển cho nó bằng cách đặt *Access-Control-Allow-Credentials: true*.

## Does CORS protect against CSRF? 

NO !

