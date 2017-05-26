package com.pltfm.remote.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.kmzyc.cms.remote.service.ViewProductInfoRemoteService;
import com.pltfm.app.dao.BnesBrowsingHisDAO;
import com.pltfm.app.dao.OrderItemDAO;
import com.pltfm.app.dao.ProdAppraiseDAO;
import com.pltfm.app.dao.ViewProductInfoDAO;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.vobject.ViewProductInfo;
import com.pltfm.app.vobject.ViewProductInfoExample;
import com.kmzyc.promotion.app.vobject.ProductSkuPriceCache;
import com.kmzyc.promotion.remote.service.PromotionRemoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * 产品标签远程实现类
 *
 * @author cjm
 * @since 2014-1-10
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Component(value = "viewProductInfoRemoteService")
public class ViewProductInfoRemoteServiceImpl implements
        ViewProductInfoRemoteService {
	private static Logger logger = LoggerFactory.getLogger(ViewProductInfoRemoteServiceImpl.class);

    /**
     * 产品视图DOA接口
     */
    @Resource(name = "viewProductInfoDAO")
    private ViewProductInfoDAO viewProductInfoDAO;

    /**
     * 产品评价DAO接口
     */
    @Resource(name = "prodAppraiseDAO")
    private ProdAppraiseDAO prodAppraiseDAO;

    /**
     * 订单重表DAO接口
     */
    @Resource(name = "orderItemDAO")
    private OrderItemDAO orderItemDAO;

    /**
     * 产品点击量DAO接口
     */
    @Resource(name = "bnesBrowsingHisDAO")
    private BnesBrowsingHisDAO bnesBrowsingHisDAO;

    @Autowired
    private PromotionRemoteService promotionRemoteService;

    /**
     * 是否为春季
     */
    public static boolean isSpring() {
        String thisDate = DateTimeUtils.getDate(DateTimeUtils
                .getCalendarInstance().getTime());

        String springFirst = ConfigurationUtil.getString("springFirst");

        String springLast = ConfigurationUtil.getString("springLast");
        // 当前年份
        Integer year = DateTimeUtils.getYear(DateTimeUtils
                .getCalendarInstance().getTime());

        springFirst = year + springFirst;

        springLast = year + springLast;
        // 春季开始时间
        Date first = DateTimeUtils.parseDate(springFirst);
        // 春季结束时间
        Date last = DateTimeUtils.parseDate(springLast);
        // 当前时间
        Date thi = DateTimeUtils.parseDate(thisDate);
        if ((thi.compareTo(first) == 0 || thi.compareTo(first) == 1)
                && (thi.compareTo(last) == 0 || thi.compareTo(last) == -1)) {
            return true;
        }
        return false;
    }

    /**
     * 是否为夏季
     */
    public static boolean isSummer() {
        String thisDate = DateTimeUtils.getDate(DateTimeUtils
                .getCalendarInstance().getTime());

        String summerFirst = ConfigurationUtil.getString("summerFirst");

        String summerLast = ConfigurationUtil.getString("summerLast");
        // 当前年份
        Integer year = DateTimeUtils.getYear(DateTimeUtils
                .getCalendarInstance().getTime());

        summerFirst = year + summerFirst;

        summerLast = year + summerLast;
        // 春季开始时间
        Date first = DateTimeUtils.parseDate(summerFirst);
        // 春季结束时间
        Date last = DateTimeUtils.parseDate(summerLast);
        // 当前时间
        Date thi = DateTimeUtils.parseDate(thisDate);
        if ((thi.compareTo(first) == 0 || thi.compareTo(first) == 1)
                && (thi.compareTo(last) == 0 || thi.compareTo(last) == -1)) {
            return true;
        }
        return false;
    }

    /**
     * 是否为秋季
     */
    public static boolean isAutumn() {
        String thisDate = DateTimeUtils.getDate(DateTimeUtils
                .getCalendarInstance().getTime());

        String autumnFirst = ConfigurationUtil.getString("autumnFirst");

        String autumnLast = ConfigurationUtil.getString("autumnLast");
        // 当前年份
        Integer year = DateTimeUtils.getYear(DateTimeUtils
                .getCalendarInstance().getTime());

        autumnFirst = year + autumnFirst;

        autumnLast = year + autumnLast;
        // 春季开始时间
        Date first = DateTimeUtils.parseDate(autumnFirst);
        // 春季结束时间
        Date last = DateTimeUtils.parseDate(autumnLast);
        // 当前时间
        Date thi = DateTimeUtils.parseDate(thisDate);
        if ((thi.compareTo(first) == 0 || thi.compareTo(first) == 1)
                && (thi.compareTo(last) == 0 || thi.compareTo(last) == -1)) {
            return true;
        }
        return false;
    }

    /**
     * 是否为冬季
     */
    public static boolean isWinter() {
        String thisDate = DateTimeUtils.getDate(DateTimeUtils
                .getCalendarInstance().getTime());

        String winterFirst = ConfigurationUtil.getString("winterFirst");

        String winterLast = ConfigurationUtil.getString("winterLast");
        // 当前年份
        Integer year = DateTimeUtils.getYear(DateTimeUtils
                .getCalendarInstance().getTime());

        winterFirst = year + winterFirst;

        // 下一年
        year++;
        winterLast = year + winterLast;
        // 春季开始时间
        Date first = DateTimeUtils.parseDate(winterFirst);
        // 春季结束时间
        Date last = DateTimeUtils.parseDate(winterLast);
        // 当前时间
        Date thi = DateTimeUtils.parseDate(thisDate);
        if ((thi.compareTo(first) == 0 || thi.compareTo(first) == 1)
                && (thi.compareTo(last) == 0 || thi.compareTo(last) == -1)) {
            return true;
        }
        return false;
    }

    /**
     * 根据产品Sku查询出产品标签集合
     *
     * @param productSkuId 产品SkuId 产品渠道
     * @return List<String> 产品挂角标签
     */
    @Override
    public List<String> queryProductTags(Integer productSkuId) {
        // 产品标签集合
        List<String> productTags = new ArrayList<String>();
        try {
            /*PromotionRemoteService promotionRemoteService = null;
			try {
				promotionRemoteService = (PromotionRemoteService) RemoteTool
						.getRemote(Constants.PROMOTION_SYSTEM_CODE, "promotionRemoteService");
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}*/

            ViewProductInfo product = null;

            ViewProductInfoExample example = new ViewProductInfoExample();
            // 转换为大写
            example.createCriteria().andProductSkuIdEqualTo(productSkuId);

            List list = viewProductInfoDAO.selectByExample(example);
            if (null != list && list.size() > 0) {


                product = (ViewProductInfo) viewProductInfoDAO
                        .selectByExample(example).get(0);
                if (null != product) {

                    ProductSkuPriceCache psp = new ProductSkuPriceCache();
                    psp.setProductSkuId(Long.valueOf(product.getProductSkuId()));
                    // 调用产品功能关联类标签接口
                    psp = promotionRemoteService.getCurrentTimeProductSkuPrice(psp);

                    String[] lebal = null;
                    // 获取功能关联类标签
                    lebal = psp.getPromotionInfoLebal();
                    if (lebal != null) {
                        for (int i = 0; i < lebal.length; i++) {
                            productTags.add(lebal[i]);
                        }
                    }

                    // 获取手动标记类标签
                    if (product.getProductAttrList().size() > 0) {
                        for (int k = 0; k < product.getProductAttrList().size(); k++) {
                            if ("清仓".equals(product.getProductAttrList().get(k)
                                    .getProductAttrName())) {
                                productTags.add(product.getProductAttrList().get(k)
                                        .getProductAttrName());
                            } else if ("药师推荐".equals(product.getProductAttrList()
                                    .get(k).getProductAttrName())) {
                                productTags.add(product.getProductAttrList().get(k)
                                        .getProductAttrName());
                            } else if ("春季适用".equals(product.getProductAttrList()
                                    .get(k).getProductAttrName())) {
                                if (isSpring()) {
                                    productTags.add("当季");
                                }
                            } else if ("夏季适用".equals(product.getProductAttrList()
                                    .get(k).getProductAttrName())) {
                                if (isSummer()) {
                                    productTags.add("当季");
                                }
                            } else if ("秋季适用".equals(product.getProductAttrList()
                                    .get(k).getProductAttrName())) {
                                if (isAutumn()) {
                                    productTags.add("当季");
                                }
                            } else if ("冬季适用".equals(product.getProductAttrList()
                                    .get(k).getProductAttrName())) {
                                if (isWinter()) {
                                    productTags.add("当季");
                                }
                            } else if ("新品".equals(product.getProductAttrList()
                                    .get(k).getProductAttrName())) {
                                productTags.add("新品");
                            }

                        }
                    }

                    // Integer time =
                    // DateTimeUtils.diffDate(DateTimeUtils.getCalendarInstance().getTime(),
                    // product.getUpTime());
                    // if(time<7){
                    // productTags.add("新品");
                    // }

                    // 获取自动判断类标签
                    Integer five = null;
                    Map skuIdAndChannle = new HashMap();
                    skuIdAndChannle.put("productSkuId", product.getProductSkuId());

                    Map skuCodeAndChannle = new HashMap();
                    skuCodeAndChannle.put("skuCode", product.getProductSkuCode());
                    if (null != product.getProductSkuId()) {
                        try {
                            five = prodAppraiseDAO.fiveStars(skuIdAndChannle);
                            if (null != five) {
                                productTags.add("五星");
                            }
                        } catch (SQLException e) {
                        	logger.error("ViewProductInfoRemoteServiceImpl.queryProductTags异常：" + e.getMessage(), e);
                        }
                    }
                    if (null != product.getProductSkuCode()) {
                        String code = null;
                        try {
                            code = orderItemDAO.count(skuCodeAndChannle);
                        } catch (SQLException e) {
                        	logger.error("ViewProductInfoRemoteServiceImpl.queryProductTags异常：" + e.getMessage(), e);
                        }
                        if (null != code) {
                            productTags.add("热卖");
                        }
                    }
                    if (null != product.getProductSkuId()) {
                        Integer count = null;
                        try {
                            count = prodAppraiseDAO
                                    .count(skuIdAndChannle);

                            if (null != count && count > 0 ) {
                                productTags.add("热评");
                            }
                        } catch (SQLException e) {
                        	logger.error("ViewProductInfoRemoteServiceImpl.queryProductTags异常：" + e.getMessage(), e);
                        }
                    }
                    if (null != product.getProductSkuCode()) {
                        String human = null;
                        try {
                            human = bnesBrowsingHisDAO.count(product
                                    .getProductSkuCode());
                        } catch (SQLException e) {
                        	logger.error("ViewProductInfoRemoteServiceImpl.queryProductTags异常：" + e.getMessage(), e);
                        }
                        if (null != human) {
                            productTags.add("人气");
                        }
                    }

                    if (productTags.size() > 0) {
                        if ("满额减免".equals(productTags.get(0).trim())) {
                            productTags.remove(0);
                            productTags.add(0, "满减");
                        }
                        if ("满额送券".equals(productTags.get(0).trim())) {
                            productTags.remove(0);
                            productTags.add(0, "送券");
                        }

                        if ("药师推荐".equals(productTags.get(0).trim())) {
                            productTags.remove(0);
                            productTags.add(0, "推荐");
                        }
                    }
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return productTags;
    }

    public ViewProductInfoDAO getViewProductInfoDAO() {
        return viewProductInfoDAO;
    }

    public void setViewProductInfoDAO(ViewProductInfoDAO viewProductInfoDAO) {
        this.viewProductInfoDAO = viewProductInfoDAO;
    }

    public ProdAppraiseDAO getProdAppraiseDAO() {
        return prodAppraiseDAO;
    }

    public void setProdAppraiseDAO(ProdAppraiseDAO prodAppraiseDAO) {
        this.prodAppraiseDAO = prodAppraiseDAO;
    }

    public OrderItemDAO getOrderItemDAO() {
        return orderItemDAO;
    }

    public void setOrderItemDAO(OrderItemDAO orderItemDAO) {
        this.orderItemDAO = orderItemDAO;
    }

    public BnesBrowsingHisDAO getBnesBrowsingHisDAO() {
        return bnesBrowsingHisDAO;
    }

    public void setBnesBrowsingHisDAO(BnesBrowsingHisDAO bnesBrowsingHisDAO) {
        this.bnesBrowsingHisDAO = bnesBrowsingHisDAO;
    }

}
