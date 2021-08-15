package com.yidong.recruiting.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/admin").setViewName("admin");
        registry.addViewController("/interviewer").setViewName("interviewer");
        registry.addViewController("/401").setViewName("401");
        registry.addViewController("/adminLogin").setViewName("adminLogin");
        registry.addViewController("/interviewerJob").setViewName("interviewerJob");
        registry.addViewController("/adminJob").setViewName("adminJob");
        registry.addViewController("/visitor").setViewName("visitor");
        registry.addViewController("/applyer").setViewName("applyer");
        registry.addViewController("/personal").setViewName("personal");
        registry.addViewController("/detail").setViewName("detail");
        registry.addViewController("/forgotPassword").setViewName("forgotPassword");
        registry.addViewController("/resetPassword").setViewName("resetPassword");
    }

}
