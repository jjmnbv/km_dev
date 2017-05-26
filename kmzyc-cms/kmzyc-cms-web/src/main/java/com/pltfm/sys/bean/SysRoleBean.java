package com.pltfm.sys.bean;

import com.pltfm.sys.dao.SysRoleDAOImpl;
import com.pltfm.sys.model.SysRole;
import com.pltfm.sys.model.SysRoleExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 类 SysRoleBean 系统角色类
 *
 * @author
 * @version 2.1
 * @since JDK1.5
 */
public class SysRoleBean extends BaseBean {

	private static Logger logger = LoggerFactory.getLogger(SysRoleBean.class);

    private static SysRoleBean instance;

    public static SysRoleBean instance(){
        if(instance==null)
            instance=new SysRoleBean();
        return instance;
    }
    public SysRoleBean() {
        super();    //加载总的sqlmap配置文件
    }


    /**
     * 根据vo条件查询列表
     *
     * @return List
     */
    public List getSysRoleList(SysRole vo) throws Exception {
        try {
            SysRoleDAOImpl dao = SysRoleDAOImpl.instance(sqlMap);
            SysRoleExample exam = new SysRoleExample();    //组装where查询条件的对象
            //组合条件 like
            String roleName = vo.getRoleName();     //名称
            if (roleName != null && !roleName.equals(""))
                roleName = "%" + roleName + "%";  //like statement
            else
                roleName = "%%";

            exam.createCriteria().andRoleNameLike(roleName);
            return dao.selectByExample(exam);
        } catch (SQLException e) {
            logger.error("SysRoleBean.getSysRoleList异常：" + e.getMessage(), e);
            
            throw e;
        }
    }


    /**
     * 新增系统角色
     *
     * @return Integer
     */
    public Integer addSysRole(SysRole vo) throws Exception {
        try {
            SysRoleDAOImpl dao = SysRoleDAOImpl.instance(sqlMap);
            dao.insert(vo);
        } catch (SQLException e) {
            logger.error("SysRoleBean.addSysRole异常：" + e.getMessage(), e);
            
            throw e;
        }
        return vo.getRoleId();
    }


    /**
     * 根据roleId查询系统角色
     *
     * @return SysRole
     */
    public SysRole getSysRoleDetail(Integer id) throws Exception {
        SysRole sysrole = null;
        try {
            SysRoleDAOImpl dao = SysRoleDAOImpl.instance(sqlMap);
            sysrole = dao.selectByPrimaryKey(id);
        } catch (SQLException e) {
            logger.error("SysRoleBean.getSysRoleDetail异常：" + e.getMessage(), e);
            
            throw e;
        }
        return sysrole;
    }


    /**
     * 修改角色
     *
     * @return SysRole
     */
    public SysRole updateSysRole(SysRole sysrole) throws Exception {
        try {
            SysRoleDAOImpl dao = SysRoleDAOImpl.instance(sqlMap);
            dao.updateByPrimaryKeySelective(sysrole);
        } catch (SQLException e) {
            logger.error("SysRoleBean.updateSysRole异常：" + e.getMessage(), e);
            
            throw e;
        }
        return sysrole;
    }


    /**
     * 删除所选角色
     *
     * @return void
     */
    public void deleteSysRole(String[] SysRole) throws Exception {
        try {
            sqlMap.startTransaction();
            SysRoleDAOImpl dao = SysRoleDAOImpl.instance(sqlMap);
            // 开始循环删除
            if (SysRole != null && SysRole.length > 0) {
                for (int i = 0; i < SysRole.length; i++) {
                    dao.deleteByPrimaryKey(Integer.valueOf(SysRole[i]));
                }
            }
            sqlMap.commitTransaction();
        } catch (SQLException e) {
            logger.error("SysRoleBean.deleteSysRole异常：" + e.getMessage(), e);
            
            throw e;
        } finally {
            try {
                sqlMap.endTransaction();
            } catch (SQLException e) {
                logger.error("SysRoleBean.deleteSysRole异常：" + e.getMessage(), e);
            }
        }
    }

}
