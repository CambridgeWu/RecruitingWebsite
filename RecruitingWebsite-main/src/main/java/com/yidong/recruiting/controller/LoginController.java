package com.yidong.recruiting.controller;

import com.yidong.recruiting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class LoginController {
    //验证码发送
    @RequestMapping("/yzm")
    @ResponseBody
    public String yzm(){
        char arrs[]={
            '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        };
        String code="";
        for (int i = 0; i < 4; i++) {
            int r=(int)(Math.random()*arrs.length);
            code+=arrs[r];
        }
        return code;
    }

    @Autowired
    UserService userService;

    //登录验证
    @PostMapping(value = "/loginJudge")
    public String login(
            @RequestParam(value = "Telephone",required = false)String telephone,
            @RequestParam(value = "Password",required = false)String password,
            @RequestParam(value = "code",required = false)String code,
            @RequestParam(value = "id",required = false)String identity,
            @RequestParam(value = "yzm",required = false)String yzm,
            Model model
                        ) {
        if ("".equals(telephone) || "".equals(password) || "".equals(code) ||"".equals(identity)) {
            model.addAttribute("msg", "登录信息不能为空！");
            return "login";
        } else {
            if (!yzm.toLowerCase().equals(code.toLowerCase())) {
                model.addAttribute("msg", "你的验证码输入有误！");
                return "login";
            } else {
                if (userService.loginVerify(telephone, password) == null) {
                    model.addAttribute("msg", "手机号/密码错误！");
                    return "login";
                } else {
                    //登录成功：根据身份进入不同页面
                    if ("管理员".equals(identity)) {
                        return "admin";
                    } else if ("面试官".equals(identity)) {
                        return "redirect:interviewer";
                    } else if ("报名者".equals(identity)) {
                        return "welcome";
                    }
                    model.addAttribute("msg", "登陆失败！");
                    return "login";
                }
            }
        }
    }

    //注册
    @RequestMapping("/register")
    @ResponseBody
    public String register(
            @RequestParam(value = "telephone",required = false)String telephone,
            @RequestParam(value = "userName",required = false)String userName,
            @RequestParam(value = "password1",required = false)String password1,
            @RequestParam(value = "password2",required = false)String password2,
            @RequestParam(value = "Id",required = false)String identity                        //身份
            ){
        if(telephone==null||userName==null||password1==null||password2==null||identity==null){
            return "注册信息不能为空！";
        }else {
            //2.判断用户名和密码以及手机号输入格式是否符合要求
            if(telephone.matches("^1[3578][0-9]{9}$")&&password1.matches("^\\w{6,18}$")&&password2.matches("^\\w{6,18}$")){
                //判断密码两次输入是否相同
                if(password1.equals(password2)){
                    //相同
                    //查询是否注册过
                    if(userService.registerJudge(userName,telephone,password1)!=null){//已经注册过
                        return "你已经注册过";
                    }else {
                        //没有注册过
                        int result = userService.saveUser(userName, password1, telephone, identity);
                        if(result==1){
                            return "注册成功";
                        }else {
                            return "注册失败";
                        }
                    }
                }else {
                    return "两次密码输入不同！";
                }
            }else {
                return "你输入的格式出现错误！";
            }
        }
    }

}
