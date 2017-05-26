package com.pltfm.crowdsourcing.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.km.crowdsourcing.model.InstitutionUser;
import com.pltfm.app.dataobject.BnesAcctTransListDO;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;
import com.pltfm.crowdsourcing.dao.UserClearingDao;

@Component(value = "userClearingDao")
public class UserClearingDaoImpl implements UserClearingDao {

  @Resource(name = "sqlMapClient")
  /**
   * 数据库操作对象
   */
  private SqlMapClient sqlMapClient;


  @Override
  public int batchInsertListDO(Map<String, AccountInfo> accountInfoMap,
      List<InstitutionUser> institutionUserList,
      List<BnesAcctTransactionQuery> bnesAcctTransactionQueryList,
      List<BnesAcctTransListDO> bnesAcctTransListDOList) throws Exception {
    try {
      if (!CollectionUtils.isEmpty(accountInfoMap) && !CollectionUtils.isEmpty(institutionUserList)
          && !CollectionUtils.isEmpty(bnesAcctTransactionQueryList)
          && !CollectionUtils.isEmpty(bnesAcctTransListDOList)) {
        // 开始事务
        sqlMapClient.startTransaction();
        sqlMapClient.startBatch();

        for (int i = 0; i < institutionUserList.size(); i++) {
          // 更新机构状态
          sqlMapClient.update("CrowdSourcingUser.ibatorgenerated_updateByPrimaryKeySelective",
              institutionUserList.get(i));
          // 添加交易流水
          Integer id = (Integer) sqlMapClient.insert("BnesAcctTransaction.insert",
              bnesAcctTransactionQueryList.get(i));
          // 添加余额变化记录
          BnesAcctTransListDO bt = bnesAcctTransListDOList.get(i);
          bt.setAccountTransactionId(id);
          sqlMapClient.insert("BnesAcctTransList.insert", bt);

        }
        // 更新余额
        for (String key : accountInfoMap.keySet()) {
          sqlMapClient.update("ACCOUNT_INFO.abatorgenerated_updateByPrimaryKeySelective",
              accountInfoMap.get(key));
        }

        sqlMapClient.executeBatch();
        // 提交事务
        // sqlMapClient.commitTransaction();
        return 1;
      }
    } catch (Exception e) {
      throw new Exception(e);
    } finally {
      sqlMapClient.endTransaction();
    }
    return 0;

  }

}
