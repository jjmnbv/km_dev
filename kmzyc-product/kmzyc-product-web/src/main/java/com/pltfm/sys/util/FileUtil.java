package com.pltfm.sys.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004 .</p>
 * <p>Company:  Technologies.</p>
 * @author 
 * @version 1.0
 */
public class FileUtil {
    private static FileUtil instance = new FileUtil();

    private static String homePath = null;

    private FileUtil() { // prevent instantiation
    }

    public static void createDir(String dir, boolean ignoreIfExitst) throws IOException {
        File file = new File(dir);

        if (ignoreIfExitst && file.exists()) {
            return;
        }

        if ( file.mkdir() == false) {
            throw new IOException("Cannot create the directory = " + dir);
        }
    }

    public static void createDirs(String dir, boolean ignoreIfExitst) throws IOException {
        File file = new File(dir);

        if (ignoreIfExitst && file.exists()) {
            return;
        }

        if ( file.mkdirs() == false) {
            throw new IOException("Cannot create directories = " + dir);
        }
    }

    public static void deleteFile(String filename) throws IOException {
        File file = new File(filename);

        if (file.isDirectory()) {
            throw new IOException("IOException -> BadInputException: not a file.");
        }
        if (file.exists() == false) {
            throw new IOException("IOException -> BadInputException: file is not exist.");
        }
        if (file.delete() == false) {
            throw new IOException("Cannot delete file. filename = " + filename);
        }
    }

    public static void deleteDir(File dir) throws IOException {
        if (dir.isFile()) throw new IOException("IOException -> BadInputException: not a directory.");
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isFile()) {
                    file.delete();
                } else {
                    deleteDir(file);
                }
            }
        }//if
        dir.delete();
    }

    public static long getDirLength(File dir) throws IOException {
        if (dir.isFile()) throw new IOException("BadInputException: not a directory.");
        long size = 0;
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                long length = 0;
                if (file.isFile()) {
                    length = file.length();
                } else {
                    length = getDirLength(file);
                }
                size += length;
            }//for
        }//if
        return size;
    }

    public static long getDirLength_onDisk(File dir) throws IOException {
        if (dir.isFile()) throw new IOException("BadInputException: not a directory.");
        long size = 0;
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                long length = 0;
                if (file.isFile()) {
                    length = file.length();
                } else {
                    length = getDirLength_onDisk(file);
                }
                double mod = Math.ceil(((double)length)/512);
                if (mod == 0) mod = 1;
                length = ((long)mod) * 512;
                size += length;
            }
        }//if
        return size;
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        byte[] block = new byte[512];
        while (true) {
            int readLength = inputStream.read(block);
            if (readLength == -1) break;// end of file
            byteArrayOutputStream.write(block, 0, readLength);
        }
        byte[] retValue = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return retValue;
    }

    public static String getFileName(String fullFilePath) {
        if (fullFilePath == null) {
            return "";
        }
        int index1 = fullFilePath.lastIndexOf('/');
        int index2 = fullFilePath.lastIndexOf('\\');

        //index is the maximum value of index1 and index2
        int index = (index1 > index2) ? index1 : index2;
        if (index == -1) {
            // not found the path separator
            return fullFilePath;
        }
        String fileName = fullFilePath.substring(index + 1);
        return fileName;
    }

    /**
     * This method could be used to override the path to WEB-INF/sunline
     * It can be set when the web app is inited
     * @param path String : new path to override the default path
     */
    public static void setHomePath(String path) {
        homePath = path;
        if (homePath.endsWith(File.separator) == false) {
            homePath = homePath + File.separatorChar;
        }
    }

    /**
     * This function is used to get the classpath of a reference of one class
     * First, this method tries to get the path from system properties
     * named "sunline.home" (can be configed in web.xml). If it cannot
     * find this parameter, then it will tries to load from the ClassLoader
     * @todo FIXME: load from ClassLoader is not correct on Resin/Linux
     * @return String
     */
    public static String getHomePath() {
        if(homePath == null) {
            String strPath = System.getProperty("sunline.home");
            if(strPath != null && (strPath.length() > 0)) {
                homePath = strPath;
            } else {
                ClassLoader classLoader = instance.getClass().getClassLoader();
                URL url = classLoader.getResource("/");
                homePath = url.getPath() + "/WEB-INF/sunline";
            }
            if(homePath.endsWith(File.separator) == false) {
                homePath = homePath + File.separatorChar;
                //log.warn("servletClassesPath does not end with /: " + servletClassesPath);
            }
        }
        homePath = homePath.replaceAll("classes/", "");
        homePath = homePath.replaceAll("WEB-INF/WEB-INF/", "WEB-INF/");
        ////System.out.println("homePath:" + homePath);

        return homePath;
    }

}
