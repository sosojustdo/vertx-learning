package com.vertx.learning.client;

import java.util.List;

import org.junit.Assert;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;

/**
 * Description:jdbc client
 * All Rights Reserved.
 * @version 1.0  2018年7月8日 下午1:32:44  by 代鹏（daipeng.456@gmail.com）创建
 */
public class JdbcClientDemo extends AbstractVerticle {
    
    private Vertx vertx = Vertx.vertx();

    @Override
    public void start() throws Exception {
        JDBCClient jdbcClient = getJdbcClientInstance(vertx);
        Assert.assertNotNull(jdbcClient);
        
        JdbcOperator op = new JdbcOperator(jdbcClient);
        op.simpleQuery();
    }

    /**
     * Description:get JDBCClient
     * @Version1.0 2018年8月1日 下午12:17:47 by 代鹏（daipeng.456@gmail.com）创建
     * @param vertx
     * @return
     */
    private JDBCClient getJdbcClientInstance(Vertx vertx) {
        JsonObject config = new JsonObject().put("jdbcUrl",
                "jdbc:mysql://192.168.10.21:3306/wx_business?useSSL=true&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&rewriteBatchedStatements=true&useServerPrepStmts=true&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull")
                .put("username", "crmadmin").put("password", "1qaz@WSX").put("max_pool_size", 50).put("initial_pool_size", 5).put("min_pool_size", 10).put("max_statements", 10)
                .put("max_statements_per_connection", 30).put("max_idle_time", 600000).put("provider_class", "io.vertx.ext.jdbc.spi.impl.HikariCPDataSourceProvider")
                .put("driver_class", "com.mysql.jdbc.Driver");
        return JDBCClient.createShared(vertx, config);
    }

    /**
     * Description: get SQLConnection
     * @Version1.0 2018年8月1日 下午12:17:57 by 代鹏（daipeng.456@gmail.com）创建
     * @param jdbcClient
     * @return
     */
    private SQLConnection getSQLConnection(JDBCClient jdbcClient) {
        SQLConnection connection = null;
        jdbcClient.getConnection(new Handler<AsyncResult<SQLConnection>>() {

            @Override
            public void handle(AsyncResult<SQLConnection> event) {
                if (event.succeeded()) {
                    event.result();
                }
            }
        });
        
        jdbcClient.getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection connection1 = res.result();
                
            } else {
                // TODO 获取连接失败 - 处理失败的情况
            }
        });
        return connection;
    }

    class JdbcOperator {

        private JDBCClient jdbcClient;

        public JdbcOperator(JDBCClient jdbcClient) {
            this.jdbcClient = jdbcClient;
        }
        
        /**
         * link:https://vertx.io/docs/vertx-sql-common/java/#_the_sql_connection
         * Description:setAutoCommit、batch、batchWithParams、batchCallableWithParams
         * @Version1.0 2018年8月1日 下午9:04:53 by 代鹏（daipeng.456@gmail.com）创建
         */
        public void setAutoCommit() {
            jdbcClient.getConnection(res -> {
                if (res.succeeded()) {
                    SQLConnection connection = res.result();
                    connection.setAutoCommit(false, res1 -> {
                        if (res1.succeeded()) {
                          // OK!
                        } else {
                          // Failed!
                        }
                      });
                } else {
                    // TODO 获取连接失败 - 处理失败的情况
                }
            });
        }
        
        /**
         * link:https://vertx.io/docs/vertx-sql-common/java/#_the_sql_connection
         * Description: query、queryWithParams、querySingle、querySingleWithParams、update、updateWithParams、call、callWithParams
         * @Version1.0 2018年8月1日 下午8:59:29 by 代鹏（daipeng.456@gmail.com）创建
         */
        public void simpleQuery() {
            jdbcClient.query("SELECT * FROM wx_user limit 10", ar -> {
                if (ar.succeeded()) {
                    if (ar.succeeded()) {
                        ResultSet result = ar.result();
                        List<JsonObject> list = result.getRows();
                        System.out.println(list);
                    } else {
                        // Failed!
                    }
                    // NOTE that you don't need to worry about
                    // the connection management (e.g.: close)
                }
            });
        }

    }

    public static void main(String[] args) throws Exception {
        JdbcClientDemo verticle = new JdbcClientDemo();
        verticle.start();
    }

}
