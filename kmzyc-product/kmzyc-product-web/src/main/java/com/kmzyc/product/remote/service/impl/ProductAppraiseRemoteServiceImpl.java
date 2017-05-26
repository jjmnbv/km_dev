package com.kmzyc.product.remote.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.product.remote.service.ProductAppraiseRemoteService;
import com.pltfm.app.dao.AppraiseAddtoContentDAO;
import com.pltfm.app.dao.AppraiseReplyDAO;
import com.pltfm.app.dao.ProdAppraiseDAO;
import com.pltfm.app.service.AppraiseAddtoContentService;
import com.pltfm.app.service.ProdAppraiseService;
import com.pltfm.app.vobject.AppraiseAddtoContent;
import com.pltfm.app.vobject.AppraiseReply;
import com.pltfm.app.vobject.ProdAppraise;
import com.pltfm.app.vobject.ProdAppraiseExample;

/**
 * 
 * @author tanyunxing
 *
 */
@Service("productAppraiseRemoteService")
public class ProductAppraiseRemoteServiceImpl implements ProductAppraiseRemoteService {

	@Resource
	private ProdAppraiseDAO prodAppraiseDao;
	
	@Resource
	private ProdAppraiseService prodAppraiseService;
	
	@Resource
	private AppraiseReplyDAO appraiseReplyDao;
	
	@Resource
	private AppraiseAddtoContentDAO appraiseAddtoContentDao;
	
	@Resource
	private AppraiseAddtoContentService appraiseAddtoContentService;
	
	@Override
	public void insertAppraise(ProdAppraise appraise) throws Exception {
		prodAppraiseService.saveProductAppraise(appraise);
	}

	@Override
	public List<ProdAppraise> findAppraiseBySkuId(Long skuId) throws Exception {
		ProdAppraiseExample exa = new ProdAppraiseExample();
		exa.createCriteria().andproductSkuIdEqualTo(BigDecimal.valueOf(skuId));
		exa.setOrderByClause(" APPRAISE_DATE desc ");
		return prodAppraiseDao.selectByExample(exa);
	}
	
	@Override
	public void updateAppraise(ProdAppraise appraise) throws Exception {
		prodAppraiseDao.updateByPrimaryKeySelective(appraise);
	}

	@Override
	public void saveAppraiseReply(AppraiseReply reply) throws Exception {
		appraiseReplyDao.insert(reply);
	}

	@Override
	public boolean saveAddtoContent(AppraiseAddtoContent content) throws Exception {
		return appraiseAddtoContentService.saveAddtoContent(content);
	}

	@Override
	public void updateAddtoContent(AppraiseAddtoContent content)
			throws Exception {
		appraiseAddtoContentDao.updateByPrimaryKeySelective(content);
	}

    @Override
    public Map<String, Object> queryProductAppraise(Boolean isTimeSort, String sort, Long skuId, int pageNumber) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        if (pageNumber <= 0) {
            result.put("isSuccess", Boolean.FALSE);
            result.put("message", "评论页数不能为负数。");
            return result;
        }

        if(skuId == null) {
            result.put("isSuccess", Boolean.FALSE);
            result.put("message", "skuId不能为空。");
            return result;
        }

        if(!"desc".equals(sort) && !"asc".equals(sort) ) {
            result.put("isSuccess", Boolean.FALSE);
            result.put("message", "排序参数有误，参考：desc表示降序，asc表示升序。");
            return result;
        }

        int count = prodAppraiseService.countProductAppraise(skuId);
        List<Map> appraiseList = prodAppraiseService.queryProductAppraise(isTimeSort, sort, skuId, pageNumber);
        result.put("recordList", appraiseList);
        result.put("isSuccess", Boolean.TRUE);
        result.put("totalRecords", count);
        result.put("totalPage",  (count  +  9) / 10);
        return result;
    }
}