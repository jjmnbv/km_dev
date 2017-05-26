package com.pltfm.app.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.MerchantInfoOrSuppliers;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.dao.DiffAdjDAO;
import com.pltfm.app.dao.HurlFareDAO;
import com.pltfm.app.dao.HurlProductDAO;
import com.pltfm.app.dao.OrderItemDAO;
import com.pltfm.app.dao.OrderMainDAO;
import com.pltfm.app.dao.SettlementRefundDAO;
import com.pltfm.app.entities.DiffAdj;
import com.pltfm.app.entities.DiffAdjExample;
import com.pltfm.app.entities.ExportInfo;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.entities.OrderPayInfo;
import com.pltfm.app.entities.SellerSettlement;
import com.pltfm.app.entities.SellerSettlementCriteria;
import com.pltfm.app.maps.OrderSource;
import com.pltfm.app.service.DiffAdjService;
import com.pltfm.app.service.OrderQryService;
import com.pltfm.app.service.SellerSettlementService;
import com.pltfm.app.util.CreateExcelUtil;
import com.pltfm.app.util.SettlementAndPayUtil;
import com.pltfm.app.util.export.SellerOrderExportService;
import com.pltfm.app.vobject.CarryStockOutDetailVO;
import com.pltfm.app.vobject.CarryStockOutVO;
import com.pltfm.app.vobject.MerchantVo;
import com.pltfm.app.vobject.OrderMainVo;
/*删除总部会员对接   import com.pltfm.remote.service.Sync2BaseRemoteService;*/
import com.pltfm.sys.util.ErrorCode;

import oracle.sql.TIMESTAMP;

@Service("orderQryService")
@Scope("singleton")
@SuppressWarnings("unchecked")
public class OrderQryServiceImpl extends BaseService implements OrderQryService {
    private static Logger logger = Logger.getLogger(OrderQryServiceImpl.class);

    @Resource
    private OrderMainDAO orderMainDAO;
    @Resource
    private OrderItemDAO orderItemDAO;

    @Resource
    private HurlProductDAO hurlProductDAO;

    @Resource
    private HurlFareDAO hurlFareDAO;

    @Resource
    private SettlementRefundDAO settlementRefundDAO;

    @Resource
    private DiffAdjDAO diffAdjDAO;

    @Resource
    private SellerSettlementService sellerSettlementService;

    @Resource
    private DiffAdjService diffAdjService;

    public static final ExecutorService executors = Executors.newFixedThreadPool(50);// 线程池

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final DecimalFormat amountformat = new DecimalFormat("#####0.00");

