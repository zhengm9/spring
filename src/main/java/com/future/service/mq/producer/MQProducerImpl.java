package com.future.service.mq.producer;

import com.future.controller.RestfulController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

/**
 * Created by zhengming on 17/5/23.
 */
@Service
public class MQProducerImpl  {
//    @Autowired
    private static RabbitTemplate rabbitTemplate = (RabbitTemplate) ContextLoader.getCurrentWebApplicationContext().getBean("amqpTemplate");
    private static Logger LOGGER = LogManager.getLogger(RestfulController.class);

    static {
        rabbitTemplate.setMandatory(true);
        LOGGER.info("rabbitTemplate:{}", rabbitTemplate);

        if(!rabbitTemplate.isConfirmListener())
        {
            LOGGER.info("rabbitTemplate is not confirmListener");
            rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
                public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                    LOGGER.info("correlationData:{}, ack:{}, cause:{}", correlationData, ack, cause);
                }
            });
        }
    }
    /* (non-Javadoc)
     * @see com.stnts.tita.rm.api.mq.MQProducer#sendDataToQueue(java.lang.String, java.lang.Object)
     */
    public void sendDataToQueue(String queueKey, Object object) {
        try {
            rabbitTemplate.convertAndSend(queueKey, object);
//            rabbitTemplate.convertAndSend("errorExchange", queueKey, object);

        } catch (AmqpException e) {
            LOGGER.error(e);
        }

    }
}
