package com.kmzyc.promotion.app.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.promotion.app.dao.ProductSkuAttrDAO;
import com.kmzyc.promotion.app.service.ProductSkuAttrService;
import com.kmzyc.promotion.app.vobject.ProductSkuAttr;
import com.kmzyc.promotion.app.vobject.ProductSkuAttrExample;

/**
 * 产品SKU属性业务逻辑类
 * 
 * @author humy
 * @since 2013-7-9
 */
@Service("productSkuAttrService")
@SuppressWarnings("unchecked")
public class ProductSkuAttrServiceImpl implements ProductSkuAttrService {
  // 日志记录
  private static final Logger logger = LoggerFactory.getLogger(ProductSkuAttrServiceImpl.class);
  /**
   * 产品SKU属性数据库接口
   */
  @Resource(name = "productSkuAttrDAO")
  private ProductSkuAttrDAO productSkuAttrDAO;

  /**
   * 添加产品SKU属性
   * 
   * @param ProductSkuAttr 产品SKU属性基本信息
   * @return
   * @throws Exception
   */
  @Override
  public void addProductSkuAttr(ProductSkuAttr productSkuAttr) throws Exception {
    productSkuAttrDAO.insert(productSkuAttr);
  }

  /**
   * 查询SKU属性
   * 
   * @param productSkuAttr
   * @return List<ProductSkuAttr>
   * @throws Exception
   */
  public List<ProductSkuAttr> queryProductSkuAttrList(ProductSkuAttr productSkuAttr)
      throws Exception {
    ProductSkuAttrExample example = new ProductSkuAttrExample(); // 组装where查询条件的对象
    example.createCriteria().andProductSkuIdEqualTo(productSkuAttr.getProductSkuId());
    example.setOrderByClause(" CATEGORY_ATTR_ID ");
    return productSkuAttrDAO.selectByExample(example);
  }

  /**
   * 删除产品SKU属性
   * 
   * @param productSkuId 产品SKU Id
   * @return
   * @throws Exception
   */
  public void deleteProductSkuAttr(Long productSkuId) throws Exception {
    ProductSkuAttrExample example = new ProductSkuAttrExample(); // 组装where查询条件的对象
    example.createCriteria().andProductSkuIdEqualTo(productSkuId);
    productSkuAttrDAO.deleteByExample(example);
  }

  @Override
  public void updateSkuAttrByProductSkuIdBatch(List list) throws Exception {
    productSkuAttrDAO.updateSkuAttrByProductSkuIdBatch(list);
  }

  @Override
  public List querySkuIdByAttr(Map condition) throws SQLException {
    return productSkuAttrDAO.querySkuIdByAttr(condition);
  }

  @Override
  public boolean queryByAttrValueId(Long attrValueId) throws Exception {

    ProductSkuAttrExample example = new ProductSkuAttrExample();
    example.createCriteria().andCategoryAttrValueIdEqualTo(attrValueId.intValue());

    if (productSkuAttrDAO.countByExample(example) > 0) {
      return true;
    }

    return false;
  }
}
