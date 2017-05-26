package com.pltfm.remote.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.user.remote.service.BnesAuthenticationInfoRemoteService;
import com.pltfm.app.dao.BnesAuthenticationInfoDAO;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.RemoteInvoking;
import com.pltfm.app.vobject.BnesAuthenticationInfo;

/**
 * 实名认证远程接口实现类
 * 
 * @author cjm
 * @since 2013-8-16
 */
@Component(value = "bnesAuthenticationInfoRemoteService")
public class BnesAuthenticationInfoRemoteServiceImpl
        implements BnesAuthenticationInfoRemoteService {

    /**
     * 日志类
     */
    Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 实名认证DAO接口
     */
    @Resource(name = "bnesAuthenticationInfoDAO")
    private BnesAuthenticationInfoDAO bnesAuthenticationInfoDAO;

    /**
     * 添加实名认证
     * 
     * @param bnesAuthenticationInfo 实名认证信息实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回受影响行数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addBnesAuthenticationInfo(BnesAuthenticationInfo bnesAuthenticationInfo,
            Integer type) throws Exception {
        bnesAuthenticationInfo.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
        bnesAuthenticationInfo.setExaminationValue(0);

        log.warn(RemoteInvoking.remoteInvokingType(type));

        return bnesAuthenticationInfoDAO.insert(bnesAuthenticationInfo);
    }

    public BnesAuthenticationInfoDAO getBnesAuthenticationInfoDAO() {
        return bnesAuthenticationInfoDAO;
    }

    public void setBnesAuthenticationInfoDAO(BnesAuthenticationInfoDAO bnesAuthenticationInfoDAO) {
        this.bnesAuthenticationInfoDAO = bnesAuthenticationInfoDAO;
    }

}
