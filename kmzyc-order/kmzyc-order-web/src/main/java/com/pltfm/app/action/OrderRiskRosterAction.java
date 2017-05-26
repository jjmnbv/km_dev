package com.pltfm.app.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.entities.OrderRiskBackList;
import com.pltfm.app.entities.OrderRiskWhiteList;
import com.pltfm.app.service.OrderRiskRosterService;
import com.pltfm.app.util.OrderRiskKey;
import com.pltfm.sys.model.SysUser;

@Controller
@Scope("prototype")
public class OrderRiskRosterAction extends BaseAction {
  private static final long serialVersionUID = 1L;
  private Logger log = Logger.getLogger(OrderRiskRosterAction.class);

  @Resource
  private OrderRiskRosterService orderRiskRosterService;

  private Long id;
  private Integer type;
  private String content;
  private Page page;
  private Map<String, String> params;

  /**
   * 黑名单列表
   * 
   * @return
   */
  public String blackList() {
    try {
      if (null == page) {
        page = new Page();
        page.setPageNo(1);
        page.setPageSize(20);
      }
      if (null == params) {
        params = new HashMap<String, String>();
      }
      params.put("start", String.valueOf((page.getPageNo() - 1) * page.getPageSize() + 1));
      params.put("end", String.valueOf(page.getPageNo() * page.getPageSize()));
      page = orderRiskRosterService.queryBlackListByPage(page, params);
    } catch (Exception ex) {
      log.error("风控黑名单列表异常:" + ex.getMessage());
    }
    return "blackList";
  }

  /**
   * 白名单列表
   * 
   * @return
   */
  public String whiteList() {
    try {
      if (null == page) {
        page = new Page();
        page.setPageNo(1);
        page.setPageSize(20);
      }
      if (null == params) {
        params = new HashMap<String, String>();
      }
      params.put("start", String.valueOf((page.getPageNo() - 1) * page.getPageSize() + 1));
      params.put("end", String.valueOf(page.getPageNo() * page.getPageSize()));
      page = orderRiskRosterService.queryWhiteListByPage(page, params);
    } catch (Exception ex) {
      log.error("风控白名单列表异常:" + ex.getMessage());
    }
    return "whiteList";
  }

  /**
   * 进入新增黑名单
   * 
   * @return
   */
  public String gotoAddBlackList() {
    return "addBlackList";
  }

  /**
   * 进入新增黑名单
   * 
   * @return
   */
  public String gotoAddWhiteList() {
    return "addWhiteList";
  }

  /**
   * 新增黑名单
   */
  public void addBlackList() {
    try {
      if (null != type && null != content) {
        if (orderRiskRosterService.isExistBlack(type, content)) {
          // 黑名单已存在
          outPut("0");
        } else if (orderRiskRosterService.isExistWhite(type, content)) {
          // 白名单已存在
          outPut("2");
        } else if (!orderRiskRosterService.isExistAccount(type, content)) {
          // 账号或者商家信息不存在
          outPut("3");
        } else {
          HttpServletRequest request = getHttpServletRequest();
          SysUser sysuser = (SysUser) request.getSession().getAttribute("sysUser");
          OrderRiskBackList orbl = new OrderRiskBackList();
          orbl.setType(type);
          orbl.setContent(content);
          orbl.setValid(OrderRiskKey.ORDER_RISK_VALID_NORMAL);
          orbl.setOperator(sysuser.getUserName());
          if (orderRiskRosterService.saveBlackList(orbl)) {
            outPut("1");
          }
        }
      }
    } catch (Exception e) {
      log.error("新增黑名单" + e.getMessage());
    }
  }

  /**
   * 新增白名单
   */
  public void addWhiteList() {
    try {
      if (null != type && null != content) {
        if (orderRiskRosterService.isExistBlack(type, content)) {
          // 黑名单已存在
          outPut("0");
        } else if (orderRiskRosterService.isExistWhite(type, content)) {
          // 白名单已存在
          outPut("2");
        } else if (!orderRiskRosterService.isExistAccount(type, content)) {
          // 账号或者商家信息不存在
          outPut("3");
        } else {
          HttpServletRequest request = getHttpServletRequest();
          SysUser sysuser = (SysUser) request.getSession().getAttribute("sysUser");
          OrderRiskWhiteList owbl = new OrderRiskWhiteList();
          owbl.setType(type);
          owbl.setContent(content);
          owbl.setValid(OrderRiskKey.ORDER_RISK_VALID_NORMAL);
          owbl.setOperator(sysuser.getUserName());
          if (orderRiskRosterService.saveWhiteList(owbl)) {
            outPut("1");
          }
        }
      }
    } catch (Exception e) {
      log.error("新增白名单" + e.getMessage());
    }
  }

  /**
   * 移除黑名单
   */
  public void removeBlackList() {
    try {
      if (null != id && orderRiskRosterService.deleteBlackList(id)) {
        outPut("1");
      }
    } catch (Exception e) {
      log.error("新增白名单" + e.getMessage());
    }
  }

  /**
   * 移除白名单
   */
  public void removeWhiteList() {
    try {
      if (null != id && orderRiskRosterService.deleteWhiteList(id)) {
        outPut("1");
      }
    } catch (Exception e) {
      log.error("新增白名单" + e.getMessage());
    }
  }

  private void outPut(String value) {
    try {
      getHttpServletRequest().setCharacterEncoding("utf-8");
      PrintWriter out = getHttpServletResponse().getWriter();
      out.write(value);
      // 释放资源
      out.flush();
      out.close();
      out = null;
    } catch (Exception e) {
      log.error(e);
    }
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
public Page getPage() {
    return page;
  }

  @Override
public void setPage(Page page) {
    this.page = page;
  }

  public Map<String, String> getParams() {
    return params;
  }

  public void setParams(Map<String, String> params) {
    this.params = params;
  }
}
