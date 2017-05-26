package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.kmzyc.product.remote.service.ProductRelationRemoteService;
import com.kmzyc.product.remote.service.ProductSkuRemoteService;
import com.kmzyc.promotion.app.vobject.ProductSkuPriceCache;
import com.kmzyc.promotion.remote.service.PromotionRemoteService;
import com.pltfm.app.dao.ViewProductInfoDAO;
import com.pltfm.app.service.ViewProductInfoService;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.CategoryAttr;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ViewProductInfo;
import com.pltfm.app.vobject.ViewProductInfoExample;
import com.pltfm.app.vobject.ViewProductSku;
import com.pltfm.app.vobject.ViewPromotion;
import com.pltfm.app.vobject.ViewSkuAttr;
import com.pltfm.cms.dao.CmsWindowDataDAO;

@Component(value = "viewProductInfoService")
public class ViewProductInfoServiceImpl implements ViewProductInfoService {
    /**
     * 产品视图DOA接口
     */
    @Resource(name = "viewProductInfoDAO")
    private ViewProductInfoDAO viewProductInfoDAO;
    @Resource
    private ProductSkuRemoteService productSkuService;

    @Resource
    private ProductRelationRemoteService productRelationService;

    @Resource
    private PromotionRemoteService promotionRemoteService;

    /**
     * 窗口数据业务逻辑层接口
     */
    @Resource(name = "cmsWindowDataDAO")
    private CmsWindowDataDAO cmsWindowDataDAO;

    public CmsWindowDataDAO getCmsWindowDataDAO() {
        return cmsWindowDataDAO;
    }

    public void setCmsWindowDataDAO(CmsWindowDataDAO cmsWindowDataDAO) {
        this.cmsWindowDataDAO = cmsWindowDataDAO;
    }

    /**
     * 分页查询产品
     *
     * @param viewProductInfo 产品实体
     * @param page            分页实体
     * @throws Exception 异常
     * @return 返回值
     */
    @Override
    public Page queryForPage(ViewProductInfo viewProductInfo, Page page)
            throws Exception {
        if (page == null) {
            page = new Page();
        }
        if (viewProductInfo == null) {
            viewProductInfo = new ViewProductInfo();
        }
        // 根据条件获取窗口数据信息总条数
        int totalNum = viewProductInfoDAO.countByViewPromotion(viewProductInfo);
        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            viewProductInfo.setStartIndex(page.getStartIndex());
            viewProductInfo.setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(viewProductInfoDAO.queryForPage(viewProductInfo));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }

    /**
     * 根据产品主键集合查询产品信息
     *
     * @param dataIds 产品主键集合
     * @throws Exception sql异常
     * @return 返回值
     */
    @Override
    public List selectByDataIds(List dataIds, List dataSkuIds, List productsSkuIdsSort)
            throws Exception {
        ViewProductInfo viewProductInfo = new ViewProductInfo();
        viewProductInfo.setProductIds(dataIds);

        if (ListUtils.isNotEmpty(dataSkuIds)) {
            viewProductInfo.setProductSkuIds(dataSkuIds);
            viewProductInfo.setProductSkuIdSort(productsSkuIdsSort);
        }
        return viewProductInfoDAO.selProByIdsOrSkuId(viewProductInfo);
    }

    /**
     * 根据产品主键集合查询产品信息
     *
     * @param dataIds 产品主键集合
     * @throws Exception sql异常
     * @return 返回值
     */
    @Override
    public List selectByDataIdsAndSiteId(List dataIds/*,int siteId*/)
            throws Exception {
        ViewProductInfoExample example = new ViewProductInfoExample();
        example.createCriteria().andProductIdIn(dataIds);
        return viewProductInfoDAO.selectByExample(example);
    }


    /**
     * 根据产品主键查询单条产品
     *
     * @param productId 产品主键
     * @throws Exception sql异常
     * @return 返回值
     */
    @Override
    public ViewProductInfo selectByPrimaryKey(Integer productId)
            throws Exception {
        return viewProductInfoDAO.selectByPrimaryKey(productId);
    }

    /**
     * 套餐根据产品主键查询单条产品
     *
     * @throws Exception sql异常
     * @return 返回值
     */
    @Override
    public ViewProductInfo selectSkuId(ViewProductInfo viewProductInfo)
            throws Exception {
        return viewProductInfoDAO.selectSkuId(viewProductInfo);
    }


