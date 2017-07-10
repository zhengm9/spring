package com.future.biz.worker;

import com.future.controller.RestfulController;
import com.future.dao.po.GeProposalMain;
import com.future.service.GeProposalService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.ContextLoader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by zhengming on 2017/6/30.
 */
public class AliAir1800Worker implements Callable<Integer>{
    private static Logger LOGGER = LogManager.getLogger(RestfulController.class);
    private int startLine;
    private int endLine;
    private Integer writeLine;
    private GeProposalService geProposalService = (GeProposalService) ContextLoader.getCurrentWebApplicationContext().getBean("geProposalService");
    private List<String> lines;
    public AliAir1800Worker(List<String> lines, int startLine, int endLine, Integer writeLine) {
        this.startLine = startLine;
        this.endLine = endLine;
        this.writeLine = writeLine;
        this.lines = lines;
    }

    public Integer call() throws Exception {
            GeProposalMain geProposalMain = new GeProposalMain();
            int i = 0;
            StringBuffer stringBuffer = new StringBuffer();

            for (int tmp = startLine;tmp<=endLine;tmp++)
            {
                LOGGER.info("current line:{}", tmp);
                geProposalMain.setProposalno(lines.get(tmp).split("\\,")[5]);
                geProposalMain = geProposalService.selectForAliAir(geProposalMain);
                if(geProposalMain != null)
                {
                    stringBuffer.append(lines.get(tmp) +  ","
                            + geProposalMain.getStatus() + "," + geProposalMain.getReason()+"\r");
                }else {
                    stringBuffer.append(lines.get(tmp) +  ",404,not found" +"\r");
                    geProposalMain = new GeProposalMain();
                }
            }
        File targetFile = new File("C:\\Users\\zhengming\\Desktop\\"+startLine+"_"+endLine+".csv");
        FileUtils.writeStringToFile(targetFile, stringBuffer.toString(), true);


          /*while(true)
            {
                if(writeLine.intValue() == startLine)
                {
                    File targetFile = new File("C:\\Users\\zhengming\\Desktop\\1800end_multi.csv");

                    FileUtils.writeStringToFile(targetFile, stringBuffer.toString(), true);
                    writeLine = new Integer(endLine+1);
                    LOGGER.info("writeLine:{}", writeLine);
                    break;
                }
            }*/

        return 0;
    }
}
