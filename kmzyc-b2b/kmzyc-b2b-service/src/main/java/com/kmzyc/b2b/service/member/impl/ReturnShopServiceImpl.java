package com.kmzyc.b2b.service.member.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.dao.OrderAlterDao;
import com.kmzyc.b2b.dao.OrderAlterPhotoDao;
import com.kmzyc.b2b.dao.member.MyOrderDao;
import com.kmzyc.b2b.model.Address;
import com.kmzyc.b2b.model.OrderAlter;
import com.kmzyc.b2b.model.OrderMain;
import com.kmzyc.b2b.service.member.AddressService;
import com.kmzyc.b2b.service.member.MyOrderService;
import com.kmzyc.b2b.service.member.ReturnShopService;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.order.remote.OrderAlterChangeStatusRemoteService;
import com.pltfm.app.entities.OrderAlterPhoto;
import com.pltfm.app.util.OrderAlterDictionaryEnum;

// import com.km.framework.common.util.RemoteTool;

@SuppressWarnings("unchecked")
@Service
public class ReturnShopServiceImpl implements ReturnShopService {

    // private static Logger logger = Logger.getLogger(ReturnShopServiceImpl.class);
    private static Logger logger = LoggerFactory.getLogger(ReturnShopServiceImpl.class);

    @Resource(name = "myOrderDaoImpl")
    private MyOrderDao myOrderDao;
    @Resource(name = "orderAlterDaoImpl")
    private OrderAlterDao orderAlterDao;
    @Resource(name = "orderAlterPhotoDaoImpl")
    private OrderAlterPhotoDao orderAlterPhotoDao;

    @Resource(name = "addressServiceImpl")
    private AddressService addressService;

    @Resource
    private OrderAlterChangeStatusRemoteService orderAlterChangeStatusRemoteService;

    /**
     * 根据查询条件进行分页查询
     * 
     * @param page
     * @return
     */
    @Override
    public Pagination findReturnShopByPage(Pagination page) throws SQLException {
        Map<String, Object> conditon = (Map<String, Object>) page.getObjCondition();
        logger.info("开始查询用户的退换货记录列表,userId:" + conditon.get("userId") + ",keyword:"
                + conditon.get("keyword") + ",status:" + conditon.get("status"));
        long startTime = System.currentTimeMillis();
        // 使用退换货记录数据源

        page = orderAlterDao.findByPage("OrderAlter.findByPage", "OrderAlter.countByPage", page);
        logger.info("查询用户的退换货记录列表结束，耗时" + (System.currentTimeMillis() - startTime) / 1000 + "秒");
        return page;
    }

    /*
     * 根据订单号查询订单
     */
    @Override
    public OrderAlter findByCode(String orderAlterCode) throws Exception {
        try {

            List list =
                    orderAlterDao.findByProperty("OrderAlter.findByOrderAlterCode", orderAlterCode);
            if (null == list || 0 == list.size()) {
                return null;
            }
            return (OrderAlter) list.get(0);
        } catch (Exception e) {
            logger.info("查询用户的退换货记录出错:" + e.getMessage(), e);
            throw e;
        }
    }

