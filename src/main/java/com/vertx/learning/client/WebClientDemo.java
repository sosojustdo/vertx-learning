package com.vertx.learning.client;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;

public class WebClientDemo {

    private Vertx vertx = Vertx.vertx();

    public WebClient getWebClientInstance() {
        WebClientOptions options = new WebClientOptions().setUserAgent("My-App/1.2.3").setKeepAlive(true);
        WebClient client = WebClient.create(vertx, options);
        return client;
    }

    /**
     * Description: Http get or head request demo
     * @Version1.0 2018年7月8日 下午12:08:05 by 代鹏（daipeng.456@gmail.com）创建
     * @return
     */
    public String requestGetDemo() {
        WebClient client = this.getWebClientInstance();
        if (null != client) {
            client.getAbs("http://ec.web.sgmw.cloud-young.cn/wxapi/openPlatfrom/authorize/getWxAuthorizeBaseInfo").addQueryParam("appId", "wx7fff11837244a6db").send(ar -> {
                if (ar.succeeded()) {
                    // 获取响应
                    HttpResponse<Buffer> response = ar.result();

                    System.out.println("Received response with status code:\n" + response.statusCode() + "\nresponse string:\n" + response.bodyAsString());
                } else {
                    System.out.println("Something went wrong " + ar.cause().getMessage());
                }
            });
        }
        return "";
    }

    public String requestPostDemo() {
        WebClient client = this.getWebClientInstance();
        if (null != client) {
            /** send pojo 
            client.postAbs("")
            .ssl(true)
            .as(BodyCodec.json(User.class))
            .sendJson(new User("Dale", "Cooper"), ar -> {
            if (ar.succeeded()) {
                // do somthing
            });
            **/

            /** send JsonObject
            client.postAbs("")
            .as(BodyCodec.jsonObject())
            .sendJsonObject(new JsonObject()
                .put("firstName", "Dale")
                .put("lastName", "Cooper"), ar -> {
                if (ar.succeeded()) {
                  // do somthing
                }
             });
             **/

            /** send form data
             MultiMap form = MultiMap.caseInsensitiveMultiMap();
             form.set("firstName", "Dale");
             form.set("lastName", "Cooper");
             
             client
             .postAbs("")
             .putHeader("content-type", "multipart/form-data")
             .sendForm(form, ar -> {
                if (ar.succeeded()) {
                  // do somthingk
                }
             });
             */
        }
        return "";
    }

    public static void main(String[] args) {
        WebClientDemo webClientDemo = new WebClientDemo();
        webClientDemo.requestGetDemo();
        // webClientDemo.requestPostDemo();
    }

}
