package com.future.service.mq.consumer;

import com.future.controller.RestfulController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

/**
 * Created by zhengming on 17/5/23.
 */
@Component(value = "queueListenter4Order")
public class QueueListenter4Order implements MessageListener {
    private static Logger LOGGER = LogManager.getLogger(RestfulController.class);

    public void onMessage(Message message) {
        LOGGER.info("Order message received:{}", message);
    }
}
