package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pltfm.app.dao.BnesMessageTempDAO;
import com.pltfm.app.service.BnesMessageTempService;
import com.pltfm.app.vobject.BnesMessageTempQry;

@Component(value = "bnesMessageTempService")
public class BnesMessageTempServiceImpl implements BnesMessageTempService {
  @Resource(name = "bnesMessageTempDAO")
  BnesMessageTempDAO bnesMessageTempDAO;

  public List selectByExample(BnesMessageTempQry qry) throws SQLException {
    return bnesMessageTempDAO.selectByExample(qry);
  }

  public BnesMessageTempDAO getBnesMessageTempDAO() {
    return bnesMessageTempDAO;
  }

  public void setBnesMessageTempDAO(BnesMessageTempDAO bnesMessageTempDAO) {
    this.bnesMessageTempDAO = bnesMessageTempDAO;
  }


}
