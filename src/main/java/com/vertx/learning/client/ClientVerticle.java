package com.vertx.learning.client;

import java.util.concurrent.CountDownLatch;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.web.client.WebClient;

public class ClientVerticle extends AbstractVerticle {
    
    private HttpClient httpClient;
    private WebClient webClient;
    private JDBCClient jdbcClient;
    
    public HttpClient getHttpClient() {
        return httpClient;
    }

    public WebClient getWebClient() {
        return webClient;
    }

    public JDBCClient getJdbcClient() {
        return jdbcClient;
    }

    public void start(){
        Vertx vertx = Vertx.vertx();
          //创建客户端
        httpClient = vertx.createHttpClient();

        webClient = WebClient.wrap(httpClient);

        JsonObject config = new JsonObject()
                .put("jdbcUrl", "jdbc:mysql://192.168.10.21:3306/wx_business?useSSL=true&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&rewriteBatchedStatements=true&useServerPrepStmts=true&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull")
                .put("maximumPoolSize", 30)
                .put("username", "crmadmin")
                .put("password", "1qaz@WSX")
                .put("provider_class", "io.vertx.ext.jdbc.spi.impl.HikariCPDataSourceProvider");

        jdbcClient = JDBCClient.createShared(vertx, config);
    }
    
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        ClientVerticle verticle = new ClientVerticle();
        verticle.start();
        JDBCClient jdbcClient = verticle.getJdbcClient();
        System.out.println(jdbcClient);
        jdbcClient.getConnection(handler->{
            handler.failed();
        });
        latch.await();
    }

}
