package com.pltfm.app.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class UploadFileAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final static String PROMOTION_UPLOADATH="/promotion";
	// 封装上传文件域的属性
    private File image;
    // 封装上传文件类型的属性
    private String imageContentType;
    // 封装上传文件名的属性
    private String imageFileName;
    // 接受依赖注入的属性
    private String savePath;
    
    public String executeUpload() {
        FileOutputStream fos = null;
        FileInputStream fis = null;
      
        try {
            // 建立文件输出流
           // System.out.println(getSavePath());
            File outFile = new File(getSavePath());
            if(!outFile.isFile()){
            	outFile.createNewFile();
            }
            fos = new FileOutputStream(outFile);
            // 建立文件上传流
            fis = new FileInputStream(getImage());
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } catch (Exception e) {
//            System.out.println("文件上传失败");
            e.printStackTrace();
        } finally {
            close(fos, fis);
        }
        return SUCCESS;
    }

    /**
     * 返回上传文件的保存位置
     * 
     * @return
     */
    public String getSavePath() throws Exception{
        return savePath; 
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    private void close(FileOutputStream fos, FileInputStream fis) {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
//                System.out.println("FileInputStream关闭失败");
                e.printStackTrace();
            }
        }
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
//                System.out.println("FileOutputStream关闭失败");
                e.printStackTrace();
            }
        }
    }

	public void deleteOriginalImage(String url) {
		// TODO Auto-generated method stub
		File file = new File(url);
		if(file.isFile()){
			file.delete();
		}
	}
}
