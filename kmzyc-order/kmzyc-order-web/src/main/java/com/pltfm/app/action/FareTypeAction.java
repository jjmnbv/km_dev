package com.pltfm.app.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.commons.exception.ActionException;
import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.FareTypeWithBLOBs;
import com.pltfm.app.service.FareTypeService;

@Controller("fareTypeAction")
@Scope("prototype")
@SuppressWarnings("unchecked")
public class FareTypeAction extends BaseAction {
  private static final long serialVersionUID = 1077438249638455666L;

  private Logger log = Logger.getLogger(FareTypeAction.class);

  @Resource
  FareTypeService fareTypeService;

  private Long type_id;
  private String name;
  private String site;

  private FareTypeWithBLOBs fareType;

  private List fareList;

  private String caption;// 标题
  private String action;// url

  /*
   * 检查序号是否存在
   */
  public void checkTypeId() {
    try {
      msg = fareTypeService.checkTypeId(type_id).toString();
    } catch (ServiceException e) {
      log.error("检查序号是否存在发生错误！", e);
      msg = "错误！";
    }
  }

  /*
   * 检查名称是否存在
   */
  public void checkName() throws ActionException, IOException {
    try {
      msg = fareTypeService.checkName(type_id, name).toString();
    } catch (ServiceException e) {
      log.error("检查名称是否存在发生错误！", e);
      msg = "错误！";
    }
  }

  /*
   * 检查适用站点是否存在
   */
  public void checkSite() throws ActionException, IOException {
    try {
      msg = fareTypeService.checkSite(type_id, site).toString();
    } catch (ServiceException e) {
      log.error("检查名称是否存在发生错误！", e);
      msg = "错误！";
    }
  }

  /*
   * 到新增运费配置页
   */
  public String showSave() throws ActionException {
    caption = "新增运费配置";
    action = "/app/fareTypesaveAction.action";
    return "fareEdit";
  }

  /*
   * 到修改运费配置页
   */
  public String showEdit() throws ActionException {
    caption = "修改运费配置";
    action = "/app/fareTypeupdateAction.action";
    try {
      fareType = fareTypeService.getById(type_id);
    } catch (ServiceException e) {
      log.error("到修改运费配置页发生错误！", e);
    }
    return "fareEdit";
  }

  /*
   * 配置列表
   */
  public String list() throws ActionException {
    try {
      fareList = fareTypeService.list();
    } catch (ServiceException e) {
      log.error("配置列表发生错误！", e);
    }
    return "fare";
  }

  /*
   * 保存
   */
  public void save() throws IOException {
    try {
      fareTypeService.save(fareType);
      msg = "保存成功！";
    } catch (ServiceException e) {
      log.error("运费保存发生错误！", e);
      msg = "保存失败！";
    }
  }

  /*
   * 修改
   */
  public void update() throws IOException {
    try {
      fareTypeService.update(fareType);
      msg = "修改成功！";
    } catch (ServiceException e) {
      log.error("运费修改发生错误！", e);
      msg = "修改失败！";
    }
  }

  /*
   * 删除
   */
  public void delete() throws IOException {
    try {
      fareTypeService.delete(type_id);
      msg = "删除成功！";
    } catch (ServiceException e) {
      log.error("运费删除发生错误！", e);
      msg = "删除失败！";
    }
  }

  public Long getType_id() {
    return type_id;
  }

  public void setType_id(Long type_id) {
    this.type_id = type_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSite() {
    return site;
  }

  public void setSite(String site) {
    this.site = site;
  }

  public List getFareList() {
    return fareList;
  }

  public void setFareList(List fareList) {
    this.fareList = fareList;
  }

  public FareTypeWithBLOBs getFareType() {
    return fareType;
  }

  public void setFareType(FareTypeWithBLOBs fareType) {
    this.fareType = fareType;
  }

  public String getCaption() {
    return caption;
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }
}
