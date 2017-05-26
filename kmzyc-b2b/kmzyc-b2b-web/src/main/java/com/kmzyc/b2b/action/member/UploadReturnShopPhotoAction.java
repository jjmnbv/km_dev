package com.kmzyc.b2b.action.member;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.action.BaseAction;
import com.kmzyc.b2b.service.member.ReturnShopService;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.entities.OrderAlterPhoto;

/**
 * Description: User: lishiming Date: 13-10-21 下午5:13 Since: 1.0
 */
@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class UploadReturnShopPhotoAction extends BaseAction {
    // static Logger logger = Logger.getLogger(UploadReturnShopPhotoAction.class);
    private static Logger logger = LoggerFactory.getLogger(UploadReturnShopPhotoAction.class);

    private final static ReentrantLock lock = new ReentrantLock();

    // private final String savePath = ConfigurationUtil.getString("RETURNSHOP_PHOTO_PATH"); //
    // 上传到服务器的路径,请先自建好

    private final String tmpPath = "/temp";// 头像上传临时目录

    private File file;

    private String fileFileName;

    private String fileContentType;

    private String path = "";

    private String errorCode = "";

    String msg = "";

    private String batchNo;

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    @Resource(name = "returnShopServiceImpl")
    private ReturnShopService returnShopService;

    /**
     * 上传图片操作
     */
    public void uploadPhoto() {
        String result = "fail";
        HttpServletResponse response = null;
        try {
            response = ServletActionContext.getResponse();

            if (null != file) {
                String targetDirectory = tmpPath + "/" + batchNo;
                String targetFileName = generateFileName(fileFileName);
                File target = new File(targetDirectory, targetFileName);

                if (!file.exists()) {
                    // 处理文件大小为0kb的情况
                    file = new File(file.getPath());
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(" ");
                    fileWriter.flush();
                    fileWriter.close();
                }

                FileUtils.copyFile(file, target);
                result = "success";
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 保存图片
     */
    public void savePhoto() {
        try {
            File srcDirectory = new File(tmpPath + "/" + batchNo);
            File targetDirectory = new File(
                    ConfigurationUtil.getString("RETURNSHOP_PHOTO_PATH") + "/" + batchNo);
            FileUtils.copyDirectory(srcDirectory, targetDirectory);
            FileUtils.deleteDirectory(srcDirectory);
            File[] fileList = srcDirectory.listFiles();
            OrderAlterPhoto photo = new OrderAlterPhoto();
            photo.setBatchNo(batchNo);
            for (File file : fileList) {
                photo.setUrl(file.getAbsolutePath());
                returnShopService.savaPhoto(photo);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 生成文件名
     */
    public static String generateFileName(String fileName) {
        lock.lock();
        try {
            DateFormat format = new SimpleDateFormat("yyMMddHHmmss");
            String formatDate = format.format(new Date());

            int random = new Random().nextInt(10000);

            int position = fileName.lastIndexOf(".");
            String extension = fileName.substring(position);

            return formatDate + random + extension;
        } finally {
            lock.unlock();
        }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 文件大小换上为bite
     */
    @SuppressWarnings("unused")
    private long getFileSizeByBite(String s) {
        long size = 0;
        size = Long.parseLong(s) * 1073741824;
        return size;
    }

    public String formetFileSize(long fileS) {// 转换文件大小
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

}
