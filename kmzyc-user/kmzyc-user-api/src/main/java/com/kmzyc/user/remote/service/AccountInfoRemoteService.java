package com.kmzyc.user.remote.service;

import java.sql.SQLException;
import java.util.Map;

import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.BnesAcctAppealInfo;

/**
 * 账户列表信息远程接口
 * 
 * @author cjm
 * @since 2013-8-7
 */
public interface AccountInfoRemoteService {
    /**
     * 按账户信息条件查询账户信息
     * 
     * @param accountInfo 账户信息条件
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    AccountInfo selectByAccountInfo(AccountInfo accountInfo, Integer type) throws Exception;

    /**
     * 注册账户信息
     * 
     * @param personalBasicInfo 账户信息实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    Map<String, String> registerAccoutInfo(AccountInfo accountInfo, Integer type) throws Exception;

    /**
     * 修改账户信息 abatorgenerated_updateByLoginIdKeySelective
     * 
     * @param record 账户信息实体
     * @return 返回值
     * @throws SQLException sql异常
     */

    int updateByLoginId(AccountInfo accountInfo) throws Exception;


    // 添加申诉
    int insertAcctAppealInfo(BnesAcctAppealInfo record) throws SQLException;
}
