package com.pltfm.sys.bean;

import com.opensymphony.xwork2.ActionContext;
import com.pltfm.cms.vobject.CmsUserSite;
import com.pltfm.sys.dao.SysMenuDAOImpl;
import com.pltfm.sys.dao.SysRoleMenuDAOImpl;
import com.pltfm.sys.model.SysMenu;
import com.pltfm.sys.model.SysMenuExample;
import com.pltfm.sys.model.SysRoleMenu;
import com.pltfm.sys.model.SysRoleMenuExample;
import com.pltfm.sys.util.DatetimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 类 SysMenuBean 菜单类
 *
 * @author
 * @version 2.1
 * @since JDK1.5
 */
public class SysMenuBean extends BaseBean {

	private static Logger logger = LoggerFactory.getLogger(SysMenuBean.class);
    private static SysMenuBean instance;

    public static SysMenuBean instance(){
        if(instance==null)
            instance=new SysMenuBean();
        return instance;
    }
    public SysMenuBean() {
        super(); // 加载总的sqlmap配置文件
    }

    /**
     * 根据level查询菜单List
     *
     * @return List
     */
    public List getMenuListbyLevel(String level) throws Exception {
        List dataList;
        try {
            SysMenuDAOImpl dao = SysMenuDAOImpl.instance(sqlMap);
            SysMenuExample exam = new SysMenuExample();
            exam.createCriteria().andMenuLvEqualTo(level).andIsEnableEqualTo(
                    "1");
            exam.setOrderByClause(" sortno asc");
            dataList = dao.selectByExample(exam);
        } catch (SQLException e) {
            logger.error("SysMenuBean.getMenuListbyLevel异常：" + e.getMessage(), e);
            throw e;
        }
        return dataList;

    }

    /**
     * 得到所有菜单List
     *
     * @return List
     */
    public List getAllMenuList() throws Exception {
        List dataList;
        try {
            SysMenuDAOImpl dao = SysMenuDAOImpl.instance(sqlMap);
            SysMenuExample exam = new SysMenuExample();
            exam.createCriteria().andIsEnableEqualTo("1");
            exam.setOrderByClause(" menu_upid asc ,sortno asc");
            dataList = dao.selectByExample(exam);
        } catch (SQLException e) {
            logger.error("SysMenuBean.getAllMenuList异常：" + e.getMessage(), e);
            throw e;
        }
        return dataList;
    }

    /**
     * 获取所有的顶层菜单
     *
     * @return List
     */
    public List getAllUpMenuList() throws Exception {
        List dataList;
        try {
            SysMenuDAOImpl dao = SysMenuDAOImpl.instance(sqlMap);
            SysMenuExample exam = new SysMenuExample(); // 组装where查询条件的对象
            exam.createCriteria().andMenuLvEqualTo("1");
            exam.setOrderByClause("sortno asc");
            dataList = dao.selectByExample(exam);
        } catch (SQLException e) {
            logger.error("SysMenuBean.getAllUpMenuList异常：" + e.getMessage(), e);
            throw e;
        }
        return dataList;
    }

    /**
     * 新增菜单
     *
     * @return Integer
     */
    public Integer addSysMenu(SysMenu sysMenu) throws Exception {
        try {
            SysMenuDAOImpl dao = SysMenuDAOImpl.instance(sqlMap);
            sysMenu.setCreateDate(DatetimeUtil.getCalendarInstance().getTime());
            dao.insert(sysMenu);
        } catch (SQLException e) {
            logger.error("SysMenuBean.addSysMenu异常：" + e.getMessage(), e);
            throw e;
        }
        return sysMenu.getMenuId();
    }

