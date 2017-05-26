package com.pltfm.app.dao;


import java.math.BigDecimal;
import java.sql.SQLException;

public interface ProductDictDAO {

	
	/**
	 * 获取代销商的PV值比例
	 * @return
	 * @throws SQLException
	 */
    BigDecimal getSupportPvProportion() throws SQLException;
}