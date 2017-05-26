package com.pltfm.app.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.product.remote.service.StockRemoteService;
import com.pltfm.app.dao.OrderMainDAO;
import com.pltfm.app.entities.Invoice;
import com.pltfm.app.entities.InvoiceItem;
import com.pltfm.app.entities.OrderItem;
import com.pltfm.app.entities.OrderMain;
import com.pltfm.app.entities.OrderOperateStatement;
import com.pltfm.app.vobject.CarryStockOutDetailVO;
import com.pltfm.app.vobject.CarryStockOutVO;
import com.pltfm.app.vobject.OrderOperateStatementDecorator;
import com.pltfm.app.vobject.ProductStock;
import com.pltfm.sys.util.ErrorCode;

@Component
@SuppressWarnings("unchecked")
public class SettlementAndPayUtil {

  private static Logger log = Logger.getLogger(SettlementAndPayUtil.class);

  // 产品系统的库存接口的服务类
  @Autowired
  private StockRemoteService stockRemoteService;

  @Resource
  private OrderMainDAO orderMainDAO;

  public static Invoice order2invoice(OrderMain om, Date now) {// 订单到发票未完整
    Invoice iv = new Invoice();
    iv.setPayeeName(om.getCustomerName());
    iv.setCreateType((short) OrderDictionaryEnum.OrderPurchaserType.Register.getKey());// 自开
    iv.setPayerName(om.getConsigneeName());
    iv.setInvoiceSum(om.getCommoditySum());
    iv.setCreateDate(now);
    return iv;
  }

  public static InvoiceItem orderItem2invoiceItem(OrderItem oit, Long invoiceId) {// 订单项到发票项
    InvoiceItem ivt = new InvoiceItem();
    // ivt.setDeductionFlag(deductionFlag);//抵扣？
    ivt.setDiscountAmount((oit.getCommodityUnitPrice().multiply(
        BigDecimal.valueOf(oit.getCommodityNumber())).subtract(oit.getCommoditySum())));// 折扣额
    ivt.setInvoiceId(invoiceId);
    ivt.setInvoiceItemCode(oit.getCommoditySku());// 商品号？
    ivt.setInvoiceItemName(oit.getCommodityName());
    ivt.setInvoiceItemNumber(oit.getCommodityNumber());
    ivt.setLineSum(oit.getCommoditySum());
    // ivt.setNote(note);
    ivt.setUnitPrice(oit.getCommodityUnitPrice());
    return ivt;
  }

  public static OrderOperateStatement buildOperateStatement(OrderOperateStatement oosPrevious,
      OrderOperateStatementDecorator decorator) {
    OrderOperateStatement oosNow = null;
    oosNow = new OrderOperateStatement();
    oosNow.setOperateInfo(decorator.getComment());
    oosNow.setNowOperateDate(decorator.getNow());
    oosNow.setNowOperateType(decorator.getNowOperateType());
    oosNow.setNowOperator(decorator.getOperator());
    oosNow.setNowOrderStatus(decorator.getNowOrderStatus());
    oosNow.setNowOrderSum(decorator.getNowOrderMoney());
    oosNow.setOrderCode(decorator.getOrderCode());
    oosNow.setPreviousOperateDate(oosPrevious.getNowOperateDate());
    oosNow.setPreviousOperateType(oosPrevious.getNowOperateType());
    oosNow.setPreviousOperator(oosPrevious.getNowOperator());
    oosNow.setPreviousOrderStatus(oosPrevious.getNowOrderStatus());
    oosNow.setPreviousOrderSum(oosPrevious.getNowOrderSum());
    return oosNow;
  }

