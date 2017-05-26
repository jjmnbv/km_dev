package com.pltfm.app.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.kmzyc.supplier.enums.SuppliersType;
import com.pltfm.app.vobject.ProductSkuDraft;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.enums.AuditStatus;
import com.pltfm.app.enums.DraftType;
import com.pltfm.app.service.ProductSkuDraftService;
import com.pltfm.app.service.ProductSkuService;
import com.pltfm.app.vobject.ProductDraft;
import com.pltfm.app.vobject.ProductSku;
import com.kmzyc.commons.page.Page;

/**
 * 
 * @author tanyunxing
 *
 */
@Controller("productSkuDraftAction")
@Scope(value = "prototype")
public class ProductSkuDraftAction extends BaseAction {

    @Resource
    private ProductSkuDraftService productSkuDraftService;

    @Resource
    private ProductSkuService productSkuService;

	private Page page = new Page();
	
	private ProductSkuDraft productSkuDraft = new ProductSkuDraft();

	private ProductDraft productForSelectPara;
	
	private Long productId;

	private String productNo;
	
	private Long[] productIdChk;
	
	// SKU商品的销售单价
	private String[] skuPrice;

	//成本价
	private String[] skuCostPrice;

	// SKU商品的市场价
	private String[] skuMarkPrice;

	// 要更新的SKUID
	private Long[] productSkuId;

	// SKU商品的重量
	private String[] skuWeight;

	// PV值
	private String[] skuPvValue;

	// 合作方收益比
	private String[] skuCostIncomeRatio;

	//商品条形码
	private String[] skuSkuBarCode;
	
	private String[] productSkuCode;

	private String reasonText;

	private String auditStatus;
	
	private String type;
	
	private Long skuId;

	private final static String FAIL = "fail";
	
