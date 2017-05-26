package com.kmzyc.supplier.dao;

import com.km.framework.page.Pagination;
import com.pltfm.app.vobject.ProdBrandDraft;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
     * @param prodBrandDraft 品牌草稿记录
     * @return 品牌id
     * @throws SQLException
     */
    Long insert(ProdBrandDraft prodBrandDraft) throws SQLException;

    /**
     * 统计总共有多少品牌草稿数据(分页数据)
     * <note>
     * 合并查询列表和统计
     * </note>
     *
     * @param pagination 实例
     * @return
     * @throws SQLException
     */
    Pagination findPaginationByPage(Pagination pagination) throws SQLException;

    /**
     * 根据品牌草稿id和供应商对应的shopCode查询品牌草稿信息
     *
     * @param prodBrandDraft 品牌草稿id和shopCode
     * @return 品牌草稿信息
     */
    ProdBrandDraft getProdBrandDraft(ProdBrandDraft prodBrandDraft) throws SQLException;

    /**
     * 更新品牌草稿（更新固定字段）
     * <note>
     * 只有审核未通过的品牌数据可以更新
     * 更新条件必须包含brandId,shopCode
     * </note>
     *
     * @param prodBrandDraft 品牌草稿数据
     * @return 更新条数
     * @throws SQLException
     */
    int updateProdBrandDraft(ProdBrandDraft prodBrandDraft) throws SQLException;

    /**
     * 通过品牌草稿主键删除品牌草稿数据
     * <note>
     * 只能删除审核未通过的品牌信息
     * </note>
     *
     * @param prodBrandDraft 品牌草稿信息和shopCode
     * @return 删除条数
     * @throws SQLException
     */
    int deleteProdBrandDraft(ProdBrandDraft prodBrandDraft) throws SQLException;

    /**
     * 检查品牌名称在品牌正式库中是否存在
     *
     * @param brandName 品牌名称
     * @return  存在true，不存在false
     * @throws SQLException
     */
    boolean isExistsBrandName(String brandName) throws SQLException;

}