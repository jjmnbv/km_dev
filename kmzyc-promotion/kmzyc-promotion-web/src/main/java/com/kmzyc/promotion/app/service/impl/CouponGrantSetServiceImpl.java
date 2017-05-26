package com.kmzyc.promotion.app.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.dao.CouponDAO;
import com.kmzyc.promotion.app.dao.CouponGrantFlowDAO;
import com.kmzyc.promotion.app.dao.CouponGrantSetDAO;
import com.kmzyc.promotion.app.service.CouponGrantService;
import com.kmzyc.promotion.app.service.CouponGrantSetService;
import com.kmzyc.promotion.app.service.CouponService;
import com.kmzyc.promotion.app.vobject.Coupon;
import com.kmzyc.promotion.app.vobject.CouponGrant;
import com.kmzyc.promotion.app.vobject.CouponGrantInfo;
import com.kmzyc.promotion.app.vobject.CouponGrantSetOBJ;
import com.kmzyc.promotion.app.vobject.CouponGrantSeting;
import com.kmzyc.promotion.app.vobject.CouponGrantVO;
import com.kmzyc.promotion.app.vobject.UserLeve;
import com.kmzyc.promotion.app.vobject.UserLoginId;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.dataobject.UserInfoDO;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Repository("couponGrantSetService")
public class CouponGrantSetServiceImpl implements CouponGrantSetService {
    // 日志记录
    private static final Logger logger = LoggerFactory.getLogger(CouponGrantSetServiceImpl.class);
    @Resource(name = "couponGrantSetdao")
    private CouponGrantSetDAO couponGrantSetdao;
    @Resource(name = "CouponGrantService")
    private CouponGrantService couponGrantService;
    @Resource(name = "CouponService")
    private CouponService couponService;
    @Resource
    private CouponGrantFlowDAO couponGrantFlowDAO;
    @Resource
    private CouponDAO coupondao;

    @Override
    public Integer saveCouponGrantSet(CouponGrantSetOBJ cgso) throws SQLException {
        couponGrantSetdao.saveCouponGrantSet(cgso);
        return 1;
    }

    @Override
    public List<CouponGrantSeting> selectCouponGrantVOList(CouponGrantSeting cgs)
            throws SQLException {
        return couponGrantSetdao.selectCouponGrantVOList(cgs);
    }

    @Override
    public Integer selectCount(CouponGrantSeting cgs) throws SQLException {

        return couponGrantSetdao.selectCount(cgs);
    }

    @Override
    public Integer updateCouponGrant(CouponGrantSeting cgs) throws SQLException {

        return couponGrantSetdao.updateCouponGrant(cgs);
    }

    @Override
    public List<CouponGrantSeting> selectByCouponGrant(CouponGrantSeting cgs) throws SQLException {

        return couponGrantSetdao.selectByCouponGrant(cgs);
    }

    @Override
    public Integer deleteCouponGrant(CouponGrantSeting cgs) throws SQLException {

        return couponGrantSetdao.deleteCouponGrant(cgs);
    }

    @Override
    public Long saveGrantCoupon(CouponGrantSeting cgs) throws SQLException {

        return couponGrantSetdao.saveGrantCoupon(cgs);
    }

    @Override
    public Long inserCouponGrantOBJ(CouponGrant cg) throws SQLException {

        return couponGrantSetdao.inserCouponGrantOBJ(cg);
    }

    @Override
    public List<UserLoginId> selectLoginIdArr(Integer leveId) throws SQLException {
        return couponGrantSetdao.selectLoginIdByLeveId(leveId);
    }

    @Override
    public List<UserLeve> selectUserLeveByLeveId(Integer leveId) throws SQLException {

        return couponGrantSetdao.selectUserLeveByLeveId(leveId);
    }

    @Override
    public List<UserLoginId> selectUserByLoginId(Integer loginId) throws SQLException {

        return couponGrantSetdao.selectUserByLoginId(loginId);
    }

    @Override
    public List<CouponGrantVO> selectCouponGrant(CouponGrantVO cgv) throws SQLException {
        return couponGrantSetdao.selectCouponGrant(cgv);
    }

    @Override
    public Integer selectCouponGrantCount(CouponGrantVO cgv) throws SQLException {
        return couponGrantSetdao.selectCouponGrantCount(cgv);
    }

    @Override
    public List<CouponGrantVO> selectCouponGrantALL(CouponGrantVO cgv) throws SQLException {
        return couponGrantSetdao.selectCouponGrantALL(cgv);
    }

