package com.kmzyc.supplier.common;

import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductSkuAttr;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

/**
 * 功能：产品sku规格列表排序，
 * <p>查询结果的目录中的sku属性需事先按照category_attr_id排序</p>
 *
 * @author Zhoujiwei
 * @since 2015/11/2 11:22
 */
public class ProductSkuComparator implements Comparator<ProductSku>, Serializable {

    private static final long serialVersionUID = 7624052656184890747L;

    public int compare(ProductSku o1, ProductSku o2) {
        if (o1 == null && o2 == null) {
            return 0;
        } else if (o2 == null) {
            return 1;
        } else if (o1 == null) {
            return -1;
        }

        //目录的sku属性集合事先按照category_attr_id排序
        List<ProductSkuAttr> skuAttrList1 = o1.getProductSkuAttrList();
        List<ProductSkuAttr> skuAttrList2 = o2.getProductSkuAttrList();
        boolean flag1 = CollectionUtils.isEmpty(skuAttrList1);
        boolean flag2 = CollectionUtils.isEmpty(skuAttrList2);
        if (flag1 && flag2) {
            return 0;
        }else if (flag2) {
            return 1;
        } else if(flag1) {
            return -1;
        }

        int length = skuAttrList1.size();
        if (length != skuAttrList2.size()) {
            return 0;
        }

        for (int i = 0; i < length; i++) {
            ProductSkuAttr productSkuAttr1 = skuAttrList1.get(i);
            ProductSkuAttr productSkuAttr2 = skuAttrList2.get(i);
            //当sku值相同，循环下一次
            if (productSkuAttr1.getCategoryAttrValue().equals(productSkuAttr2.getCategoryAttrValue())) {
                continue;
            }

            String order1 = StringUtils.EMPTY;
            String order2 = StringUtils.EMPTY;
            //若是新增的sku规格，categoryAttrValueId为下标，从1开始计数，再其前加字母A,用来排序
            if(StringUtils.isNotEmpty(productSkuAttr1.getNewAttr())) {
                order1 = "A";
            }
            if(StringUtils.isNotEmpty(productSkuAttr2.getNewAttr())) {
                order2 = "A";
            }
            if (productSkuAttr1.getCategoryAttrValueId() != null) {
                order1 += productSkuAttr1.getCategoryAttrValueId().toString();
            }
            if (productSkuAttr2.getCategoryAttrValueId() != null) {
                order2 += productSkuAttr2.getCategoryAttrValueId().toString();
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