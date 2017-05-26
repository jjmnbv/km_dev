package com.pltfm.crowdsourcing.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.crowdsourcing.common.ImgPathUtil;
import com.km.crowdsourcing.model.InstitutionApplyRecord;
import com.km.crowdsourcing.model.InstitutionImage;
import com.km.crowdsourcing.model.InstitutionInfo;
import com.km.crowdsourcing.service.InstitutionApplyRecordService;
import com.km.crowdsourcing.service.InstitutionImageService;
import com.km.crowdsourcing.service.InstitutionInfoService;
import com.kmzyc.commons.page.Page;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.action.BaseAction;

/**
 * 机构管理 2016-3-21
 * 
 * @author songmiao
 *
 */
@Controller("institutionAction")
@Scope(value = "prototype")
public class InstitutionAction extends BaseAction implements ModelDriven {
  @Resource
  private InstitutionInfoService institutionInfoService;
  @Resource
  private InstitutionImageService institutionImageService;

  @Resource
  private InstitutionApplyRecordService institutionApplyRecordService;
  private static final long serialVersionUID = 4248109542989699382L;
  private static Logger logger = LoggerFactory.getLogger(InstitutionAction.class);
  private InstitutionInfo institutionInfo; // 查询条件对象
  private List<InstitutionImage> imageList; // 图片对象list
  private String phoneNumer; // 用户注册手机号

  /******* 图片相关 ********/
  // 图片上传相对路径
  private static final String uploadUrl =
      ConfigurationUtil.getString("IMG_UPLOAD_PATH");
  // 图片展示绝对路径
  private String imgUrl = ConfigurationUtil.getString("img_path");
  private File upfile1;
  private File upfile2;
  private File upfile3;

  private Integer id1;
  private Integer id2;
  private Integer id3;

  private String image1;
  private String image2;
  private String image3;


  /**
   * 机构信息列表查询 add by songmiao 2016-3-28
   * 
   * 
   * @return
   */
  public String queryInstitutionList() {
    com.km.commons.general.model.Page<InstitutionInfo> nPage =
        new com.km.commons.general.model.Page<InstitutionInfo>();
    if (page != null) {
      page.setRecordCount(1); // 初始总条数需大于零，否则取出的pageSize会被默认赋值为 20
      nPage.setPageIndex(page.getPageNo());
      nPage.setPageSize(page.getPageSize());
    } else {
      page = new Page();
      page.setRecordCount(1); // 初始总条数需大于零，否则取出的pageSize会被默认赋值为 20
      nPage.setPageIndex(page.getPageNo());
      nPage.setPageSize(page.getPageSize());
    }
    try {
      nPage = institutionInfoService.searchPageByVo(nPage, institutionInfo);
      if (nPage != null) {
        page.setDataList(nPage.getRecordList());
        page.setRecordCount(nPage.getTotalRecords());
      }
    } catch (Exception e) {
      logger.error("", e);
    }
    return "success";
  }

  /**
   * 进入机构信息修改页面 add by songmiao 2016-3-28
   * 
   * @return
   */
  public String institutionUpdate() {
    try {
      institutionInfo =
          institutionInfoService.getInstitutionInfo(Long.valueOf(institutionInfo.getId()));
      imageList =
          institutionImageService.selectByInstitutionInfoId(Long.valueOf(institutionInfo.getId()));
      /********************** 初始化图片信息 ******************************/
      int size = imageList.size();
      if (imageList.size() < 3) {
        for (int i = 0; i < (3 - size); i++) {
          InstitutionImage im = new InstitutionImage();
          im.setId(-1);
          imageList.add(im);
        }
      }
      id1 = imageList.get(0).getId();
      id2 = imageList.get(1).getId();
      id3 = imageList.get(2).getId();
      image1 = imageList.get(0).getImageUrl();
      image2 = imageList.get(1).getImageUrl();
      image3 = imageList.get(2).getImageUrl();

    } catch (Exception e) {
      logger.error("跳转机构信息修改报错" + e);
    }
    return "success";
  }

  /**
   * 机构信息查看 add by songmiao 2016-3-28
   * 
   * @return
   */
  public String institutionDetail() {
    try {
      // 查询机构信息
      institutionInfo =
          institutionInfoService.getInstitutionInfo(Long.valueOf(institutionInfo.getId()));
      // 查询结构图片列表
      imageList =
          institutionImageService.selectByInstitutionInfoId(Long.valueOf(institutionInfo.getId()));
      if (imageList == null) {
        imageList = new ArrayList();
      }
    } catch (Exception e) {
      logger.error("跳转机构信息详情页面报错 " + e);
    }
    return "success";
  }

