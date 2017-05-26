package com.pltfm.app.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.service.SafeQuestionService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.SafeQuestion;
import com.pltfm.sys.model.SysUser;

@Controller("safeQuestionAction")
@Scope("prototype")
public class SafeQuestionAction extends BaseAction {

  private static final long serialVersionUID = -579941341825593085L;
  /** 分页实体 **/
  private Page page;

  /** 要删除问题的主键集合 **/
  private List<Integer> questionId;

  /** 安全问题实体对象 **/

  private SafeQuestion safeQuestion;
  /** 安全问题的业务类对象 **/
  @Resource
  private SafeQuestionService safeQuestionService;



  public SafeQuestionAction() {

  }

  /**
   * 跳转到安全问题界面 以及可以根据安全问题的名称查询出安全问题出来
   * 
   * @return
   */
  public String show() {
    if (page == null) {
      page = new Page();

    }
    if (safeQuestion == null) {
      safeQuestion = new SafeQuestion();
    }

    try {
      page.setRecordCount(safeQuestionService.countItem(safeQuestion));
      safeQuestionList = safeQuestionService.queryQuestionByName(safeQuestion, page);
    } catch (Exception e) {

      e.printStackTrace();
      return "error";

    }

    return "show";

  }

  /**
   * 跳转到安全问题添加页面
   * 
   * @return
   */

  public String toAddQuestionShow() {

    return "toAddQuestionShow";
  }

  /**
   * 添加安全问题
   * 
   * @return
   */
  @Token
  public String addQuestion() {

    try {
      safeQuestion.setD_create_date(DateTimeUtils.getCalendarInstance().getTime());

      SysUser sysUser = (SysUser) session.get("sysUser");
      safeQuestion.setN_created(sysUser.getCreateUser());

      safeQuestionService.addSafeQuestion(safeQuestion);
      this.addActionMessage(ConfigureUtils.getMessageConfig("safeQuestion.add.success"));
      safeQuestion = new SafeQuestion();
    } catch (Exception e) {
      this.addActionError(ConfigureUtils.getMessageConfig("safeQuestion.add.fail"));
      e.printStackTrace();
      return "error";
    }
    return this.show();
  }

  /**
   * 跳转到修改页面
   * 
   * @return
   */

  public String editQuestion() {

    try {

      safeQuestion = safeQuestionService.queryOneQuestion(safeQuestion.getN_safe_question_id());
    } catch (Exception e) {

      e.printStackTrace();
      return "error";
    }

    return "editShow";
  }

  /**
   * 保存更新后的安全问题信息
   * 
   * @return
   */
  @Token
  public String saveUpdateQuestion() {

    try {
      safeQuestion.setD_modify_date(DateTimeUtils.getCalendarInstance().getTime());

      SysUser sysUser = (SysUser) session.get("sysUser");
      safeQuestion.setN_created(sysUser.getCreateUser());

      safeQuestionService.updateOneQuestion(safeQuestion);
      this.addActionMessage(ConfigureUtils.getMessageConfig("safeQuestion.update.success"));
      safeQuestion = new SafeQuestion();
    } catch (Exception e) {
      this.addActionError(ConfigureUtils.getMessageConfig("safeQuestion.update.fail"));
      e.printStackTrace();
      return "error";
    }
    return this.show();
  }


  /**
   * 删除复选框所选择的安全问题
   * 
   * @return
   */

  public String delSelected() {
    Integer rows = 0;
    try {
      rows = safeQuestionService.delBySelected(questionId);
      if (rows > 0) {
        this.addActionMessage(ConfigureUtils.getMessageConfig("safeQuestion.delete.success"));
      } else {
        this.addActionError(ConfigureUtils.getMessageConfig("safeQuestion.delete.fail"));
      }

    } catch (Exception e) {
      e.printStackTrace();
      this.addActionError(ConfigureUtils.getMessageConfig("safeQuestion.delete.fail"));
      return "error";
    }
    return this.show();
  }

  public List<Integer> getQuestionId() {
    return questionId;
  }

  public void setQuestionId(List<Integer> questionId) {
    this.questionId = questionId;
  }

  @Override
public Page getPage() {
    return page;
  }

  @Override
public void setPage(Page page) {
    this.page = page;
  }

  public List<SafeQuestion> getSafeQuestionList() {
    return safeQuestionList;
  }

  public void setSafeQuestionList(List<SafeQuestion> safeQuestionList) {
    this.safeQuestionList = safeQuestionList;
  }

  private List<SafeQuestion> safeQuestionList;

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public SafeQuestionService getSafeQuestionService() {
    return safeQuestionService;
  }

  public void setSafeQuestionService(SafeQuestionService safeQuestionService) {
    this.safeQuestionService = safeQuestionService;
  }

  public SafeQuestion getSafeQuestion() {
    return safeQuestion;
  }

  public void setSafeQuestion(SafeQuestion safeQuestion) {
    this.safeQuestion = safeQuestion;
  }

}
