package com.kmzyc.user.remote.service;

import java.util.List;

import com.pltfm.app.vobject.SafeQuestion;

/**
 * 安全问题信息远程接口
 * 
 * @author cjm
 * @since 2013-8-7
 */
@SuppressWarnings("unused")
public interface SafeQuestionRemoteService {
    /**
     * 查询全部安全问题
     * 
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception
     */
    public List<SafeQuestion> selectByAll(Integer type) throws Exception;
}
