package com.pltfm.cms.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.kmzyc.commons.page.Page;
import com.kmzyc.supplier.model.ShopCategorys;
import com.pltfm.app.service.ShopCategorysService;
import com.pltfm.app.service.ViewProductInfoService;
import com.pltfm.app.util.Constants;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.vobject.ViewProductInfo;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsWindowDataService;
import com.pltfm.cms.vobject.CmsWindowData;

@Component(value = "shopProductAction")
@Scope("prototype")
public class ShopProductAction extends com.pltfm.app.action.BaseAction {

	private static Logger logger = LoggerFactory.getLogger(ShopProductAction.class);

    private Page page;
    // 跳转的页数
    private int pageNum = 0;
    private String productNameOrCode;//商品名称或编码，查询
    private String productSkuIds;//skuID
    private String productIds;//产品id
    private String[] dwindowDataIdArr;//窗口数据主键id
    private ViewProductInfo selectViewProductInfo = new ViewProductInfo();//查询
    private CmsWindowData cmsWindowData;
    private List<Integer> sorts;//保存排序
    private List<Integer> dwindowDataId;//保存
    private String[] sortsArr;
    private String userDefinedName;//自定义数据名称
    private long shopI;//店铺id
    private String shopCategoryParentId;
    private String categoryString;
    private Integer windowId;
    private Integer show;//用来区分窗口显示1是显示，0是隐藏
    private String supplierId;
    private String dwindowDataIds;
    private String rtnMessage; // 信息提示
    private Integer status;//是否显示标题
    @Resource
    private CmsWindowDataService cmsWindowDataService;

    @Resource
    private ViewProductInfoService viewProductInfoService;

    @Resource(name = "shopCategorysService")
    private ShopCategorysService shopCategoryService;

    @Resource(name = "shopCategorysService")
    private ShopCategorysService shopCategorysService;

    public String showListMain() throws Exception {
        if (page == null) page = new Page();
        try {
            List<CmsWindowData> cmList = cmsWindowDataService.selectUserDefinedNameByWindId(windowId);
            if (CollectionUtils.isNotEmpty(cmList)) {
                userDefinedName = cmList.get(0).getUser_defined_name();
                status = cmList.get(0).getStatus();
            }
        } catch (Exception e) {
            logger.error("查询产品标签名字出现异常" + e.getMessage(), e);
            throw e;
        }
        return SUCCESS;
    }

    /**
     * 查询产品信息
     */
    public String showListOne() throws Exception {

        try {
            if (page == null) page = new Page();
            selectViewProductInfo.setShopCode(supplierId);//供应商id
            //setShopCategoryList(shopId);
            List<ShopCategorys> ShopCategorysList = shopCategorysService.getAllShopCategorysForTree(shopI);
            categoryString = JSON.toJSONString(ShopCategorysList);
            if (pageNum == 0) {
                viewProductInfoService.queryForPage1(selectViewProductInfo, page);

            } else {
                page.setPageNo(pageNum);
                viewProductInfoService.queryForPage1(selectViewProductInfo, page);
            }
            //if(selectViewProductInfo.getShopCategoryId()!=null && selectViewProductInfo.getShopCategoryId()!=0){//查询类目
            //	List<ShopCategorys> list=shopCategoryService.queryShopCategoryListByParentId(shopId, Long.parseLong(shopCategoryParentId));
            //ServletActionContext.getRequest().setAttribute("childrenList", list);
            //}else{
            //ServletActionContext.getRequest().setAttribute("childrenList", new ArrayList());
            //}

        } catch (Exception e) {
            logger.error("查询产品信息列表出现异常" + e.getMessage(), e);
            throw e;
        }
        return SUCCESS;
    }

