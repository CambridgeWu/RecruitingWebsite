package com.yidong.recruiting.controller;

import com.yidong.recruiting.entity.User;
import com.yidong.recruiting.service.Impl.InterviewerServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/interviewer")
public class InterviewerController {

    @Autowired
    InterviewerServiceImpl interviewerService;

    //头像更新
    @RequestMapping("/upload")
    public String uploadHeadPhoto(
            @RequestPart(value = "animateimg",required = false) MultipartFile animateimg,
            @RequestPart(value = "telephone",required = false) MultipartFile telephone
    ) throws IOException {
        String originalFilename = animateimg.getOriginalFilename();
        animateimg.transferTo(new File("C:\\recruitingPhoto\\"+originalFilename));
        return null;
    }


    //面试官个人信息修改
    @PostMapping("/userInfoUpdata")
    public String userInfoUpdata(
//            @RequestPart(value = "img",required = false) MultipartFile head_photo,
            @RequestParam(value = "userName",required = false)String userName,
            @RequestParam(value = "userAge",required = false)int userAge,
            @RequestParam(value = "sex",required = false)String sex,
            @RequestParam(value = "email",required = false)String email,
            @RequestParam(value = "telephone",required = false)String telephone,
            @RequestParam(value = "education",required = false)String education,
            @RequestParam(value = "desc",required = false)String desc,
            Model model
    ) throws IOException {
        //获得用户信息进行封装然后存进数据库
        //1.查看输入是否正确
        if(!"".equals(userName)&&!userName.matches("^[\\u2E80-\\u9FFF]+$")){
            model.addAttribute("msg","用户名输入错误！");
            return "/interviewer";
        }

        if(!"".equals(userAge)&&(userAge>120||userAge<0)){
            model.addAttribute("msg","年龄输入有误!");
            return "/interviewer";
        }

        if(!"".equals(sex)&&!("男".equals(sex)||"女".equals(sex))){
            model.addAttribute("msg","性别输入有误!");
            return "/interviewer";
        }

        if(!"".equals(email)&&!email.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")){
            model.addAttribute("msg","邮箱输入有误!");
            return "/interviewer";
        }

        //2.进行封装,存入数据库
        int result = interviewerService.updateInfo(userName,userAge,sex,email,education,desc,telephone);
        if(result==1){//插入成功
            User user = interviewerService.findUser(telephone);

            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            session.setAttribute("user",user);
            return "redirect:/interviewer";
        }else {
            model.addAttribute("msg","修改失败！");
            return "/interviewer";
        }
    }

    //发布招聘
    @PostMapping("/postRecruit")
    public String postRecruit(
            @RequestParam(value = "telephone",required = false)String telephone,
            @RequestParam(value = "region",required = false)String region,
            @RequestParam(value = "education",required = false)String education,
            @RequestParam(value = "salary",required = false)String salary,
            @RequestParam(value = "scaleCompany",required = false)String scaleCompany,
            @RequestParam(value = "positionType",required = false)String positionType,
            @RequestParam(value = "position",required = false)String position,
            @RequestParam(value = "descPo",required = false)String descPo,
            Model model
    ){
        //获得面试官名字
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = (User)session.getAttribute("user");
        int result = interviewerService.postRecruit(user.getUserName(), telephone, region, education, salary, scaleCompany, positionType, position, descPo);
        if(result==1){
            model.addAttribute("msg","申请发布招聘成功！");
            return "/interviewer";
        }else {
            model.addAttribute("msg","申请发布招聘失败！");
            return "/interviewer";
        }
    }
}
