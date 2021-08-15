package com.yidong.recruiting.service;

import com.yidong.recruiting.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface InterviewerService {

    //个人中心
    public int updateInfo(String userName,int userAge,String sex,String email,String education,String desc,String telephone);

    //封装对象
    public User findUser(String telephone);

    //发布招聘
    public int postRecruit(String interviewer,String telephone,String region,String education,String salary,String scaleCompany,String positionType,
                           String position,String descPo);

    //头像
    public int updatePhoto(String photo,String telephone);
}
