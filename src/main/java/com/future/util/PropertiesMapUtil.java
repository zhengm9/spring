package com.future.util;

import com.google.common.base.Strings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.ContextLoader;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zhengming on 17/10/5.
 */
public class PropertiesMapUtil {
    private static Logger LOGGER = LogManager.getLogger(PropertiesMapUtil.class);

    private static final Map<String,Object> propertiesMap = new HashMap();//key format:"keyname@filename@filepath"
    public static final String defaultPropertyFilePath = "/WEB-INF/classes/springproperties";
    public static final String defaultPropertyFileName = "reportmap.properties";
    public static byte[] writeLock = new byte[0];

    /*public static Object getProperty(String filename, String keyname)
    {
        return getProperty(defaultPropertyFilePath, filename, keyname);
    }*/

    public static Object getProperty(String relativeFilePath, String filename, String keyname)
    {
        if(Strings.isNullOrEmpty(relativeFilePath))
            relativeFilePath = defaultPropertyFilePath;
        if(Strings.isNullOrEmpty(filename))
            filename = defaultPropertyFileName;

        Object value = propertiesMap.get(keyname+"@"+filename+"@"+relativeFilePath);
        if(value==null)
        {
            value = readProperty(relativeFilePath, filename, keyname);
        }
        return propertiesMap.get(keyname+"@"+filename+"@"+relativeFilePath);
    }

    private static Object readProperty(String filename, String keyname)
    {
        return readProperty(defaultPropertyFilePath, filename, keyname);
    }

    private static Object readProperty(String relativeFilePath, String filename, String keyname)
    {
        return readProperty(defaultPropertyFilePath, filename, keyname, new PropertiesMapUtil.Converter() {
            public Object convert(String propertyTxt) {
                try {
                    return JsonConverter.convertJson2Map(propertyTxt);
                } catch (IOException e) {
                    LOGGER.error(e);
                }
                return null;
            }
        });
    }

    private static Object readProperty(String relativeFilePath, String filename, String keyname,
                                            PropertiesMapUtil.Converter converter)
    {
        synchronized (writeLock)
        {
            Properties prop = new Properties();
            String pathName = ContextLoader.getCurrentWebApplicationContext().getServletContext()
                    .getRealPath(relativeFilePath)+ File.separator + filename;
            try{
                //读取属性文件a.properties
                InputStream in = new BufferedInputStream(new FileInputStream(pathName));
                prop.load(new InputStreamReader(in, "utf-8"));     //加载属性列表
                Iterator<String> it=prop.stringPropertyNames().iterator();
                while(it.hasNext()){
                    String key=it.next();
                    LOGGER.debug("key:{},value:{}",key, prop.getProperty(key));
                    if(converter == null)
                    {
                        propertiesMap.put(key+"@"+filename+"@"+relativeFilePath,prop.getProperty(key));
                    }else
                    {
                        propertiesMap.put(key+"@"+filename+"@"+relativeFilePath,converter.convert(prop.getProperty(key)));
                    }
                }
                in.close();
            }
            catch(Exception e){
                LOGGER.error(e);
            }

            return propertiesMap.get(keyname+"@"+filename+"@"+relativeFilePath);
        }
    }

    public interface Converter
    {
        Object convert(String propertyTxt);
    }
}
