package com.yidong.recruiting.service;


import com.yidong.recruiting.entity.User;
import com.yidong.recruiting.mapper.UserMapper;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    //登录验证
    public User loginVerify(String telephone, String password){
        return userMapper.loginVerify(telephone,password);
    }

    //注册
    //1.查看注册信息有无重复
    public User registerJudge(String userName,String telephone,String password){
        return userMapper.registerJudge(userName,telephone,password);
    }
    //2.传入数据库进行保存
    public int saveUser(String username,String password,String telephone,String identity){
        return userMapper.saveUser(username,password,telephone,identity);
    }
}
