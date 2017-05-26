package com.kmzyc.product.remote.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.product.remote.service.ProductRelationRemoteService;
import com.pltfm.app.service.ProductRelationService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pltfm.app.service.ProductImageService;
import com.pltfm.app.service.ProductStockService;
import com.pltfm.app.vobject.ProductRelation;
import com.pltfm.app.vobject.ProductRelationDetail;
import com.pltfm.app.vobject.ProductRelationView;

@Component("productRelationRemoteService")
public class ProductRelationRemoteServiceImpl implements ProductRelationRemoteService {

    private Logger logger = LoggerFactory.getLogger(ProductRelationRemoteServiceImpl.class);

    @Resource
    private ProductStockService productStockService;

    @Resource
    private ProductRelationService productRelationService;

    @Resource
    private ProductImageService productImageService;

    @Override
    public List<ProductRelation> queryProductAndDetailPackageList(Long skuId) throws ServiceException {
        List<ProductRelation> result = new ArrayList<ProductRelation>();
        //1.参数校验
        if(skuId == null) {
            logger.error("获取优惠套餐组合失败，参数为空.");
            return result;
        }

        //2.根据skuId获取优惠套餐组合
        List<ProductRelation> list = productRelationService.getPackageListBySkuId(skuId);
        if(CollectionUtils.isEmpty(list)) {
            logger.error("根据skuId[{}]没有获取优惠套餐组合.", skuId);
            return result;
        }

        //3.处理优惠套餐
        for (ProductRelation relation : list) {
            List<ProductRelationDetail> detailList = relation.getProductRelationDetails();
            if (CollectionUtils.isEmpty(detailList)) {
                continue;
            }

            // 总的市场价
            BigDecimal totalPrice = relation.getSkuMarkPrice() != null
                    ? relation.getSkuMarkPrice()
                    : new BigDecimal("0.00");
            // 总的关联价格
            BigDecimal totalRelationPrice = relation.getMainSkuPrice() != null
                    ? relation.getMainSkuPrice()
                    : new BigDecimal("0.00");

            List<ProductRelationView> relationList = new ArrayList<ProductRelationView>();
            for (ProductRelationDetail detail : detailList) {
                ProductRelationView view = new ProductRelationView();
                Long relationSkuId = detail.getRelationSkuId();
                view.setSkuId(relationSkuId);

                // 如果对应的产品中的剩余数量小于0 或者等于0 都不能 显示这个套餐出来
                //无库存记录，都不能显示这个套餐出来
                Integer integer = productStockService.selectRemainQuantityBySkuId(relationSkuId);
                if (integer == null || integer <= 0) {
                    relation.setProductStatusIsValid(Boolean.FALSE);
                    break;
                }

                // 如果被关联的产品中有状态不为3，都不能消失出这个套餐出来
                // // if(detail.getProductStatus() != null|| detail.getProductStatus() != 3){
                // // productRelation.setProductStatusIsValid(false);
                //break;
                // // }

                // 如果被关联的产品中有产品无效 ，都不能显示出这个套餐出来
                if (detail.getSkuStatus() == null || detail.getSkuStatus() == 0) {
                    relation.setProductStatusIsValid(Boolean.FALSE);
                    break;
                }
                if (detail.getRelationSkuPrice() != null) {
                    view.setNewPrice(detail.getRelationSkuPrice());
                    totalRelationPrice = totalRelationPrice.add(detail.getRelationSkuPrice());
                }
                if (detail.getSkuPrice() != null) {
                    view.setOldPrice(detail.getSkuPrice());
                    totalPrice = totalPrice.add(detail.getSkuPrice());
                }
                if (detail.getSkuMarkPrice() != null) {
                    view.setMarketPrice(detail.getSkuMarkPrice());
                }
                view.setProductName(detail.getProductName());
                view.setProductTitle(detail.getProductTitle());
                view.setRelationDetailType(detail.getRelationDetailType());
                view.setImgList(productImageService.findAllBySkuId(relationSkuId));
                relationList.add(view);
            }

            // 都瞒足条件的productRelation 才放入到界面显示 放入套餐的主表信息
            if (relation.isProductStatusIsValid() && CollectionUtils.isNotEmpty(relationList)) {
                // 设置总的市场价
                relation.setTotalPrice(totalPrice);
                relation.setTotalRelationPrice(totalRelationPrice);
                relation.setRelationViewList(relationList);
                result.add(relation);
            }
        }
        return result;
    }

