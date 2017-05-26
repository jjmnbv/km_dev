package com.kmzyc.b2b.vo;

import java.io.Serializable;
import java.util.*;

import com.kmzyc.b2b.model.PromotionInfo;
import com.kmzyc.b2b.util.SupplierType;

/**
 * 购物车
 * 
 * @author hewenfeng
 * 
 */
@SuppressWarnings("unused")
@Deprecated
public class ShopCar implements Serializable {
  private static final long serialVersionUID = -3046285507454651857L;
  /**
   * 自营和代销的key
   */
  public static final Long SELF_AND_PROXY_KEY = 1L;

  private String site;

  /**
   * 用户ID 如果游客则为sessionid
   */
  private String userID;
  /**
   * 是否登录
   */
  private Boolean userIsLogin;

  /**
   * 购物车商品数量
   */
  private int shopCarProductNum = 0;

  /**
   * 购物车唯一编码
   */
  private String code;

  /**
   * 商家购物车产品
   */
  private Map<Long, Map<Long, SellerShopProduct>> sellerShopCar;

  /**
   * 商家价格信息
   */
  private Map<Long, SellerPriceInfo> sellerPriceInfo;

  private Map<Long, CarProduct> timeProductMap;

  /**
   * 活动 key为入驻商家ID 自营代销为1
   */
  private Map<Long, ProductPromotionInfo> productPromotionInfoMap;

  /** key为活动ID 赠品和加价购商品列表 */
  private Map<Long, Map<Long, CarProduct>> giftProductMap;

  /**
   * 所有产品（临时对象，用于计算）
   */
  private Map<String, CarProduct> allProductMap;

  private ShopcartTotal shopcartTotal;

  /**
   * 初始化购物车产品数量
   * 
   * @return
   */
  public Integer calcShopCarProductCount() {
    Integer allCount = 0;
    if (null != sellerShopCar && !sellerShopCar.isEmpty()) {
      for (Iterator<Map<Long, SellerShopProduct>> sspMapIt = sellerShopCar.values().iterator(); sspMapIt
          .hasNext();) {
        Map<Long, SellerShopProduct> sellerMap = sspMapIt.next();
        for (Iterator<SellerShopProduct> ssp = sellerMap.values().iterator(); ssp.hasNext();) {
          allCount += ssp.next().calcProductCount();
        }
      }
    }
    shopCarProductNum = allCount;
    return shopCarProductNum;
  }

