package com.pltfm.app.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.kmzyc.commons.exception.ActionException;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.entities.DiffAdj;
import com.pltfm.app.entities.DiffAdjCriteria;
import com.pltfm.app.entities.DiffAdjExample;
import com.pltfm.app.entities.HurlFare;
import com.pltfm.app.entities.HurlFareCriteria;
import com.pltfm.app.entities.HurlFareExample;
import com.pltfm.app.entities.HurlProduct;
import com.pltfm.app.entities.HurlProductCriteria;
import com.pltfm.app.entities.HurlProductExample;
import com.pltfm.app.entities.SellerSettlement;
import com.pltfm.app.entities.SellerSettlementCriteria;
import com.pltfm.app.entities.SellerSettlementExample;
import com.pltfm.app.entities.SettlementRefund;
import com.pltfm.app.entities.SettlementRefundCriteria;
import com.pltfm.app.entities.SettlementRefundExample;
import com.pltfm.app.entities.SettmentOperateStatement;
import com.pltfm.app.service.DiffAdjService;
import com.pltfm.app.service.HurlFareService;
import com.pltfm.app.service.HurlProductService;
import com.pltfm.app.service.OrderQryService;
import com.pltfm.app.service.SellerSettlementService;
import com.pltfm.app.service.SettlementRefundService;
import com.pltfm.app.service.SettmentOperateStatementService;
import com.pltfm.app.util.ActionUtils;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.util.OrderDictionaryEnum.SettlementOperateType;
import com.pltfm.app.util.OrderDictionaryEnum.SettlementOperatorType;
import com.pltfm.app.vobject.BnesAcctTransactionQuery;
import com.pltfm.sys.bean.SysUserBean;
import com.pltfm.sys.model.SysUser;
import com.pltfm.sys.util.ErrorCode;

import redis.clients.jedis.JedisCluster;

/**
 * 订单结算action
 * 
 * @author weijl
 */
@SuppressWarnings("unchecked")
@Controller("orderClearingAction")
public class OrderClearingAction extends BaseAction {

  /**
     * 
     */
  private static final long serialVersionUID = 1545801476153707616L;
  private Logger log = Logger.getLogger(OrderClearingAction.class);
  @Resource
  private SellerSettlementService sellerSettlementService;
  private SellerSettlementCriteria sellerSettlementCriteria = new SellerSettlementCriteria();
  private SellerSettlement sellerSettlement;

  @Resource
  private DiffAdjService diffAdjService;
  private DiffAdj diffAdj;
  private DiffAdjCriteria diffAdjCriteria = new DiffAdjCriteria();

  @Resource
  private SettmentOperateStatementService settmentOperateStatementService;
  private Page page;
  private String sellerId = null;
  private String sno = null;
  private String hurlIds = null;
  private String period = null;
  private String supplierName = null;
  private String periodY = null;
  private List<SellerSettlement> supplierNameList = null;

  @Resource
  private HurlProductService hurlProductService;
  private HurlProductCriteria hurlProductCriteria = new HurlProductCriteria();

  @Resource
  private SettlementRefundService settlementRefundService;
  private SettlementRefundCriteria settlementRefundCriteria = new SettlementRefundCriteria();

  @Resource
  private HurlFareService hurlFareService;
  private HurlFareCriteria hurlFareCriteria = new HurlFareCriteria();

  @Resource
  private OrderQryService orderQryService;

  // 客户系统远程方法
  @Resource
  private CustomerRemoteService customerRemoteService;

  @Resource(name = "jedisCluster")
  private JedisCluster jedisCluster;

  private int flag;

  public String getPeriodY() {
    return periodY;
  }

  public void setPeriodY(String periodY) {
    this.periodY = periodY;
  }

  public List<SellerSettlement> getSupplierNameList() {
    return supplierNameList;
  }

  public void setSupplierNameList(List<SellerSettlement> supplierNameList) {
    this.supplierNameList = supplierNameList;
  }

  public String getSupplierName() {
    return supplierName;
  }

  public void setSupplierName(String supplierName) {
    this.supplierName = supplierName;
  }

  public DiffAdjCriteria getDiffAdjCriteria() {
    return diffAdjCriteria;
  }

  public void setDiffAdjCriteria(DiffAdjCriteria diffAdjCriteria) {
    this.diffAdjCriteria = diffAdjCriteria;
  }

  public HurlFareCriteria getHurlFareCriteria() {
    return hurlFareCriteria;
  }

  public void setHurlFareCriteria(HurlFareCriteria hurlFareCriteria) {
    this.hurlFareCriteria = hurlFareCriteria;
  }

  public SettlementRefundCriteria getSettlementRefundCriteria() {
    return settlementRefundCriteria;
  }

  public void setSettlementRefundCriteria(SettlementRefundCriteria settlementRefundCriteria) {
    this.settlementRefundCriteria = settlementRefundCriteria;
  }

  public int getFlag() {
    return flag;
  }

  public void setFlag(int flag) {
    this.flag = flag;
  }

  public HurlProductCriteria getHurlProductCriteria() {
    return hurlProductCriteria;
  }

  public void setHurlProductCriteria(HurlProductCriteria hurlProductCriteria) {
    this.hurlProductCriteria = hurlProductCriteria;
  }

