package com.yidong.recruiting.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig{

    @Bean("cookie")
    public SimpleCookie rememberMeCookie(){
        SimpleCookie cookie = new SimpleCookie("rememberMe");

        cookie.setHttpOnly(true);
        cookie.setMaxAge(7*24*24);
        return cookie;
    }

    @Bean("cookieRememberMeManager")
    public CookieRememberMeManager getCookieRememberMeManager(@Qualifier("cookie") SimpleCookie cookie){

        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();

        cookieRememberMeManager.setCookie(cookie);
        // rememberMe cookie加密的密钥
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));

        return cookieRememberMeManager;
    }


    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        bean.setSecurityManager(defaultWebSecurityManager);
        //添加Shiro过滤器
        Map<String,String> filterMap=new LinkedHashMap<>();

        filterMap.put("/interviewer/**","authc");
        filterMap.put("/applyer/**","authc");


        //退出
        filterMap.put("/loginOut","logout");

        //授权登录(admin和interviewer)
        filterMap.put("/interviewer/**","perms[面试官]");
        filterMap.put("/applyer/**","perms[报名者]");

        bean.setFilterChainDefinitionMap(filterMap);
        bean.setUnauthorizedUrl("/userLogin/noauth");


        bean.setLoginUrl("/userLogin/toLogin");
        return bean;
    }




    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

    //整合thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

}
