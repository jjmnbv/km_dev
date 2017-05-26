package com.pltfm.sys.util;

public class ErrorCode {

  public static final int ERROR_UNKOWN = -1;

  public static final int NULL_INPUT = 10000;

  public static final int NO_LOGIN_USER = 10001;
  public static final int INVALIDATE_PWD = 10002;
  public static final int INVALIDATE_CODE = 10003;

  public static final int SPIDER_NETWORK_ERROR = 200001;
  public static final int SPIDER_ANALYSIS_ERROR = 200002;
  public static final int SPIDER_NODATA_ERROR = 200003;

  public static final int FILE_STREAM_NULL_ERROR = 300001;
  public static final int FILE_SIZE_ERROR = 300002;
  public static final int FILE_NOT_FOUND_ERROR = 300003;


  public static final int DB_ERROR = 99999;

  public static final int ILLEGAL_REQUEST = 50000;
  public static final int INPUT_DATE_ERROR = 50001;

  public static final int SYNC_PERSONAL_INFO_ERROR = 60000;
}
