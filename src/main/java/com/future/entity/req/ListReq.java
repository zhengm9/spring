package com.future.entity.req;

/**
 * Created by zhengming on 2017/8/20.
 */
public class ListReq {
    private int limit;
    private int offset;
    private String scope;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "ListReq{" +
                "limit=" + limit +
                ", offset=" + offset +
                ", scope='" + scope + '\'' +
                '}';
    }
}
