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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.user.remote.service.TrationListRemoteService;
import com.pltfm.activity.dao.ActivityPaymentDAO;
import com.pltfm.activity.dao.ActivityPaymentDetailDAO;
import com.pltfm.activity.dao.ActivityPaymentStatementDAO;
import com.pltfm.activity.service.ActivityPaymentService;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.enums.ActivityPayStatementType;
import com.pltfm.app.enums.ActivityPaymentStatus;
import com.pltfm.app.enums.ActivityPaymentType;
import com.pltfm.app.service.ActivitySkuSalesService;
import com.pltfm.app.util.CodeUtils;
import com.pltfm.app.util.TransactionTypeEnum;
import com.pltfm.app.vobject.ActivityInfo;
import com.pltfm.app.vobject.ActivityPayment;
import com.pltfm.app.vobject.ActivityPaymentDetail;
import com.pltfm.app.vobject.ActivityPaymentExample;
import com.pltfm.app.vobject.ActivityPaymentStatement;
import com.pltfm.app.vobject.ActivitySku;
import com.pltfm.app.vobject.ActivitySupplierEntry;

@Service("activityPaymentService")
public class ActivityPaymentServiceImpl implements ActivityPaymentService {
	
	protected Logger logger = LoggerFactory.getLogger(ActivityPaymentServiceImpl.class);

	@Resource
	private ActivityPaymentDAO activityPaymentDao;
	
	@Resource
	private ActivityPaymentDetailDAO activityPaymentDetailDao;
	
	@Resource
	private ActivityPaymentStatementDAO activityPaymentStatementDao;
	
	@Resource
	private ActivitySkuSalesService activitySkuSalesService;

    @Resource
    private TrationListRemoteService trationListRemoteService;
	
	//暂时只允许一个活动退款一次
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public void saveActivityPaymentForRefundment(ActivityPayment activityPayment) throws ServiceException {
		try{
			Long activityPaymentId = activityPaymentDao.saveActivityPaymentForRefundment(activityPayment);
			List<ActivityPaymentDetail> detailList = activityPayment.getActivityPaymentDetailList();
			for(ActivityPaymentDetail detail : detailList){
				detail.setActivityPaymentId(activityPaymentId);
			}
			activityPaymentDetailDao.batchSaveActivityPaymentForReturn(detailList);

		} catch (Exception e) {
			logger.error("保存退款单失败：", e.getMessage(), e);
		    throw new ServiceException(e);
		}
	}

