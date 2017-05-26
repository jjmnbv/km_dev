package com.kmzyc.b2b.model;

import java.io.Serializable;

public class AppVersion implements Serializable {
  /**
	 * 
	 */
  private static final long serialVersionUID = 5282962084487700029L;
  private Long appID;
  private String version;
  private String remark;
  private String AppURL;

  public Long getAppID() {
    return appID;
  }

  public void setAppID(Long appID) {
    this.appID = appID;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getAppURL() {
    return AppURL;
  }

  public void setAppURL(String appURL) {
    AppURL = appURL;
  }
}
