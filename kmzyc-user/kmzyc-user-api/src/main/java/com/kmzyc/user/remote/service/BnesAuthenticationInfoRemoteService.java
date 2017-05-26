package com.kmzyc.user.remote.service;

import com.pltfm.app.vobject.BnesAuthenticationInfo;

/**
 * 实名认证远程接口
 * 
 * @author cjm
 * @since 2013-8-16
 */
@SuppressWarnings("unused")
public interface BnesAuthenticationInfoRemoteService {

    /**
     * 添加实名认证
     * 
     * @param bnesAuthenticationInfo 实名认证信息实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回受影响行数
     */
    public Integer addBnesAuthenticationInfo(BnesAuthenticationInfo bnesAuthenticationInfo,
            Integer type) throws Exception;
}
