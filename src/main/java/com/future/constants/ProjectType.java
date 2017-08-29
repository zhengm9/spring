package com.future.constants;

import com.future.dao.po.ProjectInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengming on 2017/8/20.
 */
public enum ProjectType {
    NORMAL("普通",0),
    EMERG("紧急",1),
    OPER("运维",2);

    private long order;
    private String desc;
    private static List<Enum<ProjectType>>  list = new ArrayList();
    static {
        for(Enum<ProjectType> projectTypeEnum : ProjectType.values())
        {
            list.add(projectTypeEnum);
        }

    }

    ProjectType(String desc, int order)
    {
        this.desc = desc;
        this.order = order;
    }

    public static List<Enum<ProjectType>> getDescList()
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
