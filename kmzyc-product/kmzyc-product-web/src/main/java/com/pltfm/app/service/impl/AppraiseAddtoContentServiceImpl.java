package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pltfm.app.dao.AppraiseAddtoContentDAO;
import com.pltfm.app.dao.ProdAppraiseDAO;
import com.pltfm.app.service.AppraiseAddtoContentService;
import com.pltfm.app.vobject.AppraiseAddtoContent;
import com.pltfm.app.vobject.AppraiseAddtoContentExample;
import com.pltfm.app.vobject.ProdAppraise;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;

/**
 * 
 * @author tanyunxing
 * 
 */
@Service("appraiseAddtoContentService")
public class AppraiseAddtoContentServiceImpl implements AppraiseAddtoContentService {

    private Logger logger = LoggerFactory.getLogger(AppraiseAddtoContentServiceImpl.class);

    @Resource
	private AppraiseAddtoContentDAO appraiseAddtoContentDao;

	@Resource
	private ProdAppraiseDAO prodAppraiseDao;

	@Override
	public void searchPage(Page page, AppraiseAddtoContent content) throws ServiceException {
		int pageIndex = page.getPageNo();
		if (pageIndex == 0)
			pageIndex = 1;
		int pageSize = page.getPageSize();
		int skip = (pageIndex - 1) * pageSize;
		int max = pageSize;

		if (content.getCheckStatus() == null || content.getCheckStatus().intValue()==-1) {
			content.setCheckStatus(null);
		}
		if (content.getEndDate() != null) {
			Date endAppDate = DateUtils.addDays(content.getEndDate(), 1);
			content.setEndDate(endAppDate);
		}

		try {
			int count = appraiseAddtoContentDao.findValidDataCount(content);
			List<AppraiseAddtoContent> list = appraiseAddtoContentDao.findValidData(content, skip, max);
			page.setDataList(list);
			page.setRecordCount(count);
		} catch (SQLException e) {
            logger.error("分页列表异常", e);
            throw new ServiceException(e);
		}
	}

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public void deleteBatch(Long[] addContentIds) throws ServiceException {
        try {
            appraiseAddtoContentDao.deleteByPrimaryKeyBatch(addContentIds);
        } catch (SQLException e) {
            logger.error("删除异常", e);
            throw new ServiceException(e);
        }
    }

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public void updatePass(Long[] addContentIds) throws ServiceException {
        try {
            appraiseAddtoContentDao.updateAddContentStatusPassByPrimaryKeyBatch(addContentIds);
        } catch (SQLException e) {
            logger.error("审核通过异常", e);
            throw new ServiceException(e);
        }
    }

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public void updateUnPass(Long[] addContentIds) throws ServiceException {
        try {
            appraiseAddtoContentDao.updateAddContentStatusUnPassByPrimaryKeyBatch(addContentIds);
        } catch (SQLException e) {
            logger.error("审核不通过异常", e);
            throw new ServiceException(e);
        }
    }

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public boolean saveAddtoContent(AppraiseAddtoContent content) throws ServiceException {
		try {
			content.setCheckStatus((short)0);
			appraiseAddtoContentDao.insert(content);
			ProdAppraise p = new ProdAppraise();
			p.setAppraiseId(content.getAppraiseId());
			p.setAddtoContent("1");
			int rows = prodAppraiseDao.updateByPrimaryKeySelective(p);
			if (rows > 0) {
				return true;
			}
		} catch (SQLException e) {
			throw new ServiceException(e);
		}
		return false;
	}

	@Override
	public List<AppraiseAddtoContent> findByAppId(Long appraiseId) throws ServiceException {
		AppraiseAddtoContentExample exa = new AppraiseAddtoContentExample();
		exa.createCriteria().andAppraiseIdEqualTo(appraiseId);
        try {
            return appraiseAddtoContentDao.selectByExample(exa);
        } catch (SQLException e) {
            logger.error("获取某个评价的追加信息异常", e);
            throw new ServiceException(e);
        }
    }

}
