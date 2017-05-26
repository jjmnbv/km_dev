package com.pltfm.remote.service.impl;

import com.kmzyc.cms.remote.service.DetailPublishService;
import com.pltfm.app.dao.CategoryDAO;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.Category;
import com.pltfm.app.vobject.ViewProductInfo;
import com.pltfm.cms.parse.PagePublish;
import com.pltfm.cms.service.CmsPageService;
import com.pltfm.cms.service.CmsSiteService;
import com.pltfm.cms.service.CmsWindowDataService;
import com.pltfm.cms.util.SqlJoinUtil;
import com.pltfm.cms.vobject.CmsPage;
import com.pltfm.cms.vobject.CmsSite;
import com.pltfm.cms.vobject.CmsWindowData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * 产品详细页面发布远程接口
 *
 * 提供给产品系统的接口(上下架，预览，草稿预览)
 *
 * @author gongyan
 * @since 2013-10-08
 */
@Component(value = "detailPublishService")
public class DetailPublishServiceImpl implements DetailPublishService {
    @Resource(name = "pagePublish")
    private PagePublish pagePublish;
    @Resource(name = "cmsPageService")
    private CmsPageService cmsPageService;
    @Resource(name = "cmsSiteService")
    private CmsSiteService cmsSiteService;
    @Resource(name = "cmsWindowDataService")
    private CmsWindowDataService cmsWindowDataService;
    @Resource(name = "categoryDAO")
    private CategoryDAO categoryDAO;

    //日志
    private static Logger logger = LoggerFactory.getLogger(DetailPublishServiceImpl.class);

    /**
     * 产品详细信息发布（模板一）
     *
     * @param productList 产品主键集合
     * @throws Exception 异常信息
     */
    public String detailPublish(List<Integer> productList) throws Exception {
        String returnValue = "";
        if (ListUtils.isNotEmpty(productList)) {
            for (Integer productId : productList) {
                ViewProductInfo info = new ViewProductInfo();
                info.setProductId(productId);
                //4详情页模板一
                returnValue = pagePublish.remotePublishDetailPage(info);
                logger.info("detailPublish产品详细信息发布成功productId:" + productId);
            }
        } else {
            returnValue = "fail";
            logger.error("detailPublish产品详细信息发布失败");
        }
        return returnValue;
    }


    /**
     * 预览正式产品详细
     *
     * @param productId 产品主键
     * @return 预览产品详细页面地址
     * @throws Exception 异常信息
     */
    public String viewDetailPublish(Integer productId) throws Exception {
        String returnValue = "";
        if (productId != null) {
            ViewProductInfo info = new ViewProductInfo();
            info.setProductId(productId);

            returnValue = pagePublish.remoteViewDetailPage(info);
            logger.info("viewDetailPublish产品系统调预览正式产品详细成功");
        }
        return returnValue;
    }


    /**
     * 预览草稿产品详细
     *
     * @param productId 产品主键
     * @return 预览产品详细页面地址
     * @throws Exception 异常信息
     */
    public String viewProductDraftPublish(Integer productId) throws Exception {
        String returnValue = "";
        if (productId != null) {
            ViewProductInfo info = new ViewProductInfo();
            info.setProductId(productId);
            returnValue = pagePublish.remoteViewProductDraftPublish(info);
            logger.info("viewProductDraftPublish产品系统调预览草稿产品详细成功");
        }
        return returnValue;
    }

    /**
     * 根据产品列表搜索所有需重新发布的页面("1.产品系统产品上下架时会自动触发/")
     *
     * @param productList<Integer> productList) Integer为产品主键
     * @throws Exception 异常信息
     */
    public String publishAllPage(List<Integer> productList) {
        if (ListUtils.isNotEmpty(productList)) {
            try {
                //所涉及到的详情页面
                detailPublish(productList);
                logger.info("publishAllPage产品系统上下架调详情页发布成功");
                return "success";
            } catch (Exception e) {
                logger.error("publishAllPage产品系统上下架调详情页发布报", e);
            }
        }
        return "fail";
    }


    //更新所有页面(1.活动全场打折自动触发)
    public void remotePublishAllPage(String sqlParam, List<Integer> cmsPageList) {
        pagePublish.remotePublishAllPage(null, cmsPageList);
    }

    //更新产品相关的页面(2.活动自动触发)
    public void remotePublishPartPage(List<Integer> productList) {
        if (ListUtils.isNotEmpty(productList)) {
            try {
                String sqlParam = SqlJoinUtil.listToString(productList);
                pagePublish.remotePublishAllPage(sqlParam, null);
                logger.info("促销系统MQ消息更新产品相关的页面成功");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                logger.error("促销系统MQ消息更新产品相关的页面报错", e);

            }
        }

    }


