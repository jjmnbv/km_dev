package com.kmzyc.b2b.service.impl;


import com.kmzyc.b2b.dao.ProductSkuQuantityDao;
import com.kmzyc.b2b.model.ProductSkuQuantity;
import com.kmzyc.b2b.service.ProductSkuQuantityService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("productSkuQuantityService")
public class ProductSkuQuantityServiceImpl implements ProductSkuQuantityService {
    @Resource
    private ProductSkuQuantityDao productSkuQuantityDao;

    @Override
    public void updateProductSkuQuantity(ProductSkuQuantity productSkuQuantity) {


        int count =
                productSkuQuantityDao.selectCountProductSkuQuantity(productSkuQuantity
                        .getProductSkuId());
        if (count == 0) {
            productSkuQuantityDao.insertProductSkuQuantity(productSkuQuantity);
        }
        productSkuQuantityDao.updateProductSkuQuantity(productSkuQuantity);
    }

    @Override
    public void addBrowseQuantity(Long skuId) throws Exception {

        productSkuQuantityDao.addBrowseQuantity(skuId);
    }

}
