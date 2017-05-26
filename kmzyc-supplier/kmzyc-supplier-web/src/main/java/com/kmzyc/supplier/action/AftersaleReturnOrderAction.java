package com.kmzyc.supplier.action;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.page.Pagination;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.order.remote.SysConstantsRemoteService;
import com.kmzyc.supplier.maps.OrderAlterProposeStatusMap;
import com.kmzyc.supplier.service.AftersaleReturnOrderService;
import com.kmzyc.supplier.service.OrderService;
import com.kmzyc.supplier.util.PurchaseUtils;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.entities.OrderAlter;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.util.OrderAlterDictionaryEnum;
import com.pltfm.app.vobject.AftersaleReturnOrder;
import com.pltfm.app.vobject.DistributionInfo;
import com.pltfm.app.vobject.LogisticAndDistributionInfoVO;
import com.pltfm.app.vobject.OrderAlterVo;
import com.pltfm.app.vobject.OrderMainVo;
import com.pltfm.app.vobject.StockOut;


@Controller("aftersaleReturnOrderAction")
@Scope(value = "prototype")
public class AftersaleReturnOrderAction extends SupplierBaseAction {

    private static Logger logger = LoggerFactory.getLogger(AftersaleReturnOrderAction.class);

    @Resource
    private AftersaleReturnOrderService aftersaleReturnOrderService;

    @Resource
    private OrderService orderService;

    @Resource
    private SysConstantsRemoteService sysConstantsRemoteService;

    @Resource(name = "logisticsMap")
    private Map<String,String> logisticsMap;

    AftersaleReturnOrder order = new AftersaleReturnOrder();

    // 处理结果
    private String handleResult;

    // 退货单主键
    private Long returnId;

    private boolean changeReturnOrder = false;

    // 处理状态
    private String orderStatus;

    // 条码
    private String inputBarCode;

    private String orderCode;// 订单编号

    private String alterCode;// 退换货单号

    private DistributionInfo distributionInfo;

    private StockOut stockOut;

    private OrderAlterVo selectOrderAlterVo = new OrderAlterVo();

    private String createBegin;// 申请时间

    private String createEnd;

    private String finishBegin;// 审核时间

    private String finishEnd;

    private OrderAlter orderAlter;

    private List listOrderAlterOperates;// 操作流水

    private List listOrderAlterPays;// 退换货支付流水

    private int viewType;// 展示类型1，跳转到审核页面

    private String operator;

    private Short type;

    private BigDecimal fareSubsidy; // 补偿运费

    private BigDecimal returnFare; // 返回运费

    private BigDecimal returnMoney;// 商品退款金额

    private BigDecimal preferentialAmount;

    private String comment; // 审核说明

    private String logisticsNo;// 物流订单号

    private String logisticsCompany;// 物流公司

    private String showPath;

    public static final String imgPath = "RETURNSHOP_PHOTO_PATH";

    public static final String productPath = "sysChannelToWebsiteMap";

    private List photoList;

    private String cmsPagePath = ConfigurationUtil.getString("CMS_PRODUCT_PATH");

    private OrderItem item;

    private OrderMainVo orderMain;// 订单基本信息

    private Boolean isSuit;

    private Boolean isAdditional;

    private int ckType;// 操作类型。为1就操作确认配送

    private static final Integer operatorType = 1;// 操作人类型1为供应商，0为康美健康

    // 是按订单编号查询还是按退换货编号查询
    private String queryTypeForOrderNo;

    private String queryTypeForOrderNoValue; // 查询类型下的具体值

    private String proposer;

    private String proposeStatus;

    private BigDecimal totalReturnFare; // 同一订单返还运费累计总额

