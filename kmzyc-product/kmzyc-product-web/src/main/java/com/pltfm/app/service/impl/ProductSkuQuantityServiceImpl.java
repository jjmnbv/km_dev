package com.pltfm.app.service.impl;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.ProductSkuQuantityDAO;
import com.pltfm.app.service.ProductSkuQuantityService;
import com.pltfm.app.vobject.ProductSkuQuantity;
import com.pltfm.app.vobject.ProductSkuQuantityExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component("productSkuQuantityService")
public class ProductSkuQuantityServiceImpl implements ProductSkuQuantityService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private ProductSkuQuantityDAO productSkuQuantityDao;

    @Resource
    private JedisCluster jedisCluster;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateProductSkuQuanlityEveryMonth(Date date) throws ServiceException {
        List<ProductSkuQuantity> addSkuQunatityList = new ArrayList<ProductSkuQuantity>();
        List<ProductSkuQuantity> updateSkuQunatityList = new ArrayList<ProductSkuQuantity>();
        ProductSkuQuantity addSkuQunatity = null;
        ProductSkuQuantity updateSkuQunatity = null;
        ProductSkuQuantityExample example = new ProductSkuQuantityExample();
        example.setOrderByClause("product_sku_quantity_id desc");
        try {
            // 查询出product_sku_quantity 表中总共数据出来
            List<ProductSkuQuantity> quantityListAll;
            quantityListAll = productSkuQuantityDao.selectByExampleList(example);

            List<String> list = new ArrayList();
            for (int i = 0; i < quantityListAll.size(); i++) {
                list.add(quantityListAll.get(i).getProductSkuId().toString());
            }
            // 获取缓存中所有skucode数据 product_sku_id:销量
            Map<String, String> all = jedisCluster.hgetAll("skuId_count");
            for (Map.Entry<String, String> entry : all.entrySet()) {
                if (list.contains(entry.getKey())) {
                    updateSkuQunatity = new ProductSkuQuantity();
                    updateSkuQunatity.setProductSkuId(Long.parseLong(entry.getKey()));
                    if (null == entry.getValue()) {
                        updateSkuQunatity.setSaleQuantity(BigDecimal.ZERO);
                    } else {
                        updateSkuQunatity.setSaleQuantity(new BigDecimal(entry.getValue()));
                    }
                    updateSkuQunatityList.add(updateSkuQunatity);
                } else {
                    addSkuQunatity = new ProductSkuQuantity();
                    addSkuQunatity.setProductSkuId(Long.parseLong(entry.getKey()));
                    if (null == entry.getValue()) {
                        addSkuQunatity.setSaleQuantity(BigDecimal.ZERO);
                    } else {
                        addSkuQunatity.setSaleQuantity(new BigDecimal(entry.getValue()));
                    }
                    addSkuQunatityList.add(addSkuQunatity);
                }
            }
            productSkuQuantityDao.batchUpdate(updateSkuQunatityList);
            productSkuQuantityDao.batchInsert(addSkuQunatityList);
            logger.info(date + "：更新PRODUCT_SKU_QUANTITY表信息成功");
        } catch (SQLException e) {
            logger.error(date + "：更新PRODUCT_SKU_QUANTITY表信息失败", e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<Long, ProductSkuQuantity> getLastSaleSkuIdMap(List<Long> skuIdList) throws ServiceException {
        try {
            return productSkuQuantityDao.getLastSaleSkuIdMap(skuIdList);
        } catch (Exception e) {
            throw new ServiceException(e);
        }

    }

}