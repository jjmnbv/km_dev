package com.pltfm.app.dao;

import com.pltfm.app.vobject.ProductOperateLog;

import java.sql.SQLException;
import java.util.List;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/8/10 16:25
 */
public interface ProductOperateLogDAO {

    /**
     * 保存日志
     * @param log
     * @return
     */
    Long saveProductOperateLog(ProductOperateLog log) throws SQLException;

    /**
     * 根据商品id查询日志操作
     * @param productId
     * @return
     */
    List<ProductOperateLog> getProductOperateLogList(Long productId) throws SQLException;

    /**
     * 批量保存日志
     * @param list
     * @return
     */
    void batchSaveProductOperateLog(List<ProductOperateLog> list) throws SQLException;
}
