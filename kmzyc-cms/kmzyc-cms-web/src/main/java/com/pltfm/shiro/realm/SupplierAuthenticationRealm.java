package com.pltfm.shiro.realm;

import com.pltfm.app.dao.BnesBrowsingHisDAO;
import com.pltfm.shiro.vobject.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.sql.SQLException;

import javax.annotation.Resource;

/**
 * 对供应商进行用户认证及授权处理
 *
 * @author river
 */
public class SupplierAuthenticationRealm extends AuthorizingRealm {
	private static Logger logger = LoggerFactory.getLogger(SupplierAuthenticationRealm.class);
    @Resource(name = "bnesBrowsingHisDAO")
    private BnesBrowsingHisDAO bnesBrowsingHisDAO;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {// 授权
        String username = principals.getPrimaryPrincipal().toString();
        // System.out.println("username: " + username);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole("supplier");
        info.addStringPermission(username);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {
        // 对用户认证
        String username = token.getPrincipal().toString();
        char[] psChar = (char[]) token.getCredentials();
        String password = new String(psChar);
        SimpleAuthenticationInfo authenticationInfo = null;

        User user = new User();
        user.setLoginPassword(password);
        user.setLoginAccount(username);
        User u = null;
        try {
            u = bnesBrowsingHisDAO.queryUser(user);
        } catch (SQLException e) {
            logger.error("SupplierAuthenticationRealm.doGetAuthenticationInfo供应商登录失败：" + e.getMessage(), e);
        }
        if (null != u) {
            authenticationInfo = new SimpleAuthenticationInfo(username,
                    password, name);
        }
        // 返回认证信息
        return authenticationInfo;
    }

    private final String name = SupplierAuthenticationRealm.class
            .getSimpleName();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    public BnesBrowsingHisDAO getBnesBrowsingHisDAO() {
        return bnesBrowsingHisDAO;
    }

    public void setBnesBrowsingHisDAO(BnesBrowsingHisDAO bnesBrowsingHisDAO) {
        this.bnesBrowsingHisDAO = bnesBrowsingHisDAO;
    }
}
