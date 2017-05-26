package com.kmzyc.supplier.common;

import com.pltfm.app.fobject.AttributeValue;
import com.pltfm.app.vobject.ProductSkuDraft;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

/**
 * 功能：产品草稿sku规格列表排序，
 * <p>查询结果的目录中的sku属性需事先按照category_attr_id排序</p>
 *
 * @author Zhoujiwei
 * @since 2015/11/2 11:22
 */
public class ProductSkuDraftComparator implements Comparator<ProductSkuDraft>, Serializable {

    private static final long serialVersionUID = -1776747606091441600L;

    public int compare(ProductSkuDraft o1, ProductSkuDraft o2) {
        if (o1 == null && o2 == null) {
            return 0;
        } else if (o2 == null) {
            return 1;
        } else if (o1 == null) {
            return -1;
        }

        //目录的sku属性集合事先按照category_attr_id排序
        List<AttributeValue> valueList1 = o1.getAttributeValues();
        List<AttributeValue> valueList2 = o2.getAttributeValues();
        boolean flag1 = CollectionUtils.isEmpty(valueList1);
        boolean flag2 = CollectionUtils.isEmpty(valueList2);
        if (flag1 && flag2) {
            return 0;
        }else if (flag2) {
            return 1;
        } else if(flag1) {
            return -1;
        }

        int length = valueList1.size();
        if (length != valueList2.size()) {
            return 0;
        }

        for (int i = 0; i < length; i++) {
            AttributeValue attributeValue1 = valueList1.get(i);
            AttributeValue attributeValue2 = valueList2.get(i);
            //当sku值相同，循环下一次
            if (attributeValue1.getValue().equals(attributeValue2.getValue())) {
                continue;
            }

            String order1 = StringUtils.EMPTY;
            String order2 = StringUtils.EMPTY;
            //若是新增的sku规格，categoryAttrValueId为下标，从1开始计数，再其前加字母A,用来排序
            if(Boolean.parseBoolean(attributeValue1.getIsNewAttr())) {
                order1 = "A";
            }
            if(Boolean.parseBoolean(attributeValue2.getIsNewAttr())) {
                order2 = "A";
            }
            if (attributeValue1.getCategoryAttrValueId() != null) {
                order1 += attributeValue1.getCategoryAttrValueId().toString();
            }
            if (attributeValue2.getCategoryAttrValueId() != null) {
                order2 += attributeValue2.getCategoryAttrValueId().toString();
            }
            int index = order1.compareTo(order2);

            //如果相同，则比较下一个目录sku属性，不同则返回
            if (index != 0) {
                return index;
            }

        }

        return 0;
    }

}