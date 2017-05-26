package com.kmzyc.b2b.action.member;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.model.Address;
import com.kmzyc.b2b.model.LoginInfo;
import com.kmzyc.b2b.model.OrderAlter;
import com.kmzyc.b2b.model.OrderItem;
import com.kmzyc.b2b.model.OrderMain;
import com.kmzyc.b2b.model.ProductImage;
import com.kmzyc.b2b.model.SupplierOrderItem;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.AccountInfoService;
import com.kmzyc.b2b.service.OrderItemService;
import com.kmzyc.b2b.service.SecurityCentreService;
import com.kmzyc.b2b.service.member.AddressService;
import com.kmzyc.b2b.service.member.MyOrderService;
import com.kmzyc.b2b.service.member.ReturnGoodsTraceService;
import com.kmzyc.b2b.service.member.ReturnShopService;
import com.kmzyc.b2b.service.member.UserInfoService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.b2b.vo.TraceInfoVO;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.order.remote.OrderAlterChangeStatusRemoteService;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.entities.OrderAlterPhoto;
import com.pltfm.app.util.OrderAlterCodeUtil;
import com.pltfm.app.util.OrderAlterDictionaryEnum;

import net.sf.json.JSONObject;

@SuppressWarnings({"serial", "unchecked"})
@Controller
@Scope("prototype")
public class ReturnShopAction extends BaseAction {
    // private static Logger logger = Logger.getLogger(ReturnShopAction.class);
    private static Logger logger = LoggerFactory.getLogger(ReturnShopAction.class);

    private final static ReentrantLock lock = new ReentrantLock();

    @Resource(name = "myOrderServiceImpl")
    private MyOrderService myOrderService;
    @Resource(name = "returnShopServiceImpl")
    private ReturnShopService returnShopService;
    @Resource(name = "addressServiceImpl")
    private AddressService addressService;
    @Resource(name = "orderItemServiceImpl")
    private OrderItemService orderItemService;
    @Resource(name = "returnGoodsService")
    private ReturnGoodsTraceService returnGoodsTraceService;
    @Resource(name = "userInfoServiceImpl")
    private UserInfoService userInfoService;
    @Resource(name = "securityCentreServiceImpl")
    private SecurityCentreService securityCentreService;
    @Resource(name = "accountInfoServiceImp")
    private AccountInfoService accountinfoService;

    @Resource
    private OrderAlterChangeStatusRemoteService orderAlterChangeStatusRemoteService;
    @Resource(name = "logisticsMap")
    private Map<String, String> logisticsMap;

    // private String productImgServerUrl = ConfigurationUtil.getString("PRODUCT_IMG_PATH");
    // private String productPicPath_WAP = ConfigurationUtil.getString("productPicPath_WAP");

    private static final OrderAlterDictionaryEnum.OrderAlterOperateType[] operateTypes =
            OrderAlterDictionaryEnum.OrderAlterOperateType.values();// 退换货状态

    /****** 使用的变量 ******/
    private static final String KEYWORD_TIPS = "商品名称、商品编号、订单编号";
    private static final String KEYWORD_TIPS_JILU = "商品名称/编号、订单/退换货单编号";
    private String orderAlterCode;// 退换货单号
    private long orderAlterStatus;// 退换货状态
    private String operator;// 操作人
    private LoginInfo loginInfo;
    private String checkOverDate;// 是否检查过期时间
    // 标示页码
    private int pagenumber;
    // 返回至页面的对象
    private ReturnResult returnResult;
    private OrderMain om;

    private File[] image;
    private String[] imageFileName;
    private String[] imageContentType;

    private List<TraceInfoVO> listVoline;

