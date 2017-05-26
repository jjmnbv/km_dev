package com.kmzyc.b2b.ajax;

// package com.kmzyc.b2b.ajax;
//
//import com.km.framework.action.BaseAction;
// import com.kmzyc.b2b.model.RefData;
// import com.kmzyc.b2b.service.WxReservationService;
// import com.kmzyc.b2b.util.rebate.UserIdEncode;
// import com.kmzyc.b2b.vo.ReturnResult;
// import com.kmzyc.framework.constants.Constants;
// import com.kmzyc.framework.constants.InterfaceResultCode;
// import com.kmzyc.zkconfig.ConfigurationUtil;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.struts2.ServletActionContext;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Controller;
//
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//
//import redis.clients.jedis.JedisCluster;
//
///**
// * 移动充值话费
// *
// * @author Administrator
// */
//@SuppressWarnings({"serial", "unchecked", "unused"})
//@Controller
//@Scope("prototype")
//public class PhoneBillInterfaceAction extends BaseAction {
//    // private static Logger logger = Logger.getLogger(PhoneBillInterfaceAction.class);
//    private static Logger logger = LoggerFactory.getLogger(PhoneBillInterfaceAction.class);
//
//    // 返回至页面的对象
//    private ReturnResult returnResult;
//    @Resource
//    private WxReservationService wxReservationService;
//
//    private RefData refData;
//
//    @Resource(name = "jedisCluster")
//    private JedisCluster jedisCluster;
//
//    private static final BigDecimal STARTCOUNT = new BigDecimal(113267);
//
//    private String myInvitHref;
//
//    // private String portalPath = ConfigurationUtil.getString("portalPath");
//
//    /**
//     * 获取现在的剩余个数
//     */
//    public String getNowCount() throws Exception {
//        Map<String, Object> map = new HashMap<>();
//        Date nowDate = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        map.put("nowTime", sdf.format(nowDate));
//        List<RefData> refList = wxReservationService.findRefDataList(map);
//        refData = this.getRefData();
//        // 当前时间在最开始的之前或者最末尾的之后
//        if (refList.size() == 1) {
//            // logger.info("时间范围超过了表中的最大值或最小值");
//            refData.setLeft_count(refList.get(0).getLeft_count());
//        } else { // 时间区间在中间的
//            RefData startRef = refList.get(0);
//            RefData endRef = refList.get(1);
//            // logger.info("现在时间在剩余人数表中最靠近的时间为:"+startRef.getShow_time()+"和:"+endRef.getShow_time());
//            double lef_ = startRef.getLeft_count() - endRef.getLeft_count();
//            double time_ = endRef.getShow_time().getTime() - startRef.getShow_time().getTime();
//            double x = lef_ / time_; // 进行四舍五入
//            BigDecimal chu = new BigDecimal(x);
//            BigDecimal now_ = new BigDecimal(nowDate.getTime() - startRef.getShow_time().getTime());
//            BigDecimal result = now_.multiply(chu).setScale(0, BigDecimal.ROUND_HALF_UP).abs();
//            // logger.info("人数计算差为："+result);
//            refData.setLeft_count(startRef.getLeft_count() - result.longValue());
//        }
//        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", refData);
//        return SUCCESS;
//    }
//
//    /**
//     * 累计增长人数，8-22点以3人的速度增长，22点-7点以1人的速度增长
//     */
//    public String increaseByTime() {
//        BigDecimal base = new BigDecimal(0);
//
//        try {
//            if (null != jedisCluster.get("index_count")
//                    && StringUtils.isNotBlank(jedisCluster.get("index_count"))) { // 从缓存中取得人数
//                base = new BigDecimal(jedisCluster.get("index_count"));
//            }
//        } catch (Exception e) {
//            logger.error("累计增长接口出错" + e.getMessage(), e);
//        }
//        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", base);
//        return SUCCESS;
//    }
//
//    /**
//     * 邀请有奖-我的专属链接生成
//     */
//    public String getPrizeInvitationHref() throws Exception {
//        HttpServletRequest request = ServletActionContext.getRequest();
//        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
//
//        // Long userId = Long.parseLong("11111111");
//        // 查询条件元素
//        HashMap<String, Object> newCondition = new HashMap<>();
//
//        newCondition.put("invitation", userId);
//
//        // 生成自己的专属链接
//        if (null == this.myInvitHref || ("").equals(this.myInvitHref.trim())) {
//            String code = UserIdEncode.encode(userId);
//            String portalPath = ConfigurationUtil.getString("portalPath");
//            this.myInvitHref =
//                    portalPath + "/member/toRegist.action?u=" + code + "&t=" + portalPath;
//            // this.myInvitHref =
//            // "localhost:8088/member/toRegist.action?u="+code;
//        }
//
//        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", myInvitHref);
//        return SUCCESS;
//    }
//
//    private RefData getRefData() {
//        if (refData == null) {
//            refData = new RefData();
//        }
//        return refData;
//    }
//
//    public void setRefData(RefData refData) {
//        this.refData = refData;
//    }
//
//    public ReturnResult getReturnResult() {
//        return returnResult;
//    }
//
//    public void setReturnResult(ReturnResult returnResult) {
//        this.returnResult = returnResult;
//    }
//
//}
