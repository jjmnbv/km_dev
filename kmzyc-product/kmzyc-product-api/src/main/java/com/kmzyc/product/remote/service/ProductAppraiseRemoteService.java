package com.kmzyc.product.remote.service;

import java.util.List;
import java.util.Map;

import com.pltfm.app.vobject.AppraiseAddtoContent;
import com.pltfm.app.vobject.AppraiseReply;
import com.pltfm.app.vobject.ProdAppraise;

/**
 * 商品评价接口
 * @author tanyunxing
 *
 */
public interface ProductAppraiseRemoteService {

	/**
	 * 添加商品评价信息
	 * @param appraise
	 * @throws Exception
	 */
	void insertAppraise(ProdAppraise appraise) throws Exception;
	
	
	/**
	 * 更新商品评价信息
	 * @param appraise
	 * @throws Exception
	 */
	void updateAppraise(ProdAppraise appraise) throws Exception;
	
	/**
	 * 根据SKUID查询一个产品评价LIST
	 * @param skuId
	 * @return
	 * @throws Exception
	 */
	List<ProdAppraise> findAppraiseBySkuId(Long skuId) throws Exception ;
	
	/**
	 * 保存评价回复表
	 * @param reply
	 * @throws Exception
	 */
	void saveAppraiseReply(AppraiseReply reply) throws Exception;
	
	/**
	 * 添加追加评价
	 * @param content
	 * @throws Exception
	 */
	boolean saveAddtoContent(AppraiseAddtoContent content) throws Exception;
	
	/**
	 * 更新追加评价
	 * @param content
	 * @throws Exception
	 */
	void updateAddtoContent(AppraiseAddtoContent content) throws Exception;

    /**
     * 查询商品评论
     * <note>
     *     默认查询每页10个
     * </note>
     * @param isTimeSort    true时间排序/false评分排序
     * @param sort          desc降序/asc升序
     * @param skuId         sku的id
     * @param pageNumber    评论页数
     *
     * @return 返回一个map，其中key的含义如下
     * <note>
     *     isSuccess    true成功/false失败  boolean
     *     message      当失败时的错误信息   String类型
     *     recordList   评论记录            List类型
     *     totalRecords 总评论数量          int
     *     totalPage    总页数              int
     * </note>
     * @throws Exception
     */
    Map<String, Object> queryProductAppraise(Boolean isTimeSort, String sort, Long skuId, int pageNumber) throws Exception;
}