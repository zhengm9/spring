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
        String[] columnkeyArray = splitColumnkey(columnkey);
        Object fieldObject = null;
        if(columnkeyArray.length>1)
        {
            fieldObject = PropertyUtils.getNestedProperty(object,columnkey);
        }else {
            fieldObject = PropertyUtils.getProperty(object,columnkey);
        }
        Object configPropertyObject = getFieldConfigProperty(object,columnkeyArray[0],
                                                                    columnkey);

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

    public static Object getFieldConfigProperty(Object object, String firstColumnkey,
                                                String finalColumnkey)
            throws NoSuchFieldException, IllegalAccessException,
                    NoSuchMethodException, InvocationTargetException
    {
        LOGGER.debug("firstColumnkey:[{}], finalColumnkey:[{}]",firstColumnkey,finalColumnkey);

        MappingConfig mappingConfig = object.getClass().getDeclaredField(firstColumnkey)
                .getDeclaredAnnotation(MappingConfig.class);

//        object.getClass().getDeclaredField(columnkey).getDeclaringClass().
        if(mappingConfig == null)
        {
            LOGGER.debug("there is no mappingConfig annotaion in field [{}]",firstColumnkey);
            return null;
        }else{
            return getConfigProperty(mappingConfig, finalColumnkey);
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
            return getConfigProperty(mappingConfig, null);
        }
    }

    public static Object getConfigProperty(MappingConfig mappingConfig, String finalColumnkey)
            throws NoSuchFieldException, IllegalAccessException,
            NoSuchMethodException, InvocationTargetException
    {
        LOGGER.debug("mappingConfig.locationPath:{}, mappingConfig.fileName:{}, mappingConfig.mappingKey:{}"
                ,mappingConfig.locationPath().length,mappingConfig.fileName().length
                ,mappingConfig.mappingKey().length);
        String mappedKey = null;
        int index = 0;
        for(String key : mappingConfig.mappingKey())
        {
            if(key.equals(finalColumnkey))
            {
                mappedKey=key;

                break;
            }
            index++;
        }
        Object configProperty = PropertiesMapUtil.getProperty
                ((mappingConfig.locationPath().length<=1)?mappingConfig.locationPath()[0]:mappingConfig.locationPath()[index],
                        (mappingConfig.fileName().length<=1)?mappingConfig.fileName()[0]:mappingConfig.fileName()[index],
                            mappingConfig.mappingKey()[index]);
        return configProperty;
    }

    private static String[] splitColumnkey(String columnkey)
    {
        String[] columnkeyArray = columnkey.split("\\.");
        return columnkeyArray;
    }

}
