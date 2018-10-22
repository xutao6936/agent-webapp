package com.agent.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class LoginController {



    @RequestMapping(value = "/login",method=RequestMethod.GET)
    public String toLogin(){
        return "login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName,HttpServletRequest request,
                       Model model) {
        String exceptionName = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        String errorMsg = "";
        if(exceptionName!=null){
            if (UnknownAccountException.class.getName().equals(exceptionName)) {
                log.info("UnknownAccountException -- > 账号{}不存在",userName);
                errorMsg = " 账号不存在："+userName;
            } else if (IncorrectCredentialsException.class.getName().equals(exceptionName)) {
                log.info("IncorrectCredentialsException -- > 密码不正确：");
                errorMsg = "密码不正确：";
            } else if ("kaptchaValidateFailed".equals(exceptionName)) {
                log.info("kaptchaValidateFailed -- > 验证码错误");
                errorMsg = " 验证码错误";
            } else {
                errorMsg = "其他登录异常 ";
                log.info("其他登录异常"+exceptionName);
            }
            model.addAttribute("loginErrorMsg",errorMsg);
        }
        return "login";
    }




}
