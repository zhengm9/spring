package com.future.biz;

import com.future.biz.worker.AliAir1800Worker;
import com.future.controller.RestfulController;
import com.future.dao.po.GeProposalMain;
import com.future.service.GeProposalService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhengming on 2017/6/30.
 */
public class AliAir1800 {
    private static Logger LOGGER = LogManager.getLogger(RestfulController.class);
    private Integer cur = new Integer(0);
    private String sourceFile = "C:\\Users\\zhengming\\Desktop\\1800.csv";
    private String targertFile = "C:\\Users\\zhengming\\Desktop\\1800end.csv";
    private GeProposalService geProposalService = (GeProposalService) ContextLoader.getCurrentWebApplicationContext().getBean("geProposalService");

    public void multyCount() {
        ExecutorService service = Executors.newFixedThreadPool(12);
        try {
            List<String> lines = FileUtils.readLines(new File(sourceFile));
            int workline = lines.size()/10;
            int i = 0 ;
            for(i=0 ; i<lines.size()-workline ; i++)
            {
                service.submit(new AliAir1800Worker(lines, i, i=(i+workline-1), cur));
            }
            service.submit(new AliAir1800Worker(lines, i, lines.size()-1, cur));
        } catch (IOException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }

    public void count()
    {
        GeProposalService geProposalService = (GeProposalService) ContextLoader.getCurrentWebApplicationContext().getBean("geProposalService");
        LineIterator readLineIterator = null;

        try {
             readLineIterator =
                    FileUtils.lineIterator(new File("C:\\Users\\zhengming\\Desktop\\1800.csv"));
            File targetFile = new File("C:\\Users\\zhengming\\Desktop\\1800end.csv");
            String line = null;
            String proposalNo = null;
            String reason = null;
            String status = null;
            GeProposalMain geProposalMain = new GeProposalMain();
            int i = 0;
            while(readLineIterator.hasNext())
            {
                LOGGER.info("line number:{}", i++);

                line = readLineIterator.next();
                geProposalMain.setProposalno(line.split("\\,")[5]);
                geProposalMain = geProposalService.selectForAliAir(geProposalMain);
                if(geProposalMain != null)
                {
                    FileUtils.writeStringToFile(targetFile, line +  ","
                            + geProposalMain.getStatus() + "," + geProposalMain.getReason()+"\r", true);
                }else {
                    FileUtils.writeStringToFile(targetFile, line +  ",404,not found" +"\r", true);
                    geProposalMain = new GeProposalMain();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            readLineIterator.close();
        }

    }
}
