package com.pltfm.cms.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.util.Constants;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.Token;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsWindowDataService;
import com.pltfm.cms.vobject.CmsWindowData;
import com.pltfm.cms.vobject.UploadFile;

/**
 * 店铺装修自定义Action类
 *
 * @author pb
 * @since 2014-12-08
 */
@Scope("prototype")
@Component(value = "shopsDecorateCustomAction")
public class ShopsDecorateCustomAction extends BaseAction {
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
     * 窗口数据业务逻辑层接口
     */
    @Resource(name = "cmsWindowDataService")
    private CmsWindowDataService cmsWindowDataService;

    /**
     * 窗口数据集
     */
    private List<CmsWindowData> cmsWindowDatas;
    /**
     * 窗口Id
     */
    private Integer windowId;
    private static Logger logger = Logger
            .getLogger(ShopsDecorateCustomAction.class);

    /**
     * CmsWindowData对象
     */
    private CmsWindowData cmsWindowData;

    private Integer windowDataId;
    /**
     * 单个文件上传
     */
    private File img;

    /**
     * 单个文件名
     */
    private String imgFileName;

    /**
     * 单个文件类型
     */
    private String imgContentType;

    /**
     * 数据Id
     */
    private Integer dataId;
    /**
     * 返回页面类型
     */
    private Integer backType;

    /**
     * 分页对象
     */
    private Page page;


    private Integer shopI;

    public Integer getShopI() {
        return shopI;
    }

    public void setShopI(Integer shopI) {
        this.shopI = shopI;
    }

    /**
     * 编辑导航--根据窗口查询数据信息
     */
    public String queryNavigationPageList() {
        try {
            Integer siteId = Constants.SET_B2B_ID;
            CmsWindowData data = new CmsWindowData();
            data.setWindowId(windowId);
            data.setDataType(6);
            data.setSiteId(siteId);
            page = cmsWindowDataService.queryBywindowId(data, page);
            return "navigationList";
        } catch (Exception e) {
            logger.error("ShopsDecorateCustomAction.queryNavigationPageList报错：" + e);
            return "queryError";
        }
    }

    /**
     * 编辑导航--根据窗口查询返回数据判断所用方法
     */
    public String saveNavigationCustomData() {
        try {
            Integer siteId = Constants.SET_B2B_ID;
            CmsWindowData data = new CmsWindowData();
            data.setWindowId(windowId);
            data.setDataType(6);
            data.setSiteId(siteId);
            page = cmsWindowDataService.queryBywindowId(data, page);
            if (page.getRecordCount() == 0) {
                addNavigationCustomData();
                return queryNavigationPageList();
            } else {
                for (int i = 0; i <= cmsWindowDatas.size() - 1; i++) {
                    CmsWindowData cmsWindowData = cmsWindowDatas.get(i);
                    updateNavigationCustomData(cmsWindowData);
                }
                return queryNavigationPageList();
            }
        } catch (Exception e) {
            logger.error("ShopsDecorateCustomAction.queryPageList报错：" + e);
            return null;
        }
    }

    /**
     * 编辑导航--添加自定义数据
     */
    public String addNavigationCustomData() {
        try {
            for (int i = 0; i <= cmsWindowDatas.size() - 1; i++) {
                String validStr = cmsWindowDatas.get(i).getUser_defined_name();
                boolean validResult = PathConstants.checkKeyWords(validStr);
                //若是包含,则提示其重新改动
                if (validResult) {
                    return null;
                }
            }
            uploadFileList = new ArrayList<UploadFile>();
            Integer siteId = Constants.SET_B2B_ID;
            // 窗口数据,窗口id，创建人，上传文件，站点主键
            this.cmsWindowDataService.addCmsWindowData(cmsWindowDatas,
                    this.windowId, Constants.USER_B2B_ID, this.uploadFileList,
                    siteId);
        } catch (Exception e) {
            logger.error("ShopsDecorateCustomAction.addNavigationCustomData报错："
                    + e);
        }
        // 添加有返回数据，返回值放入窗口为windowid窗口预览
        return queryNavigationPageList();
    }

