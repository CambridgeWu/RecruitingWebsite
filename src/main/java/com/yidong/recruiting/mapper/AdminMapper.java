package com.yidong.recruiting.mapper;

import com.yidong.recruiting.entity.Recruit;
import com.yidong.recruiting.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {
    //检验账号密码是否正确
    @Select("select * from user where user_name=#{arg0} and password=#{arg1} and identity='管理员'")
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
    public User loginVerify(String userName, String password);

    //封装管理员注册信息
    @Select("select * from user where identity='面试官' and id=0")
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
    public List<User> getInterviewerInfo();

    //确认注册
    @Update("update user set id=1 where telephone=#{telephone} and identity='面试官'")
    public int confirmRegister(String telephone);

    //删除注册的面试官
    @Delete("delete from user where telephone=#{telephone} and identity='面试官'")
    public int deleteRegister(String telephone);

    //返回招聘
    @Select("select * from recruit where id=0")
    @Results({
            @Result(column = "interviewer",property = "interviewer"),
            @Result(column = "telephone",property = "telephone"),
            @Result(column = "region",property = "region"),
            @Result(column = "education",property = "education"),
            @Result(column = "salary",property = "salary"),
            @Result(column = "scale_company",property = "scaleCompany"),
            @Result(column = "position_type",property = "positionType"),
            @Result(column = "position",property = "position")
    })
    public List<Recruit> returnRecruit();

    //添加Job
    @Update("update recruit set id=1 where telephone=#{arg0} and position=#{arg1}")
    public int addJob(String telephone,String position);

    //删除Job
    @Delete("delete from recruit where telephone=#{arg0} and position=#{arg1}")
    public int deleteJob(String telephone,String position);
}
