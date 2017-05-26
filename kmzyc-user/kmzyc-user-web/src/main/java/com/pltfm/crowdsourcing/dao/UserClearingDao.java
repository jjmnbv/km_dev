package com.pltfm.crowdsourcing.dao;


import java.util.List;
import java.util.Map;

import com.km.crowdsourcing.model.InstitutionUser;
import com.pltfm.app.dataobject.BnesAcctTransListDO;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;


public interface UserClearingDao {


  int batchInsertListDO(Map<String, AccountInfo> accountInfoMap,
      List<InstitutionUser> institutionUserList,
      List<BnesAcctTransactionQuery> bnesAcctTransactionQueryList,
      List<BnesAcctTransListDO> bnesAcctTransListDOList) throws Exception;

}
