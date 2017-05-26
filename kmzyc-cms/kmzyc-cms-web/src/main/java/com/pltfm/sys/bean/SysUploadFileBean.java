package com.pltfm.sys.bean;

import com.pltfm.sys.dao.SysUploadFileDAOImpl;
import com.pltfm.sys.model.SysUploadFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 类 SysUploadFileBean 附件类
 *
 * @author
 * @version 2.1
 * @since JDK1.5
 */


public class SysUploadFileBean extends BaseBean {

	private static Logger logger = LoggerFactory.getLogger(SysUploadFileBean.class);

    private static SysUploadFileBean instance;

    public static SysUploadFileBean instance(){
        if(instance==null)
            instance=new SysUploadFileBean();
        return instance;
    }

    public SysUploadFileBean() {
        //加载总的sqlmap配置文件
        super();
    }


    /**
     * 新增上传后的附件信息
     *
     * @return Integer
     */
    public Integer addUploadFileInfo(SysUploadFile upfile) throws Exception {
        try {
            SysUploadFileDAOImpl dao = SysUploadFileDAOImpl.instance(sqlMap);
            dao.insert(upfile);
        } catch (SQLException e) {
            logger.error("SysUploadFileBean.addUploadFileInfo异常：" + e.getMessage(), e);
            
        }
        return upfile.getFileId();
    }


    /**
     * 得到详细信息
     *
     * @return SysUploadFile
     */
    public SysUploadFile getDetail(Integer fileId) throws Exception {
        SysUploadFile sysUploadFile = new SysUploadFile();
        try {
            SysUploadFileDAOImpl dao = SysUploadFileDAOImpl.instance(sqlMap);
            sysUploadFile = dao.selectByPrimaryKey(fileId);
        } catch (SQLException e) {
            logger.error("SysUploadFileBean.getDetail异常：" + e.getMessage(), e);
            
        }
        return sysUploadFile;
    }


    /**
     * 根据Id字符串得到SysUploadFile列表
     *
     * @return List
     */
    public List getFileListByIdStr(String fileId) throws Exception {
        List dataList = new ArrayList();
        try {
            SysUploadFileDAOImpl dao = SysUploadFileDAOImpl.instance(sqlMap);
            if (fileId != null && !fileId.equals("")) {
                dataList = dao.getFileListByIdStr(fileId);
            }
        } catch (SQLException e) {
            logger.error("SysUploadFileBean.getFileListByIdStr异常：" + e.getMessage(), e);
            
        }
        return dataList;
    }


    /**
     * 根据Id字符串得到附件地址列表
     *
     * @return List
     */
    public List getFileUrlListByIdStr(String fileId) throws Exception {
        List resultList = new ArrayList();
        List dataList = this.getFileListByIdStr(fileId);
        if (dataList != null && dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                SysUploadFile suf = (SysUploadFile) dataList.get(i);
                resultList.add(suf.getFileUrl());
            }
        }
        return resultList;
    }

}
