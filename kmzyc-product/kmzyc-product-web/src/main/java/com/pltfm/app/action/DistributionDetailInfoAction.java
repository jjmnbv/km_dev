package com.pltfm.app.action;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.enums.DistributionInfostatus;
import com.pltfm.app.service.DistributionDetailInfoService;
import com.pltfm.app.service.DistributionInfoService;
import com.pltfm.app.service.OrderRemoteService;
import com.pltfm.app.service.StockOutService;
import com.pltfm.app.util.BillCodeUtils;
import com.pltfm.app.util.BillPrefix;
import com.pltfm.app.vobject.DistributionDetailInfo;
import com.pltfm.app.vobject.DistributionInfo;
import com.pltfm.app.vobject.LogisticAndDistributionInfoVO;
import com.pltfm.app.vobject.StockOut;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * 配送单业务处理action
 *
 * @author luoyi
 * @createDate 2013/08/15
 */
@Controller("distributionDetailInfoAction")
@Scope(value = "prototype")
public class DistributionDetailInfoAction extends BaseAction {

    private static final long serialVersionUID = -5811677142121118019L;

    @Resource
    private DistributionDetailInfoService distributionDetailInfoService;

    @Resource
    private DistributionInfoService distributionInfoService;

    @Resource
    private StockOutService stockOutService;

    private Long distributionId;// 配送单ID
    private Page page = new Page();
    private DistributionDetailInfo distributionDetailInfo = new DistributionDetailInfo();
    private DistributionInfo distributionInfo = new DistributionInfo();
    private String rtnMessage; // 显示信息
    private Integer warehouseId;// 仓库id
    private String showType;// 查看类型
    private DistributionInfo queryDistribution = new DistributionInfo();//查询条件
    private Date endDate;// 结束日期
    private int pageNum;  //分页的第几页
    private boolean isDrugTmp;//是否是药品
    private List<DistributionDetailInfo> distributionDetailInfoList;
    @Resource
    private OrderRemoteService orderRemoteService;

    /**
     * 查询配送细目单
     *
     * @return
     * @throws Exception
     */
    public String findDistributionDetails() {
        if (pageNum == 1) {
            page.setPageNo(1);
        }
        setCheckedId(distributionId);//高亮显示所用

        // 配送细目单集合
        distributionDetailInfo.setDistributionId(distributionId);
        try {
            distributionDetailInfoList = distributionDetailInfoService.findDistributionDetailInfoList(page, distributionDetailInfo);
            // 根据ID，查找配送单信息
            distributionInfo = distributionInfoService.findDistributionById(distributionId);
        } catch (Exception e) {
            logger.error("查询配送细目单失败，", e);
            return ERROR;
        }
        setWarehouseMap();// 仓库信息
        setDistributionInfoMap();// 送达信息

        if (null != showType && "checkList".equals(showType)) {// 审核列表查看
            return "checkList";
        }
        return SUCCESS;
    }

    /**
     * 配送单列表:添加配送单
     *
     * @throws Exception
     */
    public String addDistributionInfo() {

        try {
            // 处理配送单
            distributionInfo = distributionInfoService.updateDistributionByDetail(
                    distributionInfo, distributionDetailInfoList);
            distributionInfo.setLogisticsDate(new Date());// 配送日期(默认为当天)
            distributionInfo.setCreateUser(super.getLoginUserId());//创建人
            distributionInfo.setCreateUserName(super.getLoginUserName());//创建人姓名
            distributionInfo.setIsDeliveries(DistributionInfostatus.UNDELIVERIES.getStatus());

            distributionInfo.setDistributionNo(BillCodeUtils.getBillCode(BillPrefix.DISPATCHING_PREFIX));// 生成配送单号
        } catch (Exception e) {
            logger.error("配送单列表:添加配送单失败，", e);
            return ERROR;
        }

        // 保存配送单
        try {
            distributionDetailInfoService.saveDistributionDetailList(
                    distributionDetailInfoList, distributionInfo);
            rtnMessage = "配送单保存成功!";
        } catch (Exception e) {
            rtnMessage = "配送单保存失败!";
            e.printStackTrace();
            return ERROR;
        }
        setWarehouseMap();// 仓库信息
        setDistributionId(distributionInfo.getDistributionId());//设置配送单ID，供查询细目单用
        return findDistributionDetails();
    }

