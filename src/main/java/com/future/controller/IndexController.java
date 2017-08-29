package com.future.controller;

import com.future.annotation.LoginValidation;
import com.future.constants.ProjectType;
import com.future.constants.Role;
import com.future.dao.idao.ProjectInfoMapper;
import com.future.dao.po.ParentProjectInfo;
import com.future.dao.po.ProjectInfo;
import com.future.dao.po.SysUser;
import com.future.entity.WebLogin;
import com.future.entity.WebUser;
import com.future.entity.req.ListReq;
import com.future.entity.rsp.Head;
import com.future.filter.SessionRecorder;
import com.future.service.ParentProjectInfoService;
import com.future.service.ProjectInfoService;
import com.future.service.SysUserService;
import com.future.service.UserBakService;
import com.future.service.mq.SendMsg2MQ;
import com.future.util.CreateImageCode;
import com.future.util.CryptUtil;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
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

    @Autowired
    private ParentProjectInfoService parentProjectInfoService;

    //@RequestMapping({"/hello", "zhengm", "/"})
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

    @LoginValidation("user")
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

    /*@LoginValidation("user")
    @RequestMapping(value="/")
    public void entry(HttpServletRequest request, HttpServletResponse response)
    {
        try {
            request.getRequestDispatcher("index.html")
                    .forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


    @RequestMapping(value="/logincheck")
    public ResponseEntity<String> login(HttpServletRequest request, SysUser sysUserRemote, @RequestParam String yzm) throws ServletException, IOException {
//        return new ModelAndView("yzmimage");
        LOGGER.info("sysuser:{},{};remote verify code:{}",sysUserRemote.getUsername(),sysUserRemote.getPassword(), yzm);
        if(request.getSession().getAttribute("code") == null || !String.valueOf(request.getSession().getAttribute("code")).equals(yzm))
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


    @RequestMapping(value="/logwhenexception")
    public void login(HttpServletRequest request, HttpServletResponse response)
    {
        LOGGER.info("log in");

        try {
            request.getRequestDispatcher("login.html")
                    .forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @LoginValidation("user")
    @RequestMapping(value="/")
    public ModelAndView entry(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize)
    {
        Integer userid = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userid")));
        PageInfo<ProjectInfo> pageInfo = this.projectInfoService.selectByPage(userid, pageNum, pageSize);
        ModelAndView model = new ModelAndView("index");
        LOGGER.info("page info:pageNum-{},pageSize-{},isFirstPage-{},totalPages-{},isLastPage-{}",
                pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.isIsFirstPage(), pageInfo.getPages(), pageInfo.isIsLastPage());
        model.addObject("projectInfoList", pageInfo.getList());
        //获得当前页
        model.addObject("pageNum", pageInfo.getPageNum());
        //获得一页显示的条数
        model.addObject("pageSize", pageInfo.getPageSize());
        //是否是第一页
        model.addObject("isFirstPage", pageInfo.isIsFirstPage());
        //获得总页数
        model.addObject("totalPages", pageInfo.getPages());
        //是否是最后一页
        model.addObject("isLastPage", pageInfo.isIsLastPage());

        return model;

    }

    @LoginValidation("user")
    @RequestMapping(value="/details/{projectid}/{action}")
    public ModelAndView getProjectDetails(HttpServletRequest request, HttpServletResponse response,
                                                @PathVariable String projectid, @PathVariable String action)
    {
        if(Strings.isNullOrEmpty(projectid))
        {
            return new ModelAndView("errorpage");
        }

        List<ParentProjectInfo> parentProjectInfoList = this.parentProjectInfoService.selectAll();

        ProjectInfo projectInfo = this.projectInfoService.selectByPrimaryKey(Integer.valueOf(projectid));
        ModelAndView modelAndView = new ModelAndView("details");
        modelAndView.addObject("projectInfo", projectInfo);
        modelAndView.addObject("action", action);
        modelAndView.addObject("parentProjectInfoList",parentProjectInfoList);
        modelAndView.addObject("projectTypeList", ProjectType.values());

        return modelAndView;

    }

    @LoginValidation("user")
    @RequestMapping(value="/update/{projectid}")
    public ModelAndView updateProject(HttpServletRequest request, HttpServletResponse response,
                                          @PathVariable String projectid, ProjectInfo projectInfo)
    {
        if(Strings.isNullOrEmpty(projectid))
        {
            return new ModelAndView("errorpage");
        }

        projectInfo.setId(Integer.valueOf(projectid));
       int ret = this.projectInfoService.updateByPrimaryKeySelective(projectInfo);
        LOGGER.info("update RESULT IS:{}",ret);
        return null;

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
