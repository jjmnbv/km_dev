package com.pltfm.app.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.remote.service.PromotionRemoteService;
import com.pltfm.app.dao.ViewPromotionDAO;
import com.pltfm.app.service.ViewPromotionService;
import com.pltfm.app.vobject.ViewPromotion;

/**
 * 活动信息视图DAO接口实现类
 *
 * @author cjm
 * @since 2013-9-3
 */
@Component(value = "viewPromotionService")
public class ViewPromotionServiceImpl implements ViewPromotionService {
    /**
     * 活动信息DAO接口
     */
    @Resource(name = "viewPromotionDAO")
    private ViewPromotionDAO viewPromotionDAO;

    @Resource
    private PromotionRemoteService promotionRemoteService;

    /**
     * 分页查询活动信息
     *
     * @param viewPromotion 活动信息实体
     * @param page          分页实体
     * @throws Exception 异常
     * @return 返回值
     */
    @Override
    public Page queryForPage(ViewPromotion viewPromotion, Page page)
            throws Exception {
        if (page == null) {
            page = new Page();
        }
        if (viewPromotion == null) {
            viewPromotion = new ViewPromotion();
        }
        // 根据条件获取窗口数据信息总条数
        int totalNum = viewPromotionDAO.countByViewPromotion(viewPromotion);
        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            viewPromotion.setStartIndex(page.getStartIndex());
            viewPromotion.setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(viewPromotionDAO.queryForPage(viewPromotion));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }

    /**
     * 分页查询活动信息(for添加活动商品)
     *
     * @param viewPromotion 活动信息实体
     * @param page          分页实体
     * @throws Exception 异常
     * @return 返回值
     */
    @Override
    public Page queryForPagePro(ViewPromotion viewPromotion, Page page)
            throws Exception {
        if (page == null) {
            page = new Page();
        }
        // 根据条件获取窗口数据信息总条数
        int totalNum = viewPromotionDAO.countByViewPromotionPro(viewPromotion);
        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            viewPromotion.setStartIndex(page.getStartIndex());
            viewPromotion.setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(viewPromotionDAO.queryForPagePro(viewPromotion));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }

    /**
     * 根据活动信息主键查询单条活动信息
     *
     * @param viewPromotionId 活动信息主键
     * @throws Exception sql异常
     * @return 返回值
     */
    @Override
    public ViewPromotion selectByPrimaryKey(Integer viewPromotionId)
            throws Exception {
        return viewPromotionDAO.selectByPrimaryKey(viewPromotionId);
    }

    @Override
    public Map getPromotionCategory() throws Exception {
        // TODO Auto-generated method stub
        //	PromotionRemoteService promotionRemoteService = (PromotionRemoteService) RemoteTool.getRemote(Constants.PROMOTION_SYSTEM_CODE,"promotionRemoteService");
        return promotionRemoteService.getPromotionTypeMap();
    }

    public ViewPromotionDAO getViewPromotionDAO() {
        return viewPromotionDAO;
    }

    public void setViewPromotionDAO(ViewPromotionDAO viewPromotionDAO) {
        this.viewPromotionDAO = viewPromotionDAO;
    }

    public PromotionRemoteService getPromotionRemoteService() {
        return promotionRemoteService;
    }

    public void setPromotionRemoteService(PromotionRemoteService promotionRemoteService) {
        this.promotionRemoteService = promotionRemoteService;
    }


}
