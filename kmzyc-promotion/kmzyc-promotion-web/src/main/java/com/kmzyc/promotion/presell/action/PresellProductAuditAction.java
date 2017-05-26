package com.kmzyc.promotion.presell.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.cms.remote.service.DetailPublishService;
import com.kmzyc.promotion.app.action.BaseAction;
import com.kmzyc.promotion.app.vobject.Message;
import com.kmzyc.promotion.app.vobject.PromotionPresell;
import com.kmzyc.promotion.app.vobject.PromotionPresellCriteria;
import com.kmzyc.promotion.optimization.cache.PresellCache;
import com.kmzyc.promotion.presell.dao.PresellManagerDao;
import com.kmzyc.promotion.presell.service.PresellInfoService;
import com.kmzyc.promotion.presell.service.PresellProductAuditService;
import com.kmzyc.promotion.util.PresellCacheUtil;
import com.kmzyc.promotion.util.RedisTemplate;

@Controller("presellProductAuditAction")
@Scope(value = "prototype")
public class PresellProductAuditAction extends BaseAction {

    /**
     * 预售审核Action
     */
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(PresellProductAuditAction.class);

    @Resource
    private PresellProductAuditService presellProductAuditService;

    private PromotionPresellCriteria promotionPresellCriteria;
    @Resource
    private PresellCache presellCache;
    @Resource
    private PresellCacheUtil presellCacheUtil;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private DetailPublishService detailPublishService;
    @Resource
    private PresellManagerDao presellManagerDao;
    private Message message = new Message();
    @Resource
    private PresellInfoService presellInfoService;

    /**
     * 查询预售审核列表
     */
    public String queryAuditPresellList() {
        page = this.getPage();
        String flag = this.getRequest().getParameter("flag");
        if ("0".equals(flag)) {
            promotionPresellCriteria = new PromotionPresellCriteria();
            page.setPageNo(0);
            page.setPageSize(10);
        }
        /*
         * 分页处理
         */
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;
        int pageSize = page.getPageSize();
        int startIndex = (pageIndex - 1) * pageSize;
        int endIndex = pageSize * pageIndex;
        promotionPresellCriteria.setStartIndex(startIndex < 0 ? 0 : startIndex);
        promotionPresellCriteria.setEndIndex(endIndex < 0 ? 10 : endIndex);

        try {
            // 查询预售信息总数
            Integer count =
                    presellProductAuditService.queryAuditPresellCount(promotionPresellCriteria);
            page.setRecordCount(count);
            if (count > 0) {
                // 查询预售信息
                List<PromotionPresell> promotionPresellList =
                        presellProductAuditService.queryAuditPresellList(promotionPresellCriteria);
                this.getRequest().setAttribute("dataList", promotionPresellList);
                if (promotionPresellList.size() > 0) {
                    // 查询预售商品信息
                    this.getRequest().setAttribute("detailList", presellProductAuditService
                            .queryProductDetailList(promotionPresellList));
                }
            }

        } catch (Exception e) {
            logger.error("查询预售信息失败", e);
        }
        return "success";
    }

    /**
     * 查询预售审核详细信息
     */
    public String getAuditPresellDetail() {
        try {
            promotionPresellCriteria = new PromotionPresellCriteria();
            String presellId = this.getRequest().getParameter("presellId");
            this.getRequest().setAttribute("status", this.getRequest().getParameter("status"));
            if (presellId != null) {
                promotionPresellCriteria.setPresellId(Long.valueOf(presellId));
            }
            // 查询预售信息
            List<PromotionPresell> promotionPresellList =
                    presellProductAuditService.queryAuditPresellList(promotionPresellCriteria);
            if (promotionPresellList.size() > 0) {
                this.getRequest().setAttribute("info", promotionPresellList.get(0));
                // 查询商品详情信息
                this.getRequest().setAttribute("detailList",
                        presellProductAuditService.queryProductDetailList(promotionPresellList));
            }
        } catch (Exception e) {
            logger.error("查询预售信息失败", e);
        }
        return "success";
    }

