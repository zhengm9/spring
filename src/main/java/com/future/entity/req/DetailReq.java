package com.future.entity.req;

/**
 * Created by zhengming on 2017/8/20.
 */
public class DetailReq {
    private String projectName;
    private int projectId;

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    @Override
    public String toString() {
        return "DetailReq{" +
                "projectName='" + projectName + '\'' +
                ", projectId=" + projectId +
                '}';
    }
}
