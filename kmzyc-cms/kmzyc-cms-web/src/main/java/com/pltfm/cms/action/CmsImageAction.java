package com.pltfm.cms.action;

import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ActionContext;
import com.pltfm.app.util.Token;
import com.pltfm.cms.service.CmsImageService;
import com.pltfm.cms.vobject.UploadFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


@Component("cmsImageAction")
@Scope("prototype")
public class CmsImageAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(CmsImageAction.class);

    @Resource(name = "cmsImageService")
    private CmsImageService cmsImageService;//CmsImageService接口
    private List list;//图片名list
    private String imageName;//图片名
    private List<File> files;//上传文件
    private List<String> filesContentType;//文件类型
    private List<String> filesFileName;//文件名、
    //图片批量上传
    private File file;
    private String fileFileName;
    private String fileContentType;
    ActionContext actionContext = ActionContext.getContext();
    Map session = actionContext.getSession();

    //返回图片目录下的图片名
    public String imageList() {
        try {
            Integer siteId = (Integer) session.get("siteId");//分站点标志
            list = this.cmsImageService.imageList(siteId);
        } catch (Exception e) {
            logger.error("CmsImageAction.imageList异常：" + e.getMessage(), e);
        }
        return "imageList";
    }

    //跳至上传页
    public String toAdd() {
        return "toAdd";
    }

    //图片上传
    @Token
    public String uploadImage() {
        List uploadFileList = null;
        UploadFile uploadFile = null;
        File file = null;
        try {
            //将文件封装为UploadFile对象
            if (files != null) {
                Integer siteId = (Integer) session.get("siteId");//分站点标志
                uploadFileList = new ArrayList<UploadFile>();
                for (int i = 0; i < files.size(); i++) {
                    file = files.get(i);
                    if(file.length()>512000){//图片限制在500k大小
                        throw new Exception("图片超出500k大小,size = "+file.length());
                    }
                    
                    uploadFile = new UploadFile(file,this.filesFileName.get(i),this.filesContentType.get(i));
                    //将封装好的UploadFile对象放置uploadFileList集合
                    uploadFileList.add(uploadFile);
                }
                this.cmsImageService.uploadImage(uploadFileList, siteId);
            }
        } catch (Exception e) {
            logger.error("CmsImageAction.uploadImage异常：" + e.getMessage(), e);
        }
        return imageList();
    }

//	//图片批量上传 
//	public String uploadImageAll(){
//		try {
//			if(file!=null){
//				InputStream is = new FileInputStream(file);
//				Integer siteId =(Integer) session.get("siteId");//分站点标志
//				String path = PathConstants.imageOutPath(siteId);
//				String [] fname=this.getFileFileName().split("\\.");
//				String imagetype="."+fname[fname.length-1];
//				String imagename=this.getFileFileName().substring(0, this.getFileFileName().lastIndexOf("."))+"-"+new Date().getTime()+imagetype;
//				File deskFile = new File(path,imagename);
//				//String root = "C:/Users/zhwp/Desktop/testswfupload";
//				//File deskFile = new File(root,this.getFileFileName());
//				// 创建一个输出流  
//				OutputStream os = new FileOutputStream(deskFile);
//				//设置缓存  
//				byte [] bytefer = new byte[1024];
//				int length = 0 ; 
//				//读取myFile文件输出到toFile文件中  
//				while((length = is.read(bytefer) )>0)
//				{
//					os.write(bytefer,0,length);
//				}
//				//关闭输入流  
//				os.close();
//				//关闭输出流  
//				is.close();
//			}
//		} catch (FileNotFoundException e1) {
//			logger.error("图片批量上传 出现异常",e1);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			logger.error("读取myFile文件输出到toFile文件中 出现异常",e);
//		}
//		
//		return imageList();
//	}


    //图片批量上传
    public String uploadImageAll() {
        List uploadFileList = null;
        UploadFile uploadFile = null;

        try {
            //将文件封装为UploadFile对象
            if (file != null) {
                if(file.length()>512000){//图片限制在500k大小
                    throw new Exception("图片超出500k大小,size = "+file.length());
                }
                Integer siteId = (Integer) session.get("siteId");//分站点标志
                uploadFileList = new ArrayList<UploadFile>();
                uploadFile = new UploadFile(file,this.getFileFileName(),this.getFileContentType());
                //将封装好的UploadFile对象放置uploadFileList集合
                uploadFileList.add(uploadFile);

                this.cmsImageService.uploadImage(uploadFileList, siteId);
            }
        } catch (Exception e) {
            logger.error("CmsImageAction.uploadImageAll异常：" + e.getMessage(), e);
        }
        return imageList();
    }

    //图片删除
    public String deleteImageByName() {
        try {
            Integer siteId = (Integer) session.get("siteId");//分站点标志
            this.cmsImageService.deleteImgByName(imageName, siteId);
            this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        } catch (Exception e) {
            logger.error("CmsImageAction.deleteImageByName异常：" + e.getMessage(), e);
            this.addActionMessage(ConfigurationUtil.getString("delete.fail"));
        }
        return imageList();
    }

    //查看图片
    public String viewImage() {
        try {
            Integer siteId = (Integer) session.get("siteId");//分站点标志
            imageName = this.cmsImageService.viewImgByName(imageName, siteId);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("CmsImageAction.viewImage异常：" + e.getMessage(), e);
        }
        return "viewImage";
    }

    public CmsImageService getCmsImageService() {
        return cmsImageService;
    }

    public void setCmsImageService(CmsImageService cmsImageService) {
        this.cmsImageService = cmsImageService;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public List<String> getFilesContentType() {
        return filesContentType;
    }

    public void setFilesContentType(List<String> filesContentType) {
        this.filesContentType = filesContentType;
    }

    public List<String> getFilesFileName() {
        return filesFileName;
    }

    public void setFilesFileName(List<String> filesFileName) {
        this.filesFileName = filesFileName;
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

}
