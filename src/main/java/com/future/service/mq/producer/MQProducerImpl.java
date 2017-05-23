package com.future.service.mq.producer;

import com.future.controller.RestfulController;
import com.future.facade.MQProducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhengming on 17/5/23.
 */
@Service
public class MQProducerImpl  {
    @Autowired
    private AmqpTemplate amqpTemplate;
    private static Logger LOGGER = LogManager.getLogger(RestfulController.class);

    /* (non-Javadoc)
     * @see com.stnts.tita.rm.api.mq.MQProducer#sendDataToQueue(java.lang.String, java.lang.Object)
     */
    public void sendDataToQueue(String queueKey, Object object) {
        try {
            amqpTemplate.convertAndSend(queueKey, object);
        } catch (AmqpException e) {
            LOGGER.error(e);
        }

    }
}
