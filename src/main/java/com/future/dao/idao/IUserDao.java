package com.future.dao.idao;

import com.future.dao.po.User;
import org.apache.ibatis.annotations.*;

public interface IUserDao extends UserMapper{


    @Insert({
            "insert into user_t (id, user_name, ",
            "password, age)",
            "values (9, \"defaultname\", ",
            "#{password,jdbcType=VARCHAR}, #{age,jdbcType=INTEGER})"
    })
    public int insertDefault(User record);


}