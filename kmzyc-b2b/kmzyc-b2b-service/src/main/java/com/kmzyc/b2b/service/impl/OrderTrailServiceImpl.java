package com.kmzyc.b2b.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.dao.member.MyOrderDao;
import com.kmzyc.b2b.model.OrderItem;
import com.kmzyc.b2b.model.OrderMain;
import com.kmzyc.b2b.model.OrderOperateStatement;
import com.kmzyc.b2b.model.ProductImage;
import com.kmzyc.b2b.service.OrderTrailService;
import com.kmzyc.express.entities.ExpressSubscription;
import com.kmzyc.express.entities.ExpressTrack;

@Service("orderTrailService")
public class OrderTrailServiceImpl implements OrderTrailService {

    @Resource(name = "myOrderDaoImpl")
    private MyOrderDao myOrderDao;

    @Override
    public OrderMain findOrderMainByOrderNo(Integer orderNo) throws SQLException {
        // 使用订单数据源

        OrderMain orderMain =
                (OrderMain) myOrderDao.findById("OrderMain.findByOrderMainId", orderNo);
        List<OrderItem> orderItemList = orderMain.getOrderItemList();
        // 使用产品数据源

        for (OrderItem orderItem : orderItemList) {
            // 获取订单里商品的默认图片信息（主图）
            ProductImage defaultProductImage = myOrderDao
                    .findDefaultProductImageBySkuCode("ProductImage.findDefaultImageBySkuCode",
                            orderItem.getCommoditySku());
            orderItem.setDefaultProductImage(defaultProductImage);
        }
        return orderMain;
    }

    @Override
    public Pagination findOrderMainByPage(Pagination page) throws SQLException {
        // 使用订单数据源

        page = myOrderDao.findByPage("OrderMain.findByPage", "OrderMain.countByPage", page);
        // 使用产品数据源

        List<OrderMain> orderMainList = page.getRecordList();
        for (OrderMain anOrderMainList : orderMainList) {
            List<OrderItem> orderItemList = anOrderMainList.getOrderItemList();
            for (OrderItem orderItem : orderItemList) {
                // 获取订单里商品的默认图片信息（主图）
                ProductImage defaultProductImage = myOrderDao
                        .findDefaultProductImageBySkuCode("ProductImage.findDefaultImageBySkuCode",
                                orderItem.getCommoditySku());
                orderItem.setDefaultProductImage(defaultProductImage);
            }
        }
        return page;
    }

    @Override
    public void mergeOrderAndExpress(List<OrderOperateStatement> list,
            ExpressSubscription expressSub) {
        List<ExpressTrack> trackList = expressSub.getExpressTrackList();
        if (trackList == null) {
            trackList = new ArrayList<>();
        }
        if (list != null && list.size() > 0) {
            ExpressTrack tempTrack;
            for (OrderOperateStatement orderOperateStatement : list) {
                tempTrack = new ExpressTrack();
                tempTrack.setTrackDate(orderOperateStatement.getNowOperateDate());
                tempTrack.setTrackMsg(orderOperateStatement.getOperateInfo());
                tempTrack.setOper(orderOperateStatement.getNowOperator());
                trackList.add(tempTrack);
            }
        }
        trackList.sort(Comparator.comparing(ExpressTrack::getTrackDate));
    }
}
