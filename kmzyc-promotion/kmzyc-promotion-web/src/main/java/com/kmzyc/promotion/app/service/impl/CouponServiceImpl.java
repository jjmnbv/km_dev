package com.kmzyc.promotion.app.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.dao.CouponDAO;
import com.kmzyc.promotion.app.dao.CouponGrantDAO;
import com.kmzyc.promotion.app.dao.CouponProductDAO;
import com.kmzyc.promotion.app.enums.CouponGrantType;
import com.kmzyc.promotion.app.enums.CouponRuleStatus;
import com.kmzyc.promotion.app.service.CouponService;
import com.kmzyc.promotion.app.vobject.Coupon;
import com.kmzyc.promotion.app.vobject.CouponExample;
import com.kmzyc.promotion.app.vobject.CouponProduct;
import com.kmzyc.promotion.app.vobject.CouponProductExample;
import com.kmzyc.user.remote.service.CustomerRemoteService;

@Repository("CouponService")
@SuppressWarnings("unchecked")
public class CouponServiceImpl implements CouponService {

    // 日志记录
    private static final Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);
    @Resource
    private CustomerRemoteService customerRemoteService;
    /**
     * 优惠券DAO
     */
    @Resource(name = "coupondao")
    private CouponDAO coupondao;

    /**
     * 优惠券产品dao
     */
    @Resource(name = "couponProductdao")
    private CouponProductDAO couponProductdao;

    @Resource(name = "couponGrantdao")
    private CouponGrantDAO couponGrantdao;

    // 优惠券名称重复问题验证
    @Override
    public int selectCouponName(Map<String, String> map) throws Exception {
        List<Coupon> list = coupondao.selectCouponName(map);
        if (list == null || list.size() == 0) {
            return 2;
        } else {
            return 1;
        }

    }

    // 优惠券规则定时任务
    @Override
    public void creatJob(Long couponId) throws Exception {
        createStopJob(couponId);
    }

    // 优惠券定时任务处理
    private void createStopJob(Long couponId) throws SQLException {
        Scheduler scheduler = null;
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try {
            scheduler = schedulerFactory.getScheduler();
            scheduler.deleteJob("jobStop_" + couponId, "coupon_jobStop");
        } catch (SchedulerException e1) {
            logger.error("定时器创建异常", e1);
            return;
        }
        // 注入定时任务需要的dao service
        JobDetail jobDetailStop =
                new JobDetail("jobStop_" + couponId, "coupon_jobStop", StopJoinActivityJob.class);
        jobDetailStop.getJobDataMap().put("coupondao", coupondao);

        SimpleTrigger simpleTriggerStop =
                new SimpleTrigger("triggerStop_" + couponId, "tgroup_luckDrawStop");
        // 查询规则
        Coupon coupon = new Coupon();
        coupon.setCouponId(couponId);
        coupon = coupondao.selectCoupon(coupon);
        // 注入定时任务时间
        simpleTriggerStop.setStartTime(coupon.getEndtime());
        simpleTriggerStop.setRepeatInterval(1000);
        simpleTriggerStop.setRepeatCount(1);

        try {
            scheduler.scheduleJob(jobDetailStop, simpleTriggerStop);// ④ 注册并进行调度
            scheduler.start();// ⑤调度启动
        } catch (SchedulerException e) {
            logger.error("定时器调度异常", e);
        }
    }

    // 修改优惠券规则
    @Override
    public int updateCoupon(Coupon coupon, List<CouponProduct> couponProduct, String categoryIds)
            throws Exception {
        int rows = 0;
        CouponProduct cateGroyProduct = new CouponProduct();
        // 修改优惠券规则
        rows = coupondao.updateCoupon(coupon);
        // 对于couponproduct关联表的修改先删后插
        if (!StringUtils.isEmpty(categoryIds)) // 类别的处理
        {
            CouponProductExample proex = new CouponProductExample();
            proex.createCriteria().andCouponIdEqualTo(coupon.getCouponId())
                    .andRelatedTypeEqualTo(new BigDecimal(2));
            couponProductdao.deleteByExample(proex); // 删
            String[] category = categoryIds.split(",");
            // 插
            for (String categoryId : category) {
                cateGroyProduct.setCouponId(coupon.getCouponId());
                cateGroyProduct.setRelatedType(new BigDecimal(2));
                cateGroyProduct.setRelatedId(categoryId.trim());
                couponProductdao.insert(cateGroyProduct);
            }
        }
        // 空类别
        else {
            CouponProductExample proex = new CouponProductExample();
            proex.createCriteria().andCouponIdEqualTo(coupon.getCouponId())
                    .andRelatedTypeEqualTo(new BigDecimal(2));
            couponProductdao.deleteByExample(proex); // 删
        }
        // 传过来的是空的产品关联
        if (couponProduct == null) {
            // s删
            CouponProductExample proex2 = new CouponProductExample();
            proex2.createCriteria().andCouponIdEqualTo(coupon.getCouponId())
                    .andRelatedTypeEqualTo(new BigDecimal(1));
            couponProductdao.deleteByExample(proex2); // 删
        } else {
            CouponProductExample proex2 = new CouponProductExample();
            proex2.createCriteria().andCouponIdEqualTo(coupon.getCouponId())
                    .andRelatedTypeEqualTo(new BigDecimal(1));
            couponProductdao.deleteByExample(proex2); // 删
            // 插
            for (CouponProduct couponProducts : couponProduct) {
                if (couponProducts != null) {
                    couponProducts.setCouponId(coupon.getCouponId());
                    // 设置关联类型为产品类型
                    couponProducts.setRelatedType(new BigDecimal(1));
                    couponProductdao.insert(couponProducts);
                }
            }
        }
        return rows;
    }

    // 新增优惠券规则
    @Override
    public int insertRule(Coupon coupon, List<CouponProduct> couponProduct, String categoryIds)
            throws Exception {
        Integer couponId = 0;
        CouponProduct cateGroyProduct = new CouponProduct();
        // 设置规则默认状态和创建时间
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        coupon.setCreatetime(sim.parse(sim.format(new Date())));
        // 插入优惠quan表、产品表
        coupon.setStatus(Long.parseLong(CouponRuleStatus.NOTGIVE_COUPONSTATUS.getType()));
        couponId = coupondao.insertRule(coupon);
        if (couponProduct != null && couponProduct.size() > 0) {
            for (CouponProduct couponProducts : couponProduct) {
                couponProducts.setCouponId(new Long(couponId));
                // 设置关联类型为产品类型
                couponProducts.setRelatedType(new BigDecimal(1));
                couponProductdao.insert(couponProducts);
            }
        }
        // 插入关联的类目,非空
        if (!StringUtils.isEmpty(categoryIds)) {
            String[] category = categoryIds.split(",");
            for (String categoryId : category) {
                cateGroyProduct.setCouponId(new Long(couponId));
                cateGroyProduct.setRelatedType(new BigDecimal(2));
                cateGroyProduct.setRelatedId(categoryId);
                couponProductdao.insert(cateGroyProduct);
            }
        }
        return couponId;
    }

    // 分页查询优惠券规则
    @Override
    public Page selectByCondition(Page page, Coupon coupon) throws Exception {
        // 获取总条数
        int count = coupondao.selectCountByCondition(coupon);
        page.setPageCount(count);
        int pageNo = page.getPageNo();// 当前查询第几页
        if (pageNo == 0)
            pageNo = 1;// 首次查询第一页
        int pageSize = page.getPageSize(); // 每页显示记录数
        int skip = (pageNo - 1) * pageSize + 1;
        int max = (pageNo - 1) * pageSize + pageSize;
        // 设置查询索引
        coupon.setSkip(skip);
        coupon.setMax(max);
        page.setDataList(coupondao.selectByCondition(coupon));
        page.setPageNo(pageNo);// 当前查询第几页
        page.setRecordCount(count);
        return page;
    }

    /**
     * 修改优惠券方法
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editCoupon(Coupon coupon, List<CouponProduct> couponProduct, String categoryIds)
            throws SQLException {
        CouponProduct cateGroyProduct = new CouponProduct();
        // 判断优惠券对应会员
        if (coupon.getCustomLeveid() == null) {
            coupon.setCustomLeveid("");
        }
        try {
            if (!(coupon.getCouponGivetypeId() + "")
                    .equals(CouponGrantType.POINTEXCUT_COUPONGRANTTYPE.getType())) {
                // 设置优惠券有效时间
                coupon.setCouponValidDay(new BigDecimal(
                        this.getCouponValiday(coupon.getStarttime(), coupon.getEndtime())));
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        // 对于coupon实体的修改
        CouponExample exa = new CouponExample();
        exa.createCriteria().andCouponIdEqualTo(coupon.getCouponId());
        coupondao.updateByExampleSelective(coupon, exa);
        // 对于couponproduct关联表的修改先删后插
        if (!StringUtils.isEmpty(categoryIds)) // 类别的处理
        {
            CouponProductExample proex = new CouponProductExample();
            proex.createCriteria().andCouponIdEqualTo(coupon.getCouponId())
                    .andRelatedTypeEqualTo(new BigDecimal(2));
            couponProductdao.deleteByExample(proex); // 删
            String[] category = categoryIds.split(",");
            // 插
            for (String categoryId : category) {
                cateGroyProduct.setCouponId(coupon.getCouponId());
                cateGroyProduct.setRelatedType(new BigDecimal(2));
                cateGroyProduct.setRelatedId(categoryId.trim());
                couponProductdao.insert(cateGroyProduct);
            }
        }
        // 空类别
        else {
            CouponProductExample proex = new CouponProductExample();
            proex.createCriteria().andCouponIdEqualTo(coupon.getCouponId())
                    .andRelatedTypeEqualTo(new BigDecimal(2));
            couponProductdao.deleteByExample(proex); // 删
        }
        // 传过来的是空的产品关联
        if (couponProduct == null) {
            // s删
            CouponProductExample proex2 = new CouponProductExample();
            proex2.createCriteria().andCouponIdEqualTo(coupon.getCouponId())
                    .andRelatedTypeEqualTo(new BigDecimal(1));
            couponProductdao.deleteByExample(proex2); // 删
        } else {
            CouponProductExample proex2 = new CouponProductExample();
            proex2.createCriteria().andCouponIdEqualTo(coupon.getCouponId())
                    .andRelatedTypeEqualTo(new BigDecimal(1));
            couponProductdao.deleteByExample(proex2); // 删
            // 插
            for (CouponProduct couponProducts : couponProduct) {
                if (couponProducts != null) {
                    couponProducts.setCouponId(coupon.getCouponId());
                    // 设置关联类型为产品类型
                    couponProducts.setRelatedType(new BigDecimal(1));
                    couponProductdao.insert(couponProducts);
                }
            }
        }
    }

    /**
     * 查询优惠券列表
     * 
     * @return
     */
    @Override
    public Page queryCouponList(Page pageParam, Coupon coupon) throws Exception {
        int pageNo = pageParam.getPageNo();// 当前查询第几页
        if (pageNo == 0)
            pageNo = 1;// 首次查询第一页
        int pageSize = pageParam.getPageSize(); // 每页显示记录数
        int skip = (pageNo - 1) * pageSize + 1;
        int max = (pageNo - 1) * pageSize + pageSize;
        Page page = null;
        try {
            coupon.setSkip(skip);
            coupon.setMax(max);
            page = coupondao.selectPageByVo(pageParam, coupon);
            page.setPageNo(pageNo);// 当前查询第几页
        } catch (SQLException e) {
            logger.error("查询优惠券列表失败：", e);
            throw e;
        }
        return page;
    }

    /**
     * 根据优惠券id 查询
     */
    @Override
    public Coupon queryCouponById(Long couponId) throws Exception {
        Coupon coupon = coupondao.selectByPrimaryKey(new Long(couponId));
        return coupon;
    }

    /**
     * 计算两个日期之间的天数
     * 
     * @return
     */
    private long getCouponValiday(Date startDate, Date endDate) throws Exception {

        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        long startTime = sim.parse(sim.format(startDate)).getTime();
        long endTime = sim.parse(sim.format(endDate)).getTime();
        long validay = (endTime - startTime) / (1000 * 60 * 60 * 24);
        return validay;
    }

    public CouponDAO getCoupondao() {
        return coupondao;
    }

    public void setCoupondao(CouponDAO coupondao) {
        this.coupondao = coupondao;
    }

    public CouponProductDAO getCouponProductdao() {
        return couponProductdao;
    }

    public void setCouponProductdao(CouponProductDAO couponProductdao) {
        this.couponProductdao = couponProductdao;
    }

    public CouponGrantDAO getCouponGrantdao() {
        return couponGrantdao;
    }

    public void setCouponGrantdao(CouponGrantDAO couponGrantdao) {
        this.couponGrantdao = couponGrantdao;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delCoupon(Coupon coupon) throws SQLException {
        // 先删除产品表中的
        CouponProductExample example = new CouponProductExample();
        example.createCriteria().andCouponIdEqualTo(coupon.getCouponId());
        couponProductdao.deleteByExample(example);
        coupondao.delCouponById(coupon);
    }

    /**
     * 测试优惠券名称是否重复
     */
    @Override
    public int checkCouponNameRepeat(String couponName) throws SQLException {
        CouponExample exa = new CouponExample();
        exa.createCriteria().andCouponNameEqualTo(couponName);
        List<Coupon> couponList = coupondao.selectByExample(exa);
        if (couponList == null || couponList.size() == 0) {
            return 2;
        } else {
            return 1;
        }
    }

    /**
     * 查询以过期的优惠券
     */
    @Override
    public List<Coupon> queryExpiredCoupon() throws SQLException {
        List<Coupon> expiredCoupon = new ArrayList<Coupon>();
        expiredCoupon = coupondao.selectExpiredCoupon();
        return expiredCoupon;
    }

    @Override
    public void updateCouponStatus(Coupon coupon) throws SQLException {
        coupondao.updateCouponStatus(coupon);
    }

    /**
     * 根据couponId 更新coupon相关
     */
    @Override
    public int updateCouponById(Coupon coupon) throws SQLException {
        int result = coupondao.updateValide(coupon);
        return result;
    }

    // 查询优惠券规则
    @Override
    public Coupon selectCoupon(Coupon coupon) throws Exception {
        return coupondao.selectCoupon(coupon);
    }


}
