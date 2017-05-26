package com.pltfm.cms.action;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.util.AjaxUtil;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.util.Token;
import com.pltfm.cms.parse.DataType;
import com.pltfm.cms.parse.HtmlBuilder;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.service.CmsAdcolumnDataService;
import com.pltfm.cms.service.CmsAdvService;
import com.pltfm.cms.service.CmsSiteDataService;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.util.HtmlCompressor;
import com.pltfm.cms.util.SqlJoinUtil;
import com.pltfm.cms.vobject.CmsAdcolumn;
import com.pltfm.cms.vobject.CmsAdv;
import com.pltfm.cms.vobject.CmsSiteData;
import com.pltfm.cms.vobject.KeyWords;
import com.pltfm.sys.bean.SysUserBean;
import com.pltfm.sys.model.SysUser;

@Component("cmsAdvAction")
@Scope("prototype")
public class CmsAdvAction extends UploadFileAction {
    private static final long serialVersionUID = -2526125638862446742L;
    private List<CmsAdv> AdvList;
    private Integer adminType;
    private Page page;
    private Integer pageNo, siteId;
    private Integer Advid;

    private Integer[] levelId;
    private String checkeds;//多选框
    private String cmsAdcolumnname;

    private Integer dataType;   //数据类型

    /**
     * 跳转广告列表
     */
    public String openAdvList() {
        return "openAdvList";
    }

    /**
     * 用户Id
     */
    private Integer userId;

    @Resource(name = "htmlBuilder")
    HtmlBuilder htmlBuilder;
    // 广告对象
    @Resource(name = "cmsAdvService")
    CmsAdvService cmsAdvService;
    // 广告位对象
    @Resource(name = "cmsAdcolumnDataService")
    CmsAdcolumnDataService cmsAdcolumnDataService;

    @Resource(name = "cmsSiteDataService")
    CmsSiteDataService cmsSiteDataService;
    ActionContext actionContext = ActionContext.getContext();
    Map session = actionContext.getSession();

    List cmsAdcolumnList;

    CmsAdcolumn adcolumn;
    CmsAdv cmsAdv;
  /*  @Resource(name="templateConfig")
    private HashMap pathConfig;*/


    private Map<String, String> mp;

    private static Logger logger = LoggerFactory.getLogger(CmsAdvAction.class);


    public String List() {

        try {
            keyWords = null;
            page = cmsAdvService.queryForPage(cmsAdv, page);
            return "Success";
        } catch (Exception e) {
            logger.error("CmsAdvAction.List广告列表异常：" + e.getMessage(), e);
            return "Error";
        }
    }

