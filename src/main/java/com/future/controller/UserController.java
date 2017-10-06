package com.future.controller;

import com.future.annotation.LoginValidation;
import com.future.dao.po.SysUser;
import com.future.dao.service.SysUserService;
import com.google.common.base.Strings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhengming on 17/1/27.
 */

@Controller
@LoginValidation("user")
public class UserController {
    private static Logger LOGGER = LogManager.getLogger(UserController.class);

    @Autowired
    private SysUserService sysUserService;


    @RequestMapping(value = "/user/changePwd", produces = "text/html;charset=UTF-8")
    public ResponseEntity<String> changePwd(HttpServletRequest request, HttpServletResponse response,
                                        @RequestParam String oldpwd,
                                        @RequestParam String newpwd, @RequestParam String newpwdagain   ) {

        Integer userid = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userid")));
        if(Strings.isNullOrEmpty(oldpwd) || Strings.isNullOrEmpty(newpwd) ||
                Strings.isNullOrEmpty(newpwdagain) || !newpwdagain.equals(newpwd))
        {
            return new ResponseEntity<String>("输入错误！",HttpStatus.BAD_REQUEST);
        }

        if(!oldpwd.equals(sysUserService.selectByPrimaryKey(userid).getPassword()))
        {
            return new ResponseEntity<String>("旧密码错误！",HttpStatus.BAD_REQUEST);

        }

        SysUser sysUser = new SysUser();
        sysUser.setId(userid);
        sysUser.setPassword(newpwd);
        if(sysUserService.updateByPrimaryKeySelective(sysUser) > 0){
            return new ResponseEntity<String>("提交成功",HttpStatus.OK);
        }
        return new ResponseEntity<String>("提交失败",HttpStatus.BAD_REQUEST);

    }
}
