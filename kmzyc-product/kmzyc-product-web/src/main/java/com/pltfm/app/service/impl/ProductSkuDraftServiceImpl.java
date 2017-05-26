package com.pltfm.app.service.impl;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.ProductSkuDraftDAO;
import com.pltfm.app.enums.DraftType;
import com.pltfm.app.enums.IsValidStatus;
import com.pltfm.app.enums.ProductStatus;
import com.pltfm.app.service.ProductDraftService;
import com.pltfm.app.service.ProductImageDraftService;
import com.pltfm.app.service.ProductPriceRecordService;
import com.pltfm.app.service.ProductService;
import com.pltfm.app.service.ProductSkuAttrDraftService;
import com.pltfm.app.service.ProductSkuAttrService;
import com.pltfm.app.service.ProductSkuDraftService;
import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.ProductDraft;
import com.pltfm.app.vobject.ProductImageDraft;
import com.pltfm.app.vobject.ProductPriceRecord;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductSkuAttr;
import com.pltfm.app.vobject.ProductSkuDraft;
import com.pltfm.app.vobject.ProductSkuDraftExample;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("productSkuDraftService")
public class ProductSkuDraftServiceImpl implements ProductSkuDraftService {

	@Resource
	private ProductSkuDraftDAO productSkuDraftDao;

	@Resource
	private ProductImageDraftService productImageDraftService;

	@Resource
	private ProductSkuAttrDraftService productSkuAttrDraftService;

	@Resource
	private ProductSkuAttrService productSkuAttrService;

	@Resource
	private ProductService productService;

	@Resource
	private ProductDraftService productDraftService;