    /**
     * 更新预售信息状态、设置缓存、发送消息
     */
    public String UpdateAuditPresell() {
        String presellId = this.getRequest().getParameter("presellId");
        String auditStatus = this.getRequest().getParameter("auditStatus");
        try {
            if (redisTemplate.tryLock(presellId)) {
                logger.info("presellId:{} 正在修改预售信息", presellId);
                promotionPresellCriteria = new PromotionPresellCriteria();
                if (presellId != null && auditStatus != null) {
                    // 设置审核状态
                    promotionPresellCriteria.setPresellId(Long.valueOf(presellId));
                    List<Long> productList = new ArrayList<Long>();
                    Map<String, Map<String, Object>> map =
                            new HashMap<String, Map<String, Object>>();
                    if (Integer.valueOf(auditStatus) == 1) {
                        // 查询预售信息
                        List<PromotionPresell> promotionPresellList = presellProductAuditService
                                .queryAuditPresellList(promotionPresellCriteria);
                        List<PromotionPresell> presellList = null;
                        if (promotionPresellList.size() > 0) {
                            // 查询预售产品信息
                            presellList = presellProductAuditService
                                    .queryProductDetailList(promotionPresellList);
                            PromotionPresell promotionPresell = promotionPresellList.get(0);
                            // 设置变量
                            for (PromotionPresell presell : presellList) {
                                Map<String, Object> presellMap = new HashMap<String, Object>();
                                productList.add(Long.valueOf(presell.getProductSkuId()));
                                presellMap.put("price", presell.getPresellPrice().doubleValue());
                                presellMap.put("dataType", 2);
                                map.put(presell.getProductSkuId(), presellMap);
                            }
                            // 查询该商品是否存在预售活动
                            int i = presellInfoService
                                    .selectPresellAndActivityProductCount(productList);
                            if (i > 0) {
                                message.setCode(2);
                                message.setModule("预售活动已经存在！");
                                return SUCCESS;
                            }
                            promotionPresellCriteria.setAuditStatus(Integer.valueOf(auditStatus));
                            promotionPresellCriteria
                                    .setModifUser(Long.valueOf(this.getLoginUserId()));
                            // 更新预售状态
                            int status = presellProductAuditService
                                    .updateAuditPresell(promotionPresellCriteria);
                            if (status > 0) {
                                // 设置缓存信息
                                if (presellCache.setPresellCache(promotionPresell, presellList)) {
                                    logger.info("设置预售商品缓存成功！presellId:{}", presellId);
                                } else {
                                    logger.info("设置预售商品缓存异常！presellId:{}", presellId);
                                    message.setCode(1);
                                    message.setModule("审核异常！");
                                    return SUCCESS;
                                }
                                logger.info(
                                        "调用cms接口：detailPublishService.remotePublishYsPage--start");
                                if (detailPublishService.remotePublishYsPage(productList)) {
                                    logger.info(
                                            "调用cms接口：detailPublishService.remotePublishYsPage成功！presellId:{}",
                                            presellId);
                                    // 发送预售产品价格给搜索系统
                                    presellCache.sendPresellInfo(map);
                                    // 发送延迟消息mq用于下架商品
                                    // presellCache.sendPresellInfoMq(productList,
                                    // promotionPresell);
                                } else {
                                    logger.info(
                                            "调用cms接口：detailPublishService.remotePublishYsPage失败");
                                    message.setCode(1);
                                    message.setModule("审核异常！");
                                    return SUCCESS;
                                }
                                logger.info(
                                        "调用cms接口：detailPublishService.remotePublishYsPage--end");
                            }
                        }
                    } else if (Integer.valueOf(auditStatus) == 2) {
                        promotionPresellCriteria.setAuditStatus(Integer.valueOf(auditStatus));
                        promotionPresellCriteria.setModifUser(Long.valueOf(this.getLoginUserId()));
                        // 更新预售状态
                        if (presellProductAuditService
                                .updateAuditPresell(promotionPresellCriteria) > 0) {
                            logger.info("审核拒绝成功！presellId:{}", presellId);
                        } else {
                            logger.info("审核拒绝失败,presellId:{}", presellId);
                            message.setCode(1);
                            message.setModule("审核异常！");
                            return SUCCESS;
                        }

                    }
                } else {
                    logger.info("修改预售库存presellId:{} 信息加锁失败", presellId);
                }
            }
        } catch (Exception e) {
            try {
                if (presellId != null && auditStatus != null) {
                    promotionPresellCriteria.setAuditStatus(0);
                    // 异常还原审核状态为待审核
                    Integer status =
                            presellProductAuditService.updateAuditPresell(promotionPresellCriteria);
                    if (status < 0) {
                        logger.info("更新预售信息失败,状态还原为待审核。presellId:{}", presellId);
                    }
                }
            } catch (Exception e1) {
                logger.error("还原预售信息失败", e1);
                message.setCode(1);
                message.setModule("审核异常！");
            }
            logger.error("更新预售信息失败presellId:{}", e, presellId);
            message.setCode(1);
            message.setModule("审核异常！");
            return SUCCESS;
        } finally {
            redisTemplate.release(presellId);
        }
        message.setCode(0);
        message.setModule("审核成功！");
        return SUCCESS;
    }

    public PromotionPresellCriteria getPromotionPresellCriteria() {
        return promotionPresellCriteria;
    }

    public void setPromotionPresellCriteria(PromotionPresellCriteria promotionPresellCriteria) {
        this.promotionPresellCriteria = promotionPresellCriteria;
    }


    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }


}
