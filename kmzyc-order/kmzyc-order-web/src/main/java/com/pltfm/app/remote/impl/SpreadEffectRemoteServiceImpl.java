/*
 * 删除微商业务 package com.pltfm.app.remote.impl;
 * 
 * import java.math.BigDecimal; import java.util.List; import java.util.Map;
 * 
 * import javax.annotation.Resource;
 * 
 * import org.springframework.context.annotation.Scope; import
 * org.springframework.stereotype.Service;
 * 
 * import com.pltfm.app.remote.SpreadEffectRemoteService; import
 * com.pltfm.app.service.SpreadEffectService; import com.pltfm.app.vobject.SpreadEffect; import
 * com.kmzyc.commons.exception.ServiceException; import com.pltfm.sys.util.ErrorCode;
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @Service("spreadEffectRemoteService")
 * 
 * @Scope("singleton") public class SpreadEffectRemoteServiceImpl implements
 * SpreadEffectRemoteService {
 * 
 *//**
	 * 
	 *//*
  private static final long serialVersionUID = -333027008275994538L;
  @Resource
  private SpreadEffectService spreadEffectService;

  @Override
  public BigDecimal saveSpreadResultRecode(SpreadEffect vo) throws ServiceException {
    try {
      return spreadEffectService.saveSpreadResultRecode(vo);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_ORDER_DETAIL_ERROR, "查询推广效果记录时发生异常："
          + e.getMessage());
    }

  }

  @Override
  public int updateSpreadResultRecode(SpreadEffect vo) throws ServiceException {
    try {
      return spreadEffectService.updateSpreadResultRecode(vo);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_ORDER_DETAIL_ERROR, "更改推广效果记录时发生异常："
          + e.getMessage());
    }
  }

  @Override
  public List<SpreadEffect> querySpreadResultRecode(Map map) throws ServiceException {
    try {
      return spreadEffectService.querySpreadResultRecode(map);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_ORDER_DETAIL_ERROR, "查询推广效果记录时发生异常："
          + e.getMessage());
    }
  }

  @Override
  public List<SpreadEffect> querySpreadDetail(Map map) throws ServiceException {
    try {
      return spreadEffectService.querySpreadDetail(map);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_ORDER_DETAIL_ERROR, "更改推广效果详情时发生异常："
          + e.getMessage());
    }
  }

}
*/
