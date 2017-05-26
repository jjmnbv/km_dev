package com.pltfm.app.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pltfm.app.vobject.Consult;
import com.pltfm.app.vobject.ConsultExample;
import com.kmzyc.commons.page.Page;

public interface ConsultDAO {

    int countByExample(ConsultExample example) throws SQLException;

    int deleteByExample(ConsultExample example) throws SQLException;

    int deleteByPrimaryKey(BigDecimal consultId) throws SQLException;

    void insert(Consult record) throws SQLException;

    void insertSelective(Consult record) throws SQLException;

    Consult insertConsult(Consult consult) throws SQLException;

    List selectByExample(ConsultExample example) throws SQLException;

    Consult selectByPrimaryKey(long consultId) throws SQLException;

    int updateByExampleSelective(Consult record, ConsultExample example) throws SQLException;

    int updateByExample(Consult record, ConsultExample example) throws SQLException;

    int updateByPrimaryKeySelective(Consult record) throws SQLException;

    int updateByPrimaryKey(Consult record) throws SQLException;

    Page selectPageByVo(Page page, Consult vo) throws SQLException;

    /**
     * 获取栏目信息列表的远程接口
     *
     * @param vo
     * @return 分页栏目列表
     * @throws SQLException SQL异常
     */
    List selectPageByVo(Consult vo) throws SQLException;

    /**
     * 获取咨询的个数、远程接口
     *
     * @param consult
     * @return
     * @throws SQLException
     */
    List queryForList(Consult consult) throws SQLException;

    /**
     * 获取咨询分页实体
     *
     * @param consult
     * @return
     * @throws SQLException
     */
    List queryForPage(Consult consult) throws SQLException;

    Consult selectByConsultId(BigDecimal consultId) throws SQLException;

}