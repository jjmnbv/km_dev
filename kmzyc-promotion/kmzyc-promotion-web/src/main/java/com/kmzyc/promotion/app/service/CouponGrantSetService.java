package com.kmzyc.promotion.app.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.Coupon;
import com.kmzyc.promotion.app.vobject.CouponGrant;
import com.kmzyc.promotion.app.vobject.CouponGrantInfo;
import com.kmzyc.promotion.app.vobject.CouponGrantSetOBJ;
import com.kmzyc.promotion.app.vobject.CouponGrantSeting;
import com.kmzyc.promotion.app.vobject.CouponGrantVO;
import com.kmzyc.promotion.app.vobject.UserLeve;
import com.kmzyc.promotion.app.vobject.UserLoginId;
import com.pltfm.app.dataobject.UserInfoDO;

public interface CouponGrantSetService {
    /**
     * 保存优惠券发放设置
     * 
     * @param cgso
     * @return
     * @throws SQLException
     */
    public Integer saveCouponGrantSet(CouponGrantSetOBJ cgso) throws SQLException;

    /**
     * 查询优惠券发放设置集合（分页）
     * 
     * @return
     * @throws SQLException
     */
    public List<CouponGrantSeting> selectCouponGrantVOList(CouponGrantSeting cgs)
            throws SQLException;

    /**
     * 查询总数
     * 
     * @param cgs
     * @return
     * @throws SQLException
     */
    public Integer selectCount(CouponGrantSeting cgs) throws SQLException;

    /**
     * 更新优惠券设置表
     * 
     * @return
     * @throws SQLException
     */
    public Integer updateCouponGrant(CouponGrantSeting cgs) throws SQLException;

    /**
     * 查询集合
     * 
     * @param cgs
     * @return
     * @throws SQLException
     */
    public List<CouponGrantSeting> selectByCouponGrant(CouponGrantSeting cgs) throws SQLException;

    /**
     * 删除（通过couponId或是couponIssuingId）
     * 
     * @param cgs
     * @return
     * @throws SQLException
     */
    public Integer deleteCouponGrant(CouponGrantSeting cgs) throws SQLException;

    /**
     * 保存
     * 
     * @param cgs
     * @return
     * @throws SQLException
     */
    public Long saveGrantCoupon(CouponGrantSeting cgs) throws SQLException;

    /**
     * 生成优惠券实体
     * 
     * @param cg
     * @return
     * @throws SQLException
     */
    public Long inserCouponGrantOBJ(CouponGrant cg) throws SQLException;

    /**
     * 查询客户id
     * 
     * @param leveId
     * @return
     * @throws SQLException
     */
    public List<UserLoginId> selectLoginIdArr(Integer leveId) throws SQLException;

    /**
     * 查询客户等级
     * 
     * @param leveId
     * @return
     * @throws SQLException
     */
    public List<UserLeve> selectUserLeveByLeveId(Integer leveId) throws SQLException;

    /**
     * 查询客户
     * 
     * @param loginId
     * @return
     * @throws SQLException
     */
    public List<UserLoginId> selectUserByLoginId(Integer loginId) throws SQLException;

    /**
     * 分页查询出
     * 
     * @param cgv
     * @return
     * @throws SQLException
     */
    public List<CouponGrantVO> selectCouponGrant(CouponGrantVO cgv) throws SQLException;

    /**
     * 查询出总数
     * 
     * @param cgv
     * @return
     * @throws SQLException
     */
    public Integer selectCouponGrantCount(CouponGrantVO cgv) throws SQLException;

    /**
     * 查询全部集合
     * 
     * @param cgv
     * @return
     * @throws SQLException
     */
    public List<CouponGrantVO> selectCouponGrantALL(CouponGrantVO cgv) throws SQLException;

    /**
     * 导出优惠券（不记名）
     * 
     * @param strarr
     * @param list
     * @throws Exception
     */
    public void exportExcel(String[] strarr, List<CouponGrantVO> list) throws Exception;

    /**
     * 获取不记名优惠券的NO
     * 
     * @param couponIssuingId
     * @param couponGrangId
     * @return
     * @throws Exception
     */
    public String bearerCouponNO(String couponIssuingId, String couponGrangId) throws Exception;

    /**
     * 获取不记名优惠券的验证码
     * 
     * @param couponGrangId
     * @return
     * @throws Exception
     */
    public String bearerCouponCode(String couponGrangId) throws Exception;

    /**
     * 保存激活码信息
     * 
     * @param cgi
     * @return
     * @throws SQLException
     */
    public Long saveVCode(CouponGrantInfo cgi) throws SQLException;

    /**
     * 批量
     * 
     * @param cgiList
     * @return
     * @throws SQLException
     */
    public Boolean bathSaveCouponGrantInfo(List<CouponGrantInfo> cgiList) throws SQLException;

    /**
     * 查询可用规则
     * 
     * @param coupon
     * @return
     * @throws SQLException
     */
    public Page selectCouponByStatus(Page page, Coupon coupon) throws SQLException;

    /**
     * 根据时间查询发放记录信息
     * 
     * @return
     * @throws SQLException
     */
    public List<CouponGrantSeting> selectBytime(CouponGrantSeting cgs) throws SQLException;

    /**
     * 批量更新发放记录信息状态
     * 
     * @param listCgs
     * @return
     * @throws SQLException
     */
    public Boolean batchUpdateCouponG(List<CouponGrantSeting> listCgs) throws SQLException;

    /**
     * 创建定时器
     * 
     * @param couponIssuingId
     * @throws SQLException
     */
    void createJob(long couponIssuingId) throws SQLException;

    /**
     * 更新发放设置
     * 
     * @param cgs
     * @return
     * @throws SQLException
     */
    public Integer updateGrantSeting(CouponGrantSeting cgs) throws SQLException;

    /**
     * 判断券过期job
     * 
     * @param couponIssuingId
     * @throws SQLException
     */
    void createTimeOutJOB(long couponIssuingId) throws SQLException;

    /**
     * 根据userID 查询user
     */
    public List<UserInfoDO> findUserById(UserInfoDO userCondition) throws SQLException;

}
