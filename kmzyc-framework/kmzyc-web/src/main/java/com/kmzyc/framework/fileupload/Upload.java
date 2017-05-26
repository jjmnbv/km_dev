package com.kmzyc.framework.fileupload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.struts2.ServletActionContext;

public class Upload {
    static Logger logger = LoggerFactory.getLogger(Upload.class);
    private static final int BUFFER_SIZE = 16384;

    public Upload() {
    }

    public static String uploadFile(File upFile, String upFileFileName, long fileMaximumSize, String[] allowTypes, String savePath) {
        if(upFile != null) {
            if(upFile.length() > fileMaximumSize) {
                logger.error("上传的文件太大!");
                return null;
            } else if(upFileFileName.lastIndexOf(".") < 0) {
                logger.error("上传的文件类型不允许!");
                return null;
            } else {
                String extendFile = upFileFileName.substring(upFileFileName.lastIndexOf("."));
                if(!isValid(extendFile, allowTypes)) {
                    logger.error("上传的文件类型不允许!");
                    return null;
                } else {
                    Date d = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String fileName = sdf.format(d) + extendFile;
                    String fullPath = ServletActionContext.getServletContext().getRealPath("/" + savePath) + "/" + fileName;
                    File imageFile = new File(fullPath);
                    if(!uploadFile(upFile, imageFile)) {
                        logger.error("上传失败!");
                        return null;
                    } else {
                        return fileName;
                    }
                }
            }
        } else {
            return null;
        }
    }

    private static boolean uploadFile(File src, File dst) {
        try {
            BufferedInputStream e = null;
            BufferedOutputStream out = null;

            try {
                e = new BufferedInputStream(new FileInputStream(src), 16384);
                out = new BufferedOutputStream(new FileOutputStream(dst), 16384);
                boolean data = false;

                int data1;
                while((data1 = e.read()) != -1) {
                    out.write(data1);
                }
            } finally {
                if(e != null) {
                    e.close();
                }

                if(out != null) {
                    out.close();
                }

            }

            return true;
        } catch (Exception var9) {
            var9.printStackTrace();
            return false;
        }
    }

    public static boolean isValid(String contentType, String[] allowTypes) {
        if(contentType != null && !"".equals(contentType)) {
            String[] var5 = allowTypes;
            int var4 = allowTypes.length;

            for(int var3 = 0; var3 < var4; ++var3) {
                String type = var5[var3];
                if(contentType.equals(type)) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }
}

