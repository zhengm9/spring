package com.future.dao.idao;

import com.future.dao.po.ProjectInfo;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ProjectInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_info
     *
     * @mbggenerated
     */
    @Delete({
        "delete from project_info",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_info
     *
     * @mbggenerated
     */
    @Insert({
        "insert into project_info (id, project_name, ",
        "parent_project_id, project_type, ",
        "develop_field, workload, ",
        "handle_state, internal_task_state, ",
        "external_task_state, joint_debug_date, ",
        "requirements_received_date, owner_id)",
        "values (#{id,jdbcType=INTEGER}, #{projectName,jdbcType=VARCHAR}, ",
        "#{parentProjectId,jdbcType=INTEGER}, #{projectType,jdbcType=TINYINT}, ",
        "#{developField,jdbcType=VARCHAR}, #{workload,jdbcType=REAL}, ",
        "#{handleState,jdbcType=TINYINT}, #{internalTaskState,jdbcType=TINYINT}, ",
        "#{externalTaskState,jdbcType=TINYINT}, #{jointDebugDate,jdbcType=DATE}, ",
        "#{requirementsReceivedDate,jdbcType=DATE}, #{ownerId,jdbcType=INTEGER})"
    })
    int insert(ProjectInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_info
     *
     * @mbggenerated
     */
    int insertSelective(ProjectInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_info
     *
     * @mbggenerated
     */
    @Select({
        "select",
        "id, project_name, parent_project_id, project_type, develop_field, workload, ",
        "handle_state, internal_task_state, external_task_state, joint_debug_date, requirements_received_date, ",
        "owner_id",
        "from project_info",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    ProjectInfo selectByPrimaryKey(Integer id);


    @Select({
            "select",
            "a.id, a.project_name, a.parent_project_id, a.project_type, a.develop_field, a.workload, ",
            "a.handle_state, a.internal_task_state, a.external_task_state, a.joint_debug_date, a.requirements_received_date, ",
            "a.owner_id, b.first_name, b.last_name, b.email, b.username, b.team_id, b.active, c.parent_project_name",
            "from project_info a",
            "left join sys_user b on a.owner_id = b.id",
            "left join parent_project_info c on a.parent_project_id = c.id"
    })
    @ResultMap("BaseResultMap")
    List<ProjectInfo> selectAll();

    @Select({
            "select",
            "count(1)",
            "from project_info a",
            "left join sys_user b on a.owner_id = b.id",
            "left join parent_project_info c on a.parent_project_id = c.id",
//            "where a.requirements_received_date<=STR_TO_DATE(\"#{enddate,jdbcType=DATE} 23:59:59\",\"%Y-%m-%d %H:%i:%s\")  ",
//            "and a.requirements_received_date>=STR_TO_DATE(\"#{startdate,jdbcType=DATE} 00:00:00\",\"%Y-%m-%d %H:%i:%s\")"
            "where a.requirements_received_date<=STR_TO_DATE(#{enddate,jdbcType=VARCHAR}\"23:59:59\",\"%Y-%m-%d %H:%i:%s\")  ",
            "and a.requirements_received_date>=STR_TO_DATE(#{startdate,jdbcType=VARCHAR}\"23:59:59\",\"%Y-%m-%d %H:%i:%s\")"

    })

//    @ResultMap("BaseResultMap")
    Integer countSelectAll(Map<String,String> map);
//            ,Date requirementsReceivedDate1);
//    Integer countSelectAll(@Param("startdate")String startdate, @Param("enddate")String enddate);

//    , ProjectInfo record2);

    @Select({
            "select",
            "a.id, a.project_name, a.parent_project_id, a.project_type, a.develop_field, a.workload, ",
            "a.handle_state, a.internal_task_state, a.external_task_state, a.joint_debug_date, a.requirements_received_date, ",
            "a.owner_id, b.first_name, b.last_name, b.email, b.username, b.team_id, b.active, c.parent_project_name",
            "from project_info a",
            "left join sys_user b on a.owner_id = b.id",
            "left join parent_project_info c on a.parent_project_id = c.id",
            "where a.owner_id = #{ownerId,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    List<ProjectInfo> selectByOwnerId(Integer ownerId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ProjectInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project_info
     *
     * @mbggenerated
     */
    @Update({
        "update project_info",
        "set project_name = #{projectName,jdbcType=VARCHAR},",
          "parent_project_id = #{parentProjectId,jdbcType=INTEGER},",
          "project_type = #{projectType,jdbcType=TINYINT},",
          "develop_field = #{developField,jdbcType=VARCHAR},",
          "workload = #{workload,jdbcType=REAL},",
          "handle_state = #{handleState,jdbcType=TINYINT},",
          "internal_task_state = #{internalTaskState,jdbcType=TINYINT},",
          "external_task_state = #{externalTaskState,jdbcType=TINYINT},",
          "joint_debug_date = #{jointDebugDate,jdbcType=DATE},",
          "requirements_received_date = #{requirementsReceivedDate,jdbcType=DATE},",
          "owner_id = #{ownerId,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ProjectInfo record);
}