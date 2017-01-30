package com.future.controller.exception;

import com.future.exception.GlobalException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

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
}
