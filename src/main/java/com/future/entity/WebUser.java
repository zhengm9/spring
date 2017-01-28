package com.future.entity;

/**
 * Created by Administrator on 2017/1/28.
 */
public class WebUser {
    private String username;
    private String passwd;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public String toString() {
        return "WebUser{" +
                "username='" + username + '\'' +
                ", passwd='" + passwd + '\'' +
                '}';
    }
}
