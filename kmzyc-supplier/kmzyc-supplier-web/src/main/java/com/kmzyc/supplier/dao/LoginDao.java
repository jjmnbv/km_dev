package com.kmzyc.supplier.dao;


import java.sql.SQLException;

import com.kmzyc.supplier.model.User;
import com.kmzyc.supplier.vo.CountInfo;

public interface LoginDao {

    Long getUserType(String userName, String pwd) throws SQLException;

    User queryByUser(User user) throws SQLException;


    /**
     * 返回首页下排按钮相关的提示数(大改版后新增)
     *
     * @param supplierId
     * @return
     * @throws SQLException
     */
    CountInfo queryTotalCountForIndex(String supplierId) throws SQLException;

}