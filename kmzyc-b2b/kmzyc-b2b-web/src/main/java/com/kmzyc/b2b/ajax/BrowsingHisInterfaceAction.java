package com.kmzyc.b2b.ajax;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.model.BrowsingHis;
import com.kmzyc.b2b.service.BrowsingHisService;
import com.kmzyc.b2b.service.ProductPriceService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.util.StringUtil;

@SuppressWarnings({"serial", "unchecked"})
@Scope("prototype")
@Controller
public class BrowsingHisInterfaceAction extends BaseAction {
  // private static Logger logger = Logger.getLogger(BrowsingHisInterfaceAction.class);
  private static Logger logger = LoggerFactory.getLogger(BrowsingHisInterfaceAction.class);
  // 返回至页面的对象
  private ReturnResult returnResult;

  @Resource(name = "browsingHisServiceImpl")
  private BrowsingHisService browsingHisService;

  @Resource(name = "productPriceService")
  private ProductPriceService productPriceService;

  /**
   * 添加用户浏览商品记录
   *
   * @return
   */
  public String insertBrowsingHis() {
    // 商品编号
    String contentCode = getRequest().getParameter("contentCode");
    // 用户Id
    Long userId = (Long) getSession().getAttribute(Constants.SESSION_USER_ID);
    if (userId == null || StringUtil.isEmpty(contentCode)) {

      returnResult = new ReturnResult(InterfaceResultCode.NOT_LOGIN, "未登录", null);
      return SUCCESS;
    }
    try {
      BrowsingHis browsingHis = new BrowsingHis();
      browsingHis.setContentCode(contentCode);
      browsingHis.setLoginId(userId.intValue());
      // 浏览记录类型1:商品 2:商户
      browsingHis.setBrowsingType(1);
      browsingHisService.addBrowsingHis(browsingHis);
      returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", null);
    } catch (Exception e) {
      logger.error("新增浏览发生异常", e);
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
    }
    return SUCCESS;
  }

  /**
   * 根据用户id查询对应的浏览记录 2175
   * 
   * @return
   */
  public String querybrowsingHisById() {
    // 获取缓存用户
    Long userId = (Long) getSession().getAttribute(Constants.SESSION_USER_ID);
    if (userId == null) {
      returnResult = new ReturnResult(InterfaceResultCode.NOT_LOGIN, "未登录", null);
      return SUCCESS;
    }
    // 获取参数显示个数
    String rowNum = getRequest().getParameter("rowNum");
    // 判断参数是否为空处理
    Integer number = StringUtils.isBlank(rowNum) ? null : Integer.valueOf(rowNum);
    // 绑定sql参数
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("loginId", userId.intValue());
    map.put("rowNum", number);

    try {
      List<BrowsingHis> browsingHisList = browsingHisService.queryBrowsingHis(map);
      // 获取商品促销信息和价格
      productPriceService.getPriceBatch(browsingHisList);
      returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", browsingHisList);
    } catch (Exception e) {
      logger.error("浏览记录加载出错" + e.getMessage(), e);
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
    }
    return SUCCESS;
  }

  /**
   * 清空删除浏览记录
   * 
   * @return
   */
  public String deleteBrowsingHisById() {
    // 获取缓存用户
    Long userId = (Long) getSession().getAttribute(Constants.SESSION_USER_ID);
    if (userId == null) {
      returnResult = new ReturnResult(InterfaceResultCode.NOT_LOGIN, "未登录", null);
      return SUCCESS;
    }
    try {
      // 根据登录id清空浏览记录
      browsingHisService.deleteBrowingHisById(userId.intValue());
      returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", null);
    } catch (Exception e) {
      logger.error("清空浏览记录失败" + e.getMessage(), e);
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
    }
    return SUCCESS;
  }

  /**
   * 根据浏览ID删除对应的浏览记录
   * 
   * @return
   */
  public String deleteBrowsingHisByBrowsingId() {
    // 获取缓存用户
    Long userId = (Long) getSession().getAttribute(Constants.SESSION_USER_ID);
    if (userId == null) {
      returnResult = new ReturnResult(InterfaceResultCode.NOT_LOGIN, "未登录", null);
      return SUCCESS;
    }
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("loginId", userId);
    String browsingId = getRequest().getParameter("browsingId");
    map.put("browsingId", browsingId);
    try {
      // 根据登录id清空浏览记录
      browsingHisService.deleteBrowingHisByBrowingId(map);
      returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", null);
    } catch (Exception e) {
      logger.error("清空浏览记录失败" + e.getMessage(), e);
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
    }
    return SUCCESS;
  }

  /**
   * wap根据用户id查询对应的浏览记录 2175
   * 
   * @return
   */
  public String wapQuerybrowsingHisById() {
    // 获取缓存用户
    Long userId = (Long) getSession().getAttribute(Constants.SESSION_USER_ID);
    Map<String, Object> mapReturn = new HashMap<String, Object>();
    if (userId == null) {
      returnResult = new ReturnResult(InterfaceResultCode.NOT_LOGIN, "未登录", null);
      return SUCCESS;
    }
    // 获取是第几页
    String startNumber = getRequest().getParameter("pageNumber");

    Map<String, Object> map = new HashMap<String, Object>();
    map.put("loginId", userId.intValue());
    map.put("startNum", (Integer.parseInt(startNumber.toString()) - 1) * 10 + 1); // 起始页
    map.put("rowNum", Integer.parseInt(startNumber.toString()) * 10); // 终结页

    try {
      List<BrowsingHis> browsingHisList = browsingHisService.queryBrowsingHis(map);

      if (browsingHisList != null && browsingHisList.size() > 0) { // 获取类目属性
        for (int i = 0; i < browsingHisList.size(); i++) {
          String categoryAttrValueStr = "";
          List<Map<String, String>> categoryAttrValueList =
              browsingHisList.get(i).getCategoryAttrValueList();
          if (categoryAttrValueList != null && categoryAttrValueList.size() > 0) {
            for (int j = 0; j < categoryAttrValueList.size(); j++) {
              categoryAttrValueStr += categoryAttrValueList.get(j).get("categoryAttrValue") + "  ";
            }
//            categoryAttrValueStr.subSequence(0, categoryAttrValueStr.length() - 1);
          }
          browsingHisList.get(i).setCategoryAttrValueStr(categoryAttrValueStr);
        }
      }
      // 查询浏览商品的个数
      Long browsingCount = browsingHisService.queryBrowsingHisCount(map);
      // 获取商品促销信息和价格
      productPriceService.getPriceBatch(browsingHisList);
      mapReturn.put("browsingHisList", browsingHisList);
      // 计算总页数
      if (browsingCount != 0) {
        browsingCount = browsingCount / 10 + 1;
      }
      mapReturn.put("browsTotal", browsingCount);
      returnResult = new ReturnResult(InterfaceResultCode.SUCCESS, "成功", mapReturn);
    } catch (Exception e) {
      logger.error("浏览记录加载出错" + e.getMessage(), e);
      returnResult = new ReturnResult(InterfaceResultCode.FAILED, "失败", null);
    }
    return SUCCESS;
  }

  public ReturnResult getReturnResult() {
    return returnResult;
  }

  public void setReturnResult(ReturnResult returnResult) {
    this.returnResult = returnResult;
  }
}