  public SellerSettlement getSellerSettlement() {
    return sellerSettlement;
  }

  public void setSellerSettlement(SellerSettlement sellerSettlement) {
    this.sellerSettlement = sellerSettlement;
  }

  public DiffAdj getDiffAdj() {
    return diffAdj;
  }

  public void setDiffAdj(DiffAdj diffAdj) {
    this.diffAdj = diffAdj;
  }

  public String getSno() {
    return sno;
  }

  public void setSno(String sno) {
    this.sno = sno;
  }

  public SellerSettlementCriteria getSellerSettlementCriteria() {
    return sellerSettlementCriteria;
  }

  public void setSellerSettlementCriteria(SellerSettlementCriteria sellerSettlementCriteria) {
    this.sellerSettlementCriteria = sellerSettlementCriteria;
  }

  public String getSellerId() {
    return sellerId;
  }

  public void setSellerId(String sellerId) {
    this.sellerId = sellerId;
  }

  public String getPeriod() {
    return period;
  }

  public void setPeriod(String period) {
    this.period = period;
  }


  public String getHurlIds() {
    return hurlIds;
  }

  public void setHurlIds(String hurlIds) {
    this.hurlIds = hurlIds;
  }

  /**
   * 结算单列表
   * 
   * @return
   * @throws ServiceException
   */
  public String turnPage() throws ServiceException {

    page = getPage();
    
    // 取得当前用户ID userId
    Map session = ActionContext.getContext().getSession();
    SysUser sysuser = (SysUser) session.get("sysUser");
    String userId = null;
    if(null != sysuser){
       userId = sysuser.getUserId().toString(); 
    }
    
    if (flag == 1 || flag == 2 || sellerSettlementCriteria == null) {
      page.setPageNo(0);
      page.setPageSize(10);
    }
    String back = (String) this.getHttpServletRequest().getSession().getAttribute("ifback");
    if (null == back || !"back".equals(back)) {

      sellerSettlementCriteria = new SellerSettlementCriteria();

      if (flag == 2) {
        // 3:运营已结出 6:已结算
        Integer statuses[] = {3, 6};
        sellerSettlementCriteria.setStatuses(Arrays.asList(statuses));
      }
      // 财务结算页条件查询时可查不同状态单
      if (flag == 3) {
        sellerSettlementCriteria.setStatuses(null);
      }

      String params[] =
          {"_settlementNo", "_sellerName", "_startDate", "_endDate", "_settlementStatus",
              "_sellerId"};
      ActionUtils.putValueIntoMap(this.getHttpServletRequest(), sellerSettlementCriteria, params);

      if (null != sellerSettlementCriteria.getStartDate()
          && null != sellerSettlementCriteria.getEndDate()) {
        List<String> periods =
            sellerSettlementService.getPeriodsByStartDateEndDate(
                sellerSettlementCriteria.getStartDate(), sellerSettlementCriteria.getEndDate());
        sellerSettlementCriteria.setInPeriods(periods);
      }

      // 结算金额区间
      String minMoney = this.getHttpServletRequest().getParameter("_minMoney");
      String maxMoney = this.getHttpServletRequest().getParameter("_maxMoney");
      if (null != minMoney && !"".equals(minMoney)) {
        sellerSettlementCriteria.setMinMoney(new BigDecimal(minMoney));
      }

      if (null != maxMoney && !"".equals(maxMoney)) {
        sellerSettlementCriteria.setMaxMoney(new BigDecimal(maxMoney));
      }

      int pageIndex = page.getPageNo();
      if (pageIndex == 0) pageIndex = 1;
      int pageSize = page.getPageSize();

      int startIndex = (pageIndex - 1) * pageSize;
      int endIndex = pageSize * pageIndex;
      sellerSettlementCriteria.setStartIndex(startIndex < 0 ? 0 : startIndex);
      sellerSettlementCriteria.setEndIndex(endIndex < 0 ? 20 : endIndex);
      
      if(null != userId){
          this.getHttpServletRequest().getSession().setAttribute(userId, sellerSettlementCriteria);
      }
    } else {
        HttpSession thesession = this.getHttpServletRequest().getSession();
        thesession.setAttribute("ifback", null);
      if(null != userId&&thesession.getAttribute(userId)!=null){ //取得当前用户的查询条件
          sellerSettlementCriteria = (SellerSettlementCriteria)thesession.getAttribute(userId);
      }
      
    }

    sellerSettlementService.querySettlementList(getPage(), sellerSettlementCriteria);

   
    this.getHttpServletRequest().setAttribute("dataList", page.getDataList());
    return SUCCESS;
  }
  
