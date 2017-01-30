package com.future.controller;

import com.future.entity.User;
import com.future.entity.UserDetails;
import com.future.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ContextLoader;

/**
 * Created by Administrator on 2017/1/30.
 */
@Controller
public class RestfulController {
    private static Logger LOGGER = LogManager.getLogger(RestfulController.class);

    @RequestMapping(value = "/restful/update/{username}", method = RequestMethod.PUT)
    public ResponseEntity<User> update(@PathVariable String username, @RequestBody User updateUser)
    {
        LOGGER.info("request username:{}, updateUser:{}", username, updateUser);
        updateUser.setUserName(username);
        UserDetails userDetails = new UserDetails();
        userDetails.setAddress("中华人民共和国");
        userDetails.setMobile("010-66522597");
        updateUser.setUserDetails(userDetails);
        return new ResponseEntity<User>(updateUser, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/restful/query/{userId}", method = RequestMethod.GET)
    public ResponseEntity<com.future.dao.po.User> query(@PathVariable String userId)
    {
        LOGGER.info("request username:{}", userId);
        UserService userService = (UserService) ContextLoader.getCurrentWebApplicationContext().getBean("userService");
        com.future.dao.po.User user = userService.getUserById(Integer.parseInt(userId));
        return new ResponseEntity<com.future.dao.po.User>(user, HttpStatus.OK);
    }
}
