package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.promotion.app.enums.PromotionTypeEnums;
import com.kmzyc.promotion.app.util.Constants;
import com.kmzyc.promotion.app.util.PromotionInfoUtil;

@SuppressWarnings("unused")
public class PromotionInfo implements Serializable, Comparable<PromotionInfo> {
  private static final long serialVersionUID = -7936732514585377774L;
  /**
   * 用户ID
   */
  private String userId;

  /**
   * 商家ID
   */
  private Long sellerId;
  /**
   * 活动ID
   */
  private Long promotionId;

  /**
   * 互斥活动ID
   */
  private String mutualIds;

  /**
   * 活动名称
   */
  private String promotionName;

  /**
   * 活动标题
   */
  private String promotionTitle;

  /**
   * 已参加该活动商品列表
   */
  private Map<String, BaseProduct> carProductMap;

  /**
   * 广告语
   */
  private String slogan;

  /**
   * 活动描述
   */
  private String promotionDescribe;

  /**
   * 活动状态 0:'全部',1:'未发布',2:'已发布'
   */
  private Integer status;

  /**
   * 是否可使用优惠券
   */
  private Short isUseCoupon;

  /**
   * 活动开始时间
   */
  private Date startTime;

  /**
   * 活动结束时间
   */
  private Date endTime;

  /**
   * 互斥活动ID 有具体的互斥活动就是活动ID用逗号隔开，强制排他的是all
   */
  private String mutexPromotionId;

  /**
   * 优先级
   */
  private Integer promotionPriority;

  /**
   * 是否已同步索引 1同步 0为未同步
   */
  private Integer isSycnIndex;

  /**
   * 是否已同步缓存-- 0为未同步1正在同步2同步成功3同步失败
   */
  private Integer isSycnCache;

  /**
   * 活动种类
   */
  private Integer nature;

  /**
   * 商家类别 ：1所有；2指定商家；3自营代销
   */
  private Integer shopSort;

  /**
   * 所选商家id
   */
  private Long supplierId;

  /**
   * 奖励可以获得个数(满赠赠品个数,加价购商品个数,现在未使用)
   */
  private Integer promotionPrizeNum;

  /**
   * 商家名称
   */
  private String shopNames;

  /**
   * 活动一对一数据,现在只有特价和打折用到，存放统一的特价价格或者打折的折扣值
   */
  private BigDecimal promotionData;

  /**
   * 活动备注
   */
  private String promotionNote;

  /**
   * 促销活动类型 10特价 8打折 6满减 5加价购 4送券 3满赠
   */
  private Integer promotionType;

  /**
   * 筛选商品名称
   */
  private String promotionFilterProductName;

  /**
   * 活动产品筛选类型
   */
  private Integer productFilterType;

  /**
   * 活动产品筛选数据
   */
  private String productFilterSql;

  /**
   * 创建者
   */
  private Integer createrUser;

  /**
   * 活动ID集合
   */
  private String promotionIds;

  /**
   * 创建时间
   */
  private Date createtime;
  /**
   * 上线状态{0:'全部',1:'未上线',2:'正在进行',3:'已过期'}
   */
  private Integer onlineStatus;

  /**
   * 优惠数值
   */
  private BigDecimal privilegeData;

  /**
   * 规则数据（满足条件数值和优惠数值）
   */
  private List<PromotionRuleData> ruleDatas;

  /**
   * 满足条件数值
   */
  private BigDecimal meetData;

  /**
   * 最小购买数
   */
  private Integer minBuy;

  /**
   * 最大购买数
   */
  private Integer maxBuy;

  /**
   * 活动库存
   */
  private Integer promotionStock;
  /**
   * 活动销量
   */
  private Integer promotionSell;

  /**
   * 卖光后操作 1：停止销售直到活动结束；2：恢复原价销售；
   */
  private Integer sellUpType;
  /**
   * 赠品或者加价购商品列表
   */
  private List<PromotionProduct> giftProductList;

  /**
   * 分页条件
   */
  private Integer startIndex;
  private Integer endIndex;

  public List<PromotionRuleData> getRuleDatas() {
    return ruleDatas;
  }

  public void setRuleDatas(List<PromotionRuleData> ruleDatas) {
    this.ruleDatas = ruleDatas;
  }

  public Long getPromotionId() {
    return promotionId;
  }

  public void setPromotionId(Long promotionId) {
    this.promotionId = promotionId;
  }

  public String getPromotionName() {
    return promotionName;
  }

  public void setPromotionName(String promotionName) {
    this.promotionName = promotionName;
  }

  public String getPromotionTitle() {
    return promotionTitle;
  }

  public void setPromotionTitle(String promotionTitle) {
    this.promotionTitle = promotionTitle;
  }

  public String getSlogan() {
    return slogan;
  }

  public void setSlogan(String slogan) {
    this.slogan = slogan;
  }

  public String getPromotionDescribe() {
    return promotionDescribe;
  }

