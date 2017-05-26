package com.kmzyc.b2b.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.FAQDao;
import com.kmzyc.b2b.service.FAQService;
import com.km.framework.page.Pagination;

@Service
public class FAQServiceImpl implements FAQService {

    @Resource(name = "FAQDaoImpl")
    private FAQDao faqDao;

    // private static Logger logger =
    // Logger.getLogger(MyCouponServiceImpl.class);

    @Override
    public Pagination findFAQListByPage(Pagination page) throws SQLException {
        // Map<String, Object> condition = (Map<String,
        // Object>)page.getObjCondition();
        // 使用产品数据源

        return faqDao.findByPage("Information.searchPage", "Information.searchCountPage", page);
    }
}
