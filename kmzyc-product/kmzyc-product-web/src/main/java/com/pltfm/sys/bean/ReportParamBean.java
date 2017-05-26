package com.pltfm.sys.bean;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.pltfm.sys.dao.ReportColumnSettingDAOImpl;
import com.pltfm.sys.dao.ReportParamDAOImpl;
import com.pltfm.sys.model.ReportColumnSetting;
import com.pltfm.sys.model.ReportColumnSettingExample;
import com.pltfm.sys.model.ReportParam;
import com.pltfm.sys.model.ReportParamExample;
import com.pltfm.sys.bean.BaseBean;
import com.pltfm.sys.util.DatetimeUtil;


/**
 * 类 ReportParamBean 报表参数列
 *
 * @author 
 * @version 2.1
 * @since   JDK1.5
 */
public class ReportParamBean extends BaseBean {
	
	Logger log = Logger.getLogger(this.getClass());
	
	public ReportParamBean() {
        //总的sqlmap配置文件
		super();
	}
	


	
	/**
	 * 由报表主表ID得到查询语句中的所有参数
	 * 
	 * @param    Integer
	 * @return   List
	 * @exception    Exception
	 */
	public List getParamsByReportId(Integer reportId) throws Exception{
		try {
			ReportParamDAOImpl dao = new ReportParamDAOImpl(sqlMap);
			ReportParamExample exam = new ReportParamExample();
			exam.createCriteria().andReportIdEqualTo(reportId).andIsEnableEqualTo("1");
			exam.setOrderByClause("param_order asc");
            return dao.selectByExample(exam);
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e.getMessage());
			throw e;
		}
	}
	
	


	/**
	 * 由报表主表ID得到所要显示所有参数
	 * 
	 * @param    Integer
	 * @return   List
	 * @exception    Exception
	 */
	public List getDisplayParamsByReportId(Integer reportId) throws Exception{
		try {
			ReportParamDAOImpl dao = new ReportParamDAOImpl(sqlMap);
			ReportParamExample exam = new ReportParamExample();
			exam.createCriteria().andReportIdEqualTo(reportId).andIsShowEqualTo("1").andIsEnableEqualTo("1");
			exam.setOrderByClause("param_order asc");
            return dao.selectByExample(exam);
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e.getMessage());
			throw e;
		}
	}
	
	


	/**
	 * 新增
	 * 
	 * @param    ReportParam
	 * @return   Integer
	 * @exception    Exception
	 */
    public Integer addParam(ReportParam paramVo) throws Exception{
		try {
			ReportParamDAOImpl paramDao = new ReportParamDAOImpl(sqlMap);
			paramVo.setIsEnable("1");
			paramVo.setCreateDate(DatetimeUtil.getCalendarInstance().getTime());
			paramDao.insert(paramVo);
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e.getMessage());
		}
        return paramVo.getParamId();
	}
    


    
	/**
	 * 详细信息
	 * 
	 * @param    Integer
	 * @return   ReportParam
	 * @exception    Exception
	 */
	public ReportParam getParamDetail(Integer  id) throws Exception{
		log.warn("-----------id="+id);
		ReportParam paramVo = null;
		try {
			ReportParamDAOImpl dao = new ReportParamDAOImpl(sqlMap);
			paramVo = dao.selectByPrimaryKey(id);
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e.getMessage());
			throw e;
		}
		return paramVo;
	}
	



	/**
	 * 修改
	 * 
	 * @param    ReportParam
	 * @return   ReportParam
	 * @exception    Exception
	 */
    public ReportParam updateParam(ReportParam paramVo)throws Exception{
		try {
			ReportParamDAOImpl dao = new ReportParamDAOImpl(sqlMap);
			paramVo.setUpdateDate(DatetimeUtil.getCalendarInstance().getTime());
			dao.updateByPrimaryKeySelective(paramVo);
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e.getMessage());
			throw e;
		}
        return paramVo;
	}
    
   

    
    /**
	 * 删除
	 * 
	 * @param    Integer
	 * @return   
	 * @exception    Exception
	 */
	public void deleteParam(Integer paramId) throws Exception {
		try {
			ReportParamDAOImpl dao = new ReportParamDAOImpl(sqlMap);
			ReportParam paramVo = dao.selectByPrimaryKey(paramId);
			paramVo.setIsEnable("0");
			dao.updateByPrimaryKeySelective(paramVo);
			//dao.deleteByPrimaryKey(paramId);
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e.getMessage());
			throw e;
		}
	}
}
