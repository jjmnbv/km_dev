package com.kmzyc.b2b.service.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.dao.OmsLogisticNumberDao;
import com.kmzyc.b2b.dao.OmsProductDao;
import com.kmzyc.b2b.dao.OmsProductStockDao;
import com.kmzyc.b2b.service.OmsProductService;
import com.kmzyc.b2b.vo.OmsProductData;
import com.kmzyc.b2b.vo.OmsProductSkuData;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.entities.ExpressSubscription;
import com.kmzyc.express.remote.ExpressSubscriptionRemoteService;
import com.kmzyc.order.remote.OrderCallBackRemoteService;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.enums.DistributionInfostatus;
import com.pltfm.app.enums.ProductChannel;
import com.pltfm.app.enums.ProductType;
import com.pltfm.app.enums.SystemCode;
import com.pltfm.app.vobject.DistributionDetailInfo;
import com.pltfm.app.vobject.DistributionInfo;
import com.pltfm.app.vobject.ErpOrderLog;
import com.pltfm.app.vobject.LogisticAndDistributionInfoVO;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductStock;

/**
 * oms 系统对接,有关于产品系统 物流信息以及更新库存接口逻辑
 *
 * @author KM
 */
@Service("omsProductService")
public class OmsProductServiceImpl implements OmsProductService {


    // Logger log = Logger.getLogger(this.getClass());
    private static Logger log = LoggerFactory.getLogger(OmsProductServiceImpl.class);

    @Resource(name = "omsProductStockDao")
    private OmsProductStockDao omsProductStockDao;

    @Resource
    private OmsLogisticNumberDao omsLogisticNumberDao;

    @Resource(name = "omsProductDao")
    private OmsProductDao omsProductDao;

    // 默认推送到龙岗仓
    private Long wareHouseIdForSZLG =
            Long.valueOf(ConfigurationUtil.getString("oms_warehouseId_szlg"));

    @Resource
    private ExpressSubscriptionRemoteService expressSubscriptionRemoteService;

    @Resource
    private OrderCallBackRemoteService orderCallBackRemoteService;

    @Resource(name = "logisticsMap")
    private Map<String, String> logisticsMap;

