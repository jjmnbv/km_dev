package com.kmzyc.promotion.presell.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.cms.remote.service.DetailPublishService;
import com.kmzyc.promotion.app.service.ProductPromotionMQService;
import com.kmzyc.promotion.app.vobject.ProductSku;
import com.kmzyc.promotion.app.vobject.PromotionPresell;
import com.kmzyc.promotion.app.vobject.PromotionPresellCriteria;
import com.kmzyc.promotion.optimization.cache.PresellCache;
import com.kmzyc.promotion.presell.dao.PresellManagerDao;
import com.kmzyc.promotion.presell.service.PresellManagerService;


@Repository("presellManagerService")
public class PresellManagerServiveImpl implements PresellManagerService {
    private Logger logger = LoggerFactory.getLogger(PresellManagerServiveImpl.class);

    @Resource
    private PresellManagerDao presellManagerDao;

    @Resource
    private PresellCache presellCache;

    @Resource
    private DetailPublishService detailPublishService;

    @Resource
    private ProductPromotionMQService productPromotionMQService;


    @Override
    public List<PromotionPresell> queryPresellManagerList(
            PromotionPresellCriteria promotionPresellCriteria) throws Exception {
        return presellManagerDao.queryPresellManagerList(promotionPresellCriteria);
    }

    @Override
    public Integer queryPresellManagerCount(PromotionPresellCriteria promotionPresellCriteria)
            throws Exception {
        return presellManagerDao.queryPresellManagerCount(promotionPresellCriteria);
    }

    @Override
    public List<PromotionPresell> queryProductDetailList(List<PromotionPresell> list)
            throws Exception {
        return presellManagerDao.queryProductDetailList(list);

    }

    @Override
    public void submitPresellApp(PromotionPresellCriteria promotionPresellCriteria)
            throws Exception {
        PromotionPresell promotionPresell = presellManagerDao
                .queryPresellInfoByPresellId(promotionPresellCriteria.getPresellId());
        int presellStatus = promotionPresell.getPresellStatus();
        if (presellStatus != 0) {
            logger.error("提审预售活动submitPresellApp，预售状态不合法！presellId："
                    + promotionPresellCriteria.getPresellId() + "  presellStatus:" + presellStatus);
            throw new RuntimeException();
        }
        presellManagerDao.submitPresellApp(promotionPresellCriteria);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePresellInfo(Long presellId) throws Exception {
        PromotionPresell promotionPresell =
                presellManagerDao.queryPresellInfoByPresellId(presellId);
        int presellStatus = promotionPresell.getPresellStatus();
        if (presellStatus != 0) {
            logger.error("删除预售活动deletePresellInfo，预售状态不合法！presellId：" + presellId
                    + "  presellStatus:" + presellStatus);
            throw new RuntimeException();
        }
        presellManagerDao.deletePresellProduct(presellId);// 删除预售活动商品表
        presellManagerDao.deletePresellInfo(presellId);// 删除预售活动主表
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void stopPresell(PromotionPresellCriteria promotionPresellCriteria) throws Exception {
        Long presellId = promotionPresellCriteria.getPresellId();// 活动id
        if (presellId == null) {
            logger.error("终止预售活动失败，预售ID不存在：" + presellId);
            return;
        }

        PromotionPresell promotionPresell =
                presellManagerDao.queryPresellInfoByPresellId(presellId);

        if (promotionPresell == null) {
            logger.error("终止预售活动失败，预售活动不存在，ID：" + presellId);
            return;
        }

        Date depositEndTime = promotionPresell.getDepositEndTime();
        Date nowDateTime = new Date();
        int auditStatus = promotionPresell.getAuditStatus();
        if (auditStatus != 1) {
            logger.error("终止预售活动stopPresell，审核状态不合法！presellId：" + presellId + "  auditStatus:"
                    + auditStatus);
            throw new RuntimeException();
        }
        if (null != depositEndTime && nowDateTime.before(depositEndTime)) {// 当前时间早于定金支付截止时间
            // 修改数据表状态为终止状态（3）
            presellManagerDao.stopPresell(promotionPresellCriteria);
            List<Long> listSkuids = presellManagerDao.querySkuidsByPresellId(presellId);
            if (null != presellId) {
                presellCache.delPresellPresellIdCache(presellId);// 删除缓存（预售活动基本信息）
            }
            if (null != listSkuids) {
                presellCache.delPresellSkuidsCache(presellId, listSkuids);// 删除缓存（预售商品对应的活动限购信息、预售商品对应的所有预售活动信息）

                // 推送普通商品价格消息
                sendNormalProductsPrice(listSkuids);

                // 调用cms接口推送详情页
                if (detailPublishService.remotePublishNormalPage(listSkuids)) {
                    logger.info(
                            "调用cms接口：detailPublishService.remotePublishNormalPage成功" + listSkuids);
                } else {
                    logger.info(
                            "调用cms接口：detailPublishService.remotePublishNormalPage失败" + listSkuids);
                }
            }
        } else {
            logger.error("终止预售活动stopPresell，当前时间晚于定金支付截止时间！presellId：" + presellId);
            throw new RuntimeException();
        }

    }

    /**
     * 根据skuidList， 筛选其中普通商品并推送价格消息
     * 
     * @param List<Integer> listSkuids,skuid列表
     * @throws Exception
     */
    @Override
    public void sendNormalProductsPrice(List<Long> listSkuids) throws Exception {
        List<Long> listSkuidsOutofSell = new ArrayList<Long>();// 处于下架的商品
        for (int i = 0; i < listSkuids.size(); i++) {// 遍历skuids
            String productStatus = presellManagerDao.queryProductStatusBySkuid(listSkuids.get(i));// 商品状态
            if (null != productStatus && productStatus.equals("4")) {// 下架，则排除此skuid
                listSkuidsOutofSell.add(listSkuids.get(i));
                listSkuids.remove(i);
            }
        }

        // 推送价格（上架商品）
        if (listSkuids.size() > 0) {// 排除完毕后，剩下的skuid则为上架商品
            List<ProductSku> listProductSku = new ArrayList<ProductSku>();
            for (int i = 0; i < listSkuids.size(); i++) {
                ProductSku productsku = new ProductSku();
                Double productPrice = presellManagerDao.queryProductPriceBySkuid(listSkuids.get(i));
                productsku.setProductSkuId(listSkuids.get(i));
                productsku.setPrice(productPrice);
                listProductSku.add(productsku);
            }
            productPromotionMQService.promotionOnlineIndexNotity(listProductSku, "2001");
        }

    }

    @Override
    public void cancelPresellApply(Long presellId) throws Exception {
        PromotionPresell promotionPresell =
                presellManagerDao.queryPresellInfoByPresellId(presellId);
        int presellStatus = promotionPresell.getPresellStatus();
        int auditStatus = promotionPresell.getAuditStatus();
        if (presellStatus != 1 || auditStatus != 0) {
            logger.error("撤销预售活动cancelPresellApply，预售状态不合法！presellId：" + presellId
                    + "  presellStatus:" + presellStatus + "  auditStatus:" + auditStatus);
            throw new RuntimeException();
        }
        presellManagerDao.cancelPresellApply(presellId);
    }
}
