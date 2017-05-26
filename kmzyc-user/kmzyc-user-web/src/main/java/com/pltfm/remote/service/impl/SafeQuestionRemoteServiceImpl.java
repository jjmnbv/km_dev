package com.pltfm.remote.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kmzyc.user.remote.service.SafeQuestionRemoteService;
import com.pltfm.app.dao.SafeQuestionDAO;
import com.pltfm.app.util.RemoteInvoking;
import com.pltfm.app.vobject.SafeQuestion;

/**
 * 安全问题信息远程接口实现类
 * 
 * @author cjm
 * @since 2013-8-7
 */
@Component(value = "safeQuestionRemoteService")
public class SafeQuestionRemoteServiceImpl implements SafeQuestionRemoteService {
    /**
     * 日志类
     */
    Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * 安全问题DAO接口
     */
    private SafeQuestionDAO safeQuestionDAO;

    /**
     * 查询全部安全问题
     * 
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception
     */
    @Override
    public List<SafeQuestion> selectByAll(Integer type) throws Exception {

        log.warn(RemoteInvoking.remoteInvokingType(type));

        return safeQuestionDAO.selectByAll();
    }

    public SafeQuestionDAO getSafeQuestionDAO() {
        return safeQuestionDAO;
    }

    public void setSafeQuestionDAO(SafeQuestionDAO safeQuestionDAO) {
        this.safeQuestionDAO = safeQuestionDAO;
    }

}
