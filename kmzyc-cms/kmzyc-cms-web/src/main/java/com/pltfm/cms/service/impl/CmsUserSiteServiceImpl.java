package com.pltfm.cms.service.impl;

import com.pltfm.app.util.ListUtils;
import com.kmzyc.commons.page.Page;
import com.pltfm.cms.dao.CmsUserSiteDAO;
import com.pltfm.cms.service.CmsUserSiteService;
import com.pltfm.cms.util.SqlJoinUtil;
import com.pltfm.cms.vobject.CmsUserSite;
import com.pltfm.cms.vobject.CmsUserSiteShow;
import com.pltfm.sys.bean.SysUserBean;
import com.pltfm.sys.model.SysUser;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

/***
 * 站点用户记录CmsUserSiteServiceImpl
 */
@Component(value = "cmsUserSiteService")
public class CmsUserSiteServiceImpl implements CmsUserSiteService {
    /**
     * 用户与站点DAO接口
     */
    @Resource(name = "cmsUserSiteDAO")
    private CmsUserSiteDAO cmsUserSiteDAO;

    /**
     * 分页查询用户与站点信息
     *
     * @param cmsUserSite 用户与站点信息实体
     * @param page        分页实体
     * @return 返回值
     * @throws Exception 异常
     */
    @Override
    public Page queryForPage(CmsUserSite cmsUserSite, Page page)
            throws Exception {
        if (page == null) {
            page = new Page();
        }
        if (cmsUserSite == null) {
            cmsUserSite = new CmsUserSite();
        }

        // 处理通过用户名进行查询
        if (cmsUserSite != null && cmsUserSite.getSysUser() != null) {
            SysUser sysUser = cmsUserSite.getSysUser();
            System.out.println(sysUser.getUserName());
            if (sysUser.getUserName() != null
                    && !("").equals(sysUser.getUserName())) {
                SysUserBean bean = SysUserBean.instance();
                List<SysUser> list = bean.getSysUserList(sysUser);
                List ids = new ArrayList();
                for (SysUser user : list) {
                    ids.add(user.getUserId());
                }
                String userIds = SqlJoinUtil.getSqlIn(ids, 1000, "user_id");
                cmsUserSite.setUserIds(userIds);
            }
        }
        // 根据条件获取窗口数据信息总条数
        int totalNum = cmsUserSiteDAO.queryUserSiteCount(cmsUserSite);
        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            cmsUserSite.setStartIndex(page.getStartIndex());
            cmsUserSite.setEndIndex(page.getStartIndex() + page.getPageSize());
            List list = new ArrayList();
            if (ListUtils.isNotEmpty(cmsUserSiteDAO
                    .queryUserSiteList(cmsUserSite))) {
                for (CmsUserSiteShow show : cmsUserSiteDAO
                        .queryUserSiteList(cmsUserSite)) {
                    SysUserBean bean = SysUserBean.instance();
                    SysUser user = bean.getSysUserDetail(show.getUserId());
                    if (user != null && user.getUserName() != null) {
                        show.setUsername(user.getUserName());
                    }
                    list.add(show);
                }
            }
            page.setDataList(list);
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }

    /**
     * 跟据站点查询用户信息
     */
    public List<CmsUserSite> selUse(Integer siteId) throws Exception {
        return cmsUserSiteDAO.selUse(siteId);
    }

    /***
     *
     * 删除站点用户记录
     * */
    public Integer delete(List<Integer> siteId) throws Exception {
        Integer count = 0;
        if (ListUtils.isNotEmpty(siteId)) {
            for (Integer id : siteId) {
                count += cmsUserSiteDAO.delete(id);
            }
        }
        return count;
    }

    /***
     *
     * 保存用户选择站点记录
     * */
    public Integer insert(List<Integer> siteIds, int userid) throws Exception {
        // 添加站点关系
        int conu = 0;
        if (ListUtils.isNotEmpty(siteIds)) {
            for (Integer siteId : siteIds) {
                CmsUserSite cms = new CmsUserSite();
                cms.setUserId(userid);
                cms.setSiteId(siteId);
                cms.setCreated(userid);
                cms.setCreateDate(new Date());
                conu = cmsUserSiteDAO.insert(cms);
            }
        }
        return conu;
    }

