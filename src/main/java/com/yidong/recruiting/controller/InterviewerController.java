package com.yidong.recruiting.controller;

import com.yidong.recruiting.entity.Apply;
import com.yidong.recruiting.entity.User;
import com.yidong.recruiting.service.Impl.InterviewerServiceImpl;
import com.yidong.recruiting.util.EmailUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/interviewer")
public class InterviewerController {

    @Autowired
    InterviewerServiceImpl interviewerService;



    //面试官个人信息修改
    @PostMapping("/userInfoUpdata")
    public String userInfoUpdata(
            @RequestPart(value = "file",required = false) MultipartFile file,
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

        if(!file.isEmpty()){
            //新文件名
            String originalFilename = file.getOriginalFilename();
            //获取文件后缀
            String extension = FilenameUtils.getExtension(originalFilename);
            if(!"png".equals(extension.toLowerCase())&&!"jpg".equals(extension.toLowerCase())&&!"jpeg".equals(extension.toLowerCase())&&!"bmp".equals(extension.toLowerCase())){
                model.addAttribute("msg","文件类型错误,请上传图片类型");
                return "/interviewer";
            }
            String newFileName=userName+"."+telephone+"."+extension;

            File uploads = new File("C:\\recruitPhoto\\");
            // 如果文件夹不存在则创建
            if (!uploads.exists() && !uploads.isDirectory()) {
                uploads.mkdirs();
            }

            File photo = new File(uploads,newFileName);
            file.transferTo(photo);

            //端口号访问
            String photoPath="http://localhost:8080/"+"/api/file/"+newFileName;

            interviewerService.updatePhoto(photoPath,telephone);
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
            @RequestParam(value = "time",required = false) String time,
            @RequestParam(value = "descPo",required = false)String descPo,
            Model model
    ){
        if(!time.matches("^\\d{4}-\\d{2}-\\d{2}$")){
            model.addAttribute("msg","请输入正确的时间！");
            return "/interviewerJob";
        }
        //获得面试官名字
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = (User)session.getAttribute("user");
        int result = interviewerService.postRecruit(user.getUserName(), telephone, region, education, salary, scaleCompany, positionType, position,time, descPo);
        if(result==1){
            model.addAttribute("msg","申请发布招聘成功！");
            return "/interviewerJob";
        }else {
            model.addAttribute("msg","申请发布招聘失败！");
            return "/interviewerJob";
        }
    }


    //报名者审核
    @RequestMapping("/jobCheck")
    public String jobCheck(Model model){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = (User)session.getAttribute("user");

        //用户信息
        List<Apply> applys = interviewerService.jobCheck(user.getTelephone());
        model.addAttribute("applys",applys);
        return "/interviewerJobCheck";
    }

    @Autowired
    EmailUtil emailUtil;

    //报名通过
    @RequestMapping("/pass")
    public String pass(
            @RequestParam(value = "telephone")String telephone,
            @RequestParam(value = "position")String position,
            Model model
            ){
        //数据库更改+发送邮件
        interviewerService.pass(telephone, position);
        User user = interviewerService.findUserByTelephone(telephone);
        String subjectName="报名审核结果";
        String result="你报名通过，请在指定面试时间内去面试";
        emailUtil.sentEamil(user.getEmail(),subjectName,result);
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user1 = (User)session.getAttribute("user");

        //用户信息
        List<Apply> applys = interviewerService.jobCheck(user1.getTelephone());
        model.addAttribute("applys",applys);
        return "/interviewerJobCheck";
    }

    //报名不通过
    @RequestMapping("/nopass")
    public String nopass(
            @RequestParam("telephone")String telephone,
            @RequestParam("position")String pos,
            Model model
    ){
        //数据库更改+发送邮件
        User user = interviewerService.findUserByTelephone(telephone);
        interviewerService.nopass(telephone, pos);
        String subjectName="报名审核结果";
        String result="你报名未通过，请不要伤心，再接再厉";
        emailUtil.sentEamil(user.getEmail(),subjectName,result);
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user1 = (User)session.getAttribute("user");

        //用户信息
        List<Apply> applys = interviewerService.jobCheck(user1.getTelephone());
        model.addAttribute("applys",applys);
        return "/interviewerJobCheck";
    }

    //面试
    @RequestMapping("/mianshi")
    public String mianshi(Model model){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = (User)session.getAttribute("user");
        List<Apply> applys = interviewerService.findApply(user.getTelephone());
        model.addAttribute("applys", applys);
        return "/mianshi";
    }

    //面试通过
    @RequestMapping("/yes")
    public String yes(
            @RequestParam(value = "telephone")String telephone,
            @RequestParam(value = "position")String position,
            @RequestParam(value = "iphone")String iphone,
            Model model
    ){
        //数据库更改+发送邮件
        interviewerService.yes(telephone, position);

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = (User)session.getAttribute("user");
        List<Apply> applys = interviewerService.findApply(user.getTelephone());
        model.addAttribute("applys", applys);

        String subjectName="面试结果";
        String result="你面试通过，请前往报道,详情请咨询面试官电话："+iphone;
        User applyer = interviewerService.findUserByTelephone(telephone);
        emailUtil.sentEamil(applyer.getEmail(),subjectName,result);
        return "/mianshi";
    }

    //面试未通过
    @RequestMapping("/no")
    public String no(
            @RequestParam(value = "telephone")String telephone,
            @RequestParam(value = "position")String position,
            @RequestParam(value = "iphone")String iphone,
            Model model
    ){
        //数据库更改+发送邮件
        String subjectName="面试结果";
        String result="你的面试未通过，请保持好心态。详情请咨询面试官电话："+iphone;
        User applyer = interviewerService.findUserByTelephone(telephone);
        emailUtil.sentEamil(applyer.getEmail(),subjectName,result);

        interviewerService.no(telephone, position);

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = (User)session.getAttribute("user");
        List<Apply> applys = interviewerService.findApply(user.getTelephone());
        model.addAttribute("applys", applys);

        return "/mianshi";
    }

}
