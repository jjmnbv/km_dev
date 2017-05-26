package com.kmzyc.b2b.service.member.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.member.RechargeDetailsDao;
import com.kmzyc.b2b.model.RechargeDetails;
import com.kmzyc.b2b.service.member.RechargeDetailsService;
import com.km.framework.page.Pagination;

@SuppressWarnings("unused")
@Service
public class RechargeDetailsServiceImpl implements RechargeDetailsService {

    @Resource(name = "rechargeDetailsDaoImpl")
    private RechargeDetailsDao rechargeDetailsDao;

    /**
     * 根据登录id查询账号余额，状态
     */
    public RechargeDetails queryRechargeDetailsById(Pagination page) throws SQLException {

        return rechargeDetailsDao.queryRechargeDetailsByLoginId(page);
    }

}
