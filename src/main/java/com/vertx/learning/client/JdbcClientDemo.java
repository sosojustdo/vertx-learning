package com.vertx.learning.client;

import java.util.concurrent.CountDownLatch;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;

/**
 * Description:jdbc client
 * All Rights Reserved.
 * @version 1.0  2018年7月8日 下午1:32:44  by 代鹏（daipeng.456@gmail.com）创建
 */
public class JdbcClientDemo extends AbstractVerticle {
    
    private Vertx vertx = Vertx.vertx();
    
    private JDBCClient getJdbcClientInstance() {
        JsonObject config = new JsonObject()
                .put("jdbcUrl", "jdbc:mysql://192.168.10.21:3306/wx_business?useSSL=true&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&rewriteBatchedStatements=true&useServerPrepStmts=true&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull")
                .put("username", "crmadmin")
                .put("password", "1qaz@WSX")
                .put("max_pool_size", 50)
                .put("initial_pool_size", 5)
                .put("min_pool_size", 10)
                .put("max_statements", 10)
                .put("max_statements_per_connection", 30)
                .put("max_idle_time", 600000)
                .put("provider_class", "io.vertx.ext.jdbc.spi.impl.HikariCPDataSourceProvider")
                .put("driver_class", "com.mysql.jdbc.Driver");
        return JDBCClient.createShared(vertx, config);
    }
    
    private SQLConnection getSQLConnection(JDBCClient jdbcClient) {
        jdbcClient.getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection connection = res.result();
                return connection;
            }else {
                // 获取连接失败 - 处理失败的情况
            }
        });
        return null;
    }
    
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        
        
        
        JdbcClientDemo verticle = new JdbcClientDemo();
        verticle.start();
        JDBCClient jdbcClient = verticle.getJdbcClient();
        System.out.println(jdbcClient);
        jdbcClient.getConnection(handler->{
            handler.failed();
        });
        
        
        
        latch.await();
    }

}
