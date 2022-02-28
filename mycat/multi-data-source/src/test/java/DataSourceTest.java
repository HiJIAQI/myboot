import com.itcast.more.mapper.db2.Db2Mapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2021/10/7 - 17:17
 */
public class DataSourceTest {

    @Autowired
    Db2Mapper secondMapper;

//    @Test
//    public void db2() {
//        int countDb2 = secondMapper.countUser();
//        System.out.println("数据源二:" + countDb2);
//    }
}
