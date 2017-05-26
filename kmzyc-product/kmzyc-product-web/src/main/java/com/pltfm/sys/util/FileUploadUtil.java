package com.pltfm.sys.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import com.pltfm.sys.model.SysUploadFile;

import com.pltfm.sys.util.DatetimeUtil;

/**
 * 用于一个文件和多个文件上传
 *
 * @author
 * @Date Aug 7, 2006 9:18:13 AM
 */
public class FileUploadUtil {


    /**
     * 单个文件上传
     *
     * @param parentPath
     * @param fileName
     * @param contentType
     * @param file
     * @return SysUploadFile
     * @throws IOException
     * @author hudaowan Aug 7, 2006 9:28:06 AM
     */
    public static SysUploadFile oneUpload(String parentPath, String img_path, String fileName, String contentType, File file, int i) throws IOException {
        SysUploadFile fm = new SysUploadFile();
        int subNameno = 100 + (int) (Math.random() * 900);
        String subName = subNameno + fileName.substring(fileName.lastIndexOf("."));
        String newFileName = Calendar.getInstance().getTime().getTime() + i + "-" + subName;
        String savePath = parentPath + File.separator + newFileName;//服务器端存储路径
        if (file != null) {
            InputStream in = null;
            FileOutputStream out = null;
            try {
                in = new FileInputStream(file.getPath());

                fm.setFileName(fileName);
                fm.setFileUrl("/" + img_path + "/" + newFileName);
                fm.setFileType(contentType);
                fm.setRemark(newFileName);

                out = new FileOutputStream(savePath);  //文件流输出到服务器端存储路径
                byte[] buffer = new byte[1444];
                int byteSum = 0;
                int byteRead = 0;
                while ((byteRead = in.read(buffer)) != -1) {
                    byteSum += byteRead;
                    out.write(buffer, 0, byteRead);
                }

                fm.setFileSize(Double.valueOf(byteSum + ""));
                fm.setUploadTime(DatetimeUtil.getCalendarInstance().getTime());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != in) {
                    try {
                        in.close();//关闭输入流
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != out) {
                    try {
                        out.close();//关闭输出流
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return fm;
    }


    /**
     * 临时文件上传
     *
     * @param parentPath
     * @param fileName
     * @param contentType
     * @param file
     * @return SysUploadFile
     * @throws IOException
     * @author hudaowan Aug 7, 2006 9:28:06 AM
     */
    public static SysUploadFile temporaryUpload(String parentPath, String img_path, String fileName, String contentType, File file, int i) throws IOException {
        SysUploadFile fm = new SysUploadFile();

        //String savePath = parentPath + File.separator + fileName;  //服务器端存储路径
        String newFileName = Calendar.getInstance().getTime().getTime() + i + "_" + fileName.substring(fileName.lastIndexOf("."));
        String savePath = parentPath + File.separator + newFileName;//服务器端存储路径
        if (file != null) {
            InputStream in = null;
            FileOutputStream out = null;
            try {
                in = new FileInputStream(file.getPath());

                fm.setFileName(fileName);
                fm.setFileUrl("/" + img_path + "/" + newFileName);
                fm.setFileType(contentType);
                fm.setRemark(newFileName);


                out = new FileOutputStream(savePath);  //文件流输出到服务器端存储路径
                byte[] buffer = new byte[1444];
                int byteSum = 0;
                int byteRead = 0;
                while ((byteRead = in.read(buffer)) != -1) {
                    byteSum += byteRead;
                    out.write(buffer, 0, byteRead);
                }

                fm.setFileSize(Double.valueOf(byteSum + ""));
                fm.setUploadTime(DatetimeUtil.getCalendarInstance().getTime());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != in) {
                    try {
                        in.close();//关闭输入流
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != out) {
                    try {
                        out.close();//关闭输出流
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return fm;
    }

}
