package com.yidong.recruiting.mapper;

import com.yidong.recruiting.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    //登录验证
    @Select("select * from user where telephone=#{arg0} and password=#{arg1}")
    public User loginVerify(String telephone, String password);

    //注册信息(一个手机号只能注册一次)
    //1.查看注册信息有无重复
    @Select("select * from user where user_name=#{arg0} and telephone=#{arg1} and password=#{arg2}")
    public User registerJudge(String userName,String telephone,String password);
    //2.传入数据库进行保存
    @Insert("insert into user (id,user_name,password,telephone,identity) values(0,#{arg0},#{arg1},#{arg2},#{arg3})")
    public int saveUser(String username,String password,String telephone,String identity);
}
