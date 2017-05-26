package com.pltfm.cms.parse;

import com.pltfm.app.service.ViewProductInfoService;
import com.pltfm.app.vobject.ViewProductInfo;
import com.pltfm.app.vobject.ViewProductSku;
import com.pltfm.app.vobject.ViewPromotion;
import com.pltfm.cms.service.CmsPageWindowService;
import com.pltfm.cms.service.CmsSiteService;
import com.pltfm.cms.service.CmsWindowDataService;
import com.pltfm.cms.vobject.CmsPage;
import com.pltfm.cms.vobject.CmsPageWindowQry;
import com.pltfm.cms.vobject.CmsWindow;
import com.pltfm.cms.vobject.CmsWindowData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * 实现发布引擎获取数据的接口
 *
 * @author river
 */
@Component(value = "dataFetcher")
public class DataFetcherImpl implements DataFetcher {
    //窗口
    @Resource(name = "cmsPageWindowService")
    private CmsPageWindowService cmsPageWindowService;
    //窗口数据
    @Resource(name = "cmsWindowDataService")
    private CmsWindowDataService cmsWindowDataService;

    @Resource(name = "viewProductInfoService")
    private ViewProductInfoService viewProductInfoService;

    @Resource(name = "cmsSiteService")
    private CmsSiteService cmsSiteService;
    //日志
    private static Logger logger = LoggerFactory.getLogger(DataFetcherImpl.class);


    /**
     * 获取窗口中的产品数据(对应的数据类型为0)
     */
    public List<Object> getProducts(CmsWindow window) {

        CmsWindowData cmsWindowDate = new CmsWindowData();
        cmsWindowDate.setWindowId(window.getWindowId());
        cmsWindowDate.setDataType(0);
        List list = null;
        try {
            list = cmsWindowDataService.queryWindowDataList(cmsWindowDate);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("DataFetcherImpl.getProducts报错：" + e);
            return null;
        }
        return list;
    }


    /**
     * 获取窗口中的子窗口(对应的数据类型为4)(CmsWindow)
     */
    public List<Object> getSubWindow(CmsWindow window) {
        CmsWindowData cmsWindowDate = new CmsWindowData();
        cmsWindowDate.setWindowId(window.getWindowId());
        cmsWindowDate.setDataType(4);
        List list = null;
        try {
            list = cmsWindowDataService.queryWindowDataList(cmsWindowDate);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("DataFetcherImpl.getSubWindow报错：" + e);
            return null;
        }
        return list;
    }

    /**
     * 获取窗口中的活动(对应的数据类型为1)(ViewPromotion)
     */
    public List<Object> getActivity(CmsWindow window) {
        CmsWindowData cmsWindowDate = new CmsWindowData();
        cmsWindowDate.setWindowId(window.getWindowId());
        cmsWindowDate.setDataType(1);
        List list = null;
        try {
            list = cmsWindowDataService.queryWindowDataList(cmsWindowDate);
        } catch (Exception e) {
            logger.error("DataFetcherImpl.getActivity异常：" + e.getMessage(), e);
            return null;
        }
        return list;

    }

    /**
     * 获取窗口中的类别(对应的数据类型为2)
     */
    public List<Object> getCategory(CmsWindow window) {
        CmsWindowData cmsWindowDate = new CmsWindowData();
        cmsWindowDate.setWindowId(window.getWindowId());
        cmsWindowDate.setDataType(2);
        List list = null;
        try {
            list = cmsWindowDataService.queryWindowDataList(cmsWindowDate);
        } catch (Exception e) {
        	logger.error("DataFetcherImpl.getCategory异常：" + e.getMessage(), e);

        }
        return list;
    }

