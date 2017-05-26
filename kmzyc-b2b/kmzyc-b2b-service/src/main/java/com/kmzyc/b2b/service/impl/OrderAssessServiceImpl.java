package com.kmzyc.b2b.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.km.framework.page.Pagination;
import com.kmzyc.b2b.dao.member.ProdAppraiseDao;
import com.kmzyc.b2b.model.OrderItem;
import com.kmzyc.b2b.model.OrderMain;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.LoginService;
import com.kmzyc.b2b.service.OrderAssessService;
import com.kmzyc.b2b.service.member.MyOrderService;
import com.kmzyc.b2b.vo.AppraisePoint;
import com.kmzyc.express.util.RedisCacheUtil;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.order.remote.OrderAssessRemoteService;
import com.kmzyc.product.remote.service.ProductAppraiseRemoteService;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.entities.OrderAssessInfo;
import com.pltfm.app.vobject.AppraiseAddtoContent;
import com.pltfm.app.vobject.ProdAppraise;

import redis.clients.jedis.JedisCluster;

// import com.km.framework.common.util.RemoteTool;

@SuppressWarnings("unchecked")
@Service("orderAssessService")
public class OrderAssessServiceImpl implements OrderAssessService {
    @Resource(name = "prodAppraiseDaoImpl")
    private ProdAppraiseDao prodAppraiseDao;

    @Resource(name = "myOrderServiceImpl")
    private MyOrderService myOrderService;
    @Resource(name = "loginServiceImp")
    private LoginService loginService;
    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;


    // private static Logger logger = Logger.getLogger(OrderAssessServiceImpl.class);
    private static Logger logger = LoggerFactory.getLogger(OrderAssessServiceImpl.class);

    @Resource
    private ProductAppraiseRemoteService productAppraiseRemoteService;
    @Resource
    private OrderAssessRemoteService orderAssessRemoteService;

    // 评价缓存时间为12小时
    private int assessCacheTime = 60 * 60 * 12;

    /**
     * orderMain:订单总情况，包含订单详情 prodAssess:明细产品星级 contents：明细产品内容评价 OrderAssess：对订单的星级评价
     */