  /**
   * 添加套餐到购物车
   * 
   * @return
   */
  public CompositionCarProduct addComposition(CompositionCarProduct ccp) {
    if (null != ccp && null != ccp.getProductList()) {
      Long sKey = ccp.getMainCarProduct().getSellerId();
      int type = ccp.getMainCarProduct().getSupplierType().intValue();
      if (SupplierType.SELLER_TYPE_SALE_PROXY.getIndex() == type
          || SupplierType.SELLER_TYPE_SELF_SUPPORT.getIndex() == type) {
        sKey = SELF_AND_PROXY_KEY;
      }
      Long sid = ccp.getMainCarProduct().getSellerId();
      Map<Long, SellerShopProduct> sspMap = sellerShopCar.get(sKey);
      SellerShopProduct ssp = null;
      SellerInfo sellerInfo = null;
      /* 支持默认都选中需删除本段代码 */
      boolean isChecked = sellerShopCar.isEmpty();
      if (!isChecked && null != sspMap && !sspMap.isEmpty()) {
        if (0 != sKey.compareTo(sid)) {
          // 遍历自营、代销
          for (Iterator<SellerShopProduct> sspIt = sspMap.values().iterator(); sspIt.hasNext();) {
            SellerShopProduct innerSsp = sspIt.next();
            Map<Long, CarProduct> cpMap = innerSsp.getCarProducts();
            if (cpMap != null && !cpMap.isEmpty()) {
              for (Iterator<CarProduct> cpIt = cpMap.values().iterator(); cpIt.hasNext();) {
                if (cpIt.next().getIsChecked()) {
                  isChecked = true;
                  break;
                }
              }
            }
            if (!isChecked) {
              Map<Long, CompositionCarProduct> ccpMap = innerSsp.getCompositionCarProducts();
              if (ccpMap != null && !ccpMap.isEmpty()) {
                for (Iterator<CompositionCarProduct> ccpIt = ccpMap.values().iterator(); ccpIt
                    .hasNext();) {
                  if (ccpIt.next().getIsChecked()) {
                    isChecked = true;
                    break;
                  }
                }
              }
            }
          }
        } else if (0 == sKey.compareTo(sid) && null != (ssp = sspMap.get(sid))) {
          // 判断入驻
          Map<Long, CarProduct> cpMap = ssp.getCarProducts();
          if (cpMap != null && !cpMap.isEmpty()) {
            for (Iterator<CarProduct> cpIt = cpMap.values().iterator(); cpIt.hasNext();) {
              if (cpIt.next().getIsChecked()) {
                isChecked = true;
                break;
              }
            }
          }
          if (!isChecked) {
            Map<Long, CompositionCarProduct> ccpMap = ssp.getCompositionCarProducts();
            if (ccpMap != null && !ccpMap.isEmpty()) {
              for (Iterator<CompositionCarProduct> ccpIt = ccpMap.values().iterator(); ccpIt
                  .hasNext();) {
                if (ccpIt.next().getIsChecked()) {
                  isChecked = true;
                  break;
                }
              }
            }
          }
        }
      }
      ccp.setIsChecked(isChecked);
      /* 支持默认都选中需删除本段代码 */
      if (null == sspMap) {
        sspMap = new LinkedHashMap<Long, SellerShopProduct>();
      } else {
        if (sKey.compareTo(ShopCar.SELF_AND_PROXY_KEY) == 0) {
          ssp = sspMap.get(221L);
        } else {
          ssp = sspMap.get(sid);
        }
      }
      if (null == ssp) {
        ssp = new SellerShopProduct();
        // 判断是否为自营
        if (sKey.compareTo(ShopCar.SELF_AND_PROXY_KEY) == 0) {
          ssp.setSellerId(221L);
        } else {
          ssp.setSellerId(sid);
        }
        sellerInfo = ssp.getSellerInfo();
      }
      if (null == sellerInfo || null == sellerInfo.getSupplierType()) {
        CarProduct cpMain = ccp.getMainCarProduct();
        sellerInfo = new SellerInfo();
        if (sKey.compareTo(ShopCar.SELF_AND_PROXY_KEY) == 0) {
          sellerInfo.setShopName(cpMain.getSupplierName());
          sellerInfo.setSupplierType(Short.valueOf((short) 1));
          ssp.setSellerInfo(sellerInfo);
        } else {
          sellerInfo.setShopName(cpMain.getSupplierName());
          sellerInfo.setSupplierType(cpMain.getSupplierType().shortValue());
          ssp.setSellerInfo(sellerInfo);
        }
      }
      Map<Long, CompositionCarProduct> ccpMap = ssp.getCompositionCarProducts();
      CompositionCarProduct orgCcp = ccpMap.get(ccp.getId());
      if (null != orgCcp) {
        ccp.setAmount(ccp.getAmount() + orgCcp.getAmount());
      }
      shopCarProductNum +=
          ssp.updateCountForComposition(ccp.getId(), ccp.getProductList(), ccp.getAmount());
      ccpMap.put(ccp.getId(), ccp);
      ssp.setCompositionCarProducts(ccpMap);
      if (sKey.compareTo(ShopCar.SELF_AND_PROXY_KEY) == 0) {
        sspMap.put(221L, ssp);
        sellerShopCar.put(sKey, sspMap);
      } else {
        sspMap.put(sid, ssp);
        sellerShopCar.put(sKey, sspMap);
      }

    }
    return ccp;
  }

