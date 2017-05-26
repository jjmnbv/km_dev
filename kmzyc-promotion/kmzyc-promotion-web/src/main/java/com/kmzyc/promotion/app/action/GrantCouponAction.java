package com.kmzyc.promotion.app.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.dao.CouponDAO;
import com.kmzyc.promotion.app.dao.CouponGrantFlowDAO;
import com.kmzyc.promotion.app.enums.CouponGrantFlowStatus;
import com.kmzyc.promotion.app.enums.CouponIssuingStatus;
import com.kmzyc.promotion.app.service.CouponGrantService;
import com.kmzyc.promotion.app.service.CouponGrantSetService;
import com.kmzyc.promotion.app.service.CouponService;
import com.kmzyc.promotion.app.vobject.Coupon;
import com.kmzyc.promotion.app.vobject.CouponGrant;
import com.kmzyc.promotion.app.vobject.CouponGrantFlow;
import com.kmzyc.promotion.app.vobject.CouponGrantInfo;
import com.kmzyc.promotion.app.vobject.CouponGrantSeting;
import com.kmzyc.promotion.app.vobject.CouponGrantVO;
import com.kmzyc.promotion.app.vobject.UserLeve;
import com.kmzyc.promotion.app.vobject.UserLoginId;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("rawtypes")
@Controller("grantCouponAction")
@Scope(value = "prototype")
public class GrantCouponAction extends BaseAction implements ModelDriven {


    private static final long serialVersionUID = 1L;
    // 日志记录
    private static final Logger logger = LoggerFactory.getLogger(GrantCouponAction.class);
    @Resource
    private CustomerRemoteService customerRemoteService;

    @Resource(name = "couponGrantSetService")
    private CouponGrantSetService couponGrantSetService;

    @Resource(name = "CouponGrantService")
    private CouponGrantService couponGrantService;

    @Resource(name = "couponGrantFlowDao")
    private CouponGrantFlowDAO couponGrantFlowDAO;

    @Resource(name = "coupondao")
    private CouponDAO coupondao;

    @Resource(name = "CouponService")
    private CouponService couponService;

    private Coupon coupon;
    private String couponId;
    // 优惠券发放设置VO
    private CouponGrantSeting coupongrantSeting;
    private CouponGrantVO couponGrantVO;
    private List<UserLoginId> userList = new ArrayList<UserLoginId>();
    private List<UserLeve> userLeve = new ArrayList<UserLeve>();
    private List<CouponGrantVO> grantList = new ArrayList<CouponGrantVO>();
    private String cheCoupon_discription = "";
    // 优惠券发放查询实体
    private CouponGrant paraForQuery;



