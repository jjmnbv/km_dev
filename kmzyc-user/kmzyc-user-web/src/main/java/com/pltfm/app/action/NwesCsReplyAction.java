package com.pltfm.app.action;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.NwesCsReplyService;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.NwesCsReply;

/***
 * 回复服务信息Action
 */
@Component(value = "nwesCsReplyAction")
public class NwesCsReplyAction extends BaseAction implements ModelDriven {
  /***
   * 回复服务信息nwesCsReplyService
   */
  @Resource(name = "nwesCsReplyService")
  private NwesCsReplyService nwesCsReplyService;
  /***
   * 回复信息
   */
  private NwesCsReply nwesCsReply;
  /***
   * 回复主键
   */
  private Integer reply_Id;
  /***
   * 回复主键集合
   */
  private List<Integer> replyIds;

  /****
   * 服务记录
   */
  public String add() {
    try {

      nwesCsReplyService.insert(nwesCsReply);

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return "INPUT";
    }
    return "saveSuccess";
  }

  /***
   * 删除服务记录
   */
  @Token
  public String detele() {
    try {
      nwesCsReplyService.delete(replyIds);
      return "delSuccess";
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return "INPUT";
    }
  }


  public NwesCsReplyService getNwesCsReplyService() {
    return nwesCsReplyService;
  }

  public void setNwesCsReplyService(NwesCsReplyService nwesCsReplyService) {
    this.nwesCsReplyService = nwesCsReplyService;
  }

  public NwesCsReply getNwesCsReply() {
    return nwesCsReply;
  }

  public void setNwesCsReply(NwesCsReply nwesCsReply) {
    this.nwesCsReply = nwesCsReply;
  }

  public Integer getReply_Id() {
    return reply_Id;
  }

  public void setReply_Id(Integer replyId) {
    reply_Id = replyId;
  }

  public List<Integer> getReplyIds() {
    return replyIds;
  }

  public void setReplyIds(List<Integer> replyIds) {
    this.replyIds = replyIds;
  }

  @Override
  public Object getModel() {
    nwesCsReply = new NwesCsReply();
    return nwesCsReply;
  }

}
