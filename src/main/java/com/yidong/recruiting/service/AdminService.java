package com.yidong.recruiting.service;

import com.yidong.recruiting.entity.Recruit;
import com.yidong.recruiting.entity.User;

import java.util.List;


public interface AdminService {
    //检验账号密码是否正确
    public User loginVerify(String userName, String password);

    //封装管理员注册信息
    public List<User> getInterviewerInfo();

    //确认注册
    public int confirmRegister(String telephone);

    //删除注册的面试官
    public int deleteRegister(String telephone);

    //返回招聘
    public List<Recruit> returnRecruit();

    //添加Job
    public int addJob(String telephone,String position);

    //删除Job
    public int deleteJob(String telephone,String position);
}
