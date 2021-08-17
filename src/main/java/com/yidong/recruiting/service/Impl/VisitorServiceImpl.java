package com.yidong.recruiting.service.Impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yidong.recruiting.entity.Page;
import com.yidong.recruiting.entity.Recruit;
import com.yidong.recruiting.mapper.VisitorMapper;
import com.yidong.recruiting.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VisitorServiceImpl implements VisitorService {
    @Autowired
    VisitorMapper visitorMapper;


    @Override
    public PageInfo<Recruit> findAllRecruit(Page page) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        return new PageInfo<Recruit>(visitorMapper.findAllRecruit());
    }

    @Override
    public PageInfo<Recruit> search(Page page,String region, String education, String salary, String scaleCompany, String positionType){
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        return new PageInfo<Recruit>((List<Recruit>) visitorMapper.search(region,education,salary,scaleCompany,positionType));
    }
}
