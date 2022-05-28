# XML entities.

## What is XML?

- XML (aka extensible markup language) là một ngôn ngữ được thiết kế để lưu trữ và vận chuyển dữ liệu. Giống như HTML, XML sử dụng cấu trúc dạng cây gồm các thẻ và dữ liệu. 
- Điểm khác HTML, XML không sử dụng các thẻ được xác định trước và vì vậy các thẻ có thể được đặt tên mô tả dữ liệu. Trước đó, XML đã phổ biến như một định dạng truyền tải dữ liệu ("X" trong "AJAX" là viết tắt của "XML"). Nhưng sự phổ biến của nó hiện đã giảm với sự xuất hiện của JSON.

## What are XML entities?

- Các thực thể XML là một cách biểu diễn một mục dữ liệu trong tài liệu XML, thay vì sử dụng chính dữ liệu đó. Nhiều thực thể khác nhau được xây dựng trong đặc điểm kỹ thuật của ngôn ngữ XML. 
- Ví dụ, các thực thể **&lt;** và **&gt;** ,đại diện cho các ký tự **<** , **>**. Đây là các siêu ký tự được sử dụng để biểu thị các thẻ XML và do đó thường phải được biểu diễn bằng cách sử dụng các thực thể của chúng khi chúng xuất hiện trong dữ liệu.

## What is document type definition?

- Định nghĩa kiểu tài liệu XML (DTD) chứa các khai báo có thể xác định cấu trúc của tài liệu XML, các kiểu giá trị dữ liệu mà nó có thể chứa và các mục khác. DTD được khai báo trong *DOCTYPE* phần tử tùy chọn ở đầu tài liệu XML. DTD có thể hoàn toàn độc lập trong chính tài liệu (được gọi là "DTD nội bộ") hoặc có thể được tải từ nơi khác (được gọi là "DTD bên ngoài") hoặc có thể kết hợp cả hai.

## What are XML custom entities?

- XML cho phép các thực thể tùy chỉnh được định nghĩa trong DTD.

> <!DOCTYPE foo [ <!ENTITY myentity "my entity value" > ]>

- Định nghĩa này có nghĩa là khi sử dụng tham chiếu thực thể *&myentity;* trong tài liệu XML sẽ được thay thế bằng giá trị đã xác định: "my entity value".

## What are XML external entities?

- XML external entities là một loại thực thể tùy chỉnh có định nghĩa nằm bên ngoài DTD nơi chúng được khai báo.
- Khai báo của một thực thể bên ngoài sử dụng *SYSTEM* và phải chỉ định một URL mà từ đó giá trị của thực thể sẽ được tải.

> <!DOCTYPE foo [ <!ENTITY ext SYSTEM "http://normal-website.com" > ]>

- URL có thể sử dụng protocol *file://* và do đó các thực thể bên ngoài có thể được tải từ tệp.

> <!DOCTYPE foo [ <!ENTITY ext SYSTEM "file:///path/to/file" > ]>