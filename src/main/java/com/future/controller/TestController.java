package com.future.controller;

import com.future.dao.po.ProjectInfo;
import com.future.dao.service.ProjectInfoService;
import com.future.task.ReportTask;
import com.future.task.ReportTask4Mysql;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengming on 17/10/1.
 */
@Controller
public class TestController {
    private static org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(TestController.class);

    @Autowired
    private ReportTask reportTask;

    @Autowired
    private ProjectInfoService projectInfoService;
    @Value("${reportTaskStartOffset}")
    private Long reportTaskStartOffset;
    @RequestMapping("testtask")
    public ResponseEntity<String> test()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        ProjectInfo projectInfo1 = new ProjectInfo();
        ProjectInfo projectInfo2 = new ProjectInfo();
        Map<String, String> map = new HashMap<String, String>();
        try {
            projectInfo1.setRequirementsReceivedDate(simpleDateFormat.parse("2017-07-01"));
            projectInfo2.setRequirementsReceivedDate(simpleDateFormat.parse("2017-07-14"));


//            map.put("startdate",simpleDateFormat.parse("2017-07-01"));
//            map.put("enddate",simpleDateFormat.parse("2017-07-14"));
            map.put("startdate","2017-07-01");
            map.put("enddate","2017-07-14");


        Integer i = this.projectInfoService.countSelectAll(map);
//            Integer i = this.projectInfoService.countSelectAll("2017-07-01","2017-07-14");

            LOGGER.info("projectInfoService.countSelectAll size:{}",i);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date date = new Date();
        Long offsetTime = date.getTime()-reportTaskStartOffset*60*1000;
        String startDay = simpleDateFormat.format(new Date(offsetTime));
        LOGGER.info("start day:{}",startDay);
        return new ResponseEntity<String>(startDay, HttpStatus.OK);


    }

    @RequestMapping("testtask2")
    public ResponseEntity<List<ProjectInfo>> test2(@RequestParam String startDay)
    {
        LOGGER.info("startDay:{}",startDay);
        reportTask.initDayScope(startDay,startDay);
        reportTask.run();
//        return new ResponseEntity<List<ProjectInfo>>(null, HttpStatus.OK);
        return null;

    }
}
