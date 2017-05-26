package com.pltfm.app.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.pltfm.app.service.ProductMainTiedService;
import com.pltfm.app.service.ProductSkuService;
import com.pltfm.app.service.ProductTiedService;
import com.pltfm.app.vobject.ProductTiedShow;
import com.pltfm.app.vobject.ProductmainTied;
import com.pltfm.app.vobject.TiedSade;
import com.kmzyc.commons.page.Page;

@Controller("tiedSaleAction")
@Scope(value = "prototype")
public class TiedSaleAction extends BaseAction {
	// page 对象
	private Page page = new Page();

	private Long[] productSkuId;

	// 需要删除的关联产品的关系的主键值

	private Long[] tiedSadeId;

	// TiedSade 对象
	private TiedSade tiedSade = new TiedSade();
	// 用于查看 某产品其 搭售产品的 ProductTiedShow集合
	private List<ProductTiedShow> matchProdutShowList;

	@Resource
	private ProductMainTiedService productMainTiedService;
	// 查询条件传递进来的
	private ProductmainTied product = new ProductmainTied();
	// 在页面中显示主信息以及隐含参数
	private ProductmainTied prod = new ProductmainTied();

	// 传递添加的搭售组合的集合

	private List<TiedSade> list = new ArrayList<TiedSade>();

	@Resource
	private ProductTiedService productTiedService;
	
	// 主商品已经搭售的产品列表
	private   List<TiedSade>      tiedSaleListed;
	//sku 服务类
	@Resource
	private  ProductSkuService    productSkuService;
	//提示信息
     private String rtnMessage;
     
