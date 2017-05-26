package com.kmzyc.supplier.action;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.framework.constants.Constants;
import com.kmzyc.order.remote.SupplierSettlementService;
import com.kmzyc.supplier.util.StringUtil;
import com.pltfm.app.entities.DiffAdj;
import com.pltfm.app.entities.DiffAdjCriteria;
import com.pltfm.app.entities.HurlFare;
import com.pltfm.app.entities.HurlFareCriteria;
import com.pltfm.app.entities.HurlProduct;
import com.pltfm.app.entities.HurlProductCriteria;
import com.pltfm.app.entities.SellerSettlement;
import com.pltfm.app.entities.SellerSettlementCriteria;
import com.pltfm.app.entities.SettlementRefund;
import com.pltfm.app.entities.SettlementRefundCriteria;;

/**
 * 供应商结算
 *
 * @author YaoChao
 */
@Controller("supplierSettlementAction")
@Scope(value = "prototype")
public class SupplierSettlementAction extends SupplierBaseAction {

    @Resource
    private SupplierSettlementService supplierSettlementService;

    private static Logger logger = LoggerFactory.getLogger(ApplySupplierAction.class);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String startDate;

    private String endDate;

    private SellerSettlementCriteria criteria;

    private SellerSettlement sellerSettlement;

    private String settlementNo;

    private HurlProductCriteria hurlProductCriteria;

    private List<HurlProduct> hurlProductlist;

    private Map hurlProductmap;

    // 运费明细
    private HurlFareCriteria hurlFareCriteria;

    private List<HurlFare> hurlFareList;

    private SettlementRefundCriteria settlementRefundCriteria;

    // 退款明细
    private Map hurlFaremap;

    private List<SettlementRefund> settlementRefundList;

    private Map settlementRefundmap;

    // 差异调整明细
    private DiffAdjCriteria diffAdjCriteria;

    private List<DiffAdj> diffAdjList;

    private Map diffAdjmap;

    // 账期
    private String settlementPeriod;

    /**
     * 查询结算单 列表
     *
     * @return
     */
    public String findSettlementList() {
        List<SellerSettlement> list = null;
        int count = 0;
        try {
            pagintion = getPagination(Constants.VIEW_PAGE, Integer.parseInt(getPageSize()));
            String loginId = getSession().getAttribute(Constants.SESSION_USER_ID).toString();
            if (criteria == null) {
                criteria = new SellerSettlementCriteria();
            }
            if (pagintion.getStartindex() >= 1) {
                pagintion.setStartindex(pagintion.getStartindex() - 1);
            }
            criteria.setLoginId(loginId);
            if (StringUtils.isNotEmpty(startDate)) {
                criteria.setStartDate(sdf.parse(startDate + " 00:00:00"));
            }
            if (StringUtils.isNotEmpty(endDate)) {
                criteria.setEndDate(sdf.parse(endDate + " 23:59:59"));
            }
            criteria.setStartIndex(pagintion.getStartindex());
            criteria.setEndIndex(pagintion.getEndindex());
            count = supplierSettlementService.selectSettlementSize(criteria);
            list = supplierSettlementService.selectSettlementList(criteria);

        } catch (Exception e) {
            logger.error("查询结算单 列表 错误：", e);
            return ERROR;
        }

        pagintion.setRecordList(list);
        pagintion.setTotalRecords(count);
        pagintion.setObjCondition(criteria);
        return SUCCESS;
    }

