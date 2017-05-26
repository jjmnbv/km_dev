package com.pltfm.app.dao.impl;

import com.pltfm.app.dao.BaseDao;
import com.pltfm.app.dao.ProdBrandDraftDAO;
import com.pltfm.app.vobject.ProdBrandDraft;
import com.pltfm.app.vobject.ProdBrandDraftExample;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2015/11/19 16:35
 */
@Repository("prodBrandDraftDao")
public class ProdBrandDraftDAOImpl extends BaseDao implements ProdBrandDraftDAO {

    @Override
    public Long insert(ProdBrandDraft record) throws SQLException {
        return (Long) sqlMapClient.insert("prod_brand_draft.insert", record);
    }

    @Override
    public void insertIntoProdBrand(ProdBrandDraft record) throws SQLException {
        sqlMapClient.insert("prod_brand_draft.insertIntoProdBrand", record);
    }

    @Override
    public Long insertSelective(ProdBrandDraft record) throws SQLException {
        return (Long) sqlMapClient.insert("prod_brand_draft.insertSelective", record);
    }

    @Override
    public List<ProdBrandDraft> selectByExample(ProdBrandDraftExample example) throws SQLException {
        return sqlMapClient.queryForList("prod_brand_draft.selectByExample", example);
    }

    @Override
    public List selectByExample(ProdBrandDraftExample example, int skip, int max) throws SQLException {
        return sqlMapClient.queryForList("prod_brand_draft.selectByExample", example, skip, max);
    }

    @Override
    public int countByExample(ProdBrandDraftExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("prod_brand_draft.countByExample", example);
        return count.intValue();
    }

    @Override
    public ProdBrandDraft getProdBrandDraftById(Long brandId) throws SQLException {
        return (ProdBrandDraft) sqlMapClient.queryForObject("prod_brand_draft.getProdBrandDraftById", brandId);
    }

    @Override
    public int refuseProdBrandDraft(ProdBrandDraft record) throws SQLException {
        return sqlMapClient.update("prod_brand_draft.refuseProdBrandDraft", record);
    }

    @Override
    public int passProdBrandDraft(ProdBrandDraft record) throws SQLException {
        return sqlMapClient.update("prod_brand_draft.passProdBrandDraft", record);
    }

    @Override
    public int deleteByPrimaryKey(Long brandId) throws SQLException {
        return sqlMapClient.delete("prod_brand_draft.deleteByPrimaryKey", brandId);
    }
}
