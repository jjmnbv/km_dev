package com.kmzyc.b2b.service;

import java.math.BigDecimal;

import com.kmzyc.b2b.vo.ReturnResult;

public interface RefundOrderService {

  ReturnResult refundOrder(String orderCode, int rechargeOrOrderFlag, BigDecimal amount,
                           String batchNo);
  
/**
 * 
 *
 * @author KM 
 * @param orderCode
 * @param rechargeOrOrderFlag
 * @param amount
 * @param batchNo
 * @param ysFlage 1:定金  2:尾款
 * @return
 */
  ReturnResult refundOrderForYs(String orderCode, int rechargeOrOrderFlag, BigDecimal amount,
      String batchNo,String ysFlage);
  
}
