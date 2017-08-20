package com.future.entity.rsp;

/**
 * Created by zhengming on 2017/8/20.
 */
public class Head {
    private String username;
    private int count;
    private Object obj;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "Head{" +
                "username='" + username + '\'' +
                ", count=" + count +
                ", obj=" + obj +
                '}';
    }

}
