package com.kmzyc.b2b.third.service;

import java.util.List;

import com.kmzyc.b2b.third.model.ThirdBindInfo;
import com.kmzyc.framework.exception.ServiceException;
import com.pltfm.app.vobject.LoginInfo;

/**
 * 登录信息业务逻辑接口
 * 
 * @author cjm
 * @since 2014-3-25
 */
public interface LoginInfoBindingService {
    /**
     * 更新登录信息
     * 
     * @param loginInfo 登录信息bean
     * @param thirdBindInfo 第三方账号和康美中药城账号绑定信息bean
     * @throws ServiceException
     */
    public void updateLoginInfo(LoginInfo loginInfo, ThirdBindInfo thirdBindInfo)
            throws ServiceException;

    /**
     * 依据id查询某正式会员所绑定的第三方账号信息
     * 
     * @param loginId
     * @return
     * @throws ServiceException
     */
    public List<ThirdBindInfo> queryBindInfoList(String loginId) throws ServiceException;

    /**
     * 解绑操作
     * 
     * @param condition
     * @throws ServiceException
     */
    public void unBinding(ThirdBindInfo condition) throws ServiceException;
}
