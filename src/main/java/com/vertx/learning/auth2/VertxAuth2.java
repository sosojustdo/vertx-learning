package com.vertx.learning.auth2;

import java.util.UUID;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.auth.oauth2.OAuth2ClientOptions;
import io.vertx.ext.auth.oauth2.OAuth2FlowType;

/**
 * Description:OAuth2 authorize
 * All Rights Reserved.
 * @version 1.0  2018年8月6日 下午7:45:55  by 代鹏（daipeng.456@gmail.com）创建
 */
public class VertxAuth2 extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new VertxAuth2());
    }

    @Override
    public void start() throws Exception {
        String code = UUID.randomUUID().toString();
        
        OAuth2ClientOptions credentials = new OAuth2ClientOptions().setClientID("<client-id>").setClientSecret("<client-secret>").setSite("https://api.weibo.com/oauth2/authorize");

        // Initialize the OAuth2 Library
        OAuth2Auth oauth2 = OAuth2Auth.create(vertx, OAuth2FlowType.AUTH_CODE, credentials);

        // Authorization oauth2 URI
        String authorization_uri = oauth2.authorizeURL(new JsonObject().put("redirect_uri", "http://localhost:8080/callback").put("scope", "<scope>").put("state", "<state>"));

        // Redirect example using Vert.x
        
        
        //response.putHeader("Location", authorization_uri).setStatusCode(302).end();

        JsonObject tokenConfig = new JsonObject().put("code", code).put("redirect_uri", "http://localhost:8081/callback");

        // Callbacks
        // Save the access token
        oauth2.authenticate(tokenConfig, res -> {
            if (res.failed()) {
                System.err.println("Access Token Error: " + res.cause().getMessage());
            } else {
                // Get the access token object (the authorization code is given
                // from the previous step).
                User token = res.result();
                System.out.println(token);
            }
        });

    }

}
