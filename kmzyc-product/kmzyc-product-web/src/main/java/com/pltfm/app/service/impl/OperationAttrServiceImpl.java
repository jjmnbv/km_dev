package com.pltfm.app.service.impl;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.OperationAttrDAO;
import com.pltfm.app.service.OperationAttrService;
import com.pltfm.app.vobject.OperationAttr;
import com.pltfm.app.vobject.OperationAttrExample;
import com.pltfm.app.vobject.OperationAttrExample.Criteria;
import com.kmzyc.commons.page.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;


@Service("operationAttrService")
public class OperationAttrServiceImpl implements OperationAttrService {

    @Resource
    private OperationAttrDAO operationAttrDao;

    @Override
    public List<OperationAttr> queryOperationAttrList() throws ServiceException {
        OperationAttrExample exa = new OperationAttrExample();
        exa.createCriteria().andStatusEqualTo("1");

        try {
            return operationAttrDao.selectByExample(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public OperationAttr queryOperationAttr(Long operationAttrId) throws ServiceException {
        try {
            return operationAttrDao.selectByPrimaryKey(operationAttrId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateOperationAttr(OperationAttr record) throws ServiceException {
        try {
            operationAttrDao.updateByPrimaryKeySelective(record);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public String delete(String[] operationAttrId) throws ServiceException {
        StringBuffer sb = new StringBuffer();
        try {
            for (String str : operationAttrId) {
                String operationAttrName = operationAttrDao.findRelationAttr(Long.valueOf(str));
                if (StringUtils.isNotBlank(operationAttrName)) {
                    sb.append("【").append(operationAttrName).append("】");
                }
            }
            if (sb.length() > 0) {
                return sb.toString();
            } else {
                operationAttrDao.deleteBatch(operationAttrId);
            }
            return null;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void searchPage(Page page, OperationAttr attr) throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0)
            pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        OperationAttrExample exm = new OperationAttrExample();
        Criteria c = exm.createCriteria();
        if (StringUtils.isNotEmpty(attr.getOperationAttrName())) {
            c.andOperationAttrNameLike("%" + attr.getOperationAttrName() + "%");
        }
        if (StringUtils.isNotEmpty(attr.getStatus())) {
            c.andStatusEqualTo(attr.getStatus());
        }
        if (attr.getIsNav() != null && !attr.getIsNav().equals(-1)) {
            c.andIsNavEqualTo(attr.getIsNav());
        }
        exm.setOrderByClause(" OPERATION_ATTR_ID ");

        try {
            int count = operationAttrDao.countByExample(exm);
            List<OperationAttr> list = operationAttrDao.selectByExample(exm, skip, max);
            page.setDataList(list);
            page.setRecordCount(count);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void saveOperationAttr(OperationAttr record) throws ServiceException {
        try {
            operationAttrDao.insert(record);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean checkRepeatName(String name, Long operationAttrId) throws ServiceException {
        OperationAttrExample exm = new OperationAttrExample();
        Criteria c = exm.createCriteria();
        c.andOperationAttrNameEqualTo(name);
        if (operationAttrId != null) {
            c.andOperationAttrIdNotEqualTo(operationAttrId.intValue());
        }

        try {
            return operationAttrDao.countByExample(exm) > 0;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}