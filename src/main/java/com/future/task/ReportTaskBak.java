package com.future.task;

import com.future.dao4ora.po.GeAlipayAirinfo;
import com.future.util.ReportGenerator;
import com.future.util.ReportMailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import javax.mail.MessagingException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhengming on 17/10/1.
 */
@Component
public class ReportTaskBak {
    private static Logger LOGGER = LogManager.getLogger(ReportTaskBak.class);
    @Value("${reportDir}")
    private String reportDir;
    @Value("${sqlReadPageSize}")
    private int sqlReadPageSize;
    @Value("${maxPageNumPerWorkBook}")
    private int maxPageNumPerWorkBook;

    @Autowired
    ReportMailSender reportMailSender;
    @Autowired
    ReportGenerator reportGenerator;


    public ReportTaskBak()
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
        GeAlipayAirinfo geAlipayAirinfo1 = new GeAlipayAirinfo();
        GeAlipayAirinfo geAlipayAirinfo2 = new GeAlipayAirinfo();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        geAlipayAirinfo1.setProposalno("no1");
        geAlipayAirinfo1.setNum((short) 3);
        geAlipayAirinfo1.setStatus("3");
        geAlipayAirinfo2.setProposalno("no2");
        try {
            geAlipayAirinfo1.setMakedate(sf.parse(sf.format(new Date())));
            geAlipayAirinfo2.setMakedate(sf.parse(sf.format(new Date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List objects = new LinkedList<Object>();
        objects.add(geAlipayAirinfo1);
        objects.add(geAlipayAirinfo2);
        List<String> columnkeys = new LinkedList<String>();
        columnkeys.add("proposalno");
        columnkeys.add("makedate");
        columnkeys.add("num");
        columnkeys.add("serialno");
        columnkeys.add("status");

        try {
            reportGenerator.genWorkBook(filePath,"test.xls","testsheet",objects,columnkeys);
        } catch (InvocationTargetException e) {
            LOGGER.error("genWorkBook,e:{}",e.getMessage());
        } catch (NoSuchMethodException e) {
            LOGGER.error("genWorkBook,e:{}",e.getMessage());
        } catch (NoSuchFieldException e) {
            LOGGER.error("genWorkBook,e:{}",e.getMessage());
        } catch (IllegalAccessException e) {
            LOGGER.error("genWorkBook,e:{}",e.getMessage());
        } catch (IOException e) {
            LOGGER.error("genWorkBook,e:{}",e.getMessage());
        }
        try {
            reportMailSender.send(filePath,"test.xls");
        } catch (MessagingException e) {
            LOGGER.error("file send failed,e:{}",e.getMessage());
        }
        LOGGER.info("file send succeed");

    }

}
