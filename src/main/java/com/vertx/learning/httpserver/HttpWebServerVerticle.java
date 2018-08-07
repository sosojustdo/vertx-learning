package com.vertx.learning.httpserver;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;

/**
 * Description:Http WebServer
 * All Rights Reserved.
 * @version 1.0  2018年8月1日 下午12:32:34  by 代鹏（daipeng.456@gmail.com）创建
 */
public class HttpWebServerVerticle extends AbstractVerticle {
    
    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new HttpWebServerVerticle());
    }

    public void start() {
        // simple server
        vertx.createHttpServer().requestHandler(req -> {
            req.response().putHeader("content-type", "text/plain").end("Hello World!, 123");
        }).listen(8080);

        // route server
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        // restful post
        router.post("/post/:param1/:param2").handler(this::handlePost);
        // restful get
        router.get("/get/:param1/:param2").handler(this::handleGet);
        vertx.createHttpServer().requestHandler(router::accept).listen(8081);

        // support session
        Router router1 = Router.router(vertx);
        router1.route("/session").handler(SessionHandler.create(LocalSessionStore.create(vertx)));
        router1.route("/session").handler(routingContext -> {
            // get session by routingContext
            Session session = routingContext.session();
            Integer count = session.get("count");
            if (null == count)
                count = 0;
            count++;
            session.put("count", count);
            routingContext.response().putHeader("content-type", "text/html").end("total visit count:" + session.get("count"));
        });
        vertx.createHttpServer().requestHandler(router1::accept).listen(8082);

        // support cookie
        Router router2 = Router.router(vertx);
        router2.route("/cookie").handler(CookieHandler.create());
        router2.route("/cookie").handler(routingContext -> {
            Cookie cookie = routingContext.getCookie("testCookie");
            Integer c = 0;
            if (cookie != null) {
                String count = cookie.getValue();
                try {
                    c = Integer.valueOf(count);
                } catch (Exception e) {
                    c = 0;
                }
                c++;
            }
            routingContext.addCookie(Cookie.cookie("testCookie", String.valueOf(c)));
            routingContext.response().putHeader("content-type", "text/html").end("total visit count:" + c);
        });
        vertx.createHttpServer().requestHandler(router2::accept).listen(8083);

    }

    /**
     * Description: post请求处理
     * @Version1.0 2018年8月7日 上午11:52:54 by 代鹏（daipeng.456@gmail.com）创建
     * @param context
     */
    private void handlePost(RoutingContext context) {
        String param1 = context.request().getParam("param1");
        String param2 = context.request().getParam("param2");

        if (mutliteStringParamsIsBlank(param1, param2)) {
            context.response().setStatusCode(400).end();
        }

        JsonObject obj = new JsonObject();
        obj.put("method", "post").put("param1", param1).put("param2", param2);

        context.response().putHeader("content-type", "application/json").end(obj.encodePrettily());
    }

    /**
     * Description: get请求处理
     * @Version1.0 2018年8月7日 上午11:53:28 by 代鹏（daipeng.456@gmail.com）创建
     * @param context
     */
    private void handleGet(RoutingContext context) {
        String param1 = context.request().getParam("param1");
        String param2 = context.request().getParam("param2");

        if (mutliteStringParamsIsBlank(param1, param2)) {
            context.response().setStatusCode(400).end();
        }
        JsonObject obj = new JsonObject();
        obj.put("method", "get").put("param1", param1).put("param2", param2);

        context.response().putHeader("content-type", "application/json").end(obj.encodePrettily());
    }

    /**
     * Description:判断多个字符串参数是否均为空
     * @Version1.0 2018年8月7日 上午11:51:33 by 代鹏（daipeng.456@gmail.com）创建
     * @param strings
     * @return
     */
    private boolean mutliteStringParamsIsBlank(String... strings) {
        for (String str : strings) {
            if (null != str && str.length() > 0) {
                return false;
            }
        }
        return true;
    }

}
