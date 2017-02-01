package com.future.annotationservice;

import com.future.controller.IndexController;
import com.future.dao.idao.IUserDao;
import com.future.dao.idao.UserMapper;
import com.future.dao.po.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.security.acl.LastOwnerException;

/**
 * Created by Administrator on 2017/1/30.
 */
@Repository(value = "annotationUserService")
public class AnnotationUserService implements UserMapper {
    @Autowired
    private IUserDao userDao;
    @Autowired
    private PlatformTransactionManager ptm;
    private static Logger LOGGER = LogManager.getLogger(AnnotationUserService.class);

    @Transactional
    public int insertDefault(User record) {
        this.userDao.insertDefault(record);
        return 1;
    }

    public int insertTwice(User record) {
        LOGGER.info("PlatformTransactionManager:{}", ptm);
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = ptm.getTransaction(def);
        try{
            this.userDao.insertDefault(record);

            this.userDao.insert(record);
            ptm.commit(status);//手动提交事务

        } catch (Exception e) {
            ptm.rollback(status);
            e.printStackTrace();
        }
        return 1;
    }

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
