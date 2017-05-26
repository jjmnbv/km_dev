package com.kmzyc.promotion.app.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.promotion.app.dao.BaseDao;
import com.kmzyc.promotion.app.dao.ProductSkuAttrDAO;
import com.kmzyc.promotion.app.vobject.ProductSkuAttr;
import com.kmzyc.promotion.app.vobject.ProductSkuAttrExample;

@Repository("productSkuAttrDAO")
@SuppressWarnings({"unchecked", "unused"})
public class ProductSkuAttrDAOImpl extends BaseDao<ProductSkuAttr> implements ProductSkuAttrDAO {
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * PRODUCT_SKU_ATTR
   * 
   * @ibatorgenerated Thu Aug 01 14:47:07 CST 2013
   */
  @Resource
  private SqlMapClient sqlMapClient;

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PRODUCT_SKU_ATTR
   * 
   * @ibatorgenerated Thu Aug 01 14:47:07 CST 2013
   */
  @Override
  public int countByExample(ProductSkuAttrExample example) throws SQLException {
    // mkw 20150819 add

    // end
    Integer count =
        (Integer) sqlMapClient.queryForObject("PRODUCT_SKU_ATTR.ibatorgenerated_countByExample",
            example);
    return count.intValue();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PRODUCT_SKU_ATTR
   * 
   * @ibatorgenerated Thu Aug 01 14:47:07 CST 2013
   */
  @Override
  public int deleteByExample(ProductSkuAttrExample example) throws SQLException {
    // mkw 20150819 add

    // end
    int rows = sqlMapClient.delete("PRODUCT_SKU_ATTR.ibatorgenerated_deleteByExample", example);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PRODUCT_SKU_ATTR
   * 
   * @ibatorgenerated Thu Aug 01 14:47:07 CST 2013
   */
  @Override
  public int deleteByPrimaryKey(Long productSkuAttrId) throws SQLException {
    // mkw 20150819 add

    // end
    ProductSkuAttr key = new ProductSkuAttr();
    key.setProductSkuAttrId(productSkuAttrId);
    int rows = sqlMapClient.delete("PRODUCT_SKU_ATTR.ibatorgenerated_deleteByPrimaryKey", key);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PRODUCT_SKU_ATTR
   * 
   * @ibatorgenerated Thu Aug 01 14:47:07 CST 2013
   */
  @Override
  public void insert(ProductSkuAttr record) throws SQLException {
    // mkw 20150819 add

    // end
    sqlMapClient.insert("PRODUCT_SKU_ATTR.ibatorgenerated_insert", record);
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PRODUCT_SKU_ATTR
   * 
   * @ibatorgenerated Thu Aug 01 14:47:07 CST 2013
   */
  @Override
  public void insertSelective(ProductSkuAttr record) throws SQLException {
    // mkw 20150819 add

    // end
    sqlMapClient.insert("PRODUCT_SKU_ATTR.ibatorgenerated_insertSelective", record);
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PRODUCT_SKU_ATTR
   * 
   * @ibatorgenerated Thu Aug 01 14:47:07 CST 2013
   */
  @Override
  public List selectByExample(ProductSkuAttrExample example) throws SQLException {
    // mkw 20150819 add

    // end
    List list =
        sqlMapClient.queryForList("PRODUCT_SKU_ATTR.ibatorgenerated_selectByExample", example);
    return list;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PRODUCT_SKU_ATTR
   * 
   * @ibatorgenerated Thu Aug 01 14:47:07 CST 2013
   */
  @Override
  public ProductSkuAttr selectByPrimaryKey(Long productSkuAttrId) throws SQLException {
    // mkw 20150819 add

    // end
    ProductSkuAttr key = new ProductSkuAttr();
    key.setProductSkuAttrId(productSkuAttrId);
    ProductSkuAttr record =
        (ProductSkuAttr) sqlMapClient.queryForObject(
            "PRODUCT_SKU_ATTR.ibatorgenerated_selectByPrimaryKey", key);
    return record;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PRODUCT_SKU_ATTR
   * 
   * @ibatorgenerated Thu Aug 01 14:47:07 CST 2013
   */
  @Override
  public int updateByExampleSelective(ProductSkuAttr record, ProductSkuAttrExample example)
      throws SQLException {
    // mkw 20150819 add

    // end
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows =
        sqlMapClient.update("PRODUCT_SKU_ATTR.ibatorgenerated_updateByExampleSelective", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PRODUCT_SKU_ATTR
   * 
   * @ibatorgenerated Thu Aug 01 14:47:07 CST 2013
   */
  @Override
  public int updateByExample(ProductSkuAttr record, ProductSkuAttrExample example)
      throws SQLException {
    // mkw 20150819 add

    // end
    UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
    int rows = sqlMapClient.update("PRODUCT_SKU_ATTR.ibatorgenerated_updateByExample", parms);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PRODUCT_SKU_ATTR
   * 
   * @ibatorgenerated Thu Aug 01 14:47:07 CST 2013
   */
  @Override
  public int updateByPrimaryKeySelective(ProductSkuAttr record) throws SQLException {
    // mkw 20150819 add

    // end
    int rows =
        sqlMapClient.update("PRODUCT_SKU_ATTR.ibatorgenerated_updateByPrimaryKeySelective", record);
    return rows;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table PRODUCT_SKU_ATTR
   * 
   * @ibatorgenerated Thu Aug 01 14:47:07 CST 2013
   */
  @Override
  public int updateByPrimaryKey(ProductSkuAttr record) throws SQLException {
    // mkw 20150819 add

    // end
    int rows = sqlMapClient.update("PRODUCT_SKU_ATTR.ibatorgenerated_updateByPrimaryKey", record);
    return rows;
  }

  /**
   * This class was generated by Apache iBATIS ibator. This class corresponds to the database table
   * PRODUCT_SKU_ATTR
   * 
   * @ibatorgenerated Thu Aug 01 14:47:07 CST 2013
   */
  private static class UpdateByExampleParms extends ProductSkuAttrExample {
    private final Object record;

    public UpdateByExampleParms(Object record, ProductSkuAttrExample example) {
      super(example);
      this.record = record;
    }

    public Object getRecord() {
      return record;
    }
  }

  @Override
  public List querySkuIdByAttr(Map condition) throws SQLException {
    // mkw 20150819 add

    // end
    return sqlMapClient.queryForList("PRODUCT_SKU_ATTR.querySkuIdByAttr", condition);
  }

  @Override
  public int updateSkuAttrByProductSkuIdBatch(List list) throws SQLException {
    // mkw 20150819 add

    // end
    return super.batchUpdateDataNotGen("PRODUCT_SKU_ATTR.updateSkuAttrStatus", list);
  }
}