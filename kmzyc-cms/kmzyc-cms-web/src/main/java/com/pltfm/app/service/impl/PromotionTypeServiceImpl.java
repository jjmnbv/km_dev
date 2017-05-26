package com.pltfm.app.service.impl;

import com.pltfm.app.dao.PromotionTypeDAO;
import com.pltfm.app.service.PromotionTypeService;
import com.pltfm.app.vobject.PromotionTypeExample;

import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.Resource;

/**
 * 活动信息类型业务逻辑接口
 *
 * @author cjm
 * @since 2013-9-4
 */
@Component(value = "promotionTypeService")
public class PromotionTypeServiceImpl implements PromotionTypeService {
    /**
     * 活动信息类型DAO接口
     */
    @Resource(name = "promotionTypeDAO")
    private PromotionTypeDAO promotionTypeDAO;

    /**
     * 按活动信息类型数据信息条件查询
     *
     * @param promotionTypeExample 活动信息类型条件
     * @throws Exception 异常
     * @return 返回值
     */
    @Override
    public List selectByExample(PromotionTypeExample promotionTypeExample)
            throws Exception {
        return promotionTypeDAO.selectByExample(promotionTypeExample);
    }

    public PromotionTypeDAO getPromotionTypeDAO() {
        return promotionTypeDAO;
    }

    public void setPromotionTypeDAO(PromotionTypeDAO promotionTypeDAO) {
        this.promotionTypeDAO = promotionTypeDAO;
    }

}
