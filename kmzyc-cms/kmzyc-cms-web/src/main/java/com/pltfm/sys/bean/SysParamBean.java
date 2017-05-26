package com.pltfm.sys.bean;


import com.pltfm.sys.dao.SysParamDAOImpl;
import com.pltfm.sys.model.SysParamExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;
import java.util.List;


/**
 * 类 SysParamBean 系统参数类
 *
 * @author
 * @version 2.1
 * @since JDK1.5
 */
public class SysParamBean extends BaseBean {

	private static Logger logger = LoggerFactory.getLogger(SysParamBean.class);

    private static SysParamBean instance;

    public static SysParamBean instance(){
        if(instance==null)
            instance=new SysParamBean();
        return instance;
    }

    public SysParamBean() {
        super();    //加载总的sqlmap配置文件
    }


    /**
     * 根据平台编号和组名称查询该组中的所有参数 List (SysParam)
     *
     * @return List
     */
    public List getSysParamList(String sysCd, String paramGp) throws Exception {
        List paramList = null;
        //log.warn("===================Go into SysParamBean.getSysParamList(sysCd,paramGp)");
        try {
            SysParamDAOImpl dao = SysParamDAOImpl.instance(sqlMap);
            SysParamExample exam = new SysParamExample();    //组装where查询条件的对象
            exam.createCriteria().andSysCdEqualTo(sysCd).andParamGpEqualTo(paramGp);
            exam.setOrderByClause("param_value asc");
            paramList = dao.selectByExample(exam);           //获取SysParam对象集合
        } catch (SQLException e) {
        	logger.error("SysParamBean.getSysParamList异常：" + e.getMessage(), e);
            throw e;
        }
        return paramList;
    }
}
