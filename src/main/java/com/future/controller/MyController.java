package com.future.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhengming on 17/1/27.
 */

@Controller
public class MyController {
    @RequestMapping({"/hello", "lj", "/"})
    public String hello() {
        System.out.println("hello");
        return "hello";
    }
}
