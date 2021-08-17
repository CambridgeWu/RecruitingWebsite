package com.yidong.recruiting.service.Impl;


import com.yidong.recruiting.entity.Apply;
import com.yidong.recruiting.entity.Recruit;
import com.yidong.recruiting.entity.User;
import com.yidong.recruiting.mapper.ApplyerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplyerServiceImpl {

    @Autowired
    ApplyerMapper applyerMapper;

    //个人中心更新
    public int updateInfo(String userName,int userAge,String sex,String email,String education,String desc,String telephone){
        return applyerMapper.updateInfo(userName,userAge,sex,email,education, desc,telephone);
    }

    //查询人员
    public User findUser(String telephone){
        return applyerMapper.findUser(telephone);
    }

    //根据编号查找职位
    public Recruit findRecruitByNum(int num){
        return applyerMapper.findRecruitByNum(num);
    }

    //判断是否重复报名
    public List<Apply> judgeEntry(String telephone, int num){
        return applyerMapper.judgeEntry(telephone,num);
    }

    //报名
    public int entry(String telephone,int num,String iPhone,String userName,String position){
        return applyerMapper.entry(telephone,num,iPhone,userName,position);
    }

    //头像
    public int updatePhoto(String photo,String telephone){
        return applyerMapper.updatePhoto(photo,telephone);
    }

    //我的面试
    public List<Apply> myInterview(String telephone){
        return applyerMapper.myInterview(telephone);
    }

    //预约面试
    public int yuyue(String telephone,String position){
        return applyerMapper.yuyue(telephone,position);
    }

    //取消排队
    public int noQueue(String telephone,String position){
        return applyerMapper.noQueue(telephone,position);
    }

    //重新排队
    public int toQueue(String telephone,String position){
        return applyerMapper.toQueue(telephone,position);
    }
}
