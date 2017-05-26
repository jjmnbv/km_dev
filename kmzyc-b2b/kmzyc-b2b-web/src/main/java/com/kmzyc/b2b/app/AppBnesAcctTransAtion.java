package com.kmzyc.b2b.app;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.b2b.model.AccountInfo;
import com.kmzyc.b2b.service.BnesAcctTransactionService;
import com.kmzyc.b2b.service.member.AccountService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.km.framework.page.Pagination;


/**
 * 手机移动端新版获取账户交易明细
 * 
 * @author lijainjun
 * @since 15-09-16
 */
@Scope("prototype")
@Controller("appBnesAcctTransAtion")
public class AppBnesAcctTransAtion extends AppBaseAction {
  private static final long serialVersionUID = 52752627194416534L;

  // private static Logger logger = Logger.getLogger(AppBnesAcctTransAtion.class);
  private static Logger logger = LoggerFactory.getLogger(AppBnesAcctTransAtion.class);

  @Resource(name = "accountServiceImpl")
  private AccountService accountService;
  @Resource(name = "bnesAcctTransactionServiceImpl")
  private BnesAcctTransactionService bnesAcctTransactionService;
  private Long uid;
  private String token;
  private int pageNo;
  private int pageNum;
  private int rechargeDetailType;
  private String message = "请求参数错误";
  private String code = InterfaceResultCode.FAILED;
  private ReturnResult<Map<String, Object>> returnResult;

  /**
   * app端获取账户交易明细
   * 
   * @author lijianjun
   * @since 15-09-16
   */
  public void queryRechargeDetail() {
    uid = Long.parseLong(getUserid());
    JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
    if (null != jsonParams && !jsonParams.isEmpty()) {
      pageNo = jsonParams.getIntValue("pageNo");// 当前页码
      pageNum = jsonParams.getIntValue("pageNum");// 每页条数
      rechargeDetailType = jsonParams.getIntValue("rechargeDetailType");
      try {
        Map<String, Object> map = new HashMap<String, Object>();
        // 获取账号信息
        AccountInfo accountInfo = accountService.findAccountByUserId(uid);
        // 每页显示10条。
        Pagination page = this.getPagination(5, pageNum);
        page.setStartindex((pageNo - 1) * pageNum + 1);
        page.setEndindex((pageNum * pageNo));
        // sql查询条件对象
        Map<String, Object> objContion = new HashMap<String, Object>();
        // 设置流水时间范围
        Calendar cal = Calendar.getInstance();
        if (rechargeDetailType == 1) {// 1:近一周
          cal.setTime(new Date());
          cal.add(Calendar.WEDNESDAY, -1);
          objContion.put("startDate", cal.getTime());
          objContion.put("endDate", new Date());
        } else if (rechargeDetailType == 2) {// 2:近一个月
          cal.setTime(new Date());
          cal.add(Calendar.MONTH, -1);
          objContion.put("startDate", cal.getTime());
          objContion.put("endDate", new Date());
        } else if (rechargeDetailType == 3) {// 3：近3个月
          cal.setTime(new Date());
          cal.add(Calendar.MONTH, -3);
          objContion.put("startDate", cal.getTime());
          objContion.put("endDate", new Date());
        }
        // 设置交易类型为余额交易类型
        List<Integer> statusList =
            Arrays.asList(Constants.TRANSACTION_TYPE_ONLINE, Constants.TRANSACTION_TYPE_STAGEBACK,
                Constants.TRANSACTION_TYPE_PAY, Constants.TRANSACTION_TYPE_CANCEL,
                Constants.TRANSACTION_TYPE_BACK, Constants.TRANSACTION_TYPE_REW,
                Constants.TRANSACTION_TYPE_REWARD, Constants.TRANSACTION_TYPE_FREEZING,
                Constants.TRANSACTION_TYPE_THAW, Constants.TRANSACTION_TYPE_CONSUMPTION,
                Constants.TRANSACTION_TYPE_SALE, Constants.TRANSACTION_TYPE_DISTRIBUTION,
                Constants.TRANSACTION_TYPE_ENCHASHMENT);
        objContion.put("arry", StringUtils.join(statusList, ","));
        objContion.put("loginId", accountInfo.getNaccountId());
        // 设置查询条件
        page.setObjCondition(objContion);
        this.pagintion = bnesAcctTransactionService.findAcctBalanceByUserId(page);
        map.put("pageNo", pageNo);// 当前页码
        map.put("pageNum", pageNum);// 每页条数
        map.put("totalNum", this.pagintion.getTotalRecords());// 总页数
        map.put("bnesAcctTransList", this.pagintion.getRecordList());
        returnResult =
            new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS, "成功", map);
      } catch (SQLException e) {
        logger.error("app手机端获取交易明细异常" + e.getMessage(), e);
        returnResult =
            new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "失败", null);
      }
    } else {
      returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
    }
    printJsonString(returnResult);
  }



  public int getRechargeDetailType() {
    return rechargeDetailType;
  }



  public void setRechargeDetailType(int rechargeDetailType) {
    this.rechargeDetailType = rechargeDetailType;
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

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public ReturnResult<Map<String, Object>> getReturnResult() {
    return returnResult;
  }

  public void setReturnResult(ReturnResult<Map<String, Object>> returnResult) {
    this.returnResult = returnResult;
  }



}
