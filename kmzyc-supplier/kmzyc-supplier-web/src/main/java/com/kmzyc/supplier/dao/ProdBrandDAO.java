package com.kmzyc.supplier.dao;

import com.pltfm.app.vobject.ProdBrand;
import com.pltfm.app.vobject.ProdBrandExample;
import java.sql.SQLException;
import java.util.List;

/**
 * 产品品牌DAO
 * @author tanyunxing
 *
 */
public interface ProdBrandDAO {

	List<ProdBrand> selectByExample(ProdBrandExample example) throws SQLException;

	int countByExample(ProdBrandExample exm) throws SQLException;

	List<ProdBrand> selectByExample(ProdBrandExample exm, int skip, int max) throws SQLException;
	
}