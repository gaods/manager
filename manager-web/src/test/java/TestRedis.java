import com.hesh.service.UserService;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.annotation.Resource;

/**
 * Created by gaods on 2017/8/2.
 */
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class TestRedis extends AbstractJUnit4SpringContextTests {



    @Resource
    UserService userService;

    @Test
    public void testredis(){
        userService.add();
    }
}
