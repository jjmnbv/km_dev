package com.kmzyc.b2b.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.dao.member.MyOrderEmailDao;
import com.kmzyc.b2b.model.OrderItem;
import com.kmzyc.b2b.model.OrderMain;
import com.kmzyc.b2b.model.ProductImage;
import com.kmzyc.b2b.service.OrderTrailEmailService;
import com.kmzyc.b2b.service.member.MyOrderService;
import com.kmzyc.zkconfig.ConfigurationUtil;

@Service("orderTrailEmailService")
public class OrderTrailEmailServiceImpl implements OrderTrailEmailService {

    @Resource(name = "myOrderEmailDaoImpl")
    private MyOrderEmailDao myOrderEmailDao;

    @Resource(name = "myOrderServiceImpl")
    private MyOrderService myOrderService;

    // private static Logger logger = Logger.getLogger(OrderTrailEmailServiceImpl.class);
    private static Logger logger = LoggerFactory.getLogger(OrderTrailEmailServiceImpl.class);


    @Override
    public OrderMain findOrderMainByOrderNo(String orderNo, String mobole) throws SQLException {
        // 使用订单数据源

        Map<String, Object> newConditon = new HashMap<>();
        newConditon.put("orderCode", orderNo);// 订单号码
        newConditon.put("orderPurchaserMobile", mobole);// 手机号码
        OrderMain orderMain =
                myOrderEmailDao.findDilet("OrderMain.findOrderMainByPhone", newConditon);
        if (orderMain != null) {
            List<OrderItem> orderItemList = orderMain.getOrderItemList();

            // 使用产品数据源


            for (OrderItem orderItem : orderItemList) {
                // 获取订单里商品的默认图片信息（主图）
                ProductImage defaultProductImage = myOrderEmailDao
                        .findDefaultProductImageBySkuCode("ProductImage.findDefaultImageBySkuCode",
                                orderItem.getCommoditySku());
                if (defaultProductImage != null) {
                    orderItem.setDefaultProductImage(defaultProductImage);
                    orderItem.setProductSkuId(defaultProductImage.getSkuId());
                }
            }
        }
        return orderMain;
    }

    /**
     * 根据订单号和手机进行查询看是否有数据
     */
    @Override
    public Integer findOrderMainByOrderNoOrMobile(String orderNo, String mobole)
            throws SQLException {
        // 使用订单数据源

        Map<String, Object> newConditon = new HashMap<>();
        newConditon.put("orderCode", orderNo);
        newConditon.put("orderPurchaserMobile", mobole);
        String channel = ConfigurationUtil.getString("CHANNEL");
        newConditon.put("channel", channel);
        Integer orderCount;
        try {
            orderCount =
                    myOrderEmailDao.findemailorMod("OrderMain.findByorderNoOrMobile", newConditon);
        } catch (SQLException e) {
            logger.error("根据订单号和手机号查询出现异常：" + e.getMessage(), e);
            throw e;
        }
        return orderCount;

    }

    // 成果网
    @Override
    public Pagination findOrderMainByPageCgw(Pagination page) throws SQLException {
        // 使用订单数据源
        int pageIndex = page.getNumperpage();
        if (pageIndex == 0) pageIndex = 1;
        page.setStartindex((pageIndex - 1) * 200 + 1);
        page.setEndindex(200 * pageIndex);

        page = myOrderEmailDao.findByPage("OrderMain.findOrderByPageCgw",
                "OrderMain.countOrderTrailByPageCgw", page);
        return page;
    }

    // 领客
    @Override
    public Pagination findOrderMainByPageLK(Pagination page) throws SQLException {
        // 使用订单数据源

        page = myOrderEmailDao.findByPage("OrderMain.findOrderByPageLK",
                "OrderMain.countOrderTrailByPageLk", page);
        return page;
    }

    @Override
    public Pagination findOrderMainByPage(Pagination page) throws SQLException {
        // 使用订单数据源

        page = myOrderEmailDao.findByPage("OrderMain.findOrderTrailByPage",
                "OrderMain.countOrderTrailByPage", page);
        List<OrderMain> data = page.getRecordList();
        List<OrderMain> newData = new LinkedList<>();
        Map<String, OrderMain> rootMap = new HashMap<>();// 根订单集
        for (OrderMain om : data) {
            OrderMain root;// 根订单
            if (null != om.getRootOrderCode()) {// 根订单存在
                root = rootMap.get(om.getRootOrderCode());
                if (null == root) {// 根订单不在map中
                    root = myOrderService.findOrderByOrderCode(om.getRootOrderCode());
                    newData.add(root);
                    rootMap.put(root.getOrderCode(), root);
                }
                newData.add(newData.indexOf(root) + 1, om);// 添加在根订单后面
            } else {
                newData.add(om);// 添加在最后面
            }
        }
        page.setRecordList(newData);
        // 使用产品数据源

        List<OrderMain> orderMainList = page.getRecordList();
        for (OrderMain anOrderMainList : orderMainList) {
            List<OrderItem> orderItemList = anOrderMainList.getOrderItemList();
            for (OrderItem orderItem : orderItemList) {
                // 获取订单里商品的默认图片信息（主图）
                ProductImage defaultProductImage = myOrderEmailDao
                        .findDefaultProductImageBySkuCode("ProductImage.findDefaultImageBySkuCode",
                                orderItem.getCommoditySku());
                orderItem.setDefaultProductImage(defaultProductImage);
            }
        }
        return page;
    }

    /**
     * 根据邮箱和手机号查询
     */
    @Override
    public Integer findByorderEmailorMobile(String email, String mobile) throws SQLException {
        // 使用订单数据源

        Map<String, Object> newConditon = new HashMap<>();
        newConditon.put("email", email);
        newConditon.put("customerMobile", mobile);
        String channel = ConfigurationUtil.getString("CHANNEL");
        newConditon.put("channel", channel);
        Integer orderCount;
        try {
            orderCount = myOrderEmailDao.findemailorMod("OrderMain.findByorderEmailorMobile",
                    newConditon);
        } catch (SQLException e) {
            logger.error("根据订单号和邮箱查询出现异常：" + e.getMessage(), e);
            throw e;
        }
        return orderCount;
    }

    @Override
    public Pagination findOrderMainByPageDuoMai(Pagination page) throws SQLException {
        // 使用订单数据源

        page = myOrderEmailDao.findByPage("OrderMain.findOrderByPageDM",
                "OrderMain.countOrderTrailByPageLk", page);
        return page;
    }
}
