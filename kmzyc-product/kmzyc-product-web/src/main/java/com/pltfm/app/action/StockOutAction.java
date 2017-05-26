package com.pltfm.app.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.common.collect.Lists;
import com.kmzyc.commons.page.Page;
import com.kmzyc.order.remote.OrderAlterChangeStatusRemoteService;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.enums.AuditStatus;
import com.pltfm.app.enums.MsgOperation;
import com.pltfm.app.enums.StockLogBillType;
import com.pltfm.app.enums.StockLogOpType;
import com.pltfm.app.enums.StockOutStatus;
import com.pltfm.app.enums.StockOutTypeStatus;
import com.pltfm.app.service.OrderRemoteService;
import com.pltfm.app.service.ProductService;
import com.pltfm.app.service.ProductStockLogService;
import com.pltfm.app.service.StockOutService;
import com.pltfm.app.util.OrderAlterDictionaryEnum;
import com.pltfm.app.util.PurchaseUtils;
import com.pltfm.app.vobject.ProductStock;
import com.pltfm.app.vobject.StockOut;
import com.pltfm.app.vobject.ViewProductSku;

/**
 * 出库单业务处理action
 *
 * @author luoyi
 * @createDate 2013/08/15
 */
@Controller("stockOutAction")
@Scope(value = "prototype")
public class StockOutAction extends BaseAction {

    private static final long serialVersionUID = 8401049790393799213L;

    @Resource
    private StockOutService stockOutService;

    @Resource
    private OrderRemoteService orderRemoteService;

    @Resource
    private ProductStockLogService productStockLogService;

    @Resource
    private ProductService productService;

    @Resource
    private OrderAlterChangeStatusRemoteService orderAlterChangeStatusRemoteService;

    // 跳转页数
    private int pageNum;

    private Page page = new Page();

    private Date endDate;// 查询的结束日期

    private StockOut stockOut = new StockOut();// 出库单

    // 出库单查询条件
    private StockOut queryStockOut = new StockOut();

    private List<StockOut> stockoutlistList;

    private Long stockOutId; // 出库单ID

    private String type;

    private String trId;// 添加列表所在行的id

    private ViewProductSku viewProductSku = new ViewProductSku();

    private Long warehouseId;// 仓库ID

    /**
     * 出库单列表:查询出库单
     *
     * @return SUCCESS
     * @throws Exception
     */
    public String findStockOutList() {
        try {
            stockoutlistList = stockOutService.findStockOutList(page, queryStockOut, endDate);
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }

        setWarehouseMap();// 仓库信息
        setStockOutStatusMap();// 审核状态
        setStockOutTypeMap();// 出库单类型
        return SUCCESS;
    }

    /**
     * ajax请求:批量删除出库单
     *
     * @return
     * @throws SQLException
     */
    public String deleteStockOutById() {
        Long[] sIds = PurchaseUtils.strArrToIntArr(getRequest().getParameter("stockOutIdArray"));
        stockoutlistList = Lists.newArrayList(sIds).stream().map(id -> {
            StockOut out = new StockOut();
            out.setStockOutId(id);
            return out;
        }).collect(Collectors.toList());
        ResultMessage resultMessage = new ResultMessage();

        try {
            // 批量删除
            resultMessage = stockOutService.deleteStockOutList(stockoutlistList);
            json = "{\"result\":" + resultMessage.getIsSuccess() + ",\"message\":\"" + resultMessage.getMessage() + "\"}";
        } catch (Exception e) {
            e.printStackTrace();
            resultMessage.setIsSuccess(false);
            resultMessage.setMessage("批量删除出库单失败!");
            this.json = "{\"result\":" + resultMessage.getIsSuccess() + ",\"message\":\"" + resultMessage.getMessage() + "\"}";
            return ERROR;
        }
        strWriteJson(json);
        return null;
    }

    /**
     * 查找所有SKU产品(包含操作所在行的id)
     *
     * @return
     */
    public String findAllSkuProductByPurchase() {
        if (page == null) {
            page = new Page();
        }
        if (viewProductSku == null) {
            viewProductSku = new ViewProductSku();
        }

        try {
            stockOutService.searchBySkuPage(page, viewProductSku, warehouseId);
            //三级类目
            getAllCategoryList(viewProductSku.getBCategoryId(), viewProductSku.getMCategoryId());
            setProductStatusMap();
            setWarehouseForStatusMap();// 可用仓库
            setProductBrandMap();
            getRequest().getSession().setAttribute("trId", trId);// 所在行id
            getRequest().getSession().setAttribute("warehouseId", warehouseId);// 所在行id
        } catch (Exception e) {
            logger.error("查找所有SKU产品(包含操作所在行的id)失败：", e);
            return ERROR;
        }
        if ("stock".equals(type)) {
            return type;
        }
        return SUCCESS;
    }