  /**
   * 添加商品到购物车
   * 
   * @return
   */
  public CarProduct addProduct(CarProduct cp) {
    Long sid = cp.getSellerId();
    Long sKey = sid;
    if (SupplierType.SELLER_TYPE_SALE_PROXY.getIndex() == cp.getSupplierType().intValue()
        || SupplierType.SELLER_TYPE_SELF_SUPPORT.getIndex() == cp.getSupplierType().intValue()) {
      sKey = SELF_AND_PROXY_KEY;
    }
    Map<Long, SellerShopProduct> sspMap = sellerShopCar.get(sKey);
    SellerShopProduct ssp = null;
    SellerInfo sellerInfo = null;
    /* 支持默认都选中需删除本段代码 */
    boolean isChecked = sellerShopCar.isEmpty();
    if (!isChecked && null != sspMap && !sspMap.isEmpty()) {
      if (0 != sKey.compareTo(sid)) {
        // 遍历自营、代销
        for (Iterator<SellerShopProduct> sspIt = sspMap.values().iterator(); sspIt.hasNext();) {
          SellerShopProduct innerSsp = sspIt.next();
          Map<Long, CarProduct> cpMap = innerSsp.getCarProducts();
          if (cpMap != null && !cpMap.isEmpty()) {
            for (Iterator<CarProduct> cpIt = cpMap.values().iterator(); cpIt.hasNext();) {
              if (cpIt.next().getIsChecked()) {
                isChecked = true;
                break;
              }
            }
          }
          if (!isChecked) {
            Map<Long, CompositionCarProduct> ccpMap = innerSsp.getCompositionCarProducts();
            if (ccpMap != null && !ccpMap.isEmpty()) {
              for (Iterator<CompositionCarProduct> ccpIt = ccpMap.values().iterator(); ccpIt
                  .hasNext();) {
                if (ccpIt.next().getIsChecked()) {
                  isChecked = true;
                  break;
                }
              }
            }
          }
        }
      } else if (0 == sKey.compareTo(sid) && null != (ssp = sspMap.get(sid))) {
        // 判断入驻
        Map<Long, CarProduct> cpMap = ssp.getCarProducts();
        if (cpMap != null && !cpMap.isEmpty()) {
          for (Iterator<CarProduct> cpIt = cpMap.values().iterator(); cpIt.hasNext();) {
            if (cpIt.next().getIsChecked()) {
              isChecked = true;
              break;
            }
          }
        }
        if (!isChecked) {
          Map<Long, CompositionCarProduct> ccpMap = ssp.getCompositionCarProducts();
          if (ccpMap != null && !ccpMap.isEmpty()) {
            for (Iterator<CompositionCarProduct> ccpIt = ccpMap.values().iterator(); ccpIt
                .hasNext();) {
              if (ccpIt.next().getIsChecked()) {
                isChecked = true;
                break;
              }
            }
          }
        }
      }
    }
    cp.setIsChecked(isChecked);
    /* 支持默认都选中需删除本段代码 */
    if (null == sspMap) {
      sspMap = new LinkedHashMap<Long, SellerShopProduct>();
    } else {
      ssp = sspMap.get(sid);
    }
    if (null == ssp) {
      ssp = new SellerShopProduct();
      ssp.setSellerId(sid);
      sellerInfo = ssp.getSellerInfo();
    }
    if (null == sellerInfo || null == sellerInfo.getSupplierType()) {
      sellerInfo = new SellerInfo();
      sellerInfo.setShopName(cp.getSupplierName());
      sellerInfo.setSupplierType(cp.getSupplierType().shortValue());
      ssp.setSellerInfo(sellerInfo);
    }
    Map<Long, CarProduct> cpMap = ssp.getCarProducts();
    CarProduct cpOrg = cpMap.get(cp.getProductSkuId());
    if (null != cpOrg) {
      cp.setAmount(cp.getAmount() + cpOrg.getAmount());
    }
    shopCarProductNum += ssp.updateCountForProduct(cp.getProductSkuId(), cp.getAmount());
    cpMap.put(cp.getProductSkuId(), cp);
    ssp.setCarProducts(cpMap);
    sspMap.put(sid, ssp);
    sellerShopCar.put(sKey, sspMap);
    return cp;
  }

  /**
   * 添加商品到购物车
   * 
   * @param sKey sKey购物车key入驻和商家ID一致
   * @param sid 商家ID
   * @param cid 产品ID
   * @param amount 产品数量
   * @return
   */
  public CarProduct addProduct(Long sKey, Long sid, Long cid, Integer amount) {
    Map<Long, SellerShopProduct> sspMap = sellerShopCar.get(sKey);
    SellerShopProduct ssp = null;
    if (null == sspMap) {
      sspMap = new LinkedHashMap<Long, SellerShopProduct>();
    } else {
      ssp = sspMap.get(sid);
    }
    if (null == ssp) {
      ssp = new SellerShopProduct();
      ssp.setSellerId(sid);
    }
    Map<Long, CarProduct> cpMap = ssp.getCarProducts();
    CarProduct cp = cpMap.get(cid);
    if (null == cp) {
      cp = new CarProduct();
      cp.setProductSkuId(cid);
    }
    cp.setAmount(amount);
    shopCarProductNum += ssp.updateCountForProduct(cid, amount);
    cpMap.put(cid, cp);
    ssp.setCarProducts(cpMap);
    sspMap.put(sid, ssp);
    sellerShopCar.put(sKey, sspMap);
    return cp;
  }

