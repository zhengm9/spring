package com.future.controller.exception;

import com.future.exception.GlobalException;
import com.future.exception.IllegalLoginException;
import com.future.filter.SessionRecorder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by Administrator on 2017/1/30.
 */
@ControllerAdvice
public class GlobalExceptionController {
    private static Logger LOGGER = LogManager.getLogger(GlobalExceptionController.class);

    @ExceptionHandler(GlobalException.class)
    public ModelAndView handleException(GlobalException e)
    {
        LOGGER.error("GlobalException handled, e:{}", e);
        ModelAndView errorModelAndView = new ModelAndView("error/errorPage");
        errorModelAndView.addObject("errormsg", e.getMessage());
        return errorModelAndView;
    }

    @ExceptionHandler(IllegalLoginException.class)
    public ModelAndView handleIllegalLoginException(IllegalLoginException e)
    {
        LOGGER.error("IllegalLoginException handled, e:{}", e);
        ModelAndView errorModelAndView = new ModelAndView("login");
        errorModelAndView.addObject("errormsg", "您尚未登录，请输入用户名、密码");
        return errorModelAndView;

    }

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleException(RuntimeException e) {
        LOGGER.error("RuntimeException handled, e:{}", e);
        String errorMsgShow = e.getMessage();


            if (e instanceof DuplicateKeyException) {
                errorMsgShow = "error occurred during database operation. error info: Duplicate Key.";
            }
            ModelAndView errorModelAndView = new ModelAndView("error/errorPage");
            errorModelAndView.addObject("errormsg", errorMsgShow);
            return errorModelAndView;

    }
}
