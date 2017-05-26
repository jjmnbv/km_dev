package com.kmzyc.supplier.dao;

import java.sql.SQLException;
import java.util.List;

import com.km.framework.page.Pagination;
import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.supplier.model.example.ShopMainExample;

public interface ShopMainDAO {

    int countByExample(ShopMainExample example) throws SQLException;

    int deleteByExample(ShopMainExample example) throws SQLException;

    int deleteByPrimaryKey(Long shopId) throws SQLException;

    void insert(ShopMain record) throws SQLException;

    Object insertSelective(ShopMain record) throws SQLException;

    List selectByExampleWithBLOBs(ShopMainExample example) throws SQLException;

    List selectByExampleWithoutBLOBs(ShopMainExample example) throws SQLException;

    ShopMain selectByPrimaryKey(Long shopId) throws SQLException;

    int updateByExampleSelective(ShopMain record, ShopMainExample example) throws SQLException;

    int updateByPrimaryKeySelective(ShopMain record) throws SQLException;

    Pagination findShopMainListByPage(Pagination page) throws SQLException;
    
    int findShopMain(Long supplierId) throws SQLException;
    
    int updateStatus(List<Long> shopIdList,String status) throws SQLException;
    
    int updateAuditStatus(List<Long> shopIdList,String auditStatus) throws SQLException;
    
    int batchUpdateShopMain(List<ShopMain> shopMainList) throws SQLException;
    
    int deleteShopMainFilePath(ShopMain shopMain) throws SQLException;
    
    int updateShopMainDescibe(ShopMain shopMain) throws SQLException;
    
    int updateDefaultDomainUrl(ShopMain shopMain) throws SQLException;
}