package com.yidong.recruiting.mapper;

import com.yidong.recruiting.entity.Apply;
import com.yidong.recruiting.entity.Recruit;
import com.yidong.recruiting.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface ApplyerMapper {

    //个人信息更新
    @Update("update user set user_name=#{arg0},user_age=#{arg1},sex=#{arg2},email=#{arg3},education=#{arg4},description=#{arg5} where telephone=#{arg6}")
    int updateInfo(String userName,int userAge,String sex,String email,String education,String desc,String telephone);

    //封装对象
    @Select("select * from user where telephone=#{telephone} and identity='报名者'")
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
    User findUser(String telephone);


    //根据编号查找职位
    @Select("select * from recruit where num=#{num}")
    @Results({
            @Result(column = "interviewer",property = "interviewer"),
            @Result(column = "telephone",property = "telephone"),
            @Result(column = "region",property = "region"),
            @Result(column = "education",property = "education"),
            @Result(column = "salary",property = "salary"),
            @Result(column = "scale_company",property = "scaleCompany"),
            @Result(column = "position_type",property = "positionType"),
            @Result(column = "position",property = "position"),
            @Result(column = "desc_po",property = "descPo")
    })
    Recruit findRecruitByNum(int num);

    //判断是否重复报名
    @Select("select * from apply where telephone=#{arg0} and num=#{arg1}")
    List<Apply> judgeEntry(String telephone,int num);

    //报名
    @Insert("insert into apply (telephone,num) values(#{arg0},#{arg1})")
    int entry(String telephone,int num);

    //头像
    @Update("update user set head_photo=#{arg0} where telephone=#{arg1} and identity='报名者'")
    int updatePhoto(String photo,String telephone);
}
