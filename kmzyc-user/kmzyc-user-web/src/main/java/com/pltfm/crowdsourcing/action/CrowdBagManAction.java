package com.pltfm.crowdsourcing.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.commons.general.exception.KmServiceException;
import com.km.commons.general.model.ResultData;
import com.km.crowdsourcing.model.Bagman;
import com.km.crowdsourcing.service.BagmanService;
import com.kmzyc.commons.page.Page;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.action.BaseAction;
import com.pltfm.app.util.MD5;
import com.pltfm.crowdsourcing.enums.CrowdSorcingEnum;
import com.pltfm.sys.model.SysUser;

/**
 * 
 * @ClassName: CrowdBagManAction
 * @Description: 众包机构业务员(地推人员)管理
 * @author weijl
 * @date 2016年3月15日 上午11:31:17
 * @version 1.0
 */
@Controller("crowdBagManAction")
@Scope(value = "prototype")
public class CrowdBagManAction extends BaseAction implements ModelDriven {

  private static final long serialVersionUID = 4248109542989699382L;

  private static Logger logger = LoggerFactory.getLogger(CrowdBagManAction.class);

  private Bagman bagman;

  private ResultData ResultData;

  @Resource
  private BagmanService bagmanService;


  public ResultData getMessage() {
    return ResultData;
  }

  public void setResultData(ResultData ResultData) {
    this.ResultData = ResultData;
  }

  public Bagman getBagman() {
    return bagman;
  }

  public void setBagman(Bagman bagman) {
    this.bagman = bagman;
  }

  @Override
  public Object getModel() {
    bagman = new Bagman();
    return bagman;
  }

  /**
   * @Title: listBagMans @Description: 跳转业务员管理页 @return String @throws
   */
  public String listBagMans() {
    if (page == null) {
      page = new Page();
    }
    try {
      int bagmanCount = bagmanService.countBagMans(bagman);
      page.setRecordCount(bagmanCount);
      bagman.setStartIndex((page.getPageNo() - 1) * page.getPageSize() + 1);
      bagman.setEndIndex(page.getPageNo() * page.getPageSize());
      List bagmans = null;
      if (bagmanCount > 0) {
        bagmans = bagmanService.listBagMans(bagman);
      }
      page.setDataList(bagmans);
    } catch (Exception e) {
      logger.error("跳转业务员管理页异常", e);
    }
    return SUCCESS;
  }

  /**
   * @Title: saveBagMan @Description: 保存业务员信息 @param @return void @throws
   */
  public void saveBagMan() {
    SysUser sysuser = (SysUser) httpServletRequest.getSession().getAttribute("sysUser");
    if (null == bagman || StringUtils.isEmpty(bagman.getName())
        || StringUtils.isEmpty(bagman.getMobile())) {
      ResultData = new ResultData();
      ResultData.setCode("0");
      ResultData.setMessage("业务员信息为空，不予保存！");
    }else{
    ResultData = new ResultData();
    bagman.setPassword(MD5.md5crypt(bagman.getMobile()));
    bagman.setCreateBy(sysuser.getUserName());
    bagman.setStatus((short) CrowdSorcingEnum.bag_man_status_valid.getKey());
    bagman.setCreateDate(new Date());
    try {
      List<Map<String, Object>> bmans = bagmanService.isBmanExists(bagman);
      if (CollectionUtils.isNotEmpty(bmans)) {
        Map<String, Object> map = bmans.get(0);
        if (!((BigDecimal) map.get("NAMECOUNT")).equals(BigDecimal.ZERO)) {
          ResultData.setCode("2");
          ResultData.setMessage("用户名已注册，请重新输入！");
        } else if (!((BigDecimal) map.get("MOBILECOUNT")).equals(BigDecimal.ZERO)) {
          ResultData.setCode("3");
          ResultData.setMessage("手机号码已注册，请重新输入！");
        } else
          ResultData = bagmanService.saveBagMan(bagman);
      }
    } catch (Exception e) {
      logger.error("", e);
      ResultData.setCode("-1");
      ResultData.setMessage("保存业务员信息异常,请联系管理员!");
    }
    }
    this.writeJson(ResultData);
  }

  /**
   * @Title: isBmanExists @Description: 业务员信息是否已存在：包括姓名、手机号 @param @return void @throws
   */
  public void isBmanExists() {
    ResultData = new ResultData();
    try {
      List<Map<String, Object>> bmans = bagmanService.isBmanExists(bagman);
      if (CollectionUtils.isNotEmpty(bmans)) {
        Map<String, Object> map = bmans.get(0);
        if (!((BigDecimal) map.get("NAMECOUNT")).equals(BigDecimal.ZERO)) {
          ResultData.setCode("2");
          ResultData.setMessage("用户名已存在，请重新输入！");
        } else if (!((BigDecimal) map.get("MOBILECOUNT")).equals(BigDecimal.ZERO)) {
          ResultData.setCode("3");
          ResultData.setMessage("手机号码已存在，请重新输入！");
        } else {
          ResultData.setCode("1");
          ResultData.setMessage("信息可存");
        }
      }
    } catch (Exception e) {
      logger.error("", e);
      ResultData.setCode("-1");
      ResultData.setMessage("查询业务员信息是否已存在异常!");
    }
    this.writeJson(ResultData);
  }

  /**
   * @Title: editBagman @Description: 跳转业务员信息编辑页 @param @return @return String @throws
   */
  public String editBagman() {
    String bagid = this.getRequest().getParameter("bagid");
    String tag = this.getRequest().getParameter("tag");
    if (StringUtils.isEmpty(bagid) || StringUtils.isEmpty(tag)) return ERROR;
    this.getRequest().setAttribute("tag", tag);
    try {
      bagman = bagmanService.selectByPrimaryKey(Long.parseLong(bagid));
    } catch (KmServiceException e) {
      logger.error("跳转编辑页异常：", e);
    }
    return SUCCESS;
  }

  /**
   * @Title: updateBagMan @Description: 更新业务员信息 @param @return void @throws
   */
  public void updateBagMan() {
    ResultData = new ResultData();
    if (bagman == null || bagman.getId() == null) {
      ResultData.setCode("-1");
      ResultData.setMessage("修改业务员信息异常！");
    } else {
      try {
        // 当用户修改手机号码时，要改其密码为手机号MD5加密后字符串
        if (StringUtils.isNotEmpty(bagman.getMobile()))
          bagman.setPassword(MD5.md5crypt(bagman.getMobile()));
        int result = bagmanService.updateByPrimaryKeySelective(bagman);
        if (result >= 0) {
          ResultData.setCode("1");
          ResultData.setMessage("保存成功！");
        }
      } catch (Exception e) {
        logger.error("修改业务员信息异常：", e);
        ResultData.setCode("-1");
        ResultData.setMessage("修改业务员信息异常！");
      }
    }
    this.writeJson(ResultData);
  }
}
