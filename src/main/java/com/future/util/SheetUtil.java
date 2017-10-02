package com.future.util;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

/**
 * Created by zhengming on 17/10/2.
 */
public class SheetUtil {
    public static void test(HSSFSheet sheet)
    {
        HSSFRow row=sheet.createRow(0);
        row.createCell(0).setCellValue("test");

    }
}
