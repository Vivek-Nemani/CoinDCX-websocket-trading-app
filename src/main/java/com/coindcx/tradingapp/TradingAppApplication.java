package com.coindcx.tradingapp;

import com.coindcx.tradingapp.ui.TradingUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TradingAppApplication implements CommandLineRunner {

    @Autowired
    private WebSocketClient webSocketClient;

    @Autowired
    private TradingUI tradingUI;

    public static void main(String[] args) {
        SpringApplication.run(TradingAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String wsUrl = "wss://stream.coindcx.com/some-endpoint"; // Replace with actual URL
        webSocketClient.connect(wsUrl);

        tradingUI.start(); // Start the UI for user input
    }
}
