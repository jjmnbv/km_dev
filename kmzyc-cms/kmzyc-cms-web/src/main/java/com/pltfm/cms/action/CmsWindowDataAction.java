package com.pltfm.cms.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.kmzyc.supplier.model.ShopCategorys;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.service.CategoryService;
import com.pltfm.app.service.ProdBrandService;
import com.pltfm.app.service.ShopCategorysService;
import com.pltfm.app.service.ViewProductInfoService;
import com.pltfm.app.service.ViewPromotionService;
import com.pltfm.app.util.AjaxUtil;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.ViewProductInfo;
import com.pltfm.app.vobject.ViewPromotion;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsInformactionTypeService;
import com.pltfm.cms.service.CmsLogService;
import com.pltfm.cms.service.CmsWindowDataService;
import com.pltfm.cms.service.CmsWindowService;
import com.pltfm.cms.service.LotteryLuckDrawService;
import com.pltfm.cms.service.LotteryPrizeService;
import com.pltfm.cms.util.ReturnResult;
import com.pltfm.cms.vobject.CmsInformationType;
import com.pltfm.cms.vobject.CmsLog;
import com.pltfm.cms.vobject.CmsWindow;
import com.pltfm.cms.vobject.CmsWindowData;
import com.pltfm.cms.vobject.UploadFile;
import com.pltfm.sys.bean.SysUserBean;
import com.pltfm.sys.model.SysUser;

/**
 * 窗口数据Action类
 *
 * @author cjm
 * @since 2013-9-3
 */
@Scope(value = "prototype")
@Component(value = "cmsWindowDataAction")
public class CmsWindowDataAction extends BaseAction {
    /**
     * 窗口数据业务逻辑层接口
     */
    @Resource(name = "cmsWindowDataService")
    private CmsWindowDataService cmsWindowDataService;

    /**
     * 活动信息业务逻辑层接口
     */
    @Resource(name = "viewPromotionService")
    private ViewPromotionService viewPromotionService;

    /**
     * 品牌信息业务逻辑层接口
     */
    @Resource(name = "prodBrandService")
    private ProdBrandService prodBrandService;

    /**
     * 窗口业务逻辑层接口
     */
    @Resource(name = "cmsWindowService")
    private CmsWindowService cmsWindowService;
    /**
     * 抽奖活动逻辑层接口
     */
    @Resource(name = "lotteryLuckDrawService")
    private LotteryLuckDrawService lotteryLuckDrawService;
    /**
     * 奖品逻辑层接口
     */
    @Resource(name = "lotteryPrizeService")
    private LotteryPrizeService lotteryPrizeService;

    /**
     * 类目业务逻辑层接口
     */
    @Resource(name = "categoryService")
    private CategoryService categoryService;
    /**
     * 产品视图业务逻辑接口
     */
    @Resource(name = "viewProductInfoService")
    private ViewProductInfoService viewProductInfoService;

    /**
     * 咨询类别业务逻辑接口
     */
    @Resource(name = "cmsInformactionTypeService")
    private CmsInformactionTypeService cmsInformactionTypeService;
    @Resource(name = "cmsLogService")
    private CmsLogService cmsLogService;//cmsLogService接口
    private CmsLog cmsLog = new CmsLog();//日志实体;//日志实体
    private Integer adminType;//角色区分
//	private Integer channelType;//1表示供应商 2其它未定义


    @Resource(name = "shopCategorysService")
    private ShopCategorysService shopCategorysService;
    private String tabId = "";


    /**
     * 分页对象
     */
    private Page page;
    private String msg;

    /**
     * 数据Id
     */
    private Integer dataId;
    private String jsoncallback;

    /**
     * 产品SkuId
     */
    private Integer skuId;
    private Integer shopI;

    /**
     * 数据Id集合
     */
    private List<Integer> dataIds;
    private String checkeds;

    /**
     * 产品Id#SkuId集合
     */
    private List<String> datas;

    /**
     * 数据Id集合
     */
    private List<Integer> windowDataIds;

    private Integer windowDataId;
    /**
     * 页面Id
     */
    private Integer pageId;


    /**
     * 数据类型
     */
    private Integer dataType;

    /**
     * 查询数据类型
     */
    private Integer queryType;

    /**
     * 窗口Id
     */
    private Integer windowId;

    private CmsWindow cmsWindow;
    /**
     * 返回页面类型
     */
    private Integer backType;

    /**
     * 窗口数据集
     */
    private List<CmsWindowData> cmsWindowDatas;

    /**
     * 封装文件集
     */
    private List<UploadFile> uploadFileList;

    /**
     * 上传文件集
     */
    private List<File> files;
    /**
     * 文件类型
     */
    private List<String> filesContentType;
    /**
     * 文件名
     */
    private List<String> filesFileName;

    /**
     * 单个文件上传
     */
    private File img;
    /**
     * 单个文件类型
     */
    private String imgContentType;
    /**
     * 单个文件名
     */
    private String imgFileName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * CmsWindowData对象
     */
    private CmsWindowData cmsWindowData;

    /**
     * 风格Id
     */
    private Integer stylesId;

    private Integer picType;

    private String keywords;
    private String shopCategoryStr;

    private String promotionIds;
    //绑定二级窗口时，记录上级窗口id
    private String parentwindowId;


    public String getParentwindowId() {
        return parentwindowId;
    }

    public void setParentwindowId(String parentwindowId) {
        this.parentwindowId = parentwindowId;
    }

    public void setPromotionIds(String promotionIds) {
        this.promotionIds = promotionIds;
    }

    public List<CmsWindowData> getCmsWindowDatas() {
        return cmsWindowDatas;
    }