  /**
   * 更新产品数量，并返回当前数量
   * 
   * @param sKey sKey购物车key入驻和商家ID一致
   * @param sid 商家ID
   * @param cid 产品ID
   * @param amount 产品数量
   * @return
   */
  public Integer updateCountForProduct(Long sKey, Long sid, Long cid, Integer amount) {
    Map<Long, SellerShopProduct> sspMap = sellerShopCar.get(sKey);
    if (null != sspMap && !sspMap.isEmpty()) {
      SellerShopProduct ssp = sspMap.get(sid);
      if (null != ssp) {
        shopCarProductNum += ssp.updateCountForProduct(cid, amount);
        sspMap.put(sid, ssp);
        sellerShopCar.put(sKey, sspMap);
      }
    }
    return shopCarProductNum;
  }

  /**
   * 合并购物车,未保存到缓存
   * 
   * @param shopCar
   */
  public void mergeShopCar(ShopCar shopCar) {
    Map<Long, Map<Long, SellerShopProduct>> sspMapSet = shopCar.getSellerShopCar();
    if (null != sspMapSet && !sspMapSet.isEmpty()) {
      if (sellerShopCar.isEmpty()) {
        shopCarProductNum = shopCar.getShopCarProductNum();
        sellerShopCar = sspMapSet;
      }
      for (Map.Entry<Long, Map<Long, SellerShopProduct>> entry:sspMapSet.entrySet()) {
        Long key = entry.getKey();
        Map<Long, SellerShopProduct> orgSspMap = sellerShopCar.get(key);
        Map<Long, SellerShopProduct> tempSspMap = entry.getValue();
        if (null == orgSspMap || orgSspMap.isEmpty()) {
          sellerShopCar.put(key, tempSspMap);
          continue;
        }
        for (Iterator<SellerShopProduct> sspIt = tempSspMap.values().iterator(); sspIt.hasNext();) {
          SellerShopProduct tempSsp = sspIt.next();
          SellerShopProduct orgSsp = orgSspMap.get(tempSsp.getSellerId());
          if (null != orgSsp) {
            Map<Long, CarProduct> tempCps = tempSsp.getCarProducts();
            Map<Long, CompositionCarProduct> tempComs = tempSsp.getCompositionCarProducts();
            Map<Long, CarProduct> orgCps = orgSsp.getCarProducts();
            Map<Long, CompositionCarProduct> orgComs = orgSsp.getCompositionCarProducts();
            if (null != orgCps && !orgCps.isEmpty() && null != tempCps && !tempCps.isEmpty()) {
              for (Iterator<CarProduct> iterator = tempCps.values().iterator(); iterator.hasNext();) {
                CarProduct cp = iterator.next();
                Long cid = cp.getProductSkuId();
                CarProduct orgCp = orgCps.get(cid);
                int amount = cp.getAmount();
                if (null != orgCp) {
                  amount += orgCp.getAmount();
                }
                shopCarProductNum += orgSsp.updateCountForProduct(cid, amount);
                cp.setAmount(amount);
                orgCps.put(cid, cp);
              }
            } else if (null != tempCps && !tempCps.isEmpty()) {
              orgSsp.setCarProducts(tempCps);
            }
            if (null != orgComs && !orgComs.isEmpty() && null != tempComs && !tempComs.isEmpty()) {
              for (Iterator<CompositionCarProduct> iterator = tempComs.values().iterator(); iterator
                  .hasNext();) {
                CompositionCarProduct ccp = iterator.next();
                Long cid = ccp.getId();
                CompositionCarProduct orgCcp = orgComs.get(cid);
                int amount = ccp.getAmount();
                if (null != orgCcp) {
                  amount += orgCcp.getAmount();
                }
                shopCarProductNum +=
                    orgSsp.updateCountForComposition(cid, ccp.getProductList(), amount);
                ccp.setAmount(amount);
                orgComs.put(cid, ccp);
              }
            } else if (null != tempComs && !tempComs.isEmpty()) {
              orgSsp.setCompositionCarProducts(tempComs);
            }
          } else {
            orgSspMap.put(tempSsp.getSellerId(), tempSsp);
          }
        }
        this.shopCarProductNum = calcShopCarProductCount();
        this.sellerShopCar.put(key, orgSspMap);
      }
    }
  }

