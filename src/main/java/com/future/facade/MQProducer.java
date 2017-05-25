package com.future.facade;


/**
 * Created by zhengming on 17/5/23.
 */
public interface MQProducer {
    /**
     * send message to queue selected
     * @param queueKey
     * @param object
     */
    public void sendDataToQueue(String queueKey, Object object);

}
