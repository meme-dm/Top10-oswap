# Magic method in PHP.

1. **__construct()** : nó được gọi bất cứ khi nào một đối tượng của lớp được khởi tạo tương tự với __init__ trong python.

2. **__wakeup()** : được thực thi khi hàm unserialize được gọi, phương thức sẽ phục hồi lại đối tượng được lưu trong chuỗi trả về từ hàm serialize() , tương tự với readObject() trong java.

3. **__destruct()** : được gọi ở cuối khi hàm dừng hay trong một số trường hợp hàm bị hủy do lỗi.

4. **__sleep()** : thực hiện chức năng nào đó trước serialize(). Trả về một mảng với tên của tất cả các biến của đối tượng đó sẽ được serialize(). Nếu trả về null sẽ xuất hiệ lỗi E_NOTICE  khi serialize().

5. **__toString** : trả về object dưới dạng chuỗi nhưng cũng có thể được sử dụng để đọc tệp hoặc hơn thế nữa dựa trên lời gọi hàm bên trong nó.

6. **__invoke()** :  được gọi khi một tập lệnh cố gắng gọi một object dưới dạng một hàm.

7. **__set()** : được chạy khi ghi dữ liệu vào các thuộc tính không thể truy cập (được bảo vệ hoặc riêng tư) hoặc không tồn tại.

8. **__get()** : được sử dụng để đọc dữ liệu từ các thuộc tính không thể truy cập (được bảo vệ hoặc riêng tư) hoặc không tồn tại.

9. **__isset()** : được kích hoạt bằng cách gọi Isset () hoặc blank () trên các thuộc tính không thể truy cập (được bảo vệ hoặc riêng tư) hoặc không tồn tại.

10. **__unset()** : được sử dụng trên các thuộc tính không thể truy cập (được bảo vệ hoặc riêng tư) hoặc không tồn tại.