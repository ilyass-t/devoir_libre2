package org.example;


import java.util.ArrayList;
import java.util.List;

public class InsertOrdersThread extends Thread {
    private final DatabaseServices databaseServices = new DatabaseServices();
    private final FileServices fileServices = new FileServices();
    private final Object key;

    public InsertOrdersThread(Object key) {
        this.key = key;
    }

    public void run() {
        while (true) {
            synchronized (key) {
                List<Order> orders1;
                List<Order> ordersOutput = new ArrayList<>();
                List<Order> ordersError = new ArrayList<>();

                try {
                    orders1 = fileServices.getOrders("src/main/resources/Data/input.json");
                } catch (Exception e) {
                    System.err.println("Error reading input file: " + e.getMessage());
                    continue;  // Skip this iteration and try again
                }

                for (Order order : orders1) {
                    if (databaseServices.isCustomerExist(order.getCustomer_id())) {
                        databaseServices.ajouterOrder(order);
                        ordersOutput.add(order);
                    } else {
                        ordersError.add(order);
                    }
                }

                try {
                    fileServices.writeOrders(ordersOutput, "src/main/resources/Data/output.json");
                    fileServices.writeOrders(ordersError, "src/main/resources/Data/error.json");
                } catch (Exception e) {
                    System.err.println("Error writing output files: " + e.getMessage());
                }

                key.notify();  // Notify after processing orders
            }

            try {
                Thread.sleep(60 * 60 * 1000);  // Sleep for an hour before repeating the process
            } catch (InterruptedException e) {
                System.err.println("Thread was interrupted: " + e.getMessage());
                break;  // Exit the loop if the thread is interrupted
            }
        }
    }
}