    @Override
    public boolean saveOrderAssessAndProductAssess(OrderMain orderMain, String prodAssess,
            List<String> contents, String OrderAssess, List<String> appendContents,
            List<String> appraiseId) throws Exception {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 订单明细
        List<OrderItem> orderItemList =
                myOrderService.findAppraiseByOrdercode(orderMain.getOrderCode());
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        User user = loginService.queryUserByLoginId(userId.toString());
        // User user = loginDao.getUserByLoginId(userId);
        // 调用产品的远程接口
        // ProductAppraiseRemoteService productappraiseRemoteService =
        // (ProductAppraiseRemoteService) RemoteTool.getRemote(Constants.REMOTE_SERVICE_PRODUCT,
        // "prodAppraiseService");
        String p[] = prodAssess.split(",", -1);
        // 保存产品的咨询实体
        if (contents != null && contents.size() != 0 && !p[0].trim().equals("0")) {
            com.pltfm.app.vobject.ProdAppraise prodappraise = new ProdAppraise();
            String prodPoint[] = prodAssess.split(",", -1);
            // 如果有星级评价
            for (int index = 0; index < orderItemList.size(); index++) {
                com.kmzyc.b2b.model.ProdAppraise prodCondition =
                        new com.kmzyc.b2b.model.ProdAppraise();
                prodCondition.setOrderDetailId(orderItemList.get(index).getOrderItemId());
                prodCondition.setProductSkuId(orderItemList.get(index).getProductSkuId());
                if (prodAppraiseDao.findProdAppByOrderAndSku(prodCondition) == null
                        && !prodPoint[index].trim().equals("")
                        && !prodPoint[index].trim().isEmpty()) {
                    // 设置产品远程保存实体
                    prodappraise.setOrderDetailId(orderItemList.get(index).getOrderItemId());
                    prodappraise.setProductSkuId(orderItemList.get(index).getProductSkuId());
                    prodappraise.setPoint(Short.valueOf(prodPoint[index].trim()));
                    prodappraise.setAppraiseDate(sim.parse(sim.format(new Date())));
                    prodappraise.setAppraiseContent(contents.get(index));
                    // 此处由于经常取不到用户,做非空判断
                    if (user != null) {
                        prodappraise.setCustId(user.getLoginId().intValue());
                        // prodappraise.setCustLevel(user.getLevelId().toString());
                        prodappraise.setCustName(user.getLoginAccount());
                    } else {
                        prodappraise.setCustId(Integer.parseInt("0"));
                        prodappraise.setCustLevel("0");
                        prodappraise.setCustName("未取到用户数据");
                    }
                    prodappraise.setSatisficing("没用的数据");
                    prodappraise.setProdBuyDate(orderMain.getCreateDate());
                    prodappraise.setProductName(orderItemList.get(index).getCommodityName());
                    // 设置默认数据
                    prodappraise.setCheckStatus(Short.parseShort("0"));
                    prodappraise.setUserfulAmount(0L);
                    prodappraise.setReplyAmount(0L);
                    prodappraise.setAddtoContent("0");
                    prodappraise.setReplyContent("0");
                    try {
                        productAppraiseRemoteService.insertAppraise(prodappraise);
                        /*
                         * // 规则编号 result = 1 == this.getScoreByCondition("RU0007", 1,
                         * user.getLoginAccount(), new HashMap());
                         */
                    } catch (Exception e) {
                        logger.error("保存产品评价出错" + e.getMessage(), e);
                        return false;
                    }
                }
            }
        }
        if (OrderAssess != null && !OrderAssess.isEmpty() && !OrderAssess.trim().equals(",,,,")) {
            // 对订单的评价
            OrderAssessInfo orderAssessInfo = new OrderAssessInfo();
            orderAssessInfo.setOrderCode(orderMain.getOrderCode());
            // 此处以防取不到用户的数据
            if (user != null) {
                orderAssessInfo.setGuestNum(user.getLoginAccount());
            } else {
                orderAssessInfo.setGuestNum("取不到用户数据");
            }
            orderAssessInfo.setCreateDate(sim.parse(sim.format(new Date())));
            // 分数
            String orderPoint[] = OrderAssess.split(",");
            // 模拟从数据库中读取
            String[] assessName = {"宝贝描述相符", "卖家发货速度", "物流配送速度", "售前售后服务"};
            String[] assessType =
                    {"Assess_Type_one", "Assess_Type_two", "Assess_Type_three", "Assess_Type_four"};
            String orderCode = orderMain.getOrderCode();
            try {
                // 调用订单的评价接口
                // OrderAssessRemoteService orderassessRemoteService =
                // (OrderAssessRemoteService) RemoteTool.getRemote(Constants.REMOTE_SERVICE_ORDER,
                // "orderAssessService");
                orderAssessRemoteService.createAssessInfo(orderAssessInfo, assessType, assessName,
                        orderPoint, orderCode);
            } catch (Exception e) {
                logger.error("保存订单打分有误" + e.getMessage(), e);
            }
            /*
             * if (i == 1) { int count = 0; try { count = this.getScoreByCondition("RU0010", 1,
             * user.getLoginAccount(), new HashMap()); } catch (Exception e) { count = 0;
             * logger.error("保存订单打分时调用新增用户的积分有误" + e.getMessage(), e); return false; } if (count ==
             * 1) { result = true; } } else { result = false; }
             */

        }
        if (appendContents != null && appendContents.size() != 0) {
            for (int i = 0; i < appendContents.size(); i++) {
                if (appendContents.get(i) != null && !appendContents.get(i).isEmpty()) {
                    // 验证追平是否存在了
                    com.kmzyc.b2b.model.AppraiseAddtoContent appraiseAddToCondition =
                            new com.kmzyc.b2b.model.AppraiseAddtoContent();
                    appraiseAddToCondition.setAppraiseId(Long.parseLong(appraiseId.get(i).trim()));
                    if (prodAppraiseDao.findAppendByOrderAndSku(appraiseAddToCondition) == null) {
                        try {
                            AppraiseAddtoContent appraiseAddToContent = new AppraiseAddtoContent();
                            appraiseAddToContent
                                    .setAppraiseId(Long.parseLong(appraiseId.get(i).trim()));
                            appraiseAddToContent
                                    .setAddContentDate(sim.parse(sim.format(new Date())));
                            appraiseAddToContent.setAddContent(appendContents.get(i));
                            // ProductAppraiseRemoteService productAppraiseRemoteService =
                            // (ProductAppraiseRemoteService) RemoteTool.getRemote(
                            // Constants.REMOTE_SERVICE_PRODUCT, "prodAppraiseService");
                            productAppraiseRemoteService.saveAddtoContent(appraiseAddToContent);
                        } catch (Exception e) {
                            logger.error("保存产品追加评价有误:" + e.getMessage(), e);
                            return false;
                        }
                    }
                }
            }
        }
        return true; // 兼容时代用户
    }

