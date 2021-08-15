package com.yidong.recruiting.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailUtil {

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
    JavaMailSenderImpl mailSender;

    public String sentEamil(String email){
        EmailUtil emailUtil=new EmailUtil();
        String yzm = emailUtil.yzm();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("招聘验证码");
        simpleMailMessage.setText(yzm);

        simpleMailMessage.setTo(email);
        simpleMailMessage.setFrom("2381634205@qq.com");

        mailSender.send(simpleMailMessage);
        return yzm;
    }
}