    /**
     * 配送单列表:修改配送单
     *
     * @throws Exception
     */
    public String editDistributionInfo() {

        LogisticAndDistributionInfoVO logisticAndDistributionInfoVO = null;
        Map<Long, List<DistributionDetailInfo>> distributionInfoMap = null;

        // 保存配送单
        try {
            // 处理配送单
            distributionInfo = distributionInfoService.updateDistributionByDetail(distributionInfo, distributionDetailInfoList);
            distributionInfo.setCreateDate(new Date());// 创建日期(修改日期)
            distributionInfo.setCreateUser(super.getLoginUserId());//修改人
            distributionInfo.setCreateUserName(super.getLoginUserName());//修改人姓名
            distributionInfo.setIsDeliveries(DistributionInfostatus.UNDELIVERIES.getStatus());//是否送达

            if (StringUtils.isBlank(distributionInfo.getBillNo())) {
                throw new ServiceException("出库单号为空,配送单修改失败!");
            }

            StockOut stockOut = stockOutService.findStockOutByNo(distributionInfo.getBillNo());

            if (stockOut == null || stockOut.getType() == null) {
                throw new ServiceException("出库单不存在或出库类型有误,配送单修改失败!");
            }

            distributionDetailInfoService.editDistributionDetailList(distributionDetailInfoList, distributionInfo);
            rtnMessage = "配送单修改成功!";

            logisticAndDistributionInfoVO = new LogisticAndDistributionInfoVO();
            logisticAndDistributionInfoVO.setOrderCode(distributionInfo.getOrderNo());
            logisticAndDistributionInfoVO.setLogisticsCode(distributionInfo.getLogisticsCompany());
            logisticAndDistributionInfoVO.setLogisticsName(logisticsMap.get(distributionInfo.getLogisticsCompany()));
            logisticAndDistributionInfoVO.setLogisticsOrderNo(distributionInfo.getLogisticsNo());
            logisticAndDistributionInfoVO.setDistributionId(distributionInfo.getDistributionId());
            logisticAndDistributionInfoVO.setIncludeMedicineFlag(isDrugTmp);
            distributionInfoMap = new HashMap<Long, List<DistributionDetailInfo>>();
            distributionInfoMap.put(distributionInfo.getDistributionId(), distributionDetailInfoList);
            logisticAndDistributionInfoVO.setDistributionInfoMap(distributionInfoMap);

            if (StringUtils.isNotBlank(distributionInfo.getLogisticsNo())) {
                orderRemoteService.getLogisticNumber(logisticAndDistributionInfoVO, stockOut.getType());
            }

        } catch (Exception e) {
            rtnMessage = "配送单修改失败!";
            logger.error("配送单修改失败，", e);
        }
        setWarehouseMap();// 仓库信息
        distributionId = distributionInfo.getDistributionId();//返回配送细目单
        return findDistributionDetails();
    }

	/*
     * 以下为set和get方法
	 */

    public Long getDistributionId() {
        return distributionId;
    }

    public void setDistributionId(Long distributionId) {
        this.distributionId = distributionId;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public DistributionDetailInfo getDistributionDetailInfo() {
        return distributionDetailInfo;
    }

    public void setDistributionDetailInfo(
            DistributionDetailInfo distributionDetailInfo) {
        this.distributionDetailInfo = distributionDetailInfo;
    }

    public DistributionInfo getDistributionInfo() {
        return distributionInfo;
    }

    public void setDistributionlInfo(DistributionInfo distributionInfo) {
        this.distributionInfo = distributionInfo;
    }

    public String getRtnMessage() {
        return rtnMessage;
    }

    public void setRtnMessage(String rtnMessage) {
        this.rtnMessage = rtnMessage;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public List<DistributionDetailInfo> getDistributionDetailInfoList() {
        return distributionDetailInfoList;
    }

    public void setDistributionDetailInfoList(
            List<DistributionDetailInfo> distributionDetailInfoList) {
        this.distributionDetailInfoList = distributionDetailInfoList;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public void setDistributionInfo(DistributionInfo distributionInfo) {
        this.distributionInfo = distributionInfo;
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean getIsDrugTmp() {
        return isDrugTmp;
    }

    public void setIsDrugTmp(boolean isDrugTmp) {
        this.isDrugTmp = isDrugTmp;
    }


}
