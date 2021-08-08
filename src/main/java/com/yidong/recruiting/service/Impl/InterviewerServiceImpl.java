package com.yidong.recruiting.service.Impl;

import com.yidong.recruiting.entity.User;
import com.yidong.recruiting.mapper.InterviewerMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterviewerServiceImpl {
    @Autowired
    InterviewerMapper interviewerMapper;

    //个人中心
    public int updateInfo(String userName,int userAge,String sex,String email,String education,String desc,String telephone){
        return interviewerMapper.updateInfo(userName,userAge,sex,email,education,desc,telephone);
    }

    //封装对象
    public User findUser(String telephone){
        return interviewerMapper.findUser(telephone);
    }

    //发布招聘
    public int postRecruit(String interviewer,String telephone,String region,String education,String salary,String scaleCompany,String positionType,
                           String position,String descPo){
        return interviewerMapper.postRecruit(interviewer,telephone,region,education,salary,scaleCompany,positionType,position,descPo);
    }

}
