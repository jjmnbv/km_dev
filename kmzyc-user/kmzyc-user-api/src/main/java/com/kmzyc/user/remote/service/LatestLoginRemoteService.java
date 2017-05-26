package com.kmzyc.user.remote.service;

import com.pltfm.app.vobject.LatestLogin;

/**
 * 最近登录信息远程接口
 * 
 * @author cjm
 * @since 2013-8-9
 */
public interface LatestLoginRemoteService {
    /**
     * 添加最近登录
     * 
     * @param latestLogin 最近登录实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    Integer addLatestLogin(LatestLogin latestLogin, Integer type) throws Exception;

    /**
     * 根据登录ID查询最近登录信息
     * 
     * @param latestLogin 最近登录实体
     * @param pageParam 分页类
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    // List<LatestLogin> selectByLoginId(LatestLogin latestLogin,Page pageParam,Integer type) throws
    // Exception;
}
