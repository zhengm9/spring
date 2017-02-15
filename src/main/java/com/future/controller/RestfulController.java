package com.future.controller;

import com.future.annotationservice.AnnotationUserService;
import com.future.entity.User;
import com.future.entity.UserDetails;
import com.future.service.UserService;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ContextLoader;

import java.util.*;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/1/30.
 */
@Controller
public class RestfulController {
    private static Logger LOGGER = LogManager.getLogger(RestfulController.class);

    @RequestMapping(value = "/restful/update/{username}", method = RequestMethod.PUT)
    public ResponseEntity<User> update(@PathVariable String username, @RequestBody User updateUser) {
        LOGGER.info("request username:{}, updateUser:{}", username, updateUser);
        updateUser.setUserName(username);
        UserDetails userDetails = new UserDetails();
        userDetails.setAddress("中华人民共和国");
        userDetails.setMobile("010-66522597");
        updateUser.setUserDetails(userDetails);
        return new ResponseEntity<User>(updateUser, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/restful/query/{userId}", method = RequestMethod.GET)
    public ResponseEntity<com.future.dao.po.User> query(@PathVariable String userId) {
        LOGGER.info("request username:{}", userId);
        UserService userService = (UserService) ContextLoader.getCurrentWebApplicationContext().getBean("userService");
        com.future.dao.po.User user = userService.getUserById(Integer.parseInt(userId));
        return new ResponseEntity<com.future.dao.po.User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/restful/insertDefault", method = RequestMethod.POST)
    public ResponseEntity<com.future.dao.po.User> insertDefault() {
        LOGGER.info("request insertDefault update:{}");
        UserService userService = (UserService) ContextLoader.getCurrentWebApplicationContext().getBean("userService");
        com.future.dao.po.User user = (com.future.dao.po.User) ContextLoader.getCurrentWebApplicationContext().getBean("userPo");
        user.setUserName("test");
        user.setAge(11);
        user.setPassword("123");
        userService.insertDefault(user);

        return new ResponseEntity<com.future.dao.po.User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/restful/annotationInsertDefault", method = RequestMethod.GET)
    public ResponseEntity<com.future.dao.po.User> insertAnnotationDefault() {
        LOGGER.info("request annotationInsertDefault:{}");
        AnnotationUserService userService = (AnnotationUserService) ContextLoader.getCurrentWebApplicationContext().getBean("annotationUserService");
        com.future.dao.po.User user = (com.future.dao.po.User) ContextLoader.getCurrentWebApplicationContext().getBean("userPo");
        user.setUserName("test");
        user.setAge(11);
        user.setPassword("123");
        userService.insertTwice(user);
        userService.insertDefault(user);

        return new ResponseEntity<com.future.dao.po.User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/restful/mysql/transaction", method = RequestMethod.GET)
    public ResponseEntity<com.future.dao.po.User> multiThread() {
        LOGGER.info("request annotationInsertDefault:{}");
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(100));

        AnnotationUserService userService = (AnnotationUserService) ContextLoader.getCurrentWebApplicationContext().getBean("annotationUserService");
        com.future.dao.po.User user = (com.future.dao.po.User) ContextLoader.getCurrentWebApplicationContext().getBean("userPo");
        user.setUserName("test");
        user.setAge(11);
        user.setPassword("123");
        userService.insertTwice(user);
        userService.insertDefault(user);

        return new ResponseEntity<com.future.dao.po.User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/restful/collection/treemap", method = RequestMethod.GET)
    public ResponseEntity<List<User>> treeMap() {

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
        return new ResponseEntity<List<User>>(list, HttpStatus.OK);
    }

}
