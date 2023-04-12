package com.len.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.len.core.annotation.Log;
import com.len.core.shiro.Principal;
import com.len.entity.SysUser;
import com.len.menu.LoginType;
import com.len.service.AdminLoginService;
import com.len.util.CustomUsernamePasswordToken;
import com.len.util.MsHelper;
import com.len.util.VerifyCodeUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 登录、退出页面
 */

/**
 * 登录、退出页面
 */
@Controller
@Slf4j
@Api(value = "登录业务", tags = "登录业务")
public class LoginController {
    private static final Long TWO_WEEK = 1000 * 60 * 60 * 24 * 14L;

    private final AdminLoginService adminLoginService;

    public LoginController(AdminLoginService adminLoginService) {
        this.adminLoginService = adminLoginService;
    }

    @GetMapping(value = "")
    public String login() {
        return toLogin();
    }

    @GetMapping(value = "goLogin")
    public String goLogin(Model model) {
        Subject sub = SecurityUtils.getSubject();
        if (sub.isAuthenticated()) {
            return "/main/main";
        } else {
            model.addAttribute("message", MsHelper.getMsg("login.again"));
            return "/login2";
        }
    }

    @GetMapping(value = "/login")
    public String toLogin() {
        Subject sub = SecurityUtils.getSubject();
        if (sub.isAuthenticated() || sub.isRemembered()) {
            return "/main/main";
        }
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        String loginFailureMsg = request.getParameter("message");
        if(!StringUtils.isEmpty(loginFailureMsg)){
            request.setAttribute("message",loginFailureMsg);
        }
        return "/login2";
    }

    /**
     * 登录动作
     *
     * @param user
     * @param model
     * @return
     */
    @ApiOperation(value = "/login", httpMethod = "POST", notes = "登录接口")
    @PostMapping("/login")
    public String login(SysUser user, Model model, HttpServletRequest request) {
        CustomUsernamePasswordToken token =
            new CustomUsernamePasswordToken(user.getUsername().trim(), user.getPassword(), LoginType.SYS);
        Subject subject = Principal.getSubject();
        String msg = null;
        try {
            subject.login(token);
            if (subject.isAuthenticated()) {
                String isRemember = request.getParameter("isRemember");
                if (!StringUtils.isEmpty(isRemember) && "true".equals(isRemember)) {
                    subject.getSession().setTimeout(TWO_WEEK);
                }
                return "redirect:/main";
            }
        } catch (UnknownAccountException | IncorrectCredentialsException e) {
            msg = MsHelper.getMsg("login.error");
        } catch (ExcessiveAttemptsException e) {
            msg = MsHelper.getMsg("locking");
        }
        if (msg != null) {
            model.addAttribute("message", msg);
        }
        return "/login2";
    }

    @GetMapping("/main")
    public String main() {
        return "main/main";
    }

    @Log(desc = "用户退出平台")
    @GetMapping(value = "/logout")
    public String logout() {
        adminLoginService.logout();
        return "/login2";
    }

    /**
     * 获取验证码
     * 
     * @param response
     * @param request
     */
    @GetMapping(value = "/getCode")
    public void getVerityCode(HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpg");

            // 生成随机字串
            String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
            log.info("verifyCode:{}", verifyCode);
            // 存入会话session
            HttpSession session = request.getSession(true);
            session.setAttribute("code", verifyCode.toLowerCase());
            // 生成图片
            int w = 100, h = 35;
            VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
