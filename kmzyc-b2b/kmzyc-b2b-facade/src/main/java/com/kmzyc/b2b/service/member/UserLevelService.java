package com.kmzyc.b2b.service.member;

import java.util.List;

import com.kmzyc.b2b.model.UserLevel;

public interface UserLevelService {

  public List<UserLevel> findAllUserLevel() throws Exception;

  public String findCodeByLoginId(Long loginId) throws Exception;

  public UserLevel findByLevelId(Long levelId) throws Exception;

  public UserLevel findBySomeCondition(UserLevel userLevel) throws Exception;
}
