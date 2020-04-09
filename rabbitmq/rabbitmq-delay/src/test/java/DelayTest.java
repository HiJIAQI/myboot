import com.itcast.DelayApplication;
import com.itcast.delay.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/4/8 - 15:21
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DelayApplication.class)
public class DelayTest {

    @Autowired
    private MessageService messageService;

    @Test
    public void send() {
        messageService.sendMsg("test_queue_1", "hello i am delay msg");
    }
}
