package com.pltfm.app.action;

import com.google.common.collect.Lists;
import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.Action;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.service.CmsProductUpShelfService;
import com.pltfm.app.service.ProductRelationService;
import com.pltfm.app.service.ProductSkuService;
import com.pltfm.app.service.ViewProductSkuService;
import com.pltfm.app.service.ViewSkuAttrService;
import com.pltfm.app.vobject.ProductRelationAndDetail;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ViewProductSku;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 
 * @author tanyunxing
 * 
 */
@Controller("productSkuAction")
@Scope(value = "prototype")
public class ProductSkuAction extends BaseAction {

	private Long skuId;

	@Resource
	private ProductSkuService productSkuService;

	@Resource
	private ProductRelationService productRelationService;

	@Resource
	private CmsProductUpShelfService cmsProductUpShelfService;	
	
	@Resource
	private ViewProductSkuService viewProductSkuService;
	
	@Resource
	private ViewSkuAttrService viewSkuAttrService;
	
	private ViewProductSku viewProductSkuCondition = new ViewProductSku();
	
	private ViewProductSku viewProductSku = new ViewProductSku();
	
	private ProductSku productSku;

	// SKU商品的重量
	private String[] skuWeight;
	// 要更新的SKUID
	private Long[] productSkuId;
	
	private String type;

    /**
     * 初始化page和viewProductSkuCondition
     */
    private void initPageAndViewProductSkuCondition() {
        if (page == null) {
            page = new Page();
        }

        if (viewProductSkuCondition == null) {
            viewProductSkuCondition = new ViewProductSku();
        }
    }

	public String deleteProductSku() {
		String i = "0";

		try {
			ResultMessage rm = productSkuService.checkSkuRelation(skuId);

			// 查询出该sku产品关联主产品 productId,以及自己的productId
            ProductSku productSku=productSkuService.queryProductSkuList (skuId);
	        if (productSku == null) {
                strWriteJson("没有找到商品信息。");
                return null;
            }

			// 自身的productId
            List<Integer> prodIdList = Lists.newArrayList(productSku.getProductId().intValue());
            List<ProductRelationAndDetail> detailList = productRelationService.selectProductRelationAndDetailsByRelaitonSkuId(skuId);

            List<Long> productSkuList = detailList.stream()
                    .map(ProductRelationAndDetail::getMainSkuId)
                    .collect(Collectors.toList());

            if(productSkuList.size()>0){
				 Map<Long, ProductSku> skuMap=productSkuService.getSkuIdAndCodeMap(productSkuList);
			      Set<Long> skuSet=skuMap.keySet();
                //被关联的productId
                prodIdList.addAll(skuSet.stream()
                        .map(skuId -> skuMap.get(skuId).getProductId().intValue())
                        .collect(Collectors.toList()));
			}
			if(prodIdList.size()>0){
				 // 刷新主产品的页面
				cmsProductUpShelfService.productUpShelfByCms(prodIdList);  
			}

			if (rm != null && !rm.getIsSuccess()) {
				i = rm.getMessage();
			}
		} catch (Exception e) {
			i = "2";
			logger.error("deleteProductSku error:", e);
		}
		strWriteJson(i);
		return null;
	}

	public String updateProductSKUWeight() {
		try {
			List<ProductSku> list = new ArrayList<ProductSku>();
			ProductSku s = null;
			for (int i = 0; i < skuWeight.length; i++) {
				s = new ProductSku();
				if (skuWeight[i].isEmpty()) {
					s.setUnitWeight(null);
				} else {
					s.setUnitWeight(Double.valueOf(skuWeight[i]));
				}

				s.setProductSkuId(productSkuId[i]);
				list.add(s);
			}
			productSkuService.updateUnitWeight(list);
		} catch (Exception e) {
            logger.error("updateProductSKUWeight 失败：", e);
			return Action.ERROR;
		}
		return Action.SUCCESS;
	}
	
	/**
	 * SKU列表（运费）
	 * @return
	 */
	public String findAllSkuForFreight(){
		try {
            initPageAndViewProductSkuCondition();

            viewProductSkuService.searchPageForFreightByUserId(page, viewProductSkuCondition, getLoginUserId());
            setProductStatusMap();
            setFreeStatusMap();
		} catch (Exception e) {
            logger.error("运费管理失败，", e);
			return ERROR;
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 编辑运费
	 * @return
	 */
	public String toEditSkuFreight(){
        try {
            viewProductSku = viewProductSkuService.findByProductSkuId(skuId);
            getRequest().setAttribute("attrList", viewSkuAttrService.findBySkuId(skuId));
            setFreeStatusMap();
        } catch (Exception e) {
            logger.error("编辑运费失败，", e);
            return ERROR;
        }
        return Action.SUCCESS;
    }
	
	/**
	 * 保存SKU数据
	 * @return
	 */
	public String saveSkuFreight(){
		int result = 0;
		try {
			productSku.setFreeStatus(viewProductSku.getFreeStatus());
			productSku.setFreight(viewProductSku.getFreight());
			result = productSkuService.updateProductSku(productSku);
		} catch (Exception e) {
            logger.error("保存SKU数据失败，", e);
		}
		
		
		if(result>0){
			try {
				//CMS上架接口
                cmsProductUpShelfService.productUpShelfByCms(Lists.newArrayList(viewProductSku.getProductId().intValue()));
			} catch (Exception e1) {
                logger.error("CMS上架接口失败，", e1);
			}
		}

        writeJson(result);
		return null;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public String[] getSkuWeight() {
		return skuWeight;
	}

	public void setSkuWeight(String[] skuWeight) {
		this.skuWeight = skuWeight;
	}

	public Long[] getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(Long[] productSkuId) {
		this.productSkuId = productSkuId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ViewProductSku getViewProductSkuCondition() {
		return viewProductSkuCondition;
	}

	public void setViewProductSkuCondition(ViewProductSku viewProductSkuCondition) {
		this.viewProductSkuCondition = viewProductSkuCondition;
	}

	public ViewProductSku getViewProductSku() {
		return viewProductSku;
	}

	public void setViewProductSku(ViewProductSku viewProductSku) {
		this.viewProductSku = viewProductSku;
	}

	public ProductSku getProductSku() {
		return productSku;
	}

	public void setProductSku(ProductSku productSku) {
		this.productSku = productSku;
	}

}
