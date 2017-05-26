package com.kmzyc.search.facade.constants;

/**
 * 定义APP接口返回JSON数据格式的KEY
 * 
 * @author river
 * 
 */
public interface APPResultKeys {
  // 产品列表
  public static final String PRODUCTS = "products";
  // ID
  public static final String ID = "id";
  // 名称
  public static final String NAME = "name";
  // 价格
  public static final String PRICE = "price";
  // 市场价格
  public static final String MPRICE = "mprice";
  // 图片URL1
  public static final String IMAGE100 = "image100";
  // 图片URL2
  public static final String IMAGE170 = "image170";
  // 图片URL3
  public static final String IMAGE240 = "image240";
  // 总数
  public static final String COUNT = "totalNum";
  // 页码
  public static final String PAGINATION = "pageNo";
  // 当前页数据量
  public static final String SIZE = "pageNum";

  // sku的时代PV值,也称为积分 20151008 add
  public static final String PVALUE = "pValue";
}
