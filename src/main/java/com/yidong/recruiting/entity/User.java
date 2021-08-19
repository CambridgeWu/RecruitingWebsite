package com.yidong.recruiting.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@lombok.Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class User implements java.io.Serializable{
    private int id;                 //招聘用于对应职业编号            面试官用于确定是否注册成功
    private String identity;        //身份：管理员   招聘者   面试官
    private String userName;        //用户名
    private String password;        //密码
    private int userAge;            //年龄
    private String telephone;       //手机号
    private String sex;             //性别
    private String headPhoto;       //大头照
    private String email;           //邮箱
    private String education;       //学历
    private int workExperience;     //工作经验
    private String desc;            //自我描述

}
