package com.yidong.recruiting.service.Impl;


import com.yidong.recruiting.entity.User;
import com.yidong.recruiting.mapper.UserLoginMapper;
import com.yidong.recruiting.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLoginLoginServiceImpl implements UserLoginService {

    @Autowired
    UserLoginMapper userLoginMapper;

    //登录验证
    public User loginVerify(String telephone){
        return userLoginMapper.loginVerify(telephone);
    }

    //注册
    //1.查看注册信息有无重复
    public User registerJudge(String userName,String telephone,String password){
        return userLoginMapper.registerJudge(userName,telephone,password);
    }
    //2.传入数据库进行保存
    public int saveUser(String username,String password,String telephone,String identity){
        return userLoginMapper.saveUser(username,password,telephone,identity);
    }

    //返回用户
    public User findUser(String telephone,String password,String identity){
        return userLoginMapper.findUser(telephone,password,identity);
    }

    //忘记密码：寻找用户
    public User finUserByEmail(String email,String identity){
        return userLoginMapper.finUserByEmail(email,identity);
    }

    //更改密码
    public int resetPassword(String password,String telephone,String identity){
        return userLoginMapper.resetPassword(password,telephone,identity);
    }
}
