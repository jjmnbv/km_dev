package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pltfm.app.dao.ProductPriceRecordDAO;
import com.pltfm.app.service.ProductPriceRecordService;
import com.pltfm.app.vobject.ProductPriceRecord;
import com.kmzyc.commons.exception.ServiceException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("productPriceRecordService")
public class ProductPriceRecordServiceImpl implements ProductPriceRecordService {

	@Resource
	private ProductPriceRecordDAO productPriceRecordDao;

    private Logger logger = LoggerFactory.getLogger(ProductPriceRecordServiceImpl.class);

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public void batchInsertRecord(List<ProductPriceRecord> list) throws ServiceException {
		try {
			if (list != null && list.size() > 0) {
				int result = productPriceRecordDao.batchInsertRecord(list);
				if(result == 0){
					throw new ServiceException("批量保存sku价格变动失败。");
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public void insertRecord(ProductPriceRecord record) throws ServiceException {
        try {
            productPriceRecordDao.insert(record);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public List<ProductPriceRecord> getRecordList(Long productId) throws ServiceException {
        try {
            return productPriceRecordDao.getRecordList(productId);
        } catch (SQLException e) {
            logger.error("根据商品id{}查询对应的sku的价格变动失败：{}.", new Object[]{productId, e});
            throw new ServiceException(e);
        }
    }

}