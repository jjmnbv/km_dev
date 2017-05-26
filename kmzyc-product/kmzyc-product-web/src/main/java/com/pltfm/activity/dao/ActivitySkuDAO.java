package com.pltfm.activity.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public interface ActivitySkuDAO {
  /**
   * 活动商品列表详情（暂时不需要分页，查全部）  
   * @param supplierEntryId
   * @return
   * @throws SQLException
   */
  List activityProductList(Long supplierEntryId) throws SQLException;
  
  /**
   * 活动报名产品记录条数
   * @param Map
   * @return
   * @throws SQLException
   */
  int activityEntryProductCount(Map map) throws SQLException;
  
  /**
   * 活动报名产品列表
   * @param Map
   * @param skip
   * @param max
   * @return
   * @throws SQLException
   */
  List activityEntryProductList(Map map,int skip,int max) throws SQLException;
  
  /**
   * 活动报名产品导出列表(过滤之后，不算分页)
   * @param Map
   * @param skip
   * @param max
   * @return
   * @throws SQLException
   */
  List exportActivitySupplierProductList(Map map) throws SQLException;
  
  /**
   * 修改渠道报名审核通过产品在促销系统生成促销活动（activity_sku表促销id为返回活动促销id）
   *
   * @param promotionId     促销活动id
   * @param supplierEntryId 报名id          
   * @return
   * @throws SQLException
   */
  int batchUpdateActivitySkuByEntryId(Map map) throws SQLException;
  
  /**
   * 查询是否还有商品在还未结束的促销推广活动中
   *
   * @param condition
   * @return
   * @throws SQLException
   */
  List<String> getSkuInUnfinishedActivity(Map<String, Object> condition) throws SQLException;
  
}