package com.pltfm.app.action;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.CouponGrant;
import com.kmzyc.promotion.remote.service.CouponRemoteService;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.dataobject.UserInfoDO;
import com.pltfm.app.service.CouponService;
import com.pltfm.app.service.EmailInfoService;
import com.pltfm.app.service.LoginInfoService;
import com.pltfm.app.util.StringUtils;
import com.pltfm.app.vobject.Coupons;
import com.pltfm.app.vobject.LoginInfo;


@SuppressWarnings("unchecked")
@Component(value = "acctVouchersAction")
@Scope(value = "prototype")
public class AcctVouchersAction extends BaseAction implements ModelDriven {

  private static Logger logger = LoggerFactory.getLogger(AcctVouchersAction.class);
  /**
   * 
   */
  private static final long serialVersionUID = 9130921076007053752L;
  private Coupons coupons;

  private List<Coupons> couponsList;
  @Resource(name = "loginInfoService")
  private LoginInfoService loginInfoService;

  @Resource(name = "emailInfoService")
  private EmailInfoService emailInfoService;


  @Resource(name = "couponService")
  private CouponService couponService;

  @Autowired
  private CouponRemoteService couponRemoteService;

  /** 公共页面参数 **/
  private Integer showType;
  private Integer loginId;

  private Page page;
  
/**
   * @throws Exception 消费电子券信息
   * 
   * @param @return String @exception
   */
  public String showVouchersList() {
    try {
      if (page == null) {
        page = new Page();
      }
      LoginInfo loginInfo= loginInfoService.getLoginId(coupons.getCustmoId());
      coupons.setLoginAccount(loginInfo.getLoginAccount());
      
      page.setRecordCount(couponService.searchPageByCoupons(coupons));
      coupons.setSkip(page.getStartIndex());
      coupons.setMax(page.getStartIndex() + page.getPageSize());
      page.setDataList(couponService.selectPageByCoupons(coupons));
    } catch (Exception e) {
      logger.error("获取优惠券出错：" + e);
    }
    return "voucherslist";
  }

  public String showVouchersLists() throws Exception {

    try {
      if (page == null) {
        page = new Page();
      }
      List<Integer> listCustid = new ArrayList<Integer>();
      // System.out.println("---------------------=================-----------------:"+listCustid.size());
      listCustid.clear();
      if (showType != null) {

        LoginInfo loginfos = new LoginInfo();
        loginfos = loginInfoService.getLoginId(loginId);
        String customerNames = StringUtils.isStringNull(loginfos.getLoginAccount());
        coupons.setCustName(customerNames);

      }
      if (coupons.getCustName() != null && !coupons.getCustName().equals("")) {
        UserInfoDO userInfoDO = new UserInfoDO();
        userInfoDO.setLoginAccount(coupons.getCustName());
        List<LoginInfo> listLogininfo = loginInfoService.getLoginInfo(userInfoDO);
        if (listLogininfo.size() > 0) {
          for (LoginInfo lginfo : listLogininfo) {
            listCustid.add(lginfo.getN_LoginId());

          }
        } else {
          listCustid.add(-1);
        }
      }
      if (coupons.getCustmoId() != null) {
        listCustid.add(coupons.getCustmoId());
      }

      List<CouponGrant> list = couponRemoteService.SelectCouponGrantList(listCustid);
      int count = list.size();

      List<Coupons> listall = new ArrayList<Coupons>();
      String customerName = "";
      listall.clear();
      if (count > 0) {
        page.setRecordCount(count);
        int sIndex = page.getStartIndex();
        int maxIndex = page.getStartIndex() + page.getPageSize();

        for (int i = 0; i < count; i++)
          if (count < maxIndex) {
            maxIndex = count;
          }
        for (int i = sIndex; i < maxIndex; i++) {
          int custid = list.get(i).getCustomId().intValue();
          LoginInfo loginfos = loginInfoService.getLoginId(custid);
          if (loginfos == null) {
            customerName = "";
          } else {
            customerName = StringUtils.isStringNull(loginfos.getLoginAccount());
          }
          Coupons cp = new Coupons();
          cp.setCustName(customerName);
          cp.setCouponName(list.get(i).getCoupon().get(0).getCouponName());
          cp.setCouponGivetypeId(list.get(i).getCoupon().get(0).getCouponGivetypeId());
          cp.setCouponMoney(list.get(i).getCoupon().get(0).getCouponMoney());
          cp.setStartTime(list.get(i).getCoupon().get(0).getStarttime());
          cp.setEndTime(list.get(i).getCoupon().get(0).getEndtime());
          cp.setCouponStatus(list.get(i).getCouponStatus());
          listall.add(cp);
        }
      }


      /*
       * if (showType != null) {
       * 
       * LoginInfo loginfos = new LoginInfo();
       * 
       * // int v = Int32.Parse(coupons.getCustmoId()); // Int.Parse(); loginfos =
       * loginInfoService.getLoginId((new Integer(coupons.getCustmoId().toString()))); customerName
       * = StringUtils.isStringNull(loginfos.getLoginAccount()); coupons.setCustName(customerName);
       * } System.out.println("======"+listall.size());
       */
      page.setDataList(listall);

    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return "voucherslist";
  }

  @Override
public Page getPage() {
    return page;
  }

  @Override
public void setPage(Page page) {
    this.page = page;
  }


  public Integer getLoginId() {
    return loginId;
  }

  public void setLoginId(Integer loginId) {
    this.loginId = loginId;
  }

  @Override
  public Object getModel() {
    coupons = new Coupons();
    return coupons;
  }

  public List<Coupons> getCouponsList() {
    return couponsList;
  }

  public void setCouponsList(List<Coupons> couponsList) {
    this.couponsList = couponsList;
  }

  public Coupons getCoupons() {
    return coupons;
  }

  public void setCoupons(Coupons coupons) {
    this.coupons = coupons;
  }

  public Integer getShowType() {
    return showType;
  }

  public void setShowType(Integer showType) {
    this.showType = showType;
  }

  public EmailInfoService getEmailInfoService() {
    return emailInfoService;
  }

  public void setEmailInfoService(EmailInfoService emailInfoService) {
    this.emailInfoService = emailInfoService;
  }

}
