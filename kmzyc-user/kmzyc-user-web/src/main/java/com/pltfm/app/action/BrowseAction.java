package com.pltfm.app.action;

import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 浏览信息Action类
 * 
 * @author cjm
 * @since 2013-8-16
 */
@Component(value = "browseAction")
public class BrowseAction extends BaseAction implements ModelDriven {

  @Override
  public Object getModel() {
    return null;
  }

  /**
   * 列表显示
   * 
   * @return
   */
  public String pageList() {
    return "pageSuccess";
  }

}
