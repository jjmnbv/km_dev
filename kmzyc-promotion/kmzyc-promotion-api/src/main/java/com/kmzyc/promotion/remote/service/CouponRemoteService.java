package com.kmzyc.promotion.remote.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.User;
import com.kmzyc.promotion.app.vobject.Coupon;
import com.kmzyc.promotion.app.vobject.CouponCanUse;
import com.kmzyc.promotion.app.vobject.CouponGrant;
import com.kmzyc.promotion.app.vobject.OrderVo;
import com.pltfm.app.dataobject.UserInfoDO;
import com.pltfm.app.entities.OrderPreferential;

public interface CouponRemoteService {

    /**
     * 修改优惠券规则状态
     * 
     * @param coupon
     * @param couponProduct
     * @param categoryIds
     * @return
     * @throws Exception
     */
    public int updateCouponStatus(Coupon coupon) throws Exception;

    /**
     * 查询优惠券规则
     * 
     * @param coupon
     * @return
     * @throws Exception
     */
    List<Coupon> selectEffectiveCoupon(Coupon coupon) throws Exception;

    /**
     * 根据客户Id，查询优惠券发放信息
     * 
     * @return
     */
    public List<CouponGrant> SelectCouponGrantList(List<Integer> customId) throws Exception;

    /**
     * 
     * 发放使用，将conpongrant的状态改为以发放；优惠券状态改变相关操作 以下类型通过此接口发放：注册类型发放、积分兑换发放、手工发放
     * 优惠券状态改变相关操作：冻结、解冻、作废、订单支付使用
     */
    public int changeCustomGrantToGive(UserInfoDO userman, Long couponId, Long grant_man,
            Long grant_type, String relatedCode, Long grantId) throws Exception;

    /**
     * 
     * 批量发放，将conpongrant的状态改为以发放，目前暂未使用
     */
    public int changeCustomGrantToGive(List<UserInfoDO> userList, Long couponId, Long grant_man,
            Long grant_type, String relatedCode) throws Exception;

    /**
     * 在不同类型下,符合规则的有效的，所有的couponList, 传参为会员注册时间
     * 
     * @return
     */
    public List<Coupon> selectCouponByType(Long couponGivetypeId) throws Exception;

    /**
     * 已知订单的产品列表 判断在自己的优惠券的列表中在筛选一次
     */
    public List<CouponGrant> getCanUseCoupon(String loginId, List<OrderVo> orderList,
            BigDecimal orderMoney) throws Exception;

    /**
     * 优惠券查询是否能使用 传参：使用优惠券规则Id，用户Id，订单总额，类型:1.改变为已使用 2.改变为作废； 返回参数 ：1、成功 2、失败
     * 
     * @throws Exception
     */
    // public int canuseCoupon(Long couponId, String loginId,List<OrderVo> orderList,
    // BigDecimal orderMoney) throws Exception ;

    /**
     * 优惠券发放动作,不管哪种类型的都是 0:优惠券规则不存在 -1:已过期 -2：优惠券id传参为空 以下类型通过此接口发放： 订单满足条件发放优惠券、
     * 
     * @return
     */
    public Long couponGrant(CouponGrant couponGrant) throws Exception;

    /**
     * 批量发放，目前尚未完善，暂未使用
     * 
     * @param couponGrantList
     * @return
     * @throws Exception
     */
    public List<CouponGrant> couponGrantList(List<CouponGrant> couponGrantList) throws Exception;

    /**
     * 查询可用优惠券接口 可用优惠券接口
     * 
     * @param couponGrantList 属于该用户的所有尚未过期(优惠券状态为未使用并且还在有效期内)的优惠券列表
     * @param orderList 需要使用的订单列表,主要是看订单中的产品
     * @param orderTotlePrice 订单总支付金额是否满足满多少减多少
     * @return 返回可用优惠券列表(map的key值为优惠券的ID, value值为
     *         CouponCanUse对象,该对象目前只包含bondPrice:实际抵扣金额(实际优惠券的抵扣金额))
     * @throws Exception
     */
    public Map<Long, CouponCanUse> canuseCoupon(List<CouponGrant> couponGrantList,
            List<OrderVo> orderList, BigDecimal orderTotlePrice, Long supplierId) throws Exception;

