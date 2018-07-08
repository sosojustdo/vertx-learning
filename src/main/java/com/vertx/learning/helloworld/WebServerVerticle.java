package com.vertx.learning.helloworld;

import io.vertx.core.AbstractVerticle;

public class WebServerVerticle extends AbstractVerticle {

    public void start() {
        vertx.createHttpServer().requestHandler(req -> {
            req.response().putHeader("content-type", "text/plain").end("Hello World!, 123");
        }).listen(8080);
    }

}
