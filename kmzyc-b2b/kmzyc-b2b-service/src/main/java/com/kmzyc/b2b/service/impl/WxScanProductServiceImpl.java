package com.kmzyc.b2b.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.ProductSkuDao;
import com.kmzyc.b2b.dao.WxScanProductDao;
import com.kmzyc.b2b.model.WxScanProduct;
import com.kmzyc.b2b.service.WxScanProductService;
import com.kmzyc.commons.exception.ServiceException;

@Service("wxScanProductService")
public class WxScanProductServiceImpl implements WxScanProductService {


  @Resource(name = "wxScanProductDao")
  private WxScanProductDao wxScanProdctDao;

  @Resource
  private ProductSkuDao productSkuDao;

  //private static Logger logger = Logger.getLogger(WxScanProductServiceImpl.class);
  private static Logger logger = LoggerFactory.getLogger(WxScanProductServiceImpl.class);

  @Override
  public boolean saveWxScanProductRecord(WxScanProduct para) throws ServiceException {
    if (para == null) {
      return false;
    }

    Date now = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    para.setScanDate(now);
    para.setRemark(format.format(now) + "微信扫码推送事件添加记录");
    try {

      // 查询该sku条形码对应的skuid,做适当的冗余
      Long productSkuId = productSkuDao.querySkuByBarCode(para.getKeyStr());
      if (productSkuId == null) {
        logger.info("条形码为" + para.getKeyStr() + "的sku产品找不到对应的sku产品记录!");
        return false;
      }
      para.setProductSkuId(productSkuId);

      Object obj = wxScanProdctDao.insert(para);

      if (obj == null) {
        logger.info("条形码为" + para.getKeyStr() + "的sku产品新增扫码记录失败!");
        return false;
      }
    } catch (SQLException e) {
      logger.error("条形码为" + para.getKeyStr() + "的sku产品新增扫码记录失败!",e);
      throw new ServiceException(-1, "条形码为" + para.getKeyStr()
          + "的sku产品新增扫码记录失败,可能新增失败,可能查询skuid失败!");
    }
    return true;
  }

  /**
   * 根据openID获取扫码记录
   * 
   * @param openId
   * @return
   * @throws ServiceException
   */
  @Override
  public List<Long> queryWXScanProductSku(String openId) throws ServiceException {
    try {
      return wxScanProdctDao.queryWXScanProductSku(openId);
    } catch (Exception e) {
      throw new ServiceException(-1, e.getMessage());
    }
  }
}
