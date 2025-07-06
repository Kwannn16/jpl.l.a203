package fa.training.services;

import fa.training.entities.Customer;
import fa.training.entities.Order;
import fa.training.utils.Constants;
import fa.training.utils.Validator;

import java.io.*;
import java.util.*;

/**
 * Service class xử lý các thao tác liên quan đến khách hàng như:
 * tạo mới, lưu file, đọc file, tìm kiếm và xoá khách hàng.
 */
public class CustomerService {
    Scanner sc = new Scanner(System.in);
    OrderService orderService = new OrderService(); // Dùng để nhập thông tin đơn hàng

    /**
     * Nhập dữ liệu khách hàng và danh sách đơn hàng tương ứng.
     * Người dùng có thể nhập nhiều khách hàng liên tục.
     *
     * @return Danh sách mô tả chuỗi của các khách hàng vừa tạo.
     */
    public List<String> createCustomer() {
        List<Customer> customers = new ArrayList<>();

        while (true) {
            System.out.println("----Enter Customer information--");

            // Nhập tên
            System.out.print("Enter name: ");
            String name = sc.nextLine();

            // Nhập số điện thoại hợp lệ
            String phoneNumber;
            while (true) {
                System.out.print("Enter phone: ");
                phoneNumber = sc.nextLine();
                if (Validator.isValidPhone(phoneNumber))
                    break;
                System.out.println("Invalid phone number. Must be 10 digits.");
            }

            // Nhập địa chỉ
            System.out.print("Enter address: ");
            String address = sc.nextLine();

            // Nhập danh sách đơn hàng
            List<Order> orders = new ArrayList<>();
            while (true) {
                System.out.println("Enter order info:");
                orders.add(orderService.inputOrder()); // gọi hàm nhập đơn hàng

                System.out.print("Continue adding orders? (y/n): ");
                if (sc.nextLine().equalsIgnoreCase("n"))
                    break;
            }

            // Thêm khách hàng vào danh sách
            customers.add(new Customer(name, phoneNumber, address, orders));

            System.out.print("Continue adding customers? (y/n): ");
            if (sc.nextLine().equalsIgnoreCase("n"))
                break;
        }

        // Lưu toàn bộ danh sách khách hàng vào file
        save(customers);

        // Trả về danh sách String hiển thị
        List<String> result = new ArrayList<>();
        for (Customer c : customers)
            result.add(c.toString());
        return result;
    }

    /**
     * Ghi danh sách khách hàng vào file nhị phân.
     *
     * @param customers danh sách khách hàng cần lưu
     * @return Thông báo thành công hoặc thất bại
     */
    public String save(List<Customer> customers) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Constants.FILE_NAME))) {
            oos.writeObject(customers);
            return "Saved successfully.";
        } catch (IOException e) {
            return "Save failed: " + e.getMessage();
        }
    }

    /**
     * Đọc toàn bộ khách hàng từ file và chuyển thành List<String> mô tả.
     *
     * @return danh sách chuỗi mô tả khách hàng
     */
    public List<String> findAll() {
        List<String> result = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Constants.FILE_NAME))) {
            List<Customer> customers = (List<Customer>) ois.readObject();
            for (Customer c : customers)
                result.add(c.toString());
        } catch (Exception e) {
            // Nếu file không tồn tại hoặc rỗng thì trả về list rỗng
        }
        return result;
    }

    /**
     * Hiển thị danh sách khách hàng ra console ở định dạng bảng.
     *
     * @param customerStrList danh sách chuỗi khách hàng
     */
    public void display(List<String> customerStrList) {

        for (String s : customerStrList) {
            System.out.println(s);
        }
    }

    /**
     * Tìm kiếm khách hàng theo số điện thoại.
     *
     * @param phone số điện thoại cần tìm
     * @return danh sách chuỗi các khách hàng phù hợp
     */
    public List<String> search(String phone) {
        List<String> result = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Constants.FILE_NAME))) {
            List<Customer> customers = (List<Customer>) ois.readObject();
            for (Customer c : customers) {
                if (c.getPhoneNumber().equals(phone)) {
                    result.add(c.toString());
                }
            }
        } catch (Exception e) {
            // Trả về danh sách rỗng nếu không tìm thấy
        }
        return result;
    }

    /**
     * Xoá khách hàng theo số điện thoại.
     * Nếu xoá thành công sẽ ghi lại file với danh sách mới.
     *
     * @param phone số điện thoại khách hàng cần xoá
     * @return true nếu xoá thành công, false nếu không tìm thấy
     */
    public boolean remove(String phone) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Constants.FILE_NAME))) {
            List<Customer> customers = (List<Customer>) ois.readObject();
            boolean removed = customers.removeIf(c -> c.getPhoneNumber().equals(phone));
            if (removed)
                save(customers);
            return removed;
        } catch (Exception e) {
            return false;
        }
    }
}