    /**
     * 判断订单的评价是否齐全，如果追加评价的个数等于订单的详细个数 则齐全 返回追平个数
     */
    @Override
    public int checkOrderAssessComplete(List<String> appraiseIdList) throws Exception {
        int j = 0;
        com.kmzyc.b2b.model.AppraiseAddtoContent appraiseAddToCondition =
                new com.kmzyc.b2b.model.AppraiseAddtoContent();
        for (String anAppraiseIdList : appraiseIdList) {
            if (!anAppraiseIdList.trim().isEmpty() && !anAppraiseIdList.trim().equals("")) {
                appraiseAddToCondition.setAppraiseId(Long.parseLong(anAppraiseIdList.trim()));
                if (prodAppraiseDao.findAppendByOrderAndSku(appraiseAddToCondition) != null) {
                    j++;
                }
            }
        }
        return j;
    }

    /*
     * public int checkAppriseComplete(List<String> appraiseIdList) throws Exception { int j = 0;
     * com.kmzyc.b2b.model.ProdAppraise prodApprise = new com.kmzyc.b2b.model.ProdAppraise(); for
     * (int i = 0; i < appraiseIdList.size(); i++) { if (!appraiseIdList.get(i).trim().isEmpty() &&
     * !appraiseIdList.get(i).trim().equals("") && appraiseIdList.get(i).trim() != null) {
     * prodApprise.setAppraiseId(Long.parseLong(appraiseIdList.get(i).trim())); if
     * (prodAppraiseDao.findProdAppByOrderAndSku(prodApprise) == null) { j++; } } } return j; }
     */

    /*
     * @Override public AppraisePoint findAppraisPointBySkuId(Integer skuId, Short star) throws
     * Exception { AppraisePoint appraisePoint = new AppraisePoint(); appraisePoint.setSkuId(skuId);
     * 
     * com.kmzyc.b2b.model.ProdAppraise prodAppraise = new com.kmzyc.b2b.model.ProdAppraise();
     * prodAppraise.setproductSkuId((long) skuId); float totleCount =
     * prodAppraiseDao.selectCountPersonBySkuId(prodAppraise); // 如果传过来星星指数为空，则默认查询全部的 if (star ==
     * null) { // 查询出该商品的总分 float totlePoint = prodAppraiseDao.selectPointBySkuId(prodAppraise);
     * appraisePoint.setTotlePoint((long) totlePoint); appraisePoint.setCountPersonBySkuId((int)
     * totleCount); return appraisePoint; } // 如果星星数不为空 else if (star != null) {
     * prodAppraise.setPoint(star); // 查询出该对应的星级该商品的总人数 float pointPerson =
     * prodAppraiseDao.selectCountPersonBySkuId(prodAppraise); // 查询出对应的星级该商品的总分 float totlePoint =
     * prodAppraiseDao.selectPointBySkuId(prodAppraise); // 得出该商品的品均分：对应分数的人数 除以总人数 float
     * aveng_point = pointPerson / totleCount; appraisePoint.setTotlePointByStar((int) totlePoint);
     * appraisePoint.setConutPersonByStar((int) pointPerson);
     * appraisePoint.setPersonAccountByStar(aveng_point); } return appraisePoint; }
     */

