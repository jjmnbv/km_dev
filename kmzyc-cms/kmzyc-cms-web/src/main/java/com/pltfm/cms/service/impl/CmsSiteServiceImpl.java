package com.pltfm.cms.service.impl;

import com.pltfm.app.util.ListUtils;
import com.kmzyc.commons.page.Page;
import com.pltfm.cms.dao.CmsSiteDAO;
import com.pltfm.cms.dao.CmsSiteDataDAO;
import com.pltfm.cms.dao.CmsUserSiteDAO;
import com.pltfm.cms.service.CmsSiteService;
import com.pltfm.cms.vobject.CmsSite;
import com.pltfm.cms.vobject.CmsSiteData;
import com.pltfm.cms.vobject.CmsUser;
import com.pltfm.cms.vobject.CmsUserSite;
import com.pltfm.sys.model.SysRole;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;

/***
 * 站点CmsSiteServiceImpl
 */
@Component(value = "cmsSiteService")
public class CmsSiteServiceImpl implements CmsSiteService {
    @Resource(name = "cmsSiteDAO")
    private CmsSiteDAO cmsSiteDAO;
    @Resource(name = "cmsSiteDataDAO")
    private CmsSiteDataDAO cmsSiteDataDAO;
    @Resource(name = "cmsUserSiteDAO")
    private CmsUserSiteDAO cmsUserSiteDAO;

    /***
     * 多条件查询站点记录
     *
     * @param 站点记录实体
     * @return
     * @throws Exception
     *             异常
     */
    public Page searchPageByVo(Page pageParam, CmsSite vo) throws Exception {
        if (pageParam == null) {
            pageParam = new Page();
        }
        if (vo == null) {
            vo = new CmsSite();
        }
        int totalNum = cmsSiteDAO.count(vo);
        pageParam.setRecordCount(totalNum);
        vo.setStartIndex(pageParam.getStartIndex());
        vo.setEndIndex(pageParam.getStartIndex() + pageParam.getPageSize());
        pageParam.setDataList(cmsSiteDAO.select(vo));
        return pageParam;
    }


    /***
     * 多条件查询用户记录
     *
     */
    public Page searchUserByVo(Page pageParam, CmsUser vo) throws Exception {
        if (pageParam == null) {
            pageParam = new Page();
        }
        if (vo == null) {
            vo = new CmsUser();
        }
        int totalNum = cmsSiteDAO.userCount(vo);
        pageParam.setRecordCount(totalNum);
        vo.setStartIndex(pageParam.getStartIndex());
        vo.setEndIndex(pageParam.getStartIndex() + pageParam.getPageSize());
        pageParam.setDataList(cmsSiteDAO.userSelect(vo));
        return pageParam;
    }


    /***
     *
     * 删除站点
     * */
    @Transactional(rollbackFor = Exception.class)
    public Integer delete(List<Integer> siteId) throws Exception {

        Integer count = 0;
        if (ListUtils.isNotEmpty(siteId)) {
            for (Integer id : siteId) {
                count += cmsSiteDAO.delete(id);
            }
        }
        return count;
    }

    /***
     *
     * 删除站点与用户，站点与数据
     * */
    @Transactional(rollbackFor = Exception.class)
    public Integer delUserTite(String userSiteId) throws Exception {
        Integer count = 0;
        String id[] = userSiteId.split(",");
        for (int i = 0; i < id.length; i++) {
            CmsUserSite cmsUserSite = cmsUserSiteDAO.selectById(Integer
                    .valueOf(id[i]));
            CmsSiteData cdCmsSiteData = new CmsSiteData();
            cdCmsSiteData.setUserId(cmsUserSite.getUserId());
            cdCmsSiteData.setSiteId(cmsUserSite.getSiteId());
            cmsUserSiteDAO.delete(Integer.valueOf(id[i]));
            count += cmsSiteDataDAO.delUserStie(cdCmsSiteData);
        }
        return count;
    }

    /***
     *
     * 是否已存在了该站点
     * */
    public List selectIsName(CmsSite cmsSite) throws Exception {
        return cmsSiteDAO.selectIsName(cmsSite);
    }

    /***
     *
     * 添加站点记录
     * */
    public Integer insert(CmsSite cmsSite) throws Exception {
        return cmsSiteDAO.insert(cmsSite);
    }

    /***
     *
     * 修改站点记录
     * */
    public Integer update(CmsSite cmsSite) throws Exception {
        return cmsSiteDAO.update(cmsSite);
    }

    /***
     *
     * 跟据id查询站点记录
     * */
    public CmsSite selectByPrimaryKey(Integer siteId) throws Exception {
        return cmsSiteDAO.selectByPrimaryKey(siteId);
    }

    /***
     *
     * 跟据用户id查询站点记录
     * */
  /*  public CmsUser selectUser(Integer userId) throws Exception{
        return cmsSiteDAO.selectUser(userId);
    }
    /***
     * 
     * 跟据用户id角色
     * */
    public CmsUser selectUser(Integer userId) throws Exception {
        CmsUser sysuser = null;
        sysuser = cmsSiteDAO.selectUser(userId);
        // 获取roles串信息
        List rolesList = cmsSiteDAO.selectUserRolesList(userId);
        if (rolesList != null && rolesList.size() > 0) {
            String roleidStr = "";
            String roleName = "";
            for (int i = 0; i < rolesList.size(); i++) {
                SysRole role = (SysRole) rolesList.get(i);
                roleidStr += role.getRoleId().toString() + ",";
                roleName += role.getRoleName().toString() + ",";
            }
            roleidStr = roleidStr.substring(0, roleidStr.lastIndexOf(","));
            roleName = roleName.substring(0, roleName.lastIndexOf(","));
            sysuser.setRoleIdsStr(roleidStr);
            sysuser.setRoleName(roleName);
        }
        return sysuser;
    }

    /***
     *
     * 查询站点对象
     * */
    public CmsSite querySite(CmsSite site) throws Exception {
        return cmsSiteDAO.querySite(site);
    }

    /***
     *
     * 查询多站点对象
     * */
    public List querySiteLst(CmsSite site) throws Exception {
        return cmsSiteDAO.querySiteLst(site);
    }


    public CmsSiteDAO getCmsSiteDAO() {
        return cmsSiteDAO;
    }

    public void setCmsSiteDAO(CmsSiteDAO cmsSiteDAO) {
        this.cmsSiteDAO = cmsSiteDAO;
    }

    public CmsSiteDataDAO getCmsSiteDataDAO() {
        return cmsSiteDataDAO;
    }

    public void setCmsSiteDataDAO(CmsSiteDataDAO cmsSiteDataDAO) {
        this.cmsSiteDataDAO = cmsSiteDataDAO;
    }

    public CmsUserSiteDAO getCmsUserSiteDAO() {
        return cmsUserSiteDAO;
    }

    public void setCmsUserSiteDAO(CmsUserSiteDAO cmsUserSiteDAO) {
        this.cmsUserSiteDAO = cmsUserSiteDAO;
    }
}
