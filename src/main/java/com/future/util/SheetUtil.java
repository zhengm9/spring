package com.future.util;

import com.future.annotation.MappingConfig;
import org.apache.commons.beanutils.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengming on 17/10/2.
 */
public class SheetUtil {
    private static Logger LOGGER = LogManager.getLogger(SheetUtil.class);

    public static void addRow2Sheet(HSSFSheet sheet, List<Object> objects,
                                    List<String> columnkeys)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException
    {

        Integer curRowNum = sheet.getLastRowNum();
        LOGGER.info("curRowNum:{}",curRowNum);
        for(Object object : objects)
        {
            HSSFRow row = sheet.createRow(++curRowNum);
            Integer curColumnNum = 0;
            for (String columnkey : columnkeys) {
                String cellValue = getFiledValueString(object, columnkey);
                row.createCell(curColumnNum++).setCellValue(cellValue);
                LOGGER.info("write workbook, row:{},col:{},value:{}",curRowNum,curColumnNum,cellValue);
            }
        }
    }

    public static String getFiledValueString(Object object, String columnkey) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Object fieldObject = PropertyUtils.getProperty(object,columnkey);
        Object configPropertyObject = getFieldConfigProperty(object,columnkey);

        if(configPropertyObject == null)
        {
            return (fieldObject == null)?"-":((fieldObject instanceof Date)?
                    DateConverter.formatDate((Date) fieldObject, "yyyy-MM-dd HH:mm:ss"):
                    fieldObject.toString());
        }else
        {
            LOGGER.debug("value is :{}",(fieldObject==null)
                    ?((Map)configPropertyObject).get("null")
                    :((Map)configPropertyObject).get((String)fieldObject));

            return (String)((fieldObject==null)?((Map)configPropertyObject).get("null")
                                                :((Map)configPropertyObject).get((String)fieldObject));

        }

    }

    public static String getSheetHead(Object object, String columnkey) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Object configPropertyObject = getClassTypeConfigProperty(object);

        if(configPropertyObject == null)
        {
            return columnkey;
        }else
        {
            String sheetHead = (String)((Map)configPropertyObject).get(columnkey);
            LOGGER.debug("columnkey in config property, value is :{}",sheetHead);
            return (sheetHead==null)?columnkey:sheetHead;

        }

    }

    public static Object getFieldConfigProperty(Object object, String columnkey)
            throws NoSuchFieldException, IllegalAccessException,
                    NoSuchMethodException, InvocationTargetException
    {
        MappingConfig mappingConfig = object.getClass().getDeclaredField(columnkey)
                .getDeclaredAnnotation(MappingConfig.class);

        if(mappingConfig == null)
        {
            LOGGER.debug("there is no mappingConfig annotaion in field [{}]",columnkey);
            return null;
        }else{
            return getConfigProperty(mappingConfig);
        }
    }

    public static Object getClassTypeConfigProperty(Object object)
            throws NoSuchFieldException, IllegalAccessException,
            NoSuchMethodException, InvocationTargetException
    {
        MappingConfig mappingConfig = object.getClass()
                .getDeclaredAnnotation(MappingConfig.class);

        if(mappingConfig == null)
        {
            LOGGER.debug("there is no mappingConfig annotaion in Class type [{}]",object.getClass());
            return null;
        }else{
            return getConfigProperty(mappingConfig);
        }
    }

    public static Object getConfigProperty(MappingConfig mappingConfig)
            throws NoSuchFieldException, IllegalAccessException,
            NoSuchMethodException, InvocationTargetException
    {
        LOGGER.debug("mappingConfig.locationPath:{}, mappingConfig.fileName:{}, mappingConfig.mappingKey:{}"
                ,mappingConfig.locationPath(),mappingConfig.fileName()
                ,mappingConfig.mappingKey());

        Object configProperty = PropertiesMapUtil.getProperty
                (mappingConfig.locationPath(), mappingConfig.fileName(), mappingConfig.mappingKey());
        return configProperty;
    }
}
