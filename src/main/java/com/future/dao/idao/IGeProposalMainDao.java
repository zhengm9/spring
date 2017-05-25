package com.future.dao.idao;

import com.future.dao.po.GeProposalMain;
import com.future.dao.po.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

public interface IGeProposalMainDao extends GeProposalMainMapper{


//    @Select({
//            "SELECT",
//                    "a.tbOrderId,a.PROPOSALNO,a.SUMPREMIUM,a.SUMAMOUNT,a.OPERATEDATE,a.STARTDATE,a.ENDDATE,a.UNITCOUNT,",
//                    "a.REQUESTEDTIME,a.loanStartTime,a.status,a.reason ",
//                    "FROM chinalifeec.GE_PROPOSAL_MAIN a ",
//                    "LEFT JOIN chinalifeec.GE_QUOTE_PARTY b ON a.quoteno = b.quoteno ",
//                    "LEFT JOIN chinalifeec.GE_AIRORDER c ON a.tbOrderId = c.policyno ",
//                    "LEFT JOIN chinalifeec.GE_ORDER d on a.tbOrderId = d.tbOrderId ",
//                    "WHERE ",
//                    "b.partyflag = '2' " ,
//                    "AND ",
//                    "c.proposalno = a.proposalno " ,
//                    "and c.status='1' ",
//                    "and a.OPERATEDATE>=to_date(to_char(trunc(sysdate-1),'yyyy-mm-dd') ||' 00:00:00','yyyy-mm-dd hh24:mi:ss') " ,
//                    "and a.OPERATEDATE<=to_date(to_char(trunc(sysdate-0),'yyyy-mm-dd') ||' 23:59:59','yyyy-mm-dd hh24:mi:ss') " ,
//                    "and a.PRODUCTNO='313' " ,
//                    "ORDER BY " ,
//                    "A .OPERATEDATE DESC"
//    })
@Select({
        "SELECT",
        "a.tbOrderId,b.partyname ",
        "FROM chinalifeec.GE_PROPOSAL_MAIN a ",
        "LEFT JOIN chinalifeec.GE_QUOTE_PARTY b ON a.quoteno = b.quoteno ",
        "WHERE ",
        "b.partyflag = '2' " ,
        "and a.OPERATEDATE>=to_date(to_char(trunc(sysdate-1),'yyyy-mm-dd') ||' 00:00:00','yyyy-mm-dd hh24:mi:ss') " ,
        "and a.OPERATEDATE<=to_date(to_char(trunc(sysdate-0),'yyyy-mm-dd') ||' 23:59:59','yyyy-mm-dd hh24:mi:ss') " ,
        "and a.PRODUCTNO='313' " ,
        "ORDER BY " ,
        "a.OPERATEDATE DESC"
})
    @ResultMap("joinResultMap")
    GeProposalMain selectForStat();



}