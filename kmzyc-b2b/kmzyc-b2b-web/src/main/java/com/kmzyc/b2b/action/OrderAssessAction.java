package com.kmzyc.b2b.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.model.OrderAssessDetail;
import com.kmzyc.b2b.model.OrderItem;
import com.kmzyc.b2b.model.OrderMain;
import com.kmzyc.b2b.model.OrderOperateStatement;
import com.kmzyc.b2b.service.OrderAssessDetailService;
import com.kmzyc.b2b.service.OrderAssessService;
import com.kmzyc.b2b.service.member.MyOrderService;
import com.kmzyc.b2b.vo.OrderAssessPoint;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.framework.constants.OrderAssessPointMap;
import com.kmzyc.framework.sensitive.SensitiveType;
import com.kmzyc.framework.sensitive.SensitiveWordFilter;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.vobject.ProdAppraise;

import redis.clients.jedis.JedisCluster;

/**
 * 商品打分系统Action
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
@Controller("orderassessAction")
@Scope("prototype")
public class OrderAssessAction extends BaseAction {

    // static Logger logger = Logger.getLogger(OrderAssessAction.class);
    private static Logger logger = LoggerFactory.getLogger(OrderAssessAction.class);

    // 图片展示默认前缀
    private String productImgServerUrl;
    private String productDetailUrl;

    private String orderCodes;

    @Resource(name = "myOrderServiceImpl")
    private MyOrderService myOrderService;

    @Resource(name = "orderAssessService")
    private OrderAssessService orderAssessService;

    @Resource(name = "sensitiveWordFilter")
    private SensitiveWordFilter sensitiveWordFilter;

    @Resource(name = "jedisCluster")
 private JedisCluster jedisCluster;

    /**
     * 订单详细评价表
     */
    @Resource(name = "orderAssessDetailService")
    private OrderAssessDetailService orderAssessDetailService;

    // 订单是否完成KEY
    // private static final String prodAppraisedone = ConfigurationUtil.getString("r_prodappraise");

    private OrderItem orderItem;
    private List<OrderOperateStatement> listorder;
    // 订单详情
    private List<OrderMain> orderMainList;

    // 订单主体
    private OrderMain orderMain;
    // 订单ID
    private Integer orderMainId;
    // 订单明细List
    private List<OrderItem> orderItemList;

    // 产品的评价实体
    private ProdAppraise prodappraise;

    // 订单的评价分值:物流、服务等
    private String point;

    // 产品评价分值
    private String prodappraisePoint;

    // 产品评价语言
    private String prodappraiseContent;

    private List<String> contents;

    private String orderappraisePoint;

    // 展示订单详情的分数
    private OrderAssessPoint orderAssessPoint;

    // 评价ID
    private String prodAppriseId;

    // 追评内容
    private List<String> appendContents;

    private List<String> appraiseId;

    // 标示订单页面是否打分
    private int orderHavePoint;

    // 标示订单页面订单的打分是否全打了
    private int prodHavePoint;

    // 标示订单页保存评价是否成功
    private int saveOk;

    // 标示是否是从订单列表页面进来的标示
    private int isOrderList;

    // 标示页码
    private int pagenumber;

    /**
     * 查看订单编号对应的订单打分页面
     * 
     * @return
     */
    @SuppressWarnings({"static-access", "unused"})
    public String gotoAssessProduct() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            // 此处为List集合 实际上是一个订单主体。
            orderMain = myOrderService.findOrderByOrderCode(orderCodes);
            orderMainId = orderMain.getOrderId().intValue();
            orderItemList = orderMain.getOrderItemList();
            // 判断是否有订单的打分
            OrderAssessDetail orderassessDetai = new OrderAssessDetail();
            orderassessDetai.setOrderCode(orderCodes);
            List<OrderAssessDetail> orderAssessDlist =
                    orderAssessDetailService.findOrderAssessDetailByCondition(orderassessDetai);
            // 系统操作流水
            listorder = myOrderService.findOperateByNo(orderCodes);
            // TEST
            orderItemList = myOrderService.findAppraiseByOrdercode(orderCodes);
            super.getRequest().setAttribute("orderAssessMap", OrderAssessPointMap.getMap());
            if (orderAssessDlist.size() > 0) {
                orderAssessPoint = new OrderAssessPoint();
                for (OrderAssessDetail orderAssessDetail : orderAssessDlist) {
                    if (orderAssessDetail.getAssessType().equals(orderAssessPoint.getOne())) {
                        orderAssessPoint.setAssess_Type_one(orderAssessDetail.getAssessScore());
                    } else if (orderAssessDetail.getAssessType().equals(orderAssessPoint.getTwo())) {
                        orderAssessPoint.setAssess_Type_two(orderAssessDetail.getAssessScore());
                    } else if (orderAssessDetail.getAssessType()
                            .equals(orderAssessPoint.getThree())) {
                        orderAssessPoint.setAssess_Type_three(orderAssessDetail.getAssessScore());
                    } else if (orderAssessDetail.getAssessType().equals(orderAssessPoint.getFour())) {
                        orderAssessPoint.setAssess_Type_four(orderAssessDetail.getAssessScore());
                    }
                }
                orderHavePoint = 1;
            }
            List<String> praiseId = new ArrayList<String>();
            for (int j = 0; j < orderItemList.size(); j++) {
                if (orderItemList.get(j).getProdApraiseList() != null
                        && orderItemList.get(j).getProdApraiseList().getAppraiseId() != 0L) {
                    praiseId.add(orderItemList.get(j).getProdApraiseList().getAppraiseId()
                            .toString());
                }
            }
            if (praiseId.size() > 0) {
                int count = orderAssessService.checkOrderAssessComplete(praiseId);
                if (count == orderItemList.size() && orderHavePoint == 1) {
                    prodHavePoint = 1;
                }
            }
        } catch (Exception e) {
            logger.error("查看订单的评价信息出错：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 保存打分
     * 
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    public String saveAssessContent() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            orderMain = myOrderService.findOrderByOrderCode(orderCodes);
            orderItemList = myOrderService.findAppraiseByOrdercode(orderCodes);

            if (orderHavePoint == 1) {
                orderappraisePoint = null;
            }
            // 进行评价的保存之后返回orderaction false:有错 true:无误
            boolean flag =
                    orderAssessService.saveOrderAssessAndProductAssess(orderMain,
                            prodappraisePoint, contents, orderappraisePoint, appendContents,
                            appraiseId);
            // 有错
            if (!flag) {
                saveOk = 1;
            } else {
                saveOk = 2;
            }
            OrderAssessDetail orderassessDetai = new OrderAssessDetail();
            orderassessDetai.setOrderCode(orderCodes);
            List<OrderAssessDetail> orderAssessDlist =
                    orderAssessDetailService.findOrderAssessDetailByCondition(orderassessDetai);
            // 判断评价是否完全
            List<String> praiseId = new ArrayList<String>();
            orderItemList = myOrderService.findAppraiseByOrdercode(orderCodes);
            for (int j = 0; j < orderItemList.size(); j++) {
                if (orderItemList.get(j).getProdApraiseList() != null
                        && orderItemList.get(j).getProdApraiseList().getAppraiseId() != 0L) {
                    praiseId.add(orderItemList.get(j).getProdApraiseList().getAppraiseId()
                            .toString());
                }
            }
            // 如果订单打分存在
            if (orderAssessDlist.size() > 0) {
                int count = praiseId.size();
                // 如果订单评价完全，则写入缓存
                if (count == orderItemList.size()) {

                    try {
                        jedisCluster.set(ConfigurationUtil.getString("r_prodappraise") + orderCodes,
                                Constants.PROD_APPRIASEDONE);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        } catch (Exception e) {
            saveOk = 1;
            logger.error("保存订单的评价出错：" + e.getMessage(), e);
            return ERROR;
        }
        return this.gotoAssessProduct();
    }

    /**
     * ajax校验传过来的内容是否包含敏感词汇(在进行判断之前先判断会话是否过期)
     * 
     * @return
     * @throws Exception
     */
    public String isSensitive() {
        String result = InterfaceResultCode.SUCCESS;
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            String userAccount =
                    (String) request.getSession().getAttribute(Constants.SESSION_USER_NAME);
            if (StringUtils.isBlank(userAccount)) {
                // 未登录
                result = InterfaceResultCode.NOT_LOGIN;
            } else {
                if (contents != null && contents.size() != 0) {
                    for (String content : contents) {
                        if (sensitiveWordFilter.doFilt(content, SensitiveType.commit)) {
                            result = InterfaceResultCode.FAILED;
                        }
                    }
                }
                if (appendContents != null && appendContents.size() != 0) {
                    for (String appendContent : appendContents) {
                        if (!appendContent.isEmpty() && !StringUtils.isEmpty(appendContent)) {
                            if (sensitiveWordFilter.doFilt(appendContent, SensitiveType.commit)) {
                                result = InterfaceResultCode.FAILED;
                            }
                        }
                    }
                }
            }
            getResponse().getWriter().write(result);
        } catch (Exception e) {
            logger.error("校验敏感词出错:" + e.getMessage(), e);
            result = InterfaceResultCode.FAILED;
        }
        return NONE;
    }

    public String getOrderCodes() {
        return orderCodes;
    }

    public void setOrderCodes(String orderCodes) {
        this.orderCodes = orderCodes;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public List<OrderMain> getOrderMainList() {
        return orderMainList;
    }

    public void setOrderMainList(List<OrderMain> orderMainList) {
        this.orderMainList = orderMainList;
    }

    public OrderMain getOrderMain() {
        return orderMain;
    }

    public void setOrderMain(OrderMain orderMain) {
        this.orderMain = orderMain;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public String getProductImgServerUrl() {
        return ConfigurationUtil.getString("PRODUCT_IMG_PATH");
    }

    public ProdAppraise getProdappraise() {
        return prodappraise;
    }

    public void setProdappraise(ProdAppraise prodappraise) {
        this.prodappraise = prodappraise;
    }

    public String getProdappraisePoint() {
        return prodappraisePoint;
    }

    public void setProdappraisePoint(String prodappraisePoint) {
        this.prodappraisePoint = prodappraisePoint;
    }

    public String getProdappraiseContent() {
        return prodappraiseContent;
    }

    public void setProdappraiseContent(String prodappraiseContent) {
        this.prodappraiseContent = prodappraiseContent;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    public String getOrderappraisePoint() {
        return orderappraisePoint;
    }

    public void setOrderappraisePoint(String orderappraisePoint) {
        this.orderappraisePoint = orderappraisePoint;
    }

    public OrderAssessPoint getOrderAssessPoint() {
        return orderAssessPoint;
    }

    public void setOrderAssessPoint(OrderAssessPoint orderAssessPoint) {
        this.orderAssessPoint = orderAssessPoint;
    }

    public String getProdAppriseId() {
        return prodAppriseId;
    }

    public void setProdAppriseId(String prodAppriseId) {
        this.prodAppriseId = prodAppriseId;
    }

    public List<String> getAppendContents() {
        return appendContents;
    }

    public void setAppendContents(List<String> appendContents) {
        this.appendContents = appendContents;
    }

    public List<String> getAppraiseId() {
        return appraiseId;
    }

    public void setAppraiseId(List<String> appraiseId) {
        this.appraiseId = appraiseId;
    }

    public int getOrderHavePoint() {
        return orderHavePoint;
    }

    public void setOrderHavePoint(int orderHavePoint) {
        this.orderHavePoint = orderHavePoint;
    }

    public int getProdHavePoint() {
        return prodHavePoint;
    }

    public void setProdHavePoint(int prodHavePoint) {
        this.prodHavePoint = prodHavePoint;
    }

    public List<OrderOperateStatement> getListorder() {
        return listorder;
    }

    public void setListorder(List<OrderOperateStatement> listorder) {
        this.listorder = listorder;
    }

    public Integer getOrderMainId() {
        return orderMainId;
    }

    public void setOrderMainId(Integer orderMainId) {
        this.orderMainId = orderMainId;
    }

    public int getSaveOk() {
        return saveOk;
    }

    public void setSaveOk(int saveOk) {
        this.saveOk = saveOk;
    }

    public String getProductDetailUrl() {
        return ConfigurationUtil.getString("CMS_PAGE_PATH");
    }

    public int getIsOrderList() {
        return isOrderList;
    }

    public void setIsOrderList(int isOrderList) {
        this.isOrderList = isOrderList;
    }

    public int getPagenumber() {
        return pagenumber;
    }

    public void setPagenumber(int pagenumber) {
        this.pagenumber = pagenumber;
    }

}
