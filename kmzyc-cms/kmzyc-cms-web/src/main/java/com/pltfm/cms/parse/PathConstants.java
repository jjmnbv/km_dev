package com.pltfm.cms.parse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.kmzyc.util.SpringUtils;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.dao.BnesBrowsingHisDAO;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.KeyWords;
import com.pltfm.cms.service.CmsPromotionService;
import com.pltfm.cms.vobject.CmsPromotionTask;
import com.pltfm.cms.vobject.CmsSite;
import com.whalin.MemCached.MemCachedClient;

/**
 * 路径存放常量类
 *
 * @author zhl
 * @since 2013-09-27
 */
@Component
public class PathConstants implements ApplicationListener {
    private static final Logger logger = LoggerFactory.getLogger(PathConstants.class);
    private static Map<Integer, CmsSite> map = new HashMap<Integer, CmsSite>();
    static List<CmsSite> cmsSiteList = new ArrayList<CmsSite>();
    static JdbcTemplate jdbcTemplate;

    /**
     * 敏感词集合
     */
    static List<KeyWords> listKeyWords = new ArrayList<KeyWords>();


    @Resource(name = "bnesBrowsingHisDAO")
    private BnesBrowsingHisDAO bnesBrowsingHisDAO;

	/*
     *  ******************************本地静态页面文件输出路径
	 */


    /**
     * 供应商店铺地址
     */
    public static String shopMainPath(Integer siteId) {
        //String channel = map.get(siteId).getEngName();
        //return PropertyFileConfigurer.getContextProperty("shopMainPath_" + channel);

        return map.get(siteId).getPageOutputPath() + "/" + "supply";
    }

    /**
     * 关闭的供应商店铺地址
     */
    public static String colsedShopPath(Integer siteId) {


        return map.get(siteId).getPageOutputPath() + "/" + "colsedShop";


    }

    /**
     * 静态页面存放路径
     */
    public static String publishPath(Integer siteId) {
        return map.get(siteId).getPageOutputPath();
    }

    /**
     * 详细静态页面存放路径
     */

    public static String detailPublishPath(Integer siteId) {
        return map.get(siteId).getPageOutputPath() + "/" + "products";
    }

    /**
     * 详细静态页面秒杀存放路径
     */

    public static String promotionPublishPath(Integer siteId) {
        return map.get(siteId).getPageOutputPath() + "/" + "promotion";
    }

    /**
     * 套餐静态页面存放路径
     */

    public static String dulitaocanPublishPath(Integer siteId) {
        return map.get(siteId).getPageOutputPath() + "/" + "suit";
    }

    /**
     * 套餐静态页面预览路径
     */

    public static String viewdulitaocan(Integer siteId) {
        return map.get(siteId).getPageOutputPath() + "/" + "viewsuit";
    }

    /**
     * 预览页面存放地址
     */
    public static String viewPagePublishPath(Integer siteId) {
        return map.get(siteId).getPageOutputPath() + "/" + "view";
    }

    /**
     * 页面可视化存放地址
     */
    public static String visualizationPagePublishPath(Integer siteId) {
        return map.get(siteId).getPageOutputPath() + "/" + "visualization";
    }

    /**
     * 资讯文章输出路径
     */
    public static String informPublish(Integer siteId) {
        String publishPath = map.get(siteId).getPageOutputPath();
        return publishPath + "/" + "helps";
    }

    /**
     * 专题页输出路径
     */
    public static String holidayPublish(Integer siteId) {
        String publishPath = map.get(siteId).getPageOutputPath();
        return publishPath + "/" + "topic";
    }

	/*
	 ****************************** 本地所有模板输出路径
	 */

    /**
     * 页面模板存放位置
     */
    public static String pageTemPath(Integer siteId) {
        String templatePath = map.get(siteId).getTemplatePath();
        return templatePath + "/" + "page";
    }

