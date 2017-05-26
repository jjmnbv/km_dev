package com.kmzyc.b2b.util.taobao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.zkconfig.ConfigurationUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Area;
import com.taobao.api.domain.FeeDesc;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.OrderCreateResult;
import com.taobao.api.domain.PromotionDisplayTop;
import com.taobao.api.domain.TradeOrderFee;
import com.taobao.api.request.AreasGetRequest;
import com.taobao.api.request.DeliveryTradeorderCalculationRequest;
import com.taobao.api.request.ItemGetRequest;
import com.taobao.api.request.TradeCreateRequest;
import com.taobao.api.request.UmpPromotionIncrementGetRequest;
import com.taobao.api.response.AreasGetResponse;
import com.taobao.api.response.DeliveryTradeorderCalculationResponse;
import com.taobao.api.response.ItemGetResponse;
import com.taobao.api.response.TradeCreateResponse;
import com.taobao.api.response.UmpPromotionIncrementGetResponse;

public class TaoBaoUtil {
    // private static Logger logger = Logger.getLogger(TaoBaoUtil.class);
    // private Gson gson=new Gson();
    private static Logger logger = LoggerFactory.getLogger(TaoBaoUtil.class);

    // public static final String url = ConfigurationUtil.getString("taobao_normal_env");

    // 创建应用时，TOP颁发的唯一标识，TOP通过App Key来鉴别应用的身份。调用接口时必须传入的参数。
    // public static final String ht_appkey = ConfigurationUtil.getString("taobao_ht_appkey");
    // App Secret是TOP给应用分配的密钥，开发者需要妥善保存这个密钥，这个密钥用来保证应用来源的可靠性，防止被伪造。
    // public static final String ht_secret = ConfigurationUtil.getString("taobao_ht_secret");

    // public static final String qt_appkey = ConfigurationUtil.getString("taobao_qt_appkey");
    // public static final String qt_secret = ConfigurationUtil.getString("taobao_qt_secret");

