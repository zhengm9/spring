package com.future.dao4ora.service;

import com.future.dao4ora.po.GeAlipayAirinfo;
import com.future.dao4ora.idao.GeAlipayAirinfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

    public PageInfo<GeAlipayAirinfo> selectByMakedateAndPage(String startDay, String endDay,
                                                         Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<GeAlipayAirinfo> list = this.selectByMakedate(startDay, endDay);

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
