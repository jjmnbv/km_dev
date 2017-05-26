package com.kmzyc.promotion.app.service;

import java.util.List;
import java.util.Map;

import com.kmzyc.promotion.app.vobject.Composition;
import com.kmzyc.promotion.exception.ServiceException;

/**
 * 套餐service
 * 
 * @author xlg
 * 
 */
public interface CompositionService {
  /**
   * 查询套餐
   * 
   * @param suitId
   * @return
   * @throws ServiceException
   */
  public Composition getComposition(Long suitId, Integer amount) throws ServiceException;

  /**
   * 批量查询套餐
   * 
   * @param suitIds
   * @return
   * @throws ServiceException
   */
  public List<Composition> queryBacthComposition(Map<Long, Integer> suitIds)
      throws ServiceException;
}
