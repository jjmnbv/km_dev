package com.kmzyc.supplier.vo;

/**
 * 用于首页的各个按钮汇总的提示实体
 * 
 * @author KM
 * 
 */
public class CountInfo implements java.io.Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 待结转订单总数
   */
  private int waitSettCount;
  /**
   * 待付款订单总数
   */
  private int waitPayCount;
  /**
   * 已完成订单总数
   */
  private int alreadyCompleteCount;
  /**
   * 待发货订单总数
   */
  private int waitShipCount;

  /**
   * 已审核待上架商品总数
   */
  private int alreadyAuditCount;

  /**
   * 审核未通过商品总数
   */
  private int unsuccessAuditCount;

  /**
   * 待审核商品总数
   */
  private int waitAuditCount;

  /**
   * 已上架商品总数
   */
  private int upshelfCount;
  /**
   * 已下架商品总数
   */
  private int downshelfCount;
  /**
   * 草稿状态商品总数
   */
  private int draftCount;

  /**
   * 草稿库中除了 已上架的或者已上架的商品在草稿库中修改的总数 真正的待售(非上架)商品总数 = 已下架的商品 + 草稿库中除已上过架或者已下过架的商品总数
   * (针对已上架过的商品在草稿库中会存在修改记录的)
   * 
   * 20150911 update 该字段暂未启用 改版新需求,针对已经上过架的商品修改不再在草稿库里有数据,所以这块不用再做排除,反正就是
   * 非上架状态的所有商品都属于待售(已下架+所有草稿库状态)
   */
  private int waitSaleDraftCount;

  /**
   * 退换货订单总数
   */
  private int returnOrderCount;


  /**
   * 今日成交订单总数
   */
  private int todaySuccessCount;

  /**
   * 昨日成交订单总数
   */
  private int yesterdaySuccessCount;


  public int getAlreadyAuditCount() {
    return alreadyAuditCount;
  }

  public void setAlreadyAuditCount(int alreadyAuditCount) {
    this.alreadyAuditCount = alreadyAuditCount;
  }

  public int getUnsuccessAuditCount() {
    return unsuccessAuditCount;
  }

  public void setUnsuccessAuditCount(int unsuccessAuditCount) {
    this.unsuccessAuditCount = unsuccessAuditCount;
  }


  public int getUpshelfCount() {
    return upshelfCount;
  }

  public void setUpshelfCount(int upshelfCount) {
    this.upshelfCount = upshelfCount;
  }

  public int getDownshelfCount() {
    return downshelfCount;
  }

  public void setDownshelfCount(int downshelfCount) {
    this.downshelfCount = downshelfCount;
  }

  public int getDraftCount() {
    return draftCount;
  }

  public void setDraftCount(int draftCount) {
    this.draftCount = draftCount;
  }

  public int getWaitSaleDraftCount() {
    return waitSaleDraftCount;
  }

  public void setWaitSaleDraftCount(int waitSaleDraftCount) {
    this.waitSaleDraftCount = waitSaleDraftCount;
  }

  public int getReturnOrderCount() {
    return returnOrderCount;
  }

  public void setReturnOrderCount(int returnOrderCount) {
    this.returnOrderCount = returnOrderCount;
  }

  public int getTodaySuccessCount() {
    return todaySuccessCount;
  }

  public void setTodaySuccessCount(int todaySuccessCount) {
    this.todaySuccessCount = todaySuccessCount;
  }

  public int getYesterdaySuccessCount() {
    return yesterdaySuccessCount;
  }

  public void setYesterdaySuccessCount(int yesterdaySuccessCount) {
    this.yesterdaySuccessCount = yesterdaySuccessCount;
  }

  public int getWaitAuditCount() {
    return waitAuditCount;
  }

  public void setWaitAuditCount(int waitAuditCount) {
    this.waitAuditCount = waitAuditCount;
  }

  public int getWaitSettCount() {
    return waitSettCount;
  }

  public void setWaitSettCount(int waitSettCount) {
    this.waitSettCount = waitSettCount;
  }

  public int getWaitPayCount() {
    return waitPayCount;
  }

  public void setWaitPayCount(int waitPayCount) {
    this.waitPayCount = waitPayCount;
  }

  public int getAlreadyCompleteCount() {
    return alreadyCompleteCount;
  }

  public void setAlreadyCompleteCount(int alreadyCompleteCount) {
    this.alreadyCompleteCount = alreadyCompleteCount;
  }

  public int getWaitShipCount() {
    return waitShipCount;
  }

  public void setWaitShipCount(int waitShipCount) {
    this.waitShipCount = waitShipCount;
  }

}
