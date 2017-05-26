package com.kmzyc.framework.constants;

public enum RulesTableName {
  // 每会员每XX天可以参与 XX次；
  CRLE1(1l, "LOTTERY_CRULE_1", "每会员每XX天可以参与 XX次"), CRLE2(2l, "LOTTERY_CRULE_2", "每次参与消费XX个积分"), CRLE3(3l,
      "LOTTERY_CRULE_3", "通过手机验证的会员才可以参与抽奖"), CRLE4(4l, "LOTTERY_CRULE_4", "在XX到XX 期间注册的会员才可以参与"), CRLE5(5l,
      "LOTTERY_CRULE_5", "以上等级的会员才可以参与"), CRLE6(6l, "LOTTERY_CRULE_6", "每XX天允许XX人次参与"), CRLE7(7l, "LOTTERY_CRULE_7",
      "从XX开始计算，已完成订单商品总额（不含运费）满XX元才可参与"), CRLE8(12l, "CRULE_8",
      "从XX开始计算，指定范围已完成订单实付金额（含运费）每满【金额B】元可参加【次数C】次"), CRLE9(13l, "LOTTERY_CRULE_9",
      "从XX开始计算，指定范围已支付订单实付金额（含运费）每满【金额B】元可参加【次数C】次"), Zhrule8(8l, "LOTTERY_ZHRULE_8", "每会员每XX天可以中奖XX次"), ZJRULE9(
      9l, "LOTTERY_ZJRULE_9", "只有在 XX至 XX期间可能中奖"), ZJRULE10(10l, "LOTTERY_ZJRULE_10", "每XX天最多可以开出该奖项XX份"), ZJRULE11(
      11l, "LOTTERY_ZJRULE_11", "每个会员最多可以中多少次奖");

  RulesTableName(Long id, String types, String title) {
    this.id = id;
    this.type = types;
    this.titile = title;
  }

  private Long id;

  private String titile;

  private String type;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getTitle() {
    return titile;
  }

  public void setTitle(String title) {
    this.titile = title;
  }

  public String toString() {
    StringBuilder strBuilder = new StringBuilder();
    strBuilder.append("RulesTableName[type=").append(this.type).append(",title=").append(
        this.titile).append(",id=").append(this.id).append("]");
    return strBuilder.toString();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
