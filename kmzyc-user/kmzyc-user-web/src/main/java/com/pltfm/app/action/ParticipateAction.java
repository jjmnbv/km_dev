package com.pltfm.app.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.kmzyc.product.remote.service.ConsultRemoteService;
import com.pltfm.app.vobject.Consult;


/**
 * 客户参与信息处理action
 * 
 * @author zhl
 * @since 2013-08-08
 */
@Component(value = "participateAction")
public class ParticipateAction {
  
  @Autowired
  private ConsultRemoteService consultRemoteService;
    
  private Consult consult;
  private Page page;

  /***
   * 查询客户提问信息
   * 
   * @return
   */
  public String queryPageConsult() {
    try {
      if (page == null) {
        page = new Page();
      }
      if (consult == null) {
        consult = new Consult();
      }
      //TODO stockService转换成 ConsultRemoteService？
  /*    ConsultRemoteService consultRemoteService =
          (ConsultRemoteService) RemoteTool.getRemote(productIbport, "stockService");*/
      int count = consultRemoteService.getConsultNumber(consult);
      page.setRecordCount(count);
      consult.setSkip(page.getStartIndex());
      consult.setMax(page.getStartIndex() + page.getPageSize());
      List list = consultRemoteService.getPageConsult(consult);
      page.setDataList(list);

    }catch (Exception e) {
      e.printStackTrace();
    }
    return "consult";
  }

  /**
   * 查询客户点评信息
   */
  public String queryPageComment() {
    return "comment";
  }

  /**
   * 查询客户发帖信息
   * 
   * @return
   */
  public String queryPagePosting() {
    return "posting";
  }

  /**
   * 查询客户留言信息
   * 
   * @return
   */
  public String queryPageMessage() {
    return "message";
  }

  public Page getPage() {
    return page;
  }

  public void setPage(Page page) {
    this.page = page;
  }

  public Consult getConsult() {
    return consult;
  }

  public void setConsult(Consult consult) {
    this.consult = consult;
  }
}
