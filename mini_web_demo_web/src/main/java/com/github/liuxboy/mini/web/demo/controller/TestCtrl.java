package com.github.liuxboy.mini.web.demo.controller;

import com.github.liuxboy.mini.web.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liuchundong
 * @version 1.0.0
 * @comment IndexCtrl
 * @since 2015/2/1
 */
@Controller
public class TestCtrl {
    @Autowired
    private TestService testService;
    //首页
    //@RequestMapping(value = "/test", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(HttpServletRequest request, HttpServletResponse response) {
        String testStr = testService.test();
        return "/test";
    }
}