    /**
     * 分页列表
     *
     * @return
     */
    public String showList() {
        try {
            Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(super.getPageSize()));
            getRequest().setAttribute("orderAlterProposeStatusMap", OrderAlterProposeStatusMap.getMap());
            Map<String, Object> condition = new HashMap();
            // 按订单编号信息种类来区分查询
            if (StringUtils.isNotEmpty(queryTypeForOrderNo) && StringUtils.isNotEmpty(queryTypeForOrderNoValue)) {
                condition.put(queryTypeForOrderNo, queryTypeForOrderNoValue.trim());
            }
            // 退换货编号
            if (StringUtils.isNotEmpty(selectOrderAlterVo.getOrderAlterCode())) {
                condition.put("alterCode", selectOrderAlterVo.getOrderAlterCode().trim());
            }
            // 订单编号
            if (StringUtils.isNotEmpty(selectOrderAlterVo.getOrderCode())) {
                condition.put("orderCode", selectOrderAlterVo.getOrderCode().trim());
            }
            // 申请人
            if (StringUtils.isNotEmpty(selectOrderAlterVo.getProposer())) {
                condition.put("proposer", selectOrderAlterVo.getProposer().trim());
            }
            // 审核人
            if (StringUtils.isNotEmpty(selectOrderAlterVo.getChecker())) {
                condition.put("checker", selectOrderAlterVo.getChecker().trim());
            }
            // 申请开始时间
            if (StringUtils.isNotEmpty(createBegin)) {
                condition.put("createBegin", createBegin);
            }
            // 申请结束时间
            if (StringUtils.isNotEmpty(createEnd)) {
                condition.put("createEnd", createEnd);
            }
            // 审核开始时间
            if (StringUtils.isNotEmpty(finishBegin)) {
                condition.put("finishBegin", finishBegin);
            }
            // 审核结束时间
            if (StringUtils.isNotEmpty(finishEnd)) {
                condition.put("finishEnd", finishEnd);
            }
            // 订单状态
            if (selectOrderAlterVo.getProposeStatus() != null) {
                condition.put("status", selectOrderAlterVo.getProposeStatus());
            }
            condition.put("supplierId", getSupplyId());
            condition.put("start", page.getStartindex());
            condition.put("end", page.getEndindex());
            pagintion = aftersaleReturnOrderService.searchPage(page, condition);
            getRequest().setAttribute("productCMSPath", ConfigurationUtil.getString("CMS_PRODUCT_PATH"));
            setOrderNoTypeMap();
        } catch (Exception e) {
            logger.error("分页列表出错，", e);
            return ERROR;
        }

