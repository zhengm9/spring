package com.future.controller;

import com.future.annotation.LoginValidation;
import com.future.dao.idao.ProjectInfoMapper;
import com.future.dao.po.ProjectInfo;
import com.future.dao.po.SysUser;
import com.future.entity.WebLogin;
import com.future.entity.WebUser;
import com.future.entity.req.ListReq;
import com.future.entity.rsp.Head;
import com.future.service.ProjectInfoService;
import com.future.service.SysUserService;
import com.future.service.UserBakService;
import com.future.service.mq.SendMsg2MQ;
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
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by zhengming on 17/1/27.
 */

@Controller
public class IndexController {
private static Logger LOGGER = LogManager.getLogger(IndexController.class);

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ProjectInfoService projectInfoService;

    @RequestMapping({"/hello", "zhengm", "/"})
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

//    @LoginValidation("user")
    @RequestMapping(value="/yzm")
    public void getYZM(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
//        return new ModelAndView("yzmimage");
        new CreateImageCode().getResponse(request, response, session);

    }

    @RequestMapping(value="/getUsername")
    public ResponseEntity<String> getUsername(HttpSession session) throws ServletException, IOException {
//        return new ModelAndView("yzmimage");
        return new ResponseEntity<String>(String.valueOf(session.getAttribute("username")),HttpStatus.OK);

    }

    @RequestMapping(value="/getProjectList")
    public ResponseEntity<Head> getProjectList(HttpServletRequest request, ListReq listReq) throws ServletException, IOException {
//        Integer userid = Integer.parseInt(String.valueOf(request.getSession().getAttribute("userid")));
        Integer userid = 1;
        List<ProjectInfo> list = this.projectInfoService.selectByOwnerId(userid);
        Head head = new Head();
        head.setCount(list.size());
        head.setUsername("zmmason");
        head.setObj(list);
        return new ResponseEntity<Head>(head,HttpStatus.OK);

    }

    @RequestMapping(value="/logincheck")
    public ResponseEntity<String> login(HttpServletRequest request, SysUser sysUserRemote) throws ServletException, IOException {
//        return new ModelAndView("yzmimage");
        LOGGER.info("sysuser:{},{}",sysUserRemote.getUsername(),sysUserRemote.getPassword());
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

                        return new ResponseEntity<String>("yes",HttpStatus.OK);
                    }
                } catch (Exception e) {
                    return new ResponseEntity<String>("failed",HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        return new ResponseEntity<String>("failed",HttpStatus.UNAUTHORIZED);

    }


}