  /**
   * 移除选中的商品/套餐
   * 
   * @return
   */
  public boolean removeCheckedProduct() {
    boolean isRemove = false;
    List<Long> removeIds = new ArrayList<Long>();
    if (null != giftProductMap && !giftProductMap.isEmpty()) {
      List<Long> removeGKey = new ArrayList<Long>();
      for (Map.Entry<Long, Map<Long, CarProduct>> entry:giftProductMap.entrySet()) {
        Long gKey = entry.getKey();
        Map<Long, CarProduct> gifts = entry.getValue();
        if (null != gifts && !gifts.isEmpty()) {
          for (Iterator<CarProduct> giftIt = gifts.values().iterator(); giftIt.hasNext();) {
            CarProduct cp = giftIt.next();
            if (cp.getIsChecked()) {
              isRemove = true;
              removeIds.add(cp.getProductSkuId());
            }
          }
          gifts.keySet().removeAll(removeIds);
          removeIds.clear();
          if (gifts.isEmpty()) {
            removeGKey.add(gKey);
          }
        }
      }
      if (!removeGKey.isEmpty()) {
        giftProductMap.keySet().removeAll(removeGKey);
      }
    }
    if (null != sellerShopCar && !sellerShopCar.isEmpty()) {
      List<Long> removeSellerIds = new ArrayList<Long>();
      List<Long> removeSKey = new ArrayList<Long>();
      for (Map.Entry<Long, Map<Long, SellerShopProduct>> entry:sellerShopCar.entrySet()) {
        Long key = entry.getKey();
        Map<Long, SellerShopProduct> sspMap = entry.getValue();
        removeSellerIds.clear();
        for (SellerShopProduct ssp : sspMap.values()) {
          Map<Long, CarProduct> cpMap = ssp.getCarProducts();
          if (cpMap != null && !cpMap.isEmpty()) {
            removeIds.clear();
            for (CarProduct cp : cpMap.values()) {
              if (cp.getIsChecked()) {
                isRemove = true;
                shopCarProductNum += ssp.updateCountForProduct(cp.getProductSkuId(), 0);
                removeIds.add(cp.getProductSkuId());
              }
            }
            cpMap.keySet().removeAll(removeIds);
          }
          Map<Long, CompositionCarProduct> ccpMap = ssp.getCompositionCarProducts();
          if (ccpMap != null && !ccpMap.isEmpty()) {
            removeIds.clear();
            for (CompositionCarProduct ccp : ccpMap.values()) {
              if (ccp.getIsChecked()) {
                isRemove = true;
                shopCarProductNum += ssp
                        .updateCountForComposition(ccp.getId(), ccp.getProductList(), 0);
                removeIds.add(ccp.getId());
              }
            }
            ccpMap.keySet().removeAll(removeIds);
          }
          if ((null == ccpMap || ccpMap.isEmpty()) && (null == cpMap || cpMap.isEmpty())) {
            removeSellerIds.add(ssp.getSellerId());
          }
        }
        if (!removeSellerIds.isEmpty()) {
          sspMap.keySet().removeAll(removeSellerIds);
        }
        if (sspMap.isEmpty()) {
          removeSKey.add(key);
        }
      }
      if (!removeSKey.isEmpty()) {
        sellerShopCar.keySet().removeAll(removeSKey);
      }
    }
    return isRemove;
  }

  /**
   * 从购物车移除商品
   * 
   * @param sellerId
   * @param skuId
   */
  public boolean removeProduct(Long sellerId, Long skuId) {
    boolean rs = false;
    Long sKey = sellerId;
    if (!sellerShopCar.containsKey(sKey)) {
      sKey = SELF_AND_PROXY_KEY;
    }
    Map<Long, SellerShopProduct> sspMap = sellerShopCar.get(sKey);
    if (null != sspMap && !sspMap.isEmpty()) {
      SellerShopProduct ssp = sspMap.get(sellerId);
      if (null != ssp) {
        Map<Long, CarProduct> cpMap = ssp.getCarProducts();
        shopCarProductNum += ssp.updateCountForProduct(skuId, 0);
        cpMap.remove(skuId);
        if ((null == ssp.getCarProducts() || 0 == ssp.getCarProducts().size())
            && (null == ssp.getCompositionCarProducts() || 0 == ssp.getCompositionCarProducts()
                .size())) {
          sspMap.remove(sellerId);
          if (sspMap.isEmpty()) {
            sellerShopCar.remove(sKey);
          }
        }
        rs = true;
      }
    }
    return rs;
  }

