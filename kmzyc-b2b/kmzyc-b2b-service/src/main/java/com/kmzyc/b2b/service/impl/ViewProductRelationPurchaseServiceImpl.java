package com.kmzyc.b2b.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.kmzyc.b2b.dao.ProductRelationDao;
import com.kmzyc.b2b.model.ProductsOrderBySale;
import com.kmzyc.b2b.model.ViewProductRelationPurchase;
import com.kmzyc.b2b.service.ProductPriceService;
import com.kmzyc.b2b.service.ViewProductRelationPurchaseService;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;

import redis.clients.jedis.JedisCluster;

@Service("viewProductRelationPurchaseService")
public class ViewProductRelationPurchaseServiceImpl implements ViewProductRelationPurchaseService {

    // private static final String CHANNEL = ConfigurationUtil.getString("CHANNEL");
    // private static Logger logger =
    // Logger.getLogger(ViewProductRelationPurchaseServiceImpl.class);
    private static Logger logger =
            LoggerFactory.getLogger(ViewProductRelationPurchaseServiceImpl.class);
    @Resource
    private ProductRelationDao productRelationDao;
    @Resource
    ProductPriceService productPriceService;
    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;

    /**
     * 查询关联商品，不足数据条数时当前商品同品类补足
     * 
     * @return
     * @throws SQLException
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<ViewProductRelationPurchase> findRelationProduct(Map<String, Object> params)
            throws SQLException {
        List<ViewProductRelationPurchase> list;
        String skuid = (String) params.get("skuId");
        String relationType = String.valueOf(params.get("relationType"));



        if (null != skuid) {

            try {
                String key =
                        "B2B_findRelationProduct_".concat(relationType).concat("_").concat(skuid);
                String str;
                if (!StringUtil.isEmpty(str = jedisCluster.get(key))) {
                    return JSONArray.parseArray(str, ViewProductRelationPurchase.class);
                }
            } catch (Exception e) {
                logger.error("findRelationProduct", e);
            }
        }

        Integer eIndex = (Integer) params.get("endindex");
        if (null == eIndex) {
            eIndex = 5;
        }
        list = productRelationDao.findRelationProduct(params);
        if (null == list) {
            list = new ArrayList();
        }
        int endindex = eIndex - list.size();
        if (endindex > 0) {
            List queryedSkuIds = new ArrayList();
            List skuids = (List) params.get("skuIds");
            if (null != skuids && !skuids.isEmpty()) {
                queryedSkuIds.addAll(skuids);
            } else {
                queryedSkuIds.add(skuid);
            }
            if (!list.isEmpty()) {
                for (ViewProductRelationPurchase vrp : list) {
                    if (null != vrp && 0 != vrp.getRelationSkuId()) {
                        queryedSkuIds.add(vrp.getRelationSkuId());
                    }
                }
            }
            if (1 == queryedSkuIds.size()) {
                params.put("queryedSkuId", queryedSkuIds.get(0));
            } else {
                params.put("queryedSkuIds", queryedSkuIds);
            }
            params.put("endindex", endindex);
            params.put("channel", ConfigurationUtil.getString("CHANNEL"));
            List iList = productRelationDao.findTopProduct(params);
            if (null != iList && !iList.isEmpty()) {
                list.addAll(iList);
            }
        }
        if (null != skuid && !list.isEmpty()) {

            try {

                String key =
                        "B2B_findRelationProduct_".concat(relationType).concat("_").concat(skuid);

                jedisCluster.set(key, JSONArray.toJSONString(list));
                jedisCluster.expire(key, 60 * 60 * 24 * 30); // 缓存一个月

            } catch (Exception e) {
                logger.error("findRelationProduct", e);
            }
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ProductsOrderBySale> findProductOrderBySalequantity(Map<String, Object> params)
            throws SQLException {
        List<ProductsOrderBySale> list;
        String skuid = (String) params.get("skuId");


        // 根据skuid获取二级物理类目
        String parentCatagoryid = productRelationDao.queryCategoryParentId(skuid);


        if (null != parentCatagoryid && !"".equals(parentCatagoryid)) {

            try {

                String key = "B2B_findProductOrderBySalequantity_".concat(parentCatagoryid);
                String str;
                if (!StringUtil.isEmpty(str = jedisCluster.get(key))) {
                    return JSONArray.parseArray(str, ProductsOrderBySale.class);
                }
            } catch (Exception e) {
                logger.error("", e);
            }
        } else {
            logger.info("商品按销量排序：skuid " + skuid + " 获得的物理二级目录为空");
            return null;
        }


        params.put("parentCatagoryid", parentCatagoryid);// 二级物理目录编号
        list = productRelationDao.findProductOrderBySalequantity(params);
        if (null != skuid && null != list && !list.isEmpty()) {

            try {

                String key = "B2B_findProductOrderBySalequantity_".concat(parentCatagoryid);

                jedisCluster.set(key, JSONArray.toJSONString(list));
                jedisCluster.expire(key, 60 * 60 * 24 * 30); // 缓存一个月

            } catch (Exception e) {
                logger.error("商品按销量排序：skuid " + skuid + " 获得的物理二级目录异常", e);
            }
        }
        return list;
    }
}
