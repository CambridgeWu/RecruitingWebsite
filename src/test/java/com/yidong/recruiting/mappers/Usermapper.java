package com.yidong.recruiting.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface Usermapper {
    //2.传入数据库进行保存
    @Insert("insert into user (id,user_name,password,telephone,identity) values(0,#{arg0},#{arg1},#{arg2},#{arg3})")
    public int saveUser(String username,String password,String telephone,String identity);
}
