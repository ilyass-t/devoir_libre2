package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class FileServices {
    private final Gson gson;

    public FileServices() {
        // Custom Gson instance with SQL Date adapter
        gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, (com.google.gson.JsonDeserializer<Date>) (json, typeOfT, context) -> {
                    try {
                        return Date.valueOf(json.getAsString());  // Parsing the date string into java.sql.Date
                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException("Failed to parse date: " + json.getAsString(), e);
                    }
                })
                .create();
    }

    public List<Order> getOrders(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            // Parse the root JSON object
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

            // Extract the orders array
            Type orderListType = new TypeToken<List<Order>>() {}.getType();
            return gson.fromJson(jsonObject.get("orders"), orderListType);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the file: " + filePath, e);
        }
    }
    public void writeOrders(List<Order> orders, String path) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            // Read existing orders if the file exists
            List<Order> existingOrders;


            try (FileReader reader = new FileReader(path)) {
                Type ordersListType = new TypeToken<List<Order>>() {}.getType();
                existingOrders = gson.fromJson(reader, ordersListType);
            }
            if (existingOrders == null) {
                existingOrders = new ArrayList<>(); // Initialize to an empty list if null
            }

            // Merge existing orders with new orders
            existingOrders.addAll(orders);

            // Write merged orders back to the file
            try (FileWriter writer = new FileWriter(path)) {
                gson.toJson(existingOrders, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