    /**
     * 删除所选菜单
     *
     */
    public void deleteSysMenu(SysMenu sysMenu) throws Exception {
        try {
            SysMenuDAOImpl dao = SysMenuDAOImpl.instance(sqlMap);
            dao.deleteByPrimaryKey(sysMenu.getMenuId());
        } catch (SQLException e) {
            logger.error("SysMenuBean.deleteSysMenu异常：" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 根据上级menuId查询list
     *
     * @return List
     */
    public List searchListByUpId(String upId) throws Exception {
        List dataList;
        try {
            SysMenuDAOImpl dao = SysMenuDAOImpl.instance(sqlMap);
            SysMenuExample exam = new SysMenuExample(); // 组装where查询条件的对象
            exam.createCriteria().andMenuUpidEqualTo(Integer.valueOf(upId))
                    .andIsEnableEqualTo("1");
            exam.setOrderByClause("sortno asc");
            dataList = dao.selectByExample(exam);
        } catch (SQLException e) {
            logger.error("SysMenuBean.searchListByUpId异常：" + e.getMessage(), e);
            throw e;
        }
        return dataList;
    }

    /**
     * 根据menuId得到menu
     *
     * @return SysMenu
     */
    public SysMenu getMenuById(Integer menuId) throws Exception {
        SysMenu vo;
        try {
            SysMenuDAOImpl dao = SysMenuDAOImpl.instance(sqlMap);
            vo = dao.selectByPrimaryKey(menuId);
        } catch (SQLException e) {
            logger.error("SysMenuBean.getMenuById异常：" + e.getMessage(), e);
            throw e;
        }
        return vo;
    }

    /**
     * 修改菜单
     *
     * @return SysMenu
     */
    public SysMenu updateSysMenu(SysMenu sysMenu) throws Exception {
        try {
            SysMenuDAOImpl dao = SysMenuDAOImpl.instance(sqlMap);
            sysMenu.setUpdateDate(DatetimeUtil.getCalendarInstance().getTime());
            // dao.updateByPrimaryKeySelective(sysMenu);
            dao.updateByPrimaryKey(sysMenu);
        } catch (SQLException e) {
            logger.error("SysMenuBean.updateSysMenu异常：" + e.getMessage(), e);
            throw e;
        }
        return sysMenu;
    }

    /**
     * 根据角色id获取该角色的授权菜单列表
     *
     * @return List
     */
    public List getMenuListByRoleId(Integer roleId) throws Exception {
        List dataList;
        try {
            SysRoleMenuDAOImpl dao = SysRoleMenuDAOImpl.instance(sqlMap);
            SysRoleMenuExample exam = new SysRoleMenuExample(); // 组装where查询条件的对象
            exam.createCriteria().andRoleIdEqualTo(roleId);
            dataList = dao.selectByExample(exam);
        } catch (SQLException e) {
            logger.error("SysMenuBean.getMenuListByRoleId异常：" + e.getMessage(), e);
            throw e;
        }
        return dataList;
    }

    /**
     * 根据用户id获取该角色菜单列表
     *
     * @return List
     */
    public List<SysMenu> getMenuByUserId(Integer userId) throws Exception {
        SysMenuDAOImpl dao = SysMenuDAOImpl.instance(sqlMap);
        return dao.selectUpMenuByUser(userId);
    }

    /**
     * 根据角色id获取该角色的授权header菜单列表
     *
     * @return List
     */
    public List getUpMenuListByUserId(Integer userId) throws Exception {

        List dataList;
        List newDateList = new ArrayList();
        try {
            SysMenuDAOImpl dao = SysMenuDAOImpl.instance(sqlMap);
            dataList = dao.selectUpMenuListByUser(userId);

			/* 加 */
            Map session = ActionContext.getContext().getSession();
            List list = (List) session.get("UserSiteList");
            for (int i = 0; i < dataList.size(); i++) {

                SysMenu sys = (SysMenu) dataList.get(i);

                SysMenu sysm;
                if (null != list && list.size() != 0) {
                    if (sys.getMenuId() == 40021 || sys.getMenuId() == 21)// KMCMS库里CMS系统为21KMUSER库里CMS系统为40021
                    {
                        for (Object aList : list) {
                            sysm = (SysMenu) sys.clone();
                            CmsUserSite cms_user_site = (CmsUserSite) aList;
                            System.out.println(sysm.hashCode() + "  "
                                    + sys.hashCode());
                            sysm.setMenuId(sys.getMenuId()
                                    + cms_user_site.getSiteId());
                            sysm.setMenuName(cms_user_site.getSiteName());
                            sysm.setMenuLink(sys.getMenuLink() + "&siteId="
                                    + cms_user_site.getSiteId());

                            dataList.add(sysm);
                        }
                        // dataList.remove(sys);
                    } else {
                        newDateList.add(sys);
                    }

                }

            }

			/* 加 */
        } catch (SQLException e) {
            logger.error("SysMenuBean.getUpMenuListByUserId异常：" + e.getMessage(), e);
            throw e;
        }
        return newDateList;
    }

    /**
     * 根据角色id,upMenuId获取该角色的授权sub菜单列表
     *
     * @param userId ,Integer
     * @return List
     */
    public List getSubMenuListByUserId(Integer userId, Integer upMenuId)
            throws Exception {
        List dataList;
        try {
            SysMenuDAOImpl dao = SysMenuDAOImpl.instance(sqlMap);
            dataList = dao.selectSubMenuList(userId, upMenuId);
            for (int i = 0; i < dataList.size(); i++) {
                SysMenu sm = (SysMenu) dataList.get(i);
                if (sm.getMenuLink() != null) {
                    if (sm.getMenuLink().contains(
                            "/cms/cmsWindowDataAction_queryPageList.action")
                            || sm.getMenuLink()
                            .contains("/cms/Adv_Byid.action")) {
                        dataList.remove(sm);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("SysMenuBean.getSubMenuListByUserId异常：" + e.getMessage(), e);
            throw e;
        }
        return dataList;
    }

    /**
     * 先删除该角色的所有授权菜单，然后再添加新的授权菜单
     *
     * @param roleId ,String[]
     */
    public void deleteAndsaveRoleMenu(Integer roleId, String[] menuId)
            throws Exception {
        try {
            sqlMap.startTransaction();
            SysRoleMenuDAOImpl dao = SysRoleMenuDAOImpl.instance(sqlMap);
            // 开始删除
            SysRoleMenuExample exam = new SysRoleMenuExample(); // 组装where查询条件的对象
            exam.createCriteria().andRoleIdEqualTo(roleId);
            dao.deleteByExample(exam);
            // 开始循环添加
            if (menuId != null && menuId.length > 0) {
                for (String aMenuId : menuId) {
                    SysRoleMenu vo = new SysRoleMenu();
                    vo.setMenuId(Integer.valueOf(aMenuId));
                    vo.setRoleId(roleId);
                    dao.insert(vo);
                }
            }
            sqlMap.commitTransaction();
        } catch (SQLException e) {
            logger.error("SysMenuBean.deleteAndsaveRoleMenu异常：" + e.getMessage(), e);
            throw e;
        } finally {
            try {
                sqlMap.endTransaction();
            } catch (SQLException e) {
                logger.error("SysMenuBean.deleteAndsaveRoleMenu异常：" + e.getMessage(), e);
            }
        }
    }

}
