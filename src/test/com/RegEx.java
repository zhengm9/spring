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
        String regEx = "\\d{2}";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher("ac");
        System.out.println(matcher.matches());
    }
}
