package me.pnodder.gateway.validation;

import io.vertx.ext.web.RoutingContext;

public interface RequestValidator {

    ValidationResponse validate(RoutingContext request);
}
