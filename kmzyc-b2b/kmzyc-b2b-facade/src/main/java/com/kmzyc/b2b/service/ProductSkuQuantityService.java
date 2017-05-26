package com.kmzyc.b2b.service;

import com.kmzyc.b2b.model.ProductSkuQuantity;

public interface ProductSkuQuantityService {
  public void updateProductSkuQuantity(ProductSkuQuantity productSkuQuantity);

  public void addBrowseQuantity(Long skuId) throws Exception;
}
