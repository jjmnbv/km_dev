package com.pltfm.app.action;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.vobject.Follow;

/**
 * 关注信息Action
 * 
 * @author cjm
 * @since 2013-7-31
 */
@Component(value = "followAction")
public class FollowAction extends BaseAction implements ModelDriven {
  /**
   * 关注信息实体
   */
  private Follow follow;


  /**
   * 分页类
   */
  private Page page;

  /**
   * 列表显示
   * 
   * @return
   */
  public String pageList() {
    if (page == null) {
      page = new Page();
    }
    // page.setDataList(followService.findList());
    return "pageSuccess";
  }

  @Override
  public Object getModel() {
    if (follow == null) {
      follow = new Follow();
    }
    return follow;
  }

  @Override
public Page getPage() {
    return page;
  }

  @Override
public void setPage(Page page) {
    this.page = page;
  }

  public Follow getFollow() {
    return follow;
  }

  public void setFollow(Follow follow) {
    this.follow = follow;
  }


}
