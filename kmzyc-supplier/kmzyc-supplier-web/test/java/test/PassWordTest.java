package test;

import com.km.framework.common.util.MD5;
import org.junit.Test;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/11/28 9:15
 */
public class PassWordTest {

    @Test
    public void test() {
        String loginPassWord = "1bbd886460827015e5d605ed44252251";
        String salt = "11111";

        System.out.println(MD5.getMD5Str(loginPassWord + salt).toLowerCase());
    }
}
