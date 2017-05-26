package com.pltfm.app.dao;

import com.pltfm.app.vobject.TiedSade;
import com.pltfm.app.vobject.TiedSadeExample;

import java.sql.SQLException;
import java.util.List;

/**
 * 产品搭售操作
 * 对应表Tied_SADE 操作
 *
 */
public interface TiedSadeDAO {
    
	int countByExample(TiedSadeExample example) throws SQLException;

	int deleteByExample(TiedSadeExample example) throws SQLException;

	int deleteByPrimaryKey(Long tiedSadeId) throws SQLException;

	void insert(TiedSade record) throws SQLException;

	void insertSelective(TiedSade record) throws SQLException;

	List selectByExample(TiedSadeExample example) throws SQLException;

	TiedSade selectByPrimaryKey(Long tiedSadeId) throws SQLException;

	int updateByExampleSelective(TiedSade record, TiedSadeExample example) throws SQLException;

	int updateByExample(TiedSade record, TiedSadeExample example) throws SQLException;

	int updateByPrimaryKeySelective(TiedSade record) throws SQLException;

	int updateByPrimaryKey(TiedSade record) throws SQLException;

	void updateMatchPriceByKey(TiedSade record) throws SQLException;

    /**
     * 根据产品搭售表的主键id 批删除记录
     * @param list
     * @throws SQLException
     */
	void delBatchByPrimaryKey(List<Long>list)throws SQLException;

	/**
	 * 批量添加搭售产品
	 * @param list      要搭售产品的集合
	 * @throws SQLException  异常
	 */
	int insertTiedSaleByBatch(List<TiedSade>list) throws SQLException;

	/**
	 * 根据搭售表的主键id,更改其搭售类型
	 * @param tiedSade   
	 * @throws Exception
	 */
	void updateTiedSaleType(TiedSade tiedSade)throws Exception;

}