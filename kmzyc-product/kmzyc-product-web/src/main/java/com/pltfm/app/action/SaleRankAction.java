package com.pltfm.app.action;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.product.remote.service.ProductRelationRemoteService;
import com.pltfm.app.service.ProductSkuQuantityService;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductmainTied;


@Component("saleRankAction")
@Scope("protoType")
public class SaleRankAction extends BaseAction {

	// 传递进来的productSku 对象
	private ProductSku productSku;

	// 开始时间
	private Date beginDate;

	// 结束时间
	private Date endDate;
	// 同一个类别下的map信息
	private Map<ProductmainTied, Integer> sameCategoryMap;
	// 同一个价位下的map信息
	private Map<ProductmainTied, Integer> samePriceMap;
	// 同一个品牌下的map信息
	private Map<ProductmainTied, Integer> sameBrandMap;
	@Resource
     private ProductSkuQuantityService productSkuQuantityService ;
	
    @Resource
	private  ProductRelationRemoteService  productRelationRemoteService;
	
	public String querySaleRank() {

		if (productSku == null) {
			return "saleRank";
		}

		try {


//			System.out.println("你好");
			Integer i=12/10;
//			System.out.println(i);
			
		//	productSkuQuantityService.updateProductSkuQuanlityEveryMonth(new Date());
			
			
			
//	List<ProductRelation>list=productRelationRemoteService.queryProductAndDetailPackageList(new Long(9615));
////			System.out.println(list.size());
//	List<ProductRelation>list1=productRelationRemoteService.queryProductAndDetailRecommendList(new Long(9615));		
//               System.out.println(list1.size());	
////			// 根据一个产品的skuCode 查询出 同一类别下的skuCode 集合出来
//			List<String> sameCategorylist = productSkuService
//					.selectSkuCodeListByCategory(productSku);
//			// 根据同一类别的skuCode list 得到相对应的信息
//			sameCategoryMap = saleRankService.getQuantityAndProductSkuMap(
//					sameCategorylist, beginDate, endDate);
//			getRequest().setAttribute("sameCategoryMap", sameCategoryMap);
//
////			// 同一品牌 下，默认查询的是同一类别的产品  
//               List<String> samBrandList = productSkuService
//				.selectSkuCodeListByCategoryBrandBySkuCode(productSku);
////
//			sameBrandMap = saleRankService.getQuantityAndProductSkuMap(
//					samBrandList, beginDate, endDate);
//
//			getRequest().setAttribute("sameBrandMap", sameBrandMap);
//
//     			// 同一价位下
//			List<String> samePriceList = productSkuService
//					.selectSamePriceStatus(productSku);
//			samePriceMap = saleRankService.getQuantityAndProductSkuMap(
//					samePriceList, beginDate, endDate);
//			getRequest().setAttribute("samePriceMap", samePriceMap);

		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("失败");
		}

		return SUCCESS;

	}

	public String saleRankShow() {

		return SUCCESS;

	}

	public ProductSku getProductSku() {
		return productSku;
	}

	public void setProductSku(ProductSku productSku) {
		this.productSku = productSku;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
