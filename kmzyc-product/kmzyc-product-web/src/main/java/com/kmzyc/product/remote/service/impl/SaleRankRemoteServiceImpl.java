package com.kmzyc.product.remote.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.order.remote.OrderQryRemoteService;
import com.kmzyc.product.remote.service.SaleRankRemoteService;
import com.pltfm.app.dao.ProductSkuDAO;
import com.pltfm.app.dao.SectionsDetailDAO;
import com.pltfm.app.service.ProductMainTiedService;
import com.pltfm.app.service.ProductSkuQuantityService;
import com.pltfm.app.service.ProductSkuService;
import com.pltfm.app.vobject.ProductHotSellInfoCache;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductSkuQuantity;
import com.pltfm.app.vobject.ProductmainTied;
import com.pltfm.sys.util.DatetimeUtil;

@Component("saleRankRemoteService")
public class SaleRankRemoteServiceImpl implements SaleRankRemoteService {

    @Resource
    private ProductMainTiedService productMainTiedService;

    @Resource
    private ProductSkuService productSkuService;

    @Resource
    private ProductSkuQuantityService productSkuQuantityService;

    @Resource
    private ProductSkuDAO productSkuDao;

    @Resource
    private SectionsDetailDAO sectionsDetailDao;

    @Resource
    private OrderQryRemoteService orderQryRemoteService;

