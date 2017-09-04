package com.future.service;

import com.future.dao.idao.SysUserMapper;
import com.future.dao.po.SysUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/1/30.
 */
@Repository(value = "SysUserService")
public class SysUserService implements SysUserMapper {
    @Autowired
    private SysUserMapper sysUserMapper;
    private static Logger LOGGER = LogManager.getLogger(SysUserService.class);


    public SysUser getUserByName(String username)
    {
        return this.sysUserMapper.selectByUsername(username);
    }

    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    public int insert(SysUser record) {
        return 0;
    }

    public int insertSelective(SysUser record) {
        return 0;
    }

    public SysUser selectByPrimaryKey(Integer id) {
        return this.sysUserMapper.selectByPrimaryKey(id);
    }

    public SysUser selectByUsername(String username) {
        return null;
    }

    public int updateByPrimaryKeySelective(SysUser record) {
        int ret = 0;
        try {
          ret = this.sysUserMapper.updateByPrimaryKeySelective(record);
        }catch (Exception e)
        {
            LOGGER.error("update error:{}",e);
        }
        return ret;
    }

    public int updateByPrimaryKey(SysUser record) {
        return 0;
    }
}
