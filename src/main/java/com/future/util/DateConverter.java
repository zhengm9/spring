package com.future.util;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 全局日期处理类
 * Convert<T,S>
 * 泛型T:代表客户端提交的参数 String
 * 泛型S:通过convert转换的类型
 */
@Service
public class DateConverter implements Converter<String, Date> {
    public Date convert(String stringDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDate(String format)
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        Date date = new Date();
        return simpleDateFormat.format(date);

    }

    public static void main(String[] args)
    {
        String ticketNo_;
        ticketNo_= "1-2".replace("-", "");
        System.out.println(ticketNo_);
        Mapping mapping = new Mapping();
//        mapping = null;
        try {
                mapping.loadMapping(ticketNo_);
            StringReader sr = new StringReader(ticketNo_);
            Unmarshaller unMarshaller = new Unmarshaller(mapping);
            unMarshaller.unmarshal(new StringReader("<test></test>"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
