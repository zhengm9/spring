package com.future.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengming on 2017/8/20.
 */
public enum ExternalTaskStateType {
    REQUIREMENT("未签",0),
    WORKLOAD("已签",1);

    private long order;
    private String desc;
    private static List<Enum<ExternalTaskStateType>>  list = new ArrayList();
    static {
        for(Enum<ExternalTaskStateType> handleStateTypeEnum : ExternalTaskStateType.values())
        {
            list.add(handleStateTypeEnum);
        }

    }

    ExternalTaskStateType(String desc, int order)
    {
        this.desc = desc;
        this.order = order;
    }

    public static List<Enum<ExternalTaskStateType>> getDescList()
    {

        return list;
    }
    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
