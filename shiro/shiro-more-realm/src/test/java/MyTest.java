import com.itcast.MoreRealmApplication;
import com.itcast.dao.SysUserMapper;
import com.itcast.entity.SysUser;
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
 * @date 2020/5/19 - 11:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MoreRealmApplication.class)
public class MyTest {

    @Autowired
    SysUserMapper sysUserMapper;

    @Test
    public void aa() {
        SysUser sysUser = new SysUser();
        sysUser.setUserName("张三");
        sysUser.setPassword("615d6f158def3dac5ad6d817b11ae89a");
        SysUser user = sysUserMapper.findByUser(sysUser);
        Assert.assertNotNull(user);
    }
}
