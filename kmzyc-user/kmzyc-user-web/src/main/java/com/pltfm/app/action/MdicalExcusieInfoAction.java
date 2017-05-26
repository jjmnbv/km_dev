package com.pltfm.app.action;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.MdicalExcusieInfoService;
import com.pltfm.app.vobject.MdicalExcusieInfo;

@Component(value = "mdicalExcusieAction")
public class MdicalExcusieInfoAction extends ActionSupport implements ModelDriven {
  @Resource(name = "mdicalExcusieInfoService")
  private MdicalExcusieInfoService mdicalExcusieInfoService;
  private MdicalExcusieInfo mdicalExcusieInfo;
  private int nmeid;// 个人id
  /** 多条删除医务专属id集合 **/
  private List<String> nmeids;
  /**
   * 分页类
   */
  private Page page;

  public Page getPage() {
    return page;
  }

  public void setPage(Page page) {
    this.page = page;
  }



  public int getNmeid() {
    return nmeid;
  }

  public void setNmeid(int nmeid) {
    this.nmeid = nmeid;
  }

  public List<String> getNmeids() {
    return nmeids;
  }

  public void setNmeids(List<String> nmeids) {
    this.nmeids = nmeids;
  }

  @Override
  public MdicalExcusieInfo getModel() {
    mdicalExcusieInfo = new MdicalExcusieInfo();
    return mdicalExcusieInfo;
  }

  public MdicalExcusieInfo getMdicalExcusieInfo() {
    return mdicalExcusieInfo;
  }

  public void setMdicalExcusieInfo(MdicalExcusieInfo mdicalExcusieInfo) {
    this.mdicalExcusieInfo = mdicalExcusieInfo;
  }

  public MdicalExcusieInfoService getMdicalExcusieInfoService() {
    return mdicalExcusieInfoService;
  }

  public void setMdicalExcusieInfoService(MdicalExcusieInfoService mdicalExcusieInfoService) {
    this.mdicalExcusieInfoService = mdicalExcusieInfoService;
  }


  /**
   * 医属信息列表
   * 
   * @return
   */
  public String pageList() {
    try {
      page = mdicalExcusieInfoService.searchPageByVo(page, mdicalExcusieInfo);
      return "pageSuccess";
    } catch (Exception e) {
      e.printStackTrace();
      return "INPUT";
    }
  }

  /**
   * 进入添加医属信息页面
   * 
   * @return
   */
  public String preAdd() {

    return "preAddSuccess";
  }

  public String add() {
    try {

      mdicalExcusieInfoService.addMdicalExcusieInfo(mdicalExcusieInfo);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return "saveSuccess";
  }

  public String detele() {
    try {
      mdicalExcusieInfoService.deleteMdicalExcusieInfo(nmeids);
      return "delSuccess";
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return INPUT;
  }

  public String update() {
    try {

      mdicalExcusieInfoService.udpateMdicalExcusieInfo(mdicalExcusieInfo);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return "saveSuccess";
  }

  /**
   * 进入医属信息详细
   * 
   * @return
   */
  public String getRankInfoId() {
    try {
      mdicalExcusieInfo = mdicalExcusieInfoService.getPersonal_id(nmeid);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return "updateSuccess";
  }
}
