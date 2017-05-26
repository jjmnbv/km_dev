package com.pltfm.remote.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.page.Page;
import com.kmzyc.user.remote.service.LatestLoginRemoteService;
import com.pltfm.app.dao.LatestLoginDAO;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.RemoteInvoking;
import com.pltfm.app.vobject.LatestLogin;

/**
 * 最近登录信息远程接口实现类
 * 
 * @author cjm
 * @since 2013-8-9
 */
@Component(value = "latestLoginRemoteService")
public class LatestLoginRemoteServiceImpl implements LatestLoginRemoteService {
    /**
     * 日志类
     */
    Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 最近登录DAO接口
     */
    @Resource(name = "latestLoginDAO")
    private LatestLoginDAO latestLoginDAO;

    /**
     * 添加最近登录
     * 
     * @param latestLogin 最近登录实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addLatestLogin(LatestLogin latestLogin, Integer type) throws Exception {
        if (latestLogin != null) {
            latestLogin.setD_Date(DateTimeUtils.getCalendarInstance().getTime());
            if (latestLogin.getLoginModule() == null) {

                latestLogin.setLoginModule("康美之恋商城：登录");
            }
            log.info(RemoteInvoking.remoteInvokingType(type));
            return latestLoginDAO.insert(latestLogin);
        }
        return 0;
    }

    /**
     * 根据登录ID查询最近登录信息
     * 
     * @param latestLogin 最近登录实体
     * @param pageParam 分页类
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    public List<LatestLogin> selectByLoginId(LatestLogin latestLogin, Page pageParam, Integer type)
            throws Exception {
        List<LatestLogin> list = null;
        if (latestLogin != null) {
            // 根据条件获取最近登录总数
            int totalNum = latestLoginDAO.selectCountByVo(latestLogin);
            pageParam.setRecordCount(totalNum);
            // 设置查询开始结束索引
            latestLogin.setSkip(pageParam.getStartIndex());
            latestLogin.setMax(pageParam.getStartIndex() + pageParam.getPageSize());
            list = latestLoginDAO.selectPageByVo(latestLogin);
        }
        return list;
    }

    public LatestLoginDAO getLatestLoginDAO() {
        return latestLoginDAO;
    }

    public void setLatestLoginDAO(LatestLoginDAO latestLoginDAO) {
        this.latestLoginDAO = latestLoginDAO;
    }

}
