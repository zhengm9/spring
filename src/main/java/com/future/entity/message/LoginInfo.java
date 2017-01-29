package com.future.entity.message;

/**
 * Created by Administrator on 2017/1/29.
 */

import com.future.entity.WebLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("loginInfo")
public class LoginInfo {

    @Autowired
    private WebLogin webLogin;
    private Date loginTime;

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public WebLogin getWebLogin() {
        return webLogin;
    }
}