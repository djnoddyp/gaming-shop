package me.pnodder.gateway.messaging;

public interface MessageProducer {

    void produce(String topic, String message);

    void close();
}