    /**
     * 编辑导航--删除自定义数据
     */
    public String deleteNavigationCustomData() {
        try {
            cmsWindowDataService.deleteData(this.windowDataId);
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
            if (this.backType == null) {
                return this.queryNavigationPageList();
            } else {
                return null;
            }
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger
                    .error("ShopsDecorateCustomAction.deleteNavigationCustomData报错："
                            + e);
            // 删除有返回数据，返回值放入窗口为windowid窗口预览
        }
        return this.queryNavigationPageList();
    }

    public String gotoNavigationCustomData() {
        return "navigationAdd";
    }

    /**
     * 编辑导航--显示修改自定义数据
     */
    public String selectNavigationCustomData() {
        try {
            this.cmsWindowData = this.cmsWindowDataService
                    .getCmsWindowDataByPkId(this.windowDataId);
        } catch (Exception e) {
            logger
                    .error("ShopsDecorateCustomAction.selectNavigationCustomData报错："
                            + e);
        }
        // 显示有返回数据，返回值放入窗口为windowid窗口预览
        return "navigationUpdate";
    }

    /**
     * 编辑导航--保存修改排序
     */
    public String updateNavigationCustomData(CmsWindowData cmsWindowData) {
        try {
            cmsWindowData.setModified(Constants.USER_B2B_ID);
            cmsWindowData.setModifyDate(DateTimeUtils.getCalendarInstance()
                    .getTime());
            this.cmsWindowDataService.updateCmsWindowShopData(cmsWindowData);
        } catch (Exception e) {
            logger
                    .error("ShopsDecorateCustomAction.updateNavigationCustomData报错："
                            + e);
        }
        // 保存有返回数据，jsp删除相应span
        return this.queryNavigationPageList();
    }

    /**
     * 编辑导航--保存修改自定义数据
     */
    public String updateNavigationCustomDataOne() {
        try {
            String validStr = cmsWindowData.getUser_defined_name();
            boolean validResult = PathConstants.checkKeyWords(validStr);
            //若是包含,则提示其重新改动
            if (validResult) {
                return null;
            }
            cmsWindowData.setModified(Constants.USER_B2B_ID);
            cmsWindowData.setModifyDate(DateTimeUtils.getCalendarInstance()
                    .getTime());
            this.cmsWindowDataService.updateCmsWindowShopData(cmsWindowData);
        } catch (Exception e) {
            logger
                    .error("ShopsDecorateCustomAction.updateNavigationCustomDataOne报错："
                            + e);
        }
        // 保存有返回数据，jsp删除相应span
        return this.queryNavigationPageList();
    }

    /**
     * 店铺搜索--根据窗口查询数据信息
     */
    public String queryShopPageList() {
        try {
            Integer siteId = Constants.SET_B2B_ID;
            CmsWindowData data = new CmsWindowData();
            data.setWindowId(windowId);
            data.setDataType(6);
            data.setSiteId(siteId);
            page = cmsWindowDataService.queryBywindowId(data, page);
            return "storeList";
        } catch (Exception e) {
            logger.error("ShopsDecorateCustomAction.queryShopPageList报错：" + e);
            return "queryError";
        }
    }

    /**
     * 店铺搜索--根据窗口查询返回数据判断所用方法
     */
    public String saveShopStoreCustomData() {
        try {
            Integer siteId = Constants.SET_B2B_ID;
            CmsWindowData data = new CmsWindowData();
            data.setWindowId(windowId);
            data.setDataType(6);
            data.setSiteId(siteId);
            page = cmsWindowDataService.queryBywindowId(data, page);

            for (int j = 0; j <= cmsWindowDatas.size() - 1; j++) {
                String validStr = cmsWindowDatas.get(j).getUser_defined_name();
                boolean validResult = PathConstants.checkKeyWords(validStr);
                //若是包含,则提示其重新改动
                if (validResult) {
                    return null;
                }
            }
            if (page.getRecordCount() == 0) {
                addShopStoreCustomData();
                return queryShopPageList();
            } else {
                for (int i = 0; i <= cmsWindowDatas.size() - 1; i++) {
                    CmsWindowData cmsWindowData = cmsWindowDatas.get(i);
                    updateShopStoreCustomData(cmsWindowData);
                }
                return queryShopPageList();
            }

        } catch (Exception e) {
            logger.error("ShopsDecorateCustomAction.queryPageList报错：" + e);
            return null;
        }
    }

