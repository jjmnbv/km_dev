package com.kmzyc.cms.remote.service;

import java.util.List;
/**
 * 产品标签远程接口
 * @author cjm
 * @since 2014-1-10
 */
public interface ViewProductInfoRemoteService {
	/**
	 * 根据产品Sku查询出产品标签集合
	 * @param productSkuId 产品SkuId
	 * @return List<String> 产品挂角标签
	 */
	public List<String> queryProductTags(Integer productSkuId);
}
