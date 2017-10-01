package com.future.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by zhengming on 17/10/1.
 */
@Component
public class MyTask {
    private static Logger LOGGER = LogManager.getLogger(MyTask.class);
    public MyTask()
    {
        LOGGER.info("job init,time is {}", new Date());

    }
    @Scheduled(cron="0/5 * *  * * ?")
    public void run()
    {
        LOGGER.info("job start,time is {}", new Date());
    }

}
