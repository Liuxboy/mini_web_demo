package com.github.liuxboy.mini.web.demo.controller;


import com.github.liuxboy.mini.web.demo.common.constant.ManageConstant;
import com.github.liuxboy.mini.web.demo.common.util.Logger;
import com.github.liuxboy.mini.web.demo.common.util.LoggerFactory;
import com.github.liuxboy.mini.web.demo.dao.entity.UserEntity;
import com.github.liuxboy.mini.web.demo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * User: liuchundong
 * Date: 3/2/14
 * Time: 3:17 PM
 */
@Controller
public class IndexCtrl {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private HttpServletRequest request;

    private String password;

    @Resource
    private UserService userService;

    @RequestMapping(value = {"/index", "/"})
    public String index() {
        return "login";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login/do")
    public String loginDo(@Valid UserEntity user) {
        String message = checkLogin(user);
        if (StringUtils.isBlank(message)) {
            UserEntity userEntity = userService.login(user);
            if (null != userEntity) {
                logger.info(String.format("%s登录成功@" + request.getRemoteAddr(), user.getUserName()));
                request.getSession().setAttribute(ManageConstant.LOGIN_USER_KEY, userEntity);
                return "index";
            } else {
                request.setAttribute("user", user);
                request.setAttribute("message", "用户名或密码错误");
                return "login";
            }
        } else {
            request.setAttribute("user", user);
            request.setAttribute("message", message);
            return "login";
        }
    }

    @RequestMapping(value = "/exit")
    public String logout() {
        request.getSession().removeAttribute(ManageConstant.LOGIN_USER_KEY);
        // 跳转到SSO的退出页面
        return "redirect:/logout";
    }

    private String checkLogin(UserEntity user) {
        String message = null;
        if (StringUtils.isBlank(user.getUserName()) || StringUtils.isBlank(user.getPassWord())) {
            message = "用户名密码不能为空";
        }
        return message;
    }

}
