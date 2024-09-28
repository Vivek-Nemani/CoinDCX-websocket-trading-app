package com.coindcx.tradingapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderPayload {
    private String method;
    private String currencyPair;
    private double price;
    private double amount;
    private String side; // "buy" or "sell"
}
