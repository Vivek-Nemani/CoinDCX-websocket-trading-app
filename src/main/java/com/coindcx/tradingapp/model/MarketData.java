package com.coindcx.tradingapp.model;

import lombok.Data;

@Data
public class MarketData {
    private String currencyPair;
    private double lastPrice;
    private double highPrice;
    private double lowPrice;
    private double volume;
}
