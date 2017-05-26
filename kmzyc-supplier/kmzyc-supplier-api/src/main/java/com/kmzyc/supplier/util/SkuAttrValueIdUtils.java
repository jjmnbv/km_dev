package com.kmzyc.supplier.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 功能：历史遗留下的sku未按照attr_value_id排序导致顺序错乱的id修补工具类
 *
 * @author Zhoujiwei
 * @since 2015/11/4 10:33
 */
public class SkuAttrValueIdUtils {

    /**
     * 针对sku的product_attr_value排序
     *
     * @param source 源sku的checkbox勾选的value
     * @return
     */
    public static String[] sort(String[] source) {
        //String[] source = {"601","603","602","@22","@11#1","@11"};
        //String[] source = {"601","603","602","22","111","11"};
        if (source == null) {
            return source;
        }

        List<Long> needSortList = new ArrayList<Long>();
        List<String> remainList = new ArrayList<String>();
        List<String> result = new ArrayList<String>();
        for (int i=0;i<source.length;i++) {
            try {
                if (source[i].contains("@")) {
                    remainList.add(source[i]);
                } else {
                    long id = Long.parseLong(source[i]);
                    needSortList.add(id);
                }
            } catch (NumberFormatException e) {
                return source;
            }
        }

        if (!needSortList.isEmpty()) {
            Collections.sort(needSortList);
            for (Long id : needSortList) {
                result.add(id.toString());
            }
        }
        result.addAll(remainList);
        return result.toArray(new String[result.size()]);
    }
}
