package com.future.service;

import com.future.dao.idao.ProjectInfoMapper;
import com.future.dao.po.ProjectInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhengming on 2017/8/20.
 */
@Repository(value = "ProjectInfoService")
public class ProjectInfoService implements ProjectInfoMapper {
    @Autowired
    ProjectInfoMapper projectInfoMapper;
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    public int insert(ProjectInfo record) {
        return 0;
    }

    public int insertSelective(ProjectInfo record) {
        return 0;
    }

    public ProjectInfo selectByPrimaryKey(Integer id) {
        return null;
    }

    public List<ProjectInfo> selectByOwnerId(Integer ownerId) {
        PageHelper pageHelper = new PageHelper();
        return this.projectInfoMapper.selectByOwnerId(ownerId);
    }

    @Transactional(readOnly = true)
    public PageInfo<ProjectInfo> selectByPage(Integer ownerId, Integer pageNum, Integer pageSize)
    {
        PageHelper.startPage(pageNum,pageSize);
        List<ProjectInfo> projectInfoList =  this.projectInfoMapper.selectByOwnerId(ownerId);
        return new PageInfo(projectInfoList);
    }

    public int updateByPrimaryKeySelective(ProjectInfo record) {
        return 0;
    }

    public int updateByPrimaryKey(ProjectInfo record) {
        return 0;
    }
}
