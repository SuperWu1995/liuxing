package com.liuxing.user.mapper;

import com.liuxing.user.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from tb_user where id = #{id}")
    User getById(@Param("id") long id);
}
