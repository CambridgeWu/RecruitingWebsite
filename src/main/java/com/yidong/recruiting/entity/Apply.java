package com.yidong.recruiting.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Apply {
    private int id;
    private String telephone;     //报名者手机号
    private int num;              //对应的职位编号
    private String iphone;        //面试官手机号
    private String userName;
    private String position;
    private int judge;         //面试
    private int personNum;
    private int queue;      //排队
}
