package com.future.task;

import com.future.dao4ora.po.GeAlipayAirinfo;
import com.future.dao4ora.service.GeAlipayAirinfoService;
import com.future.util.DateConverter;
import com.github.pagehelper.PageInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhengming on 17/10/1.
 */
@Component
public class QuartzMain {
    private static Logger LOGGER = LogManager.getLogger(QuartzMain.class);
    @Autowired
    private ReportCancelTask reportCancelTask;
    @Autowired
    private ReportTask reportTask;


    public void runTask()
    {

        Date date = new Date();
        Long offsetTime = date.getTime()-24*60*60*1000;
        String startDay = DateConverter.formatDate(new Date(offsetTime),"yyyy-MM-dd");
        reportTask.run(startDay,startDay,"insure");
        reportCancelTask.run(startDay,startDay,"cancel");
    }

}
