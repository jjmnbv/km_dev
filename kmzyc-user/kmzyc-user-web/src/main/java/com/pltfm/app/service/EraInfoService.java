package com.pltfm.app.service;

import com.kmzyc.b2b.vo.EraInfo;
import com.kmzyc.commons.page.Page;


public interface EraInfoService {
  /**
   * 分页查询时代会员
   * 
   * @param page
   * @return
   * @throws Exception
   */
  Page selectEraInfoList(Page page, EraInfo eraInfo) throws Exception;

  /**
   * 根据时代主键id查询时代信息
   * 
   * @param eraInfoId
   * @return
   * @throws Exception
   */
  EraInfo selectEraInfoDetail(Long eraInfoId) throws Exception;

  /**
   * 更新时代会员信息
   * 
   * @param eraInfo
   * @return
   * @throws Exception 0:参数异常 erainfoId，eraNo必传 1：成功
   */
  int updateEraInfo(EraInfo eraInfo) throws Exception;
  
  /**
   * 根据eraNo查询时代会员信息
   * @param eraNo
   * @return
   * @throws Exception
   */
  EraInfo selectEraInfoDetail(String eraNo) throws Exception ;

}