    /*
     * 退换货申请
     */
    @Override
    public int apply(String operator, String orderCode, Long orderItemId, Short alterType,
            Long alterNum, Long evidence, String alterComment, String batchNo, Short backType,
            Integer addressId, String name, String province, String city, String area,
            Integer zipcode, String address, String phone, String commodityBatchNumber, long loginId)
            throws Exception {
        try {
            // OrderAlterChangeStatusRemoteService orderAlterChangeStatusRemoteService =
            // (OrderAlterChangeStatusRemoteService) RemoteTool.getRemote(
            // Constants.REMOTE_SERVICE_ORDER, "orderAlterChangeStatusService");
            // TestLocalRemote.getOrderAlterChangeStatusService();
            com.pltfm.app.entities.OrderAlter oa = new com.pltfm.app.entities.OrderAlter();
            oa.setProposer(operator);
            oa.setOrderCode(orderCode);
            oa.setOrderItemId(orderItemId);
            oa.setAlterType(alterType);
            oa.setAlterNum(alterNum);
            oa.setEvidence(evidence);
            oa.setAlterComment(alterComment.equals("请您如实填写申请原因以及商品情况") ? "" : alterComment);
            oa.setPhotoBatchNo(batchNo);
            oa.setBackType(backType);
            if (null != addressId) {
                oa.setAddressId(BigDecimal.valueOf(addressId));
                Address ar = addressService.findByNAddressID(loginId, addressId);
                oa.setName(ar.getName());
                oa.setProvince(ar.getProvince());
                oa.setCity(ar.getCity());
                oa.setArea(ar.getArea());
                if (null != ar.getPostalcode() && !"".equals(ar.getPostalcode().trim())) {
                    oa.setZipcode(Integer.valueOf(ar.getPostalcode()));
                }
                oa.setAddress(ar.getDetailedAddress());
                oa.setPhone(ar.getCellphone());
            } else {
                oa.setName(name);
                oa.setProvince(province);
                oa.setCity(city);
                oa.setArea(area);
                oa.setZipcode(zipcode);
                oa.setAddress(address);
                oa.setPhone(phone);
            }
            oa.setCommodityBatchNumber(commodityBatchNumber);
            int result = orderAlterChangeStatusRemoteService.alter(oa);
            if (1 != result) {
                throw new Exception();
            }
            return result;
        } catch (Exception e) {
            logger.info("申请退换货出错:" + e.getMessage(), e);
            return -1;
        }
    }

    /*
     * 更改退换货单状态
     */
    @Override
    public int changeOrderAlterStatus(String operator, String orderAlterCode, Long status,
            String code, String no, Address addressVar, String customerLogisticsName, long loginId)
            throws Exception {
        try {
            // OrderAlterChangeStatusRemoteService orderAlterChangeStatusRemoteService =
            // (OrderAlterChangeStatusRemoteService) RemoteTool.getRemote(
            // Constants.REMOTE_SERVICE_ORDER, "orderAlterChangeStatusService");
            // TestLocalRemote.getOrderAlterChangeStatusService();
            com.pltfm.app.entities.OrderAlter oa = new com.pltfm.app.entities.OrderAlter();
            oa.setOrderAlterCode(orderAlterCode);
            oa.setProposeStatus(status.shortValue());
            oa.setCustomerLogisticsCode(code);
            oa.setCustomerLogisticsNo(no);
            oa.setCustomerLogisticsName(customerLogisticsName);
            if (null != addressVar) {// 退货
                // return
                // orderAlterChangeStatusRemoteService.changeAlterStatus(operator,
                // orderAlterCode
                // , status,code, no
                // ,null,null,null,null,null,null,null,null,customerLogisticsName);
                // }else{//换货
                // return
                // orderAlterChangeStatusRemoteService.changeAlterStatus(operator,
                // orderAlterCode, status,code, no
                // ,0==addressVar.getAddressId()?null:BigDecimal.valueOf(addressVar.getAddressId()),addressVar.getName(),
                // addressVar.getProvince(),addressVar.getCity(),addressVar.getArea(),
                // (null==addressVar.getPostalcode()||"".equals(addressVar.getPostalcode()))?null:Integer.valueOf(addressVar.getPostalcode()),
                // addressVar.getDetailedAddress(),addressVar.getCellphone(),customerLogisticsName);
                if (0 != addressVar.getAddressId()) {
                    Address ar =
                            addressService.findByNAddressID(loginId, addressVar.getAddressId());
                    oa.setAddressId(BigDecimal.valueOf(addressVar.getAddressId()));
                    oa.setName(ar.getName());
                    oa.setProvince(ar.getProvince());
                    oa.setCity(ar.getCity());
                    oa.setArea(ar.getArea());
                    oa.setZipcode((null == ar.getPostalcode() || "".equals(ar.getPostalcode())) ? null
                            : Integer.valueOf(ar.getPostalcode()));
                    oa.setAddress(ar.getDetailedAddress());
                    oa.setPhone(ar.getCellphone());
                } else {
                    oa.setName(addressVar.getName());
                    oa.setProvince(addressVar.getProvince());
                    oa.setCity(addressVar.getCity());
                    oa.setArea(addressVar.getArea());
                    oa.setZipcode((null == addressVar.getPostalcode() || "".equals(addressVar
                            .getPostalcode())) ? null : Integer.valueOf(addressVar.getPostalcode()));
                    oa.setAddress(addressVar.getDetailedAddress());
                    oa.setPhone(addressVar.getCellphone());
                }
            }
            return orderAlterChangeStatusRemoteService.changeAlterStatus(operator, oa);
        } catch (Exception e) {
            logger.error("更改退换货单状态出错:" + e.getMessage(), e);
            throw e;
        }
    }