  /**
   * 根据单个订单号、以及相关订单明细来锁或是解锁库存
   * 
   * @param orderCode
   * @param flag 1：锁库存；2：解锁库存
   * @param orderItemList
   * @return
   * @throws ServiceException
   */
  public int batchHandleStock(String orderCode, int flag, List<OrderItem> orderItemList,
      String webSite) throws ServiceException {
    int result = 0;
    String operateStr = null;
    String stockRemoteResult = null;
    // 根据orderCode查询出所有的订单明细的列表
    // List<OrderItem> orderItemList =
    // orderItemQryService.listOrderItems(orderCode);
    String SKUStr = null;
    BigDecimal wareHouseId = null;
    Long wareHouseIdLongValue = null;
    Long count = null;
    Map<String, Object> resultMap = null;
    // 遍历orderItemMap中的仓库id，组装锁库存用的map，形如Map<SKUCode,Map<WareHouseId,Count>>
    Map<String, Map<Long, Long>> lockStockMap = new HashMap<String, Map<Long, Long>>();
    Map<Long, Long> wareHouse2CountMap = null;// new HashMap<Long,Long>();
    // 遍历前述操作得到的订单明细列表
    for (OrderItem tempItem : orderItemList) {
      // 取得SKU编码
      SKUStr = tempItem.getCommoditySku();
      // 取得仓库id
      wareHouseId = tempItem.getWarehouseId();
      wareHouseIdLongValue = wareHouseId.longValue();
      // 取得商品数量
      count = tempItem.getCommodityNumber();
      // 首先判断查询Map lockStockMap中是否包含该SKU编码的键值对
      if (lockStockMap.containsKey(SKUStr)) {// 如果存在该SKU编码的键值对，则要进一步判断仓库id到商品数量的Map数据
        wareHouse2CountMap = lockStockMap.get(SKUStr);
        // 如果仓库id到商品数量的Map中不存在该仓库id，则需要加入列表，并从lockStockMap去掉该键值对重新载入新的
        if (!wareHouse2CountMap.containsKey(wareHouseIdLongValue)) {
          wareHouse2CountMap.put(wareHouseIdLongValue, count);
          lockStockMap.remove(SKUStr);
          lockStockMap.put(SKUStr, wareHouse2CountMap);
        } else {// 如果存在，需要更新？？

        }
      } else {// 如果不存在该SKU编码的键值对，实例化仓库id到商品数量的Map并加入该仓库id
        // 实例化某个SKU编码下的仓库id到商品数量的Map
        wareHouse2CountMap = new HashMap<Long, Long>();
        wareHouse2CountMap.put(wareHouseIdLongValue, count);
        lockStockMap.put(SKUStr, wareHouse2CountMap);
      }
    }
    try {
      // 1是锁库存
      if (flag == 1) {
        operateStr = "锁库存";
        resultMap = stockRemoteService.batchLockStock(lockStockMap, orderCode, webSite);
        // 2是解锁
      } else if (flag == 2) {
        operateStr = "解锁库存";
        List<CarryStockOutVO> csovList = new ArrayList<CarryStockOutVO>();
        csovList.add(buildCarryStockOutVO(orderMainDAO
            .selectByOrderCode(orderCode)));
        resultMap = stockRemoteService.batchUnLockStock(lockStockMap, webSite);
      }
    } catch (Exception e) {
      log.error("调用产品系统的批量锁库存的接口时发生异常", e);
      throw new ServiceException(ErrorCode.INNER_SINGLE_ERROR, "调用产品系统的批量锁库存的接口时发生异常："
          + e.getMessage());
    }
    Object object = null;
    List<ProductStock> cannotLockList = null;
    if (resultMap != null && resultMap.size() > 0) {
      for (String key : resultMap.keySet()) {
        if ("success".equalsIgnoreCase(key)) {
          stockRemoteResult = "success";
        }
        if ("fail".equalsIgnoreCase(key)) {
          stockRemoteResult = "fail";
          object = resultMap.get(key);
          if (object instanceof java.util.List) {
            cannotLockList = (List<ProductStock>) resultMap.get(key);
            for (ProductStock proStock : cannotLockList) {
              log.info(operateStr + "-" + "调用产品系统的库存接口返回结果集合打印开始：");
              log.info("仓库id：" + proStock.getWarehouseId());
              log.info("sku编码：" + proStock.getSkuAttValue());
              log.info(operateStr + "-" + "调用产品系统的库存接口返回结果集合打印结束：");
            }
          }
          if (object instanceof java.lang.String) {
            log.info(operateStr + "-" + "调用产品系统的库存接口返回结果字符串为：" + object);
          }
        }
      }
      if ("success".equalsIgnoreCase(stockRemoteResult)) {
        log
            .info("############# 订单号：" + orderCode + " 通过产品系统库存接口批量" + "-" + operateStr + "-"
                + "成功");
        result = 1;
      } else {
        log
            .info("############# 订单号：" + orderCode + " 通过产品系统接口接口批量" + "-" + operateStr + "-"
                + "失败");
        result = -1;
      }
    }
    return result;
  }

