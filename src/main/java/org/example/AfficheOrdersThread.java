package org.example;

import java.util.ArrayList;
import java.util.List;

public class AfficheOrdersThread extends Thread {
    private final Object key;
    private final FileServices fileServices = new FileServices();

    public AfficheOrdersThread(Object key) {
        this.key = key;
    }

    public void run() {
        synchronized (key) {
            try {
                // Wait until notified by the other thread
                key.wait();
            } catch (InterruptedException e) {
                // If interrupted, restore the interrupt status and exit
                Thread.currentThread().interrupt();
                System.err.println("Thread was interrupted: " + e.getMessage());
                return;  // Exit the thread gracefully
            }

            // Fetch orders from the output JSON file
            List<Order> orders = fileServices.getOrders("src/main/resources/Data/output.json");

            // Print the orders
            if (orders != null) {
                for (Order order : orders) {
                    System.out.println(order);  // Assuming Order has a useful toString() method
                }
            } else {
                System.err.println("Failed to retrieve orders or orders list is empty.");
            }
        }
    }
}
