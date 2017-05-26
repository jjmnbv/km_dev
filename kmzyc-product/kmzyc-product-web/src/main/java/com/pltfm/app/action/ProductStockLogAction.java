package com.pltfm.app.action;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.pltfm.app.service.ProductService;
import com.pltfm.app.service.ProductSkuService;
import com.pltfm.app.service.ProductStockLogService;
import com.pltfm.app.service.ProductStockService;
import com.pltfm.app.service.StockOutDetailService;
import com.pltfm.app.service.StockOutService;
import com.pltfm.app.service.ViewProductSkuService;
import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.ProductImage;
import com.pltfm.app.vobject.ProductSku;
import com.pltfm.app.vobject.ProductStock;
import com.pltfm.app.vobject.ProductStockLog;
import com.pltfm.app.vobject.StockOut;
import com.pltfm.app.vobject.StockOutDetail;
import com.pltfm.app.vobject.ViewProductSku;
import com.kmzyc.commons.page.Page;


@Component("productStockLogAction")
@Scope("prototype")
public class ProductStockLogAction extends BaseAction {

    @Resource
    private ProductStockService productStockService;

    @Resource
    private StockOutService stockOutService;

    @Resource
    private StockOutDetailService stockOutDetailService;

    @Resource
    private ProductService productService;

    @Resource
    private ProductStockLogService productStockLogService;

    @Resource
    private ViewProductSkuService viewProductSkuService;

    @Resource
    private ProductSkuService productSkuService;

    private Long warehouseId;

    private Integer userId;

    private StockOut stockOut = new StockOut();// 出库单

    private Long stockId;

    private ProductStockLog stockLog = new ProductStockLog();

    private String productNo;  // 产品编号

    private String skuCode;// 产品skuCode

    private Product product;// 产品对象

    private Page page = new Page();

    // 视图对象
    ViewProductSku viewProductSku;

    //出库详情信息
    private List<StockOutDetail> stockOutDetailList;

    private int tabNum;//选显卡的选中编号

    private List<ProductImage> imageList;

    private ProductSku productSku;

    private ProductImage image;

    private String areaName;

    private ProductStock productStock; //库存信息

    private Long stockOutId;//出库单主单id

    private StockOutDetail stockOutDetail = new StockOutDetail();// 出库明细单

    private String billNo;//单据号

    private Integer stockOutNotExsist;

    public String queryProductStockLog() {
        try {
            // 根据传进来的page，log 实体进行实体的查询
            productStockLogService.selectList(stockLog, page);
        } catch (Exception e) {
            logger.error("列表失败，", e);
            return ERROR;
        }

        setStockLogOpTypeMap();
        setStockLogBillTypeMap();
        setWarehouseMap();
        return SUCCESS;
    }

    //根据产品编号查询对应的信息
    public String queryProductInfoByProductNo() {
        try {
            product = productService.queryProductByProductNo(productNo);
            if (product == null) {
                stockOutNotExsist = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 根据产品的skuCode 查询出相应的信息
     *
     * @return
     */
    public String findSkuInfoBySkuCode() {
        try {
            viewProductSku = viewProductSkuService.findByProductSkuCode(skuCode);
            ProductSku productSku = productSkuService.findProductSkuByCode(skuCode);
            if (viewProductSku == null || productSku == null) {
                stockOutNotExsist = 1;
            }
        } catch (Exception e) {
            logger.error("根据产品的skuCode 查询出相应的信息失败, ", e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 根据产品的库存id查询对应的库存信息
     *
     * @return
     */
    public String queryProductStockByStockId() {
        try {
            productStock = productStockService.findProductStockById(stockId);
            if (productStock == null) {
                stockOutNotExsist = 1;
            }
        } catch (Exception e) {
            logger.error("根据产品的库存id查询对应的库存信息失败, ", e);
            return ERROR;
        }

        setWarehouseMap();//仓库信息
        return SUCCESS;
    }

    /**
     * 根据出库单主键查询出库单详情信息
     *
     * @return
     */
    public String queryStockOutDetailByStockOutId() {
        if (billNo.indexOf("S") > -1) {
            // 出库明细单集合
            try {
                stockOut = stockOutService.findStockOutByNo(billNo);
                stockOutDetailList = stockOutDetailService.findStockOutDetailList(page,
                        stockOutDetail, stockOut.getStockOutId());

                stockOut = stockOutService.findStockOut(stockOut.getStockOutId());
                if (stockOut == null) {
                    stockOutNotExsist = 1;
                }
            } catch (Exception e) {
                logger.error("根据出库单主键查询出库单详情信息失败，", e);
                return ERROR;
            }

            // 审核状态和仓库信息
            setStockOutStatusMap();// 状态信息
            setStockOutTypeMap();//出库单类型
            setWarehouseMap();// 所有仓库信息
            // 设置默认仓库选中项
            if (stockOut != null) {
                setWarehouseId(stockOut.getWarehouseId());
                //设置经手人选中项
                setUserId(stockOut.getUserId());
            }
        }
        return SUCCESS;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ProductStockLog getStockLog() {
        return stockLog;
    }

    public void setStockLog(ProductStockLog stockLog) {
        this.stockLog = stockLog;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ViewProductSku getViewProductSku() {
        return viewProductSku;
    }

    public void setViewProductSku(ViewProductSku viewProductSku) {
        this.viewProductSku = viewProductSku;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public List<ProductImage> getImageList() {
        return imageList;
    }

    public void setImageList(List<ProductImage> imageList) {
        this.imageList = imageList;
    }

    public ProductImage getImage() {
        return image;
    }

    public void setImage(ProductImage image) {
        this.image = image;
    }

    public ProductSku getProductSku() {
        return productSku;
    }

    public void setProductSku(ProductSku productSku) {
        this.productSku = productSku;
    }

    public int getTabNum() {
        return tabNum;
    }

    public void setTabNum(int tabNum) {
        this.tabNum = tabNum;
    }

    public ProductStock getProductStock() {
        return productStock;
    }

    public void setProductStock(ProductStock productStock) {
        this.productStock = productStock;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public Long getStockOutId() {
        return stockOutId;
    }

    public void setStockOutId(Long stockOutId) {
        this.stockOutId = stockOutId;
    }

    public List<StockOutDetail> getStockOutDetailList() {
        return stockOutDetailList;
    }

    public void setStockOutDetailList(List<StockOutDetail> stockOutDetailList) {
        this.stockOutDetailList = stockOutDetailList;
    }

    public StockOutDetail getStockOutDetail() {
        return stockOutDetail;
    }

    public void setStockOutDetail(StockOutDetail stockOutDetail) {
        this.stockOutDetail = stockOutDetail;
    }

    public StockOut getStockOut() {
        return stockOut;
    }

    public void setStockOut(StockOut stockOut) {
        this.stockOut = stockOut;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getStockOutNotExsist() {
        return stockOutNotExsist;
    }

    public void setStockOutNotExsist(Integer stockOutNotExsist) {
        this.stockOutNotExsist = stockOutNotExsist;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }
}
