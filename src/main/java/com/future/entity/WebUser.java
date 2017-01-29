package com.future.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Administrator on 2017/1/28.
 */
public class WebUser {
    private static Logger LOGGER = LogManager.getLogger(WebUser.class);
    private String userName;
    private String passwd;
    private int credits;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        return "WebUser{" +
                "userName='" + userName + '\'' +
                ", passwd='" + passwd + '\'' +
                '}';
    }

    public void init() {
        LOGGER.info("xml init method called");
    }

    public void destroy() {
        LOGGER.info("xml destroy method called");
    }
}
