package com.pltfm.app.test;

import net.sf.json.JSONObject;


public class MainTest {
  public static void main(String[] args) {
    JSONObject jsonObj = JSONObject.fromObject(
        "{'orderID':'160405152120222982','ordertype':'53','detailList':[{'orderItemID':477124,'commodityTitle':'康美药业 三七粉 3g/袋*20袋','commoditySKU':'201004358901','commoditySKUDescription':'3g/袋*20袋','commodityUnitIncoming':108,'commodityNumber':1,'commodityUnitPV':5,'commodityPartnerProfit':0,'ProductID':'03027729'}],'orderTotalMoney':158,'totalMoney':15,'totalPv':5,'carryMoney':0,'consignee':'张威','ccpcCode':'山东,青岛,胶州','conAddress':'山东青岛胶州李哥庄镇欧亚新天地小区','conTelphone':'15953271093','paymentDate':'2016-04-05 15:22:01','completeDate':'2016-04-16 05:00:05','number':'A88661866'}");
    jsonObj.put("conZipCode", "");
    System.out.println(jsonObj.get("orderID").toString() + "," + jsonObj.get("ordertype").toString()
        + "," + jsonObj.get("number").toString() + "," + 0 + ","
        + jsonObj.get("consignee").toString() + "," + jsonObj.get("ccpcCode").toString() + ","
        + jsonObj.get("conAddress").toString() + "," + jsonObj.get("conZipCode").toString() + ","
        + jsonObj.get("conTelphone").toString() + "," + jsonObj.get("detailList").toString()
        + jsonObj.get("paymentDate").toString() + jsonObj.get("completeDate").toString());
  }
}
