package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.MdicalExcusieInfoDAO;
import com.pltfm.app.service.MdicalExcusieInfoService;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.MdicalExcusieInfo;

@Component(value = "mdicalExcusieInfoService")
public class MdicalExcusieInfoServiceImpl implements MdicalExcusieInfoService {
  @Resource(name = "mdicalExcusieInfoDAO")
  private MdicalExcusieInfoDAO mdicalExcusieInfoDAO;

  public MdicalExcusieInfoDAO getMdicalExcusieInfoDAO() {
    return mdicalExcusieInfoDAO;
  }

  public void setMdicalExcusieInfoDAO(MdicalExcusieInfoDAO mdicalExcusieInfoDAO) {
    this.mdicalExcusieInfoDAO = mdicalExcusieInfoDAO;
  }

  /**
   * 添加医属信息
   * 
   * @param 医属实体
   * @return 返回值
   * @throws Exception异常
   */
  @Override
public Integer addMdicalExcusieInfo(MdicalExcusieInfo mdicalExcusieInfo) throws SQLException {
    // TODO Auto-generated method stub
    return mdicalExcusieInfoDAO.insertMdicalExcusieInfo(mdicalExcusieInfo);
  }

  /**
   * 删除医属信息
   * 
   * @param 医属信息实体
   * @return 返回值
   * @throws Exception异常
   */
  @Override
public Integer deleteMdicalExcusieInfo(List<String> n_mmeids) throws SQLException {
    // TODO Auto-generated method stub
    Integer count = 0;
    if (ListUtils.isNotEmpty(n_mmeids)) {
      for (String id : n_mmeids) {
        MdicalExcusieInfo mdicalExcusieInfo = new MdicalExcusieInfo();
        mdicalExcusieInfo.setN_medicalMattersExclusive_id(Integer.parseInt(id));
        count += mdicalExcusieInfoDAO.deleteMdicalExcusieInfo(mdicalExcusieInfo);
      }
    }
    return count;
  }

  /**
   * 分页查询医务信息
   * 
   * @param 医务信息实体
   * @return
   * @throws Exception 异常
   */
  @Override
public Page searchPageByVo(Page pageParam, MdicalExcusieInfo mdicalExcusieInfo) throws Exception {
    if (pageParam == null) {
      pageParam = new Page();
    }
    if (mdicalExcusieInfo == null) {
      mdicalExcusieInfo = new MdicalExcusieInfo();
    }
    int totalNum = mdicalExcusieInfoDAO.selectCountByVo(mdicalExcusieInfo);
    pageParam.setRecordCount(totalNum);
    mdicalExcusieInfo.setSkip(pageParam.getStartIndex());
    mdicalExcusieInfo.setMax(pageParam.getStartIndex() + pageParam.getPageSize());

    pageParam.setDataList(mdicalExcusieInfoDAO.selectPageByVo(mdicalExcusieInfo));
    return pageParam;
  }


  /**
   * 修改医属基本信息
   * 
   * @param 医属基本信息实体
   * @return 返回值
   * @throws Exception异常
   */
  @Override
public Integer udpateMdicalExcusieInfo(MdicalExcusieInfo mdicalExcusieInfo) throws SQLException {
    // TODO Auto-generated method stub
    return mdicalExcusieInfoDAO.udpateMdicalExcusieInfo(mdicalExcusieInfo);
  }

  /**
   * 跟据个人id查询医务专属信息
   * 
   * @param MdicalExcusieInfo 医务专属信息实体
   * @return 返回值
   * @throws SQLException sql异常
   */
  @Override
public MdicalExcusieInfo getPersonal_id(Integer n_personal_id) throws SQLException {
    return mdicalExcusieInfoDAO.getPersonal_id(n_personal_id);
  }
}
