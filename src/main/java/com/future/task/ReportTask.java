package com.future.task;

import com.future.util.ReportGenerator;
import com.future.util.ReportMailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import javax.mail.MessagingException;
import java.util.Date;

/**
 * Created by zhengming on 17/10/1.
 */
@Component
public class ReportTask {
    private static Logger LOGGER = LogManager.getLogger(ReportTask.class);
    @Value("${reportDir}")
    private String reportDir;
    @Autowired
    ReportMailSender reportMailSender;

    public ReportTask()
    {
        LOGGER.info("job init,time is {}", new Date());

    }
//    @Scheduled(cron="0/30 * *  * * ?")
    public void run()
    {
        LOGGER.info("job start,time is {}", new Date());
        String filePath = ContextLoader.getCurrentWebApplicationContext().getServletContext()
                .getRealPath(reportDir);
        LOGGER.info("reportDir:{},filename:test.xls", filePath);
        ReportGenerator.genWorkBook(filePath,"test.xls","testsheet");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            reportMailSender.send(filePath,"test.xls");
        } catch (MessagingException e) {
            LOGGER.error("file send failed,e:{}",e.getMessage());
        }
        LOGGER.info("file send succeed");

    }

}