    @Override
    public String toOrderLogisticNumber(Map<String, String> params) throws ServiceException {
        String omsOrderCode = params.get("tid");
        String omsLogisticsCode = params.get("out_sid");
        String omsLogisticsName = params.get("company_code");
        if (StringUtils.isEmpty(omsOrderCode)) {
            throw new ServiceException(-1, "OMS推送物流信息,传入空值,请您检查所传入的tid值!");
        }

        if (StringUtils.isEmpty(omsLogisticsCode)) {
            throw new ServiceException(-1, "OMS推送物流信息,传入空值,请您检查所传入的out_sid值!");
        }

        if (StringUtils.isEmpty(omsLogisticsName)) {
            throw new ServiceException(-1, "OMS推送物流信息,传入空值,请您检查所传入的company_code值!");
        }
        DistributionInfo distributionInfo;
        String result;
        boolean isDrug = false;
        try {
            // 使用产品数据源
            //
            List<DistributionInfo> distributionInfoList =
                    omsLogisticNumberDao.getDistributionInfoList(omsOrderCode);
            if (CollectionUtils.isEmpty(distributionInfoList)) {
                log.error("订单号：" + omsOrderCode + "无对应配送单!");
                throw new ServiceException(-1, "订单号：" + omsOrderCode + "推送物流信息失败!");
            }

            distributionInfo = distributionInfoList.get(0);

            List<DistributionDetailInfo> distributionDetailInfoList =
                    distributionInfo.getDistributionDetailInfoList();
            for (DistributionDetailInfo distributionDetailInfo : distributionDetailInfoList) {
                if (distributionDetailInfo.getProduct() != null
                        && distributionDetailInfo.getProduct().getProductType() != null
                        && (distributionDetailInfo.getProduct().getProductType()
                                .intValue() == ProductType.OTC.getKey().intValue()
                                || distributionDetailInfo.getProduct().getProductType()
                                        .intValue() == ProductType.MDSIN.getKey().intValue())) {
                    isDrug = true;
                    break;
                }
            }

            LogisticAndDistributionInfoVO logisticAndDistributionInfoVO =
                    new LogisticAndDistributionInfoVO();
            logisticAndDistributionInfoVO.setOrderCode(omsOrderCode);
            logisticAndDistributionInfoVO.setLogisticsCode(omsLogisticsName);
            logisticAndDistributionInfoVO.setLogisticsName(
                    StringUtil.isEmpty(omsLogisticsName) ? "" : logisticsMap.get(omsLogisticsName));
            logisticAndDistributionInfoVO.setLogisticsOrderNo(omsLogisticsCode);
            logisticAndDistributionInfoVO.setDistributionId(distributionInfo.getDistributionId());
            logisticAndDistributionInfoVO.setIncludeMedicineFlag(isDrug);
            Map<Long, List<DistributionDetailInfo>> distributionInfoMap = new HashMap<>();
            distributionInfoMap.put(distributionInfo.getDistributionId(),
                    distributionDetailInfoList);
            logisticAndDistributionInfoVO.setDistributionInfoMap(distributionInfoMap);

            result = getLogisticNumber(logisticAndDistributionInfoVO);

        } catch (Exception e) {
            log.error("订单号：" + omsOrderCode + "推送物流信息失败!" + e.getMessage());
            throw new ServiceException(-1, "订单号：" + omsOrderCode + "推送物流信息失败!", e);
        }

        try {
            if ("SUCCESS".equals(result)) {
                distributionInfo.setLogisticsCompany(omsLogisticsName);
                distributionInfo.setLogisticsNo(omsLogisticsCode);
                distributionInfo.setIsDeliveries(DistributionInfostatus.ISDELIVERIES.getStatus());
                // // 使用产品数据源
                //
                omsLogisticNumberDao.saveLogisticNumber(distributionInfo);
            }
        } catch (Exception e) {
            log.error("订单号：" + omsOrderCode + "修改配送单物流信息失败!" + e.getMessage());
        }

        try {
            ErpOrderLog log = new ErpOrderLog();
            log.setOpType(4);
            log.setSysCode(SystemCode.JIEKE.getCode());
            log.setOrderCode(omsOrderCode);
            log.setCreateDate(new Timestamp(new Date().getTime()));
            if ("SUCCESS".equals(result)) {
                // 记录物流推送日志,暂时记录到erp_order_log中
                log.setErrorContent(new SimpleDateFormat().format(new Date())
                        + " OMS系统物流信息推送成功，订单号：" + omsOrderCode + "，物流公司：" + omsLogisticsName
                        + ",物流单号：" + omsLogisticsCode);
            } else {
                // 记录物流推送日志,暂时记录到erp_order_log中
                log.setErrorContent(new SimpleDateFormat().format(new Date())
                        + " OMS系统物流信息推送失败，订单号：" + omsOrderCode + "，物流公司：" + omsLogisticsName
                        + ",物流单号：" + omsLogisticsCode);
            }
            omsProductStockDao.insert(log);
        } catch (Exception e) {
            log.error("订单号：" + omsOrderCode + "记录物流信息推送日志失败!" + e.getMessage());
        }

        if ("SUCCESS".equals(result)) {
            return returnLogisticXmlStr(omsOrderCode, omsLogisticsCode, omsLogisticsName);
        } else {
            throw new ServiceException(-1, "订单号：" + omsOrderCode + "推送物流信息失败!");
        }

    }

