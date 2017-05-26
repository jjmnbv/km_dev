package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.supplier.model.ShopMainDraft;
import com.kmzyc.supplier.model.example.ShopMainDraftExample;

public interface ShopMainDraftDAO {
	
	int countByExample(ShopMainDraftExample example) throws SQLException;
	
	List selectByExampleShopDraftList(ShopMainDraftExample example, int skip, int max) throws SQLException;
	
	ShopMainDraft selectByPrimaryKey(Long shopId) throws SQLException;
	
	int updateByPrimaryKeySelective(ShopMainDraft record) throws SQLException;
	
	int updateBatch(List<ShopMainDraft> list) throws SQLException;
	
	List selectByExampleForOfficial(ShopMainDraftExample example) throws SQLException;
	
	int deleteDraftList(List<Long> shopIdList) throws SQLException;
	
	int updateOfficialFromDraft(ShopMainDraft record) throws SQLException;
	
	Long insertOfficialFromDraft(ShopMainDraft record) throws SQLException;
	
	int updateShopMainRemarkByShopId(ShopMainDraft shopMainDraft) throws SQLException;

}
