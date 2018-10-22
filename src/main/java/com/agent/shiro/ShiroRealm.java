package com.agent.shiro;

import com.agent.entity.User;
import com.agent.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;


    // 授权回调函数，进行鉴权但缓存中没有时调用
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        Map<String,String> maps = new HashMap<String, String>();
        maps.put("userName",shiroUser.getUserName());
        User user = userService.findOne(maps);
        if(null == user){
            throw new UnknownAccountException("用户不存在");
        }
        if(!user.getEnabled()){
            throw new LockedAccountException("用户不可用");
        }
        if (user == null) {
            throw new UnknownAccountException();
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 基于role的权限信息
        info.addRole(user.getRoleName());
        // 基于permissions的权限信息
        //info.addStringPermissions(user.getRole().getAuthList());

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName = token.getUsername();
        Map<String,String> maps = new HashMap<String, String>();
        maps.put("userName",userName);
        User user = userService.findOne(maps);
        if(null == user){
            throw new UnknownAccountException("用户不存在");
        }
        if(!user.getEnabled()){
            throw new LockedAccountException("用户不可用");
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                new ShiroUser(user.getId(),user.getUserName(),user.getAge(),user.getTelphone(),user.getRoleId(),user.getRoleName()),user.getPassword(),userName);
        return simpleAuthenticationInfo;
    }
}
