package com.future.service;

import com.future.dao.idao.GeProposalMainMapper;
import com.future.dao.idao.IGeProposalMainDao;
import com.future.dao.idao.IUserDao;
import com.future.dao.idao.UserMapper;
import com.future.dao.po.GeProposalMain;
import com.future.dao.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by Administrator on 2017/1/30.
 */
@Repository(value = "geProposalService")
public class GeProposalService implements IGeProposalMainDao {
    @Autowired
    private IGeProposalMainDao iGeProposalMainDao;

    public int deleteByPrimaryKey(String proposalsid) {
        return 0;
    }

    public int insert(GeProposalMain record) {
        return 0;
    }

    public int insertSelective(GeProposalMain record) {
        return 0;
    }

    public GeProposalMain selectByPrimaryKey(String proposalsid) {
        return null;
    }

    public int updateByPrimaryKeySelective(GeProposalMain record) {
        return 0;
    }

    public int updateByPrimaryKey(GeProposalMain record) {
        return 0;
    }

    public GeProposalMain selectForStat() {
//    public Map<String, String> selectForStat() {
    return this.iGeProposalMainDao.selectForStat();
    }

}
