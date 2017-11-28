package com.example.xieqe.test001.SQLite;

/**
 * Created by xieqe on 2017/9/15.
 */

public class Order {
    public int Id;
    public String customName;
    public int price;
    public String country;

    @Override
    public String toString() {
        return "Order{" +
                "Id=" + Id +
                ", customName='" + customName + '\'' +
                ", price=" + price +
                ", country='" + country + '\'' +
                '}';
    }
}
