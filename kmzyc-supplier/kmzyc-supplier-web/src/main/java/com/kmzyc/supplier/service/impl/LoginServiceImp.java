package com.kmzyc.supplier.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import com.kmzyc.commons.exception.ServiceException;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.supplier.model.User;
import com.kmzyc.supplier.dao.LoginDao;
import com.kmzyc.supplier.service.LoginService;
import com.kmzyc.supplier.vo.CountInfo;

@Service("loginService")
public class LoginServiceImp implements LoginService {

    @Resource
    private LoginDao loginDao;

    private static Logger logger = LoggerFactory.getLogger(LoginServiceImp.class);

    @Override
    public Long getUserType(String userName, String pwd) throws ServiceException {
        try {
            return loginDao.getUserType(userName, pwd);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    public User login(User user) throws ServiceException {
        try {
            return loginDao.queryByUser(user);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public CountInfo queryTotalCountForIndex(long supplierId) throws ServiceException {
        try {
            return loginDao.queryTotalCountForIndex(String.valueOf(supplierId));
        } catch (SQLException e) {
            logger.info("为ID为=" + supplierId + "查询 queryTotalCountForIndex发生异常,详情如下=" + e.getMessage());
            throw new ServiceException(e);
        }
    }

}
