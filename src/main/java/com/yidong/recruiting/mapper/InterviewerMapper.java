package com.yidong.recruiting.mapper;

import com.yidong.recruiting.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface InterviewerMapper {

    //个人中心
    @Update("update user set user_name=#{arg0},user_age=#{arg1},sex=#{arg2},email=#{arg3},education=#{arg4},description=#{arg5} where telephone=#{arg6}")
    public int updateInfo(String userName,int userAge,String sex,String email,String education,String desc,String telephone);

    //封装对象
    @Select("select * from user where telephone=#{telephone} and identity='面试官'")
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
    public User findUser(String telephone);

    //发布招聘
    @Insert("insert into recruit (id,interviewer,telephone,region,education,salary,scale_company,position_type,position,desc_po) " +
            "values(0,#{arg0},#{arg1},#{arg2},#{arg3},#{arg4},#{arg5},#{arg6},#{arg7},#{arg8})")
    public int postRecruit(String interviewer,String telephone,String region,String education,String salary,String scaleCompany,String positionType,
    String position,String descPo);
}
