package com.future.task;

import com.future.util.DateConverter;
import com.future.util.ReportGenerator;
import com.future.util.ReportMailSender;
import com.future.util.SheetUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhengming on 17/10/1.
 */
@Component
public abstract class AbstractReportTask<T> {
    private static Logger LOGGER = LogManager.getLogger(AbstractReportTask.class);

    @Autowired
    ReportMailSender reportMailSender;
    @Autowired
    ReportGenerator reportGenerator;

    @Value("${reportDir}")
    protected String reportDir;
    @Value("${sqlReadPageSize}")
    protected int sqlReadPageSize;
    @Value("${maxPageNumPerWorkBook}")
    protected int maxPageNumPerWorkBook;
    @Value("${sendMailRetries}")
    protected int sendMailRetries;

    protected int sqlCountAll;
    protected String startDay;
    protected String endDay;
    protected String reportName;
    protected List<String> columnkeys;

    public AbstractReportTask()
    {
        LOGGER.info("job init,time is {}", DateConverter.formatDate(new Date(),"yyyy-MM-dd hh:mm:ss"));

    }
//    @Scheduled(cron="0/30 * *  * * ?")
    public void run(String startDay, String endDay, String reportName )
    {
        LOGGER.info("job start,time is {}", new Date());
        this.reportName = reportName;
        this.startDay = startDay;
        this.endDay = endDay;
        String filePath = ContextLoader.getCurrentWebApplicationContext().getServletContext()
                .getRealPath(reportDir);
        LOGGER.info("reportDir:{}, reportName:{}, startDay:{}, endDay:{}", filePath, reportName, startDay, endDay);

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
        List<String> workBookFileNames =  initWorkBookFileName(workBookNum, reportName);
        LOGGER.info("workbookNum:{},pageNum:{},workBookFileNames:{}", workBookNum, sqlPageNum,
                                                                        workBookFileNames);

        int curSqlPage = 1;
        int curWorkBook=1;
        for(;curWorkBook<=workBookNum; curWorkBook++)
        {
            String pathName = filePath+ File.separator+workBookFileNames.get(curWorkBook-1);
            LOGGER.info("file path is:{}",pathName);
            File file = new File(pathName);
            if(file.exists()&&!file.delete())
            {
                LOGGER.error("delete old file failed:{}",pathName);
                return;
            }
            for(;curSqlPage<=curWorkBook*maxPageNumPerWorkBook;curSqlPage++)
            {
                if(curSqlPage>sqlPageNum)break;
                List<T> objects =  getSqlResults(curSqlPage, sqlReadPageSize);
                try {
                    boolean result = reportGenerator.genWorkBook(filePath,workBookFileNames.get(curWorkBook-1),reportName,
                            "reportsheet",(List<Object>)objects,columnkeys);

                    LOGGER.info("genWorkBook result:{}",result);
                    if(!result)return;

                } catch (Exception e) {
                    LOGGER.error("genWorkBook,e:{}",e.getMessage());
                    return;
                }
            }
        }
        LOGGER.info("file generated succeed");


        for(String fileName : workBookFileNames)
        {
            boolean isSendSucceed = false;
            int sendRound = 0;
            while (sendRound<sendMailRetries && !isSendSucceed)
            {
                try {
                    reportMailSender.send(filePath,fileName);
                    isSendSucceed = true;
                } catch (Exception e) {
                    LOGGER.error("file send failed,e:{}",e.getMessage());
                }
                ++sendRound;
            }
        }
        LOGGER.info("file send succeed");

    }

    public List<String> initColumnkeys() {
        Class<T> entityClass = null;
        Type t = getClass().getGenericSuperclass();
        if(t instanceof ParameterizedType){
            Type[] p = ((ParameterizedType)t).getActualTypeArguments();
            entityClass = (Class<T>)p[0];
        }
        LOGGER.debug("entityClass:{}",entityClass.getName());

        List<String> columnkeys = new ArrayList<String>();
        Map<String,String> configPropertyMap = null;
        try {
             configPropertyMap = (Map)SheetUtil.getClassTypeConfigProperty(entityClass, reportName);
        } catch (NoSuchFieldException e) {
            LOGGER.error("getClassTypeConfigProperty,e:{}",e.getMessage());

        } catch (IllegalAccessException e) {
            LOGGER.error("getClassTypeConfigProperty,e:{}",e.getMessage());
        } catch (NoSuchMethodException e) {
            LOGGER.error("getClassTypeConfigProperty,e:{}",e.getMessage());
        } catch (InvocationTargetException e) {
            LOGGER.error("getClassTypeConfigProperty,e:{}",e.getMessage());
        }
        if(configPropertyMap != null)
        {
            for(String key : configPropertyMap.keySet())
            {
                columnkeys.add(key);
            }
        }

        return columnkeys;
    }

    public abstract int getSqlCount();
    public abstract List<T> getSqlResults(int pageNum, int pageSize);
    public List<String> initWorkBookFileName(int workBookNum, String reportName)
    {
        List<String> workBookFileNames = new ArrayList<String>();
        for(int i=0;i<workBookNum;i++)
        {
            workBookFileNames.add(reportName+"-"+startDay+"-"+i+".xls");
        }
        return workBookFileNames;
    }

}
