package com.kmzyc.b2b.action.member;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.b2b.model.OrderAlter;
import com.kmzyc.b2b.service.member.ReturnGoodsTraceService;
import com.kmzyc.b2b.vo.TraceInfoVO;
import com.km.framework.action.BaseAction;
import com.pltfm.app.util.OrderAlterDictionaryEnum;

@SuppressWarnings("serial")
@Controller("returnGoodsTracingAction")
@Scope("prototype")
public class CheckreturnGoodsTrace extends BaseAction {
  @Resource(name = "returnGoodsService")
  private ReturnGoodsTraceService returnGoodsTraceService;
  private String orderCode;
  private String returnGoodsCode;
  private List<TraceInfoVO> listVo;
  private OrderAlter oalter;
  private String status;
  private String time;
  private String alterComment;

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getAlterComment() {
    return alterComment;
  }

  public void setAlterComment(String alterComment) {
    this.alterComment = alterComment;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public OrderAlter getOalter() {
    return oalter;
  }

  public void setOalter(OrderAlter oalter) {
    this.oalter = oalter;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public String getReturnGoodsCode() {
    return returnGoodsCode;
  }

  public void setReturnGoodsCode(String returnGoodsCode) {
    this.returnGoodsCode = returnGoodsCode;
  }

  public List<TraceInfoVO> getListVo() {
    return listVo;
  }

  public void setListVo(List<TraceInfoVO> listVo) {
    this.listVo = listVo;
  }

  public String checkReturnTracing() throws Exception {
    DateFormat formatStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    listVo = returnGoodsTraceService.getTraceInfo(returnGoodsCode);
    if (CollectionUtils.isNotEmpty(listVo)) {
      Collections.sort(listVo, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
      Collections.reverse(listVo);
    }

    oalter = returnGoodsTraceService.getOrderAlter(returnGoodsCode);
    status = OrderAlterDictionaryEnum.Propose_Status.getValueByKey(oalter.getProposeStatus());
    if (oalter.getAlterType() == 2) {
      status = status.substring(0, status.length() - 2);
      status += "换货";
    }
    time = formatStr.format(listVo.get(0).getDate());
    alterComment = listVo.get(0).getInfo();
    return "success";
  }

  public String checkWapReturnTracing() throws Exception {
    DateFormat formatStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    listVo = returnGoodsTraceService.getTraceInfo(returnGoodsCode);
    oalter = returnGoodsTraceService.getOrderAlter(returnGoodsCode);
    status = OrderAlterDictionaryEnum.Propose_Status.getValueByKey(oalter.getProposeStatus());
    if (oalter.getAlterType() == 2) {
      if (oalter.getProposeStatus() == 2) {
        status = status.substring(0, status.length() - 2);
        status += "换货";
      }
    }
    time = formatStr.format(listVo.get(0).getDate());
    alterComment = listVo.get(0).getInfo();
    return "success";
  }
}
