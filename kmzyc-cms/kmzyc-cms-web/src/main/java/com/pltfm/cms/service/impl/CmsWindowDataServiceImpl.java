package com.pltfm.cms.service.impl;

import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.ProductSkuPriceCache;
import com.kmzyc.promotion.remote.service.PromotionRemoteService;
import com.kmzyc.supplier.model.ShopCategorys;
import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.dao.BnesBrowsingHisDAO;
import com.pltfm.app.dao.CategoryDAO;
import com.pltfm.app.dao.OrderItemDAO;
import com.pltfm.app.dao.ProdAppraiseDAO;
import com.pltfm.app.dao.ShopCategoryDao;
import com.pltfm.app.dao.ViewPromotionDAO;
import com.pltfm.app.service.CategoryService;
import com.pltfm.app.service.ProdBrandService;
import com.pltfm.app.service.ProductImageService;
import com.pltfm.app.service.ShopCategorysService;
import com.pltfm.app.service.ViewProductDetailInfoService;
import com.pltfm.app.service.ViewProductInfoService;
import com.pltfm.app.service.ViewPromotionService;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.Category;
import com.pltfm.app.vobject.ProdBrand;
import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ViewProductInfo;
import com.pltfm.app.vobject.ViewPromotion;
import com.pltfm.cms.dao.CmsShopDataDAO;
import com.pltfm.cms.dao.CmsSiteDAO;
import com.pltfm.cms.dao.CmsWindowDataDAO;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsAdvService;
import com.pltfm.cms.service.CmsInformactionTypeService;
import com.pltfm.cms.service.CmsInformationService;
import com.pltfm.cms.service.CmsSupplyService;
import com.pltfm.cms.service.CmsWindowDataService;
import com.pltfm.cms.service.CmsWindowService;
import com.pltfm.cms.service.LotteryLuckDrawService;
import com.pltfm.cms.service.LotteryPrizeService;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.util.SqlJoinUtil;
import com.pltfm.cms.vobject.CmsInformationType;
import com.pltfm.cms.vobject.CmsShopData;
import com.pltfm.cms.vobject.CmsSite;
import com.pltfm.cms.vobject.CmsWindow;
import com.pltfm.cms.vobject.CmsWindowData;
import com.pltfm.cms.vobject.CmsWindowDataExample;
import com.pltfm.cms.vobject.CmsWindowDataExample.Criteria;
import com.pltfm.cms.vobject.LotteryAwards;
import com.pltfm.cms.vobject.LotteryLuckDraw;
import com.pltfm.cms.vobject.UploadFile;
import com.pltfm.sys.model.SysUser;
import com.whalin.MemCached.MemCachedClient;

