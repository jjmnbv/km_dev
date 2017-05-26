package com.pltfm.app.action;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.QualificaitonsFileService;
import com.pltfm.app.vobject.QualificaitonsFileDO;



@Component(value = "qualificaitonsFileAction")
@Scope("prototype")
// 资质文件审核
public class QualificaitonsFileAction extends BaseAction implements ModelDriven {

  /**
     * UID
     */
    private static final long serialVersionUID = 7402873111228503411L;

private static Logger logger = LoggerFactory.getLogger(QualificaitonsFileAction.class);

  private static final String QualificaitonsFileImage =
      ConfigurationUtil.getString("QualificaitonsFileImage");
  private QualificaitonsFileDO qualificaitonsFileDO;
  /**
   * 分页类
   */
  private Page page;
  @Resource(name = "qualificaitonsFileService")
  private QualificaitonsFileService qualificaitonsFileService;


  @Override
public Object getModel() {


    qualificaitonsFileDO = new QualificaitonsFileDO();
    return qualificaitonsFileDO;
  }


  public QualificaitonsFileDO getQualificaitonsFileDO() {
    return qualificaitonsFileDO;
  }


  public void setQualificaitonsFileDO(QualificaitonsFileDO qualificaitonsFileDO) {
    this.qualificaitonsFileDO = qualificaitonsFileDO;
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

      Integer count =
          qualificaitonsFileService.selectListQualificaitonsFileCount(qualificaitonsFileDO);
      page.setRecordCount(count);
      qualificaitonsFileDO.setSkip(page.getStartIndex());
      qualificaitonsFileDO.setMax(page.getStartIndex() + page.getPageSize());
      List list = qualificaitonsFileService.selectListQualificaitonsFile(qualificaitonsFileDO);
      page.setDataList(list);
    } catch (Exception e) {
      logger.error("获取资质文件列表异常" + e.getMessage(), e);
    }
    return "pageList";
  }



  // 进入修改
  public String qualificaitonsFileUpdate() throws Exception {
    try {
      qualificaitonsFileDO =
          qualificaitonsFileService.queryQualificaitonsFile(qualificaitonsFileDO.getId());

      qualificaitonsFileDO.setFileUrl(QualificaitonsFileImage
          + qualificaitonsFileDO.getFileUrl().substring(0,
              qualificaitonsFileDO.getFileUrl().lastIndexOf("."))
          + "_sma" + qualificaitonsFileDO.getFileUrl()
              .substring(qualificaitonsFileDO.getFileUrl().lastIndexOf(".")));
    } catch (SQLException e) {
      logger.error("进入修改资质文件异常" + e.getMessage(), e);
    }
    return "qualificaitonsFileUpdate";
  }

  // 进入审核
  public String qualificaitonsVerify() throws Exception {



    try {
      qualificaitonsFileDO =
          qualificaitonsFileService.queryQualificaitonsFile(qualificaitonsFileDO.getId());
      qualificaitonsFileDO.setFileUrl(QualificaitonsFileImage
          + qualificaitonsFileDO.getFileUrl().substring(0,
              qualificaitonsFileDO.getFileUrl().lastIndexOf("."))
          + "_sma" + qualificaitonsFileDO.getFileUrl()
              .substring(qualificaitonsFileDO.getFileUrl().lastIndexOf(".")));


      // qua.setSmallUrl(showPath+QUALIFICATION_IMG_PATH+qua.getFileUrl().substring(0,
      // qua.getFileUrl().lastIndexOf("."))+Constants.SMALL_IMG_SUFFIX+qua.getFileUrl().substring(qua.getFileUrl().lastIndexOf(".")));

    } catch (SQLException e) {
      logger.error("进入审核页面" + e.getMessage(), e);
    }
    return "qualificaitonsVerify";

  }



  // 删除

  public String qualificaitonsDelete() {

    try {
      Integer id = qualificaitonsFileDO.getId();
      if (null != id) {
        // 删除推荐短信
        qualificaitonsFileService.deleteById(id);
      }
      this.addActionMessage("删除成功！");
    } catch (SQLException e) {
      logger.error("删除资质文件成功" + e.getMessage(), e);
      this.addActionMessage("删除资质文件失败");
    }
    return pageList();
  }


  // 修改
  public String qualificaitonsFileEdit() {
    try {

      qualificaitonsFileService.updateQualificaitonsFile(qualificaitonsFileDO);
      this.addActionMessage("资质文件修改成功");
    } catch (SQLException e) {
      logger.error("资质文件修改失败" + e.getMessage(), e);
      this.addActionMessage("资质文件修改失败");
    }


    return pageList();
  }


  // 审核通过
  public String qualificaitonsFileVerify() {


    try {
      qualificaitonsFileDO.setAuditingDate(new Date());
      qualificaitonsFileService.updateQualificaitonsFile(qualificaitonsFileDO);
      this.addActionMessage("审核通过");
    } catch (SQLException e) {
      logger.error("审核失败" + e.getMessage(), e);
      this.addActionMessage("审核失败");
    }

    return pageList();
  }



  // 不通过
  public String qualificaitonsFileNoPass() {


    try {

      qualificaitonsFileDO.setAuditingDate(new Date());
      qualificaitonsFileService.updateQualificaitonsFile(qualificaitonsFileDO);
      this.addActionMessage("审核不通过");
    } catch (SQLException e) {
      logger.error("审核失败" + e.getMessage(), e);
      this.addActionMessage("审核失败");
    }

    return pageList();
  }


  // 详情


  public String qualificaitonsDetail() {

    try {
      qualificaitonsFileDO =
          qualificaitonsFileService.queryQualificaitonsFile(qualificaitonsFileDO.getId());
      qualificaitonsFileDO.setFileUrl(QualificaitonsFileImage
          + qualificaitonsFileDO.getFileUrl().substring(0,
              qualificaitonsFileDO.getFileUrl().lastIndexOf("."))
          + "_sma" + qualificaitonsFileDO.getFileUrl()
              .substring(qualificaitonsFileDO.getFileUrl().lastIndexOf(".")));


    } catch (SQLException e) {
      logger.error("进入 详情页面" + e.getMessage(), e);
    }


    return "qualificaitonsDetail";
  }*/

  public static Logger getLogger() {
    return logger;
  }

  public static void setLogger(Logger logger) {
    QualificaitonsFileAction.logger = logger;
  }

  public QualificaitonsFileService getQualificaitonsFileService() {
    return qualificaitonsFileService;
  }

  public void setQualificaitonsFileService(QualificaitonsFileService qualificaitonsFileService) {
    this.qualificaitonsFileService = qualificaitonsFileService;
  }


  @Override
public Page getPage() {
    return page;
  }


  @Override
public void setPage(Page page) {
    this.page = page;
  }

}