    /**
     * 查询当前用户可以及不可用优惠券，key:canUseCouponList/unUseCouponList分别表示可用、不可用优惠券
     * 
     * @param orderList 需要使用的订单列表,主要是看订单中的产品
     * @param customerId 当前用户id
     * @param orderTotlePrice 订单总支付金额是否满足满多少减多少
     * @param supplierId 选中的商家ID
     * @return 将可用优惠券，不可用优惠券以HashMap对象返回
     * @throws Exception
     */
    public HashMap<String, List<HashMap<String, String>>> getCanUseAndUnUseCoupon(
            List<OrderVo> orderList, String customerId, BigDecimal orderTotlePrice, Long supplierId)
            throws Exception;

    /**
     * 查询不可用优惠券接口
     * 
     * @param orderList 需要使用的订单列表,主要是看订单中的产品
     * @param customerId 用户ID
     * @param orderTotlePrice 订单总支付金额
     * @param supplierId 商家ID
     * @return 返回封装不可用优惠券信息(包含couponName,availablePrice,couponLeastPrice,couponId,endDate,
     *         orderTotlePrice ,couponMoney,isTimeOut)的hashMap 集合
     */
    public List<HashMap<String, String>> getUnUseCoupon(List<OrderVo> orderList, String customerId,
            BigDecimal orderTotlePrice, Long supplierId);


    /**
     * 优惠券激活码验证 (不记名优惠券激活接口)
     * 
     * @param userId 用户ID
     * @param activeCode 激活码
     * @return 1：用户Id为空 2:优惠券激活码为空 3:优惠券激活码无效 4:优惠券激活码已激活 6:该激活码已过期 7：激活优惠券异常 8：激活优惠券成功
     * @throws Exception
     */
    public int checkCouponGrant(String userId, String activeCode) throws Exception;

    /**
     * 抽奖奖品发放动作 0:优惠券规则不存在 -1:已过期 -2：优惠券id传参为空 -3：用户id为空
     * 
     * @return
     */
    public Long couponGrantLottery(CouponGrant couponGrant) throws Exception;



    /**
     * 激活礼品券
     * 
     * @param couponId
     * @param customerId
     * @return
     * @throws Exception
     */
    public boolean activateGiftcoupon(Long couponGrantId, Integer customerId) throws Exception;


    /**
     * 注册时发放优惠券远程调用接口
     * 
     * @param user 用户信息参数载体
     * @return
     * @throws Exception
     */
    public boolean grantCouponForRegist(User user) throws Exception;

    /**
     * 时代首次登录时发放优惠券远程调用接口
     * 
     * @param user 用户信息参数载体
     * @return
     * @throws Exception
     */
    public boolean grantCouponForSdFirstLogin(User user) throws Exception;

    /**
     * 奖品类型发放优惠券
     * 
     */
    public boolean lotteryGivenCoupon(Integer customId, List<Long> couponIdList) throws Exception;

    /**
     * 订单类型发放优惠券
     * 
     */
    public Map<Long, OrderPreferential> orderGivenCoupon(Integer customId,
            List<OrderPreferential> orderCoupon) throws Exception;

    /**
     * 
     * @param user（放入loginId即可）
     * @param couponId
     * @param grantName 发放人
     * @param num 发放优惠券数量
     * @return
     * @throws Exception
     */
    public boolean rechargeGrantCoupon(User user, Long couponId, Long grantName, int num)
            throws Exception;

    /** 微商注册发券 */
    public boolean grantCouponForCommonRegist(UserInfoDO user) throws Exception;

    /**
     * 通用发放优惠券
     * 
     * @param couponMap: customId:用户id，couponIds:优惠券id，多个券用,分隔，
     *        couponGrantDetailType:优惠券发放明细类型,值参照类CouponGrantDetailType
     *        couponGrantFlowStatus:优惠券流水操作类型,值取自类couponGrantFlowStatus
     * @return true:发放优惠券成功 false:发放优惠券失败
     * @throws Exception
     */
    public boolean generalGivenCoupon(Map<String, Object> couponMap) throws Exception;
}
