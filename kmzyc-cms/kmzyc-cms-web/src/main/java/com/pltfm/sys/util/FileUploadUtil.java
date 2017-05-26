//package com.pltfm.sys.util;
//
//import com.pltfm.sys.model.SysUploadFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Calendar;
//
///**
// * 用于一个文件和多个文件上传
// *
// * @author
// * @Date Aug 7, 2006 9:18:13 AM
// */
//public class FileUploadUtil {
//
//
//    /**
//     * 单个文件上传
//     */
//    public static SysUploadFile oneUpload(String parentPath, String img_path, String fileName, String contentType, File file, int i) throws IOException {
//        //System.out.println("=========== 进入FileUploadUtil.oneUpload()中！");
//        SysUploadFile fm = new SysUploadFile();
//        //String savePath = parentPath + "/" + fileName;  //服务器端存储路径
//        int subnameno = 100 + (int) (Math.random() * 900);
//        String subname = subnameno + fileName.substring(fileName.lastIndexOf("."));
//        String newFileName = Calendar.getInstance().getTime().getTime() + i + "-" + subname;
//        String savePath = parentPath + "/" + newFileName;//服务器端存储路径
//        if (file != null) {
//            InputStream in = null;
//            FileOutputStream out = null;
//            try {
//                in = new FileInputStream(file.getPath());
//
//                fm.setFileName(fileName);
//                fm.setFileUrl("/" + img_path + "/" + newFileName);
//                fm.setFileType(contentType);
//                fm.setRemark(newFileName);
//
//                // System.out.println("-------FileName=" + fm.getFileName());
//                // System.out.println("-------parentPath=" + parentPath);
//                // System.out.println("------SavePath=" + savePath);
//
//                out = new FileOutputStream(savePath);  //文件流输出到服务器端存储路径
//                byte[] buffer = new byte[1444];
//                int bytesum = 0;
//                int byteread = 0;
//                while ((byteread = in.read(buffer)) != -1) {
//                    bytesum += byteread;
//                    out.write(buffer, 0, byteread);
//                }
//
//                fm.setFileSize(Double.valueOf(bytesum + ""));
//                fm.setUploadTime(DatetimeUtil.getCalendarInstance().getTime());
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                if (null != in) {
//                    try {
//                        in.close();//关闭输入流
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (null != out) {
//                    try {
//                        out.close();//关闭输出流
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//        return fm;
//    }
//
//
//    /**
//     * 临时文件上传
//     *
//     * @param fileName
//     * @param file
//     * @param file
//     * @param i
//     * @return SysUploadFile
//     * @author hudaowan Aug 7, 2006 9:28:06 AM
//     */
//    public static SysUploadFile temporaryUpload(String parentPath, String img_path, String fileName, String contentType, File file, int i) throws IOException {
//        //System.out.println("=========== 进入FileUploadUtil.oneUpload()中！");
//        SysUploadFile fm = new SysUploadFile();
//
//        //String savePath = parentPath + "/" + fileName;  //服务器端存储路径
//        String newFileName = Calendar.getInstance().getTime().getTime() + i + "_" + fileName.substring(fileName.lastIndexOf("."));
//        String savePath = parentPath + "/" + newFileName;//服务器端存储路径
//        if (file != null) {
//            InputStream in = null;
//            FileOutputStream out = null;
//            try {
//                in = new FileInputStream(file.getPath());
//
//                fm.setFileName(fileName);
//                fm.setFileUrl("/" + img_path + "/" + newFileName);
//                fm.setFileType(contentType);
//                fm.setRemark(newFileName);
//
//                //System.out.println("-------FileName=" + fm.getFileName());
//                //System.out.println("-------parentPath=" + parentPath);
//                //System.out.println("------SavePath=" + savePath);
//
//                out = new FileOutputStream(savePath);  //文件流输出到服务器端存储路径
//                byte[] buffer = new byte[1444];
//                int bytesum = 0;
//                int byteread = 0;
//                while ((byteread = in.read(buffer)) != -1) {
//                    bytesum += byteread;
//                    out.write(buffer, 0, byteread);
//                }
//
//                fm.setFileSize(Double.valueOf(bytesum + ""));
//                fm.setUploadTime(DatetimeUtil.getCalendarInstance().getTime());
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                if (null != in) {
//                    try {
//                        in.close();//关闭输入流
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (null != out) {
//                    try {
//                        out.close();//关闭输出流
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//        return fm;
//    }
//
//}
