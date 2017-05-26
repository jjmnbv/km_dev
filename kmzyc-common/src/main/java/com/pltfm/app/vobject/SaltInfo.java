package com.pltfm.app.vobject;

import java.io.Serializable;

public class SaltInfo implements Serializable{

  private static final long serialVersionUID = 1L;

  private Long loginId;
  private String loginSalt;
  private String paySalt;

  public Long getLoginId() {
    return loginId;
  }

  public void setLoginId(Long loginId) {
    this.loginId = loginId;
  }

  public String getLoginSalt() {
    return loginSalt;
  }

  public void setLoginSalt(String loginSalt) {
    this.loginSalt = loginSalt;
  }

  public String getPaySalt() {
    return paySalt;
  }

  public void setPaySalt(String paySalt) {
    this.paySalt = paySalt;
  }

  @Override
  public String toString() {
    return "SaltInfo [loginId=" + loginId + ", loginsalt=" + loginSalt + ", paysalt="
        + paySalt+"]";
  }

}
