package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.dao.SectionsDetailDAO;
import com.pltfm.app.service.SectionsDetailService;
import com.pltfm.app.vobject.SectionsDetailExample.Criteria;
import com.pltfm.app.vobject.SectionsDetail;
import com.pltfm.app.vobject.SectionsDetailExample;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;

/**
 * 栏目管理逻辑处理类
 *
 * @author hl
 * @since 2013-7-30
 */
@Service("sectionsDetailService")
public class SectionsDetailServiceImpl implements SectionsDetailService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SectionsDetailDAO sectionsDetailDao;

    public void searchPage(Page page, SectionsDetail sectionsDetail) throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0) pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        SectionsDetailExample sde = new SectionsDetailExample();
        Criteria criteria = sde.createCriteria();
        if (sectionsDetail.getSectionsId() != null) {
            criteria.andSectionsIdEqualTo(sectionsDetail.getSectionsId());
        }
        sde.setOrderByClause("SECTIONS_DETAIL_ID");

        try {
            int count = sectionsDetailDao.countByExample(sde);
            List<SectionsDetail> sectionsDetailList = sectionsDetailDao.selectByExample(sde, skip, max);
            page.setDataList(sectionsDetailList);
            page.setRecordCount(count);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }

    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void saveSectionsDetail(SectionsDetail sectionsdetail) throws ServiceException {
        try {
            sectionsDetailDao.insert(sectionsdetail);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public SectionsDetail querySectionsDetailById(String sectionsDetailId) throws ServiceException{
        try {
            return sectionsDetailDao.selectByPrimaryKey(Long.parseLong(sectionsDetailId));
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateSectionDetailByStno(String[] stno, String[] sectionDetailId) {
        SectionsDetail detail = new SectionsDetail();
        if (stno.length != sectionDetailId.length) {
            return;
        }

        try {
            for (int i = 0; i < stno.length; i++) {
                detail.setSectionsDetailId(Long.parseLong(sectionDetailId[i]));
                detail.setSortno((short) Integer.parseInt(stno[i]));
                sectionsDetailDao.updateByPrimaryKey(detail);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public ResultMessage batchInsertSectionsDetail(List<SectionsDetail> sectionsDetailList) throws ServiceException {
        ResultMessage message = new ResultMessage();
        message.setIsSuccess(true);
        try {
            int count = sectionsDetailDao.batchInsertSectionsDetail(sectionsDetailList);
            if (count < 1) {
                message.setIsSuccess(false);
                message.setMessage("栏目明细保存失败!");
                return message;
            }
        } catch (Exception e) {
            log.error("系统错误，栏目明细保存失败!：", e);
            throw new ServiceException(e);
        }
        return message;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public ResultMessage batchDeleteSectionsDetail(List<Long> sectionsDetailIds) throws ServiceException {
        ResultMessage message = new ResultMessage();
        message.setIsSuccess(true);
        message.setMessage("栏目明细删除成功!");

        try {
            int count = sectionsDetailDao.batchDeleteSectionsDetail(sectionsDetailIds);
            if (count < 1) {
                message.setIsSuccess(false);
                message.setMessage("栏目明细删除失败!");
                return message;
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }

        return message;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public ResultMessage batchUpdateSectionsDetail(List<SectionsDetail> sectionsDetailList) throws ServiceException {
        ResultMessage message = new ResultMessage();
        message.setIsSuccess(true);

        try {
            int count = sectionsDetailDao.batchUpdateSectionsDetail(sectionsDetailList);
            if (count < 1) {
                message.setIsSuccess(false);
                message.setMessage("栏目明细修改失败!");
                return message;
            }
        } catch (Exception e) {
            log.error("系统错误，栏目明细修改失败!：", e);
            throw new ServiceException(e);
        }
        return message;
    }

    @Override
    public List<SectionsDetail> selectSectionsDetailByProductId(Long productId) throws ServiceException {
        try {
            return sectionsDetailDao.selectSectionsDetailByProductId(productId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
}
