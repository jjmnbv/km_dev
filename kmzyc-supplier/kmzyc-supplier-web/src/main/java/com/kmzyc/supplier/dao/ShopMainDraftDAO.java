package com.kmzyc.supplier.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.km.framework.page.Pagination;
import com.kmzyc.supplier.model.ShopMainDraft;
import com.kmzyc.supplier.model.ShopMainDraftWithBLOBs;
import com.kmzyc.supplier.model.example.ShopMainDraftExample;

public interface ShopMainDraftDAO {

    int countByExample(ShopMainDraftExample example) throws SQLException;

    int deleteByExample(ShopMainDraftExample example) throws SQLException;
    
    int deleteByShopId(Long shopId) throws SQLException;

    Long insert(ShopMainDraftWithBLOBs record) throws SQLException;

    Long insertSelective(ShopMainDraftWithBLOBs record) throws SQLException;
    
    Long insertSelectiveWithOutClob(ShopMainDraft record) throws SQLException;

    List selectByExampleWithoutBLOBs(ShopMainDraftExample example) throws SQLException;
    
    ShopMainDraft selectByPrimaryKeyWithoutBLOBS(Long shopId) throws SQLException;

    int updateByExampleSelective(ShopMainDraftWithBLOBs record, ShopMainDraftExample example) throws SQLException;

    int updateByExample(ShopMainDraftWithBLOBs record, ShopMainDraftExample example) throws SQLException;

    int updateByExample(ShopMainDraft record, ShopMainDraftExample example) throws SQLException;
    
    int updateByPrimaryKeyWithoutBLOBS(ShopMainDraft shopMainDraft) throws SQLException;
    
    Pagination findShopMainListByPage(Pagination page) throws SQLException;
    
    int findShopMainDraft(Long supplierId) throws SQLException;
    
    Long insertDraftFromOfficial(Long shopId) throws SQLException;
    
    String selectRepeatName(Map conditionMap) throws SQLException;
    
    Long insertAndUpdateShopMainDraftWithOutClob(ShopMainDraft record) throws SQLException;
    
    String selectRepeatNameForUpdateShopMain(Map conditionMap) throws SQLException;

}