    /**
     * 根据产品主键或SkuId查询单条产品
     *
     * @param viewProductInfo 产品主键
     * @throws Exception sql异常
     * @return 返回值
     */
    @Override
    public ViewProductInfo selectByIdOrSkuId(ViewProductInfo viewProductInfo)
            throws Exception {
        if (ListUtils.isNotEmpty(viewProductInfoDAO.selectByIdOrSkuId(viewProductInfo))) {
            return (ViewProductInfo) viewProductInfoDAO.selectByIdOrSkuId(viewProductInfo).get(0);
        }
        return null;
    }

    /**
     * 根据产品信息条件查询产品信息
     *
     * @param example 产品信息条件
     * @throws Exception sql异常
     * @return 返回值
     */
    @Override
    public List selectByExample(ViewProductInfoExample example)
            throws Exception {
        return viewProductInfoDAO.selectByExample(example);
    }

    /**
     * 通过查询实体查询产品信息
     *
     * @param example 查询实体
     * @return 产品信息
     * @throws Exception 异常信息
     */
    @Override
    public List queryViewProductByExample(ViewProductInfo example) throws Exception {
        return viewProductInfoDAO.queryViewProductByExample(example);
    }

    @Override
    public List<ViewProductInfo> queryViewpProductDraftInfo(ViewProductInfo example)
            throws Exception {
        return viewProductInfoDAO.queryViewpProductDraftInfo(example);
    }

    /**
     * 产品详细页面预览
     *
     * @param example 查询实体
     * @return 产品信息
     * @throws Exception 异常信息
     */
    @Override
    public List preViewProductByExample(ViewProductInfoExample example) throws Exception {
        return viewProductInfoDAO.preViewProductByExample(example);
    }

    /**
     * 通过产品主键查询产品sku信息
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<CategoryAttr> queryProductSkuInfo(Integer productId) throws Exception {
        //	ProductSkuRemoteService par = (ProductSkuRemoteService) RemoteTool.getRemote("02","productSkuService");
        return productSkuService.findSkuAttrByProductId(Long.valueOf(productId));
    }

    /**
     * select PRODUCT_SKU_ID, PRODUCT_ID, PRICE, PRODUCT_SKU_CODE,STATUS,UNIT_WEIGHT,MARK_PRICE,COST_PRICE,PV_VALUE,COST_INCOME_RATIO,SKU_INTRODUCE,SKU_INTRODUCELAZY
     *
     * from PRODUCT_SKU
     *
     * select IMAGE_ID, PRODUCT_NO, SKU_ID, IS_DEFAULT, IMG_PATH, IMG_PATH1, IMG_PATH2, IMG_PATH3,
     * IMG_PATH4, SPC1, SPC2, PRODUCT_ID, IMG_PATH5, IMG_PATH6, IMG_PATH7, SORTNO, IMG_PATH8,
     * IMG_PATH9, IMG_PATH10 from PRODUCT_IMAGE 通过产品主键产品每组sku的图片信息
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<ProductSku> queryProductSkuImage(Integer productId) throws Exception {
        //	ProductSkuRemoteService par = (ProductSkuRemoteService) RemoteTool.getRemote("02","productSkuService");
        return productSkuService.findImagesByProductId( Long.valueOf(productId));
    }


    /**
     * 通过产品草稿主键产品每组sku的图片信息
     */
    @Override
    public List<ProductSku> queryProductDraftSkuImage(Integer productId)
            throws Exception {
        //	ProductSkuRemoteService par = (ProductSkuRemoteService) RemoteTool.getRemote("02","productSkuService");
        return productSkuService.findImagesDraftByProductId( Long.valueOf(productId));
    }

    /**
     * 通过产品草稿主键查询产品sku信息
     */
    @Override
    public List<CategoryAttr> queryProductDraftSkuInfo(Integer productId)
            throws Exception {
        //	ProductSkuRemoteService par = (ProductSkuRemoteService) RemoteTool.getRemote("02","productSkuService");
        return productSkuService.findSkuAttrDraftByProductId(Long.valueOf(productId));
    }


    /**
     * 通过产品sku主键查询sku信息
     *
     * select PRODUCT_ID, PRODUCT_SKU_ID, PRODUCT_SKU_CODE, CATEGORY_ATTR_ID, CATEGORY_ATTR_NAME,
     * CATEGORY_ATTR_VALUE_ID, CATEGORY_ATTR_VALUE, STATUS,WAREHOUSE_ID,WAREHOUSE_NAME,SKU_INTRODUCE
     * from VIEW_SKU_ATTR
     */
    @Override
    public List<ViewSkuAttr> findBySkuId(Long productSkuId) throws Exception {
        //	ProductSkuRemoteService par = (ProductSkuRemoteService) RemoteTool.getRemote("02","productSkuService");
        return productSkuService.findBySkuId(productSkuId);
    }