  /**
   * 结算单导出
   * 
   * @return
   * @throws ServiceException
   */
  public String exportFinacialAuditInfo() throws ServiceException {
      
      // 取得当前用户ID userId
      Map session = ActionContext.getContext().getSession();
      SysUser sysuser = (SysUser) session.get("sysUser");
      String userId = null;
      if(null != sysuser){
         userId = sysuser.getUserId().toString(); 
      }

   
      sellerSettlementCriteria = new SellerSettlementCriteria();

      

      String params[] =
          {"_settlementNo", "_sellerName", "_startDate", "_endDate", "_settlementStatus",
              "_sellerId"};
      ActionUtils.putValueIntoMap(this.getHttpServletRequest(), sellerSettlementCriteria, params);

      if (null != sellerSettlementCriteria.getStartDate()
          && null != sellerSettlementCriteria.getEndDate()) {
        List<String> periods =
            sellerSettlementService.getPeriodsByStartDateEndDate(
                sellerSettlementCriteria.getStartDate(), sellerSettlementCriteria.getEndDate());
        sellerSettlementCriteria.setInPeriods(periods);
      }

      // 结算金额区间
      String minMoney = this.getHttpServletRequest().getParameter("_minMoney");
      String maxMoney = this.getHttpServletRequest().getParameter("_maxMoney");
      if (null != minMoney && !"".equals(minMoney)) {
        sellerSettlementCriteria.setMinMoney(new BigDecimal(minMoney));
      }

      if (null != maxMoney && !"".equals(maxMoney)) {
        sellerSettlementCriteria.setMaxMoney(new BigDecimal(maxMoney));
      }

      
      
      if(null != userId){
          this.getHttpServletRequest().getSession().setAttribute(userId, sellerSettlementCriteria);
      }


      List<Map<String,Object>> sellerMap = sellerSettlementService.selectSettlementListForExport(sellerSettlementCriteria);
      String path;
      try {
        path = orderQryService.exportFinacialAuditInfo(sellerMap);
        getHttpServletResponse().getWriter().print("{'path':'" + path + "'}");
      } catch (Exception e) {
        log.error("导出收退款报表异常", e);
      }

    return null;
  }

