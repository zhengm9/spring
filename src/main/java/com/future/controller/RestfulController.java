package com.future.controller;

import com.future.annotationservice.AnnotationUserService;
import com.future.dao.po.GeProposalMain;
import com.future.entity.User;
import com.future.entity.UserDetails;
import com.future.facade.MQProducer;
import com.future.service.GeProposalService;
import com.future.service.UserService;
import com.future.service.mq.SendMsg2MQ;
import com.future.service.mq.producer.MQProducerImpl;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ContextLoader;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/1/30.
 */
@Controller
public class RestfulController {
    private static Logger LOGGER = LogManager.getLogger(RestfulController.class);
    @Autowired
    private SendMsg2MQ sendMsg2MQ;

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

    @RequestMapping(value = "/restful/mq/{msg}", method = RequestMethod.GET)
    public ResponseEntity<String> sendMQMsg(@PathVariable String msg) {
        LOGGER.info("request message:{}, updateUser:{}", msg);
        sendMsg2MQ.send("test_queue_key_1_error", msg);
        sendMsg2MQ.send("test_queue_key_2", msg);

        return new ResponseEntity<String>("OK", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/restful/query/{userId}", method = RequestMethod.GET)
    public ResponseEntity<com.future.dao.po.User> query(@PathVariable String userId) {
        LOGGER.info("request username:{}", userId);

        Mapping mapping = new Mapping();
        mapping = null;
        try {
//            mapping.loadMapping(mappingFilePath);
//            StringReader sr = new StringReader(xmlString);
            Unmarshaller unMarshaller = new Unmarshaller(mapping);
            unMarshaller.unmarshal(new StringReader("test"));
        } catch (MappingException e) {
            e.printStackTrace();
        } catch (MarshalException e) {
            e.printStackTrace();
        } catch (ValidationException e) {
            e.printStackTrace();
        }

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

    @RequestMapping(value = "/restful/oracle/ali", method = RequestMethod.POST)
    public ResponseEntity getGeProposalMain4Stat() {
        LOGGER.info("getGeProposalMain4Stat");
        GeProposalService geProposalService = (GeProposalService) ContextLoader.getCurrentWebApplicationContext().getBean("geProposalService");
//        GeProposalMain geProposalMain = geProposalService.selectForStat();
        geProposalService.selectForStat();

        return new ResponseEntity(null, HttpStatus.OK);
    }

}
