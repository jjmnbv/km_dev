package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.NwesVistingDAO;
import com.pltfm.app.service.NwesVistingService;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.NwesVisting;

/***
 * 拜访信息ServiceImpl
 */
@Component(value = "nwesVistingService")
public class NwesVistingServiceImpl implements NwesVistingService {
  @Resource(name = "nwesVistingDAO")
  private NwesVistingDAO nwesVistingDAO;

  /***
   * 
   * 删除拜访记录
   */
  @Override
public int delete(List<Integer> vistingIds) throws SQLException {
    Integer count = 0;
    if (ListUtils.isNotEmpty(vistingIds)) {
      for (Integer id : vistingIds) {
        NwesVisting nwesVisting = new NwesVisting();
        nwesVisting.setVistingId(id);
        count += nwesVistingDAO.delete(nwesVisting);
      }
    }
    return count;
  }

  /***
   * 
   * 添加拜访记录
   */
  @Override
public Integer insert(NwesVisting nwesVisting) throws SQLException {
    Object object = nwesVistingDAO.insert(nwesVisting);
    return (Integer) object;
  }

  /***
   * 
   * 跟据id查询拜访记录
   */
  @Override
public NwesVisting selectByPrimaryKey(Integer vistingId) throws SQLException {
    return nwesVistingDAO.selectByPrimaryKey(vistingId);
  }

  /***
   * 
   * 修改拜访记录
   */
  @Override
public Integer update(NwesVisting nwesVisting) throws SQLException {
    Object object = nwesVistingDAO.update(nwesVisting);
    return (Integer) object;
  }

  /**
   * 分页查询拜访记录
   * 
   * @param 拜访记录实体
   * @return
   * @throws Exception 异常
   */
  @Override
public Page searchPageByVo(Page pageParam, NwesVisting vo) throws Exception {
    if (pageParam == null) {
      pageParam = new Page();
    }
    if (vo == null) {
      vo = new NwesVisting();
    }
    int totalNum = nwesVistingDAO.selectCountByVo(vo);
    pageParam.setRecordCount(totalNum);
    vo.setSkip(pageParam.getStartIndex());
    vo.setMax(pageParam.getStartIndex() + pageParam.getPageSize());
    pageParam.setDataList(nwesVistingDAO.selectPageByVo(vo));
    return pageParam;
  }


  public NwesVistingDAO getNwesVistingDAO() {
    return nwesVistingDAO;
  }

  public void setNwesVistingDAO(NwesVistingDAO nwesVistingDAO) {
    this.nwesVistingDAO = nwesVistingDAO;
  }
}
