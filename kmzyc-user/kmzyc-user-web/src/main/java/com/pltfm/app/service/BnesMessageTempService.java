package com.pltfm.app.service;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.BnesMessageTempQry;

public interface BnesMessageTempService {
  public List selectByExample(BnesMessageTempQry qry) throws SQLException;
}
