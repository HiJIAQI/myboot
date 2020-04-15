import com.itcast.RedisApplication;
import com.itcast.redis.util.RedisUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/4/15 - 15:19
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class RedisTest {

    @Autowired
    RedisUtil redisUtil;

    @Test
    public void redisTest() {
        boolean age = redisUtil.set("name", "李四");
        Assert.assertEquals(age, true);
    }

}