    /**
     * 出库单审核:到审核页面
     *
     * @return
     * @throws Exception
     */
    public String toStockOutCheck() {
        try {
            queryStockOut.setStatus(Short.valueOf(StockOutStatus.UNAUDIT.getStatus()));//只查找未审核的单据
            stockoutlistList = stockOutService.findStockOutList(page, queryStockOut, endDate);
        } catch (Exception e) {
            logger.error("到审核页面出错" + e.getMessage(), e);
            return ERROR;
        }
        setWarehouseMap();// 仓库信息
        setStockOutStatusMap();// 状态信息
        setStockOutTypeMap();// 类型信息
        return SUCCESS;
    }

    /**
     * ajax请求: 出库单批量审核:审核功能
     *
     * @return
     * @throws Exception
     */
    public String checkedStockOut() {
        ResultMessage resultMessage = new ResultMessage();
        // 将页面传过来的stockOutId转为数组，
        Long[] sIds = PurchaseUtils.strArrToIntArr(getRequest().getParameter("stockOutIdArray"));
        stockoutlistList = new ArrayList<StockOut>();
        List<Long> stockOutIdList = Lists.newArrayList(sIds);
        List<StockOut> stockOutOrderList = new ArrayList<StockOut>();//订单出库
        List<StockOut> stockOutExchangeList = new ArrayList<StockOut>();//换货出库
        List<StockOut> stockOutOtherList = new ArrayList<StockOut>();//其它出库

        try {
            resultMessage = stockOutService.selectStockOutListByStockOutIds(stockOutIdList);
            if (!resultMessage.getIsSuccess()) {
                json = "{\"result\":" + resultMessage.getIsSuccess() +
                        ",\"message\":\"" + resultMessage.getMessage() + "\"}";
                strWriteJson(json);
                return null;
            }

            stockoutlistList = (List<StockOut>) resultMessage.getObject();
            for (StockOut stockOutTmp : stockoutlistList) {// 转为list集合
                stockOutTmp.setAuditUser(getLoginUserId());// 审核人:登录用户ID
                stockOutTmp.setCheckUserName(getLoginUserName());// 审核人姓名
                stockOutTmp.setAuditDate(new Date());// 审核日期
                stockOutTmp.setStatus(Short.valueOf(AuditStatus.AUDIT.getStatus()));// 状态
                if (StockOutTypeStatus.OTHER.getStatus().shortValue() == stockOutTmp.getType().shortValue()) {
                    stockOutOtherList.add(stockOutTmp);
                } else if (StockOutTypeStatus.EXCHANGE.getStatus().shortValue() == stockOutTmp.getType().shortValue()) {
                    stockOutExchangeList.add(stockOutTmp);
                } else if (StockOutTypeStatus.ORDER.getStatus().shortValue() == stockOutTmp.getType().shortValue()) {
                    stockOutOrderList.add(stockOutTmp);
                }
            }

            if (CollectionUtils.isNotEmpty(stockOutOtherList)) {
                // 其它出库审核
                resultMessage = stockOutService.auditStockOutForOther(stockOutOtherList);
                if (resultMessage.getIsSuccess()) {
                    try {
                        //异步记录日志
                        productStockLogService.executeBatchAddStockLog((List<ProductStock>) resultMessage.getObject(),
                                resultMessage.getMap(), StockLogOpType.OUT.getType(),
                                StockLogBillType.STOCK_OUT.getType(), getLoginUserId(),
                                getLoginUserName(), new Date(), StockLogBillType.STOCK_OUT.getTitle());

                        //减少库存后通知搜索引擎
                        productService.changeProductInfoNotify((List<Long>) resultMessage.getObject2(),
                                MsgOperation.UPDATE.getType());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            if (CollectionUtils.isNotEmpty(stockOutOrderList)) {
                // 订单出库审核
                resultMessage = stockOutService.auditStockOutForOrder(stockOutOrderList);
                if (resultMessage.getIsSuccess()) {
                    try {
                        //异步记录日志
                        productStockLogService.executeBatchAddStockLog((List<ProductStock>) resultMessage.getObject(),
                                resultMessage.getMap(), StockLogOpType.OUT.getType(), StockLogBillType.STOCK_OUT.getType(),
                                getLoginUserId(), getLoginUserName(), new Date(), StockLogBillType.STOCK_OUT.getTitle());
                        //减少库存后通知搜索引擎
                        productService.changeProductInfoNotify((List<Long>) resultMessage.getObject2(),
                                MsgOperation.UPDATE.getType());
                    } catch (Exception e) {
                        logger.error("订单手动出库审核成功后记录日志错误："+e.getMessage(), e);
                    }
                }
            }
            this.json = "{\"result\":" + resultMessage.getIsSuccess()
                    + ",\"message\":\"" + resultMessage.getMessage() + "\"}";
        } catch (Exception e) {
            this.json = "{\"result\":"+false+",\"message\":\""+e.getMessage()+"\"}";
            super.strWriteJson(json);
            return null;
        }

        try {
            if (CollectionUtils.isNotEmpty(stockOutExchangeList)) {
                // 换货出库审核
                resultMessage = stockOutService.auditStockOutForExchange(stockOutExchangeList);
                if (!resultMessage.getIsSuccess()) {
                    throw new Exception("换货出库审核失败!");
                }

                try {
                    //异步记录日志
                    productStockLogService.executeBatchAddStockLog((List<ProductStock>) resultMessage.getObject(),
                            resultMessage.getMap(), StockLogOpType.OUT.getType(),
                            StockLogBillType.STOCK_OUT.getType(), getLoginUserId(),
                            getLoginUserName(), new Date(), StockLogBillType.STOCK_OUT.getTitle());

                    //减少库存后通知搜索引擎
                    productService.changeProductInfoNotify((List<Long>) resultMessage.getObject2(),
                            MsgOperation.UPDATE.getType());
                } catch (Exception e) {
                    logger.error("换货出库审核成功后记录日志错误：" + e.getMessage(), e);
                }
            }

            this.json = "{\"result\":" + resultMessage.getIsSuccess()
                    + ",\"message\":\"" + resultMessage.getMessage() + "\"}";

        } catch (Exception e) {
            e.printStackTrace();

            try {
                //批量修改换货出库单状态为“审核不通过”
                stockOutService.batchUpdateStatusForStockOut(stockOutExchangeList);
                for (StockOut stockOut : stockOutExchangeList) {
                    //调订单转退货接口
                    orderAlterChangeStatusRemoteService.changeAlterStatusForProduct(stockOut.getCreateUserName(),
                            stockOut.getBillNo(),
                            Long.valueOf(OrderAlterDictionaryEnum.Propose_Status.ExchangeToReturn.getKey()), null);
                }

                // TODO 批量修改换货出库单状态为“审核不通过”,这里还要释放库存
                stockOutService.orderAfterByExchange_notThroughAudit(stockOutExchangeList, null);
            } catch (Exception ex) {
                this.json = new StringBuilder("{\"result\":").append(false)
                        .append(",\"message\":\"").append(e.getMessage()).append("\"}").toString();
                super.strWriteJson(json);
            }

            try {
                orderRemoteService.changeAlterStatusForProduct(stockOutExchangeList);
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }

            this.json = new StringBuilder("{\"result\":").append(false)
                    .append(",\"message\":\"").append(e.getMessage()).append("审核不通过!").append("\"}").toString();
        }
        super.strWriteJson(json);
        return null;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<StockOut> getStockoutlistList() {
        return stockoutlistList;
    }

    public void setStockoutlistList(List<StockOut> stockoutlistList) {
        this.stockoutlistList = stockoutlistList;
    }

    public StockOut getStockOut() {
        return stockOut;
    }

    public void setStockOut(StockOut stockOut) {
        this.stockOut = stockOut;
    }

    public Long getStockOutId() {
        return stockOutId;
    }

    public void setStockOutId(Long stockOutId) {
        this.stockOutId = stockOutId;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public StockOut getQueryStockOut() {
        return queryStockOut;
    }

    public void setQueryStockOut(StockOut queryStockOut) {
        this.queryStockOut = queryStockOut;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTrId() {
        return trId;
    }

    public void setTrId(String trId) {
        this.trId = trId;
    }

    public ViewProductSku getViewProductSku() {
        return viewProductSku;
    }

    public void setViewProductSku(ViewProductSku viewProductSku) {
        this.viewProductSku = viewProductSku;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }
}
