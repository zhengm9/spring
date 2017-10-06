package com.future.dao4ora.service;

import com.future.dao4ora.idao.GeProposalMainMapper;
import com.future.dao4ora.po.GeProposalMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/1/30.
 */
@Repository(value = "geProposalService")
public class GeProposalService implements GeProposalMainMapper {
    @Autowired
//    private IGeProposalMainDao iGeProposalMainDao;
private GeProposalMainMapper geProposalMainMapper;
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

//    public GeProposalMain selectForStat() {
    public List<GeProposalMain> selectForStat() {
    return this.geProposalMainMapper.selectForStat();

    }

    public GeProposalMain selectForAliAir(GeProposalMain geProposalMain) {
        return this.geProposalMainMapper.selectForAliAir(geProposalMain);
    }


}
