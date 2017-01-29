package com.future.controller;

import com.future.entity.WebLogin;
import com.future.entity.WebUser;
import com.future.entity.message.LoginInfo;
import com.future.service.UserService;
import org.apache.commons.lang.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by zhengming on 17/1/27.
 */

@Controller
public class MsgController {
private static Logger LOGGER = LogManager.getLogger(MsgController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("msgProducer")
    public ModelAndView getMsg(HttpServletRequest request) {
        LOGGER.info("spring mvc begin， HttpServletRequest:{}", request);
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(ContextLoader.getCurrentWebApplicationContext().getServletContext());
        LoginInfo loginInfo = (LoginInfo) webApplicationContext.getBean("loginInfo");
        loginInfo.setLoginTime(new Date());
        LOGGER.info("loginInfo:{}", loginInfo.getWebLogin());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", loginInfo.getWebLogin());
        modelAndView.setViewName("helloConsumer");
        return modelAndView;
    }
}
