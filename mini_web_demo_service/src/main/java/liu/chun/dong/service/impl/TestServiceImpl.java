package liu.chun.dong.service.impl;

import liu.chun.dong.service.TestService;
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