  /**
   * 构造出库单VO的列表
   * 
   * @param list
   * @param mapOrderItem
   * @return
   * @throws ServiceException
   */
  public static List<CarryStockOutVO> buildCarryOutStockVO(List<OrderMain> list,
      Map<String, List<OrderItem>> mapOrderItem) throws ServiceException {
    // 构造返回列表
    List<CarryStockOutVO> stockOutVOList = new ArrayList<CarryStockOutVO>();
    try {
      // 构造接口入参列表的元素，为产品系统中定义的CarryStockOutVO对象
      CarryStockOutVO carryStockOutVO = null;
      // 构造出库单明细对象列表
      List<CarryStockOutDetailVO> stockOutDetailVOList = null;
      // 构造出库单明细对象，和CarryStockOutVO是主从关系，且是一对多
      CarryStockOutDetailVO carryStockOutDetailVO = null;
      // 构造订单明细列表
      List<OrderItem> orderItemList = null;
      // 初始化订单明细中商品所在仓库的id
      Long wareHouseId = null;
      /**
       * 遍历订单主表列表，然后用1个Map<OrderMain, Map<WareHouseId,List<OrderItem>>>记录该订单号下，以每个仓库id为键值的订单明细对象
       * 有多少仓库id就要生成多少出库单对象
       */
      Map<OrderMain, Map<Long, List<OrderItem>>> mapMain =
          new HashMap<OrderMain, Map<Long, List<OrderItem>>>();
      Map<Long, List<OrderItem>> mapItem = null;// new
      List<OrderItem> listItem = null;// new ArrayList<OrderItem>();
      for (OrderMain order : list) {
        // 得到订单编码
        // 得到订单明细对象列表
        orderItemList = mapOrderItem.get(order.getOrderCode());
        // 实例化前面定义的明细map mapItem
        mapItem = new HashMap<Long, List<OrderItem>>();
        // 遍历该订单编码下的订单明细列表
        for (OrderItem item : orderItemList) {
          // 得到仓库id
          if (item.getWarehouseId() != null) {
            wareHouseId = item.getWarehouseId().longValue();
          }
          // 如果mapItem中包含该wareHouseId的键值对，则需要分析该明细列表listItem中的数据
          if (mapItem.containsKey(wareHouseId)) {
            // 如果listItem中不包含该订单明细对象，则需要添加然后再mapItem中刷新该键值对
            if (!listItem.contains(item)) {
              listItem.add(item);
              mapItem.remove(wareHouseId);
              mapItem.put(wareHouseId, listItem);
            } else {// 如果listItem中包含该订单明细对象，不做处理（预留）

            }
          } else {// 如果不存在的话，要new一个listItem进行构造
            listItem = new ArrayList<OrderItem>();
            listItem.add(item);
            mapItem.put(wareHouseId, listItem);
          }
        }
        mapMain.put(order, mapItem);
      }
      // 遍历mapMain列表，并构造出库单对象以及出库单明细列表
      for (OrderMain order : mapMain.keySet()) {
        mapItem = mapMain.get(order);
        for (Long wareHouse : mapItem.keySet()) {
          // 构造出库单对象
          carryStockOutVO = buildCarryStockOutVO(order);
          // 实例化出库单明细对象列表
          stockOutDetailVOList = new ArrayList<CarryStockOutDetailVO>();
          listItem = mapItem.get(wareHouse);
          for (OrderItem orderItem : listItem) {
            carryStockOutDetailVO = buildCarryStockOutDetailVO(orderItem);
            stockOutDetailVOList.add(carryStockOutDetailVO);
          }
          carryStockOutVO.setDetailList(stockOutDetailVOList);
          stockOutVOList.add(carryStockOutVO);
        }
      }
    } catch (Exception e) {

    }
    return stockOutVOList;
  }

