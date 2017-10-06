package com.future.task;

import com.future.util.DateConverter;
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
public abstract class AbstractReportTask<T> {
    private static Logger LOGGER = LogManager.getLogger(AbstractReportTask.class);

    @Value("${reportDir}")
    protected String reportDir;
    @Value("${sqlReadPageSize}")
    protected int sqlReadPageSize;
    @Value("${maxPageNumPerWorkBook}")
    protected int maxPageNumPerWorkBook;

    protected int sqlCountAll;
    protected List<String> columnkeys;

    @Autowired
    ReportMailSender reportMailSender;
    @Autowired
    ReportGenerator reportGenerator;


    public AbstractReportTask()
    {
        LOGGER.info("job init,time is {}", DateConverter.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss"));

    }
//    @Scheduled(cron="0/30 * *  * * ?")
    public void run()
    {
        LOGGER.info("job start,time is {}", new Date());
        String filePath = ContextLoader.getCurrentWebApplicationContext().getServletContext()
                .getRealPath(reportDir);
        LOGGER.info("reportDir:{},filename:test.xls", filePath);

        if(columnkeys == null || columnkeys.isEmpty())
        {
            columnkeys = initColumnkeys();
        }

        sqlCountAll = getSqlCount();
        if(sqlCountAll == 0)
        {
            return;
        }

        int workBookNum = sqlCountAll/(sqlReadPageSize*maxPageNumPerWorkBook)+1;
        int sqlPageNum = sqlCountAll/sqlReadPageSize+1;
        List<String> workBookFileNames =  initWorkBookFileName(workBookNum);
        LOGGER.info("workbookNum:{},pageNum:{},workBookFileNames:{}", workBookNum, sqlPageNum,
                                                                        workBookFileNames);

        int curSqlPage = 1;
        int curWorkBook=1;
        for(;curWorkBook<=workBookNum; curWorkBook++)
        {
            for(;curSqlPage<=curWorkBook*maxPageNumPerWorkBook;curSqlPage++)
            {
                List<T> objects =  getSqlResults(curSqlPage, sqlReadPageSize);
                try {
                    boolean result = reportGenerator.genWorkBook(filePath,workBookFileNames.get(curWorkBook-1),
                            "testsheet",(List<Object>)objects,columnkeys);

                    LOGGER.info("genWorkBook result:{}",result);
                    if(!result)return;

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
            }
        }



        try {
            reportMailSender.send(filePath,"test.xls");
        } catch (MessagingException e) {
            LOGGER.error("file send failed,e:{}",e.getMessage());
        }
        LOGGER.info("file send succeed");

    }

    public abstract int getSqlCount();
    public abstract List<T> getSqlResults(int pageNum, int pageSize);
    public abstract List<String> initColumnkeys();
    public abstract List<String> initWorkBookFileName(int workBookNum);

}