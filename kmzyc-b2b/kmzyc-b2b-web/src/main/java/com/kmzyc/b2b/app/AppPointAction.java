package com.kmzyc.b2b.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.model.ScoreInfo;
import com.kmzyc.b2b.service.member.ScoreInfoService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.b2b.vo.ScoreView;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.km.framework.page.Pagination;

@SuppressWarnings("unchecked")
@Scope("prototype")
@Controller("appPointAction")
public class AppPointAction extends BaseAction {

  private static final long serialVersionUID = 3443290927330717006L;

  // private static final Logger LOGGER = Logger.getLogger(AppPointAction.class);
  private static Logger logger = LoggerFactory.getLogger(AppPointAction.class);
  @Resource(name = "scoreInfoServiceImpl")
  private ScoreInfoService scoreInfoService;

  private ReturnResult<Map<String, Object>> returnResult;

  private String uid;
  // 每页多少条
  private int ps;
  // 第几页
  private int pn;

  /**
   * 初始化
   */
  private void setStartParam() {
    JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
    uid = jsonParams.getString("uid");
    ps = jsonParams.getIntValue("ps");
    pn = jsonParams.getIntValue("pn");
  }

  /**
   * 获取用户的等级积分（可用、总积分）。 获取用户等级
   * 
   * @return
   */
  public String getMyPointAndLevel() {
    JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
    if (null != jsonParams && !jsonParams.isEmpty()) {
      this.setStartParam();
      if (null != uid) {
        Map<String, Object> newCondition = new HashMap<String, Object>();
        newCondition.put("loginId", uid);
        Pagination page = this.getPagination(5, ps);
        page.setStartindex((pn - 1) * ps + 1);
        page.setEndindex((ps * pn));
        page.setObjCondition(newCondition);
        try {
          ScoreView myScoreVo = scoreInfoService.findDetailMyScoreByLoginId(Integer.parseInt(uid));
          Map<String, Object> map = new HashMap<String, Object>();
          map.put("curLevel", myScoreVo.getCurLevel());
          map.put("totalConsume", myScoreVo.getTotalConsume());
          map.put("nextLevel", myScoreVo.getNextLevel());
          map.put("nextConsume", myScoreVo.getNextConsume());
          map.put("curScore", myScoreVo.getCurScore());
          setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS, "成功",
              map));
          return SUCCESS;
        } catch (Exception e) {
          logger.error("获取我的积分等级数据出错，userId:" + uid, e);
          setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
              "获取我的积分等级数据出错", null));
        }
      }
    }
    setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
        "获取我的积分等级数据出错", null));
    return SUCCESS;
  }

  /**
   * 获取积分记录信息
   * 
   * @return
   */
  public String getMyPointRecords() {
    JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
    if (null != jsonParams && !jsonParams.isEmpty()) {
      this.setStartParam();
      if (null != uid) {
        Map<String, Object> newCondition = new HashMap<String, Object>();
        newCondition.put("loginId", uid);
        Pagination page = this.getPagination(5, ps);
        page.setStartindex((pn - 1) * ps + 1);
        page.setEndindex((ps * pn));
        page.setObjCondition(newCondition);
        try {
          page = scoreInfoService.findPointsRecordByPage(page);
          List<ScoreInfo> myScoreRecode = page.getRecordList();
          Map<String, Object> map = new HashMap<String, Object>();
          List scoreReList = new ArrayList();
          for (ScoreInfo myScoreRecodes : myScoreRecode) {
            Map<String, Object> mapVo = new HashMap<String, Object>();
            mapVo.put("createDate", myScoreRecodes.getCreateDate());
            mapVo.put("discribe", myScoreRecodes.getDiscribe());
            mapVo.put("scoreNumber", myScoreRecodes.getScoreNumber());
            mapVo.put("scoreType", myScoreRecodes.getScoreType());
            scoreReList.add(mapVo);
          }
          map.put("myScoreRecord", scoreReList);
          map.put("totalRecords", page.getTotalRecords());
          setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS, "成功",
              map));
          return SUCCESS;
        } catch (Exception e) {
          logger.error("获取我的积分记录数据出错，userId:" + uid, e);
          setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
              "获取我的积分记录出错", null));
        }
      }
    }
    setReturnResult(new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
        "获取我的积分记录数据出错", null));
    return SUCCESS;
  }

  public ReturnResult<Map<String, Object>> getReturnResult() {
    return returnResult;
  }

  public void setReturnResult(ReturnResult<Map<String, Object>> returnResult) {
    this.returnResult = returnResult;
  }

}
