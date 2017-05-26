package com.kmzyc.promotion.presell.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.promotion.app.action.BaseAction;
import com.kmzyc.promotion.app.enums.AuditStatus;
import com.kmzyc.promotion.app.maps.PresellSupplierTypeMap;
import com.kmzyc.promotion.app.maps.ProductStatusMap;
import com.kmzyc.promotion.app.service.ProductSkuService;
import com.kmzyc.promotion.app.service.ViewProductSkuService;
import com.kmzyc.promotion.app.vobject.ProductSku;
import com.kmzyc.promotion.app.vobject.PromotionPresell;
import com.kmzyc.promotion.app.vobject.PromotionPresellProduct;
import com.kmzyc.promotion.app.vobject.ViewProductSku;
import com.kmzyc.promotion.presell.service.PresellInfoService;

@SuppressWarnings("unchecked")
@Controller("presellInfoAction")
@Scope(value = "prototype")
public class PresellInfoAction extends BaseAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    // 日志记录
    private Logger logger = LoggerFactory.getLogger(PresellInfoAction.class);
    // 预售信息
    private PromotionPresell promotionPresell;
    // 产品sku
    private ProductSku productSku;

    @Resource
    private ProductSkuService productSkuService;
    @Resource
    private ViewProductSkuService viewProductSkuService;
    @Resource
    private PresellInfoService presellInfoService;

    // 已经选择过的产品id；
    private String haveChoosedPro;
    // 接受到的产品实体id
    private String productArry;
    // 预售商品
    List<PromotionPresellProduct> listPresellProduct;

    /**
     * 
     * 初始化下拉列表框，跳转到预售产品新增页面
     *
     * @author Administrator
     * @return
     */
    public String toAddPresellProduct() {
        setBaseData();
        return SUCCESS;

    }

    /**
     * 查询可添加的预售商品，可选择的范围是审核通过的商品，上下架状态不限，库存不限 ，产品状态4:下架、5:系统下架、3:上架 、 -2违规下架
     * 每个商品只能同时参加一个预售活动，上一个活动全部结束之后 （预售状态为“已结束”），才可参加新的预售活动
     * 
     * @return String 返回值
     */
    public String findProductSkuForPresell() {
        try {
            page = super.getPage();
            productSkuService.queryPresellProductSkuList(page, productSku);
            // 大类,一级类目
            getBcategoryList(new Long(0));

            // 中类，二级类目
            if (productSku.getbCategoryId() != null && productSku.getbCategoryId().intValue() != 0) {
                getMcategoryList(productSku.getbCategoryId());
            }

            // 小类，三级类目
            if (productSku.getmCategoryId() != null && productSku.getmCategoryId().intValue() != 0) {
                getScategoryList(productSku.getmCategoryId());
            }
            // 设置产品的状态
            this.getRequest().setAttribute("productStatus", ProductStatusMap.getProductStatusMap());

        } catch (Exception e) {
            logger.error("查询可添加的预售商品异常 :" + e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 
     * 保存预售规则及预售产品信息
     *
     * @author Administrator
     * @return
     */
    public String savePresellProduct() {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("flag", false);
        try {
            if (listPresellProduct == null) {
                jsonMap.put("msg", "预售产品为空");
            }
            // 保存预售规则及预售商品
            promotionPresell.setCreateUser(Long.valueOf(this.getLoginUserId()));
            Long presellId =
                    presellInfoService.savePresellRuleProduct(promotionPresell, listPresellProduct);
            if (presellId != null) {
                jsonMap.put("msg", "保存预售商品成功");
                jsonMap.put("flag", true);
                jsonMap.put("presellId", presellId);
            } else {
                jsonMap.put("msg", "保存预售商品失败");
            }
        } catch (Exception e) {
            logger.error("查询可添加的预售商品异常 :" + e);
            jsonMap.put("msg", "保存预售商品失败");
            return ERROR;
        } finally {
            writeJson(jsonMap);
        }
        return null;
    }

    /**
     * ajax 方法，将选择的预售商品 转化为table表格返回值
     * 
     * @return
     * @throws Exception
     * @throws NumberFormatException
     */
    public String getPresellProductReturnTable() {
        StringBuffer printTable = new StringBuffer();
        List<ViewProductSku> listProduct = new ArrayList<ViewProductSku>();
        try {
            if (StringUtils.isNotBlank(productArry)) {
                productArry = productArry.substring(0, productArry.lastIndexOf(","));
                // 查找产品信息
                // int index = productArry.indexOf(",");
                // if (index > 0) {
                // String haveChoosedPros[] = productArry.split(",");
                // for (String a : haveChoosedPros) {
                // old.add(a);
                // }
                // }
                listProduct = viewProductSkuService.queryProductSkuBycodes(productArry);
            }
            int i = 0;
            for (ViewProductSku viewProductSku : listProduct) {
                printTable
                        .append("<tr id=tr")
                        .append(viewProductSku.getProductSkuId())
                        .append("><input type='hidden' name='listPresellProduct[")
                        .append(i)
                        .append("].productSkuId' value=")
                        .append(viewProductSku.getProductSkuId())
                        .append("><td align='center'><span style='display:none' name='haveChoosedPro'>")
                        .append(viewProductSku.getProductSkuId())
                        .append("</span>")
                        .append(viewProductSku.getProductTitle())
                        .append("</td><td align='center'>")
                        .append(viewProductSku.getProductSkuCode())
                        .append("</td><td align='center'>")
                        .append(viewProductSku.getBrandName())
                        .append("</td><td align='center' class='price_class'>")
                        .append(viewProductSku.getPrice())
                        .append("</td><td align='center'>")
                        .append(viewProductSku.getStockQuality())
                        .append("</td><td align='center'><input type='text' size='8' class='presell_price' name='listPresellProduct[")
                        .append(i)
                        .append("].presellPrice' onkeyup='javascript:checkInputIntFloat(this);' ></input></td>")
                        .append("<td align='center'><input type='text' size='8' class='deposit_price' name='listPresellProduct[")
                        .append(i)
                        .append("].depositPrice' onkeyup='javascript:checkInputIntFloat(this);' ></input></td>")
                        .append("<td align='center'><input type='text' size='8' class='presell_stock' name='listPresellProduct[")
                        .append(i)
                        .append("].presellStock' onkeyup='javascript:checkInputInt(this);' ></input></td> </tr>");
                i++;
            }
        } catch (Exception e) {
            logger.error("将选择的预售商品 转化为table表格返回值", e);
        }
        this.writeJson(printTable);
        return null;
    }

    public String toUpdatePresellRule() {
        if (promotionPresell.getPresellId() == null) {
            return ERROR;
        }
        try {
            // 查询预售活动详细信息
            promotionPresell =
                    presellInfoService.findPresellInfoDetailById(promotionPresell.getPresellId());
            Date depositStartTime = promotionPresell.getDepositStartTime();
            if (depositStartTime == null) {
                // 取当前系统时间
                promotionPresell.setDepositStartTime(new Date());
            }
            setBaseData();
        } catch (Exception e) {
            logger.error("查询可添加的预售商品异常 :" + e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 
     * 修改预售规则
     *
     * @author Administrator
     * @return
     */
    public String updatePresellRule() {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("flag", false);
        if (promotionPresell == null || promotionPresell.getPresellId() == null) {
            jsonMap.put("msg", "预售信息不存在！");
            return null;
        }
        try {
            // 修改预售规则
            if (promotionPresell.getByLimit() == null) {
                promotionPresell.setByLimit(0L);
            }
            // 初始预售数为空，则默认为0
            if (promotionPresell.getInitialPresellNum() == null) {
                promotionPresell.setInitialPresellNum(0L);
            }
            if (AuditStatus.NOT_THROUGH_AUDIT.getStatus().equals(
                    promotionPresell.getAuditStatus().toString())) {
                // 审核不通过，修改预售规则之后，将审核状态改为未审核
                promotionPresell.setAuditStatus(Integer.valueOf(AuditStatus.UNAUDIT.getStatus()));
            }
            promotionPresell.setModifUser(Long.valueOf(this.getLoginUserId()));
            presellInfoService.updatePresellRule(promotionPresell);
            jsonMap.put("flag", true);
            jsonMap.put("msg", "修改预售规则成功！");
        } catch (Exception e) {
            logger.error("修改预售规则异常 :" + e);
            jsonMap.put("msg", "修改预售规则失败");
            return null;
        } finally {
            writeJson(jsonMap);
        }
        return null;
    }

    /**
     * 
     * 初始化页面下拉框基本信息
     *
     * @author Administrator
     */
    private void setBaseData() {
        this.getRequest().setAttribute("presellSupplierTypeMap", PresellSupplierTypeMap.getMap());
    }

    public PromotionPresell getPromotionPresell() {
        return promotionPresell;
    }

    public void setPromotionPresell(PromotionPresell promotionPresell) {
        this.promotionPresell = promotionPresell;
    }

    public ProductSku getProductSku() {
        return productSku;
    }

    public void setProductSku(ProductSku productSku) {
        this.productSku = productSku;
    }

    public String getHaveChoosedPro() {
        return haveChoosedPro;
    }

    public void setHaveChoosedPro(String haveChoosedPro) {
        this.haveChoosedPro = haveChoosedPro;
    }

    public String getProductArry() {
        return productArry;
    }

    public void setProductArry(String productArry) {
        this.productArry = productArry;
    }

    public List<PromotionPresellProduct> getListPresellProduct() {
        return listPresellProduct;
    }

    public void setListPresellProduct(List<PromotionPresellProduct> listPresellProduct) {
        this.listPresellProduct = listPresellProduct;
    }


}