    /**
     * 通过产品草稿sku主键查询sku信息
     */
    @Override
    public List<ViewSkuAttr> findBySkuDraftId(Long productSkuId) throws Exception {
        //ProductSkuRemoteService par = (ProductSkuRemoteService) RemoteTool.getRemote("02","productSkuService");
        return productSkuService.findDraftBySkuId(productSkuId);
    }

    /**
     * 跟据产品skuId查询产品优惠套餐信息
     */
    @Override
    public List queryProductPreferInfo(Long skuId) throws Exception {
        //ProductRelationRemoteService relationService = (ProductRelationRemoteService) RemoteTool.getRemote("02","productRelationService");
        return productRelationService.queryProductAndDetailPackageList(skuId);
    }

    /**
     * 根据产品skuId查询产品人气组合信息
     */
    @Override
    public List queryProductPackage(Long skuId) throws Exception {
        //	ProductRelationRemoteService relationService = (ProductRelationRemoteService) RemoteTool.getRemote("02","productRelationService");
        return productRelationService.queryProductAndDetailRecommendList(skuId);
    }

    @Override
    public List selProByPk(ViewProductInfo viewProductInfo) throws Exception {
        // TODO Auto-generated method stub
        return this.viewProductInfoDAO.selProByPk(viewProductInfo);
    }

    @Override
    public List selProByViewProductInfo(ViewProductInfo viewProductInfo, ViewPromotion promotion)
            throws Exception {//促销类别ID {8:'打折',6:'满额减',10:'特价',4:'满额送券'}
        // TODO Auto-generated method stub
        List<ProductSkuPriceCache> list = new ArrayList<ProductSkuPriceCache>();
        List dataList = null;
        //判断活动是否为指定商品，2为指定商品活动
        if (promotion.getProductFilterType() == 2) {
            dataList = this.viewProductInfoDAO.getCategoryProducts(viewProductInfo);
        } else {
            dataList = this.viewProductInfoDAO.selProByViewProductInfo(viewProductInfo);
        }
        //判断该活动的活动规则是否为打折或者特价，不是打折或者特价的商品不用调接口
        if (promotion.getPromotionTypeId() == 4 || promotion.getPromotionTypeId() == 6) {
            for (int i = 0; i < dataList.size(); i++) {
                ViewProductSku product = (ViewProductSku) dataList.get(i);
                product.setPromotionPrice(product.getPrice());
            }
        } else {
            //组装ProductSkuPriceCache对象，查询活动价
            for (int i = 0; i < dataList.size(); i++) {
                ViewProductSku product = (ViewProductSku) dataList.get(i);
                ProductSkuPriceCache productSkuPriceCache = new ProductSkuPriceCache();
                productSkuPriceCache.setProductSkuId(product.getProductSkuId().longValue());
                list.add(productSkuPriceCache);
            }
            Date startTime = promotion.getStartTime();
            int sec = startTime.getSeconds() + 1;
            startTime.setSeconds(sec);
            //	PromotionRemoteService promotionRemoteService = (PromotionRemoteService) RemoteTool.getRemote(Constants.PROMOTION_SYSTEM_CODE,"promotionRemoteService");
            list = promotionRemoteService.getProductSkuPriceBatch(list, startTime);
            //判断是否有活动价，没有，则将销售价设为活动价
            if (list == null) {
                for (int i = 0; i < dataList.size(); i++) {
                    ViewProductSku productInfo = (ViewProductSku) dataList.get(i);
                    productInfo.setPromotionPrice(productInfo.getPrice());
                }
            } else//存在活动价
            {
                for (int i = 0; i < dataList.size(); i++) {
                    ViewProductSku product = (ViewProductSku) dataList.get(i);
                    if (list.get(i).getPromotionPrice() == null)//在价格List中若存在活动价为0，则用销售价代替活动价0
                    {
                        product.setPromotionPrice(product.getPrice());
                    } else {
                        product.setPromotionPrice(list.get(i).getPromotionPrice());
                    }
                    if (list.get(i).getProductSkuId() != null)//判断是否返回sku，若返回，则代替本身的SKU，反之，用原来的SKU
                    {
                        product.setProductSkuId(list.get(i).getProductSkuId().intValue());
                    }
                }
            }
        }
        return dataList;
    }

