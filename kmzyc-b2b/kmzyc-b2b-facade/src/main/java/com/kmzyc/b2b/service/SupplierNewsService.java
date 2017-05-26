package com.kmzyc.b2b.service;

import com.kmzyc.b2b.model.SupplierNews;
import com.kmzyc.b2b.vo.CommercialTenantBasicInfo;
import com.km.framework.page.Pagination;

public interface SupplierNewsService {

  /**
   * 根据supplierId分页查询资讯 企业动态
   * 
   * @param page
   * @return
   * @throws Exception
   */
  Pagination pageSupplierNewsBySupplierId(Pagination page) throws Exception;

  /**
   * 根据newsId获取资讯详情
   * 
   * @param newsId
   * @return
   * @throws Exception
   */
  SupplierNews queryByNewsId(String newsId) throws Exception;

  /**
   * 根据供应商信息查询店铺信息
   */
  public CommercialTenantBasicInfo queryCommercialBySupplierId(Long supplierId) throws Exception;
}
