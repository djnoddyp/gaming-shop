package me.pnodder.gateway;

import io.vertx.core.Vertx;
import io.vertx.core.logging.SLF4JLogDelegateFactory;

public class ApiGatewayApplication {

    public static void main(String[] args) {
        System.setProperty("vertx.logger-delegate-factory-class-name", SLF4JLogDelegateFactory.class.getName());
        Vertx.vertx().deployVerticle(new ApiVerticle());
    }
}
