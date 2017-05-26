package com.kmzyc.supplier.service;

import com.kmzyc.commons.exception.ServiceException;
import com.km.framework.page.Pagination;
import com.kmzyc.supplier.enums.ActivitySupplierStatus;
import com.kmzyc.supplier.vo.ActivityInfoVo;
import com.kmzyc.supplier.vo.ActivitySkuVo;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.vobject.ActivityInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 功能：活动服务
 *
 * @author Zhoujiwei
 * @since 2016/3/18 9:46
 */
public interface ActivityService {

    /**
     * 获取当前活动是否已经活动终止
     *
     * @param activityId    活动id
     * @throws ServiceException
     */
    boolean isStop(Long activityId) throws ServiceException;

    /**
     * 获取活动列表
     *
     * @param type              活动与商家类型关系
     * @param activityParam     活动查询参数
     * @param supplierId        供应商id
     * @param page              分页信息
     * @return
     */
    Pagination getActivityList(ActivitySupplierStatus type, ActivityInfoVo activityParam, Long supplierId, Pagination page)
            throws ServiceException;

    /**
     * 获取活动信息
     *
     * @param activityId    活动id
     * @return
     * @throws ServiceException
     */
    ActivityInfo getActivityById(String activityId) throws ServiceException;

    /**
     * 检查报名权限
     * @param activity      活动信息
     * @param supplierId    供应商id
     * @param activityId    活动id
     * @param isUpdate      true修改时检查/false新增时检查
     *
     * @return
     * @throws ServiceException
     */
    ResultMessage checkAuthority(ActivityInfo activity, Long supplierId, String activityId, Boolean isUpdate)
            throws ServiceException;

    /**
     * 检查是否可以追加推广
     * @param activity      活动信息
     * @param activityId    活动id
     *
     * @return
     * @throws ServiceException
     */
    ResultMessage checkActivityAppend(ActivityInfo activity, String activityId)
            throws ServiceException;

    /**
     * 检查sku
     *
     * @param activity              活动信息
     * @param activityId            活动id
     * @param supplierId
     *@param newActivitySkuList    新增sku列表
     * @param isUpdate              true修改时检查/false新增时检查
     * @param resultMessage    @return
     * @throws ServiceException
     */
    ResultMessage checkSku(ActivityInfo activity, String activityId, Long supplierId, List<ActivitySkuVo> newActivitySkuList,
                           Boolean isUpdate, List<ActivitySkuVo> exitsActivitySkuList, ResultMessage resultMessage)
            throws ServiceException;

    /**
     * 检查供应商数量和sku数量上限
     *
     * @param activityId    活动id
     * @param isCheck       检查报名时true/报名时false
     * @param resultMessage
     * @return
     * @throws ServiceException
     */
    ResultMessage checkSupplier(String activityId, boolean isCheck, ResultMessage resultMessage)
            throws ServiceException;

    /**
     * 当新增失败抛异常时，对活动的供应商数量增一
     *
     * @param activityId    活动id
     */
    void incrSupplierMax(String activityId);

    /**
     * 撤销报名
     *
     * @param activityId            活动id
     * @param supplierId            供应商id
     * @param loginUserId           使用用户
     * @param loginUserName
     * @throws ServiceException
     */
    void cancelActivityEntry(String activityId, Long supplierId, Long loginUserId, String loginUserName)
            throws ServiceException;

    /**
     * 检查是否符合撤销报名要求
     * <note>
     *  1.活动还未开始，
     *  2.已经提交的报名才开撤销
     * </note>
     *
     * @param activity      活动信息
     * @param activityId    活动id
     * @param supplierId    商家id
     * @return
     * @throws ServiceException
     */
    ResultMessage checkMatchCancel(ActivityInfo activity, String activityId, Long supplierId) throws ServiceException;

    /**
     * 首次缴费付款
     *
     * @param activityId    活动id
     * @param loginUserName 登录名
     * @param supplierId    商家id
     * @throws ServiceException
     */
    ResultMessage pay(String activityId, String loginUserName, Long supplierId)
            throws ServiceException;

    /**
     * 保存追加推广到缓存
     *
     * @param activityId        活动id
     * @param supplierId        供应商id
     * @param loginUserId       操作者
     * @param activitySkuList   追加推广sku
     * @return
     * @throws ServiceException
     */
    ResultMessage saveAppendSku(String activityId, Long supplierId, Long loginUserId,
                                List<ActivitySkuVo> activitySkuList) throws ServiceException;

    /**
     * 检查追加推广时sku是否符合要求,并处理
     *
     * @param activitySkuList   追加推广sku列表
     * @param activityId        活动id
     * @param loginUserId
     * @return
     * @throws ServiceException
     */
    ResultMessage checkAndHandlerAppendSku(List<ActivitySkuVo> activitySkuList, String activityId,
                                           Long loginUserId) throws ServiceException;

    /**
     * 获取追加推广时的总费用
     *
     * @param supplierId    供应商id
     * @param activityId    活动id
     * @return
     */
    BigDecimal getAppendTotalPrice(Long supplierId, String activityId) throws ServiceException;

    /**
     * 追加推广支付
     *
     * @param activityId    活动id
     * @param loginUserName 登录用户名
     * @param supplierId    商家id
     * @param loginUserId   登录id
     * @return
     * @throws ServiceException
     */
    ResultMessage payAppend(String activityId, String loginUserName, Long supplierId,
                            Long loginUserId) throws ServiceException;
    
    /**
     * 查询充值流水
     * @param accountTransactionId
     * @return
     * @throws Exception
     */
    String bnesAcctTransactionById(Integer accountTransactionId) throws ServiceException;
}
