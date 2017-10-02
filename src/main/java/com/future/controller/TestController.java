package com.future.controller;

import com.future.task.ReportTask;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by zhengming on 17/10/1.
 */
@Controller
public class TestController {
    @RequestMapping("testtask")
    public void test()
    {
        WebApplicationContext webApplicationContext =  ContextLoader.getCurrentWebApplicationContext();
        ReportTask myTask = (ReportTask)webApplicationContext.getBean("reportTask");
        System.out.println("myTask bean:"+myTask);
    }
}
