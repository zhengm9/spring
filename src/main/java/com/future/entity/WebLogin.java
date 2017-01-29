package com.future.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by Administrator on 2017/1/28.
 */
@Service("WebLogin")
@Scope("request")//todo:request作用域的destroy方法在http请求结束时调用
@Lazy(false)
@DependsOn("WebUser")
public class WebLogin {
    private static Logger LOGGER = LogManager.getLogger(WebLogin.class);

    @Autowired
    private WebUser webUser;
    private String loginTime;
    @Value("${defaultRole}")    //todo:读取配置文件中的属性
    // todo:参见<context:property-placeholder location="WEB-INF/classes/springproperties/webuser.properties"/>
    private String role;

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @PostConstruct
    public void init()
    {
        LOGGER.info("annotation init method called.");
    }

    @PreDestroy
    public void destroy()
    {
        LOGGER.info("annotation destroy method called.");
    }
    @Override
    public String toString() {
        return "WebLogin{" +
                "webUser=" + webUser +
                ", loginTime='" + loginTime + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
