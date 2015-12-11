package com.github.liuxboy.mini.web.demo.service.impl;

import com.github.liuxboy.mini.web.demo.service.TestService;
import org.springframework.stereotype.Service;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/18 19:33
 * @comment TestServiceImpl
 */
@Service
public class TestServiceImpl implements TestService {
    @Override
    public String test() {
        return "Hello World";
    }
}
