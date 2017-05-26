package com.pltfm.app.action;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.enums.CategoryAttrInputType;
import com.pltfm.app.service.CategoryAttrService;
import com.pltfm.app.service.ProductSkuAttrDraftService;
import com.pltfm.app.service.ProductSkuAttrService;
import com.pltfm.app.vobject.CategoryAttr;
import com.pltfm.app.vobject.Message;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * 产品类目属性管理Action类
 *
 * @author humy
 * @since 2013-7-10
 */
@Controller("categoryAttrAction")
@Scope(value = "prototype")
public class CategoryAttrAction extends BaseAction {

    private CategoryAttr categoryAttr;

    @Resource
    private CategoryAttrService categoryAttrService;

    @Resource
    private ProductSkuAttrService productSkuAttrService;

    @Resource
    private ProductSkuAttrDraftService productSkuAttrDraftService;

    /**
     * 类目属性主键数组
     */
    private String[] delId;

    /**
     * 分页类
     */
    private Page page;

    /**
     * 返回结果
     */
    private Message message = new Message();

    private String rtnMsg;

    private Long attrValueId;

    /**
     * 查询类目属性列表
     *
     * @return String 返回值
     */
    public String queryCategoryAttrList() {
        try {
            if (page == null) {
                page = new Page();
            }
            page = categoryAttrService.queryCategoryAttrList(page, categoryAttr);
        } catch (Exception e) {
            logger.error("查询类目属性列表 error ", e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 进入类目属性编辑界面
     *
     * @return String 返回值
     */
    public String showCategoryAttr() {
        try {
            if (categoryAttr != null && categoryAttr.getCategoryAttrId() != null) {
                categoryAttr = categoryAttrService.showCategoryAttr(categoryAttr);
            }
        } catch (Exception e) {
            logger.error("进入类目属性编辑界面 error ", e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 保存类目属性
     *
     * @return String
     * @throws
     */
    public String saveCategoryAttr() throws Exception {
        if (categoryAttr == null) {
            return ERROR;
        }

        if (categoryAttr.getInputType().equals(CategoryAttrInputType.TEXT.getType())) {
            categoryAttr.setIsNav(0);
        }

        try {
            if (categoryAttr.getCategoryAttrId() != null) {
                categoryAttrService.updateCategoryAttr(categoryAttr);
            } else {
                categoryAttrService.addCategoryAttr(categoryAttr);
            }
        } catch (Exception e) {
            logger.error("保存类目属性 error ", e);
            return ERROR;
        }
        message.setModule("保存类目属性");
        message.setCode(0);
        return SUCCESS;
    }

    /**
     * 删除类目属性
     *
     * @return
     */
    public String deleteCategoryAttr() {
        try {
            rtnMsg = categoryAttrService.deleteByPrimaryKey(delId);
        } catch (Exception e) {
            logger.error("删除类目属性 error ", e);
        }
        message.setModule("删除类目属性");
        message.setCode(0);
        return SUCCESS;
    }

    /**
     * 查询是否重命名
     *
     * @return
     */
    public String findAttrRepeatName() {
        String msg = "";
        try {
            if (categoryAttrService.findAttrRepeatName(categoryAttr)) {
                msg = "true";
            } else {
                msg = "false";
            }
            strWriteJson(msg);
        } catch (Exception e) {
            logger.error("findAttrRepeatName error ", e);
        }
        return null;
    }

    public String findIsUsedSkuValue() {
        String msg = "";
        try {
            if (productSkuAttrService.queryByAttrValueId(attrValueId)
                    || productSkuAttrDraftService.queryByAttrValueId(attrValueId)) {
                msg = "isUsed";
            }
            strWriteJson(msg);
        } catch (Exception e) {
            logger.error("findIsUsedSkuValue error ", e);
        }

        return null;
    }

    public CategoryAttr getCategoryAttr() {
        return categoryAttr;
    }

    public void setCategoryAttr(CategoryAttr categoryAttr) {
        this.categoryAttr = categoryAttr;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String[] getDelId() {
        return delId;
    }

    public void setDelId(String[] delId) {
        this.delId = delId;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getRtnMsg() {
        return rtnMsg;
    }

    public void setRtnMsg(String rtnMsg) {
        this.rtnMsg = rtnMsg;
    }

    public Long getAttrValueId() {
        return attrValueId;
    }

    public void setAttrValueId(Long attrValueId) {
        this.attrValueId = attrValueId;
    }
}
