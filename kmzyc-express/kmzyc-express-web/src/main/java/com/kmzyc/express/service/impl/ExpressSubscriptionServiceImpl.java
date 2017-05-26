package com.kmzyc.express.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.dao.ExpressSubscriptionDAO;
import com.kmzyc.express.entities.ExpressCompany;
import com.kmzyc.express.entities.ExpressLog;
import com.kmzyc.express.entities.ExpressSubscription;
import com.kmzyc.express.entities.ExpressTrack;
import com.kmzyc.express.service.ExpressCompanyService;
import com.kmzyc.express.service.ExpressLogService;
import com.kmzyc.express.service.ExpressSubscriptionService;
import com.kmzyc.express.service.ExpressTrackService;
import com.kmzyc.express.util.ErrorCode;
import com.kmzyc.express.util.ExpressHttpRequestUtil;
import com.kmzyc.express.util.JacksonHelper;
import com.kmzyc.express.util.SpringBeanUtil;
import com.kmzyc.express.util.constant.ExpressLogConstants;
import com.kmzyc.express.util.constant.ExpressSubConstants;
import com.kmzyc.express.vobject.ExpressNoticeRequestVO;
import com.kmzyc.express.vobject.ExpressSubRequestVO;
import com.kmzyc.express.vobject.ExpressSubResponseVO;
import com.kmzyc.express.vobject.NoticeResultItemVO;
import com.kmzyc.express.vobject.NoticeResultVO;
import com.kmzyc.zkconfig.ConfigurationUtil;

import redis.clients.jedis.JedisCluster;

@SuppressWarnings("unchecked")
@Service("expressSubscriptionService")
@Scope("singleton")
public class ExpressSubscriptionServiceImpl implements ExpressSubscriptionService {

    private static Logger logger = LoggerFactory.getLogger(ExpressSubscriptionServiceImpl.class);

    @Resource
    private ExpressSubscriptionDAO expressSubscriptionDAO;

    @Resource
    ExpressLogService expressLogService;

    @Resource
    ExpressTrackService expressTrackService;

    @Resource
    ExpressCompanyService expressCompanyService;

    @Resource(name="jedisCluster")
    private JedisCluster jedisCluster;

    public static final ExecutorService executors = Executors.newFixedThreadPool(500);// 线程池

    @Override
    public int queryExpressSubscriptionCount(Map<String, String> paramMap) throws ServiceException {
        try {
            return expressSubscriptionDAO.queryExpressSubscriptionCount(paramMap);
        } catch (Exception e) {
            logger.error("查询物流订阅列表数量发生异常：" + e.getMessage());
            throw new ServiceException(ErrorCode.EXPRESS_SUB_ERR, "查询物流订阅列表数量发生异常："
                    + e.getMessage());
        }
    }

    @Override
    public List queryExpressSubscriptionListByPage(Map<String, String> paramMap)
            throws ServiceException {
        try {
            return expressSubscriptionDAO.queryExpressSubscriptionList(paramMap);
        } catch (Exception e) {
            logger.error("查询物流订阅列表发生异常：" + e.getMessage());
            throw new ServiceException(ErrorCode.EXPRESS_SUB_ERR, "查询物流订阅列表发生异常：" + e.getMessage());
        }
    }

    @Override
    public List<ExpressSubscription> queryExpressSubWithTrackDetail(String logisticsCode, String logisticsNo)
            throws ServiceException {
        List<ExpressSubscription> result = null;
        if (StringUtils.isEmpty(logisticsCode) || StringUtils.isEmpty(logisticsNo)) {
            return result;
        }

        try {
            StringBuilder sBuilder = new StringBuilder(200);
            sBuilder.append("expresssubredis_").append(logisticsCode).append("_").append(logisticsNo);
            String key = sBuilder.toString();
            String value = jedisCluster.get(key);
            if (StringUtils.isNotBlank(value)) {
                result = JSON.parseArray(value, ExpressSubscription.class);
            } else {
                result = expressSubscriptionDAO.queryExpressSubWithTrackDetail(logisticsCode, logisticsNo);
                if (CollectionUtils.isNotEmpty(result)) {
                    /*for (ExpressSubscription tempSub  : result) {
                        if (tempSub.getExpressTrackList() != null && tempSub.getExpressTrackList().size() == 1
                                && tempSub.getExpressTrackList().get(0) != null
                                && tempSub.getExpressTrackList().get(0).getTrackId() == null) {
                            tempSub.setExpressTrackList(null);
                        }
                    }*/
                    // 处理当没有物流详细信息的时候，通过ibatis也可能查询出来一条track记录的问题
                    result = result.stream().map(tempSub -> {
                        if (CollectionUtils.isNotEmpty(tempSub.getExpressTrackList())
                                && tempSub.getExpressTrackList().size() == 1
                                && tempSub.getExpressTrackList().get(0) != null
                                && tempSub.getExpressTrackList().get(0).getTrackId() == null) {
                            tempSub.setExpressTrackList(null);
                        }
                        return tempSub;
                    }).collect(Collectors.toList());
                }
                //30分钟缓存
                jedisCluster.setex(key, 30 * 60, JSON.toJSONString(result));
            }
        }catch (Exception e) {
            logger.error("查询物流订阅及物流跟踪信息发生异常：" + e.getMessage());
            throw new ServiceException(ErrorCode.EXPRESS_SUB_ERR, "查询物流订阅及物流跟踪信息发生异常："
                    + e.getMessage(), e);
        }
        return result;
    }