        return SUCCESS;
    }

    /**
     * 处理单个退货单 (查看详情)
     *
     * @return
     */
    public String gotoEdit() {
        String view = null;
        try {
            if (viewType == 1) {
                view = "checkOder";
            } else {
                view = "detailOrder";
            }

            // 查询退换货订单主要信息
            orderAlter = aftersaleReturnOrderService.findByPrimaryKey(alterCode);
            // 操作流水
            listOrderAlterOperates = aftersaleReturnOrderService.listOrderAlterOperates(alterCode);
            // 支付流水已经废弃显示
            // listOrderAlterPays = aftersaleReturnOrderService.listOrderAlterPays(alterCode);
            // 查询退换货单产品信息
            order = aftersaleReturnOrderService.selectByOrderCode(alterCode);
            // isAdditional=aftersaleReturnOrderService.ckIsAdd(alterCode);是否补单
            if (order != null) {
                order = aftersaleReturnOrderService.findAfterSaleReturnOrderInfo(alterCode, order.getHandleResult(),
                        order.getReturnId());
            }

            // 20150120注释掉,大改版后尚未启用isSuit属性
            // isSuit = aftersaleReturnOrderService.isSuit(orderAlter.getOrderCode());// 获得活动商品

            // 查询退换货订单之前的原订单主要信息
            orderMain = orderService.findOrderByOrderCode(orderAlter.getOrderCode());

            // 获取退换货单对应的商品明细
            item = aftersaleReturnOrderService.getOrderById(orderAlter.getOrderItemId());

            // 各种配置路径的获取
            Object constan = sysConstantsRemoteService.getConstantsValue(imgPath);
            showPath = constan.toString();

            // 以前路径的来源于订单系统,20151016 mlq 改变为自己控制 begin
            // Object constan1 = sysConstantsRemoteService.getConstantsValue(productPath);
            // Map<String, String> map = (Map<String, String>) constan1;
            // cmsPagePath = map.get(orderMain.getOrderChannel()) + "/products/";
            // 20151016 end

            // 查询退换货添加的图片
            if (null != orderAlter.getPhotoBatchNo()) {
                photoList = aftersaleReturnOrderService.getPhotoList(orderAlter.getPhotoBatchNo());// 获得上传图片
            }

            // 物流信息map等于52才有map信息 返回原件以及确认配送的时候才有物流信息的map
            if (orderAlter.getProposeStatus() == 52 || orderAlter.getProposeStatus() == 53) {
                super.getRequest().setAttribute("logisticsCompanyMap", logisticsMap);
            }

            // 累计退换货运费总额
            totalReturnFare = aftersaleReturnOrderService.getOrderTotalReturnFare(orderAlter.getOrderCode());
        } catch (Exception e) {
            logger.error("获取退换货图片或产品链接出现异常" + e.getMessage(), e);
        }
        return view;
    }

    /**
     * 退换货审核
     *
     * @return
     */
    public String checkOrderAlter() {
        int info = 0;
        try {
            if (type == 1) {
                type = Short.valueOf(OrderAlterDictionaryEnum.Propose_Status.Pass.getKey() + "");// 通过
            } else {
                type = Short.valueOf(OrderAlterDictionaryEnum.Propose_Status.Veto.getKey() + "");// 驳回
            }

            fareSubsidy = fareSubsidy == null ? new BigDecimal("0.00") : fareSubsidy;
            returnMoney = returnMoney == null ? new BigDecimal("0.00") : returnMoney;
            returnFare = returnFare == null ? new BigDecimal("0.00") : returnFare;

            // 退款总额
            BigDecimal returnSum = fareSubsidy.add(returnMoney).add(returnFare).setScale(2, BigDecimal.ROUND_HALF_UP);
            int count = aftersaleReturnOrderService.checkOrderAlter(super.getLoginUserName(), alterCode, type,
                    fareSubsidy, returnMoney, returnFare, returnSum, preferentialAmount, comment);
            if (count > 0) {
                info = 1;
            }
            getResponse().getWriter().print(info);
        } catch (Exception e) {
            logger.error("退换货审核出现异常" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 确认退款或者返回原件
     *
     * @return
     */
    public String changeAlterStatus() {
        int info = 0;
        try {
            orderAlter = aftersaleReturnOrderService.findByPrimaryKey(alterCode);
            if (StringUtils.isNotBlank(logisticsNo) && StringUtils.isNotBlank(logisticsCompany)) {
                orderAlter.setLogisticsCode(logisticsCompany);// 快递公司编码
                orderAlter.setLogisticsOrderNo(logisticsNo);// 快递编号
                orderAlter.setOrderCode(orderCode);
                orderAlter.setLogisticsName(logisticsMap.get(logisticsCompany));// 快递公司名称
                Integer sta = OrderAlterDictionaryEnum.Propose_Status.BackShop.getKey();
                orderAlter.setProposeStatus(Short.valueOf(sta.toString()));// 已原件返回待签收
            } else {
                Integer sta = OrderAlterDictionaryEnum.Propose_Status.Backpay.getKey();
                orderAlter.setProposeStatus(Short.valueOf(sta.toString()));// 已退款待确认
            }
            int count = aftersaleReturnOrderService.changeAlterStatus(super.getLoginUserName(), orderAlter);
            if (count > 0) {
                info = 1;
            }
            getResponse().getWriter().print(info);
        } catch (Exception e) {
            logger.error("确认退款或者返回原件出现异常" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 同意退货
     *
     * @return
     */
    public String edit() {
        try {
            order.setEndTime(new Date());
            int result = aftersaleReturnOrderService.updateObjectSer(order, getLoginUserId().intValue(), getLoginUserName(),
                    changeReturnOrder);
            writeStr(result + "");
        } catch (Exception e) {
            logger.error("同意退货出现异常" + e.getMessage(), e);
            writeStr("-1");
        }
        return null;
    }

    /**
     * 进行质检,确认到货
     *
     * @return
     */
    public String editStatus() {
        AftersaleReturnOrder newOrder = new AftersaleReturnOrder();
        newOrder.setReturnId(returnId);
        if ("2".equals(orderStatus)) {
            newOrder.setBarCode(inputBarCode);
        }
        if (StringUtils.isNotEmpty(orderCode)) {
            newOrder.setOrderCode(orderCode);
        }
        newOrder.setStatus(orderStatus);

        try {
            int flg = aftersaleReturnOrderService.updateObjectSer(newOrder, getLoginUserId().intValue(),
                    getLoginUserName(), false);
            if (flg == 1) {
                strWriteJson("success");
            }
        } catch (Exception e) {
            logger.error("进行质检,确认到货失败：", e);
            strWriteJson("fail");
        }
        return null;
    }

    public String checkedStockOut() {
        ResultMessage resultMessage = new ResultMessage();
        try {
            // 将页面传过来的stockOutId转为数组，
            Long[] sIds = PurchaseUtils.strArrToIntArr(getRequest().getParameter("stockOutIdArray"));
            resultMessage = aftersaleReturnOrderService.checkedStockOut(sIds, getLoginUserId().intValue(), getLoginUserName());
            this.json = "{\"result\":" + resultMessage.getIsSuccess() + ",\"message\":\""
                            + resultMessage.getMessage() + "\"}";
        } catch (Exception e) {
            logger.error("进行质检,确认到货出现异常" + e.getMessage(), e);
            resultMessage.setIsSuccess(false);
            resultMessage.setMessage("系统异常！");
            this.json = new StringBuilder("{\"result\":").append(resultMessage.getIsSuccess())
                    .append(",\"message\":\"").append(resultMessage.getMessage()).append("\"}")
                    .toString();
            super.strWriteJson(json);
        }
        super.strWriteJson(json);
        return null;
    }

    /**
     * 配送单审核:完成配送单审核功能
     *
     * @return
     * @throws Exception
     */
    public String checkedDistributionInfo() {
        ResultMessage resultMessage = new ResultMessage();
        try {
            Long[] pIds = PurchaseUtils.strArrToIntArr(getRequest().getParameter("distributionIdArray"));
            resultMessage = aftersaleReturnOrderService.checkedDistributionInfo(pIds);
            this.json = "{\"result\":" + resultMessage.getIsSuccess() + ",\"message\":\""
                    + resultMessage.getMessage() + "\"}";
        } catch (Exception e) {
            logger.error("配送单审核:完成配送单审核功能出现异常" + e.getMessage(), e);
            resultMessage.setIsSuccess(false);
            resultMessage.setMessage("系统异常！");
            this.json = "{\"result\":" + resultMessage.getIsSuccess() + ",\"message\":\""
                    + resultMessage.getMessage() + "\"}";
            super.strWriteJson(json);
        }
        super.strWriteJson(json);
        return null;
    }

    public String logisticsInfo() {
        try {
            getRequest().setAttribute("logisticsCompanyMap", logisticsMap);
        } catch (Exception e) {
            logger.error("logisticsInfo出现异常" + e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * 确认配送
     *
     * @return
     */
    public String peiSong() {
        LogisticAndDistributionInfoVO vo = new LogisticAndDistributionInfoVO();
        int info = 0;
        try {
            orderAlter = aftersaleReturnOrderService.findByPrimaryKey(alterCode);
            // 订阅物流信息而塞入至orderAlterCode属性,
            // 因原有接口setOrderCode存入的是退换货订单号,所以订单号暂时塞入orderAlterCode属性中
            vo.setOrderAlterCode(orderCode);
            vo.setOrderCode(alterCode);
            vo.setLogisticsCode(logisticsCompany);
            // 物流公司名称
            vo.setLogisticsName(logisticsMap.get(logisticsCompany));
            vo.setLogisticsOrderNo(logisticsNo);// 快递编号
            vo.setOperator(super.getLoginUserName());
            vo.setOperatorType(operatorType);
            int count = aftersaleReturnOrderService.setLogisticAndDistributionInfo(vo);
            if (count == 1) {
                info = 1;
            }
            getResponse().getWriter().print(info);
        } catch (Exception e) {
            logger.error("确认配送出现异常" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 设置订单号或者订单编号的map
     */
    public void setOrderNoTypeMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("orderCode", "订单号");
        map.put("alterCode", "退货号");
        super.getRequest().setAttribute("orderNoQueryTypeMap", map);
    }

    /**
     * 订单导出功能
     *
     * @return
     */
    public String exportReturnOrder() {
        try {
            Map conditionMap = new HashMap();
            // 按订单编号信息种类来区分查询
            if (queryTypeForOrderNo != null && StringUtils.isNotEmpty(queryTypeForOrderNo)
                && queryTypeForOrderNoValue != null && StringUtils.isNotEmpty(queryTypeForOrderNoValue)) {
                // 导出订单和查询订单退换货单号放入的key值不一样
                if ("alterCode".equals(queryTypeForOrderNo)) {
                    queryTypeForOrderNo = "orderAlterCode";
                }
                conditionMap.put(queryTypeForOrderNo, queryTypeForOrderNoValue.trim());
            }
            // 申请人
            if (StringUtils.isNotBlank(proposer)) {
                conditionMap.put("proposer", proposer.trim());
            }
            // 状态
            if (StringUtils.isNotBlank(proposeStatus)) {
                conditionMap.put("proposeStatus", proposeStatus);
            }
            String exportBeginDate = createBegin;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 结束时间的判断,如果没有,默认当天
            if (StringUtils.isBlank(createEnd)) {
                createEnd = dateFormat.format(new Date());
            }
            // 默认只导出近三个月的 开始时间的判断
            if (StringUtils.isBlank(createBegin)) {
                // 往前推三个月
                Calendar calendar = Calendar.getInstance(); // 得到日历
                calendar.setTime(dateFormat.parse(createEnd));// 把当前时间赋给日历
                calendar.add(calendar.MONTH, -3); // 设置为前3月
                Date beforeThreeMonth = calendar.getTime();
                exportBeginDate = dateFormat.format(beforeThreeMonth);
            }
            conditionMap.put("createBegin", exportBeginDate);
            conditionMap.put("createEnd", createEnd);
            // 供应商ID
            conditionMap.put("commerceId", super.getSupplyId());
            // 返回xls地址
            String excelAddress = aftersaleReturnOrderService.exportReturnOrder(conditionMap);
            logger.info("导出订单excel下载地址=" + excelAddress);
            writeStr(excelAddress);
        } catch (Exception e) {
            logger.error("导出订单发生异常,异常详情如下:", e);
            writeStr("-1");
        }
        return null;
    }

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    public Long getReturnId() {
        return returnId;
    }

    public void setReturnId(Long returnId) {
        this.returnId = returnId;
    }

    public AftersaleReturnOrder getOrder() {
        return order;
    }

    public void setOrder(AftersaleReturnOrder order) {
        this.order = order;
    }

    public boolean isChangeReturnOrder() {
        return changeReturnOrder;
    }

    public void setChangeReturnOrder(boolean changeReturnOrder) {
        this.changeReturnOrder = changeReturnOrder;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getInputBarCode() {
        return inputBarCode;
    }

    public void setInputBarCode(String inputBarCode) {
        this.inputBarCode = inputBarCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public DistributionInfo getDistributionInfo() {
        return distributionInfo;
    }

    public void setDistributionInfo(DistributionInfo distributionInfo) {
        this.distributionInfo = distributionInfo;
    }

    public StockOut getStockOut() {
        return stockOut;
    }

    public void setStockOut(StockOut stockOut) {
        this.stockOut = stockOut;
    }

    public OrderAlterVo getSelectOrderAlterVo() {
        return selectOrderAlterVo;
    }

    public void setSelectOrderAlterVo(OrderAlterVo selectOrderAlterVo) {
        this.selectOrderAlterVo = selectOrderAlterVo;
    }

    public String getCreateBegin() {
        return createBegin;
    }

    public void setCreateBegin(String createBegin) {
        this.createBegin = createBegin;
    }

    public String getCreateEnd() {
        return createEnd;
    }

    public void setCreateEnd(String createEnd) {
        this.createEnd = createEnd;
    }

    public String getFinishBegin() {
        return finishBegin;
    }

    public void setFinishBegin(String finishBegin) {
        this.finishBegin = finishBegin;
    }

    public String getFinishEnd() {
        return finishEnd;
    }

    public void setFinishEnd(String finishEnd) {
        this.finishEnd = finishEnd;
    }

    public OrderAlter getOrderAlter() {
        return orderAlter;
    }

    public void setOrderAlter(OrderAlter orderAlter) {
        this.orderAlter = orderAlter;
    }

    public List getListOrderAlterOperates() {
        return listOrderAlterOperates;
    }

    public void setListOrderAlterOperates(List listOrderAlterOperates) {
        this.listOrderAlterOperates = listOrderAlterOperates;
    }

    public AftersaleReturnOrderService getAftersaleReturnOrderService() {
        return aftersaleReturnOrderService;
    }

    public void setAftersaleReturnOrderService(AftersaleReturnOrderService aftersaleReturnOrderService) {
        this.aftersaleReturnOrderService = aftersaleReturnOrderService;
    }

    public List getListOrderAlterPays() {
        return listOrderAlterPays;
    }

    public void setListOrderAlterPays(List listOrderAlterPays) {
        this.listOrderAlterPays = listOrderAlterPays;
    }

    public String getAlterCode() {
        return alterCode;
    }

    public void setAlterCode(String alterCode) {
        this.alterCode = alterCode;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public BigDecimal getReturnFare() {
        return returnFare;
    }

    public void setReturnFare(BigDecimal returnFare) {
        this.returnFare = returnFare;
    }

    public BigDecimal getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(BigDecimal returnMoney) {
        this.returnMoney = returnMoney;
    }

    public BigDecimal getPreferentialAmount() {
        return preferentialAmount;
    }

    public void setPreferentialAmount(BigDecimal preferentialAmount) {
        this.preferentialAmount = preferentialAmount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getShowPath() {
        return showPath;
    }

    public void setShowPath(String showPath) {
        this.showPath = showPath;
    }

    public List getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List photoList) {
        this.photoList = photoList;
    }

    public String getCmsPagePath() {
        return cmsPagePath;
    }

    public void setCmsPagePath(String cmsPagePath) {
        this.cmsPagePath = cmsPagePath;
    }

    public OrderItem getItem() {
        return item;
    }

    public void setItem(OrderItem item) {
        this.item = item;
    }

    public OrderMainVo getOrderMain() {
        return orderMain;
    }

    public void setOrderMain(OrderMainVo orderMain) {
        this.orderMain = orderMain;
    }

    public Boolean getIsSuit() {
        return isSuit;
    }

    public void setIsSuit(Boolean isSuit) {
        this.isSuit = isSuit;
    }

    public Boolean getIsAdditional() {
        return isAdditional;
    }

    public void setIsAdditional(Boolean isAdditional) {
        this.isAdditional = isAdditional;
    }

    public int getCkType() {
        return ckType;
    }

    public void setCkType(int ckType) {
        this.ckType = ckType;
    }

    public static String getImgpath() {
        return imgPath;
    }

    public static String getProductpath() {
        return productPath;
    }

    public String getQueryTypeForOrderNo() {
        return queryTypeForOrderNo;
    }

    public void setQueryTypeForOrderNo(String queryTypeForOrderNo) {
        this.queryTypeForOrderNo = queryTypeForOrderNo;
    }

    public String getQueryTypeForOrderNoValue() {
        return queryTypeForOrderNoValue;
    }

    public void setQueryTypeForOrderNoValue(String queryTypeForOrderNoValue) {
        this.queryTypeForOrderNoValue = queryTypeForOrderNoValue;
    }

    public static Integer getOperatortype() {
        return operatorType;
    }

    public String getProposer() {
        return proposer;
    }

    public void setProposer(String proposer) {
        this.proposer = proposer;
    }

    public String getProposeStatus() {
        return proposeStatus;
    }

    public void setProposeStatus(String proposeStatus) {
        this.proposeStatus = proposeStatus;
    }

    public BigDecimal getFareSubsidy() {
        return fareSubsidy;
    }

    public void setFareSubsidy(BigDecimal fareSubsidy) {
        this.fareSubsidy = fareSubsidy;
    }

    public BigDecimal getTotalReturnFare() {
        return totalReturnFare;
    }

    public void setTotalReturnFare(BigDecimal totalReturnFare) {
        this.totalReturnFare = totalReturnFare;
    }
}
