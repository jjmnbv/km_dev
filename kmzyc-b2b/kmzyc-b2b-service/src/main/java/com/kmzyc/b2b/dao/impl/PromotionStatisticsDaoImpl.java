package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.b2b.dao.PromotionStatisticsDao;
import com.kmzyc.b2b.vo.CpsTrackInfo;
import com.kmzyc.b2b.vo.RequestInfo;
import com.kmzyc.framework.exception.ServiceException;
import com.km.framework.persistence.impl.DaoImpl;
import com.pltfm.app.entities.OrderOutSideExtInfo;

@SuppressWarnings("unchecked")
@Component("promotionStatisticsDao")
public class PromotionStatisticsDaoImpl extends DaoImpl implements PromotionStatisticsDao {
  @Resource
  private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;

  /**
   * 根据标识查询请求次数
   * 
   * @param flag
   * @return
   */
  public Integer queryRequestCount(RequestInfo requestInfo) throws SQLException {
    return (Integer) sqlMapClient.queryForObject("PromotionStatistics.QUERY_REQUEST_COUNT",
        requestInfo);
  }

  /**
   * 根据下单时间查询某时间段内创建的订单数据
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  public List<Map> queryOrderInfoByCreateDate(Map<String, Object> params) throws SQLException {
    return sqlMapClient.queryForList("PromotionStatistics.QUERY_ORDERINFO_BY_CREATEDATE", params);
  }

  /**
   * 根据更新时间查询某时间段内更新过的订单信息
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  public List<Map> queryOrderInfoByUpdateDate(Map<String, Object> params) throws SQLException {
    return sqlMapClient.queryForList("PromotionStatistics.QUERY_ORDERINFO_BY_UPDATEDATE", params);
  }

  /**
   * 根据订单号查询订单明细
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public List<Map> queryOrderDetailInfoByOrderCode(String orderCode) throws SQLException {
    return sqlMapClient.queryForList("PromotionStatistics.QUERY_ORDERD_ETAILINFO_BY_ORDERCODE",
        orderCode);
  }
  /**
   * 根据订单号查询订单明细,并根据产品编号对价格和数量进行合并 add by songmiao 2016-3-4
   * 
   * @param orderCode
   * @return
   * @throws SQLException
   */
  public List<Map> queryOrderDetailInfoByOrderCodeAndProduct(String orderCode) throws SQLException {
    return sqlMapClient.queryForList("PromotionStatistics.QUERY_ORDERD_ETAILINFO_BY_ORDERCODE_PRODUCTNO",
        orderCode);
  }
  /**
   * 新增数据接口查询信息
   * 
   * @param params
   * @return
   * @throws SQLException
   */
  public int insertRequestInfo(RequestInfo requestInfo) throws SQLException {
    Long result =
        (Long) sqlMapClient.insert("PromotionStatistics.INSERT_REQUEST_INFO", requestInfo);
    return null == result ? 0 : result.intValue();
  }

  /**
   * 新增订单标识数据
   * 
   * @param orderInfo
   * @return
   * @throws ServiceException
   */
  public int insertOrderFlagInfo(OrderOutSideExtInfo orderInfo) throws SQLException {
    Long result =
        (Long) sqlMapClient.insert("PromotionStatistics.INSERT_ORDER_OUT_SIDE_EXT_INFO", orderInfo);
    return null == result ? 0 : result.intValue();
  }

  /**
   * 新增cps跳转数据
   * 
   * @param cpsTrackInfo
   * @return
   * @throws ServiceException
   */
  public int insertCpsTrackInfo(CpsTrackInfo cpsTrackInfo) throws SQLException {
    Long result =
        (Long) sqlMapClient.insert("PromotionStatistics.INSERT_CPS_TRACK_INFO", cpsTrackInfo);
    return null == result ? 0 : result.intValue();
  }
}