    private String getLogisticNumber(
            final LogisticAndDistributionInfoVO logisticAndDistributionInfoVO) throws Exception {
        ExpressSubscription expressSubscription = new ExpressSubscription();
        expressSubscription.setLogisticsCode(logisticAndDistributionInfoVO.getLogisticsCode());
        expressSubscription.setLogisticsNo(logisticAndDistributionInfoVO.getLogisticsOrderNo());
        expressSubscription.setOrderCode(logisticAndDistributionInfoVO.getOrderCode());
        expressSubscription.setChannel(ProductChannel.B2B.getStatus());

        // ExpressSubscriptionRemoteService expressSubscriptionRemoteService =
        // (ExpressSubscriptionRemoteService) RemoteTool.getRemote("12",
        // "expressSubscriptionRemoteService");
        log.info("订单" + logisticAndDistributionInfoVO.getOrderCode() + ",订阅物流信息准备开始!");
        String result =
                expressSubscriptionRemoteService.sucribeOrderExpressInfo(expressSubscription);
        log.info("订单" + logisticAndDistributionInfoVO.getOrderCode() + ",订阅物流信息完成!返回结果=" + result);

        // OrderCallBackRemoteService orderRemote =
        // (OrderCallBackRemoteService) RemoteTool.getRemote("01", "callBackService");
        log.info("订单物流单号推送准备开始!");
        result = orderCallBackRemoteService.getLogisticNumber(logisticAndDistributionInfoVO);
        log.info("订单物流单号推送完成!返回结果=" + result);
        return result;
    }


    @Override
    public String updateStockForErp(Map<String, String> params) throws ServiceException {
        try {


            String kmProductId = params.get("product_id");
            String kmSkuId = params.get("sku_id");
            String omsQuantity = params.get("storage");

            if (StringUtils.isEmpty(kmProductId) && StringUtils.isEmpty(kmSkuId)) {
                log.info("OMS推送更新库存信息,传入空值,请您检查所传入的product_id或者sku_id值,两者有一个必传!");
                throw new ServiceException(-1,
                        "OMS推送更新库存信息,传入空值,请您检查所传入的product_id或者sku_id值,两者有一个必传!");
            }

            if (StringUtils.isEmpty(omsQuantity)) {
                log.info("OMS推送更新库存信息,传入空值,请您检查所传入的storage值!");
                throw new ServiceException(-1, "OMS推送更新库存信息,传入空值,请您检查所传入的storage值!");
            }

            if (omsQuantity.contains(".") || omsQuantity.contains("-")
                    || omsQuantity.contains(",")) {
                log.info("OMS推送更新库存信息,请您检查所传入的storage值是否包含.,-,storage必须为整数!");
                throw new ServiceException(-1, "OMS推送更新库存信息,请您检查所传入的storage值是否包含.,-,storage必须为整数!");
            }

            // 绕过捷科的对应关系 20151029 annotation
            // List<ProductSku> skuList = queryProductSkuByErpProCode(kmSkuId,
            // SystemCode.JIEKE.getCode());
            //
            // if (CollectionUtils.isEmpty(skuList)) {
            // log.info("OMS推送更新库存信息,您输入的" + kmSkuId + "在康美系统中未找到对应的商品!");
            // throw new ServiceException(-1, "OMS推送更新库存信息,您输入的sku_id=" + kmSkuId +
            // "在康美系统中未找到对应的商品!");
            // }

            String codeNo = StringUtils.isNotEmpty(kmSkuId) ? kmSkuId : kmProductId;

            // 先查询传入的sku是否有效 可以直接查库存表,但是对比不了商品sku是否有效
            Map<String, String> paraMap = new HashMap<>();
            if (StringUtils.isNotEmpty(kmSkuId)) {
                paraMap.put("productSkuCode", kmSkuId);
            } else if (StringUtils.isNotEmpty(kmProductId)) {
                paraMap.put("productNo", kmProductId);
            }

            List<ProductSku> skuList = omsProductStockDao.queryValidSkuByMap(paraMap);

            if (CollectionUtils.isEmpty(skuList)) {
                log.info("OMS推送更新库存信息,您输入的" + kmSkuId + "在康美系统中未找到对应的有效的sku商品!");
                throw new ServiceException(-1,
                        "OMS推送更新库存信息,您输入的sku_id=" + kmSkuId + "在康美系统中未找到对应的有效的sku商品!");
            }
            Long quantity = Long.valueOf(omsQuantity);
            for (ProductSku productSku : skuList) {

                ProductStock stock = omsProductStockDao.selectByWareAndSkuId(wareHouseIdForSZLG,
                        productSku.getProductSkuId());

                String opType = "修改";
                if (stock == null) {
                    // 新增
                    stock = new ProductStock();
                    stock.setProductId(productSku.getProductId());
                    stock.setWarehouseId(wareHouseIdForSZLG);
                    stock.setStockQuality(quantity);
                    stock.setSkuAttValue(productSku.getProductSkuCode());
                    stock.setSkuAttributeId(productSku.getProductSkuId());
                    stock.setRemark(new SimpleDateFormat().format(new Date())
                            + "OMS系统推送过来新增库存记录,数量为" + quantity + "!");
                    stock.setTotalSales(0L);
                    stock.setAlarmQuality(0L);
                    stock.setOrderQuality(0L);
                    stock.setInTransitQuality(0L);
                    stock.setRemark(new SimpleDateFormat().format(new Date()) + "OMS系统推送过来新增库存量为"
                            + quantity + "!");
                    omsProductStockDao.insertProductStock(stock);
                    opType = "新增";
                    log.info("OMS系统更新库存接口: 为skuId=" + productSku.getProductSkuId() + "新增库存为"
                            + quantity);
                } else {
                    // 更新
                    stock.setStockQuality(quantity);
                    stock.setRemark(new SimpleDateFormat().format(new Date()) + "OMS系统推送过来更新库存量为"
                            + quantity + "!");
                    omsProductStockDao.updateStockByStockId(stock.getStockId(), quantity,
                            stock.getRemark());
                    log.info("捷科系统更新库存接口: 为skuId=" + productSku.getProductSkuId() + "修改库存为"
                            + quantity);
                }
                // 记录推送日志,暂时记录到erp_order_log中
                ErpOrderLog log = new ErpOrderLog();
                log.setOpType(3);
                log.setSysCode(SystemCode.JIEKE.getCode());
                log.setOrderCode(productSku.getProductSkuId().toString());
                log.setCreateDate(new Timestamp(new Date().getTime()));
                log.setErrorContent(new SimpleDateFormat().format(new Date()) + "OMS系统调用接口为skuId="
                        + productSku.getProductSkuId() + opType + "库存量=" + quantity);

                omsProductStockDao.insert(log);
            }
            log.info("OMS系统推送库存更新为sku编号sku_id=" + codeNo + "更新库存成功!");
            return returnXmlStrForStock();
        } catch (SQLException e) {
            log.error("OMS推送更新库存信息发生异常,具体异常信息=" + e.getMessage());
            throw new ServiceException(-1, "OMS推送更新库存信息发生异常,具体异常信息=" + e.getMessage(), e);
        }


    }

