package com.vertx.learning.threadsafe;

import io.vertx.core.AbstractVerticle;

/**
 * Description: HttpServer
 * All Rights Reserved.
 * @version 1.0  2018年8月1日 下午12:32:16  by 代鹏（daipeng.456@gmail.com）创建
 */
public class MyVerticle extends AbstractVerticle {
    
    private int i = 0;// 属性变量
    
    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(req -> {
            i++;
            req.response().putHeader("content-type", "text/plain").end("Hello World!" + i);
        }).listen(8080);
        
        vertx.createHttpServer().requestHandler(req -> { 
            System.out.println(i);
            req.response().end("" + i);
        }).listen(8081);
    }

    public static void main(String[] args) throws Exception {
        MyVerticle myVerticle = new MyVerticle();
        myVerticle.start();
    }
    
}
