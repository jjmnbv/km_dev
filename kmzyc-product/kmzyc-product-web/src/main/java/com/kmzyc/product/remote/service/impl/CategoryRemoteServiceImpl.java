package com.kmzyc.product.remote.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.kmzyc.product.remote.service.CategoryRemoteService;
import com.pltfm.app.dao.CategoryDAO;
import com.pltfm.app.vobject.Category;
import com.pltfm.app.vobject.CategoryExample;
import com.pltfm.app.vobject.CategoryExample.Criteria;

/**
 * 
 * @author tanyunxing
 * 
 */
@Service("categoryRemoteService")
public class CategoryRemoteServiceImpl implements CategoryRemoteService {

	@Resource
	private CategoryDAO categoryDao;

	@Override
	public Category findByCategoryId(Long categoryId) throws Exception {
		return categoryDao.selectByPrimaryKey(categoryId);
	}

	@Override
	public List<Category> findAllBusiCategorys(String channel) throws Exception {
		if (StringUtils.isNotBlank(channel) && !"ALL".equals(channel)) {
            throw new Exception("请输入正确的参数！");
		}
		CategoryExample exa = new CategoryExample();
		Criteria c = exa.createCriteria();
		c.andIsPhyEqualTo(2);
		return categoryDao.selectByExample(exa);
	}

	@Override
	public List<Category> findCategoryChildrenList(Category category) throws Exception {
		List<Category> list = null;
	    try {
	      CategoryExample exam = new CategoryExample(); // 组装where查询条件的对象
	      Criteria criteria = exam.createCriteria();
	      criteria.andParentIdEqualTo(category.getCategoryId()).andStatusEqualTo(category.getStatus());
	      if (category.getIsPhy() != null) {
	        criteria.andIsPhyEqualTo(category.getIsPhy());
	      }
	      list = categoryDao.selectByExample(exam);
	    } catch (SQLException e) {
	      e.printStackTrace();
	      throw e;
	    }
	    return list;
	}

}
