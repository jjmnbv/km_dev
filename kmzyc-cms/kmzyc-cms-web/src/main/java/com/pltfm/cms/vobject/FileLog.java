package com.pltfm.cms.vobject;

import java.io.File;
import java.io.Serializable;

public class FileLog implements Serializable {
    private File file;
    private String time;
    private String size;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


}
