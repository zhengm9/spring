package com.future.dao.service;

import com.future.dao.idao.IUserDao;
import com.future.dao.idao.UserMapper;
import com.future.dao.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/1/30.
 */
@Repository(value = "userService")
public class UserService implements UserMapper {
    @Autowired
    private IUserDao userDao;

    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    public int insert(User record) {
        return 0;
    }

    public int insertDefault(User record) {
        this.userDao.insertDefault(record);
        this.userDao.insertDefault(record);

        this.userDao.insert(record);
        return 1;
    }

    public int insertSelective(User record) {
        return 0;
    }

    public User selectByPrimaryKey(Integer id) {
        return null;
    }

    public User getUserById(Integer userId)
    {
        return this.userDao.selectByPrimaryKey(userId);
    }

    public int updateByPrimaryKeySelective(User record) {
        return 0;
    }

    public int updateByPrimaryKey(User record) {
        return 0;
    }
}
