# XSS and CSRF

## What is the difference between XSS and CSRF?

- XSS cho phép attacker thực thi js tùy ý trong trình duyệt của người dùng nạn nhân.

- CSRF cho phép kẻ tấn công khiến người dùng nạn nhân thực hiện các hành động mà họ không có ý định.

	+ CSRF chỉ áp dụng cho một số hành động còn XSS có thể thao tác với bất khì hành động nào mà người dùng có thể làm .

	+ CSRF là lỗ hổng một chiều kẻ tấn công chỉ có thể khiến nạn nhân gửi một request, còn xss có thể đưa ra các request đồng thời xem phản hồi và lấy dữ liệu .

## Can CSRF tokens prevent XSS attacks?

- Nếu lỗ hổng Reflected XSS tồn tại ở bất kỳ nơi nào khác trên trang web trong một chức năng không được bảo vệ bằng mã thông báo CSRF, thì XSS đó có thể được khai thác theo cách bình thường.
- Nếu lỗ hổng XSS có thể khai thác tồn tại ở bất kỳ đâu trên một trang web, thì lỗ hổng bảo mật có thể được tận dụng để khiến người dùng là nạn nhân thực hiện các hành động ngay cả khi bản thân các hành động đó được bảo vệ bằng CSRF tokens. 
- Mã thông báo CSRF không bảo vệ khỏi các lỗ hổng Stored XSS . 