    /*
     * 所有页面
     */
    public List<Integer> findAllPage() {
        try {
            return cmsPageService.findAllPage();
        } catch (SQLException e) {
        	logger.error("DetailPublishServiceImpl.findAllPage异常：" + e.getMessage(), e);
        }
        return null;

    }


    //根据站点得到不同的类目(搜索系统获取物理类目)
    public List queryCategoryBySite(String engName) {
        List dataIdsList = null;
        List list = null;
        //	site=1;
        //1.相关渠道所有类目
        try {
            CmsSite site = new CmsSite();
            site.setEngName(engName);
            site = cmsSiteService.querySite(site);
            //所有绑定的数据集合
            dataIdsList = cmsWindowDataService.queryWindowDataBySite(site.getSiteId());


            List windDataList = new ArrayList();
            if (null != dataIdsList && dataIdsList.size() > 0) {
                for (int i = 0; i < dataIdsList.size(); i++) {
                    windDataList.add(((CmsWindowData) dataIdsList.get(i)).getDataId());

                }
            }
            //组装成商品对象
            list = categoryDAO.selectCategoryParent(SqlJoinUtil
                    .getSqlIn(windDataList, 1000, "CATEGORY_ID"));
            //组装成树形商品对象
            Map map = new LinkedHashMap();
            int count = 0;// 区别所有类目和一般类目
            if (null != list && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Category category = (Category) list.get(i);

                    map.put(category.getCategoryId(), category);
                }

                list = cmsWindowDataService.categorySub(map, windDataList);

            }

            //添加至缓存
            list = cmsWindowDataService.savaMemcached(site.getSiteId(), list);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("搜索系统获取物理类目报错", e);
        }
        return list;
    }

    /**
     * 根据页面Id发布页面
     *
     * @return 为true时这发布成功, 为false时这发布失败
     */
    @Override
    public boolean pageIdParse(Integer pageId) {
        boolean flag = false;
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageId(pageId);
        try {
            flag = pagePublish.parse(cmsPage);
        } catch (Exception e) {
        	logger.error("DetailPublishServiceImpl.pageIdParse异常：" + e.getMessage(), e);
        }
        return flag;
    }


    /**
     * 促销调用CMS预售详情页发布
     *
     * @param productList 产品SKUID
     * @return 为true时这发布成功, 为false时这发布失败
     */
    public boolean remotePublishYsPage(List<Long> productList) {


        if (ListUtils.isNotEmpty(productList)) {
            for (Long skuId : productList) {
                ViewProductInfo info = new ViewProductInfo();
                info.setStartProductId(skuId.intValue());
                info.setEndProductId(skuId.intValue());
                //30预售详情页模板一
                pagePublish.remotePublishYsPage(info);
                logger.info("促销调用CMS预售详情页发布成功skuId:" + skuId);

            }

            return true;
        } else {
            logger.error("促销调用CMS预售详情页发布失败");
            return false;
        }

    }

    /**
     * 促销调用CMS普通详情页发布
     *
     * @param productList 产品SKUID
     * @return 为true时这发布成功, 为false时这发布失败
     */
    public boolean remotePublishNormalPage(List<Long> productList) {
        if (ListUtils.isNotEmpty(productList)) {
            for (Long skuId : productList) {
                ViewProductInfo info = new ViewProductInfo();
                info.setStartProductId(skuId.intValue());
                info.setEndProductId(skuId.intValue());
                //基本详情页模板一
                pagePublish.remotePublishNormalPage(info);
                logger.info("促销调用CMS普通详情页发布成功skuId:" + skuId);

            }
            return true;
        } else {
            logger.error("促销调用CMS普通详情页发布失败");
            return false;
        }


    }


    public PagePublish getPagePublish() {
        return pagePublish;
    }

    public void setPagePublish(PagePublish pagePublish) {
        this.pagePublish = pagePublish;
    }

    public CmsPageService getCmsPageService() {
        return cmsPageService;
    }

    public void setCmsPageService(CmsPageService cmsPageService) {
        this.cmsPageService = cmsPageService;
    }

    public CmsWindowDataService getCmsWindowDataService() {
        return cmsWindowDataService;
    }

    public void setCmsWindowDataService(CmsWindowDataService cmsWindowDataService) {
        this.cmsWindowDataService = cmsWindowDataService;
    }

    public CmsSiteService getCmsSiteService() {
        return cmsSiteService;
    }

    public void setCmsSiteService(CmsSiteService cmsSiteService) {
        this.cmsSiteService = cmsSiteService;
    }

    public CategoryDAO getCategoryDAO() {
        return categoryDAO;
    }

    public void setCategoryDAO(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }


}
