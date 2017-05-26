package com.kmzyc.product.remote.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.product.remote.service.ProductSkuRemoteService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pltfm.app.service.CategoryAttrService;
import com.pltfm.app.service.CategoryAttrValueService;
import com.pltfm.app.service.ProductImageDraftService;
import com.pltfm.app.service.ProductImageService;
import com.pltfm.app.service.ProductSkuAttrDraftService;
import com.pltfm.app.service.ProductSkuAttrService;
import com.pltfm.app.service.ProductSkuDraftService;
import com.pltfm.app.service.ProductSkuService;
import com.pltfm.app.service.ViewSkuAttrService;
import com.pltfm.app.vobject.CategoryAttr;
import com.pltfm.app.vobject.CategoryAttrValue;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductSkuAttrDraft;
import com.pltfm.app.vobject.ViewSkuAttr;

@Service("productSkuRemoteService")
public class ProductSkuRemoteServiceImpl implements ProductSkuRemoteService {

    private Logger logger = LoggerFactory.getLogger(ProductSkuRemoteServiceImpl.class);

    @Resource
	private ViewSkuAttrService viewSkuAttrService;
	
	@Resource
	private ProductSkuService productSkuService;
	
	@Resource
	private ProductImageService productImageService;
	
	@Resource
	private CategoryAttrService categoryAttrService;
	
	@Resource
	private CategoryAttrValueService categoryAttrValueService;
	
	@Resource
	private ProductSkuAttrDraftService productSkuAttrDraftService;
	
	@Resource
	private ProductSkuDraftService productSkuDraftService;
	
	@Resource
	private ProductImageDraftService productImageDraftService;
	
	@Resource
	private ProductSkuAttrService productSkuAttrService;

	@Override
	public Long findSkuIdByAttrAndValue(Long productId, List<Long> valueId) throws ServiceException {
		if (productId == null || valueId.size() == 0) {
			throw new ServiceException("请输入正确的参数！");
		}

		return viewSkuAttrService.findSkuIdByAttrAndValue(productId,  valueId);
	}

	@Override
	public List<ProductSku> findImagesByProductId(Long productId) throws ServiceException {
		if(productId == null){
			throw new ServiceException("请输入参数！");
		}
		ProductSku ps = new ProductSku();
		ps.setProductId(productId);
		List<ProductSku> skuList = productSkuService.queryProductSkuList(ps);
		
		for(ProductSku sku : skuList){
			List<ProductImage> images = productImageService.findAllBySkuId(sku.getProductSkuId());
			sku.setProductSkuImages(images);
			sku.setSkuAttrs(productSkuAttrService.querySkuAttrValueBySkuId(sku.getProductSkuId()));
		}
		return skuList;
	}
	
	@Override
	public List<ProductSku> findImagesDraftByProductId(Long productId) throws ServiceException {
		if(productId == null){
			throw new ServiceException("请输入参数！");
		}
		
		List<ProductSku> skuList = productSkuDraftService.findProductSkuIntoOfficialByProductId(productId);
		
		for(ProductSku sku : skuList){
			List<ProductImage> images = productImageDraftService.findAllIntoOfficialBySkuId(sku.getProductSkuId());
			sku.setProductSkuImages(images);
		}
		return skuList;
	}

	@Override
	public List<CategoryAttr> findSkuAttrByProductId(Long productId) throws ServiceException {
		if(productId == null){
			throw new ServiceException("请输入参数！");
		}
		List<ViewSkuAttr> skuAttr = viewSkuAttrService.findProductAttrAndValueByProductId(productId);
		
		Long categoryAttrId = Long.valueOf(0);
		CategoryAttr attr = null;
		CategoryAttrValue value = null;
		List<CategoryAttr> attrList = new ArrayList<CategoryAttr>();
		for(ViewSkuAttr vka : skuAttr){
			if(!categoryAttrId.equals(vka.getCategoryAttrId())){
				categoryAttrId = vka.getCategoryAttrId();
				attr = categoryAttrService.findByPrimaryKey(categoryAttrId);
				attr.setCategoryAttrValueList(new ArrayList<CategoryAttrValue>());
				attrList.add(attr);
			}
			value = new CategoryAttrValue();
			if(StringUtils.isEmpty(vka.getNewAttr()) && vka.getCategoryAttrValueId()!=null){
				value = categoryAttrValueService.queryCategoryAttrValue(vka.getCategoryAttrValueId());
			}else{
				value.setCategoryAttrValue(vka.getNewAttr());
				value.setCategoryAttrId(vka.getCategoryAttrId());
				value.setCategoryAttrValueId(vka.getSkuAttrValueId());
			}
			attr.getCategoryAttrValueList().add(value);
		}
		
		return attrList;
	}
	
	@Override
	public List<CategoryAttr> findSkuAttrDraftByProductId(Long productId) throws ServiceException {
		if(productId == null){
			throw new ServiceException("请输入参数！");
		}
		List<ProductSkuAttrDraft> skuAttr = productSkuAttrDraftService.findSkuAttrDraftByProductId(productId);
		
		Long categoryAttrId = Long.valueOf(0);
		CategoryAttr attr = null;
		CategoryAttrValue value = null;
		List<CategoryAttr> attrList = new ArrayList<CategoryAttr>();
		for(ProductSkuAttrDraft vka : skuAttr){
			if(!categoryAttrId.equals(vka.getCategoryAttrId())){
				categoryAttrId = vka.getCategoryAttrId();
				attr = categoryAttrService.findByPrimaryKey(categoryAttrId);
				attr.setCategoryAttrValueList(new ArrayList<CategoryAttrValue>());
				attrList.add(attr);
			}
			value = new CategoryAttrValue();
			if(StringUtils.isEmpty(vka.getNewAttr()) && vka.getCategoryAttrValueId() != null){
				value = categoryAttrValueService.queryCategoryAttrValue(vka.getCategoryAttrValueId());
			}else{
				value.setCategoryAttrValue(vka.getNewAttr());
				value.setCategoryAttrId(vka.getCategoryAttrId());
				value.setCategoryAttrValueId(vka.getCategoryAttrValueId());
			}
			attr.getCategoryAttrValueList().add(value);
		}
		
		return attrList;
	}

	@Override
	public List<ViewSkuAttr> findBySkuId(Long productSkuId) throws ServiceException {
		return viewSkuAttrService.findBySkuId(productSkuId);
	}

	@Override
	public List<ViewSkuAttr> findDraftBySkuId(Long productSkuId) throws ServiceException {
		return productSkuAttrDraftService.findSkuDraftInfoByProductSkuId(productSkuId);
	}

    @Override
    public void updateProductSkuCache(List<Long> productSkuIds) throws ServiceException {
        if (CollectionUtils.isEmpty(productSkuIds)) {
            logger.error("更新sku信息缓存失败，skuid为空");
            throw new ServiceException("请输入skuid。");
        }

        productSkuService.updateProductSkuCache(productSkuIds);
    }
}
