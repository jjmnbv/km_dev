package com.pltfm.app.action;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.pltfm.app.entities.KeyValueDemo;
import com.pltfm.app.util.OrderDictionaryEnum;

public class TestOrderAssessAction extends ActionSupport {

  private static final long serialVersionUID = 1L;
  private Collection<KeyValueDemo> detailList = new ArrayList<KeyValueDemo>();

  public void addValueTOList() {
    for (OrderDictionaryEnum.OrderAssessDetail o : OrderDictionaryEnum.OrderAssessDetail.values()) {

      detailList.add(new KeyValueDemo(o.getKey(), o.getValue(), o.getIndex(), o.getCode()));
    }
  }

  @Override
  public String execute() throws Exception {

    HttpServletRequest request = ServletActionContext.getRequest();
    String assessInfoId = request.getParameter("assessInfoId");
    request.setAttribute("assessInfoId", assessInfoId);

    return SUCCESS;
  }

  public String testAssessAdd() {

    HttpServletRequest request = ServletActionContext.getRequest();
    addValueTOList();
    request.setAttribute("detailList", detailList);
    return SUCCESS;
  }

}
