package com.future.controller;

import com.future.domain.User;
import com.future.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by zhengming on 17/1/27.
 */

@Controller
public class MyController {
//    private static Logger LOGGER = Logger.getLogger(MyController.class);
    @Autowired
    private UserService userService;

    @RequestMapping({"/hello", "lj", "/"})
    public ModelAndView hello(HttpServletRequest request) {
//        LOGGER.info("spring mvc begin");
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
