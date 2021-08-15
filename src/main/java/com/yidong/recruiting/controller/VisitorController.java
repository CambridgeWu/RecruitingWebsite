package com.yidong.recruiting.controller;


import com.github.pagehelper.PageInfo;
import com.yidong.recruiting.entity.Page;
import com.yidong.recruiting.entity.Recruit;
import com.yidong.recruiting.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class VisitorController {
    @Autowired
    VisitorService visitorService;


    @RequestMapping("/yidong")
    public String yidong(Page page,Model model){
        PageInfo<Recruit> allRecruit = visitorService.findAllRecruit(page);
        model.addAttribute("page",allRecruit);
        return "/visitor";
    }

    //搜索
    @PostMapping("/search")
    public String search(
            @RequestParam(value = "region")String region,
            @RequestParam(value = "education")String education,
            @RequestParam(value = "salary")String salary,
            @RequestParam(value = "scaleCompany")String scaleCompany,
            @RequestParam(value = "positionType")String positionType,
            Page page,
            Model model
    ){
        PageInfo<Recruit> search = visitorService.search(page, region, education, salary, scaleCompany, positionType);
        model.addAttribute("page",search);
        return "/visitor";
    }
}
