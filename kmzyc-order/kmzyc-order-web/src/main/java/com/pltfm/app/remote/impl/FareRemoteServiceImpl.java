package com.pltfm.app.remote.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.order.remote.FareRemoteService;
import com.pltfm.app.entities.FareType;
import com.pltfm.app.service.FareService;
import com.pltfm.app.service.FareTypeService;
import com.pltfm.app.service.OrderOperateStatementService;
import com.pltfm.app.vobject.FareTypeVo;
import com.pltfm.sys.util.ErrorCode;

@Service("fareRemoteService")
@SuppressWarnings("unchecked")
public class FareRemoteServiceImpl implements FareRemoteService {

  private static final long serialVersionUID = 5812195815095440308L;
  @Resource
  FareService fareService;
  @Resource
  FareTypeService fareTypeService;
  @Resource
  private OrderOperateStatementService orderOperateStatementService;

  @Override
  public FareType getFareType(Long id) throws ServiceException {
    FareType ft = null;
    try {
      ft = fareTypeService.getById(id);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_FREIGHT_QUERY_ERROR, "获得运费配置 id查询："
          + e.getMessage());
    }
    return ft;
  }

  @Override
  public List getAllFare() throws ServiceException {
    List list = null;
    try {
      list = fareTypeService.list();
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_FREIGHT_QUERY_ERROR, "获得所有运费配置出错："
          + e.getMessage());
    }
    return list;
  }

  @Override
  public BigDecimal getFare(FareTypeVo fareTypeVo) throws ServiceException {
    Long typeId = fareTypeVo.getType_id();
    BigDecimal sum = fareTypeVo.getSum();
    BigDecimal weight = fareTypeVo.getWeight();
    boolean flag = fareTypeVo.getIsInGDProvince();
    String site = fareTypeVo.getSite();
    BigDecimal bd = null;
    try {
      bd = fareService.getFare(typeId, sum, weight, flag, site);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INTER_FREIGHT_CALC_ERROR, "计算运费出错：" + e.getMessage());
    }
    return bd;
  }

  @Override
  public Boolean changeLogisticsFee(String orderCode, String account, BigDecimal newFare)
      throws ServiceException {
    return orderOperateStatementService.updateFare(orderCode, account, newFare);
  }
}
