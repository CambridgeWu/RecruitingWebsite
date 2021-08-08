package com.yidong.recruiting.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig{

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        bean.setSecurityManager(defaultWebSecurityManager);
        //添加Shiro过滤器
        Map<String,String> filterMap=new LinkedHashMap<>();

        filterMap.put("/admin","authc");
        filterMap.put("/interviewer","authc");

        //退出
        filterMap.put("/loginOut","logout");

        //授权登录(admin和interviewer)
        filterMap.put("/admin","perms[管理员]");
        filterMap.put("/interviewer","perms[面试官]");

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
