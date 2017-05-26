package hessian;
import java.net.MalformedURLException;
import java.util.Date;
import com.pltfm.app.service.ProductService;
import com.pltfm.app.vobject.Product;
/**
 * 客户端原生hessian调用
 * @author Administrator
 *
 */
public class MainTest {
//	public static void main(String[] args){
//		String url = "http://10.1.0.211:7080/hessian/productService";
//
//		HessianProxyFactory factory = new HessianProxyFactory();
//		ProductService productService = null;
//		try {
//			productService = (ProductService) factory.create(ProductService.class, url);
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Product p = new Product();
//		p.setCategoryId(123);
//		p.setArchiveTime(new Date());
//		p.setChannel("channel");
//		p.setMarketPrice(0.128);
//		p.setCostPrice(0.11);
//		p.setIntroduce("clob");
//		try {
//			System.out.println("返回值==== " + productService.insertProduct(p));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
