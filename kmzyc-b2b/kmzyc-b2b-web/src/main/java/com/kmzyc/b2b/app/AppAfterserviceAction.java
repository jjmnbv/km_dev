package com.kmzyc.b2b.app;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.km.framework.fileupload.Upload;
import com.kmzyc.b2b.model.Address;
import com.kmzyc.b2b.model.OrderAlter;
import com.kmzyc.b2b.model.OrderItem;
import com.kmzyc.b2b.model.OrderMain;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.EraInfoService;
import com.kmzyc.b2b.service.OrderItemService;
import com.kmzyc.b2b.service.SecurityCentreService;
import com.kmzyc.b2b.service.member.MyOrderService;
import com.kmzyc.b2b.service.member.ReturnShopService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.framework.util.IOTools;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.entities.OrderAlterPhoto;
import com.pltfm.app.util.OrderAlterCodeUtil;
import com.pltfm.app.util.OrderAlterDictionaryEnum;

@SuppressWarnings("unchecked")
@Controller("appAfterserviceAction")
public class AppAfterserviceAction extends AppBaseAction {

    private static final long serialVersionUID = -8842926142956855956L;

    // private static Logger logger = Logger.getLogger(AppAfterserviceAction.class);
    private static Logger logger = LoggerFactory.getLogger(AppAfterserviceAction.class);
    @Resource(name = "orderItemServiceImpl")
    private OrderItemService orderItemService;
    @Resource(name = "myOrderServiceImpl")
    private MyOrderService myOrderService;
    @Resource(name = "returnShopServiceImpl")
    private ReturnShopService returnShopService;
    @Resource(name = "securityCentreServiceImpl")
    private SecurityCentreService securityCentreService;
    @Resource(name = "eraInfoServiceImpl")
    private EraInfoService eraInfoService;


    private ReturnResult<HashMap<String, Object>> returnResult;// 接口返回对象

    private long orderAlterStatus;// 退换货状态 --各程序中有固定值

    /*----------上传图片----------*/
    // private final String savePath = ConfigurationUtil.getString("RETURNSHOP_PHOTO_UPLOAD_PATH");
    // // 上传到服务器的路径,请先自建好
    private long fileMaximumSize = 2 * 1024 * 1024; // 上传文件的大小限制，
    private String[] allowTypes = {".jpg", ".jpeg", ".gif", ".png"};

