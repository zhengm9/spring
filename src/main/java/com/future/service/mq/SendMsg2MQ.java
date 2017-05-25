package com.future.service.mq;

import com.future.controller.RestfulController;
import com.future.service.UserService;
import com.future.service.mq.producer.MQProducerImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

/**
 * Created by zhengming on 17/5/23.
 */
@Service(value = "sendMsg2MQ")
public class SendMsg2MQ {
    @Autowired
    MQProducerImpl mqProducer;
    private static Logger LOGGER = LogManager.getLogger(RestfulController.class);


    public void send(String key, Object object)
    {
        mqProducer.sendDataToQueue(key, object);

    }
}