  /**
   * 构造出库单对象
   * 
   * @param order
   * @return
   */
  public static CarryStockOutVO buildCarryStockOutVO(OrderMain order) {
    CarryStockOutVO cso = new CarryStockOutVO();
    cso.setBillNo(order.getOrderCode());// 订单号
    cso.setPurchaserAddrr(order.getConsigneeAddr());// 收货人地址
    cso.setPurchaserName(order.getConsigneeName());// 收货人名称
    cso.setPurchaserTel(order.getConsigneeMobile());// 收货人手机
    if (null != order.getCreateDate()) {
      cso.setCreateTime(DateTimeUtils.getDateTime(order.getCreateDate()));// 下单日期
    }
    if (null != order.getPayDate()) {
      cso.setPayTime(DateTimeUtils.getDateTime(order.getPayDate()));// 支付日期
    }
    return cso;
  }

  /**
   * 构造出库单明细对象
   * 
   * @param orderItem
   * @return
   */
  public static CarryStockOutDetailVO buildCarryStockOutDetailVO(OrderItem orderItem) {
    CarryStockOutDetailVO csod = new CarryStockOutDetailVO();
    csod.setBillDetailID(orderItem.getOrderItemId());// 订单明细id
    csod.setCommodityNumber(orderItem.getCommodityNumber());// 商品名称
    csod.setSkuCode(orderItem.getCommoditySku());// 商品sku编码
    csod.setUnitPrice(orderItem.getCommodityUnitPrice());// 商品单价
    Long wareHouseId = null;// 商品所在仓库id
    if (orderItem.getWarehouseId() != null) {
      wareHouseId = orderItem.getWarehouseId().longValue();
    }
    csod.setWarehouseId(wareHouseId);
    if (null != orderItem.getCommodityType() && 4 == orderItem.getCommodityType().intValue()) {
      csod.setIsPresent("1");// 是否是赠品,0否，1是
    } else {
      csod.setIsPresent("0");// 是否是赠品,0否，1是
    }
    return csod;
  }

  /**
   * 构造锁或是解锁库存的参数map，用于调用产品锁/解锁库存的接口
   * 
   * @param orderItemList
   * @return
   * @throws ServiceException
   */
  public static Map<String, Map<Long, Long>> builderStockMap(List<OrderItem> orderItemList)
      throws ServiceException {
    // 遍历orderItemMap中的仓库id，组装锁库存用的map，形如Map<SKUCode,Map<WareHouseId,Count>>
    Map<String, Map<Long, Long>> stockMap = new HashMap<String, Map<Long, Long>>();
    Map<Long, Long> wareHouse2CountMap = null;// new HashMap<Long,Long>();
    String SKUStr = null;
    BigDecimal wareHouseId = null;
    Long wareHouseIdLongValue = null;
    Long count = null;
    Long extCount = null;
    // 遍历前述操作得到的订单明细列表
    for (OrderItem tempItem : orderItemList) {
      // 取得SKU编码
      SKUStr = tempItem.getCommoditySku();
      // 取得仓库id
      wareHouseId = tempItem.getWarehouseId();
      wareHouseIdLongValue = wareHouseId.longValue();
      // 取得商品数量
      count = tempItem.getCommodityNumber();
      // 首先判断查询Map lockStockMap中是否包含该SKU编码的键值对
      if (stockMap.containsKey(SKUStr)) {// 如果存在该SKU编码的键值对，则要进一步判断仓库id到商品数量的Map数据
        wareHouse2CountMap = stockMap.get(SKUStr);
        // 如果仓库id到商品数量的Map中不存在该仓库id，则需要加入列表，并从lockStockMap去掉该键值对重新载入新的
        if (!wareHouse2CountMap.containsKey(wareHouseIdLongValue)) {
          wareHouse2CountMap.put(wareHouseIdLongValue, count);
          stockMap.remove(SKUStr);
          stockMap.put(SKUStr, wareHouse2CountMap);
        } else {// 如果存在，需要更新？？
          extCount = wareHouse2CountMap.get(wareHouseIdLongValue);
          wareHouse2CountMap.put(wareHouseIdLongValue, count + extCount);
          stockMap.remove(SKUStr);
          stockMap.put(SKUStr, wareHouse2CountMap);
        }
      } else {// 如果不存在该SKU编码的键值对，实例化仓库id到商品数量的Map并加入该仓库id
        // 实例化某个SKU编码下的仓库id到商品数量的Map
        wareHouse2CountMap = new HashMap<Long, Long>();
        wareHouse2CountMap.put(wareHouseIdLongValue, count);
        stockMap.put(SKUStr, wareHouse2CountMap);
      }
    }
    return stockMap;
  }

