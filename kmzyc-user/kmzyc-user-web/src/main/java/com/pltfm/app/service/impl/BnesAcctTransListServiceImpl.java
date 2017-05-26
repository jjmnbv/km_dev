package com.pltfm.app.service.impl;



import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.BnesAcctTransListDAO;
import com.pltfm.app.dataobject.BnesAcctTransListDO;
import com.pltfm.app.service.BnesAcctTransListService;
import com.pltfm.app.vobject.BnesAcctTransListQuery;

/**
 * 数据访问对象实现类
 * 
 * @since 2013-07-17
 */
@Service(value = "bnesAcctTransListService")
public class BnesAcctTransListServiceImpl implements BnesAcctTransListService {
  @Resource(name = "bnesAcctTransListDAO")
  private BnesAcctTransListDAO bnesAcctTransListDAO;


  /**
   * 分页查询查询
   * 
   * @param bnesAcctTransactionDO
   * @return
   */
  @Override
  public Page findListByPageExample(Page pageParam, BnesAcctTransListQuery bnesAcctTransListQuery)
      throws DataAccessException {

    if (pageParam == null) {
      pageParam = new Page();
    }
    if (bnesAcctTransListQuery == null) {
      bnesAcctTransListQuery = new BnesAcctTransListQuery();
    }
    int total = bnesAcctTransListDAO.countBnesAcctTransListQueryByExample(bnesAcctTransListQuery);


    pageParam.setRecordCount(total);
    // 设置查询开始结束索引

    int max = pageParam.getStartIndex() + pageParam.getPageSize();
    bnesAcctTransListQuery.setMixNum(pageParam.getStartIndex());
    bnesAcctTransListQuery.setMaxNum(max);

    pageParam.setDataList(bnesAcctTransListDAO.findListByExample(bnesAcctTransListQuery));

    return pageParam;
  }

  /**
   * 插入数据
   * 
   * @param bnesAcctTransListDO
   * @return 插入数据的主键
   */
  @Override
  public Integer insertBnesAcctTransListDO(BnesAcctTransListDO bnesAcctTransListDO)
      throws DataAccessException {
    // TODO Auto-generated method stub
    return bnesAcctTransListDAO.insertBnesAcctTransListDO(bnesAcctTransListDO);
  }


  // ------------------------------------------------------------------------------------------------
  /*
   * 
   * @Override public Integer countBnesAcctTransListDOByExample( BnesAcctTransListDO
   * bnesAcctTransListDO) throws DataAccessException { // TODO Auto-generated method stub return
   * null; }
   * 
   * @Override public Integer countBnesAcctTransListQueryByExample( BnesAcctTransListQuery
   * bnesAcctTransListQuery) throws DataAccessException { // TODO Auto-generated method stub return
   * null; }
   * 
   * @Override public Integer updateBnesAcctTransListDO( BnesAcctTransListDO bnesAcctTransListDO)
   * throws DataAccessException { // TODO Auto-generated method stub return null; }
   * 
   * @Override public List<BnesAcctTransListDO> findListByExample( BnesAcctTransListDO
   * bnesAcctTransListDO) throws DataAccessException { // TODO Auto-generated method stub return
   * null; }
   * 
   * @Override public BnesAcctTransListDO findBnesAcctTransListDOByPrimaryKey( Integer transListId)
   * throws DataAccessException { // TODO Auto-generated method stub return null; }
   * 
   * @Override public Integer deleteBnesAcctTransListDOByPrimaryKey(Integer transListId) throws
   * DataAccessException { // TODO Auto-generated method stub return null; }
   */

  public BnesAcctTransListDAO getBnesAcctTransListDAO() {
    return bnesAcctTransListDAO;
  }


  public void setBnesAcctTransListDAO(BnesAcctTransListDAO bnesAcctTransListDAO) {
    this.bnesAcctTransListDAO = bnesAcctTransListDAO;
  }

  @Override
  public List<BnesAcctTransListQuery> queryAllBnesAcctTransList(
      BnesAcctTransListQuery bnesAcctTransListQuery) throws SQLException {
    // TODO Auto-generated method stub
    return bnesAcctTransListDAO.queryAllBnesAcctTransList(bnesAcctTransListQuery);
  }


}
