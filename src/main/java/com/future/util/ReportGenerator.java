package com.future.util;

import com.google.common.base.Strings;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

/**
 * Created by zhengming on 17/10/2.
 */
public class ReportGenerator {
    private static Logger LOGGER = LogManager.getLogger(ReportGenerator.class);


    public static boolean genWorkBook(String filePath, String fileName, String sheetName)
    {
        String pathName = filePath+File.separator+fileName;
        LOGGER.info("file path is:{}",pathName);
        File file = new File(pathName);
        return genWorkBook(file.toURI(),sheetName);
    }

    public static boolean genWorkBook(URI uri, String sheetName)
    {
        LOGGER.info("file uri is:{}",uri);

        // 创建工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建工作表
        if(Strings.isNullOrEmpty(sheetName))
        {
            sheetName="sheet1";
        }
        HSSFSheet sheet = workbook.createSheet(sheetName);
        SheetUtil.test(sheet);


        File xlsFile = new File(uri);
        FileOutputStream xlsStream = null;
        try {
            xlsStream = new FileOutputStream(xlsFile);
        } catch (FileNotFoundException e) {
            LOGGER.error("write file error,e:{}",e.getMessage());
            return false;
        }
        try {
            workbook.write(xlsStream);
        } catch (IOException e) {
            LOGGER.error("write file error,e:{}",e.getMessage());
            return false;
        }
        LOGGER.info("write file finish.");

        return true;
    }
}