    public void setCmsWindowDatas(List<CmsWindowData> cmsWindowDatas) {
        this.cmsWindowDatas = cmsWindowDatas;
    }

    //日志
    private static Logger logger = LoggerFactory.getLogger(CmsWindowDataAction.class);
    // myFile属性用来封装上传的文件
    private File resumefile0, resumefile1, resumefile2, resumefile3, resumefile4;
    // myFileContentType属性用来封装上传文件的类型  
    private String resumefile0ContentType, resumefile1ContentType, resumefile2ContentType, resumefile3ContentType, resumefile4ContentType;

    // myFileFileName属性用来封装上传文件的文件名  
    private String resumefile0FileName, resumefile1FileName, resumefile2FileName, resumefile3FileName, resumefile4FileName;

    String shopPage;
    ActionContext actionContext = ActionContext.getContext();
    Map session = actionContext.getSession();

    /**
     * ajax修改排序
     */
    public void updateSort() {
        try {
            CmsWindowData data = new CmsWindowData();
            data.setWindowDataId(windowDataId);
            data.setSort(sort);
            Integer rows = cmsWindowDataService.updateSort(data);
            super.writeJson(rows);
        } catch (Exception e) {
            logger.error("CmsWindowDataAction.updateSort异常：" + e.getMessage(), e);
        }
    }

    /**
     * 单条删除数据
     */
    public String deleteData() {
        try {
            SysUser sysUser = (SysUser) session.get("sysUser");
            cmsLog.setModuleName("窗口");
            cmsLog.setDataId(dataId);
            cmsLog.setConsoleOperator(sysUser.getUserId());
            log.info(cmsLogService.del(cmsLog, windowId));

            cmsWindowDataService.deleteData(dataId);
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
            System.out.println(this.backType + "  " + this.windowId);
            if (this.backType == null) {
                return this.queryPageList();
            } else {
                return "winDetail";
            }
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger.error("CmsWindowDataAction.deleteData异常：" + e.getMessage(), e);
            return "error";
        }

    }


