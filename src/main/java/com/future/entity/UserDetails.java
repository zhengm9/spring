package com.future.entity;

import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/1/30.
 */
@Service("userDetails")
public class UserDetails {
    private String address;
    private String mobile;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "address='" + address + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
