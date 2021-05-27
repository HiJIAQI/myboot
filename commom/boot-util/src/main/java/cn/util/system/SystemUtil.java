package cn.util.system;

/**
 * 功能描述：系统工具类
 *
 * @author JIAQI
 * @date 2020/3/20 - 9:14
 */

public class SystemUtil {

    /**
     * 获取当前系统(win or linux)
     */
    public static String getSystem() {
        return System.getProperty("os.name").toLowerCase();
    }



}
