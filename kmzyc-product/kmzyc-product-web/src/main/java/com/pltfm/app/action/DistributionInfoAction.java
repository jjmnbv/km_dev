package com.pltfm.app.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kmzyc.commons.page.Page;
import com.kmzyc.order.remote.OrderQryRemoteService;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.enums.DistributionInfostatus;
import com.pltfm.app.enums.ProductType;
import com.pltfm.app.service.DistributionDetailInfoService;
import com.pltfm.app.service.DistributionInfoService;
import com.pltfm.app.service.OrderRemoteService;
import com.pltfm.app.service.StockOutService;
import com.pltfm.app.util.OrderDictionaryEnum.Order_Status;
import com.pltfm.app.util.PurchaseUtils;
import com.pltfm.app.vobject.DistributionDetailInfo;
import com.pltfm.app.vobject.DistributionInfo;
import com.pltfm.app.vobject.LogisticAndDistributionInfoVO;
import com.pltfm.app.vobject.StockOut;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.write.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 配送单业务处理action
 * 
 * @author luoyi
 * @createDate 2013/08/15
 * 
 */
@Controller("distributionInfoAction")
@Scope(value = "prototype")
public class DistributionInfoAction extends BaseAction {

    private static final long serialVersionUID = -3428404109955299394L;

    @Resource
    private DistributionInfoService distributionInfoService;
    @Resource
    private DistributionDetailInfoService distributionDetailInfoService;
    @Resource
    private OrderQryRemoteService orderQryRemoteService;
    @Resource
    private OrderRemoteService orderRemoteService;
    @Resource
    private StockOutService stockOutService;

    private List<DistributionInfo> distributionInfoList;// 配送单列表
    private Page page = new Page();// 分页
    private DistributionInfo distributionInfo = new DistributionInfo();
    private Date endDate;// 结束日期
    private String rtnMessage; // 显示返回的信息
    private Long distributionId;// 配送单ID
    private File importDisFile;// 配送信息导入文件
    private List<DistributionDetailInfo> distributionDetailInfoList;
    // 跳转的页数
    private int pageNum = 0;
    // 查询条件
    private DistributionInfo queryDistribution = new DistributionInfo();
    // 输出流
    private InputStream excelStream;

    /**
     * 配送单列表: 配送单列表
     * 
     * @return
     * @throws Exception
     */
    public String toDistributionInfoList() throws Exception {
        if (pageNum == 1) {
            page.setPageNo(1);
        }
        distributionInfoList =
                distributionInfoService.findDistributionInfoList(page, queryDistribution, endDate);

        setLogisticsCompanyMap();
        // 审核状态和仓库信息
        setDistributionInfoMap();
        setWarehouseMap();// 所有仓库信息
        return SUCCESS;
    }

    /**
     * 配送单审核:到配送单列表页面
     * 
     * @return
     * @throws Exception
     */
    public String distriListForCheck() throws Exception {
        if (pageNum == 1) {
            page.setPageNo(1);
        }
        queryDistribution.setIsDeliveries(DistributionInfostatus.UNDELIVERIES.getStatus());
        distributionInfoList =
                distributionInfoService.findDistributionInfoList(page, queryDistribution, endDate);
        // 审核状态和仓库信息
        setDistributionInfoMap();
        setWarehouseMap();
        return SUCCESS;
    }

    /**
     * 配送单列表:到添加配送单页面
     * 
     * @return
     */
    public String toAddDistributionInfo() {
        setWarehouseForStatusMap();// 可用仓库信息
        setLogisticsCompanyMap();// 物流信息
        return SUCCESS;
    }

    /**
     * 进入订单配送信息导入页面
     * 
     * @return
     */
    public String gotoImportDisInfoPage() {

        return SUCCESS;
    }

