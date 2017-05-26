package com.pltfm.cms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pltfm.app.dao.CategoryDAO;
import com.pltfm.app.dao.ProdBrandDAO;
import com.pltfm.app.dao.ViewProductInfoDAO;
import com.pltfm.app.dao.ViewPromotionDAO;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.ListUtils;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.Category;
import com.pltfm.app.vobject.ProdBrand;
import com.pltfm.app.vobject.ViewProductInfo;
import com.pltfm.app.vobject.ViewPromotion;
import com.pltfm.cms.dao.CmsLogDAO;
import com.pltfm.cms.dao.CmsPageDAO;
import com.pltfm.cms.dao.CmsPageWindowDAO;
import com.pltfm.cms.dao.CmsTemplateDAO;
import com.pltfm.cms.dao.CmsWindowDAO;
import com.pltfm.cms.dao.CmsWindowDataDAO;
import com.pltfm.cms.service.CmsLogService;
import com.pltfm.cms.vobject.CmsLog;
import com.pltfm.cms.vobject.CmsPage;
import com.pltfm.cms.vobject.CmsTemplate;
import com.pltfm.cms.vobject.CmsWindow;
import com.pltfm.cms.vobject.CmsWindowData;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

@Component(value = "cmsLogService")
public class CmsLogServiceImpl implements CmsLogService {
	private static Logger logger = LoggerFactory.getLogger(CmsLogServiceImpl.class);
    @Resource(name = "cmsLogDAO")
    private CmsLogDAO cmsLogDAO;

    @Resource(name = "cmsPageDAO")
    private CmsPageDAO cmsPageDAO;
    @Resource(name = "cmsWindowDAO")
    private CmsWindowDAO cmsWindowDAO;

    @Resource(name = "cmsPageWindowDAO")
    private CmsPageWindowDAO cmsPageWindowDAO;

    @Resource(name = "cmsTemplateDAO")
    private CmsTemplateDAO cmsTemplateDAO;
    /**
     * 产品视图DOA接口
     */
    @Resource(name = "viewProductInfoDAO")
    private ViewProductInfoDAO viewProductInfoDAO;
    @Resource(name = "categoryDAO")
    private CategoryDAO categoryDAO;
    @Resource(name = "cmsWindowDataDAO")
    private CmsWindowDataDAO cmsWindowDataDAO;


    public void setCmsWindowDataDAO(CmsWindowDataDAO cmsWindowDataDAO) {
        this.cmsWindowDataDAO = cmsWindowDataDAO;
    }

    /**
     * 品牌信息DAO接口
     */
    @Resource(name = "prodBrandDAO")
    private ProdBrandDAO prodBrandDAO;
    /**
     * 活动信息DAO接口
     */
    @Resource(name = "viewPromotionDAO")
    private ViewPromotionDAO viewPromotionDAO;

    /**
     * /***
     *
     * 删除日志
     */
    public Integer delete(List<Integer> cmsLogIds) throws SQLException {
        Integer count = 0;
        if (ListUtils.isNotEmpty(cmsLogIds)) {
            for (Integer id : cmsLogIds) {
                count += cmsLogDAO.delete(id);
            }
        }
        return count;
    }

