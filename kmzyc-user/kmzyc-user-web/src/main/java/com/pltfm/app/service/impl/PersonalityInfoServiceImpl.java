package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.PersonalityInfoDAO;
import com.pltfm.app.service.PersonalityInfoService;
import com.pltfm.app.vobject.PersonalityInfo;
import com.pltfm.app.vobject.PersonalityInfoExample;

/**
 * 个人个性信息务逻辑处理类
 * 
 * @author cjm
 * @since 2013-7-9
 */
@Component(value = "personalityInfoService")
public class PersonalityInfoServiceImpl implements PersonalityInfoService {
  /**
   * 个人个性信息DAO接口
   */
  @Resource(name = "personalityInfoDAO")
  private PersonalityInfoDAO personalityInfoDAO;

  /**
   * 添加个人个性信息
   * 
   * @param personalityInfo 个人基本信息ov
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
  public Integer addPersonalityInfo(PersonalityInfo personalityInfo) throws Exception {
    return personalityInfoDAO.insert(personalityInfo);
  }

  /**
   * 获取个人个性信息总条数
   * 
   * @param example 个人个性信息
   * @return 返回值
   * @throws SQLException 异常
   */
  @Override
  public Integer countByExample(PersonalityInfoExample example) throws Exception {
    return personalityInfoDAO.countByExample(example);
  }

  /**
   * 按头衔id查询是否存在头衔
   * 
   * @param rankId 头衔id
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public int countRank(Integer rankId) throws Exception {
    return personalityInfoDAO.countRank(rankId);
  }

  /**
   * 根据主键进行删除单条个人个性信息
   * 
   * @param n_PersonalityId 个人个性信息ID
   * @return 返回值
   * @throws SQLException 异常
   */
  @Override
  public Integer deleteByPrimaryKey(Integer n_PersonalityId) throws Exception {
    return personalityInfoDAO.deleteByPrimaryKey(n_PersonalityId);
  }

  /**
   * 获取个人个性信息列表
   * 
   * @param personalityInfo 个人个性信息ov
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
  public List<PersonalityInfo> getPersonalityInfoList(PersonalityInfo personalityInfo)
      throws Exception {
    PersonalityInfoExample exam = new PersonalityInfoExample();// 组装where查询条件的对象
    String nickName = personalityInfo.getNickname();
    if (!"".equals(nickName) && nickName != null) {
      nickName = "%" + nickName + "%";
    } else {
      nickName = "%";
    }
    exam.createCriteria().andNicknameLike(nickName);
    // System.out.println(personalityInfoDAO.selectByExample(exam).size());
    return personalityInfoDAO.selectByExample(exam);
  }

  /**
   * 根据主键查询单条个人个性信息
   * 
   * @param n_PersonalityId 个人个性信息ID
   * @return 返回值
   * @throws SQLException 异常
   */
  @Override
  public PersonalityInfo selectByPrimaryKey(Integer n_PersonalityId) throws SQLException {
    return personalityInfoDAO.selectByPrimaryKey(n_PersonalityId);
  }
  
  /**
   * 根据登录
   */
  @Override
  public PersonalityInfo selectPersonalityInfoByLoginId(Integer loginId) throws SQLException {
    return personalityInfoDAO.selectByPersonalityInfo(loginId);
  }
  
  /**
   * 修改个人个性信息
   * 
   * @param personalityInfo 个人个性信息ov
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
  public Integer updatePersonalityInfo(PersonalityInfo personalityInfo) throws Exception {
    return personalityInfoDAO.updateByPrimaryKeySelective(personalityInfo);
  }

  public PersonalityInfoDAO getPersonalityInfoDAO() {
    return personalityInfoDAO;
  }

  public void setPersonalityInfoDAO(PersonalityInfoDAO personalityInfoDAO) {
    this.personalityInfoDAO = personalityInfoDAO;
  }


  /**
   * 根据vo条件查询分类信息page
   * 
   * @param pageParam 分页实体
   * @param vo 个人个性信息实体
   * @return 返回值
   * @throws Exception
   */
  public Page searchPageByVo(Page pageParam, PersonalityInfo vo) throws Exception {
    int pageNo = pageParam.getPageNo();// 当前查询第几页
    if (pageNo == 0) {
      pageNo = 1;// 首次查询第一页
    }
    int pageSize = pageParam.getPageSize(); // 每页显示记录数

    int skip = (pageNo - 1) * pageSize + 1;

    int max = (pageNo - 1) * pageSize + pageSize;

    // System.out.println(skip+"--^^--"+max);

    Page page = null;
    try {
      // 组合条件 like
      String nickname = vo.getNickname(); // 姓名
      if (nickname != null && !"".equals(nickname)) {
        vo.setNickname("%" + nickname + "%"); // like statement
      }
      vo.setSkip(skip);
      vo.setMax(max);
      page = personalityInfoDAO.selectPageByVo(pageParam, vo);
      page.setPageNo(pageNo);// 当前查询第几页
    } catch (SQLException e) {

      throw e;
    }
    return page;
  }

}
