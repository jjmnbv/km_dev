package com.pltfm.crowdsourcing.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.km.commons.general.model.Page;
import com.km.crowdsourcing.model.InstitutionInfo;
import com.km.crowdsourcing.service.InstitutionInfoService;
import com.kmzyc.promotion.exception.ServiceException;
import com.pltfm.app.dao.LoginInfoDAO;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.crowdsourcing.dao.InstitutionInfoDao;


/**
 * 
 * @ClassName: InstitutionInfoServiceImpl
 * @Description: 机构信息接口实现类
 * @author xys
 * @date 2016年3月17日
 * @version 1.0
 */
@Service(value = "institutionInfoService")
public class InstitutionInfoServiceImpl implements InstitutionInfoService {
  private Logger logger = LoggerFactory.getLogger(InstitutionInfoServiceImpl.class);
  @Resource
  InstitutionInfoDao institutionInfoDao;
  @Resource
  LoginInfoDAO loginInfoDao;

  @Override
  public Page<Map<String, Object>> queryInsInfoList(Page<Map<String, Object>> page) {
    try {
      List<InstitutionInfo> list = institutionInfoDao.queryInsInfoList(page);
      if (page.getTotalPage() == 0) {
        Integer count = institutionInfoDao.queryInsInfoListCount(page);
        page.setTotalRecords(count.intValue());
      }
      page.setRecordList(list);
      return page;
    } catch (Exception e) {
      logger.error("page:{},查询机构信息异常", page, e);
      return page;
    }
  }

  @Override
  public InstitutionInfo queryInsInfoByInsCode(String institutionCode) {
    InstitutionInfo institutionInfo = null;
    try {
      institutionInfo = institutionInfoDao.queryInsInfoByInsCode(institutionCode);
      return institutionInfo;
    } catch (Exception e) {
      logger.error("institutionCode:{},通过机构编码查询机构信息异常", institutionCode, e);
      return institutionInfo;
    }
  }


  @Override
  public int insertInsInfo(InstitutionInfo institutionInfo) throws ServiceException {
    try {
      int sesultCount = institutionInfoDao.insertInsInfo(institutionInfo);
      return sesultCount;
    } catch (Exception e) {
      logger.error("institutionCode:{},插入机构信息异常", institutionInfo.getInstitutionCode(), e);
      return 0;
    }
  }

  @Override
  public int updateInsInfo(InstitutionInfo institutionInfo) {
    try {
      int sesultCount = institutionInfoDao.updateInsInfo(institutionInfo);
      if (institutionInfo.getMobile() != null && !institutionInfo.getMobile().isEmpty()) {
        LoginInfo l = new LoginInfo();
        l.setN_LoginId(Integer.valueOf(institutionInfo.getLoginId().toString()));
        l.setMobile(institutionInfo.getMobile());
        loginInfoDao.updateByPrimaryKey(l);
      }
      return sesultCount;
    } catch (Exception e) {
      logger.error("institutionCode:{},修改机构信息异常", institutionInfo.getInstitutionCode(), e);
      return 0;
    }
  }

  @Override
  public void deleteByPrimaryKey(InstitutionInfo institutionInfo) {
    try {
      // institutionInfoDao.deleteByPrimaryKey(institutionInfo.getId());
    } catch (Exception e) {
      logger.error("institutionCode:{},修改机构信息异常", institutionInfo.getInstitutionCode(), e);
    }
  }

  @Override
  public Page<InstitutionInfo> searchPageByVo(Page<InstitutionInfo> pageParam,
      InstitutionInfo institutionInfo) throws SQLException {
    if (pageParam == null) {
      pageParam = new Page<>();
    }
    if (institutionInfo == null) {
      institutionInfo = new InstitutionInfo();
    }
    pageParam.setQueryCondition(institutionInfo);
    int totalNum = institutionInfoDao.selectCountByVo(pageParam);
    if (totalNum < pageParam.getPageSize()) {
      pageParam.setPageIndex(1);
    }
    pageParam.setTotalRecords(totalNum);
    pageParam.setRecordList(institutionInfoDao.selectPageByVo(pageParam));
    return pageParam;
  }

  @Override
  public InstitutionInfo getInstitutionInfo(Long id) throws SQLException {
    return institutionInfoDao.getInstitutionInfo(id);
  }

}