  /**
   * @throws SQLException 结算单详情
   * @return
   * @throws ServiceException
   * @throws
   */
  public String toDetail() throws ServiceException, SQLException {
    if (StringUtils.isEmpty(sno)) {
      log.info("====//PARAM settlmentNo IS NULL,return ");

      return INPUT;
    }


    // 标识当前用户的角色名称
    Map session = ActionContext.getContext().getSession();
    HttpServletRequest request = ServletActionContext.getRequest();
    SysUser sysuser = (SysUser) session.get("sysUser");
    // 获取用户信息
    SysUserBean bean = new SysUserBean();
    try {
      sysuser = bean.getSysUserRoleDetail(sysuser.getUserId());
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    String roleName = sysuser.getRoleName();
    if (StringUtils.isNotEmpty(roleName)) {
      if (roleName.indexOf("运营") >= 0) {
        request.setAttribute("roleName", "yun");
      } else if (roleName.indexOf("财务") >= 0) {
        request.setAttribute("roleName", "cai");
      } else if (roleName.indexOf("管理员") >= 0) {
        request.setAttribute("roleName", "guan");
      }
    }
    String toType = request.getParameter("toType");
    if (StringUtils.isNotEmpty(toType)) {
      request.setAttribute("toType", toType);
    }

    // 标识当前用户的角色名称

    SellerSettlementCriteria sCriteria = new SellerSettlementCriteria();
    sCriteria.setSettlementNo(sno);

    SellerSettlement sellerSettlement = sellerSettlementService.getSettlementByNo(sCriteria, true);

    // 本账期发起差异调整明细

    DiffAdjExample diffAdjExample = new DiffAdjExample();
    diffAdjExample.createCriteria().andCurrSettmentNoEqualTo(sno);
    diffAdjExample.setOrderByClause("DIFF_ADJ_ID desc");

    List<DiffAdj> diffList = diffAdjService.selectDiffAdjDetail(diffAdjExample);

    this.getHttpServletRequest().setAttribute("info", sellerSettlement);
    this.getHttpServletRequest().setAttribute("diffs", diffList);
    return SUCCESS;
  }

  /**
   * 根据商户ID，结算期判断目标结算记录是否已生成
   */
  public String isPeriodAlreadyExists() {
    try {
      boolean isExists = sellerSettlementService.thePeriodAlreadyExists(sellerId, period);
      this.getHttpServletRequest().setAttribute("isExists", isExists);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      msg = ERROR;
      return ERROR;
    }
    return SUCCESS;
  }

  /**
   * 手动生成结算单
   */
  public void handGenerateSettlement() {
    String key = "settlementGenerate_runing";

    try {
      if (!jedisCluster.exists(key)) {
        sellerSettlementService.generateSettlement(sellerId, period,
            (short) OrderDictionaryEnum.SettlementOperateType.gener.getKey(),
            (short) OrderDictionaryEnum.SettlementOperatorType.bgUser.getKey(), false);
      } else {
        log.error("商家结算正在跑批中,不允许操作");
        msg = "商家结算正在跑批中,不允许操作";
        return;
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      msg = "生成结算单异常";
      return;
    }
    msg = "生成结算单成功";
  }

  /**
   * 保存运营确认意见
   * 
   * @return
   */
  public String saveOperateConfirmation() {
    if (null == sellerSettlement) {
      return ERROR;
    }
    sellerSettlement.setOperateConfirmTime(new Date());

    short sss = 0;
    sellerSettlement.setSettlementStatus(sss);

    try {
      sellerSettlementService.updateOperate(sellerSettlement);
    } catch (Exception e) {
      log.error(ErrorCode.INTER_DIFF_ADJ_ERROR, e);
      return ERROR;
    }
    this.getHttpServletRequest().setAttribute("sno", sno);
    this.getHttpServletRequest().getSession().setAttribute("ifback", "back");
    return SUCCESS;
  }

  /**
   * 保存运营确认意见
   * 
   * @return
   */
  public String saveAndSubmitOperateConfirmation() {
    if (null == sellerSettlement) {
      return ERROR;
    }

    synchronized (sellerSettlement.getSettlementNo()) {
      try {

        // 是否重复操作 s
        SellerSettlementExample example = new SellerSettlementExample();
        example.createCriteria().andSettlementNoEqualTo(sellerSettlement.getSettlementNo())
            .andSettlementStatusEqualTo((short) 3);

        List settmentList = sellerSettlementService.selectByExample(example);
        if (null != settmentList && !settmentList.isEmpty()) {
          return SUCCESS;
        }
        // 是否重复操作 e

        sellerSettlement.setOperateConfirmTime(new Date());

        short sss = Short.valueOf(SettlementOperateType.operateSure.getKey() + "");
        sellerSettlement.setSettlementStatus(sss);


        sellerSettlementService.updateOperate(sellerSettlement);

        // save log
        SettmentOperateStatement record = new SettmentOperateStatement();
        record.setOperateTime(new Date());

        record.setOperator(getOnlineUserName());
        record.setSettmentNo(sellerSettlement.getSettlementNo());
        // 后台用户
        short sss1 = Short.valueOf(SettlementOperatorType.bgUser.getKey() + "");
        record.setOperatorType(sss1);

        // 运营确认
        short sss2 = Short.valueOf(SettlementOperateType.operateSure.getKey() + "");
        record.setOperateType(sss2);
        settmentOperateStatementService.insert(record);

      } catch (Exception e) {
        log.error(ErrorCode.INTER_DIFF_ADJ_ERROR, e);
        return ERROR;
      }

      this.getHttpServletRequest().setAttribute("sno", sno);
      this.getHttpServletRequest().getSession().setAttribute("ifback", "back");
    }
    return SUCCESS;
  }

  /**
   * 保存财务 确认意见
   * 
   * @return
   */
  public String saveAndSubmitFinancialConfirmation() {
    if (null == sellerSettlement) {
      return ERROR;
    }

    synchronized (sellerSettlement.getSettlementNo()) {
      try {

        // 是否重复操作 s
        SellerSettlementExample example = new SellerSettlementExample();
        List<Short> list = new ArrayList<>();
        list.add((short) 4);
        list.add((short) 5);
        list.add((short) 6);
        example.createCriteria().andSettlementNoEqualTo(sellerSettlement.getSettlementNo())
            .andSettlementStatusIn(list);

        List settmentList = sellerSettlementService.selectByExample(example);
        if (null != settmentList && !settmentList.isEmpty()) {
          return SUCCESS;
        }
        // 是否重复操作 e

        sellerSettlement.setOperateConfirmTime(new Date());

        String _status = this.getHttpServletRequest().getParameter("audit");
        // 结算状态，1:未确认,2:商家已确认,3:运营已确认,4:财务审核通过,5:财务审核拒绝,6:已结出
        short settlementStatus = 6;
        if (StringUtils.isNotEmpty(_status) && _status.equals("5")) {
          settlementStatus = 5;
          SettmentOperateStatement record = new SettmentOperateStatement();
          record.setOperateTime(new Date());
          record.setOperator(getOnlineUserName());
          record.setSettmentNo(sellerSettlement.getSettlementNo()); // 后台用户
          short sss1 = Short.valueOf(SettlementOperatorType.bgUser.getKey() + "");
          record.setOperatorType(sss1);
          record.setOperateType(settlementStatus);
          try {
            settmentOperateStatementService.insert(record);
          } catch (SQLException e) {
            log.error("财务审核拒绝流水异常：" + e.getMessage());
            return ERROR;
          }
        }

        sellerSettlement.setSettlementStatus(settlementStatus);



        int loginid =
            sellerSettlementService.queryLoginIdByCommercialId(sellerSettlement.getSellerId())
                .intValue();
        // 将应结金额结算到商家用户
        if (settlementStatus == 6) {
          try {
            BnesAcctTransactionQuery query = new BnesAcctTransactionQuery();
            query.setLoginId(loginid);
            double temp= (sellerSettlement.getCurrSettleAccounts() == null ? 0 : sellerSettlement.getCurrSettleAccounts())
                            + (sellerSettlement.getDiffAdjSum() == null ?  0 : sellerSettlement.getDiffAdjSum());
            query.setAmount(new BigDecimal(temp));
            query.setContent("商家结算单 " + sellerSettlement.getSettlementNo() + " ("
                + sellerSettlement.getSettlementPeriod() + ") 结出到余额");
            query.setType(17);
            customerRemoteService.UpdateUserBalance(query, 2);
          } catch (Exception e) {
            log.error("调用远程接口结算金额至商家账户余额异常：" + e.getMessage());
            return ERROR;
          }

          // 差异调整金额计入商家账户余额
          sellerSettlementService.updateDiffForSettled(sellerSettlement);
        }

        // 操作流水：当状态为财务已结算时
        if ("4".equals(_status)) {
          SettmentOperateStatement record = new SettmentOperateStatement();
          record.setOperateTime(new Date());

          record.setOperator(getOnlineUserName());
          record.setSettmentNo(sellerSettlement.getSettlementNo()); // 后台用户
          short sss1 = Short.valueOf(SettlementOperatorType.bgUser.getKey() + "");
          record.setOperatorType(sss1);

          record.setOperateType((short) 4);
          settmentOperateStatementService.insert(record);
        }

        if (settlementStatus == 6) {
          // 操作流水：修改结算单状态为已结算
          SettmentOperateStatement recordFinacila = new SettmentOperateStatement();
          recordFinacila.setOperateTime(new Date());

          recordFinacila.setOperator(getOnlineUserName());
          recordFinacila.setSettmentNo(sellerSettlement.getSettlementNo()); // 后台用户
          short sss2 = Short.valueOf(SettlementOperatorType.bgUser.getKey() + "");
          recordFinacila.setOperatorType(sss2);
          recordFinacila.setOperateType(settlementStatus);
          settmentOperateStatementService.insert(recordFinacila);

          // 更改结算单的结算时间为当前时间
          sellerSettlement.setFinancialConfirmTime(new Date());
          sellerSettlement.setSettlementFinishTime(new Date());

          // 获取商家电话
          String mobile = sellerSettlementService.selectMobieByLoginId(sellerSettlement);

          // 发送短信通知给商家
          if (null != mobile && StringUtils.isNotEmpty(mobile)) {
            try {
              String settlementNo = sellerSettlement.getSettlementNo();
              if (null != settlementNo && !"".equals(settlementNo)) {
                sellerSettlementService.sendMsg(sellerSettlement.getSellerId(), settlementNo,
                    mobile);
              } else {
                log.error("发送短信通知给商家：结算单号为空！");
              }
            } catch (Exception e) {
              log.error(e);
            }
          }


        }



        // 更改结算单
        sellerSettlementService.updateFinancialConfirmation(sellerSettlement);
      } catch (Exception e) {
        log.error(ErrorCode.INTER_DIFF_ADJ_ERROR, e);
        return ERROR;
      }

      this.getHttpServletRequest().setAttribute("sno", sno);
      this.getHttpServletRequest().getSession().setAttribute("ifback", "back");
    }
    return SUCCESS;
  }

  /**
   * 增加差异调整明细 （
   * 
   * @return
   * @throws ActionException
   */
  public String showAddDiffAdjPage() throws ActionException {

    this.getHttpServletRequest().setAttribute("sno", sno);

    this.getHttpServletRequest().setAttribute("settlementPeriod", period);
    return "success";
  }

  /**
   * 保存 差异调整
   */
  public void saveDiffAdj() {
    if (null == diffAdj || StringUtils.isEmpty(diffAdj.getCurrSettmentNo())
        || StringUtils.isEmpty(diffAdj.getAdjTitle())
        || StringUtils.isEmpty(diffAdj.getCurrSettmentNo())) {
      msg = "error";
      return;
    }

    try {
      SellerSettlementCriteria criteria = new SellerSettlementCriteria();

      criteria.setSettlementNo(diffAdj.getCurrSettmentNo());

      SellerSettlement info = sellerSettlementService.getSettlementByNo(criteria, false);

      // 取对应商家会员ID
      diffAdj.setSellerId(new BigDecimal(info.getSellerId()));
      // 发生结算单ID

      // 调整操作人ID
      diffAdj.setOperaterId(getOnlineUserCode());

      diffAdj.setAdjTime(new Date());
      Short sss = 0;
      diffAdj.setSettmentStatus(sss);

      diffAdjService.insert(diffAdj);
    } catch (Exception e) {
      e.printStackTrace();
      msg = "error";
      return;
    }

    msg = "success";
  }

  /**
   * 删除差异调整
   */
  public void deleteDiffAdj() {
    if (StringUtils.isEmpty(sno)) {
      msg = "save error";
      return;
    }

    try {

      DiffAdjExample example = new DiffAdjExample();
      example.createCriteria().andDiffAdjIdEqualTo(Long.valueOf(sno));
      diffAdjService.deleteByExample(example);
    } catch (Exception e) {
      e.printStackTrace();
      msg = "error";
      return;
    }

    msg = "success";
  }

  /**
   * 取结算单信息
   * 
   * @param settlementNo
   * @return
   * @throws ServiceException
   */
  private SellerSettlement getSimpleSellerSettlement(String settlementNo) throws ServiceException {

    SellerSettlementCriteria criteria = new SellerSettlementCriteria();
    criteria.setSettlementNo(settlementNo);

    return sellerSettlementService.getSettlementByNo(criteria, false);
  }

  /**
   * 明细-妥投商品
   * 
   * @return
   * @throws Exception
   */
  public String showHurlProductPage() throws Exception {
    if (StringUtils.isEmpty(sno)) {
      return ERROR;
    }
    Page page = getPage();
    if (flag != 0) {

      hurlProductCriteria = new HurlProductCriteria();
      page.setPageNo(0);
      page.setPageSize(10);
      flag = 0;
    }
    hurlProductCriteria.setSettlementNo(sno);

    HurlProductExample example = HurlProductCriteria.converToExample(hurlProductCriteria);

    try {
      int count = hurlProductService.countByExample(example);
      page.setRecordCount(count);
      List<HurlProduct> dataList = null;
      if (count > 0) {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0) pageIndex = 1;
        int pageSize = page.getPageSize();

        int startIndex = (pageIndex - 1) * pageSize;
        int endIndex = pageSize * pageIndex;
        example.setStartIndex(startIndex < 0 ? 0 : startIndex);
        example.setEndIndex(endIndex < 0 ? 20 : endIndex);

        // 查询条件超出实际数据范围，返回第一页数据，设置当前页为1
        if (count < startIndex) {
          page.setPageNo(1);
        }
        dataList = hurlProductService.selectByExample(example);
      }
      page.setDataList(dataList);
      SellerSettlement info = getSimpleSellerSettlement(hurlProductCriteria.getSettlementNo());
      this.getHttpServletRequest().setAttribute("info", info);

      // 汇总总数
      // Map<String, Object> map = new HashMap<String, Object>();
      // map.put("settlementNo", sno);
      Map sums = hurlProductService.selectHurlSum(hurlProductCriteria);
      this.getHttpServletRequest().setAttribute("sums", sums);
    } catch (Exception e) {
      log.error(e.getMessage());
      return ERROR;
    }


    return SUCCESS;
  }

  /**
   * 明细-退货
   * 
   * @return
   * @throws Exception
   */
  public String showSettlementRefundPage() throws Exception {
    if (StringUtils.isEmpty(sno)) {
      return ERROR;
    }
    Page page = getPage();
    if (flag != 0) {

      settlementRefundCriteria = new SettlementRefundCriteria();
      page.setPageNo(0);
      page.setPageSize(10);
      flag = 0;
    }

    try {
      settlementRefundCriteria.setSettlementNo(sno);
      SettlementRefundExample example =
          SettlementRefundCriteria.converToExample(settlementRefundCriteria);

      int count = settlementRefundService.countByExample(example);
      page.setRecordCount(count);
      List<SettlementRefund> dataList = null;
      if (count > 0) {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0) pageIndex = 1;
        int pageSize = page.getPageSize();

        int startIndex = (pageIndex - 1) * pageSize;
        int endIndex = pageSize * pageIndex;
        example.setStartIndex(startIndex < 0 ? 0 : startIndex);
        example.setEndIndex(endIndex < 0 ? 20 : endIndex);

        // 查询条件超出实际数据范围，返回第一页数据，设置当前页为1
        if (count < startIndex) {
          page.setPageNo(1);
        }
        dataList = settlementRefundService.selectByExample(example);

      }
      page.setDataList(dataList);
      SellerSettlement info = getSimpleSellerSettlement(settlementRefundCriteria.getSettlementNo());
      this.getHttpServletRequest().setAttribute("info", info);

      Map map = settlementRefundService.refundSum(settlementRefundCriteria);
      this.getHttpServletRequest().setAttribute("sums", map);

    } catch (Exception e) {
      log.error(e.getMessage());
      return ERROR;
    }


    return SUCCESS;
  }