    /**
     * 获取品牌(对应的数据类型为3 ProdBrand)
     */
    public List<Object> getBrand(CmsWindow window) {
        CmsWindowData cmsWindowDate = new CmsWindowData();
        cmsWindowDate.setWindowId(window.getWindowId());
        cmsWindowDate.setDataType(3);
        List list = null;
        try {
            list = cmsWindowDataService.queryWindowDataList(cmsWindowDate);
        } catch (Exception e) {
        	logger.error("DataFetcherImpl.getBrand异常：" + e.getMessage(), e);

        }
        return list;
    }

    /**
     * 获取页面内的窗口(CmsWindow)
     */
    public List<Object> getWindow(CmsPage page) {
        CmsPageWindowQry qry = new CmsPageWindowQry();
        qry.setPageName(page.getName());
        qry.setPageId(page.getPageId());
        List list = null;
        try {
            list = cmsPageWindowService.queryWindowList(qry);
        } catch (SQLException e) {
        	logger.error("DataFetcherImpl.getWindow异常：" + e.getMessage(), e);
        }
        return list;
    }

    /**
     * 获取窗口自定义类型
     */

    public List<Object> getWindowDataType(CmsWindow window) {
        CmsWindowData cmsWindowDate = new CmsWindowData();
        cmsWindowDate.setWindowId(window.getWindowId());
        cmsWindowDate.setDataType(6);
        List list = null;
        try {
            list = cmsWindowDataService.queryWindowDataList(cmsWindowDate);
        } catch (Exception e) {
        	logger.error("DataFetcherImpl.getWindowDataType异常：" + e.getMessage(), e);

        }
        return list;
    }

    /**
     * 获取某类目产品排行榜
     */
    public List<Object> getRankingData(CmsWindow window) {
        CmsWindowData cmsWindowData = new CmsWindowData();
        cmsWindowData.setWindowId(window.getWindowId());
        cmsWindowData.setDataType(7);
        cmsWindowData.setSiteId(window.getSiteId());
        List list = null;
        try {
            list = cmsWindowDataService.queryWindowDataList(cmsWindowData);
        } catch (Exception e) {
        	logger.error("DataFetcherImpl.getRankingData异常：" + e.getMessage(), e);
        }
        return list;
    }

    /**
     * 获取某咨询类别查询不同的咨询
     */
    public List<Object> getInformationData(CmsWindow window) {
        CmsWindowData cmsWindowData = new CmsWindowData();
        cmsWindowData.setWindowId(window.getWindowId());
        cmsWindowData.setDataType(8);
        List list = null;
        try {
            list = cmsWindowDataService.queryWindowDataList(cmsWindowData);
        } catch (Exception e) {
        	logger.error("DataFetcherImpl.getInformationData异常：" + e.getMessage(), e);
        }
        return list;
    }

    /**
     * 获取指定商品活动的所有商品
     */
    public Map<String, List> getAssignProductData(ViewPromotion promotion) {
        List list = null;
        Map<String, List> proMap = new HashMap<String, List>();
        ViewProductInfo viewProductInfo = new ViewProductInfo();
        try {
            viewProductInfo.setPromotionId(promotion.getPromotionId());
            //获取活动产品类目
            List<String> categoryList = viewProductInfoService.getCategory(viewProductInfo);
            for (String category : categoryList) {
                viewProductInfo.setCategory(category);
                list = viewProductInfoService.selProByViewProductInfo(viewProductInfo, promotion);
                if (category == null)
                    category = "";
                proMap.put(category, list);
            }
        } catch (Exception e) {
        	logger.error("DataFetcherImpl.getAssignProductData异常：" + e.getMessage(), e);
        }
        return proMap;
    }

