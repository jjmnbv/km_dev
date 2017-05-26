package com.pltfm.app.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.kmzyc.commons.exception.ActionException;
import com.kmzyc.product.remote.service.CategoryRemoteService;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.entities.OrderDictionary;
import com.pltfm.app.service.OrderDictionaryService;
import com.pltfm.app.service.OrderItemQryService;
import com.pltfm.app.service.OrderPayStatementService;
import com.pltfm.app.service.OrderQryService;
import com.pltfm.app.vobject.Category;
import com.pltfm.sys.model.SysUser;

import redis.clients.jedis.JedisCluster;

@Controller("saleReportAction")
@Scope("prototype")
@SuppressWarnings("unchecked")
public class SaleReportAction extends BaseAction {
  private static final long serialVersionUID = 1L;
  private Logger log = Logger.getLogger(SaleReportAction.class);
  @Resource
  private OrderDictionaryService orderDictionaryService;
  @Resource
  private OrderPayStatementService orderPayStatementService;
  @Resource
  private OrderQryService orderQryService;
  @Resource
  private OrderItemQryService orderItemQryService;
  
  @Resource
  private CategoryRemoteService categoryRemoteService;

  private List<OrderDictionary> payMethodList;
  private List<OrderDictionary> channelList;
  private Map<String, String> map = new HashMap<String, String>();

  private String channel;
  private String orderSource;
  private int payMethodWay;
  
  private String payMethodWayStr;
  
  private String commerceId;
  private String bCategoryId;
  private String mCategoryId;
  private String sCategoryId;
  private String commoditySku;
  private String supplier;
  private String orderPreferentialCode;

  private BigDecimal totalMoney;// 订单总金额

  private BigDecimal totalActual;// 实付总额
  
  private Map<String, Object> reMap = new HashMap<String,Object>();
  
  
  

@Resource
  private JedisCluster jedisCluster;
  
  
  
  /**
   * 销售统计表初始化
   * 
   * @return
   * @throws ActionException
   */
  public String searchInit() {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    map.put("paymentWay", "");
    map.put("channel", "");
    map.put("startDate", formatter.format(new Date()) + " 00:00:00");
    map.put("endDate", formatter1.format(new Date()));
    payMethodList = orderDictionaryService.queryDictionaryByType("Pay_Method");
    channelList = orderDictionaryService.queryDictionaryByType("Channel");
    payMethodList = getPayMethodWithOutConpon();
    return SUCCESS;
  }

