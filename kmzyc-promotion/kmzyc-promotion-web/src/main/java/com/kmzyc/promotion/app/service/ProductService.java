package com.kmzyc.promotion.app.service;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.bean.ResultMessage;
import com.kmzyc.promotion.app.vobject.Product;
import com.kmzyc.promotion.app.vobject.ProductAttr;
import com.kmzyc.promotion.app.vobject.ProductSku;
import com.kmzyc.promotion.exception.ServiceException;

public interface ProductService {

    /**
     * 分页 查找数据
     * 
     * @param page
     * @param prod
     */

    public void searchPage(Page page, Product product, String type) throws Exception;

    public List<Product> getProducts(Product product);

    public void searchSkuPage(Page page, Product product, String type, Integer loginUserId)
            throws Exception;

    /**
     * 根据编号查找产品
     * 
     * @param id
     * @throws ServiceException
     */
    public Product findProductById(Long id) throws Exception;

    // 符合产品上架的状态（AUDIT("2","已审核,待上架"),DOWN("4","已下架"),SYSDOWN("5","系统下架")）
    public ResultMessage checkUpShelf(Product product, ResultMessage resultMsg);

    public void searchPageByB2B(Page page, Product product) throws ServiceException;

    /**
     * 设置产品属性值
     * 
     * @param productAttrList 产品属性基本信息列表
     * @return
     * @throws ServiceException 异常
     */
    public void setProductAttrValue(List<ProductAttr> productAttrList) throws Exception;

    /**
     * 设置类目属性值
     * 
     * @param productAttrList 产品属性基本信息列表
     * @return
     * @throws ServiceException 异常
     */
    public void setCategoryAttrValue(List<ProductAttr> productAttrList) throws Exception;

    /**
     * 取产品属性列表
     * 
     * @param productId 产品ID
     * @return List<ProductAttr> 产品属性列表
     * @throws ServiceException 异常
     */
    public List<ProductAttr> getProductAttrList(Long productId) throws Exception;

    /**
     * 更新产品类目属性值
     * 
     * @param productId 产品Id
     * @param productAttrList 类目属性基本信息列表
     * @return
     * @throws ServiceException 异常
     */
    public void updateCategoryAttrValue(List<ProductAttr> productAttrList) throws ServiceException;

    /**
     * 产品关联表、明细表中不包含sku返回true
     * 
     * @param skuList
     * @return
     * @throws Exception
     */
    public ResultMessage checkProductRelationForDelProduct(List<ProductSku> skuList,
            ResultMessage rmsg) throws Exception;

    /**
     * 优惠券产品表COUPON_PRODUCT表中不包含sku返回true
     * 
     * @param skuList
     * @return
     * @throws Exception
     */
    public ResultMessage checkCouponForDelProduct(List<ProductSku> skuList, ResultMessage rmsg)
            throws Exception;

    /**
     * 取产品SKU属性列表
     * 
     * @param productId 产品ID
     * @return List<CategoryAttr> 类目属性列表
     * @throws ServiceException 异常
     */
    public List<ProductSku> getProductSkuAttrList(Long productId) throws Exception;

    public boolean submitTheAudit(Long productId) throws Exception;

    public void upShelfByOneProduct(Product product) throws Exception;

    public ResultMessage checkProductName(String productName) throws Exception;

    public Map<Long, ProductAttr> checkOperationAttr(List<Long> productIdList) throws Exception;

    public void ExecuteUpdateCachePrice(List<Long> skuIdList) throws Exception;

    public Product queryProductByProductNo(String productNo) throws SQLException;

    public String previewProductInfoPage(String productId) throws Exception;

    /**
     * 分页 查找数据,并根据当前操作用户渠道进行过滤
     * 
     * @param page
     * @param prod
     */
    public void searchPageByUserId(Page page, Product product, String type, Integer loginUserId)
            throws Exception;

    public void searchPageByUserIdForPrice(Page page, Product product, String type,
            Integer loginUserId) throws Exception;

    public void searchCertificateProductPageByUserId(Page page, Product product, String type,
            Integer loginUserId) throws Exception;

    public List<Integer> getProductIdByBrandId(Long brandId) throws Exception;

    public List<Map> queryForList(Collection<?> skuIds) throws Exception;
}
