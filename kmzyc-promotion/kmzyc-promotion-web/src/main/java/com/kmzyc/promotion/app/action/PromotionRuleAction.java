package com.kmzyc.promotion.app.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.script.ScriptException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.promotion.app.enums.PromotionTypeEnums;
import com.kmzyc.promotion.app.service.PromotionRuleDataService;
import com.kmzyc.promotion.app.service.PromotionRuleService;
import com.kmzyc.promotion.app.service.PromotionTypeService;
import com.kmzyc.promotion.app.util.JavaScriptExpress;
import com.kmzyc.promotion.app.vobject.Message;
import com.kmzyc.promotion.app.vobject.PromotionRule;
import com.kmzyc.promotion.app.vobject.PromotionRuleData;
import com.kmzyc.promotion.app.vobject.PromotionRuleDataExample;
import com.kmzyc.promotion.app.vobject.PromotionRuleExample;

@SuppressWarnings({"unchecked", "unused"})
public class PromotionRuleAction extends BaseAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private PromotionRule promotionRule;
  private PromotionRuleData promotionRuleData;
  private PromotionRuleExample promotionRuleExample;

  @Resource
  private PromotionRuleService promotionRuleService;
  @Resource
  private PromotionTypeService promotionTypeService;
  @Resource
  private PromotionRuleDataService promotionRuleDataService;
  // 日志记录
  protected Logger logger = LoggerFactory.getLogger(PromotionRuleAction.class);

  /**
   * 查询活动规则
   * 
   * @return
   */
  public String queryPromotionRuleList() {
    page = this.getPage();
    page = promotionRuleService.selectPageByVo(page, promotionRule);
    setBaseData();
    return SUCCESS;
  }

  public String gotoPromotionRuleAddOrUpdate() {
    setBaseData();
    if (promotionRule != null && promotionRule.getPromotionRuleId() != null) {
      promotionRule = promotionRuleService.getPromotionRuleById(promotionRule.getPromotionRuleId());
      return SUCCESS;
    }
    return "add";
  }

  public String promotionRuleSave() {
    JSONObject json = checkPromotionRuleToJson();
    if (json.getInteger("code") == 0) {
      // promotionRuleData = ;
      promotionRuleService.savePromotionRule(promotionRule, initPromotionRuleData());
      return SUCCESS;
    }
    this.strWriteJson(json.toJSONString());
    return null;
  }

  private List<PromotionRuleData> initPromotionRuleData() {
    List<PromotionRuleData> list = new ArrayList<PromotionRuleData>();

    if (promotionRuleData == null) {
      promotionRuleData = new PromotionRuleData();
    }
    if (promotionRule.getPromotionTypeId().equals(PromotionTypeEnums.SALE.getValue())) {
      String salePrice = this.getRequest().getParameter("salePrice");// 特价
      promotionRuleData.setPrizeData(salePrice);
      list.add(promotionRuleData);
    } else if (promotionRule.getPromotionTypeId().equals(PromotionTypeEnums.DISCOUNT.getValue())) {
      String discount = this.getRequest().getParameter("discount");// 折扣
      promotionRuleData.setPrizeData(discount);
      list.add(promotionRuleData);
    } else {
      for (int i = 1;; i++) {
        String meetDataStr = this.getRequest().getParameter("meetData" + i);// 满足指定金额
        if (meetDataStr == null || meetDataStr.isEmpty()) {
          break;
        }
        String pramName = "prizeData" + i;
        if (!promotionRule.getPromotionTypeId().equals(PromotionTypeEnums.REDUCTION.getValue())) {
          pramName = "prizeData" + i + "prizeData" + i;
        }
        String prizeData = this.getRequest().getParameter(pramName);// 优惠券
        if (prizeData == null || prizeData.isEmpty()) {
          break;
        }
        promotionRuleData = new PromotionRuleData();
        promotionRuleData.setMeetData(new BigDecimal(meetDataStr));
        promotionRuleData.setMeetDataType(1);
        promotionRuleData.setPrizeData(prizeData);
        list.add(promotionRuleData);
      }
    }

    return list;
  }

  public void deletePromotionRule() {
    JSONObject json = new JSONObject();
    String ids = this.getRequest().getParameter("promotionRuleIds");
    if (ids.isEmpty()) {
      json.put("code", 1);
      json.put("title", "要删除对象的主键为空！");
    } else {
      String[] idArray = ids.split("-");
      List<PromotionRule> list = new ArrayList<PromotionRule>();
      for (String idStr : idArray) {
        if (!idStr.isEmpty()) {
          long id = Long.valueOf(idStr);
          PromotionRule p = new PromotionRule();
          p.setPromotionRuleId(id);
          list.add(p);
        }
      }
      String title = deletePromotionRule(list);
      json.put("code", 0);
      json.put("title", title);

    }
    this.strWriteJson(json.toJSONString());
  }

  private String deletePromotionRule(List<PromotionRule> list) {
    String title = promotionRuleService.deletePromotionRule(list);
    if (title.isEmpty()) {
      title = "删除成功！";
    } else {
      title = "规则：" + title.substring(0, title.length() - 1) + "已在活动中使用,删除失败";
    }
    return title;
  }

  private JSONObject checkPromotionRuleToJson() {
    JSONObject json = new JSONObject();
    if (promotionRule == null) {
      json.put("code", 10);
      json.put("title", "对象为空！");
      return json;
    }
    if (promotionRule.getPromotionRuleRuleName() == null
        || promotionRule.getPromotionRuleRuleName().isEmpty()) {
      json.put("code", 1);
      json.put("title", "名称为空！");
      return json;
    }
    if (!promotionRuleService.checkPromotionRule(promotionRule)) {
      json.put("code", 2);
      json.put("title", "名称重复！");
      return json;
    }

    /**
     * if(promotionRule.getPromotionTypeId()!=PromotionTypeEnums.SALE. getValue
     * ()&&(promotionRule.getPromotionRuleExpression()==null|| promotionRule
     * .getPromotionRuleExpression().isEmpty())){ json.put("code", 3); json.put("title",
     * "表达式不能为空！"); return json; }
     * 
     * if(promotionRule.getPromotionTypeId()==PromotionTypeEnums.COUPON.
     * getValue()&&promotionRule.getPromotionRuleCouponId()==null){ json.put("code", 4);
     * json.put("title", "优惠券不能为空！"); return json; } if(
     * !this.checkExpression(promotionRule.getPromotionRuleExpression())){ String title = "表达式不合法！"
     * +
     * "<a href='/promotion/gotoPromotionRuleAddOrUpdate.action?promotionRule.promotionRuleId=thisId'>点击返回</a>"
     * ; json.put("code", 5); json.put("title",title.replace("thisId",
     * promotionRule.getPromotionRuleId()==null?"": promotionRule.getPromotionRuleId().toString())
     * ); return json; }
     */
    String[] data = this.getRequest().getParameterValues("maimanjine");
    json.put("code", 0);
    json.put("title", "有效");
    return json;
  }

  private boolean checkExpression(String promotionRuleExpression) {
    // 'price':'商品单价','total':'订单总价','count':'订单总数量','freight':'运费'
    Map<String, String> paramsMap = new HashMap<String, String>();
    paramsMap.put("price", "10.3");
    paramsMap.put("total", "120.3");
    paramsMap.put("count", "4");
    paramsMap.put("freight", "12.0");
    JavaScriptExpress.setParamsMap(paramsMap);
    try {
      JavaScriptExpress.getMathValue(promotionRuleExpression);
      return true;
    } catch (ScriptException e) {
      // Auto-generated catch block
      logger.error("checkExpression方法异常：", e);
      return false;
    }
  }

  private void setBaseData() {
    this.getRequest().setAttribute("promotionTypeMap", promotionTypeService.getPromotionTypeMap());
  }

  public void checkPromotionRuleName() {
    JSONObject json = new JSONObject();
    if (!promotionRuleService.checkPromotionRule(promotionRule)) {
      json.put("code", 1);
      json.put("title", "名称重复！");
    } else {
      json.put("code", 0);
      json.put("title", "名称可用！");
    }
    this.strWriteJson(json.toJSONString());
  }

  /**
   * 修改更新规则优先级序号
   */
  public void updatePromotionRulePriority() {
    JSONObject json = new JSONObject();
    if (promotionRuleService.checkPromotionRule(promotionRule)) {
      promotionRuleService.saveOrUpdatePromotionRule(promotionRule, null);
      json.put("code", 0);
      json.put("title", "修改成功");
    } else {
      json.put("code", 1);
      json.put("title", "序号不能重复");
    }
    this.strWriteJson(json.toJSONString());
  }

  public void getRule() {
    JSONObject json = new JSONObject();
    try {
      PromotionRuleDataExample example = new PromotionRuleDataExample();
      example.createCriteria().andPromotionRuleIdEqualTo(promotionRule.getPromotionRuleId());
      List<PromotionRuleData> list = promotionRuleDataService.selectByExample(example);
      PromotionRuleData data = list.get(0);
      String prize = data.getPrizeData();
      Long id = Long.valueOf(prize);
      json.put("code", 0);
    } catch (Exception e) {
      logger.error("方法异常：", e);
      json.put("code", 1);
    }
    this.strWriteJson(json.toJSONString());
  }

  Message message;

  public Message getMessage() {
    return message;
  }

  public void setMessage(Message message) {
    this.message = message;
  }

  public PromotionRule getPromotionRule() {
    return promotionRule;
  }

  public void setPromotionRule(PromotionRule promotionRule) {
    this.promotionRule = promotionRule;
  }

  public PromotionRuleExample getPromotionRuleExample() {
    return promotionRuleExample;
  }

  public void setPromotionRuleExample(PromotionRuleExample promotionRuleExample) {
    this.promotionRuleExample = promotionRuleExample;
  }
}
