/*
 * 删除cps业务 package com.pltfm.app.service.impl;
 * 
 * import java.util.List; import java.util.Map;
 * 
 * import javax.annotation.Resource;
 * 
 * import org.springframework.context.annotation.Scope; import
 * org.springframework.stereotype.Service;
 * 
 * import com.pltfm.app.dao.CpsQueryDao; import com.pltfm.app.service.CpsQueryService; import
 * com.kmzyc.commons.exception.ServiceException; import com.pltfm.sys.util.ErrorCode;
 * 
 *//**
 * cps 查询Service
 * 
 * @author xlg
 * 
 *//*
@Service("cpsQueryService")
@Scope("singleton")
@SuppressWarnings("unchecked")
public class CpsQueryServiceImpl extends BaseService implements CpsQueryService {
  @Resource
  private CpsQueryDao cpsQueryDao;

  *//**
   * 查询CPS跳转数据
   * 
   * @return
   * @throws ServiceException
   *//*
  public List queryCPSTrackInfo(Map<String, String> paramsMap) throws ServiceException {
    try {
      return cpsQueryDao.queryCPSTrackInfo(paramsMap);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_FREIGHT_QUERY_ERROR, "查询CPS跳转数据发生异常", e);
    }
  }

  *//**
   * 查询CPS跳转数据条数
   * 
   * @return
   * @throws ServiceException
   *//*
  public Integer queryCPSTrackInfoCount(Map<String, String> paramsMap) throws ServiceException {
    try {
      return cpsQueryDao.queryCPSTrackInfoCount(paramsMap);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_FREIGHT_QUERY_ERROR, "查询CPS跳转数据条数发生异常", e);
    }
  }

  *//**
   * CPS订单标识信息数据
   * 
   * @param paramsMap
   * @return
   * @throws ServiceException
   *//*
  public List queryCPSOrderFlag(Map<String, String> paramsMap) throws ServiceException {
    try {
      return cpsQueryDao.queryCPSOrderFlag(paramsMap);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_FREIGHT_QUERY_ERROR, "CPS订单标识信息数据发生异常", e);
    }
  }

  *//**
   * CPS订单标识信息条数
   * 
   * @param paramsMap
   * @return
   * @throws ServiceException
   *//*
  public Integer queryCPSOrderFlagCount(Map<String, String> paramsMap) throws ServiceException {
    try {
      return cpsQueryDao.queryCPSOrderFlagCount(paramsMap);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_FREIGHT_QUERY_ERROR, "CPS订单标识信息条数发生异常", e);
    }
  }

  *//**
   * CPS请求明细数据
   * 
   * @param paramsMap
   * @return
   * @throws ServiceException
   *//*
  public List queryCPSRequestInfo(Map<String, String> paramsMap) throws ServiceException {
    try {
      return cpsQueryDao.queryCPSRequestInfo(paramsMap);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_FREIGHT_QUERY_ERROR, "CPS请求明细数据发生异常", e);
    }
  }

  *//**
   * CPS请求明细条数
   * 
   * @param paramsMap
   * @return
   * @throws ServiceException
   *//*
  public Integer queryCPSRequestInfoCount(Map<String, String> paramsMap) throws ServiceException {
    try {
      return cpsQueryDao.queryCPSRequestInfoCount(paramsMap);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_FREIGHT_QUERY_ERROR, "CPS请求明细条数发生异常", e);
    }
  }

  *//**
   * CPS订单数据
   * 
   * @param paramsMap
   * @return
   * @throws ServiceException
   *//*
  public List queryCPSOrderInfo(Map<String, String> paramsMap) throws ServiceException {
    try {
      return cpsQueryDao.queryCPSOrderInfo(paramsMap);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_FREIGHT_QUERY_ERROR, "CPS订单数据发生异常", e);
    }
  }

  *//**
   * CPS订单数据条数
   * 
   * @param paramsMap
   * @return
   * @throws ServiceException
   *//*
  public Integer queryCPSOrderInfoCount(Map<String, String> paramsMap) throws ServiceException {
    try {
      return cpsQueryDao.queryCPSOrderInfoCount(paramsMap);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_FREIGHT_QUERY_ERROR, "CPS订单数据条数发生异常", e);
    }
  }
}
*/
