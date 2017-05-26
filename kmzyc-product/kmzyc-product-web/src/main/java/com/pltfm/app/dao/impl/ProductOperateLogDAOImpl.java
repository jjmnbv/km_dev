package com.pltfm.app.dao.impl;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ProductOperateLogDAO;
import com.pltfm.app.vobject.ProductOperateLog;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/8/10 16:28
 */
@Component("productOperateLogDao")
public class ProductOperateLogDAOImpl  extends BaseDao<ProductOperateLog> implements ProductOperateLogDAO {

    @Override
    public Long saveProductOperateLog(ProductOperateLog log) throws SQLException {
        return (Long) sqlMapClient.insert("PRODUCT_OPERATE_LOG.saveProductOperateLog", log);
    }

    @Override
    public List<ProductOperateLog> getProductOperateLogList(Long productId) throws SQLException {
        return sqlMapClient.queryForList("PRODUCT_OPERATE_LOG.getProductOperateLogList", productId);
    }

    @Override
    public void batchSaveProductOperateLog(List<ProductOperateLog> list) throws SQLException {
        batchInsertData(list, "PRODUCT_OPERATE_LOG.saveProductOperateLog");
    }
}
