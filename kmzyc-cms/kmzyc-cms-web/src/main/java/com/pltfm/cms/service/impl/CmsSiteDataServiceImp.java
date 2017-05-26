package com.pltfm.cms.service.impl;

import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.util.DateTimeUtils;
import com.kmzyc.commons.page.Page;
import com.pltfm.cms.dao.CmsSiteDataDAO;
import com.pltfm.cms.service.CmsAdvService;
import com.pltfm.cms.service.CmsPageService;
import com.pltfm.cms.service.CmsPromotionService;
import com.pltfm.cms.service.CmsSiteDataService;
import com.pltfm.cms.service.CmsWindowService;
import com.pltfm.cms.vobject.CmsSiteData;
import com.pltfm.cms.vobject.CmsSiteDataExample;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Component("cmsSiteDataService")
public class CmsSiteDataServiceImp implements CmsSiteDataService {
    /**
     * 站点数据DAO接口
     */
    @Resource(name = "cmsSiteDataDAO")
    private CmsSiteDataDAO cmsSiteDataDAO;
    /**
     * 页面业务逻辑接口
     */
    @Resource(name = "cmsPageService")
    CmsPageService cmsPageService;
    /**
     * 窗口业务逻辑接口
     */
    @Resource(name = "cmsWindowService")
    CmsWindowService cmsWindowService;


    /**
     * 广告业务逻辑接口
     */
    @Resource(name = "cmsAdvService")
    CmsAdvService cmsAdvService;

    public List listToString(Integer userId, Integer dataType) throws SQLException {
        ActionContext actionContext = ActionContext.getContext();
        Map session = actionContext.getSession();
        Integer siteId = (Integer) session.get("siteId");
        CmsSiteData csd = new CmsSiteData();
        csd.setUserId(userId);
        csd.setSiteId(siteId);
        csd.setDataType(dataType);
        List list = cmsSiteDataDAO.selectList(csd);


//	   int size =list.size();
    /*   System.out.println(list.toString()+"---------------------------------------------------------------------");
       dataId=list.toString();
	   dataId=dataId.substring(1, dataId.length()-1);
	   if(dataId!=null&&!dataId.equals("")){
	   dataId="("+dataId+")";}else{
		   dataId=null;
	   }*/
        /*Integer[] arr = null;
		if(list.size()>0&&list!=null){
			arr= (Integer[])list.toArray(Integer.valueOf[size]);
			if(arr.length>0){
				CmsSiteData csd1=null;
				for(int i=0; i<arr.length;i++){
					dataId=dataId+arr[i]+",";
				}
				dataId="("+dataId+"0)";
			}
		}*/


        return list;
    }

    /**
     * 活动业务逻辑接口
     */
    @Resource(name = "cmsPromotionService")
    CmsPromotionService cmsPromotionService;

    /**
     * 根据用户id与站点id查询站点数据
     *
     * @param userId 用户id
     * @param siteId 站点id
     * @return 站点数据集合
     * @throws Exception 异常
     */
    @Override
    public List queryByUserIdAndSiteId(Integer userId,
                                       Integer siteId) throws Exception {
        CmsSiteDataExample example = new CmsSiteDataExample();
        example.createCriteria().andUserIdEqualTo(userId).andSiteIdEqualTo(siteId);
        List csdList = cmsSiteDataDAO.selectByExample(example);
        csdList = this.queryDataName(csdList);
        return csdList;
    }

    /**
     * 加载数据名称
     */
    public List queryDataName(List csdList) throws Exception {
        for (Object obj : csdList) {
            CmsSiteData csd = (CmsSiteData) obj;
            switch (csd.getDataType()) {
                case 1:
                    csd.setDataName(cmsPageService.getCmsPageById(csd.getDataId()).getName());
                    break;
                case 2:
                    csd.setDataName(cmsAdvService.byid(csd.getDataId()).getName());
                    break;
                case 3:
                    csd.setDataName(cmsPromotionService.cmspByIdAndSiteId(csd.getDataId(), csd.getSiteId()).getName());
                    break;
                case 4:
                    csd.setDataName(cmsWindowService.selectByPrimaryKey(csd.getDataId()).getName());
                    break;
            }
        }
        return csdList;
    }

