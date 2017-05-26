package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pltfm.app.dao.AppraisePropDAO;
import com.pltfm.app.dao.AppraisePropValDAO;
import com.pltfm.app.dao.AppraiseRelaPropDAO;
import com.pltfm.app.service.AppraisePropService;
import com.pltfm.app.vobject.AppraiseProp;
import com.pltfm.app.vobject.AppraisePropExample;
import com.pltfm.app.vobject.AppraisePropExample.Criteria;
import com.pltfm.app.vobject.AppraisePropVal;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;

/**
 * @author tanyunxing
 */
@Service("appraisePropService")
public class AppraisePropServiceImpl implements AppraisePropService {

    private Logger logger = LoggerFactory.getLogger(AppraisePropServiceImpl.class);

    @Resource
    private AppraisePropDAO appraisePropDao;

    @Resource
    private AppraisePropValDAO appraisePropValDao;

    @Resource
    private AppraiseRelaPropDAO appraiseRelaPropDao;

    @Override
    public void searchPage(Page page, AppraiseProp prop, List<Long> notPropIds) throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        AppraisePropExample exm = new AppraisePropExample();
        Criteria c = exm.createCriteria();
        if (StringUtils.isNotBlank(prop.getPropName())) {
            c.andPropNameLike("%" + prop.getPropName() + "%");
        }
        if (CollectionUtils.isNotEmpty(notPropIds)) {
            c.andAppraisePropIdNotIn(notPropIds);
        }
        exm.setOrderByClause(" APPRAISE_PROP_ID desc");

        try {
            int count = appraisePropDao.countByExample(exm);
            List<AppraiseProp> list = appraisePropDao.selectByExample(exm,skip, max);
            page.setDataList(list);
            page.setRecordCount(count);
        } catch (SQLException e) {
            logger.error("分页列表异常", e);
            throw new ServiceException(e);
        }

    }

    @Override
    public AppraiseProp findByPrimaryKey(Long propId) throws ServiceException {
        try {
            return appraisePropDao.selectByPrimaryKey(propId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updatePropAndValues(AppraiseProp prop) throws ServiceException {
        try {
            appraisePropValDao.updateByPrimaryKeySelectiveBatch(prop.getValList());
            appraisePropDao.updateByPrimaryKeySelective(prop);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void addPropAndValues(AppraiseProp prop) throws ServiceException {
        try {
            Long propId = appraisePropDao.insert(prop);
            for (AppraisePropVal v : prop.getValList()) {
                v.setAppraisePropId(propId);
            }
            appraisePropValDao.insertBatch(prop.getValList());
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void deletePropAndValues(Long[] propId) throws ServiceException {
        try {
            appraisePropValDao.deleteByExampleBatch(propId);
            appraiseRelaPropDao.deleteBatchByAppraisePropId(propId);
            appraisePropDao.deleteByExampleBatch(propId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public List<AppraiseProp> findBySomePropIds(List<Long> propIds) throws ServiceException {
        AppraisePropExample exm = null;
        if (propIds != null) {
            exm = new AppraisePropExample();
            exm.createCriteria().andAppraisePropIdIn(propIds);
        }
        try {
            return appraisePropDao.selectByExample(exm);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<AppraiseProp> selectByCategoryId(Long cateId) throws ServiceException {
        try {
            return appraisePropDao.selectByCategoryId(cateId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}