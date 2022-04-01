# SQL injection UNION attacks

Khi một ứng dụng dễ bị chèn SQL và kết quả của truy vấn được trả về trong các phản hồi của ứng dụng, *UNION* có thể được sử dụng để truy xuất dữ liệu từ các bảng khác trong cơ sở dữ liệu.

*UNION* cho phép bạn thực hiện một hoặc nhiều *SELECT* truy vấn bổ sung và nối kết quả vào truy vấn ban đầu.

=> **SELECT a, b FROM table1 UNION SELECT c, d FROM table2**

Để một *UNION* truy vấn hoạt động, hai yêu cầu chính phải được đáp ứng:

	- Các truy vấn riêng lẻ phải trả về cùng một số cột.
	- Các kiểu dữ liệu trong mỗi cột phải tương thích giữa các truy vấn riêng lẻ.

=> Có bao nhiêu cột đang được trả về từ truy vấn ban đầu?
=> Những cột nào được trả về từ truy vấn ban đầu thuộc loại dữ liệu phù hợp để giữ kết quả từ truy vấn được đưa vào?

## Determining the number of columns required in an SQL injection UNION attack

- Khi thực hiện một cuộc tấn công SQL injection UNION, có hai phương pháp hiệu quả để xác định có bao nhiêu cột đang được trả về từ truy vấn ban đầu.

	+ ' ORDER BY 1(2,3, ..)--
	+ ' UNION SELECT NULL(, NULL, NULL, ...)-- 

- Lý do sử dụng *NULL* làm giá trị trả về từ truy vấn được chèn *SELECT* là các kiểu dữ liệu trong mỗi cột phải tương thích giữa truy vấn gốc và truy vấn được chèn. Vì *NULL* có thể chuyển đổi thành mọi kiểu dữ liệu thường được sử dụng.

- Trên Oracle, mọi *SELECT* truy vấn phải sử dụng *FROM*  và chỉ định một bảng hợp lệ. Có một bảng tích hợp trên Oracle được gọi là bảng *dual* có thể được sử dụng cho mục đích này.
=> **' UNION SELECT NULL FROM DUAL--**

- Chúng ta có thể có nhiều dạng command : -- , /*x*/ , #

- Lab :
	+ SQL injection UNION attack, determining the number of columns returned by the query.([exploit](exploit/lab3.py))


## Finding columns with a useful data type in an SQL injection UNION attack

- Lý do để thực hiện một cuộc tấn công SQL injection *UNION* là để có thể truy xuất kết quả từ một truy vấn được đưa vào. Vì vậy, dữ liệu mà bạn muốn truy xuất sẽ ở dạng chuỗi, vì vậy bạn cần tìm một hoặc nhiều cột trong kết quả truy vấn ban đầu có kiểu dữ liệu hoặc tương thích với dữ liệu chuỗi.

- Ví dụ :
```
' UNION SELECT 'a',NULL,NULL--
' UNION SELECT NULL,'a',NULL--
' UNION SELECT NULL,NULL,'a'--
```
- Lab :
	+ SQL injection UNION attack, finding a column containing text.([exploit](exploit/lab4.py))

## Using an SQL injection UNION attack to retrieve interesting data

## Retrieving multiple values within a single column