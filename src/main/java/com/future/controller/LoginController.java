package com.future.controller;

import com.future.dao.po.SysUser;
import com.future.dao.service.ParentProjectInfoService;
import com.future.dao.service.ProjectInfoService;
import com.future.dao.service.SysUserService;
import com.future.util.CreateImageCode;
import com.future.util.CryptUtil;
import com.google.common.base.Strings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by zhengming on 17/1/27.
 */

@Controller
public class LoginController {
private static Logger LOGGER = LogManager.getLogger(LoginController.class);

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ProjectInfoService projectInfoService;

    @Autowired
    private ParentProjectInfoService parentProjectInfoService;



    @RequestMapping(value="/yzm")
    public void getYZM(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        new CreateImageCode().getResponse(request, response, session);

    }

    @RequestMapping(value="/getUsername")
    public ResponseEntity<String> getUsername(HttpSession session) throws ServletException, IOException {
        return new ResponseEntity<String>(String.valueOf(session.getAttribute("username")),HttpStatus.OK);

    }

    @RequestMapping(value="/logincheck",produces = "text/html;charset=UTF-8")
    public ResponseEntity<String> login(HttpServletRequest request,HttpServletResponse response, SysUser sysUserRemote, @RequestParam String yzm) throws ServletException, IOException {
        LOGGER.info("sysuser:{},{};remote verify code:{}",
                sysUserRemote.getUsername(),sysUserRemote.getPassword(), yzm);

        if(request.getSession().getAttribute("code") == null || !String.valueOf(request.getSession().getAttribute("code")).equalsIgnoreCase(yzm))
        {
            return new ResponseEntity<String>("验证码错误",HttpStatus.UNAUTHORIZED);
        }

        if(!Strings.isNullOrEmpty(sysUserRemote.getUsername()))
        {
            SysUser sysuserLocal = sysUserService.getUserByName(sysUserRemote.getUsername());
            if(sysuserLocal != null && sysuserLocal.getPassword() != null)
            {
                try {
                    String md5local = CryptUtil.md5Hex(sysuserLocal.getPassword()+":"+String.valueOf(request.getSession().getAttribute("code")));
                    LOGGER.info("remote md5 password is:{}, local md5 password is:{},local password is:{},code is:{}",
                            sysUserRemote.getPassword(), md5local, sysuserLocal.getPassword(), String.valueOf(request.getSession().getAttribute("code")));
                    if(md5local.equals(sysUserRemote.getPassword()))
                    {
                        request.getSession().setAttribute("username", sysUserRemote.getUsername());
                        request.getSession().setAttribute("userid", sysuserLocal.getId());

                        return new ResponseEntity<String>("{\"errormsg\",\"ok\"}",HttpStatus.OK);
                    }
                } catch (Exception e) {
                    return new ResponseEntity<String>("failed",HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
//        return new ResponseEntity<String>("{\"errormsg\",\"用户名密码错误\"}",HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<String>("用户名密码错误",HttpStatus.UNAUTHORIZED);
    }


    @RequestMapping(value="/login")
    public ModelAndView login()
    {

        return new ModelAndView("login");

    }



    @RequestMapping(value="/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response)
    {
        LOGGER.info("log out");
        request.getSession().removeAttribute("username");
        request.getSession().removeAttribute("userid");

        return new ModelAndView("login");
    }
}
