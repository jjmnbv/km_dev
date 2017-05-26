package com.kmzyc.b2b.third.dao;

import java.sql.SQLException;
import java.util.List;

import com.km.framework.persistence.Dao;
import com.kmzyc.b2b.third.model.ThirdAccountInfo;
import com.kmzyc.b2b.third.model.ThirdBindInfo;

/**
 * 绑定关系操作
 * 
 * @author Administrator 2014-03-18
 */
public interface ThirdBindInfoDao extends Dao {

    /**
     * 插入绑定信息
     * 
     * @param entity 传参实体
     * @throws SQLException
     */
    public void insert(ThirdBindInfo entity) throws SQLException;

    /**
     * 更新绑定信息
     * 
     * @param entity
     * @return
     * @throws SQLException
     */
    public int updateBindInfo(ThirdBindInfo entity) throws SQLException;

    /**
     * 删除康美会员和指定的第三方账号绑定信息
     * 
     * @param condition
     * @throws SQLException
     */
    public void deleteBindInfo(ThirdBindInfo condition) throws SQLException;


    /**
     * 删除绑定信息
     * 
     * @param condition
     * @throws SQLException
     */
    public void deleteBindInfoByOpenId(ThirdBindInfo condition) throws SQLException;

    /**
     * 查询某正式康美会员于所有第三方账号绑定的信息
     * 
     * @param condition
     * @return
     * @throws SQLException
     */
    public List<ThirdBindInfo> queryBindInfoByLoginId(String loginId) throws SQLException;

    /**
     * 查询当前登录的第三方账号绑定的是否是临时会员,如果是临时会员,先提醒其完善资料,相当于设置密码和邮箱转成 正式会员,有两种方式可以进入康美中药城(1.第三方账号 2.康美会员)
     * 
     * @param condition
     * @return
     * @throws SQLException
     */
    public ThirdBindInfo queryIsBindTourist(ThirdAccountInfo condition) throws SQLException;

    /**
     * 根据open_id和accountType查询loginId
     * 
     * @param condition
     * @return
     * @throws SQLException
     */
    public String queryLoginIdByThridAcctInfo(ThirdAccountInfo condition) throws SQLException;

}
