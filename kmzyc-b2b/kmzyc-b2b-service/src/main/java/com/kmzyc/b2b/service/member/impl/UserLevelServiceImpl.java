package com.kmzyc.b2b.service.member.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.member.UserLevelDao;
import com.kmzyc.b2b.model.UserLevel;
import com.kmzyc.b2b.service.member.UserLevelService;
import com.km.framework.persistence.util.DBContextHolder;

@Service
public class UserLevelServiceImpl implements UserLevelService {

    // private static Logger logger = Logger.getLogger(MyOrderServiceImpl.class);
    private static Logger logger = LoggerFactory.getLogger(UserLevelServiceImpl.class);
    @Resource(name = "userLevelDaoImpl")
    private UserLevelDao userLevelDao;

    @Override
    public List<UserLevel> findAllUserLevel() throws Exception {
        logger.info("查询所有的用户等级");

        List<UserLevel> usrLevelList = userLevelDao.findList("UserLevel.findAllUserLevel");
        return usrLevelList;
    }

    @Override
    public String findCodeByLoginId(Long loginId) throws Exception {
        logger.info("查询用户编号");

        Object obj = userLevelDao.findById("UserLevel.findUserLevelCodeByLoginId", loginId);
        return obj.toString();
    }

    @Override
    public UserLevel findByLevelId(Long levelId) throws Exception {
        logger.info("查询用户编号");

        return (UserLevel) userLevelDao.findById("UserLevel.findUserLevelByLevelId", levelId);
    }

    @Override
    public UserLevel findBySomeCondition(UserLevel userLevel) throws Exception {
        logger.info("查询用户编号");

        return (UserLevel) userLevelDao.findById("UserLevel.abatorgenerated_selectByUserLevelDO",
                userLevel);
    }

}
