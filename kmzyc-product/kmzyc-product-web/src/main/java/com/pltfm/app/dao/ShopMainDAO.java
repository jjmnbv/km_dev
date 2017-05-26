package com.pltfm.app.dao;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.supplier.model.example.ShopMainExample;
import com.pltfm.app.vobject.ShopForExport;

public interface ShopMainDAO {

    int countByExample(ShopMainExample example) throws SQLException;

    int deleteByExample(ShopMainExample example) throws SQLException;

    int deleteByPrimaryKey(Long shopId) throws SQLException;

    void insert(ShopMain record) throws SQLException;

    void insertSelective(ShopMain record) throws SQLException;

    List selectByExampleShopList(ShopMainExample example, int skip, int max)throws SQLException;

    ShopMain selectByPrimaryKey(Long shopId) throws SQLException;

    int updateByExampleSelective(ShopMain record, ShopMainExample example) throws SQLException;

    int updateByPrimaryKeySelective(ShopMain record) throws SQLException;

    List<ShopMain> selectBySupplierId(Long supplierId) throws SQLException;

    /**
     * 查询所有启用的供应商的店铺
     * maliqun add
     * @return
     * @throws SQLException
     */
    List<ShopMain> querySupplierShop() throws SQLException;
    
    /**
     * 供应商店铺导出
     * @param example
     * @return
     * @throws SQLException
     */
    List<ShopForExport> selectSupplierShopInfoForExcel(ShopMainExample example)throws SQLException;
    
}