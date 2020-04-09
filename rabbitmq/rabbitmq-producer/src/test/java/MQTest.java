import com.itcast.ProducerApplication;
import com.itcast.producer.rabbitmq.DirectProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProducerApplication.class)
public class MQTest {

    @Autowired
    DirectProducer directProducer;

    @Test
    public void sendEmail() {
        directProducer.sendEmail();
    }
}