    /**
     * 店铺搜索--添加自定义数据
     */
    @Token
    public void addShopStoreCustomData() {
        try {
            uploadFileList = new ArrayList<UploadFile>();
            Integer siteId = Constants.SET_B2B_ID;
            // 窗口数据,窗口id，创建人，上传文件，站点主键
            this.cmsWindowDataService.addCmsWindowShopData(cmsWindowDatas, this.windowId,
                    Constants.USER_B2B_ID,
                    siteId);
        } catch (Exception e) {
            logger.error("ShopsDecorateCustomAction.addShopStoreCustomData报错："
                    + e);
        }
    }

    /**
     * 店铺搜索--保存修改自定义数据
     */
    public void updateShopStoreCustomData(CmsWindowData cmsWindowData) {
        try {
            UploadFile uploadFile = new UploadFile();
            cmsWindowData.setModified(Constants.USER_B2B_ID);
            cmsWindowData.setModifyDate(DateTimeUtils.getCalendarInstance()
                    .getTime());
            this.cmsWindowDataService.updateCmsWindowData(cmsWindowData,
                    uploadFile);
        } catch (Exception e) {
            logger
                    .error("ShopsDecorateCustomAction.updateShopStoreCustomData报错："
                            + e);
        }
    }

    /**
     * 公告--根据窗口查询数据信息
     */
    public String queryNoticesPageList() {
        try {
            Integer siteId = Constants.SET_B2B_ID;
            CmsWindowData data = new CmsWindowData();
            data.setWindowId(windowId);
            data.setDataType(6);
            data.setSiteId(siteId);
            page = cmsWindowDataService.queryBywindowId(data, page);
            return "noticesList";
        } catch (Exception e) {
            logger.error("ShopsDecorateCustomAction.queryNoticesPageList报错：" + e);
            return "queryError";
        }
    }

    /**
     * 公告--根据窗口查询返回数据判断所用方法
     */
    public String saveNoticesStoreCustomData() {
        try {
            Integer siteId = Constants.SET_B2B_ID;
            CmsWindowData data = new CmsWindowData();
            data.setWindowId(windowId);
            data.setDataType(6);
            data.setSiteId(siteId);
            page = cmsWindowDataService.queryBywindowId(data, page);

            for (int j = 0; j <= cmsWindowDatas.size() - 1; j++) {
                String validStr = cmsWindowDatas.get(j).getUser_defined_name();
                boolean validResult = PathConstants.checkKeyWords(validStr);
                String validStr1 = cmsWindowDatas.get(j).getRemark();
                boolean validResult1 = PathConstants.checkKeyWords(validStr1);
                //若是包含,则提示其重新改动
                if (validResult || validResult1) {
                    return null;
                }
            }
            if (page.getRecordCount() == 0) {
                addNoticesCustomData();
                return queryNoticesPageList();
            } else {
                for (int i = 0; i <= cmsWindowDatas.size() - 1; i++) {
                    CmsWindowData cmsWindowData = cmsWindowDatas.get(i);
                    updataNoticesCustomData(cmsWindowData);
                }
                return queryNoticesPageList();
            }
        } catch (Exception e) {
            logger.error("ShopsDecorateCustomAction.queryPageList报错：" + e);
            return null;
        }
    }

    /**
     * 公告--添加自定义数据
     */
    @Token
    public void addNoticesCustomData() {
        try {
            uploadFileList = new ArrayList<UploadFile>();
            Integer siteId = Constants.SET_B2B_ID;
            // 窗口数据,窗口id，创建人，上传文件，站点主键
            this.cmsWindowDataService.addCmsWindowShopData(cmsWindowDatas, this.windowId,
                    Constants.USER_B2B_ID,
                    siteId);
        } catch (Exception e) {
            logger.error("ShopsDecorateCustomAction.addNoticesCustomData报错："
                    + e);
        }
    }

    /**
     * 公告--保存修改自定义数据
     */
    public void updataNoticesCustomData(CmsWindowData cmsWindowData) {
        try {
            cmsWindowData.setModified(Constants.USER_B2B_ID);
            cmsWindowData.setModifyDate(DateTimeUtils.getCalendarInstance()
                    .getTime());
            this.cmsWindowDataService.updateCmsWindowShopData(cmsWindowData);
        } catch (Exception e) {
            logger.error("ShopsDecorateCustomAction.updataNoticesCustomData报错："
                    + e);
        }
    }

