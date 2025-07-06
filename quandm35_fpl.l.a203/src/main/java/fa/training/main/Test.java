package fa.training.main;

import fa.training.services.CustomerService;

import java.util.List;
import java.util.Scanner;

/**
 * Lớp chính điều hướng menu chương trình quản lý khách hàng.
 */
public class Test {
    public static void main(String[] args) {
        CustomerService service = new CustomerService();
        Scanner sc = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n==== MENU ====");
            System.out.println("1. Add a new Customer");
            System.out.println("2. Show all Customers");
            System.out.println("3. Search Customer");
            System.out.println("4. Remove Customer");
            System.out.println("5. Exit");

            // Vòng lặp đảm bảo người dùng nhập đúng số
            while (true) {
                System.out.print("Your choice: ");
                String input = sc.nextLine();
                try {
                    choice = Integer.parseInt(input);
                    if (choice >= 1 && choice <= 5) {
                        break; // hợp lệ
                    } else {
                        System.out.println("Please enter a number between 1 and 5.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }

            switch (choice) {
                case 1:
                    // Tạo khách hàng mới và hiển thị kết quả
                    List<String> created = service.createCustomer();
                    if (!created.isEmpty()) {
                        System.out.println("\nAdded customers:");
                        service.display(created);
                    }
                    break;

                case 2:
                    // Hiển thị toàn bộ khách hàng từ file
                    List<String> all = service.findAll();
                    if (!all.isEmpty()) {
                        System.out.println("\nAll customers:");
                        service.display(all);
                    } else {
                        System.out.println("No customer found.");
                    }
                    break;

                case 3:
                    // Tìm kiếm theo số điện thoại
                    System.out.print("Enter phone to search: ");
                    String phone = sc.nextLine();
                    List<String> found = service.search(phone);
                    if (!found.isEmpty()) {
                        System.out.println("\nSearch result:");
                        service.display(found);
                    } else {
                        System.out.println("No customer found with phone: " + phone);
                    }
                    break;

                case 4:
                    // Xoá khách hàng theo số điện thoại
                    System.out.print("Enter phone to remove: ");
                    boolean removed = service.remove(sc.nextLine());
                    System.out.println(removed ? "Customer removed." : "Customer not found.");
                    break;

                case 5:
                    // Thoát chương trình
                    System.out.println("Exiting program. Goodbye!");
                    return;

                default:
                    // Trường hợp không bao giờ xảy ra do đã kiểm tra trước
                    System.out.println("Invalid choice.");
            }
        }
    }
}
