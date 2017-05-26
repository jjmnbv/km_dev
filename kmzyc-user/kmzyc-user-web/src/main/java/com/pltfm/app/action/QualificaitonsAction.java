package com.pltfm.app.action;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.QualificaitonsService;
import com.pltfm.app.vobject.QualificationsDO;



@Component(value = "qualificaitonsAction")
@Scope("prototype")
// 资质文件审核
public class QualificaitonsAction extends BaseAction implements ModelDriven {
  private static Logger logger = LoggerFactory.getLogger(QualificaitonsAction.class);
  private QualificationsDO qualificaitonsDO;
  /**
   * 分页类
   */
  private Page page;
  @Resource(name = "qualificaitonsService")
  private QualificaitonsService qualificaitonsService;

  @Override
public Object getModel() {


    qualificaitonsDO = new QualificationsDO();
    return qualificaitonsDO;
  }



  public QualificationsDO getQualificaitonsDO() {
    return qualificaitonsDO;
  }



  public void setQualificaitonsDO(QualificationsDO qualificaitonsDO) {
    this.qualificaitonsDO = qualificaitonsDO;
  }



  public QualificaitonsService getQualificaitonsService() {
    return qualificaitonsService;
  }



  public void setQualificaitonsService(QualificaitonsService qualificaitonsService) {
    this.qualificaitonsService = qualificaitonsService;
  }



  /***
   * 
   * 显示资质文件
   * 
   * @return
   */
 /*删除采购商相关  public String pageList() {
    try {
      if (page == null) {
        page = new Page();
      }

      Integer count = qualificaitonsService.selectListQualificaitonsCount(qualificaitonsDO);
      page.setRecordCount(count);
      qualificaitonsDO.setSkip(page.getStartIndex());
      qualificaitonsDO.setMax(page.getStartIndex() + page.getPageSize());
      List list = qualificaitonsService.selectListQualificaitons(qualificaitonsDO);
      page.setDataList(list);
    } catch (Exception e) {
      logger.error("获取采购资格列表异常" + e.getMessage(), e);
    }
    return "pageList";
  }


  *//****
   * 资格文件详情
   * 
   * @return
   *//*
  public String qualificaitonsDetail() {

    try {
      qualificaitonsDO = qualificaitonsService.queryQualificaitons(qualificaitonsDO.getId());
    } catch (Exception ex) {
      logger.error("获取采购资格详情" + ex.getMessage(), ex);
    }

    return "qualificaitonsDetail";
  }


  public String qualificaitonsUpdate() {

    try {
      qualificaitonsDO = qualificaitonsService.queryQualificaitons(qualificaitonsDO.getId());
    } catch (Exception ex) {
      logger.error("获取采购资格管理修改" + ex.getMessage(), ex);
    }

    return "qualificaitonsUpdate";
  }

  *//***
   * 修改
   * 
   * @return
   *//*
  public String qualificaitonsEdit() {


    try {
      qualificaitonsDO.setModifyDate(new Date());
      qualificaitonsService.qualificaitonsEdit(qualificaitonsDO);
      this.addActionMessage("资质文件修改成功");
    } catch (SQLException e) {
      logger.error("资质文件修改失败" + e.getMessage(), e);
      this.addActionMessage("资质文件修改失败");
    }

    qualificaitonsDO = new QualificationsDO();
    return pageList();

  }

  *//****
   * 
   * 进入新增
   * 
   * @return
   *//*

  public String qualificaitonsAdd() {

    return "qualificaitonsAdd";
  }

  *//***
   * 新增方法
   * 
   * @return
   *//*
  @Token
  public String addQualificaitons() {

    try {
      qualificaitonsDO.setUserId(qualificaitonsDO.getUserId());
      qualificaitonsDO.setType(qualificaitonsDO.getType());
      List listQualificaiton = qualificaitonsService.getQualificaitonsList(qualificaitonsDO);
      if (listQualificaiton.size() > 0 && listQualificaiton != null) {

        this.addActionMessage(ConfigureUtils.getMessageConfig("qualifications.add.exist"));

        return "qualificaitonsAdd";
      } else {

        qualificaitonsDO.setCreateDate(new Date());
        qualificaitonsService.insertQualifications(qualificaitonsDO);
      }
    } catch (Exception ex) {

      logger.error("添加采购资格异常" + ex.getMessage(), ex);
    }

    qualificaitonsDO = new QualificationsDO();
    return pageList();
  }
*/


  @Override
public Page getPage() {
    return page;
  }


  @Override
public void setPage(Page page) {
    this.page = page;
  }

}
