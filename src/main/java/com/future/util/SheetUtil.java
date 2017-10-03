package com.future.util;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.stereotype.Service;

/**
 * Created by zhengming on 17/10/2.
 */
@Service("sheetUtil")
public class SheetUtil {
    public static void test(HSSFSheet sheet)
    {
        HSSFRow row=sheet.createRow(0);
        row.createCell(0).setCellValue("test");

    }
}