  public void setPromotionDescribe(String promotionDescribe) {
    this.promotionDescribe = promotionDescribe;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Short getIsUseCoupon() {
    return isUseCoupon;
  }

  public void setIsUseCoupon(Short isUseCoupon) {
    this.isUseCoupon = isUseCoupon;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public String getMutexPromotionId() {
    return mutexPromotionId;
  }

  public void setMutexPromotionId(String mutexPromotionId) {
    this.mutexPromotionId = mutexPromotionId;
  }

  public Integer getPromotionPriority() {
    return promotionPriority;
  }

  public void setPromotionPriority(Integer promotionPriority) {
    this.promotionPriority = promotionPriority;
  }

  public Integer getIsSycnIndex() {
    return isSycnIndex;
  }

  public void setIsSycnIndex(Integer isSycnIndex) {
    this.isSycnIndex = isSycnIndex;
  }

  public Integer getIsSycnCache() {
    return isSycnCache;
  }

  public void setIsSycnCache(Integer isSycnCache) {
    this.isSycnCache = isSycnCache;
  }

  public Integer getNature() {
    return nature;
  }

  public void setNature(Integer nature) {
    this.nature = nature;
  }

  /** 商家类别 ：1所有；2指定入驻商家；3康美自营代销 */
  public Integer getShopSort() {
    return shopSort;
  }

  /** 商家类别 ：1所有；2指定入驻商家；3康美自营代销 */
  public void setShopSort(Integer shopSort) {
    this.shopSort = shopSort;
  }

  public Long getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(Long supplierId) {
    this.supplierId = supplierId;
  }

  public Integer getPromotionPrizeNum() {
    return promotionPrizeNum;
  }

  public void setPromotionPrizeNum(Integer promotionPrizeNum) {
    this.promotionPrizeNum = promotionPrizeNum;
  }

  public String getShopNames() {
    return shopNames;
  }

  public void setShopNames(String shopNames) {
    this.shopNames = shopNames;
  }

  public BigDecimal getPromotionData() {
    return promotionData;
  }

  public void setPromotionData(BigDecimal promotionData) {
    this.promotionData = promotionData;
  }

  public String getPromotionNote() {
    return promotionNote;
  }

  public void setPromotionNote(String promotionNote) {
    this.promotionNote = promotionNote;
  }

  public Integer getPromotionType() {
    return promotionType;
  }

  public void setPromotionType(Integer promotionType) {
    this.promotionType = promotionType;
  }

  public String getPromotionFilterProductName() {
    return promotionFilterProductName;
  }

  public void setPromotionFilterProductName(String promotionFilterProductName) {
    this.promotionFilterProductName = promotionFilterProductName;
  }

  /** 活动商品筛选类型:1:'全场',2:'指定商品',3:'产品类目',4:'品牌' */
  public Integer getProductFilterType() {
    return productFilterType;
  }

  /** 活动商品筛选类型:1:'全场',2:'指定商品',3:'产品类目',4:'品牌' */
  public void setProductFilterType(Integer productFilterType) {
    this.productFilterType = productFilterType;
  }

  public String getProductFilterSql() {
    return productFilterSql;
  }

  public void setProductFilterSql(String productFilterSql) {
    this.productFilterSql = productFilterSql;
  }

  public Integer getCreaterUser() {
    return createrUser;
  }

  public void setCreaterUser(Integer createrUser) {
    this.createrUser = createrUser;
  }

  public String getPromotionIds() {
    return promotionIds;
  }

  public void setPromotionIds(String promotionIds) {
    this.promotionIds = promotionIds;
  }

  public Date getCreatetime() {
    return createtime;
  }

  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
  }

  public Integer getOnlineStatus() {
    if (startTime == null || endTime == null) {
      return onlineStatus;
    }
    Date now = new Date();
    if (now.before(startTime)) {
      return 1;
    } else if (now.after(endTime)) {
      return 3;
    } else {
      return 2;
    }
  }

  public void setOnlineStatus(Integer onlineStatus) {
    this.onlineStatus = onlineStatus;
  }

  public BigDecimal getPrivilegeData() {
    return privilegeData;
  }

  public void setPrivilegeData(BigDecimal privilegeData) {
    this.privilegeData = privilegeData;
  }

  public JSONObject toJsonObject() {
    JSONObject json = new JSONObject();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    json.put("promotionName", this.promotionName);
    json.put("endTime", sdf.format(this.endTime));
    json.put("startTime", sdf.format(this.startTime));
    json.put("title", promotionTitle);
    json.put("promotionType", this.promotionType);
    json.put("promotionTypeName", PromotionInfoUtil.getPromotionTypeMap().get(this.promotionType));
    json.put("promotionId", this.promotionId);
    json.put("privilegeData", privilegeData);
    json.put("meetData", promotionData);
    json.put("productFilterType", productFilterType);
    String ruleKey = "ruleName";
    String ruleValue = "";
    switch (this.promotionType) {
      case 10:
        ruleValue += "特价价格:" + promotionData.doubleValue();
        break;
      case 8:
        ruleValue += "折扣:打" + promotionData.doubleValue() + "折";
        break;
      case 6:
        ruleValue +=
            "满减:满" + promotionData.doubleValue() + "元，立减" + privilegeData.doubleValue() + "元";
        break;
      case 4:
        ruleValue +=
            "满送:满" + promotionData.doubleValue() + "元，立送id为" + privilegeData.longValue() + "的优惠券券";
        break;
      default:
        break;
    }
    json.put(ruleKey, ruleValue);
    return json;
  }

  /**
   * 降序排列 优先级高的排前面 返回负数this优先级高 </br>
   * 1、商品作用域优先级高于订单优先级</br>
   * 2、同一作用域 强制排他优先级最高</br>
   * 3、类型优先级 特价>打折>满减>满送</br>
   * 4、以上情况相同 优先级数字越大 优先级越高</br>
   * 5、以上情况相同 活动开始时间越晚 优先级越高</br>
   */
  @Override
  public int compareTo(PromotionInfo o) {
    int compare = 0;
    if (0 == promotionId.compareTo(o.getPromotionId())) {
      return compare;
    }
    PromotionTypeEnums en = PromotionInfoUtil.promotionTypeEnumsMap.get(o.getPromotionType());
    PromotionTypeEnums thisEn = PromotionInfoUtil.promotionTypeEnumsMap.get(this.promotionType);
    compare = thisEn.getScope() - en.getScope();
    if (Constants.PROMOTION_MUTEX_ALL_PROMOTION_FLAG.equals(mutexPromotionId)
        && Constants.PROMOTION_MUTEX_ALL_PROMOTION_FLAG.equals(o.getMutexPromotionId())) {
      compare = 0;
    } else if (!Constants.PROMOTION_MUTEX_ALL_PROMOTION_FLAG.equals(mutexPromotionId)
        && !Constants.PROMOTION_MUTEX_ALL_PROMOTION_FLAG.equals(o.getMutexPromotionId())) {
      compare = 0;
    } else {
      if (Constants.PROMOTION_MUTEX_ALL_PROMOTION_FLAG.equals(mutexPromotionId)) {
        compare = 1;
      } else if (Constants.PROMOTION_MUTEX_ALL_PROMOTION_FLAG.equals(o.getMutexPromotionId())) {
        compare = -1;
      }
    }
    if (compare == 0) {
      compare = this.promotionType - o.getPromotionType();
    }
    if (compare == 0) {
      compare = promotionPriority - o.getPromotionPriority();
    }
    if (compare == 0) {
      compare = startTime.compareTo(o.getStartTime());
    }
    return compare * (-1);
  }

  public Boolean getExcludeAll() {
    return mutexPromotionId != null && "all".equals(mutexPromotionId);
  }

  public BigDecimal getMeetData() {
    if (null != ruleDatas && ruleDatas.size() >= 1) {
      return ruleDatas.get(ruleDatas.size() - 1).getMeetData();
    } else {
      return null;
    }
  }

  public void setMeetData(BigDecimal meetData) {
    this.meetData = meetData;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Integer getMinBuy() {
    return minBuy;
  }

  public void setMinBuy(Integer minBuy) {
    this.minBuy = minBuy;
  }

  public Integer getMaxBuy() {
    return maxBuy;
  }

  public void setMaxBuy(Integer maxBuy) {
    this.maxBuy = maxBuy;
  }

  public Integer getPromotionStock() {
    return promotionStock;
  }

  public void setPromotionStock(Integer promotionStock) {
    this.promotionStock = promotionStock;
  }

  public Integer getPromotionSell() {
    return promotionSell;
  }

  public void setPromotionSell(Integer promotionSell) {
    this.promotionSell = promotionSell;
  }

  public Integer getStartIndex() {
    return startIndex;
  }

  public void setStartIndex(Integer startIndex) {
    this.startIndex = startIndex;
  }

  public Integer getEndIndex() {
    return endIndex;
  }

  public void setEndIndex(Integer endIndex) {
    this.endIndex = endIndex;
  }

  public Long getSellerId() {
    return sellerId;
  }

  public void setSellerId(Long sellerId) {
    this.sellerId = sellerId;
  }

  public String getMutualIds() {
    if (mutualIds == null) {
      mutualIds = "";
    }
    return mutualIds;
  }

  public void setMutualIds(String mutualIds) {
    this.mutualIds = mutualIds;
  }

  public Map<String, BaseProduct> getCarProductMap() {
    if (carProductMap == null) {
      carProductMap = new HashMap<String, BaseProduct>();
    }
    return carProductMap;
  }

  public void setCarProductMap(Map<String, BaseProduct> carProductMap) {
    this.carProductMap = carProductMap;
  }

  public List<PromotionProduct> getGiftProductList() {
    return giftProductList;
  }

  public void setGiftProductList(List<PromotionProduct> giftProductList) {
    this.giftProductList = giftProductList;
  }

  /**
   * 卖光后操作 1：停止销售直到活动结束；2：恢复原价销售；
   */
  public Integer getSellUpType() {
    return sellUpType;
  }

  /**
   * 卖光后操作 1：停止销售直到活动结束；2：恢复原价销售；
   */
  public void setSellUpType(Integer sellUpType) {
    this.sellUpType = sellUpType;
  }



}
