package com.yidong.recruiting.config;

import com.yidong.recruiting.entity.User;
import com.yidong.recruiting.service.Impl.AdminServiceImpl;
import com.yidong.recruiting.service.Impl.UserLoginLoginServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserLoginLoginServiceImpl userService;
    @Autowired
    AdminServiceImpl adminService;

    @Override
    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User)subject.getPrincipal();
        info.addStringPermission(currentUser.getIdentity());
        return info;
    }

    @Override
    //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userToken=(UsernamePasswordToken)token;
        User user = userService.loginVerify(userToken.getUsername());//实际传的是电话号码
        if (userService.loginVerify(user.getTelephone()) == null) {
            return null;
        }
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }
}
