package com.kmzyc.b2b.third.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.third.dao.ThirdBindInfoDao;
import com.kmzyc.b2b.third.model.ThirdAccountInfo;
import com.kmzyc.b2b.third.model.ThirdBindInfo;
import com.kmzyc.b2b.third.service.ThirdAccountInfoService;
import com.kmzyc.b2b.third.service.ThirdBindInfoService;
import com.kmzyc.framework.exception.ServiceException;

/**
 * 实现类
 * 
 * @author Administrator 2014-03-18
 */
@Service("thirdBindInfoService")
public class ThirdBindInfoServiceImpl implements ThirdBindInfoService {

    @Resource(name = "thirdBindInfoDao")
    private ThirdBindInfoDao dao;

    @Resource(name = "thirdAccountInfoService")
    private ThirdAccountInfoService thirdAcctInfoService;

    private static Logger log = LoggerFactory.getLogger(ThirdBindInfoServiceImpl.class);

    @Override
    public void saveBindInfo(ThirdBindInfo entity) throws SQLException {
        // 设置数据源

        dao.insert(entity);
    }

    @Override
    public int updateBindInfo(ThirdBindInfo entity) throws SQLException {
        // 设置数据源

        return dao.updateBindInfo(entity);
    }

    @Override
    public void unBindingInfo(ThirdBindInfo condition) throws ServiceException {

        try {
            // 设置数据源

            dao.deleteBindInfo(condition);
            ThirdAccountInfo acctInfo = new ThirdAccountInfo();
            acctInfo.setOpenId(condition.getOpenId());
            acctInfo.setThirdAccountType(condition.getThirdAccountType());
            // 相继删除第三方账号的信息
            thirdAcctInfoService.deleteAccountInfo(acctInfo);
        } catch (SQLException e) {
            log.error("ThirdBindInfoServiceImpl: 删除绑定信息及第三方账号信息异常" + e.getMessage(), e);
        }
    }

    @Override
    public List<ThirdBindInfo> queryBindInfo(String loginId) throws SQLException {
        // 设置数据源

        return dao.queryBindInfoByLoginId(loginId);
    }

    @Override
    public boolean isBindTourist(ThirdAccountInfo condition) throws SQLException {
        // 设置数据源

        ThirdBindInfo result = dao.queryIsBindTourist(condition);
        // 如果绑定的类型是02,说明绑定的是临时会员,提示其完善资料,返回true
        if (result != null) {
            return true;
        }
        return false;
    }

    @Override
    public String queryLoginIdByThridAcctInfo(ThirdAccountInfo condition) throws SQLException {
        // 设置数据源

        return dao.queryLoginIdByThridAcctInfo(condition);
    }

    @Override
    public void deleteBindInfoByOpenId(String openId, String thirdAccountType) throws SQLException {
        // 设置数据源

        ThirdBindInfo condition = new ThirdBindInfo();
        condition.setOpenId(openId);
        condition.setThirdAccountType(thirdAccountType);
        dao.deleteBindInfoByOpenId(condition);
    }

}
