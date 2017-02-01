package com.future.controller;

import com.future.entity.User;
import com.future.exception.GlobalException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/29.
 */
@Controller
public class BindingController {
    private static Logger LOGGER = LogManager.getLogger(IndexController.class);
    @Value("${uploadFileDir}")
    private String uploadFileDir;
    @RequestMapping("form/{username}")
    public ModelAndView user(@PathVariable String username, HttpServletRequest request)
    {
        WebApplicationContext webApplicationContext =  ContextLoader.getCurrentWebApplicationContext();
        User user = (User) webApplicationContext.getBean("user");
        LOGGER.info("username:{}, method:{}, p:{}", username, request.getMethod(), request.getParameter("p"));
        ModelAndView modelAndView = new ModelAndView("/mvcbinding/userForm", "user", user);
        LOGGER.info("user.getBirthDate()", user.getBirthDate());
        modelAndView.addObject("currentTime", user.getBirthDate());
        modelAndView.addObject("genders", User.Gender.values());
        modelAndView.addObject("countries", new String[]{"China", "Foreigner"});

        return modelAndView;
    }

    @RequestMapping("result")
    public ModelAndView processUser(User user, HttpServletRequest request) throws IOException, GlobalException {
        LOGGER.info("request received:{}", user);
        LOGGER.info("username:{}, method:{}, gender:{}, file:{}", user.getUserName()
                                                        , request.getMethod(), request.getParameter("gender")
                                                        ,user.getFile().getBytes().length);

        if(user.getFile() == null || user.getFile().isEmpty())
        {
            LOGGER.error("file is empty or null");
            throw new GlobalException("file is empty or null");
        }

        LOGGER.info("filePath:{}", (ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(uploadFileDir)
                                                                                             + "/" + user.getFile().getOriginalFilename()));
        user.getFile().transferTo(new File(ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(uploadFileDir)
                + "/" + user.getFile().getOriginalFilename()));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/mvcbinding/userResult");
        modelAndView.addObject("u", user);
        modelAndView.addObject("fileLength", user.getFile().getBytes().length);

        return modelAndView;
    }

}
