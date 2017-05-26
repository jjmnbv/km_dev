package com.kmzyc.user.remote.service;

import com.pltfm.app.vobject.MobileCodeInf;

/**
 * 手机随机码信息远程接口
 * 
 * @author cjm
 * @since 2013-8-16
 */
public interface MobileCodeInfRemoteService {
    /**
     * 添加手机随机码
     * 
     * @param mobileCodeInf 手机随机码实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常updateByPrimaryKeySelective
     */
    Integer addMobileCodeInf(MobileCodeInf mobileCodeInf, Integer type) throws Exception;

    /**
     * 根据条件查询单条手机随机码信息
     * 
     * @param mobileCodeInf 手机随机码实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    MobileCodeInf selectByMobileCodeInf(MobileCodeInf mobileCodeInf, Integer type) throws Exception;

    /**
     * 更新手机随机码状态为已经验证
     * 
     * @param mobileCodeInf 手机随机码实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常updateByPrimaryKeySelective
     */
    Integer updateMobileCodeInfByPrimaryKey(MobileCodeInf mobileCodeInf, Integer type)
            throws Exception;
}
