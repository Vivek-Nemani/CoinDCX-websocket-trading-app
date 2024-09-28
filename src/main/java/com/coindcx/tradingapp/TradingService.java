package com.coindcx.tradingapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TradingService {

    private double triggerPrice;

    public void setTriggerPrice(double price) {
        this.triggerPrice = price;
        log.info("Trigger price set to: {}", price);
    }

    public void handleMarketData(String marketData) {
        // Parse market data (e.g., JSON) and extract price
        double currentPrice = extractPrice(marketData);

        if (currentPrice <= triggerPrice) {
            prepareBuyOrder(currentPrice);
        } else if (currentPrice >= triggerPrice) {
            prepareSellOrder(currentPrice);
        }
    }

    private double extractPrice(String marketData) {
        // Extract the price from market data (assumed to be JSON)
        // Placeholder logic here
        return 0.0;
    }

    private void prepareBuyOrder(double price) {
        log.info("Preparing buy order at price: {}", price);
        // Prepare the buy order payload
    }

    private void prepareSellOrder(double price) {
        log.info("Preparing sell order at price: {}", price);
        // Prepare the sell order payload
    }
}
