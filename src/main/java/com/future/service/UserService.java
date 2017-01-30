package com.future.service;

import com.future.dao.idao.UserMapper;
import com.future.dao.po.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/1/30.
 */
@Service(value = "userService")
public class UserService implements UserMapper {
    @Resource
    private UserMapper userDao;

    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    public int insert(User record) {
        return 0;
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
