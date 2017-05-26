package com.pltfm.app.vobject;

import java.io.Serializable;

/**
 * 树上每个节点存放的数据
 * 
 */

@SuppressWarnings("unused")
public class NodeData implements Serializable {
  private static final long serialVersionUID = 1L;

  private String level;
  private String orderCode;

  public NodeData() {

  }

  public NodeData(String level, String orderCode) {
    this.level = level;
    this.orderCode = orderCode;
  }

  public void output() {

  }
}
