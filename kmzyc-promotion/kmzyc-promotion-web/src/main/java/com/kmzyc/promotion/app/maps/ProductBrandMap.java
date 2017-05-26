package com.kmzyc.promotion.app.maps;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.promotion.app.service.ProdBrandService;

@Service("productBrandMap")
@SuppressWarnings("unused")
public class ProductBrandMap {
    // 日志记录
    private static final Logger logger = LoggerFactory.getLogger(ProductBrandMap.class);

    @Resource
    private ProdBrandService prodBrandService;

    private static Map<Long, String> map = new LinkedHashMap<Long, String>();

    public static Map<Long, String> getMap() {
        if (map == null) {
            map = new LinkedHashMap<Long, String>();
        }
        return map;
    }

    @PostConstruct
    private void setMap() {
        // try {
        // List<ProdBrand> prodBrandList = prodBrandService.findAllValidBrand();
        // if (prodBrandList == null || prodBrandList.isEmpty()) return;
        // for (ProdBrand brand : prodBrandList) {
        // map.put(brand.getBrandId(), brand.getBrandName());
        // }
        // } catch (Exception e) {
        // logger.error("调用prodBrandService.findAllValidBrand方法异常", e);
        // }
    }

    public static void setValue(Long key, String value) {
        ProductBrandMap.map.put(key, value);
    }

    public static void removeValue(Long key) {
        ProductBrandMap.map.remove(key);
    }

    public static String getValue(String key) {
        if (map == null) {
            getMap();
        }

        if (StringUtils.isBlank(key)) {

            return null;
        }

        return map.get(Long.valueOf(key));
    }
}
