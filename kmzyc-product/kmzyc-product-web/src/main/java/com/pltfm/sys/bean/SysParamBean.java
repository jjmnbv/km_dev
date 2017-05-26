package com.pltfm.sys.bean;


import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.pltfm.sys.dao.SysParamDAOImpl;
import com.pltfm.sys.model.SysParamExample;


/**
 * 类 SysParamBean 系统参数类
 *
 * @author  
 * @version 2.1
 * @since   JDK1.5
 */
public class SysParamBean extends BaseBean {

	Logger log = Logger.getLogger(this.getClass());
	
	public SysParamBean() {
		super();    //加载总的sqlmap配置文件
	}


	
	
    /**
	 * 根据平台编号和组名称查询该组中的所有参数 List (SysParam)
	 * 
	 * @param    String,String
	 * @return   List
	 * @exception    Exception
	 */
	public List getSysParamList(String sysCd,String paramGp) throws Exception {
		List paramList = null;
		//log.warn("===================Go into SysParamBean.getSysParamList(sysCd,paramGp)");
		try {
			SysParamDAOImpl dao = new SysParamDAOImpl(sqlMap);
			SysParamExample exam = new SysParamExample();    //组装where查询条件的对象
			exam.createCriteria().andSysCdEqualTo(sysCd).andParamGpEqualTo(paramGp);
			exam.setOrderByClause("param_value asc");
			paramList = dao.selectByExample(exam);           //获取SysParam对象集合
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn(e.getMessage());
			throw e;
		}
		return paramList;
	}
}