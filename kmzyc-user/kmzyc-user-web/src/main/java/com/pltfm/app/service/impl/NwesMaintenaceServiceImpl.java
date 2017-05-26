package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.NwesMaintenaceDAO;
import com.pltfm.app.service.NwesMaintenaceService;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.NwesMaintenace;

/***
 * 
 * 维护记录ServiceImpl
 */
@Component(value = "nwesMaintenaceService")
public class NwesMaintenaceServiceImpl implements NwesMaintenaceService {
  @Resource(name = "nwesMaintenaceDAO")
  private NwesMaintenaceDAO nwesMaintenaceDAO;

  /***
   * 
   * 删除维护记录
   */
  @Override
public int delete(List<Integer> maintenaceIds) throws SQLException {
    Integer count = 0;
    if (ListUtils.isNotEmpty(maintenaceIds)) {
      for (Integer id : maintenaceIds) {
        NwesMaintenace nwesMaintenace = new NwesMaintenace();
        nwesMaintenace.setMaintenaceId(id);
        count += nwesMaintenaceDAO.delete(nwesMaintenace);
      }
    }
    return count;
  }


  /***
   * 
   * 添加维护记录
   */
  @Override
public Integer insert(NwesMaintenace nwesMaintenace) throws SQLException {
    return nwesMaintenaceDAO.insert(nwesMaintenace);
  }

  /***
   * 
   * 跟据id查询维护记录
   */
  @Override
public NwesMaintenace selectByPrimaryKey(Integer maintenaceId) throws SQLException {
    return nwesMaintenaceDAO.selectByPrimaryKey(maintenaceId);
  }

  /***
   * 
   * 修改维护记录
   */
  @Override
public Integer update(NwesMaintenace nwesMaintenace) throws SQLException {
    return nwesMaintenaceDAO.update(nwesMaintenace);
  }

  /**
   * 分页查询维护记录
   * 
   * @param 维护记录实体
   * @return
   * @throws Exception 异常
   */
  @Override
public Page searchPageByVo(Page pageParam, NwesMaintenace vo) throws Exception {
    if (pageParam == null) {
      pageParam = new Page();
    }
    if (vo == null) {
      vo = new NwesMaintenace();
    }
    int totalNum = nwesMaintenaceDAO.selectCountByVo(vo);
    pageParam.setRecordCount(totalNum);
    vo.setSkip(pageParam.getStartIndex());
    vo.setMax(pageParam.getStartIndex() + pageParam.getPageSize());
    pageParam.setDataList(nwesMaintenaceDAO.selectPageByVo(vo));
    return pageParam;

  }

  public NwesMaintenaceDAO getNwesMaintenaceDAO() {
    return nwesMaintenaceDAO;
  }


  public void setNwesMaintenaceDAO(NwesMaintenaceDAO nwesMaintenaceDAO) {
    this.nwesMaintenaceDAO = nwesMaintenaceDAO;
  }

}
