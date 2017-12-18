package com.future.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.dao4ora.po.GeAlipayAirinfo;
import com.future.dao4ora.po.GeProposalMain;
import com.future.dao4ora.service.GeAlipayAirinfoService;
import com.future.dao4ora.service.GeProposalService;
import com.future.util.DateConverter;
import com.github.pagehelper.PageInfo;
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
        File inputFile = new File(
                ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(uploadFileDir)
                        + File.separator + inputOmittedFileName);


        File outputFile = new File(
                ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(uploadFileDir)
                        + File.separator + outputOmittedFileName);

        try {
            LineIterator iterator = FileUtils.lineIterator(inputFile, "gbk");
            while (iterator.hasNext()) {
                String tborderid = iterator.next().split("\\,")[1];
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
                        FileUtils.writeStringToFile(outputFile,
                                "no data for " + tborderid + System.lineSeparator(), true);
                        continue;

                    }

                    for (GeProposalMain geProposalMain : list) {
                        FileUtils.writeStringToFile(outputFile, geProposalMain.getTborderid() + ","
                                + geProposalMain.getPolicyno() + ","
                                + geProposalMain.getSumpremium() + ","
                                + geProposalMain.getGeQuoteParty().getPartyname() + ","
                                + geProposalMain.getGeQuoteParty().getIdentifynumber() + ","
                                + geProposalMain.getGeQuoteParty().getIdentifytype()
                                + System.lineSeparator(), true);
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
//        geProposalService.selectOmittedByOrderId("1");
        return true;
    }


}
