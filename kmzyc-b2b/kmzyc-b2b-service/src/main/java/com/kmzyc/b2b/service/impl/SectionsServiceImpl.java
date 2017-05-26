package com.kmzyc.b2b.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.SectionsDao;
import com.kmzyc.b2b.model.ProductSku;
import com.kmzyc.b2b.service.SectionsService;

@Service
public class SectionsServiceImpl implements SectionsService {
    @Resource(name = "sectionsDaoImpl")
    private SectionsDao sectionsDao;

    /**
     * 根据栏目标识查询栏目下的商品信息
     * 
     * @param identification
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public List<ProductSku> queryProductSkuBySections(String identification) throws SQLException {

        return (List<ProductSku>) sectionsDao.findByProperty(
                "ProductSku.queryProductSkuBySections", identification);
    }
}
