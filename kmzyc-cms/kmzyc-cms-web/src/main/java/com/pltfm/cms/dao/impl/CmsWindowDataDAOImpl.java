package com.pltfm.cms.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.promotion.app.vobject.ProductSkuPriceCache;
import com.kmzyc.promotion.remote.service.PromotionRemoteService;
import com.pltfm.app.dao.BnesBrowsingHisDAO;
import com.pltfm.app.dao.OrderItemDAO;
import com.pltfm.app.dao.ProdAppraiseDAO;
import com.pltfm.app.vobject.ViewProductInfo;
import com.pltfm.cms.dao.CmsWindowDataDAO;
import com.pltfm.cms.vobject.CmsWindow;
import com.pltfm.cms.vobject.CmsWindowData;
import com.pltfm.cms.vobject.CmsWindowDataExample;
import com.pltfm.remote.service.impl.ViewProductInfoRemoteServiceImpl;

/**
 * 窗口数据DAO接口实现类
 *
 * @author cjm
 * @since 2013-9-3
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Component(value = "cmsWindowDataDAO")
public class CmsWindowDataDAOImpl implements CmsWindowDataDAO {
	private static Logger logger = LoggerFactory.getLogger(CmsWindowDataDAOImpl.class);
    /**
     * 数据库操作对象
     */
    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    /**
     * 客户浏览信息
     */
    @Resource(name = "bnesBrowsingHisDAO")
    private BnesBrowsingHisDAO bnesBrowsingHisDAO;

    @Autowired
    private PromotionRemoteService promotionRemoteService;

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

    /**
     * 添加窗口数据信息
     *
     * @param record 窗口数据信息实体
     * @throws SQLException sql异常
     * @return 返回值
     */
    @Override
    public Integer insert(CmsWindowData record) throws SQLException {
        Integer rows = (Integer) sqlMapClient.insert("CMS_WINDOW_DATA.abatorgenerated_insert", record);
        return rows;
    }

    /**
     * 修改窗口数据信息
     *
     * @param record 窗口数据信息实体
     * @throws SQLException sql异常
     * @return 返回值
     */
    @Override
    public int updateByPrimaryKey(CmsWindowData record) throws SQLException {
        int rows = sqlMapClient.update("CMS_WINDOW_DATA.abatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * 动态修改窗口数据信息
     *
     * @param record 窗口数据信息实体
     * @throws SQLException sql异常
     * @return 返回值
     */
    @Override
    public int updateByPrimaryKeySelective(CmsWindowData record) throws SQLException {
        int rows = sqlMapClient.update("CMS_WINDOW_DATA.abatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * 自定义动态修数据信息
     *
     * @param record 窗口数据信息实体
     * @throws SQLException sql异常
     * @return 返回值
     */
    @Override
    public int update(CmsWindowData record) throws SQLException {
        int rows = sqlMapClient.update("CMS_WINDOW_DATA.abatorgenerated_update", record);
        return rows;
    }


    /**
     * 按窗口对象和数据类型返回窗口数据集
     *
     * @param example 窗口数据信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    @Override
    public List queryWindowDataList(CmsWindowData cmsWindowData) throws SQLException {
        List list = sqlMapClient.queryForList("CMS_WINDOW_DATA.abatorgenerated_queryWindowData", cmsWindowData);
        return list;
    }

    /**
     * 按窗口对象和数据类型返回窗口数据集
     *
     * @param example 窗口数据信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    @Override
    public CmsWindowData queryWindowData(CmsWindowData cmsWindowData) throws SQLException {
        CmsWindowData record = (CmsWindowData) sqlMapClient.queryForObject("CMS_WINDOW_DATA.abatorgenerated_queryWindowData", cmsWindowData);
        return record;
    }

    /**
     * 根据窗口对象返回窗口绑定数据类型
     *
     * @param example 窗口数据信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    @Override
    public List queryWindowDataType(CmsWindow window) throws SQLException {
        List list = sqlMapClient.queryForList("CMS_WINDOW_DATA.abatorgenerated_queryWindowDataType", window);
        return list;
    }


    /**
     * 按窗口数据信息条件查询
     *
     * @param example 窗口数据信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    @Override
    public List selectByExample(CmsWindowDataExample example) throws SQLException {
        List list = sqlMapClient.queryForList("CMS_WINDOW_DATA.abatorgenerated_selectByExample", example);
        return list;
    }

    @Override
    public Integer maxSortByExample(CmsWindowDataExample example) throws SQLException {
        Integer maxSort=(Integer) sqlMapClient.queryForObject("CMS_WINDOW_DATA.maxSortByExample",example);
        return maxSort!=null?maxSort:0;
    }

    @Override
    public CmsWindowData queryWindowDataId(CmsWindowData cmsWindowData) throws SQLException {
        CmsWindowData record = (CmsWindowData) sqlMapClient.queryForObject("CMS_WINDOW_DATA.abatorgenerated_queryWindowDataId", cmsWindowData);
        return record;
    }

    /**
     * 根据窗口数据主键查询单条账户基本信息
     *
     * @param windowDataId 窗口数据主键
     * @throws SQLException sql异常
     * @return 返回值
     */
    @Override
    public CmsWindowData selectByPrimaryKey(Integer windowDataId) throws SQLException {
        CmsWindowData key = new CmsWindowData();
        key.setWindowDataId(windowDataId);
        CmsWindowData record = (CmsWindowData) sqlMapClient.queryForObject("CMS_WINDOW_DATA.abatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * 按窗口数据信息条件进行删除
     *
     * @param example 窗口数据信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    @Override
    public int deleteByExample(CmsWindowDataExample example) throws SQLException {
        int rows = sqlMapClient.delete("CMS_WINDOW_DATA.abatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * 根据窗口数据主键删除窗口数据信息
     *
     * @param windowDataId 窗口数据主键
     * @throws SQLException sql异常
     * @return 返回值
     */
    @Override
    public int deleteByPrimaryKey(Integer windowDataId) throws SQLException {
        CmsWindowData key = new CmsWindowData();
        key.setWindowDataId(windowDataId);
        int rows = sqlMapClient.delete("CMS_WINDOW_DATA.abatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * 按窗口数据信息条件查询总条数
     *
     * @param example 窗口数据信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    @Override
    public int countByExample(CmsWindowDataExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("CMS_WINDOW_DATA.abatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * 动态按窗口数据信息条件进行修改
     *
     * @param record  窗口数据信息实体
     * @param example 窗口数据信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    @Override
    public int updateByExampleSelective(CmsWindowData record, CmsWindowDataExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("CMS_WINDOW_DATA.abatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * 按窗口数据信息按条件进行修改
     *
     * @param record  窗口数据信息实体
     * @param example 窗口数据信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    @Override
    public int updateByExample(CmsWindowData record, CmsWindowDataExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("CMS_WINDOW_DATA.abatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * 窗口数据信息总数量
     *
     * @param cmsWindowData 窗口数据信息实体
     * @throws SQLException 异常
     * @return 返回值
     */
    @Override
    public Integer countByCmsWindowData(CmsWindowData cmsWindowData)
            throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject(
                "CMS_WINDOW_DATA.abatorgenerated_countByWindowData", cmsWindowData);
        return count;
    }


    /**
     * 分页查询窗口数据信息
     *
     * @param cmsWindowData 窗口数据信息实体
     * @throws SQLException 异常
     * @return 返回值
     */
    @Override
    public List queryForPage(CmsWindowData cmsWindowData) throws SQLException {
        return sqlMapClient.queryForList("CMS_WINDOW_DATA.abatorgenerated_pageByWindowData", cmsWindowData);
    }

    /**
     * 按实体条件修改参数类
     *
     * @author cjm
     * @since 2013-9-3
     */
    private static class UpdateByExampleParms extends CmsWindowDataExample {
        private Object record;

        public UpdateByExampleParms(Object record, CmsWindowDataExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    /**
     * 返回子窗口对象
     *
     * @param cmsWindowData 窗口数据信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    @Override
    public List querySubWindowDataList(CmsWindowData cmsWindowData) throws SQLException {
        List list = sqlMapClient.queryForList("CMS_WINDOW_DATA.abatorgenerated_querySubWindowData", cmsWindowData);
//        CmsWindowDataExample e = new CmsWindowDataExample();
        return list;
    }

    /**
     * 返回活动对象
     *
     * @param cmsWindowData 窗口数据信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    public List queryPromotionDataList(CmsWindowData cmsWindowData) throws SQLException {
        List list = sqlMapClient.queryForList("CMS_WINDOW_DATA.abatorgenerated_querySubWindowDate", cmsWindowData);
        return list;
    }

    @Override
    public int deleteByCmsWindowData(CmsWindowData cmsWindowData)
            throws SQLException {
        // TODO Auto-generated method stub
        return sqlMapClient.delete("CMS_WINDOW_DATA.abatorgenerated_deleteByWindowData", cmsWindowData);
    }

    @Override
    public Integer addCmsWindowData(CmsWindowData cmsWindowData)
            throws SQLException {
        // TODO Auto-generated method stub
        return (Integer) sqlMapClient.insert("CMS_WINDOW_DATA.abatorgenerated_addcmsWindowData", cmsWindowData);
    }


    //根据数据类型号和产品ID,返回所有涉及到的页面列表
    @Override
    public List queryAllPage(String sqlParam) throws SQLException {
        return sqlMapClient.queryForList("CMS_WINDOW_DATA.abatorgenerated_queryAllPage", sqlParam);
    }

    //根据站点返回窗口数据集
    @Override
    public List queryWindowDataBySite(Integer site) throws SQLException {
        return sqlMapClient.queryForList("CMS_WINDOW_DATA.abatorgenerated_queryWindowDataBySite", site);
    }

    //根据skuID和窗口id查询数据
    @Override
    public List queryWindowDataByIdAndSkuId(CmsWindowData cmsWindowData) throws SQLException {
        return sqlMapClient.queryForList("CMS_WINDOW_DATA.abatorgenerated_selectByWindowAndSkuId", cmsWindowData);
    }

    /**
     * 修改排序窗口数据信息
     *
     * @param record 窗口数据信息实体
     * @throws SQLException sql异常
     * @return 返回值
     */
    @Override
    public int updateSortByPrimaryKeySelective(CmsWindowData record) throws SQLException {
        int rows = sqlMapClient.update("CMS_WINDOW_DATA.sort_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * 修改标签名称
     *
     * @param record 窗口数据信息实体
     * @throws SQLException sql异常
     * @return 返回值
     */
    @Override
    public int updateuserDefinedNameByPrimaryKeySelective(CmsWindowData record) throws SQLException {
        int row = sqlMapClient.update("CMS_WINDOW_DATA.userDefinedName_updateByPrimaryKey", record);
        return row;
    }

    @Override
    public List queryUserDefinedNameByWindId(CmsWindowData cmsWindowData) throws SQLException {
        return sqlMapClient.queryForList("CMS_WINDOW_DATA.selectUserDefinedNameByWindId", cmsWindowData);
    }

    @Override
    public List queryByWindIdAndUserDefinedName(CmsWindowData cmsWindowData) throws SQLException {
        return sqlMapClient.queryForList("CMS_WINDOW_DATA.queryByWindIdAndUserDefinedName", cmsWindowData);
    }

    @Override
    public void updateSearchCategory(Map map) throws SQLException {
        sqlMapClient.queryForObject("CMS_WINDOW_DATA.abatorgenerated_addcmsWindowDataToSearchCategory", map);
    }

    /**
     * 更新价格
     */
    @Override
    public List updateMoney(List<ViewProductInfo> productList) {

		/*PromotionRemoteService promotionRemoteService = null;
        try {
			promotionRemoteService = (PromotionRemoteService) RemoteTool
					.getRemote(Constants.PROMOTION_SYSTEM_CODE, "promotionRemoteService");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}*/

        for (ViewProductInfo view : productList) {
            if (view.getProductTags() == null) {
                view.setProductTags(new ArrayList<String>());
            }
            try {
                ProductSkuPriceCache psp = new ProductSkuPriceCache();

                // psp.setProductSkuId(Long.valueOf(view.getProductSkuId()));
                psp.setProductSkuId(Long.valueOf(view.getProductSkuId()));
                psp = promotionRemoteService.getCurrentTimeProductSkuPrice(psp);

                if (null != psp.getPromotionPrice()
                        && psp.getPromotionPrice() > 0) {
                    view.setPrice(psp.getPromotionPrice());
                }

                String[] lebal = null;
                lebal = psp.getPromotionInfoLebal();
                if (lebal != null) {
                    for (int i = 0; i < lebal.length; i++) {

                        view.getProductTags().add(lebal[i]);
                    }
                }
            } catch (Exception e) {
                logger.error("CmsWindowDataDAOImpl.updateMoney异常：" + e.getMessage(), e);
            }

            if (view.getProductAttrList().size() > 0) {
                for (int k = 0; k < view.getProductAttrList().size(); k++) {
                    if ("清仓".equals(view.getProductAttrList().get(k)
                            .getProductAttrName())) {
                        view.getProductTags().add(
                                view.getProductAttrList().get(k)
                                        .getProductAttrName());
                    } else if ("药师推荐".equals(view.getProductAttrList().get(k)
                            .getProductAttrName())) {
                        view.getProductTags().add(
                                view.getProductAttrList().get(k)
                                        .getProductAttrName());
                    } else if ("春季适用".equals(view.getProductAttrList().get(k)
                            .getProductAttrName())) {
                        if (ViewProductInfoRemoteServiceImpl.isSpring()) {
                            view.getProductTags().add("当季");
                        }
                    } else if ("夏季适用".equals(view.getProductAttrList().get(k)
                            .getProductAttrName())) {
                        if (ViewProductInfoRemoteServiceImpl.isSummer()) {
                            view.getProductTags().add("当季");
                        }
                    } else if ("秋季适用".equals(view.getProductAttrList().get(k)
                            .getProductAttrName())) {
                        if (ViewProductInfoRemoteServiceImpl.isAutumn()) {
                            view.getProductTags().add("当季");
                        }
                    } else if ("冬季适用".equals(view.getProductAttrList().get(k)
                            .getProductAttrName())) {
                        if (ViewProductInfoRemoteServiceImpl.isWinter()) {
                            view.getProductTags().add("当季");
                        }
                    } else if ("新品".equals(view.getProductAttrList().get(k)
                            .getProductAttrName())) {
                        view.getProductTags().add("新品");
                    }

                }
            }

            Map skuIdAndChannle = new HashMap();
            skuIdAndChannle.put("productSkuId", view.getProductSkuId());
            // skuIdAndChannle.put("channel", "B2B" /*view.getChannel()*/);

            Map skuCodeAndChannle = new HashMap();
            skuCodeAndChannle.put("skuCode", view.getProductSkuCode());
            // skuCodeAndChannle.put("channel", "B2B" /*view.getChannel()*/ );

            Integer five;

            try {
                five = prodAppraiseDAO.fiveStars(skuIdAndChannle);

                if (null != five) {
                    view.getProductTags().add("五星");
                }
            } catch (SQLException e) {
                logger.error("CmsWindowDataDAOImpl.updateMoney异常：" + e.getMessage(), e);
            }

            String code = null;
            try {
                code = orderItemDAO.count(skuCodeAndChannle);
            } catch (SQLException e) {
                logger.error("CmsWindowDataDAOImpl.updateMoney异常：" + e.getMessage(), e);
            }
            if (null != code) {
                view.getProductTags().add("热卖");
            }

            Integer count;
            try {
                count = prodAppraiseDAO.count(skuIdAndChannle);

                if (null != count) {
                    view.getProductTags().add("热评");
                }
            } catch (SQLException e) {
                logger.error("CmsWindowDataDAOImpl.updateMoney异常：" + e.getMessage(), e);
            }
            String human = null;
            try {
                human = bnesBrowsingHisDAO.count(view.getProductSkuCode());
            } catch (SQLException e) {
                logger.error("CmsWindowDataDAOImpl.updateMoney异常：" + e.getMessage(), e);
            }
            if (null != human) {
                view.getProductTags().add("人气");
            }

            if (view.getProductTags().size() > 0) {
                if ("满额减免".equals(view.getProductTags().get(0).trim())) {
                    view.getProductTags().remove(0);
                    view.getProductTags().add(0, "满减");
                }
                if ("满额送券".equals(view.getProductTags().get(0).trim())) {
                    view.getProductTags().remove(0);
                    view.getProductTags().add(0, "送券");
                }

                if ("药师推荐".equals(view.getProductTags().get(0).trim())) {
                    view.getProductTags().remove(0);
                    view.getProductTags().add(0, "推荐");
                }
            }

        }

        return productList;
    }
}