    /**
     * app 退换货申请
     */
    public void applyAppSubmit() {
        try {
            /*-----获取传过来的参数  begin ------*/

            JSONObject params = AppJsonUtils.getJsonObject(this.getRequest());
            String orderCode =
                    params.containsKey("orderCode") ? params.getString("orderCode") : null; // 订单编号
            String orderItemId =
                    params.containsKey("orderItemId") ? params.getString("orderItemId") :
                            null; // 订单明细id
            String alterType =
                    params.containsKey("alterType") ? params.getString("alterType") : null; // 服务类型
            String alterNum =
                    params.containsKey("alterNum") ? params.getString("alterNum") : null; // 数量
            String evidence =
                    params.containsKey("evidence") ? params.getString("evidence") : null; // 凭据
            String alterComment =
                    params.containsKey("alterComment") ? params.getString("alterComment") :
                            null; // 描述
            String batchNo =
                    params.containsKey("batchNo") ? params.getString("batchNo") : null; // 上传图片批次号
            String backType =
                    params.containsKey("backType") ? params.getString("backType") : null; // 商品退回方式
            String addressId =
                    params.containsKey("addressId") ? params.getString("addressId") : null; // 地址id
            String name = params.containsKey("name") ? params.getString("name") : null; // 姓名
            String address =
                    params.containsKey("address") ? params.getString("address") : null; // 详细地址
            String phone = params.containsKey("phone") ? params.getString("phone") : null; // 手机号
            String province =
                    params.containsKey("province") ? params.getString("province") : null; // 省
            String city = params.containsKey("city") ? params.getString("citycity") : null; // 市
            String area = params.containsKey("area") ? params.getString("area") : null; // 县
            String zipCode =
                    params.containsKey("zipCode") ? params.getString("zipCode") : null; // 邮编

            /*-----获取传过来的参数  end ------*/

            if (Strings.isNullOrEmpty(alterType)) {
                String errorStr = "传入的参数alterType为空";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }
            String uid = this.getUserid();// 获取用户id
            if (null == uid || "".equals(uid)) {
                String errorStr = "传入的用户Id为空";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }

            if (null == orderItemId || "".equals(orderItemId)) {
                String errorStr = "传入的订单明细Id为空";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }

            Long orderItemIdL = null;
            orderItemIdL = Long.parseLong(orderItemId);

            OrderItem orderItem;// 订单明细
            // orderItem = orderItemService.findById(orderItemIdL); findByIdForReturnShop
            orderItem = orderItemService.findByIdForReturnShop(orderItemIdL);
            if (null == orderItem) {
                String errorStr = "订单明细表中无订单明细id为【" + orderItemId + "】的数据";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }

            Long userIdt = Long.parseLong(uid.trim());
            // 判断是否是时代会员
            Map<String, Object> map = new HashMap<>();
            map.put("loginId", userIdt);
            com.kmzyc.b2b.vo.EraInfo eraInfo;
            try {
                eraInfo = eraInfoService.selectEranInfoById(map);
                if (null != eraInfo && orderItem.getCommodityPv().floatValue() > 0f) {
                    String errorStr =
                            "uid=" + uid + "为时代会员，且pv值=" + orderItem.getCommodityPv() + ",不能进行退货货！";
                    logger.error(errorStr);
                    returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                    printJsonString(returnResult);
                    return;
                }
            } catch (SQLException e) {
                String errorStr = "根据uid=" + uid + "查询时代信息失败";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }

            if (orderCode != null && !"".equals(orderCode)) {
                if (!orderCode.equals(orderItem.getOrderCode())) {
                    String errorStr =
                            "传入的订单编号【" + orderCode + "】与传入的订单明细id【" + orderItemId + "】所对应的订单编号【" +
                                    orderItem.getOrderCode() + "】不一致";
                    logger.error(errorStr);
                    returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                    printJsonString(returnResult);
                    return;
                }
            }

            if (orderItem.getIsReturning() != 0L) { // 判断是否在退货中
                String errorStr = "订单商品正在退换货中,orderItemId:" + orderItemId;
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }

            OrderMain om = myOrderService.findOrderByOrderCode(orderItem.getOrderCode());
            if (null == om) {
                String errorStr = "订单主表中无订单号为【" + orderItem.getOrderCode() + "】的订单信息";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }

            if (om.getCustomerId().compareTo(new BigDecimal(uid.trim())) != 0) {
                String errorStr = "当前用户currentUserID：" + uid + "与订单记录用户不一致";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }

            if (om.getOrderStatus().intValue() < 3) {
                String errorStr = "订单状态om.getOrderStatus()【" + om.getOrderStatus().toString() +
                        "】下不能申请退换货!,orderItemId:" + orderItemId;
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }

            User user = securityCentreService.getUserByLoginId(userIdt);// 查询用户信息(login_info)
            if (null == user) {
                String errorStr = "userId【" + userIdt + "】无对应用户信息";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }
            String operator;
            operator = user.getLoginAccount();// 登录账号


            /*---------类型转换 begin---------  */
            Integer zipCodeI = null;
            if (null != zipCode && !"".equals(zipCode)) {
                zipCodeI = Integer.parseInt(zipCode.trim());
            }

            Short alterTypeS = Short.parseShort(alterType.trim());

            Long alterNumL = null;
            if (null != alterNum && !"".equals(alterNum)) {
                alterNumL = Long.parseLong(alterNum.trim());
            }

            Long evidenceL = null;
            if (null != evidence && !"".equals(evidence)) {
                evidenceL = Long.parseLong(evidence.trim());
            }

            Short backTypeS = 1;
            if (null != backType && !"".equals(backType)) {
                backTypeS = Short.parseShort(backType.trim());
            }

            Integer addressIdI = null;
            if (null != addressId && !"".equals(addressId)) {
                addressIdI = Integer.parseInt(addressId.trim());
            }

            /*---------类型转换 end---------  */

            int result = returnShopService
                    .apply(operator, orderCode.trim(), orderItemIdL, alterTypeS, alterNumL,
                            evidenceL, alterComment, batchNo, backTypeS, addressIdI, name, province,
                            city, area, zipCodeI, address, phone,
                            orderItem.getCommodityBatchNumber(), userIdt);
            if (1 != result) {
                String errorStr = "退换货申请失败！";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }

        } catch (Exception e) {
            logger.error("退换货申请出错：" + e.getMessage(), e);
            String errorStr = "退换货申请出错：" + e.getMessage();
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
            printJsonString(returnResult);

        }
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "申请成功!", "");
        printJsonString(returnResult);

    }

    /**
     * 取消退/换货
     */
    public void cancelAppOrderAlter() {
        try {

            /*-----获取传过来的参数  begin ------*/
            JSONObject params = AppJsonUtils.getJsonObject(this.getRequest());
            String orderAlterCode =
                    params.containsKey("orderAlterCode") ? params.getString("orderAlterCode") :
                            null; // 退换货单号
            String customerLogisticsCode = params.containsKey("customerLogisticsCode") ?
                    params.getString("customerLogisticsCode") : null; // 物流公司代号
            String customerLogisticsNo = params.containsKey("customerLogisticsNo") ?
                    params.getString("customerLogisticsNo") : null; // 物流单号
            /*-----获取传过来的参数  end ------*/
            String uid = this.getUserid();// 获取用户id
            if (null == uid || "".equals(uid)) {
                String errorStr = "传入的用户Id为空";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }


            if (null == orderAlterCode || "".equals(orderAlterCode)) {
                String errorStr = "传入参数【orderAlterCode】为空";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }
            OrderAlter orderAlter;
            orderAlter = returnShopService.findByCode(orderAlterCode.trim());
            if (null == orderAlter) {
                String errorStr = "order_alter中无退换货单号为【" + orderAlterCode + "】的记录";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }

            OrderMain om = myOrderService.findOrderByOrderCode(orderAlter.getOrderCode());
            if (null == om) {
                String errorStr = "订单主表中无订单号为【" + orderAlter.getOrderCode() + "】的订单信息";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }

            if (om.getCustomerId().compareTo(new BigDecimal(uid.trim())) != 0) {
                String errorStr =
                        "非当前用户查询退换货单详情，currentUserID：" + uid + ",退换货单id:" + orderAlterCode;
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }

            if (orderAlter.getProposeStatus() ==
                    OrderAlterDictionaryEnum.Propose_Status.Pass.getKey()) {// 已通过待退货
                // Calendar calendar = Calendar.getInstance();
                // calendar.setTime(orderAlter.getCheckDate());
                // calendar.add(Calendar.HOUR, Constants.ORDER_RETURN_TIME);
                // /**
                // * 判断取消期限 审核通过后7*24小时内不能取消
                // */
                // if (calendar.getTime().getTime() >= new Date().getTime()) {
                String errorStr =
                        "orderAlterCode【" + orderAlterCode + "】退换货申请状态为:已通过待退货,此退换货申请不能取消";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
                // }
            }

            Long userIdt = Long.parseLong(uid.trim());
            User user = securityCentreService.getUserByLoginId(userIdt);
            if (null == user) {
                String errorStr = "userId【" + userIdt + "】无对应用户信息";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }

            String operator = user.getLoginAccount();

            // Map<String, String> logisticsMap = new HashMap<String, String>();
            // logisticsMap = LogisticsCompanyMap.getMap();
            String customerLogisticsName = ConfigurationUtil
                    .getString(customerLogisticsCode);// 物流公司名称
            // if (null != logisticsMap) {
            // customerLogisticsName = logisticsMap.get(customerLogisticsCode);
            // }

            orderAlterStatus = Long.parseLong("-1");// 已取消
            int i = returnShopService
                    .changeOrderAlterStatus(operator, orderAlterCode.trim(), orderAlterStatus,
                            customerLogisticsCode, customerLogisticsNo, null, customerLogisticsName,
                            userIdt);
            if (1 != i) {
                String errorStr = "取消失败！";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;

            }
        } catch (Exception e) {
            logger.error("取消退换货失败:" + e.getMessage(), e);
            String errorStr = "取消退换货失败:" + e.getMessage();
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
            printJsonString(returnResult);

        }
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "取消成功!", "");
        printJsonString(returnResult);

    }


    // 退回商品
    public void returnAppGoods() {
        try {

            /*-----获取传过来的参数  begin ------*/
            JSONObject params = AppJsonUtils.getJsonObject(this.getRequest());
            String addressId =
                    params.containsKey("addressId") ? params.getString("addressId") : null; // 地址id
            String name = params.containsKey("name") ? params.getString("name") : null; // 姓名
            String address =
                    params.containsKey("address") ? params.getString("address") : null; // 详细地址
            String phone = params.containsKey("phone") ? params.getString("phone") : null; // 手机号
            String province =
                    params.containsKey("province") ? params.getString("province") : null; // 省
            String city = params.containsKey("city") ? params.getString("city") : null; // 市
            String area = params.containsKey("area") ? params.getString("area") : null; // 县
            String zipCode =
                    params.containsKey("zipCode") ? params.getString("zipCode") : null; // 邮编
            String orderAlterCode =
                    params.containsKey("orderAlterCode") ? params.getString("orderAlterCode") :
                            null; // 退换货单号
            String customerLogisticsCode = params.containsKey("customerLogisticsCode") ?
                    params.getString("customerLogisticsCode") : null; // 物流公司代号
            String customerLogisticsNo = params.containsKey("customerLogisticsNo") ?
                    params.getString("customerLogisticsNo") : null; // 物流单号

            /*-----获取传过来的参数  end ------*/
            String uid = this.getUserid();// 获取用户id
            if (null == uid || "".equals(uid)) {
                String errorStr = "传入的用户Id为空";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }


            if (null == orderAlterCode || "".equals(orderAlterCode)) {
                String errorStr = "传入参数【orderAlterCode】为空";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }
            OrderAlter orderAlter = returnShopService.findByCode(orderAlterCode.trim());
            if (null == orderAlter) {
                String errorStr = "order_alter中无退换货单号为【" + orderAlterCode + "】的记录";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }

            OrderMain om = myOrderService.findOrderByOrderCode(orderAlter.getOrderCode());
            if (null == om) {
                String errorStr = "订单主表中无订单号为【" + orderAlter.getOrderCode() + "】的订单信息";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }

            if (om.getCustomerId().compareTo(new BigDecimal(uid.trim())) != 0) {
                String errorStr =
                        "非当前用户查询退换货单详情，currentUserID：" + uid + ",退换货单id:" + orderAlterCode;
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }

            Long userIdt = Long.parseLong(uid.trim());
            User user = securityCentreService.getUserByLoginId(userIdt);
            if (null == user) {
                String errorStr = "userId【" + user + "】无对应用户信息";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }

            String operator;
            operator = user.getLoginAccount();

            // Map<String, String> logisticsMap = new HashMap<String, String>();
            // logisticsMap = LogisticsCompanyMap.getMap();
            String customerLogisticsName = ConfigurationUtil
                    .getString(customerLogisticsCode);// 物流公司名称
            // if (null != logisticsMap) {
            // customerLogisticsName = logisticsMap.get(customerLogisticsCode);
            // }

            orderAlterStatus = 3L;// 已退货待取件
            Address addressVart = new Address();

            if (!Strings.isNullOrEmpty(addressId)) {
                addressVart.setAddressId(Integer.parseInt(addressId.trim()));// 地址ID
            }

            addressVart.setName(name);// 姓名
            addressVart.setLoginId(userIdt.intValue());
            addressVart.setDetailedAddress(address);// 详细信息
            addressVart.setCellphone(phone);// 手机号
            addressVart.setProvince(province);// 省
            addressVart.setCity(city);// 市
            addressVart.setArea(area);// 县
            addressVart.setPostalcode(zipCode);// 邮编
            int i = returnShopService
                    .changeOrderAlterStatus(operator, orderAlterCode.trim(), orderAlterStatus,
                            customerLogisticsCode, customerLogisticsNo, addressVart,
                            customerLogisticsName, userIdt);
            if (1 != i) {
                String errorStr = "退回商品失败!";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }
        } catch (Exception e) {

            logger.error("退回商品失败:" + e.getMessage());
            String errorMessage = e.getMessage();
            if (null == errorMessage) {
                errorMessage = "";
            }
            String errorStr = "退回商品失败! " + errorMessage;
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
            printJsonString(returnResult);
        }
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "退回商品操作成功!", "");
        printJsonString(returnResult);

    }


    /**
     * 确认退货/换货
     */
    public void checkAppApply() {
        try {
            /*-----获取传过来的参数  begin ------*/
            JSONObject params = AppJsonUtils.getJsonObject(this.getRequest());
            String orderAlterCode =
                    params.containsKey("orderAlterCode") ? params.getString("orderAlterCode") :
                            null; // 退换货单号
            String customerLogisticsCode = params.containsKey("customerLogisticsCode") ?
                    params.getString("customerLogisticsCode") : null; // 物流公司代号
            String customerLogisticsNo = params.containsKey("customerLogisticsNo") ?
                    params.getString("customerLogisticsNo") : null; // 物流单号
            /*-----获取传过来的参数  end ------*/
            String uid = this.getUserid();// 获取用户id
            if (null == uid || "".equals(uid)) {
                String errorStr = "传入的用户Id为空";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }
            Long userIdt = Long.parseLong(uid.trim());

            if (null == orderAlterCode || "".equals(orderAlterCode)) {
                String errorStr = "传入参数【orderAlterCode】为空";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }
            OrderAlter orderAlter = returnShopService.findByCode(orderAlterCode.trim());
            if (null == orderAlter) {
                String errorStr = "order_alter中无退换货单号为【" + orderAlterCode + "】的记录";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }

            OrderMain om = myOrderService.findOrderByOrderCode(orderAlter.getOrderCode());
            if (null == om) {
                String errorStr = "订单主表中无订单号为【" + orderAlter.getOrderCode() + "】的订单信息";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }

            if (om.getCustomerId().compareTo(new BigDecimal(uid.trim())) != 0) {
                String errorStr =
                        "非当前用户查询退换货单详情，currentUserID：" + uid + ",退换货单id:" + orderAlterCode;
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }

            User user = securityCentreService.getUserByLoginId(userIdt);
            if (null == user) {
                String errorStr = "userId【" + userIdt + "】无对应用户信息";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }

            String operator = "";
            operator = user.getLoginAccount();

            // Map<String, String> logisticsMap = new HashMap<String, String>();
            // logisticsMap = LogisticsCompanyMap.getMap();
            String customerLogisticsName = ConfigurationUtil
                    .getString(customerLogisticsCode);// 物流公司名称
            // if (null != logisticsMap) {
            // customerLogisticsName = logisticsMap.get(customerLogisticsCode);
            // }

            orderAlterStatus = 7L;
            int i = returnShopService
                    .changeOrderAlterStatus(operator, orderAlterCode.trim(), orderAlterStatus,
                            customerLogisticsCode, customerLogisticsNo, null, customerLogisticsName,
                            userIdt);
            if (1 != i) {
                String errorStr = "确认失败!";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }
        } catch (Exception e) {
            logger.error("确认退换货失败:" + e.getMessage(), e);
            String errorStr = "确认退换货失败:" + e.getMessage();
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
            printJsonString(returnResult);

        }
        returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "确认成功!", "");
        printJsonString(returnResult);

    }


    public void savaWapPhotoTest() {
        String uid = this.getUserid();// 获取用户id
        if (null == uid || "".equals(uid)) {
            String errorStr = "传入的用户Id为空";
            logger.error(errorStr);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
            printJsonString(returnResult);
            return;
        }
        String batchNo = OrderAlterCodeUtil.generateOrderAlterPhotoBatchNo();
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        if (null != jsonParams && !jsonParams.isEmpty()) {

            String fileString = jsonParams.getString("fileString");
            logger.info("上传头像文件，传入的参数为：uid【" + uid + "】，fileString【" + fileString + "】");


            String errorMessage;
            // 上传文件为空
            if (fileString == null) {
                errorMessage = "上传的文件为空!";
                logger.error(errorMessage);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorMessage, null);
                printJsonString(returnResult);
                return;
            }

            String[] fileStrings = fileString.split(",");
            String savePath = ConfigurationUtil.getString("RETURNSHOP_PHOTO_UPLOAD_PATH");
            for (int i = 0; i < fileStrings.length; i++) {
                File target = new File(savePath + "/" + batchNo);
                if (!target.getParentFile().exists()) {
                    target.getParentFile().mkdirs();
                }
                String fileName = uid + i;
                String phoneImgPath = savePath + "phoneImg";
                File file = new File(phoneImgPath + "/" + fileName + ".jpeg");
                File saveFile = new File(target, fileName + ".jpeg");
                // 将字符转换陈图片
                boolean fileFlag = this.xmlString2Bin(fileStrings[i], file);
                logger.info("将字符转换为图片结果为：【" + fileFlag + "】");
                // 转换成功
                if (fileFlag) {
                    // 获取文件后缀名称
                    if (file.getName().lastIndexOf(".") < 0) {
                        errorMessage = "缺少上传的文件类型后缀!";
                        logger.error("缺少上传的文件类型后缀!");
                        returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorMessage,
                                null);
                        printJsonString(returnResult);
                        return;
                    }
                    String filenExtendedStr = file.getName()
                            .substring(file.getName().lastIndexOf("."));
                    String[] allowTypes = {".jpg", ".jpeg", ".gif"};
                    if (!Upload.isValid(filenExtendedStr, allowTypes)) {
                        errorMessage = "上传的文件类型不允许!";
                        logger.error("上传的文件类型不允许!");
                        returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorMessage,
                                null);
                        printJsonString(returnResult);
                        return;
                    }
                    long fileMaximumSize = 2 * 1024 * 1024; // 上传文件的大小限制，
                    if (file.length() > fileMaximumSize) {
                        errorMessage = "图片文件大小超过2M";
                        logger.error("图片文件大小超过2M");
                        returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorMessage,
                                null);
                        printJsonString(returnResult);
                        return;
                    }
                    try {
                        FileUtils.copyFile(file, saveFile);
                        OrderAlterPhoto photo = new OrderAlterPhoto();
                        photo.setBatchNo(batchNo);
                        photo.setUrl(batchNo + "/" + fileName + ".jpeg");
                        int r = returnShopService.savaPhoto(photo);
                        if (r == 4) {
                            break;
                        }

                    } catch (IOException e) {
                        logger.error("上传文件出错：" + e.getMessage(), e);
                        errorMessage = "上传文件出错";
                        returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorMessage,
                                null);
                        printJsonString(returnResult);
                    } catch (SQLException sqe) {
                        logger.error("更新用户图像至数据库出错：" + sqe.getMessage(), sqe);
                        returnResult = new ReturnResult(InterfaceResultCode.FAILED, "图片保存失败", null);
                        printJsonString(returnResult);
                    } catch (Exception e) {
                        logger.error("保存图片出错：" + e.getMessage(), e);
                        returnResult = new ReturnResult(InterfaceResultCode.FAILED, "图片保存失败", null);
                        printJsonString(returnResult);
                    }


                } else {
                    // 字符转换成图片不成功
                    errorMessage = "图片转换出错";
                    returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorMessage, null);
                    printJsonString(returnResult);
                    return;
                }
            }
            returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "图片上传成功!", "");
            printJsonString(returnResult);


        } else {
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "请求参数错误", null);
            printJsonString(returnResult);
            return;
        }

        printJsonString(returnResult);
    }

    public void saveWapPhoto() {
        String errorMessage;
        this.getRequest().getParameter("userImage");
        if (this.getUpFile() == null) {
            errorMessage = "上传的文件为空!";
            logger.error(errorMessage);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorMessage, null);
            printJsonString(returnResult);
            return;
        }
        if (this.getUpFileFileName().lastIndexOf(".") < 0) {
            errorMessage = "缺少上传的文件类型后缀!";
            logger.error(errorMessage);
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorMessage, null);
            printJsonString(returnResult);
            return;
        }
        String filenExtendedStr = this.getUpFileFileName()
                .substring(this.getUpFileFileName().lastIndexOf("."));
        if (!Upload.isValid(filenExtendedStr, allowTypes)) {
            errorMessage = "上传的文件类型不允许!";
            logger.error("上传的文件类型不允许!");
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorMessage, null);
            printJsonString(returnResult);
            return;
        }
        if (this.getUpFile().length() > fileMaximumSize) {
            errorMessage = "图片文件大小超过2M";
            logger.error("图片文件大小超过2M");
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorMessage, null);
            printJsonString(returnResult);
            return;
        }
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        String dateStr = new java.text.SimpleDateFormat("yyyyMMddhhmmSS")
                .format(new java.util.Date());
        if (null != jsonParams && !jsonParams.isEmpty()) {

            String fileName;
            String uid = this.getUserid();// 获取用户id
            if (Strings.isNullOrEmpty(uid)) {
                String errorStr = "传入的用户Id为空";
                logger.error(errorStr);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
                printJsonString(returnResult);
                return;
            }
            fileName = uid + dateStr;
            // String batchNo =
            // jsonParams.containsKey("orderAlterCode")
            // ? jsonParams.getString("orderAlterCode").substring(2)
            // : null;
            // if (null == batchNo || "".equals(batchNo)) {
            // String errorStr = "传入的用户退换货单号为空，生成图片批次号失败！";
            // logger.error(errorStr);
            // returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorStr, "");
            // printJsonString(returnResult);
            // return;
            // }
            String batchNo =
                    jsonParams.containsKey("batchNo") ? jsonParams.getString("batchNo") : null;
            if (null == batchNo || "".equals(batchNo)) {
                batchNo = OrderAlterCodeUtil.generateOrderAlterPhotoBatchNo();
            }

            String originalImgName = fileName + filenExtendedStr;
            String originalImgPath =
                    ConfigurationUtil.getString("RETURNSHOP_PHOTO_UPLOAD_PATH") + batchNo + "/" +
                            originalImgName;
            try {
                FileUtils.copyFile(this.getUpFile(), new File(originalImgPath));
                logger.info(this.getUpFileFileName() + "文件上传成功，上传至服务器路径" + originalImgPath);
                OrderAlterPhoto photo = new OrderAlterPhoto();
                photo.setBatchNo(batchNo);
                photo.setUrl(batchNo + "/" + originalImgName);
                returnShopService.savaPhoto(photo);
            } catch (IOException e) {
                logger.error("上传文件出错：" + e.getMessage(), e);
                errorMessage = "上传文件出错";
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, errorMessage, null);
                printJsonString(returnResult);
                return;
            } catch (Exception e) {
                logger.error("保存图片出错：" + e.getMessage(), e);
                returnResult = new ReturnResult(InterfaceResultCode.FAILED, "图片保存失败", null);
                printJsonString(returnResult);
            }

            Map<String, Object> map = new HashMap<>();
            map.put("batchNo", batchNo);
            returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "图片上传成功成功", map);
            printJsonString(returnResult);
        } else {
            returnResult = new ReturnResult(InterfaceResultCode.FAILED, "请求参数为空", null);
            printJsonString(returnResult);
        }
    }


    public boolean xmlString2Bin(String base64String, File file) {
        byte[] data;
        FileOutputStream output = null;
        boolean ret = false;
        try {
            data = Base64.decodeBase64(base64String.getBytes());
            output = new FileOutputStream(file);
            output.write(data);
            output.flush();
            ret = true;
        } catch (Exception e) {
            logger.error("文件由字符转换成图片出错：" + e.getMessage(), e);
        } finally {
            IOTools.closeIO(output);
        }
        return ret;
    }


    /*--------------------------getters and setters--------------------------*/


    public long getOrderAlterStatus() {
        return orderAlterStatus;
    }

    public void setOrderAlterStatus(long orderAlterStatus) {
        this.orderAlterStatus = orderAlterStatus;
    }

    public ReturnResult<HashMap<String, Object>> getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult<HashMap<String, Object>> returnResult) {
        this.returnResult = returnResult;
    }


}
