package com.pltfm.app.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.enums.SuppliersType;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.dao.OrderCarryDAO;
import com.pltfm.app.dao.OrderItemDAO;
import com.pltfm.app.dao.OrderMainDAO;
import com.pltfm.app.dao.OrderPreferentialDAO;
import com.pltfm.app.entities.OrderCarry;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.maps.OrderChannel;
import com.pltfm.app.service.OrderAlterService;
import com.pltfm.app.service.OrderExecuteService;
import com.pltfm.app.service.OrderItemQryService;
import com.pltfm.app.service.OrderOperateStatementService;
import com.pltfm.app.service.OrderPayStatementService;
import com.pltfm.app.service.OrderQryService;
import com.pltfm.app.util.Constants;
import com.pltfm.app.util.CreateExcelUtil;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.util.SettlementAndPayUtil;
import com.pltfm.app.vobject.SKUEntity;
import com.pltfm.sys.util.ErrorCode;

import oracle.sql.TIMESTAMP;
import redis.clients.jedis.JedisCluster;

/**
 * 结转服务接口实现类
 * 
 * @author lvxingxing
 * @version 1.0
 * @since 2013-8-7
 */
@SuppressWarnings("unchecked")
@Service("orderExecuteService")
public class OrderExecuteServiceImpl implements OrderExecuteService {

    private static final Logger log = Logger.getLogger(OrderExecuteServiceImpl.class);

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private OrderCarryDAO orderCarryDAO;
    @Resource
    private OrderMainDAO orderMainDAO;
    @Resource
    private OrderItemDAO orderItemDAO;

    @Resource
    private OrderAlterService orderAlterService;
    @Resource
    private OrderOperateStatementService orderOperateStatementService;
    @Resource
    OrderItemQryService orderItemQryService;
    @Resource
    OrderQryService orderQryService;
    @Resource
    OrderPreferentialDAO orderPreferentialDAO;
    @Resource
    OrderPayStatementService orderPayStatementService;
    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;

    @Autowired
    private SettlementAndPayUtil settlementAndPayUtil;

    /**
     * 按SUK统计出库单
     * 
     * @param list
     * @param mapOrderItem
     * @return
     */
    private List<SKUEntity> statisticsSUK(List<OrderMain> list,
            Map<String, List<OrderItem>> mapOrderItem) {
        List<SKUEntity> listSUK = new ArrayList<SKUEntity>();
        Map<String, SKUEntity> skuMap = new HashMap<String, SKUEntity>();
        SKUEntity entity = null;
        for (OrderMain om : list) {
            for (OrderItem oi : mapOrderItem.get(om.getOrderCode())) {
                if (skuMap.containsKey(oi.getCommoditySku())) {
                    entity = skuMap.get(oi.getCommoditySku());
                    entity.setCommodityNumber(
                            entity.getCommodityNumber() + oi.getCommodityNumber());
                } else {
                    entity = getVOByOrdItem(oi);
                    entity.setCommodityChannel(om.getOrderChannel());
                }
                skuMap.put(oi.getCommoditySku(), entity);
            }
        }
        for (SKUEntity se : skuMap.values()) {
            listSUK.add(se);
        }
        return listSUK;
    }

