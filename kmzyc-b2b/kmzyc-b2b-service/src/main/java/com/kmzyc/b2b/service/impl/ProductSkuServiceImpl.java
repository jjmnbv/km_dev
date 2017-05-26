package com.kmzyc.b2b.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.dao.ProductImageDao;
import com.kmzyc.b2b.dao.ProductSkuDao;
import com.kmzyc.b2b.dao.ProductmainDao;
import com.kmzyc.b2b.model.ProductImage;
import com.kmzyc.b2b.model.ProductSku;
import com.kmzyc.b2b.model.Productmain;
import com.kmzyc.b2b.service.ProductSkuService;
import com.kmzyc.b2b.shopcart.vo.CartProduct;
import com.kmzyc.b2b.vo.ShopCategorys;
import com.kmzyc.framework.exception.ServiceException;
import com.pltfm.app.vobject.CategoryAttrValue;
import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ViewSkuAttr;

@SuppressWarnings("unchecked")
@Service
public class ProductSkuServiceImpl implements ProductSkuService {

    // private static Logger logger = Logger.getLogger(ProductSkuServiceImpl.class);
    private static Logger logger = LoggerFactory.getLogger(ProductSkuServiceImpl.class);

    @Resource(name = "productSkuDaoImpl")
    private ProductSkuDao productSkuDao;

    @Resource(name = "productImageDaoImpl")
    private ProductImageDao productImageDao;

    @Resource(name = "productmainDaoImpl")
    private ProductmainDao productmainDao;

