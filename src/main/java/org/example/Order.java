package org.example;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
public class Order {
    private Date date;
    private double amount;
    private String id;
    private int customer_id;

    @Override
    public String toString() {
        return "Order{" +
                "date=" + date +
                ", amount=" + amount +
                ", id='" + id + '\'' +
                ", customer_id=" + customer_id +
                '}';
    }
}
