package com.pltfm.crowdsourcing.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.km.crowdsourcing.model.InstitutionImage;
import com.km.crowdsourcing.service.InstitutionImageService;
import com.pltfm.crowdsourcing.dao.InstitutionImageDao;


/**
 * 
 * @ClassName: InstitutionApplyRecordServiceImpl
 * @Description:图片接口实现类
 * @author xys
 * @date 2016年3月18日
 * @version 1.0
 */
@Service(value = "institutionImageService")
public class InstitutionImageServiceImpl implements InstitutionImageService {
  private Logger logger = LoggerFactory.getLogger(InstitutionImageServiceImpl.class);

  @Resource
  private InstitutionImageDao institutionImageDao;

  @Override
  public int insert(InstitutionImage record) throws Exception {
    try {
      return institutionImageDao.insert(record);

    } catch (Exception e) {
      logger.error("record:{},插入图片信息异常", record, e);
    }
    return 0;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void bathInsert(List<InstitutionImage> recordList) throws Exception {
    institutionImageDao.bathInsert(recordList);
  }

  @Override
  public void deleteByInsAppId(Long insAppId) throws Exception {
    try {
      institutionImageDao.deleteByInsAppId(insAppId);
    } catch (Exception e) {
      logger.error("insAppId:{},删除图片信息异常", insAppId, e);
    }

  }

  @Override
  public List<InstitutionImage> selectByInsAppId(Long insAppId) throws Exception {
    List<InstitutionImage> InstitutionImageList = null;
    try {
      InstitutionImageList = institutionImageDao.selectByInsAppId(insAppId);
    } catch (Exception e) {
      logger.error("insAppId:{},查询图片信息异常", insAppId, e);
    }
    return InstitutionImageList;
  }

  @Override
  public List<InstitutionImage> selectByInstitutionInfoId(Long id) throws Exception {
    List<InstitutionImage> InstitutionImageList = null;
    try {
      InstitutionImageList = institutionImageDao.selectByInstitutionInfoId(id);
    } catch (Exception e) {
      logger.error("insAppId:{},查询图片信息异常", id, e);
    }
    return InstitutionImageList;
  }

  @Override
  public void updateById(InstitutionImage im) throws Exception {
    institutionImageDao.updateById(im);

  }
}
