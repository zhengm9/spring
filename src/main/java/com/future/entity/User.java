package com.future.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * Created by Administrator on 2017/1/28.
 */
@Service("user")
@Scope("prototype")
public class User implements Comparable<User>{
    private static Logger LOGGER = LogManager.getLogger(User.class);
    private String userName;
    private String passwd;
    private int credits;
    private String country;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    private Gender gender;
    private MultipartFile file;
    private UserDetails userDetails;

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void init() {
        LOGGER.info("xml init method called");
    }

    public void destroy() {
        LOGGER.info("xml destroy method called");
    }

    public int compareTo(User other) {
       if(this.credits > other.credits)
       {
           return -1;
       }else if(this.credits < other.credits)
       {
           return 1;
       }else {
           return 0;
       }

    }

    public enum Gender {
        Male, Female;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", passwd='" + passwd + '\'' +
                ", credits=" + credits +
                ", country='" + country + '\'' +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", file=" + file +
                ", userDetails=" + userDetails +
                '}';
    }
}
