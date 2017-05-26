package com.pltfm.cms.parse;

import com.pltfm.app.vobject.ViewPromotion;
import com.pltfm.cms.vobject.CmsPage;
import com.pltfm.cms.vobject.CmsWindow;

import java.util.List;
import java.util.Map;

/**
 * 获取数据接口
 *
 * @author river
 */
public interface DataFetcher {
    /**
     * 获取窗口抽奖活动
     */

    public List<Object> getLotteryLuckDraw(CmsWindow window);

    /**
     * 获取cms关联供应商信息
     */
    public List<Object> getCmsShopData(CmsWindow window);


    /**
     * 获取供应商信息
     */

    public List<Object> getShopMain(CmsWindow window);

    /**
     * 获取窗口奖项奖品
     */

    public List<Object> getLotteryPrize(CmsWindow window);

    /**
     * 获取窗口中的产品数据
     */
    public List<Object> getProducts(CmsWindow window);

    /**
     * 获取窗口中的子窗口
     */
    public List<Object> getSubWindow(CmsWindow window);

    /**
     * 获取窗口中的活动
     */
    public List<Object> getActivity(CmsWindow window);

    /**
     * 获取窗口中的类别
     */
    public List<Object> getCategory(CmsWindow window);


    /**
     * 获取页面内的窗口
     */
    public List<Object> getWindow(CmsPage page);

    /**
     * 获取窗口中的品牌
     */
    public List<Object> getBrand(CmsWindow window);

    /**
     * 获取窗口自定义类型
     */

    public List<Object> getWindowDataType(CmsWindow window);

    /**
     * 获取某类目产品排行榜
     */
    public List<Object> getRankingData(CmsWindow window);

    /**
     * 获取指定商品活动的所有商品
     */
    public Map<String, List> getAssignProductData(ViewPromotion promotion);

    /**
     * 获取类目活动的所有商品
     */
    public Map<String, List> getCategoryProductData(ViewPromotion promotion);

    /**
     * 获取品牌活动的所有商品
     */
    public Map<String, List> getBrandProductData(ViewPromotion promotion);

    /**
     * 获取商家活动的所以商品
     */
    public Map<String, List> getShopProductData(ViewPromotion promotion);

    /**
     * 获取某咨询类别查询不同的咨询
     */
    public List<Object> getInformationData(CmsWindow window);
    /*
	 * 店铺类目
	 */

    public List<Object> getShopCategory(CmsWindow window);
}