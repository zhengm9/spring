package com.future.controller;

import com.future.annotation.LoginValidation;
import com.future.constants.ExternalTaskStateType;
import com.future.constants.HandleStateType;
import com.future.constants.InternalTaskStateType;
import com.future.constants.ProjectType;
import com.future.dao.po.ParentProjectInfo;
import com.future.dao.po.ProjectInfo;
import com.future.dao.po.SysUser;
import com.future.entity.WebLogin;
import com.future.entity.WebUser;
import com.future.entity.req.ListReq;
import com.future.entity.rsp.Head;
import com.future.service.ParentProjectInfoService;
import com.future.service.ProjectInfoService;
import com.future.service.SysUserService;
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
import java.util.List;

/**
 * Created by zhengming on 17/1/27.
 */

@Controller
public class ParentProjectController {
private static Logger LOGGER = LogManager.getLogger(ParentProjectController.class);

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ProjectInfoService projectInfoService;

    @Autowired
    private ParentProjectInfoService parentProjectInfoService;



    @LoginValidation("user")
    @RequestMapping(value="/parentProjectCenter")
    public ModelAndView getProjectsInfo(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize)
    {
        Integer userid = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userid")));
        PageInfo<ParentProjectInfo> pageInfo = this.parentProjectInfoService.selectByPage(null, pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView("index_parent");
        LOGGER.info("page info:pageNum-{},pageSize-{},isFirstPage-{},totalPages-{},isLastPage-{}",
                pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.isIsFirstPage(), pageInfo.getPages(), pageInfo.isIsLastPage());
        modelAndView.addObject("parentProjectInfoList", pageInfo.getList());
        //获得当前页
        modelAndView.addObject("pageNum", pageInfo.getPageNum());
        //获得一页显示的条数
        modelAndView.addObject("pageSize", pageInfo.getPageSize());
        //是否是第一页
        modelAndView.addObject("isFirstPage", pageInfo.isIsFirstPage());
        //获得总页数
        modelAndView.addObject("totalPages", pageInfo.getPages());
        //是否是最后一页
        modelAndView.addObject("isLastPage", pageInfo.isIsLastPage());
        return modelAndView;

    }

    @LoginValidation("user")
    @RequestMapping(value="/parentProjectCenter/{parentProjectId}/{action}")
    public void getProjectsInfo(HttpServletRequest request, HttpServletResponse response,
                                        ParentProjectInfo parentProjectInfo,
                                        @PathVariable Integer parentProjectId,
                                        @PathVariable String action)
    {
        if(parentProjectId!=null)
        {
            parentProjectInfo.setId(parentProjectId);
        }
        if("update".equals(action))
        {
            this.parentProjectInfoService.updateByPrimaryKeySelective(parentProjectInfo);
        }
        else if("delete".equals(action))
        {

        }
        try {
            response.sendRedirect(request.getContextPath()+"/parentProjectCenter");
        } catch (IOException e) {
            LOGGER.error(e);
        }


    }

}
