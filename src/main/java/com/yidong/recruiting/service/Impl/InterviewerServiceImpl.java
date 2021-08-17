package com.yidong.recruiting.service.Impl;

import com.yidong.recruiting.entity.Apply;
import com.yidong.recruiting.entity.User;
import com.yidong.recruiting.mapper.InterviewerMapper;
import com.yidong.recruiting.service.InterviewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.List;

@Service
public class InterviewerServiceImpl implements InterviewerService {
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
    public int postRecruit(String interviewer, String telephone, String region, String education, String salary, String scaleCompany, String positionType,
                           String position, String time, String descPo){
        return interviewerMapper.postRecruit(interviewer,telephone,region,education,salary,scaleCompany,positionType,position,time,descPo);
    }

    //头像
    public int updatePhoto(String photo,String telephone){
        return interviewerMapper.updatePhoto(photo,telephone);
    }

    //报名者审核
    public List<Apply> jobCheck(String iPhone){
        return interviewerMapper.jobCheck(iPhone);
    }

    //pass
    public int pass(String telephone,String position){
        return interviewerMapper.pass(telephone,position);
    }

    //nopass
    public int nopass(String telephone,String position){
        return interviewerMapper.nopass(telephone,position);
    }

    //封装对象
    public User findUserByTelephone(String telephone){
        return interviewerMapper.findUserByTelephone(telephone);
    }

    //面试
    public List<Apply> findApply(String iphone){
        return interviewerMapper.findApply(iphone);
    }

    //面试通过
    public int yes(String telephone,String position){
        return interviewerMapper.yes(telephone,position);
    }

    //面试不通过
    public int no(String telephone,String position){
        return interviewerMapper.no(telephone,position);
    }
}
