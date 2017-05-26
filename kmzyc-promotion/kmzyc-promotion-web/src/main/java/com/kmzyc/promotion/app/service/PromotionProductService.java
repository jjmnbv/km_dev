package com.kmzyc.promotion.app.service;

import java.sql.SQLException;
import java.util.List;

import com.km.framework.page.Pagination;
import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.bean.ResultMessage;
import com.kmzyc.promotion.app.vobject.ProductAndSku;
import com.kmzyc.promotion.app.vobject.ProductSku;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.promotion.app.vobject.PromotionProduct;

public interface PromotionProductService {
    /**
     * 查询查询活动产品列表
     * 
     * @param page 分页对象
     * @param promotion 活动基本信息
     * @return Page
     * @throws Exception
     */
    public Page queryPromotionProductList(Page page, PromotionProduct promotionProduct)
            throws Exception;

    /**
     * 增加活动产品
     * 
     * @param promotionProduct 活动产品对象
     * @return
     * @throws Exception
     */
    public void addPromotionProduct(PromotionProduct promotionProduct, Double price,
            PromotionInfo promotion) throws Exception;

    /**
     * 删除活动产品
     * 
     * @param promotionProduct 活动产品对象
     * @return
     * @throws Exception
     */
    public void deletePromotionProduct(PromotionProduct promotionProduct) throws Exception;

    /**
     * 根据活动ID删除活动产品
     * 
     * @param promotionId 活动产品对象
     * @return
     * @throws Exception
     */
    public void deletePromotionProduct(Long promotionId) throws SQLException;

    public void updatePromotionProductPrice(PromotionProduct promotionProduct);

    public ResultMessage updatePromotionProductForXianGou(PromotionProduct promotionProduct);

    public Pagination queryProductListByPromotionInfo(Pagination page, PromotionInfo info)
            throws Exception;

    // 分页查询出productAndSku
    Pagination queryProductAndSkuListByPromotionInfo(Pagination page, PromotionInfo info)
            throws Exception;

    public List<ProductSku> queryProductsByPromotionInfo(PromotionInfo promotionInfo)
            throws SQLException;

    public List<ProductAndSku> queryProductByPromotionInfo(Pagination page, PromotionInfo info);

    /**
     * add by songmiao 2015-8-5 已审核上线活动修改参加活动商品
     * 
     * @throws SQLException
     */
    public int updataPromotionProductStatus(Long promotionProductId) throws SQLException;

    public void deletePromotionExceptionProduct(Long promotionId) throws SQLException;

    public void deletePromotionProductAll(Long pId) throws SQLException;

    public PromotionProduct queryPromotionProductById(Long promotionProductId) throws SQLException;
}