  /**
   * 明细-妥投订单运费
   * 
   * @return
   * @throws Exception
   */
  public String showHurlFarePage() throws Exception {
    if (StringUtils.isEmpty(sno)) {
      return ERROR;
    }
    Page page = getPage();
    if (flag != 0) {

      hurlFareCriteria = new HurlFareCriteria();
      page.setPageNo(0);
      page.setPageSize(10);
      flag = 0;
    }

    try {
      hurlFareCriteria.setSettlementNo(sno);
      HurlFareExample example = HurlFareCriteria.converToExample(hurlFareCriteria);

      int count = hurlFareService.countByExample(example);
      page.setRecordCount(count);
      List<HurlFare> dataList = null;
      if (count > 0) {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0) pageIndex = 1;
        int pageSize = page.getPageSize();

        int startIndex = (pageIndex - 1) * pageSize;
        int endIndex = pageSize * pageIndex;
        example.setStartIndex(startIndex < 0 ? 0 : startIndex);
        example.setEndIndex(endIndex < 0 ? 20 : endIndex);

        // 查询条件超出实际数据范围，返回第一页数据，设置当前页为1
        if (count < startIndex) {
          page.setPageNo(1);
        }
        dataList = hurlFareService.selectByExample(example);

      }
      page.setDataList(dataList);
      SellerSettlement info = getSimpleSellerSettlement(hurlFareCriteria.getSettlementNo());
      this.getHttpServletRequest().setAttribute("info", info);

      Map map = hurlFareService.hurlFareSumResult(hurlFareCriteria);
      this.getHttpServletRequest().setAttribute("sums", map);
    } catch (Exception e) {
      log.error(e.getMessage());
      return ERROR;
    }