    /**
     * 查询会员的可申请退换货订单列表
     */
    public String applyPrepare() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        pagintion = this.getPagination(5, 10);
        // 页面传入的查询条件
        Map<String, String> objCondition = (Map<String, String>) pagintion.getObjCondition();
        // sql查询条件对象
        Map<String, Object> newConditon = new HashMap<String, Object>();
        // 解析并组装查询条件
        if (objCondition != null) {
            if (KEYWORD_TIPS.equals(objCondition.get("keyword"))) {
                objCondition.put("keyword", "");
            } else {
                newConditon.put("keyword", objCondition.get("keyword"));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.HOUR, -Constants.ORDER_APPLY_TIME);
                newConditon.put("overDate", calendar.getTime());
            }
        }
        newConditon.put("userId", userId);
        // 添加渠道查询条件
        String channel = ConfigurationUtil.getString("CHANNEL");
        newConditon.put("channel", channel);
        newConditon.put("orderType", 7);
        if (!"false".equals(checkOverDate)) {
            // 设置查询条件
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.HOUR, -Constants.ORDER_APPLY_TIME);
            newConditon.put("overDate", calendar.getTime());
        }
        pagintion.setObjCondition(newConditon);
        try {
            this.pagintion = returnShopService.findReturnOrderMainByPage(pagintion);
        } catch (SQLException e) {
            logger.error("查询会员的可退换货订单列表信息出错：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * wap版本查询会员的可申请退换货
     *
     * @return
     */
    public String submitWapPrepare() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
            if (null == orderItemId) {
                return "backJsp";
            }
            orderItem = orderItemService.findByIdForReturnShop(orderItemId);
            om = myOrderService.findOrderByOrderCode(orderItem.getOrderCode());
            if (om.getOrderType() == 7 && orderItem.getPresellId() != null
                    && orderItem.getPresellId() > 1 && om.getOrderStatus() != 6) {
                alterType = 4;
                orderAlter = new OrderAlter();
                orderAlter.setDeposit(om.getDepositSum());
                orderAlter.setFinalmoney(om.getAmountPayable().subtract(om.getDepositSum()));
                orderAlter.setCompensate(om.getDepositSum());
                orderAlter.setRuturnSum(om.getAmountPayable().add(om.getDepositSum()));
            }
            if (om.getCustomerId().compareTo(new BigDecimal(userId)) != 0) {
                logger.error("非当前用户申请退换货，currentUserID：" + userId + ",订单商品id:" + orderItemId);
                return ERROR;
            } else if (om.getOrderStatus().intValue() < 3 && alterType != 4) {
                logger.error("订单此状态下不能申请退换货：" + userId + ",订单商品id:" + orderItemId);
                return ERROR;
            } else if (orderItem.getIsReturning() != 0L) {
                logger.error("订单商品正在退换货中：" + userId + ",订单商品id:" + orderItemId);
                return ERROR;
            }

            batchNo = OrderAlterCodeUtil.generateOrderAlterPhotoBatchNo();
            maxnum = orderItem.getCommodityNumber()
                    - orderItemService.selectOverplusNum(orderItemId);// 未退换数量
            addressList = addressService.findByLoginId(userId.intValue());

            ProductImage defaultProductImage = myOrderService.findDefaultProductImageBySkuCode(
                    "ProductImage.findDefaultImageBySkuCode", orderItem.getCommoditySku());
            orderItem.setDefaultProductImage(defaultProductImage);

        } catch (Exception e) {
            logger.error("查询会员的退换货商品信息出错：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 查询会员的可申请退换货订单列表WAP
     */
    public String applyWapPrepare() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        pagintion = this.getPagination(5, 10);
        // 页面传入的查询条件
        Map<String, String> objCondition = (Map<String, String>) pagintion.getObjCondition();
        // sql查询条件对象
        Map<String, Object> newConditon = new HashMap<String, Object>();
        // 解析并组装查询条件
        if (objCondition != null) {
            if (KEYWORD_TIPS.equals(objCondition.get("keyword"))) {
                objCondition.put("keyword", "");
            } else {
                newConditon.put("keyword", objCondition.get("keyword"));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.HOUR, -Constants.ORDER_APPLY_TIME);
                newConditon.put("overDate", calendar.getTime());
            }
        }
        newConditon.put("userId", userId);
        // 添加渠道查询条件
        String channel = ConfigurationUtil.getString("CHANNEL");
        newConditon.put("channel", channel);
        // newConditon.put("orderType", 7);
        if (!"false".equals(checkOverDate)) {
            // 设置查询条件
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.HOUR, -Constants.ORDER_APPLY_TIME);
            newConditon.put("overDate", calendar.getTime());
        }
        pagintion.setObjCondition(newConditon);
        try {
            this.pagintion = returnShopService.findReturnOrderMainByPage(pagintion);
        } catch (SQLException e) {
            logger.error("查询会员的可退换货订单列表信息出错：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 查询会员的可申请退换货订单列表WAP AJAX
     */
    public String ajaxApplyWapPrepare() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        pagintion = this.getPagination(5, 10);
        pagintion.setPage(pagenumber);
        pagintion.setStartindex(((pagenumber - 1) * pagintion.getNumperpage()) + 1);
        pagintion.setEndindex(pagenumber * pagintion.getNumperpage());
        // 页面传入的查询条件
        Map<String, String> objCondition = (Map<String, String>) pagintion.getObjCondition();
        // sql查询条件对象
        Map<String, Object> newConditon = new HashMap<String, Object>();
        // 解析并组装查询条件
        if (objCondition != null) {
            if (KEYWORD_TIPS.equals(objCondition.get("keyword"))) {
                objCondition.put("keyword", "");
            } else {
                newConditon.put("keyword", objCondition.get("keyword"));
                // Calendar calendar = Calendar.getInstance();
                // calendar.setTime(new Date());
                // calendar.add(Calendar.HOUR, -Constants.ORDER_APPLY_TIME);
                // newConditon.put("overDate", calendar.getTime());
            }
        }
        newConditon.put("userId", userId);
        // 添加渠道查询条件
        String channel = ConfigurationUtil.getString("CHANNEL");
        newConditon.put("channel", channel);
        if (!"false".equals(checkOverDate)) {
            // 设置查询条件
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.HOUR, -Constants.ORDER_APPLY_TIME);
            newConditon.put("overDate", calendar.getTime());
        }
        pagintion.setObjCondition(newConditon);
        try {
            this.pagintion = returnShopService.findReturnOrderMainByPage(pagintion);
            if (pagenumber > pagintion.getTotalpage()) {
                returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", null);
            } else {
                returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功",
                        pagintion.getRecordList());
            }
        } catch (SQLException e) {
            logger.error("查询会员的可退换货订单列表信息出错：" + e.getMessage(), e);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 查询会员的退换货记录列表
     *
     * @return
     */

    public String queryReturnShopList() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        Pagination page = getPagination(5, 10);
        // 页面传入的查询条件
        Map<String, String> objCondition = (Map<String, String>) page.getObjCondition();
        // 解析并组装查询条件
        if (objCondition != null) {
            if (KEYWORD_TIPS_JILU.equals(objCondition.get("keyword"))) {
                objCondition.put("keyword", "");
            }
        } else {
            objCondition = new HashMap<String, String>();
            page.setObjCondition(objCondition);
        }
        objCondition.put("userId", userId + "");
        try {
            pagintion = returnShopService.findReturnShopByPage(page);
        } catch (SQLException e) {
            logger.error("查询会员的退换货单列表信息出错：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * wap查询会员的退换货记录列表
     *
     * @return
     */

    public String queryWapReturnShopList() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        Pagination page = getPagination(5, 10);
        // 页面传入的查询条件
        Map<String, String> objCondition = (Map<String, String>) page.getObjCondition();
        // 解析并组装查询条件
        if (objCondition != null) {
            if (KEYWORD_TIPS_JILU.equals(objCondition.get("keyword"))) {
                objCondition.put("keyword", "");
            }
        } else {
            objCondition = new HashMap<String, String>();
            page.setObjCondition(objCondition);
        }
        objCondition.put("userId", userId + "");
        page.setObjCondition(objCondition);
        try {
            pagintion = returnShopService.findReturnShopByPage(page);
        } catch (SQLException e) {
            logger.error("查询会员的退换货单列表信息出错：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 查询会员的退换货记录WAP AJAX
     */
    public String ajaxQueryWapReturnShopList() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
        pagintion = this.getPagination(5, 10);
        pagintion.setPage(pagenumber);
        pagintion.setStartindex(((pagenumber - 1) * pagintion.getNumperpage()) + 1);
        pagintion.setEndindex(pagenumber * pagintion.getNumperpage());
        // 页面传入的查询条件
        Map<String, String> objCondition = (Map<String, String>) pagintion.getObjCondition();
        // sql查询条件对象
        Map<String, Object> newConditon = new HashMap<String, Object>();
        // 解析并组装查询条件
        if (objCondition != null) {
            if (KEYWORD_TIPS.equals(objCondition.get("keyword"))) {
                objCondition.put("keyword", "");
            } else {
                newConditon.put("keyword", objCondition.get("keyword"));
                // Calendar calendar = Calendar.getInstance();
                // calendar.setTime(new Date());
                // calendar.add(Calendar.HOUR, -Constants.ORDER_APPLY_TIME);
                // newConditon.put("overDate", calendar.getTime());
            }
        }
        newConditon.put("userId", userId);
        // 添加渠道查询条件
        String channel = ConfigurationUtil.getString("CHANNEL");
        newConditon.put("channel", channel);
        if (!"false".equals(checkOverDate)) {
            // 设置查询条件
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.HOUR, -Constants.ORDER_APPLY_TIME);
            newConditon.put("overDate", calendar.getTime());
        }
        pagintion.setObjCondition(newConditon);
        try {
            this.pagintion = returnShopService.findReturnShopByPage(pagintion);
            if (pagenumber > pagintion.getTotalpage()) {
                returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", null);
            } else {
                returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功",
                        pagintion.getRecordList());
            }
        } catch (SQLException e) {
            logger.error("查询会员的可退换货订单列表信息出错：" + e.getMessage(), e);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 查询会员的退换货详情
     *
     * @return
     */
    private String time;
    private List<TraceInfoVO> listVo;
    private OrderAlter orderAlter;

    private List photoList;

    public String queryApplyDetail() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);

            orderAlter = returnShopService.findByCode(orderAlterCode);
            om = myOrderService.findOrderByOrderCode(orderAlter.getOrderCode());
            orderItem = orderItemService.findByIdForReturnShop(orderAlter.getOrderItemId());
            if (om.getCustomerId().compareTo(new BigDecimal(userId)) != 0) {
                logger.error("非当前用户查询退换货单详情，currentUserID：" + userId + ",退换货单id:" + orderAlterCode);
                return ERROR;
            }
            photoList = returnShopService.getPhotoByBatchNo(orderAlter.getPhotoBatchNo());
            DateFormat formatStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 物流跟踪信息
            listVo = returnGoodsTraceService.getTraceInfo(orderAlterCode);
            listVoline = new ArrayList<TraceInfoVO>();
            Set orperateStatusSet = new HashSet();
            for (TraceInfoVO vo : listVo) {
                if (!orperateStatusSet.contains(vo.getOperatStatus())) {
                    listVoline.add(vo);
                    orperateStatusSet.add(vo.getOperatStatus());
                }
            }
            time = formatStr.format(listVo.get(0).getDate());
            alterComment = listVo.get(0).getInfo();
        } catch (Exception e) {
            logger.error("查询会员的退换货详情信息出错：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    public String queryWapReturnDetail() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);

            orderAlter = returnShopService.findByCode(orderAlterCode);
            om = myOrderService.findOrderByOrderCode(orderAlter.getOrderCode());
            orderItem = orderItemService.findByIdForReturnShop(orderAlter.getOrderItemId());
            if (om.getCustomerId().compareTo(new BigDecimal(userId)) != 0) {
                logger.error("非当前用户查询退换货单详情，currentUserID：" + userId + ",退换货单id:" + orderAlterCode);
                return ERROR;
            }
            photoList = returnShopService.getPhotoByBatchNo(orderAlter.getPhotoBatchNo());
            DateFormat formatStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 物流跟踪信息
            listVo = returnGoodsTraceService.getTraceInfo(orderAlterCode);
            time = formatStr.format(listVo.get(0).getDate());
            alterComment = listVo.get(0).getInfo();
        } catch (Exception e) {
            logger.error("查询会员的退换货详情信息出错：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 到退货页 update by lijianjun
     */

    private List fareTypeList;



    public String doReturn() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);

            orderAlter = returnShopService.findByCode(orderAlterCode);
            OrderMain om = myOrderService.findOrderByOrderCode(orderAlter.getOrderCode());

            if (om.getCustomerId().compareTo(new BigDecimal(userId)) != 0) {
                logger.error("非当前用户查询退换货单详情，currentUserID：" + userId + ",退换货单id:" + orderAlterCode);
                return ERROR;
            }

            loginInfo = userInfoService.queryStatus(userId.intValue());
            // 判断验证邮箱是否存在 不存在eamilStatus默认为0 存在设置为1
            if (StringUtils.isNotBlank(loginInfo.getEmail())) {
                loginInfo.setEmailStatus(1);
            }
            DateFormat formatStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            listVo = returnGoodsTraceService.getTraceInfo(orderAlterCode);
            time = formatStr.format(listVo.get(0).getDate());
            alterComment = listVo.get(0).getInfo();
            addressList = addressService.findByLoginId(userId.intValue());
        } catch (Exception e) {
            logger.error("查询会员的退货信息出错：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     *
     * @return
     */
    public String doWapReturn() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);

            orderAlter = returnShopService.findByCode(orderAlterCode);
            OrderMain om = myOrderService.findOrderByOrderCode(orderAlter.getOrderCode());

            if (om.getCustomerId().compareTo(new BigDecimal(userId)) != 0) {
                logger.error("非当前用户查询退换货单详情，currentUserID：" + userId + ",退换货单id:" + orderAlterCode);
                return ERROR;
            }

            loginInfo = userInfoService.queryStatus(userId.intValue());
            // 判断验证邮箱是否存在 不存在eamilStatus默认为0 存在设置为1
            if (StringUtils.isNotBlank(loginInfo.getEmail())) {
                loginInfo.setEmailStatus(1);
            }
            DateFormat formatStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            listVo = returnGoodsTraceService.getTraceInfo(orderAlterCode);
            time = formatStr.format(listVo.get(0).getDate());
            alterComment = listVo.get(0).getInfo();
            addressList = addressService.findByLoginId(userId.intValue());
        } catch (Exception e) {
            logger.error("查询会员的退货信息出错：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 到提交页
     */
    private OrderItem orderItem;// 订单项
    private Long status;// 审批状态

    private List addressList;// 快捷地址
    private Short type;// 通过、驳回

    private List operates;// 退换货单操作流水

    private List pays;// 退换货单支付流水
    private String email;// 电子邮件
    private BigDecimal returnMoney;// 审核后退款金额
    private BigDecimal fareSubsidy;// 审核后运费补贴
    private Long maxnum;// 最大可退数目

    private SupplierOrderItem supplierOrderItem;

    /**
     * 申请退换货
     *
     * @return
     */
    public String submitPrepare() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);


            orderItem = orderItemService.findByIdForReturnShop(orderItemId);
            OrderMain om = myOrderService.findOrderByOrderCode(orderItem.getOrderCode());
            /**** 直销会员不能退换货 ****/
            String userType =
                    (String) request.getSession().getAttribute(Constants.SESSION_B2B_OR_ERA);
            if (Constants.SESSION_B2B_OR_ERA.equals(userType)
                    && orderItem.getCommodityPv().floatValue() > 0f) {
                logger.error("些会员不能申请退换货userid:：" + userId);
                return ERROR;
            }
            /**** 直销会员不能退换货 ****/

            if (om.getCustomerId().compareTo(new BigDecimal(userId)) != 0) {
                logger.error("非当前用户申请退换货，currentUserID：" + userId + ",订单商品id:" + orderItemId);
                return ERROR;
            } else if (om.getOrderStatus().intValue() < 3) {
                logger.error("订单此状态下不能申请退换货：" + userId + ",订单商品id:" + orderItemId);
                return ERROR;
            } else if (orderItem.getIsReturning() != 0L) {
                logger.error("订单商品正在退换货中：" + userId + ",订单商品id:" + orderItemId);
                return ERROR;
            }

            batchNo = OrderAlterCodeUtil.generateOrderAlterPhotoBatchNo();
            maxnum = orderItem.getCommodityNumber()
                    - orderItemService.selectOverplusNum(orderItemId);// 未退换数量
            addressList = addressService.findByLoginId(userId.intValue());
            supplierOrderItem = orderItemService.findSupplierOrderItem(orderItemId);

        } catch (Exception e) {
            logger.error("查询会员的退换货商品信息出错：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 计算，页面选择服务类型为退货时调用订单接口计算退款金额
     */

    Map map;

    public void compute() {
        HttpServletResponse response = null;
        try {
            response = ServletActionContext.getResponse();
            // OrderAlterChangeStatusRemoteService orderAlterChangeStatusRemoteService =
            // (OrderAlterChangeStatusRemoteService) RemoteTool.getRemote(
            // Constants.REMOTE_SERVICE_ORDER, "orderAlterChangeStatusService");
            // TestLocalRemote.getOrderAlterChangeStatusService();
            map = orderAlterChangeStatusRemoteService.compute(orderCode, orderItemId, alterNum);
        } catch (Exception e) {
            map = new HashMap();
            map.put("msg", "fail");
            logger.error("计算退换货退款信息出错：" + e.getMessage(), e);
        }
        String result = JSONObject.fromObject(map).toString();
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            logger.error("计算退换货退款信息出错：" + e.getMessage(), e);
        }
    }

    /**
     * 申请
     */
    private String orderCode;// 订单号
    private Long orderItemId;// 订单明细id
    private Short alterType;// 服务类型
    private Long alterNum;// 数量
    private Long evidence;// 凭据
    private String alterComment;// 描述
    private String batchNo;// 上传图片批次号
    private Short backType;// 商品退回方式
    private Integer addressId;// 地址id
    private String name;// 姓名
    private String address;// 详细地址
    private String phone;// 手机号
    private String province;// 省
    private String city;// 市
    private String area;// 县
    private Integer zipCode;// 邮编

    public String applySubmit() {
        try {

            HttpServletRequest request = ServletActionContext.getRequest();
            Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);

            if (null == orderItemId) {
                return "backJsp";
            }


            orderItem = orderItemService.findById(orderItemId);
            OrderMain om = myOrderService.findOrderByOrderCode(orderItem.getOrderCode());
            if (om.getCustomerId().compareTo(new BigDecimal(userId)) != 0) {
                logger.error("非当前用户查询退换货单详情，currentUserID：" + userId + ",退换货单id:" + orderAlterCode);
                return ERROR;
            }
            User user = securityCentreService.getUserByLoginId(userId);
            operator = user.getLoginAccount();
            savePhoto();
            int result = returnShopService.apply(operator, orderCode, orderItemId, alterType,
                    alterNum, evidence, alterComment, batchNo, backType, addressId, name, province,
                    city, area, zipCode, address, phone, orderItem.getCommodityBatchNumber(),
                    userId);
            if (1 != result) {
                logger.error("退换货不成功！ 返回result = " + result);
                return "backJsp";
            }


        } catch (Exception e) {
            logger.error("退换货申请出错：" + e.getMessage(), e);
            return ERROR;
        }
        return "backJsp";
    }

    /**
     * wap 退换货跳转地址
     *
     * @return
     */
    public String returnWapAddress() {
        Long userId = (Long) getSession().getAttribute(Constants.SESSION_USER_ID);
        if (null == orderItemId) {
            return "backJsp";
        }
        try {
            orderItem = orderItemService.findById(orderItemId);
            OrderMain om = myOrderService.findOrderByOrderCode(orderItem.getOrderCode());
            if (om.getCustomerId().compareTo(new BigDecimal(userId)) != 0) {
                logger.error("非当前用户查询退换货单详情，currentUserID：" + userId + ",退换货单id:" + orderAlterCode);
                return ERROR;
            }
            User user = securityCentreService.getUserByLoginId(userId);
            operator = user.getLoginAccount();
            saveWapPhoto();
            addressList = addressService.findByLoginId(userId.intValue());
        } catch (Exception e) {
            logger.error("退换货申请出错：" + e.getMessage(), e);
            return ERROR;
        }
        if (alterType.equals((short) 1) || alterType.equals((short) 4)) {
            try {
                backType = 1;
                int result = returnShopService.apply(operator, orderCode, orderItemId, alterType,
                        alterNum, evidence, alterComment, batchNo, backType, addressId, name,
                        province, city, area, zipCode, address, phone,
                        orderItem.getCommodityBatchNumber(), userId);
                if (1 != result) {
                    logger.error("退换货不成功！ 返回result = " + result);
                    return "backJsp";

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return SUCCESS;
        } else {
            return "changeGoods";
        }

    }

    /**
     * wap 退换货申请提交
     *
     * @return
     */
    public String applyWapSubmit() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
            if (null == orderItemId) {
                return "backJsp";
            }
            orderItem = orderItemService.findById(orderItemId);
            OrderMain om = myOrderService.findOrderByOrderCode(orderItem.getOrderCode());
            if (om.getCustomerId().compareTo(new BigDecimal(userId)) != 0) {
                logger.error("非当前用户查询退换货单详情，currentUserID：" + userId + ",退换货单id:" + orderAlterCode);
                return ERROR;
            }
            User user = securityCentreService.getUserByLoginId(userId);
            operator = user.getLoginAccount();
            backType = 1;
            int result = returnShopService.apply(operator, orderCode, orderItemId, alterType,
                    alterNum, evidence, alterComment, batchNo, backType, addressId, name, province,
                    city, area, zipCode, address, phone, orderItem.getCommodityBatchNumber(),
                    userId);
            if (1 != result) {
                // throw new Exception();
                logger.error("退换货不成功！ 返回result = " + result);
                return "backJsp";
            }

        } catch (Exception e) {
            logger.error("退换货申请出错：" + e.getMessage(), e);
            return ERROR;
        }
        return "backJsp";
    }

    /**
     * 保存我的收货地址的修改和保存
     *
     * @return
     */
    public String saveAddressInfo() {
        try {
            HttpServletRequest request = getRequest();
            Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);
            Address address;
            AccountInfo accountInfo = accountinfoService.findByLoginId(userId);
            String naddressId = request.getParameter("naddressId");
            String province = request.getParameter("province");
            String city = request.getParameter("city");
            String area = request.getParameter("area");
            String name = request.getParameter("name");
            String postalcode = request.getParameter("postalcode");
            String detailedAddress = request.getParameter("detailedAddress");
            String telephone = request.getParameter("telephone");
            String mobile = request.getParameter("mobile");
            Date date = new Date();
            if (StringUtils.isEmpty(naddressId)) {
                addressList = addressService.findByLoginId(userId.intValue());
                if (addressList.size() >= 10) {
                    returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", -1);
                    return SUCCESS;
                }
                // 新增 并且设置默认的收货地址
                address = new Address();
                address.setProvince(province);
                address.setCity(city);
                address.setArea(area);
                address.setName(name);
                address.setPostalcode(postalcode);
                address.setDetailedAddress(detailedAddress);
                address.setTelephone(telephone);
                address.setAccountId(accountInfo.getNaccountId());
                address.setLoginId(userId.intValue());
                address.setCreatedate(date);
                address.setLastupdate(date);
                address.setCellphone(mobile);
                address.setEmail(email);
                address.setStatus(1);
                Integer addressId = addressService.save(address);
                address.setAddressId(addressId);
                address.setAccountId(0);
                returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", address);
            } else {
                address = addressService.findByNAddressID(userId, Integer.valueOf(naddressId));
                address.setProvince(province);
                address.setCity(city);
                address.setArea(area);
                address.setName(name);
                address.setPostalcode(postalcode);
                address.setDetailedAddress(detailedAddress);
                address.setTelephone(telephone);
                address.setAccountId(accountInfo.getNaccountId());
                address.setLoginId(userId.intValue());
                address.setCellphone(mobile);
                address.setLastupdate(date);
                addressService.update(address);
                address.setAccountId(0);
                returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", address);
            }
        } catch (Exception e) {
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", addressId);
            logger.error("新增更新wap端我的收货地址出错" + e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * 更改状态
     */
    private String code;// 物流公司代号
    private String customerLogisticsName;// 物流公司名称
    private String no;// 物流单号
    private Address addressVar;

    public String changeApplyStatus() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);

            if (this.returnShopService.checkApplyStatus(orderAlterCode, orderAlterStatus) > 0) {
                return "backJsp";
            }

            User user = securityCentreService.getUserByLoginId(userId);
            operator = user.getLoginAccount();

            if (!StringUtil.isEmpty(code)) {
                customerLogisticsName = logisticsMap.get(code);
            }
            int i = returnShopService.changeOrderAlterStatus(operator, orderAlterCode,
                    orderAlterStatus, code, no, addressVar, customerLogisticsName, userId);
            if (1 != i) {
                logger.error("更改退换货单状态失败！");
                // throw new Exception();
                logger.error("changeApplyStatus中更改状态不成功!");
                return "backJsp";
            }
        } catch (Exception e) {
            logger.error("更改退换货单状态失败！" + e.getMessage(), e);
            return ERROR;
        }
        return "backJsp";
    }

    public String changeWapApplyStatus() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);

            if (this.returnShopService.checkApplyStatus(orderAlterCode, orderAlterStatus) > 0) {
                return "backJsp";
            }

            User user = securityCentreService.getUserByLoginId(userId);
            operator = user.getLoginAccount();
            if (!StringUtil.isEmpty(code)) {
                customerLogisticsName = logisticsMap.get(code);
            }
            int i = returnShopService.changeOrderAlterStatus(operator, orderAlterCode,
                    orderAlterStatus, code, no, addressVar, customerLogisticsName, userId);
            if (1 != i) {
                logger.error("更改退换货单状态失败！");
                throw new Exception();
            }
        } catch (Exception e) {
            logger.error("更改退换货单状态失败！" + e.getMessage(), e);
            return ERROR;
        }
        return "backJsp";
    }

    /**
     * 校验订单状态是否允许下一步操作
     */
    public String checkApplyStatus() {

        switch (this.returnShopService.checkApplyStatus(orderAlterCode, orderAlterStatus)) {
            case 0:
                returnResult = new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "校验通过", "");
                break;
            case 1:
                returnResult =
                        new ReturnResult<Object>(InterfaceResultCode.FAILED, "校验退换货单状态异常", "");
                break;
            case 2:
                returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED, "参数为空", "");
                break;
            case 3:
                returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED, "非当前用户操作", "");
                break;
            case 4:
                returnResult = new ReturnResult<Object>(InterfaceResultCode.FAILED,
                        "审核已通过,7*24小时内不允许取消", "");
                break;
            default:
                returnResult = new ReturnResult<Object>(InterfaceResultCode.SUCCESS, "校验通过", "");
        }
        return SUCCESS;
    }

    /**
     * ajax检查申请是否过期
     *
     * @return
     * @author luoyi
     * @createDate 2013/10/30
     */
    public void checkOverdue() {
        HttpServletResponse response = ServletActionContext.getResponse();
        String result = "success";
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);

            orderAlter = returnShopService.findByCode(orderAlterCode);
            OrderMain om = myOrderService.findOrderByOrderCode(orderAlter.getOrderCode());

            if (om.getCustomerId().compareTo(new BigDecimal(userId)) != 0) {
                logger.error("非当前用户查询退换货单详情，currentUserID：" + userId + ",退换货单id:" + orderAlterCode);
                result = "notAuthority";
            } else {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(orderAlter.getCheckDate());
                calendar.add(Calendar.HOUR, Constants.ORDER_RETURN_TIME);
                /**
                 * 自退换货申请通过到未退货致失效时间 7*24小时过期
                 */
                if (calendar.getTime().getTime() < new Date().getTime()) {
                    result = "fail";
                    User user = securityCentreService.getUserByLoginId(userId);
                    operator = user.getLoginAccount();
                    returnShopService.changeOrderAlterStatus(operator, orderAlterCode,
                            (long) OrderAlterDictionaryEnum.Propose_Status.Cancel.getKey(), null,
                            null, null, null, userId);
                }
            }
        } catch (Exception e) {
            logger.info("查询订单付款信息出错" + e.getMessage());
        }
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            logger.error("计算退换货退款信息出错：" + e.getMessage(), e);
        }
    }

    /*----------上传图片----------*/
    // private final String savePath = ConfigurationUtil.getString("RETURNSHOP_PHOTO_UPLOAD_PATH");
    // // 上传到服务器的路径,请先自建好
    // private final String showPath = ConfigurationUtil.getString("RETURNSHOP_PHOTO_PATH");
    // private final String tmpPath = ConfigurationUtil.getString("RETURNSHOP_PHOTO_TEMP_PATH");//
    // 头像上传临时目录

    private File file;

    private String fileFileName;

    private String fileContentType;

    private String path = "";

    private String errorCode = "";

    String msg = "";

    /**
     * 上传图片操作
     *
     * @return
     */
    public void uploadPhoto() {
        String result = "fail";
        HttpServletResponse response = null;
        List urls = new ArrayList();
        try {
            response = ServletActionContext.getResponse();

            String targetDirectory =
                    ConfigurationUtil.getString("RETURNSHOP_PHOTO_TEMP_PATH") + "/" + batchNo;
            String targetFileFileName = generateFileFileName(fileFileName);
            File[] list = (new File(targetDirectory)).listFiles();
            int num = null == list ? 0 : list.length;

            if (5 <= num) {
                result = "outOfNum";
            } else {
                if (null != file) {

                    File target = new File(targetDirectory, targetFileFileName);

                    if (!file.exists()) {
                        // 处理文件大小为0kb的情况
                        file = new File(file.getPath());
                        FileWriter fileWriter = new FileWriter(file);
                        fileWriter.write(" ");
                        fileWriter.flush();
                        fileWriter.close();
                    }

                    FileUtils.copyFile(file, target);

                    // 图片浏览
                    File srcTemp = new File(targetDirectory);
                    File targeTemp =
                            new File(ConfigurationUtil.getString("RETURNSHOP_PHOTO_UPLOAD_PATH")
                                    + "/" + batchNo);
                    FileUtils.copyDirectory(srcTemp, targeTemp); // 存储在临时文件夹中用于浏览图片

                    list = (new File(targetDirectory)).listFiles();
                    for (int i = 0; i < list.length; i++) {
                        if (i < 5) {
                            // urls.add(targetDirectory + "/" + list[i].getName());
                            urls.add(ConfigurationUtil.getString("RETURNSHOP_PHOTO_PATH") + batchNo
                                    + "/" + list[i].getName());
                        }
                    }
                    result = "success";
                }
            }
        } catch (Exception e) {
            logger.error("上传图片出错：" + e.getMessage(), e);
        }
        try {
            JSONObject jo = new JSONObject();
            jo.put("result", result);
            jo.put("urls", urls);
            response.getWriter().write(jo.toString());
        } catch (IOException e) {
            logger.error("上传图片出错：" + e.getMessage(), e);
        }
    }

    /**
     * 删除图片
     *
     * @return
     */
    public void applySubmitCancel() {
        try {
            File srcDirectory = new File(
                    ConfigurationUtil.getString("RETURNSHOP_PHOTO_TEMP_PATH") + "/" + batchNo);
            FileUtils.deleteDirectory(srcDirectory);
        } catch (IOException e) {
            logger.error("删除图片出错：" + e.getMessage(), e);
        }
    }

    /**
     * 保存图片
     *
     * @return
     */
    @SuppressWarnings("unused")
    public void savePhoto() {
        /*
         * try { File srcDirectory = new File(tmpPath + "/" + batchNo); if (null ==
         * srcDirectory.listFiles() || 0 == srcDirectory.listFiles().length) { return; } File
         * targetDirectory = new File(savePath + "/" + batchNo); if(!targetDirectory.exists()){
         * return; } // FileUtils.copyDirectory(srcDirectory, targetDirectory);
         * //词句代码转移至uploadPhoto()方法中，用于上图图片是预览 FileUtils.deleteDirectory(srcDirectory); File[]
         * fileList = targetDirectory.listFiles(); OrderAlterPhoto photo = new OrderAlterPhoto();
         * photo.setBatchNo(batchNo); for (int i = 0, r; i < fileList.length; i++) {
         * photo.setUrl(batchNo + "/" + fileList[i].getName()); r =
         * returnShopService.savaPhoto(photo); if (i == 4) { break; } } } catch (Exception e) {
         * logger.error("保存图片出错：" + e.getMessage(), e); }
         */

        try {
            File target = new File(
                    ConfigurationUtil.getString("RETURNSHOP_PHOTO_UPLOAD_PATH") + "/" + batchNo);
            if (!target.getParentFile().exists()) {
                target.getParentFile().mkdirs();
            }
            OrderAlterPhoto photo = new OrderAlterPhoto();
            photo.setBatchNo(batchNo);
            if (image != null && image.length > 0) {
                for (int i = 0, r; i < image.length; i++) {
                    File saveFile = new File(target, imageFileName[i]);
                    FileUtils.copyFile(image[i], saveFile);
                    photo.setUrl(batchNo + "/" + imageFileName[i]);
                    r = returnShopService.savaPhoto(photo);
                    if (i == 4) {
                        break;
                    }
                }
            }

        } catch (Exception e) {
            logger.error("保存图片出错：" + e.getMessage(), e);
        }
    }

    @SuppressWarnings("unused")
    public void saveWapPhoto() {
        try {
            File target = new File(
                    ConfigurationUtil.getString("RETURNSHOP_PHOTO_UPLOAD_PATH") + "/" + batchNo);
            if (!target.getParentFile().exists()) {
                target.getParentFile().mkdirs();
            }
            OrderAlterPhoto photo = new OrderAlterPhoto();
            photo.setBatchNo(batchNo);
            if (image != null && image.length > 0) {
                for (int i = 0, r; i < image.length; i++) {
                    File saveFile = new File(target, imageFileName[i]);
                    FileUtils.copyFile(image[i], saveFile);
                    photo.setUrl(batchNo + "/" + imageFileName[i]);
                    r = returnShopService.savaPhoto(photo);
                    if (i == 4) {
                        break;
                    }
                }
            }

        } catch (Exception e) {
            logger.error("保存图片出错：" + e.getMessage(), e);
        }
    }

    /**
     * 生成文件名
     *
     * @param fileFileName
     * @return
     */
    public static String generateFileFileName(String fileFileName) {
        lock.lock();
        try {
            DateFormat format = new SimpleDateFormat("yyMMddHHmmss");
            String formatDate = format.format(new Date());

            int random = new Random().nextInt(10000);

            int position = fileFileName.lastIndexOf(".");
            String extension = fileFileName.substring(position);

            return formatDate + random + extension;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 文件大小换上为bite
     *
     * @param s
     * @return
     */
    @SuppressWarnings("unused")
    private long getFileSizeByBite(String s) {
        long size = 0;
        size = Long.parseLong(s) * 1073741824;
        return size;
    }

    public String formetFileSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /*----------*/

    /*--------------------------getters and setters--------------------------*/
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public String getOrderAlterCode() {
        return orderAlterCode;
    }

    public void setOrderAlterCode(String orderAlterCode) {
        this.orderAlterCode = orderAlterCode;
    }

    public long getOrderAlterStatus() {
        return orderAlterStatus;
    }

    public void setOrderAlterStatus(long orderAlterStatus) {
        this.orderAlterStatus = orderAlterStatus;
    }

    public OrderAlter getOrderAlter() {
        return orderAlter;
    }

    public void setOrderAlter(OrderAlter orderAlter) {
        this.orderAlter = orderAlter;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Short getAlterType() {
        return alterType;
    }

    public void setAlterType(Short alterType) {
        this.alterType = alterType;
    }

    public Long getAlterNum() {
        return alterNum;
    }

    public void setAlterNum(Long alterNum) {
        this.alterNum = alterNum;
    }

    public Long getEvidence() {
        return evidence;
    }

    public void setEvidence(Long evidence) {
        this.evidence = evidence;
    }

    public String getAlterComment() {
        return alterComment;
    }

    public void setAlterComment(String alterComment) {
        this.alterComment = alterComment;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Short getBackType() {
        return backType;
    }

    public void setBackType(Short backType) {
        this.backType = backType;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }


    public List getOperates() {
        return operates;
    }


    public void setOperates(List operates) {
        this.operates = operates;
    }


    public List getPays() {
        return pays;
    }


    public void setPays(List pays) {
        this.pays = pays;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(BigDecimal returnMoney) {
        this.returnMoney = returnMoney;
    }

    public BigDecimal getFareSubsidy() {
        return fareSubsidy;
    }

    public void setFareSubsidy(BigDecimal fareSubsidy) {
        this.fareSubsidy = fareSubsidy;
    }


    public List getAddressList() {
        return addressList;
    }


    public void setAddressList(List addressList) {
        this.addressList = addressList;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<TraceInfoVO> getListVo() {
        return listVo;
    }

    public void setListVo(List<TraceInfoVO> listVo) {
        this.listVo = listVo;
    }

    public String getProductImgServerUrl() {
        return ConfigurationUtil.getString("PRODUCT_IMG_PATH");
    }

    public String getProductPicPath_WAP() {
        return ConfigurationUtil.getString("productPicPath_WAP");
    }

    public String getCmsPagePath() {
        return ConfigurationUtil.getString("CMS_PAGE_PATH");
    }


    public List getPhotoList() {
        return photoList;
    }


    public void setPhotoList(List photoList) {
        this.photoList = photoList;
    }


    public Map getMap() {
        return map;
    }


    public void setMap(Map map) {
        this.map = map;
    }


    public List getFareTypeList() {
        return fareTypeList;
    }


    public void setFareTypeList(List fareTypeList) {
        this.fareTypeList = fareTypeList;
    }

    public String getShowPath() {
        return ConfigurationUtil.getString("RETURNSHOP_PHOTO_PATH");
    }

    public String getKmName() {
        return ConfigurationUtil.getString("contectUserName");
    }

    public String getKmAddress() {
        return ConfigurationUtil.getString("companyAddress");
    }

    public String getKmPhone() {
        return ConfigurationUtil.getString("companyPhone");
    }

    public String getCustomerLogisticsName() {
        return customerLogisticsName;
    }

    public void setCustomerLogisticsName(String customerLogisticsName) {
        this.customerLogisticsName = customerLogisticsName;
    }

    public Address getAddressVar() {
        return addressVar;
    }

    public void setAddressVar(Address addressVar) {
        this.addressVar = addressVar;
    }

    public Map<String, String> getLogisticsMap() {
        return logisticsMap;
    }

    public void setLogisticsMap(Map<String, String> logisticsMap) {
        this.logisticsMap = logisticsMap;
    }

    public String getKmZipCode() {
        return ConfigurationUtil.getString("zipCode");
    }

    public Long getMaxnum() {
        return maxnum;
    }

    public void setMaxnum(Long maxnum) {
        this.maxnum = maxnum;
    }

    public OrderAlterDictionaryEnum.OrderAlterOperateType[] getOperateTypes() {
        return operateTypes;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    public String getCheckOverDate() {
        return checkOverDate;
    }

    public void setCheckOverDate(String checkOverDate) {
        this.checkOverDate = checkOverDate;
    }

    public ReturnResult getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult returnResult) {
        this.returnResult = returnResult;
    }

    public int getPagenumber() {
        return pagenumber;
    }

    public void setPagenumber(int pagenumber) {
        this.pagenumber = pagenumber;
    }

    public File[] getImage() {
        return image;
    }

    public void setImage(File[] image) {
        this.image = image;
    }

    public String[] getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String[] imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String[] getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String[] imageContentType) {
        this.imageContentType = imageContentType;
    }

    public SupplierOrderItem getSupplierOrderItem() {
        return supplierOrderItem;
    }

    public void setSupplierOrderItem(SupplierOrderItem supplierOrderItem) {
        this.supplierOrderItem = supplierOrderItem;
    }

    public OrderMain getOm() {
        return om;
    }

    public void setOm(OrderMain om) {
        this.om = om;
    }

    public List<TraceInfoVO> getListVoline() {
        return listVoline;
    }

    public void setListVoline(List<TraceInfoVO> listVoline) {
        this.listVoline = listVoline;
    }


}
