package com.future.dao.po;

public class User {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_t.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_t.user_name
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_t.password
     *
     * @mbggenerated
     */
    private String password;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_t.age
     *
     * @mbggenerated
     */
    private Integer age;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_t.ver
     *
     * @mbggenerated
     */
    private Integer ver;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_t
     *
     * @mbggenerated
     */
    public User(Integer id, String userName, String password, Integer age, Integer ver) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.age = age;
        this.ver = ver;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_t
     *
     * @mbggenerated
     */
    public User() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_t.id
     *
     * @return the value of user_t.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_t.id
     *
     * @param id the value for user_t.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_t.user_name
     *
     * @return the value of user_t.user_name
     *
     * @mbggenerated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_t.user_name
     *
     * @param userName the value for user_t.user_name
     *
     * @mbggenerated
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_t.password
     *
     * @return the value of user_t.password
     *
     * @mbggenerated
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_t.password
     *
     * @param password the value for user_t.password
     *
     * @mbggenerated
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_t.age
     *
     * @return the value of user_t.age
     *
     * @mbggenerated
     */
    public Integer getAge() {
        return age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_t.age
     *
     * @param age the value for user_t.age
     *
     * @mbggenerated
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_t.ver
     *
     * @return the value of user_t.ver
     *
     * @mbggenerated
     */
    public Integer getVer() {
        return ver;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_t.ver
     *
     * @param ver the value for user_t.ver
     *
     * @mbggenerated
     */
    public void setVer(Integer ver) {
        this.ver = ver;
    }
}