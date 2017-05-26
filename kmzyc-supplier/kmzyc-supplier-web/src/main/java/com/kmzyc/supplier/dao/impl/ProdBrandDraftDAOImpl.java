package com.kmzyc.supplier.dao.impl;

import com.km.framework.page.Pagination;
import com.kmzyc.supplier.dao.ProdBrandDraftDAO;
import com.pltfm.app.vobject.ProdBrandDraft;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2015/11/19 16:35
 */
@Repository("prodBrandDraftDao")
public class ProdBrandDraftDAOImpl extends BaseDAO implements ProdBrandDraftDAO {

    @Override
    public Long insert(ProdBrandDraft record) throws SQLException {
        return (Long) sqlMapClient.insert("prod_brand_draft.insert", record);
    }

    @Override
    public Pagination findPaginationByPage(Pagination pagination) throws SQLException {
        return findPaginationByPage(sqlMapClient, "prod_brand_draft.selectProdBrandDraftList",
                "prod_brand_draft.countProdBrandDraftList", pagination);
    }

    @Override
    public ProdBrandDraft getProdBrandDraft(ProdBrandDraft prodBrandDraft) throws SQLException {
        return (ProdBrandDraft) sqlMapClient.queryForObject("prod_brand_draft.getProdBrandDraft", prodBrandDraft);
    }

    @Override
    public int updateProdBrandDraft(ProdBrandDraft record) throws SQLException {
        return sqlMapClient.update("prod_brand_draft.updateProdBrandDraft", record);
    }

    @Override
    public int deleteProdBrandDraft(ProdBrandDraft record) throws SQLException {
        return sqlMapClient.delete("prod_brand_draft.deleteProdBrandDraft", record);
    }

    public boolean isExistsBrandName(String brandName) throws SQLException {
        Integer i = (Integer) sqlMapClient.queryForObject("prod_brand_draft.isExistsBrandName", brandName);
        return i != null && i.intValue() > 0;
    }
}