    public static void main(String[] args) {
        // getAddress();
        try {
            Item item = getProductInfo(6827927969l,
                    "61007297f04dc624e08661dfd0c59ba2cdea3154067ffec2107949363",
                    "detail_url,num_iid,title,nick,type,cid,seller_cids,props,input_pids,input_str,desc,pic_url,num,valid_thru,list_time,delist_time,stuff_status,location,price,post_fee,express_fee,ems_fee,has_discount,freight_payer,has_invoice,has_warranty,has_showcase,modified,increment,approve_status,postage_id,product_id,auction_point,property_alias,item_img,prop_img,sku,video,outer_id,is_virtual");
            System.out.println(item.getNick());

            // PromotionDisplayTop promotions = getpromotion(6827927969l,
            // "6102b14c62849e986d760812d689e7108dffa767578f5b22107949363");
            // System.out.println(promotions.getPromotionInItem());

            /*
             * List<Area> areas = getArea(); for(Area a : areas){ System.out.println(a.getName()); }
             */

        } catch (ApiException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 获取商品信息
     *
     * @param houtai_sessionKey 可以为“”
     * @see http ://api.taobao.com/apidoc/dataStruct.htm?path=cid:4-dataStructId:63
     * -apiId:20-invokePath:item
     */
    public static Item getProductInfo(long numIid, String houtai_sessionKey, String Fidlds) throws
            ApiException {
        if (StringUtils.isEmpty(houtai_sessionKey)) {
            houtai_sessionKey = "";
        }
        TaobaoClient client = new DefaultTaobaoClient(
                ConfigurationUtil.getString("taobao_normal_env"),
                ConfigurationUtil.getString("taobao_ht_appkey"),
                ConfigurationUtil.getString("taobao_ht_secret"));
        ItemGetRequest req = new ItemGetRequest();
        req.setFields(Fidlds);
        req.setNumIid(numIid);
        ItemGetResponse response = client.execute(req, houtai_sessionKey);
        Item item = response.getItem();
        return item;
    }

    /**
     * 获取促销信息
     */
    public static PromotionDisplayTop getpromotion(Long item_id, String qiantai_sessionKey) throws
            ApiException {
//        UmpPromotionIncrementGetRequest req1 = new UmpPromotionIncrementGetRequest();
        // UmpPromotionIncrementGetResponse response1 = new
        // DefaultTaobaoClient(url, appkey,this.qiantai_secret).execute(req1
        // ,this.qiantai_sessionkey);
        TaobaoClient client = new DefaultTaobaoClient(
                ConfigurationUtil.getString("taobao_normal_env"),
                ConfigurationUtil.getString("taobao_qt_appkey"),
                ConfigurationUtil.getString("taobao_qt_secret"));
        UmpPromotionIncrementGetRequest req = new UmpPromotionIncrementGetRequest();
        req.setItemId(item_id);
        // req.setChannelKey("baidu.com");
        UmpPromotionIncrementGetResponse response = client.execute(req, qiantai_sessionKey);
        System.out.println(response.getBody());
        PromotionDisplayTop promotions = response.getPromotions();
        return promotions;
    }

    /**
     * 获取区域信息
     */
    public static List<Area> getArea() throws ApiException {
        TaobaoClient client = new DefaultTaobaoClient(
                ConfigurationUtil.getString("taobao_normal_env"),
                ConfigurationUtil.getString("taobao_qt_appkey"),
                ConfigurationUtil.getString("taobao_qt_secret"));
        AreasGetRequest req = new AreasGetRequest();
        req.setFields("id,type,name,parent_id,zip");
        AreasGetResponse response = client.execute(req);
        List<Area> areas = response.getAreas();
        return areas;
    }

    /**
     * 计算运费接口 单位为分
     */
    public static Integer getFare(String qt_sessionKey, CalcFareReqDomain domain) throws
            ApiException {
        TaobaoClient client = new DefaultTaobaoClient(
                ConfigurationUtil.getString("taobao_normal_env"),
                ConfigurationUtil.getString("taobao_qt_appkey"),
                ConfigurationUtil.getString("taobao_qt_secret"));
        DeliveryTradeorderCalculationRequest req = new DeliveryTradeorderCalculationRequest();
        Date date = new Date();
        req.setOrderIds("" + date.getTime());
        req.setAreaIds(domain.getAreaIds());
        req.setItemIds(domain.getItemIds());
        req.setSkuIds(domain.getSkuids());
        req.setUserIds(domain.getUserId());
        req.setTemplateIds(domain.getTemplateIds());
        req.setIsSellerPays(domain.getIsSellerPay());
        req.setUnifiedPost(domain.getUnifiedPost());
        req.setUnifiedEms(domain.getUnifiedEms());
        req.setItemCounts(domain.getItemCounts());
        req.setSelectedServices("express");
        // req.setItemWeights(domain.getItemWeights());
        DeliveryTradeorderCalculationResponse response = client.execute(req, qt_sessionKey);
        List<TradeOrderFee> feeList = response.getTradeOrderFees();
        if (feeList != null && feeList.size() > 0) {
            TradeOrderFee tradeOrderFee = feeList.get(0);
            List<FeeDesc> freeDescList = tradeOrderFee.getFeeList();
            if (freeDescList != null && freeDescList.size() > 0) {
                FeeDesc feeDesc = tradeOrderFee.getFeeList().get(0);
                return Integer.parseInt(feeDesc.getFee());
            }
        }
        return 0;
    }

    /**
     * 生成订单接口
     */
    public static OrderCreateResult createOrder(Map<String, String> params) throws Exception {
        OrderCreateResult ocs = null;
        try {
            if (null != params && !params.isEmpty()) {
                TaobaoClient client = new DefaultTaobaoClient(
                        ConfigurationUtil.getString("taobao_normal_env"),
                        ConfigurationUtil.getString("taobao_qt_appkey"),
                        ConfigurationUtil.getString("taobao_qt_secret"));
                TradeCreateRequest req = new TradeCreateRequest();
                req.setNumIids(params.get("num_iids"));// 商品的id序列列表not null
                req.setOutId(params.get("out_id"));// 外部订单号not null
                req.setSkuIids(params.get("sku_iids"));// skuid列表not null
                req.setNums(params.get("nums"));// 数量列表not null
                req.setItemPromotions(params.get("item_promotions"));// 商品优惠id列表
                req.setShopPromotion(params.get("shop_promotion"));// 店铺级优惠的id
                req.setPid(params.get("pid"));// 淘宝客id，创建交易支持淘宝客可以传入该参数
                req.setBuyerMemo(params.get("buyer_memo"));// 买家留言
                String shippingType = params
                        .get("shipping_type");// 创建交易时的物流方式，可选值：ems,express,post,free
                req.setShippingType(
                        StringUtils.isEmpty(shippingType) ? "post" : shippingType);// not
                // null
                req.setOrderFrom("MEDICINE");// 订单来源,商城医药馆not null
                String addressId = params.get("address_id");// 地址id
                if (StringUtils.isEmpty(addressId)) {
                    // 新增收货地址
                    req.setNewDivisioncode(params.get("new_divisioncode"));// 区域码
                    req.setNewPostcode(params.get("new_postcode"));// 邮编
                    req.setNewAddress(params.get("new_address"));// 收件地址
                    req.setNewName(params.get("new_name"));// 收件人名字
                    req.setNewMobile(params.get("new_mobile"));// 手机号码
                    req.setNewPhone(params.get("new_phone"));// 电话号码
                    String isStoreNewAddress = params.get("isStoreNewAddress");
                    req.setNewAddressStore("N".equals(isStoreNewAddress) ? false : true);// 是否保存新地址
                } else {
                    req.setAddressId(addressId);
                }
                TradeCreateResponse response = client.execute(req, params.get("sessionKey"));
                ocs = response.getOrderResult();
            }
        } catch (Exception e) {
            logger.error("生成淘宝订单发生异常！", e);
            throw e;
        }
        return ocs;
    }
}
