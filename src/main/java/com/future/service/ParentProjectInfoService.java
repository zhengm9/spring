package com.future.service;

import com.future.controller.ProjectController;
import com.future.dao.idao.ParentProjectInfoMapper;
import com.future.dao.po.ParentProjectInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengming on 17/8/28.
 */
@Repository("ParentProjectInfoService")
public class ParentProjectInfoService implements ParentProjectInfoMapper {
    @Autowired
    ParentProjectInfoMapper parentProjectInfoMapper;
    private static Logger LOGGER = LogManager.getLogger(ProjectController.class);

    public int deleteByPrimaryKey(Integer id) {
        int ret = 0;
        try {
            ret = this.parentProjectInfoMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            LOGGER.error("删除失败:{}",e);
        }
        return ret;
    }

    public int insert(ParentProjectInfo record) {
        return 0;
    }

    public int insertSelective(ParentProjectInfo record) {
        return this.parentProjectInfoMapper.insertSelective(record);
    }

    public ParentProjectInfo selectByPrimaryKey(Integer id) {
        return null;
    }

    public List<ParentProjectInfo> selectAll() {
        return this.parentProjectInfoMapper.selectAll();
    }

    public PageInfo<ParentProjectInfo> selectByPage(Integer ownerId, Integer pageNum, Integer pageSize)
    {
        PageHelper.startPage(pageNum,pageSize);
        List<ParentProjectInfo> parentProjectInfoList = new ArrayList();
        parentProjectInfoList =  this.selectAll();

        return new PageInfo(parentProjectInfoList);
    }

    public int updateByPrimaryKeySelective(ParentProjectInfo record) {
        return this.parentProjectInfoMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(ParentProjectInfo record) {
        return 0;
    }
}
