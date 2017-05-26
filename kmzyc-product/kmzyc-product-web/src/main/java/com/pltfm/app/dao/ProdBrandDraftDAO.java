package com.pltfm.app.dao;

import com.pltfm.app.vobject.ProdBrandDraft;
import com.pltfm.app.vobject.ProdBrandDraftExample;

import java.sql.SQLException;
import java.util.List;

/**
 * 功能：品牌草稿Dao
 *
 * @author Zhoujiwei
 * @since 2015/11/19 16:04
 */
public interface ProdBrandDraftDAO {

    /**
     * 插入品牌草稿
     *
     * @param record    品牌草稿记录
     * @return          品牌id
     * @throws SQLException
     */
    Long insert(ProdBrandDraft record) throws SQLException;

    /**
     * 审核通过往品牌库插入
     *
     * @param record    品牌草稿记录
     * @return          品牌id
     * @throws SQLException
     */
    void insertIntoProdBrand(ProdBrandDraft record) throws SQLException;

    /**
     * 插入品牌草稿
     * <note>
     *     动态插入非空字段
     * </note>
     *
     * @param record    品牌草稿记录
     * @return          品牌id
     * @throws SQLException
     */
    Long insertSelective(ProdBrandDraft record) throws SQLException;

    /**
     * 通过品牌草稿实例对象获取品牌草稿列表
     *
     * @param example   实例
     * @return          品牌草稿列表
     * @throws SQLException
     */
    List<ProdBrandDraft> selectByExample(ProdBrandDraftExample example) throws SQLException;

    /**
     * 通过品牌草稿实例对象获取品牌草稿列表（分页功能）
     *
     * @param example   实例
     * @param skip      起始数据
     * @param max       分页数据
     * @return          品牌草稿列表
     * @throws SQLException
     */
    List selectByExample(ProdBrandDraftExample example, int skip, int max) throws SQLException;

    /**
     * 统计总共有多少品牌草稿数据
     *
     * @param example   实例
     * @return          总数
     * @throws SQLException
     */
    int countByExample(ProdBrandDraftExample example) throws SQLException;

    /**
     * 通过品牌草稿主键获取品牌草稿数据
     *
     * @param brandId   品牌草稿主键
     * @return          品牌草稿数据
     * @throws SQLException
     */
    ProdBrandDraft getProdBrandDraftById(Long brandId) throws SQLException;

    /**
     * 品牌审核不通过
     *
     * @param record
     * @return
     * @throws SQLException
     */
    int refuseProdBrandDraft(ProdBrandDraft record) throws SQLException;

    /**
     * 品牌审核不通过
     *
     * @param record
     * @return
     * @throws SQLException
     */
    int passProdBrandDraft(ProdBrandDraft record) throws SQLException;

    /**
     * 通过品牌草稿主键删除品牌草稿数据
     *
     * @param brandId   品牌草稿主键
     * @return          删除条数
     * @throws SQLException
     */
    int deleteByPrimaryKey(Long brandId) throws SQLException;
}