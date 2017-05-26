package com.pltfm.app.action;

import java.sql.SQLException;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.DetectionResultInfoService;
import com.pltfm.app.vobject.DetectionResultInfo;

public class DetectionResultAction extends ActionSupport implements ModelDriven {
  private DetectionResultInfoService detectionResultInfoService;
  private DetectionResultInfo detectionResultInfo;
  private int nHealthYgenericId;// 健康类id
  /** 多条删除id集合 **/
  private List<String> nDetectionResultIds;

  public Object getModel() {
    // TODO Auto-generated method stub
    detectionResultInfo = new DetectionResultInfo();
    return detectionResultInfo;
  }

  public DetectionResultInfoService getDetectionResultInfoService() {
    return detectionResultInfoService;
  }

  public void setDetectionResultInfoService(DetectionResultInfoService detectionResultInfoService) {
    this.detectionResultInfoService = detectionResultInfoService;
  }

  public DetectionResultInfo getDetectionResultInfo() {
    return detectionResultInfo;
  }

  public void setDetectionResultInfo(DetectionResultInfo detectionResultInfo) {
    this.detectionResultInfo = detectionResultInfo;
  }

  public int getnHealthYgenericId() {
    return nHealthYgenericId;
  }

  public void setnHealthYgenericId(int nHealthYgenericId) {
    this.nHealthYgenericId = nHealthYgenericId;
  }

  public List<String> getnDetectionResultIds() {
    return nDetectionResultIds;
  }

  public void setnDetectionResultIds(List<String> nDetectionResultIds) {
    this.nDetectionResultIds = nDetectionResultIds;
  }

  public String add() {
    try {
      detectionResultInfo.setnDetectionResultId(11);
      detectionResultInfo.setnHealthYgenericId(22);
      detectionResultInfo.setDetectionName("AAAA");
      detectionResultInfo.setDoctor("dddddd");
      detectionResultInfoService.addDetectionResult(detectionResultInfo);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return SUCCESS;
  }

  public String update() {
    try {
      detectionResultInfo.setnDetectionResultId(11);
      detectionResultInfo.setDetectionName("BBBBB");
      detectionResultInfo.setDoctor("CCCCC");
      detectionResultInfoService.udpateDetectionResult(detectionResultInfo);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return SUCCESS;
  }

  public String date() {
    try {
      detectionResultInfoService.deleteDetectionResult(nDetectionResultIds);
      return "delSuccess";
    } catch (Exception e) {
      e.printStackTrace();
      return INPUT;
    }
  }

  public String getHealthYgenericId() {
    try {
      nHealthYgenericId = 11;
      detectionResultInfoService.getHealthYgenericId(nHealthYgenericId);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return SUCCESS;
  }
}