  /**
   * 销售统计表
   * 
   * @return
   * @throws ActionException
   */
  public String searchReport() throws ActionException {
    channelList = orderDictionaryService.queryDictionaryByType("Channel");
    if (payMethodWay != -1 && payMethodWay != 0) {
        map.put("paymentWay", "" + payMethodWay);
      } else {
        map.put("paymentWay", "");
      }
    if (channel != null && !channel.equals("-1")) {
      map.put("channel", "" + channel);
    } else {
      map.put("channel", "");
    }
   
    
    try {
      String startDate = map.get("startDate");
      if (null == startDate || 0 == startDate.length()) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        map.put("endDate", sdf.format(cal.getTime()));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        map.put("startDate", sdf.format(cal.getTime()));
      }
      map.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
      map.put("end", (getPage().getPageNo() * getPage().getPageSize()) + "");
      getPage().setRecordCount(orderPayStatementService.querySaleReportCount(map));
      getPage().setDataList(orderPayStatementService.querySaleReport(map));
      payMethodList = orderDictionaryService.queryDictionaryByType("Pay_Method");
      // payMethodList =
      getPayMethodWithOutConpon();
    } catch (Exception e) {
      log.error("查询报表异常", e);
      throw new ActionException();
    }
    return SUCCESS;
  }

  /**
   * 导出销售分析表
   * 
   * @return
   * @throws ActionException
   */
  public String createSaleReport() throws ActionException {
   if (payMethodWay != -1) {
          map.put("paymentWay", "" + payMethodWay);
    } else {
          map.put("paymentWay", "");
    }
    if (channel != null && !channel.equals("-1")) {
      map.put("channel", "" + channel);
    } else {
      map.put("channel", "");
    }
    
       
    try {
      Map session = ActionContext.getContext().getSession();
      SysUser sysuser = (SysUser) session.get("sysUser");
      String path = orderPayStatementService.saleReportExportExcel(map, sysuser.getUserId());
      getHttpServletResponse().getWriter().print("{'path':'" + path + "'}");
    } catch (Exception e) {
      log.error("销售分析表导出", e);
      throw new ActionException();
    }
    return null;
  }

  /**
   * 订单分析表
   * 
   * @return
   */
  public String analysisReportInit() {
    map = new HashMap<String, String>();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    map.put("channel", "");
    map.put("startDate", formatter.format(new Date()) + " 00:00:00");
    map.put("endDate", formatter1.format(new Date()));
    channelList = orderDictionaryService.queryDictionaryByType("Channel");
    return SUCCESS;
  }

  /**
   * 订单分析表
   * 
   * @return
   */
  public String analysisReport() throws ActionException {
    payMethodList = orderDictionaryService.queryDictionaryByType("Pay_Method");
    channelList = orderDictionaryService.queryDictionaryByType("Channel");
    if (channel != null && !channel.equals("-1")) {
      map.put("channel", "" + channel);
    } else {
      map.put("channel", "");
    }
    try {
      String startDate = map.get("startDate");
      if (null == startDate || 0 == startDate.length()) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        map.put("endDate", sdf.format(cal.getTime()));
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        map.put("startDate", sdf.format(cal.getTime()));
      }
      map.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
      map.put("end", (getPage().getPageNo() * getPage().getPageSize()) + "");
      getPage().setRecordCount(orderQryService.queryAnalysisAccount(map));
      getPage().setDataList(orderQryService.queryAnalysisReport(map));
    } catch (Exception e) {
      log.error("订单分析异常", e);
      throw new ActionException();
    }
    return SUCCESS;
  }

  public List<OrderDictionary> getPayMethodWithOutConpon() {
    List<OrderDictionary> result = new ArrayList<OrderDictionary>();
    result.addAll(payMethodList);
    for (OrderDictionary dict : payMethodList) {
      if (dict.getOrderDictionaryKey() == 2) {
        result.remove(dict);
        continue;
      }
    }
    return result;
  }

  /**
   * 导出订单分析表
   * 
   * @return
   */
  public String orderAnalysisReportExport() throws ActionException {
    if (channel != null && !channel.equals("-1")) {
      map.put("channel", "" + channel);
    } else {
      map.put("channel", "");
    }
    try {
      Map session = ActionContext.getContext().getSession();
      SysUser sysuser = (SysUser) session.get("sysUser");
      String path = orderQryService.analysisReportExport(map, sysuser.getUserId());
      getHttpServletResponse().getWriter().print("{'path':'" + path + "'}");
    } catch (Exception e) {
      log.error("导出订单分析表异常", e);
      throw new ActionException();
    }
    return null;
  }

  /**
   * 商品销售分析表初始化
   * 
   * @return
   */
  public String goodsReportInit() {
    map = new HashMap<String, String>();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    map.put("channel", "");
    map.put("startDate", formatter.format(new Date()) + " 00:00:00");
    map.put("endDate", formatter1.format(new Date()));
    channelList = orderDictionaryService.queryDictionaryByType("Channel");
    return SUCCESS;
  }

  public String selectCategory(){
      PrintWriter out = null;
      Map jsonMap = new HashMap();
      Category category = new Category();
      category.setCategoryId(new Long(getHttpServletRequest().getParameter("id")));
      category.setStatus(1);
      try {
          if(category.getCategoryId()==0){//如果是0,则返回空
              jsonMap.put("categoryList", null);
          }else{
              //用产品的远程接口
              List<Category> categoryList = categoryRemoteService.findCategoryChildrenList(category);
              jsonMap.put("categoryList", categoryList);
//            super.getResponse().setCharacterEncoding("UTF-8");
          }
          out = getHttpServletResponse().getWriter();
          out.print(JSON.toJSONString(jsonMap));
      } catch (IOException e) {
          e.printStackTrace();
      } catch (Exception e) {
          e.printStackTrace();
      }finally{
          if(out!=null){
              out.flush();
              out.close();
          }
      }
      
      return null;
  }
  
  /**
   * 商品销售分析表
   * 
   * @return
   * @throws ActionException
   */
  public String goodsReport() throws ActionException {
      
      long c1=0,c2 = 0,c3=0;
    channelList = orderDictionaryService.queryDictionaryByType("Channel");
    payMethodList = orderDictionaryService.queryDictionaryByType("Pay_Method");
    // payMethodList =
    getPayMethodWithOutConpon();
    
    
    if (channel != null && !channel.equals("-1")) {
      map.put("channel", "" + channel);
    } else {
      map.put("channel", "");
    }
    if (payMethodWayStr != null  && !payMethodWayStr.equals("-1")) {
        map.put("paymentWay", "%" + payMethodWayStr +"%");
        map.put("payMethodWayStr", "" + payMethodWayStr);
   } else {
        map.put("paymentWay", "");
        map.put("payMethodWayStr", "");
   }
    if (orderSource != null && !orderSource.equals("-1")) {
        map.put("orderSource", "" + orderSource);
      } else {
        map.put("orderSource", "");
      }
  if (commerceId != null && !commerceId.equals("-1")) {
      map.put("commerceId", "" + commerceId);
    } else {
      map.put("commerceId", "");
    }
    if (bCategoryId != null && !bCategoryId.equals("")&&!bCategoryId.equals("-1")) {
      map.put("bCategoryId", "" + bCategoryId);
      c1 = Long.valueOf(bCategoryId);
    } else {
      map.put("bCategoryId", "");
    }
    if (mCategoryId != null && !mCategoryId.equals("")&&!mCategoryId.equals("-1")) {
        map.put("mCategoryId", "" + mCategoryId);
        c2 = Long.valueOf(mCategoryId);
    } else {
        map.put("mCategoryId", "");
    }      
    if (sCategoryId != null && !sCategoryId.equals("")&&!sCategoryId.equals("-1")) {
        map.put("sCategoryId", "" + sCategoryId);
        c3 = Long.valueOf(sCategoryId);
     } else {
        map.put("sCategoryId", "");
     }

    //用产品的接口初始化联动下拉选择框
    initCategoryList(c1,c2,c3);
    
     if (commoditySku != null && !commoditySku.equals("-1")) {
        map.put("commoditySku", "" + commoditySku);
     } else {
        map.put("commoditySku", "");
     }       
     if (supplier != null && !supplier.equals("-1")) {
         map.put("supplier", "" + supplier);
     } else {
         map.put("supplier", "");
     }
     if (orderPreferentialCode != null && !orderPreferentialCode.equals("-1")) {
          map.put("orderPreferentialCode", "" + orderPreferentialCode);
     } else {
          map.put("orderPreferentialCode", "");
     }  

    try {
      String startDate = map.get("startDate");
      if (null == startDate || 0 == startDate.length()) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        map.put("endDate", sdf.format(cal.getTime()));
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        map.put("startDate", sdf.format(cal.getTime()));
      }
      map.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
      map.put("end", (getPage().getPageNo() * getPage().getPageSize()) + "");
      getPage().setRecordCount(orderItemQryService.queryGoodsCount(map));
      getPage().setDataList(orderItemQryService.queryGoodsReport(map));
      
      HashMap<String, Object> moneyM = (HashMap<String, Object>) orderItemQryService.listGoodsMoney(map);
      totalMoney = (BigDecimal) moneyM.get("ORDERMONEY");
      totalActual = (BigDecimal) moneyM.get("ACTUALMONEY");
    } catch (Exception e) {
      log.error("商品销售分析表异常！", e);
      throw new ActionException();
    }
    return SUCCESS;
  }

  
  /**
   * 初始化三级目录
   *
   * @param bCategoryId
   *            1级目录id
   * @param mCategoryId
   *            2级目录id
   * @param categoryId
   *            3级目录id
   */
  protected void initCategoryList(Long bCategoryId, Long mCategoryId, Long categoryId) {
      // 一级目录
      getBcategoryList(new Long(0));

      // 二级目录
      if (bCategoryId != null && bCategoryId.intValue() != 0) {
          getMcategoryList(bCategoryId);
      }

      // 三级目录
      if (mCategoryId != null && mCategoryId.intValue() != 0) {
          getScategoryList(mCategoryId);
      }
  }
  
  /**
   * 获取一级目录
   *
   * <note> 此方法中固定住二级和三级目录list为空,同时只取物理类目 </note>
   * 
   * @param cId
   */
  protected void getBcategoryList(Long cId) {
      Category category = new Category();
      category.setCategoryId(cId);
      category.setStatus(1);
      category.setIsPhy(1);
      List<Category> categoryList = null;
      try {
          categoryList = categoryRemoteService.findCategoryChildrenList(category);
      } catch (Exception e) {
          e.printStackTrace();
      }
      getHttpServletRequest().setAttribute("categoryList", categoryList);
      getHttpServletRequest().setAttribute("mCategoryList", new ArrayList());
      getHttpServletRequest().setAttribute("sCategoryList", new ArrayList());
  }
  
  /**
   * 获取2级目录
   *
   * @param cId
   *            1级目录id
   */
  protected void getMcategoryList(Long cId) {
      getCategoryList(cId, "mCategoryList");
  }

  /**
   * 获取3级目录
   *
   * @param cId
   *            2级目录id
   */
  protected void getScategoryList(Long cId) {
      getCategoryList(cId, "sCategoryList");
  }
 

  /**
   * 获取某目录下的子目录列表
   *
   * @param cId
   *            父级目录id
   * @param attributeName
   *            子级目录列表属性名称
   */
  protected void getCategoryList(Long cId, String attributeName) {
      Category category = new Category();
      category.setCategoryId(cId);
      category.setStatus(1);
      List<Category> categoryList = null;
      try {
          categoryList = categoryRemoteService.findCategoryChildrenList(category);
      } catch (Exception e) {
          log.error("获取目录列表失败，" + e.getMessage(), e);
      }
      getHttpServletRequest().setAttribute(attributeName, categoryList);
  }