    /**
     * 线上页面模板存放位置
     */
    public static String pageOnlineTemPath(Integer siteId) {
        String templatePath = map.get(siteId).getTemplatePath();
        return templatePath + "/" + "page-online";
    }

    /**
     * 窗口模板存放位置
     */
    public static String windowTemPath(Integer siteId) {
        String templatePath = map.get(siteId).getTemplatePath();
        return templatePath + "/" + "window";
    }

    /**
     * 风格模板存放位置
     */
    public static String stylesTemPath(Integer siteId) {
        String templatePath = map.get(siteId).getTemplatePath();
        return templatePath + "/" + "styles";
    }

    /**
     * 线上窗口模板存放位置
     */
    public static String windowOnlineTemPath(Integer siteId) {
        String templatePath = map.get(siteId).getTemplatePath();
        return templatePath + "/" + "window-online";
    }

    /**
     * 所有模板存放位置
     */
    public static String templatePath(Integer siteId) {
        String templatePath = map.get(siteId).getTemplatePath();
        return templatePath + "/" + "template";
    }

    /**
     * 咨询内容存放位置
     */
    public static String informPath(Integer siteId) {
        String templatePath = map.get(siteId).getTemplatePath();
        return templatePath + "/" + "inform";
    }

    /**
     * 静态页面模板存放位置
     */
    public static String staticHolidayTemplatePath(Integer siteId) {
        String templatePath = map.get(siteId).getTemplatePath();
        return templatePath + "/" + "topic";
    }

    /**
     * 广告模板存放位置
     */
    public static String advPath(Integer siteId) {
        String templatePath = map.get(siteId).getTemplatePath();
        return templatePath + "/" + "adv";
    }

    /**
     * 广告模板存放位置
     */
    public static String advSupplierPath(Integer siteId) {
        String templatePath = map.get(siteId).getTemplatePath();
        return templatePath + "/" + "adv" + "/" + "supplier";
    }

    /**
     * 广告个性模板存放位置
     */
    public static String advTempPath(Integer siteId) {
        String templatePath = map.get(siteId).getTemplatePath();
        return templatePath + "/" + "adv" + "/" + "advTemp";
    }

    /**
     * 活动文件
     */
    public static String promotion(Integer siteId) {
        String templatePath = map.get(siteId).getTemplatePath();
        return templatePath + "/" + "promotion";
    }

    /**
     * 活动输出文件
     */
    public static String promotionOut(Integer siteId) {
        String templatePath = map.get(siteId).getTemplatePath();
        return templatePath + "/" + "promotion";
    }

	
   /*
    *  ******************************远程地址访问的变量
    */

    /**
     * 图片PUT
     */
    public static String imagePut() {
        return ConfigurationUtil.getString("imagePut");
    }

    /**
     * 静态页面
     */
    public static String staticPath(Integer siteId) {
        String engName = map.get(siteId).getEngName();

        return ConfigurationUtil.getString("staticPath_" + engName);
    }

    /**
     * 供应商图片输出
     */
    public static String supplierImagePut() {
        return ConfigurationUtil.getString("imagePut_supplier");
    }

    /**
     * 搜索输出
     */
    public static String searchPath_B2B(Integer siteId) {
        String engName = map.get(siteId).getEngName();
        return ConfigurationUtil.getString("searchPath_" + engName);
    }

    /**
     * CMS称之为图片输出
     */
    public static String cmsPath_B2B(Integer siteId) {
        String engName = map.get(siteId).getEngName();
        return ConfigurationUtil.getString("cmsPath_" + engName);
    }

    /**
     * AppPUT
     */
    public static String appPut() {
        return ConfigurationUtil.getString("appPut");
    }

    /**
     * AppPath
     */
    public static String appPath() {
        return ConfigurationUtil.getString("appPath");
    }

    /**
     * cms系统图片输出地址
     */
    public static String cmsPicPath() {
        return ConfigurationUtil.getString("cmsPath");
    }

