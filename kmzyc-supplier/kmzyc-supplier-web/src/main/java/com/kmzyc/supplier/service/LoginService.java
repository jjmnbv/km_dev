package com.kmzyc.supplier.service;


import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.User;
import com.kmzyc.supplier.vo.CountInfo;

public interface LoginService {

    Long getUserType(String userName, String pwd) throws ServiceException;

    /**
     * 登录
     * @param user
     * @return
     * @throws Exception
     */
    User login(User user) throws ServiceException;


    /**
     * 返回首页下排按钮相关的提示数
     *
     * @param supplierId
     * @return
     * @throws ServiceException
     */
    CountInfo queryTotalCountForIndex(long supplierId) throws ServiceException;

}