    @Override
    public BigDecimal listCountMoney(Map<String, Object> map) throws ServiceException {
        try {
            return orderMainDAO.countMoneyByMap(map);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "获取订单列表总金额时发生异常："
                    + e.getMessage());
        }
    }

    @Override
    public BigDecimal listCountActual(Map<String, Object> map) throws ServiceException {
        try {
            return orderMainDAO.countActualByMap(map);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "获取订单列表实付总金额时发生异常："
                    + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public Integer listCount(Map<String, Object> map) throws ServiceException {
        try {
            return orderMainDAO.countByMap(map);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "获取订单列表计数时发生异常："
                    + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public List listOrder(Map<String, Object> map) throws ServiceException {
        try {
            List<OrderMainVo> data = orderMainDAO.selectByMap(map);
            List newData = new LinkedList();
            if (data.size() == 1) {
                // 主子订单
                OrderMainVo om = data.get(0);
                List<OrderMainVo> leafs =
                        orderMainDAO.selectRootAndLeafsByOrderCode(om.getOrderCode());
                newData.addAll(leafs);
            } else {
                Map<String, OrderMainVo> rootMap = new HashMap<String, OrderMainVo>();// 根订单集
                for (OrderMainVo om : data) {
                    OrderMainVo root;// 根订单
                    if (null != om.getRootOrderCode()) {// 根订单存在
                        root = rootMap.get(om.getRootOrderCode());
                        if (null == root) {// 根订单不在map中
                            root = getOrderByCode(om.getRootOrderCode());
                            newData.add(root);
                            rootMap.put(root.getOrderCode(), root);
                        }
                        newData.add(newData.indexOf(root) + 1, om);// 添加在根订单后面
                    } else {
                        newData.add(om);// 添加在最后面
                    }
                }
            }
            return newData;
        } catch (Exception e) {
            logger.error("获取订单列表时发生异常", e);
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "获取订单列表时发生异常："
                    + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public OrderMainVo getOrderByCode(String orderCode) throws ServiceException {
        try {
            return orderMainDAO.selectByOrderCode(orderCode);
        } catch (Exception e) {
            logger.error("获取订单发生错误", e);
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "获取订单" + orderCode + "："
                    + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public BigDecimal getOrderMoneyByCode(String orderCode) throws ServiceException {
        try {
            return orderMainDAO.selectOrderMoneyByOrderCode(orderCode);
        } catch (Exception e) {
            logger.error("获取订单发生错误", e);
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "获取订单" + orderCode + "："
                    + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public BigDecimal getOrderPaidDepositByCode(String orderCode) throws ServiceException {
        try {
            return orderMainDAO.selectOrderPaidDepositByOrderCode(orderCode);
        } catch (Exception e) {
            logger.error("获取订单发生错误", e);
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "获取订单" + orderCode + "："
                    + e.getMessage());
        }
    }


    @Override
    public List getOrderByAccount(String accountCode) throws ServiceException {
        return null;
    }

    @Override
    public Map countSKU(Date begin, Date end, List skuList) throws ServiceException {
        try {
            Map map = new HashMap();
            map.put("beginDate", begin);
            map.put("endDate", end);
            map.put("skuCodeList", skuList);
            return orderMainDAO.countSKU(map);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_ORDER_PRODUCT_DETAIL_ERROR, "sku出货量："
                    + e.getMessage());
        }
    }

    @Override
    public BigDecimal getPersonalConsume(Map map) throws ServiceException {
        BigDecimal result = null;
        try {
            result = orderMainDAO.getPersonalConsume(map);
        } catch (Exception e) {
            logger.error("查询时间范围内的客户消费情况异常", e);
            throw new ServiceException(ErrorCode.INNER_ORDER_DETAIL_ERROR, "查询时间范围内的客户消费情况异常："
                    + e.getMessage());
        }
        return result;
    }

    @Override
    public OrderMain getRootOrderByCode(String orderCode) throws ServiceException {
        try {
            return orderMainDAO.selectRootOrderByCode(orderCode);
        } catch (Exception e) {
            logger.error("查询根订单异常", e);
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "查询根订单异常："
                    + e.getMessage());
        }
    }

    @Override
    public Integer queryAnalysisAccount(Map<String, String> map) throws ServiceException {
        try {
            return this.orderMainDAO.queryAnalysisAccount(map);
        } catch (SQLException e) {
            logger.error("查询订单分析表总数异常", e);
            throw new ServiceException(ErrorCode.INNER_ORDER_ANALYSIS_REPORT_ERROR, "查询订单分析表总数异常："
                    + e.getMessage());
        }
    }

    @Override
    public List<Map<String, Object>> queryAnalysisReport(Map<String, String> map)
            throws ServiceException {
        try {
            return this.orderMainDAO.queryAnalysisReport(map);
        } catch (SQLException e) {
            logger.error("查询订单分析表异常", e);
            throw new ServiceException(ErrorCode.INNER_ORDER_ANALYSIS_REPORT_ERROR, "查询订单分析表异常："
                    + e.getMessage());
        }
    }

    @Override
    public String analysisReportExport(Map<String, String> map, Integer userId)
            throws ServiceException {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        try {
            result = this.orderMainDAO.analysisReportExport(map);
        } catch (SQLException e) {
            logger.error("查询订单分析表异常", e);
            throw new ServiceException(ErrorCode.INNER_ORDER_ANALYSIS_REPORT_ERROR, "查询订单分析表异常："
                    + e.getMessage());
        }
        List<String> lable = new ArrayList<String>();
        lable.add("区域");
        lable.add("订单总数");
        lable.add("总金额");
        lable.add("单均金额");
        lable.add("商品总数");
        lable.add("单均商品");
        lable.add("单均运费");
        lable.add("退换单数");
        List<String> key = new ArrayList<String>();
        key.add("PROVINCE");
        key.add("ORDERCOUNT");
        key.add("TOTALAMOUNT");
        key.add("AVGORDERAMOUNT");
        key.add("PRODUCTCOUNT");
        key.add("AVGPRODUCTORDER");
        key.add("AVGORDERFARE");
        key.add("VETO");
        return exportFile(userId.intValue(), "订单分析表", lable, key, result);
    }

    /**
     * 分页查询客户采购数据
     * 
     * @param map
     * @return
     * @throws ServiceException
     */
    @Override
    public List<Map<String, Object>> queryCustomerPurchaseAnalysisByPage(Map<String, String> map)
            throws ServiceException {
        try {
            return orderMainDAO.queryCustomerPurchaseAnalysisByPage(map);
        } catch (SQLException e) {
            logger.error("分页查询客户采购数据异常", e);
            throw new ServiceException(ErrorCode.INNER_ORDER_ANALYSIS_REPORT_ERROR, "分页查询客户采购数据异常："
                    + e.getMessage());
        }
    }

    /**
     * 查询客户采购数据条数
     * 
     * @param map
     * @return
     * @throws ServiceException
     */
    @Override
    public Integer countCustomerPurchaseAnalysis(Map<String, String> map) throws ServiceException {
        try {
            return orderMainDAO.countCustomerPurchaseAnalysis(map);
        } catch (SQLException e) {
            logger.error("查询客户采购数据条数异常", e);
            throw new ServiceException(ErrorCode.INNER_ORDER_ANALYSIS_REPORT_ERROR, "查询客户采购数据条数异常："
                    + e.getMessage());
        }
    }

    /**
     * 导出客户采购数据
     * 
     * @param map
     * @return
     * @throws ServiceException
     */
    @Override
    public String queryCustomerPurchaseAnalysis(Map<String, String> map, Integer uid)
            throws ServiceException {
        List<Map<String, Object>> result = null;
        try {
            result = orderMainDAO.queryCustomerPurchaseAnalysis(map);
            List<String> lable = new ArrayList<String>();
            lable.add("区域");
            lable.add("客户数");
            lable.add("采购数量");
            lable.add("采购金额");
            lable.add("客均采采购");
            lable.add("客均金额");
            lable.add("占比(%)");
            List<String> key = new ArrayList<String>();
            key.add("PROVINCE");
            key.add("CUSTOMERCOUNT");
            key.add("COMMODITYNUMBER");
            key.add("AMOUNTPAYABLE");
            key.add("AVGCOMMODITYNUMBER");
            key.add("AVGAMOUNTPAYABLE");
            key.add("CCRATIO");
            return exportFile(uid.intValue(), "客户采购分析报表", lable, key, result);
        } catch (SQLException e) {
            logger.error("导出客户采购分析报表异常", e);
            throw new ServiceException(ErrorCode.INNER_ORDER_ANALYSIS_REPORT_ERROR, "导出客户采购分析报表异常："
                    + e.getMessage());
        }
    }

    /**
     * 自营商城订单财务版
     * 
     * @param map
     * @param uid
     * @return
     * @throws ServiceException
     */
    @Override
    public String ExportFinanceOrderReport(Map<String, String> map, Integer uid)
            throws ServiceException {
        List<List<Map<String, Object>>> result = null;
        try {
            result = orderMainDAO.queryFinanceOrderReportData(map);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INNER_ORDER_ANALYSIS_REPORT_ERROR,
                    "导出自营商城订单财务版报表异常：" + e.getMessage());
        }
        HSSFWorkbook wb = new HSSFWorkbook();// excel
        if (null != result && !result.isEmpty()) {
            List<Map<String, Object>> rs1 = result.get(0);
            if (null != rs1 && !rs1.isEmpty()) {
                String[] columns =
                        {"销售日期", "订单完成时间", "下单账号", "订单来源", "订单渠道", "母订单号", "子订单号", "发货仓库", "供应商",
                                "供应商类型", "SKU编码", "品牌", "商品标题", "单价", "实收单价", "数量", "优惠前小计",
                                "实收小计", "PV值小计", "订单总额", "实收金额", "成本单价", "成本小计", "运费",
                                "是否开票", "发票类型", "发票抬头", "收货人", "收货地址", "收货人电话"};
                String[] key =
                        {"PAYDATE", "FINISHDATE", "CUSTOMERACCOUNT", "ORDERSOURCE", "ORDERCHANNEL",
                                "ORDERCODE", "CHILDCODE", "WAREHOUSENAME", "SUPPLIER",
                                "SUPPLIERTYPESTR", "COMMODITYSKU", "BRANDNAME", "COMMODITYTITLE",
                                "COMMODITYCALLEDPRICE", "COMMODITYUNITPRICE", "COMMODITYNUMBER",
                                "COMMODITYCALLEDSUM", "COMMODITYSUM", "COMMODITYPV",
                                "ORDERSUM", "AMOUNTPAYABLE",
                                "COMMODITYCOSTPRICE", "COMMODITYCOSTSUM", "FARE", "HASINVOICE",
                                "INVOICETYPE", "INVOICETITLE", "CONSIGNEE", "DELIVERYADDRESS",
                                "CELLPHONE"};
                // int[] colIndexs = {0, 1, 2, 3, 18, 19, 22, 23, 24, 25, 26, 27, 28};
                int[] colIndexs = {0, 2, 3, 4, 5, 20, 21, 24, 25, 26, 27, 28, 29, 30};
                writeOrderMapToSheet(wb, "自营商城订单财务版", columns, key, rs1, colIndexs);
            }
            rs1 = result.get(1);
            if (null != rs1 && !rs1.isEmpty()) {
                String[] columns =
                        {"退款完成日期", "订单来源", "订单渠道", "母订单号", "子订单号", "发货仓库", "供应商", "供应商类型", "SKU编码",
                                "商品标题", "单价", "实收单价", "数量", "优惠前小计", "实收小计", "PV值小计",
                                "订单总额", "退款流水", "退款平台", "退换数量", "退款金额", "退货返运费", "退换货实收小计", "成本小计",
                                "成本金额", "运费", "是否开票", "发票类型", "发票抬头", "收货人", "收货地址", "收货人电话"};
                String[] key =
                        {"TRADEDATE", "ORDERSOURCE", "ORDERCHANNEL", "PARENTORDERCODE",
                                "ORDERCODE", "WAREHOUSENAME", "CORPORATENAME", "SUPPLIERTYPESTR",
                                "COMMODITYSKU", "COMMODITYTITLE", "COMMODITYCALLEDPRICE",
                                "COMMODITYUNITPRICE", "COMMODITYNUMBER", "COMMODITYCALLEDSUM",
                                "COMMODITYSUM", "COMMODITYPV", "ORDERSUM",
                                "BATCHNO", "PAYPLATFORM", "ALTERNUM", "RETURNMONEY", "RETURNFARE",
                                "AMOUNTPAYABLE", "COMMODITYCOSTPRICE", "COMMODITYCOSTSUM", "FARE",
                                "HASINVOICE", "INVOICETYPE", "INVOICETITLE", "CONSIGNEE",
                                "DELIVERYADDRESS", "CELLPHONE"};
                writeSimpleDataToSheet(wb, "退款数据", columns, key, rs1);
            }
        }
        List<String> path = getExcelName(uid + "_export", ".xls");
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(path.get(0));
            wb.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INNER_ORDER_ANALYSIS_REPORT_ERROR,
                    "导出自营商城订单财务版报表异常：" + e.getMessage());
        }
        return path.get(1);

    }

    /**
     * 商家订单财务版
     * 
     * @param map
     * @param uid
     * @return
     * @throws ServiceException
     */
    @Override
    public String exportmerchantsOrderReport(List<Map<String, String>> mapList, Integer uid)
            throws ServiceException {
        List<Map<String, Object>> orderMaps = new ArrayList<Map<String, Object>>(); // 存储商家订单财务版数据
        List<Map<String, Object>> refundMaps = new ArrayList<Map<String, Object>>(); // 存储退款数据
        List<Map<String, Object>> diffAdjMaps = new ArrayList<Map<String, Object>>(); // 存储退款数据
        for (Map<String, String> map : mapList) {// 循环查询各个账期的数据，并保存在maps中，作为导入excel的数据
            List<List<Map<String, Object>>> resultList = null;
            try {
                resultList = orderMainDAO.queryMerchantsOrderReportData(map);
                if (null != resultList && resultList.size() > 0) {
                    List<Map<String, Object>> orderList = resultList.get(0);// 商家订单财务版数据
                    for (Map<String, Object> mapInfo : orderList) {
                        orderMaps.add(mapInfo);
                    }

                    // List<Map<String, Object>> refundList = resultList.get(1);// 退款数据
                    refundMaps = resultList.get(1);// 退款数据
                    /*
                     * 去除应结金额项 2016.1.8 // 应结货款 BigDecimal settlementLoan = BigDecimal.ZERO; // 实收金额
                     * BigDecimal amountPayable = BigDecimal.ZERO; // 退款金额 RETURNMONEY BigDecimal
                     * returnMoney = BigDecimal.ZERO; // 退货返运费 RETURNFARE BigDecimal returnFare =
                     * BigDecimal.ZERO;
                     * 
                     * 
                     * for (Map<String, Object> mapInfo : refundList) { // 实收金额 if (null !=
                     * mapInfo.get("AMOUNTPAYABLE")) { amountPayable = (BigDecimal)
                     * mapInfo.get("AMOUNTPAYABLE"); }
                     * 
                     * // 退款金额 if (null != mapInfo.get("RETURNMONEY")) { returnMoney = (BigDecimal)
                     * mapInfo.get("RETURNMONEY"); }
                     * 
                     * // 退货返运费 if (null != mapInfo.get("RETURNFARE")) { returnFare = (BigDecimal)
                     * mapInfo.get("RETURNFARE"); }
                     * 
                     * // 应结金额=实收金额-退款金额-退货返运费 settlementLoan =
                     * amountPayable.subtract(returnMoney.abs()).subtract(returnFare.abs());
                     * mapInfo.put("SETTLEMENT_LOAN", settlementLoan); refundMaps.add(mapInfo); }
                     */

                }
            } catch (Exception e) {
                throw new ServiceException(ErrorCode.INNER_ORDER_ANALYSIS_REPORT_ERROR,
                        "导出商家订单财务版报表异常：" + e.getMessage());
            }
        }
        Map<String, Object> diffMap = new HashMap<String, Object>();
        Map<String, String> mapStr = mapList.get(0);
        String supplier = mapStr.get("supplier");// 供应商名称
        String selectTimes = mapStr.get("selectTimes");// 时间集,保存一个或多个账期
        List<String> timeList = new ArrayList<String>();
        String[] times = selectTimes.split(",");
        for (int i = 0; i < times.length; i++) {
            timeList.add(times[i]);
        }
        diffMap.put("supplier", supplier);
        diffMap.put("selectTimes", timeList);
        try {
            diffAdjMaps = orderMainDAO.queryDiffAdjReportData(diffMap);
        } catch (SQLException e1) {

            e1.printStackTrace();
        }
        HSSFWorkbook wb = new HSSFWorkbook();// excel


        List<Map<String, Object>> rs1 = orderMaps;
        String[] columns =
                {"销售日期", "订单完成时间", "对应账期", "订单来源", "订单渠道", "母订单号", "子订单号", "发货仓库", "供应商", "供应商类型",
                        "品牌", "SKU编码", "商品标题", "单价", "实收单价", "数量", "优惠前小计", "实收小计", "PV值小计",
                         "订单总额", "实收金额", "佣金比例", "佣金", "运费", "应结货款", "是否开票", "发票类型",
                        "发票抬头", "收货人", "收货地址", "收货人电话"};
        String[] key =
                {"PAYDATE", "FINISHDATE", "ZQ", "ORDERSOURCE", "ORDERCHANNEL", "ORDERCODE",
                        "CHILDCODE", "WAREHOUSENAME", "SUPPLIER", "SUPPLIERTYPESTR", "BRANDNAME",
                        "COMMODITYSKU", "COMMODITYTITLE", "COMMODITYCALLEDPRICE",
                        "COMMODITYUNITPRICE", "COMMODITYNUMBER", "COMMODITYCALLEDSUM",
                        "COMMODITYSUM", "COMMODITYPV", "ORDERSUM",
                        "AMOUNTPAYABLE", "COMMISSION_RATE", "COMMISSION", "FARE",
                        "SETTLEMENT_LOAN", "HASINVOICE", "INVOICETYPE", "INVOICETITLE",
                        "CONSIGNEE", "DELIVERYADDRESS", "CELLPHONE"};
        int[] colIndexs = {0, 2, 3, 4, 5, 20, 21, 26, 27, 28, 29, 30, 31};
        writeOrderMapToSheet(wb, "商家订单财务版", columns, key, rs1, colIndexs);



        List<Map<String, Object>> rs2 = refundMaps;
        String[] columns1 =
                {"退款完成时间", "订单来源", "订单渠道", "母订单号", "子订单号", "发货仓库", "供应商", "供应商类型", "SKU编码", "商品标题",
                        "单价", "实收单价", "数量", "优惠前小计", "实收小计", "PV值小计", "订单总额", "退款流水",
                        "退款平台", "退换数量", "退款金额", "退货返运费", "退换货实收小计", "佣金比例", "佣金", "运费", "是否开票",
                        "发票类型", "发票抬头", "收货人", "收货地址", "收货人电话"};
        String[] key1 =
                {"TRADEDATE", "ORDERSOURCE", "ORDERCHANNEL", "PARENTORDERCODE", "ORDERCODE",
                        "WAREHOUSENAME", "CORPORATENAME", "SUPPLIERTYPESTR", "COMMODITYSKU",
                        "COMMODITYTITLE", "COMMODITYCALLEDPRICE", "COMMODITYUNITPRICE",
                        "COMMODITYNUMBER", "COMMODITYCALLEDSUM", "COMMODITYSUM", "COMMODITYPV",
                        "ORDERSUM", "BATCHNO", "PAYPLATFORM", "ALTERNUM",
                        "RETURNMONEY", "RETURNFARE", "AMOUNTPAYABLE", "COMMISSION_RATE",
                        "COMMISSION", "FARE", "HASINVOICE", "INVOICETYPE", "INVOICETITLE",
                        "CONSIGNEE", "DELIVERYADDRESS", "CELLPHONE"};
        writeSimpleDataToSheet(wb, "退款数据", columns1, key1, rs2);


        /* 差异调整明细导出表列名 */
        // String[] columns2 = {"发生账期", "调整人", "调整说明", "调整金额", "调整发生时间"};
        // String[] key2 = {"SETTLEMENT_PERIOD", "USER_NAME", "ADJ_DETAIL", "ADJ_MONEY",
        // "ADJ_TIME"};
        List<String> tzcolumns = new ArrayList<String>();
        tzcolumns.add("发生账期");
        tzcolumns.add("调整人");
        tzcolumns.add("调整说明");
        tzcolumns.add("调整金额");
        tzcolumns.add("调整发生时间");

        List<String> tzkeys = new ArrayList<String>();
        tzkeys.add("SETTLEMENT_PERIOD");
        tzkeys.add("USER_NAME");
        tzkeys.add("ADJ_DETAIL");
        tzkeys.add("ADJ_MONEY");
        tzkeys.add("ADJ_TIME");

        // writeSimpleDataToSheet(wb, "差异调整", columns2, key2, diffAdjMaps);
        this.writejsSheet(wb, "差异调整明细", tzcolumns, tzkeys, diffAdjMaps);

        // List<String> path = getExcelName(uid + "_export", ".xls");
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        List<String> path =
                getExcelName2("商家订单财务版_" + supplier + "_" + sdf.format(cal.getTime()), ".xls");
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(path.get(0));
            wb.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INNER_ORDER_ANALYSIS_REPORT_ERROR,
                    "导出商家订单财务版报表异常：" + e.getMessage());
        }
        return path.get(1);

    }

    private void writeSimpleDataToSheet(HSSFWorkbook wb, String SheetName, String[] columns,
            String[] keys, List<Map<String, Object>> rs) {
        // 列名样式 水平居中 垂直居中 12px出题黑楷体文字
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setFontName("楷体");
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.BLACK.index);
        cellStyle.setFont(font);
        // 数据样式 水平居中 垂直居中 默认字体大小
        HSSFCellStyle cellStyleCon = wb.createCellStyle();
        cellStyleCon.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleCon.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        HSSFSheet sheet = wb.createSheet(SheetName);
        int max = columns.length;
        for (int i = 0; i < max; i++) {
            sheet.setColumnWidth(i, 4500);
        }

        // 标题
        CreateExcelUtil excel = new CreateExcelUtil(wb, sheet);
        int rowIndex = 0;// 行下标
        HSSFRow row = sheet.createRow(rowIndex++);
        for (int i = 0; i < max; i++) {
            excel.cteateCell(wb, row, i, cellStyle, columns[i]);
        }

        for (Map temp : rs) {
            String pv = String.valueOf(temp.get("COMMODITYPV"));
            String num = String.valueOf(temp.get("ALTERNUM"));
            String orderSource = String.valueOf(temp.get("ORDERSOURCE"));
            String costinComeMoney = String.valueOf(temp.get("COSTINCOMEMONEY"));
            if (costinComeMoney == null || costinComeMoney.equals("null")) {
                costinComeMoney = "0";
            }
            if (orderSource != null && !orderSource.equals("")) {
                temp.put("ORDERSOURCE", OrderSource.getValueByKey(orderSource));
            }
            if (pv == null || pv.isEmpty() || Double.valueOf(pv) == 0 || costinComeMoney == null) {
                pv = "0";
                temp.put("COSTINCOMEMONEY", "0");
            }
            if (num != null && !num.isEmpty() && !num.equals("null")) {
                pv = String.valueOf((Long.valueOf(num) * Float.valueOf(pv)));

                costinComeMoney =
                        String.valueOf((Long.valueOf(num) * Float.valueOf(costinComeMoney)));
                temp.put("COMMODITYPV", pv);
                temp.put("COSTINCOMEMONEY", costinComeMoney);
            }

            row = sheet.createRow(rowIndex++);
            for (int i = 0; i < max; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(cellStyleCon);
                getStringValue2(cell, temp.get(keys[i]));
            }
        }
    }

    /**
     * 将OrderMap写入sheet
     * 
     * @param wb
     * @param SheetName
     * @param columns
     * @param keys
     * @param rs
     * @param colIndexs
     */
    private void writeOrderMapToSheet(HSSFWorkbook wb, String SheetName, String[] columns,
            String[] keys, List<Map<String, Object>> rs, int[] colIndexs) {
        // 列名样式 水平居中 垂直居中 12px出题黑楷体文字
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setFontName("楷体");
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.BLACK.index);
        cellStyle.setFont(font);
        // cellStyle.setWrapText(true);

        // 数据样式 水平居中 垂直居中 默认字体大小
        HSSFCellStyle cellStyleCon = wb.createCellStyle();
        cellStyleCon.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleCon.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        // cellStyleCon.setWrapText(true);

        HSSFSheet sheet = wb.createSheet(SheetName);
        sheet.autoSizeColumn(1, true);
        int max = columns.length;
        for (int i = 0; i < max; i++) {
            sheet.setColumnWidth(i, 4500);
        }

        // 标题
        CreateExcelUtil excel = new CreateExcelUtil(wb, sheet);
        int rowIndex = 0;// 行下标
        HSSFRow row = sheet.createRow(rowIndex++);
        for (int i = 0; i < max; i++) {
            excel.cteateCell(wb, row, i, cellStyle, columns[i]);
        }

        String orderCode = null;
        int colspan = 0;
        for (Map temp : rs) {
            String num = String.valueOf(temp.get("COMMODITYNUMBER"));
            String tempCode = String.valueOf(temp.get("ORDERCODE"));
            String pv = String.valueOf(temp.get("COMMODITYPV"));
            String costinComeMoney = String.valueOf(temp.get("COSTINCOMEMONEY"));
            if (costinComeMoney == null || costinComeMoney.equals("null")) {
                costinComeMoney = "0";
            }
            if (pv == null || pv.isEmpty() || Double.valueOf(pv) == 0 || costinComeMoney == null) {
                pv = "0";
                temp.put("COSTINCOMEMONEY", "0");
            }
            if (num != null && !num.isEmpty() && !num.equals("null")) {
                pv = String.valueOf((Long.valueOf(num) * Float.valueOf(pv)));
                costinComeMoney =
                        String.valueOf((Long.valueOf(num) * Float.valueOf(costinComeMoney)));
                temp.put("COMMODITYPV", pv);
                temp.put("COSTINCOMEMONEY", costinComeMoney);
            }
            String orderSource = String.valueOf(temp.get("ORDERSOURCE"));

            if (orderSource != null && !orderSource.equals("")) {
                temp.put("ORDERSOURCE", OrderSource.getValueByKey(orderSource));
            }

            if (null != orderCode && !tempCode.equals(orderCode)) {
                if (colspan > 1) {
                    for (int cos : colIndexs) {
                        sheet.addMergedRegion(new CellRangeAddress(rowIndex - colspan,
                                rowIndex - 1, cos, cos));
                    }
                }
                colspan = 0;
            }
            colspan++;
            row = sheet.createRow(rowIndex++);
            for (int i = 0; i < max; i++) {
                if (tempCode.equals(orderCode) && Arrays.binarySearch(colIndexs, i) >= -1) {
                    continue;
                }
                Cell cell = row.createCell(i);
                cell.setCellStyle(cellStyleCon);
                getStringValue2(cell, temp.get(keys[i]));
            }
            orderCode = tempCode;
        }
        if (colspan > 1) {
            for (int cos : colIndexs) {
                sheet.addMergedRegion(new CellRangeAddress(rowIndex - colspan, rowIndex - 1, cos,
                        cos));
            }
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public List listFuzzyOrder(Map<String, Object> map) throws ServiceException {
        try {
            List<OrderMainVo> data = null;
            if (null != map.get("source")) {
                data = orderMainDAO.selectOutFuzzyOrderByMap(map);
            } else {
                data = orderMainDAO.selectFuzzyOrderByMap(map);
            }
            List newData = new LinkedList();
            if (data.size() == 1) {
                if (null != map.get("source")) {
                    newData.addAll(data);
                } else {
                    // 主子订单
                    OrderMainVo om = data.get(0);
                    List<OrderMainVo> leafs;
                    if(map.get("fkChange")!=null&&map.get("fkChange").toString().equals("changeSort"))
                        leafs =
                            orderMainDAO.selectRootAndLeafsByOrderCodeASCSort(om.getOrderCode());
                    else 
                        leafs =
                        orderMainDAO.selectRootAndLeafsByOrderCode(om.getOrderCode());
                    newData.addAll(leafs);
                }
            } else {
//                Map<String, OrderMainVo> rootMap = new HashMap<String, OrderMainVo>();// 根订单集
                Map<String, OrderMainVo> orderMap = new HashMap<String, OrderMainVo>();// 子单集
                List<String> rootOrderCodes = new ArrayList<String>();
                for (OrderMainVo om : data) {
//                    OrderMainVo root;// 根订单
                    if (null != om.getRootOrderCode()) {// 根订单存在
                        rootOrderCodes.add(om.getRootOrderCode());// 搜集父订单号用于查询
                        orderMap.put(om.getRootOrderCode() + om.getOrderCode(), om); // 保存子订单，用于后面加入列表
                        // root = rootMap.get(om.getRootOrderCode());
                        // if (null == root) {// 根订单不在map中
                        // root = getOrderByCode(om.getRootOrderCode());
                        // newData.add(root);
                        // rootMap.put(root.getOrderCode(), root);
                        // }
                        // newData.add(newData.indexOf(root) + 1, om);// 添加在根订单后面
                    } else {
                        newData.add(om);// 添加在最后面
                    }
                }
                if (null != rootOrderCodes && rootOrderCodes.size() > 0) {
                    Map<String, Object> rootMaps = new HashMap<String, Object>();
                    List<OrderMainVo> oms;
                    if(map.get("fkChange")!=null&&map.get("fkChange").toString().equals("changeSort"))
                        oms = this.getOrdersByCodes(rootOrderCodes);// 父订单;
                    else 
                        oms = this.getOrdersByCodesASCSort(rootOrderCodes,map);// 父订单;                    
                    if (null != oms && oms.size() > 0) {
                        for (OrderMainVo om : oms) {
                            rootMaps.put(om.getOrderCode(), om);
                        }
                        newData.addAll(oms); // 添加父订单
                        for (String key : orderMap.keySet()) {
                            OrderMainVo rootOrder =
                                    (OrderMainVo) rootMaps.get(key.subSequence(0, 18));
                            if (rootOrder == null) {
                                throw new Exception("取得父订单失败！");
                            }
                            newData.add(newData.indexOf(rootOrder) + 1, orderMap.get(key));// 子订单添加在父订单后面
                        }
                    }

                }

            }
            return newData;
        } catch (Exception e) {
            logger.error("获取订单列表时发生异常", e);
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "获取订单列表时发生异常："
                    + e.getMessage());
        }
    }

    private List<OrderMainVo> getOrdersByCodesASCSort(List<String> orderCodes, Map<String, Object> map) throws ServiceException{
        
        Map<String, Object> mapCodes = new HashMap<String, Object>();
        mapCodes.put("orderCodes", orderCodes);
        List<OrderMainVo> oms = new ArrayList<OrderMainVo>();
        try {
            oms = orderMainDAO.getOrdersByCodesASCSort(mapCodes);
            return oms;
        } catch (Exception e) {
            logger.error("获取父订单信息出错！", e);
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "获取父订单信息出错！"
                    + e.getMessage());
        }
    }

    @Override
    public List<OrderMainVo> getOrdersByCodes(List<String> orderCodes) throws ServiceException {
        Map<String, Object> mapCodes = new HashMap<String, Object>();
        mapCodes.put("orderCodes", orderCodes);
        List<OrderMainVo> oms = new ArrayList<OrderMainVo>();
        try {
            oms = orderMainDAO.getOrdersByCodes(mapCodes);
            return oms;
        } catch (Exception e) {
            logger.error("获取父订单信息出错！", e);
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "获取父订单信息出错！"
                    + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public Integer listCountFuzzyOrder(Map<String, Object> map) throws ServiceException {
        try {
            if (null != map.get("source")) {
                return orderMainDAO.countOutFuzzyOrderByMap(map);
            } else {
                return orderMainDAO.countFuzzyOrderByMap(map);
            }
        } catch (Exception e) {
            logger.error("获取订单列表时发生异常", e);
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "获取订单列表计数时发生异常："
                    + e.getMessage());
        }
    }

    @Override
    public BigDecimal listCountFuzzyOrderActual(Map<String, Object> paramMap)
            throws ServiceException {
        try {
            if (null != paramMap.get("source")) {
                return orderMainDAO.countOutFuzzyOrderActualByMap(paramMap);
            } else {
                return orderMainDAO.countFuzzyOrderActualByMap(paramMap);
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "获取订单列表实付总金额时发生异常："
                    + e.getMessage());
        }
    }

    @Override
    public BigDecimal listCountFuzzyOrderMoney(Map<String, Object> paramMap)
            throws ServiceException {
        try {
            if (null != paramMap.get("source")) {
                return orderMainDAO.countOutFuzzyOrderMoneyByMap(paramMap);
            } else {
                return orderMainDAO.countFuzzyOrderMoneyByMap(paramMap);
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "获取订单列表总金额时发生异常："
                    + e.getMessage());
        }
    }

    @Override
    public BigDecimal listCountFuzzyOrderRebateMoney(Map<String, Object> paramMap)
            throws ServiceException {
        try {
            return orderMainDAO.countOutFuzzyOrderRebateMoneyByMap(paramMap);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "获取订单列表总金额时发生异常："
                    + e.getMessage());
        }
    }



    @Override
    public Map listCommercial(Map<String, Object> map) throws ServiceException {
        try {
            Map mmap = new LinkedHashMap<String, Object>();
            List<MerchantVo> omList = orderMainDAO.selectCommercialByMap(map);
            for (MerchantVo vo : omList) {
                mmap.put(vo.getMerchantId(), vo.getMerchantName());
            }
            return mmap;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException(ErrorCode.DB_ERROR, "获取所属商家发生异常：" + e.getMessage());
        }
    }

    @Override
    public Map listOrderData(Map<String, Object> map) throws ServiceException {
        try {
            Map mapp = new HashMap();
            mapp.put("orderMain", orderMainDAO.selectOrderDataByMap(map));
            mapp.put("orderMoney", orderMainDAO.countFuzzyOrderMoneyByMap2(map));
            mapp.put("orderActual", orderMainDAO.countFuzzyOrderActualByMap2(map));
            return mapp;
        } catch (SQLException e) {
            logger.error("获取订单列表时发生异常", e);
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "获取订单列表时发生异常："
                    + e.getMessage());
        }
    }

    /**
     * 批量根据订单号查询出库信息
     * 
     * @param orderCodes
     * @return
     * @throws ServiceException
     */
    @Override
    public List<CarryStockOutVO> queryCarryStockOutVOByOrderCodes(List<String> orderCodes)
            throws ServiceException {
        List<CarryStockOutVO> rsList = null;
        try {
            List<OrderMain> dataList = orderMainDAO.queryOrderByOrderCodes(orderCodes);
            List<OrderItem> oiList = orderItemDAO.queryOrderItemByOrderCodes(orderCodes);
            if (null != dataList && !dataList.isEmpty() && null != oiList) {
                rsList = new ArrayList<CarryStockOutVO>();
                Map<String, List<CarryStockOutDetailVO>> oiMap =
                        new HashMap<String, List<CarryStockOutDetailVO>>();
                for (OrderItem oi : oiList) {
                    List<CarryStockOutDetailVO> csovList = oiMap.get(oi.getOrderCode());
                    if (null == csovList) {
                        csovList = new ArrayList<CarryStockOutDetailVO>();
                    }
                    csovList.add(SettlementAndPayUtil.buildCarryStockOutDetailVO(oi));
                    oiMap.put(oi.getOrderCode(), csovList);
                }
                for (OrderMain omv : dataList) {
                    CarryStockOutVO csov = SettlementAndPayUtil.buildCarryStockOutVO(omv);
                    csov.setDetailList(oiMap.get(omv.getOrderCode()));
                    rsList.add(csov);
                }
            }
        } catch (SQLException e) {
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "获取订单列表时发生异常："
                    + e.getMessage());
        }
        return rsList;
    }

    /**
     * 跟进订单号查询该订单用户购买次数
     * 
     * @param orderCode
     * @return userId_buyNum
     * @throws ServiceException
     */
    @Override
    public String queryUserBuyNumByOrderCode(String orderCode) throws ServiceException {
        try {
            return orderMainDAO.queryUserBuyNumByOrderCode(orderCode);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "跟进订单号查询该订单用户购买次数发生异常"
                    + e.getMessage());
        }
    }

    /**
     * 导出时代订单数据
     * 
     * @param params
     * @return
     * @throws ServiceException
     */
    @Override
    public Map<String, Object> queryTimesOrderForExport(Map<String, String> params)
            throws ServiceException {
        try {
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("SaleInfo", orderMainDAO.queryTimesSaleInfo(params));// 销售数据
            result.put("ProvinceOrder", orderMainDAO.queryTimesOrderStatisticsByProvince(params));// 省份订单
            result.put("SaleVolume", orderMainDAO.queryRankProductBySaleVolume(params));// 销量前*数据
            result.put("SaleAmount", orderMainDAO.queryRankProductBySaleAmount(params));// 销售额前*数据
            return result;
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "导出时代订单数据发生异常"
                    + e.getMessage());
        }
    }

    /**
     * 获取商家信息
     * 
     * @return
     * @throws ServiceException
     */
    @Override
    public Map<String, String> queryCommerceInfo(String commerceId) throws ServiceException {
        try {
            return orderMainDAO.queryCommerceInfo(commerceId);
        } catch (SQLException e) {
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "获取商家信息发生异常"
                    + e.getMessage());
        }
    }

    /**
     * 导出结算单详情
     */
    @Override
    public String exportDetailOrder(String sno, Integer uid, SellerSettlement sellerSettlement,
            List<DiffAdj> diffList) throws ServiceException {
        return this.exportDetailOrderFile(sno, uid, sellerSettlement, diffList);

    }

    public String exportDetailOrderFile(String sno, int uid, SellerSettlement sellerSettlement,
            List<DiffAdj> diffList) throws ServiceException {

        HSSFWorkbook wb = new HSSFWorkbook();// excel
        // 列名样式 水平居中 垂直居中 12px出题黑楷体文字
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setFontName("楷体");
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.BLACK.index);
        cellStyle.setFont(font);
        // 数据样式 水平居中 垂直居中 默认字体大小
        HSSFCellStyle cellStyleCon = wb.createCellStyle();
        cellStyleCon.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleCon.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        // 数据样式 水平靠右，垂直居中，默认字体大小
        HSSFCellStyle cellStyleCon2 = wb.createCellStyle();
        cellStyleCon2.setAlignment(CellStyle.ALIGN_LEFT);
        cellStyleCon2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        /* 结算单 */
        HSSFSheet sheet = wb.createSheet("结算单");
        for (int i = 0; i < 8; i++) {
            sheet.setColumnWidth(i, 6000);
        }

        DecimalFormat df = new DecimalFormat("#####0.00");
        // 结算单表格数据 -----begin
        String settlementNo = "";// 结算单号
        String settlementPeriod = "";// 结算账期
        String shopName = ""; // 商家/店铺 info.settlementStatus
        String settlementStatus = ""; // 结算状态
        String sellerConfirmation = ""; // 商家确认意见
        String operateConfirmation = ""; // 运营确认意见
        String financialConfirmation = ""; // 财务审核意见
        String receiveSum = ""; // 妥投实收
        String refundSum = ""; // 退款金额
        String fareSum = ""; // 妥投运费
        String refundFareSum = "";// 退货返运费
        String commissionSum = ""; // 妥投佣金
        String refundComSum = ""; // 退款佣金
        String addpvsum = ""; //妥投推广服务费
        String returnpvsum = "";//退款返推广服务费
        String diffAdjSum = ""; // 差异调整明细
        String qnhktotal = "";// 期内货款汇总 ( receiveSum - refundSum)
        String qnyjtotal = "";// 期内佣金汇总 (refundComSum-commissionSum)
        String qntgfwtotal = "";//期内推广费用汇总
        String yunftotal = "";// 运费汇总(fareSum - feturnFareSum)
        String total = ""; // 本期应结金额(currSettleAccounts+diffAdjSum)
        if (null != sellerSettlement) {
            settlementNo = sellerSettlement.getSettlementNo();
            settlementPeriod =
                    sellerSettlement.getSettlementPeriod() + "("
                            + sellerSettlement.getSettlementPeriodExp() + ")";
            shopName =
                    sellerSettlement.getShopName() != null
                            ? sellerSettlement.getShopName()
                            : sellerSettlement.getSellerName();
            Short Status = sellerSettlement.getSettlementStatus();
            if (null != Status) {
                if (Status == 1) {
                    settlementStatus = "未确认";
                } else if (Status == 2) {
                    settlementStatus = "商家已确认";
                } else if (Status == 3) {
                    settlementStatus = "运营已确认";
                } else if (Status == 4) {
                    settlementStatus = "财务审核通过";
                } else if (Status == 5) {
                    settlementStatus = "财务审核拒绝";
                } else if (Status == 6) {
                    settlementStatus = "已结出";
                }
            }

            sellerConfirmation = sellerSettlement.getSellerConfirmation();

            operateConfirmation = sellerSettlement.getOperateConfirmation();

            financialConfirmation = sellerSettlement.getFinancialConfirmation();

            Double receive =
                    sellerSettlement.getReceiveSum() == null ? 0 : sellerSettlement.getReceiveSum();
            receiveSum = receive == 0 ? "0.00" : "+" + df.format(receive);

            Double refund =
                    sellerSettlement.getRefundSum() == null ? 0 : sellerSettlement.getRefundSum();
            refundSum = refund == 0 ? "0.00" : "-" + df.format(refund);

            Double fare = sellerSettlement.getFareSum() == null ? 0 : sellerSettlement.getFareSum();
            fareSum = fare == 0 ? "0.00" : "+" + df.format(fare);

            Double refundFare =
                    sellerSettlement.getReturnFareSum() == null ? 0 : sellerSettlement
                            .getReturnFareSum();
            refundFareSum = refundFare == 0 ? "0.00" : "-" + df.format(refundFare);

            Double commission =
                    sellerSettlement.getCommissionSum() == null ? 0 : sellerSettlement
                            .getCommissionSum();
            commissionSum = commission == 0 ? "0.00" : "-" + df.format(commission);

            Double refundCom =
                    sellerSettlement.getRefundComSum() == null ? 0 : sellerSettlement
                            .getRefundComSum();
            refundComSum = refundCom == 0 ? "0.00" : "+" + df.format(refundCom);
            
            Double apv = sellerSettlement.getAddpvsum();
            addpvsum = apv == 0  ? "0.00" : "-"+df.format(apv);
            
            Double rpv = sellerSettlement.getReturnpvsum();
            returnpvsum = rpv == 0 ? "0.00" : "+"+df.format(rpv);

            qntgfwtotal = (apv-rpv) == 0 ? "0.00" : ((apv-rpv) > 0 ? "-"+df.format(apv-rpv): "+"+df.format(rpv-apv));
                            
                            
            Double diffAdj =
                    sellerSettlement.getDiffAdjSum() == null ? 0 : sellerSettlement.getDiffAdjSum();
            diffAdjSum = diffAdj == 0 ? "0.00" : df.format(diffAdj);

            qnhktotal =
                    (receive - refund) == 0 ? "0.00" : (receive - refund >= 0 ? "+" : "")
                            + df.format(receive - refund);
            qnyjtotal =
                    (refundCom - commission) == 0 ? "0.00" : (refundCom - commission >= 0
                            ? "+"
                            : "") + df.format(refundCom - commission);
            yunftotal =
                    (fare - refundFare) == 0 ? "0.00" : (fare - refundFare >= 0 ? "+" : "")
                            + df.format(fare - refundFare);
            Double currSettleAccounts =
                    sellerSettlement.getCurrSettleAccounts() == null ? 0 : sellerSettlement
                            .getCurrSettleAccounts();
            total =
                    (currSettleAccounts + diffAdj) == 0
                            ? "0.00"
                            : (currSettleAccounts + diffAdj >= 0 ? "+" : "")
                                    + df.format(currSettleAccounts + diffAdj);
        }else{
            throw new ServiceException("导出结算单详情失败!sno = "+sno);
        }
        // 结算单表格数据 -----end

        // 结算单表格生成 -- begin
        CreateExcelUtil excel = new CreateExcelUtil(wb, sheet);
        HSSFRow row = null;
        List<String> lable = new ArrayList<String>();
        lable.add("结算单号");
        lable.add("结算账期");
        lable.add("商家/店铺");
        lable.add("结算状态");
        lable.add("商家确认意见");
        lable.add("运营确认意见");
        lable.add("财务审核意见");

        int leng = 0;
        if (null != diffList) {
            leng = diffList.size();
        }

        for (int i = 0; i < 12 + leng; i++) { // 创建行,其中前12行是固定的，12行以后是动态的
            row = sheet.createRow(i);
            if (i < 7) {
                excel.cteateCell(wb, row, 0, cellStyle, lable.get(i));
                sheet.addMergedRegion(new CellRangeAddress(i, i, 1, 8));
                if (i == 0) {//
                    excel.cteateCell(wb, row, 1, cellStyleCon2, settlementNo);
                } else if (i == 1) {
                    excel.cteateCell(wb, row, 1, cellStyleCon2, settlementPeriod);
                } else if (i == 2) {
                    excel.cteateCell(wb, row, 1, cellStyleCon2, shopName);
                } else if (i == 3) {
                    excel.cteateCell(wb, row, 1, cellStyleCon2, settlementStatus);
                } else if (i == 4) {
                    excel.cteateCell(wb, row, 1, cellStyleCon2, sellerConfirmation);
                } else if (i == 5) {
                    excel.cteateCell(wb, row, 1, cellStyleCon2, operateConfirmation);
                } else if (i == 6) {
                    excel.cteateCell(wb, row, 1, cellStyleCon2, financialConfirmation);
                }
            } else if (i == 7) {
                excel.cteateCell(wb, row, 0, cellStyle, "结算金额");

                excel.cteateCell(wb, row, 1, cellStyle, "期内货款汇总");

                excel.cteateCell(wb, row, 3, cellStyle, "运费汇总");

                excel.cteateCell(wb, row, 5, cellStyle, "期内佣金汇总");
                
                excel.cteateCell(wb, row, 7, cellStyle, "期内推广服务费汇总");

                excel.cteateCell(wb, row, 9, cellStyle, "差异调整");

                excel.cteateCell(wb, row, 10, cellStyle, "本期应结金额");
            } else if (i == 8) {
                excel.cteateCell(wb, row, 1, cellStyle, "妥投实收");
                excel.cteateCell(wb, row, 2, cellStyle, "退款金额");
                excel.cteateCell(wb, row, 3, cellStyle, "妥投运费");
                excel.cteateCell(wb, row, 4, cellStyle, "退货返运费");
                excel.cteateCell(wb, row, 5, cellStyle, "妥投佣金");
                excel.cteateCell(wb, row, 6, cellStyle, "退款佣金");
                excel.cteateCell(wb, row, 7, cellStyle, "妥投推广服务费");
                excel.cteateCell(wb, row, 8, cellStyle, "退款返推广服务费");
                excel.cteateCell(wb, row, 9, cellStyle, "明细");
            } else if (i == 9) {
                excel.cteateCell(wb, row, 1, cellStyleCon, receiveSum);
                excel.cteateCell(wb, row, 2, cellStyleCon, refundSum);
                excel.cteateCell(wb, row, 3, cellStyleCon, fareSum);
                excel.cteateCell(wb, row, 4, cellStyleCon, refundFareSum);
                excel.cteateCell(wb, row, 5, cellStyleCon, commissionSum);
                excel.cteateCell(wb, row, 6, cellStyleCon, refundComSum);
                excel.cteateCell(wb, row, 7, cellStyleCon, addpvsum);
                excel.cteateCell(wb, row, 8, cellStyleCon, returnpvsum);
                excel.cteateCell(wb, row, 9, cellStyleCon, diffAdjSum);
            } else if (i == 10) {
                excel.cteateCell(wb, row, 1, cellStyleCon, qnhktotal);
                excel.cteateCell(wb, row, 3, cellStyleCon, yunftotal);
                excel.cteateCell(wb, row, 5, cellStyleCon, qnyjtotal);
                excel.cteateCell(wb, row, 7, cellStyleCon, qntgfwtotal);
                excel.cteateCell(wb, row, 9, cellStyleCon, diffAdjSum);
                excel.cteateCell(wb, row, 10, cellStyleCon, total);
            } else if (i == 11) {
                excel.cteateCell(wb, row, 0, cellStyle, "本账期发起差异\r\n调整明细");
                excel.cteateCell(wb, row, 1, cellStyle, "调整项ID");
                excel.cteateCell(wb, row, 2, cellStyle, "调整说明");
                excel.cteateCell(wb, row, 4, cellStyle, "调整时间");
                excel.cteateCell(wb, row, 5, cellStyle, "调整人");
                excel.cteateCell(wb, row, 6, cellStyle, "结算状态");
                excel.cteateCell(wb, row, 7, cellStyle, "调整金额");
            } else if (i > 11) {// 本账期发起差异调整明细数据项
                if (null != diffList && diffList.size() > 0) {
                    excel.cteateCell(wb, row, 1, cellStyleCon,
                            diffList.get(i - 12).getDiffAdjId() == null ? "" : diffList.get(i - 12)
                                    .getDiffAdjId().toString());
                    excel.cteateCell(wb, row, 2, cellStyleCon,
                            diffList.get(i - 12).getAdjDetail() == null ? "" : diffList.get(i - 12)
                                    .getAdjDetail().toString()
                                    + (diffList.get(i - 12).getAdjTitle() == null ? "" : diffList
                                            .get(i - 12).getAdjTitle().toString()));

                    Date theDate = diffList.get(i - 12).getAdjTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
                    String dateStr = "";
                    if (null != theDate) {
                        dateStr = sdf.format(theDate);
                    }
                    excel.cteateCell(wb, row, 4, cellStyleCon, dateStr);
                    excel.cteateCell(wb, row, 5, cellStyleCon,
                            diffList.get(i - 12).getUserName() == null ? "" : diffList.get(i - 12)
                                    .getUserName().toString());
                    excel.cteateCell(wb, row, 6, cellStyleCon, diffList.get(i - 12)
                            .getSettmentStatus() == null ? "" : (diffList.get(i - 12)
                            .getSettmentStatus() == 0 ? "未结算" : "已计入【"
                            + diffList.get(i - 12).getCalcSettmentPeriod() + "】账期"));
                    excel.cteateCell(
                            wb,
                            row,
                            7,
                            cellStyleCon,
                            diffList.get(i - 12).getAdjMoney() == null ? "0.00" : df
                                    .format(diffList.get(i - 12).getAdjMoney()));

                    sheet.addMergedRegion(new CellRangeAddress(i, i, 2, 3));
                }

            }
        }
        // 结算单表格生成 -- end


        // 结算单表格合并单元格 -- begin
        sheet.addMergedRegion(new CellRangeAddress(7, 10, 0, 0));

        sheet.addMergedRegion(new CellRangeAddress(11, 12 + leng, 0, 0));

        sheet.addMergedRegion(new CellRangeAddress(10, 10, 1, 2));

        sheet.addMergedRegion(new CellRangeAddress(10, 10, 3, 4));

        sheet.addMergedRegion(new CellRangeAddress(10, 10, 5, 6));
        
        sheet.addMergedRegion(new CellRangeAddress(10, 10, 7, 8));

        sheet.addMergedRegion(new CellRangeAddress(7, 7, 1, 2));

        sheet.addMergedRegion(new CellRangeAddress(7, 7, 3, 4));

        sheet.addMergedRegion(new CellRangeAddress(7, 7, 5, 6));
        
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 7, 8));

        sheet.addMergedRegion(new CellRangeAddress(11, 11, 2, 3));
        // 结算单表格合并单元格 -- end

        /* 妥投商品明细导出表列名 */
        List<String> columns = new ArrayList<String>();
        columns.add("订单编号");
        columns.add("SKU编号");
        columns.add("商品标题");
        columns.add("数量");
        columns.add("实收小计");
        columns.add("佣金比例");
        columns.add("佣金");
        columns.add("推广服务费");
        columns.add("应结金额");
        columns.add("订单完成时间");

        List<String> keys = new ArrayList<String>();
        keys.add("ORDER_CODE");
        keys.add("SKU_NO");
        keys.add("PRODUCT_TILE");
        keys.add("COMMODITY_NUMBER");
        keys.add("RECEIVE_SUB");
        keys.add("COMMISSION_RATE");
        keys.add("COMMISSION");
        keys.add("COMMODITY_PV_SUM");
        keys.add("SETTLE_ACCOUNTS");
        keys.add("SETTLEMENT_TIME");

        /* 运费明细导出表列名 */
        List<String> farecolumns = new ArrayList<String>();
        farecolumns.add("订单编号");
        farecolumns.add("收货人");
        farecolumns.add("手机号码");
        farecolumns.add("收货地址");
        farecolumns.add("运费金额");
        farecolumns.add("订单完成时间");


        List<String> farekeys = new ArrayList<String>();
        farekeys.add("ORDER_CODE");
        farekeys.add("CONSIGNEE_NAME");
        farekeys.add("CONSIGNEE_MOBILE");
        farekeys.add("CONSIGNEE_ADDR");
        farekeys.add("FARE");
        farekeys.add("SETTLEMENT_TIME");


        /* 退款明细明细导出表列名 */
        List<String> tkcolumns = new ArrayList<String>();
        tkcolumns.add("退换货单号");
        tkcolumns.add("SKU编号");
        tkcolumns.add("商品标题");
        tkcolumns.add("服务类型");
        tkcolumns.add("数量");
        tkcolumns.add("退款金额");
        tkcolumns.add("退货返运费");
        tkcolumns.add("佣金比例");
        tkcolumns.add("退款佣金");
        tkcolumns.add("退款返推广服务费");
        // tkcolumns.add("应结退款");
        tkcolumns.add("退款完成时间");

        List<String> tkkeys = new ArrayList<String>();
        tkkeys.add("ORDER_ALTER_CODE");
        tkkeys.add("SKU_NO");
        tkkeys.add("PRODUCT_TITLE");
        tkkeys.add("SERVICE_TYPE");
        tkkeys.add("DEAL_NUMBER");
        tkkeys.add("REFUND_MONEY");
        tkkeys.add("REFUND_FARE");
        tkkeys.add("COMMISSION_RATE");
        tkkeys.add("COMMISSION");
        tkkeys.add("REFUND_COMMODITY_PV_SUM");
        // tkkeys.add("SETTLE_ACCOUNTS");
        tkkeys.add("SETTLEMENT_TIME");

        /* 差异调整明细导出表列名 */
        List<String> tzcolumns = new ArrayList<String>();
        tzcolumns.add("发生账期");
        tzcolumns.add("调整人");
        tzcolumns.add("调整说明");
        tzcolumns.add("调整金额");
        tzcolumns.add("调整发生时间");

        List<String> tzkeys = new ArrayList<String>();
        tzkeys.add("SETTLEMENT_PERIOD");
        tzkeys.add("USER_NAME");
        tzkeys.add("ADJ_DETAIL");
        tzkeys.add("ADJ_MONEY");
        tzkeys.add("ADJ_TIME");


        Map<String, String> map = new HashMap<String, String>();
        // map.put("settlementNo", "000000003590"); //测试
        map.put("settlementNo", sno);
        List<Map<String, Object>> hurlProductList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> hurlFareList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> refundList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> diffAdjList = new ArrayList<Map<String, Object>>();
        try {
            // 导出妥投商品明细
            hurlProductList = hurlProductDAO.queryExportHurlInfo(map);
            this.writejsSheet(wb, "妥投商品明细", columns, keys, hurlProductList);
            // 导出运费明细
            hurlFareList = hurlFareDAO.queryExportHurlFare(map);
            this.writejsSheet(wb, "运费明细", farecolumns, farekeys, hurlFareList);
            // 导出退款明细
            refundList = settlementRefundDAO.queryExportRefundInfo(map);
            this.writejsSheet(wb, "退款明细", tkcolumns, tkkeys, refundList);
            // 导出差异调整明细
            diffAdjList = diffAdjDAO.queryExportDiffAdjInfo(map);
            this.writejsSheet(wb, "差异调整明细", tzcolumns, tzkeys, diffAdjList);

        } catch (SQLException e1) {

            e1.printStackTrace();
        }

        // T List<String> path = getExcelName(uid + "_export", ".xls");
        String zq = "";
        if (null != sellerSettlement && sellerSettlement.getSettlementPeriod() != null) {
            zq = sellerSettlement.getSettlementPeriod();
        }
        
        List<String> path =
                getExcelName2("结算_" + sellerSettlement.getSellerName() + "_" + sno + "_" + zq,
                        ".xls");
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(path.get(0));
            wb.write(fileOut);
            fileOut.close();
            return path.get(1);
        } catch (Exception e) {
            throw new ServiceException(0, "生成表单数据异常" + e.getMessage());
        }


    }

    public List<String> getExcelName2(String channel, String type) {
        List<String> liststr = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        String excelPath =
                ConfigurationUtil.getString("path");
        String visitPath =
                ConfigurationUtil.getString("visitPath");
        sb.append("/report/").append(channel).append(type);
        File file = new File(excelPath + sb.toString());
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        liststr.add(file.getPath());
        liststr.add(visitPath + sb.toString());
        return liststr;
    }

    /**
     * 导出商家结算相关列表
     * 
     * @param wb
     * @param SheetName
     * @param columns
     * @param keys
     * @param rs
     * @param colIndexs
     */
    private void writejsSheet(HSSFWorkbook wb, String SheetName, List<String> columns,
            List<String> keys, List<Map<String, Object>> rs) {
        // 列名样式 水平居中 垂直居中 12px出题黑楷体文字
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setFontName("楷体");
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.BLACK.index);
        cellStyle.setFont(font);
        // 数据样式 水平居中 垂直居中 默认字体大小
        HSSFCellStyle cellStyleCon = wb.createCellStyle();
        cellStyleCon.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleCon.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        HSSFSheet sheet = wb.createSheet(SheetName);
        int max = columns.size();
        for (int i = 0; i < max; i++) {
            sheet.setColumnWidth(i, 6000);
        }

        // 标题
        CreateExcelUtil excel = new CreateExcelUtil(wb, sheet);
        int rowIndex = 0;// 行下标
        HSSFRow row = sheet.createRow(rowIndex++);
        for (int i = 0; i < max; i++) {
            excel.cteateCell(wb, row, i, cellStyle, columns.get(i));
        }

        // 添加内容
        DecimalFormat df = new DecimalFormat("#####0.00");
        // 妥投商品明细合计变量 -- begin
        int sumNum = 0; // 数量合计
        double shishou = 0.0; // 实收合计
        double yongjin = 0.0; // 佣金合计
        double tgpvSum = 0.0; // 推广服务费
        double accounts = 0.0; // 应结金额合计
        // 妥投商品明细合计变量 -- end

        // 运费明细明计变量 -- begin
        double faretotal = 0.0; // 用费金额合计
        // 运费明细合计变量 -- end

        // 退款明细合计变量 -- begin
        int dealNumSum = 0; // 数量合计
        double refundMoneySum = 0.0; // 退款金额合计
        double refundFareSum = 0.0; // 退货返运费合计
        double refundPvSum = 0.0; // 退货返推广服务费
        double commissionSum = 0.0; // 佣金合计
        // double settleAccountsSum = 0.0; // 应结退款合计
        // 退款明细合计变量 -- end

        // 差异调整明细合计变量 -- begin
        double adjMoneySum = 0.0; // 调整金额合计
        // 差异调整明细合计变量 -- end
        if (null != rs && !rs.isEmpty()) {
            for (Map<String, Object> map : rs) {
                // 计算合计值
                if ("妥投商品明细".equals(SheetName)) {
                    sumNum +=
                            map.get("COMMODITY_NUMBER") == null ? 0 : Integer.parseInt(map.get(
                                    "COMMODITY_NUMBER").toString());
                    shishou +=
                            map.get("RECEIVE_SUB") == null ? 0.00 : Double.parseDouble(map.get(
                                    "RECEIVE_SUB").toString());
                    yongjin +=
                            map.get("COMMISSION") == null ? 0.00 : Double.parseDouble(map.get(
                                    "COMMISSION").toString());
                    accounts +=
                            map.get("SETTLE_ACCOUNTS") == null ? 0.00 : Double.parseDouble(map.get(
                                    "SETTLE_ACCOUNTS").toString());
                    tgpvSum += map.get("COMMODITY_PV_SUM") == null ? 0.00 : Double.parseDouble(map.get(
                                    "COMMODITY_PV_SUM").toString());
                } else if ("运费明细".equals(SheetName)) {
                    faretotal +=
                            map.get("FARE") == null ? 0.00 : Double.parseDouble(map.get("FARE")
                                    .toString());
                } else if ("退款明细".equals(SheetName)) {
                    dealNumSum +=
                            map.get("DEAL_NUMBER") == null ? 0 : Integer.parseInt(map.get(
                                    "DEAL_NUMBER").toString());
                    refundMoneySum +=
                            map.get("REFUND_MONEY") == null ? 0.00 : Double.parseDouble(map.get(
                                    "REFUND_MONEY").toString());
                    refundFareSum +=
                            map.get("REFUND_FARE") == null ? 0.00 : Double.parseDouble(map.get(
                                    "REFUND_FARE").toString());
                    commissionSum +=
                            map.get("COMMISSION") == null ? 0.00 : Double.parseDouble(map.get(
                                    "COMMISSION").toString());
                    refundPvSum +=
                                    map.get("REFUND_COMMODITY_PV_SUM") == null ? 0.00 : Double.parseDouble(map.get(
                                                    "REFUND_COMMODITY_PV_SUM").toString());
                    // settleAccountsSum +=
                    // map.get("SETTLE_ACCOUNTS") == null ? 0.00 : Double.parseDouble(map.get(
                    // "SETTLE_ACCOUNTS").toString());
                } else if ("差异调整明细".equals(SheetName)) {
                    adjMoneySum +=
                            map.get("ADJ_MONEY") == null ? 0.00 : Double.parseDouble(map.get(
                                    "ADJ_MONEY").toString());
                }

                row = sheet.createRow(rowIndex++);
                for (int j = 0; j < columns.size(); j++) {
                    if ("退款明细".equals(SheetName) && keys.get(j).equals("SERVICE_TYPE")) {

                        String serviceType = map.get("SERVICE_TYPE").toString();
                        String serviceName = "";
                        if ("1".equals(serviceType)) {
                            serviceName = "退货";
                        } else if ("2".equals(serviceType)) {
                            serviceName = "换货";
                        } else if ("3".equals(serviceType)) {
                            serviceName = "不退货退款";
                        } else if ("4".equals(serviceType)) {
                            serviceName = "超时未发货赔付";
                        }
                        Cell cell = row.createCell(j);
                        cell.setCellStyle(cellStyleCon);
                        getStringValue(cell, serviceName);
                    } else if ("退款明细".equals(SheetName) && keys.get(j).equals("DEAL_NUMBER")) { // 数量去除小数点
                        String dealnumber = map.get("DEAL_NUMBER").toString();
                        Cell cell = row.createCell(j);
                        cell.setCellStyle(cellStyleCon);
                        getStringValue(cell, dealnumber);
                    } else if ("妥投商品明细".equals(SheetName) && keys.get(j).equals("COMMODITY_NUMBER")) {// 数量去除小数点
                        String ttnumber = map.get("COMMODITY_NUMBER").toString();
                        Cell cell = row.createCell(j);
                        cell.setCellStyle(cellStyleCon);
                        getStringValue(cell, ttnumber);
                    } else {
                        Cell cell = row.createCell(j);
                        cell.setCellStyle(cellStyleCon);
                        getStringValue2(cell, map.get(keys.get(j)));
                    }
                }
            }
            // 创建合计行
            row = sheet.createRow(rowIndex);
            excel.cteateCell(wb, row, 0, cellStyle, "合计");

            // 根据不同导出页设计不同的合计列
            if ("妥投商品明细".equals(SheetName)) {
                sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 2));
                excel.cteateCell(wb, row, 3, cellStyle, sumNum + "");
                excel.cteateCell(wb, row, 4, cellStyle, df.format(shishou));
                excel.cteateCell(wb, row, 6, cellStyle, df.format(yongjin));
                excel.cteateCell(wb, row, 7, cellStyle, df.format(tgpvSum));
                excel.cteateCell(wb, row, 8, cellStyle, df.format(accounts));
            } else if ("运费明细".equals(SheetName)) {
                sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 3));
                excel.cteateCell(wb, row, 4, cellStyle, df.format(faretotal));
            } else if ("退款明细".equals(SheetName)) {
                sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 3));
                excel.cteateCell(wb, row, 4, cellStyle, dealNumSum + "");
                excel.cteateCell(wb, row, 5, cellStyle, df.format(refundMoneySum));
                excel.cteateCell(wb, row, 6, cellStyle, df.format(refundFareSum));
                excel.cteateCell(wb, row, 8, cellStyle, df.format(commissionSum));
                excel.cteateCell(wb, row, 9, cellStyle, df.format(refundPvSum));
                // excel.cteateCell(wb, row, 9, cellStyle, df.format(settleAccountsSum));

            } else if ("差异调整明细".equals(SheetName)) {
                sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 2));
                excel.cteateCell(wb, row, 3, cellStyle, df.format(adjMoneySum));
            }
        }
    }

    private void getStringValue2(Cell cell, Object obj) {
        if (null != obj) {
            if (obj instanceof Date) {
                Date t = (Date) obj;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    cell.setCellValue(sdf.format(t));
                } catch (Exception e) {}
            } else if (obj instanceof TIMESTAMP) {
                TIMESTAMP t = (TIMESTAMP) obj;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                try {
                    cell.setCellValue(sdf.format(t.timestampValue()));
                } catch (SQLException e) {}
            } else if (obj instanceof Long) {
                cell.setCellValue(new BigDecimal(obj.toString()).doubleValue());
            } else if (obj instanceof Double || obj instanceof Float || obj instanceof BigDecimal) {
                DecimalFormat df = new DecimalFormat("#####0.00");
                cell.setCellValue(df.format(new BigDecimal(obj.toString()).doubleValue()));
            } else if (obj instanceof Integer) {
                cell.setCellValue(Integer.parseInt(obj.toString()));
            } else {
                cell.setCellValue(obj.toString());
            }
        }
    }

    @Override
    public String exportSettleOrder(String sno, Integer uid) throws ServiceException {
        String errorStr = "";
        if (StringUtils.isEmpty(sno)) {
            errorStr = "传入的结算单号为空！";
            logger.error(errorStr);
            return errorStr;
        }
        if (null == uid) {
            errorStr = "传入的用户编号为空！";
            logger.error(errorStr);
            return errorStr;
        }

        String path = "";
        try {
            SellerSettlementCriteria sCriteria = new SellerSettlementCriteria();
            sCriteria.setSettlementNo(sno);

            SellerSettlement sellerSettlement =
                    sellerSettlementService.getSettlementByNo(sCriteria, true);
            // 本账期发起差异调整明细

            DiffAdjExample diffAdjExample = new DiffAdjExample();
            diffAdjExample.createCriteria().andCurrSettmentNoEqualTo(sno);
            diffAdjExample.setOrderByClause("DIFF_ADJ_ID desc");

            List<DiffAdj> diffList = diffAdjService.selectDiffAdjDetail(diffAdjExample);

            path = this.exportDetailOrderFile(sno, uid, sellerSettlement, diffList);
        } catch (Exception e) {
            logger.error("结算单导出失败！", e);
            return "结算单导出失败！";
        }


        return path;
    }

    @Override
    public String exportSellerOrders(Map<String, Object> params) throws ServiceException {
        List orderList = null;
        // 文件名
        StringBuffer filePath = new StringBuffer(100);
        String strTitleValue = null;
        String strEndDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            orderList = orderMainDAO.querySellerOrderExportData(params);
            if (orderList == null || orderList.isEmpty()) {
                return "NO_DATA";
            }
            SellerOrderExportService exportService = new SellerOrderExportService();

            filePath.append("/report/");

            strTitleValue =
                    params.containsKey("titleValue")
                            ? String.valueOf(params.get("titleValue"))
                            : "";
            if (StringUtils.isNotBlank(strTitleValue)) {
                filePath.append(strTitleValue).append("_");
            }

            // 获取订单完成结束时间，如果为空字符串，则取完成时间的结束时间
            strEndDate =
                    params.containsKey("orderEndDate")
                            ? String.valueOf(params.get("orderEndDate"))
                            : "";

            if (StringUtils.isBlank(strEndDate)) {
                strEndDate =
                        params.containsKey("finishEndDate") ? String.valueOf(params
                                .get("finishEndDate")) : "";
            }

            if (StringUtils.isNotBlank(strEndDate)) {
                Calendar endCal = Calendar.getInstance();
                endCal.setTime(dateFormat.parse(strEndDate));
                filePath.append(endCal.get(Calendar.YEAR)).append("年");
                filePath.append(endCal.get(Calendar.MONTH) + 1).append("月");
                filePath.append(endCal.get(Calendar.DAY_OF_MONTH)).append("日");
                filePath.append("_");
            }

            // 添加唯一UUID随机数
            filePath.append(UUID.randomUUID()).append(".xls");
            String excelPath =
                    ConfigurationUtil.getString("path");
            String visitPath =
                    ConfigurationUtil.getString("visitPath");

            File file = new File(excelPath.concat(filePath.toString()));
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            exportService.exportExcel(excelPath.concat(filePath.toString()), "订单导出", orderList,
                    params);
            return visitPath.concat(filePath.toString());
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "导出供应商订单错误".concat(ex
                    .getMessage()));
        }
    }

    @Override
    public Map<String, Object> listCountFuzzy(Map<String, Object> paramMap) throws ServiceException {
        try {
            Map<String, Object> moneyMap = new HashMap<String, Object>();
            moneyMap = orderMainDAO.countFuzzyByMap(paramMap);
            return moneyMap;
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "获取订单列表总金额时发生异常："
                    + e.getMessage());
        }
    }

    @Override
    public Integer orderPayInfoListCount(Map<String, Object> map) throws ServiceException {
        try {
               Integer re = 0;
                re = orderMainDAO.orderPayInfoListCount(map);
                return re;
            
        } catch (Exception e) {
            logger.error("获取收退款数据大小异常", e);
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "获取收退款数据大小异常："
                    + e.getMessage());
        }
    }

    @Override
    public List<OrderPayInfo> queryOrderPayInfoList(Map<String, Object> map) throws ServiceException {
        List<OrderPayInfo> orderPays = null;
        try {
            orderPays = orderMainDAO.queryOrderPayInfoList(map);
        } catch (SQLException e) {
            logger.error("查询收退款数据列表异常!"+e.getMessage());
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "查询收退款数据异常："
                    + e.getMessage());
        }
        return orderPays;
    }

    @Override
    public String ExportOrderPayInfo(Map<String, Object> map, Integer userId ) throws ServiceException {
        
        List<Map<String,Object>> orderPays = null;
        try {
            orderPays = orderMainDAO.queryOrderPayInfoForExport(map);
        } catch (SQLException e) {
            logger.error("", e);
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "获取收退款数据大小异常："
                    + e.getMessage());
        }
        
        List<String> lable = new ArrayList<String>();
        lable.add("支付渠道");
        lable.add("支付类型");
        lable.add("订单号");
        lable.add("第三方支付流水号");
        lable.add("下单账号");
        lable.add("收/退款类型");
        lable.add("金额");
        lable.add("交易时间");
        List<String> key = new ArrayList<String>();
        key.add("PLATFORMNAME");
        key.add("PAYTYPE");
        key.add("ORDERCODE");
        key.add("OUTSIDEPAYSTATEMENTNO");
        key.add("CUSTOMERACCOUNT");
        key.add("FLAG");
        key.add("ORDERMONEY");
        key.add("OPERATEDATE");
        
        return exportFile(userId.intValue(), "收退款报表", lable, key, orderPays);
    
    }

    @Override
    public String insertExportInfo(Map<String, String> map, String userName,String readUrl)
                    throws ServiceException {
        String newKey = null;
        ExportInfo ei = new ExportInfo();
        
        String startDate = map.get("startDate");
        String endDate = map.get("endDate");
        String supplier = map.get("supplier");// 供应商名称
        String selectTimes = map.get("selectTimes");// 时间集,保存一个或多个账期
        
        StringBuffer sf = new StringBuffer();
        
        if(null != startDate && !"".equals(startDate.trim())&&null != endDate && !"".equals(endDate.trim())){
        sf.append("订单付款时间从"+startDate+"至"+endDate+";");
        }
       
       
        if(null != supplier && !supplier.isEmpty())
        {
            sf.append("供应商:"+supplier);
        }
        if(null !=selectTimes && !"".equals(selectTimes.trim())){
            sf.append("结算账期："+selectTimes);
        }
        
        ei.setOperator(userName);
        ei.setExportContent(sf.toString());
        ei.setCreateDate(new Date());
        ei.setExportType(map.get("exprotType"));
        ei.setExportStatus("0");
        ei.setUrl(readUrl);
        try {
            Long id = orderMainDAO.insertExportInfo(ei);
            newKey = id+"";
        } catch (SQLException e) {
            logger.error("导出商家财务版报表功能，在添加导出报表信息时发生异常!"+e.getMessage());
        }
        return newKey;
    }

    @Override
    public String AsynExportFinanceOrderReport(Map<String, String> map, Integer uid,
                    String writeUrl,String newKey) throws ServiceException {
        
       
        executors.execute(new Runnable() {
            @Override
            public void run() {
                List<List<Map<String, Object>>> result = null;
                try {
                    result = orderMainDAO.queryShopOrderReportData(map);
                } catch (Exception e) {
                    throw new ServiceException(ErrorCode.INNER_ORDER_ANALYSIS_REPORT_ERROR,
                            "导出商城订单财务版报表异常：" + e.getMessage());
                }
                HSSFWorkbook wb = new HSSFWorkbook();// excel
                if (null != result && !result.isEmpty()) {
                    List<Map<String, Object>> rs1 = result.get(0);
                    if (null != rs1 && !rs1.isEmpty()) {
                        String[] columns =
                                {"订单付款时间", "下单账号","订单来源", "支付渠道","第三方支付流水号","母订单号", "子订单号","订单完成时间","订单状态",
                                        "供应商","供应商类型","SKU编码","品牌", "商品标题","产品名称","规格","一级类目","二级类目","三级类目",
                                        "商品类型","单价", "实收单价", "数量","优惠券分摊金额小计","满减分摊金额小计", 
                                        "实收小计", "PV值小计","成本单价", "成本小计","实收金额", "运费","活动ID(商品级)",
                                        "是否开票", "发票类型", "发票抬头", "收货人", "收货地址", "收货人电话"};
                        String[] key =
                                {"PAYDATE", "CUSTOMERACCOUNT","ORDERSOURCE", "PLATFORMNAME",
                                        "OUTSIDEPAYSTATEMENTNO","ORDERCODE", "CHILDCODE","FINISHDATE","ORDERSTATUS",
                                        "SUPPLIER","SUPPLIERTYPESTR","COMMODITYSKU","BRANDNAME","COMMODITYTITLE",
                                        "PRODUCTNAME","COMMODITYSKUDESCRIPTION","L1","L2","L3","COMMODITYTYPE","COMMODITYCALLEDPRICE", 
                                        "COMMODITYUNITPRICE", "COMMODITYNUMBER","COUPONDIVIDE","FULLREDUCTIONDIVIDE",
                                        "COMMODITYSUM","COMMODITYPV","COMMODITYCOSTPRICE", "COMMODITYCOSTSUM",
                                        "AMOUNTPAYABLE","FARE","ORDERPREFERENTIALCODE","HASINVOICE",
                                        "INVOICETYPE", "INVOICETITLE", "CONSIGNEE", "DELIVERYADDRESS",
                                        "CELLPHONE"};
                     
                        int[] colIndexs = {0, 1, 2, 3, 4, 5, 29, 30, 32, 33, 34, 35, 36, 37};
                        writeOrderMapToSheet(wb, "商城订单财务版", columns, key, rs1, colIndexs);
                    }
                    rs1 = result.get(1);
                    if (null != rs1 && !rs1.isEmpty()) {
                        String[] columns =
                                {"退款完成日期", "下单账号","订单来源","母订单号", "子订单号","供应商", "供应商类型", "SKU编码",
                                        "商品标题","产品名称","规格","商品类型","单价", "实收单价", "数量", "优惠券分摊金额小计","满减分摊金额小计", "实收小计", "PV值小计",
                                        "退款流水", "退款平台", "退货数量","退款金额","商品退款金额", "退货返运费","补偿运费", "成本单价",
                                        "成本小计", "运费","是否开票", "发票类型", "发票抬头", "收货人", "收货地址", "收货人电话"};
                        String[] key =
                                {"TRADEDATE","CUSTOMERACCOUNT", "ORDERSOURCE","PARENTORDERCODE",
                                        "ORDERCODE","CORPORATENAME", "SUPPLIERTYPESTR",
                                        "COMMODITYSKU", "COMMODITYTITLE","PRODUCTNAME","COMMODITYSKUDESCRIPTION","COMMODITYTYPE", "COMMODITYCALLEDPRICE",
                                        "COMMODITYUNITPRICE", "COMMODITYNUMBER","COUPONDIVIDE","FULLREDUCTIONDIVIDE", "COMMODITYSUM", "COMMODITYPV",
                                        "BATCHNO", "PAYPLATFORM", "ALTERNUM", "RETURNMONEY","AMOUNTPAYABLE", "RETURNFARE","FARESUBSIDY",
                                         "COMMODITYCOSTPRICE", "COMMODITYCOSTSUM", "FARE",
                                        "HASINVOICE", "INVOICETYPE", "INVOICETITLE", "CONSIGNEE",
                                        "DELIVERYADDRESS", "CELLPHONE"};
                        writeSimpleDataToSheet(wb, "退款数据", columns, key, rs1);
                    }
                }else{
                    logger.info("导出商城财务版的数据为空,writeUrl="+writeUrl);
                    return;
                }
                
                FileOutputStream fileOut;
                try {
                    fileOut = new FileOutputStream(writeUrl);
                    wb.write(fileOut);
                    fileOut.close();
                } catch (Exception e) {
                    throw new ServiceException(ErrorCode.INNER_ORDER_ANALYSIS_REPORT_ERROR,
                            "导出商城订单财务版报表异常：" + e.getMessage());
                }
               
                try{
                    Thread.sleep(60000);
                    if(null != newKey && !newKey.isEmpty()){
                    ExportInfo ei = new ExportInfo();
                    ei.setExportId(Long.parseLong(newKey));
                    orderMainDAO.updateExportInfo(ei);
                    }else{
                        throw new ServiceException("异步导出商城财务报表功能，修改导出报表信息状态时传入参数exportId为空");
                    }
                }catch(Exception e){
                    throw new ServiceException("异步导出商城财务报表功能，修改导出报表信息状态时异常"+e.getMessage());
                }
           }});
        
        return null;
    }

    @Override
    public List<ExportInfo> queryExportInfo(Map<String, String> map) throws ServiceException {
        List<ExportInfo> exportInfos = null;
        try {
            exportInfos = orderMainDAO.queryExportInfo(map);
        } catch (SQLException e) {
            logger.error("查询导出报表信息异常"+e.getMessage());
            throw new ServiceException(ErrorCode.INNER_ORDER_QUERY_ERROR, "查询导出报表信息异常："
                    + e.getMessage());
        }
        return exportInfos;
    }

    @Override
    public Integer getExportInfoCount(Map<String, String> map) throws ServiceException {
      Integer re = 0 ;
          try {
            re = orderMainDAO.getExportInfoCount(map);
            } catch (SQLException e) {
                logger.error("获取导出报表记录数异常"+e.getMessage());
                throw new ServiceException("获取导出报表记录数异常！"+ e.getMessage());
            }
          return re;
    }

    @Override
    public Map<String, String> CountOrderPayMoney(Map<String, Object> map) throws ServiceException {
        Map<String,String> reMap = new HashMap<String,String>();
        try {
            reMap = orderMainDAO.CountOrderPayMoney(map);
        } catch (SQLException e) {
             logger.error("统计收退款金额失败!"+e.getMessage());
             throw new ServiceException("统计收退款金额失败!"+ e.getMessage());
        }
        return reMap;
    }

    @Override
    public String AsynExportmerchantsOrderReport(List<Map<String, String>> mapList, Integer uid,
                    String writeUrl, String newKey) throws ServiceException {
        
        executors.execute(new Runnable() {
        @Override
        public void run() {
            List<Map<String, Object>> orderMaps = new ArrayList<Map<String, Object>>(); // 存储入驻商家订单财务版数据
            List<Map<String, Object>> refundMaps = new ArrayList<Map<String, Object>>(); // 存储退款数据
            List<Map<String, Object>> diffAdjMaps = new ArrayList<Map<String, Object>>(); // 存储差异调整数据
            for (Map<String, String> map : mapList) {// 循环查询各个账期的数据，并保存在maps中，作为导入excel的数据
                List<List<Map<String, Object>>> resultList = null;
                try {
                    resultList = orderMainDAO.AsynMerchantsOrderReportData(map);
                    if (null != resultList && resultList.size() > 0) {
                        List<Map<String, Object>> orderList = resultList.get(0);// 商家订单财务版数据
                        for (Map<String, Object> mapInfo : orderList) {
                            orderMaps.add(mapInfo);
                        }
                        List<Map<String,Object>> refundList = resultList.get(1);// 退款数据
                        for(Map<String,Object> refundMap:refundList){
                            refundMaps.add(refundMap);
                        }
                    
    
                    }
                } catch (Exception e) {
                    throw new ServiceException(ErrorCode.INNER_ORDER_ANALYSIS_REPORT_ERROR,
                            "导出商家订单财务版报表异常：" + e.getMessage());
                }
            }
            Map<String, Object> diffMap = new HashMap<String, Object>();
            Map<String, String> mapStr = mapList.get(0);
            String supplier = mapStr.get("supplier");// 供应商名称
            String selectTimes = mapStr.get("selectTimes");// 时间集,保存一个或多个账期
            List<String> timeList = new ArrayList<String>();
            String[] times = selectTimes.split(",");
            for (int i = 0; i < times.length; i++) {
                timeList.add(times[i]);
            }
            diffMap.put("supplier", supplier);
            diffMap.put("selectTimes", timeList);
            try {
                diffAdjMaps = orderMainDAO.AsynDiffAdjReportData(diffMap);
            } catch (SQLException e1) {
    
                e1.printStackTrace();
            }
            HSSFWorkbook wb = new HSSFWorkbook();// excel
    
    
            List<Map<String, Object>> rs1 = orderMaps;
            String[] columns =
                    {"订单付款时间","发生账期", "订单来源","支付渠道","第三方支付流水号", "母订单号", "子订单号","订单完成时间","供应商",
                            "品牌", "SKU编码", "商品标题","商品类型","单价", "实收单价", "数量","优惠券分摊金额小计","满减分摊金额小计","实收小计", "PV值小计",
                             "实收金额", "佣金比例","佣金","应结货款","运费", "应结金额"};
            String[] key =
                    {"PAYDATE", "ZQ", "ORDERSOURCE","PLATFORMNAME","OUTSIDEPAYSTATEMENTNO", "ORDERCODE",
                            "CHILDCODE","FINISHDATE",  "SUPPLIER", "BRANDNAME",
                            "COMMODITYSKU", "COMMODITYTITLE","COMMODITYTYPE", "COMMODITYCALLEDPRICE",
                            "COMMODITYUNITPRICE", "COMMODITYNUMBER", "COUPONDIVIDE","FULLREDUCTIONDIVIDE",
                            "COMMODITYSUM", "COMMODITYPV","AMOUNTPAYABLE", "COMMISSION_RATE","COMMISSION","SETTLEMENT_LOAN",
                            "FARE","SETTLEMONEY"};
            int[] colIndexs = {0, 1, 2, 3, 4, 5, 24, 25};
            writeOrderMapToSheet(wb, "商家订单财务版", columns, key, rs1, colIndexs);
    
    
    
            List<Map<String, Object>> rs2 = refundMaps;
            String[] columns1 =
                    {"退款完成时间", "发生账期","下单账号","订单来源","母订单号", "子订单号", "供应商", "SKU编码", "商品标题","商品类型",
                            "单价", "实收单价", "数量","优惠券分摊金额小计","满减分摊金额小计", "实收小计", "PV值小计","退款流水",
                            "退款平台", "退货数量","退款金额","商品退款金额", "退货返运费","佣金比例", "佣金", "运费"};
            String[] key1 =
                    {"TRADEDATE","ZQ","CUSTOMERACCOUNT", "ORDERSOURCE","PARENTORDERCODE", "ORDERCODE",
                            "CORPORATENAME", "COMMODITYSKU","COMMODITYTITLE","COMMODITYTYPE","COMMODITYCALLEDPRICE",
                             "COMMODITYUNITPRICE","COMMODITYNUMBER","COUPONDIVIDE","FULLREDUCTIONDIVIDE",
                            "COMMODITYSUM", "COMMODITYPV","BATCHNO", "PAYPLATFORM", "ALTERNUM",
                            "RETURNMONEY", "AMOUNTPAYABLE","RETURNFARE","COMMISSION_RATE",
                            "COMMISSION", "FARE"};
            writeSimpleDataToSheet(wb, "退款数据", columns1, key1, rs2);
    
    
           
            List<String> tzcolumns = new ArrayList<String>();
            tzcolumns.add("供应商");
            tzcolumns.add("发生账期");
            tzcolumns.add("调整人");
            tzcolumns.add("调整说明");
            tzcolumns.add("调整金额");
            tzcolumns.add("调整发生时间");
    
            List<String> tzkeys = new ArrayList<String>();
            tzkeys.add("SUPPLIER");
            tzkeys.add("SETTLEMENT_PERIOD");
            tzkeys.add("USER_NAME");
            tzkeys.add("ADJ_DETAIL");
            tzkeys.add("ADJ_MONEY");
            tzkeys.add("ADJ_TIME");
    
           
            writejsSheet(wb, "差异调整明细", tzcolumns, tzkeys, diffAdjMaps);
    
            FileOutputStream fileOut;
            try {
                fileOut = new FileOutputStream(writeUrl);
                wb.write(fileOut);
                fileOut.close();
            } catch (Exception e) {
                throw new ServiceException(ErrorCode.INNER_ORDER_ANALYSIS_REPORT_ERROR,
                        "导出入驻商家结算报表异常：" + e.getMessage());
            }
            
            try{
                Thread.sleep(60000);
                if(null != newKey && !newKey.isEmpty()){
                ExportInfo ei = new ExportInfo();
                ei.setExportId(Long.parseLong(newKey));
                orderMainDAO.updateExportInfo(ei);
                }else{
                    throw new ServiceException("异步导出入驻商家结算报表功能，修改导出报表信息状态时传入参数exportId为空");
                }
            }catch(Exception e){
                throw new ServiceException("异步导出入驻商家结算报表功能，修改导出报表信息状态时异常"+e.getMessage());
            }
        }});
        return null;
    }

    @Override
    public String exportFinacialAuditInfo(List<Map<String,Object>> sellerMap) throws ServiceException {
        List<String> lable = new ArrayList<String>();
        lable.add("结算单号");
        lable.add("商家");
        lable.add("账期");
        lable.add("结算时间");
        lable.add("期内货款");
        lable.add("运费");
        lable.add("平台佣金");
        lable.add("推广服务费");
        lable.add("差异调整");
        lable.add("应结金额");
        lable.add("结算状态");
        List<String> key = new ArrayList<String>();
        key.add("SETTLEMENTNO");
        key.add("SELLERNAME");
        key.add("SETTLEMENTPERIOD");
        key.add("SETTLEMENTCREATETIME");
        key.add("SETTLEMONEY");
        key.add("FARESUM");
        key.add("COMMISSION");
        key.add("PVSUM");
        key.add("DIFFADJSUM");
        key.add("SETTLEACCOUNT");
        key.add("SETTLEMENTSTATUS");
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String filename = "入驻商家结算单汇总_"+sdf.format(cal.getTime());
        return exportFileForFileName(filename, "结算单报表", lable, key, sellerMap);
    
    }

    @Override
    public Map<String, String> supplierIdAndMerchantNameMap(Map<String,String> map) throws ServiceException {
        List<MerchantInfoOrSuppliers> suppIdAndNameList = null;
        Map<String, String> suppIdAndNameMap = new LinkedHashMap<String, String>();
        MerchantInfoOrSuppliers record = new MerchantInfoOrSuppliers();
        try {
            record.setStatus(Short.parseShort("3"));// 审核通过的供应商
            record.setEnterpriseStatus(Short.parseShort("1"));
            String type = map.get("supplierType");
            if(null != type && !type.isEmpty()){
            record.setSupplierType(Short.parseShort(type));
            }
            suppIdAndNameList = orderMainDAO.getSupplierByCloseStatus(record);
     
            for (int i = 0; i < suppIdAndNameList.size(); i++) {
                if (suppIdAndNameList.get(i).getSupplierId().intValue() == 221) {
                    continue;
                }
                suppIdAndNameMap.put(String.valueOf(suppIdAndNameList.get(i).getSupplierId()),
                        suppIdAndNameList.get(i).getCorporateName());
            }
        } catch (Exception e) {
            logger.error("查询供应商信息和商户信息出现异常map!" + e.getMessage());
            throw new ServiceException(e);
        }
        return suppIdAndNameMap;
    }
    
    

}
