package com.vertx.learning.helloworld;

import io.vertx.core.AbstractVerticle;

public class WebServerStart extends AbstractVerticle{
    
    @Override
    public void start() throws Exception {
        vertx.deployVerticle(WebServerVerticle.class.getName());
    }
    
    public static void main(String[] args) throws Exception{
        WebServerStart server = new WebServerStart();
        server.start();
    }

}
