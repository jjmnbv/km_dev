package com.kmzyc.b2b.vo;

/**
 * 对应的分值
 * 
 * @author Administrator
 * 
 */
public class OrderAssessPoint {
  // 物流发货速度
  public static String one = "Assess_Type_one";
  // 包装是否完好
  public static String two = "Assess_Type_two";
  // 卖家发货速度
  public static String three = "Assess_Type_three";
  // 售前售后服务
  public static String four = "Assess_Type_four";

  /**
   * 物流发货速度分数
   */
  private Integer Assess_Type_one;

  /**
   * 包装是否完好分数
   */
  private Integer Assess_Type_two;

  /**
   * 卖家发货速度分数
   */
  private Integer Assess_Type_three;

  /**
   * 售前售后服务分数
   */
  private Integer Assess_Type_four;

  public static String getOne() {
    return one;
  }

  public static void setOne(String one) {
    OrderAssessPoint.one = one;
  }

  public static String getTwo() {
    return two;
  }

  public static void setTwo(String two) {
    OrderAssessPoint.two = two;
  }

  public static String getThree() {
    return three;
  }

  public static void setThree(String three) {
    OrderAssessPoint.three = three;
  }

  public static String getFour() {
    return four;
  }

  public static void setFour(String four) {
    OrderAssessPoint.four = four;
  }

  public Integer getAssess_Type_one() {
    return Assess_Type_one;
  }

  public void setAssess_Type_one(Integer assess_Type_one) {
    Assess_Type_one = assess_Type_one;
  }

  public Integer getAssess_Type_two() {
    return Assess_Type_two;
  }

  public void setAssess_Type_two(Integer assess_Type_two) {
    Assess_Type_two = assess_Type_two;
  }

  public Integer getAssess_Type_three() {
    return Assess_Type_three;
  }

  public void setAssess_Type_three(Integer assess_Type_three) {
    Assess_Type_three = assess_Type_three;
  }

  public Integer getAssess_Type_four() {
    return Assess_Type_four;
  }

  public void setAssess_Type_four(Integer assess_Type_four) {
    Assess_Type_four = assess_Type_four;
  }

}
