package com.pltfm.app.service;

import com.pltfm.app.vobject.PromotionTypeExample;

import java.util.List;

/**
 * 活动信息类型业务逻辑接口
 *
 * @author cjm
 * @since 2013-9-4
 */
public interface PromotionTypeService {
    /**
     * 按活动信息类型数据信息条件查询
     *
     * @param promotionTypeExample 活动信息类型条件
     * @throws Exception 异常
     * @return 返回值
     */
    List selectByExample(PromotionTypeExample promotionTypeExample) throws Exception;
}
