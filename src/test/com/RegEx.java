package com;

import com.future.annotation.MappingConfig;
import com.future.dao.po.ProjectInfo;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhengming on 17/2/16.
 */
public class RegEx {
    @Test
    public void test()
    {
        ProjectInfo projectInfo = new ProjectInfo();
        try {
            Field f = projectInfo.getClass().getDeclaredField("projectName");
            System.out.println("locationPath:"+f.getAnnotation(MappingConfig.class).locationPath()[0]
                    +";"+f.getAnnotation(MappingConfig.class).mappingKey()[1]);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        String[] columnkeyArray = "address.tel".split("\\.");
        for(String str:columnkeyArray){

            System.out.println(str);
        }
        String[] columnkeyArray2 = "address".split("\\.");
        for(String str:columnkeyArray2){

            System.out.println(str);
        }
        int i = 5099/100;
        int j = 5037%100;
        int m = 68/100;

        System.out.println(i);
        System.out.println(j);
        System.out.println(m);

        String regEx = "\\d{2}";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher("ac");
        System.out.println(matcher.matches());
    }

    @Test
    public void test2()
    {
        String str = "你好";

        System.out.println(str.getBytes().length);
    }
}
