package com.pltfm.app.dao.impl;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ViewSkuAttrDAO;
import com.pltfm.app.vobject.ViewSkuAttr;
import com.pltfm.app.vobject.ViewSkuAttrExample;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * SKU关联属性视图
 * @author tanyunxing
 *
 */
@Repository("viewSkuAttrDao")
public class ViewSkuAttrDAOImpl extends BaseDao implements ViewSkuAttrDAO {

    public int countByExample(ViewSkuAttrExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("VIEW_SKU_ATTR.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(ViewSkuAttrExample example) throws SQLException {
        return sqlMapClient.delete("VIEW_SKU_ATTR.ibatorgenerated_deleteByExample", example);
    }

    public void insert(ViewSkuAttr record) throws SQLException {
        sqlMapClient.insert("VIEW_SKU_ATTR.ibatorgenerated_insert", record);
    }

    public void insertSelective(ViewSkuAttr record) throws SQLException {
        sqlMapClient.insert("VIEW_SKU_ATTR.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(ViewSkuAttrExample example) throws SQLException {
        return sqlMapClient.queryForList("VIEW_SKU_ATTR.ibatorgenerated_selectByExample", example);
    }

    public int updateByExampleSelective(ViewSkuAttr record, ViewSkuAttrExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("VIEW_SKU_ATTR.ibatorgenerated_updateByExampleSelective", parms);
    }

    public int updateByExample(ViewSkuAttr record, ViewSkuAttrExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("VIEW_SKU_ATTR.ibatorgenerated_updateByExample", parms);
    }

    private static class UpdateByExampleParms extends ViewSkuAttrExample {
        private Object record;

        public UpdateByExampleParms(Object record, ViewSkuAttrExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

	@Override
	public Long selectByAttrIdAndValueId(ViewSkuAttrExample example) throws SQLException {
		Object obj = sqlMapClient.queryForObject("VIEW_SKU_ATTR.findSkuIdByAttrAndValue", example);
		return (Long) obj;
	}

	@Override
	public List<ViewSkuAttr> findProductAttrAndValueByProductId(Long productId) throws SQLException {
		return sqlMapClient.queryForList("VIEW_SKU_ATTR.queryProductAttrAndValue", productId);
	}
}