package com.future.task;

import com.future.dao4ora.po.GeAlipayAirinfo;
import com.future.dao4ora.service.GeAlipayAirinfoService;
import com.github.pagehelper.PageInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zhengming on 17/10/1.
 */
@Component
public class ReportCancelTask extends AbstractReportTask<GeAlipayAirinfo>{
    private static Logger LOGGER = LogManager.getLogger(ReportCancelTask.class);
    @Autowired
    private GeAlipayAirinfoService geAlipayAirinfoService;


    public ReportCancelTask()
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

        Integer i = this.geAlipayAirinfoService.countAllCancel(startDay,endDay);

        return i;
    }

    public List<GeAlipayAirinfo> getSqlResults(int pageNum, int pageSize) {


        PageInfo<GeAlipayAirinfo> pageInfo =
                this.geAlipayAirinfoService.selectCancelByMakedateAndPage(startDay,endDay,
                pageNum, pageSize);
        LOGGER.info("page info:pageNum-{},pageSize-{},isFirstPage-{},totalPages-{},isLastPage-{}",
                pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.isIsFirstPage(), pageInfo.getPages(), pageInfo.isIsLastPage());

        return pageInfo.getList();
    }

    /*public List<String> initColumnkeys() {
        List<String> columnkeys = new ArrayList<String>();

        columnkeys.add("productname");
        columnkeys.add("policyno");
        columnkeys.add("proposalno");
        columnkeys.add("makedate");

        return columnkeys;
    }*/

   /* public List<String> initWorkBookFileName(int workBookNum) {
        List<String> workBookFileNames = new ArrayList<String>();
        for(int i=0;i<workBookNum;i++)
        {
            workBookFileNames.add("cancel-"+startDay+"-"+i+".xls");
        }
        return workBookFileNames;
    }*/

}
