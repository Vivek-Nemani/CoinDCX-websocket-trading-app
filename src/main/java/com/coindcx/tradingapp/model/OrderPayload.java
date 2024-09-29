package com.coindcx.tradingapp.model;

public class OrderPayload {
    private String side;
    private String symbol;
    private double price;
    private double quantity;

    public OrderPayload(String side, String symbol, double price, double quantity) {
        this.side = side;
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and setters omitted for brevity
}