    /**
     * 弹出层广告
     */
    public String queryByAdv() {
        try {

            cmsAdv = new CmsAdv();
            if (page == null && keyWords != null) {
                page = new Page();
                page.setPageNo(keyWords.getPageNo());
                page.setPageSize(keyWords.getPageSize());
            }
            if (keyWords != null) {
                cmsAdv.setName(keyWords.getName_keyword().trim());
                cmsAdv.setBeginTime(keyWords.getBeginTime_keyword());
                cmsAdv.setEndTime(keyWords.getEndTime_keyword());
                if (keyWords.getAdcolumnId_keyword() == null || keyWords.getAdcolumnId_keyword() == 0) {
                    cmsAdv.setAdcolumnId(null);
                } else {

                    //adcolumnId = keyWords.getAdcolumnId_keyword();
                    cmsAdv.setAdcolumnId(keyWords.getAdcolumnId_keyword());
                }
                if (keyWords.getStatus_keyword() == null)
                    cmsAdv.setStatus(null);
                else
                    cmsAdv.setStatus(keyWords.getStatus_keyword());
            }
            cmsAdv.setSiteId(siteId);
            page = cmsAdvService.queryForPage(cmsAdv, page);


            List<CmsSiteData> dataList = null;

            dataList = cmsSiteDataService.queryByUserIdAndSiteIdAndDataType(userId, siteId, dataType);

            if (page.getDataList().size() != 0) {
                for (int i = 0; i < page.getDataList().size(); i++) {
                    CmsAdv cmsAdv = (CmsAdv) page.getDataList().get(i);
                    for (int j = 0; j < dataList.size(); j++) {
                        CmsSiteData cmsSiteData = dataList.get(j);
                        if (cmsAdv.getAdvId().equals(cmsSiteData.getDataId())) {
                            cmsAdv.setFlag(1);//1表示已选择
                            break;
                        }
                    }
                }
            }

            if (keyWords == null) {
                keyWords = new KeyWords();
            }
            keyWords.setPageNo(page.getPageNo());
            keyWords.setPageSize(page.getPageSize());
            return "queryByAdv";
        } catch (Exception e) {
            logger.error("弹出层广告异常", e);
            return "Error";
        }

    }

//  public String queryAdv()
//  {
//      
//      SysUser sysuser = (SysUser)session.get("sysUser");
//      String ids=null;
//      try {
//          if(adminType!=null&&adminType==0){
//          List lsits=cmsSiteDataService.listToString(sysuser.getUserId(),2);
//          if(lsits!=null&&lsits.size()!=0){
//              ids=SqlJoinUtil.getSqlIn(lsits,1000,"ADV_ID");}
//          }
//      } catch (Exception e1) {
//          // TODO Auto-generated catch block
//          e1.printStackTrace();
//      }
//      try {
//          
//          
//          if(cmsAdv==null){
//              cmsAdv = new CmsAdv();
//          }
//          Integer siteId = (Integer)session.get("siteId");
//          cmsAdv.setSiteId(siteId);//新加的站点ID
//          cmsAdv.setIds(ids);//新加的ID集合
//          if(page==null&&keyWords!=null)
//          {
//              page=new Page();
//              if(keyWords.getPageNo()!=null&&keyWords.getPageSize()!=null){
//                  page.setPageNo(keyWords.getPageNo());
//                  page.setPageSize(keyWords.getPageSize());
//              }
//          
//          }
//          if(keyWords!=null)
//          {
//              if(keyWords.getName_keyword()!=null){
//                  cmsAdv.setName(keyWords.getName_keyword().trim());
//              }
//              if(keyWords.getBeginTime_keyword()!=null){
//                  cmsAdv.setBeginTime(keyWords.getBeginTime_keyword());
//              }
//              if(keyWords.getEndTime_keyword()!=null){
//                  cmsAdv.setEndTime(keyWords.getEndTime_keyword());
//              }
//              
//              
//              if(keyWords.getAdcolumnId_keyword()==null||keyWords.getAdcolumnId_keyword()==0){
//                  cmsAdv.setAdcolumnId(null);
//              }
//              else{
//                  cmsAdv.setAdcolumnId(keyWords.getAdcolumnId_keyword());
//                  
//                  List list=cmsAdvService.adcolumnList(siteId);
//                  mp = new LinkedHashMap<String,String>();
//                  if(null!=list&&list.size()>0){
//                      for(int i=0;i<list.size();i++){
//                          CmsAdcolumn cmsadcolumn=(CmsAdcolumn)list.get(i); 
//                          mp.put(cmsadcolumn.getAdcolumnId()+"",cmsadcolumn.getName());
//                      }
//                  }
//                  for (Entry<String, String> entry : mp.entrySet()) {  
//                    if(cmsAdv.getAdcolumnId() == Integer.parseInt(entry.getKey()) || cmsAdv.getAdcolumnId().equals(Integer.parseInt(entry.getKey()))){
//                        cmsAdcolumnname = entry.getValue();
//                    }
//                   } 
//              }
//              if(keyWords.getStatus_keyword()==null)
//                  cmsAdv.setStatus(null);
//              else
//                  cmsAdv.setStatus(keyWords.getStatus_keyword());
//          }
//          this.page = this.cmsAdvService.queryForPage(cmsAdv, page);
//          if(keyWords==null)
//          {
//              keyWords=new KeyWords();
//          }
//          keyWords.setPageNo(page.getPageNo());
//          keyWords.setPageSize(page.getPageSize());
//          
//          sysUserMap=new HashMap();
//          SysUserBean bean = SysUserBean.instance();
//          SysUser vo=new SysUser();
//          List<SysUser> list=bean.getSysUserList(vo);
//          for(SysUser user:list)
//          {
//              sysUserMap.put(user.getUserId()+"", user.getUserName());
//          }
//          return "Success";
//      } catch (Exception e) {
//          logger.error("广告列表查询异常", e);
//          return "Error";
//      }
//  }