    /**
     * 获取分类活动的商品
     */
    public Map<String, List> getCategoryProductData(ViewPromotion promotion) {
        List<ViewProductSku> list = null;
        Map<String, List> data = new HashMap<String, List>();
        List dataList = null;
        ViewProductInfo viewProductInfo = null;
        try {
//			int siteId=(Integer) ActionContext.getContext().getSession().get("siteId");
            viewProductInfo = new ViewProductInfo();
//			CmsSite cmsSite=this.cmsSiteService.selectByPrimaryKey(siteId);
//			if(null!=cmsSite)
//			{
//				viewProductInfo.setChannel(cmsSite.getEngName());
//			}
            //将FilterSql去掉前面和后面的,号，再加上()
            String sql = promotion.getProductFilterSql();
            sql = sql.substring(1, sql.length() - 1);
            sql = "(" + sql + ")";
            //设置属性查询
            viewProductInfo.setCategoryId(1);
            viewProductInfo.setProductFilterSql(sql);
            list = this.viewProductInfoService.selProByViewProductInfo(viewProductInfo, promotion);
            //将返回结果封装成MAP
            for (ViewProductSku product : list) {
                if (data.containsKey(product.getCategoryName())) {
                    data.get(product.getCategoryName()).add(product);
                } else {
                    dataList = new ArrayList();
                    dataList.add(product);
                    data.put(product.getCategoryName(), dataList);
                }
            }

        } catch (Exception e) {
        	logger.error("DataFetcherImpl.getCategoryProductData异常：" + e.getMessage(), e);
        }
        return data;
    }

    /**
     * 获取指定品牌活动的所有商品
     */
    public Map<String, List> getBrandProductData(ViewPromotion promotion) {
        List<ViewProductSku> list = null;
        List dataList = null;
        ViewProductInfo viewProductInfo = null;
        Map<String, List> data = new HashMap<String, List>();
        try {
//			int siteId=(Integer) ActionContext.getContext().getSession().get("siteId");
            viewProductInfo = new ViewProductInfo();
//			CmsSite cmsSite=this.cmsSiteService.selectByPrimaryKey(siteId);
//			if(null!=cmsSite)
//			{
//				viewProductInfo.setChannel(cmsSite.getEngName());
//			}
            //查分FilterSql
            String sql = promotion.getProductFilterSql();
            sql = sql.substring(1, sql.length() - 1);
            sql = "(" + sql + ")";
            //设置属并查询
            viewProductInfo.setBrandId(1);
            viewProductInfo.setProductFilterSql(sql);
            list = this.viewProductInfoService.selProByViewProductInfo(viewProductInfo, promotion);
            //将返回结果封装成MAP
            for (ViewProductSku product : list) {
                if (data.containsKey(product.getBrandName())) {
                    data.get(product.getBrandName()).add(product);
                } else {
                    dataList = new ArrayList();
                    dataList.add(product);
                    data.put(product.getBrandName(), dataList);
                }
            }
        } catch (Exception e) {
        	logger.error("DataFetcherImpl.getBrandProductData异常：" + e.getMessage(), e);
        }
        return data;
    }

    /**
     * 获取指定商家活动的所有商品
     */
    public Map<String, List> getShopProductData(ViewPromotion promotion) {
        List<ViewProductSku> list = null;
        List dataList = null;
        ViewProductInfo viewProductInfo = null;
        Map<String, List> data = new HashMap<String, List>();
        try {
            //设置属性查询
//			int siteId=(Integer) ActionContext.getContext().getSession().get("siteId");
            viewProductInfo = new ViewProductInfo();
//			CmsSite cmsSite=this.cmsSiteService.selectByPrimaryKey(siteId);
//			if(null!=cmsSite)
//			{
//				viewProductInfo.setChannel(cmsSite.getEngName());
//			}
            String sql = promotion.getProductFilterSql();
            sql = sql.substring(1, sql.length() - 1);
            sql = "('" + sql + "')";
            viewProductInfo.setShopCode(sql);
            list = this.viewProductInfoService.selProByViewProductInfo(viewProductInfo, promotion);
            //将结果封装成map集合
            for (ViewProductSku product : list) {
                if (data.containsKey(product.getCategoryName())) {
                    data.get(product.getCategoryName()).add(product);
                } else {
                    dataList = new ArrayList();
                    dataList.add(product);
                    data.put(product.getCategoryName(), dataList);
                }
            }
        } catch (Exception e) {
        	logger.error("DataFetcherImpl.getShopProductData异常：" + e.getMessage(), e);
        }
        return data;
    }