    /**
     * 重写查询某个商品SKUID的
     */
    @Override
    public List<AppraisePoint> findAppraisPointBySkuId(Integer skuId) throws Exception {
        // AppraisePoint appraisePoint = new AppraisePoint();
        // appraisePoint.setSkuId(skuId);
        List<AppraisePoint> appraisePointList = new ArrayList<>();


        com.kmzyc.b2b.model.ProdAppraise prodAppraise = new com.kmzyc.b2b.model.ProdAppraise();
        prodAppraise.setproductSkuId((long) skuId);
        // 总人数
        float totleCount = prodAppraiseDao.selectCountPersonBySkuId(prodAppraise);
        float aveng;
        // 查询出该商品的总分
        float totlePoint = prodAppraiseDao.selectPointBySkuId(prodAppraise);
        // 得出该商品的品均分：计算公式为总分除以总人数
        if (totleCount == 0 && totlePoint == 0)// 如果查询出来的总人数为0
        {
            aveng = 0;
        } else {
            aveng = totlePoint / totleCount;
            BigDecimal aveng1 = new BigDecimal(aveng);
            aveng = aveng1.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        }
        // tod查询出了总分、品均分、总人数(还没有查询总星数的方法)
        for (int i = 1; i < 6; i++) {
            AppraisePoint ap = new AppraisePoint();
            ap.setSkuId(skuId);
            ap.setTotlePoint((long) totlePoint);
            ap.setCountPersonBySkuId((int) totleCount);
            ap.setAverageTotlePoint(aveng);
            // 平均星级 四舍五入
            ap.setAverageTotleStar(Math.round(aveng));
            ap.setStarId(i);
            prodAppraise.setPoint(Short.valueOf((i + "")));
            // 查询出该对应的星级该商品的总人数
            prodAppraise.setPoint(Short.valueOf(i + ""));
            float pointPersonBystar = prodAppraiseDao.selectCountPersonBySkuId(prodAppraise);
            ap.setTotlePointByStar((int) totlePoint);
            ap.setConutPersonByStar((int) pointPersonBystar);
            BigDecimal pointPersonlv;
            if (totleCount == 0 && pointPersonBystar == 0)// 如果查询出来的总人数为0
            {
                pointPersonlv = new BigDecimal(0);
            } else {
                pointPersonlv = new BigDecimal(pointPersonBystar / totleCount);
            }
            ap.setPersonAccountByStar(
                    pointPersonlv.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
            appraisePointList.add(ap);
        }
        return appraisePointList;
    }

    /**
     * 查询产品总评分
     */
    @Override
    public List<AppraisePoint> queryAssessPoint(Long skuId) throws ServiceException {
        List<AppraisePoint> apList = null;
        try {
            String rs = jedisCluster
                    .get(ConfigurationUtil.getString("b2b_product_assess_point") + skuId);

            if (null != rs && rs.length() > 0) {
                apList = JSONObject.parseObject(rs, List.class);
            } else {
                apList = findAppraisPointBySkuId(skuId.intValue());
                jedisCluster.setex(ConfigurationUtil.getString("b2b_product_assess_point") + skuId,
                        assessCacheTime, JSONObject.toJSONString(apList));
            }
        } catch (Exception e) {
            logger.error("根据SKUID查询商品评分信息出错：" + e.getMessage(), e);
        }
        return apList;
    }

    /**
     * 分页查询产品评价数据
     */
    @Override
    public Pagination findAppraisListByPage(Pagination page) throws SQLException {
        try {
            Map<String, Object> map = (Map<String, Object>) page.getObjCondition();
            int pageNo = map.containsKey("pageNo") ? ((Integer) map.get("pageNo")) : 1;
            int pageNum = 10;// map.containsKey("pageNum") ? ((Integer) map.get("pageNum")) : 10;
            String key = "Assess_" + map.get("timeSort") + "_" + map.get("pointSort") + "_"
                    + map.get("skuId") + "_" + pageNo;
            byte[] btKey = key.getBytes();
            page.setStartindex((pageNo - 1) * pageNum + 1);
            page.setEndindex(pageNo * pageNum);
            byte[] result;
            String pageCountKey = "Assess_Count_" + map.get("skuId");
            jedisCluster.del(btKey);
            if (null == (result = jedisCluster.get(btKey)) || result.length == 0) {

                String[] keys = {"Assess_desc_null_" + map.get("skuId") + "_" + pageNo,
                        "Assess_asc_null_" + map.get("skuId") + "_" + pageNo,
                        "Assess_null_desc_" + map.get("skuId") + "_" + pageNo,
                        "Assess_null_asc_" + map.get("skuId") + "_" + pageNo};
                for (int idx = 0, len = keys.length; idx < len; idx++) {
                    if (idx == 0) {
                        map.put("timeSort", "desc");
                        map.remove("pointSort");
                    } else if (idx == 1) {
                        map.put("timeSort", "asc");
                        map.remove("pointSort");
                    } else if (idx == 2) {
                        map.put("pointSort", "desc");
                        map.remove("timeSort");
                    } else if (idx == 3) {
                        map.put("pointSort", "asc");
                        map.remove("timeSort");
                    }
                    Pagination tempPage =
                            prodAppraiseDao.findByPage("ProdAppraise.findProdAppraListByPage",
                                    "ProdAppraise.countProAppraisByPage", page);
                    List<com.kmzyc.b2b.model.ProdAppraise> prodList = tempPage.getRecordList();
                    if (null == prodList) {
                        prodList = new ArrayList<>();
                    }
                    // 回复审核不通过
                    for (com.kmzyc.b2b.model.ProdAppraise prodAppraise : prodList) {
                        for (int i = 0; i < prodAppraise.getAppReplyList().size(); i++) {
                            if (prodAppraise.getAppReplyList().get(i).getApprReplyId() == null) {
                                prodAppraise.getAppReplyList().clear();
                            } else if (prodAppraise.getAppReplyList().get(i)
                                    .getReplyStatus() != 1) {
                                prodAppraise.getAppReplyList().clear();
                            }
                        }
                        for (int j = 0; j < prodAppraise.getAppAddtoContent().size(); j++) {
                            if (prodAppraise.getAppAddtoContent().get(j)
                                    .getAddContentId() == null) {
                                prodAppraise.getAppAddtoContent().clear();
                            } else if (prodAppraise.getAppAddtoContent().get(j)
                                    .getCheckStatus() != 1) {
                                prodAppraise.getAppAddtoContent().clear();
                            }
                        }
                    }
                    jedisCluster.setex(keys[idx].getBytes(), assessCacheTime,
                            RedisCacheUtil.serialize(prodList));
                    if (keys[idx].equals(key)) {
                        page = tempPage;
                        jedisCluster.setex(pageCountKey, assessCacheTime,
                                "" + tempPage.getTotalRecords());
                    }
                }
            } else {
                page.setRecordList((List<com.kmzyc.b2b.model.ProdAppraise>) RedisCacheUtil
                        .unserialize(result));
                String count = jedisCluster.get(pageCountKey);
                if (null != count) {
                    page.setTotalRecords(Integer.parseInt(count));
                } else {
                    page.setTotalRecords(0);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return page;
    }


}