    /**
     * 在线客服--根据窗口查询数据信息
     */
    public String queryOnlineServicePageList() {
        try {
            Integer siteId = Constants.SET_B2B_ID;
            CmsWindowData data = new CmsWindowData();
            data.setWindowId(windowId);
            data.setDataType(6);
            data.setSiteId(siteId);
            page = cmsWindowDataService.queryBywindowId(data, page);
            return "onlineServiceList";
        } catch (Exception e) {
            logger.error("ShopsDecorateCustomAction.queryPageList报错：" + e);
            return "queryError";
        }
    }

    /**
     * 在线客服--根据窗口查询返回数据判断所用方法
     */
    public String saveOnlineServiceStoreCustomData() {
        try {
            for (int j = 0; j <= cmsWindowDatas.size() - 1; j++) {
                String validStr = cmsWindowDatas.get(j).getUser_defined_name();
                boolean validResult = PathConstants.checkKeyWords(validStr);
                //若是包含,则提示其重新改动
                if (validResult) {
                    return null;
                }
            }
            deleteOnlineServiceCustomDatas();
            addOnlineServiceCustomData();
            return queryOnlineServicePageList();
        } catch (Exception e) {
            logger.error("ShopsDecorateCustomAction.queryPageList报错：" + e);
            return null;
        }
    }

    /**
     * 在线客服--根据窗口id删除所有自定义数据
     */
    public String deleteOnlineServiceCustomDatas() {
        try {
            cmsWindowDataService.deleteByWindowId(this.windowId);
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
            if (this.backType == null) {
                return this.queryOnlineServicePageList();
            } else {
                return null;
            }
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger
                    .error("ShopsDecorateCustomAction.deleteOnlineServiceCustomData报错："
                            + e);
            return null;
        }
    }

    /**
     * 在线客服--根据windowDataId删除自定义数据
     */
    public void deleteOnlineServiceCustomData() {
        try {
            cmsWindowDataService.deleteData(this.windowDataId);
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger
                    .error("ShopsDecorateCustomAction.deleteNavigationCustomData报错："
                            + e);
        }
    }

    /**
     * 在线客服--添加自定义数据
     */
    @Token
    public void addOnlineServiceCustomData() {
        try {
            uploadFileList = new ArrayList<UploadFile>();
            Integer siteId = Constants.SET_B2B_ID;
            // 窗口数据,窗口id，创建人，上传文件，站点主键
            this.cmsWindowDataService.addCmsWindowShopData(cmsWindowDatas, this.windowId,
                    Constants.USER_B2B_ID,
                    siteId);
        } catch (Exception e) {
            logger
                    .error("ShopsDecorateCustomAction.addOnlineServiceCustomData报错："
                            + e);
        }
    }


    /**
     * Tab切换页--根据窗口查询数据信息
     */
    public String queryTabPageList() {
        try {
            Integer siteId = Constants.SET_B2B_ID;
            CmsWindowData data = new CmsWindowData();
            data.setWindowId(windowId);
            data.setDataType(6);
            data.setSiteId(siteId);
            page = cmsWindowDataService.queryBywindowId(data, page);
            return "tabList";
        } catch (Exception e) {
            logger.error("ShopsDecorateCustomAction.queryTabPageList报错：" + e);
            return "queryError";
        }
    }

    /**
     * Tab切换页--根据窗口查询返回数据判断所用方法
     */
    public String saveTabCustomData() {
        try {
            Integer siteId = Constants.SET_B2B_ID;
            CmsWindowData data = new CmsWindowData();
            data.setWindowId(windowId);
            data.setDataType(6);
            data.setSiteId(siteId);
            page = cmsWindowDataService.queryBywindowId(data, page);
            for (int j = 0; j <= cmsWindowDatas.size() - 1; j++) {
                String validStr = cmsWindowDatas.get(j).getUser_defined_name();
                boolean validResult = PathConstants.checkKeyWords(validStr);
                //若是包含,则提示其重新改动
                if (validResult) {
                    return null;
                }
            }
//			if(page.getRecordCount()==0){
//				addTabCustomData();
//				return queryTabPageList();
//			}else{
//				for(int i=0;i<=cmsWindowDatas.size()-1;i++){
//					CmsWindowData cmsWindowData=cmsWindowDatas.get(i);
//					updataTabCustomData(cmsWindowData);
//				}
//				return queryTabPageList();
//			}
            deleteTabCustomData();
            addTabCustomData();
            return queryTabPageList();
        } catch (Exception e) {
            logger.error("ShopsDecorateCustomAction.saveTabCustomData报错：" + e);
            return null;
        }
    }

