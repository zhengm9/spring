package com.future.controller;

import com.future.domain.User;
import com.future.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by zhengming on 17/1/27.
 */

@Controller
public class MyController {
//    private static final Logger LOGGER = Logger.getLogger(MyController.class);
private static Logger LOGGER = LogManager.getLogger(MyController.class);

    @Autowired
    private UserService userService;

    @RequestMapping({"/hello", "lj", "/"})
    public ModelAndView hello(HttpServletRequest request) {
        LOGGER.fatal("fatal");
        LOGGER.error("error");
        LOGGER.info("info");
        LOGGER.debug("debug");
        LOGGER.trace("trace");
        LOGGER.error("test");
        LOGGER.info("spring mvc begin:{}", request.getParameter("u"));
        LOGGER.info("class loader:{}"+this.getClass().getClassLoader().getClass().getSimpleName());
        String path="WEB-INF/applicationContext.xml";

        XmlWebApplicationContext ctx = new XmlWebApplicationContext();
        ctx.setConfigLocation(path);
        ctx.setServletContext(ContextLoader.getCurrentWebApplicationContext().getServletContext());
        ctx.refresh();
        LOGGER.info( "ctx bean:"+ctx.getBean("WebUser").getClass().getSimpleName());
        System.out.println("hello");
        User user = new User();
        user.setLastIp(request.getLocalAddr());
        user.setLastVisit(new Date());
        user.setUserName(request.getParameter("u"));
        user.setCredits(10);
//        userService.loginSuccess(user);
        request.getSession().setAttribute("user", user);
        return new ModelAndView("main");
    }
}
