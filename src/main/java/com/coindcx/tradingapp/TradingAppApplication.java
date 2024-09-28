package com.coindcx.tradingapp;

import com.coindcx.tradingapp.ui.TradingUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TradingAppApplication implements CommandLineRunner {

    @Autowired
    private CoinDCXWebSocketClient webSocketClient;

    @Autowired
    private TradingUI tradingUI;

    public static void main(String[] args) {
        SpringApplication.run(TradingAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String wsUrl = "wss://ws.coindcx.com/";
        try {
            webSocketClient.connect(wsUrl); // Call the connect method
            tradingUI.start(); // Start the UI for user input
        } catch (Exception e) {
            System.err.println("Failed to connect to WebSocket: " + e.getMessage());
        }
    }
}


