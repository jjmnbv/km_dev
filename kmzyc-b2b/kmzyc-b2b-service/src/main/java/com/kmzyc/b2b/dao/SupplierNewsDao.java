package com.kmzyc.b2b.dao;

import com.kmzyc.b2b.model.SupplierNews;
import com.kmzyc.b2b.vo.CommercialTenantBasicInfo;
import com.km.framework.persistence.Dao;

public interface SupplierNewsDao extends Dao {
  /**
   * 根据newsId获取资讯详情
   * 
   * @param newsId
   * @return
   * @throws Exception
   */
  SupplierNews queryByNewsId(String newsId) throws Exception;

  public CommercialTenantBasicInfo queryCommercialBySupplierId(Long supplierId) throws Exception;
}