    return SUCCESS;
  }

  /**
   * 明细-差异调整Diff_adj
   * 
   * @return
   * @throws Exception
   */
  public String showDiffAdjPage() throws Exception {
    if (StringUtils.isEmpty(sno)) {
      return ERROR;
    }
    // Date[] dates = null;
    // if (StringUtils.isNotEmpty(period)) {
    // dates = sellerSettlementService.getStartEndDates(period);
    // }

    Page page = getPage();
    if (flag != 0) {

      diffAdjCriteria = new DiffAdjCriteria();
      page.setPageNo(0);
      page.setPageSize(10);
      flag = 0;
    }

    // diffAdjCriteria.setCurrSettmentNo(sno);
    diffAdjCriteria.setCalcSettmentNo(sno);
    // if(null != dates && dates.length > 1){
    // diffAdjCriteria.setStartDate(dates[0]);
    // diffAdjCriteria.setEndDate(dates[1]);
    // }

    int count = diffAdjService.selectDiffAdjSize(diffAdjCriteria);
    page.setRecordCount(count);
    List<DiffAdj> dataList = null;
    if (count > 0) {
      int pageIndex = page.getPageNo();
      if (pageIndex == 0) pageIndex = 1;
      int pageSize = page.getPageSize();

      int startIndex = (pageIndex - 1) * pageSize;
      int endIndex = pageSize * pageIndex;
      diffAdjCriteria.setStartIndex(startIndex < 0 ? 0 : startIndex);
      diffAdjCriteria.setEndIndex(endIndex < 0 ? 20 : endIndex);

      // 查询条件超出实际数据范围，返回第一页数据，设置当前页为1
      if (count < startIndex) {
        page.setPageNo(1);
      }
      dataList = diffAdjService.selectDiffAdjList(diffAdjCriteria);

    }
    page.setDataList(dataList);
    SellerSettlement info = getSimpleSellerSettlement(sno);
    this.getHttpServletRequest().setAttribute("info", info);
    try {
      Map map = diffAdjService.selectSumDiff(diffAdjCriteria);
      this.getHttpServletRequest().setAttribute("sums", map);
    } catch (Exception e) {
      log.error(e.getMessage());
      return ERROR;
    }

    return SUCCESS;
  }

  /**
   * 导出-妥投商品
   * 
   * @return
   * @throws Exception
   */
  public String outputHurlProductData() throws Exception {
    if (StringUtils.isEmpty(sno)) {
      return ERROR;
    }
    String filePath = diffAdjService.getFilePath(sno, 1);

    if (StringUtils.isEmpty(filePath)) {
      filePath = hurlProductService.hurlExport(sno, filePath, hurlProductCriteria);
    }
    this.getHttpServletRequest().setAttribute("filePath", filePath);
    this.getHttpServletRequest().setAttribute("fileName", period);
    return SUCCESS;
  }


  /**
   * 导出-退货
   * 
   * @return
   * @throws Exception
   */
  public String outputSettlementRefundData() throws Exception {
    if (StringUtils.isEmpty(sno)) {
      return ERROR;
    }
    String filePath = diffAdjService.getFilePath(sno, 3);

    if (StringUtils.isEmpty(filePath)) {
      filePath = settlementRefundService.refundExport(sno, filePath, settlementRefundCriteria);
    }

    this.getHttpServletRequest().setAttribute("filePath", filePath);
    this.getHttpServletRequest().setAttribute("fileName", period);
    return SUCCESS;
  }



  /**
   * 导出-妥投订单运费
   * 
   * @return
   * @throws Exception
   */
  public String outputHurlFareData() throws Exception {
    if (StringUtils.isEmpty(sno)) {
      return ERROR;
    }
    String filePath = diffAdjService.getFilePath(sno, 2);

    if (StringUtils.isEmpty(filePath)) {
      filePath = hurlFareService.hurlFareExport(sno, filePath, hurlFareCriteria);
    }

    this.getHttpServletRequest().setAttribute("filePath", filePath);
    this.getHttpServletRequest().setAttribute("fileName", period);

    return SUCCESS;
  }


  /**
   * 导出-差异调整Diff_adj
   * 
   * @return
   * @throws Exception
   */
  public String outputDiffAdjData() throws Exception {
    /*
     * 1.判断是否存在文件 2.生成文件
     */
    if (StringUtils.isEmpty(sno)) {
      return ERROR;
    }
    String filePath = diffAdjService.getFilePath(sno, 4);

    if (StringUtils.isEmpty(filePath)) {

      filePath = diffAdjService.diffAdjExport(sno, filePath, diffAdjCriteria);
    }

    this.getHttpServletRequest().setAttribute("filePath", filePath);
    this.getHttpServletRequest().setAttribute("fileName", period);
    return SUCCESS;
  }


  /**
   * 查询公司名称
   * 
   * @throws Exception
   */
  public void selectSupplierListByName() throws Exception {

    List<SellerSettlement> dataList = sellerSettlementService.selectAllSupplier();

    Map<String, Object> commap = new LinkedHashMap<String, Object>();
    for (SellerSettlement info : dataList) {

      commap.put(info.getSellerId(), info.getSellerName());

    }
    writeJson(commap);

  }

  /**
   * 显示创建结算单页
   * 
   * @return
   * @throws Exception
   */
  public String createSettlementPage() throws Exception {
    List<String> dataList = new ArrayList<String>();

    String nowY = new SimpleDateFormat("yyyy").format(new Date());
    int nowYear = Integer.valueOf(nowY);
    // 最多五年
    for (int i = 0; i < 5; i++) {
      dataList.add(nowYear - i + "");
      if (nowYear - i <= 2015) {
        break;
      }
    }
    if (flag != 0) {
      sellerId = null;
      periodY = null;
      flag = 0;
      supplierName = null;
    }

    // 查询当前年度的存在账期
    List<SellerSettlement> periodList = selectPeriodList(sellerId, periodY);

    this.getHttpServletRequest().setAttribute("dataList", dataList);
    this.getHttpServletRequest().setAttribute("periodList", periodList);
    this.getHttpServletRequest().getSession().setAttribute("ifback", "back");
    return SUCCESS;
  }

  /**
   * 查询年度 没有生成的结算单
   * 
   * @throws ServiceException
   */
  private List<SellerSettlement> selectPeriodList(String sellerId, String period)
      throws ServiceException {

    if (StringUtils.isEmpty(period)) {
      return null;
    }
    List<SellerSettlement> dataList = new ArrayList<SellerSettlement>();

    List<String> resultList = new ArrayList<String>();

    // 取所有的
    Date data = new Date();
    String _nowY = new SimpleDateFormat("yyyy").format(data);
    String _nowM = new SimpleDateFormat("MM").format(data);
    String _nowD = new SimpleDateFormat("dd").format(data);
    int nowY = Integer.valueOf(_nowY);
    int nowM = Integer.valueOf(_nowM);
    int nowD = Integer.valueOf(_nowD);

    int selectY = Integer.valueOf(StringUtils.isEmpty(period) ? _nowY : period);
    if (nowY > selectY) {

      for (int i = 0; i < 12; i++) {
        String flag = "";
        if (i + 1 <= 9) {
          flag = 0 + "";
        }
        resultList.add(period + flag + (i + 1) + "H1");
        resultList.add(period + flag + (i + 1) + "H2");
      }

    } else {
      for (int i = 0; i < nowM; i++) {
        String flag = "";
        if (i + 1 <= 9) {
          flag = 0 + "";
        }
        if (nowD < 16 && nowM - 1 == i) continue;
        resultList.add(_nowY + flag + (i + 1) + "H1");
        if (nowD >= 16 && nowM - 1 == i) continue;
        resultList.add(_nowY + flag + (i + 1) + "H2");
      }
      // if (16 > nowD) {
      // resultList.remove(resultList.size() - 1);
      // }
    }

    // 查询当前年度的存在账期
    List<String> list = null;
    if (StringUtils.isNotEmpty(sellerId) && StringUtils.isNotEmpty(period)) {

      list = sellerSettlementService.selectSupplierByLikePeriod(sellerId, period);
    }

    for (String ss : resultList) {

      SellerSettlement info = new SellerSettlement();
      info.setSettlementPeriod(ss);

      if (list != null && list.contains(ss)) {
        info.setSettlementPeriodFlag("y");
      }

      dataList.add(info);
    }

    return dataList;

  }

  /**
   * 取当前用户ID
   * 
   * @return
   */
  private long getOnlineUserCode() {
    // SysConstants.SYS

    // 当前用户是
    Map session = ActionContext.getContext().getSession();
    SysUser sysuser = (SysUser) session.get("sysUser");
    return sysuser.getUserId();
  }

  private String getOnlineUserName() {
    // SysConstants.SYS

    // 当前用户是
    Map session = ActionContext.getContext().getSession();
    SysUser sysuser = (SysUser) session.get("sysUser");
    return sysuser.getUserName();
  }

  @Override
