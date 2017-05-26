package com.pltfm.crowdsourcing.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.km.commons.general.exception.KmServiceException;
import com.km.commons.general.model.ResultData;
import com.km.crowdsourcing.common.CommonEnum;
import com.km.crowdsourcing.model.Bagman;
import com.km.crowdsourcing.service.BagmanService;
import com.pltfm.crowdsourcing.dao.BagmanDao;

/**
 * 
 * @ClassName: BagManServiceImpl
 * @Description: 众包业务员service实现类
 * @author weijl
 * @date 2016年3月15日 下午3:24:30
 * @version 1.0
 */
@Component(value = "bagmanService")
public class BagmanServiceImpl implements BagmanService {

  @Resource
  private BagmanDao bagmanDao;

  @SuppressWarnings("rawtypes")
  @Override
  public ResultData saveBagMan(Bagman bagman) throws KmServiceException {
    ResultData ResultData = new ResultData();
    try {
      bagmanDao.insert(bagman);
      ResultData.setCode("1");
      ResultData.setMessage("业务员信息保存成功！");
    } catch (Exception e) {
      ResultData.setCode("0");
      ResultData.setMessage("业务员信息保存异常，请联系管理员！");
      throw new KmServiceException(e);
    }
    return ResultData;
  }

  @Override
  public List<Map<String, Object>> isBmanExists(Bagman bagman) throws KmServiceException {
    try {
      return bagmanDao.existsBmans(bagman);
    } catch (Exception e) {
      throw new KmServiceException(e);
    }
  }

  @Override
  public List<Bagman> listBagMans(Bagman bagman) throws KmServiceException {
    List<Bagman> bmans = null;
    try {
      bmans = bagmanDao.listBagMans(bagman);
    } catch (Exception e) {
      throw new KmServiceException(e);
    }
    return bmans;
  }

  @Override
  public int countBagMans(Bagman bagman) throws KmServiceException {
    try {
      return bagmanDao.countBagMans(bagman);
    } catch (Exception e) {
      throw new KmServiceException(e);
    }
  }


  @Override
  public ResultData<Bagman> login(Bagman bagman) {
    Bagman bg = bagmanDao.selectByBagman(bagman);
    ResultData<Bagman> data = new ResultData<Bagman>();
    if (bg == null) {
      data.setCode(CommonEnum.LoginResultType.nonentity.name());
    } else if (bg.getStatus() == null || bg.getStatus().intValue() == 0) {
      data.setCode(CommonEnum.LoginResultType.invalid.name());
    } else {
      data.setCode(CommonEnum.LoginResultType.success.name());
      data.setData(bg);
    }
    return data;
  }

  @Override
  public List<Map<String, Object>> ajaxBagManInfos() throws KmServiceException {
    try {
      return bagmanDao.ajaxBagManInfos();
    } catch (Exception e) {
      throw new KmServiceException(e);
    }
  }

  public Bagman selectByPrimaryKey(Long id) throws KmServiceException {
    try {
      return bagmanDao.selectByPrimaryKey(id);
    } catch (Exception e) {
      throw new KmServiceException(e);
    }
  }

  public int updateByPrimaryKeySelective(Bagman bagman) throws KmServiceException {
    try {
      return bagmanDao.updateByPrimaryKeySelective(bagman);
    } catch (Exception e) {
      throw new KmServiceException(e);
    }
  }

}
