//package com.pltfm.sys.action;
//
//import com.opensymphony.xwork2.ActionSupport;
//import com.opensymphony.xwork2.ModelDriven;
//import com.pltfm.cms.util.FileOperateUtils;
//import com.pltfm.sys.bean.SysUploadFileBean;
//import com.pltfm.sys.model.SysUploadFile;
//import com.pltfm.sys.util.DatetimeUtil;
//import com.pltfm.sys.util.FileUploadUtil;
//import com.pltfm.sys.util.PropertiesUtil;
//
//import org.apache.log4j.Logger;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 类 UploadFileAction  附件上传Action类
// *
// * @author
// * @version 2.1
// * @since JDK1.5
// */
//
//public class SysUploadFileAction extends ActionSupport implements ModelDriven {
//    Logger log = Logger.getLogger(this.getClass());
//
//    private File[] doc;
//
//    private String[] contentType;
//
//    private String[] fileName;
//
//    private String rootPath; //绝对路径参数
//
//    private String relativePath; //相对路径参数
//
//    private String specPath;//特定的路径（从前台传过来的比如按照userId或newsClass来分目录）
//
//    private String isDate = "0";   //如果为1,则在相对目录最后追加日期目录，默认为0，不用加
//
//    private String isMultiple = "0"; //是否是多张上传，默认为0,如果为1则多张
//
//    private String fileFlag;
//
//    private List dataList;
//
//    private SysUploadFile sysUploadFile = new SysUploadFile();
//
//    /**
//     * 进入上传页面
//     *
//     * @return String
//     */
//    public String gotoUpload() throws Exception {
//        try {
//            return SUCCESS;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ERROR;
//        }
//    }
//
//    /**
//     * 批量上传
//     *
//     * @return String
//     */
//    public String doUpload() throws Exception {
//        if (doc != null && doc.length != 0) { //如果文件上传不为空
//
//            String rootPathStr = ""; //根目录
//
//            String relaPathStr = ""; //相对地址
//
//            log.warn("rootPath---------------------------------" + rootPath);
//            log.warn("relativePath---------------------------------" + relativePath);
//
//            //得到根目录
//            if (rootPath != null && !rootPath.equals("")) {
//                rootPathStr = PropertiesUtil.getValue(rootPath.trim());
//            } else {
//                rootPathStr = PropertiesUtil.getValue("uploadFile_rootPath");
//            }
//
//            log.warn("rootPathStr---1------------------------------" + rootPathStr);
//
//            if (rootPathStr == null || rootPathStr.equals("")) {
//                rootPathStr = PropertiesUtil.getValue("uploadFile_rootPath");
//            }
//
//            log.warn("rootPathStr---2------------------------------" + rootPathStr);
//
//            //得到相对地址
//            if (relativePath != null && !relativePath.equals("")) {
//                relaPathStr = PropertiesUtil.getValue(relativePath.trim());
//            } else {
//                relaPathStr = PropertiesUtil.getValue("uploasFile_temp");
//            }
//
//            log.warn("relaPathStr---1------------------------------" + relaPathStr);
//
//            if (relaPathStr == null || relaPathStr.equals("")) {
//                relaPathStr = PropertiesUtil.getValue("uploasFile_temp");
//            }
//
//            log.warn("relaPathStr---2------------------------------" + relaPathStr);
//
//            if (specPath != null && !specPath.equals("")) {
//                relaPathStr = relaPathStr + "/" + specPath;
//            }
//
//            log.warn("relaPathStr---4------------------------------" + relaPathStr);
//
//            if (isDate != null && isDate.equals("1")) {
//                relaPathStr = relaPathStr + "/" + DatetimeUtil.getNow("yyyy-MM-dd");
//            }
//
//            log.warn("relaPathStr---4------------------------------" + relaPathStr);
//
//            String absolutePath = rootPathStr + "/" + relaPathStr;
//            log.warn("absolutePath========================" + absolutePath);
//            if (relativePath != null && !relativePath.equals("")) {
//                log.warn("relaPathStr========================" + relaPathStr);
//                String[] relativePathArray = relaPathStr.split("/");
//                log.warn("relativePathArray========================" + relativePathArray.length);
//
//                String tempPath = rootPathStr;
//                for (int i = 0; i < relativePathArray.length; i++) {
//                    if (relativePathArray[i] != null && !relativePathArray[i].equals("")) {
//                        tempPath = tempPath + relativePathArray[i] + "/";
//                        log.warn(i + "=tempPath========================" + tempPath);
//                        FileOperateUtils.checkAndCreateDirs(tempPath);
//
//                        String indexFileName = tempPath + "index.jsp";
//                        File indexFile = new File(indexFileName);
//                        if (!indexFile.exists()) {
//                            String indexFileContent = "<script>location.href='<%=request.getContextPath()%>/index.html';</script>";
//                            String indexFileCode = "utf-8";
//                            createFile(indexFileName, indexFileContent, indexFileCode);
//                        }
//                    }
//                }
//            }
//
//            dataList = new ArrayList();
//
//            for (int i = 0; i < doc.length; i++) {
//
//                // 将图片进行物理上传
//                SysUploadFile fm = FileUploadUtil.oneUpload(absolutePath, relaPathStr, fileName[i], contentType[i], doc[i], i);
//
//                log.warn("fm.getFileId()^^^^^^^^^^^^^^^^^^^^^^^^" + fm.getFileId());
//                log.warn("fm.getFileName()^^^^^^^^^^^^^^^^^^^^^^^^" + fm.getFileName());
//                log.warn("fm.getFileType()^^^^^^^^^^^^^^^^^^^^^^^^" + fm.getFileType());
//                log.warn("fm.getFileUrl()^^^^^^^^^^^^^^^^^^^^^^^^" + fm.getFileUrl());
//                log.warn("fm.getFileSize()^^^^^^^^^^^^^^^^^^^^^^^^" + fm.getFileSize());
//                log.warn("fm.getRemark()^^^^^^^^^^^^^^^^^^^^^^^^" + fm.getRemark());
//
//                // 上传完图片后，写表sys_upload_file
//                SysUploadFileBean bean = SysUploadFileBean.instance();
//                bean.addUploadFileInfo(fm);
//
//                dataList.add(fm);
//            }
//        }
//        return SUCCESS;
//
//    }
//
//
//    public void createFile(String fileName, String fileContent, String coding) {
//        FileOutputStream fout = null;
//        OutputStreamWriter writer = null;
//        try {
//            fout = new FileOutputStream(fileName);
//            writer = new OutputStreamWriter(fout, coding);
//            writer.write(fileContent);
//            writer.flush();
//            writer.close();
//            fout.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (null != writer) {
//                try {
//                    writer.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (null != fout) {
//                try {
//                    fout.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//
//    public File[] getDoc() {
//        return doc;
//    }
//
//    public void setDoc(File[] doc) {
//        this.doc = doc;
//    }
//
//    public String[] getDocContentType() {
//        return contentType;
//    }
//
//    public void setDocContentType(String[] contentType) {
//        this.contentType = contentType;
//    }
//
//    public String[] getDocFileName() {
//        return fileName;
//    }
//
//    public void setDocFileName(String[] fileName) {
//        this.fileName = fileName;
//    }
//
//    public String getRootPath() {
//        return rootPath;
//    }
//
//    public void setRootPath(String rootPath) {
//        this.rootPath = rootPath;
//    }
//
//    public String getRelativePath() {
//        return relativePath;
//    }
//
//    public void setRelativePath(String relativePath) {
//        this.relativePath = relativePath;
//    }
//
//    public String getIsDate() {
//        return isDate;
//    }
//
//    public void setIsDate(String isDate) {
//        this.isDate = isDate;
//    }
//
//    public String getIsMultiple() {
//        return isMultiple;
//    }
//
//    public void setIsMultiple(String isMultiple) {
//        this.isMultiple = isMultiple;
//    }
//
//    public String getFileFlag() {
//        return fileFlag;
//    }
//
//    public void setFileFlag(String fileFlag) {
//        this.fileFlag = fileFlag;
//    }
//
//    public String getSpecPath() {
//        return specPath;
//    }
//
//    public void setSpecPath(String specPath) {
//        this.specPath = specPath;
//    }
//
//    public List getDataList() {
//        return dataList;
//    }
//
//    public void setDataList(List dataList) {
//        this.dataList = dataList;
//    }
//
//
//    public String doDefault() throws Exception {
//        return INPUT;
//    }
//
//    public Object getModel() {
//        return sysUploadFile;
//    }
//
//
//}