    /**
     * 获取窗口抽奖活动
     */

    public List<Object> getLotteryLuckDraw(CmsWindow window) {
        CmsWindowData cmsWindowDate = new CmsWindowData();
        cmsWindowDate.setWindowId(window.getWindowId());
        cmsWindowDate.setDataType(9);
        List list = null;
        try {
            list = cmsWindowDataService.queryWindowDataList(cmsWindowDate);
        } catch (Exception e) {
        	logger.error("DataFetcherImpl.getLotteryLuckDraw异常：" + e.getMessage(), e);

        }
        return list;
    }

    /**
     * 获取窗口奖项奖品
     */

    public List<Object> getLotteryPrize(CmsWindow window) {
        CmsWindowData cmsWindowDate = new CmsWindowData();
        cmsWindowDate.setWindowId(window.getWindowId());
        cmsWindowDate.setDataType(10);
        List list = null;
        try {
            list = cmsWindowDataService.queryWindowDataList(cmsWindowDate);
        } catch (Exception e) {
        	logger.error("DataFetcherImpl.getLotteryPrize异常：" + e.getMessage(), e);

        }
        return list;
    }

    /**
     * 获取供应商信息
     */

    public List<Object> getShopMain(CmsWindow window) {
        CmsWindowData cmsWindowDate = new CmsWindowData();
        cmsWindowDate.setWindowId(window.getWindowId());
        cmsWindowDate.setDataType(11);
        List list = null;
        try {
            list = cmsWindowDataService.queryWindowDataList(cmsWindowDate);
        } catch (Exception e) {
        	logger.error("DataFetcherImpl.getShopMain异常：" + e.getMessage(), e);

        }
        return list;
    }

    /**
     * 获取cms关联供应商信息
     */
    public List<Object> getCmsShopData(CmsWindow window) {
        CmsWindowData cmsWindowDate = new CmsWindowData();
        cmsWindowDate.setWindowId(window.getWindowId());
        cmsWindowDate.setDataType(12);
        List list = null;
        try {
            list = cmsWindowDataService.queryWindowDataList(cmsWindowDate);
        } catch (Exception e) {
        	logger.error("DataFetcherImpl.getCmsShopData异常：" + e.getMessage(), e);

        }
        return list;
    }

    /**
     * 供应商店铺类目
     */
    public List<Object> getShopCategory(CmsWindow window) {
        CmsWindowData cmsWindowDate = new CmsWindowData();
        cmsWindowDate.setWindowId(window.getWindowId());
        cmsWindowDate.setDataType(13);
        List list = null;
        try {
            list = cmsWindowDataService.queryWindowDataList(cmsWindowDate);
        } catch (Exception e) {
        	logger.error("DataFetcherImpl.getShopCategory异常：" + e.getMessage(), e);

        }
        return list;
    }

    public CmsPageWindowService getCmsPageWindowService() {
        return cmsPageWindowService;
    }


    public void setCmsPageWindowService(CmsPageWindowService cmsPageWindowService) {
        this.cmsPageWindowService = cmsPageWindowService;
    }


    public CmsWindowDataService getCmsWindowDataService() {
        return cmsWindowDataService;
    }


    public void setCmsWindowDataService(CmsWindowDataService cmsWindowDataService) {
        this.cmsWindowDataService = cmsWindowDataService;
    }


    public ViewProductInfoService getViewProductInfoService() {
        return viewProductInfoService;
    }


    public void setViewProductInfoService(
            ViewProductInfoService viewProductInfoService) {
        this.viewProductInfoService = viewProductInfoService;
    }


    public CmsSiteService getCmsSiteService() {
        return cmsSiteService;
    }


    public void setCmsSiteService(CmsSiteService cmsSiteService) {
        this.cmsSiteService = cmsSiteService;
    }


}