    /**
     * 日期时分秒
     */
    private String stringDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new java.util.Date());
    }

    /***
     *
     * 添加日志记录
     *
     * */
    public Integer insert(CmsLog cms) throws Exception {
        Object object = null;
        if (cms.getIds() != null) {
            String id[] = cms.getIds().split(",");
            for (String anId : id) {

                switch (cms.getModuleName()) {
                    case "窗口":
                        CmsWindow cmsWindow = this.cmsWindowDAO
                                .selectByPrimaryKey(Integer.valueOf(anId));
                        if (cmsWindow != null) {
                            cms.setModuleContent("删除:" + cmsWindow.getName() + "窗口");
                        }
                        break;
                    case "模板":
                        CmsTemplate cmsTemplate = this.cmsTemplateDAO
                                .selectByPrimaryKey(Integer.valueOf(anId));
                        if (cmsTemplate != null) {
                            cms.setModuleContent("删除:" + cmsTemplate.getName() + "模板");
                        }

                        break;
                    case "页面":
                        CmsPage cmsPages = cmsPageDAO.selectByPrimaryKey(Integer.valueOf(anId));
                        if (cmsPages != null) {
                            cms.setModuleContent("删除:" + cmsPages.getName() + "页");
                        }
                        break;
                }
                cms.setType(3);
                cms.setConsoleOperatorDate(DateTimeUtils.parseTimestamp(this.stringDate()));
                object = cmsLogDAO.insert(cms);
            }
        } else {
            cms.setConsoleOperatorDate(DateTimeUtils.parseTimestamp(this.stringDate()));
            object = cmsLogDAO.insert(cms);
        }

        return (Integer) object;
    }


    /***
     *
     * 记录绑定窗口、数据 id id值
     * */

    public Integer add(CmsLog cms, int windowId) throws Exception {
        Object object = null;
        String name;
        if (cms.getModuleName().equals("窗口")) {
            if (cms.getListObject() != null) {
                if (cms.getDataType() == 6) {
                    List list = cms.getListObject();
                    CmsWindow cmsWindow = this.cmsWindowDAO.selectByPrimaryKey(windowId);
                    for (int i = 1; i < list.size(); i++) {
                        CmsWindowData cmsDate = (CmsWindowData) list.get(i);
                        cms.setModuleContent("窗口:" + cmsWindow.getName() + " 自定义数据:" +
                                cmsDate.getUser_defined_name() + " 数据连接地址:" +
                                cmsDate.getUser_defined_url());
                        cms.setType(4);
                        cms.setConsoleOperatorDate(DateTimeUtils.parseTimestamp(this.stringDate()));
                        object = cmsLogDAO.insert(cms);
                    }
                }
            } else {
                for (Integer id : cms.getDataIds()) {
                    CmsWindow cmsWindow = this.cmsWindowDAO.selectByPrimaryKey(windowId);

                    name = this.queryData(id, cms.getDataType());
                    cms.setModuleContent("窗口:" + cmsWindow.getName() + " 绑定数据:" + name);
                    cms.setType(4);
                    cms.setConsoleOperatorDate(DateTimeUtils.parseTimestamp(this.stringDate()));
                    object = cmsLogDAO.insert(cms);
                }
            }
        } else if (cms.getModuleName().equals("页面")) {
            String id[] = cms.getIds().split(",");
            for (String anId : id) {
                CmsWindow cmsWindow = this.cmsWindowDAO.selectByPrimaryKey(Integer.valueOf(anId));
                CmsPage cmsPages = cmsPageDAO.selectByPrimaryKey(windowId);
                cms.setModuleContent(
                        "页面:" + cmsPages.getName() + " 绑定:" + " 窗口:" + cmsWindow.getName());
                cms.setType(4);
                cms.setConsoleOperatorDate(DateTimeUtils.parseTimestamp(this.stringDate()));
                object = cmsLogDAO.insert(cms);
            }

        }

        return (Integer) object;
    }

    /***
     *
     * 记录修改数据
     * */

    public Integer update(Object cms, int type) throws Exception {
        CmsLog cmsLog = new CmsLog();
        Object object = null;
        if (type == 6) {
            CmsWindowData cmsWindowData = (CmsWindowData) cms;
            CmsWindowData cw = cmsWindowDataDAO.selectByPrimaryKey(cmsWindowData.getWindowDataId());
            CmsWindow cmsWindow = this.cmsWindowDAO.selectByPrimaryKey(cw.getWindowId());
            cmsLog.setModuleContent("修改窗口:" + cmsWindow.getName() + " 绑定数据:" +
                    cmsWindowData.getUser_defined_name() + " 连接地址:" +
                    cmsWindowData.getUser_defined_url());
            cmsLog.setType(2);
            cmsLog.setConsoleOperatorDate(DateTimeUtils.parseTimestamp(this.stringDate()));
            cmsLog.setConsoleOperator(cmsWindowData.getModified());
            cmsLog.setModuleName("窗口");
            object = cmsLogDAO.insert(cmsLog);
        }
        return (Integer) object;
    }


    /***
     *
     * 记录删除窗口、数据 id id值
     * */
    public Integer del(CmsLog cms, int windowId) throws Exception {
        Object object = null;
        String name = null;
        if (cms.getModuleName().equals("窗口")) {
            if (cms.getDataIds() == null) {
                List<Integer> dataIds;
                dataIds = new ArrayList<>();
                if (cms.getDataId() != null) {
                    dataIds.add(cms.getDataId());
                    cms.setDataIds(dataIds);
                }

            }
            for (Integer id : cms.getDataIds()) {
                CmsWindow cmsWindow = this.cmsWindowDAO.selectByPrimaryKey(windowId);
                CmsWindowData cw = cmsWindowDataDAO.selectByPrimaryKey(id);
                if (cw != null) {
                    if (cw.getDataType() != 6) {
                        name = this.queryData(cw.getDataId(), cw.getDataType());
                    } else {
                        name = cw.getUser_defined_name();
                    }

                }
                cms.setModuleContent("删除窗口:" + cmsWindow.getName() + " 绑定的数据:" + name);
                cms.setType(3);
                cms.setConsoleOperatorDate(DateTimeUtils.parseTimestamp(this.stringDate()));
                object = cmsLogDAO.insert(cms);
            }
        } else if (cms.getModuleName().equals("页面")) {
            String id[] = cms.getIds().split(",");
            for (String anId : id) {
                CmsWindow cmsWindow = this.cmsWindowDAO.selectByPrimaryKey(Integer.valueOf(anId));
                CmsPage cmsPages = cmsPageDAO.selectByPrimaryKey(windowId);
                cms.setModuleContent("删除:" + cmsPages.getName() + "页面的窗口:" + cmsWindow.getName());
                cms.setType(3);
                cms.setConsoleOperatorDate(DateTimeUtils.parseTimestamp(this.stringDate()));
                object = cmsLogDAO.insert(cms);
            }
        }

        return (Integer) object;
    }

    /**
     * 根据数据类型与数据Id查询数据
     */
    private String queryData(int id, int dataType) {
        String obj = null;
        try {

            switch (dataType) {
                case 0:
                    ViewProductInfo v = viewProductInfoDAO.selectByPrimaryKey(id);
                    obj = v.getProcuctName();
                    break;
                case 1:
                    ViewPromotion viewPromotion = viewPromotionDAO.selectByPrimaryKey(id);
                    obj = viewPromotion.getPromotionName();
                    break;
                case 2:
                    Category c = categoryDAO.selectByPrimaryKey(id);
                    obj = c.getCategoryName();
                    break;
                case 3:
                    ProdBrand p = prodBrandDAO.selectByPrimaryKey(id);
                    obj = p.getBrandName();
                    break;
                case 4:
                    CmsWindow w = cmsWindowDAO.selectByPrimaryKey(id);
                    obj = w.getName();
                    break;
                case 7:
                    Category cs = categoryDAO.selectByPrimaryKey(id);
                    obj = cs.getCategoryName();
                    break;
            }
        } catch (Exception e) {
        	logger.error("CmsLogServiceImpl.queryData异常：" + e.getMessage(), e);
        }
        return obj;
    }

    /***
     *
     * 跟据id查询日志记录
     * */
    public CmsLog selectByPrimaryKey(Integer cmsLogId) throws SQLException {
        return cmsLogDAO.selectByPrimaryKey(cmsLogId);
    }

    /**
     * 分页查询日志记录
     *
     * @throws Exception 异常
     */
    public Page searchPageByVo(Page pageParam, CmsLog vo) throws Exception {
        if (pageParam == null) {
            pageParam = new Page();
        }
        if (vo == null) {
            vo = new CmsLog();
        }
        int totalNum = cmsLogDAO.count(vo);
        pageParam.setRecordCount(totalNum);
        vo.setStartIndex(pageParam.getStartIndex());
        vo.setEndIndex(pageParam.getStartIndex() + pageParam.getPageSize());
        pageParam.setDataList(cmsLogDAO.select(vo));
        return pageParam;
    }


