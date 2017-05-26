package dubbo;

import com.kmzyc.product.remote.service.ProductRelationRemoteService;
import com.pltfm.app.vobject.ProductRelation;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/8/2 13:48
 */
public class ProductRelationRemoteServiceTest {

    ProductRelationRemoteService service;

    @Before
    public void before() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{
                "/springs/spring-consumer.xml"
        });
        context.start();
        service = context.getBean("productRelationRemoteService", ProductRelationRemoteService.class);
    }

    @Test
    public void test() {
        try {
            //人气
            List<ProductRelation> list = service.queryProductAndDetailRecommendList(114352l);
            System.out.println("人气结果：--------------------------------------------" + list);

            //套餐
            list = service.queryProductAndDetailPackageList(114352l);
            System.out.println("套餐结果2：--------------------------------------------" + list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
