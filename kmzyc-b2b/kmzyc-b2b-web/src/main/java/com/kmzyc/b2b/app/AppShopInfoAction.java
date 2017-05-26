package com.kmzyc.b2b.app;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.b2b.model.ShopBannerImage;
import com.kmzyc.b2b.model.ShopInfo;
import com.kmzyc.b2b.service.ShopInfoService;
import com.kmzyc.zkconfig.ConfigurationUtil;

/**
 * app 获取店铺信息接口 20150917 mlq add
 * 
 * @author KM
 * 
 */
@Scope("prototype")
@Controller("appShopInfoAction")
public class AppShopInfoAction extends AppBaseAction {

    /**
   * 
   */
    private static final long serialVersionUID = 1L;

    // private static final Logger logger = Logger.getLogger(AppShopInfoAction.class);
    private static Logger logger = LoggerFactory.getLogger(AppShopInfoAction.class);

    @Resource(name = "shopInfoService")
    private ShopInfoService shopInfoService;

    private String shopId;

    // private String productImgServerUrl = ConfigurationUtil.getString("PRODUCT_IMG_PATH");


    /**
     * 获取店铺信息 输入参数: shopid
     * 
     * @return
     */
    public void getShopInfo() {
        ShopInfo shopInfo = null;

        String code = "200";
        String message = "";
        try {
            JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
            if (null != jsonParams && !jsonParams.isEmpty()) {
                shopId = jsonParams.containsKey("shopid") ? jsonParams.getString("shopid") : null;
            }
            if (shopId == null || shopId.isEmpty()) {
                logger.error("查询店铺信息参数为空");
                code = "0";
                message = "传入的shopid为空,查询失败!";
            } else {
                shopInfo = shopInfoService.queryShopInfoForApp(Integer.valueOf(shopId));
                message = "查询店铺id=" + shopId + "的信息成功!";
            }

            if (shopInfo == null) {
                shopInfo = new ShopInfo();
            }
            shopInfo.setCode(code);
            if (shopInfo.getShopId() != null) {
                // TODO 暂时这块没有非常明确的关联关系可以将该集合加载出来,后续需要完善
                List<ShopBannerImage> bannerList = new ArrayList<ShopBannerImage>();
                ShopBannerImage image = new ShopBannerImage();
                image.setBannerType("暂无");
                image.setId("1234");
                image.setImageUrl("http://10.1.0.213:82/product/banner/bannerImage.jpg");
                bannerList.add(image);
                shopInfo.setBannerImages(bannerList);
                if (StringUtils.isBlank(shopInfo.getLogoPath())) {
                    shopInfo.setLogoPath(ConfigurationUtil.getString("PRODUCT_IMG_PATH")
                            + shopInfo.getLogoPath());
                }
                if (StringUtils.isBlank(shopInfo.getPhyshopImagePath())) {
                    shopInfo.setLogoPath(ConfigurationUtil.getString("PRODUCT_IMG_PATH")
                            + shopInfo.getPhyshopImagePath());
                }
            } else {
                message = "查询成功,但是并不存在shopId=" + shopId + "的数据记录!";
            }
        } catch (Exception e) {
            logger.error("查询shopId=" + shopId + "的信息发生异常,报错信息如下=" + e.getMessage(),e);

            message = "查询shopId=" + shopId + "的信息发生异常!";
        }
        shopInfo.setMessage(message);
        printJsonString(shopInfo);
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

}
