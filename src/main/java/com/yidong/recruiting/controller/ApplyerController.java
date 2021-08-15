package com.yidong.recruiting.controller;

import com.github.pagehelper.PageInfo;
import com.yidong.recruiting.entity.Apply;
import com.yidong.recruiting.entity.Page;
import com.yidong.recruiting.entity.Recruit;
import com.yidong.recruiting.entity.User;
import com.yidong.recruiting.service.Impl.ApplyerServiceImpl;
import com.yidong.recruiting.service.VisitorService;
import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/applyer")
public class ApplyerController {


    @Autowired
    VisitorService visitorService;

    @Autowired
    ApplyerServiceImpl applyerService;

    @RequestMapping("/yidong")
    public String yidong(Page page,Model model){
        PageInfo<Recruit> allRecruit = visitorService.findAllRecruit(page);
        model.addAttribute("page",allRecruit);
        return "/applyer";
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
        return "/applyer";
    }

    //个人信息的修改
    @PostMapping("/personalUpdate")
    public String userInfoUpdate(
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


        //2.进行封装,存入数据库
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
            applyerService.updatePhoto(photoPath,telephone);
        }

        int result = applyerService.updateInfo(userName,userAge,sex,email,education,desc,telephone);
        if(result==1){//插入成功
            User user = applyerService.findUser(telephone);

            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            session.setAttribute("user",user);
            return "redirect:/personal";
        }else {
            model.addAttribute("msg","修改失败！");
            return "/personal";
        }
    }


    //查看详细
    @RequestMapping("/toDetail")
    public String toDetail(@RequestParam("num")int num,Model model){
        Recruit recruit = applyerService.findRecruitByNum(num);
        model.addAttribute("recruit",recruit);
        return "/detail";
    }

    //报名
    @RequestMapping("/enter")
    public String enter(
            @RequestParam("num")int num,
            Model model
    ){
        //获取用户手机号
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User user = (User)session.getAttribute("user");
        String telephone = user.getTelephone();
        //先判断是否重复报名
        List<Apply> applies = applyerService.judgeEntry(telephone, num);
        if(applies==null){
            applyerService.entry(telephone, num);
            model.addAttribute("msg","报名成功！");
            Recruit recruit = applyerService.findRecruitByNum(num);
            model.addAttribute("recruit",recruit);
            return "/detail";
        }else {
            model.addAttribute("msg","你已经报名过！");
            Recruit recruit = applyerService.findRecruitByNum(num);
            model.addAttribute("recruit",recruit);
            return "/detail";
        }
    }
}