    /**
     * 商家结算单详情 （详细的，全部查出） ByNo
     *
     * @return
     */
    public String sellerSettlementDetailByNo() {
        try {
            sellerSettlement = supplierSettlementService.getSellerSettlementDetailByNo(settlementNo);
        } catch (Exception e) {
            logger.error("商家结算单详情 错误：", e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 明细展示接口 （简单）
     *
     * @return
     */
    public String sellerSettlementSimpleDetailByNo() {
        return SUCCESS;
    }

    /**
     * 查询商家结算单详情 以及妥投商品结算明细 列表
     *
     * @return
     */
    public String findHurlProductList() {
        int count = 0;
        try {
            pagintion = getPagination(Constants.VIEW_PAGE, Integer.parseInt(getPageSize()));
            // 结算单详情
            sellerSettlement = supplierSettlementService.getSellerSettlementDetailByNo(settlementNo);
            if (hurlProductCriteria == null) {
                hurlProductCriteria = new HurlProductCriteria();
            }
            hurlProductCriteria.setSettlementNo(settlementNo);
            if (pagintion.getStartindex() >= 1) {
                pagintion.setStartindex(pagintion.getStartindex() - 1);
            }
            if (StringUtils.isNotEmpty(startDate)) {
                hurlProductCriteria.setStartDate(sdf.parse(startDate));
            }
            if (StringUtils.isNotEmpty(endDate)) {
                hurlProductCriteria.setEndDate(sdf.parse(endDate));
            }
            hurlProductCriteria.setStartIndex(pagintion.getStartindex());
            hurlProductCriteria.setEndIndex(pagintion.getEndindex());
            count = supplierSettlementService.selectHurlProductSize(hurlProductCriteria);
            hurlProductlist = supplierSettlementService.selectHurlProductList(hurlProductCriteria);
            hurlProductmap = supplierSettlementService.selectHurlSum(hurlProductCriteria);
        } catch (Exception e) {
            logger.error("妥投商品结算明细 列表错误：", e);
            return ERROR;
        }

        pagintion.setRecordList(hurlProductlist);
        pagintion.setTotalRecords(count);
        pagintion.setObjCondition(hurlProductCriteria);
        return SUCCESS;
    }

    /**
     * 运费明细 列表
     *
     * @return
     */
    public String findHurlFareList() {

        int count = 0;
        try {
            // 结算单详情
            pagintion = getPagination(Constants.VIEW_PAGE, Integer.parseInt(super.getPageSize()));
            sellerSettlement = supplierSettlementService.getSellerSettlementDetailByNo(settlementNo);
            if (hurlFareCriteria == null) {
                hurlFareCriteria = new HurlFareCriteria();
            }
            hurlFareCriteria.setSettlementNo(settlementNo);
            if (pagintion.getStartindex() >= 1) {
                pagintion.setStartindex(pagintion.getStartindex() - 1);
            }
            if (StringUtils.isNotEmpty(startDate)) {
                hurlFareCriteria.setStartDate(sdf.parse(startDate));
            }
            if (StringUtils.isNotEmpty(endDate)) {
                hurlFareCriteria.setEndDate(sdf.parse(endDate));
            }
            hurlFareCriteria.setStartIndex(pagintion.getStartindex());
            hurlFareCriteria.setEndIndex(pagintion.getEndindex());
            count = supplierSettlementService.selectHurlFareSize(hurlFareCriteria);
            hurlFareList = supplierSettlementService.selectHurlFareList(hurlFareCriteria);
            hurlFaremap = supplierSettlementService.hurlFareSumResult(hurlFareCriteria);
        } catch (Exception e) {
            logger.error("运费明细 列表列表错误：", e);
            return ERROR;
        }

        pagintion.setRecordList(hurlFareList);
        pagintion.setTotalRecords(count);
        pagintion.setObjCondition(hurlFareCriteria);
        return SUCCESS;
    }

    /**
     * 退款明细
     *
     * @return
     */
    public String findSettlementRefundList() {
        int count = 0;
        try {
            pagintion = getPagination(Constants.VIEW_PAGE, Integer.parseInt(super.getPageSize()));
            // 退款明细
            sellerSettlement = supplierSettlementService.getSellerSettlementDetailByNo(settlementNo);
            if (settlementRefundCriteria == null) {
                settlementRefundCriteria = new SettlementRefundCriteria();
            }
            settlementRefundCriteria.setSettlementNo(settlementNo);
            if (pagintion.getStartindex() >= 1) {
                pagintion.setStartindex(pagintion.getStartindex() - 1);
            }
            if (StringUtils.isNotEmpty(startDate)) {
                settlementRefundCriteria.setStartDate(sdf.parse(startDate));
            }
            if (StringUtils.isNotEmpty(endDate)) {
                settlementRefundCriteria.setEndDate(sdf.parse(endDate));
            }
            settlementRefundCriteria.setStartIndex(pagintion.getStartindex());
            settlementRefundCriteria.setEndIndex(pagintion.getEndindex());
            count = supplierSettlementService.selectSettlementRefundSize(settlementRefundCriteria);
            settlementRefundList = supplierSettlementService.selectSettlementRefundList(settlementRefundCriteria);
            settlementRefundmap = supplierSettlementService.refundSum(settlementRefundCriteria);
        } catch (Exception e) {
            logger.error("退款明细列表错误：", e);
            return ERROR;
        }

        pagintion.setRecordList(settlementRefundList);
        pagintion.setTotalRecords(count);
        pagintion.setObjCondition(settlementRefundCriteria);
        return SUCCESS;
    }

    /**
     * 差异调整明细
     *
     * @return
     */
    public String findDiffAdjList() {
        int count = 0;
        try {
            // 差异调整明细
            sellerSettlement = supplierSettlementService.getSellerSettlementDetailByNo(settlementNo);
            pagintion = getPagination(Constants.VIEW_PAGE, Integer.parseInt(super.getPageSize()));
            if (diffAdjCriteria == null) {
                diffAdjCriteria = new DiffAdjCriteria();
            }
            diffAdjCriteria.setCalcSettmentNo(settlementNo);
            if (pagintion.getStartindex() >= 1) {
                pagintion.setStartindex(pagintion.getStartindex() - 1);
            }
            if (StringUtils.isNotEmpty(startDate)) {
                diffAdjCriteria.setStartDate(sdf.parse(startDate));
            }
            if (StringUtils.isNotEmpty(endDate)) {
                diffAdjCriteria.setEndDate(sdf.parse(endDate));
            }
            diffAdjCriteria.setStartIndex(pagintion.getStartindex());
            diffAdjCriteria.setEndIndex(pagintion.getEndindex());
            count = supplierSettlementService.selectDiffAdjSize(diffAdjCriteria);
            diffAdjList = supplierSettlementService.selectDiffAdjList(diffAdjCriteria);
            diffAdjmap = supplierSettlementService.selectSumDiff(diffAdjCriteria);
        } catch (Exception e) {
            logger.error("差异调整明细列表错误：", e);
            return ERROR;
        }

        pagintion.setRecordList(diffAdjList);
        pagintion.setTotalRecords(count);
        pagintion.setObjCondition(diffAdjCriteria);
        return SUCCESS;
    }

    /**
     * 导出-妥投商品明细
     *
     * @return
     * @throws Exception
     */
    public String outHurlProductList() throws Exception {
        String filePath = "";
        if (StringUtils.isEmpty(settlementNo)) {
            return ERROR;
        }
        if (hurlProductCriteria == null) {
            hurlProductCriteria = new HurlProductCriteria();
        }
        if (StringUtils.isNotEmpty(startDate)) {
            hurlProductCriteria.setStartDate(sdf.parse(startDate));
        }
        if (StringUtils.isNotEmpty(endDate)) {
            hurlProductCriteria.setEndDate(sdf.parse(endDate));
        }
        try {
            settlementNo = StringUtil.formatScript(settlementNo);
            if (StringUtils.isEmpty(filePath)) {
                filePath = supplierSettlementService.hurlExport(settlementNo, filePath, hurlProductCriteria);
            }
        } catch (Exception e) {
            logger.error("导出-妥投商品明细失败:", e);
        }
        getRequest().setAttribute("filePath", filePath);
        getRequest().setAttribute("fileName", settlementPeriod);
        return SUCCESS;
    }

    /**
     * 导出-退货
     *
     * @return
     * @throws Exception
     */
    public String outputSettlementRefundData() throws Exception {
        String filePath = "";
        if (StringUtils.isEmpty(settlementNo)) {
            return ERROR;
        }
        if (settlementRefundCriteria == null) {
            settlementRefundCriteria = new SettlementRefundCriteria();
        }
        if (StringUtils.isNotEmpty(startDate)) {
            settlementRefundCriteria.setStartDate(sdf.parse(startDate));
        }
        if (StringUtils.isNotEmpty(endDate)) {
            settlementRefundCriteria.setEndDate(sdf.parse(endDate));
        }
        try {
            settlementNo = StringUtil.formatScript(settlementNo);
            if (StringUtils.isEmpty(filePath)) {
                filePath = supplierSettlementService.refundExport(settlementNo, filePath, settlementRefundCriteria);
            }
        } catch (Exception e) {
            logger.error("导出-退货失败:", e);
        }
        getRequest().setAttribute("filePath", filePath);
        getRequest().setAttribute("fileName", settlementPeriod);
        return SUCCESS;
    }

    /**
     * 导出-妥投订单运费
     *
     * @return
     * @throws Exception
     */
    public String outputHurlFareData() throws Exception {
        if (StringUtils.isEmpty(settlementNo)) {
            return ERROR;
        }
        if (hurlFareCriteria == null) hurlFareCriteria = new HurlFareCriteria();
        if (StringUtils.isNotEmpty(startDate)) hurlFareCriteria.setStartDate(sdf.parse(startDate));
        if (StringUtils.isNotEmpty(endDate)) hurlFareCriteria.setEndDate(sdf.parse(endDate));
        String filePath = "";
        try {
            settlementNo = StringUtil.formatScript(settlementNo);
            if (StringUtils.isEmpty(filePath)) {
                filePath = supplierSettlementService.hurlFareExport(settlementNo, filePath, hurlFareCriteria);
            }
        } catch (Exception e) {
            logger.error("导出-妥投订单运费失败:", e);
        }
        getRequest().setAttribute("filePath", filePath);
        getRequest().setAttribute("fileName", settlementPeriod);
        return SUCCESS;
    }

    /**
     * 导出-差异调整Diff_adj
     *
     * @return
     * @throws Exception
     */
    public String outputDiffAdjData() throws Exception {
        String filePath = "";
        if (StringUtils.isEmpty(settlementNo)) {
            return ERROR;
        }
        if (diffAdjCriteria == null) diffAdjCriteria = new DiffAdjCriteria();
        if (StringUtils.isNotEmpty(startDate)) diffAdjCriteria.setStartDate(sdf.parse(startDate));
        if (StringUtils.isNotEmpty(endDate)) diffAdjCriteria.setEndDate(sdf.parse(endDate));
        try {
            settlementNo = StringUtil.formatScript(settlementNo);
            if (StringUtils.isEmpty(filePath)) {
                filePath = supplierSettlementService.diffAdjExport(settlementNo, filePath, diffAdjCriteria);
            }
        } catch (Exception e) {
            logger.error("导出-差异调整失败:", e);
        }
        getRequest().setAttribute("filePath", filePath);
        getRequest().setAttribute("fileName", settlementPeriod);
        return SUCCESS;
    }

    /**
     * 导出-订单详情
     *
     * @return
     * @throws Exception
     */
    public String outExportSettleOrder() throws Exception {
        String filePath = "";
        if (StringUtils.isEmpty(settlementNo)) {
            return ERROR;
        }

        try {
            settlementNo = StringUtil.formatScript(settlementNo);
            if (StringUtils.isEmpty(filePath)) {
                filePath = supplierSettlementService.exportSettleOrder(settlementNo,
                        getLoginUserId().intValue());
            }
        } catch (Exception e) {
            logger.error("供应商结算管理-导出订单详情出错:" + e);
        }
        getRequest().setAttribute("filePath", filePath);
        getRequest().setAttribute("fileName", settlementPeriod);
        return SUCCESS;
    }

    public SellerSettlementCriteria getCriteria() {
        return criteria;
    }

    public void setCriteria(SellerSettlementCriteria criteria) {
        this.criteria = criteria;
    }

    public SellerSettlement getSellerSettlement() {
        return sellerSettlement;
    }

    public void setSellerSettlement(SellerSettlement sellerSettlement) {
        this.sellerSettlement = sellerSettlement;
    }

    public String getSettlementNo() {
        return settlementNo;
    }

    public void setSettlementNo(String settlementNo) {
        this.settlementNo = settlementNo;
    }

    public HurlProductCriteria getHurlProductCriteria() {
        return hurlProductCriteria;
    }

    public void setHurlProductCriteria(HurlProductCriteria hurlProductCriteria) {
        this.hurlProductCriteria = hurlProductCriteria;
    }

    public List<HurlProduct> getHurlProductlist() {
        return hurlProductlist;
    }

    public void setHurlProductlist(List<HurlProduct> hurlProductlist) {
        this.hurlProductlist = hurlProductlist;
    }

    public List<HurlFare> getHurlFareList() {
        return hurlFareList;
    }

    public void setHurlFareList(List<HurlFare> hurlFareList) {
        this.hurlFareList = hurlFareList;
    }

    public HurlFareCriteria getHurlFareCriteria() {
        return hurlFareCriteria;
    }

    public void setHurlFareCriteria(HurlFareCriteria hurlFareCriteria) {
        this.hurlFareCriteria = hurlFareCriteria;
    }

    public SettlementRefundCriteria getSettlementRefundCriteria() {
        return settlementRefundCriteria;
    }

    public void setSettlementRefundCriteria(SettlementRefundCriteria settlementRefundCriteria) {
        this.settlementRefundCriteria = settlementRefundCriteria;
    }

    public List<SettlementRefund> getSettlementRefundList() {
        return settlementRefundList;
    }

    public void setSettlementRefundList(List<SettlementRefund> settlementRefundList) {
        this.settlementRefundList = settlementRefundList;
    }

    public DiffAdjCriteria getDiffAdjCriteria() {
        return diffAdjCriteria;
    }

    public void setDiffAdjCriteria(DiffAdjCriteria diffAdjCriteria) {
        this.diffAdjCriteria = diffAdjCriteria;
    }

    public List<DiffAdj> getDiffAdjList() {
        return diffAdjList;
    }

    public void setDiffAdjList(List<DiffAdj> diffAdjList) {
        this.diffAdjList = diffAdjList;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public String getSettlementPeriod() {
        return settlementPeriod;
    }

    public void setSettlementPeriod(String settlementPeriod) {
        this.settlementPeriod = settlementPeriod;
    }

    public Map getSettlementRefundmap() {
        return settlementRefundmap;
    }

    public void setSettlementRefundmap(Map settlementRefundmap) {
        this.settlementRefundmap = settlementRefundmap;
    }

    public Map getDiffAdjmap() {
        return diffAdjmap;
    }

    public void setDiffAdjmap(Map diffAdjmap) {
        this.diffAdjmap = diffAdjmap;
    }

    public Map getHurlFaremap() {
        return hurlFaremap;
    }

    public void setHurlFaremap(Map hurlFaremap) {
        this.hurlFaremap = hurlFaremap;
    }

    public Map getHurlProductmap() {
        return hurlProductmap;
    }

    public void setHurlProductmap(Map hurlProductmap) {
        this.hurlProductmap = hurlProductmap;
    }
}
