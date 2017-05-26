package com.pltfm.app.action;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.LoginInfoService;
import com.pltfm.app.service.PersonalityInfoService;
import com.pltfm.app.service.ScoreInfoService;
import com.pltfm.app.service.ScoreRuleService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.PersonalityInfo;
import com.pltfm.app.vobject.ScoreInfo;
import com.pltfm.app.vobject.ScoreRule;

/**
 * 积分明细action 处理类
 * 
 * @author zhl
 * @since 2013-07-24
 */
@Component(value = "scoreInfoAction")
@Scope(value = "prototype")
public class ScoreInfoAction extends ActionSupport implements ModelDriven {
  private static final long serialVersionUID = -5843454052513277722L;
  private ScoreInfo scoreInfo;
  private Page page;// 分页对象
  @Resource(name = "scoreInfoService")
  private ScoreInfoService scoreInfoService;
  @Resource(name = "loginInfoService")
  private LoginInfoService loginInfoService;
 
  
  @Resource(name = "personalityInfoService")
  private PersonalityInfoService personalityInfoService;
 
  @Resource(name = "scoreRuleService")
  private ScoreRuleService scoreRuleService;


  private List<String> scoreInfoIds;// 积分明细主键集合
  private String scoreInfoId;// 积分明细主键
  private Integer showType;// 公共页面判断参数
  private Integer loginId;

  /**
   * 分页查询积分明细
   * 
   * @return
   */
  public String queryPageList() {
    HttpServletRequest request = ServletActionContext.getRequest();
    if (page == null) {
        page = new Page();
    }
   
    String isMenu = request.getParameter("isMenu");
   
    try {
      if (showType != null) {// 公共页面判断参数是否为空
        // 跟据登录ID查询账户信息
        LoginInfo  loginInfo =  loginInfoService.getLoginId(loginId);
        PersonalityInfo personalityInfo = personalityInfoService.selectPersonalityInfoByLoginId(loginId);
        if(personalityInfo!=null){
            request.setAttribute("userTotalScore",personalityInfo.getN_TotalIntegral());
            request.setAttribute("userAvlibalScore",personalityInfo.getN_AvailableIntegral());
        }
       
        // 取和登录账号
          scoreInfo.setLoginAccount(loginInfo.getLoginAccount());
      }
      
      if(!"true".equals(isMenu)){
          page = scoreInfoService.queryForPageList(scoreInfo, page);
      }else{
          request.setAttribute("isMenu", "true");
      }
      
      //获取所有预售规则名称MAP
      ScoreRule paramRule = new ScoreRule();
      Page scoreRulePage = new Page();
      scoreRulePage.setPageNo(1);
      scoreRulePage.setPageSize(Integer.MAX_VALUE);
      scoreRuleService.queryForPageList(paramRule, scoreRulePage);
      Map<String,String> scoreRuleMap = new LinkedHashMap<String,String>();
      ScoreRule tempRule = null;
      if(scoreRulePage.getDataList()!=null && !scoreRulePage.getDataList().isEmpty()){
          for(Object obj:scoreRulePage.getDataList()){
              tempRule = (ScoreRule)obj;
              scoreRuleMap.put(tempRule.getN_scoreRuleId().toString(), tempRule.getDiscribe());   
          }
      }
      ScoreRule scoreRule = scoreRuleService.queryByRuleCode("RU0005");
      if(scoreRule!=null){
          scoreRuleMap.put(scoreRule.getN_scoreRuleId().toString(),scoreRule.getDiscribe());
      }
      
      request.setAttribute("scoreRuleMap", scoreRuleMap);
      return "querySuccess";
    } catch (Exception e) {
      e.printStackTrace();
      this.addActionError(ConfigureUtils.getMessageConfig("scoreInfo.query.fail"));
      return "querySuccess";
    }
  }

  /**
   * 删除积分明细信息
   * 
   * @return
   */
  public String operateDelete() {
    try {
      scoreInfoService.deleteByPrimaryKey(Integer.valueOf(scoreInfoId));
      return "deleteSuccess";
    } catch (Exception e) {
      e.printStackTrace();
      return queryPageList();
    }
  }

  /**
   * 多条删除积分明细信息
   * 
   * @return
   */
  public String operateDeleteAll() {
    try {
      scoreInfoService.deleteScoreInfo(scoreInfoIds);
      return "deleteSuccess";
    } catch (Exception e) {
      e.printStackTrace();
      return queryPageList();
    }
  }

  public ScoreInfo getScoreInfo() {
    return scoreInfo;
  }

  public void setScoreInfo(ScoreInfo scoreInfo) {
    this.scoreInfo = scoreInfo;
  }

  public Page getPage() {
    return page;
  }

  public void setPage(Page page) {
    this.page = page;
  }

  public ScoreInfoService getScoreInfoService() {
    return scoreInfoService;
  }

  public void setScoreInfoService(ScoreInfoService scoreInfoService) {
    this.scoreInfoService = scoreInfoService;
  }

  public List<String> getScoreInfoIds() {
    return scoreInfoIds;
  }

  public void setScoreInfoIds(List<String> scoreInfoIds) {
    this.scoreInfoIds = scoreInfoIds;
  }

  public String getScoreInfoId() {
    return scoreInfoId;
  }

  public void setScoreInfoId(String scoreInfoId) {
    this.scoreInfoId = scoreInfoId;
  }

  public Integer getShowType() {
    return showType;
  }

  public void setShowType(Integer showType) {
    this.showType = showType;
  }

  public LoginInfoService getLoginInfoService() {
    return loginInfoService;
  }

  public void setLoginInfoService(LoginInfoService loginInfoService) {
    this.loginInfoService = loginInfoService;
  }

  public Integer getLoginId() {
    return loginId;
  }

  public void setLoginId(Integer loginId) {
    this.loginId = loginId;
  }

  @Override
public ScoreInfo getModel() {
    scoreInfo = new ScoreInfo();
    return scoreInfo;
  }

}
