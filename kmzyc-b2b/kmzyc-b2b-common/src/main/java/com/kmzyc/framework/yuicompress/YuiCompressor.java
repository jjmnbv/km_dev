package com.kmzyc.framework.yuicompress;

import java.io.*;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

public class YuiCompressor {
    public static final String charset = "UTF-8";
    public static final int lineBreakPos = -1;
    public static final boolean munge = true;
    public static final boolean verbose = false;
    public static final boolean preserveAllSemiColons = false;
    public static final boolean disableOptimizations = false;

    public static final String JSYUIFOLDER = "js";
    public static final String JS_RESOURCE = "jssource";
    public static final String CSSYUIFOLDER = "css";
    public static final String CSS_RESOURCE = "csssource";
    // private static Logger logger = Logger.getLogger(YuiCompressor.class.getName());
    private static Logger logger = LoggerFactory.getLogger(YuiCompressor.class);

    public static String WEBROOT_FOLDER = "\\src\\main\\webapp\\";

  /*
   * /** 多文件合并成一个文件
   * 
   * @param files 文件列表
   * 
   * @param targetfile 目标文件
   * 
   * public static void mergeFile(File[] files, File targetfile) { if (null == files || files.length
   * == 0) return;
   * 
   * String str ; StringBuilder allFileContext = new StringBuilder(); Reader fileRead = null;
   * BufferedReader bufferedreader = null;
   * 
   * for (File file : files) { if(!file.exists()) continue; try { fileRead = new FileReader(file);
   * try { bufferedreader = new BufferedReader(new InputStreamReader(new
   * FileInputStream(file),"UTF-8")); } catch (UnsupportedEncodingException e1) { // TODO
   * Auto-generated catch block e1.printStackTrace(); }
   * 
   * str = ""; try { str = bufferedreader.readLine(); while(null != str){
   * allFileContext.append(str+"\n"); str = bufferedreader.readLine(); } } catch (IOException e) {
   * logger.log(Level.SEVERE, "file:" + file.getName() + " can not read.", e); } } catch
   * (FileNotFoundException e) { logger.log(Level.SEVERE, "file:" + file.getName() + " not exist.",
   * e); continue; } }
   * 
   * 
   * OutputStreamWriter outputStreamWriter = null;
   * 
   * try { if(!targetfile.exists()){ targetfile.createNewFile(); } outputStreamWriter = new
   * OutputStreamWriter(new FileOutputStream( targetfile.getAbsoluteFile()), "UTF-8"); //fileWriter
   * = new FileWriter(targetfile); outputStreamWriter.write(allFileContext.toString());
   * 
   * } catch (IOException e) { e.printStackTrace(); }finally{ IOUtils.closeQuietly(fileRead);
   * IOUtils.closeQuietly(bufferedreader); IOUtils.closeQuietly(outputStreamWriter); }
   * 
   * }
   */

    /**
     * 压缩文件夹中所有的js和css文件
     *
     * @param inputfolder  （相对路径）
     * @param outputfolder （相对路径）
     */
    public static void compressFolder(String inputfolder, String outputfolder) {
        String resourceBasePath = new File("").getAbsolutePath() + WEBROOT_FOLDER;

        inputfolder = resourceBasePath + inputfolder;
        outputfolder = resourceBasePath + outputfolder;

        compressFolderWithRealPath(inputfolder, outputfolder);
    }

    /**
     * 压缩文件夹中所有的js和css文件
     *
     * @param inputfolder  （绝对路径）
     * @param outputfolder （绝对路径）
     */
    public static void compressFolderWithRealPath(String inputfolder, String outputfolder) {

        File file1 = new File(inputfolder);
        File[] fs = file1.listFiles();
        File file2 = new File(outputfolder);

        if (!file2.exists()) {
            file2.mkdirs();
        }

        for (File f : fs) {
            if (f.isFile() && (f.getName().endsWith(".css") || f.getName().endsWith(".js"))) {
                compressJavaScriptWithRealPath(f.getPath(), outputfolder + "\\" + f.getName());
                System.out.println(inputfolder + "\\" + f.getName() + " 压缩完成.");
            } else if (f.isDirectory()) {
                compressFolderWithRealPath(f.getPath(), outputfolder + "\\" + f.getName());
            }
        }
    }

    /**
     * 压缩js和CSS文件
     *
     * @param inputFilename  （相对路径）
     * @param outputFilename （相对路径）
     */
    public static void compressJavaScript(String inputFilename, String outputFilename) {
        String resourceBasePath = new File("").getAbsolutePath() + WEBROOT_FOLDER;
        inputFilename = resourceBasePath + inputFilename;

        outputFilename = resourceBasePath + outputFilename;
        compressJavaScriptWithRealPath(inputFilename, outputFilename);
    }

    /**
     * 压缩文件
     *
     * @param inputFilename  （绝对路径）
     * @param outputFilename （绝对路径）
     */
    public static void compressJavaScriptWithRealPath(String inputFilename, String outputFilename) {

        Reader in = null;
        Writer out = null;
        File inputFile = new File(inputFilename);
        if (!inputFile.exists()) {

            logger.info("file " + inputFile.getName() + "not exist.");

        }

        try {
            in = new InputStreamReader(new FileInputStream(inputFilename), charset);

            File outputFile = new File(outputFilename);
            if (!outputFile.exists()) {
                File dirfile = new File(outputFile.getParent());
                if (!dirfile.exists()) {
                    dirfile.mkdirs();
                }
            }
            out = new OutputStreamWriter(new FileOutputStream(outputFile), charset);

            if (inputFilename.endsWith(".js")) {
                JavaScriptCompressor compressor = new JavaScriptCompressor(in,
                        new YuiCompressorErrorReporter());

                in.close();
                in = null;

                compressor.compress(out, lineBreakPos, munge, verbose, preserveAllSemiColons,
                        disableOptimizations);
            } else {
                CssCompressor csscompressor = new CssCompressor(in);

                csscompressor.compress(out, lineBreakPos);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }

}
