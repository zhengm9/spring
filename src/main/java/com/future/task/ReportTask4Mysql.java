package com.future.task;

import com.future.dao.po.ProjectInfo;
import com.future.dao.service.ProjectInfoService;
import com.github.pagehelper.PageInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengming on 17/10/1.
 */
@Component
public class ReportTask4Mysql extends AbstractReportTask<ProjectInfo>{
    private static Logger LOGGER = LogManager.getLogger(ReportTask4Mysql.class);
    @Autowired
    private ProjectInfoService projectInfoService;

    public ReportTask4Mysql()
    {
        super();

    }

    public int getSqlCount() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("startdate","2017-07-01");
        map.put("enddate","2017-07-14");
        Integer i = this.projectInfoService.countSelectAll(map);

        return i;
    }

    public List<ProjectInfo> getSqlResults(int pageNum, int pageSize) {
        PageInfo<ProjectInfo> pageInfo =
                this.projectInfoService.selectByDateAndPage("2017-07-01", "2017-07-14",
                pageNum, pageSize);
        LOGGER.info("page info:pageNum-{},pageSize-{},isFirstPage-{},totalPages-{},isLastPage-{}",
                pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.isIsFirstPage(), pageInfo.getPages(), pageInfo.isIsLastPage());

        return pageInfo.getList();
    }

    public List<String> initColumnkeys() {
        List<String> columnkeys = new ArrayList<String>();
        columnkeys.add("projectName");
        columnkeys.add("requirementsReceivedDate");

        return columnkeys;
    }

    public List<String> initWorkBookFileName(int workBookNum, String reportName) {
        List<String> workBookFileNames = new ArrayList<String>();
        for(int i=0;i<workBookNum;i++)
        {
            workBookFileNames.add(reportName+"-"+i+".xls");
        }
        return workBookFileNames;
    }

}
