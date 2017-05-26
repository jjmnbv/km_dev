package com.pltfm.app.vobject;

import java.io.Serializable;

/**
 * 微商用户红包 条件查询
 * 
 * @author xys
 *
 */
public class SpreaderRPInfoVO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * 用户登录id
   */
  private String spreaderLoginId;
  /**
   * 用户已获得的红包个数
   */
  private String spreaderRPCount;

  public String getSpreaderLoginId() {
    return spreaderLoginId;
  }

  public void setSpreaderLoginId(String spreaderLoginId) {
    this.spreaderLoginId = spreaderLoginId;
  }

  public String getSpreaderRPCount() {
    return spreaderRPCount;
  }

  public void setSpreaderRPCount(String spreaderRPCount) {
    this.spreaderRPCount = spreaderRPCount;
  }



}