    @Override
    public List<Integer> getProuductCountByCategoryIdList(List<Long> categoryIdList)
            throws ServiceException {
        List<Integer> productList = new ArrayList<Integer>();
        // 根据目录查出所有目录下的skuId
        List<Long> skuIdList = productSkuService.selectSkuIdListByCategoryIdList(categoryIdList);
        // 如果目录中没有skuCode ，那么直接返回productList
        if (skuIdList.size() == 0) {
            return productList;
        }

        // 对应的skuID 上个月的销量
        Map<Long, ProductSkuQuantity> skuQuantityMap = productSkuQuantityService
                .getLastSaleSkuIdMap(skuIdList);
        // skuId 对应的set
        Set<Long> skuIdSet = skuQuantityMap.keySet();

        List<Long> skuIdList1 = new ArrayList<Long>(skuIdSet);

        try {
            // 得到skuID 对应skuCode的map
            Map<Long, ProductSku> skuIdAndCodeMap = productSkuService.getSkuIdAndCodeMap(skuIdList1);
            List<String> skuCodeList = productSkuDao.selectSkuCodeByManySkuId(skuIdList1);
            // 根据skuCode 获得相应的 产品信息map
            Map<String, ProductmainTied> tiedMap = productMainTiedService.getProductSkuMapBySkucode(skuCodeList);
            // 根据数量 对productMainTied对象进行重新排序
            // 对应数量以及productId
            Map<ProductmainTied, Integer> productIdMap = new HashMap<ProductmainTied, Integer>();

            for (int i = 0; i < skuIdList1.size(); i++) {
                Integer skuIdQuantity = skuQuantityMap.get(skuIdList1.get(i))
                        .getSaleQuantity().intValue();
                String skuCode = skuIdAndCodeMap.get(skuIdList1.get(i))
                        .getProductSkuCode();
                ProductmainTied productmainTied = tiedMap.get(skuCode);
                productIdMap.put(productmainTied, skuIdQuantity);
            }

            List<ProductmainTied> prodList = new ArrayList(productIdMap.keySet());
            // 对同一个product下的sku进行合并，之后得到的prodIdCountMap将是同一个productId 的销量，而不是sku的销量
            List<Integer> delIndexList = new ArrayList();
            Map<Integer, Integer> prodIdCountMap = new HashMap();
            for (int i = 0; i < prodList.size(); i++) {
                ProductmainTied s1 = prodList.get(i);
                boolean flag = false;
                for (Integer index : delIndexList) {
                    if (i == index)
                        flag = true;
                }
                if (flag) {
                    continue;
                }
                int quantity = productIdMap.get(s1);
                for (int j = 0; j < prodList.size(); j++) {
                    ProductmainTied s2 = prodList.get(j);
                    if (i == j)
                        continue;
                    if (s1.getProductId().intValue() == s2.getProductId().intValue()) {
                        delIndexList.add(j);
                        quantity = quantity + productIdMap.get(s2);
                    }
                }
                prodIdCountMap.put(s1.getProductId().intValue(), quantity);
            }

            // 根据销量对 prodIdCountMap 从多到少 进行排序
            ArrayList<Entry<Integer, Integer>> list = new ArrayList(prodIdCountMap.entrySet());
            Collections.sort(list, (arg0, arg1) -> arg1.getValue() - arg0.getValue());
            // 如果 超过10 个，则取销量前10的productId 返回
            if (list.size() > 9) {
                for (int i = 0; i < 10; i++) {
                    Entry<Integer, Integer> entry = list.get(i);
                    productList.add(entry.getKey());
                }
            } else {
                // 不超过10,则取得所有
                for (int i = 0; i < list.size(); i++) {
                    Entry<Integer, Integer> entry = list.get(i);
                    productList.add(entry.getKey());
                }
            }
            return productList;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Integer> getProductCountByAllProductSkuList() throws ServiceException {
        List<Integer> productList = new ArrayList<Integer>();
        List<String> skuCodeList = productSkuService.selectAllSkuCodeList();
        if (skuCodeList.size() == 0) {
            return productList;
        }

        // 获得上个月第一天开始的O:00 点
        Date lastMonthFirstDate = DatetimeUtil.getOneDayStart(DatetimeUtil.changeToDate(DatetimeUtil.getLastMonthFirstDay(), "yyyyMMdd"));
        // 获得上个月的最后一天的结束时间23:59点
        Date lastMonthLastDate = DatetimeUtil.getOneDayEnd(DatetimeUtil.changeToDate(DatetimeUtil.getLastMonthLastDay(), "yyyyMMdd"));

        try {
            Map<String, BigDecimal> skuCodeCount = orderQryRemoteService.countSKU(lastMonthFirstDate, lastMonthLastDate, skuCodeList);

            // 根据skuCode 获得相应的 产品信息map
            List<String> mapList = new ArrayList(skuCodeCount.keySet());
            Map<String, ProductmainTied> productMainTiedMap = productMainTiedService.getProductSkuMapBySkucode(mapList);
            // 根据数量 对productMainTied对象进行重新排序 对应数量以及productId
            Map<ProductmainTied, Integer> productMap = new HashMap();
            for (Map.Entry<String, BigDecimal> entry : skuCodeCount.entrySet()) {
                String skuCode = entry.getKey();
                Integer quantity = entry.getValue().intValue();
                ProductmainTied productmainTied = productMainTiedMap.get(skuCode);
                productMap.put(productmainTied, quantity);
            }
            List<ProductmainTied> prodList = new ArrayList<ProductmainTied>(productMap.keySet());
            List<Integer> delIndexList = new ArrayList<Integer>();
            Map<Integer, Integer> prodIdCountMap = new HashMap<Integer, Integer>();

            for (int i = 0; i < prodList.size(); i++) {
                ProductmainTied s1 = prodList.get(i);

                boolean flag = false;
                for (Integer index : delIndexList) {
                    if (i == index)
                        flag = true;
                }

                if (flag) {
                    continue;
                }
                int quantity = productMap.get(s1);
                for (int j = 0; j < prodList.size(); j++) {
                    ProductmainTied s2 = prodList.get(j);
                    if (i == j)
                        continue;
                    if (s1.getProductId().longValue() == s2.getProductId().longValue()) {
                        delIndexList.add(j);
                        quantity = quantity + productMap.get(s2);
                    }
                }
                prodIdCountMap.put(s1.getProductId().intValue(), quantity);
            }

            ArrayList<Entry<Integer, Integer>> list = new ArrayList<Entry<Integer, Integer>>(prodIdCountMap.entrySet());
            Collections.sort(list, (arg0, arg1) -> arg1.getValue() - arg0.getValue());
            if (list.size() > 9) {
                for (int i = 0; i < 10; i++) {
                    Entry<Integer, Integer> entry = list.get(i);
                    productList.add(entry.getKey());
                }
            } else {
                for (int i = 0; i < list.size(); i++) {
                    Entry<Integer, Integer> entry = list.get(i);
                    productList.add(entry.getKey());
                }
            }

            return productList;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Map<String, Object>> findSectionsHotSell(String saleType) throws ServiceException {
        if (StringUtils.isBlank(saleType)) {
            throw new ServiceException("请输入合法参数！");
        }
        saleType = new StringBuilder(saleType.toUpperCase()).append("-REXIAO").toString();

        try {
            List<ProductHotSellInfoCache> list = sectionsDetailDao.findHotSellProducts(saleType);
            if (CollectionUtils.isEmpty(list)) {
                return null;
            }

            List<Map<String, Object>> li = new ArrayList<>(list.size());
            Map<String, Object> obj = null;
            for (ProductHotSellInfoCache p : list) {
                obj = new HashMap();
                obj.put("productSkuId", p.getProductSkuId());
                obj.put("imgPath", p.getImgPath());
                obj.put("price", p.getPrice());
                obj.put("productName", p.getProductTitle());
                obj.put("productSkuCode", p.getProductSkuCode());
                li.add(obj);
            }

            return li;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}