    @Override
    public List<ProductSku> queryProductSkuByErpProCode(String erpProCode, String sysCode)
            throws ServiceException {
        try {
            Map<String, Object> conditionMap = new HashMap<>();
            conditionMap.put("erpProCode", erpProCode);
            conditionMap.put("sysCode", sysCode);
            return omsProductStockDao.queryProductSkuByErpProCode(conditionMap);
        } catch (SQLException e) {
            log.error("根据oms系统传入的sku编码查询对应的sku列表出错,具体异常信息=" + e.getMessage());
            throw new ServiceException(0, "根据oms系统传入的sku编码查询对应的sku列表出错,具体异常信息=" + e.getMessage(),
                    e);

        }
    }

    @Override
    public String querySingleProduct(Map<String, String> params) throws ServiceException {
        try {
            // 查询信息 ,其实这边id代表的全是no,比如productId代表的是productno ,shop开头的代表是捷科,没有的代表的km
            String kmProductId = params.get("product_id");
            String kmSkuId = params.get("sku_id"); // 代表的sku_code
            String omsProductId = params.get("shop_product_id");
            String omsSkuId = params.get("shop_sku_id");


            boolean omsPidFlag = true;
            boolean omsSkuIdFlag = true;
            boolean kmPidFlag = true;
            boolean kmSkuIdFlag = true;

            if (StringUtils.isEmpty(omsProductId)) {
                omsPidFlag = false;
                log.info("OMS系统查询单个商品信息,传入空值,请您检查所传入的shop_product_id值!");
            }

            if (StringUtils.isEmpty(omsSkuId)) {
                omsSkuIdFlag = false;
                log.info("OMS系统查询单个商品信息,传入空值,请您检查所传入的shop_sku_id值!");
            }

            if (StringUtils.isEmpty(kmProductId)) {
                kmPidFlag = false;
                log.info("OMS系统查询单个商品信息,传入空值,请您检查所传入的product_id值!");
            }

            if (StringUtils.isEmpty(kmSkuId)) {
                kmSkuIdFlag = false;
                log.info("OMS系统查询单个商品信息,传入空值,请您检查所传入的sku_id值!");
            }


            if (!omsPidFlag && !omsSkuIdFlag && !kmPidFlag && !kmSkuIdFlag) {
                log.info(
                        "OMS系统查询单个商品信息,所传入的均为空值,包括product_id,sku_id,shop_product_id,shop_sku_id参数!");
                throw new ServiceException(-1,
                        "OMS系统查询单个商品信息,所传入的均为空值,包括product_id,sku_id,shop_product_id,shop_sku_id参数!");
            }


            // 此处先查产品信息
            // 如果四个都传了,优先查捷科传过来的skuId,如果捷科的sku什么的都没有传,传了product_id,那就用productno查,因为product_id
            // 代表的是product_no
            String queryType = "0";
            String erpCode = "";


            Map<String, Object> paraMapForProductInfo = new HashMap<>();
            paraMapForProductInfo.put("wareHouseId", wareHouseIdForSZLG);
            if (omsSkuIdFlag) {
                paraMapForProductInfo.put("erpProCode", omsSkuId);
                erpCode = omsSkuId;
            } else if (omsPidFlag) {
                paraMapForProductInfo.put("erpProCode", omsProductId);
                erpCode = omsProductId;
            }


            // skucode优先级高于productno
            if (!omsSkuIdFlag && !omsPidFlag) {
                if (kmPidFlag) {
                    paraMapForProductInfo.put("productNo", kmProductId);
                    queryType = "1";
                } else {
                    paraMapForProductInfo.put("productSkuCode", kmSkuId);
                    queryType = "2";
                }
            }


            // TODO 不直接一次性查询所关联的skulist出来是因为库存id值
            List<OmsProductData> productList = new ArrayList<>();
            if ("0".equals(queryType)) {
                productList = omsProductDao.queryProductErpCode(
                        String.valueOf(paraMapForProductInfo.get("erpProCode")));
            } else if ("1".equals(queryType) || "2".equals(queryType)) {
                // 传入的是产品id或者productNo查询 先查产品,然后查询产品下的sku列表
                productList = omsProductDao.queryProductByMap(paraMapForProductInfo);
            }


            if (CollectionUtils.isEmpty(productList)) {
                log.info("OMS系统查询单个商品信息,没有找到对应康美中药城的有效sku信息!");
                throw new ServiceException(-1, "OMS系统查询单个商品信息,没有找到对应康美中药城的有效sku信息!");
            }

            int count = 0;

            for (OmsProductData omsProductData : productList) {
                paraMapForProductInfo.put("productId", omsProductData.getProductId());
                List<OmsProductSkuData> skuList =
                        omsProductDao.queryProductSkuByProductId(paraMapForProductInfo);

                if (omsProductData.getOmsProductSkuList() == null) {
                    omsProductData.setOmsProductSkuList(new ArrayList<>());
                }
                if (CollectionUtils.isNotEmpty(skuList)) {
                    count++;
                    if ("1".equals(queryType)) {
                        omsProductData.setOmsProductSkuList(skuList);
                        continue;
                    }
                    // 只查询指定的sku的信息返回
                    for (OmsProductSkuData sku : skuList) {
                        if ("0".equals(queryType)) {
                            // sql是一并查出来productid的所有sku信息,如果查询出来没有erpcode或者skucode不相等的直接过滤掉
                            if (StringUtils.isNotEmpty(sku.getErpProCodeForOms())
                                    && erpCode.equals(sku.getErpProCodeForOms())) {
                                omsProductData.getOmsProductSkuList().add(sku);
                            }
                        } else if ("2".equals(queryType)) {
                            if (kmSkuId.equals(sku.getProductSkuCode())) {
                                omsProductData.getOmsProductSkuList().add(sku);
                            }
                        }
                    }
                }
            }

            if (count < 1) {
                log.info("OMS系统查询单个商品信息,没有找到对应康美中药城的有效sku信息!");
                throw new ServiceException(-1, "OMS系统查询单个商品信息,没有找到对应康美中药城的有效sku信息!");
            }
            // return generateXmlForProduct(productList, code, message);
            return generateXmlForProduct(productList, null, null, null, null);
        } catch (Exception e) {
            log.error("OMS系统查询单个商品信息发生异常,具体异常:" + e.getMessage());
            throw new ServiceException(-1, "OMS系统查询单个商品信息发生异常,具体异常:" + e.getMessage(), e);
        }
    }