  /**
   * 移除商家选中的产品
   * 
   * @param sellerId
   * @return
   */
  public boolean removeSellerChecked(Long sellerId) {
    boolean isRemove = false;
    if (null != sellerShopCar && !sellerShopCar.isEmpty()) {
      List<Long> removeIds = new ArrayList<Long>();
      List<Long> removeSellerIds = new ArrayList<Long>();
      Map<Long, SellerShopProduct> sspMap = sellerShopCar.get(sellerId);
      for (SellerShopProduct ssp : sspMap.values()) {
        Map<Long, CarProduct> cpMap = ssp.getCarProducts();
        if (cpMap != null && !cpMap.isEmpty()) {
          removeIds.clear();
          for (CarProduct cp : cpMap.values()) {
            if (cp.getIsChecked()) {
              isRemove = true;
              shopCarProductNum += ssp.updateCountForProduct(cp.getProductSkuId(), 0);
              removeIds.add(cp.getProductSkuId());
            }
          }
          cpMap.keySet().removeAll(removeIds);
        }
        Map<Long, CompositionCarProduct> ccpMap = ssp.getCompositionCarProducts();
        if (ccpMap != null && !ccpMap.isEmpty()) {
          removeIds.clear();
          for (CompositionCarProduct ccp : ccpMap.values()) {
            if (ccp.getIsChecked()) {
              isRemove = true;
              shopCarProductNum += ssp
                      .updateCountForComposition(ccp.getId(), ccp.getProductList(), 0);
              removeIds.add(ccp.getId());
            }
          }
          ccpMap.keySet().removeAll(removeIds);
        }
        if ((null == ccpMap || ccpMap.isEmpty()) && (null == cpMap || cpMap.isEmpty())) {
          removeSellerIds.add(ssp.getSellerId());
        }
      }
      if (!removeSellerIds.isEmpty()) {
        sspMap.keySet().removeAll(removeSellerIds);
      }
      if (sspMap.isEmpty()) {
        sellerShopCar.keySet().remove(sellerId);
      }
    }
    return isRemove;
  }

  /**
   * 从购物车移除套餐
   * 
   * @param sellerId
   * @param id
   */
  public boolean removeComposition(Long sellerId, Long id) {
    boolean rs = false;
    Long sKey = sellerId;
    if (!sellerShopCar.containsKey(sKey)) {
      sKey = SELF_AND_PROXY_KEY;
    }
    Map<Long, SellerShopProduct> sspMap = sellerShopCar.get(sKey);
    if (null != sspMap && !sspMap.isEmpty()) {
      SellerShopProduct ssp = sspMap.get(sellerId);
      if (null != ssp) {
        Map<Long, CompositionCarProduct> ccpMap = ssp.getCompositionCarProducts();
        if (ccpMap.containsKey(id)) {
          shopCarProductNum +=
              ssp.updateCountForComposition(id, ccpMap.get(id).getProductList(), 0);
        }
        ccpMap.remove(id);
        if ((null == ssp.getCarProducts() || 0 == ssp.getCarProducts().size())
            && (null == ssp.getCompositionCarProducts() || 0 == ssp.getCompositionCarProducts()
                .size())) {
          sspMap.remove(sellerId);
          if (sspMap.isEmpty()) {
            sellerShopCar.remove(sKey);
          }
        }
        rs = true;
      }
    }
    return rs;
  }

  /**
   * 获取商家活动
   * 
   * @return
   */
  public Map<Long, Map<Long, CarProduct>> getSellerGiftProductMap(Long sellerId) {
    Map<Long, Map<Long, CarProduct>> selleriftProductMap = null;
    if (null != productPromotionInfoMap && !productPromotionInfoMap.isEmpty()
        && null != giftProductMap && !giftProductMap.isEmpty()) {
      ProductPromotionInfo ppi = productPromotionInfoMap.get(sellerId);
      List<PromotionInfo> meetPromotionInfos = null;
      if (null != ppi && null != (meetPromotionInfos = ppi.getMeetPromotionInfos())
          && meetPromotionInfos.size() > 0) {
        selleriftProductMap = new HashMap<Long, Map<Long, CarProduct>>();
        for (PromotionInfo p : meetPromotionInfos) {
          Long pid = p.getPromotionId();
          selleriftProductMap.put(pid, giftProductMap.get(pid));
        }
      }
    }
    return selleriftProductMap;
  }

