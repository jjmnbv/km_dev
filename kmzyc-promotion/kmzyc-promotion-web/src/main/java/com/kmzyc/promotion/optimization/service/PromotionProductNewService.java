package com.kmzyc.promotion.optimization.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.km.framework.page.Pagination;
import com.kmzyc.promotion.app.vobject.ProductAndSku;
import com.kmzyc.promotion.app.vobject.PromotionInfo;
import com.kmzyc.promotion.optimization.model.RestrictionProduct;

public interface PromotionProductNewService {


    Map<String, Double> queryPromotionProductSkuIdMapFromDb(PromotionInfo info, Pagination page);

    List<ProductAndSku> queryProductAndSkuForBatch(Collection<?> set, Long promotionId);

    RestrictionProduct queryRestrictionProduct(Long skuId, Long promotionId);

}
