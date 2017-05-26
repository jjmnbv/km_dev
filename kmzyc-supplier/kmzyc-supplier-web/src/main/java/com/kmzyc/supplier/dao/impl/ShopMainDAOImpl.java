package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.page.Pagination;
import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.supplier.dao.ShopMainDAO;
import com.kmzyc.supplier.model.example.ShopMainExample;

@Repository("shopMainDAO")
public class ShopMainDAOImpl extends BaseDAO implements ShopMainDAO {

	@Resource
    private SqlMapClient sqlMapClient;

    public int countByExample(ShopMainExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("SHOP_MAIN.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(ShopMainExample example) throws SQLException {
        int rows = sqlMapClient.delete("SHOP_MAIN.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long shopId) throws SQLException {
        ShopMain key = new ShopMain();
        key.setShopId(shopId);
        int rows = sqlMapClient.delete("SHOP_MAIN.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(ShopMain record) throws SQLException {
        sqlMapClient.insert("SHOP_MAIN.ibatorgenerated_insert", record);
    }

    public Object insertSelective(ShopMain record) throws SQLException {
        return sqlMapClient.insert("SHOP_MAIN.ibatorgenerated_insertSelective", record);
    }

    public List selectByExampleWithBLOBs(ShopMainExample example) throws SQLException {
        List list = sqlMapClient.queryForList("SHOP_MAIN.ibatorgenerated_selectByExampleWithBLOBs", example);
        return list;
    }

    public List selectByExampleWithoutBLOBs(ShopMainExample example) throws SQLException {
        List list = sqlMapClient.queryForList("SHOP_MAIN.ibatorgenerated_selectByExample", example);
        return list;
    }

    public ShopMain selectByPrimaryKey(Long shopId) throws SQLException {
        ShopMain key = new ShopMain();
        key.setShopId(shopId);
        ShopMain record = (ShopMain) sqlMapClient.queryForObject("SHOP_MAIN.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(ShopMain record, ShopMainExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SHOP_MAIN.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(ShopMain record) throws SQLException {
        int rows = sqlMapClient.update("SHOP_MAIN.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    private static class UpdateByExampleParms extends ShopMainExample {
        private Object record;

        public UpdateByExampleParms(Object record, ShopMainExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
    
	@Override
	public Pagination findShopMainListByPage(Pagination page) throws SQLException {
		return findPaginationByPage(sqlMapClient, "SHOP_MAIN.selectShopMainListBySupplierId",
				"SHOP_MAIN.selectCountForShopMainBySupplierId", page);
	}

	@Override
	public int findShopMain(Long supplierId) throws SQLException {
		Map paramMap = new HashMap();
		paramMap.put("supplierId", supplierId);
		return (Integer) sqlMapClient.queryForObject("SHOP_MAIN.findShopMain", paramMap);
	}

	@Override
	public int updateStatus(List<Long> shopIdList,String status) throws SQLException {
		Map map = new HashMap();
		map.put("status", status);
		map.put("shopIdList", shopIdList);
		return sqlMapClient.update("SHOP_MAIN.updateStatusForOpenOrClose", map);
	}

	@Override
	public int updateAuditStatus(List<Long> shopIdList,String auditStatus) throws SQLException {
		Map map = new HashMap();
		map.put("auditStatus", auditStatus);
		map.put("shopIdList", shopIdList);
		return sqlMapClient.update("SHOP_MAIN.updateAuditStatus", map);
	}

	@Override
	public int batchUpdateShopMain(List<ShopMain> shopMainList) throws SQLException {
		return super.batchUpdateData("SHOP_MAIN.ibatorgenerated_updateByPrimaryKeySelective", shopMainList);
	}

	@Override
	public int deleteShopMainFilePath(ShopMain shopMain) throws SQLException {
		return sqlMapClient.update("SHOP_MAIN.deleteShopMainFilePath", shopMain);
	}

	@Override
	public int updateShopMainDescibe(ShopMain shopMain) throws SQLException {
		 int rows = sqlMapClient.update("SHOP_MAIN.updateShopMainDescibe", shopMain);
	     return rows;
	}

	@Override
	public int updateDefaultDomainUrl(ShopMain shopMain) throws SQLException {
		int rows = sqlMapClient.update("SHOP_MAIN.updateDefaultDomainUrl", shopMain);
	     return rows;
	}
}