    /***
     *
     * 保存站点选择用户记录 userid 用户id集合 siteId 站点id id 前当操作员id
     * */
    public Integer addUuseStie(List<Integer> userid, int siteId, int id)
            throws Exception {
        int conu = 0;
        if (ListUtils.isNotEmpty(userid)) {
            for (Integer uid : userid) {

                CmsUserSite cms = new CmsUserSite();
                cms.setUserId(uid);
                cms.setSiteId(siteId);
                cms.setCreated(id);
                cms.setCreateDate(new Date());
                conu = cmsUserSiteDAO.insert(cms);
            }

        }

        return conu;
    }

    /***
     *
     * 查询用户下站点
     * */

    public String selectStieName(Integer userid) throws Exception {
        List<CmsUserSite> list = cmsUserSiteDAO.selectStieName(userid);
        String name = "";
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                CmsUserSite cmsUserSite = list.get(i);
                name += cmsUserSite.getSiteName() + ",";
            }
            if (!name.equals("")) {
                name = name.substring(0, name.length() - 1);
            }

        }
        return name;

    }

    /***
     *
     * 添加站点用户记录
     * */
    public Integer insert(CmsUserSite cmsUserSite) throws Exception {
        return cmsUserSiteDAO.insert(cmsUserSite);
    }

    /***
     *
     * 修改站点用户记录
     * */
    public Integer update(CmsUserSite cmsUserSite) throws Exception {
        return cmsUserSiteDAO.update(cmsUserSite);
    }

    /***
     *
     * 跟据id查询站点用户记录
     * */
    public CmsUserSite selectByPrimaryKey(Integer siteId) throws Exception {
        return cmsUserSiteDAO.selectById(siteId);
    }

    /***
     *
     * 跟据站点id查询是否关系到用户站点表
     * */

    public Integer checkUserSite(Integer siteId) throws Exception {
        return cmsUserSiteDAO.checkUserSite(siteId);
    }

    /***
     * 多条件查询站点记录
     *
     * @param 站点记录实体
     * @return
     * @throws Exception
     *             异常
     */
    public Page searchPageByVo(Page pageParam, CmsUserSite vo) throws Exception {
        if (pageParam == null) {
            pageParam = new Page();
        }
        if (vo == null) {
            vo = new CmsUserSite();
        }
        // 处理通过用户名进行查询
        if (vo != null && vo.getSysUser() != null) {
            SysUser sysUser = vo.getSysUser();
            System.out.println(sysUser.getUserName());
            if (sysUser.getUserName() != null
                    && !("").equals(sysUser.getUserName())) {
                SysUserBean bean = SysUserBean.instance();
                List<SysUser> list = bean.getSysUserList(sysUser);
                List ids = new ArrayList();
                for (SysUser user : list) {
                    ids.add(user.getUserId());
                }
                String userIds = SqlJoinUtil.getSqlIn(ids, 1000, "user_id");
                vo.setUserIds(userIds);
            }
        }
        int totalNum = cmsUserSiteDAO.count(vo);
        pageParam.setRecordCount(totalNum);
        vo.setStartIndex(pageParam.getStartIndex());
        vo.setEndIndex(pageParam.getStartIndex() + pageParam.getPageSize());
        pageParam.setDataList(cmsUserSiteDAO.select(vo));
        return pageParam;
    }

    /**
     * 查询用户所拥有的站点信息
     *
     * @param userSite 用户站点对应实体
     * @return 所有用户所拥有的站点信息
     * @throws Exception 异常信息
     */
    public List<CmsUserSiteShow> queryUserSiteList(CmsUserSite userSite)
            throws Exception {
        return cmsUserSiteDAO.queryUserSiteList(userSite);
    }

    /***
     *
     * 跟据用户Id查询用户所有的站点
     * */
    public List cmsUserSite(Integer id) throws Exception {

        return cmsUserSiteDAO.cmsUserSite(id);
    }

    public CmsUserSiteDAO getCmsUserSiteDAO() {
        return cmsUserSiteDAO;
    }

    public void setCmsUserSiteDAO(CmsUserSiteDAO cmsUserSiteDAO) {
        this.cmsUserSiteDAO = cmsUserSiteDAO;
    }

}