    /**
     * cms系统供应商图片输出地址
     */
    public static String cmsSupplierPicPath() {
        return ConfigurationUtil.getString("imagePut_supplier");
    }


    /**
     * 页面预览地址
     */
    public static String pageViewPath(Integer siteId) {
        String engName = map.get(siteId).getEngName();
        return ConfigurationUtil.getString("viewPath_" + engName);
    }

    /**
     * 页面可视化地址
     */
    public static String pageVisualizationPath(Integer siteId) {
        String engName = map.get(siteId).getEngName();
        return ConfigurationUtil.getString("visualizationPath_" + engName);
    }


    /**
     * 图片存放位置(设计、或运营他们用)
     */
    public static String imageOutPath(Integer siteId) {
        String engName = map.get(siteId).getEngName();
        return imagePut() + "/" + "pic" + engName;
    }

    /**
     * 图片访问路径((设计、或运营他们用))
     */
    public static String imgViewPath(Integer siteId) {
        String engName = map.get(siteId).getEngName();
        return cmsPicPath() + "/" + "pic" + engName;
    }

    /**
     *
     * @return
     */
    public static String applicationLogPath() {
        return ConfigurationUtil.getString("applicationLog");
    }

    /**
     * 通过站点主键获取对应站点信息
     */
    public static CmsSite getCmsSite(Integer siteId) {
        return map.get(siteId);
    }

    // 通过站点得对应的变量path

    /**
     * var 变量key type 页面类型 siteId 站点Id
     */
    public static String getSitePath(String var, String type, Integer siteId) {
        // 动态页面
        if (!"mail".equals(type)) {
            return getPageVarPath(type, var, siteId);
            // 静态页面
        } else {
            String engName = map.get(siteId).getEngName();
            return ConfigurationUtil.getString(var + "_" + engName);
        }
    }

    public static String getPageVarPath(String type, String var, Integer siteId) {
        String varPath = "";
        String engName = map.get(siteId).getEngName();
        if ("portal".equals(type)) {
            // portal ,WAP区分变量
            if (engName.equals("WAP")) {
                varPath = "<%=ConfigurationUtil.getString(\"" + var + "_" + engName + "\")%>";
            } else {
                varPath = "<%=ConfigurationUtil.getString(\"" + var + "\")%>";
            }

        } else if ("search".equals(type)) {// 搜索引擎的一套变量转化
            varPath = "<%=Configuration.getContextProperty(\"" + var + "_" + engName + "\")%>";
        } else {
            varPath = "<!--#echo var=\"" + var + "\" -->";
        }
        return varPath;
    }

    public static Map getMap() {
        return map;
    }

    public static void setMap(Map map) {
        PathConstants.map = map;
    }

