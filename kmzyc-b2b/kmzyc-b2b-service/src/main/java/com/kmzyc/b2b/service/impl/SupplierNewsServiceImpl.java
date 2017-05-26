package com.kmzyc.b2b.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.SupplierNewsDao;
import com.kmzyc.b2b.model.SupplierNews;
import com.kmzyc.b2b.service.SupplierNewsService;
import com.kmzyc.b2b.vo.CommercialTenantBasicInfo;
import com.km.framework.page.Pagination;

@Service
public class SupplierNewsServiceImpl implements SupplierNewsService {
  // 资讯dao类引用
  @Resource(name = "supplierNewsDaoImpl")
  private SupplierNewsDao supplierNewsDao;

  // 根据supplierId分页获取资讯

  public Pagination pageSupplierNewsBySupplierId(Pagination page) throws Exception {
    return supplierNewsDao.findByPage("SUPPLIER_NEWS.queryPage_selectBySupplierId",
        "SUPPLIER_NEWS.queryCount_selectBySupplierId", page);
  }

  // 根据newsId获取资讯详情
  public SupplierNews queryByNewsId(String newsId) throws Exception {
    return supplierNewsDao.queryByNewsId(newsId);
  }

  @Override
  public CommercialTenantBasicInfo queryCommercialBySupplierId(Long supplierId) throws Exception {
    return supplierNewsDao.queryCommercialBySupplierId(supplierId);
  }
}
