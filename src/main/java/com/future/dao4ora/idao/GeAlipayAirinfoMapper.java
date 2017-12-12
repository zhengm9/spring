package com.future.dao4ora.idao;

import com.future.dao4ora.po.GeAlipayAirinfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface GeAlipayAirinfoMapper {

    @Select({
            "select",
            "count(1)",
            "from chinalifeec.GE_ALIPAY_AIRINFO a",
//            "where a.MAKEDATE >=to_date(#{startDay,jdbcType=VARCHAR}\"00:00:00\",\"yyyy-mm-dd hh24:mi:ss\") ",
//            "and a.MAKEDATE<=to_date(#{endDay,jdbcType=VARCHAR}\"00:00:00\",\"yyyy-mm-dd hh24:mi:ss\")"
            "where a.MAKEDATE >=to_date('${startDay} 00:00:00','yyyy-mm-dd hh24:mi:ss') ",
            "and a.MAKEDATE<=to_date('${endDay} 23:59:59','yyyy-mm-dd hh24:mi:ss')"

    })
    Integer countAll(@Param("startDay")String startDay, @Param("endDay")String endDay);


    @Select({
            "select",
            "'蚂蚁航意险' as PRODUCTNAME, ",
            "SERIALNO, TRANSTIME, REQMSGID, POLICYNO, PRODNO, SUMMARYPOLICYNO, POLICYTYPE, ",
            "PREMIUM/100 as PREMIUM, ACTUALPREMIUM/100 as ACTUALPREMIUM, SUMINSURED/100 as SUMINSURED, INSUREDTIME, EFFECTSTARTTIME, EFFECTENDTIME, ",
            "APPLYNUM, AIRNUMBER, ISREQUIREINVOICE, AIRORDEREND, AIRTAKEOFF, AIRORDERID, ",
            "SHAREDCOMMISSION, TICKETNO, FLIGHTNO, AIRCOMNAME, INSUREDCOPIES, AIRORDERSTART, ",
            "BIZORDERID, AIRLANDING, MERCHANTACCOUNTTYPE, MERCHANTACCOUNTID, OTHERACCOUNTTYPE, ",
            "OTHERACCOUNTID, PAYTIME, FEE/100 as FEE, PAYFLOWID, HOLDERPERSONNO, HOLDERACCOUNTTYPE, ",
            "HOLDERACCOUNTNO, HOLDERACCOUNTNAME, HOLDERCERTTYPE, HOLDERCERTNO, HOLDERCERTNAME, ",
            "HOLDERBIRTHDAY, HOLDERPHONE, INSUREDSAMEWITHHOLDER, INSUREDPERSONNO, INSUREDACCOUNTTYPE, ",
            "INSUREDACCOUNTNO, INSUREDACCOUNTNAME, INSUREDCERTTYPE, INSUREDCERTNO, INSUREDCERTNAME, ",
            "INSUREDBIRTHDAY, INSUREDPHONE, STATUS, MAKEDATE, MODIFYDATE, PROPOSALNO, PROVINCE, ",
            "CITY, AREA, DETAILADDRESS, MOBILE, ZIP, NUM, REASON, SAVEERRORCOUNT",
            "from chinalifeec.GE_ALIPAY_AIRINFO a",
            "where a.MAKEDATE >=to_date('${startDay} 00:00:00','yyyy-mm-dd hh24:mi:ss') ",
            "and a.MAKEDATE<=to_date('${endDay} 23:59:59','yyyy-mm-dd hh24:mi:ss')",
            " order by a.makedate,a.policyno"
    })
    @ResultMap("BaseResultMap")
    List<GeAlipayAirinfo> selectByMakedate(@Param("startDay")String startDay, @Param("endDay")String endDay);

    @Select({
            "select count(1)",
            "from chinalifeec.GE_ALIPAY_AIRINFO a",
            "right join chinalifeec.ENDORSEMENT_ENDORSE  b ",
            "on b.policyno=a.policyno ",
            "where b.createstamp >=to_date('${startDay} 00:00:00','yyyy-mm-dd hh24:mi:ss') ",
            "and b.createstamp<=to_date('${endDay} 23:59:59','yyyy-mm-dd hh24:mi:ss')",
            "and b.type='402' ",
            " order by b.createstamp,b.batchId"
    })
    Integer countAllCancel(@Param("startDay")String startDay, @Param("endDay")String endDay);


    @Select({
            "select",
            "'蚂蚁航意险' as PRODUCTNAME, b.batchId, a.policyno as policyno, b.amount/100 as amount, b.finishtinme,",
            "a.proposalno, a.airorderid, a.flightno, a.airtakeoff, a.bizorderid, a.merchantaccounttype, a.merchantaccountid, ",
            "a.paytime, a.payflowid, a.holdercertname, a.holderphone, ",
            "case when a.insuredcertNo is null then p.identifynumber else a.insuredcertNo  end as insuredcertNo,",
            "case when a.insuredcerttype is null then p.identifytype else a.insuredcerttype end as insuredcerttype,",
            "case when a.insuredcertname is null then p.partyname else a.insuredcertname end as insuredcertname,",
            "a.insuredbirthday, b.createstamp ",
            "from chinalifeec.GE_ALIPAY_AIRINFO a",
            "right join chinalifeec.ENDORSEMENT_ENDORSE  b ",
            "on b.policyno=a.policyno ",
            "LEFT JOIN chinalifeec.ge_proposal_main  m  on m.proposalno=a.proposalno ",
            "left join chinalifeec.ge_quote_party p on m.quoteno=p.quoteno ",
            "where b.createstamp >=to_date('${startDay} 00:00:00','yyyy-mm-dd hh24:mi:ss') ",
            "and b.createstamp<=to_date('${endDay} 23:59:59','yyyy-mm-dd hh24:mi:ss')",
            "and b.type='402' ",
            "and ( p.partyflag is null or p.partyflag = '2')",
            " order by b.createstamp,b.batchId"
    })
    @ResultMap("reportResultMap")
    List<GeAlipayAirinfo> selectCancelByMakedate(@Param("startDay")String startDay, @Param("endDay")String endDay);



    @Select({
            "select count(distinct e1.batchid) ",
            "from chinalifeec.ge_alipay_airinfo a ",
            "left join chinalifeec.ENDORSEMENT_ENDORSE e1 on a.policyno=e1.policyno ",
            " left join chinalifeec.ENDORSEMENT_ENDORSEITEMS e2 on e2.batchid =e1.batchid ",
            "where e1.createstamp>=to_date('${startDay} 00:00:00','yyyy-mm-dd hh24:mi:ss') ",
            "and e1.createstamp<to_date('${endDay} 23:59:59','yyyy-mm-dd hh24:mi:ss') ",
            "and e1.type='301'  "
    })
    Integer countAllEndorse(@Param("startDay")String startDay, @Param("endDay")String endDay);


    @Select({
            "select  ",
            "'蚂蚁航意险' as PRODUCTNAME, a.policyno,tmp.batchid,a.premium/100 as premium,tmp.finishtinme,a.proposalno,a.payflowid,a.holderphone, ",
            "a.insuredcertname,a.insuredcerttype,a.insuredcertNo,tmp.createstamp,tmp.status as endorsestatus,tmp.reason as endorsereason, ",
            "tmp.newtimevalue,tmp.newflightvalue ",
            "from chinalifeec.ge_alipay_airinfo a  ",
            "right join   ",
            "( ",
            "select  ",
            "e1.policyno,e1.batchid,max(e1.finishtinme) as finishtinme ",
            ",min(e1.createstamp) as createstamp,max(e1.status) as status,max(e1.reason) as reason ",
            ",max(case when e2.itemtype='303' then to_char(e2.newvalue) else null end) as newtimevalue ",
            ",max(case when e2.itemtype='301' then to_char(e2.newvalue) else null end) as newflightvalue ",
            "from chinalifeec.ENDORSEMENT_ENDORSE e1 ",
            " left join chinalifeec.ENDORSEMENT_ENDORSEITEMS e2 on e2.batchid =e1.batchid ",
            "where e1.createstamp>=to_date('${startDay} 00:00:00','yyyy-mm-dd hh24:mi:ss') ",
            "and e1.createstamp<to_date('${endDay} 23:59:59','yyyy-mm-dd hh24:mi:ss') ",
            "and e1.type='301' ",
            "group by e1.batchid,e1.policyno ",
            ")tmp ",
            "on tmp.policyno=a.policyno ",
            "order by tmp.createstamp"
    })
    @ResultMap("reportResultMap")
    List<GeAlipayAirinfo> selectEndorseByMakedate(@Param("startDay")String startDay, @Param("endDay")String endDay);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GE_ALIPAY_AIRINFO
     *
     * @mbggenerated
     */
    @Delete({
        "delete from GE_ALIPAY_AIRINFO",
        "where SERIALNO = #{serialno,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String serialno);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GE_ALIPAY_AIRINFO
     *
     * @mbggenerated
     */
    @Insert({
        "insert into GE_ALIPAY_AIRINFO (SERIALNO, TRANSTIME, ",
        "REQMSGID, POLICYNO, ",
        "PRODNO, SUMMARYPOLICYNO, ",
        "POLICYTYPE, PREMIUM, ",
        "ACTUALPREMIUM, SUMINSURED, ",
        "INSUREDTIME, EFFECTSTARTTIME, ",
        "EFFECTENDTIME, APPLYNUM, ",
        "AIRNUMBER, ISREQUIREINVOICE, ",
        "AIRORDEREND, AIRTAKEOFF, ",
        "AIRORDERID, SHAREDCOMMISSION, ",
        "TICKETNO, FLIGHTNO, ",
        "AIRCOMNAME, INSUREDCOPIES, ",
        "AIRORDERSTART, BIZORDERID, ",
        "AIRLANDING, MERCHANTACCOUNTTYPE, ",
        "MERCHANTACCOUNTID, OTHERACCOUNTTYPE, ",
        "OTHERACCOUNTID, PAYTIME, ",
        "FEE, PAYFLOWID, ",
        "HOLDERPERSONNO, HOLDERACCOUNTTYPE, ",
        "HOLDERACCOUNTNO, HOLDERACCOUNTNAME, ",
        "HOLDERCERTTYPE, HOLDERCERTNO, ",
        "HOLDERCERTNAME, HOLDERBIRTHDAY, ",
        "HOLDERPHONE, INSUREDSAMEWITHHOLDER, ",
        "INSUREDPERSONNO, INSUREDACCOUNTTYPE, ",
        "INSUREDACCOUNTNO, INSUREDACCOUNTNAME, ",
        "INSUREDCERTTYPE, INSUREDCERTNO, ",
        "INSUREDCERTNAME, INSUREDBIRTHDAY, ",
        "INSUREDPHONE, STATUS, ",
        "MAKEDATE, MODIFYDATE, ",
        "PROPOSALNO, PROVINCE, ",
        "CITY, AREA, DETAILADDRESS, ",
        "MOBILE, ZIP, NUM, ",
        "REASON, SAVEERRORCOUNT)",
        "values (#{serialno,jdbcType=VARCHAR}, #{transtime,jdbcType=TIMESTAMP}, ",
        "#{reqmsgid,jdbcType=VARCHAR}, #{policyno,jdbcType=VARCHAR}, ",
        "#{prodno,jdbcType=VARCHAR}, #{summarypolicyno,jdbcType=VARCHAR}, ",
        "#{policytype,jdbcType=VARCHAR}, #{premium,jdbcType=NUMERIC}, ",
        "#{actualpremium,jdbcType=NUMERIC}, #{suminsured,jdbcType=NUMERIC}, ",
        "#{insuredtime,jdbcType=TIMESTAMP}, #{effectstarttime,jdbcType=TIMESTAMP}, ",
        "#{effectendtime,jdbcType=TIMESTAMP}, #{applynum,jdbcType=NUMERIC}, ",
        "#{airnumber,jdbcType=VARCHAR}, #{isrequireinvoice,jdbcType=VARCHAR}, ",
        "#{airorderend,jdbcType=VARCHAR}, #{airtakeoff,jdbcType=TIMESTAMP}, ",
        "#{airorderid,jdbcType=VARCHAR}, #{sharedcommission,jdbcType=VARCHAR}, ",
        "#{ticketno,jdbcType=VARCHAR}, #{flightno,jdbcType=VARCHAR}, ",
        "#{aircomname,jdbcType=VARCHAR}, #{insuredcopies,jdbcType=NUMERIC}, ",
        "#{airorderstart,jdbcType=VARCHAR}, #{bizorderid,jdbcType=VARCHAR}, ",
        "#{airlanding,jdbcType=TIMESTAMP}, #{merchantaccounttype,jdbcType=VARCHAR}, ",
        "#{merchantaccountid,jdbcType=VARCHAR}, #{otheraccounttype,jdbcType=VARCHAR}, ",
        "#{otheraccountid,jdbcType=VARCHAR}, #{paytime,jdbcType=TIMESTAMP}, ",
        "#{fee,jdbcType=NUMERIC}, #{payflowid,jdbcType=VARCHAR}, ",
        "#{holderpersonno,jdbcType=VARCHAR}, #{holderaccounttype,jdbcType=VARCHAR}, ",
        "#{holderaccountno,jdbcType=VARCHAR}, #{holderaccountname,jdbcType=VARCHAR}, ",
        "#{holdercerttype,jdbcType=VARCHAR}, #{holdercertno,jdbcType=VARCHAR}, ",
        "#{holdercertname,jdbcType=VARCHAR}, #{holderbirthday,jdbcType=TIMESTAMP}, ",
        "#{holderphone,jdbcType=VARCHAR}, #{insuredsamewithholder,jdbcType=VARCHAR}, ",
        "#{insuredpersonno,jdbcType=VARCHAR}, #{insuredaccounttype,jdbcType=VARCHAR}, ",
        "#{insuredaccountno,jdbcType=VARCHAR}, #{insuredaccountname,jdbcType=VARCHAR}, ",
        "#{insuredcerttype,jdbcType=VARCHAR}, #{insuredcertno,jdbcType=VARCHAR}, ",
        "#{insuredcertname,jdbcType=VARCHAR}, #{insuredbirthday,jdbcType=TIMESTAMP}, ",
        "#{insuredphone,jdbcType=VARCHAR}, #{status,jdbcType=VARCHAR}, ",
        "#{makedate,jdbcType=TIMESTAMP}, #{modifydate,jdbcType=TIMESTAMP}, ",
        "#{proposalno,jdbcType=VARCHAR}, #{province,jdbcType=VARCHAR}, ",
        "#{city,jdbcType=VARCHAR}, #{area,jdbcType=VARCHAR}, #{detailaddress,jdbcType=VARCHAR}, ",
        "#{mobile,jdbcType=VARCHAR}, #{zip,jdbcType=VARCHAR}, #{num,jdbcType=NUMERIC}, ",
        "#{reason,jdbcType=VARCHAR}, #{saveerrorcount,jdbcType=NUMERIC})"
    })
    int insert(GeAlipayAirinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GE_ALIPAY_AIRINFO
     *
     * @mbggenerated
     */
    int insertSelective(GeAlipayAirinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GE_ALIPAY_AIRINFO
     *
     * @mbggenerated
     */
    @Select({
        "select",
        "SERIALNO, TRANSTIME, REQMSGID, POLICYNO, PRODNO, SUMMARYPOLICYNO, POLICYTYPE, ",
        "PREMIUM, ACTUALPREMIUM, SUMINSURED, INSUREDTIME, EFFECTSTARTTIME, EFFECTENDTIME, ",
        "APPLYNUM, AIRNUMBER, ISREQUIREINVOICE, AIRORDEREND, AIRTAKEOFF, AIRORDERID, ",
        "SHAREDCOMMISSION, TICKETNO, FLIGHTNO, AIRCOMNAME, INSUREDCOPIES, AIRORDERSTART, ",
        "BIZORDERID, AIRLANDING, MERCHANTACCOUNTTYPE, MERCHANTACCOUNTID, OTHERACCOUNTTYPE, ",
        "OTHERACCOUNTID, PAYTIME, FEE, PAYFLOWID, HOLDERPERSONNO, HOLDERACCOUNTTYPE, ",
        "HOLDERACCOUNTNO, HOLDERACCOUNTNAME, HOLDERCERTTYPE, HOLDERCERTNO, HOLDERCERTNAME, ",
        "HOLDERBIRTHDAY, HOLDERPHONE, INSUREDSAMEWITHHOLDER, INSUREDPERSONNO, INSUREDACCOUNTTYPE, ",
        "INSUREDACCOUNTNO, INSUREDACCOUNTNAME, INSUREDCERTTYPE, INSUREDCERTNO, INSUREDCERTNAME, ",
        "INSUREDBIRTHDAY, INSUREDPHONE, STATUS, MAKEDATE, MODIFYDATE, PROPOSALNO, PROVINCE, ",
        "CITY, AREA, DETAILADDRESS, MOBILE, ZIP, NUM, REASON, SAVEERRORCOUNT",
        "from GE_ALIPAY_AIRINFO",
        "where SERIALNO = #{serialno,jdbcType=VARCHAR}"
    })
    @ResultMap("BaseResultMap")
    GeAlipayAirinfo selectByPrimaryKey(String serialno);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GE_ALIPAY_AIRINFO
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(GeAlipayAirinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table GE_ALIPAY_AIRINFO
     *
     * @mbggenerated
     */
    @Update({
        "update GE_ALIPAY_AIRINFO",
        "set TRANSTIME = #{transtime,jdbcType=TIMESTAMP},",
          "REQMSGID = #{reqmsgid,jdbcType=VARCHAR},",
          "POLICYNO = #{policyno,jdbcType=VARCHAR},",
          "PRODNO = #{prodno,jdbcType=VARCHAR},",
          "SUMMARYPOLICYNO = #{summarypolicyno,jdbcType=VARCHAR},",
          "POLICYTYPE = #{policytype,jdbcType=VARCHAR},",
          "PREMIUM = #{premium,jdbcType=NUMERIC},",
          "ACTUALPREMIUM = #{actualpremium,jdbcType=NUMERIC},",
          "SUMINSURED = #{suminsured,jdbcType=NUMERIC},",
          "INSUREDTIME = #{insuredtime,jdbcType=TIMESTAMP},",
          "EFFECTSTARTTIME = #{effectstarttime,jdbcType=TIMESTAMP},",
          "EFFECTENDTIME = #{effectendtime,jdbcType=TIMESTAMP},",
          "APPLYNUM = #{applynum,jdbcType=NUMERIC},",
          "AIRNUMBER = #{airnumber,jdbcType=VARCHAR},",
          "ISREQUIREINVOICE = #{isrequireinvoice,jdbcType=VARCHAR},",
          "AIRORDEREND = #{airorderend,jdbcType=VARCHAR},",
          "AIRTAKEOFF = #{airtakeoff,jdbcType=TIMESTAMP},",
          "AIRORDERID = #{airorderid,jdbcType=VARCHAR},",
          "SHAREDCOMMISSION = #{sharedcommission,jdbcType=VARCHAR},",
          "TICKETNO = #{ticketno,jdbcType=VARCHAR},",
          "FLIGHTNO = #{flightno,jdbcType=VARCHAR},",
          "AIRCOMNAME = #{aircomname,jdbcType=VARCHAR},",
          "INSUREDCOPIES = #{insuredcopies,jdbcType=NUMERIC},",
          "AIRORDERSTART = #{airorderstart,jdbcType=VARCHAR},",
          "BIZORDERID = #{bizorderid,jdbcType=VARCHAR},",
          "AIRLANDING = #{airlanding,jdbcType=TIMESTAMP},",
          "MERCHANTACCOUNTTYPE = #{merchantaccounttype,jdbcType=VARCHAR},",
          "MERCHANTACCOUNTID = #{merchantaccountid,jdbcType=VARCHAR},",
          "OTHERACCOUNTTYPE = #{otheraccounttype,jdbcType=VARCHAR},",
          "OTHERACCOUNTID = #{otheraccountid,jdbcType=VARCHAR},",
          "PAYTIME = #{paytime,jdbcType=TIMESTAMP},",
          "FEE = #{fee,jdbcType=NUMERIC},",
          "PAYFLOWID = #{payflowid,jdbcType=VARCHAR},",
          "HOLDERPERSONNO = #{holderpersonno,jdbcType=VARCHAR},",
          "HOLDERACCOUNTTYPE = #{holderaccounttype,jdbcType=VARCHAR},",
          "HOLDERACCOUNTNO = #{holderaccountno,jdbcType=VARCHAR},",
          "HOLDERACCOUNTNAME = #{holderaccountname,jdbcType=VARCHAR},",
          "HOLDERCERTTYPE = #{holdercerttype,jdbcType=VARCHAR},",
          "HOLDERCERTNO = #{holdercertno,jdbcType=VARCHAR},",
          "HOLDERCERTNAME = #{holdercertname,jdbcType=VARCHAR},",
          "HOLDERBIRTHDAY = #{holderbirthday,jdbcType=TIMESTAMP},",
          "HOLDERPHONE = #{holderphone,jdbcType=VARCHAR},",
          "INSUREDSAMEWITHHOLDER = #{insuredsamewithholder,jdbcType=VARCHAR},",
          "INSUREDPERSONNO = #{insuredpersonno,jdbcType=VARCHAR},",
          "INSUREDACCOUNTTYPE = #{insuredaccounttype,jdbcType=VARCHAR},",
          "INSUREDACCOUNTNO = #{insuredaccountno,jdbcType=VARCHAR},",
          "INSUREDACCOUNTNAME = #{insuredaccountname,jdbcType=VARCHAR},",
          "INSUREDCERTTYPE = #{insuredcerttype,jdbcType=VARCHAR},",
          "INSUREDCERTNO = #{insuredcertno,jdbcType=VARCHAR},",
          "INSUREDCERTNAME = #{insuredcertname,jdbcType=VARCHAR},",
          "INSUREDBIRTHDAY = #{insuredbirthday,jdbcType=TIMESTAMP},",
          "INSUREDPHONE = #{insuredphone,jdbcType=VARCHAR},",
          "STATUS = #{status,jdbcType=VARCHAR},",
          "MAKEDATE = #{makedate,jdbcType=TIMESTAMP},",
          "MODIFYDATE = #{modifydate,jdbcType=TIMESTAMP},",
          "PROPOSALNO = #{proposalno,jdbcType=VARCHAR},",
          "PROVINCE = #{province,jdbcType=VARCHAR},",
          "CITY = #{city,jdbcType=VARCHAR},",
          "AREA = #{area,jdbcType=VARCHAR},",
          "DETAILADDRESS = #{detailaddress,jdbcType=VARCHAR},",
          "MOBILE = #{mobile,jdbcType=VARCHAR},",
          "ZIP = #{zip,jdbcType=VARCHAR},",
          "NUM = #{num,jdbcType=NUMERIC},",
          "REASON = #{reason,jdbcType=VARCHAR},",
          "SAVEERRORCOUNT = #{saveerrorcount,jdbcType=NUMERIC}",
        "where SERIALNO = #{serialno,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(GeAlipayAirinfo record);
}