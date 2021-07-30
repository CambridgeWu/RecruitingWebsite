package com.yidong.recruiting.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.crypto.Data;

@lombok.Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class recruit {
    private int id;                 //用于管理员审核发布招聘是否通过
    private String interviewer;     //面试官
    private String telephone;       //手机号
    private String region;          //地区
    private String education;       //学历
    private String salary;          //薪资
    private int scaleCompany;      //公司规模
    private String positionType;   //职位类型
    private String position;        //具体职位
    private String descPo;         //职位描述
    private Data interviewTime;    //面试时间
    private int num;                //职位编号
}
