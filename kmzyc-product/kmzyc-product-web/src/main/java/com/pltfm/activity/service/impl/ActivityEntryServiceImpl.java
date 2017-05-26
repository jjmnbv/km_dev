package com.pltfm.activity.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.promotion.app.vobject.PromotionProduct;
import com.kmzyc.promotion.remote.service.PromotionRemoteService;
import com.kmzyc.supplier.model.User;
import com.kmzyc.user.remote.service.TrationListRemoteService;
import com.pltfm.activity.dao.ActivitySupplierEntryDAO;
import com.pltfm.activity.service.ActivityEntryService;
import com.pltfm.activity.service.ActivityPaymentDetailService;
import com.pltfm.activity.service.ActivityPaymentService;
import com.pltfm.activity.service.ActivitySkuService;
import com.pltfm.app.enums.ActivityChargeType;
import com.pltfm.app.enums.ActivityPaymentStatus;
import com.pltfm.app.enums.ActivityType;
import com.pltfm.app.enums.PromotionTypeEnums;
import com.pltfm.app.util.CodeUtils;
import com.pltfm.app.util.TransactionTypeEnum;
import com.pltfm.app.vobject.ActivityPayment;
import com.pltfm.app.vobject.ActivityPaymentDetail;
import com.pltfm.app.vobject.ActivitySku;
import com.pltfm.app.vobject.ActivitySupplierEntry;

@Service("activityEntryService")
public class ActivityEntryServiceImpl implements ActivityEntryService {

    @Resource
    private ActivitySupplierEntryDAO activitySupplierEntryDao;

    @Resource
    private ActivityPaymentService activityPaymentService;

    @Resource
    private ActivityPaymentDetailService activityPaymentDetailService;

    @Resource
    private ActivitySkuService activitySkuService;

    private Logger logger = LoggerFactory.getLogger(ActivityPaymentServiceImpl.class);

    @Resource
    private PromotionRemoteService promotionRemoteService;

    @Resource
    private TrationListRemoteService trationListRemoteService;

