package com.kmzyc.product.remote.service;

import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.CarryStockOutVO;

/**
 * 外部系统对接
 *
 * @author xkj
 */
public interface SystemDockingService {

    List<String> convertOrderReturnMsgToXml(List<CarryStockOutVO> carryStockOutList,
                                            Map<String, String> erpProductSkuCodeMap) throws ServiceException;
}