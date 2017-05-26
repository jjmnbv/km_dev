package com.pltfm.sys.bean;

import com.pltfm.sys.dao.SysLogDAOImpl;
import com.pltfm.sys.model.SysLog;
import com.kmzyc.commons.page.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;

/**
 * 类 SysLogBean 日志类
 *
 * @author
 * @version 2.1
 * @since JDK1.5
 */
public class SysLogBean extends BaseBean {

	private static Logger logger = LoggerFactory.getLogger(SysLogBean.class);

    public SysLogBean() {
        super();    //加载总的sqlmap配置文件
    }
    private static SysLogBean instance;

    public static SysLogBean instance(){
        if(instance==null)
            instance=new SysLogBean();
        return instance;
    }

    /**
     * 根据vo条件查询日志
     *
     * @return Page
     */
    public Page searchPageByVo(Page pageParam, SysLog vo) throws Exception {
        int pageNo = pageParam.getPageNo();//当前查询第几页
        if (pageNo == 0)
            pageNo = 1;//首次查询第一页
        int pageSize = pageParam.getPageSize();   //每页显示记录数
        int skip = (pageNo - 1) * pageSize + 1;
        int max = (pageNo - 1) * pageSize + pageSize;
        Page page = null;
        try {
            SysLogDAOImpl dao = SysLogDAOImpl.instance(sqlMap);
            vo.setSkip(skip);
            vo.setMax(max);
            page = dao.selectPageByVo(pageParam, vo);
            page.setPageNo(pageNo);
        } catch (SQLException e) {
            logger.error("SysLogBean.searchPageByVo异常：" + e.getMessage(), e);
            throw e;
        }
        return page;
    }


    /**
     * 添加日志，有问题抛出异常
     *
     * @return void
     */
    public void addLogInfo_e(SysLog vo) throws Exception {
        try {
            SysLogDAOImpl dao = SysLogDAOImpl.instance(sqlMap);
            dao.insert(vo);
        } catch (SQLException e) {
            logger.error("SysLogBean.addLogInfo_e异常：" + e.getMessage(), e);
            throw e;
        }
    }


    /**
     * 添加日志
     *
     * @return void
     */
    public void addLogInfo(SysLog vo) throws Exception {
        try {
            SysLogDAOImpl dao = SysLogDAOImpl.instance(sqlMap);
            dao.insert(vo);
        } catch (SQLException e) {
            logger.error("SysLogBean.addLogInfo异常：" + e.getMessage(), e);
            throw e;
        }
    }
}
