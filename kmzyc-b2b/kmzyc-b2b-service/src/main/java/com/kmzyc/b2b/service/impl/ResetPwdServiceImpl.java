package com.kmzyc.b2b.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.LoginDao;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.ResetPwdService;
import com.kmzyc.b2b.vo.UserBaseInfo;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.pltfm.app.vobject.LoginInfo;

// import com.km.framework.common.util.RemoteTool;

@Service
public class ResetPwdServiceImpl implements ResetPwdService {
    private static Logger logger= LoggerFactory.getLogger(ResetPwdServiceImpl.class);
    @Resource(name = "loginDaoImp")
    private LoginDao loginDao;

    @Resource
    private CustomerRemoteService customerRemoteService;


    /**
     * 忘记密码重置密码
     * 
     * @param userInfo
     */
    public boolean resetPassword(UserBaseInfo userInfo) throws Exception {
        boolean result = false;
        try {
            //SaltInfo saltInfo = this.saltInfoService.querySaltInfo(userInfo);
            userInfo.setPassword(userInfo.getLoginPassword());
            userInfo = this.customerRemoteService.queryUserPasswordTwice(userInfo,"login");
            if(null == userInfo ){
               result =false;
            }else {
                LoginInfo loginInfo = new LoginInfo();
                loginInfo.setN_LoginId(userInfo.getLoginId().intValue());
                loginInfo.setLoginPassword(userInfo.getPassword());
                customerRemoteService.updateByPrimaryKeySelective(loginInfo);
                result = true;
            }
        } catch (Exception e) {
            logger.error("重置密码ERROR",e);
        }
        return result;
    }

    /**
     * 根据登录ID获取用户信息
     * 
     * @param loginId
     * @return
     * @throws Exception
     */
    @Override
    public User getUserByLoginId(long loginId) throws Exception {

        return loginDao.getUserByLoginId(loginId);
    }
}
