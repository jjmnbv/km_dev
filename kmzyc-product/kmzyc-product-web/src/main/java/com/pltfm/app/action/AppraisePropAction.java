package com.pltfm.app.action;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.Action;
import com.pltfm.app.service.AppraisePropService;
import com.pltfm.app.service.AppraiseRelaPropService;
import com.pltfm.app.service.CategoryService;
import com.pltfm.app.vobject.AppraiseProp;
import com.pltfm.app.vobject.Category;
import com.kmzyc.commons.page.Page;

@Controller("appraisePropAction")
@Scope(value = "prototype")
public class AppraisePropAction extends BaseAction {

    @Resource
    private AppraisePropService appraisePropService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private AppraiseRelaPropService appraiseRelaPropService;

    private AppraiseProp prop = new AppraiseProp();

    String rtnMessage; // 返回的信息

    String nodes;

    private Long propId;

    //删除
    private Long[] delId;

    //要绑定的类目Id
    private String categoryIds;

    private Long cateId;

    //选中的属性Id
    private String[] appPropIds;

    /**
     * 分页显示数据
     *
     * @return
     */
    public String showList() {
        try {
            appraisePropService.searchPage(page, prop, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    /**
     * 选择评价属性显示
     *
     * @return
     */
    public String propSelectList() {
        try {
            List<Long> li = null;
            if (appPropIds != null && appPropIds.length != 0) {
                li = Lists.newArrayList(appPropIds).stream()
                        .map(propId -> Long.valueOf(propId))
                        .collect(Collectors.toList());
                page.setPageSize(page.getPageSize() - li.size());
                getRequest().setAttribute("selectList", appraisePropService.findBySomePropIds(li));
            }
            appraisePropService.searchPage(page, prop, li);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    /**
     * 根据类目Id获取评价属性
     *
     * @return
     */
    public String findAppPropByCageId() {
        try {
            writeJson(appraisePropService.selectByCategoryId(cateId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 消除已绑定的评价属性
     *
     * @return
     */
    public String unBindProp() {
        try {
            appraiseRelaPropService.unBindProp(StringUtils.split(categoryIds, ","));
            strWriteJson("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存属性与类目的关系
     *
     * @return
     */
    public String saveCategoryPropRela() {
        try {
            appraiseRelaPropService.saveBatch(StringUtils.split(categoryIds, ","), appPropIds);
            strWriteJson("success");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 更新前的操作
     *
     * @return
     */
    public String toUpdate() {
        try {
            prop = appraisePropService.findByPrimaryKey(propId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    /**
     * 更新
     *
     * @return
     */
    public String update() {
        try {
            appraisePropService.updatePropAndValues(prop);
            this.setRtnMessage("修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            this.setRtnMessage("修改失败！");
        }
        prop = new AppraiseProp();
        return this.showList();
    }

    public String toAdd() {
        return Action.SUCCESS;
    }

    /**
     * 添加
     *
     * @return
     */
    public String add() {
        try {
            appraisePropService.addPropAndValues(prop);
            this.setRtnMessage("添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            this.setRtnMessage("添加失败！");
        }
        prop = new AppraiseProp();
        return this.showList();
    }

    /**
     * 删除
     *
     * @return
     */
    public String delete() {
        try {
            appraisePropService.deletePropAndValues(delId);
            this.setRtnMessage("删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            this.setRtnMessage("删除失败！");
        }
        return this.showList();
    }

    public String findAllCategory() {
        Category c = new Category();
        c.setParentId(Long.valueOf(0));
        c.setIsPhy(1);
        c.setStatus(1);

        try {
            List<Category> list = categoryService.queryCategoryList(c);
            nodes = JSON.toJSONString(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }


    public AppraiseProp getProp() {
        return prop;
    }

    public void setProp(AppraiseProp prop) {
        this.prop = prop;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getRtnMessage() {
        return rtnMessage;
    }

    public void setRtnMessage(String rtnMessage) {
        this.rtnMessage = rtnMessage;
    }

    public Long getPropId() {
        return propId;
    }

    public void setPropId(Long propId) {
        this.propId = propId;
    }

    public Long[] getDelId() {
        return delId;
    }

    public void setDelId(Long[] delId) {
        this.delId = delId;
    }

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public String getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String[] getAppPropIds() {
        return appPropIds;
    }

    public void setAppPropIds(String[] appPropIds) {
        this.appPropIds = appPropIds;
    }

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }


}
