package com.yidong.recruiting.service;


import com.github.pagehelper.PageInfo;
import com.yidong.recruiting.entity.Page;
import com.yidong.recruiting.entity.Recruit;


public interface VisitorService{
    //查询所有招聘
    public PageInfo<Recruit> findAllRecruit(Page page);

    //搜索查询
    public PageInfo<Recruit> search(Page page,String region, String education, String salary, String scaleCompany, String positionType);
}
