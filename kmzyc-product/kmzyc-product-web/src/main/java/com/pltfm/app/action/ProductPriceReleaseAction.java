package com.pltfm.app.action;

import com.kmzyc.supplier.enums.SuppliersType;
import com.pltfm.app.service.ViewProductSkuService;
import com.pltfm.app.util.ProductListType;
import com.pltfm.app.vobject.ViewProductSku;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

import javax.annotation.Resource;

/**
 * 产品价格发布Action
 * 
 * @author tanyunxing
 * 
 */
@Controller("productPriceReleaseAction")
@Scope(value="prototype")
public class ProductPriceReleaseAction extends BaseAction {

	private Long productId;
	
	private String type;
	
	@Resource
	private ViewProductSkuService viewProductSkuService;

    /**
     * 查询单个sku
     * @return
     */
	public String findSingleProduct() {
		try {
			List<ViewProductSku> productSkuList = viewProductSkuService.findProductAndSkuAttrByProductId(productId);
			getRequest().setAttribute("productSkuList", productSkuList);
		} catch (Exception e) {
            logger.error("查询单个sku,", e);
		}
		
		if(ProductListType.WEIGHT.equals(type)){
			return ProductListType.WEIGHT;
		} else if("update".equals(type)){
            Integer supplierType = supplierAuditService.getSupplierTypeByProduct(productId);
            getRequest().setAttribute("sell", SuppliersType.SELL.getStatus().intValue() == supplierType);
            getRequest().setAttribute("support", SuppliersType.SUPPORT.getStatus().intValue() == supplierType
                    || SuppliersType.EMTER.getStatus().intValue() == supplierType);
        }
		
		return SUCCESS;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}