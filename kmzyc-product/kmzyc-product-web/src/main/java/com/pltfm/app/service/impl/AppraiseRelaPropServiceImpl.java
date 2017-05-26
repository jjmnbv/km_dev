package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pltfm.app.dao.AppraiseRelaPropDAO;
import com.pltfm.app.service.AppraiseRelaPropService;
import com.pltfm.app.vobject.AppraiseRelaProp;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("appraiseRelaPropService")
public class AppraiseRelaPropServiceImpl implements AppraiseRelaPropService {

	@Resource
	private AppraiseRelaPropDAO appraiseRelaPropDao;

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public void saveBatch(String[] categoryIds,String[] appraisePropIds) throws ServiceException {
        try {
            appraiseRelaPropDao.deleteBatchByCagetoryId(categoryIds);
            List<AppraiseRelaProp> list = new ArrayList();
            AppraiseRelaProp arp = null;
            for(int i=0;i<categoryIds.length;i++){
                for(int j =0;j<appraisePropIds.length;j++){
                    arp = new AppraiseRelaProp();
                    arp.setCategoryId(Long.valueOf(categoryIds[i]));
                    arp.setAppraisePropId(Long.valueOf(appraisePropIds[j]));
                    list.add(arp);
                }
            }
            appraiseRelaPropDao.insertBatch(list);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public void unBindProp(String[] categoryIds) throws ServiceException {
        try {
            appraiseRelaPropDao.deleteBatchByCagetoryId(categoryIds);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
}