    /**
     * 导入订单配送信息
     * 
     */
    public void doImportDisInfo() {
        // 返回结果
        Map<String, Object> resultMap = Maps.newHashMap();
        // 用户名称
        Workbook rwb = null;
        InputStream in = null;
        // 获取Excel文件对象
        try {
            // 提示信息
            StringBuilder msg = new StringBuilder();
            in = new FileInputStream(importDisFile);
            rwb = Workbook.getWorkbook(in);
            // 获取文件的指定工作表 默认的第一个
            Sheet sheet = rwb.getSheet(0);
            // 判断记录总数（不算表头）
            if (sheet.getRows() - 1 > 5000) {
                // 提示信息
                msg.append("excel记录数不能超出5000条！\n");
                resultMap.put("msg", msg.toString());
                // 结果返回
                writeJson(resultMap);
                return;
            }
            // 判断记录列数
            if (sheet.getColumns() < 4) {
                // 提示信息
                msg.append("excel数据格式有误，请根据模板填写！\n");
                resultMap.put("msg", msg.toString());
                // 结果返回
                writeJson(resultMap);
                return;
            }

            // 异常数据总数
            int faildCount = 0;
            // 数据集合
            List<Map<String, String>> disLists = Lists.newArrayList();
            // 订单集合
            Map<String, OrderMain> orderMap = Maps.newHashMap();
            // 行数(表头的目录不需要，从1开始)
            for (int i = 1; i < sheet.getRows(); i++) {
                // 判断订单编号是否为空
                String orderNo = sheet.getCell(0, i).getContents();
                if (StringUtils.isBlank(orderNo)) {
                    msg.append("第").append(i + 1).append("行：订单号为空！\n");
                    faildCount++;
                    continue;
                }

                // 判断物流公司编码是否为空
                String logisticsCompany = sheet.getCell(1, i).getContents();
                if (StringUtils.isBlank(logisticsCompany)) {
                    msg.append("第").append(i + 1).append("行：物流公司编码为空！\n");
                    faildCount++;
                    continue;
                }

                // 判断物流单号是否为空
                String logisticsNo = sheet.getCell(3, i).getContents();
                if (StringUtils.isBlank(logisticsNo)) {
                    msg.append("第").append(i + 1).append("行：物流单号为空！\n");
                    faildCount++;
                    continue;
                }

                // 查询订单详情
                OrderMain orderMain = orderQryRemoteService.getOrderByCode(orderNo);
                if (orderMain == null) {
                    msg.append("第").append(i + 1).append("行：订单号不存在！\n");
                    faildCount++;
                    continue;
                }
                if (orderMain.getOrderStatus() != Order_Status.Stock_Done.getKey()) {
                    msg.append("第").append(i + 1).append("行：订单不为待配送状态！\n");
                    faildCount++;
                    continue;
                }
                if (StringUtils.isNotBlank(orderMain.getCommerceId())) {
                    msg.append("第").append(i + 1).append("行：订单不属于自营或代销！\n");
                    faildCount++;
                    continue;
                }

                // 物流信息
                if (!logisticsMap.containsKey(logisticsCompany)) {
                    msg.append("第").append(i + 1).append("行：物流公司不存在！\n");
                    faildCount++;
                    continue;
                }

                // 校验通过信息记录
                Map<String, String> disMap = Maps.newHashMap();
                disMap.put("orderNo", orderNo);
                disMap.put("logisticsCompany", logisticsCompany);
                disMap.put("logisticsNo", logisticsNo);

                // 集合组装
                disLists.add(disMap);
                // 订单结合组装
                orderMap.put(orderMain.getOrderCode(), orderMain);
            }

            // 出现异常数据，则终止导入操作
            if (faildCount > 0) {
                // 提示信息
                resultMap.put("msg", msg.toString());
                // 结果返回
                writeJson(resultMap);
                return;
            }

            // 执行配送信息修改
            for (Map<String, String> dis : disLists) {
                // 1.更新配送单信息
                DistributionInfo disInfo =
                        distributionInfoService.findDistributionByOrderNo(dis.get("orderNo"));
                disInfo.setLogisticsNo(dis.get("logisticsNo"));
                disInfo.setLogisticsCompany(dis.get("logisticsCompany"));
                disInfo.setIsDeliveries(DistributionInfostatus.ISDELIVERIES.getStatus());
                disInfo.setLogisticsDate(new Date());
                disInfo.setCreateDate(new Date());
                disInfo.setCreateUser(super.getLoginUserId());
                disInfo.setCreateUserName(super.getLoginUserName());
                // 执行修改
                distributionInfoService.updateDistributionInfo(disInfo);

                // 2.更新订单物流信息及订单状态
                LogisticAndDistributionInfoVO logisticAndDistributionInfoVO =
                        new LogisticAndDistributionInfoVO();
                logisticAndDistributionInfoVO.setOrderCode(dis.get("orderNo"));
                logisticAndDistributionInfoVO.setLogisticsCode(dis.get("logisticsCompany"));
                logisticAndDistributionInfoVO.setLogisticsName(
                        logisticsMap.get(dis.get("logisticsCompany")));
                logisticAndDistributionInfoVO.setLogisticsOrderNo(dis.get("logisticsNo"));
                logisticAndDistributionInfoVO.setDistributionId(disInfo.getDistributionId());
                logisticAndDistributionInfoVO.setIncludeMedicineFlag(false);

                Map<Long, List<DistributionDetailInfo>> distributionInfoMap = Maps.newHashMap();

                // 配送单明细
                List<DistributionDetailInfo> disInfoList = distributionDetailInfoService
                        .findDistributionDetailInfoList(disInfo.getDistributionId());
                distributionInfoMap.put(disInfo.getDistributionId(), disInfoList);
                logisticAndDistributionInfoVO.setDistributionInfoMap(distributionInfoMap);

                StockOut stockOut = stockOutService.findStockOutByNo(disInfo.getBillNo());
                // 执行处理
                orderRemoteService.getLogisticNumber(logisticAndDistributionInfoVO,
                        stockOut.getType());
            }

            resultMap.put("msg", "全部导入成功，共" + (sheet.getRows() - 1) + "条数据！");
            // 结果返回
            writeJson(resultMap);
        } catch (Exception e) {
            logger.error(" import distribution exception!", e);
            resultMap.put("msg", "导入失败！");
            // 结果返回
            writeJson(resultMap);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("InputStream close exception!", e);
                }
            }
        }
    }

    /**
     * 订单配送信息导入模板下载
     * 
     * @return
     */
    public String downloadDistributionTemplate() {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        WritableWorkbook workbook;
        try {
            workbook = Workbook.createWorkbook(out);
            WritableSheet sheet = workbook.createSheet("配送单列表", 0);
            // 设置单元格样式，背景色，居中，边框
            WritableFont font = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD, false,
                    UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat format = new WritableCellFormat(font);
            format.setAlignment(Alignment.CENTRE);
            // 给sheet电子版中所有的列设置默认的列的宽度;
            sheet.getSettings().setDefaultColumnWidth(25);

            sheet.addCell(new Label(0, 0, "订单号（*）", format));
            sheet.addCell(new Label(1, 0, "物流公司编码（*）", format));
            sheet.addCell(new Label(2, 0, "物流公司名称", format));
            sheet.addCell(new Label(3, 0, "物流单号（*）", format));

            workbook.write();
            workbook.close();

            excelStream = new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "excel";
    }

    /**
     * 配送单列表:到修改配送单页面
     * 
     * @return
     */
    public String toEditDistributionInfo() {

        setWarehouseMap();// 可用仓库信息
        setLogisticsCompanyMap();
        boolean isDrug = false;// 是否是药品
        try {
            distributionInfo = distributionInfoService.findDistributionById(distributionId);// 配送单信息
            distributionDetailInfoList = null;// 配送单明细列表
            distributionDetailInfoList =
                    distributionDetailInfoService.findDistributionDetailInfoList(distributionId);
            for (DistributionDetailInfo distributionDetailInfo : distributionDetailInfoList) {
                if (distributionDetailInfo.getProduct() != null
                        && distributionDetailInfo.getProduct().getProductType() != null
                        && distributionDetailInfo.getProduct().getProductType()
                                .intValue() == ProductType.OTC.getKey().intValue()) {
                    isDrug = true;
                    break;
                }
            }
            getRequest().setAttribute("isDrug", isDrug);
        } catch (Exception e) {
            rtnMessage = "无此配送单信息";
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 配送单审核:ajax请求，完成配送单审核功能
     * 
     * @return
     * @throws Exception
     */
    public String checkedDistributionInfo() throws Exception {
        // 审核状态和仓库信息
        setDistributionInfoMap();
        setWarehouseMap();
        Long[] pIds =
                PurchaseUtils.strArrToIntArr(getRequest().getParameter("distributionIdArray"));
        distributionInfoList = new ArrayList<DistributionInfo>();
        for (Long pId : pIds) {
            DistributionInfo dInfo = new DistributionInfo();
            dInfo.setDistributionId(pId);
            dInfo.setIsDeliveries(DistributionInfostatus.ISDELIVERIES.getStatus());
            distributionInfoList.add(dInfo);
        }

        ResultMessage resultMessage = new ResultMessage();

        // 根据配送单的ID,进行审核 try { resultMessage =
        try {
            resultMessage =
                    distributionInfoService.updateDistributionInfoList(distributionInfoList);
            this.json = "{\"result\":" + resultMessage.getIsSuccess() + ",\"message\":\""
                    + resultMessage.getMessage() + "\"}";
        } catch (Exception e) {
            e.printStackTrace();
            resultMessage.setIsSuccess(false);
            resultMessage.setMessage("批量删除配送单失败!");
            this.json = "{\"result\":" + resultMessage.getIsSuccess() + ",\"message\":\""
                    + resultMessage.getMessage() + "\"}";
            super.strWriteJson(json);
        }
        super.strWriteJson(json);
        return null;
    }

    /**
     * ajax请求:批量删除配送单
     * 
     * @return
     * @throws SQLException
     */
    public String deleteDistributionInfo() throws SQLException {
        Long[] pIds =
                PurchaseUtils.strArrToIntArr(getRequest().getParameter("distributionIdArray"));
        distributionInfoList = new ArrayList<DistributionInfo>();
        for (long pId : pIds) {
            DistributionInfo dInfo = new DistributionInfo();
            dInfo.setDistributionId(pId);
            distributionInfoList.add(dInfo);
        }

        ResultMessage resultMessage = new ResultMessage();
        // 根据配送单的ID,进行审核
        try {
            resultMessage =
                    distributionInfoService.deleteDistributionInfoList(distributionInfoList);
            this.json = "{\"result\":" + resultMessage.getIsSuccess() + ",\"message\":\""
                    + resultMessage.getMessage() + "\"}";
        } catch (Exception e) {
            e.printStackTrace();
            resultMessage.setIsSuccess(false);
            resultMessage.setMessage("批量删除配送单失败!");
            this.json = "{\"result\":" + resultMessage.getIsSuccess() + ",\"message\":\""
                    + resultMessage.getMessage() + "\"}";
            super.strWriteJson(json);
        }
        super.strWriteJson(json);
        return null;
    }

    /*
     * 以上为set和get方法
     * 
     * @return
     */

    public List<DistributionInfo> getDistributionInfoList() {
        return distributionInfoList;
    }

    public void setDistributionInfoList(List<DistributionInfo> distributionInfoList) {
        this.distributionInfoList = distributionInfoList;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    public DistributionInfo getDistributionInfo() {
        return distributionInfo;
    }

    public void setDistributionInfo(DistributionInfo distributionInfo) {
        this.distributionInfo = distributionInfo;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getRtnMessage() {
        return rtnMessage;
    }

    public void setRtnMessage(String rtnMessage) {
        this.rtnMessage = rtnMessage;
    }

    public Long getDistributionId() {
        return distributionId;
    }

    public void setDistributionId(Long distributionId) {
        this.distributionId = distributionId;
    }

    public List<DistributionDetailInfo> getDistributionDetailInfoList() {
        return distributionDetailInfoList;
    }

    public void setDistributionDetailInfoList(
            List<DistributionDetailInfo> distributionDetailInfoList) {
        this.distributionDetailInfoList = distributionDetailInfoList;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public DistributionInfo getQueryDistribution() {
        return queryDistribution;
    }

    public void setQueryDistribution(DistributionInfo queryDistribution) {
        this.queryDistribution = queryDistribution;
    }

    public File getImportDisFile() {
        return importDisFile;
    }

    public void setImportDisFile(File importDisFile) {
        this.importDisFile = importDisFile;
    }

    public InputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(InputStream excelStream) {
        this.excelStream = excelStream;
    }

}
