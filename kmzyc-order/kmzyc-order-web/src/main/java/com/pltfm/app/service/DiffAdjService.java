package com.pltfm.app.service;

import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.DiffAdj;
import com.pltfm.app.entities.DiffAdjCriteria;
import com.pltfm.app.entities.DiffAdjExample;

@SuppressWarnings("unchecked")
public interface DiffAdjService {

  /**
   * 差异调整明细記錄數查詢
   * 
   * @param example
   * @return
   * @throws ServiceException
   */
  int countByExample(DiffAdjExample example) throws ServiceException;

  /**
   * 删除差异调整明细
   * 
   * @param example
   * @return
   * @throws ServiceException
   */
  int deleteByExample(DiffAdjExample example) throws ServiceException;

  /**
   * 新增差异调整明细
   * 
   * @param record
   * @return
   * @throws ServiceException
   */
  Long insert(DiffAdj record) throws ServiceException;

  /**
   * 新增差异调整明细
   * 
   * @param record
   * @return
   * @throws ServiceException
   */
  Long insertSelective(DiffAdj record) throws ServiceException;

  /**
   * 查询差异调整明细
   * 
   * @param example
   * @return
   * @throws ServiceException
   */
  List selectByExample(DiffAdjExample example) throws ServiceException;

  /**
   * 修改差异调整明细
   * 
   * @param record
   * @param example
   * @return
   * @throws ServiceException
   */
  int updateByExample(DiffAdj record, DiffAdjExample example) throws ServiceException;

  /**
   * 查 询 差异调整明细 加用户名
   * 
   * @param example
   * @return
   * @throws ServiceException
   */
  public List selectDiffAdjDetail(DiffAdjExample example) throws ServiceException;

  /**
   * 查询 差异调整明细 列表
   * 
   * @param criteria
   * @return
   * @throws ServiceException
   */
  public List<DiffAdj> selectDiffAdjList(DiffAdjCriteria criteria) throws ServiceException;

  /**
   * 查询 差异调整明细 数量
   * 
   * @param criteria
   * @return
   * @throws ServiceException
   */
  public int selectDiffAdjSize(DiffAdjCriteria criteria) throws ServiceException;

  /**
   * 导出excel
   * 
   * @param settlementNo 结算单
   * @param type 类型 1:妥投商品明细，2:运费明细,3:退款明细,4:差异调整明细
   * @return
   * @throws ServiceException
   */
  public String exportExcel(String settlementNo, int type, List data) throws ServiceException;

  /**
   * 查询文件路径
   * 
   * @param settlementNo
   * @param type
   * @return 返回
   */
  String getFilePath(String settlementNo, int type);

  /**
   * 差异调整明细导出
   * 
   * @param sno
   * @param filePath
   * @param diffAdjCriteria
   * @return
   * @throws ServiceException
   */
  public String diffAdjExport(String sno, String filePath, DiffAdjCriteria diffAdjCriteria)
      throws ServiceException;

  /**
   * 差异调整金额汇总
   * 
   * @param diffAdjCriteria
   * @return
   * @throws ServiceException
   */
  public Map selectSumDiff(DiffAdjCriteria diffAdjCriteria) throws ServiceException;
}
