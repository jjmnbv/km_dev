package com.pltfm.sys.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004 .
 * </p>
 * <p>
 * Company: Technologies.
 * </p>
 * 
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

    if (file.mkdir() == false) {
      throw new IOException("Cannot create the directory = " + dir);
    }
  }

  public static void createDirs(String dir, boolean ignoreIfExitst) throws IOException {
    File file = new File(dir);

    if (ignoreIfExitst && file.exists()) {
      return;
    }

    if (file.mkdirs() == false) {
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
    } // if
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
      } // for
    } // if
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
        double mod = Math.ceil(((double) length) / 512);
        if (mod == 0) mod = 1;
        length = ((long) mod) * 512;
        size += length;
      }
    } // if
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

    // index is the maximum value of index1 and index2
    int index = (index1 > index2) ? index1 : index2;
    if (index == -1) {
      // not found the path separator
      return fullFilePath;
    }
    String fileName = fullFilePath.substring(index + 1);
    return fileName;
  }

  /**
   * This method could be used to override the path to WEB-INF/sunline It can be set when the web
   * app is inited
   * 
   * @param path String : new path to override the default path
   */
  public static void setHomePath(String path) {
    homePath = path;
    if (homePath.endsWith(File.separator) == false) {
      homePath = homePath + File.separatorChar;
    }
  }

  /**
   * This function is used to get the classpath of a reference of one class First, this method tries
   * to get the path from system properties named "sunline.home" (can be configed in web.xml). If it
   * cannot find this parameter, then it will tries to load from the ClassLoader
   * 
   * @todo FIXME: load from ClassLoader is not correct on Resin/Linux
   * @return String
   */
  public static String getHomePath() {
    if (homePath == null) {
      String strPath = System.getProperty("sunline.home");
      if (strPath != null && (strPath.length() > 0)) {
        homePath = strPath;
      } else {
        ClassLoader classLoader = instance.getClass().getClassLoader();
        URL url = classLoader.getResource("/");
        homePath = url.getPath() + "/WEB-INF/sunline";
      }
      if (homePath.endsWith(File.separator) == false) {
        homePath = homePath + File.separatorChar;
        // log.warn("servletClassesPath does not end with /: " + servletClassesPath);
      }
    }
    homePath = homePath.replaceAll("classes/", "");
    homePath = homePath.replaceAll("WEB-INF/WEB-INF/", "WEB-INF/");
    //// System.out.println("homePath:" + homePath);

    return homePath;
  }

  /**
   * provider file path <code>d:/filepath</code> to add content <code>this is context !</code> . if
   * file not found then create new file to add.
   * 
   * @throws IOException
   * @exception if error throws exception.
   */

  public static void appendContextToFile(String path, String content) throws IOException {
    File file = new File(path);
    if (file.exists()) {
      file.createNewFile();
    }

    RandomAccessFile fis = new RandomAccessFile(file, "rw");
    FileChannel filechenel = fis.getChannel();
    filechenel.position(filechenel.size());// set file point position to prepare for add context.
    ByteBuffer bf = ByteBuffer.wrap(content.getBytes());
    filechenel.write(bf);
    filechenel.close();
    fis.close();

  }


  /**
   * 读文件
   *
   * @param File
   * @return String
   * @exception Exception
   */
  public static String gotoReadLocalFile(File file) throws Exception {
    String temp = "";
    StringBuffer sb = new StringBuffer();
    try {
      BufferedReader in = new BufferedReader(new FileReader(file));
      while ((temp = in.readLine()) != null) {
        sb.append(temp);
      }
      in.close();
    } catch (final MalformedURLException me) {
      // System.out.println("the url is error");
      throw me;
    } catch (final IOException e) {
      e.printStackTrace();
      throw e;
    }
    return sb.toString();
  }

  /**
   * 读文件（分行读取）
   *
   * @param File
   * @return String
   * @exception Exception
   */
  public static List gotoReadLocalFileByLine(File file) throws Exception {
    List resultList = new ArrayList();

    try {
      BufferedReader in = new BufferedReader(new FileReader(file));
      while (in.readLine() != null) {
        resultList.add(in.readLine());
      }
      in.close();
    } catch (final MalformedURLException me) {
      throw me;
    } catch (final IOException e) {
      e.printStackTrace();
      throw e;
    }
    return resultList;
  }

}
