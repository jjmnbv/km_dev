package com.kmzyc.supplier.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.framework.page.Pagination;
import com.kmzyc.supplier.model.ShopMainDraft;
import com.kmzyc.supplier.model.ShopMainDraftWithBLOBs;
import com.kmzyc.supplier.dao.ShopMainDraftDAO;
import com.kmzyc.supplier.model.example.ShopMainDraftExample;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

@Repository("shopMainDraftDAO")
public class ShopMainDraftDAOImpl extends BaseDAO implements ShopMainDraftDAO {
	
	@Resource
    private SqlMapClient sqlMapClient;

    public int countByExample(ShopMainDraftExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("SHOP_MAIN_DRAFT.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(ShopMainDraftExample example) throws SQLException {
        int rows = sqlMapClient.delete("SHOP_MAIN_DRAFT.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public Long insert(ShopMainDraftWithBLOBs record) throws SQLException {
		return (Long) sqlMapClient.insert("SHOP_MAIN_DRAFT.ibatorgenerated_insert", record);
    }

    public Long insertSelective(ShopMainDraftWithBLOBs record) throws SQLException {
		return (Long) sqlMapClient.insert("SHOP_MAIN_DRAFT.ibatorgenerated_insertSelective", record);
    }
    
    public Long insertSelectiveWithOutClob(ShopMainDraft record) throws SQLException {
		return (Long) sqlMapClient.insert("SHOP_MAIN_DRAFT.ibatorgenerated_insertSelectiveWithOutClob", record);
    }
    
    public Long insertAndUpdateShopMainDraftWithOutClob(ShopMainDraft record) throws SQLException {
    	return (Long) sqlMapClient.insert("SHOP_MAIN_DRAFT.insertAndUpdateShopMainDraftWithOutClob", record);
    }

    public List selectByExampleWithoutBLOBs(ShopMainDraftExample example) throws SQLException {
        List list = sqlMapClient.queryForList("SHOP_MAIN_DRAFT.ibatorgenerated_selectByExample", example);
        return list;
    }

    public int updateByExampleSelective(ShopMainDraftWithBLOBs record, ShopMainDraftExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SHOP_MAIN_DRAFT.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(ShopMainDraftWithBLOBs record, ShopMainDraftExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SHOP_MAIN_DRAFT.ibatorgenerated_updateByExampleWithBLOBs", parms);
        return rows;
    }

    public int updateByExample(ShopMainDraft record, ShopMainDraftExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SHOP_MAIN_DRAFT.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    private static class UpdateByExampleParms extends ShopMainDraftExample {
        private Object record;

        public UpdateByExampleParms(Object record, ShopMainDraftExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    @Override
	public Pagination findShopMainListByPage(Pagination page) throws SQLException {
		return findPaginationByPage(sqlMapClient, "SHOP_MAIN_DRAFT.selectShopMainListBySupplierId",
				"SHOP_MAIN_DRAFT.selectCountForShopMainBySupplierId", page);
	}

	@Override
	public int deleteByShopId(Long shopId) throws SQLException {
		int rows = sqlMapClient.delete("SHOP_MAIN_DRAFT.deleteByShopId", shopId);
        return rows;
	}

	@Override
	public ShopMainDraft selectByPrimaryKeyWithoutBLOBS(Long shopId) throws SQLException {
		return (ShopMainDraft) sqlMapClient.queryForObject("SHOP_MAIN_DRAFT.ibatorgenerated_selectByPrimaryKey", shopId);
	}

	@Override
	public int updateByPrimaryKeyWithoutBLOBS(ShopMainDraft shopMainDraft) throws SQLException {
		return sqlMapClient.update("SHOP_MAIN_DRAFT.ibatorgenerated_updateByPrimaryKey", shopMainDraft);
	}

	@Override
	public int findShopMainDraft(Long supplierId) throws SQLException {
		Map paramMap = new HashMap();
		paramMap.put("supplierId", supplierId);
		return (Integer) sqlMapClient.queryForObject("SHOP_MAIN_DRAFT.findShopMainDraft", paramMap);
	}

	@Override
	public Long insertDraftFromOfficial(Long shopId) throws SQLException {
		return (Long) sqlMapClient.insert("SHOP_MAIN_DRAFT.insertDraftFromOfficial", shopId);
	}

	@Override
	public String selectRepeatName(Map conditionMap) throws SQLException {
		return (String) sqlMapClient.queryForObject("SHOP_MAIN_DRAFT.selectRepeatName", conditionMap);
	}
	
	@Override
	public String selectRepeatNameForUpdateShopMain(Map conditionMap) throws SQLException {
		return (String) sqlMapClient.queryForObject("SHOP_MAIN_DRAFT.selectRepeatNameForUpdateShopMain", conditionMap);
	}
}