    @Override
    public List<ProductRelation> queryProductAndDetailRecommendList(Long skuId) throws ServiceException {
        List<ProductRelation> result = new ArrayList<ProductRelation>();
        //1.参数校验
        if(skuId == null) {
            logger.error("获取人气组合失败，参数为空.");
            return result;
        }

        //2.根据skuId获取人气组合
        List<ProductRelation> list = productRelationService.getRecommendListBySkuId(skuId);
        if(CollectionUtils.isEmpty(list)) {
            logger.error("根据skuId[{}]没有获取到人气组合.", skuId);
            return result;
        }

        //3.处理组合
        for (ProductRelation relation : list) {
            List<ProductRelationDetail> detailList = relation.getProductRelationDetails();
            if (CollectionUtils.isEmpty(detailList)) {
                continue;
            }

            // 总的市场价
            BigDecimal totalPrice = relation.getSkuMarkPrice() != null
                    ? relation.getSkuMarkPrice()
                    : new BigDecimal("0.00");
            // 总的关联价格
            BigDecimal totalRelationPrice = relation.getMainSkuPrice() != null
                    ? relation.getMainSkuPrice()
                    : new BigDecimal("0.00");

            List<ProductRelationView> relationList = new ArrayList<ProductRelationView>();
            for (ProductRelationDetail detail : detailList) {
                ProductRelationView view = new ProductRelationView();
                Long relationSkuId = detail.getRelationSkuId();
                view.setSkuId(relationSkuId);

                // 如果被关联的产品中有状态不为3，都不能消失出这个套餐出来
                if (detail.getProductStatus() == null || detail.getProductStatus() != 3l) {
                    view.setProductIsValid(Boolean.FALSE);
                    continue;
                }
                // 如果被关联的产品中有产品无效 ，都不能显示出这个套餐出来
                if (detail.getSkuStatus() == null || detail.getSkuStatus() == 0l) {
                    view.setProductIsValid(Boolean.FALSE);
                    continue;
                }

                // 没有库存记录，在人气关联中不显示这个关联产品
                // 产品的剩余数量小于或者等于零，在人气中关联中不显示出这个关联产品
                Integer integer = productStockService.selectRemainQuantityBySkuId(relationSkuId);
                if (integer == null || integer <= 0) {
                    view.setProductIsValid(Boolean.FALSE);
                    continue;
                }
                view.setNewPrice(detail.getRelationSkuPrice());
                view.setOldPrice(detail.getSkuPrice());
                view.setMarketPrice(detail.getSkuMarkPrice());
                view.setProductName(detail.getProductName());
                view.setProductTitle(detail.getProductTitle());
                view.setRelationDetailType(detail.getRelationDetailType());

                if (view.isProductIsValid()) {
                    if (detail.getSkuMarkPrice() != null) {
                        totalPrice = totalPrice.add(detail.getSkuMarkPrice());
                    }
                    if (detail.getRelationSkuPrice() != null) {
                        totalRelationPrice = totalRelationPrice.add(detail.getRelationSkuPrice());
                    }
                    // 如果符合条件的,才允许添加到关联系列中的产品显示
                    relationList.add(view);
                    view.setImgList(productImageService.findAllBySkuId(relationSkuId));
                }
            }

            // 设置总的市场价
            relation.setTotalPrice(totalPrice);
            relation.setTotalRelationPrice(totalRelationPrice);
            if (CollectionUtils.isNotEmpty(relationList)) {
                relation.setRelationViewList(relationList);
                result.add(relation);
            }
        }
        return result;
    }
}