	@Override
	public ResultMessage refundmentValidity(Long supplierEntryId,Integer activityPaymentType,Long createUser)
            throws ServiceException {
		ResultMessage message = new ResultMessage();
		List<ActivityPaymentDetail> activityPaymentDetailList = new ArrayList<ActivityPaymentDetail>();
		ActivityPaymentDetail activityPaymentDetail = null;
		List<Long> skuIdList = new ArrayList<Long>();
		
		//调用此方法的action中redis分布式锁 锁（supplierEntryId）
		try{
			ActivityInfo activityInfo = activityPaymentDao.getPaymentBySupplierEntryId(supplierEntryId);
			if(activityInfo==null){
				logger.warn("报名ID为："+supplierEntryId+"的活动数据错误!");
				message.setIsSuccess(false);
				message.setMessage("报名ID为：" + supplierEntryId + "的活动数据错误!");
				return message;
			}
			ActivitySupplierEntry activitySupplierEntry = activityInfo.getActivitySupplierEntryList().get(0);
			if(activitySupplierEntry==null){
				logger.warn("报名ID为："+supplierEntryId+"的活动报名数据错误!");
				message.setIsSuccess(false);
				message.setMessage("报名ID为：" + supplierEntryId + "的活动报名数据错误!");
				return message;
			}
			List<ActivitySku> activitySkuList = activitySupplierEntry.getActivitySkuList();
			if(CollectionUtils.isEmpty(activitySkuList)){
				logger.warn("报名ID为："+supplierEntryId+"的活动报名商品数据错误!");
				message.setIsSuccess(false);
				message.setMessage("报名ID为：" + supplierEntryId + "的活动报名商品数据错误!");
				return message;
			}
			List<ActivityPayment> activityPaymentList = activitySupplierEntry.getActivityPaymentList();
			if(CollectionUtils.isEmpty(activityPaymentList)){
				logger.warn("报名ID为："+supplierEntryId+"的活动报名款项数据错误!");
				message.setIsSuccess(false);
				message.setMessage("报名ID为：" + supplierEntryId + "的活动报名款项数据错误!");
				return message;
			}
			
			Map<Long,Integer> skuPreSaleQuantityMap = new HashMap<Long,Integer>();//sku预售数量Map（首次提交和追加提交加总）
			Map<Long,BigDecimal> skuActivityPriceMap = new HashMap<Long,BigDecimal>();//sku活动价Map
			Map<Long,BigDecimal> skuRateMap = new HashMap<Long,BigDecimal>();//sku返佣比例Map
			Map<Long,BigDecimal> skuTotalPriceMap = new HashMap<Long,BigDecimal>();//sku总费用Map
			for(ActivitySku activitySku : activitySkuList){
				skuIdList.add(activitySku.getProductSkuId());
				if(!skuPreSaleQuantityMap.containsKey(activitySku.getProductSkuId())){
					skuPreSaleQuantityMap.put(activitySku.getProductSkuId(), activitySku.getPreSaleQuantity());
				}else{
					skuPreSaleQuantityMap.put(activitySku.getProductSkuId(), skuPreSaleQuantityMap.get(activitySku.getProductSkuId())+activitySku.getPreSaleQuantity());
				}
				if(!skuTotalPriceMap.containsKey(activitySku.getProductSkuId())){
					skuTotalPriceMap.put(activitySku.getProductSkuId(), activitySku.getSkuTotalPrice());
				}else{
					skuTotalPriceMap.put(activitySku.getProductSkuId(), skuTotalPriceMap.get(activitySku.getProductSkuId()).add(activitySku.getSkuTotalPrice()));
				}
				if(!skuActivityPriceMap.containsKey(activitySku.getProductSkuId())){//相同SKU，追加的活动价和首次的SKU都是一样的
					skuActivityPriceMap.put(activitySku.getProductSkuId(), activitySku.getActivityPrice());
				}
				if(!skuRateMap.containsKey(activitySku.getProductSkuId())){//相同SKU，追加的返佣比例和首次的SKU都是一样的
					skuRateMap.put(activitySku.getProductSkuId(), activitySku.getCommissionRate());
				}
				
	//			Integer skuReturnNum = activitySku.getPreSaleQuantity()-activitySku.getSaleQuantity();
	//			skuTotalRefundment = skuTotalRefundment.add(new BigDecimal(skuReturnNum.toString()).multiply(activitySku.getActivityPrice()).multiply(activitySku.getCommissionRate()));
	//			
	//			activityPaymentDetail = new ActivityPaymentDetail();
	//			activityPaymentDetail.setProductSkuId(activitySku.getProductSkuId());
	//			activityPaymentDetail.setSkuTotalPrice(skuTotalRefundment);
	//			activityPaymentDetailList.add(activityPaymentDetail);
			}
			
			Map<Long,Integer> skuSaleQuantityMap = activitySkuSalesService.getActivitySkuSales(supplierEntryId, skuIdList);//调缓存接口获取SKU销量
			BigDecimal allSkuTotalSaleAmount = new BigDecimal("0");//所有SKU已经消费的总额
			BigDecimal singleSkuTotalSaleAmount = null;//单个SKU已经消费的总额
			for(Map.Entry<Long, Integer> entry : skuSaleQuantityMap.entrySet()){
                Long skuId = entry.getKey();
				singleSkuTotalSaleAmount = new BigDecimal(entry.getValue().intValue()).multiply(skuActivityPriceMap.get(skuId)).multiply(skuRateMap.get(skuId));
				allSkuTotalSaleAmount = allSkuTotalSaleAmount.add(singleSkuTotalSaleAmount);
				activityPaymentDetail = new ActivityPaymentDetail();
				activityPaymentDetail.setProductSkuId(skuId);
				activityPaymentDetail.setSkuTotalPrice(skuTotalPriceMap.get(skuId).subtract(singleSkuTotalSaleAmount));
				activityPaymentDetailList.add(activityPaymentDetail);
			}
			
			BigDecimal activityTotalPrice = activitySupplierEntry.getActivityTotalPrice();//正向缴费金额(活动总费用)
			BigDecimal skuTotalRefundment = activityTotalPrice.subtract(allSkuTotalSaleAmount);//应退金额
			if(skuTotalRefundment.compareTo(activityTotalPrice)>0){//应退金额 <= 活动总费用,大于则非法
				logger.warn("报名ID为："+supplierEntryId+"的退款金额非法，应退金额大于活动总费用，无法退款!");
				message.setIsSuccess(false);
				message.setMessage("报名ID为：" + supplierEntryId + "的退款金额非法，应退金额大于活动总费用，无法退款!");
				return message;
			}
			
			BigDecimal activityPaymentPrice = new BigDecimal("0");//缴费成功的总金额,款项表中，此活动报名已经支付的款项总额，理论上等于报名的费用总和
			BigDecimal activityRefundmentPrice = new BigDecimal("0");//已退费加总,款项表中，此活动报名已经退款的款项总额，暂时只支持一笔退款
			for(ActivityPayment activityPayment : activityPaymentList){
				if(activityPayment.getActivityPaymentType().equals(ActivityPaymentType.ADD_PAY.getType())
						|| (activityPayment.getActivityPaymentType().equals(ActivityPaymentType.FIRST_PAY.getType()) && activityPayment.getOnePayStatus()==1)){//首次有效缴款和追加缴款
					activityPaymentPrice = activityPaymentPrice.add(activityPayment.getTotalFunds());
				}else if(activityPayment.getActivityPaymentType().equals(ActivityPaymentType.ACTIVITY_STOP_REFUND.getType())
						|| activityPayment.getActivityPaymentType().equals(ActivityPaymentType.ACTIVITY_CANCLE_REFUND.getType())){//退款
					activityRefundmentPrice = activityRefundmentPrice.add(activityPayment.getTotalFunds());
				}else{
					
				}
			}
			
	
			if(activityTotalPrice.compareTo(activityPaymentPrice)!=0){// 活动总费用==缴费成功的总金额,不等于就需要查实
				logger.warn("报名ID为："+supplierEntryId+"的退款金额非法，活动总费用不等于缴费成功的总金额，无法退款!");
				message.setIsSuccess(false);
				message.setMessage("报名ID为：" + supplierEntryId + "的退款金额非法，活动总费用不等于缴费成功的总金额，无法退款!");
				return message;
			}
	//		if(skuTotalRefundment.compareTo(activityPaymentPrice)>0){// 应退金额 <= 缴费成功的总金额,大于则非法
	//			throw new ServiceException("报名ID为："+supplierEntryId+"的退款金额非法，无法退款!");
	//		}
			
			BigDecimal activityCanReturnPrice = skuTotalRefundment.subtract(activityRefundmentPrice);//还能退金额,应退金额-已退费总和=还能退金额
			if(skuTotalRefundment.compareTo(activityCanReturnPrice)>0){// 应退金额 <= 还能退金额,大于则非法
				logger.warn("报名ID为："+supplierEntryId+"的退款金额非法，应退金额大于还能退金额，无法退款!");
				message.setIsSuccess(false);
				message.setMessage("报名ID为：" + supplierEntryId + "的退款金额非法，应退金额大于还能退金额，无法退款!");
				return message;
			}
			
			ActivityPayment activityPaymentForRefundment = new ActivityPayment();
			activityPaymentForRefundment.setActivityId(activitySupplierEntry.getActivityId());
			activityPaymentForRefundment.setActivityPaymentCode(CodeUtils.getActivityPaymentCode(1));
			activityPaymentForRefundment.setActivityPaymentStatus(ActivityPaymentStatus.NOT_REFUND.getStatus());
			activityPaymentForRefundment.setActivityPaymentType(activityPaymentType);
			activityPaymentForRefundment.setCreateUser(createUser);
			activityPaymentForRefundment.setSupplierEntryId(supplierEntryId);
			activityPaymentForRefundment.setSupplierId(activitySupplierEntry.getSupplierId());
			activityPaymentForRefundment.setTotalFunds(skuTotalRefundment);
			activityPaymentForRefundment.setActivityPaymentDetailList(activityPaymentDetailList);
			message.setIsSuccess(true);
			message.setObject(activityPaymentForRefundment);
		} catch (Exception e){
			logger.error("退款验证不通过，", e.getMessage(), e);
			message.setIsSuccess(false);
			message.setMessage("退款验证不通过,请核对款项正确性!");
		}
		return message;
		//只有渠道推广类型活动才可以退费
		//首先根据计算应退金额（查询商家报名表、报名商品表）  :活动总费用-(已售数量*活动价*佣金<此活动报名所有SKU总和，包括追加>) 
		//判断 活动总费用==缴费成功的总金额（包括追加缴费）<缴费时应该判断>
		//应退金额 <= 活动总费用  && 应退金额 <= 缴费成功的总金额（包括追加缴费）
		//计算已退金额（查询活动款项表此商家报名退款类型的数据）：已退费加总
		//计算还能退金额：应退金额-已退费总和=还能退金额
		//注：暂时只允许一个活动退款一次，因此退款验证暂时没有太多限制，后续如果允许多次退款，这里需要修改验证逻辑
		//调用此方法的action中redis分布式锁 解锁（supplierEntryId）
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public ResultMessage activityEntryReturnByRemote(ActivityPayment payment,Long createUser)
            throws ServiceException {
		ResultMessage message = new ResultMessage();
		
		try{
			payment.setActivityPaymentStatus(ActivityPaymentStatus.REFUNDED.getStatus());
			Long activityPaymentId = activityPaymentDao.saveActivityPaymentForRefundment(payment);
			List<ActivityPaymentDetail> activityPaymentDetailList = payment.getActivityPaymentDetailList();
			for(ActivityPaymentDetail detail : activityPaymentDetailList){
				detail.setActivityPaymentId(activityPaymentId);
			}
			activityPaymentDetailDao.batchSaveActivityPaymentForReturn(activityPaymentDetailList);
//			ActivityPayment activityReturndment = activityPaymentDao.getRefundmentBySupplierEntryId(supplierEntryId);
			
			Integer resultCode = trationListRemoteService.orderTrationAccout(
                    activityPaymentDetailDao.selectUserBySupplierId(payment.getSupplierId()).getLoginAccount(),
                    payment.getTotalFunds().doubleValue(),
                    payment.getActivityPaymentCode(),
                    "活动退款,活动ID："+ payment.getActivityId(),
                    TransactionTypeEnum.TRANSACTION_TYPE_PROMOTION_REFUND.getType());
			
			if(resultCode!=1){
				logger.warn("报名ID为："+payment.getSupplierEntryId()+",调用退款接口，返回值为："+resultCode+"，退款失败!");
				message.setIsSuccess(false);
                message.setMessage("退款失败!");
				return message;
			}
			
			ActivityPaymentStatement activityPaymentStatement = new ActivityPaymentStatement();
			activityPaymentStatement.setActivityPaymentId(activityPaymentId);
			activityPaymentStatement.setOperator(createUser);
			activityPaymentStatement.setPrice(payment.getTotalFunds());
			activityPaymentStatement.setStatementCode(CodeUtils.getActivityPaymentCode(2));
			activityPaymentStatement.setStatementType(ActivityPayStatementType.REFUND_TYPE.getType());
			activityPaymentStatement.setRemark("活动结束退款!商家报名ID：" + payment.getSupplierEntryId());
			activityPaymentStatementDao.insertActivityPaymentStatement(activityPaymentStatement);
			
//			activityReturndment.setActivityPaymentStatus(ActivityPaymentStatus.REFUNDED.getStatus());
//			if(activityPaymentDao.updateById(activityReturndment)<1){
//				throw new ServiceException("更新退款单号："+activityReturndment.getActivityPaymentCode()+"，状态更新失败，退款失败!");
//			}
		} catch (Exception e) {
			logger.error("退款失败：", e.getMessage(), e);
		    throw new ServiceException(e);
		}
		
		message.setIsSuccess(true);
		return message;
	}

    @Override
    public ActivityPayment getLastValidFirstPayment(String activityId, Long supplierEntryId,
                                                    Long supplierId) throws ServiceException {
        ActivityPaymentExample example = new ActivityPaymentExample();
        ActivityPaymentExample.Criteria criteria = example.createCriteria();
        criteria.andActivityIdEqualTo(Long.parseLong(activityId));
        criteria.andSupplierIdEqualTo(supplierId);
        criteria.andActivityPaymentTypeEqualTo(ActivityPaymentType.FIRST_PAY.getType());
        criteria.andSupplierEntryIdEqualTo(supplierEntryId);
        criteria.andOnePayStatusEqualTo((short) 1);

        try {
            return activityPaymentDao.selectByExample(example);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public Long saveRefundActivityPayment(Long activityId, Long supplierId,
                                          Long supplierEntryId, Long loginUserId,
                                          Long activityPaymentId, BigDecimal total ,String paymentCode)
            throws ServiceException {
        ActivityPayment activityPayment = new ActivityPayment();
        activityPayment.setActivityPaymentCode(paymentCode);
        activityPayment.setActivityId(activityId);
        activityPayment.setSupplierEntryId(supplierEntryId);
        activityPayment.setSupplierId(supplierId);
        activityPayment.setActivityPaymentType(ActivityPaymentType.ENTRY_NOT_THROUGH_AUDIT_REFUND.getType());
        activityPayment.setParentId(activityPaymentId);
        if (total.intValue() == 0) {//0时为已退款，非0时为待退款
            activityPayment.setActivityPaymentStatus(ActivityPaymentStatus.REFUNDED.getStatus());
        } else {
            activityPayment.setActivityPaymentStatus(ActivityPaymentStatus.NOT_REFUND.getStatus());
        }
        activityPayment.setTotalFunds(total);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        activityPayment.setCreateTime(now);
        activityPayment.setModifyTime(now);
        activityPayment.setCreateUser(loginUserId);
        activityPayment.setModifUser(loginUserId);
//        activityPayment.setOnePayStatus(0);

        try {
            return activityPaymentDao.insertSelective(activityPayment);
        } catch (SQLException e) {
            logger.error("新增退款失败，失败信息：", e);
            throw new ServiceException(e);
        }
    }
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int updateRefundPayment2Refunded(Long activityPaymentId) throws ServiceException {
        ActivityPayment activityPayment = new ActivityPayment();
        activityPayment.setActivityPaymentId(activityPaymentId);
        activityPayment.setActivityPaymentStatus(ActivityPaymentStatus.REFUNDED.getStatus());

        try {
            return activityPaymentDao.updateById(activityPayment);
        } catch (SQLException e) {
            logger.error("将退款修改为已退款失败，失败信息：", e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int invalidLastFirstPayment(Long loginUserId,Long activityPaymentId) throws ServiceException {
        ActivityPayment activityPayment = new ActivityPayment();
        activityPayment.setActivityPaymentId(activityPaymentId);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        activityPayment.setModifyTime(now);
        activityPayment.setModifUser(loginUserId);
        activityPayment.setOnePayStatus(0);

        try {
            return activityPaymentDao.updateById(activityPayment);
        } catch (SQLException e) {
            logger.error("将本次商家报名活动id的付款设为无效失败，失败信息：", e);
            throw new ServiceException(e);
        }
    }

}