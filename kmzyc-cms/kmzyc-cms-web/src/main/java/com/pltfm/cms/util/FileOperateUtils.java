package com.pltfm.cms.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.google.common.base.Strings;
import com.kmzyc.commons.exception.DirCreateFailException;
import com.kmzyc.commons.exception.FileUploadException;
import com.pltfm.cms.vobject.UploadFile;

/**
 * 文件读写
 *
 * @author zhl
 * @since 2013-09-13
 */
public class FileOperateUtils {
	private static Logger logger = LoggerFactory.getLogger(FileOperateUtils.class);

    /**
     * 读取文件内容
     *
     * @param filePath 文件所存放路径
     * @return valueString 文件内容
     * @throws IOException 异常信息
     */
    public static String reader(String filePath) throws IOException {
        File file = new File(filePath);
        try(FileInputStream fileInputStream=new FileInputStream(file);
            InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream, "utf-8");
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
        ) {
            StringBuilder valueString = new StringBuilder();
            String value;
            while ((value = bufferedReader.readLine()) != null) {
                valueString.append(value);
            }
            return valueString.toString();
        }
    }

    /**
     *
     * @param outPath
     * @param content
     */
    public static void writer(String outPath, String content) throws IOException {
        Assert.hasText(content,"写入文件的内容为空");
        if (!Strings.isNullOrEmpty(content)) {
            try (FileOutputStream fos = new FileOutputStream(outPath);
                 OutputStreamWriter ops = new OutputStreamWriter(fos, "utf-8");
                 BufferedWriter buf = new BufferedWriter(ops);) {
                buf.write(content);
                ops.flush();
                buf.flush();
            }
        }
    }



    /**
     * 删除文件
     *
     * @param filePathAndName String  文件路径及名称  如c:/fqf.txt
     * @return boolean
     */
    public static void delFile(String filePathAndName) {
        try {
            String filePath = filePathAndName;
            filePath = filePath.toString();
            java.io.File myDelFile = new java.io.File(filePath);
            if (myDelFile.exists()) myDelFile.delete();

        } catch (Exception e) {
            logger.error("FileOperateUtils.delFile异常：" + e.getMessage(), e);

        }

    }


    public static String upload(UploadFile file, String imagePath) throws FileUploadException {
        String filename;
        try {
            filename = extractFilename(file.getName());
            File toFile = new File(imagePath, filename);
            copyData(file.getFile(), toFile);
        } catch (Exception e) {
            throw new FileUploadException(e);
        }
        return filename;
    }

    /**
     *
     * @param name
     * @return 返回格式 2016/10/31/1918321.jpg
     */
    private static String extractFilename(String name){
        String imagetype = FilenameUtils.getExtension(name);
        return datePath()+"/"+ randomFileName()+"." + imagetype;
    }


    public static String uploadSource(UploadFile file, String imagePath) throws
            FileUploadException {
        try {
            String imagename = file.getName();
            String path = imagename;
            File toFile = new File(imagePath, imagename);
            copyData(file.getFile(), toFile);
            return path;
        } catch (Exception e) {
            throw new FileUploadException(e);
        }
    }

    /**
     * 上传（时间戳_文件名）
     */

    public static String uploadFile(UploadFile file, String imagePath) throws FileUploadException {
        try {
            String filename = System.currentTimeMillis()+ "_"  + file.getName();
            File toFile = new File(imagePath, filename);
            copyData(file.getFile(), toFile);
            return filename;

        } catch (Exception e) {
            throw new FileUploadException(e);
        }
    }

    private static void copyData(File from, File to) throws IOException {
//        InputStream is = null;
        checkAndCreateDirs(to.getParent());
        try (OutputStream os = new FileOutputStream(to)) {
            Files.copy(from.toPath(), os);
//            //设置缓存
//            byte[] buffer = new byte[1024];
//            int length = 0;
//            //读取myFile文件输出到toFile文件中
//            is = new FileInputStream(from);
//
//            while ((length = is.read(buffer)) > 0) {
//                os.write(buffer, 0, length);
//            }
        }
    }

	/*public Map<String, String> getTemplateConfig() {
        return templateConfig;
	}

	public void setTemplateConfig(Map<String, String> templateConfig) {
		this.templateConfig = templateConfig;
	}*/

    /**
     *
     * @param path
     */
    public static void checkAndCreateDirs(String path) {
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) throw new DirCreateFailException("dir create failed");
        } else {
            if (!file.isDirectory()) {
                throw new DirCreateFailException(
                        "dir create failed：file exists,but not a directory");
            }
        }
    }

    /**
     * 日期路径 即年/月/日  如2013/01/03
     *
     * @return
     */
    private static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }



    private static final String randomFileName(){
        Date now = new Date();
        return DateFormatUtils.format(now, "HHmmss")+ getCounter();
    }
    private static AtomicInteger counter=new AtomicInteger(0);
    private static int getCounter(){
        int i=counter.getAndAdd(1);
        if(i<0){
            i=0;
            counter.set(0);
        }
        return i;
    }




}