	/**
	 * 更新SKU价格
	 * @return
	 */
	public String updateSkuDraftPrice(){
		
		List<ProductSkuDraft> list = new ArrayList<ProductSkuDraft>();
		try {
			ProductSkuDraft s = null;
			for (int i = 0; i < skuPrice.length; i++) {
				s = new ProductSkuDraft();
                if (StringUtils.isNotBlank(skuPrice[i])) {
					s.setPrice(Double.valueOf(skuPrice[i]));
                }
                if (StringUtils.isNotBlank(skuCostPrice[i])) {
					s.setCostPrice(Double.valueOf(skuCostPrice[i]));
                } else {
					s.setCostPrice(0d);
                }
                if(StringUtils.isNotBlank(skuMarkPrice[i])){
                    s.setMarkPrice(Double.valueOf(skuMarkPrice[i]));
                }
                if(StringUtils.isNotBlank(skuWeight[i])) {
                    s.setUnitWeight(Double.valueOf(skuWeight[i]));
                }
                if(StringUtils.isNotBlank(skuPvValue[i])){
                    s.setPvValue(Double.valueOf(skuPvValue[i]));
                }
                if(StringUtils.isNotBlank(skuSkuBarCode[i])){
                    s.setSkuBarCode(skuSkuBarCode[i]);
                }

//				if(StringUtils.isNotBlank(skuCostIncomeRatio[i])){
//					s.setCostIncomeRatio(Double.valueOf(skuCostIncomeRatio[i]));
//				}
				
				s.setProductSkuId(productSkuId[i]);
				list.add(s);
			}
			productSkuDraftService.updateBatchByPrimaryKey(productId, list);
		} catch (Exception e) {
			logger.error("修改sku价格和重量失败，" + e.getMessage(), e);
			return FAIL;
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 更新SKU重量
	 * @return
	 */
	public String updateSkuDraftWeight(){
//		try {
//			List<ProductSkuDraft> list = new ArrayList<ProductSkuDraft>();
//			ProductSkuDraft s = null;
//			for (int i = 0; i < skuWeight.length; i++) {
//				s = new ProductSkuDraft();
//				if (skuWeight[i].isEmpty()) {
//					s.setUnitWeight(null);
//				} else {
//					s.setUnitWeight(Double.valueOf(skuWeight[i]));
//				}
//
//				s.setProductSkuId(productSkuId[i]);
//				list.add(s);
//			}
//			productSkuDraftService.updateBatchByPrimaryKey(productId,list);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return FAIL;
//		}
		
		return Action.SUCCESS;
	}
	
	/**
	 * 删除SKU
	 * @return
	 */
	public String deleteProductSkuDraft(){
		
		return null;
	}
	
	/**
	 * 获取所有SKU列表
	 * @return
	 */
	public String findAllProductSkuDraft(){
		try {
			productSkuDraft.setUserId(String.valueOf(getLoginUserId()));
			//productSkuDraftService.searchPage(page, productSkuDraft);
			productSkuDraftService.searchPageByUser(page, productSkuDraft);

            getDraftTypeMap();
            setProductStatusMap();
			//super.getRequest().setAttribute("DraftTypeMap", DraftTypeMap.getMap());
			//super.getRequest().setAttribute("productStatusMap", ProductStatusMap.getProductStatusMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}

	/**
	 * 更新正式表中的价格
	 * @return
	 */
	public String updateProductSKUPrice(){
		try {
			List<ProductSkuDraft> list = new ArrayList<ProductSkuDraft>();
			ProductSkuDraft psd = null;
			for (int i = 0; i < productSkuId.length; i++) {
				psd = new ProductSkuDraft();
				psd.setProductId(productId);
				psd.setProductSkuCode(productSkuCode[i]);
				psd.setProductSkuId(productSkuId[i]);
				if(!StringUtils.isEmpty(skuPrice[i])){
					psd.setPrice(Double.valueOf(skuPrice[i]));
				}
				if(!StringUtils.isEmpty(skuCostPrice[i])){
					psd.setCostPrice(Double.valueOf(skuCostPrice[i]));
				}
				if(!StringUtils.isEmpty(skuMarkPrice[i])){
					psd.setMarkPrice(Double.valueOf(skuMarkPrice[i]));
				}
				if(!StringUtils.isEmpty(skuWeight[i])){
					psd.setUnitWeight(Double.valueOf(skuWeight[i]));
				}
				if(!StringUtils.isEmpty(skuPvValue[i])){
					psd.setPvValue(Double.valueOf(skuPvValue[i]));
				}
				if(StringUtils.isNotBlank(skuSkuBarCode[i])){
					psd.setSkuBarCode(skuSkuBarCode[i]);
				}
//				if(!StringUtils.isEmpty(skuCostIncomeRatio[i])){
//					psd.setCostIncomeRatio(Double.valueOf(skuCostIncomeRatio[i]));
//				}
				
				psd.setOpType(DraftType.ALONEPRICE.getStatus());
				psd.setStatus(AuditStatus.UNAUDIT.getStatus());
				
				list.add(psd);
			}
			productSkuDraftService.updateSingleSkuPrice(productId, list);
			type = "priceSuccessFromOfficial";
		} catch (Exception e) {
            logger.error("更新正式表中的价格失败，", e);
			return Action.ERROR;
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 审核价格
	 * @return
	 */
	public String auditProductPriceDraft(){
		try {
			productSkuDraftService.auditProductPrice(productIdChk, auditStatus, reasonText);
		} catch (Exception e) {
			logger.error("审核价格失败，productIdChk={},auditStatus={},reasonText={},错误信息={}",
                    new Object[]{productIdChk, auditStatus, reasonText, e});
			return Action.ERROR;
		}
		
		return Action.SUCCESS;
	}
	
	/**
	 * 检查是否有未填写的价格或者重量
	 * @return
	 */
	public String gotoCheckSkuPriceAndWeight(){
		try {
			List<ProductSkuDraft> list = productSkuDraftService.findValidProductSkus(productId);
			String result = "1";
			for (ProductSkuDraft psd : list) {
				if (psd.getMarkPrice() == null || psd.getPrice() == null ||
                        psd.getUnitWeight() == null || psd.getPvValue() == null) {
					result = "0";
					break;
				}
			}
			
			strWriteJson(result);
		} catch (Exception e) {
			logger.error("检查是否有未填写的价格或者重量失败。", e);
		}
		return null;
	}
	
	public void findSameSkuBarCodeProductSku(){
		ResultMessage rm = new ResultMessage();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			List<ProductSkuDraft> skuDraftList = new ArrayList<ProductSkuDraft>();
			ProductSkuDraft skuDraft = null;
			for(int i=0;i<productSkuId.length;i++){
				skuDraft = new ProductSkuDraft();
				skuDraft.setProductSkuId(productSkuId[i]);
				skuDraft.setSkuBarCode(skuSkuBarCode[i]);
                if (StringUtils.isNotBlank(skuPrice[i])) {
                    skuDraft.setPrice(Double.valueOf(skuPrice[i]));
                }
				if (StringUtils.isNotBlank(skuMarkPrice[i])) {
                    skuDraft.setMarkPrice(Double.valueOf(skuMarkPrice[i]));
                }
				if (StringUtils.isNotBlank(skuCostPrice[i])) {
                    skuDraft.setCostPrice(Double.valueOf(skuCostPrice[i]));
                }
				if (StringUtils.isNotBlank(skuPvValue[i])) {
                    skuDraft.setPvValue(Double.valueOf(skuPvValue[i]));
                }
				skuDraftList.add(skuDraft);
				
			}
			map.put("skuDraftList", skuDraftList);
			List<ProductSkuDraft> skuList = productSkuDraftService.findSameSkuBarCodeProductSku(map);
            StringBuffer errorMessage = new StringBuffer();
			if(CollectionUtils.isNotEmpty(skuList)){
				rm.setIsSuccess(false);
				for(ProductSkuDraft psd : skuList){
					errorMessage.append("条形码【").append(psd.getSkuBarCode())
                            .append("】，已存在编辑列表中标题为【" + psd.getProductTitle())
                            .append("】的商品中，请保证条形码的唯一性！\r\n");
				}
				rm.setMessage(errorMessage.toString());
				writeJson(rm);
				return ;
			}
			
			List<ProductSku> list = productSkuService.findSameSkuBarCodeProductSku(map);
			if(CollectionUtils.isNotEmpty(list)){
				for(ProductSku ps : list){
                    errorMessage.append("条形码【").append(ps.getSkuBarCode())
                            .append("】，已存在发布列表中标题为【").append(ps.getProductTitle())
                            .append("】的商品中，请保证条形码的唯一性！\r\n");
				}
				rm.setIsSuccess(false);
				rm.setMessage(errorMessage.toString());
				writeJson(rm);
				return ;
			}

            if (!checkSkuPv(rm, skuDraftList)) {
                writeJson(rm);
                return;
            }

            rm.setIsSuccess(true);
		} catch (Exception e) {
			logger.error("检查barCode和pv出错。", e);
			rm.setIsSuccess(false);
			rm.setMessage("系统发生错误！");
		}
		writeJson(rm);
		return ;
	}

    /**
     * 检查sku的pv
     *
     * @param rm            返回信息
     * @param skuDraftList  sku草稿列表
     * @return
     */
    private boolean checkSkuPv(ResultMessage rm, List<ProductSkuDraft> skuDraftList) {
        for (ProductSkuDraft draft : skuDraftList) {
            Double pvValue = draft.getPvValue();
            Double price = draft.getPrice();
            //pv为1时，跳过检查
            if (pvValue == 1d) {
                break;
            }

            //pv小于1时且大于销售价
            if (pvValue < 1d || pvValue > price) {
                rm.setIsSuccess(false);
                if (price < 1) {
                    rm.setMessage("销售价小于1时，SKU的PV值需为1!");
                } else {
                    rm.setMessage("SKU的PV值应满足，1<=PV值<=销售单价!");
                }
                return false;
            }
        }
        return true;
    }

    /**
     * 修改正式产品的价格时，检查pv值
     */
    public void checkSkuPv() {
        List<ProductSkuDraft> skuDraftList = new ArrayList<>();
        ProductSkuDraft skuDraft = null;
        for(int i=0;i<productSkuId.length;i++){
            skuDraft = new ProductSkuDraft();
            skuDraft.setProductSkuId(productSkuId[i]);
            if (StringUtils.isNotBlank(skuPrice[i])) {
                skuDraft.setPrice(Double.valueOf(skuPrice[i]));
            }
            if (StringUtils.isNotBlank(skuMarkPrice[i])) {
                skuDraft.setMarkPrice(Double.valueOf(skuMarkPrice[i]));
            }
            if (StringUtils.isNotBlank(skuCostPrice[i])) {
                skuDraft.setCostPrice(Double.valueOf(skuCostPrice[i]));
            }
            if (StringUtils.isNotBlank(skuPvValue[i])) {
                skuDraft.setPvValue(Double.valueOf(skuPvValue[i]));
            }
            skuDraftList.add(skuDraft);
        }

        ResultMessage rm = new ResultMessage();
        if (!checkSkuPv(rm, skuDraftList)) {
            writeJson(rm);
            return;
        }

        rm.setIsSuccess(Boolean.TRUE);
        writeJson(rm);
    }

    public void getDefaultPvValue() {
        HttpServletRequest request = getRequest();
        double price     = Double.parseDouble(request.getParameter("price"));
        double costPrice = Double.parseDouble(request.getParameter("costPrice"));
        strWriteJson(productSkuService.getSkuMaxPvValue(price, costPrice).toString());
    }

	public Long[] getProductSkuId() {
		return productSkuId;
	}
	public void setProductSkuId(Long[] productSkuId) {
		this.productSkuId = productSkuId;
	}
	public String[] getSkuPrice() {
		return skuPrice;
	}
	public void setSkuPrice(String[] skuPrice) {
		this.skuPrice = skuPrice;
	}
	public String[] getSkuMarkPrice() {
		return skuMarkPrice;
	}
	public void setSkuMarkPrice(String[] skuMarkPrice) {
		this.skuMarkPrice = skuMarkPrice;
	}
	public String[] getSkuWeight() {
		return skuWeight;
	}
	public void setSkuWeight(String[] skuWeight) {
		this.skuWeight = skuWeight;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public ProductSkuDraft getProductSkuDraft() {
		return productSkuDraft;
	}
	public void setProductSkuDraft(ProductSkuDraft productSkuDraft) {
		this.productSkuDraft = productSkuDraft;
	}
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String[] getProductSkuCode() {
		return productSkuCode;
	}
	public void setProductSkuCode(String[] productSkuCode) {
		this.productSkuCode = productSkuCode;
	}

	public String getReasonText() {
		return reasonText;
	}

	public void setReasonText(String reasonText) {
		this.reasonText = reasonText;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Long[] getProductIdChk() {
		return productIdChk;
	}

	public void setProductIdChk(Long[] productIdChk) {
		this.productIdChk = productIdChk;
	}

	public ProductDraft getProductForSelectPara() {
		return productForSelectPara;
	}

	public void setProductForSelectPara(ProductDraft productForSelectPara) {
		this.productForSelectPara = productForSelectPara;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getSkuCostPrice() {
		return skuCostPrice;
	}

	public void setSkuCostPrice(String[] skuCostPrice) {
		this.skuCostPrice = skuCostPrice;
	}

	public String[] getSkuPvValue() {
		return skuPvValue;
	}

	public void setSkuPvValue(String[] skuPvValue) {
		this.skuPvValue = skuPvValue;
	}

	public String[] getSkuCostIncomeRatio() {
		return skuCostIncomeRatio;
	}

	public void setSkuCostIncomeRatio(String[] skuCostIncomeRatio) {
		this.skuCostIncomeRatio = skuCostIncomeRatio;
	}

	public String[] getSkuSkuBarCode() {
		return skuSkuBarCode;
	}

	public void setSkuSkuBarCode(String[] skuSkuBarCode) {
		this.skuSkuBarCode = skuSkuBarCode;
	}
}