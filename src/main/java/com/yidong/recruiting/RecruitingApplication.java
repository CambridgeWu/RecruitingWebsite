package com.yidong.recruiting;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@EnableAsync
public class RecruitingApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecruitingApplication.class, args);
    }
}
