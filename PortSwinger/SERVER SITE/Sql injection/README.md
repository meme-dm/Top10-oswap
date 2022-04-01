# SQL injection 

## What is SQL injection (SQLi)?

- SQL injection là một lỗ hổng bảo mật web cho phép kẻ tấn công can thiệp vào các truy vấn mà ứng dụng thực hiện đối với cơ sở dữ liệu của nó. Từ đó , kẻ tấn công có thể xem dữ liệu nhạy cảm bao gồm dữ liệu thuộc về người dùng khác hoặc bất kỳ dữ liệu nào khác mà bản thân ứng dụng có thể truy cập. Trong nhiều trường hợp, kẻ tấn công có thể sửa đổi hoặc xóa dữ liệu này, gây ra các thay đổi đối với nội dung hoặc hành vi của ứng dụng.

- Trong một số tình huống, kẻ tấn công có thể leo thang một cuộc tấn công SQL injection để xâm nhập máy chủ bên dưới hoặc cơ sở hạ tầng back-end khác hoặc thực hiện một cuộc tấn công từ chối dịch vụ hay thậm chí là RCE.

## Retrieving hidden data.

- Một số ứng dụng cho phép hiển thị dữ liệu ví dụ :

```https://insecure-website.com/products?category=Gifts```

=> Câu truy vấn có thể là  : **SELECT * FROM products WHERE category = 'Gifts' AND released = 1**

- Ta có thể thấy ứng dụng liệt kê ra các sản phẩm *Gifts* và có *released=1* , như vậy sẽ ra sao nếu câu truy vấn trở thành :

=> Liệt kê hết tất cả *Gifts* kể cả *released=0* : **SELECT * FROM products WHERE category = 'Gifts'-- AND released = 1**(?category=Gifts'--)
=> Liệt kê tất cả các sản phẩm : **SELECT * FROM products WHERE category = 'Gifts' or 1=1-- - AND released = 1**(?category=Gifts' or 1=1--)

- Lab :
	+ SQL injection vulnerability in WHERE clause allowing retrieval of hidden data.([exploit](exploit/lab1.py))

## Subverting application logic.

- Ứng dụng cho phép người dùng đăng nhập bằng tên người dùng và mật khẩu. Nếu người dùng gửi username='x' và pasword='y', ứng dụng sẽ kiểm tra thông tin đăng nhập bằng cách thực hiện truy vấn SQL sau:
=> **SELECT * FROM users WHERE username = 'x' AND password = 'y';** 

- Tuy nhiên , khi username=administrator'-- thậm chí người dùng ko cần quan tâm đến mật khẩu mà có thể đăng nhập với tài khoản bất kì .
=> **SELECT * FROM users WHERE username = 'administrator'--' AND password = ''**

- Lab : 
	+ SQL injection vulnerability allowing login bypass.([exploit](exploit/lab2.py))

## Retrieving data from other database tables.

- Trong trường hợp kết quả của một truy vấn SQL được trả về trong các phản hồi của ứng dụng, kẻ tấn công có thể tận dụng lỗ hổng SQL injection để lấy dữ liệu từ các bảng khác trong cơ sở dữ liệu.
- Điều này được thực hiện bằng cách sử dụng *UNION*, cho phép bạn thực hiện một *SELECT* truy vấn bổ sung và nối kết quả vào truy vấn ban đầu.

=> SELECT name, description FROM products WHERE category = 'Gifts' UNION SELECT * FROM users-- -

[SQL injection UNION attacks](union.md).

## Examining the database.

## Blind SQL injection vulnerabilities.

## How to detect SQL injection vulnerabilities.

## SQL injection in different parts of the query.

## Second-order SQL injection.

## Database-specific factors.

## How to prevent SQL injection.


 


