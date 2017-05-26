package com.kmzyc.b2b.third.service;

import java.sql.SQLException;
import java.util.List;

import com.kmzyc.b2b.third.model.ThirdAccountInfo;
import com.kmzyc.b2b.third.model.ThirdBindInfo;
import com.kmzyc.framework.exception.ServiceException;

/**
 * service 层
 * 
 * @author Administrator 2014-03-18
 */
public interface ThirdBindInfoService {

    /**
     * 存储信息
     * 
     * @param entity
     * @throws SQLException
     */
    public void saveBindInfo(ThirdBindInfo entity) throws SQLException;

    /**
     * 更新绑定关系信息(将绑定会员的类型从绑定临时会员更新为正式会员类型)
     * 
     * @param entity
     * @throws SQLException
     */
    public int updateBindInfo(ThirdBindInfo entity) throws SQLException;

    /**
     * 解除绑定关系
     * 
     * @param condition
     * @throws ServiceException
     */
    public void unBindingInfo(ThirdBindInfo condition) throws ServiceException;

    /**
     * 按loginId查询康美某正式会员所有的绑定信息
     * 
     * @param condition 条件实体
     * @return
     * @throws SQLException
     */
    public List<ThirdBindInfo> queryBindInfo(String loginId) throws SQLException;

    /**
     * 查询第三方账号是否是和临时会员绑定,如果绑定,则提示其去完善个人资料
     * 
     * @param condition
     * @return
     * @throws SQLException
     */
    public boolean isBindTourist(ThirdAccountInfo condition) throws SQLException;

    /**
     * 根据open_id和accountType查询loginId和nickName
     * 
     * @param condition
     * @return
     * @throws SQLException
     */
    public String queryLoginIdByThridAcctInfo(ThirdAccountInfo condition) throws SQLException;


    /**
     * 根据openId删除绑定数据
     * 
     * @param openId
     * @param thirdAccountType
     * @throws SQLException
     */
    public void deleteBindInfoByOpenId(String openId, String thirdAccountType) throws SQLException;

}
