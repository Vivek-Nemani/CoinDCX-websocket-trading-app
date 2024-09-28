package com.coindcx.tradingapp;

import com.coindcx.tradingapp.model.MarketData;
import com.coindcx.tradingapp.model.OrderPayload;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import java.net.URI;

@Slf4j
@ClientEndpoint
public class CoinDCXWebSocketClient {

    private static final String COINDCX_WEBSOCKET_URL = "wss://ws.coindcx.com"; // Replace with actual URL

    private Session session;

    public CoinDCXWebSocketClient() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(COINDCX_WEBSOCKET_URL));
        } catch (Exception e) {
            log.error("Error connecting to WebSocket: {}", e.getMessage());
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        log.info("WebSocket connection opened.");
        // Subscribe to market data for a specific currency pair
        String subscribeMessage = "{\"id\": 1, \"method\": \"subscribed\", \"params\": {\"channels\": [\"ticker.BTC_USDT\"]}}";
        sendMessage(subscribeMessage);
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("Received message: {}", message);
        MarketData marketData = parseMarketData(message);
        if (marketData != null) {
            // Display or process the market data
            log.info("Market Data: {}", marketData);
            // Check if conditions are met for buying/selling
            checkTriggerPrice(marketData);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        log.info("WebSocket connection closed: {}", closeReason);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("WebSocket error: {}", throwable.getMessage());
    }

    private void sendMessage(String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("Error sending message: {}", e.getMessage());
        }
    }

    private MarketData parseMarketData(String message) {
        try {
            JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();
            JsonObject params = jsonObject.getAsJsonObject("params");

            MarketData marketData = new MarketData();
            marketData.setCurrencyPair(params.get("currency_pair").getAsString());
            marketData.setLastPrice(params.get("last_price").getAsDouble());
            marketData.setHighPrice(params.get("high_price").getAsDouble());
            marketData.setLowPrice(params.get("low_price").getAsDouble());
            marketData.setVolume(params.get("volume").getAsDouble());

            return marketData;
        } catch (Exception e) {
            log.error("Error parsing market data: {}", e.getMessage());
            return null; // Return null if parsing fails
        }
    }

    private void checkTriggerPrice(MarketData marketData) {
        // Example trigger price and order amount (replace these with user inputs)
        double triggerPrice = 30000; // This should be dynamic based on user input
        double orderAmount = 0.01;    // Example amount to buy/sell

        if (marketData.getLastPrice() <= triggerPrice) {
            OrderPayload buyOrder = new OrderPayload("placeOrder", marketData.getCurrencyPair(), marketData.getLastPrice(), orderAmount, "buy");
            log.info("Prepared Buy Order: {}", buyOrder);
            // Optionally send the buy order to the API
        } else if (marketData.getLastPrice() >= triggerPrice) {
            OrderPayload sellOrder = new OrderPayload("placeOrder", marketData.getCurrencyPair(), marketData.getLastPrice(), orderAmount, "sell");
            log.info("Prepared Sell Order: {}", sellOrder);
            // Optionally send the sell order to the API
        }
    }
}
