package com.pltfm.app.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pltfm.app.dao.Crule8ExtentDAO;
import com.pltfm.app.dataobject.Crule8ExtentDO;
import com.pltfm.app.service.Crule8ExtentService;

@Component(value = "crule8ExtentService")
public class Crule8ExtentServiceImpl implements Crule8ExtentService {


  @Resource(name = "crule8ExtentDAO")
  private Crule8ExtentDAO crule8ExtentDAO;

  @Override
  public int insertCrule8ExtentDO(String supplierId, BigDecimal pkeyDetailId) {
    // TODO Auto-generated method stub
    int result = 0;
    if (null != supplierId && !supplierId.equals("")) {
      String[] args = supplierId.split(",");
      for (int i = 0; i < args.length; i++) {
        if (!args[i].equals("")) {
          Crule8ExtentDO crule8ExtentDO = new Crule8ExtentDO();
          crule8ExtentDO.setCrule8Id(pkeyDetailId);
          crule8ExtentDO.setRelateCode(args[i]);
          crule8ExtentDO.setType((short) 1);
          crule8ExtentDAO.insertCrule8ExtentDO(crule8ExtentDO);
          result = i;
        }
      }
    }
    return result;
  }

  public int insertCrule8ExProduct(String skuCodeLists, BigDecimal pkeyDetailId) {


    int result = 0;

    if (null != skuCodeLists && !skuCodeLists.equals("")) {
      String[] args = skuCodeLists.split("\\|");
      for (int i = 0; i < args.length; i++) {
        if (!args[i].equals("")) {
          Crule8ExtentDO crule8ExtentDO = new Crule8ExtentDO();
          crule8ExtentDO.setCrule8Id(pkeyDetailId);
          crule8ExtentDO.setRelateCode(args[i]);
          crule8ExtentDO.setType((short) 2);
          crule8ExtentDAO.insertCrule8ExtentDO(crule8ExtentDO);
          result = i;
        }
      }
    }


    return result;
  }

  @Override
  public List<Crule8ExtentDO> findListByExample(Crule8ExtentDO crule8ExtentDO) {
    // TODO Auto-generated method stub
    return null;
  }

  public Crule8ExtentDAO getCrule8ExtentDAO() {
    return crule8ExtentDAO;
  }

  public void setCrule8ExtentDAO(Crule8ExtentDAO crule8ExtentDAO) {
    this.crule8ExtentDAO = crule8ExtentDAO;
  }

  @Override
  public List<Crule8ExtentDO> findSuplerListByExample(Crule8ExtentDO crule8ExtentDO) {
    // TODO Auto-generated method stub
    return crule8ExtentDAO.findSuplerListByExample(crule8ExtentDO);

  }



}
