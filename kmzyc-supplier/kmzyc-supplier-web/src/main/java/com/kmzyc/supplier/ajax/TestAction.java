package com.kmzyc.supplier.ajax;


import com.kmzyc.supplier.model.User;
import com.kmzyc.supplier.action.SupplierBaseAction;
import com.kmzyc.supplier.service.LoginService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@SuppressWarnings("unchecked")
@Controller("testAction")
public class TestAction extends SupplierBaseAction {

    private static Logger logger = LoggerFactory.getLogger(TestAction.class);

    @Resource
    private LoginService loginService;

    private User user;

    public String checkUserType() {
        try {
            Long count = loginService.getUserType(user.getLoginAccount(), user.getLoginPassword());
            writeStr(count != null ? count.toString() : "用户不存在!");
        } catch (Exception e) {
            logger.error("检查用户类型出现异常：" + e.getMessage(), e);
            return ERROR;
        }

        return null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
