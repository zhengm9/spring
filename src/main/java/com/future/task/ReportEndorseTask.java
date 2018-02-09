package com.future.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.dao4ora.po.GeAlipayAirinfo;
import com.future.dao4ora.service.GeAlipayAirinfoService;
import com.future.util.DateConverter;
import com.github.pagehelper.PageInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengming on 17/10/1.
 */
@Component
public class ReportEndorseTask extends AbstractReportTask<GeAlipayAirinfo>{
    private static Logger LOGGER = LogManager.getLogger(ReportEndorseTask.class);
    @Autowired
    private GeAlipayAirinfoService geAlipayAirinfoService;
    @Autowired
    private DateConverter dateConverter;

    public ReportEndorseTask()
    {
        super();

    }

   /* public void initDayScope(String startDay, String endDay)
    {
        super.startDay=startDay;
        super.endDay=endDay;
    }*/

    public int getSqlCount() {
        /*Date date = new Date();
        Long offsetTime = date.getTime()-24*60*60*1000;
        startDay = DateConverter.formatDate(new Date(offsetTime),"yyyy-MM-dd");*/

        Integer i = this.geAlipayAirinfoService.countAllEndorse(startDay,endDay);

        return i;
    }

    public List<GeAlipayAirinfo> getSqlResults(int pageNum, int pageSize) {


        PageInfo<GeAlipayAirinfo> pageInfo =
                this.geAlipayAirinfoService.selectEndorseByMakedateAndPage(startDay,endDay,
                pageNum, pageSize);
        LOGGER.info("page info:pageNum-{},pageSize-{},isFirstPage-{},totalPages-{},isLastPage-{}",
                pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.isIsFirstPage(), pageInfo.getPages(), pageInfo.isIsLastPage());

        ObjectMapper objectMapper = new ObjectMapper();
        for(GeAlipayAirinfo geAlipayAirinfo : pageInfo.getList())
        {
            Map<String, String> mapsData = null;
            try {
                Map<String, String>  mapsFlightData = objectMapper.readValue(geAlipayAirinfo.getNewflightvalue(), Map.class);
                Map<String, String>  mapsEffectData = objectMapper.readValue(geAlipayAirinfo.getNewtimevalue(), Map.class);
                Map<String, String> mapsDetail = objectMapper.readValue(mapsFlightData.get("json_data"), Map.class);
                geAlipayAirinfo.setFlightno(mapsDetail.get("flightNo"));
                geAlipayAirinfo.setAirtakeoff(
                        dateConverter.convert(mapsDetail.get("airTakeOff"),"yyyy-MM-dd HH:mm:ss")
                );
                geAlipayAirinfo.setEffectstarttime(
                        dateConverter.convert(mapsEffectData.get("effect_start_time"),"yyyy-MM-dd HH:mm:ss")
                );
                geAlipayAirinfo.setEffectendtime(
                        dateConverter.convert(mapsEffectData.get("effect_end_time"),"yyyy-MM-dd HH:mm:ss")
                );
            } catch (Exception e) {
                LOGGER.info("geAlipayAirinfo:{},error:{}",geAlipayAirinfo.getPolicyno(),e);
            }
        }
        return pageInfo.getList();
    }



}
