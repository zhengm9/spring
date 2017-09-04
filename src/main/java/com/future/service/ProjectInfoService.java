package com.future.service;

import com.future.controller.ProjectController;
import com.future.dao.idao.ProjectInfoMapper;
import com.future.dao.po.ProjectInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengming on 2017/8/20.
 */
@Repository(value = "ProjectInfoService")
public class ProjectInfoService implements ProjectInfoMapper {
    private static org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(ProjectController.class);

    @Autowired
    ProjectInfoMapper projectInfoMapper;
    public int deleteByPrimaryKey(Integer id) {
        return this.projectInfoMapper.deleteByPrimaryKey(id);
    }

    public int insert(ProjectInfo record) {
        return 0;
    }

    public int insertSelective(ProjectInfo record) {
        return this.projectInfoMapper.insertSelective(record);
    }

    public ProjectInfo selectByPrimaryKey(Integer id) {
        return this.projectInfoMapper.selectByPrimaryKey(id);
    }

    public List<ProjectInfo> selectAll() {
        return this.projectInfoMapper.selectAll();
    }

    public List<ProjectInfo> selectByOwnerId(Integer ownerId) {
        return this.projectInfoMapper.selectByOwnerId(ownerId);
    }

//    @Transactional(readOnly = true)
    public PageInfo<ProjectInfo> selectByPage(Integer ownerId, Integer pageNum, Integer pageSize)
    {
        PageHelper.startPage(pageNum,pageSize);
        List<ProjectInfo> projectInfoList = new ArrayList();
        if(ownerId==null || ownerId==0)
        {
            projectInfoList =  this.selectAll();
        }else {
            projectInfoList =  this.selectByOwnerId(ownerId);
        }

        return new PageInfo(projectInfoList);
    }

    public int updateByPrimaryKeySelective(ProjectInfo record) {
        LOGGER.info("updateByPrimaryKeySelective...");
        return this.projectInfoMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(ProjectInfo record) {
        return 0;
    }
}