/**
   * 导出商品销售分析表
   * 
   * @return
   * @throws ActionException
   */
  public String createGoodsReport() throws ActionException {
      if (channel != null && !channel.equals("-1")) {
          map.put("channel", "" + channel);
        } else {
          map.put("channel", "");
        }
      if (payMethodWayStr != null  && !payMethodWayStr.equals("-1")) {
          map.put("paymentWay", "%" + payMethodWayStr +"%");
          map.put("payMethodWayStr", "" + payMethodWayStr);
     } else {
          map.put("paymentWay", "");
          map.put("payMethodWayStr", "");
     }
        if (orderSource != null && !orderSource.equals("-1")) {
            map.put("orderSource", "" + orderSource);
          } else {
            map.put("orderSource", "");
          }
      if (commerceId != null && !commerceId.equals("-1")) {
          map.put("commerceId", "" + commerceId);
        } else {
          map.put("commerceId", "");
        }
        if (bCategoryId != null && !bCategoryId.equals("-1")) {
          map.put("bCategoryId", "" + bCategoryId);
        } else {
          map.put("bCategoryId", "");
        }
        if (mCategoryId != null && !mCategoryId.equals("-1")) {
            map.put("mCategoryId", "" + mCategoryId);
        } else {
            map.put("mCategoryId", "");
        }      
        if (sCategoryId != null && !sCategoryId.equals("-1")) {
            map.put("sCategoryId", "" + sCategoryId);
         } else {
            map.put("sCategoryId", "");
         }
         if (commoditySku != null && !commoditySku.equals("-1")) {
            map.put("commoditySku", "" + commoditySku);
         } else {
            map.put("commoditySku", "");
         }       
         if (supplier != null && !supplier.equals("-1")) {
             map.put("supplier", "" + supplier);
         } else {
             map.put("supplier", "");
         }
         if (orderPreferentialCode != null && !orderPreferentialCode.equals("-1")) {
              map.put("orderPreferentialCode", "" + orderPreferentialCode);
         } else {
              map.put("orderPreferentialCode", "");
         }  

    try {
      Map session = ActionContext.getContext().getSession();
      SysUser sysuser = (SysUser) session.get("sysUser");
      String path = orderItemQryService.goodsReportExportExcel(map, sysuser.getUserId());
      getHttpServletResponse().getWriter().print("{'path':'" + path + "'}");
    } catch (Exception e) {
      log.error("导出销售分析表异常！", e);
      throw new ActionException();
    }
    return null;
  }

  /**
   * 客户采购分析报表
   * 
   * @return
   * @throws ActionException
   */
  public String customerPurchaseAnalysis() throws ActionException {
    if (!"-1".equals(channel)) {
      map.put("channel", channel);
    } else {
      map.put("channel", "");
    }
    try {
      String startDate = map.get("startDate");
      if (null == startDate || 0 == startDate.length()) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        map.put("endDate", sdf.format(cal.getTime()));
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        map.put("startDate", sdf.format(cal.getTime()));
      }
      int pageNo = getPage().getPageNo();
      int pageSize = getPage().getPageSize();
      map.put("start", ((pageNo - 1) * pageSize + 1) + "");
      map.put("end", (pageNo * pageSize) + "");
      channelList = orderDictionaryService.queryDictionaryByType("Channel");
      getPage().setRecordCount(orderQryService.countCustomerPurchaseAnalysis(map));
      getPage().setDataList(orderQryService.queryCustomerPurchaseAnalysisByPage(map));
    } catch (Exception e) {
      log.error("客户采购分析报表", e);
    }
    return SUCCESS;
  }

  /**
   * 导出客户采购分析报表
   * 
   * @return
   * @throws ActionException
   */
  public String customerPurchaseAnalysisExport() throws ActionException {
    SysUser sysuser = (SysUser) ActionContext.getContext().getSession().get("sysUser");
    Integer userId = 0;
    if (null != sysuser && null != sysuser.getUserId()) {
      userId = sysuser.getUserId();
    }
    String path;
    try {
      path = orderQryService.queryCustomerPurchaseAnalysis(map, userId);
      getHttpServletResponse().getWriter().print("{'path':'" + path + "'}");
    } catch (Exception e) {
      log.error("导出客户采购分析报表", e);
    }
    return null;
  }

  /**
   * 自营商城订单财务版报表
   * 
   * @return
   * @throws ActionException
   */
  public String financeOrderReport() throws ActionException {
      String startDate = map.get("startDate");
      if (null == startDate || startDate.length() < 1) {
        return "success";
      }
      SysUser sysuser = (SysUser) ActionContext.getContext().getSession().get("sysUser");
      Integer userId = 0;
      if (null != sysuser && null != sysuser.getUserId()) {
        userId = sysuser.getUserId();
      }
      String path;
      try {
        path = orderQryService.ExportFinanceOrderReport(map, userId);
        getHttpServletResponse().getWriter().print("{'path':'" + path + "'}");
      } catch (Exception e) {
        log.error("自营商城订单财务版报表", e);
      }
   
    
    return null;
  }
  
  
  public String gotoFinanceOrderReport() throws ActionException{
      return SUCCESS;
  }
  
  /**
   * 商城订单财务版报表
   * 
   * @return
   * @throws ActionException
   */
  public String AsynfinanceOrderReport() throws ActionException {
    
      SysUser sysuser = (SysUser) ActionContext.getContext().getSession().get("sysUser");
      Integer userId = 0;
      String  userName ="";
      if (null != sysuser && null != sysuser.getUserId()) {
        userId = sysuser.getUserId();
        userName = sysuser.getUserName();
      }
      
      String startDate = map.get("startDate");
      String endDate = map.get("endDate");
      if (null == startDate || startDate.length() < 1 || null == endDate || endDate.length() < 1) {
        return "success";
      }
      
      startDate += " 00:00:00";
      endDate += " 23:59:59";
      map.put("startDate", startDate);
      map.put("endDate", endDate);
      
    String key = "financeOrderReport_"+userId;
    Long i = jedisCluster.setnx(key, "1");
    if (i > 0) {//并发控制
        try{
            jedisCluster.expire(key, 60); // 缓存过期 1分钟 
            
            StringBuffer sf = new StringBuffer();
            sf.append("订单付款时间从"+startDate+"至"+endDate+";");
            String supplier = map.get("supplier");
            if(null != supplier && !supplier.isEmpty())
            {
                sf.append("供应商:"+supplier);
            }
            
            List<String> pathList = getExcelUrl("商城订单财务版", ".xls");
            String writeUrl = pathList.get(0);
            String readUrl = pathList.get(1);
            map.put("exportType", "0");
            try{
                String newKey = orderQryService.insertExportInfo(map, userName, readUrl);
                orderQryService.AsynExportFinanceOrderReport(map, userId, writeUrl,newKey);
            }catch(Exception e){
                log.error("商城订单财务版报表异常!"+e.getMessage());
            }
       }finally{
      jedisCluster.del(key);
  }  
    
    }
    
    return SUCCESS;
  }
  
  /**
   * 跳转到生成入驻商家结算报表页面
   * @return
   * @throws ActionException
   */
  public String gotoMerchantsOrderReport() throws ActionException{
      return SUCCESS;
  }
  
 /**
  * 入驻商家结算报表
  * 
  * @return
  * @throws ActionException
  */
 public String AsynmerchantsOrderReport() throws ActionException {
     
     SysUser sysuser = (SysUser) ActionContext.getContext().getSession().get("sysUser");
     Integer userId = 0;
     String  userName ="";
     if (null != sysuser && null != sysuser.getUserId()) {
       userId = sysuser.getUserId();
       userName = sysuser.getUserName();
     } 
     
     
     
     String key = "AsynmerchantsOrderReport"+userId;
     Long i = jedisCluster.setnx(key, "1");
     if (i > 0) {//并发控制
     try{
       jedisCluster.expire(key, 60); // 缓存过期 1分钟  
       String supplier = map.get("supplier");// 供应商名称
       String selectTimes = map.get("selectTimes");// 时间集,保存一个或多个账期
       
       List<String> pathList = getExcelUrl("入驻商家结算报表", ".xls");
       String writeUrl = pathList.get(0);
       String readUrl = pathList.get(1);
       String newKey = null;
       try{
           map.put("exportType", "1");
           newKey = orderQryService.insertExportInfo(map, userName, readUrl);
       }catch(Exception e){
           log.error("导出入驻商家结算报表功能,插入导出报表出错!"+e.getMessage());
           throw new ActionException("导出入驻商家结算报表功能,插入导出报表出错!"+e.getMessage());
       }
       
     
       if (null != selectTimes && !"".equals(selectTimes)) {
         String[] times = selectTimes.split(",");
         List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();// 用于存储根据账期得到的起止时间，一个元素代表一个账期对应的起止时间
    
         /* 1 获取开始及结束日期 */
         for (int j = 0; j < times.length; j++) {
           Map<String, String> maptime = new HashMap<String, String>();
           maptime.put("supplier", supplier);
           maptime.put("selectTimes", selectTimes);
           String time = times[j];
           maptime.put("ZQ", time);// 对应账期
           String year = time.substring(0, 4);
           String month = time.substring(4, 6);
           String hhmmss = time.substring(6);
           String startDate = "";
           String endDate = "";
           if ("H1".equals(hhmmss)) {
             startDate = year + "-" + month + "-01 00:00:00";
             endDate = year + "-" + month + "-16 00:00:00";
             maptime.put("startDate", startDate);
             maptime.put("endDate", endDate);
           } else if ("H2".equals(hhmmss)) {
             startDate = year + "-" + month + "-16 00:00:00";
    
             Calendar calendar = new GregorianCalendar();
             SimpleDateFormat sdf = new SimpleDateFormat("", Locale.ENGLISH);
             sdf.applyPattern("yyyyMM");
             try {
               calendar.setTime(sdf.parse(year + month));
             } catch (ParseException e) {
               log.error("时间转换出错");
               e.printStackTrace();
               return null;
             }
    
      
    
             calendar.add(Calendar.MONTH, +1); // 获取下个月的年月
             endDate = new SimpleDateFormat("yyyy-MM-01 00:00:00").format(calendar.getTime());
    
             maptime.put("startDate", startDate);
             maptime.put("endDate", endDate);
    
           }
           mapList.add(maptime);
         }
    
           try {
             orderQryService.AsynExportmerchantsOrderReport(mapList, userId, writeUrl, newKey);
           } catch (Exception e) {
             log.error("导出入驻商家结算报表出错"+e.getMessage());
             throw new ActionException("导出入驻商家结算报表出错"+e.getMessage());
           }
           
    
       } else {
         log.error("传入的账期为空！");
         return null;
       }
 }finally{
     jedisCluster.del(key);
 }  
     }
   return SUCCESS;
 }

  

  /**
   * 商家订单财务版报表
   * 
   * @return
   * @throws ActionException
   */
  public String merchantsOrderReport() throws ActionException {
    String supplier = map.get("supplier");// 供应商名称
    String selectTimes = map.get("selectTimes");// 时间集,保存一个或多个账期
    if (null != selectTimes && !"".equals(selectTimes)) {
      String[] times = selectTimes.split(",");
      List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();// 用于存储根据账期得到的起止时间，一个元素代表一个账期对应的起止时间

      /* 1 获取开始及结束日期 */
      for (int i = 0; i < times.length; i++) {
        Map<String, String> maptime = new HashMap<String, String>();
        maptime.put("supplier", supplier);
        maptime.put("selectTimes", selectTimes);
        String time = times[i];
        maptime.put("ZQ", time);// 对应账期
        String year = time.substring(0, 4);
        String month = time.substring(4, 6);
        String hhmmss = time.substring(6);
        String startDate = "";
        String endDate = "";
        if ("H1".equals(hhmmss)) {
          startDate = year + "-" + month + "-01 00:00:00";
          endDate = year + "-" + month + "-16 00:00:00";
          maptime.put("startDate", startDate);
          maptime.put("endDate", endDate);
        } else if ("H2".equals(hhmmss)) {
          startDate = year + "-" + month + "-16 00:00:00";

          Calendar calendar = new GregorianCalendar();
          SimpleDateFormat sdf = new SimpleDateFormat("", Locale.ENGLISH);
          sdf.applyPattern("yyyyMM");
          try {
            calendar.setTime(sdf.parse(year + month));
          } catch (ParseException e) {
            log.error("时间转换出错");
            e.printStackTrace();
            return SUCCESS;
          }

          // int dd = calendar.getActualMaximum(calendar.DAY_OF_MONTH); //获取实际用的最大天数
          // endDate = year+"-"+month+"-"+dd+" 23:59:59";

          calendar.add(Calendar.MONTH, +1); // 获取下个月的年月
          endDate = new SimpleDateFormat("yyyy-MM-01 00:00:00").format(calendar.getTime());

          maptime.put("startDate", startDate);
          maptime.put("endDate", endDate);

        }
        mapList.add(maptime);
      }

      /* 2 查询并导出数据 */
      if (null != supplier && !"".equals(supplier)) {

        SysUser sysuser = (SysUser) ActionContext.getContext().getSession().get("sysUser");
        Integer userId = 0;
        if (null != sysuser && null != sysuser.getUserId()) {
          userId = sysuser.getUserId();
        }
        String path = "";
        try {
          path = orderQryService.exportmerchantsOrderReport(mapList, userId);
          getHttpServletResponse().getWriter().print("{'path':'" + path + "'}");
        } catch (Exception e) {
          log.error("自营商城订单财务版报表", e);
        }
      }

    } else {
      log.error("传入的账期为空！");
      return SUCCESS;
    }


    return SUCCESS;
  }


  /**
   * 导出时代订单
   * 
   * @return
   * @throws ActionException
   */
  public String timeOrderExport() throws ActionException {
    try {
      String startDate = map.get("startDate");
      String endDate = map.get("endDate");
      if (null != startDate && startDate.length() > 0 && null != endDate && endDate.length() > 0) {
        getHttpServletRequest().setAttribute("result",
            orderQryService.queryTimesOrderForExport(map));
        getHttpServletRequest().setAttribute("time", startDate + "——" + endDate);
      }
    } catch (Exception e) {
      log.error("导出时代订单", e);
    }
    return SUCCESS;
  }

  
  /**
   * 收退款列表
   * 
   * @return
   * @throws ActionException
   */
  public String orderPayInfoList() throws ActionException {
      
     String menu =  map.get("menu");
     if("ok".equals(menu)){
         return SUCCESS;
     }
     
     map.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
     String end = getPage().getPageNo() * getPage().getPageSize()+"";
     map.put("end", end);
     
     
     
     List<String> flags = new ArrayList<String>();
     
     String payType = map.get("payType");//订单交易12，余额充值3
     String flag = map.get("flag");//付款1，退款2
     
     
     if("3".equals(payType) && "2".equals(flag)){ //余额充值，且为退款，返回空记录
         return SUCCESS;
     }
     
     
     if(null != payType && !payType.isEmpty() && (null == flag||flag.isEmpty())){
         if("12".equals(payType)){//订单交易
             flags.add("1");
             flags.add("2");
         }else if("3".equals(payType)){//余额支付
             flags.add("3");
         }
     }
     
     if("3".equals(payType)){
         flags.add("3");
     }
     
    if(null != flag && "1".equals(flag)){//付款
        if(!flags.contains("1") && !flags.contains("3")){
            flags.add("1");
        }
    }
    
    if(null != flag && "2".equals(flag)){//退款，
        if(!flags.contains("2") && !flags.contains("3")){ //余额充值是付款操作
            flags.add("2");
        }
    }
    
    Map<String,Object> conditionMap = new HashMap<String,Object>();
    
    String dateStart = map.get("dateStart");
    String dateEnd = map.get("dateEnd");
    if(null !=dateStart && !dateStart.isEmpty() && null !=dateEnd && !dateEnd.isEmpty()){
        map.put("dateStart",dateStart+" 00:00:00");
        map.put("dateEnd",dateEnd+" 23:59:59");
    }
    conditionMap.putAll(map);
//    if(null !=dateStart && !dateStart.isEmpty() && dateStart.equals(dateEnd)){
//        conditionMap.put("time",dateStart);
//    }
    conditionMap.put("flags",flags);
     Map<String,String> rMap = orderQryService.CountOrderPayMoney(conditionMap);
    for(Entry<String, String> ent:rMap.entrySet()){

        reMap.put("pay",null == ent.getKey()?"0.00":ent.getKey());
        reMap.put("return",null == ent.getValue()?"0.00":ent.getValue());
        
    }
     getPage().setRecordCount(orderQryService.orderPayInfoListCount(conditionMap));
     getPage().setDataList(orderQryService.queryOrderPayInfoList(conditionMap));
     map.put("dateStart",dateStart);
     map.put("dateEnd",dateEnd);
    return SUCCESS;
  }
  
  /**
   * 收退款数据导出 
   * 
   * @return
   * @throws ActionException
   */
  public String orderPayReport() throws ActionException {
     
     List<String> flags = new ArrayList<String>();
     
     String payType = map.get("payType");//订单交易12，余额充值3
     String flag = map.get("flag");//付款1，退款2
     if("3".equals(payType) && "2".equals(flag)){ //余额充值，且为退款，返回空记录
         return SUCCESS;
     }
     
     
     if(null != payType && !payType.isEmpty() && (null == flag||flag.isEmpty())){
         if("12".equals(payType)){//订单交易
             flags.add("1");
             flags.add("2");
         }else if("3".equals(payType)){//余额支付
             flags.add("3");
         }
     }
     
     if("3".equals(payType)){
         flags.add("3");
     }
     
    if(null != flag && "1".equals(flag)){//付款
        if(!flags.contains("1") && !flags.contains("3")){
            flags.add("1");
        }
    }
    
    if(null != flag && "2".equals(flag)){//退款，
        if(!flags.contains("2") && !flags.contains("3")){ //余额充值是付款操作
            flags.add("2");
        }
    }
    
    Map<String,Object> conditionMap = new HashMap<String,Object>();
    
    String dateStart = map.get("dateStart");
    String dateEnd = map.get("dateEnd");
    if(null !=dateStart && !dateStart.isEmpty() && null !=dateEnd && !dateEnd.isEmpty()){
        map.put("dateStart",dateStart+" 00:00:00");
        map.put("dateEnd",dateStart+" 23:59:59");
    }
    conditionMap.putAll(map);
//    if(null !=dateStart && !dateStart.isEmpty() && dateStart.equals(dateEnd)){
//        conditionMap.put("time",dateStart);
//    }
    conditionMap.put("flags",flags);
     
    SysUser sysuser = (SysUser) ActionContext.getContext().getSession().get("sysUser");
    Integer userId = 0;
    if (null != sysuser && null != sysuser.getUserId()) {
      userId = sysuser.getUserId();
    }
    String path;
    try {
      path = orderQryService.ExportOrderPayInfo(conditionMap,userId);
      getHttpServletResponse().getWriter().print("{'path':'" + path + "'}");
      map.put("path", "{'path':'" + path + "'}");
    } catch (Exception e) {
      log.error("导出收退款报表异常", e);
    }
    return null;
  }
  
  public List<String> getExcelUrl(String channel, String type) {
      List<String> liststr = new ArrayList<String>();
      StringBuffer sb = new StringBuffer();
      Calendar cal = Calendar.getInstance();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
      String excelPath = ConfigurationUtil.getString("path");
      String visitPath =
          ConfigurationUtil.getString("visitPath");
      // String excelPath = "E:/";
      // String visitPath = "E:/";
      sb.append("/report/").append(channel).append('_').append(sdf.format(cal.getTime()))
          .append(type);
      File file = new File(excelPath + sb.toString());
      if (!file.getParentFile().exists()) {
        file.getParentFile().mkdirs();
      }
      liststr.add(file.getPath());
      liststr.add(visitPath + sb.toString());
      return liststr;
    }
  
  
  
  /**
   * 查询报表导出信息列表
   * 
   * @return
   * @throws ActionException
   */
  public String queryExportInfoList() throws ActionException {
      
      String startDate = map.get("startDate");
      String endDate = map.get("endDate");
      String exportType = map.get("exportType");
      ActionContext ctx = ActionContext.getContext();
      HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
      
      if(null == exportType){
          exportType = request.getParameter("exportType");
      }
      if(null == exportType){
          return null;
      }
      
      if (null == startDate || startDate.length() < 1 || null == endDate || endDate.length() < 1) {
          
          //为空则设置时间段为最近30天
          SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
          Calendar cal=Calendar.getInstance();
          Date date=cal.getTime();
          map.put("endDate", df.format(date));
          cal.add(Calendar.DATE, -30);
          date=cal.getTime();
          map.put("startDate", df.format(date));
      }
     
     map.put("start", ((getPage().getPageNo() - 1) * getPage().getPageSize() + 1) + "");
     String end = getPage().getPageNo() * getPage().getPageSize()+"";
     map.put("end", end);
     
     map.put("exportType", exportType);//0代表商城财务版报表 ;1代表入驻商家结算报表
     SysUser sysuser = (SysUser) ActionContext.getContext().getSession().get("sysUser");

     String  userName ="";
     if (null != sysuser && null != sysuser.getUserId()) {
       userName = sysuser.getUserName();
       map.put("operator", userName);
     }
     
     getPage().setRecordCount(orderQryService.getExportInfoCount(map));
     getPage().setDataList(orderQryService.queryExportInfo(map));
     if("0".equals(exportType)){
         return "finance";
     }else if("1".equals(exportType)){
         return "merchants";
     }
    return null;
  }
  
  
  /**
   * 获取到所有的供应商列表
   * @return
   */
  public String findAllSuppliersForJson(){
      try {
          writeJson(orderQryService.supplierIdAndMerchantNameMap(map));
      } catch (Exception e) {
          log.error("获取到所有的供应商列表失败，", e);
      }
      return null;
  }

  public List<OrderDictionary> getDictList() {
    return payMethodList;
  }

  public void setDictList(List<OrderDictionary> dictList) {
    this.payMethodList = dictList;
  }

  public Map<String, String> getMap() {
    return map;
  }

  public void setMap(Map<String, String> map) {
    this.map = map;
  }

  public int getPayMethodWay() {
    return payMethodWay;
  }

  public void setPayMethodWay(int payMethodWay) {
    this.payMethodWay = payMethodWay;
  }

  public List<OrderDictionary> getChannelList() {
    return channelList;
  }

  public void setChannelList(List<OrderDictionary> channelList) {
    this.channelList = channelList;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public List<OrderDictionary> getPayMethodList() {
    return payMethodList;
  }

  public void setPayMethodList(List<OrderDictionary> payMethodList) {
    this.payMethodList = payMethodList;
  }

public String getOrderSource() {
    return orderSource;
}

public void setOrderSource(String orderSource) {
    this.orderSource = orderSource;
}

public String getCommerceId() {
    return commerceId;
}

public void setCommerceId(String commerceId) {
    this.commerceId = commerceId;
}

public String getCommoditySku() {
    return commoditySku;
}

public void setCommoditySku(String commoditySku) {
    this.commoditySku = commoditySku;
}

public String getbCategoryId() {
    return bCategoryId;
}

public void setbCategoryId(String bCategoryId) {
    this.bCategoryId = bCategoryId;
}

public String getmCategoryId() {
    return mCategoryId;
}

public void setmCategoryId(String mCategoryId) {
    this.mCategoryId = mCategoryId;
}

public String getsCategoryId() {
    return sCategoryId;
}

public void setsCategoryId(String sCategoryId) {
    this.sCategoryId = sCategoryId;
}

public String getSupplier() {
    return supplier;
}

public void setSupplier(String supplier) {
    this.supplier = supplier;
}

public String getOrderPreferentialCode() {
    return orderPreferentialCode;
}

public void setOrderPreferentialCode(String orderPreferentialCode) {
    this.orderPreferentialCode = orderPreferentialCode;
}

public String getPayMethodWayStr() {
    return payMethodWayStr;
}

public void setPayMethodWayStr(String payMethodWayStr) {
    this.payMethodWayStr = payMethodWayStr;
}

public BigDecimal getTotalMoney() {
    return totalMoney;
}

public void setTotalMoney(BigDecimal totalMoney) {
    this.totalMoney = totalMoney;
}

public BigDecimal getTotalActual() {
    return totalActual;
}

public void setTotalActual(BigDecimal totalActual) {
    this.totalActual = totalActual;
}
public Map<String, Object> getReMap() {
    return reMap;
}

public void setReMap(Map<String, Object> reMap) {
    this.reMap = reMap;
}
  
}
