package com.pltfm.app.entities;

import java.io.Serializable;

public class OrderAlterPhoto implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 8907468974089067857L;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.ORDER_ALTER_PHOTO.PHOTO_ID
   * 
   * @ibatorgenerated Fri Sep 27 14:27:36 CST 2013
   */
  private Long photoId;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.ORDER_ALTER_PHOTO.BATCH_NO
   * 
   * @ibatorgenerated Fri Sep 27 14:27:36 CST 2013
   */
  private String batchNo;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.ORDER_ALTER_PHOTO.URL
   * 
   * @ibatorgenerated Fri Sep 27 14:27:36 CST 2013
   */
  private String url;

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.ORDER_ALTER_PHOTO.PHOTO_ID
   * 
   * @return the value of KMORDER.ORDER_ALTER_PHOTO.PHOTO_ID
   * @ibatorgenerated Fri Sep 27 14:27:36 CST 2013
   */
  public Long getPhotoId() {
    return photoId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.ORDER_ALTER_PHOTO.PHOTO_ID
   * 
   * @param photoId the value for KMORDER.ORDER_ALTER_PHOTO.PHOTO_ID
   * @ibatorgenerated Fri Sep 27 14:27:36 CST 2013
   */
  public void setPhotoId(Long photoId) {
    this.photoId = photoId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.ORDER_ALTER_PHOTO.BATCH_NO
   * 
   * @return the value of KMORDER.ORDER_ALTER_PHOTO.BATCH_NO
   * @ibatorgenerated Fri Sep 27 14:27:36 CST 2013
   */
  public String getBatchNo() {
    return batchNo;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.ORDER_ALTER_PHOTO.BATCH_NO
   * 
   * @param batchNo the value for KMORDER.ORDER_ALTER_PHOTO.BATCH_NO
   * @ibatorgenerated Fri Sep 27 14:27:36 CST 2013
   */
  public void setBatchNo(String batchNo) {
    this.batchNo = batchNo;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.ORDER_ALTER_PHOTO.URL
   * 
   * @return the value of KMORDER.ORDER_ALTER_PHOTO.URL
   * @ibatorgenerated Fri Sep 27 14:27:36 CST 2013
   */
  public String getUrl() {
    return url;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.ORDER_ALTER_PHOTO.URL
   * 
   * @param url the value for KMORDER.ORDER_ALTER_PHOTO.URL
   * @ibatorgenerated Fri Sep 27 14:27:36 CST 2013
   */
  public void setUrl(String url) {
    this.url = url;
  }
}
