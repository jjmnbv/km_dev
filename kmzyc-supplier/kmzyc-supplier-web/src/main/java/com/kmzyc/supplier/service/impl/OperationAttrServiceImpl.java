package com.kmzyc.supplier.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.dao.OperationAttrDAO;
import com.kmzyc.supplier.service.OperationAttrService;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pltfm.app.vobject.OperationAttr;
import com.pltfm.app.vobject.OperationAttrExample;


@Service("operationAttrService")
public class OperationAttrServiceImpl implements OperationAttrService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "operationAttrDAO")
	private OperationAttrDAO operationAttrDAO;

	@Override
	public OperationAttr queryOperationAttr(Long operationAttrId) throws ServiceException {
        try {
            return operationAttrDAO.selectByPrimaryKey(operationAttrId);
        } catch (SQLException e) {
            logger.error("查询运营属性：", e);
            throw new ServiceException(e);
        }
    }

	@Override
	public List<OperationAttr> queryOperationAttrList() throws ServiceException {
		OperationAttrExample exa = new OperationAttrExample();
		exa.createCriteria().andStatusEqualTo("1");
        try {
            return operationAttrDAO.selectByExample(exa);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}
