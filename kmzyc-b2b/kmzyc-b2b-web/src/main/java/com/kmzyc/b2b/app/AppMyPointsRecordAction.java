package com.kmzyc.b2b.app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.b2b.model.ScoreInfo;
import com.kmzyc.b2b.service.member.ScoreInfoService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.km.framework.page.Pagination;

/**
 * 我的积分
 * 
 * @author lijianjun
 * 
 */
@Scope
@Controller
public class AppMyPointsRecordAction extends AppBaseAction {
  private static final long serialVersionUID = -8175005697586156158L;
  // private static Logger logger = Logger.getLogger(AppMyPointsRecordAction.class);
  private static Logger logger = LoggerFactory.getLogger(AppMyPointsRecordAction.class);
  @Resource(name = "scoreInfoServiceImpl")
  private ScoreInfoService scoreInfoService;
  private Long uid;
  private String token;
  private int pageNo;
  private int pageNum;
  private String message = "请求参数错误";
  private String code = InterfaceResultCode.FAILED;
  private ReturnResult<Map<String, Object>> returnResult;

  // 获取积分明细
  public void queryPointsRecordList() {
    uid = Long.parseLong(getUserid());
    JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
    if (null != jsonParams && !jsonParams.isEmpty()) {
      token = jsonParams.getString("token");
      pageNo = jsonParams.getIntValue("pageNo");// 当前页码
      pageNum = jsonParams.getIntValue("pageNum");// 每页条数
      Pagination page = this.getPagination(5, pageNum);
      page.setStartindex((pageNo - 1) * pageNum + 1);
      page.setEndindex((pageNum * pageNo));
      Map<String, Object> map = new HashMap<String, Object>();
      // 页面传入的查询条件
      Map<String, String> objContion = new HashMap<String, String>();
      // sql查询条件对象
      Map<String, Object> conditon = new HashMap<String, Object>();
      // 解析并组装查询条件
      conditon.put("loginId", uid);
      try {
        if (objContion != null) {
          if (StringUtils.isNotEmpty(objContion.get("dateBegin"))) {
            conditon.put("dateBegin", objContion.get("dateBegin"));
          }
          if (StringUtils.isNotEmpty(objContion.get("dateEnd"))) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse(objContion.get("dateEnd"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            conditon.put("dateEnd",
                sdf.format(DateUtils.addSeconds(DateUtils.addDays(date, 1), -1)));
          }
        }
        // 设置查询条件
        page.setObjCondition(conditon);
        this.pagintion = scoreInfoService.findPointsRecordByPage(page);
        // 去掉分数的负号
        if (pagintion.getRecordList() != null && pagintion.getRecordList().size() > 0) {
          for (ScoreInfo info : (List<ScoreInfo>) pagintion.getRecordList()) {
            System.out.println(info.getScoreNumber());
            info.setScoreNumber(Math.abs(info.getScoreNumber() == null ? 0 : info.getScoreNumber()));
          }
        }
        map.put("pageNo", pageNo);// 当前页码
        map.put("pageNum", pageNum);// 每页条数
        map.put("totalNum", this.pagintion.getTotalRecords());// 总条数
        map.put("recordList", pagintion.getRecordList());
        returnResult =
            new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS, "成功", map);
      } catch (Exception e) {
        logger.error("积分记录查询出错：" + e.getMessage(), e);
        returnResult =
            new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "失败", null);
      }
    } else {
      logger.error("app手机端参数异常");
      returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
    }
    printJsonString(returnResult);
  }



  public int getPageNo() {
    return pageNo;
  }


  public void setPageNo(int pageNo) {
    this.pageNo = pageNo;
  }


  public int getPageNum() {
    return pageNum;
  }


  public void setPageNum(int pageNum) {
    this.pageNum = pageNum;
  }


  public Long getUid() {
    return uid;
  }


  public void setUid(Long uid) {
    this.uid = uid;
  }


  public String getToken() {
    return token;
  }


  public void setToken(String token) {
    this.token = token;
  }



}