//    public void setCmsLogDAO(CmsLogDAO cmsLogDAO) {
//        this.cmsLogDAO = cmsLogDAO;
//    }
//
//
//    public void setCmsPageDAO(CmsPageDAO cmsPageDAO) {
//        this.cmsPageDAO = cmsPageDAO;
//    }
//
//
//    public void setCmsPageWindowDAO(CmsPageWindowDAO cmsPageWindowDAO) {
//        this.cmsPageWindowDAO = cmsPageWindowDAO;
//    }
//
//
//    public void setCmsWindowDAO(CmsWindowDAO cmsWindowDAO) {
//        this.cmsWindowDAO = cmsWindowDAO;
//    }
//
//
//    public void setCmsTemplateDAO(CmsTemplateDAO cmsTemplateDAO) {
//        this.cmsTemplateDAO = cmsTemplateDAO;
//    }
//
//
//    public void setViewProductInfoDAO(ViewProductInfoDAO viewProductInfoDAO) {
//        this.viewProductInfoDAO = viewProductInfoDAO;
//    }
//
//
//    public void setCategoryDAO(CategoryDAO categoryDAO) {
//        this.categoryDAO = categoryDAO;
//    }
//
//
//    public void setProdBrandDAO(ProdBrandDAO prodBrandDAO) {
//        this.prodBrandDAO = prodBrandDAO;
//    }
//
//
//    public void setViewPromotionDAO(ViewPromotionDAO viewPromotionDAO) {
//        this.viewPromotionDAO = viewPromotionDAO;
//    }
}
