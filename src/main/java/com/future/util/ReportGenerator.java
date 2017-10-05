package com.future.util;

import com.future.dao.po.GeAlipayAirinfo;
import com.google.common.base.Strings;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhengming on 17/10/2.
 */
@Service("reportGenerator")
public class ReportGenerator {
    private static Logger LOGGER = LogManager.getLogger(ReportGenerator.class);
    @Value("${sqlReadPageSize}")
    private int sqlReadPageSize;
    @Value("${excelSheetSize}")
    private int excelSheetSize;

    public boolean genWorkBook(String filePath, String fileName, String sheetName
                                                                , List<Object> objects
                                                                ,List<String> columnkeys)
            throws InvocationTargetException, NoSuchMethodException,
            NoSuchFieldException, IllegalAccessException, IOException
    {
        LOGGER.info("excelSheetSize:{}",excelSheetSize);

        String pathName = filePath+File.separator+fileName;
        LOGGER.info("file path is:{}",pathName);
        File file = new File(pathName);
        return genWorkBook(file.toURI(),sheetName,objects,columnkeys);
    }

    public boolean genWorkBook(URI uri, String sheetName, List<Object> objects
                                                        ,List<String> columnkeys)
                            throws InvocationTargetException, NoSuchMethodException,
                             NoSuchFieldException, IllegalAccessException, IOException
    {
        LOGGER.info("file uri is:{}",uri);
        File xlsFile = new File(uri);
        HSSFWorkbook workbook = null;
        if(xlsFile.exists())
        {
            LOGGER.info("file exists!");
            POIFSFileSystem ps= null;  //使用POI提供的方法得到excel的信息
            ps = new POIFSFileSystem(xlsFile);
            workbook=new HSSFWorkbook(ps);
        }else
        {
            // 创建工作薄
            workbook = new HSSFWorkbook();
        }

        // 创建工作表
        int sheetNum = workbook.getNumberOfSheets();
        HSSFSheet sheet = null;
        if(sheetNum == 0)
        {
            LOGGER.info("there is no sheet in this workbook.");
            if(Strings.isNullOrEmpty(sheetName))
            {
                sheetName="sheet1";
            }
            sheet = workbook.createSheet(sheetName);
            HSSFRow row = sheet.createRow(0);
            Integer curColumnNum = 0;
            Object notNullObject = null;
            for (Object o : objects)
            {
                if(o!=null)
                {
                    notNullObject = o;
                    break;
                }
            }
            for(String columnkey : columnkeys)
            {
                HSSFCell cell = row.createCell(curColumnNum++);
                cell.setCellValue(SheetUtil.getSheetHead(notNullObject,columnkey));
            }

        }else{
            sheet = workbook.getSheetAt(0);
        }

        Integer curRowNum = sheet.getLastRowNum();
        LOGGER.info("curRowNum:{}",curRowNum);

        SheetUtil.addRow2Sheet(sheet, objects, columnkeys, curRowNum);

        FileOutputStream xlsStream = null;
        xlsStream = new FileOutputStream(xlsFile);
        workbook.write(xlsStream);
        LOGGER.info("write file finish.");

        return true;
    }
}
