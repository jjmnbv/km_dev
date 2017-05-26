package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.PersonalityInfoDAO;
import com.pltfm.app.dao.RankDAO;
import com.pltfm.app.dataobject.RankDO;
import com.pltfm.app.service.RankService;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.PersonalityInfo;
import com.pltfm.app.vobject.Rank;

/**
 * 客户头衔信息处理
 * 
 * @author gwl
 * @since 2013-07-08
 */
@Component(value = "rankService")
public class RankServiceImpl implements RankService {
  @Resource(name = "rankDAO")
  private RankDAO rankDAO;

  /**
   * 个人个性信息DAO接口
   */
  @Resource(name = "personalityInfoDAO")
  private PersonalityInfoDAO personalityInfoDAO;


  /**
   * 添加客户头衔信息
   * 
   * @param Rank 客户头衔等级实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public void insertRank(Rank rank) throws SQLException {
    rankDAO.insertRank(rank);
  }

  /**
   * 删除客户头衔信息
   * 
   * @param Rank 客户头衔信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public Integer deleteRank(List<String> nRankIds) throws SQLException {
    Integer count = 0;
    if (ListUtils.isNotEmpty(nRankIds)) {
      for (String id : nRankIds) {
        Rank rank = new Rank();
        rank.setRankId(Integer.valueOf(id));
        count += rankDAO.deleteRank(rank);
      }
    }
    return count;
  }

  /**
   * 分页查询客户头衔信息
   * 
   * @param 客户头衔信息实体
   * @return
   * @throws Exception 异常
   */
  @Override
  public Page searchPageByVo(Page pageParam, Rank vo) throws Exception {
    if (pageParam == null) {
      pageParam = new Page();
    }
    if (vo == null) {
      vo = new Rank();
    }
    int totalNum = rankDAO.selectCountByVo(vo);
    pageParam.setRecordCount(totalNum);
    vo.setSkip(pageParam.getStartIndex());
    vo.setMax(pageParam.getStartIndex() + pageParam.getPageSize());

    pageParam.setDataList(rankDAO.selectPageByVo(vo));
    return pageParam;
  }

  /**
   * 按类型条件查询息总数量
   * 
   * @param vo
   * @return 返回值
   */
  @Override
public Integer selectCountRank(Integer customerTypeId) throws SQLException {
    Rank vo = new Rank();
    vo.setCustomerTypeId(customerTypeId);
    return rankDAO.selectCountRank(vo);
  }

  /**
   * 修改客户头衔信息
   * 
   * @param Rank 客户头衔信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public Integer udpateRank(Rank rank) throws SQLException {
    return rankDAO.udpateRank(rank);
  }

  /**
   * 跟据个人id查询客户头衔信息
   * 
   * @param Rank 客户头衔信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public Rank getRankInfoId(Integer nRankId) throws SQLException {
    return rankDAO.getRankInfoId(nRankId);
  }

  /**
   * 查询是否已存在头衔编号
   * 
   * @param vo
   * @return 返回值
   */
  @Override
public List selectCode(String code) throws SQLException {
    return rankDAO.selectCode(code);
  }

  /**
   * 查询是否已存在头衔名称
   * 
   * @param vo
   * @return 返回值
   */
  @Override
public List selectRankName(String rankName) throws SQLException {
    return rankDAO.selectRankName(rankName);
  }

  /**
   * 按类型查询最新记录
   * 
   * @param vo
   * @return 返回值
   */
  @Override
public Rank maxRankiId(Integer customerTypeId) throws SQLException {
    Rank r = new Rank();
    r.setCustomerTypeId(customerTypeId);
    return rankDAO.maxRankiId(r);
  }

  /**
   * 通过个性id查询客户头衔并更新客户信息中头衔字段
   * 
   * @param userLevelDO
   * @return 受影响行数
   * @throws Exception
   */
  @Override
public Integer updateRankName(RankDO rank) throws SQLException {
    Integer count = 0;
    Rank r = rankDAO.getRankiId(rank);
    if (r != null) {
      PersonalityInfo personalityInfo = new PersonalityInfo();
      personalityInfo.setN_PersonalityId(rank.getPersonalityId());
      personalityInfo.setN_RankId(r.getRankId());
      count = personalityInfoDAO.updateByPrimaryKeySelective(personalityInfo);
    }
    return count;
  }

  /**
   * 跟据客户类型来查询客户头衔信息
   * 
   **/
  @Override
public List getCustomerTypeIdKey(Integer customerTypeId) throws SQLException {
    return rankDAO.getCustomerTypeIdKey(customerTypeId);
  }

  /**
   * 跟据客户类型来查询客户头最大经验值
   * 
   **/
  @Override
public int getScoreMaxs(Integer customerTypeId) throws SQLException {
    Rank rank = new Rank();
    rank.setCustomerTypeId(customerTypeId);
    Integer number;
    if (rankDAO.getScoreMaxs(rank) != null) {
      number = rankDAO.getScoreMaxs(rank);
      if (number != null) {
        number += 1;
      } else {
        number = 0;
      }
    } else {
      number = 0;
    }
    return number;
  }

  /**
   * 跟据客户类型客户最大经验值 和来查询是否有下个等级头衔
   * 
   **/
  @Override
public Rank selectRank(Integer scoreMax, Integer customerTypeId) throws SQLException {
    RankDO rank = new RankDO();
    rank.setCustomerTypeId(customerTypeId);
    rank.setIntegralnumber(scoreMax);
    return rankDAO.getRankiId(rank);
  }

  public PersonalityInfoDAO getPersonalityInfoDAO() {
    return personalityInfoDAO;
  }

  public void setPersonalityInfoDAO(PersonalityInfoDAO personalityInfoDAO) {
    this.personalityInfoDAO = personalityInfoDAO;
  }

  public RankDAO getRankDAO() {
    return rankDAO;
  }

  public void setRankDAO(RankDAO rankDAO) {
    this.rankDAO = rankDAO;
  }
}