  /**
   * 根据单个订单号来锁或是解锁库存
   * 
   * @param orderCode
   * @param flag 1：锁库存；2：解锁库存
   * @return
   * @throws ServiceException
   */
  @SuppressWarnings("unused")
  public  int batchHandleStock(String orderCode, List<OrderItem> orderItemList, int flag,
      String webSite) throws ServiceException {
    int result = 0;
    String stockRemoteResult = null;
    // 根据orderCode查询出所有的订单明细的列表
    // List<OrderItem> orderItemList =
    // orderItemQryService.listOrderItems(orderCode);
    String SKUStr = null;
    BigDecimal wareHouseId = null;
    Long wareHouseIdLongValue = null;
    Long count = null;
    Map<String, Object> resultMap = null;
    // 遍历orderItemMap中的仓库id，组装锁库存用的map，形如Map<SKUCode,Map<WareHouseId,Count>>
    Map<String, Map<Long, Long>> // lockStockMap = new HashMap<String,
    // Map<Long, Long>>();
    lockStockMap = builderStockMap(orderItemList);
    /*
     * Map<Long,Long> wareHouse2CountMap = null;//new HashMap<Long,Long>(); //遍历前述操作得到的订单明细列表
     * for(OrderItem tempItem:orderItemList){ //取得SKU编码 SKUStr = tempItem.getCommoditySku();
     * //取得仓库id wareHouseId = tempItem.getWarehouseId(); wareHouseIdLongValue =
     * wareHouseId.longValue(); //取得商品数量 count = tempItem.getCommodityNumber(); //首先判断查询Map
     * lockStockMap中是否包含该SKU编码的键值对 if(lockStockMap.containsKey(SKUStr)){//如果存在该SKU编码的键值对
     * ，则要进一步判断仓库id到商品数量的Map数据 wareHouse2CountMap = lockStockMap.get(SKUStr);
     * //如果仓库id到商品数量的Map中不存在该仓库id，则需要加入列表，并从lockStockMap去掉该键值对重新载入新的
     * if(!wareHouse2CountMap.containsKey(wareHouseIdLongValue)){
     * wareHouse2CountMap.put(wareHouseIdLongValue,count); lockStockMap.remove(SKUStr);
     * lockStockMap.put(SKUStr, wareHouse2CountMap); }else{//如果存在，需要更新？？
     * 
     * } }else{//如果不存在该SKU编码的键值对，实例化仓库id到商品数量的Map并加入该仓库id //实例化某个SKU编码下的仓库id到商品数量的Map
     * wareHouse2CountMap = new HashMap<Long,Long>();
     * wareHouse2CountMap.put(wareHouseIdLongValue,count); lockStockMap.put(SKUStr,
     * wareHouse2CountMap); } }
     */
    try {
      // 1是锁库存
      if (flag == 1) {
        resultMap = stockRemoteService.batchLockStock(lockStockMap, orderCode, webSite);
        // 2是解锁
      } else if (flag == 2) {
        List<CarryStockOutVO> csovList = new ArrayList<CarryStockOutVO>();
        csovList.add(buildCarryStockOutVO(orderMainDAO
            .selectByOrderCode(orderCode)));
        resultMap = stockRemoteService.batchUnLockStock(lockStockMap, webSite);
      }
    } catch (Exception e) {
      log.error("调用产品系统的批量锁库存的接口时发生异常", e);
      throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR, "订单 " + orderCode
          + " 调用产品系统的批量锁库存的接口时发生异常！" + e.getMessage());
    }
    Object object = null;
    List<ProductStock> cannotLockList = null;
    if (resultMap != null && resultMap.size() > 0) {
      for (String key : resultMap.keySet()) {
        if ("success".equalsIgnoreCase(key)) {
          stockRemoteResult = "success";
        }
        if ("fail".equalsIgnoreCase(key)) {
          stockRemoteResult = "fail";
          object = resultMap.get(key);
          if (object instanceof java.util.List) {
            cannotLockList = (List<ProductStock>) resultMap.get(key);
          }
        }
      }
      if ("success".equalsIgnoreCase(stockRemoteResult)) {
        log.info("############# 订单号：" + orderCode + " 批量锁库存成功");
        result = 1;
      } else {
        log.info("############# 订单号：" + orderCode + " 批量锁库存失败");
        result = -1;
      }
    }
    return result;
  }

  /**
   * 根据订单明细列表来锁或是解锁库存
   * 
   * @param orderItemList
   * @param flag 1：锁库存；2：解锁库存
   * @return
   * @throws ServiceException
   */
  @SuppressWarnings("unused")
  public  int batchHandleStock(List<OrderItem> orderItemList, int flag, String webSite)
      throws ServiceException {
    int result = 0;
    String stockRemoteResult = null;
    // 根据orderCode查询出所有的订单明细的列表
    // List<OrderItem> orderItemList =
    // orderItemQryService.listOrderItems(orderCode);
    // 订单号
    String orderCode = null;
    if (orderItemList != null && orderItemList.size() > 0) {
      orderCode = orderItemList.get(0).getOrderCode();
    }else{
        throw new ServiceException("调用函数batchHandleStock失败，参数orderItemList传入为空!");
    }
    String SKUStr = null;
    BigDecimal wareHouseId = null;
    Long wareHouseIdLongValue = null;
    Long count = null;
    Map<String, Object> resultMap = null;

    // 遍历orderItemMap中的仓库id，组装锁库存用的map，形如Map<SKUCode,Map<WareHouseId,Count>>
    Map<String, Map<Long, Long>> // lockStockMap = new HashMap<String,
    // Map<Long, Long>>();
    lockStockMap = builderStockMap(orderItemList);
    /*
     * Map<Long,Long> wareHouse2CountMap = null;//new HashMap<Long,Long>(); //遍历前述操作得到的订单明细列表
     * for(OrderItem tempItem:orderItemList){ //取得SKU编码 SKUStr = tempItem.getCommoditySku();
     * //取得仓库id wareHouseId = tempItem.getWarehouseId(); wareHouseIdLongValue =
     * wareHouseId.longValue(); //取得商品数量 count = tempItem.getCommodityNumber(); //首先判断查询Map
     * lockStockMap中是否包含该SKU编码的键值对 if(lockStockMap.containsKey(SKUStr)){//如果存在该SKU编码的键值对
     * ，则要进一步判断仓库id到商品数量的Map数据 wareHouse2CountMap = lockStockMap.get(SKUStr);
     * //如果仓库id到商品数量的Map中不存在该仓库id，则需要加入列表，并从lockStockMap去掉该键值对重新载入新的
     * if(!wareHouse2CountMap.containsKey(wareHouseIdLongValue)){
     * wareHouse2CountMap.put(wareHouseIdLongValue,count); lockStockMap.remove(SKUStr);
     * lockStockMap.put(SKUStr, wareHouse2CountMap); }else{//如果存在，需要更新？？
     * 
     * } }else{//如果不存在该SKU编码的键值对，实例化仓库id到商品数量的Map并加入该仓库id //实例化某个SKU编码下的仓库id到商品数量的Map
     * wareHouse2CountMap = new HashMap<Long,Long>();
     * wareHouse2CountMap.put(wareHouseIdLongValue,count); lockStockMap.put(SKUStr,
     * wareHouse2CountMap); } }
     */
    try {
      // 1是锁库存
      if (flag == 1) {
        resultMap = stockRemoteService.batchLockStock(lockStockMap, null, webSite);
        // 2是解锁
      } else if (flag == 2) {
        List<CarryStockOutVO> csovList = new ArrayList<CarryStockOutVO>();
        csovList.add(buildCarryStockOutVO(orderMainDAO
            .selectByOrderCode(orderCode)));
        resultMap = stockRemoteService.batchUnLockStock(lockStockMap, webSite);
      }
    } catch (Exception e) {
      log.error("调用产品系统的批量锁库存的接口时发生异常", e);
      throw new ServiceException(ErrorCode.INNER_PAYMENT_ERROR, "订单 " + orderCode
          + " 调用产品系统的批量锁库存的接口时发生异常！" + e.getMessage());
    }
    Object object = null;
    List<ProductStock> cannotLockList = null;
    if (resultMap != null && resultMap.size() > 0) {
      for (String key : resultMap.keySet()) {
        if ("success".equalsIgnoreCase(key)) {
          stockRemoteResult = "success";
        }
        if ("fail".equalsIgnoreCase(key)) {
          stockRemoteResult = "fail";
          object = resultMap.get(key);
          if (object instanceof java.util.List) {
            cannotLockList = (List<ProductStock>) resultMap.get(key);
          }
        }
      }
      if ("success".equalsIgnoreCase(stockRemoteResult)) {
        log.info("############# 订单号：" + orderCode + " 批量锁库存成功");
        result = 1;
      } else {
        log.info("############# 订单号：" + orderCode + " 批量锁库存失败");
        result = -1;
      }
    }
    return result;
  }

  public  Boolean productStockOut(String commerceId, List<OrderMain> list,
      Map<String, List<OrderItem>> mapOrderItem) throws ServiceException {
    Boolean bool = false;
    try {
      // 构造接口入参列表
      List<CarryStockOutVO> stockOutVOList = buildCarryOutStockVO(list, mapOrderItem);

      if (commerceId == null) {
        bool = stockRemoteService.insertCarryStockOut(stockOutVOList);
      } else {
        bool = stockRemoteService.carryStockOutForSupplier(stockOutVOList);
      }

    } catch (Exception e) {
      log.error("调用产品的出库单接口时发生异常", e);
      throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_PRODUCT_ERROR, "调用产品的出库单接口时发生异常："
          + e.getMessage());
    }
    return bool;
  }

  /**
   * 单个订单出库
   * 
   * @param isCommerceId
   * @param om
   * @param ois
   * @return
   * @throws ServiceException
   */
  public  Boolean productStockOut(boolean isCommerceId, OrderMain om, List<OrderItem> ois)
      throws ServiceException {
    Boolean bool = false;
    try {
      // 构造接口入参列表
      List<CarryStockOutVO> stockOutVOList = buildCarryOutStockVO(om, ois);
      if (!isCommerceId) {
        bool = stockRemoteService.insertCarryStockOut(stockOutVOList);
      } else {
        bool = stockRemoteService.carryStockOutForSupplier(stockOutVOList);
      }
    } catch (Exception e) {
      log.error("订单"+om.getOrderCode()+"调用产品的出库单接口时发生异常", e);
      throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_PRODUCT_ERROR, "调用产品的出库单接口时发生异常："
          + e.getMessage());
    }
    return bool;
  }

  /**
   * 构造出库单VO的列表
   * 
   * @param list
   * @param mapOrderItem
   * @return
   * @throws ServiceException
   */
  public static List<CarryStockOutVO> buildCarryOutStockVO(OrderMain om, List<OrderItem> ois)
      throws ServiceException {
    List<CarryStockOutVO> stockOutVOList = new ArrayList<CarryStockOutVO>();
    try {
      CarryStockOutVO csov = buildCarryStockOutVO(om);
      List<CarryStockOutDetailVO> csodvList = new ArrayList<CarryStockOutDetailVO>();
      CarryStockOutDetailVO csodv = null;
      for (OrderItem orderItem : ois) {
        csodv = buildCarryStockOutDetailVO(orderItem);
        csodvList.add(csodv);
      }
      csov.setDetailList(csodvList);
      stockOutVOList.add(csov);
    } catch (Exception e) {
      throw new ServiceException(ErrorCode.INNER_OPERATE_ASSEMBLY_PRODUCT_ERROR, "调用产品的出库单接口时发生异常："
          + e.getMessage());
    }
    return stockOutVOList;
  }
}
