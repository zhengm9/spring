package com.future.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengming on 2017/8/20.
 */
public enum HandleStateType {
    REQUIREMENT("需求受理",0),
    WORKLOAD("已评估工作量",1),
    ARCH("已架构评审",2),
    COMMITTED("已上会",3);

    private long order;
    private String desc;
    private static List<Enum<HandleStateType>>  list = new ArrayList();
    static {
        for(Enum<HandleStateType> handleStateTypeEnum : HandleStateType.values())
        {
            list.add(handleStateTypeEnum);
        }

    }

    HandleStateType(String desc, int order)
    {
        this.desc = desc;
        this.order = order;
    }

    public static List<Enum<HandleStateType>> getDescList()
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