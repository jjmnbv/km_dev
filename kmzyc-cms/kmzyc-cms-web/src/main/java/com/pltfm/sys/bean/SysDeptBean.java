package com.pltfm.sys.bean;

import com.pltfm.sys.dao.SysDeptDAOImpl;
import com.pltfm.sys.model.SysDept;
import com.pltfm.sys.model.SysDeptExample;
import com.pltfm.sys.model.SysLog;
import com.pltfm.sys.util.DatetimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 类 SysDeptBean 部门类
 *
 * @author
 * @version 2.1
 * @since JDK1.5
 */
public class SysDeptBean extends BaseBean {

	private static Logger logger = LoggerFactory.getLogger(SysDeptBean.class);

    public SysDeptBean() {
        super();    //加载总的sqlmap配置文件
    }
    private static SysDeptBean instance;

    public static SysDeptBean instance(){
        if(instance==null)
            instance=new SysDeptBean();
        return instance;
    }

    /**
     * 根据vo条件查询部门列表
     *
     * @return List
     */
    public List getSysDeptList(SysDept vo) throws Exception {
        try {
            SysDeptDAOImpl dao = SysDeptDAOImpl.instance(sqlMap);
            SysDeptExample exam = new SysDeptExample();    //组装where查询条件的对象
            //组合条件 like
            String deptName = vo.getDeptName();     //名称
            if (deptName != null && !deptName.equals(""))
                deptName = "%" + deptName + "%";  //like statement
            else
                deptName = "%%";
            exam.createCriteria().andDeptNameLike(deptName);
            exam.setOrderByClause("dept_cd asc");
            return dao.selectByExample(exam);
        } catch (SQLException e) {
            logger.error("SysDeptBean.getSysDeptList异常：" + e.getMessage(), e);
            throw e;
        }
    }


    /**
     * 获取所有部门
     *
     * @return List
     */
    public List getAllDeptList() throws Exception {
        List dataList;
        try {
            SysDeptDAOImpl dao = SysDeptDAOImpl.instance(sqlMap);
            SysDeptExample exam = new SysDeptExample();    //组装where查询条件的对象
            exam.createCriteria().andIsEnableEqualTo("1");
            exam.setOrderByClause("up_deptid asc , dept_cd asc");
            dataList = dao.selectByExample(exam);
            logger.warn("------------- deptList.size()=" + dataList.size());
        } catch (SQLException e) {
            logger.error("SysDeptBean.getAllDeptList异常：" + e.getMessage(), e);
            throw e;
        }
        return dataList;
    }


    /**
     * 获取子部门列表
     *
     * @return List
     */
    public List getSonDeptList(Integer upId) throws Exception {
        List dataList;
        try {
            SysDeptDAOImpl dao = SysDeptDAOImpl.instance(sqlMap);
            SysDeptExample exam = new SysDeptExample();    //组装where查询条件的对象
            exam.createCriteria().andIsEnableEqualTo("1").andUpDeptidEqualTo(upId);
            exam.setOrderByClause("up_deptid asc , dept_cd asc");
            dataList = dao.selectByExample(exam);
        } catch (SQLException e) {
            logger.error("SysDeptBean.getSonDeptList异常：" + e.getMessage(), e);
            throw e;
        }
        return dataList;
    }


    /**
     * 新增部门
     *
     * @return Integer
     */
    public Integer addSysDept(SysDept vo) throws Exception {
        try {
            //add
            SysDeptDAOImpl dao = SysDeptDAOImpl.instance(sqlMap);
            vo.setCreateDate(DatetimeUtil.getCalendarInstance().getTime());
            Integer newid = dao.insert(vo);
            logger.warn("----------------- new deptId=" + newid);
            //set sys log
            SysLog syslog = new SysLog();
            syslog.setSyslogTime(DatetimeUtil.getCalendarInstance().getTime());
            syslog.setSyslogUser(vo.getCreateUser());
            syslog.setTableName("sys_dept");
            syslog.setTableId(newid);
            syslog.setSyslogDiscrp("sys_dept insert");
            syslog.setSyslogType("0004");
            (new SysLogBean()).addLogInfo(syslog);
            sqlMap.commitTransaction();
        } catch (SQLException e) {
            logger.error("SysDeptBean.addSysDept异常：" + e.getMessage(), e);
            throw e;
        } finally {
            try {
                sqlMap.endTransaction();
            } catch (SQLException e) {
                logger.error("SysDeptBean.addSysDept异常：" + e.getMessage(), e);
            }
        }
        return vo.getDeptId();
    }


    /**
     * 根据deptId查询部门
     *
     * @return SysDept
     */
    public SysDept getSysDeptDetail(Integer id) throws Exception {
        SysDept sysDept = null;
        try {
            SysDeptDAOImpl dao = SysDeptDAOImpl.instance(sqlMap);
            sysDept = dao.selectByPrimaryKey(id);
        } catch (SQLException e) {
            logger.error("SysDeptBean.getSysDeptDetail异常：" + e.getMessage(), e);
            throw e;
        }
        return sysDept;
    }


