package com.yidong.recruiting.mapper;

import com.yidong.recruiting.entity.Apply;
import com.yidong.recruiting.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InterviewerMapper {

    //个人中心
    @Update("update user set user_name=#{arg0},user_age=#{arg1},sex=#{arg2},email=#{arg3},education=#{arg4},description=#{arg5} where telephone=#{arg6}")
    int updateInfo(String userName,int userAge,String sex,String email,String education,String desc,String telephone);

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
    User findUser(String telephone);

    //头像
    @Update("update user set head_photo=#{arg0} where telephone=#{arg1} and identity='面试官'")
    int updatePhoto(String photo,String telephone);

    //发布招聘
    @Insert("insert into recruit (id,interviewer,telephone,region,education,salary,scale_company,position_type,position,interview_time,desc_po) " +
            "values(0,#{arg0},#{arg1},#{arg2},#{arg3},#{arg4},#{arg5},#{arg6},#{arg7},#{arg8},#{arg9})")
    int postRecruit(String interviewer, String telephone, String region, String education, String salary, String scaleCompany, String positionType,
                    String position, String time,String descPo);

    //报名者审核
    @Select("select * from apply where iphone=#{iPhone} and id=0")
    @Results({
            @Result(column = "num",property = "num"),
            @Result(column = "id",property = "id"),
            @Result(column = "telephone",property = "telephone"),
            @Result(column = "user_name",property = "userName"),
            @Result(column = "iphone",property = "iphone"),
            @Result(column = "position",property = "position")
    })
    List<Apply> jobCheck(String iPhone);

    //pass
    @Update("update apply set id=1 where telephone=#{arg0} and position=#{arg1}")
    int pass(String telephone,String position);

    //nopass
    @Delete("delete from apply where telephone=#{arg0} and position=#{arg1}")
    int nopass(String telephone,String position);

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
    User findUserByTelephone(String telephone);

    //面试
    @Select("SELECT ta.*, @rank := @rank+1 AS personnum FROM(SELECT * FROM apply WHERE iphone=#{iphone} and id=1 and queue=1 ORDER BY judge ASC ) AS ta,(SELECT @rank := 0) r")
    @Results({
            @Result(column = "num",property = "num"),
            @Result(column = "id",property = "id"),
            @Result(column = "telephone",property = "telephone"),
            @Result(column = "user_name",property = "userName"),
            @Result(column = "iphone",property = "iphone"),
            @Result(column = "position",property = "position"),
            @Result(column = "personnum",property = "personNum"),
    })
    List<Apply> findApply(String iphone);

    //面试通过
    @Update("update apply set id=2 and queue=2 where telephone=#{arg0} and position=#{arg1}")
    int yes(String telephone,String position);

    //面试不通过
    @Delete("delete from apply where telephone=#{arg0} and position=#{arg1}")
    int no(String telephone,String position);
}
