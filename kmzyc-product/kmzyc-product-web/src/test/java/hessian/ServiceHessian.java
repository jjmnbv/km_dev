package hessian;

import java.util.List;

import com.pltfm.app.vobject.Product;

public interface ServiceHessian {

	public abstract Integer insertProduct(Product product);
  
	public abstract List<Product> getProducts(Product product);

}