    /**
     * 根据skuId查询ProductSku
     * 
     * @param skuId
     * @return
     * @throws ServiceException
     */
    @Override
    public ProductSku findProductSkuById(Long skuId) throws ServiceException {
        try {

            return (ProductSku) productSkuDao.findById("ProductSku.findById", skuId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 根据skuCode查询categotyId
     * 
     * @param productSkuCode
     * @return
     * @throws ServiceException
     */
    @Override
    public Long findCategotyIdBySkuCode(String productSkuCode) throws ServiceException {
        try {

            return (Long) productSkuDao.findById("ProductSku.findCategotyIdBySkuCode",
                    productSkuCode);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductImage> findAllBySkuId(Long productSkuId) throws Exception {

        ProductImage productImCondition = new ProductImage();
        List<ProductImage> list = new ArrayList<>();
        try {
            productImCondition.setSkuId(productSkuId);
            list = productImageDao.findAllImageBySku(productImCondition);
            return list;
        } catch (Exception e) {
            logger.error("查询商品组图出错", e);
        }
        return list;
    }

    /**
     * 通过产品主键查询产品个性信息
     * 
     * @param productId 产品主键
     * @return 属性值集合信息
     * @throws Exception 异常
     */
    @Override
    public List<ViewSkuAttr> findAttrAndValueByProductId(Long productId) throws Exception {
        List<ViewSkuAttr> viewAttrList = new ArrayList<>();

        try {
            List<ProductAttr> attrList = queryProductAttrByProductId(productId);
            for (ProductAttr t : attrList) {
                ViewSkuAttr viewSkuAttr = new ViewSkuAttr();
                // 值为空，直接过滤不展示
                if (StringUtils.isBlank(t.getProductAttrValue())) {
                    continue;
                }

                if (t.getProductAttrType() == 1) {// 类目属性
                    if (t.getIsSku() == 1) {
                        continue;
                    }
                    viewSkuAttr.setCategoryAttrName(t.getProductAttrName());
                    if (t.getInputType() == 0) {// 文本输入框
                        viewSkuAttr.setCategoryAttrValue(t.getProductAttrValue());
                    } else if (t.getProductAttrValue() != null) {// 其他空间需要通过id找到value
                        // 执行的SQL为：select * from CATEGORY_ATTR_VALUE t where
                        // t.CATEGORY_ATTR_VALUE_ID in (#productAttrValue#)
                        List<CategoryAttrValue> valueList = findByValueId(t.getProductAttrValue());
                        String value = "";
                        if (valueList != null && valueList.size() > 0) {
                            StringBuilder sb = new StringBuilder();
                            for (CategoryAttrValue v : valueList) {
                                sb.append(v.getCategoryAttrValue()).append(",");
                            }
                            value = sb.substring(0, sb.length() - 1);
                        }
                        viewSkuAttr.setCategoryAttrValue(value);
                    }
                } else if (t.getProductAttrType() == 2) {// 自定义属性
                    viewSkuAttr.setCategoryAttrName(t.getProductAttrName());
                    viewSkuAttr.setCategoryAttrValue(t.getProductAttrValue());
                } else {
                    continue;
                }
                viewAttrList.add(viewSkuAttr);
            }
        } catch (Exception e) {
            logger.error("通过产品主键查询产品个性信息异常" + e.getMessage(), e);
        }
        return viewAttrList;
    }

    /**
     * 通过产品主键查询产品sku属性信息
     * 
     * @param productId 产品主键
     * @return sku属性信息
     * @throws ServiceException 异常信息
     */
    @Override
    public List<ProductAttr> queryProductAttrByProductId(Long productId) throws ServiceException {
        List<ProductAttr> prodAttrL;
        try {

            prodAttrL = productSkuDao.queryProductAttrByProductId(productId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return prodAttrL;
    }

    /**
     * 通过产品sku查询产品属性值信息
     * 
     * @param attrValue sku属性值
     * @return
     * @throws ServiceException 异常信息
     */
    private List<CategoryAttrValue> findByValueId(String attrValue) throws ServiceException {
        List list;
        try {

            list = productSkuDao.findByValueId(attrValue);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return list;
    }

    /**
     * 通过产品ID 查询产品的对应的SKU集合
     */
    @Override
    public List<ProductSku> findSkuListByProductId(Long productId) throws Exception {
        return productSkuDao.findPorductSkuByProductId(productId);
    }

    @Override
    public String findProductWareHouseBySkuId(Long productSkuId) throws ServiceException {
        try {

            return productSkuDao.queryProductWareHouse(productSkuId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 通过产品的SKUCODE查询产品的相关信息 （productmain,sku,product_imge,SKU个性属性）
     */
    @Override
    public List<ProductSku> findProductDetailByCode(String productSkuCode) throws ServiceException {
        try {

            return productSkuDao.queryProductDetailBySku(productSkuCode);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 通过供应商ID 以及查询产品的销量排行。
     */
    @Override
    public List<ProductSku> findProductRankByCondition(Map mapCondition) throws ServiceException {
        try {

            return productSkuDao.queryProductRankByCondition(mapCondition);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 通过供应商ID ，以及条件查询产品的收藏排行
     */
    @Override
    public List<ProductSku> findProductFavRankByCondition(Map mapCondition)
            throws ServiceException {
        try {

            return productSkuDao.findProductFavRankByCondition(mapCondition);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 通过供应商ID ，产品的上架时间来查询产品的排行
     */
    @Override
    public List<ProductSku> findProductUpTimeRankByCondition(Map mapCondition)
            throws ServiceException {
        try {

            return productSkuDao.findProductUpTimeRankByCondition(mapCondition);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 通过供应商ID，查询店铺的类目以及推荐类目
     */
    @Override
    public List<ShopCategorys> findCategorysByShopId(Map condition, String recommed)
            throws ServiceException {
        try {
            return productSkuDao.findCategorysByShopId(condition, recommed);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<String, Object> querySkuInfoForDetailPage(String skuId) {
        try {

            return productSkuDao.querySkuInfoForDetailPage(skuId);
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 查询返佣率
     * 
     * @param ids
     * @return
     * @throws ServiceException
     */
    @Override
    public Map<String, BigDecimal> queryBatchComRatio(List<Long> ids) throws ServiceException {
        try {

            return productSkuDao.queryBatchComRatio(ids);
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    /**
     * 查询活动产品信息
     * 
     * @param ids
     * @return
     * @throws ServiceException
     */
    @Override
    public Map<Long, CartProduct> queryPromotionProducts(List<Long> ids) throws ServiceException {
        try {

            List<CartProduct> products = productSkuDao.queryPromotionProducts(ids);
            Map<Long, CartProduct> pps = null;
            if (null != products && !products.isEmpty()) {
                pps = new HashMap<>();
                for (CartProduct cp : products) {
                    pps.put(cp.getProductSkuId(), cp);
                }
            }
            return pps;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 从cms系统中获取秒杀商品列表 add by songmiao 2015-9-17
     * 
     */
    @Override
    public String getSeckillProducts(HashMap<String, String> skucondition) throws ServiceException {

        try {
            return productSkuDao.getSeckillProducts(skucondition);
        } catch (SQLException e) {
            logger.error("从CMS系统获取秒杀商品列表失败", e);
        }
        return null;
    }

    @Override
    public List<ProductSku> getProductBySkuIds(String skuIds) throws ServiceException {
        try {


            return productSkuDao.getProductBySkuIds(skuIds);
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public Pagination getRecommendProduct(Pagination page, String name) throws ServiceException {
        try {


            return productSkuDao.getRecommendProduct(page, name);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductSku> getWindowDatas(String jboxWindowName) throws ServiceException {

        try {
            return productSkuDao.getWindowDatas(jboxWindowName);
        } catch (SQLException e) {
            logger.error("从CMS系统获取秒杀商品列表失败", e);
        }
        return null;
    }

    /**
     * 根据productRelationType类型及productSkuId组成Map查询套餐或组合
     * 
     * @param typeAndSkuIdMap
     * @param productRelationType
     * @return
     * @throws ServiceException
     */
    @Override
    public List findProductRelation(Map typeAndSkuIdMap, String productRelationType) {
        try {

            return productSkuDao.findProductRelation(typeAndSkuIdMap, productRelationType);
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    @Override
    public Productmain findProductSupplyType(Long productId) throws ServiceException {
        try {

            return productmainDao.findProductSupplyType(productId);
        } catch (SQLException e) {
            logger.error("", e);
            return null;
        }
    }

    /*
     * @Override public String querySkuInfoForFanLi(Long productSkuId) throws ServiceException {
     * Map<String, Object> condition = new HashMap<String, Object>(); condition.put("productSkuId",
     * productSkuId);
     * 
     * try {
     * 
     * // 未限定产品状态和sku有效状态 //ProductSku skuInfo = productSkuDao.querySkuInfoForFanli(condition);
     * 
     * // 为空可以返回一些提示信息给返利网 if (skuInfo == null) { logger.info("查询超级返商品返回数据为null,productSkuId=" +
     * productSkuId); return null; }
     * 
     * String fanliProductStatus = "1"; // 将kangmei的商品状态转换为返利网可识别的商品状态，0表示下架，1表示上架（默认），2表示缺货，3表示预售
     * ,kangmei暂时没有预售概念 // 当商品处于上架状态时需要判断是否是缺货状态 康美中药城产品状态 4为下架,-2 为违规下架 5为系统下架 (0,1,2,6,-1) if
     * ("3".equals(skuInfo.getStatus())) { ProductStock productStock =
     * productStockDao.queryMaxStockRecord(productSkuId); // 如果库存-订购数小于0,则为缺货 if
     * (productStock.getStockQuality() - productStock.getOrderQuality() <= 0) { fanliProductStatus =
     * "2"; } } else { // 其他的状态均为下架状态 fanliProductStatus = "0"; }
     * skuInfo.setStatus(fanliProductStatus); Map<String, BigDecimal> skuIdMap = new HashMap<String,
     * BigDecimal>(); skuIdMap.put(productSkuId.toString(), skuInfo.getPrice()); // 获取促销价格
     * Map<String, BigDecimal> skuIdPricePromotionMap =
     * productPriceService.getPromotionPricetBySkuIds(skuIdMap);
     * 
     * skuInfo.setPromotionPrice(skuIdPricePromotionMap.get(productSkuId.toString()));
     * 
     * String xmlInfo = skuInfoConvertXml(skuInfo);
     * 
     * return xmlInfo; } catch (SQLException e) { logger.error("查询超级返产品信息发生异常,具体异常信息如下=" +
     * e.getMessage()); e.printStackTrace(); } catch (Exception e) {
     * logger.error("查询超级返产品信息发生异常,具体异常信息如下=" + e.getMessage()); e.printStackTrace(); }
     * 
     * return null; }
     */

    /**
     * 将sku信息转化为所需要的xml格式
     * 
     * @param productId
     * @return
     */
    // protected String skuInfoConvertXml(ProductSku skuInfo) {
    // StringBuilder sb = new StringBuilder();
    //
    // String productDetailPostfix = ".html";
    //
    // String defaultSize = "800*800";
    //
    // String productImgServerUrl = ConfigurationUtil.getString("PRODUCT_IMG_PATH");
    // String pcProductDetailPath =
    // ConfigurationUtil.getString("detailPath") + "/" + skuInfo.getProductSkuId()
    // + productDetailPostfix;
    // String wapProductDetailPath =
    // ConfigurationUtil.getString("productPicPath_WAP") + skuInfo.getProductSkuId()
    // + productDetailPostfix;
    //
    // // 当出现异常情况,商品详情页使用公用的默认主图
    // String defaultImagePath = "http://jscss.kmb2b.com/res/images/default__logo_err400_400.jpg";
    //
    // sb.append("<item>");
    // sb.append("<id>" + skuInfo.getProductSkuId() + "</id>");
    // sb.append("<title><![CDATA[" + skuInfo.getProductTitle() + "]]></title>");
    // sb.append("<category><![CDATA[" + skuInfo.getTopCategoryName() + ">"
    // + skuInfo.getMiddleCategoryName() + ">" + skuInfo.getTopCategoryName()
    // + "]]></category>");
    // sb.append("<url><![CDATA[" + pcProductDetailPath + "]]></url> ");
    // sb.append("<url_wap><![CDATA[" + wapProductDetailPath + "]]></url_wap> ");
    // sb.append("<price>" + skuInfo.getPrice() + "</price>");
    // sb.append("<promotion_price>" + skuInfo.getPromotionPrice() + "</promotion_price>");
    // sb.append("<wap_price>" + skuInfo.getPromotionPrice() + "</wap_price> ");
    // sb.append("<status>" + skuInfo.getStatus() + "</status>");
    //
    // if (!StringUtils.isBlank(skuInfo.getIntroduce())) {
    // sb.append("<detail><![CDATA[" + skuInfo.getIntroduce() + "]]></detail>");
    // } else {
    // // 需要留有一定的空格,不然解析时会把前半部分的节点给去掉
    // sb.append("<detail> </detail>");
    // }
    //
    //
    // // 是否有默认主图
    // boolean hasDefaultImg = false;
    // for (ProductImage image : skuInfo.getImageList()) {
    // if ("0".equals(image.getIsDefault())) {
    // defaultImagePath = productImgServerUrl + image.getImgPath1();
    // hasDefaultImg = true;
    // break;
    // }
    // }
    //
    // sb.append("<pic_main>");
    // sb.append("<img>");
    // sb.append("<url><![CDATA[" + defaultImagePath + "]]></url>");
    // sb.append("<size>" + defaultSize + "</size>");
    // sb.append("</img>");
    // sb.append("</pic_main>");
    //
    //
    // boolean hasExtraImg = false;
    //
    // // 技术文档中 pic_extra节点是必须存在
    // // 20151226 如果节点值为空,则会进行过滤,所以将其放入到里面执行
    // // 避免只有一张图片并且那种图片还是主图的情况,重复拼接主图
    // if (skuInfo.getImageList().size() > 1 && hasDefaultImg) {
    // sb.append("<pic_extra>");
    // hasExtraImg = true;
    // for (ProductImage image : skuInfo.getImageList()) {
    // if ("0".equals(image.getIsDefault())) {
    // continue;
    // }
    // sb.append("<img>");
    // sb.append("<url><![CDATA[" + productImgServerUrl + image.getImgPath1()
    // + "]]></url>");
    // sb.append("<size>" + defaultSize + "</size>");
    // sb.append("</img>");
    // }
    // sb.append("</pic_extra>");
    // }
    // if (!hasExtraImg) {
    // // 需要留有一定的空格,不然解析时会把前半部分的节点给去掉
    // sb.append("<pic_extra> </pic_extra>");
    // }
    //
    // sb.append("</item>");
    // return sb.toString();
    // }

    @Override
    public List<ProductImage> findAllIsDefaultByProductId(Long productId) throws Exception {

        ProductImage productImCondition = new ProductImage();
        List<ProductImage> list = new ArrayList<>();
        try {
            productImCondition.setProductId(productId);
            productImCondition.setIsDefault("0");
            list = productImageDao.findAllImageByProductId(productImCondition);
            return list;
        } catch (Exception e) {
            logger.error("查询商品主图出错", e);
        }
        return list;
    }

}