    @Override
    public void searchPage(Page page, ActivitySupplierEntry activitySupplierEntry) throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0) pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;
        Map map = new HashMap();

        // 活动Id
        if (activitySupplierEntry.getActivityId() != null) {
            map.put("activityId", activitySupplierEntry.getActivityId());
        }
        // 公司名称
        if (StringUtils.isNotEmpty(activitySupplierEntry.getCompanyShowName())) {
            map.put("companyShowName", activitySupplierEntry.getCompanyShowName());
        }
        // 店铺名称
        if (StringUtils.isNotEmpty(activitySupplierEntry.getShopName())) {
            map.put("shopName", activitySupplierEntry.getShopName());
        }
        // 查询使用报名初始时间
        if (activitySupplierEntry.getEntryStartTime() != null) {
            map.put("entryStartTime", activitySupplierEntry.getEntryStartTime());
        }
        // 查询使用报名末时间
        if (activitySupplierEntry.getEntryEndTime() != null) {
            map.put("entryEndTime", activitySupplierEntry.getEntryEndTime());
        }
        // 状态
        if (activitySupplierEntry.getAuditStatus()!=null) {
            map.put("auditStatus", activitySupplierEntry.getAuditStatus());
        }
        // 活动商家类型
        if (activitySupplierEntry.getActivitySupplierType() != null) {
            map.put("activitySupplierType", activitySupplierEntry.getActivitySupplierType());
        }
        // 查询使用创建初始时间/创建末时间
        if (activitySupplierEntry.getCreateStartTime() != null
                && activitySupplierEntry.getCreateEndTime() != null) {
            map.put("createStartTime", activitySupplierEntry.getCreateStartTime());
            map.put("createEndTime", activitySupplierEntry.getCreateEndTime());
        }
        if (StringUtils.isNotEmpty(activitySupplierEntry.getLoginAccount())) {
            map.put("loginAccount", activitySupplierEntry.getLoginAccount());
        }

        try {
            int count = activitySupplierEntryDao.countByEntryExample(map);
            List<ActivitySupplierEntry> list = activitySupplierEntryDao.selectByEntryExamples(map, skip, max);
            page.setDataList(list);
            page.setRecordCount(count);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ActivitySupplierEntry activityEntryDetail(ActivitySupplierEntry activitySupplierEntry)
            throws ServiceException {
        Map map = new HashMap();
        if (activitySupplierEntry.getSupplierEntryId() != null) {
            map.put("supplierEntryId", activitySupplierEntry.getSupplierEntryId());
        }

        try {
            return activitySupplierEntryDao.activityEntryDetail(map);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public String activityAuditEntryNoPass(ActivitySupplierEntry activitySupplierEntry,
            String[] activitySkuIds) throws ServiceException {
        try {
            List<ActivitySku> list = new ArrayList<ActivitySku>();
            List<ActivitySupplierEntry> verifyStauts;
            verifyStauts = verifyStatus(activitySupplierEntry);
            // 活动收费类型 1：免费 2：固定收费 3：单品收费 4：返佣
            String chargeType = verifyStauts.get(0).getActivityInfo().getChargeType().toString();
            // 活动开始时间
            //Timestamp activityStartTime = verifyStauts.get(0).getActivityInfo().getActivityStartTime();
            // 当前时间
            //Timestamp time = new Timestamp(System.currentTimeMillis());
            // 审核状态 0：未审核 1：已审核 2：审核不通过
            String auditStatus = verifyStauts.get(0).getAuditStatus().toString();
            // 款项状态1：待缴费 2：已缴费 3：待退款 4：已退款
            String paymentStatus =
                    verifyStauts.get(0).getActivityPayment().getActivityPaymentStatus().toString();
            /*
                         为不影响活动开始自动退款   先开放活动开始允许'审核不通过'操作
            if (time.getTime() > activityStartTime.getTime()) {// 当前时间大于活动时间
                return "活动已开始，不能进行审核";
            }
            */
            // 审核不通过
            if (activitySupplierEntry.getAuditStatus().toString().equals(auditStatus)) {// 将要审核状态等于原审核状态
                return "请检查报名状态！";
            }
            activitySupplierEntryDao.activityAuditEntryById(activitySupplierEntry);
            if (ArrayUtils.isNotEmpty(activitySkuIds)) {
                ActivitySku activitySku = null;
                for (int j = 0; j < activitySkuIds.length; j++) {
                    activitySku = new ActivitySku();
                    activitySku.setActivitySkuId(Long.parseLong(activitySkuIds[j]));
                    activitySku.setAuditTime(activitySupplierEntry.getAuditTime());
                    activitySku.setAuditUser(activitySupplierEntry.getAuditUser());
                    activitySku.setAuditStatus(activitySupplierEntry.getAuditStatus());
                    list.add(activitySku);
                }
                activitySupplierEntryDao.batchUpdateDatas("ACTIVITY_SKU.markNoPassActivityProduct", list);
            }
            // 活动收费类型不为免费 调用接口退款操作 将有效的首次缴款记录设为无效、新增一条退款记录、
            if (!(chargeType.equals(ActivityChargeType.FREE.getType().toString()))) {
                if ((paymentStatus.equals(ActivityPaymentStatus.REFUNDED.getStatus().toString()))) {// 款项状态为已退款
                    // 1.获取最后一次有效的首次缴费
                    ActivityPayment activityPayment =
                            activityPaymentService.getLastValidFirstPayment(activitySupplierEntry
                                    .getActivityId().toString(), activitySupplierEntry
                                    .getSupplierEntryId(), activitySupplierEntry.getSupplierId());
                    if (activityPayment == null) {
                        logger.error("获取报名id为" + activitySupplierEntry.getSupplierEntryId()
                                + "最后一次有效的首次缴费失败");
                        throw new ServiceException("活动报名审核不通过失败");
                    }
                    // 2.将之前有效的首次缴费变为无效
                    activityPaymentService.invalidLastFirstPayment(activityPayment.getModifUser(),
                            activityPayment.getActivityPaymentId());
                    return "审核不通过！";
                } else {
                    // 1.获取最后一次有效的首次缴费
                    ActivityPayment activityPayment = activityPaymentService.getLastValidFirstPayment(activitySupplierEntry
                                    .getActivityId().toString(), activitySupplierEntry
                                    .getSupplierEntryId(), activitySupplierEntry.getSupplierId());
                    if (activityPayment == null) {
                        logger.error("获取报名id为" + activitySupplierEntry.getSupplierEntryId()
                                + "最后一次有效的首次缴费失败");
                        throw new ServiceException("活动报名审核不通过失败");
                    }
                    BigDecimal totalFunds = activityPayment.getTotalFunds();
                    // 2.将之前有效的首次缴费变为无效
                    activityPaymentService.invalidLastFirstPayment(activityPayment.getModifUser(),
                            activityPayment.getActivityPaymentId());

                    // 上一次缴费是否为已缴费
                    if (activityPayment.getActivityPaymentStatus().intValue() != ActivityPaymentStatus.PAYED
                            .getStatus().intValue()) {
                        return "审核不通过！";
                    }

                    // 3.新增一条退款记录
                    String paymentCode = CodeUtils.getActivityPaymentCode(1);
                    Long refundPaymentId = activityPaymentService.saveRefundActivityPayment(
                            activitySupplierEntry.getActivityId(),
                            activitySupplierEntry.getSupplierId(),
                            activitySupplierEntry.getSupplierEntryId(),
                            activityPayment.getModifUser(),
                            activityPayment.getActivityPaymentId(), totalFunds,
                            paymentCode);

                    // 4.获取上次有效首次缴费的详细信息
                    List<ActivityPaymentDetail> paymentDetailList =
                            activityPaymentDetailService.getPaymentDetails(activityPayment.getActivityPaymentId());

                    // 5.批量保存退款信息
                    activityPaymentDetailService.batchInsertRefundPaymentDetail(refundPaymentId, paymentDetailList);

                    // 如果上次缴费不为0，需要退款
                    if (totalFunds.doubleValue() != 0) {

                        // 通过缴款创建id（用户id），查询账号
                        User user = activityPaymentDetailService.findByUserIdObj(activityPayment.getCreateUser());
                        if (user == null) {
                            logger.error("根据缴费创建id查询账号名称失败！");
                            throw new ServiceException("活动报名审核不通过失败");
                        }
                        String loginUserName = user.getLoginAccount();
                        Integer resultCode = trationListRemoteService.orderTrationAccout(loginUserName,
                                        totalFunds.doubleValue(), paymentCode, "活动退款,活动ID："
                                                + activitySupplierEntry.getActivityId(),
                                        TransactionTypeEnum.TRANSACTION_TYPE_PROMOTION_REFUND
                                                .getType());
                        logger.info("审核不通过退款，" + "商家用户名" + loginUserName + "，活动"
                                + activitySupplierEntry.getActivityId() + ",费用" + totalFunds
                                + "，返回状态" + resultCode);
                        if (resultCode != 1) {
                            logger.error("审核退款失败，商家用户名{}，活动{},费用{}，返回状态{}.", new Object[] {
                                    loginUserName, activitySupplierEntry.getActivityId(),
                                    totalFunds, resultCode});
                            throw new ServiceException("活动报名审核不通过失败");
                        }
                        // 将退款改为已退款
                        activityPaymentService.updateRefundPayment2Refunded(refundPaymentId);
                    }
                }
            }else {
             // 1.获取最后一次有效的首次缴费
                ActivityPayment activityPayment =
                        activityPaymentService.getLastValidFirstPayment(activitySupplierEntry
                                .getActivityId().toString(), activitySupplierEntry
                                .getSupplierEntryId(), activitySupplierEntry.getSupplierId());
                if (activityPayment == null) {
                    logger.error("获取报名id为" + activitySupplierEntry.getSupplierEntryId()
                            + "最后一次有效的首次缴费失败");
                    throw new ServiceException("活动报名审核不通过失败");
                }
                // 2.将之前有效的首次缴费变为无效
                activityPaymentService.invalidLastFirstPayment(activityPayment.getModifUser(),
                        activityPayment.getActivityPaymentId());
                return "审核不通过！";
            }
        } catch (Exception e) {
            logger.error("活动报名审核不通过失败", e);
            throw new ServiceException(e);
        }
        return "审核不通过！";
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public String activityAuditEntryPass(ActivitySupplierEntry activitySupplierEntry,
            List<ActivitySku> activitySkuList) throws ServiceException {
        try {
            //List<ActivitySku> list = new ArrayList<ActivitySku>();
            List<ActivitySupplierEntry> verifyStauts;
            verifyStauts = verifyStatus(activitySupplierEntry);
            // 活动收费类型 1：免费 2：固定收费 3：单品收费 4：返佣
            String chargeType = verifyStauts.get(0).getActivityInfo().getChargeType().toString();
            // 活动开始时间
            Timestamp activityStartTime =
                    verifyStauts.get(0).getActivityInfo().getActivityStartTime();
            // 当前时间
            Timestamp time = new Timestamp(System.currentTimeMillis());
            // 审核状态 0：未审核 1：已审核 2：审核不通过
            String auditStatus = verifyStauts.get(0).getAuditStatus().toString();
            // 款项状态1：待缴费 2：已缴费 3：待退款 4：已退款
            String paymentStatus =
                    verifyStauts.get(0).getActivityPayment().getActivityPaymentStatus().toString();
            if (time.getTime() > activityStartTime.getTime()) {// 当前时间大于活动时间
                return "活动已开始，不能进行审核！";
            }
            //渠道活动审核时，判断渠道报名产品是否在其他活动中审核通过或进行中
            if (ActivityType.CHANNL_TYPE.getType().toString().equals(activitySupplierEntry.getActivityType().toString())) {
                List<String> unfinishedSkuList = activitySkuService.getSkuInUnfinishedActivity(activitySkuList,activitySupplierEntry);
                if (CollectionUtils.isNotEmpty(unfinishedSkuList)) {
                    return "当前活动中的商品【" + StringUtils.join(unfinishedSkuList, ",")
                            + "】已报名参加其他渠道推广活动，且此活动还未结束，审核失败！";
                }
            }
            // 审核通过需要验证是否免费、不免费是否缴费
            int promotionId = 0;
            if (!(chargeType.equals(ActivityChargeType.FREE.getType().toString()))) {// 活动收费类型不为免费
                if (!(paymentStatus.equals(ActivityPaymentStatus.PAYED.getStatus().toString()))) {// 款项状态不为已缴费
                    return "该商家尚未缴费，不能进行审核！";
                } else if (activitySupplierEntry.getAuditStatus().toString().equals(auditStatus)) {// 将要审核状态等于原审核状态
                    return "请检查报名状态！";
                }
                // 活动状态为渠道推广则创建促销活动
                if (ActivityType.CHANNL_TYPE.getType().toString().equals(activitySupplierEntry.getActivityType().toString()) && null != activitySkuList) {
                    promotionId = createPromotion(activitySkuList, activitySupplierEntry);
                    //创建促销成功，OK返回为促销活动id，修改审核通过activity_sku表促销id为返回活动促销id
                    if (promotionId != 1) {
                        activitySkuService.batchUpdateActivitySkuByEntryId(Long.valueOf(promotionId),activitySupplierEntry.getSupplierEntryId());
                    }
                }
                if (promotionId != 1) {
                    // 审核通过
                    activityPassUpdateAuditEntryById(activitySupplierEntry);
                } else {
                    logger.error("调用促销远程接口创建促销活动失败");
                    throw new ServiceException("活动报名审核通过失败");
                }
            } else {
                // 活动状态为渠道推广则创建促销活动 
                if (ActivityType.CHANNL_TYPE.getType().toString().equals(activitySupplierEntry.getActivityType().toString()) && null != activitySkuList) {
                    promotionId = createPromotion(activitySkuList, activitySupplierEntry);
                  //创建促销成功，OK返回为促销活动id，修改审核通过activity_sku表促销id为返回活动促销id
                    if (promotionId != 1) {
                        activitySkuService.batchUpdateActivitySkuByEntryId(Long.valueOf(promotionId),activitySupplierEntry.getSupplierEntryId());
                    }
                }
                if (promotionId != 1) {
                    // 审核通过
                    activityPassUpdateAuditEntryById(activitySupplierEntry);
                } else {
                    logger.error("调用促销远程接口创建促销活动失败");
                    throw new ServiceException("活动报名审核通过失败");
                }
            }
        } catch (Exception e) {
            logger.error("活动报名审核通过失败", e);
            throw new ServiceException(e);
        }
        return "审核通过！";
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public Integer createPromotion(List<ActivitySku> activitySkuList,
            ActivitySupplierEntry activitySupplierEntry) throws ServiceException {
        try {
            // 创建促销活动
            List<PromotionProduct> promotionProductlist = new ArrayList<PromotionProduct>();
            PromotionProduct promotionProduct = null;
            for (int j = 0; j < activitySkuList.size(); j++) {
                promotionProduct = new PromotionProduct();
                // 促销价格
                promotionProduct.setPrice(activitySkuList.get(j).getActivityPrice());
                // skuid
                promotionProduct.setProductSkuId(activitySkuList.get(j).getProductSkuId());
//                //预销数量
//                promotionProduct.setPromotionStock(activitySkuList.get(j).getPreSaleQuantity());
                promotionProduct.setStatus(2);
                promotionProductlist.add(promotionProduct);
            }
            PromotionInfo promotionInfo = new PromotionInfo();
            // 活动名称
            promotionInfo.setPromotionName(activitySupplierEntry.getActivityName());
            // 活动标题
            promotionInfo.setPromotionTitle(activitySupplierEntry.getActivityName());
            // 发布状态(已发布2)
            promotionInfo.setStatus(2);
            // (1)
            promotionInfo.setNature(1);
            // 活动商家类别（指定商家2）
            promotionInfo.setShopSort(2);
            // 活动商品筛选类型:1:'全场',2:'指定商品',3:'产品类目',4:'品牌'
            promotionInfo.setProductFilterType(2);
            // 商家id
            promotionInfo.setSupplierId(activitySupplierEntry.getSupplierId());
            // 活动类型（特价10）
            promotionInfo.setPromotionType(PromotionTypeEnums.SALE.getValue());
            //活动级别
            promotionInfo.setPromotionPriority(100);
            //活动开始时间
            promotionInfo.setStartTime(activitySupplierEntry.getCreateStartTime());
            //活动结束时间
            promotionInfo.setEndTime(activitySupplierEntry.getCreateEndTime());
            //活动价格或销售状态(活动库存卖光后操作类型 1：停止销售直到活动结束； 2：恢复原价销售；)
            promotionInfo.setSellUpType(2);
            // 远程调用促销系统
            return promotionRemoteService.createPromotion(promotionProductlist, promotionInfo);
        } catch (Exception e) {
            logger.error("调用促销远程接口创建促销活动失败", e);
            throw new ServiceException(e);
        }

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void activityPassUpdateAuditEntryById(ActivitySupplierEntry activitySupplierEntry)
            throws ServiceException {
        try {
            List<ActivitySku> list = new ArrayList<ActivitySku>();
            // 审核通过
            activitySupplierEntryDao.activityAuditEntryById(activitySupplierEntry);

            ActivitySku activitySku = new ActivitySku();
            activitySku.setSupplierEntryId(activitySupplierEntry.getSupplierEntryId());
            activitySku.setAuditTime(activitySupplierEntry.getAuditTime());
            activitySku.setAuditUser(activitySupplierEntry.getAuditUser());
            activitySku.setAuditStatus(activitySupplierEntry.getAuditStatus());
            list.add(activitySku);
            activitySupplierEntryDao.batchUpdateDatas("ACTIVITY_SKU.markPassActivityProduct", list);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List verifyStatus(ActivitySupplierEntry activitySupplierEntry) throws ServiceException {
        try {
            return activitySupplierEntryDao.verifyStatus(activitySupplierEntry.getSupplierEntryId());
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void queryInviteSupplierEntry(Page page, ActivitySupplierEntry activitySupplierEntry)
            throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0) pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;
        Map map = new HashMap();

        // 活动Id
        if (activitySupplierEntry.getActivityId()!=null) {
            map.put("activityId", activitySupplierEntry.getActivityId());
        }
        // 公司名称
        if (StringUtils.isNotEmpty(activitySupplierEntry.getCompanyShowName())) {
            map.put("companyShowName", activitySupplierEntry.getCompanyShowName());
        }
        // 店铺名称
        if (StringUtils.isNotEmpty(activitySupplierEntry.getShopName())) {
            map.put("shopName", activitySupplierEntry.getShopName());
        }
        // 活动商家类型
        if (activitySupplierEntry.getActivitySupplierType()!=null) {
            map.put("activitySupplierType", activitySupplierEntry.getActivitySupplierType());
        }
        // 查询使用创建初始时间/创建末时间
        if (activitySupplierEntry.getCreateStartTime() != null
                && activitySupplierEntry.getCreateEndTime() != null) {
            map.put("createStartTime", activitySupplierEntry.getCreateStartTime());
            map.put("createEndTime", activitySupplierEntry.getCreateEndTime());
        }
        if (StringUtils.isNotEmpty(activitySupplierEntry.getLoginAccount())) {
            map.put("loginAccount", activitySupplierEntry.getLoginAccount());
        }

        try {
            int count = activitySupplierEntryDao.queryInviteSupplierEntryCount(map);
            List<ActivitySupplierEntry> list = activitySupplierEntryDao.queryInviteSupplierEntry(map, skip, max);
            page.setDataList(list);
            page.setRecordCount(count);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void saveInviteSuppliers(List<ActivitySupplierEntry> list)
            throws ServiceException {
        try {
            int count = activitySupplierEntryDao.batchSaveActivityInviteSupplierEntry(list);
            if (count < 1) {
                throw new ServiceException("批量保存邀请商家失败");
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public PromotionInfo selectPromotion(Long promotionId) throws ServiceException {
        try {
            return promotionRemoteService.getPromotionById(promotionId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

}
