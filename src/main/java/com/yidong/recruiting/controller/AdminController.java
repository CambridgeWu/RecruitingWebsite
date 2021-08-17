package com.yidong.recruiting.controller;

import com.yidong.recruiting.entity.Recruit;
import com.yidong.recruiting.entity.User;
import com.yidong.recruiting.service.Impl.AdminServiceImpl;
import com.yidong.recruiting.service.Impl.UserLoginLoginServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/login")
    public String tologin(){
        return "/adminLogin";
    }

    @Autowired
    AdminServiceImpl adminService;
    @Autowired
    UserLoginLoginServiceImpl userLoginLoginService;


    @PostMapping("/alogin")
    public String login(
            @RequestParam(value = "userName",required = false)String userName,
            @RequestParam(value = "password",required = false)String password,
            Model model
    ){
        if("".equals(userName)||"".equals(password)){
            model.addAttribute("msg","用户名/密码不能为空！");
            return "/adminLogin";
        }else {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token=new UsernamePasswordToken(userName,password);
            try {
                subject.login(token);
                //封装管理员注册信息
                List<User> interviewer = adminService.getInterviewerInfo();
                Session session = subject.getSession();
                session.setAttribute("interviewer",interviewer);
                return "redirect:/admin";
            }catch (UnknownAccountException e){
                model.addAttribute("msg","用户名不存在!");
                return "/adminLogin";
            }catch (IncorrectCredentialsException e){
                model.addAttribute("msg","密码错误！");
                return "/adminLogin";
            }
        }
    }

    //确认注册
    @RequestMapping("/confirmRegister")
    public String confirmRegister(@RequestParam(value = "telephone")String telephone){
        adminService.confirmRegister(telephone);
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        List<User> interviewer = adminService.getInterviewerInfo();
        session.setAttribute("interviewer",interviewer);
        return "redirect:/admin";
    }

    //删除注册
    @RequestMapping("/deleteRegister")
    public String deleteRegister(@RequestParam(value = "telephone")String telephone){
        adminService.deleteRegister(telephone);
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        List<User> interviewer = adminService.getInterviewerInfo();
        session.setAttribute("interviewer",interviewer);
        return "redirect:/admin";
    }

    //返回招聘
    @RequestMapping("/returnJob")
    public String returnJob(){
        List<Recruit> recruits = adminService.returnRecruit();
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("recruits",recruits);
        return "redirect:/adminJob";
    }

    //确认添加
    @RequestMapping("/addJob")
    public String addJob(@RequestParam(value = "telephone")String telephone,
                         @RequestParam(value = "position")String position){
        adminService.addJob(telephone,position);
        List<Recruit> recruits = adminService.returnRecruit();
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("recruits",recruits);
        return "redirect:/adminJob";
    }


    //删除Job
    @RequestMapping("/deleteJob")
    public String deleteJob(@RequestParam(value = "telephone")String telephone,
                            @RequestParam(value = "position")String position){
        adminService.deleteJob(telephone,position);
        List<Recruit> recruits = adminService.returnRecruit();
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("recruits",recruits);
        return "redirect:/adminJob";
    }
}
