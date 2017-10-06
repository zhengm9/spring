package com.future.dao4ora.idao;

import com.future.dao4ora.po.GeAirorder;
import org.apache.ibatis.annotations.Insert;

public interface GeAirorderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GE_AIRORDER
     *
     * @mbggenerated
     */
    @Insert({
        "insert into GE_AIRORDER (ID, AIRORDERID, ",
        "AIRCOMNAME, FLIGHTNO, ",
        "AIRTAKEOFF, AIRLANDING, ",
        "AIRORDERSTART, AIRORDEREND, ",
        "TICKETNO, ISREQUIREINVOICE, ",
        "PROVINCE, CITY, ",
        "AREA, DETAILADDRESS, ",
        "MOBILE, TEL, ZIP, ",
        "PROPOSALNO, ENDORSENO, ",
        "POLICYNO, ENDORSEFROM, ",
        "FEE, ENDORSEFINISHTIME, ",
        "OUTPOLICYNO, OUTBIZNO, ",
        "STATUS, INSUREDCOPIES, ",
        "BIZORDERID)",
        "values (#{id,jdbcType=VARCHAR}, #{airorderid,jdbcType=VARCHAR}, ",
        "#{aircomname,jdbcType=VARCHAR}, #{flightno,jdbcType=VARCHAR}, ",
        "#{airtakeoff,jdbcType=TIMESTAMP}, #{airlanding,jdbcType=TIMESTAMP}, ",
        "#{airorderstart,jdbcType=VARCHAR}, #{airorderend,jdbcType=VARCHAR}, ",
        "#{ticketno,jdbcType=VARCHAR}, #{isrequireinvoice,jdbcType=VARCHAR}, ",
        "#{province,jdbcType=VARCHAR}, #{city,jdbcType=VARCHAR}, ",
        "#{area,jdbcType=VARCHAR}, #{detailaddress,jdbcType=VARCHAR}, ",
        "#{mobile,jdbcType=VARCHAR}, #{tel,jdbcType=VARCHAR}, #{zip,jdbcType=VARCHAR}, ",
        "#{proposalno,jdbcType=VARCHAR}, #{endorseno,jdbcType=VARCHAR}, ",
        "#{policyno,jdbcType=VARCHAR}, #{endorsefrom,jdbcType=VARCHAR}, ",
        "#{fee,jdbcType=NUMERIC}, #{endorsefinishtime,jdbcType=TIMESTAMP}, ",
        "#{outpolicyno,jdbcType=VARCHAR}, #{outbizno,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=VARCHAR}, #{insuredcopies,jdbcType=VARCHAR}, ",
        "#{bizorderid,jdbcType=VARCHAR})"
    })
    int insert(GeAirorder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GE_AIRORDER
     *
     * @mbggenerated
     */
    int insertSelective(GeAirorder record);
}