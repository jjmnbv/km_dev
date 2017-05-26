//package com.pltfm.sys.util;
//
//import java.util.List;
//
//public class Page implements java.io.Serializable {
//
//  private static final long serialVersionUID = 1L;
//  int pageSize = 10; // 每页显示记录数
//  int recordCount = 0; // 共多少条记录
//  int pageCount = 1; // 共几页
//  int pageNo = 1; // 当前第几页
//  private List dataList; // 数据
//
//  public Page() {}
//
//  public int getPageSize() {
//    return pageSize;
//  }
//
//  public void setPageSize(int pageSize) {
//    this.pageSize = pageSize;
//  }
//
//  public int getRecordCount() {
//    return recordCount;
//  }
//
//  public void setRecordCount(int recordCount) {
//    this.recordCount = recordCount;
//  }
//
//  public int getPageCount() {
//    /** 根据总页数，获取总页数 */
//    if (this.recordCount > 0) {
//      if (recordCount % pageSize == 0)
//        this.pageCount = recordCount / pageSize;
//      else
//        this.pageCount = recordCount / pageSize + 1;
//    } else
//      this.pageCount = 1;
//    return this.pageCount;
//  }
//
//  public void setPageCount(int pageCount) {
//    this.pageCount = pageCount;
//  }
//
//  public int getPageNo() {
//    if (pageNo == 0) pageNo = 1;
//    return pageNo;
//  }
//
//  public void setPageNo(int pageNo) {
//    this.pageNo = pageNo;
//  }
//
//  public List getDataList() {
//    return dataList;
//  }
//
//  public void setDataList(List dataList) {
//    this.dataList = dataList;
//  }
//
//}