    /**
     * Tab切换页--添加自定义数据
     */
    @Token
    public void addTabCustomData() {
        try {
            uploadFileList = new ArrayList<UploadFile>();
            Integer siteId = Constants.SET_B2B_ID;
            // 窗口数据,窗口id，创建人，上传文件，站点主键
            this.cmsWindowDataService.addCmsWindowShopData(cmsWindowDatas, this.windowId,
                    Constants.USER_B2B_ID,
                    siteId);
        } catch (Exception e) {
            logger.error("ShopsDecorateCustomAction.addTabCustomData报错："
                    + e);
        }
    }

    /**
     * Tab切换页--根据窗口id删除所有自定义数据
     */
    public String deleteTabCustomData() {
        try {
            cmsWindowDataService.deleteByWindowId(this.windowId);
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
            if (this.backType == null) {
                return this.queryOnlineServicePageList();
            } else {
                return null;
            }
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger
                    .error("ShopsDecorateCustomAction.deleteTabCustomData报错："
                            + e);
            return null;
        }
    }

    /**
     * Tab切换页--根据windowDataId删除自定义数据
     */
    public void deleteWindowDataIdCustomData() {
        try {
            cmsWindowDataService.deleteData(this.windowDataId);
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger
                    .error("ShopsDecorateCustomAction.deleteWindowDataIdCustomData报错："
                            + e);
        }
    }

    /**
     * Tab切换页--保存修改自定义数据
     */
    public void updataTabCustomData(CmsWindowData cmsWindowData) {
        try {
            cmsWindowData.setModified(Constants.USER_B2B_ID);
            cmsWindowData.setModifyDate(DateTimeUtils.getCalendarInstance()
                    .getTime());
            this.cmsWindowDataService.updateCmsWindowShopData(cmsWindowData);
        } catch (Exception e) {
            logger.error("ShopsDecorateCustomAction.updataNoticesCustomData报错："
                    + e);
        }
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

    public void setFilesFileName(List<String> filesFileName) {
        this.filesFileName = filesFileName;
    }

    public CmsWindowDataService getCmsWindowDataService() {
        return cmsWindowDataService;
    }

    public void setCmsWindowDataService(CmsWindowDataService cmsWindowDataService) {
        this.cmsWindowDataService = cmsWindowDataService;
    }

    public List<CmsWindowData> getCmsWindowDatas() {
        return cmsWindowDatas;
    }

    public void setCmsWindowDatas(List<CmsWindowData> cmsWindowDatas) {
        this.cmsWindowDatas = cmsWindowDatas;
    }

    public Integer getWindowId() {
        return windowId;
    }

    public void setWindowId(Integer windowId) {
        this.windowId = windowId;
    }

    public CmsWindowData getCmsWindowData() {
        return cmsWindowData;
    }

    public void setCmsWindowData(CmsWindowData cmsWindowData) {
        this.cmsWindowData = cmsWindowData;
    }

    public Integer getWindowDataId() {
        return windowDataId;
    }

    public void setWindowDataId(Integer windowDataId) {
        this.windowDataId = windowDataId;
    }

    public File getImg() {
        return img;
    }

    public void setImg(File img) {
        this.img = img;
    }

    public String getImgFileName() {
        return imgFileName;
    }

    public void setImgFileName(String imgFileName) {
        this.imgFileName = imgFileName;
    }

    public String getImgContentType() {
        return imgContentType;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }

    public Integer getDataId() {
        return dataId;
    }


    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public Integer getBackType() {
        return backType;
    }

    public void setBackType(Integer backType) {
        this.backType = backType;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
