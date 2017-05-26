package com.kmzyc.b2b.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.PromotionStatisticsDao;
import com.kmzyc.b2b.service.PromotionStatisticsService;
import com.kmzyc.b2b.vo.CpsTrackInfo;
import com.kmzyc.b2b.vo.RequestInfo;
import com.kmzyc.framework.exception.ServiceException;
import com.pltfm.app.entities.OrderOutSideExtInfo;

/**
 * 推广统计Service
 * 
 * @author xiongliguang
 * 
 */
@SuppressWarnings("unchecked")
@Service("promotionStatisticsService")
public class PromotionStatisticsServiceImpl implements PromotionStatisticsService {
    @Resource(name = "promotionStatisticsDao")
    private PromotionStatisticsDao promotionStatisticsDao;

    /**
     * 根据标识查询请求次数
     * 
     * @param requestInfo
     * @return
     */
    public Integer queryRequestCount(RequestInfo requestInfo) throws ServiceException {

        Integer rs;
        try {
            rs = promotionStatisticsDao.queryRequestCount(requestInfo);
        } catch (Exception e) {
            throw new ServiceException(0, "根据标识查询请求次数发生异常，标识：" + requestInfo, e);
        }
        return rs;
    }

    /**
     * 根据下单时间查询某时间段内创建的订单数据
     * 
     * @param params
     * @return
     * @throws SQLException
     */
    public List<Map> queryOrderInfoByCreateDate(Map<String, Object> params) throws ServiceException {

        List<Map> list;
        try {
            list = promotionStatisticsDao.queryOrderInfoByCreateDate(params);
        } catch (Exception e) {
            throw new ServiceException(0, "根据下单时间查询某时间段内创建的订单数据发生异常，参数：" + params.toString(), e);
        }
        return list;
    }

    /**
     * 根据更新时间查询某时间段内更新过的订单信息
     * 
     * @param params
     * @return
     * @throws SQLException
     */
    public List<Map> queryOrderInfoByUpdateDate(Map<String, Object> params) throws ServiceException {

        List<Map> list;
        try {
            list = promotionStatisticsDao.queryOrderInfoByUpdateDate(params);
        } catch (Exception e) {
            throw new ServiceException(0, "根据更新时间查询某时间段内更新过的订单信息发生异常，参数：" + params.toString(), e);
        }
        return list;
    }

    /**
     * 根据订单号查询订单明细
     * 
     * @param orderCode
     * @return
     * @throws SQLException
     */
    public List<Map> queryOrderDetailInfoByOrderCode(String orderCode) throws ServiceException {

        List<Map> list;
        try {
            list = promotionStatisticsDao.queryOrderDetailInfoByOrderCode(orderCode);
        } catch (Exception e) {
            throw new ServiceException(0, "根据订单号查询订单明细发生异常，参数：" + orderCode, e);
        }
        return list;
    }

    /**
     * 根据订单号查询订单明细,并根据产品编号对价格和数量进行合并 add by songmiao 2016-3-4
     * 
     * @param orderCode
     * @return
     * @throws SQLException
     */
    public List<Map> queryOrderDetailInfoByOrderCodeAndProduct(String orderCode)
            throws ServiceException {

        List<Map> list;
        try {
            list = promotionStatisticsDao.queryOrderDetailInfoByOrderCodeAndProduct(orderCode);
        } catch (Exception e) {
            throw new ServiceException(0, "根据订单号查询订单明细发生异常，参数：" + orderCode, e);
        }
        return list;
    }

    /**
     * 新增数据接口查询信息
     * 
     * @param requestInfo
     * @return
     * @throws ServiceException
     */
    public int insertRequestInfo(RequestInfo requestInfo) throws ServiceException {

        int rs;
        try {
            rs = promotionStatisticsDao.insertRequestInfo(requestInfo);
        } catch (Exception e) {
            throw new ServiceException(0, "新增数据接口查询信息发生异常，参数：" + requestInfo.toString(), e);
        }
        return rs;
    }

    /**
     * 新增订单标识数据
     * 
     * @param orderInfo
     * @return
     * @throws ServiceException
     */
    public int insertOrderFlagInfo(OrderOutSideExtInfo orderInfo) throws ServiceException {

        int rs;
        try {
            rs = promotionStatisticsDao.insertOrderFlagInfo(orderInfo);
        } catch (Exception e) {
            throw new ServiceException(0, "新增订单标识数据发生异常，参数：" + orderInfo.toString(), e);
        }
        return rs;
    }

    /**
     * 新增cps跳转数据
     * 
     * @param cpsTrackInfo
     * @return
     * @throws ServiceException
     */
    public int insertCpsTrackInfo(CpsTrackInfo cpsTrackInfo) throws ServiceException {

        int rs;
        try {
            rs = promotionStatisticsDao.insertCpsTrackInfo(cpsTrackInfo);
        } catch (Exception e) {
            throw new ServiceException(0, "新增cps跳转数据发生异常，参数：" + cpsTrackInfo.toString(), e);
        }
        return rs;
    }
}
