package com.kmzyc.b2b.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.b2b.util.PromotionInfoUtil;
import com.kmzyc.b2b.vo.CarProduct;
import com.kmzyc.promotion.app.enums.PromotionTypeEnums;

@SuppressWarnings("unused")
public class PromotionInfo implements Comparable<PromotionInfo>, Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 9162317830223947801L;

  // ID
  private Long promotionId;
  /**
   * 商家ID
   */
  private Long sellerId;

  /**
   * 标题
   */
  private String title;

  /**
   * 名称
   */
  private String promotionName;

  /**
   * 满足条件数值
   */
  private BigDecimal meetData;

  /**
   * 优惠数值
   */
  private BigDecimal privilegeData;

  /**
   * 优惠数值类型
   */
  private Integer privilegeDataType;

  /**
   * 活动类型
   */
  private Integer promotionType;

  /**
   * 优先级
   */
  private Integer priority;

  /**
   * 规则数据（满足条件数值和优惠数值）
   */
  private List<PromotionRuleData> ruleDatas;

  private String cmsUrlPath;
  /**
   * {1:'全场',2:'指定商品',3:'商品类目',4:'商品品牌',5:'指定商家'}
   */
  private Integer productFilterType;
  private String productFilterSql;

  private String promotionTypeName;

  private String channel;
  /**
   * 已参加该活动商品列表
   */
  private Map<String, CarProduct> carProductMap;

  /**
   * 该活动中赠品或者加价购商品列表
   */
  private List<CarProduct> giftCarProductList;

  /**
   * 互斥活动ID
   */
  private String mutualIds;

  /**
   * 活动一对一数据特价或者折扣值
   */
  private BigDecimal promotionData;

  private Integer shopSort;
  private Long supplierId;
  
  /**
   * 卖光后操作 1：停止销售直到活动结束；2：恢复原价销售；
   */
  private Integer sellUpType;

  @JSON(serialize = false)
  public String getMutualIds() {
    if (mutualIds == null) {
      mutualIds = "";
    }
    return mutualIds;
  }

  public void setMutualIds(String mutualIds) {
    this.mutualIds = mutualIds;
  }

  public Long getPromotionId() {
    return promotionId;
  }

  @JSON(serialize = false)
  public String getProductNameView() {
    if (carProductMap == null || carProductMap.isEmpty()) return "";
    Iterator<Map.Entry<String, CarProduct>> iterator = carProductMap.entrySet().iterator();
    String name = "";
    if (iterator.hasNext()) {
      Map.Entry<String, CarProduct> entry = iterator.next();
      name = entry.getValue().getTitle();

    }
    return name;
  }

  public BigDecimal getLastAllMoney() {
    BigDecimal allMoney = new BigDecimal(0);
    if (carProductMap == null || carProductMap.isEmpty()) return allMoney;
    Set<String> keySet = carProductMap.keySet();
    Iterator<String> i = keySet.iterator();
    while (i.hasNext()) {
      CarProduct cp = carProductMap.get(i.next());
      if (cp.getIsChecked()) {
        allMoney = allMoney.add(cp.getLastAllMoney());
      }
    }
    return allMoney;
  }

  public void setPromotionInfoId(Long promotionId) {
    this.promotionId = promotionId;
  }

  /**
   * 标题（广告语）
   * 
   * @return
   */
  @JSON(serialize = false)
  public String getTitle() {
    return title;
  }

  /**
   * 标题（广告语）
   * 
   * @return
   */
  public void setTitle(String title) {
    this.title = title;
  }

  public String getPromotionName() {
    return promotionName;
  }

  public void setPromotionName(String promotionName) {
    this.promotionName = promotionName;
  }

  public void setPromotionId(Long promotionId) {
    this.promotionId = promotionId;
  }

  /**
   * 规则满足数值
   * 
   * @return
   */
  public BigDecimal getMeetData() {
    if (null != ruleDatas && ruleDatas.size() >= 1) {
      return ruleDatas.get(ruleDatas.size() - 1).getMeetData();
    } else {
      return null;
    }
  }

  /**
   * 规则满足数值
   * 
   * @return
   */
  public void setMeetData(BigDecimal meetData) {
    this.meetData = meetData;
  }

  /**
   * 规则优惠数值
   * 
   * @return
   */
  @JSON(serialize = false)
  public BigDecimal getPrivilegeData() {
    return privilegeData;
  }

  /**
   * 规则优惠数值
   * 
   * @return
   */
  public void setPrivilegeData(BigDecimal privilegeData) {
    this.privilegeData = privilegeData;
  }

  /**
   * 规则优惠数值类型(1：减免金额；2：折扣率；3：优惠券ID;4:特价价格)
   * 
   * @return
   */
  @JSON(serialize = false)
  public Integer getPrivilegeDataType() {
    return privilegeDataType;
  }

  /**
   * 规则优惠数值类型(1：减免金额；2：折扣率；3：优惠券ID;4:特价价格)
   * 
   * @return
   */
  public void setPrivilegeDataType(Integer privilegeDataType) {
    this.privilegeDataType = privilegeDataType;
  }

  /**
   * 活动类型10特价；8打折；6满额减免；4满额送券
   * 
   * @return
   */
  public Integer getPromotionType() {
    return promotionType;
  }

  /**
   * 活动类型10特价；8打折；6满额减免；4满额送券
   * 
   * @return
   */
  public void setPromotionType(Integer promotionType) {
    this.promotionType = promotionType;
  }

  /**
   * 活动优先级
   * 
   * @return
   */
  @JSON(serialize = false)
  public Integer getPriority() {
    return priority;
  }

  /**
   * 活动优先级
   * 
   * @return
   */
  public void setPriority(Integer priority) {
    this.priority = priority;
  }

  /**
   * 活动规则数据
   * 
   * @return
   */
  @JSON(serialize = false)
  public List<PromotionRuleData> getRuleDatas() {
    return ruleDatas;
  }

  /**
   * 活动规则数据
   * 
   * @return
   */
  public void setRuleDatas(List<PromotionRuleData> ruleDatas) {
    this.ruleDatas = ruleDatas;
  }

  // 用于查询
  private Date startTime;
  private Date endTime;

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

  private int minBuy;
  private int maxBuy;
  private int promotionStock;
  private int promotionSell;

  public JSONObject toJsonObject() {
    JSONObject json = new JSONObject();
    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    json.put("promotionName", this.promotionName);
    json.put("endTime", this.endTime);
    json.put("startTime", this.startTime);
    // json.put("title",this.title);
    // json.put("ruleId",this.ruleId);
    // json.put("promotionType",this.promotionType);
    json.put("promotionTypeName", this.getPromotionTypeName());
    json.put("promotionId", this.promotionId);
    // json.put("privilegeData", privilegeData);
    // json.put("meetData", meetData);
    // json.put("productFilterType", productFilterType);
    // String ruleKey = "ruleName";
    // String ruleValue="";
    // switch (this.promotionType) {
    // case 10:
    // ruleValue
    // +="特价:价格"+(privilegeData==null?"null":privilegeData.doubleValue());
    // break;
    // case 8:
    // ruleValue +="折扣:打"+privilegeData.doubleValue()+"折";
    // break;
    // case 6:
    // //ruleValue
    // +="满减:满"+meetData.doubleValue()+"元，立减"+privilegeData.doubleValue()+"元";
    // break;
    // case 4:
    // //ruleValue
    // +="满送:满"+meetData.doubleValue()+"元，立送id为"+privilegeData.longValue()+"的优惠券";
    // break;
    //			
    // default:
    // break;
    // }
    // json.put(ruleKey, ruleValue);
    return json;
  }

  // public String toString(){
  // return this.toJsonObject().toJSONString();
  // }

  @JSON(serialize = false)
  public String getCmsUrlPath() {
    return cmsUrlPath;
  }

  public void setCmsUrlPath(String cmsUrlPath) {
    this.cmsUrlPath = cmsUrlPath;
  }

  public String getPromotionTypeName() {
    if (promotionTypeName == null) {
      promotionTypeName = PromotionInfoUtil.typeNameMap.get(this.promotionType);
    }
    return promotionTypeName;
  }

  public void setPromotionTypeName(String promotionTypeName) {
    this.promotionTypeName = promotionTypeName;
  }

  /**
   * {1:'全场',2:'指定商品',3:'商品类目',4:'商品品牌',5:'指定商家'}
   * 
   * @return
   */
  @JSON(serialize = false)
  public Integer getProductFilterType() {
    return productFilterType;
  }

  /**
   * {1:'全场',2:'指定商品',3:'商品类目',4:'商品品牌',5:'指定商家'}
   * 
   * @param productFilterType
   */
  public void setProductFilterType(Integer productFilterType) {
    this.productFilterType = productFilterType;
  }

  @JSON(serialize = false)
  public String getProductFilterSql() {
    return productFilterSql;
  }

  public void setProductFilterSql(String productFilterSql) {
    this.productFilterSql = productFilterSql;
  }

  /**
   * 降序排列 优先级高的排前面 返回负数this优先级高 </br> 1、商品作用域优先级高于订单优先级</br> 2、同一作用域 强制排他优先级最高</br> 3、类型优先级
   * 特价>打折>满减>满送</br> 4、以上情况相同 优先级数字越大 优先级越高</br> 5、以上情况相同 活动开始时间越晚 优先级越高</br>
   */
  @Override
  public int compareTo(PromotionInfo o) {
    int compare = 0;
    if (getPromotionId().intValue() - o.getPromotionId().intValue() == 0) {
      return compare;
    }
    Integer opt = o.getPromotionType();
    PromotionTypeEnums en = PromotionInfoUtil.promotionTypeEnumsMap.get(opt);
    PromotionTypeEnums thisEn = PromotionInfoUtil.promotionTypeEnumsMap.get(this.promotionType);
    compare = thisEn.getScope() - en.getScope();
    if (compare == 0) {
      if (getMutualIds().equals("all") && o.getMutualIds().equals("all")) {
        compare = 0;
      } else if (!this.mutualIds.equals("all") && !o.getMutualIds().equals("all")) {
        compare = 0;
      } else {
        if (this.mutualIds.equals("all")) {
          compare = 1;
        } else if (o.getMutualIds().equals("all")) {
          compare = -1;
        }
      }
    }
    if (compare == 0) {
      compare = this.promotionType - o.getPromotionType();
    }

    if (compare == 0) {
      compare = this.priority - o.getPriority();
    }
    if (compare == 0) {
      compare = this.startTime.compareTo(o.getStartTime());
    }
    return compare * (-1);
  }

  public static void main(String[] age) {

  }

  @JSON(serialize = false)
  public Boolean getExcludeAll() {
    return mutualIds != null && mutualIds.equals("all");
    // return !doStack;
  }

  @JSON(serialize = false)
  public Map<String, CarProduct> getCarProductMap() {
    if (carProductMap == null) {
      carProductMap = new HashMap<String, CarProduct>();
    }
    return carProductMap;
  }

  public void setCarProductMap(Map<String, CarProduct> carProductMap) {
    this.carProductMap = carProductMap;
  }

  @JSON(serialize = false)
  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  @JSON(serialize = false)
  public int getMinBuy() {
    return minBuy;
  }

  public void setMinBuy(int minBuy) {
    this.minBuy = minBuy;
  }

  @JSON(serialize = false)
  public int getMaxBuy() {
    return maxBuy;
  }

  public void setMaxBuy(int maxBuy) {
    this.maxBuy = maxBuy;
  }

  @JSON(serialize = false)
  public int getPromotionStock() {
    return promotionStock;
  }

  public void setPromotionStock(int promotionStock) {
    this.promotionStock = promotionStock;
  }

  @JSON(serialize = false)
  public int getPromotionSell() {
    return promotionSell;
  }

  public void setPromotionSell(int promotionSell) {
    this.promotionSell = promotionSell;
  }

  public List<CarProduct> getGiftCarProductList() {
    return giftCarProductList;
  }

  public void setGiftCarProductList(List<CarProduct> giftCarProductList) {
    this.giftCarProductList = giftCarProductList;
  }

  public Long getSellerId() {
    return sellerId;
  }

  public void setSellerId(Long sellerId) {
    this.sellerId = sellerId;
  }

  public BigDecimal getPromotionData() {
    return promotionData;
  }

  public void setPromotionData(BigDecimal promotionData) {
    this.promotionData = promotionData;
  }

  /** 商家类别 ：1所有；2指定入驻商家；3康美自营代销；4后期 */
  public Integer getShopSort() {
    return shopSort;
  }

  /**
   * 商家类别 ：1所有；2指定入驻商家；3康美自营代销；4后期
   */
  public void setShopSort(Integer shopSort) {
    this.shopSort = shopSort;
  }

  /** 活动所属入驻商家 */
  public Long getSupplierId() {
    return supplierId;
  }

  /** 活动所属入驻商家 */
  public void setSupplierId(Long supplierId) {
    this.supplierId = supplierId;
  }

  public Integer getSellUpType() {
    return sellUpType;
  }

  public void setSellUpType(Integer sellUpType) {
    this.sellUpType = sellUpType;
  }
  
}
