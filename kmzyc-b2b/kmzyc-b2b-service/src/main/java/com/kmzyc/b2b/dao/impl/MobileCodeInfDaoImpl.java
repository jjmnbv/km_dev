package com.kmzyc.b2b.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.kmzyc.b2b.dao.MobileCodeInfDao;
import com.kmzyc.b2b.model.MobileCodeInf;
import com.kmzyc.framework.exception.DaoException;

@Component("mobileCodeInfDaoImpl")
public class MobileCodeInfDaoImpl implements MobileCodeInfDao {
    @javax.annotation.Resource(name = "sqlMapClient")
    private com.ibatis.sqlmap.client.SqlMapClient sqlMapClient;

    @Override
    public MobileCodeInf findByMobileAndNLoginId(MobileCodeInf mobileCodeInf) throws SQLException,
            DaoException {
        // 设置数据源

        List list =
                sqlMapClient.queryForList("MobileCodeInf.findByMobileAndNLoginId", mobileCodeInf);
        if (list != null && list.size() > 0) {
            MobileCodeInf rsMobileCodeInf = (MobileCodeInf) list.get(0);
            return rsMobileCodeInf;
        } else {
            return null;
        }
    }

}
