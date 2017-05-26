package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pltfm.app.dao.AppraiseReplyDAO;
import com.pltfm.app.dao.ProdAppraiseDAO;
import com.pltfm.app.service.AppraiseReplyService;
import com.pltfm.app.vobject.AppraiseReply;
import com.pltfm.app.vobject.AppraiseReplyExample;
import com.pltfm.app.vobject.ProdAppraise;
import com.pltfm.app.vobject.AppraiseReplyExample.Criteria;
import com.kmzyc.commons.page.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("appraiseReplyService")
public class AppraiseReplyServiceImpl implements AppraiseReplyService {

    private static Logger logger = LoggerFactory.getLogger(AppraiseReplyServiceImpl.class);

    @Resource
    private AppraiseReplyDAO appraiseReplyDao;

    @Resource
    private ProdAppraiseDAO prodAppraiseDao;

    @Override
    public List<AppraiseReply> findByAppraiseId(Long appraiseId, Short replyStyle) throws ServiceException {
        AppraiseReplyExample exa = new AppraiseReplyExample();
        Criteria c = exa.createCriteria();
        c.andAppraiseIdEqualTo(appraiseId);
        if (replyStyle != null) {
            c.andReplyStyleEqualTo(replyStyle);
        }

        try {
            return appraiseReplyDao.selectByExample(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void saveReply(AppraiseReply reply, ProdAppraise prodApp) throws ServiceException {
        try {
            appraiseReplyDao.insert(reply);
            prodAppraiseDao.updateByPrimaryKeySelective(prodApp);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void searchByPage(Page page, AppraiseReply reply) throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        AppraiseReplyExample exm = new AppraiseReplyExample();
        Criteria c = exm.createCriteria();
        if (reply.getReplyStatus() != null && !"-1".equals(reply.getReplyStatus().toString())) {
            c.andReplyStatusEqualTo(reply.getReplyStatus());
        }
        if (reply.getStartDate() != null) {
            c.andReplyDateGreaterThanOrEqualTo(reply.getStartDate());
        }
        if (reply.getEndDate() != null) {
            Date endAppDate = DateUtils.addDays(reply.getEndDate(), 1);
            c.andReplyDateLessThanOrEqualTo(endAppDate);
        }
        exm.setOrderByClause(" REPLY_STATUS,REPLY_DATE desc");

        try {
            int count = appraiseReplyDao.countByExample(exm);
            List<AppraiseReply> list = appraiseReplyDao.selectByExample(exm, skip, max);
            page.setDataList(list);
            page.setRecordCount(count);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void deleteBatch(Long[] appraiseReplyIds) throws ServiceException {
        try {
            appraiseReplyDao.deleteByPrimaryKeyBatch(appraiseReplyIds);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updatePass(Long[] appraiseReplyIds) throws ServiceException {
        try {
            appraiseReplyDao.updateReplyStatusPassByPrimaryKeyBatch(appraiseReplyIds);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateUnPass(Long[] appraiseReplyIds) throws ServiceException {
        try {
            appraiseReplyDao.updateReplyStatusUnPassByPrimaryKeyBatch(appraiseReplyIds);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int updateByAppraiseReplyId(AppraiseReply reply) throws ServiceException {
        int count = 0;
        try {
            count = appraiseReplyDao.updateByPrimaryKeySelective(reply);
        } catch (Exception e) {
            logger.error("根据id修改客服回复信息出现异常" + e.getMessage(), e);
            throw new ServiceException(e);
        }
        return count;
    }

}
