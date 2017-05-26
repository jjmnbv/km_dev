package com.kmzyc.promotion.app.action;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import com.alibaba.fastjson.JSON;
import com.kmzyc.promotion.app.service.CategoryService;
import com.kmzyc.promotion.app.vobject.Category;

/**
 * 产品类目管理Action类
 * 
 * @author humy
 * @since 2013-7-10
 */
@Scope(value = "prototype")
public class CategoryAction extends BaseAction {
  private static final long serialVersionUID = 4780277557847740433L;
  /**
   * 类目信息
   */
  private Category category;
  /**
   * 类目业务逻辑接口
   */
  @Resource(name = "categoryService")
  private CategoryService categoryService;

  private String categoryString;

  private String type;

  private String para;

  // 运营类目的Sql
  private String execSql;
  // sql关系文字说明
  private String sqlString;

  private String phyCategorys;

  private String copyCategoryIds;
  // 日志记录
  private static final Logger logger = LoggerFactory.getLogger(CategoryAction.class);

  /**
   * 产品类目列表
   * 
   * @return String 返回值
   */
  public String queryCategoryList() {
    String resultInfo = "success";
    try {
      if (category == null) {
        category = new Category();
      }

      if ("phy".equals(type)) {
        category.setIsPhy(1);
      }
      if ("busi".equals(type)) {
        category.setIsPhy(2);
      }

      List<Category> list = categoryService.queryCategoryList(category);
      categoryString = JSON.toJSONString(list);

    } catch (Exception e) {
      logger.error("queryCategoryList异常:", e);
      return ERROR;
    }
    return resultInfo;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String getCategoryString() {
    return categoryString;
  }

  public void setCategoryString(String categoryString) {
    this.categoryString = categoryString;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getPara() {
    return para;
  }

  public void setPara(String para) {
    this.para = para;
  }

  public String getExecSql() {
    return execSql;
  }

  public void setExecSql(String execSql) {
    this.execSql = execSql;
  }

  public String getSqlString() {
    return sqlString;
  }

  public void setSqlString(String sqlString) {
    this.sqlString = sqlString;
  }

  public String getPhyCategorys() {
    return phyCategorys;
  }

  public void setPhyCategorys(String phyCategorys) {
    this.phyCategorys = phyCategorys;
  }

  public String getCopyCategoryIds() {
    return copyCategoryIds;
  }

  public void setCopyCategoryIds(String copyCategoryIds) {
    this.copyCategoryIds = copyCategoryIds;
  }

}
