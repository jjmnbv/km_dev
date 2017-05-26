package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kmzyc.supplier.model.ShopMainDraft;
import com.kmzyc.supplier.model.example.ShopMainDraftExample;
import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ShopMainDraftDAO;

@Repository("shopMainDraftDao")
public class ShopMainDraftDAOImpl extends BaseDao implements ShopMainDraftDAO {

	@Override
	public int countByExample(ShopMainDraftExample example) throws SQLException {
		Integer count = (Integer)  sqlMapClient.queryForObject("SHOP_MAIN_DRAFT.ibatorgenerated_countByExample", example);
        return count.intValue();
	}

	@Override
	public List selectByExampleShopDraftList(ShopMainDraftExample example, int skip, int max) throws SQLException {
        return sqlMapClient.queryForList("SHOP_MAIN_DRAFT.ibatorgenerated_selectByExample", example,skip,max);
	}

	@Override
	public ShopMainDraft selectByPrimaryKey(Long shopId) throws SQLException {
		ShopMainDraft key = new ShopMainDraft();
        key.setShopId(shopId);
        return (ShopMainDraft) sqlMapClient.queryForObject("SHOP_MAIN_DRAFT.ibatorgenerated_selectByPrimaryKey", key);
	}

	@Override
	public int updateByPrimaryKeySelective(ShopMainDraft record) throws SQLException {
        return sqlMapClient.update("SHOP_MAIN_DRAFT.ibatorgenerated_updateByPrimaryKey", record);
	}

	@Override
	public int updateBatch(List<ShopMainDraft> list) throws SQLException {
		return super.batchUpdateNt(list, "SHOP_MAIN_DRAFT.ibatorgenerated_updateByPrimaryKey");
	}

	@Override
	public List selectByExampleForOfficial(ShopMainDraftExample example) throws SQLException {
        return sqlMapClient.queryForList("SHOP_MAIN_DRAFT.ibatorgenerated_selectByExampleForOfficial", example);
	}

	@Override
	public int deleteDraftList(List<Long> shopIdList) throws SQLException {
		return super.batchDeleteByDataPrimaryKeyNt(shopIdList, "SHOP_MAIN_DRAFT.deleteByShopId");
	}

	@Override
	public int updateOfficialFromDraft(ShopMainDraft record) throws SQLException {
		return sqlMapClient.update("SHOP_MAIN_DRAFT.updateOfficialFromDraftByPrimaryKey", record);
	}

	@Override
	public Long insertOfficialFromDraft(ShopMainDraft record) throws SQLException {
		return (Long) sqlMapClient.insert("SHOP_MAIN_DRAFT.insertOfficialFromDraft", record);
	}

	@Override
	public int updateShopMainRemarkByShopId(ShopMainDraft shopMainDraft) throws SQLException {
		return sqlMapClient.update("SHOP_MAIN_DRAFT.updateShopMainRemarkByShopId", shopMainDraft);
	}

}