    /**
     * 根据产品信息条件查询产品信息
     *
     * @param shopCode   供应商ID
     * @param startIndex 开始索引值
     * @param endIndex   结束索引值
     * @throws Exception sql异常
     * @return 产品集合
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ViewProductInfo> selectByShopCode(String shopCode/*,String channel*/,
                                                  Integer startIndex, Integer endIndex) throws Exception {
        ViewProductInfo viewProductInfo = new ViewProductInfo();
        viewProductInfo.setShopCode(shopCode);
        viewProductInfo.setUpTime(new Date());
//    		viewProductInfo.setChannel(channel) ;
        // 设置查询开始结束索引
        viewProductInfo.setStartIndex(startIndex);
        viewProductInfo.setEndIndex(endIndex);
        List<ViewProductInfo> list = viewProductInfoDAO.queryForPage(viewProductInfo);
        return cmsWindowDataDAO.updateMoney(list);
    }

    /**
     * 查询新产品排行
     *
     * @throws Exception sql异常
     * @return 产品集合
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ViewProductInfo> pageRanking() throws Exception {
        List<ViewProductInfo> list = viewProductInfoDAO.pageRanking();
        return cmsWindowDataDAO.updateMoney(list);
    }


    @Override
    public List getCategory(ViewProductInfo viewProductInfo) throws Exception {
        // TODO Auto-generated method stub
        return this.viewProductInfoDAO.getCategory(viewProductInfo);
    }

    public ViewProductInfoDAO getViewProductInfoDAO() {
        return viewProductInfoDAO;
    }

    public void setViewProductInfoDAO(ViewProductInfoDAO viewProductInfoDAO) {
        this.viewProductInfoDAO = viewProductInfoDAO;
    }

    /**
     * 分页查询产品
     *
     * @param viewProductInfo 产品实体
     * @param page            分页实体
     * @throws Exception 异常
     * @return 返回值
     */
    @Override
    public Page queryForPagePro(ViewProductInfo viewProductInfo, Page page)
            throws Exception {
        if (page == null) {
            page = new Page();
        }
        if (viewProductInfo == null) {
            viewProductInfo = new ViewProductInfo();
        }
        // 根据条件获取窗口数据信息总条数
        int totalNum = viewProductInfoDAO.countByViewPromotionPro(viewProductInfo);
        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            //viewProductInfo.setStartIndex(page.getStartIndex());
            //viewProductInfo.setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(viewProductInfoDAO.queryForPagePro(viewProductInfo, page));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }

    /**
     * 分页查询产品
     *
     * @param viewProductInfo 产品实体
     * @param page            分页实体
     * @throws Exception 异常
     * @return 返回值
     */
    @Override
    public Page queryForPage1(ViewProductInfo viewProductInfo, Page page)
            throws Exception {
        if (page == null) {
            page = new Page();
        }
        if (null != viewProductInfo.getProductNameOrCode()) {
            if (viewProductInfo.getProductNameOrCode().length() == 0) {
                viewProductInfo.setProductNameOrCode(null);
            }
        }
        if (null != viewProductInfo.getShopCategoryId()) {
            if (viewProductInfo.getShopCategoryId().longValue() != 0) {
                int totalNum = viewProductInfoDAO.countByViewPromotion1(viewProductInfo);
                if (totalNum != 0) {
                    page.setRecordCount(totalNum);
                    // 设置查询开始结束索引
                    viewProductInfo.setStartIndex(page.getStartIndex());
                    viewProductInfo.setEndIndex(page.getStartIndex() + page.getPageSize());
                    page.setDataList(viewProductInfoDAO.queryForPage1(viewProductInfo));
                } else {
                    page.setRecordCount(0);
                    page.setDataList(null);
                }
            }
        } else {
            // 根据条件获取窗口数据信息总条数
            int totalNum = viewProductInfoDAO.countByViewPromotionTwo(viewProductInfo);
            if (totalNum != 0) {
                page.setRecordCount(totalNum);
                // 设置查询开始结束索引
                viewProductInfo.setStartIndex(page.getStartIndex());
                viewProductInfo.setEndIndex(page.getStartIndex() + page.getPageSize());
                page.setDataList(viewProductInfoDAO.queryForPageTwo(viewProductInfo));
            } else {
                page.setRecordCount(0);
                page.setDataList(null);
            }
        }
        return page;
    }

    /**
     * 查询活动商品
     */
    @Override
    public List queryByViewPromotionId(List<Integer> promotionIds) throws ServiceException {
        List promotionProductList;
        try {
            promotionProductList = viewProductInfoDAO.queryByViewPromotionId(promotionIds);
        } catch (SQLException e) {
            throw new ServiceException(e.getErrorCode(), e.getMessage(), e);
        }
        return promotionProductList;
    }


}
