package com.yidong.recruiting.mapper;

import com.yidong.recruiting.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserLoginMapper {

    //登录验证
    @Select("select * from user where telephone=#{telephone}")
    public User loginVerify(String telephone);

    //注册信息(一个手机号只能注册一次)
    //1.查看注册信息有无重复
    @Select("select * from user where user_name=#{arg0} and telephone=#{arg1} and password=#{arg2}")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "identity",property = "identity"),
            @Result(column = "user_name",property = "userName"),
            @Result(column = "password",property = "password"),
            @Result(column = "user_age",property = "userAge"),
            @Result(column = "telephone",property = "telephone"),
            @Result(column = "sex",property = "sex"),
            @Result(column = "head_photo",property = "headPhoto"),
            @Result(column = "email",property = "email"),
            @Result(column = "education",property = "education"),
            @Result(column = "work_exper",property = "workExperience"),
            @Result(column = "description",property = "desc"),
    })
    public User registerJudge(String userName,String telephone,String password);
    //2.传入数据库进行保存
    @Insert("insert into user (id,user_name,password,telephone,identity) values(0,#{arg0},#{arg1},#{arg2},#{arg3})")
    public int saveUser(String username,String password,String telephone,String identity);

    //返回用户
    @Select("select * from user where telephone=#{arg0} and password=#{arg1} and identity=#{arg2}")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "identity",property = "identity"),
            @Result(column = "user_name",property = "userName"),
            @Result(column = "password",property = "password"),
            @Result(column = "user_age",property = "userAge"),
            @Result(column = "telephone",property = "telephone"),
            @Result(column = "sex",property = "sex"),
            @Result(column = "head_photo",property = "headPhoto"),
            @Result(column = "email",property = "email"),
            @Result(column = "education",property = "education"),
            @Result(column = "work_exper",property = "workExperience"),
            @Result(column = "description",property = "desc"),
    })
    public User findUser(String telephone,String password,String identity);
}
