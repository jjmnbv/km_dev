package dubbo;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kmzyc.product.remote.service.ProductAppraiseRemoteService;

import java.util.Map;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/8/10 13:56
 */
public class ProductAppraiseRemoteServiceTest {

    ProductAppraiseRemoteService service;

    @Before
    public void before() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{
                "/springs/spring-consumer.xml"
        });
        context.start();
        System.out.println("dubbo-server服务正在监听，按任意键退出");
        service = context.getBean("productAppraiseRemoteService", ProductAppraiseRemoteService.class);
    }

    @Test
    public void test() {
        try {
            //17
            //8
            Map<String, Object> map = service.queryProductAppraise(true, "desc", 3480l, 1);
            System.out.println("-------------------------------------------------------------");
            System.out.println("结果--------------------：" + map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
