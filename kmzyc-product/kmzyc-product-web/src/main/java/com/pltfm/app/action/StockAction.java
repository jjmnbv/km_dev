package com.pltfm.app.action;

import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Action;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.enums.MsgOperation;
import com.pltfm.app.service.CmsProductUpShelfService;
import com.pltfm.app.service.ProductRelationService;
import com.pltfm.app.service.ProductService;
import com.pltfm.app.service.ProductSkuService;
import com.pltfm.app.service.ProductStockService;
import com.pltfm.app.service.ViewProductSkuService;
import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.ProductRelationAndDetail;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductStock;
import com.pltfm.app.vobject.ViewProductSku;
import com.kmzyc.commons.page.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 仓库
 *
 * @author xkj
 */
@Controller(value = "stockAction")
@Scope(value = "prototype")
public class StockAction extends BaseAction {

    private Page page;

    private String type;

    private String msg;

    private ProductStock stock;

    private ProductStock stockForSelectPara;

    private ViewProductSku viewProductSku = new ViewProductSku();

    @Resource
    private ProductStockService stockService;

    @Resource
    private ProductSkuService productSkuService;

    @Resource
    private ProductService productService;

    @Resource
    private ViewProductSkuService viewProductSkuService;

    @Resource
    private CmsProductUpShelfService cmsProductUpShelfService;

    @Resource
    private ProductRelationService productRelationService;

    // 库存列表
    public String showList() {
        try {
            if (page == null)
                page = new Page();
            if (stockForSelectPara == null) {
                stockForSelectPara = new ProductStock();
            }

            stockService.searchPage(page, stockForSelectPara);
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }

        setWarehouseMap();
        return SUCCESS;
    }

    // 告警库存列表
    public String showAlarmList() {
        if (page == null)
            page = new Page();
        if (stockForSelectPara == null) {
            stockForSelectPara = new ProductStock();
        }

        try {
            type = "alarmlist";
            stockService.searchPageForAlarm(page, stockForSelectPara);
        } catch (Exception e) {
            logger.error("告警库存列表失败，", e);
            return ERROR;
        }

        setWarehouseMap();
        return SUCCESS;
    }

    public String toStockAdd() {
        setWarehouseForStatusMap();
        return SUCCESS;
    }

    public String toStockView() {
        try {
            setCheckedId(stock.getStockId());
            stock = stockService.findProductStockById(stock.getStockId());
        } catch (Exception e) {
            logger.error("查看告警库存失败，", e);
            return ERROR;
        }

        setWarehouseMap();
        setWarehouseStatusMap();
        setProductBrandMap();
        return SUCCESS;
    }

    public String toStockUpdate() {
        try {
            setCheckedId(stock.getStockId());
            stock = stockService.findProductStockById(stock.getStockId());
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
        setWarehouseMap();
        return SUCCESS;
    }

    // 新增产品基础信息记录
    public String addStock() {
        try {
            ResultMessage message = stockService.insertProductStock(stock, getLoginUserId(), getLoginUserName());
            if (!message.getIsSuccess())
                return ERROR;
            msg = "库存创建成功!";
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }

        stock = null;
        return showList();
    }

    public String updateStock() {
        try {
            boolean flag = stockService.updateProductStockById(stock, getLoginUserId(), getLoginUserName());
            if (!flag) {
                return ERROR;
            }
            msg = "库存修改成功!";
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }

        // 库存改变成功后，通知搜索引擎
        try {
            List<Long> skuIdList = Lists.newArrayList(stock.getSkuAttributeId());
            productService.changeProductInfoNotify(skuIdList, MsgOperation.ADD.getType());

            Product prod = productService.queryProductByProductNo(stock.getProductNo());
            List<Integer> prodIdList = Lists.newArrayList(Integer.valueOf(prod.getProductId().toString()));

            // 关联中被关联产品库存下架的刷新页面出来
            // 根据被关联的sku,查询出对应的主skuID 出来
            List<ProductRelationAndDetail> relationList = productRelationService
                    .selectProductRelationAndDetailsByRelaitonSkuId(stock.getSkuAttributeId());
            List<Long> productSkuList = relationList.stream()
                    .map(ProductRelationAndDetail::getMainSkuId)
                    .collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(productSkuList)) {
                Map<Long, ProductSku> skuMap = productSkuService.getSkuIdAndCodeMap(productSkuList);
                Set<Long> skuSet = skuMap.keySet();
                prodIdList.addAll(skuSet.stream()
                        .map(skuId -> skuMap.get(skuId).getProductId().intValue())
                        .collect(Collectors.toList()));
            }
            //刷新主产品的页面
            cmsProductUpShelfService.productUpShelfByCms(prodIdList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        stock = null;
        if ("alarmlist".equals(type)) {
            try {
                stockService.searchPageForAlarm(page, stockForSelectPara);
            } catch (Exception e) {
                e.printStackTrace();
                return ERROR;
            }
            setWarehouseMap();
            return "alarmlist";
        }
        return showList();
    }

    public String checkSku() {
        Map jsonMap = new HashMap();

        try {
            String skuCode = getRequest().getParameter("sku");
            String warehouseId = getRequest().getParameter("warehouseId");
            if (StringUtils.isBlank(warehouseId)) {
                jsonMap.put("result", "warehouseIdIsNot");
                writeJson(jsonMap);
                return null;
            }

            boolean isRight = stockService.checkProductStockBySkuCode(skuCode, Long.valueOf(warehouseId));
            jsonMap.put("result", isRight);
            if (!isRight) {
                writeJson(jsonMap);
                return null;
            }

            ProductSku sku = productSkuService.findProductSkuByCode(skuCode);
            if (sku == null) {
                writeJson(jsonMap);
                return null;
            }

            Product product = productService.findProductById(sku.getProductId());
            jsonMap.put("product", product);
            jsonMap.put("sku", sku);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("product", null);
        }
        writeJson(jsonMap);
        return null;
    }

    public String findAllSkuProduct() {
        if (page == null) {
            page = new Page();
        }
        if (viewProductSku == null) {
            viewProductSku = new ViewProductSku();
        }

        try {
            if ("stock".equals(type)) {
                viewProductSkuService.searchPageByUserId(page, viewProductSku, "", getLoginUserId());
            } else {
                viewProductSkuService.searchPageByUserId(page, viewProductSku, "ALL", getLoginUserId());
            }
            setProductStatusMap();
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }

        if ("stock".equals(type)) {
            return type;
        }
        return Action.SUCCESS;
    }

    public Page getPage() {
        return page;
    }

    public ProductStock getStock() {
        return stock;
    }

    public void setStock(ProductStock stock) {
        this.stock = stock;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ViewProductSku getViewProductSku() {
        return viewProductSku;
    }

    public void setViewProductSku(ViewProductSku viewProductSku) {
        this.viewProductSku = viewProductSku;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ProductStock getStockForSelectPara() {
        return stockForSelectPara;
    }

    public void setStockForSelectPara(ProductStock stockForSelectPara) {
        this.stockForSelectPara = stockForSelectPara;
    }

}