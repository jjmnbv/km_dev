package com.pltfm.app.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.LatestLoginDAO;
import com.pltfm.app.service.LatestLoginService;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.vobject.LatestLogin;

/**
 * 最近登录信息业务逻辑接口实现类
 * 
 * @author cjm
 * @since 2013-7-24
 */
@Component(value = "latestLoginService")
public class LatestLoginServiceImpl implements LatestLoginService {

  /**
   * 最近登录DAO接口
   */
  @Resource(name = "latestLoginDAO")
  private LatestLoginDAO latestLoginDAO;

  /**
   * 添加最近登录
   * 
   * @param record 最近登录实体
   * @return 返回值
   * @throws Exception 异常
   */
  @Override
  public Integer addLatestLogin(LatestLogin latestLogin) throws Exception {
    latestLogin.setD_Date(DateTimeUtils.getCalendarInstance().getTime());
    return latestLoginDAO.insert(latestLogin);
  }

  /**
   * 根据vo条件查询分类信息page
   * 
   * @param pageParam 分页实体
   * @param vo 最近登录实体
   * @return 返回值
   * @throws Exception
   */
  @Override
  public Page searchPageByVo(Page pageParam, LatestLogin vo) throws Exception {
    if (pageParam == null) {
      pageParam = new Page();
    }
    if (vo == null) {
      vo = new LatestLogin();
    }
    // 获取客户积分规则总数
    int totalNum = latestLoginDAO.selectCountByVo(vo);
    pageParam.setRecordCount(totalNum);
    // 设置查询开始结束索引
    vo.setSkip(pageParam.getStartIndex());
    vo.setMax(pageParam.getStartIndex() + pageParam.getPageSize());
    pageParam.setDataList(latestLoginDAO.selectPageByVo(vo));
    return pageParam;
  }

  public LatestLoginDAO getLatestLoginDAO() {
    return latestLoginDAO;
  }

  public void setLatestLoginDAO(LatestLoginDAO latestLoginDAO) {
    this.latestLoginDAO = latestLoginDAO;
  }

}
