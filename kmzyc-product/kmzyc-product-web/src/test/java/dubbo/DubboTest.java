package dubbo;

import com.kmzyc.product.remote.service.StockRemoteService;
import com.pltfm.app.vobject.ProductStock;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/7/8 14:15
 */
public class DubboTest {

    //将本地的注册中心改为211
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{
                "/springs/spring-consumer.xml"
        });
        context.start();
        System.out.println("dubbo-server服务正在监听，按任意键退出");
        StockRemoteService stockRemoteService = context.getBean("stockRemoteService", StockRemoteService.class);
        //testLock(stockRemoteService);
        testUnlock(stockRemoteService);
        System.gc();
    }

    private static void testLock(StockRemoteService stockService) {
        try {
            List<ProductStock> list = new ArrayList<ProductStock>();
            ProductStock stock = new ProductStock();
            stock.setSkuAttValue("041000266015");
            stock.setLockCount(30l);
            ProductStock stock2 = new ProductStock();
            stock2.setSkuAttValue("041000266014");
            stock2.setLockCount(30l);
            list.add(stock);
            list.add(stock2);
            Map<String, Object> map = stockService.batchLockStock4KJ(list);
            System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testUnlock(StockRemoteService stockService) {
        try {
            List<ProductStock> list = new ArrayList<ProductStock>();
            ProductStock stock = new ProductStock();
            ProductStock stock2 = new ProductStock();
            stock.setSkuAttValue("041000266015");
            stock.setUnLockCount(90l);
            stock.setWarehouseId(22l);
            stock2.setSkuAttValue("041000266014");
            stock2.setWarehouseId(22l);
            stock2.setUnLockCount(78l);
            list.add(stock);
            list.add(stock2);
            Map<String, Object> map = stockService.batchUnLockStock4KJ(list);
            System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
