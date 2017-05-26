package com.kmzyc.b2b.shopcart.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.promotion.optimization.vo.ProductPromotion;
import com.kmzyc.promotion.optimization.vo.Promotion;
import com.kmzyc.promotion.optimization.vo.PromotionProductData;
import com.kmzyc.zkconfig.ConfigurationUtil;

public class ShopCartProduct implements Serializable, Comparable<ShopCartProduct> {
    private static final long serialVersionUID = -8424637131595531189L;
    JSONObject json;

    public ShopCartProduct(JSONObject map) {
        if (!map.containsKey("time")) {
            map.put("time", new Date().getTime());
        }
        this.json = map;
    }

    private BigDecimal productPriceTotal;
    /** skuId或者套餐id */
    private String id;
    // private String name;
    private BigDecimal finalPrice;
    private Integer amount;
    private BigDecimal totalPrice;
    private String imgUrl;
    private boolean check;
    private Integer stockCount;
    private boolean isOutOfStock;
    private String imagePath; // 产品图片
    private String title;// 产品标题+规格
    private Long orderPromotionId;
    private BigDecimal pvalue;
    /** 外部系统产品编码 */
    private String erpProCode;
    /** skucode **/
    private String productSkuCode;

    /** 活动 限购 */
    private ProductPromotion productPromotion;
    /** 附赠商品 */
    private List<PromotionProductData> affixProductList;

    /** 套餐产品集合 */
    private List<NormalCartProduct> productList;
    /** 推广规则 */
//    private String ruleid;

    /** 推广规则 */
//    public String getRuleid() {
//        return json.getString("ruleid");
//    }

    public JSONObject getMap() {
        return json;
    }

    public void setMap(JSONObject map) {
        this.json = map;
    }

    /** skuId或者套餐id */
    public String getId() {
        return json.getString("id");
    }

    public String getName() {
        return json.getString("name");
    }

    public BigDecimal getProductPriceTotal() {
        return json.getBigDecimal("productPriceTotal");
    }

    public BigDecimal getPvalue() {
        return json.getBigDecimal("pvalue");
    }

    public void setPvalue(BigDecimal pvalue) {
        json.put("pvalue", pvalue);
    }

    public BigDecimal getFinalPrice() {
        return json.getBigDecimal("finalPrice");
    }

    public BigDecimal getAmount() {
        return json.getBigDecimal("amount");
    }

    public Integer getCount() {
        return json.getInteger("amount");
    }

    public BigDecimal getTotalPrice() {
        return json.getBigDecimal("totalPrice");
    }

    public BigDecimal getFreight() {
        return json.getBigDecimal("freight");
    }

    public String getImgUrl() {
        return json.getString("imgUrl");
    }

    public boolean getCheck() {
        return json.getBooleanValue("check");
    }

    public int getStockCount() {
        return json.getIntValue("stockCount");
    }

    public ProductPromotion getProductPromotion() {
        return (ProductPromotion) json.get("productPromotion");
    }

    public List<PromotionProductData> getAffixProductList() {
        if (this.getProductPromotion() == null) {
            return null;
        }
        return this.getProductPromotion().getAffixProductList();
    }

    public boolean getIsOutOfStock() {
        return json.getBooleanValue("isOutOfStock");
    }

    public Long getOrderPromotionId() {
        if (json.containsKey("orderPromotionId")) {
            return json.getLong("orderPromotionId");
        }
        List<Promotion> list = getOrderPromtotions();
        if (list == null) {
            return null;
        }
        return list.get(0).getId();
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        json.put("finalPrice", finalPrice);
    }

    public BigDecimal getFreightTotal() {
        if (getFreight() == null || getAmount() == null) {
            return BigDecimal.ZERO;
        }
        return getFreight().multiply(getAmount());
    }

    public void setFreight(BigDecimal freight) {

        json.put("freight", freight);
    }

    /** 套餐产品集合 */
    @SuppressWarnings("unchecked")
    public List<NormalCartProduct> getProductList() {
        return (List<NormalCartProduct>) json.get("productList");
    }

    public String getImagePath() {
        return json.getString("imagePath");
    }

    public String getTitle() {
        return json.getString("title");
    }

    public Integer getMax() {
        return json.getInteger("max");
    }

    public Integer getMaxApp() {
        return json.getInteger("maxApp");
    }

    public Integer getUserPurchase() {
        return json.getInteger("userPurchase");
    }

    public Integer getMaxCode() {
        return json.getInteger("maxCode");
    }

    public Integer getMin() {
        return json.getInteger("min");
    }

    public Integer getMinCode() {
        return json.getInteger("minCode");
    }

    /** 外部系统产品编码 */
    public String getErpProCode() {
        return json.getString("erpProCode");
    }

    public String getProductSkuCode() {
        return json.getString("productSkuCode");
    }

    /** 订单级活动 */
    public List<Promotion> getOrderPromtotions() {
        ProductPromotion pp = this.getProductPromotion();
        if (pp == null) {
            return null;
        }
        return pp.getSortedOrderPromtotions();
    }

    /** 单品级活动 */
    public Promotion getPricePromtotion() {
        ProductPromotion pp = this.getProductPromotion();
        if (pp == null) {
            return null;
        }
        return pp.getPricePromotion();
    }

    /** 购物车唯一id，带前缀,套餐为c_,普通商品为n_ */
    public String getPid() {
        return json.getString("pid");
    }

    static String productImage;

    public String getImg() {
        String imge = this.getImagePath();
        if (imge == null) {
            return null;
        }
        if (productImage == null) {
            synchronized (ShopCartProduct.class) {
                if (productImage == null) {
                    productImage = ConfigurationUtil.getString("picPath");
                    if (productImage == null) {
                        throw new NullPointerException("picPath 配置为空");
                    }
                }
            }
        }
        return productImage + imge.replace(".jpg", "_5.jpg");
    }

    public String getImgConnectPath(String path) {

        if (path == null) {
            return null;
        }
        if (productImage == null) {
            synchronized (ShopCartProduct.class) {
                if (productImage == null) {
                    productImage = ConfigurationUtil.getString("picPath");
                    if (productImage == null) {
                        throw new NullPointerException("picPath 配置为空");
                    }
                }
            }
        }
        return productImage + path.replace(".jpg", "_5.jpg");
    }

    public ShopCartProduct() {}

    @Override
    public int compareTo(ShopCartProduct o) {
        // if(json.containsKey("time"))
        int compare = json.getLong("time").compareTo(o.getMap().getLong("time"));
        if (compare == 0) {
            compare = this.getPid().compareTo(o.getPid());
        }
        return compare * -1;
    }

    public ShopCartProductReminder getProductReminder() {
        if (!json.containsKey("reminder")) {
            return null;
        }
        return (ShopCartProductReminder) json.get("reminder");
    }

    public String getActivityChannel() {
        return json.getString("activityChannel");
    }
}
