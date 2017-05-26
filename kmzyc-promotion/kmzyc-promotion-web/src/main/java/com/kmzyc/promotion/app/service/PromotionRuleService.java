package com.kmzyc.promotion.app.service;

import java.util.List;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.PromotionRule;
import com.kmzyc.promotion.app.vobject.PromotionRuleData;

public interface PromotionRuleService {
    public void saveOrUpdatePromotionRule(PromotionRule pr, PromotionRuleData promotionRuleData);

    /**
     * 分页查询规则列表
     */
    public Page selectPageByVo(Page page, PromotionRule record);

    /**
     * 检查添加或者修改的规则是否合法
     */
    public boolean checkPromotionRule(PromotionRule record);

    /**
     * 根据的规则ID获取规则信息
     */
    public PromotionRule getPromotionRuleById(Long promotionRuleId);

    /**
     * 根据的规则ID删除一条规则信息，此处为逻辑删除
     */
    public void deletePromotionRule(PromotionRule record);

    /**
     * 根据的规则ID列表删除规则信息，此处为逻辑删除
     */
    public String deletePromotionRule(List<PromotionRule> list);

    public Long addPromotionRule(PromotionRule pr);

    /** 多条规则数据保存 */
    public void savePromotionRule(PromotionRule pr, List<PromotionRuleData> promotionRuleDataList);

    public Long getIncreasePromotionRuleId();

}
