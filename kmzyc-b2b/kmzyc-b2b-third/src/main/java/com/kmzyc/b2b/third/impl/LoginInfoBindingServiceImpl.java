package com.kmzyc.b2b.third.impl;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.third.model.ThirdBindInfo;
import com.kmzyc.b2b.third.service.LoginInfoBindingService;
import com.kmzyc.b2b.third.service.ThirdBindInfoService;
import com.kmzyc.b2b.third.util.ThirdConstant;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.user.remote.service.AccountInfoRemoteService;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.LoginInfo;

// import com.km.framework.common.util.RemoteTool;

/**
 * 登录信息业务逻辑实现类
 * 
 * @author cjm
 * @since 2014-3-25
 */
@Service("loginInfoBindingService")
public class LoginInfoBindingServiceImpl implements LoginInfoBindingService {
    /**
     * 第三方账号和康美中药城账号绑定信息业务逻辑接口
     */
    @Resource(name = "thirdBindInfoService")
    private ThirdBindInfoService thirdBindInfoService;

    // private static Logger log = LoggerFactory.getLogger(LoginInfoBindingServiceImpl.class);
    private static Logger log = LoggerFactory.getLogger(LoginInfoBindingServiceImpl.class);

    @Resource
    private AccountInfoRemoteService accountInfoRemoteService;
    @Resource
    private CustomerRemoteService customerRemoteService;

    /**
     * 更新登录信息
     * 
     * @param loginInfo 登录信息bean
     * @param thirdBindInfo 第三方账号和康美中药城账号绑定信息bean
     * @throws ServiceException
     */
    @Override
    public void updateLoginInfo(LoginInfo loginInfo, ThirdBindInfo thirdBindInfo)
            throws ServiceException {
        try {
            // 设置thirdBindInfoBean的信息
            thirdBindInfo.setBindType(ThirdConstant.THIRD_BIND_TYPE_FORMAL);
            thirdBindInfo.setLastUpdateTime(
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            thirdBindInfo.setIsBind("Y");
            thirdBindInfo.setnLoginId(thirdBindInfo.getnLoginId());

            // thirdBindInfo的loginId设到LoginInfoBean中
            loginInfo.setN_LoginId(Integer.valueOf((thirdBindInfo.getnLoginId())));
            loginInfo.setN_CustomerTypeId(1);

            // 将loginInfoBean的信息存入accountInfoBean中
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setN_LoginId(loginInfo.getN_LoginId());
            accountInfo.setEmail(loginInfo.getEmail());
            accountInfo.setAccountLogin(loginInfo.getLoginAccount());
            accountInfo.setN_CustomerTypeId(1);
            accountInfo.setMobile(loginInfo.getMobile());

            // 更新绑定类型为正式会员
            thirdBindInfoService.updateBindInfo(thirdBindInfo);

            // 根据loginId更新accountInfoBean
            // AccountInfoRemoteService accountInfoRemoteService =
            // (AccountInfoRemoteService) RemoteTool.getRemote(Constants.REMOTE_SERVICE_CUSTOMER,
            // "accountInfoRemoteService");
            accountInfoRemoteService.updateByLoginId(accountInfo);

            // 根据loginId更新loginInfoBean
            // CustomerRemoteService customerRemoteService =
            // (CustomerRemoteService) RemoteTool.getRemote(Constants.REMOTE_SERVICE_CUSTOMER,
            // "customerRemoteService");
            // 只更新account_info表中的数据,不更新login_info表中的Email,这样就没有已验证的问题
            loginInfo.setEmail("");
            customerRemoteService.updateByPrimaryKeySelective(loginInfo);

            /*
             * 20141031 maliqun add begin 电商会员关系系统和总部会员关系系统同步 注册时调用webservice 此段代码请勿删除,后续需要嵌入
             * 第三方会员登录过来的用户只有在和正式会员绑定或者完善了信息之后才做数据的同步
             */
            // loginInfo.setEmail(accountInfo.getEmail());
            //
            // String xmlStr = GenerateXMLStrUtil.generateXMlForAdd(loginInfo);
            // List<WsReturnResult> result =
            // WebServiceUtil.sendWebService(endPoint, targetNameSpace,
            // Constants.WEBSERVICE_METHOD_FOR_ADD, xmlStr, Constants.WEBSERVICE_PARA_NAME);
            //
            // if (result == null) {
            // System.out.println("LoginInfoBindingServiceImpl ---> updateLoginInfo
            // -->调用webservice服务出错~");
            // log.error("LoginInfoBindingServiceImpl ---> updateLoginInfo -->调用webservice服务出错~");
            // } else {
            // System.out.println("LoginInfoBindingServiceImpl 执行webservice返回码:"
            // + result.get(result.size() - 1).getReturnCode() + "====具体信息:"
            // + result.get(result.size() - 1).getMessageContent());
            // log.info("LoginInfoBindingServiceImpl 执行webservice返回码:"
            // + result.get(result.size() - 1).getReturnCode() + "====具体信息:"
            // + result.get(result.size() - 1).getMessageContent());
            // }
            // 20141031 maliqun add end

        } catch (MalformedURLException e) {
            log.error("LoginInfoBindingServiceImpl -->updateLoginInfo  --->" + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            log.error("LoginInfoBindingServiceImpl -->updateLoginInfo  --->" + e.getMessage(), e);
        } catch (SQLException e) {
            log.error("LoginInfoBindingServiceImpl -->updateLoginInfo  --->" + e.getMessage(), e);
        } catch (Exception e) {
            log.error("LoginInfoBindingServiceImpl -->updateLoginInfo  --->" + e.getMessage(), e);
        }
    }

    @Override
    public List<ThirdBindInfo> queryBindInfoList(String loginId) throws ServiceException {
        try {
            return thirdBindInfoService.queryBindInfo(loginId);
        } catch (SQLException e) {
            log.error("LoginInfoBindingServiceImpl --> 按loginId查询绑定信息异常!" + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void unBinding(ThirdBindInfo condition) throws ServiceException {
        thirdBindInfoService.unBindingInfo(condition);
    }

    public ThirdBindInfoService getThirdBindInfoService() {
        return thirdBindInfoService;
    }

    public void setThirdBindInfoService(ThirdBindInfoService thirdBindInfoService) {
        this.thirdBindInfoService = thirdBindInfoService;
    }

}
