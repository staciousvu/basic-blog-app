package com.example.blogapp;

import lombok.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
//@Data
//class Order{
//    private final int orderId;
//    private final int tableNumber;
//    private final String dish;
//    public Order(int orderId, int tableNumber, String dish) {
//        this.orderId = orderId;
//        this.tableNumber = tableNumber;
//        this.dish = dish;
//    }
//    @Override
//    public String toString() {
//        return "Order ID: " + orderId + ", Table: " + tableNumber + ", Dish: " + dish;
//    }
//}
//class KitChen{
//    private final Queue<Order> orderQueue = new LinkedList<>();
//
//    // Đồng bộ hóa đặt món
//    public synchronized void placeOrder(Order order) {
//        orderQueue.add(order);
//        System.out.println("Đã nhận " + order);
//        notify(); // Thông báo cho đầu bếp rằng có đơn hàng mới
//    }
//
//    // Đồng bộ hóa xử lý món ăn
//    public synchronized void processOrder() {
//        while (orderQueue.isEmpty()) {
//            try {
//                wait(); // Chờ cho đến khi có đơn hàng
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        }
//        Order order = orderQueue.poll();
//        System.out.println("Đầu bếp đang xử lý: " + order);
//        try {
//            Thread.sleep(2000); // Giả lập thời gian nấu ăn
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//        System.out.println("Hoàn thành: " + order);
//    }
//}
@SpringBootTest
class BlogAppApplicationTests {

    @Test
    void contextLoads() throws IOException {
//        KitChen kitchen = new KitChen();
//
//        // Thread đầu bếp
//        Thread chefThread = new Thread(() -> {
//            while (true) {
//                kitchen.processOrder();
//            }
//        }, "Đầu bếp");
//
//        chefThread.start();
//
//        // Thread khách hàng
//        Thread customer1 = new Thread(() -> {
//            for (int i = 1; i <= 5; i++) {
//                kitchen.placeOrder(new Order(i, 1, "Món ăn " + i));
//                try {
//                    Thread.sleep(500); // Giả lập thời gian khách đặt món
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }
//            }
//        }, "Khách hàng 1");
//
//        Thread customer2 = new Thread(() -> {
//            for (int i = 6; i <= 10; i++) {
//                kitchen.placeOrder(new Order(i, 2, "Món ăn " + i));
//                try {
//                    Thread.sleep(700); // Giả lập thời gian khách đặt món
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }
//            }
//        }, "Khách hàng 2");
//
//        customer1.start();
//        customer2.start();
//
//        try {
//            customer1.join();
//            customer2.join();
//            System.out.println("Hoan thanh tasks");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }
}








//
//package com.example.blogapp;
//
//import com.example.blogapp.utils.UploadCloudServiceUtil;
//import lombok.*;
//        import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.IOException;
//import java.util.LinkedList;
//import java.util.Queue;
//import java.util.Random;
//
//class Warehouse {
//    private int inventory = 0; // Số lượng hàng hiện có trong kho
//    private final int MAX_CAPACITY = 50; // Giới hạn tối đa của kho
//
//    public synchronized void addStock(int quantity) {
//        // Chờ nếu hàng trong kho đạt giới hạn tối đa
//        while (inventory + quantity > MAX_CAPACITY) {
//            try {
//                System.out.println(Thread.currentThread().getName() + " chờ vì kho đã đầy!");
//                wait();
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                System.out.println("Thread bị gián đoạn.");
//            }
//        }
//
//        // Nhập hàng
//        inventory += quantity;
//        System.out.println(Thread.currentThread().getName() + " nhập " + quantity + " sản phẩm. Số lượng hiện tại: " + inventory);
//        System.out.println();
//        notifyAll(); // Thông báo cho các thread đang chờ
//    }
//
//    public synchronized void removeStock(int quantity) {
//        // Chờ nếu hàng trong kho không đủ
//        while (quantity > inventory) {
//            try {
//                System.out.println(Thread.currentThread().getName() + " chờ vì kho không đủ "+quantity +" hàng để xuất!");
//                wait();
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                System.out.println("Thread bị gián đoạn.");
//            }
//        }
//
//        // Xuất hàng
//        inventory -= quantity;
//        System.out.println(Thread.currentThread().getName() + " xuất " + quantity + " sản phẩm. Số lượng còn lại: " + inventory);
//        System.out.println();
//        notifyAll(); // Thông báo cho các thread đang chờ
//    }
//}
//
//@SpringBootTest
//class BlogAppApplicationTests {
//
//    @Test
//    void contextLoads() throws IOException {
//        Random random = new Random();
//        Warehouse warehouse = new Warehouse();
//
//        // Thread nhập hàng
//        Thread addStock = new Thread(() -> {
//            for (int i = 0; i < 10; i++) {
//                int quantity = random.nextInt(10) + 1;
//                warehouse.addStock(quantity);
//                try {
//                    Thread.sleep(100); // Tạm dừng để mô phỏng quá trình nhập hàng
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }
//            }
//        }, "Thread-Nhập");
//
//        // Thread xuất hàng
//        Thread removeStock = new Thread(() -> {
//            for (int i = 0; i < 10; i++) {
//                int quantity = random.nextInt(20) + 1;
//                warehouse.removeStock(quantity);
//                try {
//                    Thread.sleep(150); // Tạm dừng để mô phỏng quá trình xuất hàng
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }
//            }
//        }, "Thread-Xuất");
//
//        // Bắt đầu các thread
//        addStock.start();
//        removeStock.start();
//
//        // Đợi các thread hoàn thành
//        try {
//            addStock.join();
//            removeStock.join();
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//}
