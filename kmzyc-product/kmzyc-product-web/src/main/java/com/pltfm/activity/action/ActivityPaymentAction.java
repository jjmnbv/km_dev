package com.pltfm.activity.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.pltfm.app.util.RedisLock;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.pltfm.activity.service.ActivityPaymentService;
import com.pltfm.app.action.BaseAction;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.enums.ActivityPaymentType;
import com.pltfm.app.vobject.ActivityPayment;

@Controller("activityPaymentAction")
@Scope(value = "prototype")
public class ActivityPaymentAction extends BaseAction {

    @Resource
    private RedisLock redisLock;

	@Resource
	private ActivityPaymentService activityPaymentService;
	
	private String supplierEntryId;
	
	private String activityId;
	
	private static final String PREFIX_LOCK = "/product_locks";

	/**
	 * 活动结束退款
	 * @return
	 */
	public String activityEndRefundment() {
		Map jsonMap = new HashMap();
		if(StringUtils.isBlank(activityId) || StringUtils.isBlank(supplierEntryId)){
			jsonMap.put("flag", false);
			jsonMap.put("msg", "参数错误,活动退款失败!");
			writeJson(jsonMap);
			return null;
		}
        String lockKey = PREFIX_LOCK + "/" + activityId + "/" + supplierEntryId;
        try {
            logger.info("线程[{}]开始去获取退款锁[{}].", new Object[]{Thread.currentThread().getName(), lockKey});
            if (!redisLock.tryLock(lockKey)) {
                logger.error("线程[{}],没有获取到退款锁{}.", new Object[] {Thread.currentThread().getName(), lockKey});
                jsonMap.put("flag", false);
                jsonMap.put("msg", "不可以重复退款!");
                writeJson(jsonMap);
                return null;
            }
            logger.info("线程{}已获取到退款锁{}.", new Object[] {Thread.currentThread().getName(),  lockKey});

			ResultMessage message = activityPaymentService.refundmentValidity(Long.valueOf(supplierEntryId),
                    ActivityPaymentType.ACTIVITY_STOP_REFUND.getType(),
                    super.getLoginUserId().longValue());
			if(!message.getIsSuccess()){
				jsonMap.put("flag", false);
				jsonMap.put("msg", message.getMessage());
				writeJson(jsonMap);
				return null;
			}
			
			message = activityPaymentService.activityEntryReturnByRemote((ActivityPayment) message.getObject(),
                    super.getLoginUserId().longValue());
			if(!message.getIsSuccess()){
				jsonMap.put("flag", false);
				jsonMap.put("msg", "活动退款失败!");
				writeJson(jsonMap);
				return null;
			}
			
			jsonMap.put("flag", true);
			jsonMap.put("msg", "活动退款成功!");
		} catch (Exception e) {
			logger.error("活动结束退款出错:", e.getMessage(), e);
			jsonMap.put("flag", false);
			jsonMap.put("msg", "活动退款失败!");
		} finally {
            redisLock.release(lockKey);
            logger.info("线程[{}]释放退款锁[{}].", new Object[] {Thread.currentThread().getName(), lockKey});
        }
		writeJson(jsonMap);
		return null;
	}
	
	/**
	 * 活动中止退款
	 * @return
	 */
	public String activityCancleRefundment() {
		Map jsonMap = new HashMap();
		if(StringUtils.isBlank(activityId) || StringUtils.isBlank(supplierEntryId)){
			jsonMap.put("flag", false);
			jsonMap.put("msg", "参数错误,活动退款失败!");
			writeJson(jsonMap);
			return null;
		}

        String lockKey = PREFIX_LOCK + "/" + activityId + "/" + supplierEntryId;
		try {
            //2.获取锁
            logger.info("线程[{}]开始去获取中止退款锁[{}].", new Object[]{Thread.currentThread().getName(), lockKey});
            if (!redisLock.tryLock(lockKey)) {
                logger.error("线程[{}],没有获取到中止退款锁{}.", new Object[] {Thread.currentThread().getName(), lockKey});
                jsonMap.put("flag", false);
                jsonMap.put("msg", "不可以重复退款!");
                writeJson(jsonMap);
                return null;
            }
            logger.info("线程{}已获取到中止退款锁{}.", new Object[] {Thread.currentThread().getName(),  lockKey});

			ResultMessage message = activityPaymentService.refundmentValidity(Long.valueOf(supplierEntryId),ActivityPaymentType.ACTIVITY_CANCLE_REFUND.getType(),super.getLoginUserId().longValue());
			if(!message.getIsSuccess()){
				jsonMap.put("flag", false);
				jsonMap.put("msg", message.getMessage());
				writeJson(jsonMap);
				return null;
			}
			
			message = activityPaymentService.activityEntryReturnByRemote((ActivityPayment) message.getObject(),super.getLoginUserId().longValue());
			if(!message.getIsSuccess()){
				jsonMap.put("flag", false);
				jsonMap.put("msg", "活动退款失败!");
				writeJson(jsonMap);
				return null;
			}
			
			jsonMap.put("flag", true);
			jsonMap.put("msg", "活动退款成功!");
		} catch (Exception e) {
			logger.error("活动中止退款出错:", e.getMessage(), e);
			jsonMap.put("flag", false);
			jsonMap.put("msg", "活动退款失败!");
		} finally {
            redisLock.release(lockKey);
            logger.info("线程[{}]释放中止退款锁[{}].", new Object[] {Thread.currentThread().getName(), lockKey});
		}
		writeJson(jsonMap);
		return null;
	}

	public String getSupplierEntryId() {
		return supplierEntryId;
	}

	public void setSupplierEntryId(String supplierEntryId) {
		this.supplierEntryId = supplierEntryId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

}
