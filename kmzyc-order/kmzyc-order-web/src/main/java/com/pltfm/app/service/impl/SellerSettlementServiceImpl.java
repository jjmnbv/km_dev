package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Destination;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.km.framework.mq.Sender;
import com.km.framework.mq.bean.KmMsg;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.kmzyc.framework.constants.EmailSendType;
import com.kmzyc.framework.constants.MessageConstants;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.dao.DiffAdjDAO;
import com.pltfm.app.dao.SellerSettlementDAO;
import com.pltfm.app.dao.SettmentOperateStatementDAO;
import com.pltfm.app.entities.DiffAdj;
import com.pltfm.app.entities.DiffAdjExample;
import com.pltfm.app.entities.SellerSettlement;
import com.pltfm.app.entities.SellerSettlementCriteria;
import com.pltfm.app.entities.SellerSettlementExample;
import com.pltfm.app.entities.SettmentOperateStatement;
import com.pltfm.app.entities.SettmentOperateStatementExample;
import com.pltfm.app.service.SellerSettlementService;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.vobject.Message;
import com.pltfm.sys.model.SysUser;
import com.pltfm.sys.util.DatetimeUtil;
import com.pltfm.sys.util.ErrorCode;

import redis.clients.jedis.JedisCluster;

@SuppressWarnings("unchecked")
@Service("sellerSettlementService")
public class SellerSettlementServiceImpl implements SellerSettlementService {

    private static final Logger log = Logger.getLogger(SellerSettlementServiceImpl.class);

    @Resource
    private SellerSettlementDAO sellerSettlementDAO;

    @Resource
    private SettmentOperateStatementDAO settmentOperateStatementDAO;

    @Resource
    private DiffAdjDAO diffAdjDAO;

    @Resource
    private JmsTemplate jmsTemplate;
    @Resource
    private Destination destination;

    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;


