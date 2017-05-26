package com.pltfm.cms.parse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.collections.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pltfm.app.vobject.ViewPromotion;
import com.pltfm.cms.service.CmsWindowDataService;
import com.pltfm.cms.vobject.CmsPage;
import com.pltfm.cms.vobject.CmsWindow;

/**
 * 模板数据获取数据操作类
 *
 * @author river
 */

@Component(value = "dataHandler")
public class DataHandler {
	private static Logger logger = LoggerFactory.getLogger(DataHandler.class);

    @Resource(name = "dataFetcher")
    private DataFetcher dataFetcher;

    @Resource(name = "cmsWindowDataService")
    private CmsWindowDataService cmsWindowDataService;

    public DataFetcher getDataFetcher() {
        return dataFetcher;
    }

    public void setDataFetcher(DataFetcher dataFetcher) {
        this.dataFetcher = dataFetcher;
    }

    @SuppressWarnings("unchecked")
    public List<Object> getWindows(CmsPage page) {
        List<Object> result = dataFetcher.getWindow(page);
        return result == null ? ListUtils.EMPTY_LIST : result;
    }

    /**
     * 获取页面数据信息
     */
    public Map<String, Object> getWindowData(CmsWindow window) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            List list = cmsWindowDataService.queryWindowDataType(window);
            if (null != list && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {

                    Integer dataType = (Integer) list.get(i);
                    //	Integer dataType=windowData.getDataType();
                    switch (dataType) {
                        case 0:

                            getProducts(window, result);
                            break;
                        case 4:
                            getSubWindow(window, result);
                            break;
                        case 1:
                            getActivity(window, result);
                            break;
                        case 2:
                            getCategory(window, result);
                            break;
                        case 3:
                            getBrand(window, result);
                            break;
                        case 6:
                            getWindowDataType(window, result);
                            break;
                        case 7:
                            getRankingData(window, result);
                            break;
                        case 8:
                            getInformationData(window, result);
                            break;
                        case 9:
                            getLotteryLuckDrawData(window, result);
                            break;
                        case 10:
                            getLotteryPrizeData(window, result);
                            break;
                        case 11://产品供应商信息
                            getShopMain(window, result);
                            break;
                        case 12://cms关联供应商表
                            getCmsShopData(window, result);
                            break;
                        case 13://供应商店铺类目
                            getShopCategory(window, result);
                            break;
                    }

                }
            }
            if (window.getShopType() != null) { //发布店铺时店铺模板类型是否为空
                result.put("windShopTyp", window.getShopType());
            }
            result.put("windowId", window.getWindowId());
            if (null != window.getSiteId()) {
                String cmsPath = PathConstants.cmsPath_B2B(window.getSiteId());

                result.put("cms_Path", cmsPath);
            }

        } catch (Exception e) {
        	logger.error("DataHandler.getWindowData异常：" + e.getMessage(), e);
        }
        //	getActivity(window, result);

        //	getProducts(window, result);
        //	getSubWindow(window, result);
        //	getBrand(window, result);
        //	getWindowDataType(window, result);
        //	getRankingData(window, result);
        return result;
    }


    /**
     * 详细页面发布窗口绑定窗口以及系统绑定参数
     *
     * @param window 窗口对象
     * @param obj    系统参数对象
     */
    public Map<String, Object> getSonwindowData(CmsWindow window, Object obj) {
        Map<String, Object> result = new HashMap<String, Object>();
        getSubWindow(window, result);
        result.put(DataType.product.name(), obj);
        return result;
    }

    /**
     * 供应商页面发布窗口绑定窗口以及系统绑定参数
     *
     * @param window 窗口对象
     * @param obj    系统参数对象
     */
    public Map<String, Object> getSupplierWindowData(CmsWindow window, Map<String, Object> supplierMap) {
        Map<String, Object> result = new HashMap<String, Object>();
        getSubWindow(window, result);
        for (Entry<String, Object> supplier : supplierMap.entrySet()) {
            result.put(supplier.getKey(), supplier.getValue());
                /*
				 if(DataType.suppliersCertificate.name().equals(supplier.getKey())){
					 result.put(DataType.suppliersCertificate.name(), supplier.getValue());
				}else if(DataType.basicInfo.name().equals(supplier.getKey())){
					result.put(DataType.basicInfo.name(), supplier.getValue());
				}else if(DataType.product.name().equals(supplier.getKey())){
					result.put(DataType.product.name(), supplier.getValue());
				}else if(DataType.shopMain.name().equals(supplier.getKey())){
					result.put(DataType.shopMain.name(), supplier.getValue());
				}else if(DataType.supplyType.name().equals(supplier.getKey())){
					result.put(DataType.supplyType.name(), supplier.getValue());	
				}*/
        }
        return result;

    }

    /**
     * 套餐页面发布窗口绑定窗口以及系统绑定参数
     *
     * @param window 窗口对象
     * @param obj    系统参数对象
     */
    public Map<String, Object> getproductRelationDetailWindowData(CmsWindow window, Map<String, Object> productRelationDetailMap) {
        Map<String, Object> result = new HashMap<String, Object>();
        getSubWindow(window, result);

        for (Entry<String, Object> productRelationDetail : productRelationDetailMap.entrySet()) {
            if (DataType.productRelation.name().equals(productRelationDetail.getKey())) {
                result.put(DataType.productRelation.name(), productRelationDetail.getValue());
            }

            if (DataType.productRelationDetail.name().equals(productRelationDetail.getKey())) {
                result.put(DataType.productRelationDetail.name(), productRelationDetail.getValue());
            }

        }
        return result;

    }

    public void getActivity(CmsWindow window, Map<String, Object> data) {
        List<Object> result = dataFetcher.getActivity(window);
        result = null == result ? ListUtils.EMPTY_LIST : result;
        data.put(DataType.activity.name(), result);
    }


    public void getRankingData(CmsWindow window, Map<String, Object> data) {
        List<Object> result = dataFetcher.getRankingData(window);
        result = null == result ? ListUtils.EMPTY_LIST : result;
        data.put(DataType.productRank.name(), result);
    }

    public void getInformationData(CmsWindow window, Map<String, Object> data) {
        List<Object> result = dataFetcher.getInformationData(window);
        result = null == result ? ListUtils.EMPTY_LIST : result;
        data.put(DataType.information.name(), result);
    }

    public void getCategory(CmsWindow window, Map<String, Object> data) {
        List<Object> result = dataFetcher.getCategory(window);
        result = null == result ? ListUtils.EMPTY_LIST : result;
        data.put(DataType.category.name(), result);
    }


    public void getProducts(CmsWindow window, Map<String, Object> data) {
        List<Object> result = dataFetcher.getProducts(window);
        result = null == result ? ListUtils.EMPTY_LIST : result;
        data.put(DataType.product.name(), result);
    }


    public void getSubWindow(CmsWindow window, Map<String, Object> data) {
        List<Object> result = dataFetcher.getSubWindow(window);
        result = null == result ? ListUtils.EMPTY_LIST : result;
        data.put(DataType.window.name(), result);
    }

    @SuppressWarnings("unchecked")
    public void getBrand(CmsWindow window, Map<String, Object> data) {
        List<Object> result = dataFetcher.getBrand(window);
        result = null == result ? ListUtils.EMPTY_LIST : result;
        data.put(DataType.brand.name(), result);
    }

    public void getWindowDataType(CmsWindow window, Map<String, Object> data) {
        List<Object> result = dataFetcher.getWindowDataType(window);
        result = null == result ? ListUtils.EMPTY_LIST : result;
        data.put(DataType.windowTitle.name(), result);
    }

    public void getLotteryLuckDrawData(CmsWindow window, Map<String, Object> data) {
        List<Object> result = dataFetcher.getLotteryLuckDraw(window);
        result = null == result ? ListUtils.EMPTY_LIST : result;
        data.put(DataType.lotteryLuckDraw.name(), result);
    }

    public void getLotteryPrizeData(CmsWindow window, Map<String, Object> data) {
        List<Object> result = dataFetcher.getLotteryPrize(window);
        result = null == result ? ListUtils.EMPTY_LIST : result;
        data.put(DataType.lotteryAwards.name(), result);
    }

    //产品供应商信息
    public void getShopMain(CmsWindow window, Map<String, Object> data) {
        List<Object> result = dataFetcher.getShopMain(window);
        result = null == result ? ListUtils.EMPTY_LIST : result;
        data.put(DataType.shopMain.name(), result);
    }

    //cma关联供应商信息
    public void getCmsShopData(CmsWindow window, Map<String, Object> data) {
        List<Object> result = dataFetcher.getCmsShopData(window);
        result = null == result ? ListUtils.EMPTY_LIST : result;
        data.put(DataType.cmsShopData.name(), result);
    }

    //cma关联供应商信息
    public void getShopCategory(CmsWindow window, Map<String, Object> data) {
        List<Object> result = dataFetcher.getShopCategory(window);
        result = null == result ? ListUtils.EMPTY_LIST : result;
        data.put(DataType.shopCategory.name(), result);
    }


    //活动类型判断
    public Map<String, List> getActivityData(ViewPromotion promotion) {
        Map<String, List> data = null;
        if (promotion != null && promotion.getProductFilterType() != null) {
            switch (promotion.getProductFilterType()) {
                case 2:
                    data = dataFetcher.getAssignProductData(promotion);
                    break;
                case 3:
                    data = dataFetcher.getCategoryProductData(promotion);
                    break;
                case 4:
                    data = dataFetcher.getBrandProductData(promotion);
                    break;
                case 5:
                    data = dataFetcher.getShopProductData(promotion);
                    break;
            }
        }
        return data;
    }


    public CmsWindowDataService getCmsWindowDataService() {
        return cmsWindowDataService;
    }

    public void setCmsWindowDataService(CmsWindowDataService cmsWindowDataService) {
        this.cmsWindowDataService = cmsWindowDataService;
    }


}