    // 优惠券发放设置
    public String gotoGrantCouponList() {
        try {
            if (page == null) {
                page = new Page();
                coupongrantSeting = new CouponGrantSeting();
                // coupongrantSeting.setCouponGiveTypeId(new Long(1));
            }

            if (null != coupongrantSeting.getCouponName()) {
                if ("".equals(coupongrantSeting.getCouponName().intern())) {
                    coupongrantSeting.setCouponName(null);
                }
            }

            // 查询总条数
            int pagecount = 1;
            // 总页数
            int count = couponGrantSetService.selectCount(coupongrantSeting);
            if (count > 1) {
                pagecount = (count - 1) / page.getPageSize() + 1;
            }
            page.setRecordCount(count);
            page.setPageCount(pagecount);
            // 分页查询
            int pageNo = page.getPageNo();// 当前查询第几页
            if (pageNo == 0)
                pageNo = 1;// 首次查询第一页
            int pageSize = page.getPageSize(); // 每页显示记录数
            int skip = (pageNo - 1) * pageSize + 1;
            int max = (pageNo - 1) * pageSize + pageSize;
            coupongrantSeting.setStartIndex(skip);
            coupongrantSeting.setEndIndex(max);
            page.setPageNo(pageNo);// 当前查询第几页
            page.setDataList(couponGrantSetService.selectCouponGrantVOList(coupongrantSeting));
        } catch (Exception e) {
            logger.error("查询优惠券发放设置异常" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    public String grantCouponAdd() {
        return SUCCESS;
    }

    /**
     * 选择优惠券规则
     * 
     * @return
     */
    public String chooseCouponRule() {
        try {
            if (page == null) {
                page = new Page();
                coupon = new Coupon();
            }

            page = couponGrantSetService.selectCouponByStatus(page, coupon);
        } catch (Exception e) {

        }
        return SUCCESS;
    }

    /**
     * 重新启动注册类型的优惠券之前进行校验
     * 
     * @return
     * @throws Exception
     */
    public String checktimeOutCoupon() throws Exception {
        if (coupongrantSeting == null) {
            logger.error("GrantCouponAction.java中方法checktimeOutCoupon数据参数异常！");
            return ERROR;
        }
        List<CouponGrantSeting> couList =
                couponGrantSetService.selectByCouponGrant(coupongrantSeting);
        Date date = new Date();
        if (couList == null || couList.size() == 0) {
            logger.error("GrantCouponAction.java中方法checktimeOutCoupon查询数据失败！");

            return null;
        }

        if (date.getTime() >= couList.get(0).getIssuingEndTime().getTime()) {
            // 设置发放已截止
            CouponGrantSeting cgs = null;
            try {
                cgs = new CouponGrantSeting();
                cgs.setCouponIssuingId(coupongrantSeting.getCouponIssuingId());
                cgs.setIsStatus(new Long(CouponIssuingStatus.IS_PALUSE.getType()));
                couponGrantSetService.updateCouponGrant(cgs);
                this.writeJson("false");
            } catch (SQLException e) {
                logger.error("couponGrantSetService.updateCouponGrant方法数据更新异常", e);
                return ERROR;
            }
        } else {
            this.writeJson("true");
        }
        return null;
    }

    /**
     * 注册类型的优惠券启动发放
     * 
     * @return
     */
    public String startGrantCoupon() {
        if (coupongrantSeting == null) {
            logger.error("GrantCouponAction.java中方法startGrantCoupon数据参数异常！");
            return ERROR;
        }
        CouponGrantSeting cgs = null;
        try {
            cgs = new CouponGrantSeting();
            cgs.setCouponIssuingId(coupongrantSeting.getCouponIssuingId());
            cgs.setIsStatus(new Long(CouponIssuingStatus.IS_ING.getType()));
            cgs.setIssuingStartTime(new Date());
            couponGrantSetService.updateCouponGrant(cgs);
            return gotoGrantCouponList();
        } catch (SQLException e) {
            logger.error("startGrantCoupon方法数据更新异常", e);
            return ERROR;
        }
    }

    /**
     * 暂停发放
     * 
     * @return
     */
    public String pauseGrantCoupon() {
        if (coupongrantSeting == null) {
            logger.error("GrantCouponAction.java中方法pauseGrantCoupon数据参数异常！");
            return ERROR;
        }
        CouponGrantSeting cgs = null;
        try {
            cgs = new CouponGrantSeting();
            cgs.setCouponIssuingId(coupongrantSeting.getCouponIssuingId());
            cgs.setIsStatus(new Long(4));
            couponGrantSetService.updateCouponGrant(cgs);
            return gotoGrantCouponList();
        } catch (SQLException e) {
            logger.error("pauseGrantCoupon方法数据更新异常", e);
            return ERROR;
        }
    }

    /**
     * 截止发放
     * 
     * @return
     */
    public String closeGrantCoupon() {
        if (coupongrantSeting == null) {
            logger.error("GrantCouponAction.java中方法closeGrantCoupon数据参数异常！");
            return ERROR;
        }
        CouponGrantSeting cgs = null;
        try {
            cgs = new CouponGrantSeting();
            cgs.setCouponIssuingId(coupongrantSeting.getCouponIssuingId());
            cgs.setIsStatus(new Long(2));// 已完成
            cgs.setIssuingEndTime(new Date());
            couponGrantSetService.updateCouponGrant(cgs);
            return gotoGrantCouponList();
        } catch (SQLException e) {
            logger.error("closeGrantCoupon方法数据更新异常", e);
            return ERROR;
        }
    }

    /**
     * 查看发放信息
     * 
     * @return
     */
    public String checkCouponGrant() {
        // 根据发放设置ID查询出发放实体
        if (coupongrantSeting == null) {
            logger.error("GrantCouponAction.java中方法checkCouponGrant数据参数异常！");
            return ERROR;
        }
        List<CouponGrantSeting> cgsList;
        try {
            cgsList = couponGrantSetService.selectByCouponGrant(coupongrantSeting);
            if (cgsList != null && cgsList.size() > 0) {
                coupongrantSeting = cgsList.get(0);
            }
            String customIds = coupongrantSeting.getCustomId();
            String customLeveId = coupongrantSeting.getCustomLeveId();
            if (customIds != null && !"".equals(customIds)) { // 如果是针对会员发放
                String[] couIdarr = customIds.split(",");
                for (int i = 0; i < couIdarr.length; i++) {
                    userList.add(couponGrantSetService
                            .selectUserByLoginId(Integer.parseInt(couIdarr[i])).get(0));

                }
                userLeve = null;
            } else if (customLeveId != null && !"".equals(customLeveId)) { // 如果是针对等级发放
                String[] customLeveidarr = customLeveId.split(",");
                // 根据客户等级查询出客户id
                for (int j = 0; j < customLeveidarr.length; j++) {
                    userLeve.add(couponGrantSetService
                            .selectUserLeveByLeveId(Integer.valueOf(customLeveidarr[j])).get(0));

                }
            } else if (coupongrantSeting.getCouponGivetypeId() == 6) {// 不记名发放
                // grantList=couponGrantSetService.
                if (page == null) {
                    page = new Page();
                    couponGrantVO = new CouponGrantVO();
                    couponGrantVO.setCouponIssuingId(coupongrantSeting.getCouponIssuingId());
                }
                // 查询总条数
                int pagecount = 1;
                // 总页数
                int count = couponGrantSetService.selectCouponGrantCount(couponGrantVO);
                if (count > 1) {
                    pagecount = (count - 1) / page.getPageSize() + 1;
                }
                page.setRecordCount(count);
                page.setPageCount(pagecount);
                // 分页查询
                int pageNo = page.getPageNo();// 当前查询第几页
                if (pageNo == 0)
                    pageNo = 1;// 首次查询第一页
                int pageSize = page.getPageSize(); // 每页显示记录数
                int skip = (pageNo - 1) * pageSize;
                int max = (pageNo - 1) * pageSize + pageSize;
                couponGrantVO.setStartIndex(skip);
                couponGrantVO.setEndIndex(max);
                page.setPageNo(pageNo);// 当前查询第几页
                page.setDataList(couponGrantSetService.selectCouponGrant(couponGrantVO));

            }

            return SUCCESS;
        } catch (SQLException e) {
            logger.error("checkCouponGrant方法数据更新异常", e);
            return ERROR;
        }
    }

    /**
     * 编辑
     * 
     * @return
     */
    public String editCouponGrant() {
        if (coupongrantSeting == null) {
            logger.error("GrantCouponAction.java中方法editCouponGrant数据参数异常！");
            return ERROR;
        }
        List<CouponGrantSeting> cgsList;
        try {
            cgsList = couponGrantSetService.selectByCouponGrant(coupongrantSeting);
            if (cgsList != null && cgsList.size() > 0) {
                coupongrantSeting = cgsList.get(0);
            }
            return SUCCESS;
        } catch (SQLException e) {
            logger.error("editCouponGrant方法数据更新异常", e);
            return ERROR;
        }
    }

    /**
     * 修改未发放的设置
     */
    public String updateCouponGrantSeting() {
        if (coupongrantSeting.getCouponGivetypeId() != 2) {
            coupongrantSeting.setIsStatus(new Long(2));// 已完成
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date();
            coupongrantSeting.setCreateTime(now);
            couponGrantSetService.updateGrantSeting(coupongrantSeting);
            List<CouponGrantSeting> listcgs =
                    couponGrantSetService.selectByCouponGrant(coupongrantSeting);
            if (listcgs != null && listcgs.size() > 0) {
                coupongrantSeting = listcgs.get(0);
            }

            if (coupongrantSeting.getCouponGivetypeId() == 1) {
                String customIds = coupongrantSeting.getCustomId();
                String customLeveId = coupongrantSeting.getCustomLeveId();
                if (customIds != null && !"".equals(customIds)) {
                    String[] couIdarr = customIds.split(",");
                    for (int i = 0; i < couIdarr.length; i++) {
                        UserLoginId userlo = couponGrantSetService
                                .selectUserByLoginId(Integer.parseInt(couIdarr[i])).get(0);
                        userList.add(userlo);
                        CouponGrant cgrant = new CouponGrant();
                        cgrant.setCouponId(coupongrantSeting.getCouponId());
                        cgrant.setCouponStatus(new Long(3));// 未使用
                        cgrant.setCustomId(Integer.parseInt(couIdarr[i]));
                        cgrant.setGrantType(new Long(11));
                        // 规则的时间限制类型
                        Coupon couponc = new Coupon();
                        couponc.setCouponId(coupongrantSeting.getCouponId());
                        couponc = couponService.selectCoupon(couponc);
                        if (couponc.getTimeType() == 1) {
                            cgrant.setStartTime(couponc.getStarttime());
                            cgrant.setEndTime(couponc.getEndtime());
                        } else if (couponc.getTimeType() == 2) {// 固定天数
                            Date da = new Date();
                            cgrant.setStartTime(da);
                            Calendar c = Calendar.getInstance();
                            c.setTime(da);
                            c.add(Calendar.DATE, couponc.getCouponValidDay().intValue());
                            Date temp_date = c.getTime();
                            cgrant.setEndTime(temp_date);

                        }
                        cgrant.setCouponIssuingId(coupongrantSeting.getCouponIssuingId());// 设置发放设置ID
                        Long couponGrantId = couponGrantSetService.inserCouponGrantOBJ(cgrant);
                        // 开始发放,并且录入流水
                        CouponGrantFlow record = new CouponGrantFlow();
                        record.setCouponGrantFlowType(Long
                                .valueOf(CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType()));
                        record.setCreateDate(dateFormat.parse(dateFormat.format(now)));
                        record.setCouponGrantId(couponGrantId);
                        record.setCouponId(cgrant.getCouponId());
                        record.setCouponName(couponc.getCouponName());
                        record.setCustomId(cgrant.getCustomId().longValue());
                        record.setCustomer(userlo.getLoginAccount());
                        record.setOperatingPersonName("发放设置发放");
                        record.setRemark("手工发放  ，优惠券操作类型为:发放，    优惠券编号：" + cgrant.getCouponId()
                                + "，     优惠券发放id：" + cgrant.getCouponGrantId() + "，     操作人： "
                                + record.getOperatingPersonName());
                        couponGrantFlowDAO.insert(record);
                    }
                } else if (customLeveId != null && !"".equals(customLeveId)) {
                    String[] customLeveidarr = customLeveId.split(",");
                    List<Long> list = new ArrayList<Long>();
                    // 根据客户等级查询出客户id
                    for (int j = 0; j < customLeveidarr.length; j++) {
                        List<UserLoginId> listu = couponGrantSetService
                                .selectLoginIdArr(Integer.valueOf(customLeveidarr[j]));
                        userLeve.add(couponGrantSetService
                                .selectUserLeveByLeveId(Integer.valueOf(customLeveidarr[j]))
                                .get(0));
                        for (int i = 0; i < listu.size(); i++) {
                            list.add(listu.get(i).getCustomId());
                        }
                    }
                    // 最后得到customId集合List
                    for (int i = 0; i < list.size(); i++) {
                        CouponGrant cgrant = new CouponGrant();
                        cgrant.setCouponId(coupongrantSeting.getCouponId());
                        cgrant.setCouponStatus(new Long(3));// 未使用
                        cgrant.setCustomId(list.get(i).intValue());
                        cgrant.setGrantType(new Long(11));// 手工发放
                        // 规则的时间限制类型
                        Coupon couponc = new Coupon();
                        couponc.setCouponId(coupongrantSeting.getCouponId());
                        couponc = couponService.selectCoupon(couponc);
                        UserLoginId userlo = couponGrantSetService
                                .selectUserByLoginId(list.get(i).intValue()).get(0);
                        if (couponc.getTimeType() == 1) {
                            cgrant.setStartTime(couponc.getStarttime());
                            cgrant.setEndTime(couponc.getEndtime());
                        } else if (couponc.getTimeType() == 2) {// 固定天数
                            Date da = new Date();
                            cgrant.setStartTime(da);
                            Calendar c = Calendar.getInstance();
                            c.setTime(da);
                            c.add(Calendar.DATE, couponc.getCouponValidDay().intValue());
                            Date temp_date = c.getTime();
                            cgrant.setEndTime(temp_date);

                        }
                        cgrant.setCouponIssuingId(coupongrantSeting.getCouponIssuingId());// 设置发放设置ID
                        Long couponGrantId = couponGrantSetService.inserCouponGrantOBJ(cgrant);
                        // 开始发放,并且录入流水
                        CouponGrantFlow record = new CouponGrantFlow();
                        record.setCouponGrantFlowType(Long
                                .valueOf(CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType()));
                        record.setCreateDate(dateFormat.parse(dateFormat.format(now)));
                        record.setCouponGrantId(couponGrantId);
                        record.setCouponId(cgrant.getCouponId());
                        record.setCouponName(couponc.getCouponName());
                        record.setCustomId(cgrant.getCustomId().longValue());
                        record.setCustomer(userlo.getLoginAccount());
                        record.setOperatingPersonName("发放设置发放");
                        record.setRemark("手工发放  ，优惠券操作类型为:发放，    优惠券编号：" + cgrant.getCouponId()
                                + "，     优惠券发放id：" + cgrant.getCouponGrantId() + "，     操作人： "
                                + record.getOperatingPersonName());
                        couponGrantFlowDAO.insert(record);
                    }

                }
            } else if (coupongrantSeting.getCouponGivetypeId() == 6) {// 不记名发放
                Coupon couponc = new Coupon();
                couponc.setCouponId(coupongrantSeting.getCouponId());
                couponc = couponService.selectCoupon(couponc);
                // 激活码生成规则
                int count = coupongrantSeting.getIssuingCount().intValue();
                List<CouponGrantInfo> cgiList = new ArrayList<CouponGrantInfo>();
                long start = System.currentTimeMillis();
                for (int i = 0; i < count; i++) {
                    CouponGrant cgrant = new CouponGrant();
                    cgrant.setCouponId(coupongrantSeting.getCouponId());
                    cgrant.setCouponStatus(new Long(1));// 未发放
                    cgrant.setGrantType(new Long(61));// 不记名发放
                    // 获取激活码
                    // cgrant.setActiveCode();
                    cgrant.setCouponIssuingId(coupongrantSeting.getCouponIssuingId());
                    cgrant.setActStatus(new Long(0));// 未激活
                    Long cGrantId = couponGrantSetService.inserCouponGrantOBJ(cgrant);
                    // 开始发放,并且录入流水
                    CouponGrantFlow record = new CouponGrantFlow();
                    record.setCouponGrantFlowType(
                            Long.valueOf(CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType()));
                    record.setCreateDate(dateFormat.parse(dateFormat.format(now)));
                    record.setCouponGrantId(cGrantId);
                    record.setCouponId(cgrant.getCouponId());
                    record.setCouponGrantId(cGrantId);
                    record.setCouponName(couponc.getCouponName());
                    record.setOperatingPersonName("发放设置不记名发放");
                    record.setRemark("不记名发放  ，优惠券操作类型为:未发放，    优惠券编号：" + cgrant.getCouponId()
                            + "，     优惠券发放id：" + cgrant.getCouponGrantId() + "，   操作人： "
                            + record.getOperatingPersonName());
                    couponGrantFlowDAO.insert(record);

                    String CouponNo = couponGrantSetService.bearerCouponNO(
                            coupongrantSeting.getCouponIssuingId().toString(), cGrantId.toString());
                    String vCode = couponGrantSetService.bearerCouponCode(cGrantId.toString());
                    CouponGrantInfo cgi = new CouponGrantInfo();
                    cgi.setCouponGrantId(cGrantId);
                    cgi.setCouponInfoNo(CouponNo);
                    cgi.setActiveCode(vCode);
                    cgiList.add(cgi);
                    // Long cgiId=couponGrantSetService.saveVCode(cgi);

                }
                couponGrantSetService.bathSaveCouponGrantInfo(cgiList);
                long end = System.currentTimeMillis();
                System.out.println("执行时间是（ms）：" + (end - start));
                // 分页查询出优惠券
                if (couponGrantVO == null) {
                    page = new Page();
                    couponGrantVO = new CouponGrantVO();
                    couponGrantVO.setCouponIssuingId(coupongrantSeting.getCouponIssuingId());
                }
                // 查询总条数
                int pagecount = 1;
                // 总页数
                int countC = couponGrantSetService.selectCouponGrantCount(couponGrantVO);
                if (countC > 1) {
                    pagecount = (countC - 1) / page.getPageSize() + 1;
                }
                page.setRecordCount(countC);
                page.setPageCount(pagecount);
                // 分页查询
                int pageNo = page.getPageNo();// 当前查询第几页
                if (pageNo == 0)
                    pageNo = 1;// 首次查询第一页
                int pageSize = page.getPageSize(); // 每页显示记录数
                int skip = (pageNo - 1) * pageSize;
                int max = (pageNo - 1) * pageSize + pageSize;
                couponGrantVO.setStartIndex(skip);
                couponGrantVO.setEndIndex(max);
                page.setPageNo(pageNo);// 当前查询第几页
                page.setDataList(couponGrantSetService.selectCouponGrant(couponGrantVO));
            }
        } catch (SQLException e) {
            logger.error("updateCouponGrantSeting方法数据更新异常", e);
            return ERROR;
        } catch (ParseException e1) {
            logger.error("updateCouponGrantSeting方法转化时间异常", e1);
            return ERROR;
        } catch (Exception e2) {
            logger.error("updateCouponGrantSeting方法数据操作异常", e2);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 提供给规则链接发放
     * 
     * @return
     */
    public String eidtGrant() {
        if (coupongrantSeting == null) {
            logger.error("GrantCouponAction.java中方法eidtGrant数据参数异常！");
            return ERROR;
        }
        try {
            Coupon cou = new Coupon();
            cou.setCouponId(coupongrantSeting.getCouponId());
            coupon = couponService.selectCoupon(cou);
            return SUCCESS;
        } catch (SQLException e) {
            logger.error("eidtGrant方法数据更新异常SQLException", e);
            return ERROR;
        } catch (Exception e) {
            logger.error("eidtGrant方法数据更新异常", e);
            return ERROR;
        }
    }

    /**
     * 删除
     * 
     * @return
     */
    public String deleteCouponGrant() {
        if (coupongrantSeting == null) {
            logger.error("GrantCouponAction.java中方法deleteCouponGrant数据参数异常！");
            return ERROR;
        }
        try {
            couponGrantSetService.deleteCouponGrant(coupongrantSeting);

            return gotoGrantCouponList();
        } catch (SQLException e) {
            logger.error("deleteCouponGrant方法删除数据异常", e);
            return ERROR;
        }
    }

    public String getReturnRule() {
        String returnString = "";
        Coupon coupon = null;
        try {
            if (StringUtils.isNotBlank(couponId)) {
                // 查询出规则实体
                coupon = new Coupon();
                coupon.setCouponId(Long.valueOf(couponId));
                coupon = couponService.selectCoupon(coupon);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String strTime = null;
                String endTime = null;
                if (coupon.getStarttime() != null) {
                    strTime = sdf.format(coupon.getStarttime());
                }
                if (coupon.getEndtime() != null) {
                    endTime = sdf.format(coupon.getEndtime());
                }
                returnString = coupon.getCouponId().toString() + "," + coupon.getCouponName() + ","
                        + strTime + "," + endTime;
            }
        } catch (Exception e) {
            logger.error("getReturnRule方法查询规则异常:", e);
            return ERROR;
        }

        this.writeJson(returnString);
        return null;

    }

    /**
     * 保存
     * 
     * @return
     */
    public String saveGrantCoupon() {
        if (coupongrantSeting == null || coupongrantSeting.getCouponId() == null) {
            logger.error("GrantCouponAction.java中方法saveGrantCoupon数据参数异常！");
            return ERROR;
        }
        try {
            Date grantCreattime = new Date();
            Long grantCreateman = Long.valueOf(this.getLoginUserId().toString());
            Long couponGrantSetId = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date();
            long saveStart = System.currentTimeMillis(); // 发放开始时间
            if (coupongrantSeting.getCouponGivetypeId() == 1) {
                // 手工发放
                coupongrantSeting.setIsStatus(new Long(2));// 设置状态为已完成
                couponGrantSetId = couponGrantSetService.saveGrantCoupon(coupongrantSeting);
                // 开始发放动作
                CouponGrantSeting cgs = new CouponGrantSeting();
                cgs.setCouponIssuingId(couponGrantSetId);
                List<CouponGrantSeting> listcgs = couponGrantSetService.selectByCouponGrant(cgs);
                if (listcgs != null && listcgs.size() > 0) {
                    coupongrantSeting = listcgs.get(0);
                }
                // 获取用户
                String customIds = coupongrantSeting.getCustomId();
                String customLeveId = coupongrantSeting.getCustomLeveId();
                if (customIds != null && !"".equals(customIds)) {
                    // 进行发放、插入流水
                    String[] couIdarr = customIds.split(",");
                    for (int i = 0; i < couIdarr.length; i++) {
                        UserLoginId userlo = couponGrantSetService
                                .selectUserByLoginId(Integer.parseInt(couIdarr[i])).get(0);
                        userList.add(userlo);
                        CouponGrant cgrant = new CouponGrant();
                        cgrant.setCouponId(coupongrantSeting.getCouponId());
                        cgrant.setCouponStatus(new Long(3));// 未使用
                        cgrant.setCustomId(Integer.parseInt(couIdarr[i]));
                        cgrant.setGrantType(new Long(11));
                        // 此处couponc 以为最新的实体规则 规则的时间限制类型
                        Coupon couponc = new Coupon();
                        couponc.setCouponId(coupongrantSeting.getCouponId());
                        couponc = couponService.selectCoupon(couponc);
                        if (couponc.getTimeType() == 1) {
                            cgrant.setStartTime(couponc.getStarttime());
                            cgrant.setEndTime(couponc.getEndtime());
                        } else if (couponc.getTimeType() == 2) {// 固定天数
                            Date da = new Date();
                            cgrant.setStartTime(da);
                            Calendar c = Calendar.getInstance();
                            c.setTime(da);
                            c.add(Calendar.DATE, couponc.getCouponValidDay().intValue());
                            Date temp_date = c.getTime();
                            cgrant.setEndTime(temp_date);

                        }
                        cgrant.setActTime(new Date());
                        cgrant.setCouponIssuingId(couponGrantSetId);// 设置发放设置ID
                        // 设置优惠券发放人
                        cgrant.setGrantCreateman(grantCreateman);
                        cgrant.setGrantCreattime(grantCreattime);
                        Long couponGrantId = couponGrantSetService.inserCouponGrantOBJ(cgrant);
                        // 开始发放,并且录入流水
                        CouponGrantFlow record = new CouponGrantFlow();
                        record.setCouponGrantFlowType(Long
                                .valueOf(CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType()));
                        record.setCreateDate(dateFormat.parse(dateFormat.format(now)));
                        record.setCouponGrantId(couponGrantId);
                        record.setCouponId(cgrant.getCouponId());
                        record.setCouponName(couponc.getCouponName());
                        record.setCustomId(cgrant.getCustomId().longValue());
                        record.setCustomer(userlo.getLoginAccount());
                        record.setOperatingPersonName("发放设置发放");
                        record.setRemark("手工发放  ，优惠券操作类型为:发放，    优惠券编号：" + cgrant.getCouponId()
                                + "，     优惠券发放id：" + cgrant.getCouponGrantId() + "，     操作人： "
                                + record.getOperatingPersonName());
                        couponGrantFlowDAO.insert(record);
                        // 进行状态的更改,---个体用户的时候
                        if (couponc.getStatus() == 1) {
                            Coupon co = new Coupon();
                            co.setCouponId(coupongrantSeting.getCouponId());
                            co.setStatus(new Long(2));// 设置状态为已发放
                            coupondao.updateCoupon(co);
                        }
                    }
                } else if (customLeveId != null && !"".equals(customLeveId)) { // 用户等级调用发放
                    String[] customLeveidarr = customLeveId.split(",");
                    List<Long> list = new ArrayList<Long>();
                    // 根据客户等级查询出客户id
                    for (int j = 0; j < customLeveidarr.length; j++) {
                        List<UserLoginId> listu = couponGrantSetService
                                .selectLoginIdArr(Integer.valueOf(customLeveidarr[j]));
                        userLeve.add(couponGrantSetService
                                .selectUserLeveByLeveId(Integer.valueOf(customLeveidarr[j]))
                                .get(0));
                        for (int i = 0; i < listu.size(); i++) {
                            list.add(listu.get(i).getCustomId());
                        }
                    }
                    // 最后得到customId集合List
                    for (int i = 0; i < list.size(); i++) {
                        CouponGrant cgrant = new CouponGrant();
                        cgrant.setCouponId(coupongrantSeting.getCouponId());
                        cgrant.setCouponStatus(new Long(3));// 未使用
                        cgrant.setCustomId(list.get(i).intValue());
                        cgrant.setGrantType(new Long(11));// 手工发放
                        // 此处couponc为最新查询出来的规则， 规则的时间限制类型
                        Coupon couponc = new Coupon();
                        couponc.setCouponId(coupongrantSeting.getCouponId());
                        couponc = couponService.selectCoupon(couponc);
                        UserLoginId userlo = couponGrantSetService
                                .selectUserByLoginId(list.get(i).intValue()).get(0);
                        if (couponc.getTimeType() == 1) {
                            cgrant.setStartTime(couponc.getStarttime());
                            cgrant.setEndTime(couponc.getEndtime());
                        } else if (couponc.getTimeType() == 2) {// 固定天数
                            Date da = new Date();
                            cgrant.setStartTime(da);
                            Calendar c = Calendar.getInstance();
                            c.setTime(da);
                            c.add(Calendar.DATE, couponc.getCouponValidDay().intValue());
                            Date temp_date = c.getTime();
                            cgrant.setEndTime(temp_date);

                        }
                        cgrant.setActTime(new Date());
                        cgrant.setCouponIssuingId(couponGrantSetId);// 设置发放设置ID
                        // 设置优惠券发放人
                        cgrant.setGrantCreateman(grantCreateman);
                        cgrant.setGrantCreattime(grantCreattime);
                        Long couponGrantId = couponGrantSetService.inserCouponGrantOBJ(cgrant);
                        // 开始发放,并且录入流水
                        CouponGrantFlow record = new CouponGrantFlow();
                        record.setCouponGrantFlowType(Long
                                .valueOf(CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType()));
                        record.setCreateDate(dateFormat.parse(dateFormat.format(now)));
                        record.setCouponGrantId(couponGrantId);
                        record.setCouponId(cgrant.getCouponId());
                        record.setCouponName(couponc.getCouponName());
                        record.setCustomId(cgrant.getCustomId().longValue());
                        record.setCustomer(userlo.getLoginAccount());
                        record.setOperatingPersonName("发放设置发放");
                        record.setRemark("手工发放  ，优惠券操作类型为:发放，    优惠券编号：" + cgrant.getCouponId()
                                + "，     优惠券发放id：" + cgrant.getCouponGrantId() + "，     操作人： "
                                + record.getOperatingPersonName());
                        couponGrantFlowDAO.insert(record);
                        // 获取短信规则实体，进行状态的更改,---用户等级的时候
                        if (couponc.getStatus() == 1) {
                            Coupon co = new Coupon();
                            co.setCouponId(coupongrantSeting.getCouponId());
                            co.setStatus(new Long(2));// 设置状态为已发放
                            coupondao.updateCoupon(co);
                        }
                    }
                }
            } else if (coupongrantSeting.getCouponGivetypeId() == 2) {// 注册类型发放
                // 注册发放根据时间设置状态
                Date sys = new Date();
                Date StartTime = coupongrantSeting.getIssuingStartTime();
                Date endTime = coupongrantSeting.getIssuingEndTime();
                Long status = new Long(1);
                if (sys.getTime() < StartTime.getTime()) {
                    status = new Long(1);
                } else if (sys.getTime() > StartTime.getTime()
                        && sys.getTime() < endTime.getTime()) {
                    status = new Long(3);
                } else {
                    status = new Long(2);
                }
                coupongrantSeting.setIsStatus(status);// 设置状态为已完成
                couponGrantSetId = couponGrantSetService.saveGrantCoupon(coupongrantSeting);
                // 设置定时器任务
                couponGrantSetService.createJob(couponGrantSetId);
                CouponGrantSeting cgs = new CouponGrantSeting();
                cgs.setCouponIssuingId(couponGrantSetId);
                List<CouponGrantSeting> listcgs = couponGrantSetService.selectByCouponGrant(cgs);
                if (listcgs != null && listcgs.size() > 0) {
                    coupongrantSeting = listcgs.get(0);
                }
                // 注册类型获取的最新的优惠券，更新优惠券规则实体
                Coupon couponx = new Coupon();
                couponx.setCouponId(coupongrantSeting.getCouponId());
                couponx = couponService.selectCoupon(couponx);
                if (couponx.getStatus() == 1) {
                    Coupon co = new Coupon();
                    co.setCouponId(coupongrantSeting.getCouponId());
                    co.setStatus(new Long(2));// 设置状态为已发放
                    coupondao.updateCoupon(co);
                }
            } else {
                // 优惠券激活码获取的最新的coupon规则
                Coupon couponc = new Coupon();
                couponc.setCouponId(coupongrantSeting.getCouponId());
                couponc = couponService.selectCoupon(couponc);
                Date couponStarTime = null;
                Date couponEndTime = null;
                // 判断规则是否为固定时间
                if (couponc.getTimeType() == 1) {
                    couponStarTime = couponc.getStarttime();
                    couponEndTime = couponc.getEndtime();
                }
                // 不记名发放
                coupongrantSeting.setIsStatus(new Long(2));// 设置状态为已完成
                couponGrantSetId = couponGrantSetService.saveGrantCoupon(coupongrantSeting);
                CouponGrantSeting cgs = new CouponGrantSeting();
                cgs.setCouponIssuingId(couponGrantSetId);
                List<CouponGrantSeting> listcgs = couponGrantSetService.selectByCouponGrant(cgs);
                if (listcgs != null && listcgs.size() > 0) {
                    coupongrantSeting = listcgs.get(0);
                }
                // 激活码生成规则
                int count = coupongrantSeting.getIssuingCount().intValue();
                List<CouponGrantInfo> cgiList = new ArrayList<CouponGrantInfo>();
                long start = System.currentTimeMillis();
                for (int i = 0; i < count; i++) {
                    CouponGrant cgrant = new CouponGrant();
                    cgrant.setCouponId(coupongrantSeting.getCouponId());
                    cgrant.setCouponStatus(new Long(1));// 未发放
                    cgrant.setGrantType(new Long(61));// 不记名发放
                    if (couponStarTime != null) {
                        cgrant.setStartTime(couponStarTime);
                        cgrant.setEndTime(couponEndTime);
                    }
                    // 获取激活码
                    // cgrant.setActiveCode();
                    cgrant.setCouponIssuingId(coupongrantSeting.getCouponIssuingId());
                    cgrant.setActStatus(new Long(0));// 未激活
                    // 设置优惠券发放人
                    cgrant.setGrantCreateman(grantCreateman);
                    cgrant.setGrantCreattime(grantCreattime);
                    Long cGrantId = couponGrantSetService.inserCouponGrantOBJ(cgrant);
                    // 开始发放,并且录入流水
                    CouponGrantFlow record = new CouponGrantFlow();
                    record.setCouponGrantFlowType(
                            Long.valueOf(CouponGrantFlowStatus.GIVE_COUPONFLOWSTATUS.getType()));
                    record.setCreateDate(dateFormat.parse(dateFormat.format(now)));
                    record.setCouponGrantId(cGrantId);
                    record.setCouponId(cgrant.getCouponId());
                    record.setCouponGrantId(cGrantId);
                    record.setCouponName(couponc.getCouponName());
                    record.setOperatingPersonName("发放设置不记名发放");
                    record.setRemark("不记名发放  ，优惠券操作类型为:未发放，    优惠券编号：" + cgrant.getCouponId()
                            + "，     优惠券发放id：" + cgrant.getCouponGrantId() + "，   操作人： "
                            + record.getOperatingPersonName());
                    couponGrantFlowDAO.insert(record);

                    String CouponNo = couponGrantSetService.bearerCouponNO(
                            coupongrantSeting.getCouponIssuingId().toString(), cGrantId.toString());
                    String vCode = couponGrantSetService.bearerCouponCode(cGrantId.toString());
                    CouponGrantInfo cgi = new CouponGrantInfo();
                    cgi.setCouponGrantId(cGrantId);
                    cgi.setCouponInfoNo(CouponNo);
                    cgi.setActiveCode(vCode);
                    cgiList.add(cgi);
                }
                couponGrantSetService.bathSaveCouponGrantInfo(cgiList);
                long end = System.currentTimeMillis();
                logger.info("--------------->生成优惠券激活码，执行时间是（ms）：" + (end - start));
                // 进行状态的更改,---激活码
                if (couponc.getStatus() == 1) {
                    Coupon co = new Coupon();
                    co.setCouponId(coupongrantSeting.getCouponId());
                    co.setStatus(new Long(2));// 设置状态为已发放
                    coupondao.updateCoupon(co);
                }
                // 分页查询出优惠券
                if (couponGrantVO == null) {
                    page = new Page();
                    couponGrantVO = new CouponGrantVO();
                    couponGrantVO.setCouponIssuingId(coupongrantSeting.getCouponIssuingId());
                }
                // 查询总条数
                int pagecount = 1;
                // 总页数
                int countC = couponGrantSetService.selectCouponGrantCount(couponGrantVO);
                if (countC > 1) {
                    pagecount = (countC - 1) / page.getPageSize() + 1;
                }
                page.setRecordCount(countC);
                page.setPageCount(pagecount);
                // 分页查询
                int pageNo = page.getPageNo();// 当前查询第几页
                if (pageNo == 0)
                    pageNo = 1;// 首次查询第一页
                int pageSize = page.getPageSize(); // 每页显示记录数
                int skip = (pageNo - 1) * pageSize;
                int max = (pageNo - 1) * pageSize + pageSize;
                couponGrantVO.setStartIndex(skip);
                couponGrantVO.setEndIndex(max);
                page.setPageNo(pageNo);// 当前查询第几页
                page.setDataList(couponGrantSetService.selectCouponGrant(couponGrantVO));
            }
            logger.info(
                    "----执行优惠券发放完毕，总共耗时：" + (System.currentTimeMillis() - saveStart) / 1000 + "秒");
            return SUCCESS;
        } catch (SQLException e) {
            logger.error("saveGrantCoupon方法保存数据失败！", e);
            return ERROR;
        } catch (Exception e) {
            logger.error("saveGrantCoupon方法发放优惠券失败！", e);
            return ERROR;
        }
    }

    /**
     * 已发放优惠券列表
     * 
     * @return
     */
    public String showAlreadyGrantCouponList() {

        if (page == null) {
            page = new Page();
        }

        if (paraForQuery == null) {
            paraForQuery = new CouponGrant();
            paraForQuery.setCouponAlreadyGrant(new Coupon());
        }

        couponGrantService.queryAlreadyGrantCouponList(page, paraForQuery);

        // Map值设置进去
        super.setCouponGrantType();
        super.setCouponStatus();

        return SUCCESS;
    }

    /**
     * 查询优惠券实体
     * 
     * @return
     */
    public String selectCouponGrant() {
        try {
            if (page == null) {
                page = new Page();
            }

            // 查询总条数
            int pagecount = 1;
            // 总页数
            int count = couponGrantSetService.selectCouponGrantCount(couponGrantVO);
            if (count > 1) {
                pagecount = (count - 1) / page.getPageSize() + 1;
            }
            page.setRecordCount(count);
            page.setPageCount(pagecount);
            // 分页查询
            int pageNo = page.getPageNo();// 当前查询第几页
            if (pageNo == 0)
                pageNo = 1;// 首次查询第一页
            int pageSize = page.getPageSize(); // 每页显示记录数
            int skip = (pageNo - 1) * pageSize;
            int max = (pageNo - 1) * pageSize + pageSize;
            couponGrantVO.setStartIndex(skip);
            couponGrantVO.setEndIndex(max);
            page.setPageNo(pageNo);// 当前查询第几页
            page.setDataList(couponGrantSetService.selectCouponGrant(couponGrantVO));
            List<CouponGrantSeting> cgsList;
            CouponGrantSeting cgs = new CouponGrantSeting();
            cgs.setCouponIssuingId(couponGrantVO.getCouponIssuingId());
            cgsList = couponGrantSetService.selectByCouponGrant(cgs);
            if (cgsList != null && cgsList.size() > 0) {
                coupongrantSeting = cgsList.get(0);
            }

        } catch (Exception e) {
            logger.error("selectCouponGrant方法查询优惠券异常" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 导出
     * 
     * @return
     */
    public String exportCouponGrant() {
        String[] titel = {"券ID", "激活状态", "优惠券号", "优惠券激活码", "有效时间", "关联会员", "激活时间", "使用状态", "使用时间"};
        try {
            List<CouponGrantVO> listData =
                    couponGrantSetService.selectCouponGrantALL(couponGrantVO);
            couponGrantSetService.exportExcel(titel, listData);
            File file = new File(ConfigurationUtil.getString("exportExcelPath") + File.separator
                    + "bearerCoupon.xls");
            FileInputStream f = new FileInputStream(file);
            byte[] fb = new byte[f.available()];
            f.read(fb);
            ServletActionContext.getResponse().setHeader("Content-disposition",
                    "attachment; filename="
                            + new String("不记名优惠券表.xls".getBytes("gb2312"), "iso8859-1"));
            ByteArrayInputStream bais = new ByteArrayInputStream(fb);
            int b;
            while ((b = bais.read()) != -1) {
                ServletActionContext.getResponse().getOutputStream().write(b);
            }
            ServletActionContext.getResponse().getOutputStream().flush();

            f.close();
            bais.close();
        } catch (Exception e) {
            logger.error("exportCouponGrant方法导出数据失败" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 校验提交的数据
     * 
     * @return
     */
    public String checkCouponTime() {
        String strtime = this.getRequest().getParameter("startime");
        String endtime = this.getRequest().getParameter("endtime");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date starDate = null;
        Date endDate = null;
        try {
            starDate = sdf.parse(strtime);
            endDate = sdf.parse(endtime);
        } catch (ParseException e1) {
            logger.error("checkCouponTime方法转换时间异常", e1);
            return ERROR;
        }
        String couponId = this.getRequest().getParameter("couponId");
        Coupon co = new Coupon();
        co.setCouponId(new Long(couponId));
        try {
            co = couponService.selectCoupon(co);
        } catch (Exception e) {
            logger.error("checkCouponTime中couponService.selectCoupon查询优惠券规则失败", e);
            return ERROR;
        }
        if (starDate != null) {
            if (co.getEndtime() != null) {
                if (starDate.getTime() > co.getEndtime().getTime()) {
                    cheCoupon_discription = "2";// 注册发放开始时间必须小于规则结束时间
                    this.writeJson(cheCoupon_discription);
                    return null;
                }
            }
        }
        if (endDate != null) {

            if (co.getEndtime() != null) {
                if (endDate.getTime() > co.getEndtime().getTime()) {
                    cheCoupon_discription = "4";// 注册发放开始时间必须小于规则结束时间
                    this.writeJson(cheCoupon_discription);
                    return null;
                }
            }
        }
        cheCoupon_discription = "0";
        this.writeJson(cheCoupon_discription);
        return null;
    }

    public CouponGrant getParaForQuery() {
        return paraForQuery;
    }

    public void setParaForQuery(CouponGrant paraForQuery) {
        this.paraForQuery = paraForQuery;
    }

    public CouponGrantService getCouponGrantService() {
        return couponGrantService;
    }

    public void setCouponGrantService(CouponGrantService couponGrantService) {
        this.couponGrantService = couponGrantService;
    }

    @Override
    public Object getModel() {
        return null;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public String getCheCoupon_discription() {
        return cheCoupon_discription;
    }

    public void setCheCoupon_discription(String cheCoupon_discription) {
        this.cheCoupon_discription = cheCoupon_discription;
    }

    public List<CouponGrantVO> getGrantList() {
        return grantList;
    }

    public void setGrantList(List<CouponGrantVO> grantList) {
        this.grantList = grantList;
    }

    public List<UserLeve> getUserLeve() {
        return userLeve;
    }

    public void setUserLeve(List<UserLeve> userLeve) {
        this.userLeve = userLeve;
    }

    public List<UserLoginId> getUserList() {
        return userList;
    }

    public void setUserList(List<UserLoginId> userList) {
        this.userList = userList;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public CouponGrantVO getCouponGrantVO() {
        return couponGrantVO;
    }

    public void setCouponGrantVO(CouponGrantVO couponGrantVO) {
        this.couponGrantVO = couponGrantVO;
    }

    public CouponGrantSeting getCoupongrantSeting() {
        return coupongrantSeting;
    }

    public void setCoupongrantSeting(CouponGrantSeting coupongrantSeting) {
        this.coupongrantSeting = coupongrantSeting;
    }

}
