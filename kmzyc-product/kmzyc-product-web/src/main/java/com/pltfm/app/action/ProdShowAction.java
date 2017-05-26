package com.pltfm.app.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.ProductService;
import com.pltfm.app.vobject.Product;
import com.kmzyc.commons.page.Page;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller(value = "prodShowAction")
@Scope(value = "prototype")
public class ProdShowAction extends ActionSupport implements ModelDriven {

    private Page page;

    @Resource
    private ProductService productService;

    //产品实体
    private Product product;

    /**
     * 根据产品Id查看产品详情
     *
     * @return
     */
    public String showIntor() {
        try {
            product = productService.findProductById(new Long(153));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }


    /**
     * 更改clob大数据
     *
     * @return
     */
    public String saveIntor() {
        try {
            productService.updateProductById(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.showIntor();
    }

    @Override
    public Object getModel() {

        return null;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}