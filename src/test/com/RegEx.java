package com;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhengming on 17/2/16.
 */
public class RegEx {
    @Test
    public void test()
    {
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
