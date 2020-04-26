package me.pnodder.gateway;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.*;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import me.pnodder.gateway.messaging.KafkaMessageProducer;
import me.pnodder.gateway.messaging.MessageProducer;
import me.pnodder.gateway.messaging.MessagingConstants;
import me.pnodder.gateway.validation.InventoryRequestValidator;
import me.pnodder.gateway.validation.ValidationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ApiVerticle extends AbstractVerticle {
    private static final Logger LOG = LoggerFactory.getLogger(ApiVerticle.class);
    private final MessageProducer messageProducer = new KafkaMessageProducer();

    @Override
    public void start(Future<Void> startFuture) {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        router.route(HttpMethod.POST, "/api/inventory").consumes("application/json").handler(this::handleInventory);

        HttpServer server = vertx.createHttpServer();
        server.requestHandler(router).listen(9001, res -> {
            if (res.succeeded()) {
                System.out.println("Server listening on port 9001");
                startFuture.complete();
            } else {
                startFuture.fail(res.cause());
            }
        });
    }

    @Override
    public void stop(Promise<Void> stopPromise) {
        messageProducer.close();
    }

    private void handleInventory(RoutingContext routingContext) {
        InventoryRequestValidator validator = new InventoryRequestValidator();
        ValidationResponse response = validator.validate(routingContext);

        if (response.isJsonValid()) {
            LOG.info("JSON validated");
            messageProducer.produce(MessagingConstants.INVENTORY_TOPIC, routingContext.getBodyAsString());
        }
    }

}
