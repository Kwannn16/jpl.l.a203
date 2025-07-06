package fa.training.services;

import fa.training.entities.Order;
import fa.training.utils.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

// Lớp phục vụ việc nhập đơn hàng
public class OrderService {
    private Scanner sc = new Scanner(System.in);

    // Nhập một đơn hàng và trả về đối tượng Order
    public Order inputOrder() {
        String number;
        while (true) {
            System.out.print("+ number: ");
            number = sc.nextLine();
            if (Validator.isValidOrderNumber(number))
                break;
            System.out.println("Invalid order number. Must be 10 digits.");
        }

        Date date;
        while (true) {
            try {
                System.out.print("+ date (dd/MM/yyyy): ");
                date = new SimpleDateFormat("dd/MM/yyyy").parse(sc.nextLine());
                break;
            } catch (ParseException e) {
                System.out.println("Invalid date format. Try again.");
            }
        }

        return new Order(number, date);
    }
}
