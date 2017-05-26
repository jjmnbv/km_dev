package com.kmzyc.b2b.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.OrderDictonaryDao;
import com.kmzyc.b2b.model.OrderDictionary;
import com.kmzyc.b2b.service.OrderDictonaryService;

@Service
public class OrderDictonaryServiceImpl implements OrderDictonaryService {
    @Resource(name = "orderDictonaryDaoImpl")
    private OrderDictonaryDao orderDictonaryDao;

    @Override
    public List<OrderDictionary> getPaymentModel() throws SQLException {

        return this.orderDictonaryDao.getPaymentModel();
    }

    @Override
    public List<OrderDictionary> getOrderDictionary(String dictionaryType) throws SQLException {

        return this.orderDictonaryDao.getorderdictonary(dictionaryType);
    }

    @Override
    public OrderDictionary getOrderDictionaryByOrderDict(OrderDictionary orderDict)
            throws SQLException {

        return this.orderDictonaryDao.getOrderDictionaryByOrderDict(orderDict);
    }
}