/**
 * 窗口数据业务逻辑层接口实现类
 *
 * @author cjm
 * @since 2013-9-3
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Component(value = "cmsWindowDataService")
public class CmsWindowDataServiceImpl implements CmsWindowDataService {
    private static Logger logger = LoggerFactory.getLogger(CmsWindowDataServiceImpl.class);

    /**
     * 窗口数据DAO接口
     */
    @Resource(name = "cmsWindowDataDAO")
    private CmsWindowDataDAO cmsWindowDataDAO;
    /**
     * 抽奖活动逻辑层接口
     */
    @Resource(name = "lotteryLuckDrawService")
    private LotteryLuckDrawService lotteryLuckDrawService;
    /**
     * 奖品逻辑层接口
     */
    @Resource(name = "lotteryPrizeService")
    private LotteryPrizeService lotteryPrizeService;

    public LotteryPrizeService getLotteryPrizeService() {
        return lotteryPrizeService;
    }

    public void setLotteryPrizeService(LotteryPrizeService lotteryPrizeService) {
        this.lotteryPrizeService = lotteryPrizeService;
    }

    @Resource(name = "cmsShopDataDAO")
    private CmsShopDataDAO cmsShopDataDAO;

    // 活动数据DAO
    @Resource(name = "viewPromotionDAO")
    private ViewPromotionDAO viewPromotionDAO;

    /**
     * 咨询业务逻辑接口
     */
    @Resource(name = "cmsInformationService")
    private CmsInformationService cmsInformationService;
    /**
     * 供应商信息业务逻辑接口
     */
    @Resource(name = "cmsSupplyService")
    private CmsSupplyService cmsSupplyService;

    /**
     * 咨询类别业务逻辑接口
     */
    @Resource(name = "cmsInformactionTypeService")
    private CmsInformactionTypeService cmsInformactionTypeService;

    /**
     * 窗口业务逻辑层接口
     */
    @Resource(name = "cmsWindowService")
    private CmsWindowService cmsWindowService;

    /**
     * 类目业务逻辑层接口
     */
    @Resource(name = "categoryService")
    private CategoryService categoryService;

    /**
     * 活动信息业务逻辑层接口
     */
    @Resource(name = "viewPromotionService")
    private ViewPromotionService viewPromotionService;

    /**
     * 品牌信息业务逻辑层接口
     */
    @Resource(name = "prodBrandService")
    private ProdBrandService prodBrandService;

    /**
     * 产品视图业务逻辑接口
     */
    @Resource(name = "viewProductInfoService")
    private ViewProductInfoService viewProductInfoService;

    /**
     * 产品图片业务逻辑接口
     */
    @Resource(name = "productImageService")
    private ProductImageService productImageService;

    /**
     * 产品详情业务逻辑接口
     */
    @Resource(name = "viewProductDetailInfoService")
    private ViewProductDetailInfoService viewProductDetailInfoService;
    /**
     * 广告业务逻辑接口
     */
    @Resource(name = "cmsAdvService")
    private CmsAdvService cmsAdvService;

    /**
     * 读取属性文件内容
     */
    /*
     * @Resource(name = "templateConfig") private Map<String, String> templateConfig;
     */

    // 商品分类数据DAO
    @Resource(name = "categoryDAO")
    private CategoryDAO categoryDAO;

    // 店铺类目分类数据DAO
    @Resource(name = "shopCategoryDao")
    private ShopCategoryDao shopCategoryDao;

    /**
     * 客户浏览信息
     */
    @Resource(name = "bnesBrowsingHisDAO")
    private BnesBrowsingHisDAO bnesBrowsingHisDAO;

    /**
     * 产品评价
     */
    @Resource(name = "prodAppraiseDAO")
    private ProdAppraiseDAO prodAppraiseDAO;

    /**
     * 产品从表
     */
    @Resource(name = "orderItemDAO")
    private OrderItemDAO orderItemDAO;

    @Resource(name = "memcachedClient")
    private MemCachedClient memcachedClient;

    // 站点
    @Resource(name = "cmsSiteDAO")
    private CmsSiteDAO cmsSiteDAO;

    @Resource(name = "shopCategorysService")
    private ShopCategorysService shopCategorysService;

    @Autowired
    private PromotionRemoteService promotionRemoteService;

    /**
     * 添加窗口数据信息
     *
     * @param dataIds 数据信息集合
     * @param dataType 数据类型集合
     * @param windowId 窗口Id
     * @param created 当前用户Id
     * @return 返回值
     * @throws Exception sql异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addCmsWindowData(List dataIds, Integer dataType, Integer windowId,
            Integer created, Integer siteId) throws Exception {
        // 根据数据类型根据数据
        List<CmsWindowData> dataList = this.queryByWindowIdDataType(windowId, dataType, siteId);
        Integer sortMax = 0;
        // 受影响行数
        int rows = 0;
        for (int i = 0; i < dataIds.size(); i++) {
            boolean flag = true;
            for (int j = 0; j < dataList.size(); j++) {
                // 非产品类型数据
                if (dataType != 0) {
                    if (Integer.valueOf(dataIds.get(i).toString())
                            .equals(dataList.get(j).getDataId())) {
                        flag = false;
                        break;
                    }
                    // 产品类型数据
                } else {
                    if (Integer.valueOf(dataIds.get(i).toString().split("@")[0])
                            .equals(dataList.get(j).getDataId())) {
                        if (dataList.get(j).getProductSkuId() != null) {
                            if (Integer.valueOf(dataIds.get(i).toString().split("@")[1])
                                    .equals(dataList.get(j).getProductSkuId())) {
                                flag = false;
                                break;
                            }
                        }

                    }
                }

            }
            if (flag) {
                CmsWindowData data = new CmsWindowData();

                // 非产品类型数据
                if (dataType != 0) {
                    data.setDataId(Integer.valueOf(dataIds.get(i).toString()));
                } else {
                    // 产品类型数据
                    data.setDataId(Integer.valueOf(dataIds.get(i).toString().split("@")[0]));
                    data.setProductSkuId(Integer.valueOf(dataIds.get(i).toString().split("@")[1]));
                }

                data.setWindowId(windowId);

                data.setDataType(dataType);
                data.setSiteId(siteId);

                if (dataList.size() != 0) {
                    if (sortMax == 0) {
                        sortMax = dataList.get(dataList.size() - 1).getSort();
                    }

                }
                if (sortMax == null) {
                    sortMax = 0;

                }
                ++sortMax;
                data.setSort(sortMax);
                data.setCreated(created);
                data.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
                rows += cmsWindowDataDAO.insert(data);

            }
        }

        return rows;
    }

    /**
     * 添加奖项奖品数据信息
     *
     * @param cmsWindowDatas 数据信息集合
     * @param cmsWindowDatas 数据类型集合
     * @param windowId 窗口Id
     * @param created 当前用户Id
     * @return 返回值
     * @throws Exception sql异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addWindowDatas(List<CmsWindowData> cmsWindowDatas, Integer windowId,
            Integer created, Integer siteId) throws Exception {
        CmsWindowData cmsWindowData;
        Integer sortMax = 0;
        int res = 0;
        for (CmsWindowData cmsWindowData1 : cmsWindowDatas) {
            // 获得单个CmsWindowData对象
            cmsWindowData = cmsWindowData1;
            if (cmsWindowData != null && (!cmsWindowData.getUser_defined_name().equals(""))) {

                // 根据数据类型根据数据
                List<CmsWindowData> dataList = this.queryByWindowIdDataType(windowId, 10, siteId);

                if (dataList.size() != 0) {
                    if (sortMax == 0) {
                        sortMax = dataList.get(dataList.size() - 1).getSort();
                    }

                }
                if (sortMax == null) {
                    sortMax = 0;
                }
                ++sortMax;

                cmsWindowData.setSort(sortMax);
                // 设置属性
                cmsWindowData.setDataType(10);
                cmsWindowData.setWindowId(windowId);
                cmsWindowData.setCreated(created);
                cmsWindowData.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
                cmsWindowData.setSiteId(siteId);

                // 保存数据
                this.cmsWindowDataDAO.addCmsWindowData(cmsWindowData);
            }

        }
        return res;
    }

    /**
     * 根据窗口Id删除数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteByWindowId(Integer windowId) throws Exception {
        CmsWindowDataExample example = new CmsWindowDataExample();
        example.createCriteria().andWindowIdEqualTo(windowId);
        return cmsWindowDataDAO.deleteByExample(example);
    }

    /**
     * 分页查询窗口数据信息
     *
     * @param cmsWindowData 窗口数据信息实体
     * @param page 分页实体
     * @return 返回值
     * @throws Exception 异常
     */
    @Override
    public Page queryForPage(CmsWindowData cmsWindowData, Page page) throws Exception {
        if (page == null) {
            page = new Page();
        }
        if (cmsWindowData == null) {
            cmsWindowData = new CmsWindowData();
        }
        // 根据条件获取窗口数据信息总条数
        int totalNum = cmsWindowDataDAO.countByCmsWindowData(cmsWindowData);
        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            cmsWindowData.setStartIndex(page.getStartIndex());
            cmsWindowData.setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(cmsWindowDataDAO.queryForPage(cmsWindowData));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }

    // 根据条件获取窗口数据信息总条数
    @Override
    public int isCmsWindow(CmsWindowData cmsWindowData) throws Exception {
        return cmsWindowDataDAO.countByCmsWindowData(cmsWindowData);

    }

    /**
     * 返回子窗口数据集
     *
     * @param cmsWindowData 窗口数据信息条件
     * @return 返回值
     * @throws SQLException sql异常
     */
    @Override
    public List queryWindowDataList(CmsWindowData cmsWindowData) throws Exception {
        Integer dateType = cmsWindowData.getDataType();
        List list;
        // 窗口类型
        if (dateType == 4) {

            list = cmsWindowDataDAO.querySubWindowDataList(cmsWindowData);
            return list;
        }
        // 窗口自定义类型()
        else if (dateType == 6) {
            list = cmsWindowDataDAO.queryWindowDataList(cmsWindowData);
            return list;
        } else {
            // 根据数据类型得到所有数据集
            list = cmsWindowDataDAO.queryWindowDataList(cmsWindowData);

            List<Integer> dataIdsList_sort = new ArrayList<>();
            // 窗口数据ID集合
            List<Integer> dataIdsList = new ArrayList<>();
            List<Integer> dataSkuIds = new ArrayList<>();
            // 存放类目对应的排序位置
            Map<Integer, Integer> categorySortMap = new HashMap<>();
            if (null != list && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    // 已选择的数据List
                    CmsWindowData data = (CmsWindowData) list.get(i);
                    dataIdsList.add(data.getDataId());
                    categorySortMap.put(data.getDataId(), data.getSort());
                    if (null != data.getProductSkuId()) {
                        dataSkuIds.add(data.getProductSkuId());
                        // 已选择的数据String用于in 排序
                        dataIdsList_sort.add(data.getProductSkuId());
                        dataIdsList_sort.add(i + 1);
                    } else {
                        // 已选择的数据String用于in 排序
                        dataIdsList_sort.add(data.getDataId());
                        dataIdsList_sort.add(i + 1);
                    }


                }
            }
            if (dataIdsList.size() > 0) {
                // 活动类型
                if (dateType == 1) {

                    return viewPromotionDAO.queryPromotionData(dataIdsList, dataIdsList_sort);
                    // 产品类型
                } else if (dateType == 0) {

                    list = viewProductInfoService.selectByDataIds(dataIdsList, dataSkuIds,
                            dataIdsList_sort);

                    // 拿最终活动价格
                    return updateMoney(list);
                    // 类目
                } else if (dateType == 2) {
                    // 同步搜索类目
                    /*
                     * String b2b_category_format =
                     * ConfigurationUtil.getString("b2b_category_format"); String
                     * search_category_format =
                     * ConfigurationUtil.getString("search_category_format"); if (null !=
                     * b2b_category_format && null != search_category_format) { String[]
                     * b2b_category_list = b2b_category_format.split(","); String[]
                     * search_category_list = search_category_format.split(","); // 当前窗口 CmsWindow
                     * cmsWindow_curr =
                     * cmsWindowService.selectByPrimaryKey(cmsWindowData.getWindowId()); String
                     * windowName_curr = cmsWindow_curr.getName();
                     * 
                     * int index = Arrays.binarySearch(b2b_category_list, cmsWindow_curr.getName());
                     * if (index >= 0) { for (int i = 0; i < b2b_category_list.length; i++) { //
                     * 窗口名称 String categoryWindowName = b2b_category_list[i]; if
                     * (windowName_curr.equals(categoryWindowName)) { Map map = new HashMap();
                     * String searchCategoryName = search_category_list[i];
                     * map.put("p_categoryWindowName", categoryWindowName);
                     * map.put("p_searchCategoryName", searchCategoryName); // 先清除后添加
                     * cmsWindowDataDAO.updateSearchCategory(map);
                     * 
                     * } } } }
                     */


                    // 查询已绑定的类目
                    List<Category> categorylist = categoryDAO.selectCategoryParent(
                            SqlJoinUtil.getSqlIn(dataIdsList, 1000, "CATEGORY_ID"));

                    if (null == categorylist || categorylist.isEmpty()) {

                        return categorylist;
                    }

                    // 重新排序
                    List<Category> sortCategoryList = new ArrayList<>();
                    for (Integer dataId : dataIdsList) {
                        for (Category category : categorylist) {
                            if (category.getCategoryId().intValue() == dataId.intValue()) {
                                category.setSortno(categorySortMap.get(dataId));
                                sortCategoryList.add(category);
                                break;
                            }
                        }
                    }

                    list = categorySub(sortCategoryList);

                    CmsWindow cmsWindow =
                            cmsWindowService.selectByPrimaryKey(cmsWindowData.getWindowId());

                    // 添加至缓存
                    savaMemcached(cmsWindow.getSiteId(), list);

                    return list;
                    // 店铺类目
                } else if (dateType == 13) {
                    // 查询已绑定的类目
                    list = shopCategoryDao.queryBandShopCategoryList(
                            SqlJoinUtil.getSqlIn(dataIdsList, 1000, "shop_CATEGORY_ID"));

                    return shopCategorysSub(list);
                    // 品牌
                } else if (dateType == 3) {

                    return prodBrandService.selectByDataIds(dataIdsList);
                    // 产品供应商信息
                } else if (dateType == 11) {

                    return cmsSupplyService.getShopMainList(dataIdsList);
                    // cms关联供应商信息
                } else if (dateType == 12) {

                    return cmsShopDataDAO.selectList(dataIdsList);
                    // 产品排行榜所需类目
                } else if (dateType == 7) {
                    // 通过绑定的类目信息查询产品主键信息
                    List productList = categoryService.selectByCategoryIds(dataIdsList);
                    // 通过产品主键信息查询产品信息
                    if (ListUtils.isNotEmpty(productList)) {
                        list = viewProductInfoService.selectByDataIdsAndSiteId(
                                productList/* , cmsWindowData.getSiteId() */);
                        list = updateMoney(list);
                    }
                    return list;
                    // 咨询类别查询咨询
                } else if (dateType == 8) {
                    list = cmsInformationService.inforTypesList(dataIdsList);
                    return list;

                }

            }
        }
        return list;


    }


    // 添加至缓存中(搜索系统)
    @Override
    public List savaMemcached(Integer siteId, List list) throws SQLException {
        CmsSite cmsSite = cmsSiteDAO.selectByPrimaryKey(siteId);

        List<Map<String, Object>> cacheData = caver2CacheDate(list);
        memcachedClient.delete(cmsSite.getEngName() + "_cms_categList");
        memcachedClient.add(cmsSite.getEngName() + "_cms_categList", cacheData);
        return cacheData;

    }

    /**
     * 是否为春季
     */
    public boolean isSpring() {
        String thisDate = DateTimeUtils.getDate(DateTimeUtils.getCalendarInstance().getTime());

        String springFirst = ConfigurationUtil.getString("springFirst");

        String springLast = ConfigurationUtil.getString("springLast");
        // 当前年份
        Integer year = DateTimeUtils.getYear(DateTimeUtils.getCalendarInstance().getTime());

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
    public boolean isSummer() {
        String thisDate = DateTimeUtils.getDate(DateTimeUtils.getCalendarInstance().getTime());

        String summerFirst = ConfigurationUtil.getString("summerFirst");

        String summerLast = ConfigurationUtil.getString("summerLast");
        // 当前年份
        Integer year = DateTimeUtils.getYear(DateTimeUtils.getCalendarInstance().getTime());

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
    public boolean isAutumn() {
        String thisDate = DateTimeUtils.getDate(DateTimeUtils.getCalendarInstance().getTime());

        String autumnFirst = ConfigurationUtil.getString("autumnFirst");

        String autumnLast = ConfigurationUtil.getString("autumnLast");
        // 当前年份
        Integer year = DateTimeUtils.getYear(DateTimeUtils.getCalendarInstance().getTime());

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
    public boolean isWinter() {
        String thisDate = DateTimeUtils.getDate(DateTimeUtils.getCalendarInstance().getTime());

        String winterFirst = ConfigurationUtil.getString("winterFirst");

        String winterLast = ConfigurationUtil.getString("winterLast");
        // 当前年份
        Integer year = DateTimeUtils.getYear(DateTimeUtils.getCalendarInstance().getTime());

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
     * 更新价格
     */
    @Override
    public List updateMoney(List<ViewProductInfo> productList) {

        /*
         * PromotionRemoteService promotionRemoteService = null; try { promotionRemoteService =
         * (PromotionRemoteService) RemoteTool .getRemote(Constants.PROMOTION_SYSTEM_CODE,
         * "promotionRemoteService"); } catch (MalformedURLException e1) { e1.printStackTrace(); }
         * catch (ClassNotFoundException e1) { e1.printStackTrace(); }
         */

        for (ViewProductInfo view : productList) {
            if (view.getProductTags() == null) {
                view.setProductTags(new ArrayList<String>());
            }
            try {
                ProductSkuPriceCache psp = new ProductSkuPriceCache();
                psp.setProductSkuId(Long.valueOf(view.getProductSkuId()));
                psp = promotionRemoteService.getCurrentTimeProductSkuPrice(psp);

                if (null != psp.getPromotionPrice() && psp.getPromotionPrice() > 0) {
                    view.setPrice(psp.getPromotionPrice());
                }

                /*
                 * String[] lebal = null; lebal = psp.getPromotionInfoLebal(); if (lebal != null) {
                 * for (int i = 0; i < lebal.length; i++) {
                 * 
                 * view.getProductTags().add(lebal[i]); } }
                 */
            } catch (Exception e) {
                logger.error("CmsWindowDataServiceImpl.updateMoney异常：" + e.getMessage(), e);
            }
            /*
             * 
             * if (view.getProductAttrList().size() > 0) { for (int k = 0; k <
             * view.getProductAttrList().size(); k++) { if
             * ("清仓".equals(view.getProductAttrList().get(k) .getProductAttrName())) {
             * view.getProductTags().add( view.getProductAttrList().get(k) .getProductAttrName()); }
             * else if ("药师推荐".equals(view.getProductAttrList().get(k) .getProductAttrName())) {
             * view.getProductTags().add( view.getProductAttrList().get(k) .getProductAttrName()); }
             * else if ("春季适用".equals(view.getProductAttrList().get(k) .getProductAttrName())) { if
             * (isSpring()) { view.getProductTags().add("当季"); } } else if
             * ("夏季适用".equals(view.getProductAttrList().get(k) .getProductAttrName())) { if
             * (isSummer()) { view.getProductTags().add("当季"); } } else if
             * ("秋季适用".equals(view.getProductAttrList().get(k) .getProductAttrName())) { if
             * (isAutumn()) { view.getProductTags().add("当季"); } } else if
             * ("冬季适用".equals(view.getProductAttrList().get(k) .getProductAttrName())) { if
             * (isWinter()) { view.getProductTags().add("当季"); } } else if
             * ("新品".equals(view.getProductAttrList().get(k) .getProductAttrName())) {
             * view.getProductTags().add("新品"); }
             * 
             * } }
             * 
             * Map skuIdAndChannle = new HashMap(); skuIdAndChannle.put("productSkuId",
             * view.getProductSkuId()); skuIdAndChannle.put("channel", view.getChannel());
             * 
             * Map skuCodeAndChannle = new HashMap(); skuCodeAndChannle.put("skuCode",
             * view.getProductSkuCode()); skuCodeAndChannle.put("channel", view.getChannel());
             * 
             * Integer five;
             * 
             * try { five = prodAppraiseDAO.fiveStars(skuIdAndChannle);
             * 
             * if (null != five) { view.getProductTags().add("五星"); } } catch (SQLException e) {
             * logger.error("异常：" + e.getMessage(), e); }
             * 
             * String code = null; try { code = orderItemDAO.count(skuCodeAndChannle); } catch
             * (SQLException e) { logger.error("异常：" + e.getMessage(), e); } if (null != code) {
             * view.getProductTags().add("热卖"); }
             * 
             * Integer count; try { count = prodAppraiseDAO.count(skuIdAndChannle);
             * 
             * if (null != count) { view.getProductTags().add("热评"); } } catch (SQLException e) { //
             * TODO Auto-generated catch block logger.error("异常：" + e.getMessage(), e); } String
             * human = null; try { human = bnesBrowsingHisDAO.count(view.getProductSkuCode()); }
             * catch (SQLException e) { // TODO Auto-generated catch block logger.error("异常：" +
             * e.getMessage(), e); } if (null != human) { view.getProductTags().add("人气"); }
             * 
             * if (view.getProductTags().size() > 0) { if
             * ("满额减免".equals(view.getProductTags().get(0).trim())) {
             * view.getProductTags().remove(0); view.getProductTags().add(0, "满减"); } if
             * ("满额送券".equals(view.getProductTags().get(0).trim())) {
             * view.getProductTags().remove(0); view.getProductTags().add(0, "送券"); }
             * 
             * if ("药师推荐".equals(view.getProductTags().get(0).trim())) {
             * view.getProductTags().remove(0); view.getProductTags().add(0, "推荐"); } }
             */

        }

        return productList;
    }

    /**
     * 递归类目
     */
    public List<Category> categorySub(List<Category> categorys) throws Exception {
        List<Category> categList1 = new ArrayList();
        for (Category category : categorys) {
            // 一级
            if (category.getParentId().longValue() == 0) {
                Integer categoryId1 = category.getCategoryId();
                // 二级
                List categList2 = new ArrayList();
                for (Category subCategory2 : categorys) {
                    if (subCategory2.getParentId().longValue() == categoryId1.longValue()) {
                        Integer categoryId2 = subCategory2.getCategoryId();
                        List categList3 = new ArrayList();
                        // 三级
                        for (Category subCategory3 : categorys) {
                            if (subCategory3.getParentId().longValue() == categoryId2.longValue()) {
                                categList3.add(subCategory3);
                            }
                        }
                        subCategory2.setSubCategory(categList3);
                        categList2.add(subCategory2);
                    }
                }
                category.setSubCategory(categList2);
                categList1.add(category);
            }

        }
        return categList1;
    }


    /**
     * 递归店铺类目
     */
    public List<ShopCategorys> shopCategorysSub(List<ShopCategorys> shopCategorysLists)
            throws Exception {
        List<ShopCategorys> shopCategorysList = new ArrayList();
        for (ShopCategorys shopCategorys : shopCategorysLists) {
            if (shopCategorys.getParentCategoryId() == 0) {
                Long shopCategoryId = shopCategorys.getShopCategoryId();
                List shopCategorysList2 = new ArrayList();
                for (ShopCategorys shopCategorys2 : shopCategorysLists) {

                    if (shopCategorys2.getParentCategoryId().longValue() == shopCategoryId
                            .longValue()) {
                        shopCategorysList2.add(shopCategorys2);
                    }
                }

                shopCategorys.setShopCategoryChildrenList(shopCategorysList2);
                shopCategorysList.add(shopCategorys);
            }


        }

        return shopCategorysList;
        /*
         * // 二级子类 List<ShopCategorys> subCateListAll2 =
         * shopCategorysService.queryShopCategoryListByParentId(shopCategorys.getShopCategoryId());
         * //List<ShopCategorys> subCategList2 = new ArrayList();
         * 
         * // 排序
         * 
         * if(null!=subCateListAll2&&subCateListAll2.size()>0){ List listSort = new ArrayList(); for
         * (Object objId : dataIdsList) { Integer dataId = (Integer) objId; for (Object obj :
         * subCateListAll2) { ShopCategorys ShopCategorys2 = (ShopCategorys) obj; if
         * (dataId==ShopCategorys2.getShopCategoryId().intValue()) { listSort.add(ShopCategorys2);
         * break; } } } subCateListAll2 = listSort; }
         * 
         * 
         * 
         * 
         * shopCategorys.setShopCategoryChildrenList(subCateListAll2); categList.add(shopCategorys);
         */
    }

    private List<Map<String, Object>> caver2CacheDate(List<Category> categList) {
        if (null == categList || categList.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(categList.size());
        for (Category c : categList) {
            Map<String, Object> info = new HashMap<String, Object>(5);

            int id = c.getCategoryId();
            String code = c.getCategoryCode();
            String name = c.getCategoryName();
            String sql = c.getExecSql();
            List<Category> subList = c.getSubCategory();

            info.put("id", id);
            info.put("code", code);
            info.put("name", name);
            info.put("sql", sql);
            info.put("children", caver2CacheDate(subList));
            result.add(info);
        }
        return result;
    }

    // 根据数据类型号和产品ID,返回所有涉及到的页面列表
    @Override
    public List queryAllPage(String sqlParam) throws SQLException {
        return cmsWindowDataDAO.queryAllPage(sqlParam);
    }

    /**
     * 按窗口和数据类型查询数据
     *
     * @param windowId 窗口主键
     * @param dataType 数据类型
     * @return 返回值
     * @throws Exception 异常
     */
    @Override
    public List queryByWindowIdDataType(Integer windowId, Integer dataType, Integer siteId)
            throws Exception {
        CmsWindowDataExample example = new CmsWindowDataExample();
        example.setOrderByClause("sort");
        Criteria criteria = example.createCriteria();
        if (null != windowId) {
            criteria.andWindowIdEqualTo(windowId);
        }
        if (null != dataType) {
            criteria.andDataTypeEqualTo(dataType);
        }
        if (null != siteId) {
            criteria.andSiteIdEqualTo(siteId);
        }


        List list = cmsWindowDataDAO.selectByExample(example);
        List windowDataList = new ArrayList();
        String imageOut = PathConstants.cmsPicPath();
        if (null != list && list.size() > 0) {
            for (Object aList : list) {
                CmsWindowData cmsWindowData = (CmsWindowData) aList;
                if (cmsWindowData.getUser_defined_picpath() != null) {
                    cmsWindowData.setUser_defined_picpath(
                            imageOut + "/" + cmsWindowData.getUser_defined_picpath());
                }
                windowDataList.add(cmsWindowData);
            }

        }

        return windowDataList;
    }

    @Override
    public CmsWindowData queryWindowDataId(CmsWindowData cmsWindowData) throws Exception {
        return cmsWindowDataDAO.queryWindowDataId(cmsWindowData);
    }
    // 查询店铺类目和自定义数据

    /**
     * 按窗口Id查询数据
     *
     * @param cmsWindowData 数据类型
     * @return 返回值
     * @throws Exception 异常
     */
    @Override
    public Page queryBywindowId(CmsWindowData cmsWindowData, Page page) throws Exception {
        if (page == null) {
            page = new Page();
            // page.setPageSize(100);
        }
        if (cmsWindowData == null) {
            cmsWindowData = new CmsWindowData();
        }
        // 根据条件获取窗口数据信息总条数
        int totalNum = cmsWindowDataDAO.countByCmsWindowData(cmsWindowData);
        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            cmsWindowData.setStartIndex(page.getStartIndex());
            cmsWindowData.setEndIndex(page.getStartIndex() + page.getPageSize());

            List<CmsWindowData> list = cmsWindowDataDAO.queryForPage(cmsWindowData);
            for (CmsWindowData data : list) {
                switch (data.getDataType()) {
                    case 0:
                        ViewProductInfo view = new ViewProductInfo();
                        view.setProductId(data.getDataId());
                        view.setProductSkuId(data.getProductSkuId());
                        ViewProductInfo viewProductInfo =
                                viewProductInfoService.selectByIdOrSkuId(view);
                        if (null != viewProductInfo) {
                            data.setDataName(viewProductInfo.getProcuctName() + "_"
                                    + viewProductInfo.getProductSkuId());
                        }

                        break;
                    case 1:
                        ViewPromotion viewPromotion =
                                viewPromotionService.selectByPrimaryKey(data.getDataId());
                        if (null != viewPromotion) {
                            data.setDataName(viewPromotion.getPromotionName());
                        }

                        break;
                    // 商城类目
                    case 2:
                        Category category = categoryService.selectByPrimaryKey(data.getDataId());
                        if (null != category) {
                            data.setDataName(category.getCategoryName());
                        }

                        break;
                    // 店铺类目
                    case 13:
                        ShopCategorys shopCategorys =
                                shopCategorysService.queryByPrimaryKey(data.getDataId());
                        if (null != shopCategorys) {
                            data.setDataName(shopCategorys.getCategoryName());
                        }

                        break;
                    case 3:
                        ProdBrand prodBrand = prodBrandService.selectByPrimaryKey(data.getDataId());
                        if (null != prodBrand) {
                            data.setDataName(prodBrand.getBrandName());
                        }
                        break;
                    case 4:
                        CmsWindow cmsWindow = cmsWindowService.selectByPrimaryKey(data.getDataId());
                        if (null != cmsWindow) {
                            data.setDataName(cmsWindow.getName() + "_" + cmsWindow.getTheme());
                            data.setWindowId(cmsWindow.getWindowId());
                        }
                        break;
                    case 6:
                        data.setDataName(data.getUser_defined_name());
                        break;
                    case 7:
                        Category cate = categoryService.selectByPrimaryKey(data.getDataId());
                        if (null != cate) {
                            data.setDataName(cate.getCategoryName());
                        }
                        break;
                    case 9:
                        LotteryLuckDraw l =
                                lotteryLuckDrawService.selectByPrimaryKey(data.getDataId());
                        if (null != l) {
                            data.setDataName(l.getTitel());
                        }
                        break;
                    case 10:
                        LotteryAwards lp = lotteryPrizeService.selectByPrimaryKey(data.getDataId());
                        if (null != lp) {
                            data.setDataName(lp.getTitle());
                        }
                        break;
                    case 11:
                        ShopMain s = cmsSupplyService.getShopMain(Long.valueOf(data.getDataId()));
                        if (null != s) {
                            data.setDataName(s.getShopName());
                        }
                        break;
                    case 12:
                        CmsShopData cms = cmsShopDataDAO.select(data.getDataId());
                        if (null != cms) {
                            data.setDataName(cms.getTitle());
                        }
                        break;
                    case 8:
                        CmsInformationType cmsInformationType =
                                cmsInformactionTypeService.byid(data.getDataId());
                        if (null != cmsInformationType) {
                            data.setDataName(cmsInformationType.getName());
                        }
                        break;
                    case 14: // 活动商品
                        ViewProductInfo promotionProduct = new ViewProductInfo();
                        promotionProduct.setProductId(data.getDataId());
                        promotionProduct.setProductSkuId(data.getProductSkuId());
                        promotionProduct =
                                viewProductInfoService.selectByIdOrSkuId(promotionProduct);
                        if (null != promotionProduct) {
                            data.setDataName(promotionProduct.getProcuctName() + "_"
                                    + promotionProduct.getProductSkuId());
                        }
                        break;
                }

            }
            page.setDataList(list);
        } else {
            page.setRecordCount(0);
            page.setDataList(null);

        }
        return page;
    }

    /**
     * 按窗口Id、数据类型和数据Id删除数据
     *
     * @param windowDataId 窗口数据Id
     * @return 返回值
     * @throws Exception 异常
     */
    @Override
    public Integer deleteData(Integer windowDataId) throws Exception {
        return cmsWindowDataDAO.deleteByPrimaryKey(windowDataId);
    }

    /**
     * 通过商品信息查询商品标签标签信息
     *
     * @param view 商品实体信息
     * @return 商品标签信息集合
     * @throws Exception 异常信息
     */
    @Override
    public List<String> selectProductAutoTags(ViewProductInfo view) throws Exception {
        List<String> productTags = new ArrayList<>();
        Map skuIdAndChannle = new HashMap();
        skuIdAndChannle.put("productSkuId", view.getProductSkuId());
        // skuIdAndChannle.put("channel", "B2B"/*view.getChannel()*/);

        Map skuCodeAndChannle = new HashMap();
        skuCodeAndChannle.put("skuCode", view.getProductSkuCode());
        // skuCodeAndChannle.put("channel", "B2B"/*view.getChannel()*/);
        String human;
        human = bnesBrowsingHisDAO.count(view.getProductSkuCode());
        if (null != human) {
            productTags.add("人气");
        }
        String code;
        code = orderItemDAO.count(skuCodeAndChannle);
        if (null != code) {
            productTags.add("热卖");
        }

        Integer count;
        count = prodAppraiseDAO.count(skuIdAndChannle);
        if (null != count && count > 50) {
            productTags.add("热评");
        }
        Integer five;
        five = prodAppraiseDAO.fiveStars(skuIdAndChannle);
        if (null != five) {
            productTags.add("五星");
        }
        return productTags;
    }

    /**
     * 处理商品手动标签数据集合信息
     *
     * @param productAttrList 商品手动标签集合信息
     */
    @Override
    public List<String> selectProductTags(List<ProductAttr> productAttrList) throws Exception {
        List<String> productTags = new ArrayList<>();
        if (ListUtils.isNotEmpty(productAttrList)) {
            for (ProductAttr attr : productAttrList) {
                if ("清仓".equals(attr.getProductAttrName())) {
                    productTags.add(attr.getProductAttrName());
                } else if ("药师推荐".equals(attr.getProductAttrName())) {
                    productTags.add(attr.getProductAttrName());
                } else if ("春季适用".equals(attr.getProductAttrName())) {
                    if (isSpring()) {
                        productTags.add("当季");
                    }
                } else if ("夏季适用".equals(attr.getProductAttrName())) {
                    if (isSummer()) {
                        productTags.add("当季");
                    }
                } else if ("秋季适用".equals(attr.getProductAttrName())) {
                    if (isAutumn()) {
                        productTags.add("当季");
                    }
                } else if ("冬季适用".equals(attr.getProductAttrName())) {
                    if (isWinter()) {
                        productTags.add("当季");
                    }
                } else if ("新品".equals(attr.getProductAttrName())) {
                    productTags.add("新品");
                }
            }
        }
        return productTags;
    }

    /**
     * 修改数据的排序
     */
    @Override
    public Integer updateSort(CmsWindowData cmsWindowData) throws Exception {
        return cmsWindowDataDAO.updateByPrimaryKeySelective(cmsWindowData);
    }

    // /**
    // * 根据窗口Id查询不同类型的数据
    // * @param windowId
    // * @return
    // * @throws Exception
    // */
    // @Override
    // public List<Object> queryByWindowId(String dataIds,Integer dataType)
    // throws Exception {
    // List<CmsWindowData> list = null;
    // CmsWindowDataExample example = new CmsWindowDataExample();
    // example.createCriteria().andWindowIdEqualTo(windowId);
    // //根据窗口Id查询此窗口的数据
    // list = cmsWindowDataDAO.selectByExample(example);
    // //返回具体对象集合
    // List<Object> objList = new ArrayList<Object>();
    // for(CmsWindowData data : list){
    // switch (data.getDataType()) {
    // case 0:
    // ViewProductInfo viewProductInfo =
    // viewProductInfoService.selectByPrimaryKey(data.getDataId());
    // //添加产品图片
    // viewProductInfo.setProductImageList(productImageService.selectBySkuId(viewProductInfo.getProductSkuId()));
    // //添加产品详情
    // viewProductInfo.setViewProductDetailInfoList(viewProductDetailInfoService.selectByproductId(viewProductInfo.getProductId()));
    // objList.add(viewProductInfo);
    // break;
    // case 1:
    // objList.add(viewPromotionService.selectByPrimaryKey(data.getDataId()));
    // break;
    // case 2:
    // objList.add(categoryService.selectByPrimaryKey(data.getDataId()));
    // break;
    // case 3:
    // objList.add(prodBrandService.selectByPrimaryKey(data.getDataId()));
    // break;
    //
    // }
    // }
    // return objList;
    // }

    @Override
    public Integer deleteData(CmsWindowData cmsWindowData) throws Exception {
        return this.cmsWindowDataDAO.deleteByCmsWindowData(cmsWindowData);
    }

    @Override
    public Integer addCmsWindowData(List<CmsWindowData> cmsWindowDatas, Integer windowId,
            Integer created, List<UploadFile> uploadFile, Integer siteId) throws Exception {
        int res = 0, num = 0;
        // 取得上传路径
        String imagePath = ConfigurationUtil.getString("imagePut");
        FileOperateUtils.checkAndCreateDirs(imagePath);
        UploadFile file;
        Integer sortMax = maxSort(windowId, 6, siteId);
        for (CmsWindowData cmsWindowData : cmsWindowDatas) {
            // 获得单个CmsWindowData对象
            if (cmsWindowData != null && !cmsWindowData.getUser_defined_name().equals("")) {
                // 文件上传
                if (cmsWindowData.getUser_defined_type() == 1) {
                    file = uploadFile.get(num);
                    if (file != null) {
                        String imgPath = FileOperateUtils.upload(file, imagePath);
                        cmsWindowData.setUser_defined_picpath(imgPath);
                    }
                    num++;
                }
                // 需要优化 根据数据类型根据数据
                // List<CmsWindowData> dataList = this.queryByWindowIdDataType(windowId, 6, siteId);
                //
                // if (dataList.size() != 0) {
                // if (sortMax == 0) {
                // sortMax = dataList.get(dataList.size() - 1).getSort();
                // }
                //
                // }
                // if (sortMax == null) {
                // sortMax = 0;
                //
                // }
                // ++sortMax;

                cmsWindowData.setSort(++sortMax);
                String user_defined_name = cmsWindowData.getUser_defined_name();
                cmsWindowData.setUser_defined_url(cmsWindowData.getUser_defined_url().replaceAll(
                        user_defined_name, URLEncoder.encode(user_defined_name, "UTF-8")));

                // 设置属性
                cmsWindowData.setDataType(6);
                cmsWindowData.setWindowId(windowId);
                cmsWindowData.setCreated(created);
                cmsWindowData.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
                cmsWindowData.setSiteId(siteId);

                // 保存数据
                this.cmsWindowDataDAO.addCmsWindowData(cmsWindowData);
            }
        }
        return res;
    }

    private Integer maxSort(Integer windowId, Integer dataType, Integer siteId) throws Exception {
        CmsWindowDataExample example = new CmsWindowDataExample();
        Criteria criteria = example.createCriteria();
        if (null != windowId) {
            criteria.andWindowIdEqualTo(windowId);
        }
        if (null != dataType) {
            criteria.andDataTypeEqualTo(dataType);
        }
        if (null != siteId) {
            criteria.andSiteIdEqualTo(siteId);
        }
        return cmsWindowDataDAO.maxSortByExample(example);
    }

    @Override
    public Integer editCmsWindowData(List<CmsWindowData> cmsWindowDatas,
            List<UploadFile> uploadFile) throws Exception {
        int res = 0, num = 0;
        // 取得上传路径
        String imagePath = ConfigurationUtil.getString("imagePut");
        CmsWindowData cmsWindowData = null;
        UploadFile file = null;
        // Integer sortMax = 0;
        for (int i = 0; i < cmsWindowDatas.size(); i++) {
            // 获得单个CmsWindowData对象
            cmsWindowData = cmsWindowDatas.get(i);
            if (cmsWindowData != null && (!cmsWindowData.getUser_defined_name().equals(""))) {
                // 文件上传
                if (cmsWindowData.getUser_defined_type() == 1) {
                    file = uploadFile.get(num);
                    if (file != null) {
                        String imgPath = FileOperateUtils.upload(file, imagePath);
                        cmsWindowData.setUser_defined_picpath(imgPath);
                    }
                    num++;
                }
                // 根据数据类型根据数据
                /*
                 * List<CmsWindowData> dataList = this.queryByWindowIdDataType(
                 * cmsWindowData.getWindowId(), 6,null);
                 * 
                 * if (dataList.size() != 0) { if (sortMax == 0) { sortMax =
                 * dataList.get(dataList.size() - 1).getSort(); }
                 * 
                 * } if (sortMax == null) { sortMax = 0;
                 * 
                 * } ++sortMax;
                 * 
                 * cmsWindowData.setSort(sortMax);
                 */

                String encode = cmsWindowData.getUser_defined_name();
                cmsWindowData.setUser_defined_url(cmsWindowData.getUser_defined_url()
                        .replaceAll(encode, java.net.URLEncoder.encode(encode)));

                // 设置属性
                cmsWindowData.setDataType(6);
                // cmsWindowData.setWindowId(windowId);
                // cmsWindowData.setCreated(created);
                cmsWindowData.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
                // cmsWindowData.setSiteId(siteId);

                // 修改数据
                if (null != cmsWindowData.getWindowDataId()) {
                    // cmsWindowData.setModified(sysuser.getUserId());
                    cmsWindowData.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
                    this.cmsWindowDataDAO.update(cmsWindowData);
                    // 保存数据
                } else {
                    // cmsWindowData.setCreated(sysuser.getUserId());
                    cmsWindowData.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());

                    this.cmsWindowDataDAO.addCmsWindowData(cmsWindowData);


                }


            }
        }
        return res;
    }


    // 店铺上传图片
    @Override
    public Integer editShopWindowData(List<CmsWindowData> cmsWindowDatas,
            List<UploadFile> uploadFile, SysUser sysuser, List dataIds) throws Exception {
        int res = 0;
        // num = 0;
        // Integer windowId=0;
        // Integer siteId=0;

        // 取得上传路径
        String imagePath = ConfigurationUtil.getString("imagePut");
        CmsWindowData cmsWindowData = null;
        UploadFile file = null;
        // Integer sortMax = 0;
        for (int i = 0; i < cmsWindowDatas.size(); i++) {
            // 获得单个CmsWindowData对象
            cmsWindowData = cmsWindowDatas.get(i);
            if (cmsWindowData != null) {

                // 文件上传
                if (cmsWindowData.getUser_defined_type() == 1) {
                    file = uploadFile.get(i);
                    if (file != null) {
                        String imgPath = FileOperateUtils.upload(file, imagePath);
                        cmsWindowData.setUser_defined_picpath(imgPath);
                    }

                }

                String encode = cmsWindowData.getUser_defined_name();
                cmsWindowData.setUser_defined_url(cmsWindowData.getUser_defined_url()
                        .replaceAll(encode, java.net.URLEncoder.encode(encode)));


                Integer status = 1;
                if (null != dataIds && dataIds.size() > 0) {
                    status = (Integer) dataIds.get(0);
                }
                cmsWindowData.setStatus(status);

                // 修改数据
                if (null != cmsWindowData.getWindowDataId()) {
                    // cmsWindowData.setModified(sysuser.getUserId());
                    cmsWindowData.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
                    this.cmsWindowDataDAO.update(cmsWindowData);
                    // 保存数据
                } else {
                    // cmsWindowData.setCreated(sysuser.getUserId());
                    cmsWindowData.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
                    if (null != cmsWindowData.getUser_defined_picpath()) {
                        this.cmsWindowDataDAO.addCmsWindowData(cmsWindowData);
                    }

                }

            }
        }
        return res;
    }

    @Override
    public CmsWindowData getCmsWindowDataByPkId(Integer windowDataId) throws Exception {
        CmsWindowData cmsWindowData = this.cmsWindowDataDAO.selectByPrimaryKey(windowDataId);
        String path = PathConstants.cmsPicPath();
        cmsWindowData.setPicFullPath(path + "/" + cmsWindowData.getUser_defined_picpath());
        return cmsWindowData;
    }

    @Override
    public List queryWindowDataType(CmsWindow window) throws Exception {
        return this.cmsWindowDataDAO.queryWindowDataType(window);
    }

    @Override
    public Integer updateCmsWindowData(CmsWindowData cmsWindowData, UploadFile uploadFile)
            throws Exception {
        // 取得上传路径
        String imagePath = ConfigurationUtil.getString("imagePut");
        CmsWindowData data =
                this.cmsWindowDataDAO.selectByPrimaryKey(cmsWindowData.getWindowDataId());
        // 设置修改的属性
        data.setUser_defined_name(cmsWindowData.getUser_defined_name());

        if (uploadFile.getFile() != null) {
            String imgPath = FileOperateUtils.upload(uploadFile, imagePath);
            data.setUser_defined_picpath(imgPath);
        }

        data.setUser_defined_type(cmsWindowData.getUser_defined_type());

        String encode = cmsWindowData.getUser_defined_name();
        data.setUser_defined_url(cmsWindowData.getUser_defined_url().replaceAll(encode,
                java.net.URLEncoder.encode(encode)));
        data.setRemark(cmsWindowData.getRemark());
        data.setModified(cmsWindowData.getModified());
        data.setModifyDate(cmsWindowData.getModifyDate());
        return this.cmsWindowDataDAO.updateByPrimaryKey(data);
    }

    /**
     * 根据窗口Id、站点Id与数据类型查询数据Id集合
     */
    @Override
    public String queryByWindowData(CmsWindowData cmsWindowData) throws Exception {
        CmsWindowDataExample example = new CmsWindowDataExample();
        Criteria criteria = example.createCriteria();
        if (null != cmsWindowData.getWindowId()) {
            criteria.andWindowIdEqualTo(cmsWindowData.getWindowId());
        }
        if (null != cmsWindowData.getSiteId()) {
            criteria.andSiteIdEqualTo(cmsWindowData.getSiteId());
        }
        if (null != cmsWindowData.getDataType()) {
            criteria.andDataTypeEqualTo(cmsWindowData.getDataType());
        }

        List<CmsWindowData> list = cmsWindowDataDAO.selectByExample(example);
        String dataIdS = "";
        for (int i = 0; i < list.size(); i++) {
            if (i != list.size() - 1) {
                dataIdS += list.get(i).getDataId() + ",";
            } else {
                dataIdS += list.get(i).getDataId();
            }

        }
        return dataIdS;
    }

    public CmsWindowDataDAO getCmsWindowDataDAO() {
        return cmsWindowDataDAO;
    }

    public void setCmsWindowDataDAO(CmsWindowDataDAO cmsWindowDataDAO) {
        this.cmsWindowDataDAO = cmsWindowDataDAO;
    }

    public ViewPromotionDAO getViewPromotionDAO() {
        return viewPromotionDAO;
    }

    public void setViewPromotionDAO(ViewPromotionDAO viewPromotionDAO) {
        this.viewPromotionDAO = viewPromotionDAO;
    }

    public CategoryDAO getCategoryDAO() {
        return categoryDAO;
    }

    public void setCategoryDAO(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public CmsWindowService getCmsWindowService() {
        return cmsWindowService;
    }

    public void setCmsWindowService(CmsWindowService cmsWindowService) {
        this.cmsWindowService = cmsWindowService;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public ViewPromotionService getViewPromotionService() {
        return viewPromotionService;
    }

    public void setViewPromotionService(ViewPromotionService viewPromotionService) {
        this.viewPromotionService = viewPromotionService;
    }

    public ProdBrandService getProdBrandService() {
        return prodBrandService;
    }

    public void setProdBrandService(ProdBrandService prodBrandService) {
        this.prodBrandService = prodBrandService;
    }

    public ViewProductInfoService getViewProductInfoService() {
        return viewProductInfoService;
    }

    public void setViewProductInfoService(ViewProductInfoService viewProductInfoService) {
        this.viewProductInfoService = viewProductInfoService;
    }

    public ProductImageService getProductImageService() {
        return productImageService;
    }

    public void setProductImageService(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    public ViewProductDetailInfoService getViewProductDetailInfoService() {
        return viewProductDetailInfoService;
    }

    public void setViewProductDetailInfoService(
            ViewProductDetailInfoService viewProductDetailInfoService) {
        this.viewProductDetailInfoService = viewProductDetailInfoService;
    }

    /*
     * public Map<String, String> getTemplateConfig() { return templateConfig; }
     */

    public CmsShopDataDAO getCmsShopDataDAO() {
        return cmsShopDataDAO;
    }

    public void setCmsShopDataDAO(CmsShopDataDAO cmsShopDataDAO) {
        this.cmsShopDataDAO = cmsShopDataDAO;
    }

    /*
     * public void setTemplateConfig(Map<String, String> templateConfig) { this.templateConfig =
     * templateConfig; }
     */

    public BnesBrowsingHisDAO getBnesBrowsingHisDAO() {
        return bnesBrowsingHisDAO;
    }

    public void setBnesBrowsingHisDAO(BnesBrowsingHisDAO bnesBrowsingHisDAO) {
        this.bnesBrowsingHisDAO = bnesBrowsingHisDAO;
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

    public MemCachedClient getMemcachedClient() {
        return memcachedClient;
    }

    public void setMemcachedClient(MemCachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    public CmsInformactionTypeService getCmsInformactionTypeService() {
        return cmsInformactionTypeService;
    }

    public void setCmsInformactionTypeService(
            CmsInformactionTypeService cmsInformactionTypeService) {
        this.cmsInformactionTypeService = cmsInformactionTypeService;
    }

    public CmsInformationService getCmsInformationService() {
        return cmsInformationService;
    }

    public void setCmsInformationService(CmsInformationService cmsInformationService) {
        this.cmsInformationService = cmsInformationService;
    }

    public CmsSiteDAO getCmsSiteDAO() {
        return cmsSiteDAO;
    }

    public void setCmsSiteDAO(CmsSiteDAO cmsSiteDAO) {
        this.cmsSiteDAO = cmsSiteDAO;
    }

    // 根据站点返回窗口数据集
    @Override
    public List queryWindowDataBySite(Integer site) throws Exception {
        return cmsWindowDataDAO.queryWindowDataBySite(site);
    }

    public LotteryLuckDrawService getLotteryLuckDrawService() {
        return lotteryLuckDrawService;
    }

    public void setLotteryLuckDrawService(LotteryLuckDrawService lotteryLuckDrawService) {
        this.lotteryLuckDrawService = lotteryLuckDrawService;
    }


    @Override
    public CmsWindowData queryByDataId(Long shopDataId) throws SQLException {
        // TODO Auto-generated method stub
        CmsWindowDataExample example = new CmsWindowDataExample();
        example.createCriteria().andDataIdEqualTo(Integer.valueOf(shopDataId.toString()));
        return (CmsWindowData) cmsWindowDataDAO.selectByExample(example).get(0);

    }

    @Override
    public void updateCmsWindowData(CmsWindowData cmsWindowData) throws SQLException {
        this.cmsWindowDataDAO.updateByPrimaryKey(cmsWindowData);

    }

    public CmsSupplyService getCmsSupplyService() {
        return cmsSupplyService;
    }

    public void setCmsSupplyService(CmsSupplyService cmsSupplyService) {
        this.cmsSupplyService = cmsSupplyService;
    }


    /**
     * 插入商品推荐
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addProdcutRecommentService(CmsWindowData addCmsWindowData, String proSkuIds,
            String productIds) throws Exception {
        boolean flg = true;
        try {
            // CmsWindowData newCmsWindowData=new CmsWindowData();
            String proSkuIdsArray[] = proSkuIds.split(",");
            String proIdsArray[] = productIds.split(",");
            for (int i = 0; i < proSkuIdsArray.length; i++) {
                addCmsWindowData.setProductSkuId(Integer.parseInt(proSkuIdsArray[i]));// skuId
                addCmsWindowData.setDataId(Integer.parseInt(proIdsArray[i]));// 产品id
                List winList = cmsWindowDataDAO.queryWindowDataByIdAndSkuId(addCmsWindowData);// 根据Window_Id和skuid查询数据
                if (winList.size() == 0) {// 如果不存在就插入到数据库
                    cmsWindowDataDAO.insert(addCmsWindowData);
                }
            }
        } catch (Exception e) {
            flg = false;
            logger.error("给窗口添加产品出现异常！" + e.getMessage(), e);
            throw e;
        }
        return flg;
    }

    /**
     * 根据主键删除窗口数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delProdcutRecommentService(String dwindowDataIds) throws Exception {
        boolean flg = true;
        try {
            String dwindowDataIdArray[] = dwindowDataIds.split(",");
            for (int i = 0; i < dwindowDataIdArray.length; i++) {
                cmsWindowDataDAO.deleteByPrimaryKey(Integer.parseInt(dwindowDataIdArray[i]));
            }
        } catch (Exception e) {
            flg = false;
            logger.error("删除窗口产品数据出现异常！" + e.getMessage(), e);
            throw e;
        }
        return flg;
    }

    /**
     * 修改窗口数据排序
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSorts(List<CmsWindowData> updateSor, CmsWindowData cmsWindowDataBe)
            throws Exception {
        boolean flg = true;
        try {

            if (cmsWindowDataBe != null) {
                CmsWindowData newUpdateDeName = new CmsWindowData();
                List<CmsWindowData> cmsWindowDataSelectList =
                        cmsWindowDataDAO.queryByWindIdAndUserDefinedName(cmsWindowDataBe);
                if (CollectionUtils.isNotEmpty(cmsWindowDataSelectList)) {
                    if ((!cmsWindowDataBe.getUser_defined_name().isEmpty())
                            && null != cmsWindowDataBe.getUser_defined_name()) {
                        cmsWindowDataDAO.insert(cmsWindowDataBe);
                    }
                } else {
                    newUpdateDeName
                            .setWindowDataId(cmsWindowDataSelectList.get(0).getWindowDataId());
                    newUpdateDeName.setUser_defined_name(cmsWindowDataBe.getUser_defined_name());
                    newUpdateDeName.setStatus(cmsWindowDataBe.getStatus());
                    cmsWindowDataDAO.updateuserDefinedNameByPrimaryKeySelective(newUpdateDeName);
                }
            }

            if (CollectionUtils.isNotEmpty(updateSor)) {
                CmsWindowData newWind = new CmsWindowData();
                for (int i = 0; i < updateSor.size(); i++) {
                    newWind.setWindowDataId(updateSor.get(i).getWindowDataId());
                    newWind.setSort(updateSor.get(i).getSort());
                    cmsWindowDataDAO.updateSortByPrimaryKeySelective(newWind);
                }
            }

        } catch (Exception e) {
            flg = false;
            logger.error("修改窗口产品数据排序出现异常！" + e.getMessage(), e);
            throw e;
        }
        return flg;
    }

    /**
     * 根据窗口id查询信息
     */
    @Override
    public List<CmsWindowData> selectUserDefinedNameByWindId(Integer windId) throws Exception {
        List<CmsWindowData> cmswinList = null;
        try {
            CmsWindowData cmsWindowDataBe = new CmsWindowData();
            cmsWindowDataBe.setWindowId(windId);
            cmswinList = cmsWindowDataDAO.queryUserDefinedNameByWindId(cmsWindowDataBe);
            // if(!cmswinList.isEmpty() && null!=cmswinList){
            // userDefinedName=cmswinList.get(0).getUser_defined_name();
            // }

        } catch (Exception e) {
            logger.error("根据窗口id查询数据出现异常！" + e.getMessage(), e);
            throw e;
        }
        return cmswinList;
    }

    @Override
    public Integer addCmsWindowShopData(List<CmsWindowData> cmsWindowDatas, Integer windowId,
            Integer created, Integer siteId) throws Exception {
        int res = 0;
        CmsWindowData cmsWindowData = null;
        Integer sortMax = 0;
        for (int i = 0; i < cmsWindowDatas.size(); i++) {
            // 获得单个CmsWindowData对象
            cmsWindowData = cmsWindowDatas.get(i);
            if (cmsWindowData != null) {
                if (sortMax == null) {
                    sortMax = 0;


                }
                ++sortMax;

                cmsWindowData.setSort(sortMax);
                cmsWindowData.setUser_defined_url(cmsWindowData.getUser_defined_url());

                // 设置属性
                cmsWindowData.setDataType(6);
                cmsWindowData.setWindowId(windowId);
                cmsWindowData.setCreated(created);
                cmsWindowData.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
                cmsWindowData.setSiteId(siteId);

                // 保存数据
                this.cmsWindowDataDAO.addCmsWindowData(cmsWindowData);
            }
        }
        return res;
    }

    @Override
    public Integer updateCmsWindowShopData(CmsWindowData cmsWindowData) throws Exception {
        // TODO Auto-generated method stub
        // 取得上传路径
        // String imagePath = ConfigurationUtil.getString("imagePut");
        CmsWindowData data =
                this.cmsWindowDataDAO.selectByPrimaryKey(cmsWindowData.getWindowDataId());
        // 设置修改的属性
        data.setUser_defined_name(cmsWindowData.getUser_defined_name());
        data.setSort(cmsWindowData.getSort());
        data.setUser_defined_type(cmsWindowData.getUser_defined_type());
        data.setUser_defined_url(cmsWindowData.getUser_defined_url());
        data.setRemark(cmsWindowData.getRemark());
        data.setModified(cmsWindowData.getModified());
        data.setModifyDate(cmsWindowData.getModifyDate());
        return this.cmsWindowDataDAO.updateByPrimaryKey(data);
    }

    @Override
    public void addCmsWindowData(CmsWindowData cmsWindowData) throws SQLException {
        this.cmsWindowDataDAO.addCmsWindowData(cmsWindowData);

    }

    public ShopCategorysService getShopCategorysService() {
        return shopCategorysService;
    }

    public void setShopCategorysService(ShopCategorysService shopCategorysService) {
        this.shopCategorysService = shopCategorysService;
    }

    public ShopCategoryDao getShopCategoryDao() {
        return shopCategoryDao;
    }

    public void setShopCategoryDao(ShopCategoryDao shopCategoryDao) {
        this.shopCategoryDao = shopCategoryDao;
    }

    public CmsAdvService getCmsAdvService() {
        return cmsAdvService;
    }

    public void setCmsAdvService(CmsAdvService cmsAdvService) {
        this.cmsAdvService = cmsAdvService;
    }

    @Override
    public List categorySub(Map map, List list) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }


}
