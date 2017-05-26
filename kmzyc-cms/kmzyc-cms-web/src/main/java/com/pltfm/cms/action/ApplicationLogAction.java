package com.pltfm.cms.action;

import com.opensymphony.xwork2.Action;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.util.FileOperateUtils;
import com.pltfm.cms.vobject.FileLog;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Component(value = "applicationLogAction")
public class ApplicationLogAction extends BaseAction {

    private List<FileLog> fileList;

    private String filePath;

    private String fileName;

    public String applicationLogList() {
        File f = new File(PathConstants.applicationLogPath());
        File[] fs = f.listFiles();
        if (fileList == null) {
            fileList = new ArrayList<FileLog>();
        }
        if(fs!=null)
        for (int i = 0; i < fs.length; ++i) {
            File file = fs[i];
            System.out.println(file.length());
            if (!file.isDirectory() && file.length() > 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(file.lastModified());
                FileLog fl = new FileLog();
                fl.setTime(sdf.format(cal.getTime()));
                fl.setFile(file);
                fl.setSize(this.formetFileSize(file.length()));
                fileList.add(fl);

            }
        }
        Collections.reverse(fileList);
        return Action.SUCCESS;
    }

    public String formetFileSize(long fileS) {//转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    public void writeLog() throws IOException {
        String content = FileOperateUtils.reader(filePath);
        FileOperateUtils.writer("D:\\" + fileName, content);
        super.writeJson("success");
    }

    public List<FileLog> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileLog> fileList) {
        this.fileList = fileList;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