  /**
   * 机构信息修改 add by songmiao 2016-3-28
   * 
   * @return
   */
  public String updateInstitutionInfo() {
    try {
      institutionInfoService.updateInsInfo(institutionInfo);
      // 图片相关处理
      updateImages();
      // 去空格操作
      if (institutionInfo.getInstitutionName() != null) {
        institutionInfo.setInstitutionName(institutionInfo.getInstitutionName().trim());
      }
      if (institutionInfo.getInstitutionAddress() != null) {
        institutionInfo.setInstitutionAddress(institutionInfo.getInstitutionAddress().trim());
      }
      if (institutionInfo.getInstitutionContactor() != null) {
        institutionInfo.setInstitutionContactor(institutionInfo.getInstitutionContactor().trim());
      }
      if (institutionInfo.getInstitutionPhonenumber() != null) {
        institutionInfo
            .setInstitutionPhonenumber(institutionInfo.getInstitutionPhonenumber().trim());
      }
      Integer id = institutionInfo.getId();
      String code = institutionInfo.getInstitutionCode();
      institutionInfo = new InstitutionInfo();
      institutionInfo.setId(id);
      institutionInfo.setInstitutionCode(code);
    } catch (Exception e) {
      logger.error("跳转机构信息修改报错" + e);
    }
    return queryInstitutionList();
  }

  public void updateImages() throws Exception {
    try {
      if (upfile1 != null) {
        upImage(upfile1, id1, image1, 1);
      }
      if (upfile2 != null) {
        upImage(upfile2, id2, image2, 2);
      }
      if (upfile3 != null) {
        upImage(upfile3, id3, image3, 3);
      }
    } catch (Exception e) {
      logger.error("上传图片报错报错：" + e);
    }

  }

  private void upImage(File file, Integer id, String imageName, Integer index) throws Exception {
    try {

      InstitutionImage im = new InstitutionImage();
      InputStream stream = new FileInputStream(file);// 把文件读入
      // 生成新的图片名
      String newName = ImgPathUtil.getImgSavePath(institutionInfo.getInstitutionCode(), index,
          imageName.substring(imageName.indexOf("."), imageName.length()));
      File f = new File(uploadUrl + newName.substring(0, newName.lastIndexOf("/")));
      if (!f.exists()) {
        f.mkdirs();
      }
      OutputStream bos = new FileOutputStream(uploadUrl + newName);
      // 建立一个上传文件的输出流
      int bytesRead = 0;
      byte[] buffer = new byte[8192];
      while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
        bos.write(buffer, 0, bytesRead);// 将文件写入服务器
      }
      bos.close();
      stream.close();
      im.setImageUrl(newName);
      im.setInstitutionCode(institutionInfo.getInstitutionCode());

      if (id != null && id > 0) { // 存在id则为更新
        im.setId(id);
        institutionImageService.updateById(im);
      } else {// 不存在合法id 则为新增
        im.setCreateDate(new Date());
        institutionImageService.insert(im);
      }
    } catch (FileNotFoundException e) {
      logger.error("上传图片报错报错：" + e);
    } catch (IOException e) {
      logger.error("上传图片报错报错：" + e);
    }

  }

  public void checkInsName() throws Exception {
    HttpServletRequest request = getRequest();
    Map<String, String> remap = new HashMap<String, String>();
    InstitutionApplyRecord institutionApplyRecord = new InstitutionApplyRecord();
    String institutionName = request.getParameter("institutionName");// 机构名称
    String institutionCode = request.getParameter("institutionCode");// 机构编码

    institutionApplyRecord.setInstitutionCode(institutionCode);
    institutionApplyRecord.setInstitutionName(institutionName);
    try {
      remap = institutionApplyRecordService.checkInsName(institutionApplyRecord);
    } catch (Exception e) {
      logger.error("institutionCode:{},检查机构码或名称异常", institutionCode, e);
    }
    HttpServletResponse response = getResponse();
    PrintWriter writer = response.getWriter();
    writer.print(remap);
    writer.flush();
    writer.close();
  }

  public InstitutionInfo getInstitutionInfo() {
    return institutionInfo;
  }

  public void setInstitutionInfo(InstitutionInfo institutionInfo) {
    this.institutionInfo = institutionInfo;
  }

  public List<InstitutionImage> getImageList() {
    return imageList;
  }

  public void setImageList(List<InstitutionImage> imageList) {
    this.imageList = imageList;
  }

  @Override
  public Object getModel() {
    // TODO Auto-generated method stub
    return null;
  }


  public String getPhoneNumer() {
    return phoneNumer;
  }


  public void setPhoneNumer(String phoneNumer) {
    this.phoneNumer = phoneNumer;
  }



  public File getUpfile3() {
    return upfile3;
  }

  public void setUpfile3(File upfile3) {
    this.upfile3 = upfile3;
  }

  public File getUpfile1() {
    return upfile1;
  }

  public void setUpfile1(File upfile1) {
    this.upfile1 = upfile1;
  }

  public File getUpfile2() {
    return upfile2;
  }

  public void setUpfile2(File upfile2) {
    this.upfile2 = upfile2;
  }



  public Integer getId1() {
    return id1;
  }

  public void setId1(Integer id1) {
    this.id1 = id1;
  }

  public Integer getId2() {
    return id2;
  }

  public void setId2(Integer id2) {
    this.id2 = id2;
  }

  public Integer getId3() {
    return id3;
  }

  public void setId3(Integer id3) {
    this.id3 = id3;
  }

  public String getImage1() {
    return image1;
  }

  public void setImage1(String image1) {
    this.image1 = image1;
  }

  public String getImage2() {
    return image2;
  }

  public void setImage2(String image2) {
    this.image2 = image2;
  }

  public String getImage3() {
    return image3;
  }

  public void setImage3(String image3) {
    this.image3 = image3;
  }

  public String getImgUrl() {
    return imgUrl;
  }



}
