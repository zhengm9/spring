package com.future.service;

import com.future.dao.idao.SysUserMapper;
import com.future.dao.po.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/1/30.
 */
@Repository(value = "SysUserService")
public class SysUserService implements SysUserMapper {
    @Autowired
    private SysUserMapper sysUserMapper;


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
        return null;
    }

    public SysUser selectByUsername(String username) {
        return null;
    }

    public int updateByPrimaryKeySelective(SysUser record) {
        return 0;
    }

    public int updateByPrimaryKey(SysUser record) {
        return 0;
    }
}
