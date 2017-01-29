package com.future.controller;

import com.future.entity.WebLogin;
import com.future.entity.WebUser;
import com.future.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhengming on 17/1/27.
 */

@Controller
public class MyController {
private static Logger LOGGER = LogManager.getLogger(MyController.class);

    @Autowired
    private UserService userService;

    @RequestMapping({"/hello", "lj", "/"})
    public ModelAndView hello(HttpServletRequest request) {
        LOGGER.info("spring mvc begin:{}", request.getParameter("u"));
        //1.通过XmlWebApplicationContext取得BeanFactory
        String path="WEB-INF/applicationContext.xml";
        XmlWebApplicationContext ctx = new XmlWebApplicationContext();
        ctx.setConfigLocation(path);
        //1.1通过ContextLoader取得ServletContext
        ctx.setServletContext(ContextLoader.getCurrentWebApplicationContext().getServletContext());
        ctx.refresh();
        WebUser webUser = (WebUser) ctx.getBean("WebUser");
        LOGGER.info( "web ctx webUser:"+webUser);
        LOGGER.info( "web ctx webUser:"+webUser.getUserName());
        LOGGER.info( "web ctx webUser:"+webUser.getPasswd());
        WebLogin webLogin = (WebLogin) ctx.getBean("WebLogin");
//                WebLogin webLogin = (WebLogin) ctx.getBean("webLogin", WebLogin.class);

        LOGGER.info("new ctx webLogin:{}", webLogin);
        request.getSession().setAttribute("user", webUser);
        return new ModelAndView("main");
    }
}