    /**
     * 添加窗口数据
     */
    @Token
    public String add() {
        try {
            SysUser sysuser = (SysUser) session.get("sysUser");
            Integer siteId = (Integer) session.get("siteId");
            if (dataType == 14) {//14 活动商品
                dataType = 0;
            }
            //产品类型数据
            if (dataType != 0) {
                if (dataType == 9) {
                    cmsWindowDataService
                            .addCmsWindowData(dataIds, dataType, windowId, sysuser.getUserId(),
                                    siteId);
                    CmsWindowData cmsWindowData = new CmsWindowData();
                    cmsWindowData.setWindowId(windowId);
                    cmsWindowData.setDataType(6);
                    int i = cmsWindowDataService.isCmsWindow(cmsWindowData);
                    if (i < 1) {
                        List<CmsWindowData> cmsWindow = new ArrayList<CmsWindowData>();
                        CmsWindowData cms = new CmsWindowData();
                        cms.setUser_defined_name("201");
                        cms.setUser_defined_url("即开型活动中奖");
                        cms.setUser_defined_type(0);

                        CmsWindowData cms1 = new CmsWindowData();
                        cms1.setUser_defined_name("300");
                        cms1.setUser_defined_url("抽奖活动未开始，感谢您的关注。");
                        cms1.setUser_defined_type(0);

                        CmsWindowData cms2 = new CmsWindowData();
                        cms2.setUser_defined_name("301");
                        cms2.setUser_defined_url("系统维护中，请稍后重试。");
                        cms2.setUser_defined_type(0);

                        CmsWindowData cms3 = new CmsWindowData();
                        cms3.setUser_defined_name("302");
                        cms3.setUser_defined_url("本次抽奖已经结束，感谢您的关注。");
                        cms3.setUser_defined_type(0);

                        CmsWindowData cms4 = new CmsWindowData();
                        cms4.setUser_defined_name("402");
                        cms4.setUser_defined_url("很遗憾，您不能参与本抽奖（会员等级不符合参与要求）。");
                        cms4.setUser_defined_type(0);

                        CmsWindowData cms5 = new CmsWindowData();
                        cms5.setUser_defined_name("405");
                        cms5.setUser_defined_url("很遗憾，您不能参与本抽奖（手机号码未验证）。");
                        cms5.setUser_defined_type(0);

                        CmsWindowData cms6 = new CmsWindowData();
                        cms6.setUser_defined_name("406");
                        cms6.setUser_defined_url("抽奖活动参与次数已达上限。");
                        cms6.setUser_defined_type(0);

                        CmsWindowData cms7 = new CmsWindowData();
                        cms7.setUser_defined_name("407");
                        cms7.setUser_defined_url("很抱歉，今天您已抽过奖，请明天再来！");
                        cms7.setUser_defined_type(0);

                        CmsWindowData cms8 = new CmsWindowData();
                        cms8.setUser_defined_name("411");
                        cms8.setUser_defined_url("很抱歉，会员的中奖次数已达上限！");
                        cms8.setUser_defined_type(0);

                        CmsWindowData cms9 = new CmsWindowData();
                        cms9.setUser_defined_name("408");
                        cms9.setUser_defined_url("很遗憾，您不能参与本抽奖（注册时间不符合参与要求）。");
                        cms9.setUser_defined_type(0);

                        CmsWindowData cms10 = new CmsWindowData();
                        cms10.setUser_defined_name("409");
                        cms10.setUser_defined_url("很遗憾，您不能参与本抽奖（订单消费金额不符合参与要求）。");
                        cms10.setUser_defined_type(0);

                        CmsWindowData cms11 = new CmsWindowData();
                        cms11.setUser_defined_name("410");
                        cms11.setUser_defined_url("很遗憾，您的积分不够，不能参与本抽奖，可以通过购物或者评价获取积分再来参加。");
                        cms11.setUser_defined_type(0);

                        CmsWindowData cms12 = new CmsWindowData();
                        cms12.setUser_defined_name("412");
                        cms12.setUser_defined_url("很遗憾，会员中该奖项的中奖次数已达上限。");
                        cms12.setUser_defined_type(0);

                        CmsWindowData cms13 = new CmsWindowData();
                        cms13.setUser_defined_name("413");
                        cms13.setUser_defined_url("很遗憾，活动中A天中某个奖项B次已达上限。");
                        cms13.setUser_defined_type(0);

                        CmsWindowData cms14 = new CmsWindowData();
                        cms14.setUser_defined_name("414");
                        cms14.setUser_defined_url("很遗憾，参与时间在某个时间区间才可能中奖。");
                        cms14.setUser_defined_type(0);

                        CmsWindowData cms15 = new CmsWindowData();
                        cms15.setUser_defined_name("415");
                        cms15.setUser_defined_url("很遗憾，您暂时不符合抽奖要求，详情请了解抽奖[活动规则]！");
                        cms15.setUser_defined_type(0);


                        CmsWindowData cms16 = new CmsWindowData();
                        cms16.setUser_defined_name("很遗憾未中奖!");
                        cms16.setUser_defined_url("");
                        cms16.setUser_defined_type(0);


                        CmsWindowData cms17 = new CmsWindowData();
                        cms17.setUser_defined_name("400");
                        cms17.setUser_defined_url("很遗憾，抽奖活动不存在！");
                        cms17.setUser_defined_type(0);

                        CmsWindowData cms18 = new CmsWindowData();
                        cms18.setUser_defined_name("0");
                        cms18.setUser_defined_url("很遗憾，系统内部错！");
                        cms18.setUser_defined_type(0);

                        CmsWindowData cms19 = new CmsWindowData();
                        cms19.setUser_defined_name("200");
                        cms19.setUser_defined_url("后开型活动参与成功！");
                        cms19.setUser_defined_type(0);

                        CmsWindowData cms20 = new CmsWindowData();
                        cms20.setUser_defined_name("101");
                        cms20.setUser_defined_url("很遗憾，颁奖失败！");
                        cms20.setUser_defined_type(0);

                        cmsWindow.add(cms);
                        cmsWindow.add(cms1);
                        cmsWindow.add(cms2);
                        cmsWindow.add(cms3);
                        cmsWindow.add(cms4);
                        cmsWindow.add(cms5);
                        cmsWindow.add(cms6);
                        cmsWindow.add(cms7);
                        cmsWindow.add(cms8);
                        cmsWindow.add(cms9);
                        cmsWindow.add(cms10);
                        cmsWindow.add(cms11);
                        cmsWindow.add(cms12);
                        cmsWindow.add(cms13);
                        cmsWindow.add(cms14);
                        cmsWindow.add(cms15);

                        cmsWindow.add(cms17);
                        cmsWindow.add(cms18);
                        cmsWindow.add(cms19);
                        cmsWindow.add(cms20);
                        cmsWindow.add(cms16);
                        this.cmsWindowDataService
                                .addCmsWindowData(cmsWindow, this.windowId, sysuser.getUserId(),
                                        this.uploadFileList, siteId);
                    }
                } else if (dataType == 10) {
                    this.cmsWindowDataService
                            .addWindowDatas(cmsWindowDatas, this.windowId, sysuser.getUserId(),
                                    siteId);
                } else {
                    cmsWindowDataService
                            .addCmsWindowData(dataIds, dataType, windowId, sysuser.getUserId(),
                                    siteId);
                }
                //非产品类型数据
            } else {
                cmsWindowDataService
                        .addCmsWindowData(datas, dataType, windowId, sysuser.getUserId(), siteId);
            }


            //日志记录
            cmsLog.setModuleName("窗口");
            cmsLog.setListObject(datas);
            cmsLog.setDataIds(dataIds);
            cmsLog.setDataType(dataType);
            cmsLog.setConsoleOperator(sysuser.getUserId());
            log.info(cmsLogService.add(cmsLog, windowId));

            this.addActionMessage(ConfigurationUtil.getString("add.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("add.fail"));
            logger.error("CmsWindowDataAction.add异常：" + e.getMessage(), e);
        }
        return this.queryPageList();
    }

    /**
     * 根据数据类型与数据Id查询数据
     */
    public void queryData() {
        Object obj = null;
        try {
            switch (dataType) {
                case 0:
                    ViewProductInfo viewProductInfo = new ViewProductInfo();
                    viewProductInfo.setProductId(dataId);
                    viewProductInfo.setProductSkuId(skuId);
                    obj = viewProductInfoService.selectByIdOrSkuId(viewProductInfo);
                    break;
                case 1:
                    ViewPromotion viewPromotion = viewPromotionService.selectByPrimaryKey(dataId);
                    Map typeMap = viewPromotionService.getPromotionCategory();
                    viewPromotion.setPromotionType(
                            typeMap.get(viewPromotion.getPromotionTypeId()).toString());
                    if (viewPromotion.getStartTime() != null) {

                        viewPromotion.setStartTimePage(
                                DateTimeUtils.getDateTime(viewPromotion.getStartTime()));
                    }
                    if (viewPromotion.getEndTime() != null) {
                        viewPromotion.setEndTimePage(
                                DateTimeUtils.getDateTime(viewPromotion.getEndTime()));
                    }
                    obj = viewPromotion;
                    break;
                case 2:
                    obj = categoryService.selectByPrimaryKey(dataId);
                    break;
                case 3:
                    obj = prodBrandService.selectByPrimaryKey(dataId);
                    break;
                case 4:
                    obj = cmsWindowService.selectByPrimaryKey(dataId);
                    break;
                case 7:
                    obj = categoryService.selectByPrimaryKey(dataId);
                    break;
                case 9:
                    obj = lotteryLuckDrawService.selectByPrimaryKey(dataId);
                    break;
                case 10:
                    obj = lotteryPrizeService.selectByPrimaryKey(dataId);
                    break;
                case 8:
                    CmsInformationType cmsInformationType = cmsInformactionTypeService.byid(dataId);
                    //获取对应的管理员
                    sysUserMap = new HashMap();
                    SysUserBean bean = SysUserBean.instance();
                    SysUser vo = new SysUser();
                    List<SysUser> list = bean.getSysUserList(vo);
                    Map sysUser = new HashMap();
                    for (SysUser user : list) {
                        sysUser.put(user.getUserId(), user.getUserName());
                    }
                    if (cmsInformationType.getCreated() != null) {
                        System.out.println(sysUser.get(cmsInformationType.getCreated()));
                        cmsInformationType.setCreatedInfo(
                                sysUser.get(cmsInformationType.getCreated()).toString());
                    }

                    if (cmsInformationType.getModified() != null) {
                        cmsInformationType.setModifyDateInfo(
                                sysUser.get(cmsInformationType.getModified()).toString());
                    }

                    if (cmsInformationType.getCreateDate() != null) {
                        cmsInformationType.setCreateDateInfo(
                                DateTimeUtils.getDateTime(cmsInformationType.getCreateDate()));
                    }
                    if (cmsInformationType.getModifyDate() != null) {
                        cmsInformationType.setModifyDateInfo(
                                DateTimeUtils.getDateTime(cmsInformationType.getModifyDate()));
                    }
                    obj = cmsInformationType;
                    break;
                case 14:
                    List<Integer> promotionIdList = new ArrayList<Integer>();
                    if (StringUtils.isNotBlank(promotionIds)) {
                        String[] promotionIdsArray = promotionIds.split(",");
                        Integer promotionId;
                        for (int i = 0; i < promotionIdsArray.length; i++) {
                            promotionId = Integer.valueOf(promotionIdsArray[i]);
                            promotionIdList.add(promotionId);
                        }
                    }
                    obj = viewProductInfoService.queryByViewPromotionId(promotionIdList);
                    break;
            }
            super.writeJson(obj);
        } catch (Exception e) {
            logger.error("CmsWindowDataAction.queryData异常：" + e.getMessage(), e);
        }
    }

    /**
     * 根据窗口分页查询数据信息
     */
    public String queryPageList() {
        try {
            Integer siteId = (Integer) session.get("siteId");
            CmsWindowData data = new CmsWindowData();
            data.setWindowId(windowId);
            data.setDataType(queryType);
            data.setSiteId(siteId);
            page = cmsWindowDataService.queryBywindowId(data, page);

            return "querySuccess";
        } catch (Exception e) {
            logger.error("CmsWindowDataAction.queryPageList异常：" + e.getMessage(), e);
            return "queryError";
        }
    }

    /**
     * 增加自定义数据
     */
    public String addUserDefineDate() {
        return "addUserDefineDate";
    }


    //修改自定义数据
    public String updateUserDefineDate() {
        try {
            this.cmsWindowData = this.cmsWindowDataService
                    .getCmsWindowDataByPkId(this.windowDataId);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsWindowDataAction.updateUserDefineDate异常：" + e.getMessage(), e);
        }
        return "cmsWindowDataDetail";
    }

    //保存修改的自定义数据
    public void savaUserDefineDateMod() {
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            SysUser sysuser = (SysUser) session.get("sysUser");
            //封装上传的图片
            UploadFile uploadFile = new UploadFile(img, imgFileName, imgContentType);
            cmsWindowData.setModified(sysuser.getUserId());
            cmsWindowData.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
            this.cmsWindowDataService.updateCmsWindowData(this.cmsWindowData, uploadFile);

            //日志记录
            log.info(cmsLogService.update(cmsWindowData, 6));

            response.getWriter()
                    .write("<script>parent.closeOpenUserDefinedDataInfo('" + this.windowId +
                            "');</script>");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsWindowDataAction.savaUserDefineDateMod异常：" + e.getMessage(), e);
        }
    }

    /**
     * 保存自定义数据
     */
    @Token
    public String saveDate() throws Exception {
        SysUser sysuser = (SysUser) session.get("sysUser");
        uploadFileList = new ArrayList<>();
        UploadFile uploadFile;
        File file;
        //将文件封装为UploadFile对象
        if (files != null) {
            for (int i = 0; i < files.size(); i++) {
                file = files.get(i);
                if(file.length()>512000){//图片限制在500k大小
                    throw new Exception("图片超出500k大小,size = "+file.length());
                }
                uploadFile = new UploadFile(file, this.filesFileName.get(i),
                        this.filesContentType.get(i));
                //将封装好的UploadFile对象放置uploadFileList集合
                uploadFileList.add(uploadFile);
            }
        }
        Integer siteId = (Integer) session.get("siteId");


        this.cmsWindowDataService
                .addCmsWindowData(cmsWindowDatas, this.windowId, sysuser.getUserId(),
                        this.uploadFileList, siteId);
        //日志记录
        cmsLog.setModuleName("窗口");
        cmsLog.setListObject(cmsWindowDatas);
        cmsLog.setDataType(6);
        cmsLog.setConsoleOperator(sysuser.getUserId());
        cmsLogService.add(cmsLog, windowId);
        return this.queryPageList();


    }

    /**
     * 多条删除数据
     */
    @Token
    public String deleteDatas() {
        try {
            SysUser sysUser = (SysUser) session.get("sysUser");
            cmsLog.setModuleName("窗口");
            cmsLog.setDataIds(windowDataIds);
            cmsLog.setConsoleOperator(sysUser.getUserId());
            log.info(cmsLogService.del(cmsLog, windowId));


            for (int i = 0; i < windowDataIds.size(); i++) {
                cmsWindowDataService.deleteData(windowDataIds.get(i));
            }
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
            if (this.backType == null) {
                return this.queryPageList();

            } else {
                return "winDetail";
            }
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger.error("CmsWindowDataAction.deleteDatas异常：" + e.getMessage(), e);
            return "error";
        }

    }


    //店铺窗口管理

    //1.前往自定义和类目数据(根据店铺ID和店铺类目)(已废)
    public String gotoDataBindList() {
        try {
            cmsWindow = cmsWindowService.getWindowById(windowId);
            //自定义数据
            CmsWindowData data = new CmsWindowData();
            data.setWindowId(windowId);

            page = cmsWindowDataService.queryBywindowId(data, page);

        } catch (Exception e) {
            logger.error("CmsWindowDataAction.dataBindList异常：" + e.getMessage(), e);
        }
        return "dataBindList";
    }

    //1-2前往店铺添加自定义数据
    public String gotoBindData() {

        try {
            //自定义数据
            cmsWindowDatas = cmsWindowDataService.queryByWindowIdDataType(windowId, 6, null);

	/*	CmsWindowData windowDataObj=null;
        //默认为一级类目的自定义数据赋值
		for(int i=0;i<cmsWindowDataList.size();i++){
			windowDataObj=(CmsWindowData)cmsWindowDataList.get(i);
			if(windowDataObj.getSort()==0){
				cmsWindowData=windowDataObj;
			}
		}*/
            //店铺类目
            List<CmsWindowData> list = cmsWindowDataService
                    .queryByWindowIdDataType(windowId, 13, null);

            shopCategoryStr = "";
            for (CmsWindowData cmsWindowData : list) {

                ShopCategorys shopCategorys = shopCategorysService
                        .queryByPrimaryKey(cmsWindowData.getDataId());
                if (null != shopCategorys) {
                    //一级类目
                    if (shopCategorys.getParentCategoryId() == 0) {
                        shopCategoryStr += shopCategorys.getCategoryName() + ",";
                    }
                }

            }
            if (!shopCategoryStr.equals("")) {
                shopCategoryStr = shopCategoryStr.substring(0, shopCategoryStr.length() - 1);
            }


            //	this.cmsWindowData=this.cmsWindowDataService.getCmsWindowDataByPkId(this.windowDataId);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsWindowDataAction.updateUserDefineDate异常：" + e.getMessage(), e);
        }

        return "bindData";
    }

    //1-3前往修改自定义数据(已废)
    public String gotoUpdateShopData() {
        try {
            this.cmsWindowData = this.cmsWindowDataService
                    .getCmsWindowDataByPkId(this.windowDataId);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsWindowDataAction.updateUserDefineDate异常：" + e.getMessage(), e);
        }
        return "shopDataDetail";
    }

    //2.删除多条自定义数据
    public String deleteShopData() {
        try {

            if (null != dataId) {
                cmsWindowDataService.deleteData(dataId);
            } else {
                for (int i = 0; i < windowDataIds.size(); i++) {
                    cmsWindowDataService.deleteData(windowDataIds.get(i));
                }
            }


            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
            msg = "操作成功！";


        } catch (Exception e) {
            msg = "操作失败！";
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger.error("CmsWindowDataAction.deleteDatas异常：" + e.getMessage(), e);
            return "error";
        }
        return this.gotoBindData();

    }

    //3.保存自定义类目数据
    public String saveCategoryData() {
        if (null != checkeds && checkeds.length() > 0) {
            String[] str = checkeds.split(",");
            if (null != str && str.length > 0) {
                if (null == dataIds) {
                    dataIds = new ArrayList();
                }
                for (int i = 0; i < str.length; i++) {

                    dataIds.add(Integer.valueOf(str[i]));
                }
            }

        }

        try {
            //先删除
            List<CmsWindowData> list = cmsWindowDataService
                    .queryByWindowIdDataType(windowId, 13, null);

            for (CmsWindowData cmsWindowData : list) {

                cmsWindowDataService.deleteData(cmsWindowData.getWindowDataId());

            }

            //添加
            if (null != dataIds) {
                cmsWindowDataService.addCmsWindowData(dataIds, dataType, windowId, null, null);
            }

            msg = "操作成功！";
        } catch (Exception e) {
            logger.error("CmsWindowDataAction.saveCategoryData异常：" + e.getMessage(), e);
            msg = "操作失败！";
        }
        //	cmsWindowDataService.addCmsWindowData(dataIds,dataType,windowId,sysuser.getUserId(),siteId);

        return this.gotoBindData();
    }

    //1-2/3保存或修改自定义数据
    public String saveShopData() {
        try {
            //自定义数据
            uploadFileList = new ArrayList<UploadFile>();
            UploadFile uploadFile = null;
            File file = null;
            //将文件封装为UploadFile对象
            if (files != null) {
                for (int i = 0; i < files.size(); i++) {
                    file = files.get(i);
                    uploadFile = new UploadFile(file, this.filesFileName.get(i),
                            this.filesContentType.get(i));
                    //将封装好的UploadFile对象放置uploadFileList集合
                    uploadFileList.add(uploadFile);


                }
            }

            //修改
            if (null != this.cmsWindowData) {
                //封装上传的图片
                uploadFile = new UploadFile(img, imgFileName, imgContentType);
                this.cmsWindowDataService.updateCmsWindowData(this.cmsWindowData, uploadFile);

                //批量保存
            } else {
                if (null != cmsWindowDatas) {
                    this.cmsWindowDataService
                            .editCmsWindowData(cmsWindowDatas, this.uploadFileList);
                }

            }


            //店铺分类

            if (null != checkeds && checkeds.length() > 0) {
                String[] str = checkeds.split(",");
                if (null != str && str.length > 0) {
                    if (null == dataIds) {
                        dataIds = new ArrayList();
                    }
                    for (int i = 0; i < str.length; i++) {

                        dataIds.add(Integer.valueOf(str[i]));
                    }
                }

            }

            //添加
            //	if(null!=dataIds){
            //先删除
            List<CmsWindowData> list = cmsWindowDataService
                    .queryByWindowIdDataType(windowId, 13, null);
            for (CmsWindowData cmsWindowData : list) {
                cmsWindowDataService.deleteData(cmsWindowData.getWindowDataId());
            }
            if (null != dataIds) {
                cmsWindowDataService.addCmsWindowData(dataIds, dataType, windowId, null, null);
            }
            //	}


            msg = "操作成功！";
            //日志记录

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsWindowDataAction.saveShopData异常：" + e.getMessage(), e);
            msg = "操作失败！";
        }

        return this.gotoBindData();


    }
    //1.编辑店招

    public String gotoEditBanner() {
        try {
            cmsWindow = cmsWindowService.getWindowById(windowId);
            //自定义数据
            cmsWindowDatas = cmsWindowDataService
                    .queryByWindowIdDataType(cmsWindow.getWindowId(), 6, cmsWindow.getSiteId());

        } catch (Exception e) {
            logger.error("CmsWindowDataAction.gotoEditBanner异常：" + e.getMessage(), e);
        }
        return "gotoEditBanner";
    }

    //2.编辑单个广告

    public String gotoAdvTypeOne() {
        try {
            cmsWindow = cmsWindowService.getWindowById(windowId);
            //自定义数据
            cmsWindowDatas = cmsWindowDataService
                    .queryByWindowIdDataType(cmsWindow.getWindowId(), 6, cmsWindow.getSiteId());
        } catch (Exception e) {
            logger.error("CmsWindowDataAction.gotoAdvTypeOne异常：" + e.getMessage(), e);
        }
        return "gotoAdvTypeOne";
    }

    //3.编辑多个广告

    public String gotoAdvTypeTwo() {
        try {
            cmsWindow = cmsWindowService.getWindowById(windowId);
            //自定义数据
            cmsWindowDatas = cmsWindowDataService
                    .queryByWindowIdDataType(cmsWindow.getWindowId(), 6, cmsWindow.getSiteId());
        } catch (Exception e) {
            logger.error("CmsWindowDataAction.gotoAdvTypeTwo异常：" + e.getMessage(), e);
        }
        return "gotoAdvTypeTwo";
    }
    //3.编辑轮播广告

    public String gotoAdvTypeThree() {
        try {
            Integer aa = shopI;
            cmsWindow = cmsWindowService.getWindowById(windowId);
            //自定义数据
            cmsWindowDatas = cmsWindowDataService
                    .queryByWindowIdDataType(cmsWindow.getWindowId(), 6, cmsWindow.getSiteId());
        } catch (Exception e) {
            logger.error("CmsWindowDataAction.gotoAdvTypeThree异常：" + e.getMessage(), e);
        }
        return "gotoAdvTypeThree";
    }

//3.编辑四个广告

    public String gotoAdvTypeFour() {
        try {
            Integer aa = shopI;
            cmsWindow = cmsWindowService.getWindowById(windowId);
            //自定义数据
            cmsWindowDatas = cmsWindowDataService
                    .queryByWindowIdDataType(cmsWindow.getWindowId(), 6, cmsWindow.getSiteId());
        } catch (Exception e) {
            logger.error("CmsWindowDataAction.gotoAdvTypeThree异常：" + e.getMessage(), e);
        }
        return "gotoAdvTypeFour";
    }

    //3.编辑三个广告
    public String gotoAdvTypeFive() {
        try {

            cmsWindow = cmsWindowService.getWindowById(windowId);
            //自定义数据
            cmsWindowDatas = cmsWindowDataService
                    .queryByWindowIdDataType(cmsWindow.getWindowId(), 6, cmsWindow.getSiteId());
        } catch (Exception e) {
            logger.error("CmsWindowDataAction.gotoAdvTypeThree异常：" + e.getMessage(), e);
        }
        return "gotoAdvTypeFive";
    }


    public List getFileList() {
        List fileList = new ArrayList<UploadFile>();
        UploadFile uploadFile0 = null;
        UploadFile uploadFile1 = null;
        UploadFile uploadFile2 = null;
        UploadFile uploadFile3 = null;
        UploadFile uploadFile4 = null;

        if (resumefile0 != null) {
            uploadFile0 = new UploadFile(resumefile0, resumefile0FileName, resumefile0ContentType);
        }

        if (resumefile1 != null) {
            uploadFile1 = new UploadFile(resumefile1, resumefile1FileName, resumefile1ContentType);
        }

        if (resumefile2 != null) {
            uploadFile2 = new UploadFile(resumefile2, resumefile2FileName, resumefile2ContentType);
        }

        if (resumefile3 != null) {
            uploadFile3 = new UploadFile(resumefile3, resumefile3FileName, resumefile3ContentType);
        }

        if (resumefile4 != null) {
            uploadFile4 = new UploadFile(resumefile4, resumefile4FileName, resumefile4ContentType);
        }

        fileList.add(uploadFile0);
        fileList.add(uploadFile1);
        fileList.add(uploadFile2);
        fileList.add(uploadFile3);
        fileList.add(uploadFile4);


        return fileList;
    }


    //2.保存所有广告图片

    public String savaWindowPic() {
        String message = "";
        String url = "";
        try {
            SysUser sysuser = (SysUser) session.get("sysUser");
            uploadFileList = getFileList();
            Integer aa = windowId;
            this.cmsWindowDataService
                    .editShopWindowData(cmsWindowDatas, uploadFileList, sysuser, dataIds);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsWindowDataAction.saveDate异常：" + e.getMessage(), e);
            this.addActionMessage(ConfigurationUtil.getString("add.fail"));

            msg = "操作失败！";
            //	message="err";

        }
        //this.addActionMessage("aaaaaaaaaaaaaaaaa");
        msg = "操作成功！";
        //		message="success";
        //		ReturnResult returnResult=new ReturnResult("200","成功",message);

        //		AjaxUtil.writeJSONToResponseByObject(returnResult, true, jsoncallback);
        //	shopPage="561";
        if (picType == 1) {
            return gotoEditBanner();
        } else if (picType == 3 || picType == 2) {
            return gotoAdvTypeOne();
        } else if (picType == 4) {
            return gotoAdvTypeTwo();
        } else if (picType == 5) {
            return gotoAdvTypeThree();
        } else if (picType == 6) {
            return gotoAdvTypeFour();
        } else if (picType == 7) {
            return gotoAdvTypeFive();
        }
        //	return "shopPage";
        return gotoEditBanner();

        //setValue();

    }


    //删除自定义

    public void deletePic() {
        String message = "";
        try {

            cmsWindowDataService.deleteData(dataId);

            message = "success";
        } catch (Exception e) {

            logger.error("CmsWindowDataAction.deleteData异常：" + e.getMessage(), e);
            message = "fail";
        }
        ReturnResult returnResult = new ReturnResult("200", "成功", message);

        AjaxUtil.writeJSONToResponseByObject(returnResult, true, jsoncallback);

    }

    //敏感词
    public void checkKeywords() {
        boolean validResult = PathConstants.checkKeyWords(keywords);
        ReturnResult returnResult = new ReturnResult("200", "成功", validResult);

        AjaxUtil.writeJSONToResponseByObject(returnResult, true, jsoncallback);

    }

    public CmsWindowDataService getCmsWindowDataService() {
        return cmsWindowDataService;
    }

    public void setCmsWindowDataService(CmsWindowDataService cmsWindowDataService) {
        this.cmsWindowDataService = cmsWindowDataService;
    }


    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ViewPromotionService getViewPromotionService() {
        return viewPromotionService;
    }

    public void setViewPromotionService(ViewPromotionService viewPromotionService) {
        this.viewPromotionService = viewPromotionService;
    }

    public Integer getDataId() {
        return dataId;
    }


    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }


