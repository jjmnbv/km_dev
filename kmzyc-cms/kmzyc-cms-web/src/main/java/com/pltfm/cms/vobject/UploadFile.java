package com.pltfm.cms.vobject;

import java.io.File;
import java.io.Serializable;

public class UploadFile implements Serializable {

    public UploadFile() {
    }

    public UploadFile(File file, String name, String contentType) {
        this.file = file;
        this.name = name;
        this.contentType = contentType;
    }

    private File file;//上传的文件
    private String name;//文件名字
    private String contentType;//文件类型
    private String path;//路径
    private String rename;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRename() {
        return rename;
    }

    public void setRename(String rename) {
        this.rename = rename;
    }

}
