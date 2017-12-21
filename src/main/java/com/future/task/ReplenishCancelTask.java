package com.future.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.dao4ora.po.GeAlipayAirinfo;
import com.future.dao4ora.po.GeProposalMain;
import com.future.dao4ora.service.GeAlipayAirinfoService;
import com.future.dao4ora.service.GeProposalService;
import com.future.util.DateConverter;
import com.future.util.PropertiesMapUtil;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.google.common.io.Files;
import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengming on 17/10/1.
 */
@Component
public class ReplenishCancelTask {
    private static Logger LOGGER = LogManager.getLogger(ReplenishCancelTask.class);
    @Autowired
    private GeProposalService geProposalService;
    @Autowired
    private GeAlipayAirinfoService geAlipayAirinfoService;
    @Value("${uploadFileDir}")
    private String uploadFileDir;
    @Value("${inputOmittedFileName}")
    private String inputOmittedFileName;
    @Value("${outputOmittedFileName}")
    private String outputOmittedFileName;

    public ReplenishCancelTask() {
        super();

    }

   /* public void initDayScope(String startDay, String endDay)
    {
        super.startDay=startDay;
        super.endDay=endDay;
    }*/

    public boolean run() {
        compare();
        Map map = (Map) PropertiesMapUtil.getProperty(null,null,"insuredcerttype");
        File inputFile = new File(
                ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(uploadFileDir)
                        + File.separator + inputOmittedFileName);


        File outputFile = new File(
                ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(uploadFileDir)
                        + File.separator + outputOmittedFileName);

        try {
            LineIterator iterator = FileUtils.lineIterator(inputFile, "gbk");
            while (iterator.hasNext()) {
                String tborderid = iterator.next().split("\\,")[0];
                Integer i = null;

                try {
                    i = geAlipayAirinfoService.countOmittedAll(tborderid);
                } catch (Exception e) {
                    LOGGER.error("countOmittedAll error:{}", e.getMessage());
                    FileUtils.writeStringToFile(outputFile,
                            "error data for " + tborderid + System.lineSeparator(), true);
                    continue;
                }
                if (i == null || i < 1) {
                    List<GeProposalMain> list = null;
                    try {
                        list = geProposalService.selectOmittedByOrderId(tborderid);
                    } catch (Exception e) {
                        LOGGER.error("countOmittedAll error:{}", e.getMessage());
                        FileUtils.writeStringToFile(outputFile,
                                "error data for " + tborderid + System.lineSeparator(), true);
                        continue;
                    }

                    if (list == null || list.size() < 1) {
                        List<GeAlipayAirinfo> airinfoList = null;
                        try {
                            airinfoList = geAlipayAirinfoService.selectOmittedByOrderId(tborderid);
                        } catch (Exception e) {
                            LOGGER.error("selectOmittedByOrderId error:{}", e.getMessage());
                            FileUtils.writeStringToFile(outputFile,
                                    "error data for " + tborderid + System.lineSeparator(), true);
                            continue;
                        }
                        if(airinfoList == null || airinfoList.size() < 1)
                        {
                            FileUtils.writeStringToFile(outputFile,
                                    "no data for " + tborderid + System.lineSeparator(), true);
                            continue;
                        }else{
                            for(GeAlipayAirinfo geAlipayAirinfo : airinfoList)
                            {
                                FileUtils.writeStringToFile(outputFile, geAlipayAirinfo.getPolicyno() + ","
                                        + geAlipayAirinfo.getProposalno() + ","
                                        + geAlipayAirinfo.getPremium() + ","
                                        + geAlipayAirinfo.getInsuredcertname() + ","
                                        + geAlipayAirinfo.getInsuredcertno() + ","
                                        + map.get(geAlipayAirinfo.getInsuredcerttype())
                                        + System.lineSeparator(), true);
                            }
                        }


                    }else {
                        for (GeProposalMain geProposalMain : list) {
                            FileUtils.writeStringToFile(outputFile, geProposalMain.getTborderid() + ","
                                    + geProposalMain.getPolicyno() + ","
                                    + geProposalMain.getSumpremium() + ","
                                    + geProposalMain.getGeQuoteParty().getPartyname() + ","
                                    + geProposalMain.getGeQuoteParty().getIdentifynumber() + ","
                                    + geProposalMain.getGeQuoteParty().getIdentifytype()
                                    + System.lineSeparator(), true);
                        }

                    }

                } else {

                    FileUtils.writeStringToFile(outputFile,
                            "reported data for " + tborderid + System.lineSeparator(), true);
                }
                LOGGER.info("next line:{}", tborderid);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean compare() {
        File localFile = new File(
                ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(uploadFileDir)
                        + File.separator + "local.txt");


        File remoteFile = new File(
                ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(uploadFileDir)
                        + File.separator + "remote.txt");


        File outputFile = new File(
                ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(uploadFileDir)
                        + File.separator + inputOmittedFileName);

        try {
            LineIterator localIterator = FileUtils.lineIterator(localFile, "gbk");
            LineIterator remoteIterator = FileUtils.lineIterator(remoteFile, "gbk");
            String localId = (localIterator.hasNext())?localIterator.next():null;
            String remoteId = (remoteIterator.hasNext())?remoteIterator.next():null;
            while(localId!=null && remoteId!=null)
            {
                if(compareId(localId,remoteId)<0)
                {
                    localId = (localIterator.hasNext())?localIterator.next():null;
                }else if(compareId(localId,remoteId)>0){
                    //remoteid写入输出文件
                    FileUtils.writeStringToFile(outputFile,
                            remoteId + System.lineSeparator(), true);
                    //remoteFile下移一行
                    remoteId = (remoteIterator.hasNext())?remoteIterator.next():null;
                }else{
                    //remoteFile下移一行
                    remoteId = (remoteIterator.hasNext())?remoteIterator.next():null;
                    localId = (localIterator.hasNext())?localIterator.next():null;
                }
            }

            if(localId==null)
            {
                //遍历剩余的remoteIterator,写入输出文件
                while(remoteIterator.hasNext())
                {
                    FileUtils.writeStringToFile(outputFile,
                            remoteIterator.next() + System.lineSeparator(), true);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public int compareId(String id1, String id2)
    {
        boolean isId1Null = Strings.isNullOrEmpty(id1);
        boolean isId2Null = Strings.isNullOrEmpty(id2);

        if(isId1Null&&isId2Null)
        {
            return 0;
        }else if(isId1Null&&!isId2Null)
        {
            return -1;
        }else if(!isId1Null&&isId2Null)
        {
            return 1;
        }
        char[] ch1 = id1.toCharArray();
        char[] ch2 = id2.toCharArray();
        if(ch1.length>ch2.length)
        {
            return 1;
        }else if(ch1.length<ch2.length)
        {
            return 2;
        }else{
            for(int i=0;i<ch1.length;i++)
            {
                if(ch1[i]==ch2[i])continue;
                if(ch1[i]>ch2[i])
                    return 1;
                else
                    return -1;
            }
        }
        return 0;
    }

}
