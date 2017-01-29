package com.future.controller;

import com.future.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/29.
 */
@Controller
public class BindingController {
    private static Logger LOGGER = LogManager.getLogger(IndexController.class);

    @RequestMapping("form")
    public ModelAndView user()
    {
        WebApplicationContext webApplicationContext =  ContextLoader.getCurrentWebApplicationContext();
        User user = (User) webApplicationContext.getBean("user");
        ModelAndView modelAndView = new ModelAndView("/mvcbinding/userForm", "user", user);
//        user.setBirthDate(new Date());
        LOGGER.info("user.getBirthDate()", user.getBirthDate());
        modelAndView.addObject("currentTime", user.getBirthDate());
        modelAndView.addObject("genders", User.Gender.values());
        modelAndView.addObject("countries", new String[]{"China", "Foreigner"});

        return modelAndView;
    }

    @RequestMapping("result")
    public ModelAndView processUser(User user)
    {
        LOGGER.info("request received:{}", user);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/mvcbinding/userResult");
        modelAndView.addObject("u", user);

        return modelAndView;
    }

}