public void writeJson(Object obj) {
    String json = JSON.toJSONString(obj);
    strWriteJson(json);
  }

  @Override
protected void strWriteJson(String strJson) {
    ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
    PrintWriter pw = null;
    try {
      pw = ServletActionContext.getResponse().getWriter();
      pw.write(strJson);
      pw.flush();
    } catch (IOException e) {
      log.error("json输出异常", e);
    } finally {
      if (pw != null) {
        pw.close();
      }
    }
  }

  /**
   * 导出结算单详情
   * 
   * @return
   * @throws ActionException
   */
  public String exportDetailOrder() throws ActionException {
    if (StringUtils.isEmpty(sno)) {
      log.info("====//PARAM settlmentNo IS NULL,return ");
      return INPUT;
    }
    SysUser sysuser = (SysUser) ActionContext.getContext().getSession().get("sysUser");
    Integer userId = 0;
    if (null != sysuser && null != sysuser.getUserId()) {
      userId = sysuser.getUserId();
    }

    String path = "";
    try {
      SellerSettlementCriteria sCriteria = new SellerSettlementCriteria();
      sCriteria.setSettlementNo(sno);

      SellerSettlement sellerSettlement =
          sellerSettlementService.getSettlementByNo(sCriteria, true);
      // 本账期发起差异调整明细

      DiffAdjExample diffAdjExample = new DiffAdjExample();
      diffAdjExample.createCriteria().andCurrSettmentNoEqualTo(sno);
      diffAdjExample.setOrderByClause("DIFF_ADJ_ID desc");

      List<DiffAdj> diffList = diffAdjService.selectDiffAdjDetail(diffAdjExample);

      path = orderQryService.exportDetailOrder(sno, userId, sellerSettlement, diffList);
      getHttpServletResponse().getWriter().print("{'path':'" + path + "'}");

    } catch (Exception e) {
      log.error("导出结算单详情", e);
    }

    return SUCCESS;
  }

}
