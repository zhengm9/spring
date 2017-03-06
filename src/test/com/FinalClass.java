package com;

import com.future.controller.RestfulController;
import com.future.entity.User;
import com.future.exception.MyRunTimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ContextLoader;

import java.util.*;

/**
 * Created by zhengming on 17/2/18.
 */
public class FinalClass {
    //    private final RegEx regEx ;
    private final String str2 = null;
    private static Logger LOGGER = LogManager.getLogger(FinalClass.class);
    @Test
    public void test() {
        Map<String,String> map =new HashMap();
        TreeSet<String> treeSet = new TreeSet();
        treeSet.add("1");
        String str = "test";
        int i = 3;
        Integer j = 3;
        change(str);
        change(i);
        change(j);
//        regEx = new RegEx();
        System.out.println(str);
        System.out.println(i);
        System.out.println(j);

    }

    void change(String str) {
//        str = "new test";
//        str = str + "new";
//        str = String.valueOf("new test");
        str = new String("new test");
    }

    void change(int i) {
        i = 50;
    }

    void change(Integer i) {
        i = 50;
    }

    @Test
    public void test2() {
        StringBuffer s = new StringBuffer("good");
        StringBuffer s2 = new StringBuffer("bad");
        testnew(s, s2);
        System.out.println(s);//9
        System.out.println(s2);//10
    }

    public void testnew(StringBuffer s, StringBuffer s2) {
        s.append("test");
        System.out.println(s);//1
        System.out.println(s2);//2
        s2 = s;//3
        s = new StringBuffer("new");//4
        System.out.println(s);//5
        System.out.println(s2);//6
        s.append("hah");//7
//        s2.append("hah");//8
    }

    @Test
    public void treeMap() {

        TreeMap<User, Integer> treeMap = new TreeMap<User, Integer>(new Comparator<User>() {
            public int compare(User o1, User o2) {

                if (o1.getCredits() > o2.getCredits()) {
                    return 1;
                } else if (o1.getCredits() < o2.getCredits()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        List<User> list = new ArrayList();
        User user1 = (User) ContextLoader.getCurrentWebApplicationContext().getBean("user");
        User user2 = (User) ContextLoader.getCurrentWebApplicationContext().getBean("user");
        User user3 = (User) ContextLoader.getCurrentWebApplicationContext().getBean("user");
        User user4 = (User) ContextLoader.getCurrentWebApplicationContext().getBean("user");
        User user5 = (User) ContextLoader.getCurrentWebApplicationContext().getBean("user");

        user1.setCredits(1);
        user2.setCredits(6);
        user3.setCredits(3);
        user4.setCredits(4);
        user5.setCredits(5);

        list.add(user1);
        list.add(user2);
        list.add(user3);
        Collections.sort(list);

        treeMap.put(user1, 1);
        treeMap.put(user2, 2);
        treeMap.put(user3, 3);
        treeMap.put(user4, 4);
        treeMap.put(user5, 5);

        LOGGER.info("headMap:{}", treeMap.headMap(user3));
        LOGGER.info("tailMap:{}", treeMap.tailMap(user3));
        LOGGER.info("subMap:{}", treeMap.subMap(user2, user3));
//        LOGGER.info("headMap:{}", treeMap.subMap(user3, user2));


        TreeMap<User, Integer> treeMap2 = new TreeMap<User, Integer>();

        for (User user : treeMap.keySet()) {
            LOGGER.info("treemap key:{}, object:{}", user.getCredits(), treeMap.get(user));
        }
    }

    @Test
    public void testRunTime()
    {
        catchException();
    }

    public void catchException()
    {
        System.out.println("start");
        throw new MyRunTimeException("test error");
    }

    public void catchNewException()
    {
        catchException();
    }

    @Test
    public void testString()
    {
        String a= "abc";
        String b = "abc";
        String c = new String("abc");
        String d = "ab" + "c";
        System.out.println(a==b);
        System.out.println(a==c);
        System.out.println(a==d);
        System.out.println(b==c);
        System.out.println(b==d);
        System.out.println(c==d);

    }

}
