package com.kmzyc.promotion.presell.dao;

import java.util.List;

import com.kmzyc.promotion.app.vobject.PromotionPresell;
import com.kmzyc.promotion.app.vobject.PromotionPresellCriteria;


public interface PresellManagerDao {
    /**
     * @查询预售管理列表信息
     * @param Map map
     * @throws Exception
     */
    public List<PromotionPresell> queryPresellManagerList(
            PromotionPresellCriteria promotionPresellCriteria) throws Exception;

    /**
     * @查询预售管理列表数量
     * @param PromotionPresellCriteria promotionPresellCriteria
     * @return int预售活动数量
     * @throws Exception
     */
    public Integer queryPresellManagerCount(PromotionPresellCriteria promotionPresellCriteria)
            throws Exception;

    /**
     * @查询预售信息
     * @param Long presellId
     * @throws Exception
     */
    public PromotionPresell queryPresellInfoByPresellId(long presellId) throws Exception;


    /**
     * @查询预售产品详情信息
     * @param List<PromotionPresell> list
     * @return 列表信息 List<PromotionPresell>
     * @throws Exception
     */
    public List<PromotionPresell> queryProductDetailList(List<PromotionPresell> list)
            throws Exception;


    /**
     * 预售活动提审
     * 
     * @param promotionPresellCriteria （预售活动对象）
     * @throws Exception
     */
    public void submitPresellApp(PromotionPresellCriteria promotionPresellCriteria)
            throws Exception;

    /**
     * 预售活动删除
     * 
     * @param presellId （预售活动id）
     * @throws Exception
     */
    public void deletePresellInfo(Long presellId) throws Exception;


    /**
     * 预售活动商品删除
     * 
     * @param presellId （预售活动id）
     * @throws Exception
     */
    public void deletePresellProduct(Long presellId) throws Exception;

    /**
     * 预售活动撤销审批
     * 
     * @param presellId （预售活动id）
     * @throws Exception
     */
    public void cancelPresellApply(Long presellId) throws Exception;


    /**
     * 预售活动终止
     * 
     * @param promotionPresellCriteria （预售活动对象）
     * @throws Exception
     */
    public int stopPresell(PromotionPresellCriteria promotionPresellCriteria) throws Exception;

    /**
     * 修改预售库存（已售数量），每次自增1
     * 
     * @param skuid
     * @throws Exception
     */
    public void addPreselledCount(Long skuid, Long presellId, int count) throws Exception;


    /**
     * 根据活动id，查询活动下的所有商品skuid
     * 
     * @param presellId （预售活动id）
     * @return list<int> skuid
     * @throws Exception
     */
    public List<Long> querySkuidsByPresellId(Long presellId) throws Exception;

    /**
     * 根据skuid，查询正在进行的促销活动数量
     * 
     * @param skuid （商品id）
     * @return int 进行中的活动数量
     * @throws Exception
     */
    public Integer queryPromotionCountBySkuid(Long skuid) throws Exception;

    /**
     * 根据skuid，查询商品价格
     * 
     * @param skuid （商品id）
     * @return Double 商品价格
     * @throws Exception
     */
    public Double queryProductPriceBySkuid(Long skuid) throws Exception;


    /**
     * 根据skuid，查询商品状态
     * 
     * @param skuid （商品id）
     * @return String 商品状态（产品状态（0:草稿、1:待审核、2:待上架、4:下架、5:系统下架、3:上架 6: 审核不通过, -1 :删除, -2违规下架））
     * @throws Exception
     */
    public String queryProductStatusBySkuid(Long skuid) throws Exception;

    /**
     * 查询需要自动结束的活动id
     * 
     * @param null
     * @return List<Long> presellIds
     * @throws Exception
     */
    public List<Long> queryPresellIdForAutoStop() throws Exception;

}