  /**
   * 清空
   * 
   * @return
   */
  public boolean clear() {
    boolean rs = true;
    try {
      this.code = null;
      if (null != sellerShopCar && !sellerShopCar.isEmpty()) {
        for (Iterator<Map<Long, SellerShopProduct>> sspMapSet = sellerShopCar.values().iterator(); sspMapSet
            .hasNext();) {
          for (Iterator<SellerShopProduct> ssp = sspMapSet.next().values().iterator(); ssp
              .hasNext();) {
            if (!ssp.next().clear()) {
              rs = false;
              break;
            }
          }
        }
      }
      if (rs) {
        this.sellerShopCar.clear();
        this.shopCarProductNum = 0;
      }
    } catch (Exception e) {
      rs = false;
    }
    return rs;
  }

  public ShopCar() {
    shopCarProductNum = 0;
    sellerShopCar = new LinkedHashMap<Long, Map<Long, SellerShopProduct>>();
    code = String.valueOf(System.currentTimeMillis());
    shopcartTotal = new ShopcartTotal();
  }

  public String getUserID() {
    return userID;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }

  public Boolean getUserIsLogin() {
    if (null == userIsLogin) {
      userIsLogin = false;
    }
    return userIsLogin;
  }

  public void setUserIsLogin(Boolean userIsLogin) {
    this.userIsLogin = userIsLogin;
  }

  public int getShopCarProductNum() {
    if (shopCarProductNum <= 0) {
      shopCarProductNum = 0;
      calcShopCarProductCount();
    }
    return shopCarProductNum;
  }

