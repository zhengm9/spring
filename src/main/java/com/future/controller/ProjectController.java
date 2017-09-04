package com.future.controller;

import com.future.annotation.LoginValidation;
import com.future.constants.*;
import com.future.dao.po.ParentProjectInfo;
import com.future.dao.po.ProjectInfo;
import com.future.service.ParentProjectInfoService;
import com.future.service.ProjectInfoService;
import com.future.service.SysUserService;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by zhengming on 17/1/27.
 */

@Controller
@LoginValidation("user")
public class ProjectController {
private static Logger LOGGER = LogManager.getLogger(ProjectController.class);

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ProjectInfoService projectInfoService;

    @Autowired
    private ParentProjectInfoService parentProjectInfoService;


    @RequestMapping(value="/")
    public void entry(HttpServletRequest request, HttpServletResponse response)
    {

        try {
            response.sendRedirect(request.getContextPath()+"/projectCenter");
        } catch (IOException e) {
            LOGGER.error(e);
        }

    }


    @RequestMapping(value="/projectCenter")
    public ModelAndView getProjectsInfo(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "0") Integer isSelfish)
    {
        Integer userid = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userid")));
        PageInfo<ProjectInfo> pageInfo = this.projectInfoService.selectByPage(((isSelfish==0)?userid:null), pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView("index");
        LOGGER.info("page info:pageNum-{},pageSize-{},isFirstPage-{},totalPages-{},isLastPage-{}",
                pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.isIsFirstPage(), pageInfo.getPages(), pageInfo.isIsLastPage());
        modelAndView.addObject("projectInfoList", pageInfo.getList());
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
        //是否是仅查询用户自己的项目
        modelAndView.addObject("isSelfish", (isSelfish==0));
        List<ParentProjectInfo> parentProjectInfoList = this.parentProjectInfoService.selectAll();
        modelAndView.addObject("parentProjectInfoList",parentProjectInfoList);
        modelAndView.addObject("projectTypeList", ProjectType.values());
        modelAndView.addObject("handleStateTypeList", HandleStateType.values());
        modelAndView.addObject("externalTaskStateTypeList", ExternalTaskStateType.values());
        modelAndView.addObject("internalTaskStateTypeList", InternalTaskStateType.values());

        return modelAndView;

    }


    @RequestMapping(value="/projectCenter/details/{projectid}/{action}")
    public ModelAndView getProjectDetails(HttpServletRequest request, HttpServletResponse response,
                                                @PathVariable String projectid, @PathVariable String action)
    {
        if(Strings.isNullOrEmpty(projectid))
        {
            return new ModelAndView("errorpage");
        }

        if("delete".equals(action))
        {
            this.projectInfoService.deleteByPrimaryKey(Integer.valueOf(projectid));
            try {
                response.sendRedirect(request.getContextPath()+"/projectCenter");
            } catch (IOException e) {
                LOGGER.error(e);
            }
            return null;
        }

        List<ParentProjectInfo> parentProjectInfoList = this.parentProjectInfoService.selectAll();

        ModelAndView modelAndView = new ModelAndView("details");

        if(!"create".equals(action))
        {
            ProjectInfo projectInfo = this.projectInfoService.selectByPrimaryKey(Integer.valueOf(projectid));
            modelAndView.addObject("projectInfo", projectInfo);
        }

        modelAndView.addObject("action", action);
        modelAndView.addObject("parentProjectInfoList",parentProjectInfoList);
        modelAndView.addObject("projectTypeList", ProjectType.values());
        modelAndView.addObject("handleStateTypeList", HandleStateType.values());
        modelAndView.addObject("externalTaskStateTypeList", ExternalTaskStateType.values());
        modelAndView.addObject("internalTaskStateTypeList", InternalTaskStateType.values());

        return modelAndView;

    }

    @RequestMapping(value="/projectCenter/action/delete/{projectid}")
    public ResponseEntity<String> deleteProject(HttpServletRequest request, HttpServletResponse response,
                                        @PathVariable String projectid)
    {
        if(Strings.isNullOrEmpty(projectid))
        {
            return new ResponseEntity<String>("删除失败，项目ID为空", HttpStatus.BAD_REQUEST);
        }

        if(this.projectInfoService.deleteByPrimaryKey(Integer.valueOf(projectid))<0)
        {
            return new ResponseEntity<String>("删除失败", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("删除成功", HttpStatus.OK);


    }

    @RequestMapping(value="/projectCenter/action/update/{projectid}")
    public void updateProject(HttpServletRequest request, HttpServletResponse response,
                                          @PathVariable String projectid, ProjectInfo projectInfo)
    {
        if(Strings.isNullOrEmpty(projectid))
        {
            return ;
        }

        projectInfo.setId(Integer.valueOf(projectid));
       int ret = this.projectInfoService.updateByPrimaryKeySelective(projectInfo);
        LOGGER.info("update RESULT IS:{}",ret);
        try {
            response.sendRedirect(request.getContextPath()+"/projectCenter/details/"+projectid+"/view");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @RequestMapping(value="/projectCenter/action/insert")
    public void insertProject(HttpServletRequest request, HttpServletResponse response,
                              ProjectInfo projectInfo)
    {
        projectInfo.setOwnerId((Integer)request.getSession().getAttribute("userid"));
        int ret = this.projectInfoService.insertSelective(projectInfo);
        LOGGER.info("insert RESULT IS:{}",ret);
        try {
            response.sendRedirect(request.getContextPath()+"/projectCenter");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
