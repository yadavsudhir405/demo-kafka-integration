package com.example.demo;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * Created by sudhiry on 1/20/18.
 */

public class CountDownLatchHandler implements MessageHandler {

    private final CountDownLatch countDownLatch = new CountDownLatch(10);

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        countDownLatch.countDown();
    }
}