	@Resource
	private ProductPriceRecordService productPriceRecordService;

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor=ServiceException.class)
    public void addProductSkuDraft(ProductSkuDraft productSkuDraft) throws ServiceException {
        try {
            productSkuDraftDao.insert(productSkuDraft);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor=ServiceException.class)
	public void updateBatchByPrimaryKey(Long productId,List<ProductSkuDraft> list) throws ServiceException {
		try {
			productSkuDraftDao.updatePriceBatch(list);

			ProductDraft pd = new ProductDraft();
			pd.setProductId(productId);
			pd.setPriceReasons("");
			pd.setPriceStatus(ProductStatus.DRAFT.getStatus());
			productDraftService.updateObject(pd);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void searchPage(Page page, ProductSkuDraft record) throws ServiceException {
		int pageIndex = page.getPageNo();
		if (pageIndex == 0)
			pageIndex = 1;
		int pageSize = page.getPageSize();
		int min = (pageIndex - 1) * pageSize;
		int max = pageIndex * pageSize;
		record.setMin(min);
		record.setMax(max);

        try {
            page.setDataList(productSkuDraftDao.selectSomeSKUS(record));
            page.setRecordCount(productSkuDraftDao.selectSomeSKUSCounts(record));
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public void searchPageByUser(Page page, ProductSkuDraft record) throws ServiceException {
		int pageIndex = page.getPageNo();
		if (pageIndex == 0)
			pageIndex = 1;
		int pageSize = page.getPageSize();
		int min = (pageIndex - 1) * pageSize;
		int max = pageIndex * pageSize;
		record.setMin(min);
		record.setMax(max);

        try {
            List<ProductSkuDraft> list = productSkuDraftDao.selectSomeSKUSByUser(record);
            for(ProductSkuDraft psd : list){
                psd.setProductName(psd.getProductTitle().replaceAll(" ", "&nbsp;"));
            }

            page.setDataList(list);
            page.setRecordCount(productSkuDraftDao.selectSomeSKUSCountsByUser(record));
        } catch (SQLException e) {
            throw new ServiceException(e);
        }

    }
	
	@Override
	public ProductSkuDraft findSingleProductSku(Long productSkuId) throws ServiceException {
        try {
            return productSkuDraftDao.findSingleSkuAndAttrValue(productSkuId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
	
	@Override
	public ProductSkuDraft findSingleProductSkuForIntroduce(Long productSkuId) throws ServiceException {
        try {
            return productSkuDraftDao.findSingleSkuAndAttrValueForIntroduce(productSkuId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	public List<ProductSkuDraft> findValidProductSkus(Long productId) throws ServiceException {
		ProductSkuDraftExample exa = new ProductSkuDraftExample();
		exa.createCriteria().andProductIdEqualTo(productId)
                .andStatusEqualTo(IsValidStatus.VALID.getStatus());
        try {
            return productSkuDraftDao.selectByExample(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
	
	@Override
	public List<ProductSkuDraft> findProductSkuByProductId(Long productId) throws ServiceException {
		ProductSkuDraftExample exa = new ProductSkuDraftExample();
		exa.createCriteria().andProductIdEqualTo(productId);

        try {
            return productSkuDraftDao.selectByExample(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
	
	@Override
	public List<ProductSku> findProductSkuIntoOfficialByProductId(Long productId)
            throws ServiceException {
		ProductSkuDraftExample exa = new ProductSkuDraftExample();
		exa.createCriteria().andProductIdEqualTo(productId);

        try {
            return productSkuDraftDao.selectByExampleIntoOfficial(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor=ServiceException.class)
	public void batchInsertOfficial(List<ProductSkuDraft> list, Long loginId, Long productId)
            throws ServiceException {
		try {
			if(list!=null&&list.size()>0){
				int result = productSkuDraftDao.batchInsertOfficial(list);
				if(result == 0){
					throw new ServiceException("批量插入ProductSku正式表失败。为空。");
				}
				//进行价格变动记录
                List<ProductSkuDraft> validSkuList = findValidProductSkus(productId);
                insertProductPriceRecord(validSkuList, loginId);
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor=ServiceException.class)
	public void deleteDraftByProductId(Long productId) throws ServiceException {
		ProductSkuDraftExample exa = new ProductSkuDraftExample();
		exa.createCriteria().andProductIdEqualTo(productId);

        try {
            productSkuDraftDao.deleteByExample(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public void deleteProductSku(Long productSkuId) throws ServiceException {
		try {
			List<ProductImageDraft> imageList = productImageDraftService.findAllBySkuId(productSkuId);
			productImageDraftService.batchDeleteDraftWithImagePath(imageList);

			productSkuAttrDraftService.deleteProductSkuAttr(productSkuId);
			productSkuDraftDao.deleteByPrimaryKey(productSkuId);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public void batchInsertDraft(List<ProductSkuDraft> list) throws ServiceException {
		try {
			int result = productSkuDraftDao.batchInsertDraftFromOfficialWithOutSeq(list);
			if(result == 0){
				throw new ServiceException("批量插入ProductSku草稿为空。失败。");
			}
		} catch (SQLException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public int batchUpdateOfficialFromDraft(List<ProductSkuDraft> list,Long loginId)
            throws ServiceException {
		int re = 0;
		try {
			int result = productSkuDraftDao.batchUpdateOfficialFromDraft(list);
			if(result == 0 ){
				throw new ServiceException("产品SKU草稿更新至SKU正式表出错!");
			}
			this.insertProductPriceRecord(list, loginId);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return re;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public void updateSingleSkuPrice(Long productId, List<ProductSkuDraft> list)
            throws ServiceException {
		try {
			Product pd = productService.findProductById(productId);
			ProductDraft pdt = new ProductDraft();
			pdt.coptyPropertisFromProduct(pd);
			pdt.setOpType(DraftType.ALONEPRICE.getStatus());
			pdt.setStatus(pd.getStatus());
			pdt.setPriceStatus(ProductStatus.DRAFT.getStatus());
			
			//查找是否已存在记录，如果存在则更新
			if(productDraftService.findByProductIdWithOutClob(productId)!=null){
				productDraftService.updateObject(pdt);
				this.deleteDraftByProductId(productId);
				for(ProductSkuDraft sku : list){
					productSkuAttrDraftService.deleteProductSkuAttr(sku.getProductSkuId());
				}
			}else{
				productDraftService.insertProductDraftWithOutSeq(pdt);
			}
			
			int result = productSkuDraftDao.batchInsertDraftWithOutSeq(list);
			if(result == 0){
				throw new ServiceException("");
			}
			
			ProductSkuAttr attr = null;
			for(ProductSkuDraft sku : list){
				attr =  new ProductSkuAttr();
				attr.setProductSkuId(sku.getProductSkuId());
				List<ProductSkuAttr> skuAttrList = productSkuAttrService.queryProductSkuAttrList(attr);
				productSkuAttrDraftService.batchInsertDraft(skuAttrList);
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public void auditProductPrice(Long[] productIdChk, String auditStatus, String reasonText)
            throws ServiceException {
		ProductDraft psd = null;
		List<ProductDraft> list = new ArrayList<ProductDraft>();
		
		try {
			for(Long productId : productIdChk){
				psd = new ProductDraft();
				psd.setProductId(productId);
				psd.setPriceStatus(auditStatus);
				psd.setPriceReasons(reasonText);
				list.add(psd);
			}
			productDraftService.batchUpdateObject(list);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor=ServiceException.class)
	public void batchInsertDraftFromOfficial(List<ProductSku> list) throws ServiceException {
		try {
			if (list != null && list.size() > 0) {
				int result = productSkuDraftDao.batchInsertDraftFromOfficial(list);
				if(result == 0){
					throw new ServiceException("正式sku插入到草稿表失败。为空。");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 向产品价格记录表插入数据
	 * @param list
	 * @param loginId
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	private void insertProductPriceRecord(List<ProductSkuDraft> list,Long loginId){
		try {
            if (CollectionUtils.isEmpty(list)) {
                return;
            }

            List<ProductPriceRecord> recordList = list.stream()
                    .filter(psd -> psd.getPrice() != null && psd.getMarkPrice() != null && psd.getUnitWeight() != null)
                    .map(psd -> {
                        ProductPriceRecord record = new ProductPriceRecord();
                        record.setCreateTime(new Date());
                        record.setCreateUser(loginId);
                        record.setPrice(psd.getPrice());
                        record.setProductId(psd.getProductId());
                        record.setProductSkuId(psd.getProductSkuId());
                        record.setMarkPrice(psd.getMarkPrice());
                        record.setUnitWeight(psd.getUnitWeight());
                        record.setPvValue(psd.getPvValue());
                        return record;
                    }).collect(Collectors.toList());
			productPriceRecordService.batchInsertRecord(recordList);
		} catch (ServiceException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor=ServiceException.class)
	public int updateSkuIntroduce(ProductSkuDraft productSkuDraft) throws ServiceException {
        try {
            return productSkuDraftDao.updateByPrimaryKeySelective(productSkuDraft);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public void batchUpdateProductSkuDraft(List<ProductSkuDraft> list) throws ServiceException {
		try {
			if (list != null && list.size() > 0) {
                //批量修改sku列表的状态
				productSkuDraftDao.batchUpdateByPrimaryKeySelective(list);

                //批处理更新sku属性的状态
                productSkuAttrDraftService.batchUpdateSkuAttrStatus(list);
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean checkSkuPriceAndWeight(List<Long> productIds) throws ServiceException {
        try {
            return productSkuDraftDao.checkSkuPriceAndWeight(productIds);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor=ServiceException.class)
	public int batchUpdatePriceOnlyOfficialFromDraft(List<ProductSkuDraft> list, Long loginId)
            throws ServiceException {
		int result = 0;
		try {
			result = productSkuDraftDao.batchUpdatePriceOnlyOfficialFromDraft(list);
			if(result == 0 ){
				throw new ServiceException("产品SKU草稿更新至SKU正式表出错!");
			}
			insertProductPriceRecord(list, loginId);
            return result;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<ProductSkuDraft> findSameSkuBarCodeProductSku(Map<String,Object> map)
            throws ServiceException {
        try {
            return productSkuDraftDao.findSameSkuBarCodeProductSku(map);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}