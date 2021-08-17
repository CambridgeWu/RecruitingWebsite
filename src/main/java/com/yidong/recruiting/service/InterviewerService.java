package com.yidong.recruiting.service;

import com.yidong.recruiting.entity.Apply;
import com.yidong.recruiting.entity.User;
import org.apache.ibatis.annotations.*;

import javax.xml.crypto.Data;
import java.util.List;

public interface InterviewerService {

    //个人中心
    public int updateInfo(String userName,int userAge,String sex,String email,String education,String desc,String telephone);

    //封装对象
    public User findUser(String telephone);

    //发布招聘
    public int postRecruit(String interviewer, String telephone, String region, String education, String salary, String scaleCompany, String positionType,
                           String position, String time, String descPo);

    //头像
    public int updatePhoto(String photo,String telephone);

    //报名者审核
    public List<Apply> jobCheck(String iPhone);

    //pass
    public int pass(String telephone,String position);

    //nopass
    public int nopass(String telephone,String position);

    //封装对象
    public User findUserByTelephone(String telephone);

    //面试
    public List<Apply> findApply(String iphone);

    //面试通过
    public int yes(String telephone,String position);

    //面试不通过
    public int no(String telephone,String position);
}
