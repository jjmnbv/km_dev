package com.kmzyc.supplier.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.kmzyc.supplier.dao.ProdBrandDAO;
import com.kmzyc.supplier.service.ProdBrandService;

import org.springframework.stereotype.Service;

import com.pltfm.app.vobject.ProdBrand;
import com.pltfm.app.vobject.ProdBrandExample;

@Service("prodBrandService")
public class ProdBrandServiceImpl implements ProdBrandService {

	@Resource
	private ProdBrandDAO prodBrandDao;
	
	@Override
	public List<ProdBrand> findAllValidBrand() throws ServiceException {
		ProdBrandExample exm = new ProdBrandExample();
		exm.createCriteria().andIsValidEqualTo("1");
        try {
            return prodBrandDao.selectByExample(exm);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public Page searchPage(ProdBrand prod, Page page) throws ServiceException {
		int pageIndex = page.getPageNo();
		if (pageIndex == 0)
			pageIndex = 1;
		int pageSize = page.getPageSize();
		int skip = (pageIndex - 1) * pageSize;
		int max = pageSize;
		String brandName = prod.getBrandName() == null || prod.getBrandName().isEmpty() ? "%%" : "%"
				+ prod.getBrandName() + "%";
		String chnSpell = prod.getChnSpell() == null || prod.getChnSpell().isEmpty() ? "%%" : "%" + prod.getChnSpell()
				+ "%";
		ProdBrandExample exm = new ProdBrandExample();
		ProdBrandExample.Criteria c = exm.createCriteria();
		c.andBrandNameLike(brandName).andChnSpellLike(chnSpell);
		if (prod.getEngName() != null && !prod.getEngName().isEmpty()) {
			c.andEngNameLike("%" + prod.getEngName() + "%");
		}
		exm.setOrderByClause(" BRAND_ID desc");
		try {
            int count = prodBrandDao.countByExample(exm);
			List<ProdBrand> list = prodBrandDao.selectByExample(exm, skip, max);
			page.setDataList(list);
			page.setRecordCount(count);
		} catch (SQLException e) {
            throw new ServiceException(e);
		}
		return page;
	}
}