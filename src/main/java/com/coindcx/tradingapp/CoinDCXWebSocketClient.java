package com.coindcx.tradingapp;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.Response;
import okio.ByteString; 
import java.util.concurrent.TimeUnit;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CoinDCXWebSocketClient {

    private static final String API_KEY = "efe25d49807545bd7c614abc568a58f656e73efe2fe93c67";
    private static final String WS_URL = "wss://ws.coindcx.com/";

    private OkHttpClient client;
    private WebSocket webSocket;

    public CoinDCXWebSocketClient() {
        client = new OkHttpClient.Builder()
                .pingInterval(30, TimeUnit.SECONDS)
                .build();
    }

    // Method to start WebSocket connection
    public void connect() {
        Request request = new Request.Builder()
                .url(WS_URL)
                .addHeader("Authorization", API_KEY)  // Add your API key to headers
                .build();

        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                System.out.println("WebSocket Connected");
                subscribeToMarketData(webSocket);
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                System.out.println("Received message: " + text);
                handleMarketData(text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                System.out.println("Received ByteString message: " + bytes.hex());
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                System.out.println("WebSocket connection failed: " + t.getMessage());
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                System.out.println("Closing WebSocket: " + reason);
                webSocket.close(1000, null);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                System.out.println("WebSocket Closed: " + reason);
            }
        });
    }

    // Send subscription request to CoinDCX WebSocket
    private void subscribeToMarketData(WebSocket webSocket) {
        JSONObject subscribeMessage = new JSONObject();
        subscribeMessage.put("id", 1);
        subscribeMessage.put("method", "subscribe");
        subscribeMessage.put("params", new JSONObject()
                .put("channels", new String[]{"market.BTC_USDT"}));  // Example market data

        webSocket.send(subscribeMessage.toString());
        System.out.println("Sent subscription request: " + subscribeMessage.toString());
    }

    // Handle the incoming market data from WebSocket
    private void handleMarketData(String message) {
        try {
            JSONObject jsonObject = new JSONObject(message);
            if (jsonObject.has("params")) {
                JSONObject params = jsonObject.getJSONObject("params");
                System.out.println("Market Data: " + params.toString());

                // Check trigger price logic or further processing...
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error parsing market data.");
        }
    }

    public void close() {
        if (webSocket != null) {
            webSocket.close(1000, "Closing WebSocket");
        }
    }
}