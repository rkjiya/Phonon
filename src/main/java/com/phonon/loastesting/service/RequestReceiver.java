package com.phonon.loastesting.service;

import com.phonon.loastesting.model.RequestData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class RequestReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestReceiver.class);


//    public void receiveMessage(RequestData requestData){
//        LOGGER.info("Request received: {}", requestData);
//    }

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public void receive(String  message){
        LOGGER.info("Request received: {}", message);
        countDownLatch.countDown();
    }


    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }
}
