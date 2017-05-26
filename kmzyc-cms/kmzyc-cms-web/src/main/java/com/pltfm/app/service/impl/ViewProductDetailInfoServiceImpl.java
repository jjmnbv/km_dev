package com.pltfm.app.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pltfm.app.dao.ViewProductDetailInfoDAO;
import com.pltfm.app.service.ViewProductDetailInfoService;
import com.pltfm.app.vobject.CategoryAttrValue;
import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ViewProductDetailInfo;
import com.pltfm.app.vobject.ViewProductDetailInfoExample;
import com.pltfm.app.vobject.ViewSkuAttr;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

/**
 * 产品详情业务逻辑实现类
 *
 * @author cjm
 * @since 2013-9-23
 */
@Component(value = "viewProductDetailInfoService")
public class ViewProductDetailInfoServiceImpl implements
        ViewProductDetailInfoService {
	private static Logger logger = LoggerFactory.getLogger(ViewProductDetailInfoServiceImpl.class);

    /**
     * 产品详情DAO接口
     */
    @Resource(name = "viewProductDetailInfoDAO")
    private ViewProductDetailInfoDAO viewProductDetailInfoDAO;

    /**
     * 根据产品主键查询产品详情信息
     *
     * @param productId 产品详情条件类
     * @return 返回值
     * @throws Exception 异常
     */
    @Override
    public List<ViewProductDetailInfo> selectByproductId(Integer productId)
            throws Exception {
        ViewProductDetailInfoExample example = new ViewProductDetailInfoExample();
        example.createCriteria().andProductIdEqualTo(productId);
        return viewProductDetailInfoDAO.selectByExample(example);
    }

    /**
     * 通过skuId查询产品详细信息
     *
     * @param skuId sku主键信息
     * @throws Exception 异常信息
     */
    public List<ViewProductDetailInfo> selectBySkuId(Integer skuId)
            throws Exception {
        ViewProductDetailInfoExample example = new ViewProductDetailInfoExample();
        example.createCriteria().andProductSkuIdEqualTo(skuId);
        return viewProductDetailInfoDAO.selectByExample(example);
    }

    /**
     * 通过产品主键查询产品个性信息
     *
     * @param productId 产品主键
     * @return 属性值集合信息
     * @throws Exception 异常
     */
    public List<ViewSkuAttr> findAttrAndValueByProductId(Long productId)
            throws Exception {
        List<ViewSkuAttr> viewAttrList = new ArrayList<ViewSkuAttr>();
        try {
            List<ProductAttr> attrList = viewProductDetailInfoDAO
                    .queryProductAttrByProductId(productId);


            for (ProductAttr t : attrList) {
                ViewSkuAttr viewSkuAttr = new ViewSkuAttr();

                //自定义属性

                if (t.getProductAttrType().equals((short) 2)) {
                    viewSkuAttr.setCategoryAttrId(t.getProductAttrType().longValue());
                    viewSkuAttr.setCategoryAttrName(t.getProductAttrName());
                    String s = null;
                    if (t.getProductAttrValue() != null) {
                        s = t.getProductAttrValue();
                        s = s.replaceAll("(\r\n|\r|\n|\n\r)", "<br/>");
                    }
                    viewSkuAttr.setCategoryAttrValue(s);
                    viewAttrList.add(viewSkuAttr);
                }

                //处方药一些基本属性 41    代表的是药方属性  42    代表的是药方创建人属性   43    代表的是药方创建人图片属性
                //44 代表的是药方创建人介绍属性    45   代表的是使用说明属性   46   代表的是售后服务属性
                //	product_attr_type=4
                if (t.getProductAttrType().equals((short) 4)) {
                    viewSkuAttr.setCategoryAttrId(t.getProductAttrType().longValue());
                    viewSkuAttr.setCategoryAttrName(t.getChildrenAttrType() + "");
                    //	viewSkuAttr.set
                    String s = null;
                    if (t.getProductAttrValue() != null) {
                        s = t.getProductAttrValue();
                        s = s.replaceAll("(\r\n|\r|\n|\n\r)", "<br/>");
                    }
                    viewSkuAttr.setCategoryAttrValue(s);
                    viewAttrList.add(viewSkuAttr);
                }


                //	4.按方抓药属性[children_type为支持大类属性的子类具体属性类型,扩展]
                //1:类目属性
                if (t.getProductAttrType().equals((short) 1)) {
                    //是否SKU属性（0:否,1:是）
                    if (t.getIsSku().equals((short) 1)) {
                        continue;
                    }
                    //输入类型（0:文本框,1:单选框,2:多选框,3:下拉框 4:文本域 5.上传） [4和5  是 支持 自定义属性下的药方属性输入类型 ]
                    if (t.getInputType().equals((short) 0)) {
                        viewSkuAttr.setCategoryAttrId(t.getProductAttrType().longValue());
                        viewSkuAttr.setCategoryAttrName(t.getProductAttrName());
                        viewSkuAttr.setCategoryAttrValue(t
                                .getProductAttrValue());
                        viewAttrList.add(viewSkuAttr);
                    } else {
                        // 执行的SQL为：select * from CATEGORY_ATTR_VALUE t where
                        // t.CATEGORY_ATTR_VALUE_ID in (#productAttrValue#)
                        if (null != t.getProductAttrValue()) {
                            List<CategoryAttrValue> valueList = viewProductDetailInfoDAO
                                    .findByValueId(t.getProductAttrValue());
                            String value = "";
                            if (valueList != null && valueList.size() > 0) {
                                StringBuffer sb = new StringBuffer();
                                for (CategoryAttrValue v : valueList) {
                                    sb.append(v.getCategoryAttrValue()).append(",");
                                }
                                value = sb.substring(0, sb.length() - 1);
                            }
                            viewSkuAttr.setCategoryAttrId(t.getProductAttrType().longValue());
                            viewSkuAttr.setCategoryAttrName(t.getProductAttrName());
                            viewSkuAttr.setCategoryAttrValue(value);
                            viewAttrList.add(viewSkuAttr);
                        }

                    }

                }
//				3:运营属性 
            /*	if (t.getProductAttrType().equals((short) 3)) {
					   viewSkuAttr.setCategoryAttrName(t.getProductAttrName());
					   viewSkuAttr.setCategoryAttrValue(t.getProductAttrValue());
					  
				}*/

            }
        } catch (Exception e) {
            logger.error("ViewProductDetailInfoServiceImpl.findAttrAndValueByProductId异常：" + e.getMessage(), e);
        }
        return viewAttrList;
    }

    /**
     * 通过产品主键查询产品药品信息
     *
     * @param productId 产品主键
     * @return 属性值集合信息
     * @throws Exception 异常
     */
    public List<ViewSkuAttr> findProductAttr(Long productId) throws Exception {
        List<ViewSkuAttr> viewAttrList = new ArrayList<ViewSkuAttr>();
        try {


            List<ProductAttr> attrList = viewProductDetailInfoDAO
                    .queryProductAttrByProductId(productId);


            for (ProductAttr t : attrList) {
                ViewSkuAttr viewSkuAttr = new ViewSkuAttr();
                if (t.getProductAttrType().equals((short) 2)
                        ) {
                    viewSkuAttr.setCategoryAttrId(t.getProductAttrType().longValue());
                    viewSkuAttr.setCategoryAttrName(t.getProductAttrName());
                    String s = null;
                    if (t.getProductAttrValue() != null) {
                        s = t.getProductAttrValue();
                        s = s.replaceAll("(\r\n|\r|\n|\n\r)", "<br/>");
                    }
                    viewSkuAttr.setCategoryAttrValue(s);
                    viewAttrList.add(viewSkuAttr);
                }


                //处方药一些基本属性 41    代表的是药方属性  42    代表的是药方创建人属性   43    代表的是药方创建人图片属性
                //44 代表的是药方创建人介绍属性    45   代表的是使用说明属性   46   代表的是售后服务属性
                //	product_attr_type=4
                if (t.getProductAttrType().equals((short) 4)) {
                    viewSkuAttr.setCategoryAttrId(t.getProductAttrType().longValue());
                    viewSkuAttr.setCategoryAttrName(t.getChildrenAttrType() + "");
                    //	viewSkuAttr.set
                    String s = null;
                    if (t.getProductAttrValue() != null) {
                        s = t.getProductAttrValue();
                        s = s.replaceAll("(\r\n|\r|\n|\n\r)", "<br/>");
                    }
                    viewSkuAttr.setCategoryAttrValue(s);
                    viewAttrList.add(viewSkuAttr);
                }

                if (t.getProductAttrType().equals((short) 1)) {
                    if (t.getIsSku().equals((short) 1)) {
                        continue;
                    }
                    if (t.getInputType().equals((short) 0)) {
                        viewSkuAttr.setCategoryAttrId(t.getProductAttrType().longValue());
                        viewSkuAttr.setCategoryAttrName(t.getProductAttrName());
                        viewSkuAttr.setCategoryAttrValue(t
                                .getProductAttrValue());
                    } else {
                        // 执行的SQL为：select * from CATEGORY_ATTR_VALUE t where
                        // t.CATEGORY_ATTR_VALUE_ID in (#productAttrValue#)
                        List<CategoryAttrValue> valueList = viewProductDetailInfoDAO
                                .findByValueId(t.getProductAttrValue());
                        String value = "";
                        if (valueList != null && valueList.size() > 0) {
                            StringBuffer sb = new StringBuffer();
                            for (CategoryAttrValue v : valueList) {
                                sb.append(v.getCategoryAttrValue()).append(",");
                            }
                            value = sb.substring(0, sb.length() - 1);
                        }
                        viewSkuAttr.setCategoryAttrId(t.getProductAttrType().longValue());
                        viewSkuAttr.setCategoryAttrName(t.getProductAttrName());
                        viewSkuAttr.setCategoryAttrValue(value);
                    }
                } else if (!t.getProductAttrType().equals((short) 2)
                        || t.getSortno() == null) {
                    if (!t.getProductAttrType().equals((short) 3)) {
                        viewSkuAttr.setCategoryAttrId(t.getProductAttrType().longValue());
                        viewSkuAttr.setCategoryAttrName(t.getProductAttrName());
                        viewSkuAttr.setCategoryAttrValue(t.getProductAttrValue());
                    }
                } else {
                    continue;
                }
                viewAttrList.add(viewSkuAttr);


            }
        } catch (Exception e) {
            logger.error("ViewProductDetailInfoServiceImpl.findProductAttr异常：" + e.getMessage(), e);
        }
        return viewAttrList;
    }

    /**
     * （草稿）通过产品主键查询产品个性信息
     *
     * @param productId 产品主键
     * @return 属性值集合信息
     * @throws Exception 异常
     */
    public List<ViewSkuAttr> findAttrAndValueByProductIdDraft(Long productId)
            throws Exception {
        List<ViewSkuAttr> viewAttrList = new ArrayList<ViewSkuAttr>();
        try {


            List<ProductAttr> attrList = viewProductDetailInfoDAO
                    .queryProductAttrDraftByProductId(productId);


            for (ProductAttr t : attrList) {
                ViewSkuAttr viewSkuAttr = new ViewSkuAttr();

                //自定义属性

                if (t.getProductAttrType().equals((short) 2)) {
                    viewSkuAttr.setCategoryAttrId(t.getProductAttrType().longValue());
                    viewSkuAttr.setCategoryAttrName(t.getProductAttrName());
                    String s = null;
                    if (t.getProductAttrValue() != null) {
                        s = t.getProductAttrValue();
                        s = s.replaceAll("(\r\n|\r|\n|\n\r)", "<br/>");
                    }
                    viewSkuAttr.setCategoryAttrValue(s);
                    viewAttrList.add(viewSkuAttr);
                }

                //处方药一些基本属性 41    代表的是药方属性  42    代表的是药方创建人属性   43    代表的是药方创建人图片属性
                //44 代表的是药方创建人介绍属性    45   代表的是使用说明属性   46   代表的是售后服务属性
                //	product_attr_type=4
                if (t.getProductAttrType().equals((short) 4)) {
                    viewSkuAttr.setCategoryAttrId(t.getProductAttrType().longValue());
                    viewSkuAttr.setCategoryAttrName(t.getChildrenAttrType() + "");
                    //	viewSkuAttr.set
                    String s = null;
                    if (t.getProductAttrValue() != null) {
                        s = t.getProductAttrValue();
                        s = s.replaceAll("(\r\n|\r|\n|\n\r)", "<br/>");
                    }
                    viewSkuAttr.setCategoryAttrValue(s);
                    viewAttrList.add(viewSkuAttr);
                }


                //	4.按方抓药属性[children_type为支持大类属性的子类具体属性类型,扩展]
                //1:类目属性
                if (t.getProductAttrType().equals((short) 1)) {
                    //是否SKU属性（0:否,1:是）
                    if (t.getIsSku().equals((short) 1)) {
                        continue;
                    }
                    //输入类型（0:文本框,1:单选框,2:多选框,3:下拉框 4:文本域 5.上传） [4和5  是 支持 自定义属性下的药方属性输入类型 ]
                    if (t.getInputType().equals((short) 0)) {
                        viewSkuAttr.setCategoryAttrId(t.getProductAttrType().longValue());
                        viewSkuAttr.setCategoryAttrName(t.getProductAttrName());
                        viewSkuAttr.setCategoryAttrValue(t
                                .getProductAttrValue());
                        viewAttrList.add(viewSkuAttr);
                    } else {
                        // 执行的SQL为：select * from CATEGORY_ATTR_VALUE t where
                        // t.CATEGORY_ATTR_VALUE_ID in (#productAttrValue#)
                        if (null != t.getProductAttrValue()) {
                            List<CategoryAttrValue> valueList = viewProductDetailInfoDAO
                                    .findByValueId(t.getProductAttrValue());
                            String value = "";
                            if (valueList != null && valueList.size() > 0) {
                                StringBuffer sb = new StringBuffer();
                                for (CategoryAttrValue v : valueList) {
                                    sb.append(v.getCategoryAttrValue()).append(",");
                                }
                                value = sb.substring(0, sb.length() - 1);
                            }
                            viewSkuAttr.setCategoryAttrId(t.getProductAttrType().longValue());
                            viewSkuAttr.setCategoryAttrName(t.getProductAttrName());
                            viewSkuAttr.setCategoryAttrValue(value);
                            viewAttrList.add(viewSkuAttr);
                        }

                    }

                }


            }


        } catch (Exception e) {
            logger.error("ViewProductDetailInfoServiceImpl.findAttrAndValueByProductIdDraft异常：" + e.getMessage(), e);
        }
        return viewAttrList;
    }

    /**
     * 通过草稿产品主键查询产品药品信息
     *
     * @param productId 产品主键
     * @return 属性值集合信息
     * @throws Exception 异常
     */
    public List<ViewSkuAttr> findProductAttrDraft(Long productId)
            throws Exception {
        List<ViewSkuAttr> viewAttrList = new ArrayList<ViewSkuAttr>();
        try {
            List<ProductAttr> attrList = viewProductDetailInfoDAO
                    .queryProductAttrDraftByProductId(productId);
            for (ProductAttr t : attrList) {
                ViewSkuAttr viewSkuAttr = new ViewSkuAttr();
                if (t.getProductAttrType().equals((short) 2)
                        ) {
                    viewSkuAttr.setCategoryAttrId(t.getProductAttrType().longValue());
                    viewSkuAttr.setCategoryAttrName(t.getProductAttrName());
                    String s = null;
                    if (t.getProductAttrValue() != null) {
                        s = t.getProductAttrValue();
                        s = s.replaceAll("(\r\n|\r|\n|\n\r)", "<br/>");
                    }
                    viewSkuAttr.setCategoryAttrValue(s);
                    //	viewAttrList.add(viewSkuAttr);

                }

                //处方药一些基本属性 41    代表的是药方属性  42    代表的是药方创建人属性   43    代表的是药方创建人图片属性
                //44 代表的是药方创建人介绍属性    45   代表的是使用说明属性   46   代表的是售后服务属性
                //	product_attr_type=4
                if (t.getProductAttrType().equals((short) 4)) {
                    viewSkuAttr.setCategoryAttrId(t.getProductAttrType().longValue());
                    viewSkuAttr.setCategoryAttrName(t.getChildrenAttrType() + "");
                    //	viewSkuAttr.set
                    String s = null;
                    if (t.getProductAttrValue() != null) {
                        s = t.getProductAttrValue();
                        s = s.replaceAll("(\r\n|\r|\n|\n\r)", "<br/>");
                    }
                    viewSkuAttr.setCategoryAttrValue(s);
                    //	viewAttrList.add(viewSkuAttr);
                }

                viewAttrList.add(viewSkuAttr);
            }
        } catch (Exception e) {
            logger.error("ViewProductDetailInfoServiceImpl.findProductAttrDraft异常：" + e.getMessage(), e);
        }
        return viewAttrList;
    }

    public ViewProductDetailInfoDAO getViewProductDetailInfoDAO() {
        return viewProductDetailInfoDAO;
    }

    public void setViewProductDetailInfoDAO(
            ViewProductDetailInfoDAO viewProductDetailInfoDAO) {
        this.viewProductDetailInfoDAO = viewProductDetailInfoDAO;
    }

}