  public void setShopCarProductNum(int shopCarProductNum) {
    this.shopCarProductNum = shopCarProductNum;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Map<Long, Map<Long, SellerShopProduct>> getSellerShopCar() {
    return sellerShopCar;
  }

  public void setSellerShopCar(Map<Long, Map<Long, SellerShopProduct>> sellerShopCar) {
    this.sellerShopCar = sellerShopCar;
  }

  public Map<String, CarProduct> getAllProductMap() {
    return allProductMap;
  }

  public void setAllProductMap(Map<String, CarProduct> allProductMap) {
    this.allProductMap = allProductMap;
  }

  public Map<Long, ProductPromotionInfo> getProductPromotionInfoMap() {
    return productPromotionInfoMap;
  }

  public void setProductPromotionInfoMap(Map<Long, ProductPromotionInfo> productPromotionInfoMap) {
    this.productPromotionInfoMap = productPromotionInfoMap;
  }

  public Map<Long, Map<Long, CarProduct>> getGiftProductMap() {
    return giftProductMap;
  }

  public void setGiftProductMap(Map<Long, Map<Long, CarProduct>> giftProductMap) {
    if (giftProductMap == null || giftProductMap.isEmpty()) {
      return;
    }
    if (this.giftProductMap == null || this.giftProductMap.isEmpty()) {
      this.giftProductMap = giftProductMap;
    } else {
      this.giftProductMap.putAll(giftProductMap);
    }

  }

  /** 站点 */
  public String getSite() {
    return site;
  }

  /** 站点 */
  public void setSite(String site) {
    this.site = site;
  }

  /** 汇总信息 */
  public ShopcartTotal getShopcartTotal() {
    return shopcartTotal;
  }

  /** 汇总信息 */
  public void setShopcartTotal(ShopcartTotal shopcartTotal) {
    this.shopcartTotal = shopcartTotal;
  }

  private Map<Long, CarProduct> groupByCarProductSkuIdMap;

  public Map<Long, CarProduct> getGroupByCarProductSkuIdMap() {
    return groupByCarProductSkuIdMap;
  }

  public void setGroupByCarProductSkuIdMap(Map<Long, CarProduct> groupByCarProductSkuIdMap) {
    this.groupByCarProductSkuIdMap = groupByCarProductSkuIdMap;
  }

  /** 用于一个商家的结算 **/
  private Long isCheckSellerId;
  /*** 套装产品map */
  private Map<Long, CompositionCarProduct> compositionCarProducts;

  public Long getIsCheckSellerId() {
    return isCheckSellerId;
  }

  public void setIsCheckSellerId(Long isCheckSellerId) {
    this.isCheckSellerId = isCheckSellerId;
  }

  public Map<Long, CarProduct> getCarProducts() {
    Map<Long, CarProduct> cpMap = new LinkedHashMap<Long, CarProduct>();
    Map<Long, SellerShopProduct> sellerMap = sellerShopCar.get(isCheckSellerId);
    if (isCheckSellerId != null) {
      if (isCheckSellerId.equals(SELF_AND_PROXY_KEY)) {
        Iterator<SellerShopProduct> ite = sellerMap.values().iterator();
        while (ite.hasNext()) {
          cpMap.putAll(ite.next().getCarProducts());
        }
      } else if (null != sellerMap && sellerMap.containsKey(isCheckSellerId)) {
        cpMap = sellerMap.get(isCheckSellerId).getCarProducts();
      }
    }
    return cpMap;
  }

  public Map<Long, CompositionCarProduct> getCompositionCarProducts() {
    Map<Long, CompositionCarProduct> comps = new LinkedHashMap<Long, CompositionCarProduct>();
    Map<Long, SellerShopProduct> sellerMap = sellerShopCar.get(isCheckSellerId);
    if (isCheckSellerId.equals(SELF_AND_PROXY_KEY)) {
      Iterator<SellerShopProduct> ite = sellerMap.values().iterator();
      while (ite.hasNext()) {
        comps.putAll(ite.next().getCompositionCarProducts());
      }
      return comps;
    } else {
      comps = sellerMap.get(isCheckSellerId).getCompositionCarProducts();
    }
    return comps;
  }

  public Map<Long, Map<Long, CarProduct>> getGiftCarProducts() {
    ProductPromotionInfo pinfo = productPromotionInfoMap.get(isCheckSellerId);
    if (null == pinfo) {
      return new HashMap<Long, Map<Long, CarProduct>>();
    }
    // 判断是否自营代销
    boolean isSelf = 0 == SELF_AND_PROXY_KEY.compareTo(isCheckSellerId);
    Map<Long, Map<Long, CarProduct>> gps = pinfo.getGiftProductMap();
    if (null != gps && !gps.isEmpty()) {
      Iterator<Long> keys = gps.keySet().iterator();
      while (keys.hasNext()) {
        Long key = keys.next();
        Map<Long, CarProduct> cpMap = gps.get(key);
        if (null != cpMap && !cpMap.isEmpty()) {
          for (Iterator<CarProduct> cpIt = cpMap.values().iterator(); cpIt.hasNext();) {
            cpIt.next().setIsChecked(false);
          }
        }
        Map<Long, CarProduct> tempMap = giftProductMap.get(key);
        if (cpMap != null && null != tempMap && !tempMap.isEmpty() && !cpMap.isEmpty()) {
          for (CarProduct cp : tempMap.values()) {
            // 非当前结算商家，不校验
            if ((isSelf && 0 == cp.getSupplierType()
                    .compareTo(SupplierType.SELLER_TYPE_ENTER_SALE.getIndex())) ||
                    (!isSelf && 0 != isCheckSellerId.compareTo(cp.getSellerId()))) {
              continue;
            }
            if (cp.getIsChecked()) {
              CarProduct cprs = cpMap.get(cp.getProductSkuId());
              if (null != cprs) {
                cprs.setIsChecked(true);
              }
            }
          }
        }
      }
    }
    return gps;
  }

  public ProductPromotionInfo getProductPromotionInfo() {
    return productPromotionInfoMap.get(isCheckSellerId);
  }

  public Map<Long, SellerPriceInfo> getSellerPriceInfo() {
    return null == sellerPriceInfo ? new HashMap<>() : sellerPriceInfo;
  }

  public void setSellerPriceInfo(Map<Long, SellerPriceInfo> sellerPriceInfo) {
    this.sellerPriceInfo = sellerPriceInfo;
  }

  public Map<Long, CarProduct> getTimeProductMap() {
    return timeProductMap;
  }

  public void setTimeProductMap(Map<Long, CarProduct> timeProductMap) {
    this.timeProductMap = timeProductMap;
  }

}