    /**
     * 根据出库单,配送单,未能结转的单,生成excel文件,并保存到对应的目录.
     * 
     * @param list
     * @param ExecutedMap
     * @param disexecuteMap
     * @return
     * @throws IOException
     */
    private String createExcelFile(String fix, List<SKUEntity> list, List<OrderMain> orderExList,
            Map<String, List<OrderItem>> ExecutedMap, List<OrderMain> orderDisList,
            Map<String, List<OrderItem>> disexecuteMap) throws IOException, Exception {

        int rowNumEx = 0;// 数据行下标
        int colsNumStart = 0;// 合并开始行
        HSSFWorkbook wb = new HSSFWorkbook();// excel
        // 地址样式 水平居中 垂直居中 默认字体大小 自动换行
        HSSFCellStyle cellStyleAddr = wb.createCellStyle();
        cellStyleAddr.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleAddr.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyleAddr.setWrapText(true);

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

        // 小标题样式 水平居中 垂直居中 12px出题黑楷体文字
        HSSFCellStyle cellStyleItem = wb.createCellStyle();
        cellStyleItem.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleItem.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        Font fontItem = wb.createFont();
        fontItem.setFontHeightInPoints((short) 12);
        fontItem.setFontName("楷体");
        fontItem.setColor(HSSFColor.BLACK.index);
        cellStyleItem.setFont(fontItem);

        HSSFRow row = null;
        // 发货订单汇总表sheet
        rowNumEx = 0;
        HSSFSheet deliver = wb.createSheet("发货汇总表");
        deliver.setColumnWidth(0, 4500);
        deliver.setColumnWidth(1, 4500);
        deliver.setColumnWidth(2, 4500);
        deliver.setColumnWidth(3, 4500);
        deliver.setColumnWidth(4, 4500);
        deliver.setColumnWidth(5, 4500);
        deliver.setColumnWidth(6, 4500);
        deliver.setColumnWidth(7, 4500);
        deliver.setColumnWidth(8, 4500);
        CreateExcelUtil excel = new CreateExcelUtil(wb, deliver);

        Calendar cal = Calendar.getInstance();
        String dateStr = (cal.get(Calendar.MONTH) + 1) + "月" + cal.get(Calendar.DAY_OF_MONTH) + "日";

        // sheet标题
        row = deliver.createRow(rowNumEx);
        row.setHeight((short) 1400);
        rowNumEx++;
        excel.cteateCell(wb, row, 0, cellStyle, "康美中药城营销部订单-深圳配送中心发货订单汇总表" + dateStr);
        deliver.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));

        // 列名
        row = deliver.createRow(rowNumEx);
        rowNumEx++;
        excel.cteateCell(wb, row, 0, cellStyle, "品名");
        excel.cteateCell(wb, row, 1, cellStyle, "产地");
        excel.cteateCell(wb, row, 2, cellStyle, "所属仓库");
        excel.cteateCell(wb, row, 3, cellStyle, "规格");
        excel.cteateCell(wb, row, 4, cellStyle, "单位");
        excel.cteateCell(wb, row, 5, cellStyle, "数量");
        excel.cteateCell(wb, row, 6, cellStyle, "订单日期");
        excel.cteateCell(wb, row, 7, cellStyle, "需发货日期");
        excel.cteateCell(wb, row, 8, cellStyle, "备注");

        colsNumStart = rowNumEx;
        // 汇总数据
        if (list != null) {
            for (int i = 0, len = list.size(); i < len; i++) {
                SKUEntity skuEn = list.get(i);
                row = deliver.createRow(rowNumEx);
                rowNumEx++;
                excel.cteateCell(wb, row, 0, cellStyleCon, skuEn.getCommodityTitle());
                // 1产地
                excel.cteateCell(wb, row, 2, cellStyleCon, skuEn.getWarehouseName());
                excel.cteateCell(wb, row, 3, cellStyleCon, skuEn.getCommoditySkuDescription());
                // 4单位
                excel.cteateCell(wb, row, 5, cellStyleCon,
                        getStringValue(skuEn.getCommodityNumber()));
                if (i == 0) {
                    excel.cteateCell(wb, row, 6, cellStyleCon, sdf.format(cal.getTime()));
                }
            }
        }
        if (colsNumStart != rowNumEx) {
            deliver.addMergedRegion(new CellRangeAddress(colsNumStart, rowNumEx - 1, 6, 6));
            deliver.addMergedRegion(new CellRangeAddress(colsNumStart, rowNumEx - 1, 7, 7));
            deliver.addMergedRegion(new CellRangeAddress(colsNumStart, rowNumEx - 1, 8, 8));
        }

        // 发货明细表 sheet
        rowNumEx = 0;
        HSSFSheet deliverDetail = wb.createSheet("发货明细表");
        deliverDetail.setColumnWidth(0, 3500);
        deliverDetail.setColumnWidth(1, 3500);
        deliverDetail.setColumnWidth(2, 3500);
        deliverDetail.setColumnWidth(3, 4500);
        deliverDetail.setColumnWidth(4, 3500);
        deliverDetail.setColumnWidth(5, 3500);
        deliverDetail.setColumnWidth(6, 3500);
        deliverDetail.setColumnWidth(7, 3500);
        deliverDetail.setColumnWidth(8, 3500);
        deliverDetail.setColumnWidth(9, 3500);
        deliverDetail.setColumnWidth(10, 3500);
        deliverDetail.setColumnWidth(11, 3500);
        deliverDetail.setColumnWidth(12, 3500);
        deliverDetail.setColumnWidth(13, 3500);
        deliverDetail.setColumnWidth(14, 3500);
        deliverDetail.setColumnWidth(15, 3500);
        deliverDetail.setColumnWidth(16, 3500);
        deliverDetail.setColumnWidth(17, 3500);
        deliverDetail.setColumnWidth(18, 5500);

        // 列名
        row = deliverDetail.createRow(rowNumEx);
        rowNumEx++;
        excel.cteateCell(wb, row, 0, cellStyle, "销售平台");
        excel.cteateCell(wb, row, 1, cellStyle, "所属仓库");
        excel.cteateCell(wb, row, 2, cellStyle, "SKU");
        excel.cteateCell(wb, row, 3, cellStyle, "订单号");
        excel.cteateCell(wb, row, 4, cellStyle, "产品归属");
        excel.cteateCell(wb, row, 5, cellStyle, "品牌");
        excel.cteateCell(wb, row, 6, cellStyle, "品名");
        excel.cteateCell(wb, row, 7, cellStyle, "产地");
        excel.cteateCell(wb, row, 8, cellStyle, "规格");
        excel.cteateCell(wb, row, 9, cellStyle, "单位");
        excel.cteateCell(wb, row, 10, cellStyle, "数量");
        excel.cteateCell(wb, row, 11, cellStyle, "单价");
        excel.cteateCell(wb, row, 12, cellStyle, "总价");
        excel.cteateCell(wb, row, 13, cellStyle, "PV值");
        excel.cteateCell(wb, row, 14, cellStyle, "买家账号");
        excel.cteateCell(wb, row, 15, cellStyle, "买家备注");
        excel.cteateCell(wb, row, 16, cellStyle, "客服备注");
        excel.cteateCell(wb, row, 17, cellStyle, "收件人");
        excel.cteateCell(wb, row, 18, cellStyle, "电话");
        excel.cteateCell(wb, row, 19, cellStyle, "地址");

        if (orderExList != null) {
            for (int i = 0, len = orderExList.size(); i < len; i++) {
                OrderMain orderMain = orderExList.get(i);
                List<OrderItem> orderItemList = new ArrayList<OrderItem>();
                if (ExecutedMap != null) {
                    orderItemList = ExecutedMap.get(orderMain.getOrderCode());
                }
                colsNumStart = rowNumEx;
                for (int j = 0, jlen = orderItemList.size(); j < jlen; j++) {
                    OrderItem orderItem = orderItemList.get(j);
                    row = deliverDetail.createRow(rowNumEx);
                    rowNumEx++;
                    excel.cteateCell(wb, row, 2, cellStyleCon, orderItem.getCommoditySku());
                    if (null != orderItem.getSupplierType() && (SuppliersType.EMTER.getStatus()
                            .longValue() == orderItem.getSupplierType()
                            || SuppliersType.SUPPORT.getStatus().longValue() == orderItem
                                    .getSupplierType())) {
                        if ("康美药业OTC".equals(orderItem.getSupplierName())) {
                            excel.cteateCell(wb, row, 4, cellStyleCon, "OTC产品");
                        } else if ("广东康美之恋大药房连锁有限公司".equals(orderItem.getSupplierName())
                                || "康美按方抓药".equals(orderItem.getSupplierName())) {
                            excel.cteateCell(wb, row, 4, cellStyleCon, "OTC产品");
                        } else if ("康美人生".equals(orderItem.getSupplierName())) {
                            excel.cteateCell(wb, row, 4, cellStyleCon, "康美人生");
                        } else {
                            excel.cteateCell(wb, row, 4, cellStyleCon, "时代自营");
                        }
                    } else {
                        excel.cteateCell(wb, row, 4, cellStyleCon, orderItem.getSupplierTypeName());
                    }
                    excel.cteateCell(wb, row, 5, cellStyleCon, orderItem.getCommodityBrand());
                    excel.cteateCell(wb, row, 6, cellStyleCon, orderItem.getCommodityTitle());
                    // 7产地
                    excel.cteateCell(wb, row, 8, cellStyleCon,
                            orderItem.getCommoditySkuDescription());
                    // 9单位
                    excel.cteateCell(wb, row, 10, cellStyleCon,
                            getStringValue(orderItem.getCommodityNumber()));
                    excel.cteateCell(wb, row, 11, cellStyleCon,
                            getStringValue(orderItem.getCommodityUnitIncoming()));
                    excel.cteateCell(wb, row, 12, cellStyleCon, orderItem.getCommodityUnitIncoming()
                            .multiply(new BigDecimal(orderItem.getCommodityNumber())).toString());
                    if (null != orderItem.getCommodityPv()) {
                        excel.cteateCell(wb, row, 13, cellStyleCon, String.valueOf(
                                orderItem.getCommodityPv() * orderItem.getCommodityNumber()));
                    }
                    if (colsNumStart == rowNumEx - 1) {
                        excel.cteateCell(wb, row, 0, cellStyleCon,
                                OrderChannel.getValueByKey(orderMain.getOrderChannel()));
                        excel.cteateCell(wb, row, 1, cellStyleCon, orderItem.getWarehouseName());
                        excel.cteateCell(wb, row, 3, cellStyleCon, orderMain.getOrderCode());
                        excel.cteateCell(wb, row, 14, cellStyleCon, orderMain.getCustomerAccount());
                        excel.cteateCell(wb, row, 15, cellStyleCon,
                                orderMain.getOrderDescription());
                        excel.cteateCell(wb, row, 16, cellStyleCon,
                                orderMain.getOrderOperationRemark());
                        excel.cteateCell(wb, row, 17, cellStyleCon, orderMain.getConsigneeName());
                        excel.cteateCell(wb, row, 18, cellStyleCon, orderMain.getConsigneeMobile());
                        excel.cteateCell(wb, row, 19, cellStyleCon, orderMain.getConsigneeAddr());
                    }
                }
                if (colsNumStart != rowNumEx && colsNumStart != rowNumEx - 1) {
                    deliverDetail.addMergedRegion(
                            new CellRangeAddress(colsNumStart, rowNumEx - 1, 0, 0));
                    deliverDetail.addMergedRegion(
                            new CellRangeAddress(colsNumStart, rowNumEx - 1, 1, 1));
                    deliverDetail.addMergedRegion(
                            new CellRangeAddress(colsNumStart, rowNumEx - 1, 3, 3));
                    deliverDetail.addMergedRegion(
                            new CellRangeAddress(colsNumStart, rowNumEx - 1, 14, 14));
                    deliverDetail.addMergedRegion(
                            new CellRangeAddress(colsNumStart, rowNumEx - 1, 15, 15));
                    deliverDetail.addMergedRegion(
                            new CellRangeAddress(colsNumStart, rowNumEx - 1, 16, 16));
                    deliverDetail.addMergedRegion(
                            new CellRangeAddress(colsNumStart, rowNumEx - 1, 17, 17));
                    deliverDetail.addMergedRegion(
                            new CellRangeAddress(colsNumStart, rowNumEx - 1, 18, 18));
                    deliverDetail.addMergedRegion(
                            new CellRangeAddress(colsNumStart, rowNumEx - 1, 19, 19));
                }
            }
        }

        // 未能结转单sheet
        /*
         * 删除未结转订单sheet rowNumEx = 0; HSSFSheet unsettled = wb.createSheet("未结转订单"); row =
         * unsettled.createRow(rowNumEx); rowNumEx++; unsettled.setColumnWidth(0, 5500);
         * unsettled.setColumnWidth(1, 3500); unsettled.setColumnWidth(2, 8500);
         * unsettled.setColumnWidth(3, 5500); unsettled.setColumnWidth(4, 4500);
         * unsettled.setColumnWidth(5, 4000); unsettled.setColumnWidth(6, 3500);
         * unsettled.setColumnWidth(7, 3500); unsettled.setColumnWidth(8, 3500);
         * unsettled.setColumnWidth(9, 3500);
         * 
         * excel.cteateCell(wb, row, 0, cellStyle, "订单号"); excel.cteateCell(wb, row, 1, cellStyle,
         * "收货人"); excel.cteateCell(wb, row, 2, cellStyle, "收货地址"); excel.cteateCell(wb, row, 3,
         * cellStyle, "收货人联系方式"); excel.cteateCell(wb, row, 4, cellStyle, "订单来源");
         * excel.cteateCell(wb, row, 5, cellStyle, "商品列表");
         * 
         * row = unsettled.createRow(rowNumEx); rowNumEx++; excel.cteateCell(wb, row, 5,
         * cellStyleItem, "SKU号"); excel.cteateCell(wb, row, 6, cellStyleItem, "商品编号");
         * excel.cteateCell(wb, row, 7, cellStyleItem, "商品主标题"); excel.cteateCell(wb, row, 8,
         * cellStyleItem, "商品名称"); excel.cteateCell(wb, row, 9, cellStyleItem, "渠道");
         * excel.cteateCell(wb, row, 10, cellStyleItem, "单价"); excel.cteateCell(wb, row, 11,
         * cellStyleItem, "数量"); unsettled.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
         * unsettled.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
         * unsettled.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
         * unsettled.addMergedRegion(new CellRangeAddress(0, 1, 3, 3));
         * unsettled.addMergedRegion(new CellRangeAddress(0, 1, 4, 4));
         * unsettled.addMergedRegion(new CellRangeAddress(0, 0, 5, 11));
         * 
         * // 结转数据 if (orderDisList != null) { for (int i = 0, len = orderDisList.size(); i < len;
         * i++) { OrderMain orderMain = orderDisList.get(i); List<OrderItem> orderItemList = new
         * ArrayList<OrderItem>(); if (disexecuteMap != null) { orderItemList =
         * disexecuteMap.get(orderMain.getOrderCode()); }
         * 
         * colsNumStart = rowNumEx; row = unsettled.createRow(rowNumEx); rowNumEx++;
         * excel.cteateCell(wb, row, 0, cellStyleCon, getStringValue(orderMain.getOrderCode()));
         * excel.cteateCell(wb, row, 1, cellStyleCon, orderMain.getConsigneeName());
         * excel.cteateCell(wb, row, 2, cellStyleAddr, orderMain.getConsigneeAddr());
         * excel.cteateCell(wb, row, 3, cellStyleCon, orderMain.getConsigneeMobile());
         * excel.cteateCell(wb, row, 4, cellStyleCon, orderMain.getOrderChannel()); for (int j = 0,
         * jlen = orderItemList.size(); j < jlen; j++) { OrderItem orderItem = orderItemList.get(j);
         * excel.cteateCell(wb, row, 5, cellStyleCon, orderItem.getCommoditySku());
         * excel.cteateCell(wb, row, 6, cellStyleCon, orderItem.getCommodityCode());
         * excel.cteateCell(wb, row, 7, cellStyleAddr, orderItem.getCommodityTitle());
         * excel.cteateCell(wb, row, 8, cellStyleAddr, orderItem.getCommodityName());
         * excel.cteateCell(wb, row, 9, cellStyleAddr, ""); excel.cteateCell(wb, row, 10,
         * cellStyleCon, getStringValue(orderItem .getCommodityUnitPrice())); excel.cteateCell(wb,
         * row, 11, cellStyleCon, getStringValue(orderItem.getCommodityNumber())); row =
         * unsettled.createRow(rowNumEx); rowNumEx++;// 每个订单最后一行是空行 } if (colsNumStart != rowNumEx
         * && colsNumStart != rowNumEx - 1) { unsettled.addMergedRegion(new
         * CellRangeAddress(colsNumStart, rowNumEx - 2, 0, 0)); unsettled.addMergedRegion(new
         * CellRangeAddress(colsNumStart, rowNumEx - 2, 1, 1)); unsettled.addMergedRegion(new
         * CellRangeAddress(colsNumStart, rowNumEx - 2, 2, 2)); unsettled.addMergedRegion(new
         * CellRangeAddress(colsNumStart, rowNumEx - 2, 3, 3)); unsettled.addMergedRegion(new
         * CellRangeAddress(colsNumStart, rowNumEx - 2, 4, 4)); } } }
         */
        // 订单信息明细表 sheet
        /*
         * 删除订单信息明细表sheet rowNumEx = 0; HSSFSheet orderInfoDetail = wb.createSheet("订单信息明细表");
         * orderInfoDetail.setColumnWidth(0, 3500); orderInfoDetail.setColumnWidth(1, 3500);
         * orderInfoDetail.setColumnWidth(2, 3500); orderInfoDetail.setColumnWidth(3, 3500);
         * orderInfoDetail.setColumnWidth(4, 3500); orderInfoDetail.setColumnWidth(5, 3500);
         * orderInfoDetail.setColumnWidth(6, 3500); orderInfoDetail.setColumnWidth(7, 3500);
         * orderInfoDetail.setColumnWidth(8, 3500); orderInfoDetail.setColumnWidth(9, 3500);
         * orderInfoDetail.setColumnWidth(10, 3500); orderInfoDetail.setColumnWidth(11, 3500);
         * orderInfoDetail.setColumnWidth(12, 3500); orderInfoDetail.setColumnWidth(13, 3500);
         * orderInfoDetail.setColumnWidth(14, 3500); orderInfoDetail.setColumnWidth(15, 3500);
         * orderInfoDetail.setColumnWidth(16, 3500); orderInfoDetail.setColumnWidth(17, 3500);
         * orderInfoDetail.setColumnWidth(18, 3500); orderInfoDetail.setColumnWidth(19, 3500);
         * orderInfoDetail.setColumnWidth(20, 3500); orderInfoDetail.setColumnWidth(21, 3500);
         * orderInfoDetail.setColumnWidth(22, 3500); orderInfoDetail.setColumnWidth(23, 5500);
         * 
         * // 列名 row = orderInfoDetail.createRow(rowNumEx); rowNumEx++; excel.cteateCell(wb, row, 0,
         * cellStyle, "SKU号"); excel.cteateCell(wb, row, 1, cellStyle, "商品标题"); excel.cteateCell(wb,
         * row, 2, cellStyle, "商品名称"); excel.cteateCell(wb, row, 3, cellStyle, "产地");
         * excel.cteateCell(wb, row, 4, cellStyle, "规格"); excel.cteateCell(wb, row, 5, cellStyle,
         * "单位"); excel.cteateCell(wb, row, 6, cellStyle, "数量"); excel.cteateCell(wb, row, 7,
         * cellStyle, "产品单价"); excel.cteateCell(wb, row, 8, cellStyle, "产品金额总计");
         * excel.cteateCell(wb, row, 9, cellStyle, "运费"); excel.cteateCell(wb, row, 10, cellStyle,
         * "满减"); excel.cteateCell(wb, row, 11, cellStyle, "订单总计"); excel.cteateCell(wb, row, 12,
         * cellStyle, "仓库"); excel.cteateCell(wb, row, 13, cellStyle, "买家用户名"); excel.cteateCell(wb,
         * row, 14, cellStyle, "需要发票"); excel.cteateCell(wb, row, 15, cellStyle, "发票抬头");
         * excel.cteateCell(wb, row, 16, cellStyle, "开票金额"); excel.cteateCell(wb, row, 17,
         * cellStyle, "买家备注"); excel.cteateCell(wb, row, 18, cellStyle, "客服备注");
         * excel.cteateCell(wb, row, 19, cellStyle, "收件人"); excel.cteateCell(wb, row, 20, cellStyle,
         * "电话"); excel.cteateCell(wb, row, 21, cellStyle, "收货地址"); excel.cteateCell(wb, row, 22,
         * cellStyle, "订单号"); excel.cteateCell(wb, row, 23, cellStyle, "药方图片地址");
         * 
         * if (orderExList != null) { for (int i = 0, len = orderExList.size(); i < len; i++) {
         * OrderMain orderMain = orderExList.get(i); List<OrderItem> orderItemList = new
         * ArrayList<OrderItem>(); if (ExecutedMap != null) { orderItemList =
         * ExecutedMap.get(orderMain.getOrderCode()); } colsNumStart = rowNumEx; for (int j = 0,
         * jlen = orderItemList.size(); j < jlen; j++) { OrderItem orderItem = orderItemList.get(j);
         * row = orderInfoDetail.createRow(rowNumEx); rowNumEx++; excel.cteateCell(wb, row, 0,
         * cellStyleCon, orderItem.getCommoditySku()); excel.cteateCell(wb, row, 1, cellStyleCon,
         * orderItem.getCommodityTitle()); excel.cteateCell(wb, row, 2, cellStyleCon,
         * orderItem.getCommodityName()); // 3产地 excel.cteateCell(wb, row, 4, cellStyleCon,
         * orderItem.getCommoditySkuDescription()); // 5单位 excel .cteateCell(wb, row, 6,
         * cellStyleCon, getStringValue(orderItem.getCommodityNumber())); excel.cteateCell(wb, row,
         * 7, cellStyleCon, getStringValue(orderItem .getCommodityUnitPrice()));
         * excel.cteateCell(wb, row, 8, cellStyleCon, getStringValue(orderItem.getCommoditySum()));
         * if (colsNumStart == rowNumEx - 1) { excel.cteateCell(wb, row, 9, cellStyleCon,
         * getStringValue(orderMain.getFare())); if (null == orderMain.getParentOrderCode() || 0 ==
         * orderMain.getParentOrderCode().length()) { excel.cteateCell(wb, row, 10, cellStyleCon,
         * "-" + getStringValue(orderMain.getOriginalOrderSum().add(orderMain.getFare())
         * .subtract(orderMain.getAmountPayable()))); } excel.cteateCell(wb, row, 11, cellStyleCon,
         * getStringValue(orderMain.getAmountPayable())); // excel.cteateCell(wb, row, 12,
         * cellStyleCon, // getStringValue(orderMain.getInvoiceMoney())); excel.cteateCell(wb, row,
         * 12, cellStyleCon, orderItem.getWarehouseName()); excel.cteateCell(wb, row, 13,
         * cellStyleCon, orderMain.getCustomerAccount()); if ("Y".equals(orderMain.getHasInvoice()))
         * { excel.cteateCell(wb, row, 14, cellStyleCon, "是"); excel.cteateCell(wb, row, 15,
         * cellStyleCon, getStringValue(orderMain .getInvoiceInfoTitle())); excel.cteateCell(wb,
         * row, 16, cellStyleCon, getStringValue(orderMain .getInvoiceMoney())); } else {
         * excel.cteateCell(wb, row, 14, cellStyleCon, ""); excel.cteateCell(wb, row, 15,
         * cellStyleCon, ""); excel.cteateCell(wb, row, 16, cellStyleCon, ""); }
         * excel.cteateCell(wb, row, 17, cellStyleCon, orderMain.getOrderDescription());
         * excel.cteateCell(wb, row, 18, cellStyleCon, orderMain.getOrderOperationRemark());
         * excel.cteateCell(wb, row, 19, cellStyleCon, orderMain.getConsigneeName());
         * excel.cteateCell(wb, row, 20, cellStyleCon, orderMain.getConsigneeMobile());
         * excel.cteateCell(wb, row, 21, cellStyleCon, orderMain.getConsigneeAddr());
         * excel.cteateCell(wb, row, 22, cellStyleCon, orderMain.getOrderCode());
         * excel.cteateCell(wb, row, 23, cellStyleCon, orderMain.getPrescriptionAttachment()); } }
         * if (colsNumStart != rowNumEx && colsNumStart != rowNumEx - 1) {
         * orderInfoDetail.addMergedRegion(new CellRangeAddress(colsNumStart, rowNumEx - 1, 9, 9));
         * orderInfoDetail.addMergedRegion(new CellRangeAddress(colsNumStart, rowNumEx - 1, 10,
         * 10)); orderInfoDetail.addMergedRegion(new CellRangeAddress(colsNumStart, rowNumEx - 1,
         * 11, 11)); orderInfoDetail.addMergedRegion(new CellRangeAddress(colsNumStart, rowNumEx -
         * 1, 12, 12)); orderInfoDetail.addMergedRegion(new CellRangeAddress(colsNumStart, rowNumEx
         * - 1, 13, 13)); orderInfoDetail.addMergedRegion(new CellRangeAddress(colsNumStart,
         * rowNumEx - 1, 14, 14)); orderInfoDetail.addMergedRegion(new
         * CellRangeAddress(colsNumStart, rowNumEx - 1, 15, 15));
         * orderInfoDetail.addMergedRegion(new CellRangeAddress(colsNumStart, rowNumEx - 1, 16,
         * 16)); orderInfoDetail.addMergedRegion(new CellRangeAddress(colsNumStart, rowNumEx - 1,
         * 17, 17)); orderInfoDetail.addMergedRegion(new CellRangeAddress(colsNumStart, rowNumEx -
         * 1, 18, 18)); orderInfoDetail.addMergedRegion(new CellRangeAddress(colsNumStart, rowNumEx
         * - 1, 19, 19)); orderInfoDetail.addMergedRegion(new CellRangeAddress(colsNumStart,
         * rowNumEx - 1, 20, 20)); orderInfoDetail.addMergedRegion(new
         * CellRangeAddress(colsNumStart, rowNumEx - 1, 21, 21));
         * orderInfoDetail.addMergedRegion(new CellRangeAddress(colsNumStart, rowNumEx - 1, 22,
         * 22)); orderInfoDetail.addMergedRegion(new CellRangeAddress(colsNumStart, rowNumEx - 1,
         * 23, 23)); orderInfoDetail.addMergedRegion(new CellRangeAddress(colsNumStart, rowNumEx -
         * 1, 24, 24)); } } }
         */

        // 发货订单发票信息 sheet
        rowNumEx = 0;
        HSSFSheet invoiceInfoSheet = wb.createSheet("订单发票信息");
        invoiceInfoSheet.setColumnWidth(0, 3500);
        invoiceInfoSheet.setColumnWidth(1, 3500);
        invoiceInfoSheet.setColumnWidth(2, 4500);
        invoiceInfoSheet.setColumnWidth(3, 3500);
        invoiceInfoSheet.setColumnWidth(4, 3500);
        invoiceInfoSheet.setColumnWidth(5, 3500);
        invoiceInfoSheet.setColumnWidth(6, 5500);
        // 列名
        row = invoiceInfoSheet.createRow(rowNumEx);
        rowNumEx++;
        excel.cteateCell(wb, row, 0, cellStyle, "主订单号");
        excel.cteateCell(wb, row, 1, cellStyle, "用户名");
        excel.cteateCell(wb, row, 2, cellStyle, "发票抬头");
        excel.cteateCell(wb, row, 3, cellStyle, "开票金额");
        excel.cteateCell(wb, row, 4, cellStyle, "收货人");
        excel.cteateCell(wb, row, 5, cellStyle, "收货人电话");
        excel.cteateCell(wb, row, 6, cellStyle, "收货地址");

        if (orderExList != null) {
            for (int i = 0, len = orderExList.size(); i < len; i++) {
                OrderMain orderMain = orderExList.get(i);
                if (null != orderMain.getInvoiceInfoType()
                        || null != orderMain.getInvoiceInfoTitle()
                        || null != orderMain.getInvoiceInfoContent()) { // 买家要求开具发票才导出
                    row = invoiceInfoSheet.createRow(rowNumEx);
                    rowNumEx++;
                    excel.cteateCell(wb, row, 0, cellStyleCon, orderMain.getOrderCode());
                    excel.cteateCell(wb, row, 1, cellStyleCon, orderMain.getCustomerAccount());
                    excel.cteateCell(wb, row, 2, cellStyleCon, orderMain.getInvoiceInfoTitle());
                    excel.cteateCell(wb, row, 3, cellStyleCon,
                            getStringValue(orderMain.getInvoiceMoney()));
                    excel.cteateCell(wb, row, 4, cellStyleCon, orderMain.getConsigneeName());
                    excel.cteateCell(wb, row, 5, cellStyleCon, orderMain.getConsigneeMobile());
                    excel.cteateCell(wb, row, 6, cellStyleCon, orderMain.getConsigneeAddr());
                }
            }
        }


        /*
         * 删除进销存订单数据sheet rowNumEx = 0; HSSFSheet jxcTemplate = wb.createSheet("进销存订单数据导入");
         * jxcTemplate.setColumnWidth(0, 3500); jxcTemplate.setColumnWidth(1, 3500);
         * jxcTemplate.setColumnWidth(2, 4500); jxcTemplate.setColumnWidth(3, 3500);
         * jxcTemplate.setColumnWidth(4, 3500); jxcTemplate.setColumnWidth(5, 3500);
         * jxcTemplate.setColumnWidth(6, 3500); jxcTemplate.setColumnWidth(7, 3500);
         * jxcTemplate.setColumnWidth(8, 3500); jxcTemplate.setColumnWidth(9, 3500);
         * jxcTemplate.setColumnWidth(10, 3500); jxcTemplate.setColumnWidth(11, 3500);
         * jxcTemplate.setColumnWidth(12, 3500); jxcTemplate.setColumnWidth(13, 3500);
         * jxcTemplate.setColumnWidth(14, 3500); jxcTemplate.setColumnWidth(15, 3500);
         * jxcTemplate.setColumnWidth(16, 3500); jxcTemplate.setColumnWidth(17, 5500); // 列名 row =
         * jxcTemplate.createRow(rowNumEx); rowNumEx++; excel.cteateCell(wb, row, 0, cellStyle,
         * "订单来源"); excel.cteateCell(wb, row, 1, cellStyle, "产品编号"); excel.cteateCell(wb, row, 2,
         * cellStyle, "订单号"); excel.cteateCell(wb, row, 3, cellStyle, "产品归属"); excel.cteateCell(wb,
         * row, 4, cellStyle, "品名"); excel.cteateCell(wb, row, 5, cellStyle, "产地");
         * excel.cteateCell(wb, row, 6, cellStyle, "规格"); excel.cteateCell(wb, row, 7, cellStyle,
         * "单位"); excel.cteateCell(wb, row, 8, cellStyle, "数量"); excel.cteateCell(wb, row, 9,
         * cellStyle, "单价"); excel.cteateCell(wb, row, 10, cellStyle, "总价"); excel.cteateCell(wb,
         * row, 11, cellStyle, "买家账号"); excel.cteateCell(wb, row, 12, cellStyle, "买家备注");
         * excel.cteateCell(wb, row, 13, cellStyle, "客服备注"); excel.cteateCell(wb, row, 14,
         * cellStyle, "收件人"); excel.cteateCell(wb, row, 15, cellStyle, "手机"); excel.cteateCell(wb,
         * row, 16, cellStyle, "电话"); excel.cteateCell(wb, row, 17, cellStyle, "地址");
         * 
         * if (orderExList != null) { final String orderSource = "康美健康"; for (int i = 0, len =
         * orderExList.size(); i < len; i++) { OrderMain orderMain = orderExList.get(i);
         * List<OrderItem> orderItemList = new ArrayList<OrderItem>(); if (ExecutedMap != null) {
         * orderItemList = ExecutedMap.get(orderMain.getOrderCode()); } for (int j = 0, jlen =
         * orderItemList.size(); j < jlen; j++) { OrderItem orderItem = orderItemList.get(j); row =
         * jxcTemplate.createRow(rowNumEx); rowNumEx++; excel.cteateCell(wb, row, 0, cellStyleCon,
         * orderSource); OrderItemExt oie = orderItem.getOrderItemExt(); if (null != oie) {
         * excel.cteateCell(wb, row, 1, cellStyleCon, oie.getInvoicingCode()); }
         * excel.cteateCell(wb, row, 2, cellStyleCon, orderItem.getOrderCode()); if (null !=
         * orderItem.getSupplierType() && (SuppliersType.EMTER.getStatus().longValue() ==
         * orderItem.getSupplierType() || SuppliersType.SUPPORT .getStatus().longValue() ==
         * orderItem.getSupplierType())) { if ("康美药业OTC".equals(orderItem.getSupplierName())) {
         * excel.cteateCell(wb, row, 3, cellStyleCon, "OTC产品"); } else if
         * ("广东康美之恋大药房连锁有限公司".equals(orderItem.getSupplierName()) ||
         * "康美按方抓药".equals(orderItem.getSupplierName())) { excel.cteateCell(wb, row, 3,
         * cellStyleCon, "OTC产品"); } else if ("康美人生".equals(orderItem.getSupplierName())) {
         * excel.cteateCell(wb, row, 3, cellStyleCon, "康美人生"); } else { excel.cteateCell(wb, row, 3,
         * cellStyleCon, "电商自营"); } } else { excel.cteateCell(wb, row, 3, cellStyleCon,
         * orderItem.getSupplierTypeName()); } excel.cteateCell(wb, row, 4, cellStyleCon,
         * orderItem.getCommodityTitle()); // 5产地 excel.cteateCell(wb, row, 6, cellStyleCon,
         * orderItem.getCommoditySkuDescription()); // 7单位 excel .cteateCell(wb, row, 8,
         * cellStyleCon, getStringValue(orderItem.getCommodityNumber())); excel.cteateCell(wb, row,
         * 9, cellStyleCon, getStringValue(orderItem .getCommodityUnitIncoming()));
         * excel.cteateCell(wb, row, 10, cellStyleCon, orderItem.getCommodityUnitIncoming()
         * .multiply(new BigDecimal(orderItem.getCommodityNumber())).toString());
         * excel.cteateCell(wb, row, 11, cellStyleCon, orderMain.getCustomerAccount());
         * excel.cteateCell(wb, row, 12, cellStyleCon, orderMain.getOrderDescription());
         * excel.cteateCell(wb, row, 13, cellStyleCon, orderMain.getOrderOperationRemark());
         * excel.cteateCell(wb, row, 14, cellStyleCon, orderMain.getConsigneeName());
         * excel.cteateCell(wb, row, 15, cellStyleCon, orderMain.getConsigneeMobile());
         * excel.cteateCell(wb, row, 16, cellStyleCon, orderMain.getConsigneeTel());
         * excel.cteateCell(wb, row, 17, cellStyleCon, orderMain.getConsigneeAddr()); } } }
         */


        List<String> path = getExcelName(fix);
        FileOutputStream fileOut = new FileOutputStream(path.get(0));
        wb.write(fileOut);
        fileOut.close();
        log.info("生成excel文件完成！");
        return getVisitPath(path.get(1));
    }

    private String getStringValue(Object obj) {
        String result = "";
        if (null != obj) {
            if (obj instanceof TIMESTAMP) {
                TIMESTAMP t = (TIMESTAMP) obj;
                try {
                    return sdf.format(t.timestampValue());
                } catch (Exception e) {
                }
            } else if (obj instanceof BigDecimal) {
                BigDecimal temp = ((BigDecimal) obj).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                return temp.toString();
            } else {
                result = obj.toString();
            }
        }
        return result;
    }

    private String getVisitPath(String path) {
        String visitPath = ConfigurationUtil.getString("visitPath");
        visitPath = visitPath + path;
        return visitPath;
    }

    /**
     * 获取excel保存地址
     * 
     * @return
     */
    private List<String> getExcelName(String fix) {
        List<String> liststr = new ArrayList<String>();
        String excelPath = ConfigurationUtil.getString("path");
        String path = null;
        String pathFix = null;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        pathFix = "/" + cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/"
                + cal.get(Calendar.DATE);
        excelPath = excelPath + pathFix;
        path = excelPath + "/" + sdf.format(cal.getTime()) + "_" + fix + ".xls";

        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        // 组装外部访问路径
        String pathfile = pathFix + "/" + sdf.format(cal.getTime()) + "_" + fix + ".xls";
        liststr.add(path);
        liststr.add(pathfile);
        return liststr;
    }

    /**
     * 转换实体类
     * 
     * @param oi
     * @return
     */
    private SKUEntity getVOByOrdItem(OrderItem oi) {
        SKUEntity se = new SKUEntity();
        se.setCommoditySku(oi.getCommoditySku());
        se.setCommodityBatchNumber(oi.getCommodityBatchNumber());
        se.setCommodityCode(oi.getCommodityCode());
        se.setCommodityName(oi.getCommodityName());
        se.setCommodityNumber(oi.getCommodityNumber());
        se.setCommodityUnitPrice(oi.getCommodityUnitPrice());
        se.setCommodityTitle(oi.getCommodityTitle());
        se.setCommoditySkuDescription(oi.getCommoditySkuDescription());
        se.setWarehouseName(oi.getWarehouseName());
        se.setSupplierTypeName(oi.getSupplierTypeName());
        return se;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public Integer listCount(Map map) throws ServiceException {
        try {
            return orderCarryDAO.countByMap(map);
        } catch (Exception e) {
            log.error(e);
            throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_ORDER_ERROR,
                    "获取结转列表计数时发生异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public List listOrder(Map map) throws ServiceException {
        try {
            return orderCarryDAO.selectByMap(map);
        } catch (Exception e) {
            log.error(e);
            throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_ORDER_ERROR,
                    "获取结转列表时发生异常：" + e.getMessage());
        }
    }

    /**
     * 批量更新订单状态
     * 
     * @param list
     * @param status
     * @return
     * @throws ServiceException
     */
    private boolean updateOrderStatus(List<OrderMain> list, Long status, Long settlementNo)
            throws ServiceException {
        boolean result = false;
        if (list != null) {
            List<String> orderCodes = new ArrayList<String>();
            Map<String, Object> params = new HashMap<String, Object>();
            try {
                for (OrderMain om : list) {
                    orderCodes.add(om.getOrderCode());
                    if (orderCodes.size() == 999) {
                        params.put("orderCodes", orderCodes);
                        params.put("orderStatus", status);
                        params.put("settlementNo", settlementNo);
                        result = orderMainDAO.batchUpdateOrderStatus(params) > 0;
                        orderCodes.clear();
                    }
                }
                if (!orderCodes.isEmpty()) {
                    params.put("orderCodes", orderCodes);
                    params.put("orderStatus", status);
                    params.put("settlementNo", settlementNo);
                    result = orderMainDAO.batchUpdateOrderStatus(params) > 0;
                }
            } catch (Exception e) {
                throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_PRODUCT_ERROR,
                        "批量更新订单状态：" + e.getMessage());
            }
        }
        return result;
    }

    /**
     * 查询结转最近的时间
     * 
     * @param operator
     * @return
     * @throws ServiceException
     */
    @Override
    public Date queryMaxCreateDate(String operator) throws ServiceException {
        try {
            return orderCarryDAO.queryMaxCreateDate(operator);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INTER_CARRYOVER_ERROR, e.getMessage());
        }
    }

    public static final ExecutorService executors =
            Executors.newFixedThreadPool(Constants.EXECUTE_THREAD_MAX);// 线程池

    /**
     * 查询出可结转的订单的订单项集合
     * 
     * @param list
     * @return map<订单号,订单号对应的订单明细列表>
     * @throws ServiceException
     */
    private Map<String, List<OrderItem>> orderItemQuery(List<OrderMain> list)
            throws ServiceException {
        try {
            if (list != null && list.size() > 0) {
                List<String> orderCodes = new ArrayList<String>();
                if (list.size() < 999) {
                    for (OrderMain om : list) {
                        orderCodes.add(om.getOrderCode());
                    }
                    return orderItemDAO.queryExecOrderItem(orderCodes);
                } else {
                    Map<String, List<OrderItem>> map = new HashMap<String, List<OrderItem>>();
                    for (int i = 0, len =
                            (list.size() / 999) + (list.size() % 999 == 0 ? 0 : 1); i < len; i++) {
                        orderCodes.clear();
                        for (int j = i * 999, jlen =
                                (j + 999) > list.size() ? list.size() : (j + 999); j < jlen; j++) {
                            orderCodes.add(list.get(j).getOrderCode());
                        }
                        map.putAll(orderItemDAO.queryExecOrderItem(orderCodes));
                    }
                    return map;
                }
            }
        } catch (Exception e) {
            throw new ServiceException(0, "查询出可结转的订单的订单项集合发生异常", e);
        }
        return null;
    }

    /**
     * 移除结转标识
     * 
     * @param symbol
     * @throws ServiceException
     */
    @Override
    public void removeExecuteSymbol(String symbol) throws ServiceException {
        try {
            jedisCluster.srem(Constants.EXECUTE_SYMBOL_SET, symbol);
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * 订单结转统一入口
     * 
     * @param symbol2modify 修改渠道，不再使用这个参数
     * @param executeCondMap
     * @return
     */
    @Override
    public OrderCarry OrderExecuteEntrance(final String symbol2modify,
            final Map<String, Object> executeCondMap) throws ServiceException {
        try {
            final String symbol = "B2B";// 增加-20161024
            final OrderCarry oc = new OrderCarry();
            executors.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 缓存中不存在标示可以结转，结转前锁定，结转后释放
                        if (!jedisCluster.sismember(Constants.EXECUTE_SYMBOL_SET, symbol)) {// sismember
                            // 判断symbol是否已经存在缓存中，用于避免重复结转问题
                            jedisCluster.sadd(Constants.EXECUTE_SYMBOL_SET, symbol);
                            try {
                                oc.setHandleId(0l);
                                List<OrderMain> orderList =
                                        orderMainDAO.selectByOrderExecute(executeCondMap);
                                if (null != orderList && !orderList.isEmpty()) {
                                    oc.setHandleId(1l);
                                    String operator = null == executeCondMap.get("operator")
                                            ? symbol : executeCondMap.get("operator").toString();
                                    String commerceId = null == executeCondMap.get("commerceId")
                                            ? null : executeCondMap.get("commerceId").toString();// 此参数区别结转的是否是第三方还是自营的，null为自营
                                    orderExecute(oc, orderList, orderItemQuery(orderList), operator,
                                            commerceId);
                                }
                            } catch (Exception e) {
                                log.error("订单结转异常：" + e.getMessage());
                            }
                            jedisCluster.srem(Constants.EXECUTE_SYMBOL_SET, symbol);
                        } else {
                            oc.setHandleId(-1l);// 正在执行
                        }
                    } catch (Exception e) {
                        log.error(e);
                    }
                }
            });
            Thread.sleep(5000);
            return oc;
        } catch (Exception e) {
            log.error("订单结转发生异常" + e.getMessage());
            throw new ServiceException(0, "订单结转任务发生异常" + e.getMessage(), e);
        }
    }

    /**
     * 订单结转任务
     * 
     * @param orderItemMap
     * @param oc
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    private void orderExecute(final OrderCarry oc, List<OrderMain> orderList,
            Map<String, List<OrderItem>> orderItemMap, String operator, String commerceId)
            throws ServiceException {
        try {
            if (null != orderList && !orderList.isEmpty() && null != orderItemMap
                    && !orderItemMap.isEmpty()) {
                List<String> wareSkuList = new ArrayList<String>();// 存储所结转的订单下所有商品的仓库_SKUID
                Long wareHouse = null;
                List<OrderItem> oiListTemp = null, orderSplitItem = null;// 订单明细/拆单明细
                Map<String, Map<Long, List<OrderItem>>> splitOrderCodes =
                        new HashMap<String, Map<Long, List<OrderItem>>>();// 某订单下需要拆单的订单明细
                                                                          // map<ordercode,map<仓库id,orderCode对应的订单明细集合>
                Map<Long, List<OrderItem>> splitMap = null;// 需要拆单的订单明细集合 map<仓库id,订单明细集合>
                boolean isSplit = false;// 判断是否需要拆单
                // 循环得到需要拆单的订单明细集合 splitOrderCodes map<ordercode,map<仓库id,orderCode对应的订单明细集合>
                for (String orderCode : orderItemMap.keySet()) {// 1循环订单 orderItemMap --
                                                                // map<ordercode,orderCode对应的订单明细集合》
                    oiListTemp = orderItemMap.get(orderCode);// 某订单orderCode的订单明细
                    isSplit = false;
                    wareHouse = oiListTemp.get(0).getWarehouseId().longValue();// 某订单某种商品的仓库id
                    for (OrderItem oi : oiListTemp) {// 2循环某订单下的订单明细
                        wareSkuList.add(oi.getWarehouseId() + "_" + oi.getSkuId());
                        if (!isSplit && wareHouse != oi.getWarehouseId().longValue()) {// 如果某订单下所有商品都为同一个仓库则不进行拆单
                            isSplit = true;
                        }
                    }
                    if (isSplit) {
                        splitMap = new HashMap<Long, List<OrderItem>>();
                        for (OrderItem oi : oiListTemp) {
                            orderSplitItem = splitMap.get(oi.getWarehouseId().longValue());
                            if (null == orderSplitItem) {
                                orderSplitItem = new ArrayList<OrderItem>();
                            }
                            orderSplitItem.add((OrderItem) oi.clone());
                            splitMap.put(oi.getWarehouseId().longValue(), orderSplitItem);
                        }
                        splitOrderCodes.put(orderCode, splitMap);
                    }
                }
                if (!splitOrderCodes.isEmpty()) {// 根据仓库拆单
                    orderAlterService.splitOrderByWarehouse(orderList, orderItemMap,
                            splitOrderCodes, operator);
                }

                Map<String, Integer> wl = null;
                if (wareSkuList.size() < 999) {
                    wl = orderItemDAO.queryOrderItemStock(wareSkuList);// 查询仓库下各sku_id对应的库存数 wl =
                                                                       // map<WarehouseId() + "_" +
                                                                       // getSkuId(),库存数)
                } else {
                    wl = new HashMap<String, Integer>();
                    List<String> wares = new ArrayList<String>();
                    for (int i = 0, len = (wareSkuList.size() / 999)
                            + (wareSkuList.size() % 999 == 0 ? 0 : 1); i < len; i++) {
                        wares.clear();
                        for (int j = i * 999, jlen = (j + 999) > wareSkuList.size()
                                ? wareSkuList.size() : (j + 999); j < jlen; j++) {
                            wares.add(wareSkuList.get(j));
                        }
                        wl.putAll(orderItemDAO.queryOrderItemStock(wares));
                    }
                }
                executeCore(orderList, orderItemMap, wl, null != commerceId, operator);
            }
        } catch (Exception e) {
            throw new ServiceException(0, "订单结转任务发生异常", e);
        }
    }

    /**
     * 结转核心方法
     */
    private void executeCore(List<OrderMain> orderList, Map<String, List<OrderItem>> orderItemMap,
            Map<String, Integer> stockMap, boolean isCommerceId, String operator)
            throws ServiceException {
        try {
            Integer stock = 0;// 库存
            int status = OrderDictionaryEnum.Order_Status.Split_Done.getKey();// 已拆分不处理
            Map<String, Integer> tempStockMap = new HashMap<String, Integer>();// 临时库存列表，用于单个订单判断是否存在库存超出商品
            boolean settFlag = true;// 结转标识
            String key = null;
            List<OrderMain> settList = new ArrayList<OrderMain>(),
                    settUnOutList = new ArrayList<OrderMain>(),
                    unSettList = new ArrayList<OrderMain>();// 已出库/未出库/未结转
            Map<String, List<OrderItem>> settItem = new HashMap<String, List<OrderItem>>(),
                    settUnOutItem = new HashMap<String, List<OrderItem>>(),
                    unSettMap = new HashMap<String, List<OrderItem>>();// 已出库/未出库/未结转

            List<OrderMain> unSettListForSd = new ArrayList<OrderMain>();// 康美中药城其他入驻商家未结转订单列表
            List<OrderMain> settUnOutListForSd = new ArrayList<OrderMain>();// 康美中药城其他入驻商家已结转未出库订单列表
            List<OrderMain> SettListForSd = new ArrayList<OrderMain>();// 康美中药城其他入驻商家已出库订单列表
            Map<String, List<OrderItem>> SettItemForSd = new HashMap<String, List<OrderItem>>(); // 康美中药城其他入驻商家已出库订单明细列表

            List<OrderMain> unSettListForDs = new ArrayList<OrderMain>();// 驻商家是康美电商的未结转订单列表
            List<OrderMain> settUnOutListForDs = new ArrayList<OrderMain>();// 驻商家是康美电商的已结转未出库订单列表
            List<OrderMain> SettListForDs = new ArrayList<OrderMain>();// 驻商家是康美电商的已出库订单列表
            Map<String, List<OrderItem>> SettItemForDs = new HashMap<String, List<OrderItem>>(); // 入驻商家是康美电商的已出库订单明细列表

            for (OrderMain om : orderList) {
                OrderMain checkStuts = orderMainDAO.selectByPrimaryKey(om.getOrderId());
                if (om.getOrderStatus().intValue() == status
                        || checkStuts.getOrderStatus().intValue() < 2) {// 父订单(此时状态为已拆分)和未付款的订单不做结转
                    continue;
                }
                try {
                    settFlag = true;
                    tempStockMap.clear();
                    for (OrderItem oi : orderItemMap.get(om.getOrderCode())) {// 此时om.getOrderCode为拆分后的订单号
                        key = oi.getWarehouseId() + "_" + oi.getSkuId();
                        stock = tempStockMap.containsKey(key) ? tempStockMap.get(key)
                                : stockMap.get(key);
                        if (null == stock || stock < oi.getCommodityNumber()) {
                            log.info("订单" + om.getOrderCode() + "库存不足，结转失败：产品"
                                    + oi.getCommoditySku() + "实时库存" + stock);
                            settFlag = false;
                            unSettList.add(om);// 添加未结转的订单
                            unSettMap.put(om.getOrderCode(), orderItemMap.get(om.getOrderCode()));
                            if (!isCommerceId) {
                                String supplier = oi.getSupplier();
                                if (null != supplier && supplier.equals("222")) {// 入驻商家为康美电商
                                    unSettListForDs.add(om);
                                } else {// 其他入驻商家
                                    unSettListForSd.add(om);
                                }
                            }
                            break;// 库存不足不结转
                        }
                        tempStockMap.put(key, stock - oi.getCommodityNumber().intValue());
                    }
                    if (!settFlag) {
                        continue;
                    }
                    stockMap.putAll(tempStockMap);
                    try {
                        if (settlementAndPayUtil.productStockOut(isCommerceId, om,
                                orderItemMap.get(om.getOrderCode()))) {
                            settList.add(om);
                            List<OrderItem> itemList = orderItemMap.get(om.getOrderCode());
                            settItem.put(om.getOrderCode(), itemList);

                            /* 为入驻商家的结转订单进行订单，订单明细拆分，用于后面分别导出结转报表 */
                            if (!isCommerceId) {// 入驻商家依据康美电商入驻和其他入驻供应商拆分订单和明细
                                List<OrderItem> itemListTempForSd = new ArrayList<OrderItem>(); // 临时存储其他入驻商家的订单明细列表
                                List<OrderItem> itemListTempForDs = new ArrayList<OrderItem>(); // 临时存储入驻商家为康美电商的订单明细列表
                                for (int k = 0; k < itemList.size(); k++) {
                                    OrderItem item = itemList.get(k);
                                    String supplier = item.getSupplier();
                                    if (null != supplier && supplier.equals("222")) {// 入驻商家为康美电商
                                        itemListTempForDs.add(item);
                                    } else {// 其他入驻商家
                                        itemListTempForSd.add(item);
                                    }
                                }
                                if (itemListTempForDs.size() > 0) {// 存在入驻商家为康美电商的订单明细
                                    SettListForDs.add(om);
                                    SettItemForDs.put(om.getOrderCode(), itemListTempForDs);
                                }

                                if (itemListTempForSd.size() > 0) {// 存在其他入驻商家的订单明细
                                    SettListForSd.add(om);
                                    SettItemForSd.put(om.getOrderCode(), itemListTempForSd);
                                }
                            }
                        } else {
                            settUnOutList.add(om);
                            settUnOutItem.put(om.getOrderCode(),
                                    orderItemMap.get(om.getOrderCode()));

                            List<OrderItem> itemList = orderItemMap.get(om.getOrderCode());

                            if (!isCommerceId) {// 入驻商家依据康美电商入驻和其他入驻供应商拆分订单和明细
                                List<OrderItem> itemListTempForSd = new ArrayList<OrderItem>(); // 临时存储其他入驻商家的订单明细列表
                                List<OrderItem> itemListTempForDs = new ArrayList<OrderItem>(); // 临时存储入驻商家为康美电商的订单明细列表
                                for (int k = 0; k < itemList.size(); k++) {
                                    OrderItem item = itemList.get(k);
                                    String supplier = item.getSupplier();
                                    if (null != supplier && supplier.equals("222")) {// 入驻商家为康美电商
                                        itemListTempForDs.add(item);
                                    } else {// 其他入驻商家
                                        itemListTempForSd.add(item);
                                    }
                                }
                                if (itemListTempForDs.size() > 0) {// 存在入驻商家为康美电商的订单明细
                                    settUnOutListForDs.add(om);
                                }

                                if (itemListTempForSd.size() > 0) {// 存在其他入驻商家的订单明细
                                    settUnOutListForSd.add(om);
                                }
                            }
                            log.error(om.getOrderCode() + "出库失败！");
                        }
                    } catch (Exception e) {
                        log.error(om.getOrderCode() + "出库失败！" + e.getMessage());
                        settUnOutList.add(om);
                        settUnOutItem.put(om.getOrderCode(), orderItemMap.get(om.getOrderCode()));

                        List<OrderItem> itemList = orderItemMap.get(om.getOrderCode());

                        if (!isCommerceId) {// 入驻商家依据康美电商入驻和其他入驻供应商拆分订单和明细
                            List<OrderItem> itemListTempForSd = new ArrayList<OrderItem>(); // 临时存储其他入驻商家的订单明细列表
                            List<OrderItem> itemListTempForDs = new ArrayList<OrderItem>(); // 临时存储入驻商家为康美电商的订单明细列表
                            for (int k = 0; k < itemList.size(); k++) {
                                OrderItem item = itemList.get(k);
                                String supplier = item.getSupplier();
                                if (null != supplier && supplier.equals("222")) {// 入驻商家为康美电商
                                    itemListTempForDs.add(item);
                                } else {// 其他入驻商家
                                    itemListTempForSd.add(item);
                                }
                            }
                            if (itemListTempForDs.size() > 0) {// 存在入驻商家为康美电商的订单明细
                                settUnOutListForDs.add(om);
                            }

                            if (itemListTempForSd.size() > 0) {// 存在其他入驻商家的订单明细
                                settUnOutListForSd.add(om);
                            }
                        }

                    }
                } catch (Exception e) {
                    log.error(om.getOrderCode() + "结转失败！" + e.getMessage());
                }
            }

            String path = null;
            if (isCommerceId) { // 供应商结转报表生成
                List<SKUEntity> listSKU = statisticsSUK(settList, settItem);
                path = this.createExcelFile(operator, listSKU, settList, settItem, unSettList,
                        unSettMap);// 生成Excel
                this.doCarryAndSome(settList, unSettList, settUnOutList, path, operator);

            } else { // 入驻商家结转报表生成
                if (SettListForDs.size() > 0 && SettItemForDs.size() > 0) { // 入驻商家为康美电商
                    List<SKUEntity> listSKUForDs = statisticsSUK(SettListForDs, SettItemForDs);
                    path = this.createExcelFileForDs("KMDS", listSKUForDs, SettListForDs,
                            SettItemForDs);
                    this.doCarryAndSome(SettListForDs, unSettListForDs, settUnOutListForDs, path,
                            operator);
                }

                if (SettListForSd.size() > 0 && SettItemForSd.size() > 0) {// 其他入驻商家
                    List<SKUEntity> listSKUForSd = statisticsSUK(SettListForSd, SettItemForSd);
                    path = this.createExcelFileForSd("KMB2B", listSKUForSd, SettListForSd,
                            SettItemForSd);
                    this.doCarryAndSome(SettListForSd, unSettListForSd, settUnOutListForSd, path,
                            operator);
                }
            }

            /*
             * OrderCarry orderCarry = new OrderCarry(); orderCarry.setExcelAddress(path);
             * orderCarry.setCreateDate(new Date()); orderCarry.setOrderSum((long)
             * settList.size());// 结转的订单数 orderCarry.setNoOrderSum((long) unSettList.size());//
             * 未结转的订单数 orderCarry.setOperator(operator); Long result =
             * orderCarryDAO.insert(orderCarry);// 保存结转单实体 if (!settUnOutList.isEmpty()) { // 已结转未出库
             * updateOrderStatus(settUnOutList, (long)
             * OrderDictionaryEnum.Order_Status.Settle_Not_Stock .getKey(), result);
             * orderOperateStatementService.insertByList(settUnOutList, operator, "您的订单中的商品暂未出库",
             * (long) OrderDictionaryEnum.Order_Status.Settle_Not_Stock.getKey(), (long)
             * OrderDictionaryEnum.OrderOperateType.Settle.getKey()); } if (!settList.isEmpty()) {
             * // 已出库 updateOrderStatus(settList, (long)
             * OrderDictionaryEnum.Order_Status.Stock_Done.getKey(), result);
             * orderOperateStatementService.insertByList(settList, operator, "结转", (long)
             * OrderDictionaryEnum.Order_Status.Settle_Done.getKey(), (long)
             * OrderDictionaryEnum.OrderOperateType.Settle.getKey());
             * orderOperateStatementService.insertByList(settList, operator, "您的订单中的商品已经出库", (long)
             * OrderDictionaryEnum.Order_Status.Stock_Done.getKey(), (long)
             * OrderDictionaryEnum.OrderOperateType.Settle.getKey()); }
             */
        } catch (Exception e) {
            log.error("订单结转失败！" + e.getMessage());
            throw new ServiceException(0, "订单结转任务发生异常", e);
        }
    }

    /**
     * 
     * @param settList 已结转订单列表
     * @param unSettList 未结转订单列表
     * @param settUnOutList 已结转未出库订单列表
     * @param path 结转导出excel文件路径
     * @param operator 操作人
     */
    public void doCarryAndSome(List<OrderMain> settList, List<OrderMain> unSettList,
            List<OrderMain> settUnOutList, String path, String operator) {
        try {
            OrderCarry orderCarry = new OrderCarry();
            orderCarry.setExcelAddress(path);
            orderCarry.setCreateDate(new Date());
            orderCarry.setOrderSum((long) settList.size());// 结转的订单数
            orderCarry.setNoOrderSum((long) unSettList.size());// 未结转的订单数
            orderCarry.setOperator(operator);
            Long result = orderCarryDAO.insert(orderCarry);// 保存结转单实体
            if (!settUnOutList.isEmpty()) {
                // 已结转未出库
                updateOrderStatus(settUnOutList,
                        (long) OrderDictionaryEnum.Order_Status.Settle_Not_Stock.getKey(), result);
                orderOperateStatementService.insertByList(settUnOutList, operator, "您的订单中的商品暂未出库",
                        (long) OrderDictionaryEnum.Order_Status.Settle_Not_Stock.getKey(),
                        (long) OrderDictionaryEnum.OrderOperateType.Settle.getKey());
            }
            if (!settList.isEmpty()) {
                // 已出库
                updateOrderStatus(settList,
                        (long) OrderDictionaryEnum.Order_Status.Stock_Done.getKey(), result);
                orderOperateStatementService.insertByList(settList, operator, "结转",
                        (long) OrderDictionaryEnum.Order_Status.Settle_Done.getKey(),
                        (long) OrderDictionaryEnum.OrderOperateType.Settle.getKey());
                orderOperateStatementService.insertByList(settList, operator, "您的订单中的商品已经出库",
                        (long) OrderDictionaryEnum.Order_Status.Stock_Done.getKey(),
                        (long) OrderDictionaryEnum.OrderOperateType.Settle.getKey());
            }
        } catch (Exception e) {
            log.error("订单结转出错，方法doCarryAndSome：" + e.getMessage());
        }
    }



    /**
     * 康美电商入驻结转数据导出 根据出库单,配送单,生成excel文件,并保存到对应的目录.
     * 
     * @param list
     * @param ExecutedMap
     * @param disreputeMap
     * @return
     * @throws IOException
     */
    private String createExcelFileForDs(String fix, List<SKUEntity> list,
            List<OrderMain> orderExList, Map<String, List<OrderItem>> ExecutedMap)
            throws IOException, Exception {

        int rowNumEx = 0;// 数据行下标
        int colsNumStart = 0;// 合并开始行
        HSSFWorkbook wb = new HSSFWorkbook();// excel
        // 地址样式 水平居中 垂直居中 默认字体大小 自动换行
        HSSFCellStyle cellStyleAddr = wb.createCellStyle();
        cellStyleAddr.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleAddr.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyleAddr.setWrapText(true);

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

        // 小标题样式 水平居中 垂直居中 12px出题黑楷体文字
        HSSFCellStyle cellStyleItem = wb.createCellStyle();
        cellStyleItem.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleItem.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        Font fontItem = wb.createFont();
        fontItem.setFontHeightInPoints((short) 12);
        fontItem.setFontName("楷体");
        fontItem.setColor(HSSFColor.BLACK.index);
        cellStyleItem.setFont(fontItem);

        HSSFRow row = null;
        // 发货订单汇总表sheet
        rowNumEx = 0;
        HSSFSheet deliver = wb.createSheet("发货汇总表");
        deliver.setColumnWidth(0, 4500);
        deliver.setColumnWidth(1, 4500);
        deliver.setColumnWidth(2, 4500);
        deliver.setColumnWidth(3, 4500);
        deliver.setColumnWidth(4, 4500);
        deliver.setColumnWidth(5, 4500);
        deliver.setColumnWidth(6, 4500);
        deliver.setColumnWidth(7, 4500);
        deliver.setColumnWidth(8, 4500);
        CreateExcelUtil excel = new CreateExcelUtil(wb, deliver);

        Calendar cal = Calendar.getInstance();
        String dateStr = (cal.get(Calendar.MONTH) + 1) + "月" + cal.get(Calendar.DAY_OF_MONTH) + "日";

        // sheet标题
        row = deliver.createRow(rowNumEx);
        row.setHeight((short) 1400);
        rowNumEx++;
        excel.cteateCell(wb, row, 0, cellStyle, "康美中药城营销部订单-深圳配送中心发货订单汇总表" + dateStr);
        deliver.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));

        // 列名
        row = deliver.createRow(rowNumEx);
        rowNumEx++;
        excel.cteateCell(wb, row, 0, cellStyle, "品名");
        excel.cteateCell(wb, row, 1, cellStyle, "产地");
        excel.cteateCell(wb, row, 2, cellStyle, "所属仓库");
        excel.cteateCell(wb, row, 3, cellStyle, "规格");
        excel.cteateCell(wb, row, 4, cellStyle, "单位");
        excel.cteateCell(wb, row, 5, cellStyle, "数量");
        excel.cteateCell(wb, row, 6, cellStyle, "订单日期");
        excel.cteateCell(wb, row, 7, cellStyle, "需发货日期");
        excel.cteateCell(wb, row, 8, cellStyle, "备注");

        colsNumStart = rowNumEx;
        // 汇总数据
        if (list != null) {
            for (int i = 0, len = list.size(); i < len; i++) {
                SKUEntity skuEn = list.get(i);
                row = deliver.createRow(rowNumEx);
                rowNumEx++;
                excel.cteateCell(wb, row, 0, cellStyleCon, skuEn.getCommodityTitle());
                // 1产地
                excel.cteateCell(wb, row, 2, cellStyleCon, skuEn.getWarehouseName());
                excel.cteateCell(wb, row, 3, cellStyleCon, skuEn.getCommoditySkuDescription());
                // 4单位
                excel.cteateCell(wb, row, 5, cellStyleCon,
                        getStringValue(skuEn.getCommodityNumber()));
                if (i == 0) {
                    excel.cteateCell(wb, row, 6, cellStyleCon, sdf.format(cal.getTime()));
                }
            }
        }
        if (colsNumStart != rowNumEx) {
            deliver.addMergedRegion(new CellRangeAddress(colsNumStart, rowNumEx - 1, 6, 6));
            deliver.addMergedRegion(new CellRangeAddress(colsNumStart, rowNumEx - 1, 7, 7));
            deliver.addMergedRegion(new CellRangeAddress(colsNumStart, rowNumEx - 1, 8, 8));
        }

        // 发货明细表 sheet
        rowNumEx = 0;
        HSSFSheet deliverDetail = wb.createSheet("发货明细表");
        deliverDetail.setColumnWidth(0, 3500);
        deliverDetail.setColumnWidth(1, 3500);
        deliverDetail.setColumnWidth(2, 3500);
        deliverDetail.setColumnWidth(3, 4500);
        deliverDetail.setColumnWidth(4, 3500);
        deliverDetail.setColumnWidth(5, 3500);
        deliverDetail.setColumnWidth(6, 5500);
        deliverDetail.setColumnWidth(7, 3500);
        deliverDetail.setColumnWidth(8, 3500);
        deliverDetail.setColumnWidth(9, 3500);
        deliverDetail.setColumnWidth(10, 3500);
        deliverDetail.setColumnWidth(11, 3500);
        deliverDetail.setColumnWidth(12, 3500);
        deliverDetail.setColumnWidth(13, 3500);
        deliverDetail.setColumnWidth(14, 3500);
        deliverDetail.setColumnWidth(15, 3500);
        deliverDetail.setColumnWidth(16, 3500);
        deliverDetail.setColumnWidth(17, 3500);
        deliverDetail.setColumnWidth(18, 3500);
        deliverDetail.setColumnWidth(19, 3500);
        deliverDetail.setColumnWidth(20, 3500);
        deliverDetail.setColumnWidth(21, 3500);


        // 列名
        row = deliverDetail.createRow(rowNumEx);
        rowNumEx++;
        excel.cteateCell(wb, row, 0, cellStyle, "交易号");
        excel.cteateCell(wb, row, 1, cellStyle, "店铺名称");
        excel.cteateCell(wb, row, 2, cellStyle, "买家昵称");
        excel.cteateCell(wb, row, 3, cellStyle, "买家留言");
        excel.cteateCell(wb, row, 4, cellStyle, "卖家备注");
        excel.cteateCell(wb, row, 5, cellStyle, "收货人");
        excel.cteateCell(wb, row, 6, cellStyle, "收货地址");
        excel.cteateCell(wb, row, 7, cellStyle, "省");
        excel.cteateCell(wb, row, 8, cellStyle, "市");
        excel.cteateCell(wb, row, 9, cellStyle, "区");
        excel.cteateCell(wb, row, 10, cellStyle, "收货人手机");
        excel.cteateCell(wb, row, 11, cellStyle, "收货人电话");
        excel.cteateCell(wb, row, 12, cellStyle, "快递编号");
        excel.cteateCell(wb, row, 13, cellStyle, "快递成本");
        excel.cteateCell(wb, row, 14, cellStyle, "快递费用");
        excel.cteateCell(wb, row, 15, cellStyle, "是否货到付款");
        excel.cteateCell(wb, row, 16, cellStyle, "订单类型");
        excel.cteateCell(wb, row, 17, cellStyle, "SKU");
        excel.cteateCell(wb, row, 18, cellStyle, "销售单价");
        excel.cteateCell(wb, row, 19, cellStyle, "代理价");
        excel.cteateCell(wb, row, 20, cellStyle, "数量");
        excel.cteateCell(wb, row, 21, cellStyle, "邮编");

        if (orderExList != null) {
            for (int i = 0, len = orderExList.size(); i < len; i++) {
                OrderMain orderMain = orderExList.get(i);
                List<OrderItem> orderItemList = new ArrayList<OrderItem>();
                if (ExecutedMap != null) {
                    orderItemList = ExecutedMap.get(orderMain.getOrderCode());
                }
                colsNumStart = rowNumEx;
                for (int j = 0, jlen = orderItemList.size(); j < jlen; j++) {
                    OrderItem orderItem = orderItemList.get(j);
                    row = deliverDetail.createRow(rowNumEx);
                    rowNumEx++;


                    excel.cteateCell(wb, row, 17, cellStyleCon, orderItem.getSkuBarCode());// sku条形码
                    excel.cteateCell(wb, row, 18, cellStyleCon,
                            getStringValue(orderItem.getCommodityUnitPrice()));// 销售单价
                    excel.cteateCell(wb, row, 19, cellStyleCon, "");// 代理价
                    excel.cteateCell(wb, row, 20, cellStyleCon,
                            getStringValue(orderItem.getCommodityNumber()));// 数量


                    // if (colsNumStart == rowNumEx - 1) {
                    excel.cteateCell(wb, row, 0, cellStyleCon, orderMain.getOrderCode());
                    if (orderItem.getSupplier().equals("222")) {
                        excel.cteateCell(wb, row, 1, cellStyleCon, "康美1818健康商城");
                    } else {
                        excel.cteateCell(wb, row, 1, cellStyleCon, orderItem.getSupplierName());
                    }
                    excel.cteateCell(wb, row, 2, cellStyleCon, orderMain.getCustomerAccount());
                    excel.cteateCell(wb, row, 3, cellStyleCon, "");
                    excel.cteateCell(wb, row, 4, cellStyleCon, "康美中药城");
                    excel.cteateCell(wb, row, 5, cellStyleCon, orderMain.getConsigneeName());// 收货人
                    excel.cteateCell(wb, row, 6, cellStyleCon, orderMain.getConsigneeAddr());// 收货人地址
                    excel.cteateCell(wb, row, 7, cellStyleCon, orderMain.getProvince());// 省
                    excel.cteateCell(wb, row, 8, cellStyleCon, orderMain.getCity());// 市
                    excel.cteateCell(wb, row, 9, cellStyleCon, orderMain.getArea());// 区
                    excel.cteateCell(wb, row, 10, cellStyleCon, orderMain.getConsigneeMobile());// 收货人手机
                    excel.cteateCell(wb, row, 11, cellStyleCon, orderMain.getConsigneeTel());// 收货人电话
                    excel.cteateCell(wb, row, 12, cellStyleCon, "ZTO");
                    excel.cteateCell(wb, row, 13, cellStyleCon, "");
                    excel.cteateCell(wb, row, 14, cellStyleCon, "");
                    excel.cteateCell(wb, row, 15, cellStyleCon, "");
                    excel.cteateCell(wb, row, 16, cellStyleCon, "销售订单");
                    excel.cteateCell(wb, row, 21, cellStyleCon, "");

                    // }
                }
            }
        }
        // 发货订单发票信息 sheet
        rowNumEx = 0;
        HSSFSheet invoiceInfoSheet = wb.createSheet("订单发票信息");
        invoiceInfoSheet.setColumnWidth(0, 3500);
        invoiceInfoSheet.setColumnWidth(1, 3500);
        invoiceInfoSheet.setColumnWidth(2, 4500);
        invoiceInfoSheet.setColumnWidth(3, 3500);
        invoiceInfoSheet.setColumnWidth(4, 3500);
        invoiceInfoSheet.setColumnWidth(5, 3500);
        invoiceInfoSheet.setColumnWidth(6, 5500);
        // 列名
        row = invoiceInfoSheet.createRow(rowNumEx);
        rowNumEx++;
        excel.cteateCell(wb, row, 0, cellStyle, "主订单号");
        excel.cteateCell(wb, row, 1, cellStyle, "用户名");
        excel.cteateCell(wb, row, 2, cellStyle, "发票抬头");
        excel.cteateCell(wb, row, 3, cellStyle, "开票金额");
        excel.cteateCell(wb, row, 4, cellStyle, "收货人");
        excel.cteateCell(wb, row, 5, cellStyle, "收货人电话");
        excel.cteateCell(wb, row, 6, cellStyle, "收货地址");

        if (orderExList != null) {
            for (int i = 0, len = orderExList.size(); i < len; i++) {
                OrderMain orderMain = orderExList.get(i);
                if (null != orderMain.getInvoiceInfoType()
                        || null != orderMain.getInvoiceInfoTitle()
                        || null != orderMain.getInvoiceInfoContent()) { // 买家要求开具发票才导出
                    row = invoiceInfoSheet.createRow(rowNumEx);
                    rowNumEx++;
                    excel.cteateCell(wb, row, 0, cellStyleCon, orderMain.getOrderCode());
                    excel.cteateCell(wb, row, 1, cellStyleCon, orderMain.getCustomerAccount());
                    excel.cteateCell(wb, row, 2, cellStyleCon, orderMain.getInvoiceInfoTitle());
                    excel.cteateCell(wb, row, 3, cellStyleCon,
                            getStringValue(orderMain.getInvoiceMoney()));
                    excel.cteateCell(wb, row, 4, cellStyleCon, orderMain.getConsigneeName());
                    excel.cteateCell(wb, row, 5, cellStyleCon, orderMain.getConsigneeMobile());
                    excel.cteateCell(wb, row, 6, cellStyleCon, orderMain.getConsigneeAddr());
                }
            }
        }


        List<String> path = getExcelName(fix);
        FileOutputStream fileOut = new FileOutputStream(path.get(0));
        wb.write(fileOut);
        fileOut.close();
        log.info("生成excel文件完成！");
        return getVisitPath(path.get(1));
    }



    /**
     * 除康美电商外的其他入驻商家结转数据导出 根据出库单,配送单,生成excel文件,并保存到对应的目录.
     * 
     * @param list
     * @param ExecutedMap
     * @param fix
     * @return
     * @throws IOException
     */
    private String createExcelFileForSd(String fix, List<SKUEntity> list,
            List<OrderMain> orderExList, Map<String, List<OrderItem>> ExecutedMap)
            throws IOException, Exception {

        int rowNumEx = 0;// 数据行下标
        int colsNumStart = 0;// 合并开始行
        HSSFWorkbook wb = new HSSFWorkbook();// excel
        // 地址样式 水平居中 垂直居中 默认字体大小 自动换行
        HSSFCellStyle cellStyleAddr = wb.createCellStyle();
        cellStyleAddr.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleAddr.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyleAddr.setWrapText(true);

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

        // 小标题样式 水平居中 垂直居中 12px出题黑楷体文字
        HSSFCellStyle cellStyleItem = wb.createCellStyle();
        cellStyleItem.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleItem.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        Font fontItem = wb.createFont();
        fontItem.setFontHeightInPoints((short) 12);
        fontItem.setFontName("楷体");
        fontItem.setColor(HSSFColor.BLACK.index);
        cellStyleItem.setFont(fontItem);

        HSSFRow row = null;
        // 发货订单汇总表sheet
        rowNumEx = 0;
        HSSFSheet deliver = wb.createSheet("发货汇总表");
        deliver.setColumnWidth(0, 4500);
        deliver.setColumnWidth(1, 4500);
        deliver.setColumnWidth(2, 4500);
        deliver.setColumnWidth(3, 4500);
        deliver.setColumnWidth(4, 4500);
        deliver.setColumnWidth(5, 4500);
        deliver.setColumnWidth(6, 4500);
        deliver.setColumnWidth(7, 4500);
        deliver.setColumnWidth(8, 4500);
        CreateExcelUtil excel = new CreateExcelUtil(wb, deliver);

        Calendar cal = Calendar.getInstance();
        String dateStr = (cal.get(Calendar.MONTH) + 1) + "月" + cal.get(Calendar.DAY_OF_MONTH) + "日";

        // sheet标题
        row = deliver.createRow(rowNumEx);
        row.setHeight((short) 1400);
        rowNumEx++;
        excel.cteateCell(wb, row, 0, cellStyle, "康美中药城营销部订单-深圳配送中心发货订单汇总表" + dateStr);
        deliver.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));

        // 列名
        row = deliver.createRow(rowNumEx);
        rowNumEx++;
        excel.cteateCell(wb, row, 0, cellStyle, "品名");
        excel.cteateCell(wb, row, 1, cellStyle, "产地");
        excel.cteateCell(wb, row, 2, cellStyle, "所属仓库");
        excel.cteateCell(wb, row, 3, cellStyle, "规格");
        excel.cteateCell(wb, row, 4, cellStyle, "单位");
        excel.cteateCell(wb, row, 5, cellStyle, "数量");
        excel.cteateCell(wb, row, 6, cellStyle, "订单日期");
        excel.cteateCell(wb, row, 7, cellStyle, "需发货日期");
        excel.cteateCell(wb, row, 8, cellStyle, "备注");

        colsNumStart = rowNumEx;
        // 汇总数据
        if (list != null) {
            for (int i = 0, len = list.size(); i < len; i++) {
                SKUEntity skuEn = list.get(i);
                row = deliver.createRow(rowNumEx);
                rowNumEx++;
                excel.cteateCell(wb, row, 0, cellStyleCon, skuEn.getCommodityTitle());
                // 1产地
                excel.cteateCell(wb, row, 2, cellStyleCon, skuEn.getWarehouseName());
                excel.cteateCell(wb, row, 3, cellStyleCon, skuEn.getCommoditySkuDescription());
                // 4单位
                excel.cteateCell(wb, row, 5, cellStyleCon,
                        getStringValue(skuEn.getCommodityNumber()));
                if (i == 0) {
                    excel.cteateCell(wb, row, 6, cellStyleCon, sdf.format(cal.getTime()));
                }
            }
        }
        if (colsNumStart != rowNumEx) {
            deliver.addMergedRegion(new CellRangeAddress(colsNumStart, rowNumEx - 1, 6, 6));
            deliver.addMergedRegion(new CellRangeAddress(colsNumStart, rowNumEx - 1, 7, 7));
            deliver.addMergedRegion(new CellRangeAddress(colsNumStart, rowNumEx - 1, 8, 8));
        }

        // 发货明细表 sheet
        rowNumEx = 0;
        HSSFSheet deliverDetail = wb.createSheet("发货明细表");
        deliverDetail.setColumnWidth(0, 3500);
        deliverDetail.setColumnWidth(1, 3500);
        deliverDetail.setColumnWidth(2, 3500);
        deliverDetail.setColumnWidth(3, 4500);
        deliverDetail.setColumnWidth(4, 3500);
        deliverDetail.setColumnWidth(5, 3500);
        deliverDetail.setColumnWidth(6, 3500);
        deliverDetail.setColumnWidth(7, 3500);
        deliverDetail.setColumnWidth(8, 3500);
        deliverDetail.setColumnWidth(9, 3500);
        deliverDetail.setColumnWidth(10, 3500);
        deliverDetail.setColumnWidth(11, 3500);
        deliverDetail.setColumnWidth(12, 3500);
        deliverDetail.setColumnWidth(13, 3500);
        deliverDetail.setColumnWidth(14, 3500);
        deliverDetail.setColumnWidth(15, 3500);
        deliverDetail.setColumnWidth(16, 3500);
        deliverDetail.setColumnWidth(17, 3500);
        deliverDetail.setColumnWidth(18, 5500);

        // 列名
        row = deliverDetail.createRow(rowNumEx);
        rowNumEx++;
        excel.cteateCell(wb, row, 0, cellStyle, "销售平台");
        excel.cteateCell(wb, row, 1, cellStyle, "所属仓库");
        excel.cteateCell(wb, row, 2, cellStyle, "SKU");
        excel.cteateCell(wb, row, 3, cellStyle, "订单号");
        excel.cteateCell(wb, row, 4, cellStyle, "产品归属");
        excel.cteateCell(wb, row, 5, cellStyle, "品牌");
        excel.cteateCell(wb, row, 6, cellStyle, "品名");
        excel.cteateCell(wb, row, 7, cellStyle, "产地");
        excel.cteateCell(wb, row, 8, cellStyle, "规格");
        excel.cteateCell(wb, row, 9, cellStyle, "单位");
        excel.cteateCell(wb, row, 10, cellStyle, "数量");
        excel.cteateCell(wb, row, 11, cellStyle, "单价");
        excel.cteateCell(wb, row, 12, cellStyle, "总价");
        excel.cteateCell(wb, row, 13, cellStyle, "PV值");
        excel.cteateCell(wb, row, 14, cellStyle, "买家账号");
        excel.cteateCell(wb, row, 15, cellStyle, "买家备注");
        excel.cteateCell(wb, row, 16, cellStyle, "客服备注");
        excel.cteateCell(wb, row, 17, cellStyle, "收件人");
        excel.cteateCell(wb, row, 18, cellStyle, "电话");
        excel.cteateCell(wb, row, 19, cellStyle, "地址");

        if (orderExList != null) {
            for (int i = 0, len = orderExList.size(); i < len; i++) {
                OrderMain orderMain = orderExList.get(i);
                List<OrderItem> orderItemList = new ArrayList<OrderItem>();
                if (ExecutedMap != null) {
                    orderItemList = ExecutedMap.get(orderMain.getOrderCode());
                }
                colsNumStart = rowNumEx;
                for (int j = 0, jlen = orderItemList.size(); j < jlen; j++) {
                    OrderItem orderItem = orderItemList.get(j);
                    row = deliverDetail.createRow(rowNumEx);
                    rowNumEx++;
                    excel.cteateCell(wb, row, 2, cellStyleCon, orderItem.getCommoditySku());
                    if (null != orderItem.getSupplierType() && (SuppliersType.EMTER.getStatus()
                            .longValue() == orderItem.getSupplierType()
                            || SuppliersType.SUPPORT.getStatus().longValue() == orderItem
                                    .getSupplierType())) {
                        if ("康美药业OTC".equals(orderItem.getSupplierName())) {
                            excel.cteateCell(wb, row, 4, cellStyleCon, "OTC产品");
                        } else if ("广东康美之恋大药房连锁有限公司".equals(orderItem.getSupplierName())
                                || "康美按方抓药".equals(orderItem.getSupplierName())) {
                            excel.cteateCell(wb, row, 4, cellStyleCon, "OTC产品");
                        } else if ("康美人生".equals(orderItem.getSupplierName())) {
                            excel.cteateCell(wb, row, 4, cellStyleCon, "康美人生");
                        } else {
                            excel.cteateCell(wb, row, 4, cellStyleCon, "时代自营");
                        }
                    } else {
                        excel.cteateCell(wb, row, 4, cellStyleCon, orderItem.getSupplierTypeName());
                    }
                    excel.cteateCell(wb, row, 5, cellStyleCon, orderItem.getCommodityBrand());
                    excel.cteateCell(wb, row, 6, cellStyleCon, orderItem.getCommodityTitle());
                    // 7产地
                    excel.cteateCell(wb, row, 8, cellStyleCon,
                            orderItem.getCommoditySkuDescription());
                    // 9单位
                    excel.cteateCell(wb, row, 10, cellStyleCon,
                            getStringValue(orderItem.getCommodityNumber()));
                    excel.cteateCell(wb, row, 11, cellStyleCon,
                            getStringValue(orderItem.getCommodityUnitIncoming()));
                    excel.cteateCell(wb, row, 12, cellStyleCon, orderItem.getCommodityUnitIncoming()
                            .multiply(new BigDecimal(orderItem.getCommodityNumber())).toString());
                    if (null != orderItem.getCommodityPv()) {
                        excel.cteateCell(wb, row, 13, cellStyleCon, String.valueOf(
                                orderItem.getCommodityPv() * orderItem.getCommodityNumber()));
                    }
                    if (colsNumStart == rowNumEx - 1) {
                        excel.cteateCell(wb, row, 0, cellStyleCon,
                                OrderChannel.getValueByKey(orderMain.getOrderChannel()));
                        excel.cteateCell(wb, row, 1, cellStyleCon, orderItem.getWarehouseName());
                        excel.cteateCell(wb, row, 3, cellStyleCon, orderMain.getOrderCode());
                        excel.cteateCell(wb, row, 14, cellStyleCon, orderMain.getCustomerAccount());
                        excel.cteateCell(wb, row, 15, cellStyleCon,
                                orderMain.getOrderDescription());
                        excel.cteateCell(wb, row, 16, cellStyleCon,
                                orderMain.getOrderOperationRemark());
                        excel.cteateCell(wb, row, 17, cellStyleCon, orderMain.getConsigneeName());
                        excel.cteateCell(wb, row, 18, cellStyleCon, orderMain.getConsigneeMobile());
                        excel.cteateCell(wb, row, 19, cellStyleCon, orderMain.getConsigneeAddr());
                    }
                }
                if (colsNumStart != rowNumEx && colsNumStart != rowNumEx - 1) {
                    deliverDetail.addMergedRegion(
                            new CellRangeAddress(colsNumStart, rowNumEx - 1, 0, 0));
                    deliverDetail.addMergedRegion(
                            new CellRangeAddress(colsNumStart, rowNumEx - 1, 1, 1));
                    deliverDetail.addMergedRegion(
                            new CellRangeAddress(colsNumStart, rowNumEx - 1, 3, 3));
                    deliverDetail.addMergedRegion(
                            new CellRangeAddress(colsNumStart, rowNumEx - 1, 14, 14));
                    deliverDetail.addMergedRegion(
                            new CellRangeAddress(colsNumStart, rowNumEx - 1, 15, 15));
                    deliverDetail.addMergedRegion(
                            new CellRangeAddress(colsNumStart, rowNumEx - 1, 16, 16));
                    deliverDetail.addMergedRegion(
                            new CellRangeAddress(colsNumStart, rowNumEx - 1, 17, 17));
                    deliverDetail.addMergedRegion(
                            new CellRangeAddress(colsNumStart, rowNumEx - 1, 18, 18));
                    deliverDetail.addMergedRegion(
                            new CellRangeAddress(colsNumStart, rowNumEx - 1, 19, 19));
                }
            }
        }
        // 发货订单发票信息 sheet
        rowNumEx = 0;
        HSSFSheet invoiceInfoSheet = wb.createSheet("订单发票信息");
        invoiceInfoSheet.setColumnWidth(0, 3500);
        invoiceInfoSheet.setColumnWidth(1, 3500);
        invoiceInfoSheet.setColumnWidth(2, 4500);
        invoiceInfoSheet.setColumnWidth(3, 3500);
        invoiceInfoSheet.setColumnWidth(4, 3500);
        invoiceInfoSheet.setColumnWidth(5, 3500);
        invoiceInfoSheet.setColumnWidth(6, 5500);
        // 列名
        row = invoiceInfoSheet.createRow(rowNumEx);
        rowNumEx++;
        excel.cteateCell(wb, row, 0, cellStyle, "主订单号");
        excel.cteateCell(wb, row, 1, cellStyle, "用户名");
        excel.cteateCell(wb, row, 2, cellStyle, "发票抬头");
        excel.cteateCell(wb, row, 3, cellStyle, "开票金额");
        excel.cteateCell(wb, row, 4, cellStyle, "收货人");
        excel.cteateCell(wb, row, 5, cellStyle, "收货人电话");
        excel.cteateCell(wb, row, 6, cellStyle, "收货地址");

        if (orderExList != null) {
            for (int i = 0, len = orderExList.size(); i < len; i++) {
                OrderMain orderMain = orderExList.get(i);
                if (null != orderMain.getInvoiceInfoType()
                        || null != orderMain.getInvoiceInfoTitle()
                        || null != orderMain.getInvoiceInfoContent()) { // 买家要求开具发票才导出
                    row = invoiceInfoSheet.createRow(rowNumEx);
                    rowNumEx++;
                    excel.cteateCell(wb, row, 0, cellStyleCon, orderMain.getOrderCode());
                    excel.cteateCell(wb, row, 1, cellStyleCon, orderMain.getCustomerAccount());
                    excel.cteateCell(wb, row, 2, cellStyleCon, orderMain.getInvoiceInfoTitle());
                    excel.cteateCell(wb, row, 3, cellStyleCon,
                            getStringValue(orderMain.getInvoiceMoney()));
                    excel.cteateCell(wb, row, 4, cellStyleCon, orderMain.getConsigneeName());
                    excel.cteateCell(wb, row, 5, cellStyleCon, orderMain.getConsigneeMobile());
                    excel.cteateCell(wb, row, 6, cellStyleCon, orderMain.getConsigneeAddr());
                }
            }
        }


        List<String> path = getExcelName(fix);
        FileOutputStream fileOut = new FileOutputStream(path.get(0));
        wb.write(fileOut);
        fileOut.close();
        log.info("生成excel文件完成！");
        return getVisitPath(path.get(1));
    }
}
