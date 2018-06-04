package com.vertx.learning.helloworld;

import io.vertx.core.Vertx;

public class WebServerStart {
    
    public static void main(String[] args){
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(WebServerVerticle.class.getName());
    }

}
