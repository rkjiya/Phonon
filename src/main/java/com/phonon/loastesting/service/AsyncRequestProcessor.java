package com.phonon.loastesting.service;

import com.phonon.loastesting.model.RequestData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class AsyncRequestProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncRequestProcessor.class);

    @Value("${rabbitmq-phonon-exchange}")
    private String topicExchangeName;

    @Value("${rabbitmq-phonon-queue}")
    private String queueName;

    @Value("${rabbitmq-phonon-routingKey}")
    private String routingKey;

    private RabbitTemplate rabbitTemplate;


    public AsyncRequestProcessor(@Qualifier("template") RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(RequestData requestData){
        LOGGER.info("Rbbitmq drtails: "+topicExchangeName+ " "+routingKey+" "+queueName);
        rabbitTemplate.convertAndSend(topicExchangeName, routingKey, requestData);
        LOGGER.info("Request Sent for processing : {}", requestData);
    }





}
