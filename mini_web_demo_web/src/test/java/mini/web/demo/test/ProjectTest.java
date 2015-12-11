package mini.web.demo.test;

import com.github.liuxboy.mini.web.demo.service.CalcService;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.runner.RunWith;

import javax.annotation.Resource;

/**
 * Created In www.jdpay.com
 *
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/8/6 14:38
 * @comment ProjectTest
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/META-INF/spring/spring-config.xml",
        "/META-INF/spring/springMVC-servlet.xml"})
public class ProjectTest {
    @Resource
    private CalcService calcService;

    @Test
    public void calcVehicleTravelTimeAndDelayTest() {
        //拼接参数，发送测试数据
    }
}
