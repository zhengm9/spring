package com.future.dao4ora.service;

import com.future.dao4ora.po.GeAlipayAirinfo;
import com.future.dao4ora.idao.GeAlipayAirinfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhengming on 17/10/7.
 */
@Repository(value = "geAlipayAirinfoService")

public class GeAlipayAirinfoService implements GeAlipayAirinfoMapper {
    @Autowired
    private GeAlipayAirinfoMapper geAlipayAirinfoMapper;

    public Integer countAll(String startDay, String endDay) {
        return this.geAlipayAirinfoMapper.countAll(startDay,endDay);
    }

    public List<GeAlipayAirinfo> selectByMakedate(String startDay, String endDay) {
        return this.geAlipayAirinfoMapper.selectByMakedate(startDay, endDay);
    }

    public Integer countAllCancel(@Param("startDay") String startDay, @Param("endDay") String endDay) {
        return this.geAlipayAirinfoMapper.countAllCancel(startDay, endDay);
    }

    public List<GeAlipayAirinfo> selectCancelByMakedate(@Param("startDay") String startDay, @Param("endDay") String endDay) {
        return this.geAlipayAirinfoMapper.selectCancelByMakedate(startDay, endDay);
    }

    public Integer countAllEndorse(@Param("startDay") String startDay, @Param("endDay") String endDay) {
        return this.geAlipayAirinfoMapper.countAllEndorse(startDay, endDay);
    }

    public List<GeAlipayAirinfo> selectEndorseByMakedate(@Param("startDay") String startDay, @Param("endDay") String endDay) {
        return this.geAlipayAirinfoMapper.selectEndorseByMakedate(startDay, endDay);
    }

    public Integer countOmittedAll(@Param("tborderid") String tborderid) {
        return this.geAlipayAirinfoMapper.countOmittedAll(tborderid);
    }

    public List<GeAlipayAirinfo> selectOmittedByOrderId(@Param("tborderid") String tborderid) {
        return this.geAlipayAirinfoMapper.selectOmittedByOrderId(tborderid);
    }

    public PageInfo<GeAlipayAirinfo> selectByMakedateAndPage(String startDay, String endDay,
                                                         Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<GeAlipayAirinfo> list = this.selectByMakedate(startDay, endDay);

        return new PageInfo(list);
    }

    public PageInfo<GeAlipayAirinfo> selectCancelByMakedateAndPage(String startDay, String endDay,
                                                                   Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<GeAlipayAirinfo> list = this.selectCancelByMakedate(startDay, endDay);

        return new PageInfo(list);
    }

    public PageInfo<GeAlipayAirinfo> selectEndorseByMakedateAndPage(String startDay, String endDay,
                                                                   Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<GeAlipayAirinfo> list = this.selectEndorseByMakedate(startDay, endDay);

        return new PageInfo(list);
    }

    public int deleteByPrimaryKey(String serialno) {
        return 0;
    }

    public int insert(GeAlipayAirinfo record) {
        return 0;
    }

    public int insertSelective(GeAlipayAirinfo record) {
        return 0;
    }

    public GeAlipayAirinfo selectByPrimaryKey(String serialno) {
        return null;
    }

    public int updateByPrimaryKeySelective(GeAlipayAirinfo record) {
        return 0;
    }

    public int updateByPrimaryKey(GeAlipayAirinfo record) {
        return 0;
    }
}