    @Override
    public void exportExcel(String[] strarr, List<CouponGrantVO> list) throws Exception {
        OutputStream ops = null;
        try {
            String filePath = ConfigurationUtil.getString("exportExcelPath") + File.separator
                    + "bearerCoupon.xls";
            // String filePath = "F:/bearerCoupon.xls";
            // 创建Excel工作薄
            WritableWorkbook wwb;
            ops = new FileOutputStream(filePath);
            wwb = Workbook.createWorkbook(ops);
            // 添加第一个工作表并设置第一个Sheet的名字
            WritableSheet sheet = wwb.createSheet("不记名优惠券表", 0);

            Label label;
            WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            WritableCellFormat wcf = new WritableCellFormat(wf);
            wcf.setBackground(Colour.YELLOW2);
            wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
            // 将列标题循环添加到Label中
            for (int i = 0; i < strarr.length; i++) {
                label = new Label(i, 0, strarr[i], wcf);
                sheet.addCell(label);
            }
            wcf = new WritableCellFormat();
            wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < list.size(); i++) {
                CouponGrantVO cgvo = list.get(i);
                label = new Label(0, i + 1, cgvo.getCouponGrantId().toString(), wcf);
                sheet.addCell(label);
                if (cgvo.getActStatus() == 0) {
                    label = new Label(1, i + 1, "未激活", wcf);
                    sheet.addCell(label);
                } else {
                    label = new Label(1, i + 1, "已激活", wcf);
                    sheet.addCell(label);
                }
                label = new Label(2, i + 1, cgvo.getCouponInfoNo().toString(), wcf); // 优惠券号
                sheet.addCell(label);
                label = new Label(3, i + 1, cgvo.getActiveCode().toString(), wcf); // 优惠券激活码
                sheet.addCell(label);
                String StarTimetoEndTime = null;
                if (cgvo.getStarttime() != null) {
                    StarTimetoEndTime = sdf.format(cgvo.getStarttime());
                    StarTimetoEndTime =
                            StarTimetoEndTime + "  至    " + sdf.format(cgvo.getEndtime());
                }

                //
                label = new Label(4, i + 1, StarTimetoEndTime, wcf);
                sheet.addCell(label);
                label = new Label(5, i + 1, cgvo.getCustomerName(), wcf);
                sheet.addCell(label);
                String actTime = null;
                if (cgvo.getActTime() != null) {
                    actTime = sdf.format(cgvo.getActTime());
                }
                label = new Label(6, i + 1, actTime, wcf);
                sheet.addCell(label);
                if (cgvo.getCouponStatus() == 1) {
                    label = new Label(7, i + 1, "未发放", wcf);
                    sheet.addCell(label);
                } else if (cgvo.getCouponStatus() == 3) {
                    label = new Label(7, i + 1, "未使用", wcf);
                    sheet.addCell(label);
                } else if (cgvo.getCouponStatus() == 2) {
                    label = new Label(7, i + 1, "已发放", wcf);
                    sheet.addCell(label);
                } else if (cgvo.getCouponStatus() == 4) {
                    label = new Label(7, i + 1, "已使用", wcf);
                    sheet.addCell(label);
                } else if (cgvo.getCouponStatus() == 5) {
                    label = new Label(7, i + 1, "已过期", wcf);
                    sheet.addCell(label);
                } else if (cgvo.getCouponStatus() == 6) {
                    label = new Label(7, i + 1, "已作废", wcf);
                    sheet.addCell(label);
                } else if (cgvo.getCouponStatus() == 7) {
                    label = new Label(7, i + 1, "已冻结", wcf);
                    sheet.addCell(label);
                }
                String useTime = null;
                if (cgvo.getUseTime() != null) {
                    useTime = sdf.format(cgvo.getUseTime());
                }
                label = new Label(8, i + 1, useTime, wcf);
                sheet.addCell(label);
            }
            wwb.write();
            wwb.close();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (ops != null) {
                ops.flush();
                ops.close();
            }
        }


    }

    @Override
    public String bearerCouponNO(String couponIssuingId, String couponGrangId) throws Exception {
        int len = couponIssuingId.length();
        if (len < 5) {
            for (int i = 0; i < 5 - len; i++) {
                couponIssuingId = "0" + couponIssuingId;
            }
        }
        if (len > 5) {
            couponIssuingId = couponIssuingId.substring(couponIssuingId.length() - 5,
                    couponIssuingId.length());
        }
        int lenCG = couponGrangId.length();
        if (lenCG < 7) {
            for (int i = 0; i < 7 - lenCG; i++) {
                couponGrangId = "0" + couponGrangId;
            }
        }
        if (lenCG > 7) {
            couponGrangId =
                    couponGrangId.substring(couponGrangId.length() - 7, couponGrangId.length());
        }
        return couponIssuingId + couponGrangId;
    }

    @Override
    public String bearerCouponCode(String couponGrangId) throws Exception {
        int couponGrangIdInt = Integer.parseInt(couponGrangId);
        String couponGrangIdStr = Integer.toHexString(couponGrangIdInt).toString();
        int len = couponGrangIdStr.length();
        if (len < 6) {
            for (int i = 0; i < 6 - len; i++) {
                couponGrangIdStr = "0" + couponGrangIdStr;
            }
        }
        if (len > 6) {
            couponGrangIdStr = couponGrangIdStr.substring(couponGrangIdStr.length() - 6,
                    couponGrangIdStr.length());
        }
        String vCode = Integer.toHexString(new Random().nextInt()).toString();
        int lenvcode = vCode.length();
        if (lenvcode > 6) {
            vCode = vCode.substring(vCode.length() - 6, vCode.length());
        }
        if (lenvcode < 6) {
            for (int i = 0; i < 6 - lenvcode; i++) {
                vCode = "0" + vCode;
            }
        }
        char[] carCode = new char[12];
        carCode[0] = couponGrangIdStr.charAt(0);
        carCode[1] = vCode.charAt(0);
        carCode[2] = couponGrangIdStr.charAt(1);
        carCode[3] = vCode.charAt(1);
        carCode[4] = couponGrangIdStr.charAt(2);
        carCode[5] = vCode.charAt(2);
        carCode[6] = couponGrangIdStr.charAt(3);
        carCode[7] = vCode.charAt(3);
        carCode[8] = couponGrangIdStr.charAt(4);
        carCode[9] = vCode.charAt(4);
        carCode[10] = couponGrangIdStr.charAt(5);
        carCode[11] = vCode.charAt(5);
        return String.copyValueOf(carCode);
    }

    @Override
    public Long saveVCode(CouponGrantInfo cgi) throws SQLException {

        return couponGrantSetdao.saveVCode(cgi);
    }

    @Override
    public Boolean bathSaveCouponGrantInfo(List<CouponGrantInfo> cgiList) throws SQLException {

        return couponGrantSetdao.bathSaveCouponGrantInfo(cgiList);
    }

    @Override
    public Page selectCouponByStatus(Page page, Coupon coupon) throws SQLException {
        // 查询总条数
        int pagecount = 1;
        // 获取总条数
        int count = couponGrantSetdao.selectCountCoupon(coupon);
        if (count > 1) {
            pagecount = (count - 1) / page.getPageSize() + 1;
        }
        page.setRecordCount(count);
        page.setPageCount(pagecount);
        int pageNo = page.getPageNo();// 当前查询第几页
        if (pageNo == 0)
            pageNo = 1;// 首次查询第一页
        int pageSize = page.getPageSize(); // 每页显示记录数
        int skip = (pageNo - 1) * pageSize + 1;
        int max = (pageNo - 1) * pageSize + pageSize;
        // 设置查询索引
        coupon.setSkip(skip);
        coupon.setMax(max);
        page.setDataList(couponGrantSetdao.selectCouponList(coupon));
        page.setPageNo(pageNo);// 当前查询第几页
        return page;
    }

    @Override
    public List<CouponGrantSeting> selectBytime(CouponGrantSeting cgs) throws SQLException {

        return couponGrantSetdao.qureyBystarAndEndTime(cgs);
    }

    @Override
    public Boolean batchUpdateCouponG(List<CouponGrantSeting> listCgs) throws SQLException {
        return couponGrantSetdao.batchUpdateCouponGrant(listCgs);
    }

    @Override
    public void createJob(long couponIssuingId) throws SQLException {
        createStartJob(couponIssuingId);
        createStopJob(couponIssuingId);

    }

    private void createStartJob(long couponIssuingId) throws SQLException {
        Scheduler scheduler = null;
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try {
            scheduler = schedulerFactory.getScheduler(); // 取得任务调度
            scheduler.deleteJob("jobStart_" + couponIssuingId, "couponGrant_job");
        } catch (SchedulerException e1) {
            logger.error("定时器创建异常", e1);
        }
        JobDetail jobDetailStart = new JobDetail("jobStart_" + couponIssuingId, "couponGrant_job",
                StartActivityJob.class);
        jobDetailStart.getJobDataMap().put("couponGrantSetService", this);

        SimpleTrigger simpleTriggerStart =
                new SimpleTrigger("triggerStart_" + couponIssuingId, "tgroup_couponGrant");
        CouponGrantSeting cgs = new CouponGrantSeting();
        cgs.setCouponIssuingId(couponIssuingId);
        List<CouponGrantSeting> couponGrantSetinglsit = couponGrantSetdao.selectByCouponGrant(cgs);
        if (couponGrantSetinglsit != null && couponGrantSetinglsit.size() > 0) {
            simpleTriggerStart.setStartTime(couponGrantSetinglsit.get(0).getIssuingStartTime());
            simpleTriggerStart.setRepeatInterval(1000);
            simpleTriggerStart.setRepeatCount(1);
            try {
                scheduler.scheduleJob(jobDetailStart, simpleTriggerStart);// ④ 注册并进行调度
                scheduler.start();// ⑤调度启动
            } catch (SchedulerException e) {
                logger.error("定时器调度异常", e);
            }
        } else {
            logger.error("查询记录失败");
        }



    }

    private void createStopJob(long couponIssuingId) throws SQLException {
        Scheduler scheduler = null;
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try {
            scheduler = schedulerFactory.getScheduler();
            scheduler.deleteJob("jobStop_" + couponIssuingId, "couponGrant_job");
        } catch (SchedulerException e1) {
            logger.error("定时器创建异常", e1);
        }
        JobDetail jobDetailStop = new JobDetail("jobStop_" + couponIssuingId, "couponGrant_job",
                StopCouponActivityJob.class);
        jobDetailStop.getJobDataMap().put("couponGrantSetService", this);

        SimpleTrigger simpleTriggerStart =
                new SimpleTrigger("triggerStop_" + couponIssuingId, "tgroup_couponGrant");
        CouponGrantSeting cgs = new CouponGrantSeting();
        cgs.setCouponIssuingId(couponIssuingId);
        List<CouponGrantSeting> couponGrantSetinglsit = couponGrantSetdao.selectByCouponGrant(cgs);
        if (couponGrantSetinglsit != null && couponGrantSetinglsit.size() > 0) {
            simpleTriggerStart.setStartTime(couponGrantSetinglsit.get(0).getIssuingEndTime());
            simpleTriggerStart.setRepeatInterval(1000);
            simpleTriggerStart.setRepeatCount(1);
            try {
                scheduler.scheduleJob(jobDetailStop, simpleTriggerStart);// ④ 注册并进行调度
                scheduler.start();// ⑤调度启动
            } catch (SchedulerException e) {
                logger.error("定时器调度异常", e);
            }
        } else {
            logger.error("查询记录失败");
        }
    }

    private void createCouponGrantTimeOutJOB(long couponGrantId) throws SQLException {
        Scheduler scheduler = null;
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try {
            scheduler = schedulerFactory.getScheduler();
            scheduler.deleteJob("jobTimeOut_" + couponGrantId, "couponGrantTimeOut_job");
        } catch (SchedulerException e1) {
            logger.error("定时器创建异常", e1);
        }
        JobDetail jobDetailStop = new JobDetail("jobTimeOut_" + couponGrantId,
                "couponGrantTimeOut_job", CouponGrantTimeOutJOB.class);
        jobDetailStop.getJobDataMap().put("couponGrantSetService", this);
        jobDetailStop.getJobDataMap().put("couponGrantService", couponGrantService);
        jobDetailStop.getJobDataMap().put("couponGrantFlowDAO", couponGrantFlowDAO);
        jobDetailStop.getJobDataMap().put("couponService", couponService);


        SimpleTrigger simpleTriggerStart =
                new SimpleTrigger("triggerTimeOut_" + couponGrantId, "tgroup_TimeOut");
        CouponGrant cgs = new CouponGrant();
        cgs.setCouponGrantId(couponGrantId);
        CouponGrant couponGrant = couponGrantService.selectCouponGrantById(cgs);
        if (couponGrant != null) {
            simpleTriggerStart.setStartTime(couponGrant.getEndTime());
            simpleTriggerStart.setRepeatInterval(1000);
            simpleTriggerStart.setRepeatCount(1);
            try {
                scheduler.scheduleJob(jobDetailStop, simpleTriggerStart);// ④ 注册并进行调度
                scheduler.start();// ⑤调度启动
            } catch (SchedulerException e) {
                logger.error("定时器调度异常", e);
            }
        } else {
            logger.error("查询记录失败");
        }
    }

    // 查询优惠券规则
    public Coupon selectCoupon(Coupon coupon) throws Exception {
        return coupondao.selectCoupon(coupon);
    }

    @Override
    public Integer updateGrantSeting(CouponGrantSeting cgs) throws SQLException {

        return couponGrantSetdao.updateGrantSeting(cgs);
    }

    @Override
    public void createTimeOutJOB(long couponGrantId) throws SQLException {
        this.createCouponGrantTimeOutJOB(couponGrantId);

    }

    @Override
    public List<UserInfoDO> findUserById(UserInfoDO userCondition) throws SQLException {
        return couponGrantSetdao.selectUserById(userCondition);
    }
}
