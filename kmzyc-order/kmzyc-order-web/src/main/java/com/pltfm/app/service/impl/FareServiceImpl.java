package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.FareTypeDAO;
import com.pltfm.app.entities.FareType;
import com.pltfm.app.entities.FareTypeExample;
import com.pltfm.app.entities.FareTypeExample.Criteria;
import com.pltfm.app.entities.FareTypeWithBLOBs;
import com.pltfm.app.service.FareService;
import com.pltfm.app.util.SysConstants;
import com.pltfm.sys.util.ErrorCode;

@Service("fareService")
@Scope("singleton")
@SuppressWarnings("unchecked")
public class FareServiceImpl implements FareService {
  private static Logger logger = Logger.getLogger(FareServiceImpl.class);
  private static short AVAILABLE = 0;

  @Resource
  private FareTypeDAO fareTypeDAO;

  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
  public BigDecimal getFare(Long typeId, BigDecimal sum, BigDecimal weight, boolean flag,
      String site) throws ServiceException {
    BigDecimal fare = null;
    try {
      FareType fareType = null;
      List<FareType> fareTypeList = new ArrayList<FareType>();
      FareTypeExample example = new FareTypeExample();
      Criteria criteria = example.createCriteria();
      // criteria.andType_idEqualTo(typeId);
      criteria.andSiteEqualTo(site);
      criteria.andDisabledEqualTo(AVAILABLE);

      fareTypeList = fareTypeDAO.selectByExampleWithoutBLOBs(example);
      if(fareTypeList.size()<1){
          logger.error("Disabled = "+AVAILABLE+"site = "+site+"查询表FARE_TYPE为空");
          throw new ServiceException(ErrorCode.INNER_FREIGHT_CALC_ERROR, "计算运费时发生异常：fareTypeList 为空！");
      }
      if (fareTypeList.size() > 1) {
        fareType = new FareType();
        fareType.setFreePrice(new BigDecimal(SysConstants.DEFAULT_FREE_PRICE));
      } else {
        fareType = fareTypeList.get(0);
      }

      // if (null == typeId) {
      // fareType = fareTypeDAO.selectByPrimaryKey(1L);
      // } else {
      // fareType = fareTypeDAO.selectByPrimaryKey(typeId);
      // }
      // if (fareType == null) {
      // fareType = new FareTypeWithBLOBs();
      // fareType.setFreePrice(new
      // BigDecimal(SysConstants.DEFAULT_FREE_PRICE));
      // }
      // 根据传入的订单金额进行判断，只区分大于等于fareType.getFreePrice()和小于fareType.getFreePrice()2种情况
      if (fareType.getFreePrice().compareTo(sum) <= 0) {
        // 如果订单金额大于或等于免运费金额，则返回的运费为0
        fare = BigDecimal.ZERO;
      } else {
        // 如果订单金额小于免运费金额，则返回的运费为第一条记录的首重运费
        fare = fareType.getFirstHeavyFreight();
      }
      /*
       * // 根据重量区间来计算运费 if (fareType.getFreePrice().compareTo(sum) > 0) { if
       * (weight.compareTo(fareSectionOne) <= 0) { fare = fareOne; } if
       * (weight.compareTo(fareSectionOne) > 0 && weight.compareTo(fareSectionTwo) <= 0) { fare =
       * fareTwo; } if (weight.compareTo(fareSectionTwo) > 0) { fare = fareOver; } } else { fare =
       * new BigDecimal(0); } FareTypeWithBLOBs fareType = fareTypeDAO.selectByPrimaryKey(typeId);
       * return getFare ( fareType, sum, weight );
       */
    } catch (Exception e) {
      logger.error("计算运费时发生异常", e);
      throw new ServiceException(ErrorCode.INNER_FREIGHT_CALC_ERROR, "计算运费时发生异常：" + e.getMessage());
    }
    return fare;

  }

  @SuppressWarnings("unused")
  private BigDecimal getFare(FareTypeWithBLOBs fareType, BigDecimal sum, BigDecimal weight) {
    return getFare(sum, weight, fareType.getFirstHeavy(), fareType.getContinuedHeavy(), fareType
        .getFirstHeavyFreight(), fareType.getContinuedHeavyFreight(), fareType.getFreePrice());
  };

  private BigDecimal getFare(BigDecimal sum, BigDecimal weight, BigDecimal firstWeight,
      BigDecimal continuWeight, BigDecimal firstPrice, BigDecimal continuPrice, BigDecimal freePrice) {
    return sum.compareTo(freePrice) == -1 ? (weight.compareTo(firstWeight) == 1 ? (firstPrice
        .add(continuPrice.multiply(weight.subtract(firstWeight).divide(continuWeight, 0,
            BigDecimal.ROUND_UP)))) : firstPrice) : BigDecimal.ZERO;
  }

}