    /**
     * 查询窗口数据列表
     */
    public String showListTwo() throws Exception {
        if (rtnMessage != null) {
            if (rtnMessage.equalsIgnoreCase("update"))
                setRtnMessage("保存成功！");
            if (rtnMessage.equalsIgnoreCase("updateFail"))
                setRtnMessage("保存失败！");
            if (rtnMessage.equalsIgnoreCase("err"))
                setRtnMessage("输入的栏目标签不合法！");
        }
        try {
            List<CmsWindowData> cmList = cmsWindowDataService.selectUserDefinedNameByWindId(windowId);
            if (CollectionUtils.isNotEmpty(cmList)) {
                userDefinedName = cmList.get(0).getUser_defined_name();
                status = cmList.get(0).getStatus();
            }

            if (page == null) page = new Page();
            selectViewProductInfo.setWindowId(windowId);
            if (pageNum == 0) {
                viewProductInfoService.queryForPagePro(selectViewProductInfo, page);

            } else {
                page.setPageNo(pageNum);
                viewProductInfoService.queryForPagePro(selectViewProductInfo, page);
            }
        } catch (Exception e) {
            logger.error("查询窗口数据信息列表出现异常" + e.getMessage(), e);
            throw e;
        }
        return SUCCESS;
    }

    /**
     * 添加窗口数据
     */
    public String addProdcutRecomment() throws Exception {

        try {
            CmsWindowData addCmsWindowData = new CmsWindowData();
            //SysUser sysuser = (SysUser)session.get("sysUser");
            //Integer siteId =(Integer)session.get("siteId");
            addCmsWindowData.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());//创建时间
            addCmsWindowData.setCreated(Constants.USER_B2B_ID);// 创建人
            addCmsWindowData.setSiteId(Constants.SET_B2B_ID);// 站点主键
            addCmsWindowData.setDataType(Integer.parseInt("0"));//类型
            addCmsWindowData.setWindowId(windowId);//主窗口Id
            boolean flg = cmsWindowDataService.addProdcutRecommentService(addCmsWindowData, productSkuIds, productIds);
            if (flg) {//成功
                getResponse().getWriter().print("1");
            } else {
                getResponse().getWriter().print("0");
            }
        } catch (Exception e) {
            logger.error("给窗口添加产品出现异常！" + e.getMessage(), e);
            throw e;
        }
        return null;
    }

    /**
     * 根据窗口主键删除信息
     */
    public String delProductRecommendAction() throws Exception {
        try {
            boolean flg = cmsWindowDataService.delProdcutRecommentService(dwindowDataIds);
            if (flg) {//删除成功
                getResponse().getWriter().print("1");
            } else {
                getResponse().getWriter().print("0");
            }
        } catch (Exception e) {
            logger.error("删除窗口产品数据出现异常！" + e.getMessage(), e);
            throw e;
        }
        return null;
    }

    public String updateSorts1() throws Exception {
        try {
            CmsWindowData addCmsWindowData = new CmsWindowData();
            if (1 == show) {

                //SysUser sysuser = (SysUser)session.get("sysUser");
                //Integer siteId =(Integer)session.get("siteId");
                addCmsWindowData.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());//创建时间
                addCmsWindowData.setCreated(Constants.USER_B2B_ID);// 创建人
                addCmsWindowData.setSiteId(Constants.SET_B2B_ID);// 站点主键
                addCmsWindowData.setDataType(Integer.parseInt("6"));//类型
                addCmsWindowData.setWindowId(windowId);//主窗口Id
                addCmsWindowData.setStatus(status);
                //	HttpServletRequest request = ServletActionContext.getRequest();
                //String newuserDefinedName = request.getParameter("userDefinedName"); //获取页面上的名称
                //	newuserDefinedName = URLDecoder.decode(newuserDefinedName, "UTF-8"); //进行解码
                addCmsWindowData.setUser_defined_name(userDefinedName);
                boolean flg = PathConstants.checkKeyWords(userDefinedName);//验证输入的栏目标签是否合法
                if (flg) {
                    rtnMessage = "err";
                    return showListTwo();
                }
            } else {
                addCmsWindowData = null;
            }
            List<CmsWindowData> updateSorList = new ArrayList<CmsWindowData>();
            if (dwindowDataIdArr != null) {
                //String sortsArr1[]=sortsArr.split(",");
                //String dwindowDataIdArr[]=dwindowDataIds.split(",");
                CmsWindowData cmsWindowData = null;
                int length = dwindowDataIdArr.length;
                for (int i = 0; i < length; i++) {
                    cmsWindowData = new CmsWindowData();
                    if ((null != sortsArr && StringUtils.isNotBlank(sortsArr[i].trim()))) {
                        cmsWindowData.setSort(Integer.parseInt(sortsArr[i].trim()));
                    }
                    cmsWindowData.setWindowDataId(Integer.parseInt(dwindowDataIdArr[i].trim()));
                    updateSorList.add(cmsWindowData);
                    //if(Long.valueOf(dwindowDataIdArr[i]).intValue()!=0){

                    //}
                }

            }

            boolean flg = cmsWindowDataService.updateSorts(updateSorList, addCmsWindowData);
            if (flg) {//修改排序成功
                //userDefinedName=cmsWindowDataService.selectUserDefinedNameByWindId(windowId);
                //	getResponse().getWriter().print("1,"+userDefinedName);
                rtnMessage = "update";
            } else {
                //getResponse().getWriter().print("0");
                rtnMessage = "updateFail";
            }
        } catch (Exception e) {
            logger.error("修改窗口产品数据排序出现异常！" + e.getMessage(), e);
            throw e;
        }

        return showListTwo();
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getProductNameOrCode() {
        return productNameOrCode;
    }

    public void setProductNameOrCode(String productNameOrCode) {
        this.productNameOrCode = productNameOrCode;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public ViewProductInfo getSelectViewProductInfo() {
        return selectViewProductInfo;
    }

    public void setSelectViewProductInfo(ViewProductInfo selectViewProductInfo) {
        this.selectViewProductInfo = selectViewProductInfo;
    }

    public CmsWindowData getCmsWindowData() {
        return cmsWindowData;
    }

    public void setCmsWindowData(CmsWindowData cmsWindowData) {
        this.cmsWindowData = cmsWindowData;
    }

    public String getProductSkuIds() {
        return productSkuIds;
    }

    public void setProductSkuIds(String productSkuIds) {
        this.productSkuIds = productSkuIds;
    }

    public String getProductIds() {
        return productIds;
    }

    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }

    public List<Integer> getSorts() {
        return sorts;
    }

    public void setSorts(List<Integer> sorts) {
        this.sorts = sorts;
    }

    public List<Integer> getDwindowDataId() {
        return dwindowDataId;
    }

    public void setDwindowDataId(List<Integer> dwindowDataId) {
        this.dwindowDataId = dwindowDataId;
    }

    public String getUserDefinedName() {
        return userDefinedName;
    }

    public void setUserDefinedName(String userDefinedName) {
        this.userDefinedName = userDefinedName;
    }

    public String getShopCategoryParentId() {
        return shopCategoryParentId;
    }

    public void setShopCategoryParentId(String shopCategoryParentId) {
        this.shopCategoryParentId = shopCategoryParentId;
    }

    public String getCategoryString() {
        return categoryString;
    }

    public void setCategoryString(String categoryString) {
        this.categoryString = categoryString;
    }

    public Integer getWindowId() {
        return windowId;
    }

    public void setWindowId(Integer windowId) {
        this.windowId = windowId;
    }

    public Integer getShow() {
        return show;
    }

    public void setShow(Integer show) {
        this.show = show;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getDwindowDataIds() {
        return dwindowDataIds;
    }

    public void setDwindowDataIds(String dwindowDataIds) {
        this.dwindowDataIds = dwindowDataIds;
    }

    public String[] getDwindowDataIdArr() {
        return dwindowDataIdArr;
    }

    public void setDwindowDataIdArr(String[] dwindowDataIdArr) {
        this.dwindowDataIdArr = dwindowDataIdArr;
    }

    public String[] getSortsArr() {
        return sortsArr;
    }

    public void setSortsArr(String[] sortsArr) {
        this.sortsArr = sortsArr;
    }

    public String getRtnMessage() {
        return rtnMessage;
    }

    public void setRtnMessage(String rtnMessage) {
        this.rtnMessage = rtnMessage;
    }

    public long getShopI() {
        return shopI;
    }

    public void setShopI(long shopI) {
        this.shopI = shopI;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