    public String queryAdv() {


        try {

            if (cmsAdv == null) {
                cmsAdv = new CmsAdv();
            }

            String ids = null;
            Integer siteId = (Integer) session.get("siteId");
            SysUser sysuser = (SysUser) session.get("sysUser");
            if (adminType != null && adminType == 0) {
                List lsits = cmsSiteDataService.listToString(sysuser.getUserId(), 2);
                if (lsits != null && lsits.size() != 0) {
                    ids = SqlJoinUtil.getSqlIn(lsits, 1000, "ADV_ID");
                }
                cmsAdv.setIds(ids);//新加的ID集合
            }


            if (page == null && keyWords != null) {
                page = new Page();
                if (keyWords.getPageNo() != null && keyWords.getPageSize() != null) {
                    page.setPageNo(keyWords.getPageNo());
                    page.setPageSize(keyWords.getPageSize());
                }

            }
            if (keyWords != null) {
                if (keyWords.getName_keyword() != null) {
                    cmsAdv.setName(keyWords.getName_keyword().trim());
                }
                if (keyWords.getBeginTime_keyword() != null) {
                    cmsAdv.setBeginTime(keyWords.getBeginTime_keyword());
                }
                if (keyWords.getEndTime_keyword() != null) {
                    cmsAdv.setEndTime(keyWords.getEndTime_keyword());
                }


                if (keyWords.getAdcolumnId_keyword() == null || keyWords.getAdcolumnId_keyword() == 0) {
                    cmsAdv.setAdcolumnId(null);
                } else {
                    cmsAdv.setAdcolumnId(keyWords.getAdcolumnId_keyword());

                    List list = cmsAdvService.adcolumnList(siteId);
                    mp = new LinkedHashMap<String, String>();
                    if (null != list && list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            CmsAdcolumn cmsadcolumn = (CmsAdcolumn) list.get(i);
                            mp.put(cmsadcolumn.getAdcolumnId() + "", cmsadcolumn.getName());
                        }
                    }
                    for (Entry<String, String> entry : mp.entrySet()) {
                        if (cmsAdv.getAdcolumnId() == Integer.parseInt(entry.getKey()) || cmsAdv.getAdcolumnId().equals(Integer.parseInt(entry.getKey()))) {
                            cmsAdcolumnname = entry.getValue();
                        }
                    }
                }
                if (keyWords.getStatus_keyword() != null)
                    cmsAdv.setStatus(keyWords.getStatus_keyword());
            }
            if (null != siteId) {
                cmsAdv.setSiteId(siteId);//新加的站点ID
            }

            this.page = this.cmsAdvService.queryForPage(cmsAdv, page);
            if (keyWords == null) {
                keyWords = new KeyWords();
            }
            keyWords.setPageNo(page.getPageNo());
            keyWords.setPageSize(page.getPageSize());

            sysUserMap = new HashMap();
            SysUserBean bean = SysUserBean.instance();
            SysUser vo = new SysUser();
            List<SysUser> list = bean.getSysUserList(vo);
            for (SysUser user : list) {
                sysUserMap.put(user.getUserId() + "", user.getUserName());
            }
            return "Success";
        } catch (Exception e) {
            logger.error("广告列表查询异常", e);
            return "Error";
        }
    }

    //供应商广告查询
    public String querySupplierAdv() {
        
    /*  SysUser sysuser = (SysUser)session.get("sysUser");
        String ids=null;
        try {
            if(adminType!=null&&adminType==0){
            List lsits=cmsSiteDataService.listToString(sysuser.getUserId(),2);
            if(lsits!=null&&lsits.size()!=0){
            ids=SqlJoinUtil.getSqlIn(lsits,1000,"ADV_ID");}
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        */
        try {


            if (cmsAdv == null) {
                cmsAdv = new CmsAdv();
            }
//            Integer siteId = (Integer) session.get("siteId");
            //      cmsAdv.setSiteId(siteId);//新加的站点ID
            //      cmsAdv.setIds(ids);//新加的ID集合
            if (page == null && keyWords != null) {
                page = new Page();
                if (keyWords.getPageNo() != null && keyWords.getPageSize() != null) {
                    page.setPageNo(keyWords.getPageNo());
                    page.setPageSize(keyWords.getPageSize());
                }

            }
            if (keyWords != null) {
                if (keyWords.getName_keyword() != null) {
                    cmsAdv.setName(keyWords.getName_keyword().trim());
                }
                if (keyWords.getBeginTime_keyword() != null) {
                    cmsAdv.setBeginTime(keyWords.getBeginTime_keyword());
                }
                if (keyWords.getEndTime_keyword() != null) {
                    cmsAdv.setEndTime(keyWords.getEndTime_keyword());
                }


                if (keyWords.getAdcolumnId_keyword() == null || keyWords.getAdcolumnId_keyword() == 0)
                    cmsAdv.setAdcolumnId(null);
                else
                    cmsAdv.setAdcolumnId(keyWords.getAdcolumnId_keyword());
                if (keyWords.getStatus_keyword() == null)
                    cmsAdv.setStatus(null);
                else
                    cmsAdv.setStatus(keyWords.getStatus_keyword());
            }
            page = cmsAdvService.queryForPage(cmsAdv, page);
            if (keyWords == null) {
                keyWords = new KeyWords();
            }
            keyWords.setPageNo(page.getPageNo());
            keyWords.setPageSize(page.getPageSize());

            sysUserMap = new HashMap();
            SysUserBean bean = SysUserBean.instance();
            SysUser vo = new SysUser();
            List<SysUser> list = bean.getSysUserList(vo);
            for (SysUser user : list) {
                sysUserMap.put(user.getUserId() + "", user.getUserName());
            }
            return "Success";
        } catch (Exception e) {
            logger.error("供应商广告查询异常", e);
            return "Error";
        }
    }

    public String addgoto() {
        return "goto";
    }

    /*  添加广告 */
    @Token
    public String Add() {

        try {
            Integer siteId = (Integer) session.get("siteId");
            cmsAdv.setSiteId(siteId);
            SysUser sysuser = (SysUser) session.get("sysUser");

            cmsAdv.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
            cmsAdv.setModified(sysuser.getUserId());

            int s = cmsAdvService.addCmsAdv(file, file2, file3, file4, file5, cmsAdv);

            try {
                List lsits = cmsSiteDataService.listToString(sysuser.getUserId(), 2);
                if (lsits != null && lsits.size() != 0) {
                    cmsSiteDataService.addCSD(cmsAdv.getAdvId(), 2, sysuser.getUserId(), siteId, sysuser.getUserId());
                }
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                logger.error("广告添入站点异常", e1);
            }

            if (s == 0) {
                this.addActionMessage(ConfigurationUtil.getString("add.fail"));
                return queryAdv();

            } else {/*加入用户数据管理*/
                List lsits = cmsSiteDataService.listToString(sysuser.getUserId(), 2);
                if (lsits != null && lsits.size() != 0) {
                    try {
                        cmsSiteDataService.addCSD(cmsAdv.getAdvId(), 2, sysuser.getUserId(), siteId, sysuser.getUserId());
                    } catch (Exception e) {
                        logger.error("广加入用户数据管理异常", e);
                    }
                }
            }
            //   cmsAdv=new CmsAdv();


        } catch (Exception e) {
            logger.error("广告添加异常", e);
            this.addActionMessage(ConfigurationUtil.getString("add.fail"));
            return "Error";
        }
        this.addActionMessage(ConfigurationUtil.getString("add.success"));
        //  this.keyWords=null;
        //保持状态
        this.keyWords = new KeyWords();
        keyWords.setName_keyword(cmsAdv.getName());
        return queryAdv();
    }

    /**
     * 根据ID查广告信息
     * Adv_Byid.action?Advid=
     */
    public String Byid() {
        try {

            cmsAdv = cmsAdvService.byid(Advid);

        } catch (Exception e) {
            logger.error("根据ID查广告信息异常", e);
            return "Error";
        }
        return "ByidSuccess";
    }

    @Token
    public String Update() {

        buildFile();
        try {
            SysUser sysuser = (SysUser) session.get("sysUser");

            cmsAdv.setModifyDate(DateTimeUtils.getCalendarInstance().getTime());
            cmsAdv.setModified(sysuser.getUserId());
            cmsAdv.setStatus(2);

            cmsAdvService.update(file, file2, file3, file4, file5, file6, cmsAdv);
            //  cmsAdv=new CmsAdv();
            this.addActionMessage(ConfigurationUtil.getString("update.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("update.fail"));
            logger.error("广告修改异常", e);
            return queryAdv();
        }
        //保持状态
        this.keyWords = new KeyWords();
        keyWords.setName_keyword(cmsAdv.getName());

        return queryAdv();
    }


    /**
     * 添加广告中的广告位LIST
     */

    public String Add_ajax() {

        try {
//          Integer siteId = (Integer)session.get("siteId");
//          List list=cmsAdvService.adcolumnList(siteId);
//          JSONObject json=new JSONObject();
//          json.put("advlist", list);
//          HttpServletResponse response = ServletActionContext.getResponse();
//          response.setContentType("text/html;charset=utf-8");
//          response.getWriter().print(json);

            Integer siteId = (Integer) session.get("siteId");
            List list = cmsAdvService.adcolumnList(siteId);
            mp = new LinkedHashMap<String, String>();
            if (null != list && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    CmsAdcolumn cmsadcolumn = (CmsAdcolumn) list.get(i);
                    mp.put(cmsadcolumn.getAdcolumnId() + "", cmsadcolumn.getName());
                }
            }
            AjaxUtil.writeJSONToResponse(mp);

        } catch (Exception e) {
            logger.error("添加广告中的广告位LIST异常", e);
        }
        return null;
    }

    /**
     * 根据ID删除广告信息
     */
    public String Delete() {
        try {
            int s = cmsAdvService.delete(Advid);
            if (s > 0) {
                this.addActionMessage(ConfigurationUtil.getString("delete.success"));
            } else {
                this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
                return queryAdv();
            }
        } catch (Exception e) {
            logger.error("根据ID删除广告信息异常", e);
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            this.keyWords = null;
            return queryAdv();
        }
        this.keyWords = null;
        return queryAdv();
    }

    /**
     * 根据ID数组删除广告位信息
     */
    @Token
    public String All_Delete() {
        try {
            for (int i = 0; i < levelId.length; i++) {
                cmsAdvService.delete(levelId[i]);
            }
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (Exception e) {
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
            logger.error("根据ID数组删除广告位信息异常", e);
            this.keyWords = null;
            return queryAdv();
        }
        this.keyWords = null;
        return queryAdv();
    }
    
    /*public String Adv_temp(){
        try {
              String content=cmsAdvService.Adv_Temp(Advid);
              HttpServletResponse response = ServletActionContext.getResponse();
              response.setContentType("text/html;charset=utf-8");
              response.getWriter().print(content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("异常：" + e.getMessage(), e);
            return "Error";
        }
        return null;
    }*/

    public String parseAll() {
        boolean res;
        try {
            res = this.allParse(this.checkeds);
            this.checkeds = null;
            cmsAdv = new CmsAdv();
            if (res) {
                this.addActionMessage(ConfigurationUtil.getString("adv.publish.succ"));
            } else {
                this.addActionMessage(ConfigurationUtil.getString("adv.publish.err"));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsAdvAction.parseAll多选发布报错：" + e.getMessage(), e);
            this.addActionMessage(ConfigurationUtil.getString("adv.publish.err"));
        }
        return this.queryAdv();
    }


    public boolean allParse(String checkeds) {
        String id[] = checkeds.split(",");
        int err = 0;
        for (int i = 0; i < id.length; i++) {
            try {

                byIdParse(Integer.parseInt(id[i]));

            } catch (Exception e) {
                err++;
                logger.error("CmsAdvAction.allParse广告单个发布方法异常：" + e.getMessage(), e);
            }
        }
        this.levelId = null;
        if (err > 0) {
            return false;
        } else {
            return true;
        }
    }


//  public String allParse(){
//      int err=0;
//      for(int i=0;i<levelId.length;i++){
//          try {
//              
//              byIdParse(levelId[i]);
//              
//          } catch (Exception e) {
//              err++;
//              logger.error("广告单个发布方法异常", e);
//          }
//          
//      }
//      this.levelId=null;
//      if(err>0){
//          this.addActionMessage(ConfigureUtils.getMessageConfig("adv.publish.err"));
//      }else{
//          this.addActionMessage(ConfigureUtils.getMessageConfig("adv.publish.succ"));
//      }
//      return queryAdv();
//  }


    // 单个发布方法
    public String byIdParse(int id) {

        // 对应广告
        try {

            CmsAdv cmsAdv = new CmsAdv();
            cmsAdv.setAdvId(id);
            cmsAdv = cmsAdvService.byid(cmsAdv.getAdvId());
            advPublish(cmsAdv.getAdvId());
            this.addActionMessage(ConfigurationUtil.getString("adv.publish.succ"));
//            cmsAdv = new CmsAdv();
            this.keyWords = null;
            return "success";
        } catch (Exception e) {
            logger.error("CmsAdvAction.byIdParse广告单个发布方法异常：" + e.getMessage(), e);
            this.addActionMessage(ConfigurationUtil.getString("adv.publish.err"));
        }

        return "fail";
    }

    // 单个发布方法
    public String parse() {
        try {

            advPublish(cmsAdv.getAdvId());
        } catch (Exception e) {
            logger.error("CmsAdvAction.parse广告单个发布方法异常：" + e.getMessage(), e);
            this.addActionMessage(ConfigurationUtil.getString("adv.publish.err"));
        }
        this.addActionMessage(ConfigurationUtil.getString("adv.publish.succ"));
        cmsAdv = new CmsAdv();
        this.keyWords = null;
        return queryAdv();

    }

    // 调度器
    @SuppressWarnings("unchecked")
    public void work() {
        try {

            List<CmsAdv> list = cmsAdvService.checkAllAdv();
            if (ListUtils.isNotEmpty(list)) {
                for (CmsAdv cmsadv : list) {
                    advPublish(cmsAdv.getAdvId());
                }
            }
        } catch (Exception e) {


            LOG.error("调度报错：", e);
        }
    }

    //广告发布方法
    public void advPublish(Integer advId) throws Exception {
        cmsAdv = cmsAdvService.byid(advId);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(DataType.advert.name(), cmsAdv);
        result.put(DataType.cssAndJsPath.name(), PathConstants.getSitePath("cssAndJsPath", "mail", cmsAdv.getSiteId()));
        adcolumn = cmsAdcolumnDataService.byid(cmsAdv
                .getAdcolumnId());

        // 选择对应模版

        String path = PathConstants.advTempPath(cmsAdv.getSiteId()) + "/" + cmsAdv.getAdvId() + ".html";
        String content = htmlBuilder.getHtml(path, result);

        if (!content.equals("")) {
            //更新广告状态(已发布)
            cmsAdv = cmsAdvService.findAdv(cmsAdv.getAdvId());
            cmsAdv.setStatus(1);
            cmsAdvService.update(null, null, null, null, null,null, cmsAdv);
        }
        // 广告位输出路径

        String outPath = PathConstants.publishPath(cmsAdv.getSiteId())
                + "/" + adcolumn.getOutput();

        // 判断pages文件夹是否存在，若不存在，则新建一个


        String outPathFile = "";
        if (adcolumn.getOutput() != null) {
            String outPutPath = adcolumn.getOutput().substring(0, adcolumn.getOutput().lastIndexOf("/"));
            if (!outPutPath.equals("")) {
                outPathFile = PathConstants.publishPath(cmsAdv.getSiteId()) + "/" + outPutPath;
            } else {
                outPathFile = PathConstants.publishPath(cmsAdv.getSiteId());
            }
        }

        // 判断pages文件夹是否存在，若不存在，则新建一个
        FileOperateUtils.checkAndCreateDirs(outPathFile);
        //写入广告位路径
        if (outPath.endsWith("js")) {
            content = java.net.URLEncoder.encode(content);
            content = "document.write(decodeURIComponent(\"" + content + "\").replace(/\\+/g,' '));";
            FileOperateUtils.writer(outPath, "true".equals(ConfigurationUtil.getString("isCompress")) ? HtmlCompressor.compress(content) : content);
            //shtml格式
        } else {
            FileOperateUtils.writer(outPath, "true".equals(ConfigurationUtil.getString("isCompress")) ? HtmlCompressor.compress(content) : content);
        }
    }

    //广告预览
    public void avdPreview() {
        HttpServletResponse response = ServletActionContext.getResponse();
        Map<String, Object> result = new HashMap<String, Object>();

        // 对应广告

        try {
            Integer siteId = (Integer) session.get("siteId");
            cmsAdv = cmsAdvService.byid(cmsAdv.getAdvId());
            result.put(DataType.advert.name(), cmsAdv);
            adcolumn = cmsAdcolumnDataService.byid(cmsAdv.getAdcolumnId());

            // 选择对应模版

            String path = PathConstants.advTempPath(siteId) + "/" + cmsAdv.getAdvId() + ".html";
            String content = htmlBuilder.getHtml(path, result);
            response.getWriter().write(content);


        } catch (Exception e) {
        	logger.error("CmsAdvAction.avdPreview广告预览异常：" + e.getMessage(), e);
        }
    }


    /**
     * 查广告位名是否存在
     */
    public void name_ajax() {
        try {
            Integer siteId = (Integer) session.get("siteId");

            if (siteId != null && cmsAdv != null)
                cmsAdv.setSiteId(siteId);
            int count = cmsAdvService.countByAdvname(this.cmsAdv);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(count);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsAdvAction.name_ajax查广告位名是否存在异常：" + e.getMessage(), e);

        }
    }


    public String getCmsAdcolumnname() {
        return cmsAdcolumnname;
    }

    public void setCmsAdcolumnname(String cmsAdcolumnname) {
        this.cmsAdcolumnname = cmsAdcolumnname;
    }

    public CmsAdvService getCmsAdvService() {
        return cmsAdvService;
    }

    public void setCmsAdvService(CmsAdvService cmsAdvService) {
        this.cmsAdvService = cmsAdvService;
    }

    public List<CmsAdv> getAdvList() {
        return AdvList;
    }

    public void setAdvList(List<CmsAdv> advList) {
        AdvList = advList;
    }

    public CmsAdv getCmsAdv() {
        return cmsAdv;
    }

    public void setCmsAdv(CmsAdv cmsAdv) {
        this.cmsAdv = cmsAdv;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }








    public Integer getAdvid() {
        return Advid;
    }

    public void setAdvid(Integer advid) {
        Advid = advid;
    }

    public Integer[] getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer[] levelId) {
        this.levelId = levelId;
    }




















    public HtmlBuilder getHtmlBuilder() {
        return htmlBuilder;
    }

    public void setHtmlBuilder(HtmlBuilder htmlBuilder) {
        this.htmlBuilder = htmlBuilder;
    }

    public CmsAdcolumnDataService getCmsAdcolumnDataService() {
        return cmsAdcolumnDataService;
    }

    public void setCmsAdcolumnDataService(
            CmsAdcolumnDataService cmsAdcolumnDataService) {
        this.cmsAdcolumnDataService = cmsAdcolumnDataService;
    }

    public List getCmsAdcolumnList() {
        return cmsAdcolumnList;
    }

    public void setCmsAdcolumnList(List cmsAdcolumnList) {
        this.cmsAdcolumnList = cmsAdcolumnList;
    }

    public CmsAdcolumn getAdcolumn() {
        return adcolumn;
    }

    public void setAdcolumn(CmsAdcolumn adcolumn) {
        this.adcolumn = adcolumn;
    }

  /*  public HashMap getPathConfig() {
        return pathConfig;
    }

    public void setPathConfig(HashMap pathConfig) {
        this.pathConfig = pathConfig;
    }*/


    public Integer getAdminType() {
        return adminType;
    }

    public void setAdminType(Integer adminType) {
        this.adminType = adminType;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }


    public CmsSiteDataService getCmsSiteDataService() {
        return cmsSiteDataService;
    }

    public void setCmsSiteDataService(CmsSiteDataService cmsSiteDataService) {
        this.cmsSiteDataService = cmsSiteDataService;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCheckeds() {
        return checkeds;
    }

    public void setCheckeds(String checkeds) {
        this.checkeds = checkeds;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }


}
