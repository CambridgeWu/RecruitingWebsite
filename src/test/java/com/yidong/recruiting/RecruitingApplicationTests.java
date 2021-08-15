package com.yidong.recruiting;

import com.yidong.recruiting.mappers.Usermapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class RecruitingApplicationTests {

    @Autowired
    DataSource dataSource;
    @Autowired
    Usermapper usermapper;
    @Test
    void contextLoads() throws SQLException {
       int i = usermapper.saveUser("吴剑桥", "1234567", "13286658135", "面试官");
        System.out.println(i);
    }

    @Autowired
    JavaMailSenderImpl mailSender;

    @Test
    void test1(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("cambridge");
        simpleMailMessage.setText("123123");

        simpleMailMessage.setTo("2381634205@qq.com");
        simpleMailMessage.setFrom("2381634205@qq.com");

        mailSender.send(simpleMailMessage);
    }


    @Test
    void test2() throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);

        helper.setSubject("cambridge");
        helper.setText("<a href=\"www.baidu.com\">dianji?</a>",true);

        helper.setTo("2381634205@qq.com");
        helper.setFrom("2381634205@qq.com");
        mailSender.send(mimeMessage);

    }

}
