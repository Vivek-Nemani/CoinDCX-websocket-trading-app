package com.coindcx.tradingapp.ui;

import com.coindcx.tradingapp.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class TradingUI {

    @Autowired
    private TradingService tradingService;

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter trigger price: ");
        double triggerPrice = scanner.nextDouble();
        tradingService.setTriggerPrice(triggerPrice);

        System.out.println("Listening for real-time market data...");
    }
}