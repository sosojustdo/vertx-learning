package com.vertx.learning.helloworld;

import io.vertx.core.AbstractVerticle;

/**
 * Description:WebServer
 * All Rights Reserved.
 * @version 1.0  2018年8月1日 下午12:32:34  by 代鹏（daipeng.456@gmail.com）创建
 */
public class WebServerVerticle extends AbstractVerticle {

    public void start() {
        vertx.createHttpServer().requestHandler(req -> {
            req.response().putHeader("content-type", "text/plain").end("Hello World!, 123");
        }).listen(8080);
    }

}