    /**
     * 项目初始化的时候加载站点信息
     */
    @Override
    @SuppressWarnings("unchecked")
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            ContextRefreshedEvent cre = (ContextRefreshedEvent) event;
            if (cre.getApplicationContext().getParent() == null) {// root
                // application
                // context
                jdbcTemplate = SpringUtils.getBean("jdbcTemplate");
                List<CmsSite> list = jdbcTemplate
                        .query("select site_id,name,template_path,page_output_path,url,/*channel,*/engName from cms_site",
                                new RowMapper() {
                                    @Override
                                    public CmsSite mapRow(ResultSet rs, int rowNum) throws
                                            SQLException {
                                        CmsSite cmsSite = new CmsSite();
                                        cmsSite.setSiteId(rs.getInt(1));
                                        cmsSite.setName(rs.getString(2));
                                        cmsSite.setTemplatePath(rs.getString(3));
                                        cmsSite.setPageOutputPath(rs.getString(4));
                                        cmsSite.setUrl(rs.getString(5));
//								cmsSite.setChannel(rs.getString(6));
                                        cmsSite.setEngName(rs.getString(6));
                                        return cmsSite;
                                    }

                                });
                if (ListUtils.isNotEmpty(list)) {
                    cmsSiteList = list;
                    for (CmsSite cmsSite : list) {
                        map.put(cmsSite.getSiteId(), cmsSite);
                    }
                }
                // 项目启动时将活动的访问地址放入缓存
                promotionStartup();
            }
        }
        try {
            listKeyWords = bnesBrowsingHisDAO.findKeyWords();
        } catch (Exception e) {
        }

    }

    /**
     * 站点信息变动的时候更新初始化加载出的站点信息
     */
    @SuppressWarnings("unchecked")
    public static void updateSiteInfo() {
        List<CmsSite> list = jdbcTemplate
                .query("select site_id,name,template_path,page_output_path,url,/*channel,*/engName from cms_site",
                        new RowMapper() {
                            @Override
                            public CmsSite mapRow(ResultSet rs, int rowNum) throws SQLException {
                                CmsSite cmsSite = new CmsSite();
                                cmsSite.setSiteId(rs.getInt(1));
                                cmsSite.setName(rs.getString(2));
                                cmsSite.setTemplatePath(rs.getString(3));
                                cmsSite.setPageOutputPath(rs.getString(4));
                                cmsSite.setUrl(rs.getString(5));
//						cmsSite.setChannel(rs.getString(6));
                                cmsSite.setEngName(rs.getString(6));
                                return cmsSite;
                            }

                        });
        if (ListUtils.isNotEmpty(list)) {
            cmsSiteList = list;
            for (CmsSite cmsSite : list) {
                map.put(cmsSite.getSiteId(), cmsSite);
            }
        }
    }

    // 项目启动时将活动对应的访问路径放到缓存中
    public void promotionStartup() {
        try {
            // 通过上下文获取到CmsPromotionService、MemCachedClient对象
            CmsPromotionService cmsPromotionService = SpringUtils.getBean("cmsPromotionService");
            MemCachedClient memcachedClient = SpringUtils.getBean("memcachedClient");
            if (cmsPromotionService != null) {
                // 获取所有的活动信息
                logger.info("获取所有的活动信息");
                List<CmsPromotionTask> list = cmsPromotionService.getAllPromotionTask(null);
                logger.info("活动输出路径放到缓存");
                if (memcachedClient != null && list != null) {
                    for (CmsPromotionTask promotionTask : list) {
                        // 活动输出路径放到缓存
                        memcachedClient.set("cms_url_promotion_id_" + promotionTask.getId() + "",
                                promotionTask.getOutput());
                    }
                }
                logger.info("活动输出路径放到缓存完成");
            }
        } catch (Exception e) {
        	logger.error("PathConstants.promotionStartup异常：" + e.getMessage(), e);
        }
    }

    public static List<CmsSite> getCmsSiteList() {
        return cmsSiteList;
    }

    public static void setCmsSiteList(List<CmsSite> cmsSiteList) {
        PathConstants.cmsSiteList = cmsSiteList;
    }

    /**
     * 产品新旧IdMap
     */
//    public static Map<Long, Long> getUpholdMap() {
//        return upholdMap;
//    }


    /**
     * maliqun add
     * 检查是否包含特殊字符
     */
    public static boolean checkKeyWords(String targetValidStr) {
        boolean validResult = false;
        for (int i = 0; i < PathConstants.listKeyWords.size(); i++) {
            if (targetValidStr.contains(PathConstants.listKeyWords.get(i).getKeyWords())) {
                validResult = true;
                break;
            }
        }
        return validResult;
    }


    public BnesBrowsingHisDAO getBnesBrowsingHisDAO() {
        return bnesBrowsingHisDAO;
    }

    public void setBnesBrowsingHisDAO(BnesBrowsingHisDAO bnesBrowsingHisDAO) {
        this.bnesBrowsingHisDAO = bnesBrowsingHisDAO;
    }
}
