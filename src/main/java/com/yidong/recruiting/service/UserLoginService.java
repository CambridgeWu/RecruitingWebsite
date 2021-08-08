package com.yidong.recruiting.service;
import com.yidong.recruiting.entity.User;

public interface UserLoginService {
    //登录验证
    public User loginVerify(String telephone);

    //注册
    //1.查看注册信息有无重复
    public User registerJudge(String userName,String telephone,String password);
    //2.传入数据库进行保存
    public int saveUser(String username,String password,String telephone,String identity);

    //返回用户
    public User findUser(String telephone,String password,String identity);
}
