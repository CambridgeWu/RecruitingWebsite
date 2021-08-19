package com.yidong.recruiting.service.Impl;

import com.yidong.recruiting.entity.Recruit;
import com.yidong.recruiting.entity.User;
import com.yidong.recruiting.mapper.AdminMapper;
import com.yidong.recruiting.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminmapper;

    @Override
    public User loginVerify(String userName, String password) {
        return adminmapper.loginVerify(userName,password);
    }

    //封装管理员注册信息
    public List<User> getInterviewerInfo(){
        return adminmapper.getInterviewerInfo();
    }

    //确认注册
    public int confirmRegister(String telephone){
        return adminmapper.confirmRegister(telephone);
    }

    //删除注册的面试官
    public int deleteRegister(String telephone){
        return adminmapper.deleteRegister(telephone);
    }

    //返回招聘
    public List<Recruit> returnRecruit(){
        return adminmapper.returnRecruit();
    }

    //添加Job
    public int addJob(String telephone,String position){
        return adminmapper.addJob(telephone,position);
    }

    //删除Job
    public int deleteJob(String telephone,String position){
        return adminmapper.deleteJob(telephone,position);
    }
}
