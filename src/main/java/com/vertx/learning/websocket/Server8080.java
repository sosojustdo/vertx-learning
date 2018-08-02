package com.vertx.learning.websocket;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class Server8080 extends AbstractVerticle {

    private static final String CHAT_CHANNEL = "chat";

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new Server8080());
    }

    public void start() throws Exception {
        vertx.createHttpServer().websocketHandler(handler -> {
            System.out.println("client connected: " + handler.remoteAddress());

            vertx.eventBus().consumer(CHAT_CHANNEL, message -> {
                handler.writeTextMessage((String) message.body());
            });

            handler.textMessageHandler(message -> {
                vertx.eventBus().publish(CHAT_CHANNEL, message);
            });

            handler.closeHandler(message -> {
                System.out.println("client disconnected " + handler.textHandlerID());
            });

        }).listen(8080);
    }
}
