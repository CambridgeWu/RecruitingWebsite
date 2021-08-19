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
    @Insert("insert into apply (telephone,num,iphone,user_name,position,id,queue) values(#{arg0},#{arg1},#{arg2},#{arg3},#{arg4},0,0)")
    int entry(String telephone,int num,String iPhone,String userName,String position);

    //头像
    @Update("update user set head_photo=#{arg0} where telephone=#{arg1} and identity='报名者'")
    int updatePhoto(String photo,String telephone);

    //我的面试
    @Select("SELECT ta.*, @rank := @rank+1 AS personnum FROM(SELECT * FROM apply WHERE id=1 and telephone=#{telephone} ORDER BY judge ASC ) AS ta,(SELECT @rank := 0) r")
    @Results({
            @Result(column = "telephone",property = "telephone"),
            @Result(column = "id",property = "id"),
            @Result(column = "num",property = "num"),
            @Result(column = "iphone",property = "iphone"),
            @Result(column = "position",property = "position"),
            @Result(column = "user_name",property = "userName"),
            @Result(column = "judge",property = "judge"),
            @Result(column = "personnum",property = "personNum")
    })
    List<Apply> myInterview(String telephone);

    //我的面试
    @Update("update apply set judge=1 where telephone=#{arg0} and position=#{arg1}")
    int yuyue(String telephone,String position);

    //取消排队
    @Update("update apply set queue=0 where telephone=#{arg0} and position=#{arg1}")
    int noQueue(String telephone,String position);

    //重新排队
    @Update("update apply set queue=1 where telephone=#{arg0} and position=#{arg1}")
    int toQueue(String telephone,String position);
}