    @Override
    public int countByExample(SellerSettlementExample example) throws ServiceException {
        try {
            return sellerSettlementDAO.countByExample(example);
        } catch (Exception e) {
            log.error(ErrorCode.INTER_SELLER_SETTLEMENT_ERROR, e);
        }
        return 0;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public int deleteByExample(SellerSettlementExample example) throws ServiceException {
        try {
            return sellerSettlementDAO.deleteByExample(example);
        } catch (Exception e) {
            log.error(ErrorCode.INTER_SELLER_SETTLEMENT_ERROR, e);
        }
        return 0;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public Long insert(SellerSettlement record) throws ServiceException {
        try {
            return sellerSettlementDAO.insert(record);
        } catch (Exception e) {
            log.error(ErrorCode.INTER_SELLER_SETTLEMENT_ERROR, e);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public List selectByExample(SellerSettlementExample example) throws ServiceException {
        try {
            return sellerSettlementDAO.selectByExample(example);
        } catch (Exception e) {
            log.error(ErrorCode.INTER_SELLER_SETTLEMENT_ERROR, e);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public int updateByExample(SellerSettlement record, SellerSettlementExample example)
            throws ServiceException {
        try {
            return sellerSettlementDAO.updateByExample(record, example);
        } catch (Exception e) {
            log.error(ErrorCode.INTER_SELLER_SETTLEMENT_ERROR, e);
        }
        return 0;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public Message generateSettlement(String sellerId, String period, short operateType,
            short operatorType, boolean isSysAuto) throws Exception {
        Message message = new Message();
        if (thePeriodAlreadyExists(sellerId, period)) {
            message.setCode(1001);
            message.setContent("ID：" + sellerId + "的商户已生成过期数为" + period + "的结算单，无需从新生成");
            return message;
        } else {
            int year = Integer.parseInt(period.substring(0, 4));
            int month = Integer.parseInt(period.substring(4, 6));
            String sig = period.substring(6, 8);
            List<Date> dates = getDateZoneByYearAndMonth(year, month, sig);
            Map map = new HashMap();
            map.put("sellerId", sellerId);
            map.put("smPeriod", period);
            map.put("finishDate", dates.toArray());
            map.put("diffDate", dates.get(1));
            try {
                // 查询妥投、退款、预售超时未支付尾款 的数据，生成结算单
                generateSettle(sellerId, period, map);

                // 查询刚插入的结算表单数据
                SellerSettlement sellerSettlement =
                        querySettlementBySellerIdAndPeriod(sellerId, period);
                map.put("settlementNo", sellerSettlement.getSettlementNo());
                // 查询妥投、退款的商品明细，生成妥投商品明细
                sellerSettlementDAO.insertSettleDataDetail(map);
                // 查询预售超时未支付尾款的商品明细，生成妥投商品明细
                sellerSettlementDAO.insertSettleDataDetailForPresell(map);

                // 查询妥投订单运费明细所需数据并入库
                sellerSettlementDAO.insertSettleFareDetail(map);

                // 插入退款商品明细表所需数据
                sellerSettlementDAO.insertSettleRefundDetail(map);
                // // 插入预售超时未发货赔付退款商品明细表的所需数据
                // sellerSettlementDAO.insertSettleRefundDetailForPresell(map);

                // 结算单操作流水插入数据
                generateOperateStatement(operateType, operatorType, sellerSettlement, isSysAuto);

                // 更新关联的差异调整明细:
                diffAdjUpdate(sellerSettlement, map);

            } catch (Exception e) {
                message.setCode(1003);
                message.setContent("插入生成结算单数据时出错：" + e.getMessage());
                log.error("插入生成结算单数据时出错：", e);
                throw new ServiceException(1003, null, e);
            }
            message.setCode(1002);
            message.setContent("生成结算单数据成功！");
            return message;
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void diffAdjUpdate(SellerSettlement sellerSettlement, Map map) throws ServiceException {
        String period = sellerSettlement.getSettlementPeriod();

        String sellerId = (String) map.get("sellerId");

        // DiffAdjExample adjExample = new DiffAdjExample();
        // adjExample
        // .createCriteria()
        // .andSellerIdEqualTo(sellerId)
        // .andSettmentStatusEqualTo((short) OrderDictionaryEnum.DiffAdjStatus.notYet.getKey())
        // // 差异调整时间在当前结算单结算结束期前
        // .andAdjTimeLessThan(getStartEndDates(period)[1]).andCalcSettmentNoIsNull();
        try {

            // 超时未发货赔付
            Map pressellMap = this.sellerSettlementDAO.queryCompensateForPressell(map);
            if (null != pressellMap && !pressellMap.isEmpty()) {
                BigDecimal compensate = (BigDecimal) pressellMap.get("COMPENSATE");
                BigDecimal countOrderNumber = (BigDecimal) pressellMap.get("COUNTORDERNUMBER");
                if (null != compensate && null != countOrderNumber
                        && countOrderNumber.compareTo(new BigDecimal(0)) > 0) {
                    DiffAdj adj = new DiffAdj();
                    adj.setSellerId(new BigDecimal(sellerId));
                    adj.setCurrSettmentNo(sellerSettlement.getSettlementNo());
                    adj.setAdjMoney(compensate);
                    adj.setAdjTitle("超时未发货赔付");
                    adj.setAdjDetail("共" + countOrderNumber + "笔");
                    adj.setAdjTime(new Date());
                    adj.setSettmentStatus(new Short("0"));// 未结算
                    adj.setOperaterId(23l);// 系统管理员
                    adj.setIsDelete(1l);// 不允许删除
                    this.diffAdjDAO.insertSelective(adj);
                }
            }

            // List list = diffAdjDAO.selectByExample(adjExample);
            Map<String, Object> adjExample = new HashMap<String, Object>();
            adjExample.put("sellerId", sellerId);
            adjExample.put("settlementStatus",
                    OrderDictionaryEnum.SettlementStatus.setted.getKey());
            adjExample.put("daSettmentStatus", OrderDictionaryEnum.DiffAdjStatus.notYet.getKey());
            adjExample.put("adjTime", getStartEndDates(period)[1]);
            List<DiffAdj> list = this.diffAdjDAO.queryDiffAdjForSellerSettlement(adjExample);


            Double sumAdjMoney = 0.0;
            if (CollectionUtils.isNotEmpty(list)) {
                for (Object o : list) {
                    DiffAdj adj = (DiffAdj) o;
                    adj.setCalcSettmentNo(sellerSettlement.getSettlementNo());
                    adj.setSettmentStatus(
                            (short) OrderDictionaryEnum.DiffAdjStatus.alReady.getKey());
                    adj.setSettmentDate(new Date());
                    sumAdjMoney += adj.getAdjMoney().doubleValue();
                    diffAdjDAO.updateByPrimaryKey(adj);
                }
            }

            // 差异调整金额计入结算单中
            if (!(sumAdjMoney == 0.0)) {
                sellerSettlement.setDiffAdjSum(sumAdjMoney);
                sellerSettlement.setSettlementStatus(
                        (short) OrderDictionaryEnum.SettlementStatus.unSure.getKey());
                sellerSettlementDAO.updateByPrimaryKey(sellerSettlement);
            }

        } catch (Exception e) {
            log.error(ErrorCode.INTER_SELLER_SETTLEMENT_ERROR, e);
            throw new ServiceException(ErrorCode.INTER_SELLER_SETTLEMENT_ERROR, null, e);
        }
    }

    protected void generateOperateStatement(short operateType, short operatorType,
            SellerSettlement sellerSettlement, boolean isSysAuto) throws Exception {

        SettmentOperateStatement statement = new SettmentOperateStatement(),
                statementEnd = new SettmentOperateStatement();
        statement.setOperateType(operateType);
        String operator = "";
        if (isSysAuto)
            operator = "系统自动";
        else {
            Map session = ActionContext.getContext().getSession();
            SysUser sysuser = (SysUser) session.get("sysUser");
            operator = sysuser.getUserName();
        }
        statement.setOperator(operator);
        statement.setOperatorType(operatorType);
        statement.setSettmentNo(sellerSettlement.getSettlementNo());
        statement.setOperateTime(new Date());
        settmentOperateStatementDAO.insert(statement);
        // 当期结算金额为0时，1、则结算单生成完毕后自动变更状态为“已结出”，2、并产生相应的操作流水
        if (sellerSettlement.getCurrSettleAccounts() == 0.0
                && sellerSettlement.getDiffAdjSum() == 0) {
            statementEnd.setOperateTime(new Date());
            statementEnd.setOperateType(
                    (short) OrderDictionaryEnum.SettlementOperateType.financialSett.getKey());
            statementEnd.setOperator(operator);
            statementEnd.setOperatorType(
                    (short) OrderDictionaryEnum.SettlementOperatorType.sysAuto.getKey());
            statementEnd.setSettmentNo(sellerSettlement.getSettlementNo());
            statementEnd.setRemark("本期应结金额为0，自动添加‘财务结出’流水");
            settmentOperateStatementDAO.insert(statementEnd);
        }
    }

    protected void generateOperateStatementEnd(short operateType, short operatorType,
            SellerSettlement sellerSettlement) throws Exception {
        SettmentOperateStatement statement = new SettmentOperateStatement();
        statement.setOperatorType(operatorType);
        statement.setSettmentNo(sellerSettlement.getSettlementNo());
        statement.setOperateTime(new Date());
        settmentOperateStatementDAO.insert(statement);
    }

    protected SellerSettlement querySettlementBySellerIdAndPeriod(String sellerId, String period)
            throws ServiceException {
        SellerSettlementExample example = new SellerSettlementExample();
        example.createCriteria().andSellerIdEqualTo(new BigDecimal(sellerId))
                .andSettlementPeriodEqualTo(period);
        try {
            SellerSettlement settlement =
                    (SellerSettlement) sellerSettlementDAO.selectByExample(example).get(0);
            return settlement;
        } catch (Exception e) {
            log.error(ErrorCode.INTER_SELLER_SETTLEMENT_ERROR, e);
        }
        return null;
    }

    /**
     * 判断商户period期结算单是否已结出
     * 
     * @param period
     * @return
     * @throws ServiceException
     */
    @Override
    public boolean thePeriodAlreadyExists(String sellerId, String period) throws ServiceException {
        SellerSettlementExample example = new SellerSettlementExample();
        example.createCriteria().andSettlementPeriodEqualTo(period)
                .andSellerIdEqualTo(new BigDecimal(sellerId));
        List settles = null;
        try {
            settles = sellerSettlementDAO.selectByExample(example);
        } catch (Exception e) {
            log.error(ErrorCode.INTER_SELLER_SETTLEMENT_ERROR, e);
        }
        if (null == settles || settles.size() == 0)
            return false;
        else
            return true;
    }

    /**
     * 查询数据，并将数据插入
     * 
     * @param sellerId
     * @param period
     * @param map
     * @throws ServiceException
     */
    protected void generateSettle(String sellerId, String period, Map map) throws ServiceException {
        List tDatas = null;
        try {
            tDatas = sellerSettlementDAO.selectSettleData(map);
            Map ttMap = (Map) tDatas.get(0);
            Double ttsshz = ((BigDecimal) ttMap.get("ttsshz")).doubleValue(), // 妥投实收
                    ttyjhz = ((BigDecimal) ttMap.get("ttyjhz")).doubleValue(), // 妥投佣金
                    fare = ((BigDecimal) ttMap.get("fare")).doubleValue(), // 妥投运费
                    returnmoney = ((BigDecimal) ttMap.get("returnmoney")).doubleValue(), // 退款金额
                    returncommission = ((BigDecimal) ttMap.get("returncommission")).doubleValue(), // 退款佣金
                    refundFareSum = ((BigDecimal) ttMap.get("returnFareSum")).doubleValue(), // 退货返运费
                    addpvsum = ((BigDecimal) ttMap.get("addpvsum")).doubleValue(), // 妥投推广服务费
                    returnpvsum = ((BigDecimal) ttMap.get("returnpvsum")).doubleValue();// 退款返推广服务费
            // 本期应结算差异金额查询
            BigDecimal diffadj = sellerSettlementDAO.selectSumAdjForSettle(map);
            // 本期应结金额 扣减pv值20161028
            Double bqyjje = (ttsshz - returnmoney - refundFareSum) + fare
                    - (ttyjhz - returncommission) - (addpvsum - returnpvsum);
            // 创建、保存SellerSettlement(结算单对象)对象
            SellerSettlement settlement = new SellerSettlement();
            settlement.setSellerId(sellerId);
            settlement.setReceiveSum(ttsshz);
            settlement.setCommissionSum(ttyjhz);
            settlement.setCurrSettleAccounts(bqyjje);
            settlement.setFareSum(fare);
            settlement.setReturnFareSum(refundFareSum);
            settlement.setRefundSum(returnmoney);
            settlement.setRefundComSum(returncommission);
            settlement.setSettlementPeriod(period);
            settlement.setAddpvsum(addpvsum);
            settlement.setReturnpvsum(returnpvsum);
            settlement.setDiffAdjSum(diffadj.doubleValue());
            // 生成的结算单如果当期结算金额为0且当期应结算差异调整金额为0，则结算单生成完毕后自动变更状态为“已结出”
            settlement.setSettlementStatus((short) ((bqyjje == 0 && diffadj.doubleValue() == 0.0)
                    ? OrderDictionaryEnum.SettlementStatus.setted.getKey()
                    : OrderDictionaryEnum.SettlementStatus.unSure.getKey()));
            if (bqyjje == 0 && diffadj.doubleValue() == 0.0) {
                settlement.setSettlementFinishTime(new Date());
            }
            settlement.setSettlementCreateTime(new Date());
            sellerSettlementDAO.insert(settlement);
        } catch (Exception e) {
            log.error(ErrorCode.INTER_SELLER_SETTLEMENT_ERROR, e);
        }
    }

    @Override
    public Page querySettlementList(Page page, SellerSettlementCriteria criteria)
            throws ServiceException {

        int count = 0;
        List<SellerSettlement> list = null;
        try {

            count = sellerSettlementDAO.selectSettlementListSize(criteria);

            // 判断搜索条件中的条数是否超出实际条数范围（是，则返回第一页数据）
            if (criteria.getStartIndex() != null && count < criteria.getStartIndex()) {
                criteria.setStartIndex(0);
                criteria.setEndIndex(page.getPageSize());
                page.setPageNo(1);
            }
            if (count > 0)
                list = sellerSettlementDAO.querySettlementList(criteria);
        } catch (Exception e1) {
            log.error("查询结算单列表 ====>", e1);
            throw new ServiceException(0, null, e1);
        }

        page.setDataList(list);
        page.setRecordCount(count);

        return page;

    }

    /**
     * 查询店铺名称
     * 
     * @param sellerid
     * @return
     */
    private String getShopName(String sellerid) {
        String shopName = null;

        if (StringUtils.isNotEmpty(sellerid)) {
            try {
                SellerSettlementCriteria criteria = new SellerSettlementCriteria();
                criteria.setSellerId(sellerid);

                shopName = sellerSettlementDAO.getSellerShopName(criteria);
            } catch (Exception e) {
                log.error("根据ID查询店铺名称sellerid ====>" + sellerid, e);
            }
        }
        return shopName;
    }

    /**
     * 查 询结算单操作流水
     * 
     * @param settlementNo
     * @return
     */
    private List<SettmentOperateStatement> getOperatesLog(String settlementNo) {
        List<SettmentOperateStatement> operaters = null;

        if (StringUtils.isNotEmpty(settlementNo)) {
            try {
                SettmentOperateStatementExample example = new SettmentOperateStatementExample();
                example.createCriteria().andSettmentNoEqualTo(settlementNo);

                operaters = settmentOperateStatementDAO.selectByExample(example);

            } catch (Exception e) {
                log.error("根据ID操作日志settlementNo ====>" + settlementNo, e);
            }
        }
        return operaters;
    }

    @Override
    public SellerSettlement getSettlementByNo(SellerSettlementCriteria criteria,
            boolean isPintOperates) throws ServiceException {

        SellerSettlement info = null;
        try {
            info = sellerSettlementDAO.getSettlementByNo(criteria);
        } catch (Exception e) {
            log.error("查询结算单详情  ====>", e);
            throw new ServiceException(0, "查询结算单详情 no =" + criteria.getSettlementNo(), e);
        }

        if (null != info) {
            // 店铺名称
            info.setShopName(getShopName(info.getSellerId()));
            // 查询操作日志列表
            if (isPintOperates)
                info.setOperates(getOperatesLog(info.getSettlementNo()));
        }
        return info;
    }

    @Override
    public int selectSettlementSize(SellerSettlementCriteria criteria) throws ServiceException {
        int count = 0;
        try {

            criteria.setInPeriods(
                    getPeriodsByStartDateEndDate(criteria.getStartDate(), criteria.getEndDate()));
            count = sellerSettlementDAO.selectSettlementListSize(criteria);
        } catch (Exception e1) {
            log.error("查询结算单列表 ====>", e1);
            throw new ServiceException(0, null, e1);
        }

        return count;
    }

    @Override
    public List<SellerSettlement> selectSettlementList(SellerSettlementCriteria criteria)
            throws ServiceException {
        List<SellerSettlement> list = null;
        try {
            criteria.setInPeriods(
                    getPeriodsByStartDateEndDate(criteria.getStartDate(), criteria.getEndDate()));
            list = sellerSettlementDAO.querySettlementList(criteria);

            // 如果查询页数非第一页且查询结果为空
            if (list.size() == 0 && criteria.getStartIndex() != 0) {
                criteria.setEndIndex(criteria.getEndIndex() - criteria.getStartIndex());
                criteria.setStartIndex(0);
                list = sellerSettlementDAO.querySettlementList(criteria);
            }
        } catch (Exception e1) {
            log.error("查询结算单列表 ====>", e1);
            throw new ServiceException(0, null, e1);
        }

        return list;
    }

    @Override
    public int updateOperate(SellerSettlement record) throws ServiceException {
        try {
            return sellerSettlementDAO.updateOperate(record);
        } catch (Exception e) {
            log.error(ErrorCode.INTER_SELLER_SETTLEMENT_ERROR, e);
        }
        return 0;
    }

    /**
     * 通过年、月、上/下半月标志获取时间区间
     * 
     * @param year
     * @param month
     * @param sig
     * @return
     */
    public static List<Date> getDateZoneByYearAndMonth(int year, int month, String sig) {
        Calendar calendar = DatetimeUtil.getCalendarInstance();
        calendar.set(Calendar.YEAR, year);
        List<Date> dates = new ArrayList<Date>();
        Date start = null, end = null;
        calendar.set(Calendar.MONTH, month - 1);
        if (sig.equals("H2")) {
            // calendar.set(Calendar.DAY_OF_MONTH,
            // Integer.parseInt(getCurMonthFirstDay(date)));
            calendar.set(Calendar.DAY_OF_MONTH, 16);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            start = calendar.getTime();
            dates.add(calendar.getTime());
            int lastDayOfCurrMonth = Integer
                    .parseInt(DatetimeUtil.getCurMonthLastDay(calendar.getTime()).substring(6, 8));
            calendar.set(Calendar.DAY_OF_MONTH, lastDayOfCurrMonth);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            end = calendar.getTime();
            dates.add(end);
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            start = calendar.getTime();
            dates.add(start);
            calendar.set(Calendar.DAY_OF_MONTH, 15);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            end = calendar.getTime();
            dates.add(end);
        }
        return dates;
    }

    @Override
    public List selectSellersNeedSettle(Map map) throws ServiceException {
        try {
            if (map == null || map.get("settlePeriod") == null)
                return null;
            return sellerSettlementDAO.selectSellersNeedSettle(map);
        } catch (Exception e) {
            log.error(ErrorCode.INTER_SELLER_SETTLEMENT_ERROR, e);
        }
        return null;
    }

    @Override
    public Message sysAutoGenerateSettlements(Map map) throws ServiceException {
        Message message = new Message();
        String settlePeriod = null;
        List ids = null;
        String key = "settlementGenerate_runing";
        int seconds = 3600; // 缓存一小时
        try {
            if (!jedisCluster.exists(key)) {

                jedisCluster.setex(key, seconds, "1");

                short operateType =
                        (short) OrderDictionaryEnum.SettlementOperateType.gener.getKey();
                short operatorType =
                        (short) OrderDictionaryEnum.SettlementOperatorType.sysAuto.getKey();
                settlePeriod = (String) map.get("settlePeriod");
                ids = (List) map.get("sellerIds");

                for (Object o : ids) {
                    Map sellerIdMap = (Map) o;
                    log.warn("系统开始自动结算期数为：" + settlePeriod + "，商户ID为"
                            + sellerIdMap.get("COMMERCIALID").toString());
                    generateSettlement(sellerIdMap.get("COMMERCIALID").toString(), settlePeriod,
                            operateType, operatorType, true);
                }

                jedisCluster.del(key);
            }
        } catch (Exception e) {
            jedisCluster.del(key);
            message.setCode(10001);
            message.setContent("系统自动生成期数为：" + settlePeriod + "结算数据循环方法异常");
            throw new ServiceException(0, "系统自动生成结算数据循环方法异常", e);

        }
        message.setCode(10000);
        message.setContent("系统自动生成期数为：" + settlePeriod + "所有商户结算数据成功");
        return null;
    }

    @Override
    public List<SellerSettlement> selectSupplierListByName(String supplierName)
            throws ServiceException {
        try {
            return sellerSettlementDAO.selectSupplierListByName(supplierName);
        } catch (Exception e) {
            log.error(ErrorCode.INTER_SELLER_SETTLEMENT_ERROR, e);
        }
        return null;
    }

    @Override
    public List<String> selectSupplierByLikePeriod(String sellerId, String period)
            throws ServiceException {
        try {
            return sellerSettlementDAO.selectSupplierByLikePeriod(sellerId, period);
        } catch (Exception e) {
            log.error(ErrorCode.INTER_SELLER_SETTLEMENT_ERROR, e);
        }
        return null;
    }

    @Override
    public List<SellerSettlement> selectAllSupplier() throws ServiceException {
        try {
            return sellerSettlementDAO.selectAllSupplier();
        } catch (Exception e) {
            log.error(ErrorCode.INTER_SELLER_SETTLEMENT_ERROR, e);
        }
        return null;
    }

    @Override
    public int updateFinancialConfirmation(SellerSettlement record) throws ServiceException {
        try {
            return sellerSettlementDAO.updateFinancialConfirmation(record);
            // + sellerSettlementDAO.finacialAmountSettle(record)
        } catch (Exception e) {
            log.error(ErrorCode.INTER_SELLER_SETTLEMENT_ERROR, e);
        }
        return 0;
    }

    @Override
    public int updateSellerConfirmation(SellerSettlement record) throws ServiceException {
        try {
            log.info("updateSellerConfirmation===>" + record);
            return sellerSettlementDAO.updateSellerConfirmation(record);
        } catch (Exception e) {
            log.error(ErrorCode.INTER_SELLER_SETTLEMENT_ERROR, e);
        }
        return 0;
    }

    @Override
    public Date[] getStartEndDates(String period) {
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = new SimpleDateFormat("yyyyMM").parse(period);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        if (period.endsWith("h1") || period.endsWith("H1")) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date t = calendar.getTime();
            calendar.set(Calendar.DAY_OF_MONTH, 15);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            Date t2 = calendar.getTime();
            return new Date[] {t, t2};

        } else {
            calendar.set(Calendar.DAY_OF_MONTH, 16);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date t = calendar.getTime();
            calendar.add(Calendar.MONTH, +1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date t2 = calendar.getTime();
            return new Date[] {t, t2};
        }
    }

    @Override
    public List<String> getPeriodsByStartDateEndDate(Date start, Date end) {
        // SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        List<String> periods = new ArrayList<String>();
        if (null == start || null == end)
            return null;
        // Date start =null, end=null;
        // try {
        // start = sf.parse("2014-11-06 12:22:22");
        // end= sf.parse("2015-05-16 12:22:22");
        // } catch (ParseException e) {
        // e.printStackTrace();
        // }
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(start);
        Calendar calendarStartEnd = Calendar.getInstance();
        calendarStartEnd.setTime(end);
        int monthStart = calendarStart.get(Calendar.MONTH) + 1;
        int monthEnd = calendarStartEnd.get(Calendar.MONTH) + 1;
        int yearStart = calendarStart.get(Calendar.YEAR);
        int yearEnd = calendarStartEnd.get(Calendar.YEAR);
        int dayStart = calendarStart.get(Calendar.DAY_OF_MONTH);
        int dayEnd = calendarStartEnd.get(Calendar.DAY_OF_MONTH);
        int dayStartHour = calendarStart.get(Calendar.HOUR_OF_DAY);
        int dayStartMinute = calendarStart.get(Calendar.MINUTE);
        int dayStartSecond = calendarStart.get(Calendar.SECOND);
        if (yearStart == yearEnd) {

            if (dayStart > 16 & dayEnd < 16 & monthEnd == monthStart) {
                String emptyPeriods[] = {"null"};
                return Arrays.asList(emptyPeriods);
            }

            if (dayStart < 16 & dayEnd <= 16 & monthEnd == monthStart) {

                if (dayStart == 1 & dayStartHour == 0 & dayStartMinute == 0 & dayStartSecond == 0) {

                    String p = yearStart + "" + (monthStart < 10 ? ("0" + "" + monthStart) :

                            monthStart) + "H1";
                    periods.add(p);
                    return periods;

                } else {

                    String emptyPeriods[] = {"null"};
                    return Arrays.asList(emptyPeriods);
                }

            }
            if (dayStart > 16 & dayEnd > 16 & monthEnd == monthStart) {
                String emptyPeriods[] = {"null"};
                return Arrays.asList(emptyPeriods);
            }
            if (dayStart < 16 & dayEnd > 16 & monthEnd == monthStart) {
                if (dayStart == 1 & dayStartHour == 0 & dayStartMinute == 0 & dayStartSecond == 0) {

                    String p = yearStart + ""
                            + (monthStart < 10 ? ("0" + "" + monthStart) : monthStart) + "H1";
                    periods.add(p);
                    return periods;

                } else {

                    String emptyPeriods[] = {"null"};
                    return Arrays.asList(emptyPeriods);
                }

            }
            if (dayStart == 16 & dayEnd > 16 & monthEnd == monthStart) {
                String emptyPeriods[] = {"null"};
                return Arrays.asList(emptyPeriods);
            }


            if (dayStart <= 16 & monthEnd > monthStart) {
                if (dayStart == 16 & dayStartHour == 0 & dayStartMinute == 0
                        & dayStartSecond == 0) {
                    String p = yearStart + ""
                            + (monthStart < 10 ? ("0" + "" + monthStart) : monthStart) + "H2";
                    periods.add(p);
                }
                if (dayStart < 16) {
                    String p = yearStart + ""
                            + (monthStart < 10 ? ("0" + "" + monthStart) : monthStart) + "H2";
                    periods.add(p);

                }
            }
            for (int i = monthStart + 1; i < monthEnd; i++) {
                String h1 = yearStart + "" + (i < 10 ? ("0" + "" + i) : i) + "H1";
                String h2 = yearStart + "" + (i < 10 ? ("0" + "" + i) : i) + "H2";
                periods.add(h1);
                periods.add(h2);
            }
            if (dayEnd >= 16 && monthEnd > monthStart) {
                String p = yearEnd + "" + (monthEnd < 10 ? ("0" + "" + monthEnd) : monthEnd) + "H1";
                periods.add(p);
            }
        } else if (yearEnd > yearStart) {
            if (dayStart <= 16) {
                String p = yearStart + "" + (monthStart < 10 ? ("0" + "" + monthStart) : monthStart)
                        + "H2";
                periods.add(p);
            }
            for (int i = monthStart + 1; i < (monthEnd + 12); i++) {
                String h1 =
                        (i > 12 ? yearEnd : yearStart) + ""
                                + ((i > 12 ? (i - 12) : i) < 10
                                        ? ("0" + "" + (i > 12 ? (i - 12) : i))
                                        : (i > 12 ? (i - 12) : i))
                                + "H1";
                String h2 =
                        (i > 12 ? yearEnd : yearStart) + ""
                                + ((i > 12 ? (i - 12) : i) < 10
                                        ? ("0" + "" + (i > 12 ? (i - 12) : i))
                                        : (i > 12 ? (i - 12) : i))
                                + "H2";
                periods.add(h1);
                periods.add(h2);
            }
            if (dayEnd >= 16) {
                String p = yearEnd + "" + (monthEnd < 10 ? ("0" + "" + monthEnd) : monthEnd) + "H1";
                periods.add(p);
            }
        }

        if (CollectionUtils.isEmpty(periods)) {
            String emptyPeriods[] = {"null"};
            return Arrays.asList(emptyPeriods);
        }
        return periods;

    }

    @Override
    public void updateDiffForSettled(SellerSettlement sellerSettlement) throws ServiceException {
        String period = sellerSettlement.getSettlementPeriod();

        DiffAdjExample adjExample = new DiffAdjExample();
        adjExample.createCriteria().andCalcSettmentNoEqualTo(sellerSettlement.getSettlementNo())
                .andSettmentStatusEqualTo((short) OrderDictionaryEnum.DiffAdjStatus.notYet.getKey())
                // 差异调整时间在当前结算单结算结束期前
                .andAdjTimeLessThan(getStartEndDates(period)[1]);
        try {
            List list = diffAdjDAO.selectByExample(adjExample);
            if (CollectionUtils.isNotEmpty(list)) {
                for (Object o : list) {
                    DiffAdj adj = (DiffAdj) o;
                    adj.setSettmentDate(new Date());
                    diffAdjDAO.updateByPrimaryKey(adj);
                    // // 调用后台客户系统的接口结算差异金额到商家账户
                    // try {
                    // CustomerRemoteService remoteService =
                    // (CustomerRemoteService) RemoteTool.getRemote("03", "/customerRemoteService");
                    // BnesAcctTransactionQuery query = new BnesAcctTransactionQuery();
                    // query.setLoginId(Integer.parseInt(sellerId));
                    // query.setAmount(adj.getAdjMoney().doubleValue());
                    // query.setContent("订单后台结算差异调整金额");
                    // query.setType(17);
                    // remoteService.UpdateUserBalance(query, 2);
                    // } catch (Exception e) {
                    // throw new ServiceException(0, "订单后台结算差异调整金额操作异常：" + e.getMessage());
                    // }

                }
            }
        } catch (Exception e) {
            log.error(ErrorCode.INTER_SELLER_SETTLEMENT_ERROR, e);
        }
    }

    @Override
    public void sendMsg(String loginId, String period, String mobile) throws ServiceException {
        // TODO 短信接口通知：4) 在完成结出到商家余额动作时，发送通知短信到商家主账号的手机号码，短信内容为：
        // “您的201404H1期结算单已经结出到账户余额，请登录商家平台查看。康美健康”。
        try {
            List<Long> uidList = new ArrayList<Long>();
            uidList.add(Long.parseLong(loginId));
            List<String> mobilePhones = new ArrayList<String>();
            mobilePhones.add(mobile);
            KmMsg kmMsg = new KmMsg("6000", false);// 报文编号为1000,参数2为是否回复
            kmMsg.getMsgData().put("kmMsgType", MessageConstants.KMMSG_MOBIL);// (String)1
            kmMsg.getMsgData().put("smsmailType", EmailSendType.MSG_SETTLEMENT.getStatus());// 短信类型（int）35
            kmMsg.getMsgData().put("systemType", MessageConstants.KMMSG_SYSTEM_TYPE_B2B);// 1
            kmMsg.getMsgData().put("settlementNo", period);// 结算编号(String)
            kmMsg.getMsgData().put("mobilePhones", mobilePhones);// 手机号码
            kmMsg.getMsgData().put("msgSendType", MessageConstants.EM_SEND_TYPE_IMM);// (int)
                                                                                     // 1为立即发送，2为定时发送
            kmMsg.getMsgData().put("uidList", uidList);// 用户ID
            Sender.send(kmMsg, destination, jmsTemplate);
        } catch (Exception e) {
            throw new ServiceException(10001, "" + e.getMessage());
        }

    }

    @Override
    public String selectMobieByLoginId(SellerSettlement record) throws ServiceException {
        try {
            return sellerSettlementDAO.selectMobieByLoginId(record);
        } catch (Exception e) {
            throw new ServiceException(10001, "查询商家手机号码异常：" + e.getMessage());
        }
    }

    @Override
    public Long queryLoginIdByCommercialId(String sellerId) throws ServiceException {
        try {
            return sellerSettlementDAO.queryLoginIdByCommercialId(sellerId);
        } catch (Exception e) {
            throw new ServiceException(10001, "查询商家登录id异常：" + e.getMessage());
        }
    }

    @Override
    public List<Map<String, Object>> selectSettlementListForExport(
            SellerSettlementCriteria criteria) throws ServiceException {
        List<Map<String, Object>> list = null;
        try {

            list = sellerSettlementDAO.querySettlementListForExport(criteria);


        } catch (Exception e) {
            log.error("查询结算单导出数据出错 " + e.getMessage());
            throw new ServiceException("查询结算单导出数据出错 " + e.getMessage());
        }

        return list;
    }
}