	public String updateTiedSaleType() {

		try {
			productTiedService.updateTiedSaleType(tiedSade);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;

	}

	// 产品搭售功能下 跳转到搭售查询的主界面
	public String tiedSaleShow() {
		
		if(rtnMessage!=null){
		if(rtnMessage.equalsIgnoreCase("saveTiedSuccess"))
			setRtnMessage("商品搭售成功");
		
		 if(rtnMessage.equalsIgnoreCase("saveTiedSaleFail"))
			 setRtnMessage("商品搭售失败");
		}
		
		try {
			productMainTiedService.selectList(product, page);
			// ajax 查询
			getCategoryList();
			setProductBrandMap();
			setProductStatusMap();
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	// 从一个主商品跳到为其添加产品的页面上去
	public String tiedSaleAdd() {
		try {
			
			TiedSade tiedSade=new  TiedSade();
			tiedSade.setMainSku(product.getProductSkuId());
		    tiedSaleListed = productTiedService
					.findListByExample(tiedSade);
			getCategoryList();
			setProductBrandMap();
			setProductStatusMap();
			getTiedSaleType(); // 搭售类型
			// 根据主商品的sku值查询出主商品的信息 ,在add页面中显示主产品的信息
			prod = productMainTiedService.findObjectBySku(product
					.getProductSkuId());
			productMainTiedService.selectListNotMainSKU(product, page);
			productMainTiedService.countItemExceptMainsku(product);

		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;

	}

	// 在添加页面中根据点击查询 根据名称查询列表
	public String addSearch() {
		
		// ajax 查询
		getCategoryList();
		setProductBrandMap();
		setProductStatusMap();
		getTiedSaleType();
		getTiedSaleType(); // 搭售类型
		try {
            //查询出主产品已搭售的产品，在添加添加页面不显示出来
			TiedSade tiedSade=new  TiedSade();
			tiedSade.setMainSku(product.getProductSkuId());
		    tiedSaleListed = productTiedService.findListByExample(tiedSade);
			
			prod = productMainTiedService.findObjectBySku(product
					.getProductSkuId());
			// 设置主产品的id，返回到页面 hidden type 类型中
			product.setProductId(prod.getProductSkuId());
			productMainTiedService.selectListNotMainSKU(product, page);
			productMainTiedService.countItemExceptMainsku(product);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;

	}

	// 在添加页面中根据sku 保存主商品以及匹配商品的关系
	public String saveTied() {
           
		try {
			productTiedService.insertTiedSaleByBatch(list);
		} catch (Exception e) {
			e.printStackTrace();
			setRtnMessage("saveTiedSaleFail");
		    return SUCCESS;
		}
		
		setRtnMessage("saveTiedSuccess");
		return SUCCESS;
	}

	// 产品搭售功能下 查看某商品下的搭售产品集合
	public String tiedSaleView() {
		     
		 if(rtnMessage!=null){
			 
			if(rtnMessage.equalsIgnoreCase("delTiedFail"))
			setRtnMessage("删除失败");
			 
		if(rtnMessage.equalsIgnoreCase("delTiedSuccess"))
			setRtnMessage("删除搭售成功");
			 
		 }
		
	
		try {
			getTiedSaleType(); // 搭售搭类型
			// 在tiedsade 表中根据主sku 得到其搭配商品的sku 记录集合
			List<TiedSade> list = productTiedService
					.findListByExample(tiedSade);
			matchProdutShowList = new ArrayList<ProductTiedShow>();
			product = productMainTiedService.findObjectBySku(tiedSade
					.getMainSku());
			for (TiedSade t : list) {
				// 根据被搭配的sku 查询出 其productmainTied 对象
				ProductmainTied prodTied = productMainTiedService
						.findObjectBySku(t.getTiedSadeSku());
				ProductTiedShow show = new ProductTiedShow();
				show.setSkuCode(prodTied.getProductSkuCode());
				show.setTiedSadeId(t.getTiedSadeId());
				show.setEdproductSkuId(t.getMainSku());
				show.setProductSkuId(t.getTiedSadeSku());
				show.setSelfName(prodTied.getProcuctName());
				show.setMatchPrice(t.getTiedSadeSkuPrice());
				show.setEdPrice(prodTied.getPrice());
				show.setTiedSadeType(t.getTiedSadeType());
				// 在查看页面显示搭配产品的信息 （集合）
				matchProdutShowList.add(show);
			}
			page.setRecordCount(matchProdutShowList.size());

		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	// 根据主产品的sku 以及被搭配的产品sku 在tied_sade 表中删除关联记录
	public String delTied() {

		
		try {
			productTiedService.delBatchByPrimaryKey(tiedSadeId);
			
		} catch (Exception e) {
			e.printStackTrace();
			setRtnMessage("delTiedFail");
			return SUCCESS;
		}
		
		setRtnMessage("delTiedSuccess");
		
		return SUCCESS;
	}

	// 搭销商品的搭配价格更改
	public String update() {
		try {
			productTiedService.updatePriceByKey(tiedSade);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 产品搭售功能查看模块 下的方法
	 * 
	 * @return
	 */

	// 产品关联下 跳转到搭售查看查询的主界面
	public String tiedSaleViewQuery() {
		
		if(rtnMessage!=null){
			if(rtnMessage.equalsIgnoreCase("saveSTSuccess"))
				
				setRtnMessage("关联成功");
			
			if(rtnMessage.equalsIgnoreCase("saveSTFail"))
				setRtnMessage("关联失败");
		      }
		
		try {
			productMainTiedService.selectList(product, page);
			// ajax 查询
			getCategoryList();
			setProductBrandMap();
			setProductStatusMap();
			getTiedSaleType(); // 搭售搭类型
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	// 产品关联功能下 跳转到添加页面
	public String tsAdd() {
		try {
			
			TiedSade tiedSade=new  TiedSade();
			tiedSade.setMainSku(product.getProductSkuId());
		    tiedSaleListed = productTiedService.findConnectListByMainSku(tiedSade);
			getCategoryList();
			setProductBrandMap();
			setProductStatusMap();
			getTiedSaleType(); // 搭售搭类型
			prod = productMainTiedService.findObjectBySku(product
					.getProductSkuId());
			productMainTiedService.selectListNotMainSKU(product, page);
			productMainTiedService.countItemExceptMainsku(product);

		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;

	}

	// 产品关联下 的添加页面中 根据条件查询功能
	public String stAddSearch() {
		// ajax 查询
		getCategoryList();
		setProductBrandMap();
		setProductStatusMap();
		getTiedSaleType(); // 搭售搭类型
		
		try {
			  tiedSade.setMainSku(product.getProductSkuId());
			  tiedSaleListed = productTiedService
						.findConnectListByMainSku(tiedSade);
			prod = productMainTiedService.findObjectBySku(product
					.getProductSkuId());
			// 设置主产品的id，返回到页面 hidden type 类型中
			product.setProductId(prod.getProductSkuId());
			productMainTiedService.selectListNotMainSKU(product, page);
			productMainTiedService.countItemExceptMainsku(product);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	// 产品关联下功能下 保存产品搭售关系功能
	public String saveST() {

		try {
			productTiedService.insertTiedSaleByBatch(list);
		} catch (Exception e) {
			e.printStackTrace();
			setRtnMessage("saveSTFail");
			return SUCCESS;
			
		}
           setRtnMessage("saveSTSuccess");
		return SUCCESS;

	}

	// 产品关联功能下的 查看主产品下的搭售产品
	public String tiedSaleViewToView() {
		
		
		if(rtnMessage!=null){//页面的提示信息
		if(rtnMessage.equalsIgnoreCase("delSTFail"))	
			setRtnMessage("删除关联失败");
			
		if(rtnMessage.equalsIgnoreCase("delSTSuccess"))
			setRtnMessage("删除关联成功");
		}
		
		    getTiedSaleType(); // 搭售搭类型
		try {
			// 在tiedsade 表中根据主sku 得到其搭配商品的sku 记录集合
			List<TiedSade> list = productTiedService
					.findConnectListByExample(tiedSade);
			matchProdutShowList = new ArrayList<ProductTiedShow>();
			product = productMainTiedService.findObjectBySku(tiedSade
					.getMainSku());
			for (TiedSade t : list) {
				// 根据被搭配的sku 查询出 其productmainTied 对象
				ProductmainTied prodTied = productMainTiedService
						.findObjectBySku(t.getTiedSadeSku());
				ProductTiedShow show = new ProductTiedShow();
				show.setSkuCode(prodTied.getProductSkuCode());
				show.setTiedSadeId(t.getTiedSadeId());
				show.setEdproductSkuId(t.getMainSku());
				show.setProductSkuId(t.getTiedSadeSku());
				show.setEdPrice(prodTied.getPrice());
				show.setSelfName(prodTied.getProcuctName());
				show.setMatchPrice(t.getTiedSadeSkuPrice());
				show.setTiedSadeType(t.getTiedSadeType());
		         
                
				// 在查看页面显示搭配产品的信息 （集合）
				matchProdutShowList.add(show);
			}
			page.setRecordCount(matchProdutShowList.size());

		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	// 产品关联功能下 的搭配商品删除
	public String delST() {
		try {
			productTiedService.delBatchByPrimaryKey(tiedSadeId);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			setRtnMessage("delSTFail");
			
			return SUCCESS;
			
		
		}
		setRtnMessage("delSTSuccess");
		return SUCCESS;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public TiedSade getTiedSade() {
		return tiedSade;
	}

	public void setTiedSade(TiedSade tiedSade) {
		this.tiedSade = tiedSade;
	}

	public List<ProductTiedShow> getMatchProdutShowList() {
		return matchProdutShowList;
	}

	public void setMatchProdutShowList(List<ProductTiedShow> matchProdutShowList) {
		this.matchProdutShowList = matchProdutShowList;
	}

	public Long[] getTiedSadeId() {
		return tiedSadeId;
	}

	public void setTiedSadeId(Long[] tiedSadeId) {
		this.tiedSadeId = tiedSadeId;
	}

	public Long[] getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(Long[] productSkuId) {
		this.productSkuId = productSkuId;
	}

	public ProductmainTied getProduct() {
		return product;
	}

	public void setProduct(ProductmainTied product) {
		this.product = product;
	}

	public ProductmainTied getProd() {
		return prod;
	}

	public void setProd(ProductmainTied prod) {
		this.prod = prod;
	}

	public List<TiedSade> getList() {
		return list;
	}

	public void setList(List<TiedSade> list) {
		this.list = list;
	}

	public List<TiedSade> getTiedSaleListed() {
		return tiedSaleListed;
	}

	public void setTiedSaleListed(List<TiedSade> tiedSaleListed) {
		this.tiedSaleListed = tiedSaleListed;
	}

	public String getRtnMessage() {
		return rtnMessage;
	}

	public void setRtnMessage(String rtnMessage) {
		this.rtnMessage = rtnMessage;
	}


	

}
