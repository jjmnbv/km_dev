package com.kmzyc.b2b.ajax;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.model.SupplierNews;
import com.kmzyc.b2b.service.SupplierNewsService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.km.framework.page.Pagination;

@Controller
@Scope("prototype")
public class SupplierNewsInterFaceAction extends BaseAction {

  private static final long serialVersionUID = 1L;
  // 日志文件生成
  // private static Logger logger = Logger.getLogger(SupplierNewsInterFaceAction.class);
  private static Logger logger = LoggerFactory.getLogger(SupplierNewsInterFaceAction.class);
  // 返回至页面的对象
  @SuppressWarnings("rawtypes")
  private ReturnResult returnResult;
  // 资讯service类引用
  @Resource(name = "supplierNewsServiceImpl")
  private SupplierNewsService supplierNewsService;
  // supplierId
  private Long supplierId;
  // 请求条数
  private String numCount;
  // 资讯id
  private String newsId;
  // 店铺Id
  private Long shopId;
  // 资讯实体
  private SupplierNews supplierNews;

  // 根据newsId获取资讯详情
  public String getNewsDetailByNewsId() {
    if (StringUtils.isBlank(newsId)) {
      logger.error("请求newsId为空");
      return ERROR;
    }
    try {
      supplierNews = supplierNewsService.queryByNewsId(newsId);
      logger.info("资讯详情获取成功");
    } catch (Exception e) {
      logger.error("获取资讯详情异常" + e.getMessage(), e);
    }
    return SUCCESS;
  }

  // cms请求根据supplierId获取资讯信息
  @SuppressWarnings("unchecked")
  public String getSupplyNews() {
    if (Objects.isNull(shopId)) {
      logger.error("参数shopId空");
      returnResult =
          new ReturnResult<List<SupplierNews>>(InterfaceResultCode.FAILED, "请传入参数id", null);
    }
    // 每页显示10条。
    Pagination page = this.getPagination(5, 10);
    // sql查询条件对象
    Map<String, Object> newConditon = new HashMap<String, Object>();
    if (StringUtils.isNotBlank(numCount)) {
      page.setStartindex(1);
      page.setEndindex(Integer.parseInt(numCount));
    }
    newConditon.put("shopId", shopId);
    // 设置查询条件
    page.setObjCondition(newConditon);
    // 根据资讯supplierId分页获取资讯信息列表
    try {
      this.pagintion = supplierNewsService.pageSupplierNewsBySupplierId(page);
      logger.info("初始化资讯列表成功" + page.getRecordList().size() + "条");
      logger.info("获取类目资讯成功");
      if (pagintion.getRecordList().size() > 0) {
        returnResult =
            new ReturnResult<List<SupplierNews>>(InterfaceResultCode.SUCCESS, "成功",
                pagintion.getRecordList());
      } else {
        returnResult =
            new ReturnResult<List<SupplierNews>>(InterfaceResultCode.SUCCESS, "暂未有数据",
                pagintion.getRecordList());
      }
    } catch (Exception e) {
      logger.error("获取类目资讯异常" + e.getMessage(), e);
      returnResult = new ReturnResult<List<SupplierNews>>(InterfaceResultCode.FAILED, "失败", null);
    }
    return SUCCESS;
  }

  // 分页显示资讯信息
  public String initSupplierNews() {
    if (Objects.isNull(shopId)) {
      logger.error("参数shopId空");
      return ERROR;
    }
    // 每页显示10条。
    Pagination page = this.getPagination(5, 10);
    // sql查询条件对象
    Map<String, Object> newConditon = new HashMap<String, Object>();
    newConditon.put("shopId", shopId);
    // 设置查询条件
    page.setObjCondition(newConditon);
    // 根据资讯supplierId分页获取资讯信息列表
    try {
      this.pagintion = supplierNewsService.pageSupplierNewsBySupplierId(page);
      logger.info("初始化资讯列表成功" + page.getRecordList().size() + "条");
    } catch (Exception e) {
      logger.error("初始化资讯列表异常" + e.getMessage(), e);
    }
    return SUCCESS;
  }

  public Long getShopId() {
    return shopId;
  }

  public void setShopId(Long shopId) {
    this.shopId = shopId;
  }

  public SupplierNews getSupplierNews() {
    return supplierNews;
  }

  public void setSupplierNews(SupplierNews supplierNews) {
    this.supplierNews = supplierNews;
  }

  public String getNewsId() {
    return newsId;
  }

  public void setNewsId(String newsId) {
    this.newsId = newsId;
  }

  public String getNumCount() {
    return numCount;
  }

  public void setNumCount(String numCount) {
    this.numCount = numCount;
  }

  public Long getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(Long supplierId) {
    this.supplierId = supplierId;
  }

  @SuppressWarnings("unchecked")
  public ReturnResult<List<SupplierNews>> getReturnResult() {
    return returnResult;
  }

  public void setReturnResult(ReturnResult<List<SupplierNews>> returnResult) {
    this.returnResult = returnResult;
  }

}
