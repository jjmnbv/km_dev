package com.pltfm.app.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.app.dao.BnesAcctEnchashmentDAO;
import com.pltfm.app.vobject.BnesAcctEnchashment;

@Component(value = "bnesAcctEnchashmentDAO")
public class BnesAcctEnchashmentDAOImpl implements BnesAcctEnchashmentDAO {
    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;



    // 删除取现信息
    public int deleteByPrimaryKey(BigDecimal enchashmentId) throws SQLException {
        BnesAcctEnchashment key = new BnesAcctEnchashment();
        key.setEnchashmentId(enchashmentId);
        int rows =
                sqlMapClient
                        .delete("BNES_ACCT_ENCHASHMENT.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    // 添加取现信息
    public BigDecimal insertSelective(BnesAcctEnchashment record) throws SQLException {
        return (BigDecimal) sqlMapClient.insert(
                "BNES_ACCT_ENCHASHMENT.ibatorgenerated_insertSelective", record);
    }

    // 查询取现申请详情
    public BnesAcctEnchashment selectByPrimaryKeyEnchashment(int BnesAcctEnchashmentId)
            throws SQLException {
        return (BnesAcctEnchashment) sqlMapClient.queryForObject(
                "BNES_ACCT_ENCHASHMENT.ibatorgenerated_selectByPrimaryKeyEnchashment",
                BnesAcctEnchashmentId);
    }

    // 关联供应商查询取现信息
    public BnesAcctEnchashment selectByPrimaryKey(int bnesAcctEnchashmentId) throws SQLException {
        return (BnesAcctEnchashment) sqlMapClient.queryForObject(
                "BNES_ACCT_ENCHASHMENT.ibatorgenerated_selectByPrimaryKey", bnesAcctEnchashmentId);
    }

    // 关联微商查询取现信息
    public BnesAcctEnchashment selectByPrimaryKeyVS(int bnesAcctEnchashmentId) throws SQLException {
        return (BnesAcctEnchashment) sqlMapClient
                .queryForObject("BNES_ACCT_ENCHASHMENT.ibatorgenerated_selectByPrimaryKeyVS",
                        bnesAcctEnchashmentId);
    }

    // 修改取现信息
    public int updateByPrimaryKeySelective(BnesAcctEnchashment record) throws SQLException {
        int rows =
                sqlMapClient
                        .update("BNES_ACCT_ENCHASHMENT.ibatorgenerated_updateByPrimaryKeySelective",
                                record);
        return rows;
    }

    // 分页查询余额取现列表
    @SuppressWarnings("unchecked")
    public List<BnesAcctEnchashment> queryBnesAcctEnchashmentListByPage(
            BnesAcctEnchashment bnesAcctEnchashment) throws SQLException {
        return sqlMapClient.queryForList(
                "BNES_ACCT_ENCHASHMENT.ibatorgenerated_selectByPrimaryKeyByPage",
                bnesAcctEnchashment);
    }

    // 条件查询余额取现记录总数
    public int queryBnesAcctEnchashmentListByCount(BnesAcctEnchashment bnesAcctEnchashment)
            throws SQLException {
        Integer i =
                (Integer) sqlMapClient.queryForObject(
                        "BNES_ACCT_ENCHASHMENT.ibatorgenerated_selectByPrimaryKeyByCount",
                        bnesAcctEnchashment);
        if (i == null) {
            i = 0;
        }
        return i;
    }

    @Override
    public BigDecimal queryEnchashmentTotalAmount(BnesAcctEnchashment record) throws Exception {
        return (BigDecimal) sqlMapClient.queryForObject(
                "BNES_ACCT_ENCHASHMENT.ibatorgenerated_queryEnchashmentTotalAmount", record);
    }

    @Override
    public BigDecimal queryEnchashmentTotalAmountForFinish(BnesAcctEnchashment record)
            throws Exception {
        return (BigDecimal) sqlMapClient.queryForObject(
                "BNES_ACCT_ENCHASHMENT.ibatorgenerated_queryEnchashmentTotalAmountForFinish",
                record);
    }


    @Override
    public int queryUnfinashedEnchashmentCount(BnesAcctEnchashment record) throws Exception {
        Integer i =
                (Integer) sqlMapClient.queryForObject(
                        "BNES_ACCT_ENCHASHMENT.selectUnfinashedEnchashmentCount", record);
        if (i == null) {
            i = 0;
        }
        return i;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BnesAcctEnchashment> queryExportDatas(BnesAcctEnchashment bnesAcctEnchashment)
            throws SQLException {

        return sqlMapClient.queryForList("BNES_ACCT_ENCHASHMENT.queryExportDates",
                bnesAcctEnchashment);
    }

}
