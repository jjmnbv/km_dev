package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.NwesCsReplyDAO;
import com.pltfm.app.dao.NwesCustomServiceDAO;
import com.pltfm.app.service.NwesCustomServiceService;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.NwesCustomService;

/**
 * 服务信息处理
 * 
 * @author gwl
 * @since 2013-07-08
 */
@Component(value = "nwesCustomServiceService")
public class NwesCustomServiceServiceImpl implements NwesCustomServiceService {
  @Resource(name = "nwesCustomServiceDAO")
  private NwesCustomServiceDAO nwesCustomServiceDAO;
  @Resource(name = "nwesCsReplyDAO")
  private NwesCsReplyDAO nwesCsReplyDAO;


  /***
   * 
   * 删除服务信息
   */
  @Override
@Transactional(rollbackFor = Exception.class)
  public int delete(List<Integer> customServiceIds) throws SQLException {
    Integer count = 0;
    if (ListUtils.isNotEmpty(customServiceIds)) {
      for (Integer id : customServiceIds) {
        NwesCustomService nwesCustomService = new NwesCustomService();

        nwesCustomService.setCustomServiceId(id);
        nwesCsReplyDAO.deleteCustomerSurveyId(id);// 服务主键删除回复信息
        count += nwesCustomServiceDAO.delete(nwesCustomService);
      }
    }
    return count;
  }


  /***
   * 
   * 添加服务信息
   */
  @Override
public Integer insert(NwesCustomService nwesCustomService) throws SQLException {
    return nwesCustomServiceDAO.insert(nwesCustomService);
  }

  /***
   * 
   * 跟据id查询服务信息
   */
  @Override
public NwesCustomService selectByPrimaryKey(Integer customServiceId) throws SQLException {
    return nwesCustomServiceDAO.selectByPrimaryKey(customServiceId);
  }

  /***
   * 
   * 修改服务信息
   */
  @Override
public Integer update(NwesCustomService nwesCustomService) throws SQLException {
    return nwesCustomServiceDAO.update(nwesCustomService);
  }

  /**
   * 分页查询服务信息信息
   * 
   * @param vo
   * @throws Exception 异常
   */
  @Override
public Page searchPageByVo(Page pageParam, NwesCustomService vo) throws Exception {
    if (pageParam == null) {
      pageParam = new Page();
    }
    if (vo == null) {
      vo = new NwesCustomService();
    }
    int totalNum = nwesCustomServiceDAO.selectCountByVo(vo);
    pageParam.setRecordCount(totalNum);
    vo.setSkip(pageParam.getStartIndex());
    vo.setMax(pageParam.getStartIndex() + pageParam.getPageSize());
    pageParam.setDataList(nwesCustomServiceDAO.selectPageByVo(vo));
    return pageParam;
  }

  public NwesCustomServiceDAO getNwesCustomServiceDAO() {
    return nwesCustomServiceDAO;
  }

  public void setNwesCustomServiceDAO(NwesCustomServiceDAO nwesCustomServiceDAO) {
    this.nwesCustomServiceDAO = nwesCustomServiceDAO;
  }

  public NwesCsReplyDAO getNwesCsReplyDAO() {
    return nwesCsReplyDAO;
  }


  public void setNwesCsReplyDAO(NwesCsReplyDAO nwesCsReplyDAO) {
    this.nwesCsReplyDAO = nwesCsReplyDAO;
  }

}