    @Override
    public int savaPhoto(OrderAlterPhoto photo) throws Exception {
        try {
            // OrderAlterChangeStatusRemoteService orderAlterChangeStatusRemoteService =
            // (OrderAlterChangeStatusRemoteService) RemoteTool.getRemote(
            // Constants.REMOTE_SERVICE_ORDER, "orderAlterChangeStatusService");
            return orderAlterChangeStatusRemoteService.savaPhoto(photo);
        } catch (Exception e) {
            logger.info("保存退换货图片出错:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List getPhotoByBatchNo(String photoBatchNo) throws Exception {
        try {
            return orderAlterPhotoDao.findByProperty("OrderAlterPhoto.findByPhotoBatchNo",
                    photoBatchNo);
        } catch (SQLException e) {
            logger.info("查询退换货图片出错:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List listFareTypes() throws Exception {
        try {
            return orderAlterPhotoDao.findList("FareType.listAll");
        } catch (SQLException e) {
            logger.info("查询快递公司列表出错:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Pagination findReturnOrderMainByPage(Pagination page) throws SQLException {
        // 使用订单数据源

        return myOrderDao.findByPage("OrderMain.findByPageForReturnShop",
                "OrderMain.countByPageForReturnShop", page);
    }

    @Resource(name = "myOrderServiceImpl")
    private MyOrderService myOrderService;

    @Override
    public int checkApplyStatus(String orderAlterCode,long orderAlterStatus){
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            Long userId = (Long) request.getSession().getAttribute(Constants.SESSION_USER_ID);

            if (null == orderAlterCode || "".equals(orderAlterCode)) {
                logger.error("方法changeApplyStatus中orderAlterCode传入为空");
                return 2;
            }
            OrderAlter orderAlter = this.findByCode(orderAlterCode);
            OrderMain om = myOrderService.findOrderByOrderCode(orderAlter.getOrderCode());

            if (om.getCustomerId().compareTo(new BigDecimal(userId)) != 0) {
                logger.error("非当前用户查询退换货单详情，currentUserID：" + userId + ",退换货单id:" + orderAlterCode);
                return 3;
            }

            if (orderAlterStatus == OrderAlterDictionaryEnum.Propose_Status.Cancel.getKey()) {
                if (orderAlter.getProposeStatus() == OrderAlterDictionaryEnum.Propose_Status.Pass.getKey()) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(orderAlter.getCheckDate());
                    calendar.add(Calendar.HOUR, Constants.ORDER_RETURN_TIME);
                    /**
                     * 判断取消期限 审核通过后7*24小时内不能取消
                     */
                    if (calendar.getTime().getTime() >= new Date().getTime()) {
                        return 4;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("校验退换货单状态异常！" + e.getMessage(), e);
            return 1;
        }
        return 0;
    }

}
