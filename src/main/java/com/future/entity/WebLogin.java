package com.future.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/1/28.
 */
@Service("WebLogin")
@DependsOn("WebUser")
public class WebLogin {
    @Autowired
    private WebUser webUser;
    private String loginTime;
    @Value("${defaultRole}")
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

    @Override
    public String toString() {
        return "WebLogin{" +
                "webUser=" + webUser +
                ", loginTime='" + loginTime + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
