package com.vertx.learning.threadsafe;

import io.vertx.core.AbstractVerticle;

public class MyVerticle extends AbstractVerticle {
    
    private static volatile MyVerticle instance;
    
    private MyVerticle() {}
    
    public static MyVerticle getInstance() {
        if(null == instance) {
            synchronized (MyVerticle.class) {
                if(null == instance) {
                    instance = new MyVerticle();
                }
            }
        }
        return instance;
    }
    
    int i = 0;// 属性变量

    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(req -> {
            i++;
            req.response().end();// 要关闭请求，否则连接很快会被占满
        }).listen(8080);

        vertx.createHttpServer().requestHandler(req -> {
            System.out.println(i);
            req.response().end("" + i);
        }).listen(8081);
    }

}