    /**
     * 修改部门
     *
     * @return SysDept
     */
    public SysDept updateSysDept(SysDept sysDept) throws Exception {
        try {
            //update
            SysDeptDAOImpl dao = SysDeptDAOImpl.instance(sqlMap);
            sysDept.setUpdateDate(DatetimeUtil.getCalendarInstance().getTime());
            dao.updateByPrimaryKey(sysDept);
            //set sys log
            SysLog vo = new SysLog();
            vo.setSyslogTime(DatetimeUtil.getCalendarInstance().getTime());
            vo.setSyslogType("0005");
            vo.setSyslogUser(sysDept.getUpdateUser());
            vo.setTableName("sys_dept");
            vo.setSyslogDiscrp("update sys_dept");
            vo.setTableId(sysDept.getDeptId());
            (new SysLogBean()).addLogInfo(vo);

        } catch (SQLException e) {
            logger.error("SysDeptBean.updateSysDept异常：" + e.getMessage(), e);
            throw e;
        }
        return sysDept;
    }


    /**
     * 删除所选部门
     *
     * @return void
     */
    public void deleteSysDept(String[] sysDept) throws Exception {
        try {
            sqlMap.startTransaction();
            SysDeptDAOImpl dao = SysDeptDAOImpl.instance(sqlMap);
            SysLog vo = new SysLog();
            SysLogBean logbean = SysLogBean.instance();
            SysDept sysdept;
            // 开始循环删除
            if (sysDept != null && sysDept.length > 0) {
                for (int i = 0; i < sysDept.length; i++) {
                    //do delete
                    sysdept = dao.selectByPrimaryKey(Integer.valueOf(sysDept[i]));
                    dao.deleteByPrimaryKey(Integer.valueOf(sysDept[i]));
                    //set sys log
                    vo.setSyslogTime(DatetimeUtil.getCalendarInstance().getTime());
                    vo.setSyslogDiscrp("delete sys_dept");
                    vo.setSyslogType("0006");
                    vo.setSyslogUser(sysdept.getCreateUser());
                    ;
                    vo.setTableName("sys_dept");
                    vo.setTableId(Integer.valueOf(sysDept[i]));
                    logbean.addLogInfo(vo);
                }
            }
            sqlMap.commitTransaction();
        } catch (SQLException e) {
            logger.error("SysDeptBean.deleteSysDept异常：" + e.getMessage(), e);
            throw e;
        } finally {
            try {
                sqlMap.endTransaction();
            } catch (SQLException e) {
                logger.error("SysDeptBean.deleteSysDept异常：" + e.getMessage(), e);
            }
        }
    }


    /**
     * 根据部门id删除部门
     *
     * @return void
     */
    public void deleteSysDeptById(Integer deptId) throws Exception {
        try {
            SysDeptDAOImpl dao = SysDeptDAOImpl.instance(sqlMap);
            dao.deleteByPrimaryKey(deptId);
        } catch (SQLException e) {
            logger.error("SysDeptBean.deleteSysDeptById异常：" + e.getMessage(), e);
            throw e;
        }
    }


    /**
     * 获取除自己外的所有
     *
     * @return List
     */
    public List getOtherList(Integer deptId) throws Exception {
        List dataList;
        try {
            SysDeptDAOImpl dao = SysDeptDAOImpl.instance(sqlMap);
            SysDeptExample exam = new SysDeptExample();    //组装where查询条件的对象
            exam.createCriteria().andIsEnableEqualTo("1");
            //exam.createCriteria().andIsEnableEqualTo("1").andDeptIdNotEqualTo(deptId);
            //exam.setOrderByClause("up_deptid asc , dept_cd asc");
            dataList = dao.selectByExample(exam);
            List tempList = new ArrayList();
            SysDept thisDept = dao.selectByPrimaryKey(deptId);
            tempList.add(thisDept);
            int flage = 1;
            while (flage > 0) {
                flage = 0;
                for (int i = 0; i < tempList.size(); i++) {
                    SysDept tempDept = (SysDept) tempList.get(i);
                    for (int j = 0; j < dataList.size(); j++) {
                        SysDept sonDept = (SysDept) dataList.get(j);
                        if (sonDept.getDeptId().equals(tempDept.getDeptId()) || (sonDept.getUpDeptid() != null && sonDept.getUpDeptid().equals(tempDept.getDeptId()))) {
                            flage++;
                            dataList.remove(sonDept);
                            tempList.add(sonDept);
                        }
                    }
                }
                for (int i = 0; i < tempList.size(); i++) {
                    SysDept tempDept = (SysDept) tempList.get(i);
                }
                for (int j = 0; j < dataList.size(); j++) {
                    SysDept sonDept = (SysDept) dataList.get(j);
                }
            }
        } catch (SQLException e) {
            logger.error("SysDeptBean.getOtherList异常：" + e.getMessage(), e);
            throw e;
        }
        return dataList;
    }


}
