package com.coindcx.tradingapp.ui;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class TradingUI {

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter trigger price: ");
        double triggerPrice = scanner.nextDouble();

        // Other user inputs or actions can be processed here
    }
}
