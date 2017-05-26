package com.kmzyc.user.remote.service;

import com.pltfm.app.vobject.BnesAnswerInfo;

/**
 * 安全问题答案信息远程接口
 * 
 * @author cjm
 * @since 2013-8-9
 */
@SuppressWarnings("unused")
public interface BnesAnswerInfoRemoteService {
    /**
     * 添加安全问题答案信息
     * 
     * @param bnesAnswerInfo 安全问题答案信息实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    Integer addBnesAnswerInfo(BnesAnswerInfo bnesAnswerInfo, Integer type) throws Exception;

    /**
     * 根据登录ID、安全问题ID和答案信息，验证是否正确
     * 
     * @param bnesAnswerInfo 安全问题答案信息实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception
     */
    BnesAnswerInfo selectByBnesAnswerInfo(BnesAnswerInfo bnesAnswerInfo, Integer type)
            throws Exception;
}
