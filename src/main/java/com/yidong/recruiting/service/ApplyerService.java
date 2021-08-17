package com.yidong.recruiting.service;

import com.yidong.recruiting.entity.Apply;
import com.yidong.recruiting.entity.Recruit;
import com.yidong.recruiting.entity.User;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ApplyerService {

    //个人中心更新
    public int updateInfo(String userName,int userAge,String sex,String email,String education,String desc,String telephone);

    //查询人员
    public User findUser(String telephone);

    //根据编号查找职位
    public Recruit findRecruitByNum(int num);

    //判断是否重复报名
    public List<Apply> judgeEntry(String telephone, int num);

    //报名
    public int entry(String telephone,int num,String iPhone,String userName,String position);

    //头像
    public int updatePhoto(String photo,String telephone);

    //我的面试
    public List<Apply> myInterview(String telephone);

    //预约面试
    public int yuyue(String telephone,String position);

    //取消排队
    public int noQueue(String telephone,String position);

    //重新排队
    public int toQueue(String telephone,String position);
}