    @Override
    public String queryProductListForOms(Map<String, String> params) throws ServiceException {
        try {

            String startTime = params.get("starttime");
            String endTime = params.get("endtime");
            String page = params.get("page"); // 页数
            String pageSize = params.get("pagesize"); // 返回多少条

            if (StringUtils.isEmpty(startTime)) {
                throw new ServiceException(-1, "OMS系统查询批量商品信息,传入空值,请您检查所传的starttime参数!");
            }

            if (StringUtils.isEmpty(endTime)) {
                throw new ServiceException(-1, "OMS系统查询批量商品信息,传入空值,请您检查所传的endtime参数!");
            }

            if (StringUtils.isEmpty(page)) {
                throw new ServiceException(-1, "OMS系统查询批量商品信息,传入空值,请您检查所传的page参数!");
            }

            if (StringUtils.isEmpty(pageSize)) {
                throw new ServiceException(-1, "OMS系统查询批量商品信息,传入空值,请您检查所传的pageSize参数!");
            }

            page = "0".equals(page) ? "1" : page;

            Map<String, String> paraMap = new HashMap<>();
            paraMap.put("startTime", startTime);
            paraMap.put("endTime", endTime);
            paraMap.put("startIndex", String
                    .valueOf((Integer.parseInt(page) - 1) * (Integer.parseInt(pageSize)) + 1));
            paraMap.put("endIndex",
                    String.valueOf((Integer.parseInt(page)) * Integer.parseInt(pageSize)));

            List<OmsProductData> productList = omsProductDao.queryProductListForOms(paraMap);

            if (CollectionUtils.isEmpty(productList)) {
                log.info("OMS系统查询批量商品信息,您所查询的条件内没有对应的商品!");
                return generateXmlForProduct(productList, "list", Integer.valueOf(page), 0, 0);
            }

            Integer totalCount = omsProductDao.queryCountListForOms(paraMap);

            int totalPage = totalCount % Integer.parseInt(pageSize) == 0
                    ? totalCount / Integer.parseInt(pageSize)
                    : totalCount / Integer.parseInt(pageSize) + 1;

            Map<String, Object> skuMap = new HashMap<>();
            skuMap.put("wareHouseId", wareHouseIdForSZLG);

            for (OmsProductData omsProductData : productList) {
                skuMap.put("productId", omsProductData.getProductId());
                List<OmsProductSkuData> skuList = omsProductDao.queryProductSkuByProductId(skuMap);
                if (CollectionUtils.isEmpty(skuList)) {
                    continue;
                }
                if (omsProductData.getOmsProductSkuList() == null) {
                    omsProductData.setOmsProductSkuList(new ArrayList<>());
                }
                omsProductData.setOmsProductSkuList(skuList);
            }
            return generateXmlForProduct(productList, "list", Integer.valueOf(page), totalPage,
                    totalCount);
        } catch (Exception e) {
            log.error("OMS系统查询批量商品信息发生异常,具体异常:" + e.getMessage());
            throw new ServiceException(-1, "OMS系统查询批量商品信息发生异常,具体异常:" + e.getMessage(), e);
        }
    }

