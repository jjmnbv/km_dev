package com.pltfm.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.BnesAcctTransactionDAO;
import com.pltfm.app.service.BnesAcctTransactionService;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;

/**
 * 数据访问对象实现类
 * 
 * @since 2013-07-17
 */
@Service(value = "bnesAcctTransactionService")
public class BnesAcctTransactionServiceImpl implements BnesAcctTransactionService {

  @Resource(name = "bnesAcctTransactionDAO")
  private BnesAcctTransactionDAO bnesAcctTransactionDAO;

  public BnesAcctTransactionDAO getBnesAcctTransactionDAO() {
    return bnesAcctTransactionDAO;
  }

  public void setBnesAcctTransactionDAO(BnesAcctTransactionDAO bnesAcctTransactionDAO) {
    this.bnesAcctTransactionDAO = bnesAcctTransactionDAO;
  }

  /**
   * 查询
   * 
   * @param bnesAcctTransactionDO
   * @return
   */

  @Override
  public List<BnesAcctTransactionQuery> findListByExample(
      BnesAcctTransactionQuery bnesAcctTransactionQuery) throws DataAccessException {
    // TODO Auto-generated method stub
    return bnesAcctTransactionDAO.findListByExample(bnesAcctTransactionQuery);
  }

  /**
   * 分页查询查询
   * 
   * @param bnesAcctTransactionDO
   * @return
   */
  @Override
  public Page findListByPageExample(Page pageParam,
      BnesAcctTransactionQuery bnesAcctTransactionQuery) throws Exception {

    if (pageParam == null) {
      pageParam = new Page();
    }
    if (bnesAcctTransactionQuery == null) {
      bnesAcctTransactionQuery = new BnesAcctTransactionQuery();
    }
    int total =
        bnesAcctTransactionDAO.countBnesAcctTransactionQueryByExample(bnesAcctTransactionQuery);

    pageParam.setRecordCount(total);
    // 设置查询开始结束索引

    int max = pageParam.getStartIndex() + pageParam.getPageSize();
    bnesAcctTransactionQuery.setMixNum(pageParam.getStartIndex());
    bnesAcctTransactionQuery.setMaxNum(max);

    pageParam.setDataList(bnesAcctTransactionDAO.findListByPageExample(bnesAcctTransactionQuery));

    return pageParam;
  }


  /**
   * 新增充值
   * 
   * @param bnesAcctTransactionDO
   * @return 插入数据的主键
   */
  @Override
  public Integer insertBnesAcctTransactionDO(BnesAcctTransactionQuery bnesAcctTransactionQuery)
      throws Exception {

    return bnesAcctTransactionDAO.insertBnesAcctTransactionDO(bnesAcctTransactionQuery);
  }

  /**
   * 修改充值
   * 
   * @param bnesAcctTransactionDO
   * @return 受影响的行数
   */
  @Override
  public Integer updateBnesAcctTransactionDO(BnesAcctTransactionQuery bnesAcctTransactionQuery)
      throws Exception {

    return bnesAcctTransactionDAO.updateBnesAcctTransactionDO(bnesAcctTransactionQuery);
  }

  /**
   * 查询充值信息
   * 
   * @param bnesAcctTransactionDO
   * @return bnesAcctTransactionQuery
   */
  @Override
  public BnesAcctTransactionQuery findById(Integer accountTransactionId) throws Exception {

    return bnesAcctTransactionDAO.findById(accountTransactionId);
  }

  /**
   * 删除记录
   * 
   * @param accountTransactionId
   * @return 受影响的行数
   */
  @Override
  public Integer deleteBnesAcctTransactionDOByPrimaryKey(Integer accountTransactionId)
      throws Exception {

    return bnesAcctTransactionDAO.deleteBnesAcctTransactionDOByPrimaryKey(accountTransactionId);
  }



  /**
   * 多条删除信息
   * 
   * @param levelIds
   * @return
   * @throws Exception 异常
   */
  @Override
  public Integer deleteAll(List<String> levelIds) throws Exception {
    Integer count = 0;
    if (ListUtils.isNotEmpty(levelIds)) {
      for (String id : levelIds) {

        count +=
            bnesAcctTransactionDAO.deleteBnesAcctTransactionDOByPrimaryKey(Integer.valueOf(id));
      }
    }

    return count;
  }
}
