package com.pltfm.cms.action;

import com.pltfm.cms.vobject.UploadFile;

import java.io.File;

/**
 * Created by min on 2016/10/31.
 */
public class UploadFileAction extends BaseAction {

    protected UploadFile file, file2, file3, file4, file5,file6;
    // myFile属性用来封装上传的文件
    protected File resumefile, resumefile2, resumefile3, resumefile4, resumefile5, resumefile6;

    // myFileContentType属性用来封装上传文件的类型
    protected String resumefileContentType, resumefile2ContentType, resumefile3ContentType, resumefile4ContentType, resumefile5ContentType,resumefile6ContentType;

    // myFileFileName属性用来封装上传文件的文件名
    protected String resumefileFileName, resumefile2FileName, resumefile3FileName, resumefile4FileName, resumefile5FileName,resumefile6FileName;

    /**
     * 很多地方都有构建UploadFile object,提取出来
     */
    protected void buildFile(){
        if (resumefile != null) {
            file = new UploadFile(resumefile,resumefileFileName,resumefileContentType);
        }
        if (resumefile2 != null) {
            file2 = new UploadFile(resumefile2,resumefile2FileName,resumefile2ContentType);
        }
        if (resumefile3 != null) {
            file3 =  new UploadFile(resumefile3,resumefile3FileName,resumefile3ContentType);
        }
        if (resumefile4 != null) {
            file4 =  new UploadFile(resumefile4,resumefile4FileName,resumefile4ContentType);
        }
        if (resumefile5 != null) {
            file5 = new UploadFile(resumefile5,resumefile5FileName,resumefile5ContentType);
        }
        if (resumefile6 != null) {
            file6 = new UploadFile(resumefile6,resumefile6FileName,resumefile6ContentType);
        }
    }

    public UploadFile getFile() {
        return file;
    }

    public void setFile(UploadFile file) {
        this.file = file;
    }

    public UploadFile getFile2() {
        return file2;
    }

    public void setFile2(UploadFile file2) {
        this.file2 = file2;
    }

    public UploadFile getFile3() {
        return file3;
    }

    public void setFile3(UploadFile file3) {
        this.file3 = file3;
    }

    public UploadFile getFile4() {
        return file4;
    }

    public void setFile4(UploadFile file4) {
        this.file4 = file4;
    }

    public UploadFile getFile5() {
        return file5;
    }

    public void setFile5(UploadFile file5) {
        this.file5 = file5;
    }

    public File getResumefile() {
        return resumefile;
    }

    public void setResumefile(File resumefile) {
        this.resumefile = resumefile;
    }

    public File getResumefile2() {
        return resumefile2;
    }

    public void setResumefile2(File resumefile2) {
        this.resumefile2 = resumefile2;
    }

    public File getResumefile3() {
        return resumefile3;
    }

    public void setResumefile3(File resumefile3) {
        this.resumefile3 = resumefile3;
    }

    public File getResumefile4() {
        return resumefile4;
    }

    public void setResumefile4(File resumefile4) {
        this.resumefile4 = resumefile4;
    }

    public File getResumefile5() {
        return resumefile5;
    }

    public void setResumefile5(File resumefile5) {
        this.resumefile5 = resumefile5;
    }

    public String getResumefileContentType() {
        return resumefileContentType;
    }

    public void setResumefileContentType(String resumefileContentType) {
        this.resumefileContentType = resumefileContentType;
    }

    public String getResumefile2ContentType() {
        return resumefile2ContentType;
    }

    public void setResumefile2ContentType(String resumefile2ContentType) {
        this.resumefile2ContentType = resumefile2ContentType;
    }

    public String getResumefile3ContentType() {
        return resumefile3ContentType;
    }

    public void setResumefile3ContentType(String resumefile3ContentType) {
        this.resumefile3ContentType = resumefile3ContentType;
    }

    public String getResumefile4ContentType() {
        return resumefile4ContentType;
    }

    public void setResumefile4ContentType(String resumefile4ContentType) {
        this.resumefile4ContentType = resumefile4ContentType;
    }

    public String getResumefile5ContentType() {
        return resumefile5ContentType;
    }

    public void setResumefile5ContentType(String resumefile5ContentType) {
        this.resumefile5ContentType = resumefile5ContentType;
    }

    public String getResumefileFileName() {
        return resumefileFileName;
    }

    public void setResumefileFileName(String resumefileFileName) {
        this.resumefileFileName = resumefileFileName;
    }

    public String getResumefile2FileName() {
        return resumefile2FileName;
    }

    public void setResumefile2FileName(String resumefile2FileName) {
        this.resumefile2FileName = resumefile2FileName;
    }

    public String getResumefile3FileName() {
        return resumefile3FileName;
    }

    public void setResumefile3FileName(String resumefile3FileName) {
        this.resumefile3FileName = resumefile3FileName;
    }

    public String getResumefile4FileName() {
        return resumefile4FileName;
    }

    public void setResumefile4FileName(String resumefile4FileName) {
        this.resumefile4FileName = resumefile4FileName;
    }

    public String getResumefile5FileName() {
        return resumefile5FileName;
    }

    public void setResumefile5FileName(String resumefile5FileName) {
        this.resumefile5FileName = resumefile5FileName;
    }

    public UploadFile getFile6() {
        return file6;
    }

    public void setFile6(UploadFile file6) {
        this.file6 = file6;
    }

    public File getResumefile6() {
        return resumefile6;
    }

    public void setResumefile6(File resumefile6) {
        this.resumefile6 = resumefile6;
    }

    public String getResumefile6ContentType() {
        return resumefile6ContentType;
    }

    public void setResumefile6ContentType(String resumefile6ContentType) {
        this.resumefile6ContentType = resumefile6ContentType;
    }

    public String getResumefile6FileName() {
        return resumefile6FileName;
    }

    public void setResumefile6FileName(String resumefile6FileName) {
        this.resumefile6FileName = resumefile6FileName;
    }
}