    /**
     * 返回xml格式
     */
    private String returnLogisticXmlStr(String id, String code, String name) {
        // sb.append("<code>" + msgCode + "</code>");
        // sb.append("<msg>" + msg + "</msg>");
        return "<logistics_companies><logistics_company>" + "<id>" + id + "</id>" + "<code>" + code
                + "</code>" + "<name>" + name + "</name>"
                + "</logistics_company></logistics_companies>";
    }

    /**
     * 返回xml格式 更新库存
     */
    private String returnXmlStrForStock() {
        // sb.append("<code>" + code + "</code>");
        // sb.append("<msg>" + message + "</msg>");
        return ("<changed_at>" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                + "</changed_at>");
    }


    /**
     * 返回单个产品数据
     */
    private String generateXmlForProduct(List<OmsProductData> productList, String type,
            Integer currentPage, Integer totalPage, Integer totalCount) {
        StringBuilder sb = new StringBuilder();
        // sb.append("<code>" + code + "</code>");
        // if(CollectionUtils.isEmpty(productList)){
        // message="你所查询的编码在康美系统中没有找到对应的有效的商品!";
        // }
        // sb.append("<msg>" + message + "</msg>");

        if ("list".equals(type)) {
            if (CollectionUtils.isEmpty(productList)) {
                sb.append("<total_results><![CDATA[").append(0).append("]]></total_results>");
                sb.append("<total_page><![CDATA[").append(0).append("]]></total_page>");
                sb.append("<current_page><![CDATA[").append(1).append("]]></current_page>");
                return sb.toString();
            }

            sb.append("<total_results><![CDATA[").append(totalCount).append("]]></total_results>");
            sb.append("<total_page><![CDATA[").append(totalPage).append("]]></total_page>");
            sb.append("<current_page><![CDATA[").append(currentPage).append("]]></current_page>");
            sb.append("<product_list>");
        }

        for (OmsProductData product : productList) {
            if (CollectionUtils.isEmpty(product.getOmsProductSkuList())) {
                continue;
            }
            // String skuName = product.getName();
            sb.append("<product>");
            sb.append("<product_id><![CDATA[").append(product.getProductNo())
                    .append("]]></product_id>");
            sb.append("<product_name><![CDATA[").append(product.getName())
                    .append("]]></product_name>");
            String statusValue = "onsale";
            if ("4".equals(product.getStatus())) {
                statusValue = "forsale";
            }
            sb.append("<product_status><![CDATA[").append(statusValue)
                    .append("]]></product_status>");
            sb.append("<sku_list>");
            for (OmsProductSkuData sku : product.getOmsProductSkuList()) {
                sb.append("<sku>");
                sb.append("<sku_id><![CDATA[").append(sku.getProductSkuCode())
                        .append("]]></sku_id>");
                sb.append("<shop_sku_id><![CDATA[").append(sku.getErpProCodeForOms())
                        .append("]]></shop_sku_id>");
                sb.append("<sku_name><![CDATA[")
                        .append(StringUtils.isEmpty(sku.getSkuAttrs()) ? "无" : sku.getSkuAttrs())
                        .append("]]></sku_name>");
                sb.append("<sku_storage><![CDATA[")
                        .append(sku.getStockQuantity() == null ? 0 : sku.getStockQuantity())
                        .append("]]></sku_storage>");
                sb.append("<sku_price><![CDATA[").append(sku.getPrice()).append("]]></sku_price>");
                sb.append("</sku>");
            }
            sb.append("</sku_list>");
            sb.append("</product>");
        }
        if ("list".equals(type)) {
            sb.append("</product_list>");
        }
        return sb.toString();
    }
}
