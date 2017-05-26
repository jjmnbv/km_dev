package com.kmzyc.promotion.app.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.kmzyc.promotion.app.service.PromotionTypeService;
import com.kmzyc.promotion.app.util.PromotionInfoUtil;

@Service("promotionTypeService")
public class PromotionTypeServiceImpl implements PromotionTypeService {

  @Override
  public Map<Integer, String> getPromotionTypeMap() {

    return PromotionInfoUtil.getPromotionTypeMap();
  }

}
