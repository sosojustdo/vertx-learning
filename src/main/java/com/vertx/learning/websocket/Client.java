package com.vertx.learning.websocket;

import io.vertx.core.Vertx;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;

import java.util.UUID;

public class Client extends AbstractVerticle {
    
    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new Client());
    }

    @Override
    public void start() throws Exception {
        for(int i=1; i<=100; i++) {
            HttpClient client = vertx.createHttpClient();

            client.websocket(8081, "localhost", "", websocket -> {
                websocket.handler(data -> System.out.println(data.toString("ISO-8859-1")));
                websocket.writeTextMessage(UUID.randomUUID().toString().substring(0,5) + ":hello from client");
            });
        }
    }

}
