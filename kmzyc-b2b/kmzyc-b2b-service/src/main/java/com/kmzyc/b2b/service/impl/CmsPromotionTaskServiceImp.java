package com.kmzyc.b2b.service.impl;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.dao.CmsPromotionTaskDao;
import com.kmzyc.b2b.service.CmsPromotionTaskService;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

@Component("cmsPromotionTaskService")
public class CmsPromotionTaskServiceImp implements CmsPromotionTaskService {
    @Resource(name = "cmsPromotionTaskDao")
    private CmsPromotionTaskDao cmsPromotionTaskDao;

    public Pagination queryForPage(Pagination page) throws SQLException {

        page = cmsPromotionTaskDao
                .findByPage("CmsPromotionTask.queryByPage", "CmsPromotionTask.countByPage", page);
        return page;
    }

    /**
     * 过期活动
     */
    public List queryExpirePromotion() throws SQLException {

        return cmsPromotionTaskDao.queryExpirePromotion();
    }
}
