package com.pltfm.app.threadHandler;

import com.pltfm.app.service.ProductSkuService;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.sys.util.AppApplicationContextUtil;
import org.apache.log4j.Logger;
import redis.clients.jedis.JedisCluster;

import java.util.List;

public class ProductPriceCache implements Runnable {

    Logger log = Logger.getLogger(this.getClass());

    private List<Long> skuIdList;

    private final static String SKU_PRICE = "skuPrice_";

    public ProductPriceCache(List<Long> list) {
        this.skuIdList = list;
    }

    @Override
    public void run() {
        try {
            ProductSkuService productSkuService = (ProductSkuService) AppApplicationContextUtil.getApplicationContext().getBean("productSkuService");
            JedisCluster jedisCluster = (JedisCluster) AppApplicationContextUtil.getApplicationContext().getBean("jedisCluster");
            List<ProductSku> skuList = productSkuService.findBySkuIds(skuIdList);
            String skuId = "";
            for (ProductSku sku : skuList) {
                skuId = SKU_PRICE + sku.getProductSkuId().toString();
                if (jedisCluster.exists(skuId)) {
                    jedisCluster.del(skuId);
                }
                jedisCluster.set(skuId, sku.getPrice().toString());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