    /**
     * 根据用户id、站点id和数据类型查询站点数据
     *
     * @param userId   用户id
     * @param siteId   站点id
     * @param dataType 数据类型
     * @return 站点数据集合
     * @throws Exception 异常
     */
    @Override
    public List<CmsSiteData> queryByUserIdAndSiteIdAndDataType(Integer userId,
                                                               Integer siteId, Integer dataType) throws Exception {
        CmsSiteDataExample example = new CmsSiteDataExample();
        example.createCriteria().andUserIdEqualTo(userId).andSiteIdEqualTo(siteId).andDataTypeEqualTo(dataType);
        List<CmsSiteData> csdList = cmsSiteDataDAO.selectByExample(example);
        return csdList;
    }

    /**
     * 添加站点数据信息
     *
     * @param dataIds  数据信息集合
     * @param dataType 数据类型集合
     * @param userId   用户Id
     * @param siteId   站点Id
     * @param created  当前用户Id
     * @return 无返回值
     * @throws Exception sql异常
     */
    @Override
    public void addCmsSiteData(List<Integer> dataIds, Integer dataType,
                               Integer userId, Integer siteId, Integer created) throws Exception {
        for (Integer dataId : dataIds) {
            CmsSiteData csd = new CmsSiteData();
            csd.setDataId(dataId);
            csd.setDataType(dataType);
            csd.setUserId(userId);
            csd.setSiteId(siteId);
            csd.setCreated(created);
            csd.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
            csd.setStatus(1);
            cmsSiteDataDAO.insert(csd);
        }
    }

    @Override
    public Integer delCmsSiteData(List<Integer> userSiteDataIds)
            throws Exception {
        Integer rows = 0;
        if (userSiteDataIds != null && userSiteDataIds.size() > 0) {
            for (Integer id : userSiteDataIds) {
                rows += cmsSiteDataDAO.deleteByPrimaryKey(id);
            }
        }
        return rows;
    }


    /**
     * 添加站点数据信息
     *
     * @param dataIds  数据信息集合
     * @param dataType 数据类型集合
     * @param userId   用户Id
     * @param siteId   站点Id
     * @param created  当前用户Id
     * @return 无返回值
     * @throws Exception sql异常
     */
    @Override
    public void addCSD(Integer dataId, Integer dataType, Integer userId, Integer siteId, Integer created) throws Exception {

        CmsSiteData csd = new CmsSiteData();
        csd.setDataId(dataId);
        csd.setDataType(dataType);
        csd.setUserId(userId);
        csd.setSiteId(siteId);
        csd.setCreated(created);
        csd.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
        csd.setStatus(1);
        cmsSiteDataDAO.insert(csd);

    }

    /**
     * 分页查询站点与数据信息
     *
     * @param cmsUserSite 站点与数据信息实体
     * @param page        分页实体
     * @return 返回值
     * @throws Exception 异常
     */
    @Override
    public Page queryForPage(CmsSiteData cmsSiteData, Page page)
            throws Exception {
        if (page == null) {
            page = new Page();
        }
        if (cmsSiteData == null) {
            cmsSiteData = new CmsSiteData();
        }
        // 根据条件获取窗口数据信息总条数
        //int totalNum = cmsUserSiteDAO.countByCmsUserSite(cmsUserSite);
        int totalNum = cmsSiteDataDAO.countByCmsSiteData(cmsSiteData);
        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            cmsSiteData.setStartIndex(page.getStartIndex());
            cmsSiteData.setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(this.queryDataName(cmsSiteDataDAO.queryForPage(cmsSiteData)));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }


    public CmsSiteDataDAO getCmsSiteDataDAO() {
        return cmsSiteDataDAO;
    }

    public void setCmsSiteDataDAO(CmsSiteDataDAO cmsSiteDataDAO) {
        this.cmsSiteDataDAO = cmsSiteDataDAO;
    }

    public CmsPageService getCmsPageService() {
        return cmsPageService;
    }

    public void setCmsPageService(CmsPageService cmsPageService) {
        this.cmsPageService = cmsPageService;
    }

    public CmsAdvService getCmsAdvService() {
        return cmsAdvService;
    }

    public void setCmsAdvService(CmsAdvService cmsAdvService) {
        this.cmsAdvService = cmsAdvService;
    }

    public CmsPromotionService getCmsPromotionService() {
        return cmsPromotionService;
    }

    public void setCmsPromotionService(CmsPromotionService cmsPromotionService) {
        this.cmsPromotionService = cmsPromotionService;
    }

    public CmsWindowService getCmsWindowService() {
        return cmsWindowService;
    }

    public void setCmsWindowService(CmsWindowService cmsWindowService) {
        this.cmsWindowService = cmsWindowService;
    }


}