    //	public Integer getChannelType() {
//		return channelType;
//	}
//
//	public void setChannelType(Integer channelType) {
//		this.channelType = channelType;
//	}
    public Integer getWindowId() {
        return windowId;
    }


    public void setWindowId(Integer windowId) {
        this.windowId = windowId;
    }


    public List<Integer> getDataIds() {
        return dataIds;
    }


    public void setDataIds(List<Integer> dataIds) {
        this.dataIds = dataIds;
    }


    public Integer getDataType() {
        return dataType;
    }


    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public CmsWindowService getCmsWindowService() {
        return cmsWindowService;
    }

    public void setCmsWindowService(CmsWindowService cmsWindowService) {
        this.cmsWindowService = cmsWindowService;
    }

    public ProdBrandService getProdBrandService() {
        return prodBrandService;
    }

    public void setProdBrandService(ProdBrandService prodBrandService) {
        this.prodBrandService = prodBrandService;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    public List<Integer> getWindowDataIds() {
        return windowDataIds;
    }

    public void setWindowDataIds(List<Integer> windowDataIds) {
        this.windowDataIds = windowDataIds;
    }

    public ViewProductInfoService getViewProductInfoService() {
        return viewProductInfoService;
    }

    public void setViewProductInfoService(ViewProductInfoService viewProductInfoService) {
        this.viewProductInfoService = viewProductInfoService;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getBackType() {
        return backType;
    }

    public void setBackType(Integer backType) {
        this.backType = backType;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public List<String> getFilesContentType() {
        return filesContentType;
    }

    public void setFilesContentType(List<String> filesContentType) {
        this.filesContentType = filesContentType;
    }

    public List<String> getFilesFileName() {
        return filesFileName;
    }

    public LotteryPrizeService getLotteryPrizeService() {
        return lotteryPrizeService;
    }

    public void setLotteryPrizeService(LotteryPrizeService lotteryPrizeService) {
        this.lotteryPrizeService = lotteryPrizeService;
    }

    public void setFilesFileName(List<String> filesFileName) {
        this.filesFileName = filesFileName;
    }

    public CmsWindowData getCmsWindowData() {
        return cmsWindowData;
    }

    public void setCmsWindowData(CmsWindowData cmsWindowData) {
        this.cmsWindowData = cmsWindowData;
    }

    public File getImg() {
        return img;
    }

    public void setImg(File img) {
        this.img = img;
    }

    public String getImgContentType() {
        return imgContentType;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }

    public String getImgFileName() {
        return imgFileName;
    }

    public CmsLogService getCmsLogService() {
        return cmsLogService;
    }

    public void setCmsLogService(CmsLogService cmsLogService) {
        this.cmsLogService = cmsLogService;
    }

    public CmsLog getCmsLog() {
        return cmsLog;
    }

    public void setCmsLog(CmsLog cmsLog) {
        this.cmsLog = cmsLog;
    }

    public void setImgFileName(String imgFileName) {
        this.imgFileName = imgFileName;
    }

    public Integer getWindowDataId() {
        return windowDataId;
    }

    public void setWindowDataId(Integer windowDataId) {
        this.windowDataId = windowDataId;
    }

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public CmsInformactionTypeService getCmsInformactionTypeService() {
        return cmsInformactionTypeService;
    }

    public void setCmsInformactionTypeService(
            CmsInformactionTypeService cmsInformactionTypeService) {
        this.cmsInformactionTypeService = cmsInformactionTypeService;
    }

    public Integer getAdminType() {
        return adminType;
    }

    public void setAdminType(Integer adminType) {
        this.adminType = adminType;
    }

    public List<String> getDatas() {
        return datas;
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getStylesId() {
        return stylesId;
    }

    public void setStylesId(Integer stylesId) {
        this.stylesId = stylesId;
    }

    public LotteryLuckDrawService getLotteryLuckDrawService() {
        return lotteryLuckDrawService;
    }

    public void setLotteryLuckDrawService(LotteryLuckDrawService lotteryLuckDrawService) {
        this.lotteryLuckDrawService = lotteryLuckDrawService;
    }

    public CmsWindow getCmsWindow() {
        return cmsWindow;
    }

    public void setCmsWindow(CmsWindow cmsWindow) {
        this.cmsWindow = cmsWindow;
    }


    public File getResumefile2() {
        return resumefile2;
    }

    public void setResumefile2(File resumefile2) {
        this.resumefile2 = resumefile2;
    }

    public File getResumefile3() {
        return resumefile3;
    }

    public void setResumefile3(File resumefile3) {
        this.resumefile3 = resumefile3;
    }

    public File getResumefile4() {
        return resumefile4;
    }

    public void setResumefile4(File resumefile4) {
        this.resumefile4 = resumefile4;
    }


    public String getResumefile2ContentType() {
        return resumefile2ContentType;
    }

    public void setResumefile2ContentType(String resumefile2ContentType) {
        this.resumefile2ContentType = resumefile2ContentType;
    }

    public String getResumefile3ContentType() {
        return resumefile3ContentType;
    }

    public void setResumefile3ContentType(String resumefile3ContentType) {
        this.resumefile3ContentType = resumefile3ContentType;
    }

    public String getResumefile4ContentType() {
        return resumefile4ContentType;
    }

    public void setResumefile4ContentType(String resumefile4ContentType) {
        this.resumefile4ContentType = resumefile4ContentType;
    }


    public String getResumefile2FileName() {
        return resumefile2FileName;
    }

    public void setResumefile2FileName(String resumefile2FileName) {
        this.resumefile2FileName = resumefile2FileName;
    }

    public String getResumefile3FileName() {
        return resumefile3FileName;
    }

    public void setResumefile3FileName(String resumefile3FileName) {
        this.resumefile3FileName = resumefile3FileName;
    }

    public String getResumefile4FileName() {
        return resumefile4FileName;
    }

    public void setResumefile4FileName(String resumefile4FileName) {
        this.resumefile4FileName = resumefile4FileName;
    }


    public File getResumefile1() {
        return resumefile1;
    }

    public void setResumefile1(File resumefile1) {
        this.resumefile1 = resumefile1;
    }

    public String getResumefile1ContentType() {
        return resumefile1ContentType;
    }

    public void setResumefile1ContentType(String resumefile1ContentType) {
        this.resumefile1ContentType = resumefile1ContentType;
    }

    public String getResumefile1FileName() {
        return resumefile1FileName;
    }

    public void setResumefile1FileName(String resumefile1FileName) {
        this.resumefile1FileName = resumefile1FileName;
    }

    public List<UploadFile> getUploadFileList() {
        return uploadFileList;
    }

    public void setUploadFileList(List<UploadFile> uploadFileList) {
        this.uploadFileList = uploadFileList;
    }

    public File getResumefile0() {
        return resumefile0;
    }

    public void setResumefile0(File resumefile0) {
        this.resumefile0 = resumefile0;
    }

    public String getResumefile0ContentType() {
        return resumefile0ContentType;
    }

    public void setResumefile0ContentType(String resumefile0ContentType) {
        this.resumefile0ContentType = resumefile0ContentType;
    }

    public String getResumefile0FileName() {
        return resumefile0FileName;
    }

    public void setResumefile0FileName(String resumefile0FileName) {
        this.resumefile0FileName = resumefile0FileName;
    }

    public Integer getPicType() {
        return picType;
    }

    public void setPicType(Integer picType) {
        this.picType = picType;
    }

    public String getJsoncallback() {
        return jsoncallback;
    }

    public void setJsoncallback(String jsoncallback) {
        this.jsoncallback = jsoncallback;
    }

    public String getShopPage() {
        return shopPage;
    }

    public void setShopPage(String shopPage) {
        this.shopPage = shopPage;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getShopI() {
        return shopI;
    }

    public void setShopI(Integer shopI) {
        this.shopI = shopI;
    }

    public String getCheckeds() {
        return checkeds;
    }

    public void setCheckeds(String checkeds) {
        this.checkeds = checkeds;
    }

    public ShopCategorysService getShopCategorysService() {
        return shopCategorysService;
    }

    public void setShopCategorysService(ShopCategorysService shopCategorysService) {
        this.shopCategorysService = shopCategorysService;
    }

    public String getShopCategoryStr() {
        return shopCategoryStr;
    }

    public void setShopCategoryStr(String shopCategoryStr) {
        this.shopCategoryStr = shopCategoryStr;
    }

    public String getTabId() {
        return tabId;
    }

    public void setTabId(String tabId) {
        this.tabId = tabId;
    }


}