    @Override
    public ExpressSubscription selectSubscriptionByPrimaryKey(ExpressSubscription record)
            throws ServiceException {
        try {
            return expressSubscriptionDAO.selectSubscriptionByPrimaryKey(record);
        } catch (Exception e) {
            logger.error("根据主键获取物流订阅信息异常：subId=" + record.getSubId().toPlainString()
                    + e.getMessage());
            throw new ServiceException(ErrorCode.EXPRESS_SUB_ERR, "根据主键获取物流订阅信息异常："
                    + e.getMessage(), e);
        }
    }


    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public BigDecimal insertSubscription(ExpressSubscription record) throws ServiceException {
        try {
            return expressSubscriptionDAO.insertSubscription(record);
        } catch (SQLException e) {
            logger.error("新增物流订阅信息发生异常：record:" + record.toString() + " , " + e.getMessage());
            throw new ServiceException(ErrorCode.EXPRESS_SUB_ERR, "新增物流订阅信息发生异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int updateSubscription(ExpressSubscription record) throws ServiceException {
        try {
            return expressSubscriptionDAO.updateSubscriptionByPrimaryKey(record);
        } catch (Exception e) {
            logger.error("更新物流订阅信息异常：subId=" + record.getSubId().toPlainString() + e.getMessage());
            throw new ServiceException(ErrorCode.EXPRESS_SUB_ERR, "更新物流订阅信息异常：" + e.getMessage());
        }
    }

    /**
     * 订阅任务
     */
    @Override
    public void executeSubscription(final ExpressSubscription record) throws ServiceException {
        try {
            executors.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        SpringBeanUtil.getBean("expressSubscriptionService", ExpressSubscriptionService.class)
                                .executeOutersubscription(record);
                    } catch (ServiceException e) {
                        logger.error("提交物流100订阅操作异常：record=" + record.toString() + " , "
                                + e.getMessage(), e);
                    }
                }
            });
        } catch (Exception e) {
            logger.error("提交物流100订阅操作异常：record=" + record.toString() + " , " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void executeOutersubscription(ExpressSubscription record) throws ServiceException {
        // 1.如果是可用单据，则提交到快递100
        if (record == null || ExpressSubConstants.FLAG_USELESS.equals(record.getUselessFlag())) {
            logger.error("废单，不提交到快递100.");
            return;
        }
        try {
            //2.构造参数
            ExpressSubRequestVO requestVO = new ExpressSubRequestVO();
            requestVO.setCompany(record.getLogisticsCode() != null
                    ? record.getLogisticsCode()
                    : "");
            requestVO.setNumber(record.getLogisticsNo() != null ? record.getLogisticsNo() : "");
            requestVO.setFrom(record.getFromCity() != null ? record.getFromCity() : "");
            requestVO.setTo(record.getToCity() != null ? record.getToCity() : "");
            requestVO.getParameters().put("callbackurl", ConfigurationUtil.getString("EXPRESS_CALLBACK_URL"));
            requestVO.setKey(ConfigurationUtil.getString("AUTHORIZATION_KEY"));
            HashMap<String, String> requestMap = new HashMap<String, String>();
            requestMap.put("schema", "json");
            requestMap.put("param", JacksonHelper.toJSON(requestVO));

            // 3.提交请求，并获取响应信息
            String strResponseJson = ExpressHttpRequestUtil.postData(
                ConfigurationUtil.getString("EXPRESS_SUB_URL"),
                            requestMap, ExpressSubConstants.POST_CHARSET);
            //4.结果校验
            if (StringUtils.isEmpty(strResponseJson)) {
                logger.error("请求失败快递公司编号[{}],快递单号[{}]，没有获取到返回值。",
                        new Object[]{record.getLogisticsCode(), record.getLogisticsNo()});
                return;
            }

            //5.解析响应
            ExpressSubResponseVO responseVO = JacksonHelper.fromJSON(strResponseJson, ExpressSubResponseVO.class);

            // 6.记录日志和更新订阅记录
            ExpressLog expressLog = new ExpressLog();
            Date now = new Date();
            expressLog.setOrderCode(record.getOrderCode());
            expressLog.setLogisticsName(record.getLogisticsName());
            expressLog.setLogisticsCode(record.getLogisticsCode());
            expressLog.setLogisticsNo(record.getLogisticsNo());
            expressLog.setCreateDate(now);
            expressLog.setNode(ExpressLogConstants.SyncNode.SynSubscription.getIntegerKey());
            // 默认状态为失败
            expressLog.setStatus(ExpressLogConstants.LogStatus.FAIL.getIntegerKey());
            // 备注
            StringBuilder strMarkBuilder = new StringBuilder();
            strMarkBuilder.append("code:");
            strMarkBuilder.append(responseVO.getReturnCode());
            strMarkBuilder.append(" , message:");
            strMarkBuilder.append(responseVO.getMessage());
            expressLog.setMark(strMarkBuilder.toString());

            record.setSubDate(now);
            // 订阅成功
            if (responseVO.getResult()) {
                record.setTrackStatus(ExpressSubConstants.TrackStatus.POLLING.getIntegerKey());
                expressLog.setStatus(ExpressLogConstants.LogStatus.SUCESS.getIntegerKey());// 设置状态为成功
            } else if (ExpressSubConstants.DUPLICATE_RETURN_CODE.equals(responseVO.getReturnCode())) {// 订阅为重复订阅
                Integer origStatus = record.getTrackStatus();
                // 如果该订阅记录状态为未订阅或订阅失败，更新订阅记录跟踪状态为监控中,并设置订阅日志为成功
                if (ExpressSubConstants.TrackStatus.NOSUB.getIntegerKey().equals(origStatus)
                        || ExpressSubConstants.TrackStatus.SUB_FAIL.getIntegerKey().equals(origStatus)) {
                    record.setTrackStatus(ExpressSubConstants.TrackStatus.POLLING.getIntegerKey());
                    expressLog.setStatus(ExpressLogConstants.LogStatus.SUCESS.getIntegerKey());
                    expressLog.setMark("");
                }
            } else {
                record.setTrackStatus(ExpressSubConstants.TrackStatus.SUB_FAIL.getIntegerKey());
            }

            // 更新订阅记录
            updateSubscription(record);
            // 记录日志
            expressLogService.insertExpressLog(expressLog);
        } catch (Exception e) {
            logger.error("提交物流100订阅操作异常：record=" + record.toString() + " , " + e.getMessage());
            throw new ServiceException(ErrorCode.EXPRESS_SUB_ERR, "提交物流100订阅操作异常：" + e.getMessage());
        } finally {
            // 不管向快递100订阅成功还是失败，都清理缓存的订阅缓存标识
            try {
                expressSubscriptionDAO.clearExpressSubProcessingCacheFlag(
                        record.getLogisticsCode(), record.getLogisticsNo());
            } catch (Exception e) { // 处理异常
                logger.error("清理订阅处理缓存标识失败：record=" + record.toString() + " , " + e.getMessage());
            }
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = ServiceException.class)
    public BigDecimal executeInnerSubscription(ExpressSubscription record) throws ServiceException {
        BigDecimal subId = BigDecimal.ZERO;
        //1.参数校验 （物流公司编码和物流单号）
        if (record == null || StringUtils.isEmpty(record.getLogisticsCode())
                || StringUtils.isEmpty(record.getLogisticsNo())) {
            logger.error("向快递100提交订阅失败,参数有为空。" );
            return subId;
        }

        try {
            // 2.根据的物流公司编码和物流单号查询是否已经订阅
            String logisticsCode = record.getLogisticsCode();
            String logisticsNo = record.getLogisticsNo();
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("logisticsCode", logisticsCode);
            paramMap.put("logisticsNo", logisticsNo);
            //查询订阅单据
            List<ExpressSubscription> subList = querySubscriptionByLosCodeAndNo(paramMap);

            // 2.1如果不存在则进行内部订阅
            if (CollectionUtils.isEmpty(subList)) {
                //2.1.1查询快递公司信息
                ExpressCompany expressCompany = expressCompanyService.selectExpressCompanyByCode(logisticsCode);
                if (expressCompany != null) {
                    record.setLogisticsName(expressCompany.getLogisticsName());
                }

                //2.1.2保存订阅信息
                Date now = new Date();
                record.setSubDate(now);
                record.setTrackStatus(ExpressSubConstants.TrackStatus.NOSUB.getIntegerKey());
                record.setAbortCount(BigDecimal.ZERO.intValue());
                record.setUselessFlag(ExpressSubConstants.FLAG_USEABLE);
                subId = insertSubscription(record);
                record.setSubId(subId);

                // 2.1.3设置日志信息并保存日志
                ExpressLog expressLog = new ExpressLog();
                expressLog.setOrderCode(record.getOrderCode());
                expressLog.setLogisticsName(record.getLogisticsName());
                expressLog.setLogisticsCode(logisticsCode);
                expressLog.setLogisticsNo(logisticsNo);
                expressLog.setCreateDate(now);
                expressLog.setStatus(ExpressLogConstants.LogStatus.SUCESS.getIntegerKey());
                expressLog.setNode(ExpressLogConstants.SyncNode.SyncOrder.getIntegerKey());
                expressLog.setMark("新增内部系统订阅成功");
                expressLogService.insertExpressLog(expressLog);
            } else {//2.2存在
                record = subList.get(0);
                subId = record.getSubId();
            }

            // 3.订阅成功，向缓存中加入订阅标识,并向快递快递100订阅
            try {
                // 如果状态不是结束状态，则允许继续提交订阅，记录重复订阅日志
                if (!ExpressSubConstants.TrackStatus.SHUTDOWN.getIntegerKey().equals(record.getExpressStatus())) {
                    // 设置订阅缓存标志15分钟
                    expressSubscriptionDAO.insertExpressSubProcessingCacheFlag(logisticsCode, logisticsNo);
                    // 提交快递100订阅
                    executeSubscription(record);
                }
            } catch (ServiceException ex) {
                logger.error("向快递100提交订阅失败, {}", ex); // 记录日志，不需要抛出
            } catch (Exception ex) {
                logger.error("向redis缓存中插入订阅缓存标识失败, {}", ex); // 记录日志，不需要抛出
            }
        } catch (Exception e) {
            logger.error("物流订阅操作异常：record=" + record.toString() + " , " + e.getMessage());
            throw new ServiceException(ErrorCode.EXPRESS_SUB_ERR, "物流订阅操作异常：" + e.getMessage());
        }
        return subId;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = ServiceException.class)
    public int processExpressSubPushInfo(ExpressNoticeRequestVO requestVO) throws ServiceException {
        // 返回值 0表示处理失败，1表示处理成功
        int result = 0;
        ExpressSubscription expressSubscription = null;
        List<NoticeResultItemVO> itemList = null;
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String pushStatus = requestVO.getStatus(); // 监控状态
            String pushMsg = requestVO.getMessage();// 监控消息
            NoticeResultVO resultVO = requestVO.getLastResult(); // 监控结果
            // 1.快递公司编码并且快递单号不能为空
            String com = resultVO.getCom();
            String nu = resultVO.getNu();
            if (StringUtils.isEmpty(com) && StringUtils.isEmpty(nu)) {
                logger.error("快递公司编码[{}]并且快递单号[{}]不能为空", new Object[]{com, nu});
                return result;
            }

            // 2.根据订单号和物流公司编码获取物流订阅记录
            List<ExpressSubscription> subList = queryExpressSubWithTrackDetail(com, nu);
            // 如果推送的订阅记录存在
            if (CollectionUtils.isNotEmpty(subList)) {
                expressSubscription = subList.get(0);
                expressSubscription.setLatestPushDate(now);
                expressSubscription.setExpressStatus(Integer.valueOf(resultVO.getState()));
                expressSubscription.setLatestPushStatus(ExpressSubConstants.PushStatus.PUSH_SUCESS.getIntegerKey());
                expressSubscription.setTrackStatus(ExpressSubConstants.TrackStatus.getByCode(pushStatus).getIntegerKey());

                // 如果是abort的状态，则需要将abortCount +1 ,如果已经满4次，则设置为废单
                if (ExpressSubConstants.TrackStatus.ABORT.getCode().equals(pushStatus)) {
                    expressSubscription.setAbortCount(expressSubscription.getAbortCount() + 1);
                    if (expressSubscription.getAbortCount() >= ExpressSubConstants.MAX_ABORT_NUM) {
                        expressSubscription.setUselessFlag(ExpressSubConstants.FLAG_USELESS);
                    }
                }

                //3.修改订阅信息
                updateSubscription(expressSubscription);

                // 4.物流跟踪明细
                itemList = resultVO.getData();
                if (itemList != null && itemList.size() > 0) {
                    List<ExpressTrack> trackList = new ArrayList<ExpressTrack>(itemList.size());
                    ExpressTrack tempTrack = null;
                    for (NoticeResultItemVO itemVO : itemList) {
                        tempTrack = new ExpressTrack();
                        tempTrack.setSubId(expressSubscription.getSubId());
                        tempTrack.setTrackDate(dateFormat.parse(itemVO.getFtime()));
                        tempTrack.setTrackMsg(itemVO.getContext());
                        trackList.add(tempTrack);
                    }
                    // 推送信息为全量，需删掉之前的，重新新增
                    expressTrackService.deleteExpressTrackBySubId(expressSubscription.getSubId());
                    expressTrackService.batchInsertExpressTrack(trackList);
                }

                // 5.记录日志
                ExpressLog expressLog = new ExpressLog();
                expressLog.setOrderCode(expressSubscription.getOrderCode());
                expressLog.setLogisticsName(expressSubscription.getLogisticsName());
                expressLog.setLogisticsCode(expressSubscription.getLogisticsCode());
                expressLog.setLogisticsNo(expressSubscription.getLogisticsNo());
                expressLog.setCreateDate(now);
                expressLog.setMark("status: " + pushStatus + ", message:" + pushMsg);
                expressLog.setNode(ExpressLogConstants.SyncNode.SynReceive.getIntegerKey());
                expressLog.setStatus(ExpressLogConstants.LogStatus.SUCESS.getIntegerKey());
                expressLogService.insertExpressLog(expressLog);
            }
            result = 1;
        } catch (Exception e) {
            logger.error("快递100推送信息处理失败：requestInfo:" + requestVO.toJsonString() + " , "
                    + e.getMessage());
            throw new ServiceException(ErrorCode.EXPRESS_SUB_ERR, "快递100推送信息处理失败：" + e.getMessage(), e);
        }
        return result;
    }

    @Override
    public List queryAllAbortSubscriptionList(Map<String, String> paramMap) throws Exception {
        try {
            return expressSubscriptionDAO.queryAllAbortSubscriptionList(paramMap);
        } catch (Exception e) {
            logger.error("查询需要自动订阅的abort记录失败：" + e.getMessage());
            throw new ServiceException(ErrorCode.EXPRESS_SUB_ERR, "查询需要自动订阅的abort记录失败："
                    + e.getMessage());
        }
    }

    @Override
    public int querySubCountByLosCodeAndNo(Map<String, String> paramMap) throws ServiceException {
        try {
            return expressSubscriptionDAO.querySubCountByLosCodeAndNo(paramMap);
        } catch (Exception e) {
            logger.error("根据公司编码和物流单号查询数量发生失败：" + e.getMessage());
            throw new ServiceException(ErrorCode.EXPRESS_SUB_ERR, "根据公司编码和物流单号查询数量发生失败："
                    + e.getMessage());
        }
    }

    @Override
    public List<ExpressSubscription> querySubscriptionByLosCodeAndNo(Map<String, String> paramMap) throws Exception {
        try {
            return expressSubscriptionDAO.querySubscriptionByLosCodeAndNo(paramMap);
        } catch (Exception e) {
            logger.error("根据物流单号和物流公司编码查询订阅单据失败：" + e.getMessage());
            throw new ServiceException(ErrorCode.EXPRESS_SUB_ERR, "根据物流单号和物流公司编码查询订阅单据失败："
                    + e.getMessage(), e);
        }
    }
}
