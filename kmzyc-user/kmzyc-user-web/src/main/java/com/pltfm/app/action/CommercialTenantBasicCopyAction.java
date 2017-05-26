package com.pltfm.app.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.dataobject.CommercialTenantBasicCopyDO;
import com.pltfm.app.service.AccountInfoService;
import com.pltfm.app.service.CommercialTenantBasicCopyService;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.AreaDict;

@Component(value = "commercialTenantBasicCopyAction")
@Scope(value = "prototype")
public class CommercialTenantBasicCopyAction extends BaseAction implements ModelDriven {
    /**
     * 
     */
    private static final long serialVersionUID = 6551972765198731407L;
    private static Logger logger = LoggerFactory.getLogger(CommercialTenantBasicCopyAction.class);
    private CommercialTenantBasicCopyDO commercialTenantBasicCopyDO;
    /**
     * 分页类
     */
    private Page page;
    private File uploadFileImage;
    private String uploadFileImageFileName;
    private String otherSidImageFileName;
    private String certificateCopyImageFileName;
    private String certificateImageFileName;
    private File otherSidImage;
    private File certificateCopyImage;
    private File certificateImage;
    private static final int BUFFER_SIZE = 16 * 1024;
    private static final String uploadImage = ConfigurationUtil.getString("uploadImage");
    private static final String showImage = ConfigurationUtil.getString("showImage");
    private String imageFileName; // 修改后的文件
    @Resource(name = "commercialTenantBasicCopyService")
    private CommercialTenantBasicCopyService commercialTenantBasicCopyService;
    @Resource(name = "accountInfoService")
    private AccountInfoService accountInfoService;

    /*
     * 删除邮件业务 @Autowired private EmailSubscriptionRemoteService emailSubscriptionRemoteService;
     */

    private AccountInfo accountInfo;
    private List<AreaDict> areaList;
    private List<AreaDict> cityList;
    private List<AreaDict> provinceList;



    public List<AreaDict> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<AreaDict> areaList) {
        this.areaList = areaList;
    }

    public List<AreaDict> getCityList() {
        return cityList;
    }

    public void setCityList(List<AreaDict> cityList) {
        this.cityList = cityList;
    }

    public List<AreaDict> getProvinceList() {
        return provinceList;
    }

    public void setProvinceList(List<AreaDict> provinceList) {
        this.provinceList = provinceList;
    }

    @Override
    public Object getModel() {
        commercialTenantBasicCopyDO = new CommercialTenantBasicCopyDO();
        return commercialTenantBasicCopyDO;
    }

    /**
     * 查询采购商基本资料审核信息
     * 
     * @return
     */
    /*
     * 删除采购商相关业务 public String VeriPageList() { try { if (page == null) { page = new Page(); }
     * Integer count = commercialTenantBasicCopyService
     * .selectListCommercialTenantBasicCopyCount(commercialTenantBasicCopyDO);
     * page.setRecordCount(count); commercialTenantBasicCopyDO.setSkip(page.getStartIndex());
     * commercialTenantBasicCopyDO.setMax(page.getStartIndex() + page.getPageSize()); List list =
     * commercialTenantBasicCopyService
     * .selectListCommercialTenantBasicCopy(commercialTenantBasicCopyDO); page.setDataList(list); }
     * catch (Exception e) { logger.error("采购商信息变更审核异常" + e.getMessage(), e); } return
     * "veriPageList"; }
     */

    /***
     * 进入采购商基本资料审核信息详情页面
     * 
     * @return
     * @throws SQLException
     */
    /*
     * 删除采购商相关业务 public String CommercialTenantBasicCopyDetail() throws SQLException {
     * commercialTenantBasicCopyDO = commercialTenantBasicCopyService
     * .queryCommercialTenantBasicCopyDO(commercialTenantBasicCopyDO.getCommercialCopyId());
     * commercialTenantBasicCopyDO.setUploadBusinessLicencePictur( showImage +
     * commercialTenantBasicCopyDO.getUploadBusinessLicencePictur()); // 设置图片路径
     * commercialTenantBasicCopyDO .setCertificatePicture(showImage +
     * commercialTenantBasicCopyDO.getCertificatePicture());// 身份证扫描件
     * commercialTenantBasicCopyDO.setTaxRegCertificateCopy( showImage +
     * commercialTenantBasicCopyDO.getTaxRegCertificateCopy());// 税务登记证(副本)
     * commercialTenantBasicCopyDO .setTaxpayerCertificate(showImage +
     * commercialTenantBasicCopyDO.getTaxpayerCertificate());// 一般纳税人证书 // 查询出所有省份 provinceList =
     * commercialTenantBasicCopyService.selectAreaByAreaId(1); if
     * (commercialTenantBasicCopyDO.getProvince() != null) { Integer provinceId; try {
     * 
     * 
     * provinceId = Integer.parseInt(commercialTenantBasicCopyDO.getProvince()); } catch (Exception
     * e) { logger.error("provinceId不能转换为数据" + e.getMessage(), e); return ERROR; } try { cityList =
     * commercialTenantBasicCopyService.selectAreaByAreaId(provinceId); } catch (Exception e) {
     * logger.error("查询城市集合出错" + e.getMessage(), e); return ERROR; }
     * 
     * } else { cityList = commercialTenantBasicCopyService.selectAreaByAreaId(2);
     * 
     * } if (commercialTenantBasicCopyDO.getCity() != null) { Integer cityId; try { cityId =
     * Integer.parseInt(commercialTenantBasicCopyDO.getCity()); } catch (Exception e) {
     * logger.error("provinceId不能转换为数据" + e.getMessage(), e); return ERROR; } try { areaList =
     * commercialTenantBasicCopyService.selectAreaByAreaId(cityId); } catch (Exception e) {
     * logger.error("查询城市集合出错" + e.getMessage(), e); return ERROR; } } else { areaList =
     * commercialTenantBasicCopyService.selectAreaByAreaId(52); } return
     * "CommercialTenantBasicCopyDetail"; }
     */

    /***
     * 进入采购商基本资料审核页面
     * 
     * @return
     * @throws SQLException
     */
    /*
     * 删除采购商相关业务 public String commercialTenantBasicCopyVerify() throws SQLException {
     * commercialTenantBasicCopyDO = commercialTenantBasicCopyService
     * .queryCommercialTenantBasicCopyDO(commercialTenantBasicCopyDO.getCommercialCopyId());
     * commercialTenantBasicCopyDO.setUploadBusinessLicencePictur( showImage +
     * commercialTenantBasicCopyDO.getUploadBusinessLicencePictur()); // 设置图片路径
     * commercialTenantBasicCopyDO .setCertificatePicture(showImage +
     * commercialTenantBasicCopyDO.getCertificatePicture());// 身份证扫描件
     * commercialTenantBasicCopyDO.setTaxRegCertificateCopy( showImage +
     * commercialTenantBasicCopyDO.getTaxRegCertificateCopy());// 税务登记证(副本)
     * commercialTenantBasicCopyDO .setTaxpayerCertificate(showImage +
     * commercialTenantBasicCopyDO.getTaxpayerCertificate());// 一般纳税人证书 // 查询出所有省份 provinceList =
     * commercialTenantBasicCopyService.selectAreaByAreaId(1); if
     * (commercialTenantBasicCopyDO.getProvince() != null) { Integer provinceId = null; try {
     * 
     * provinceId = Integer.parseInt(commercialTenantBasicCopyDO.getProvince());
     * 
     * } catch (Exception e) { logger.error("provinceId不能转换为数据" + e.getMessage(), e); return ERROR;
     * } try {
     * 
     * cityList = commercialTenantBasicCopyService.selectAreaByAreaId(provinceId); } catch
     * (Exception e) { logger.error("查询城市集合出错" + e.getMessage(), e); return ERROR; }
     * 
     * } else { cityList = commercialTenantBasicCopyService.selectAreaByAreaId(2);
     * 
     * } if (commercialTenantBasicCopyDO.getCity() != null) { Integer cityId; try { cityId =
     * Integer.parseInt(commercialTenantBasicCopyDO.getCity()); } catch (Exception e) {
     * logger.error("provinceId不能转换为数据" + e.getMessage(), e); return ERROR; } try { areaList =
     * commercialTenantBasicCopyService.selectAreaByAreaId(cityId); } catch (Exception e) {
     * logger.error("查询城市集合出错" + e.getMessage(), e); return ERROR; } } else {
     * 
     * areaList = commercialTenantBasicCopyService.selectAreaByAreaId(52); } return
     * "commercialTenantBasicCopyVerify"; }
     */

    /***
     * 
     * 修改采购商基本资料
     * 
     * @throws SQLException
     */

    /*
     * 删除采购商相关业务 public String commercialTenantBasicCopyUpdate() throws SQLException { if
     * (uploadFileImage != null) { imageFileName = new Date().getTime() +
     * getExtention(uploadFileImageFileName); File imageFile = new File(uploadImage +
     * imageFileName); copy(uploadFileImage, imageFile);
     * commercialTenantBasicCopyDO.setUploadBusinessLicencePictur(imageFileName); } if
     * (otherSidImage != null) { imageFileName = new Date().getTime() +
     * getExtention(otherSidImageFileName); File imageFile = new File(uploadImage + imageFileName);
     * copy(otherSidImage, imageFile);
     * commercialTenantBasicCopyDO.setCertificatePicture(imageFileName);// 身份证扫描件
     * 
     * } if (certificateCopyImage != null) { imageFileName = new Date().getTime() +
     * getExtention(certificateCopyImageFileName); File imageFile = new File(uploadImage +
     * imageFileName); copy(certificateCopyImage, imageFile);
     * commercialTenantBasicCopyDO.setTaxRegCertificateCopy(imageFileName); } if (certificateImage
     * != null) { imageFileName = new Date().getTime() + getExtention(certificateImageFileName);
     * File imageFile = new File(uploadImage + imageFileName); copy(certificateImage, imageFile);
     * commercialTenantBasicCopyDO.setTaxpayerCertificate(imageFileName); }
     * commercialTenantBasicCopyService.updateBasicStatus(commercialTenantBasicCopyDO);
     * commercialTenantBasicCopyDO = new CommercialTenantBasicCopyDO(); return VeriPageList(); }
     */

    /**
     * 文件上传
     * 
     * @param src
     * @param dst
     */
    private static void copy(File src, File dst) {
        try {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
                out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
                byte[] buffer = new byte[BUFFER_SIZE];
                while (in.read(buffer) > 0) {
                    out.write(buffer);
                }
            } finally {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 截取后缀名
     * 
     * @param fileName
     * @return
     */
    private static String getExtention(String fileName) {
        int pos = fileName.lastIndexOf(".");
        return fileName.substring(pos);
    }



    /****
     * 进入采购商基本资料修改页面
     * 
     * @throws SQLException
     * 
     */
    /*
     * 删除采购商相关业务 public String CommercialTenantBasicUpdate() throws SQLException {
     * commercialTenantBasicCopyDO = commercialTenantBasicCopyService
     * .queryCommercialTenantBasicCopyDO(commercialTenantBasicCopyDO.getCommercialCopyId());
     * commercialTenantBasicCopyDO.setUploadBusinessLicencePictur( showImage +
     * commercialTenantBasicCopyDO.getUploadBusinessLicencePictur()); // 设置图片路径
     * commercialTenantBasicCopyDO .setCertificatePicture(showImage +
     * commercialTenantBasicCopyDO.getCertificatePicture());// 身份证扫描件
     * commercialTenantBasicCopyDO.setTaxRegCertificateCopy( showImage +
     * commercialTenantBasicCopyDO.getTaxRegCertificateCopy());// 税务登记证(副本)
     * commercialTenantBasicCopyDO .setTaxpayerCertificate(showImage +
     * commercialTenantBasicCopyDO.getTaxpayerCertificate());// 一般纳税人证书
     * 
     * 
     * // 查询出所有省份 provinceList = commercialTenantBasicCopyService.selectAreaByAreaId(1); if
     * (commercialTenantBasicCopyDO.getProvince() != null) { Integer provinceId; try { provinceId =
     * Integer.parseInt(commercialTenantBasicCopyDO.getProvince()); } catch (Exception e) {
     * logger.error("provinceId不能转换为数据" + e.getMessage(), e); return ERROR; } try { cityList =
     * commercialTenantBasicCopyService.selectAreaByAreaId(provinceId); } catch (Exception e) {
     * logger.error("查询城市集合出错" + e.getMessage(), e); return ERROR; }
     * 
     * } else { cityList = commercialTenantBasicCopyService.selectAreaByAreaId(2);
     * 
     * } if (commercialTenantBasicCopyDO.getCity() != null) { Integer cityId; try { cityId =
     * Integer.parseInt(commercialTenantBasicCopyDO.getCity()); } catch (Exception e) {
     * logger.error("provinceId不能转换为数据" + e.getMessage(), e); return ERROR; } try { areaList =
     * commercialTenantBasicCopyService.selectAreaByAreaId(cityId); } catch (Exception e) {
     * logger.error("查询城市集合出错" + e.getMessage(), e); return ERROR; } } else { areaList =
     * commercialTenantBasicCopyService.selectAreaByAreaId(52);
     * 
     * }
     * 
     * return "CommercialTenantBasicUpdate"; }
     */


    public void getChildrenArea() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Integer areaId = null;
        try {
            areaId = Integer.parseInt(request.getParameter("areaId"));
        } catch (Exception e) {
            logger.error("areaId类型强转失败", e);
        }
        if (areaId == null) {
            logger.error("areaId为空，无法查询出子区域集合");

        }

        try {
            // 查询出子区域
            areaList = commercialTenantBasicCopyService.selectAreaByAreaId(areaId);
            super.writeJson(areaList);
        } catch (SQLException e) {
            logger.error("查询出子区域出错", e);

        }

    }

    /****
     * 
     * 审核不通过备注
     */


    /*
     * 删除采购商相关业务 public String procurementVerifyNoPassSupplier() { commercialTenantBasicCopyDO
     * .setCommercialCopyId(commercialTenantBasicCopyDO.getCommercialCopyId());
     * commercialTenantBasicCopyDO.setLoginId(commercialTenantBasicCopyDO.getLoginId()); return
     * "procurementVerifyNoPassSupplier"; }
     * 
     * 
     *//***
       * 审核不通过
       * 
       * @return
       * @throws SQLException
       *//*
         * 
         * public String CommercialTenantBasicNoPassSupplier() throws SQLException {
         * 
         * 
         * try { commercialTenantBasicCopyDO.setReviewChange(new Short((short) 2));
         * commercialTenantBasicCopyService.updateCopyStatus(commercialTenantBasicCopyDO);
         * accountInfo = accountInfoService
         * .selectByPrimaryLoginInfo(commercialTenantBasicCopyDO.getLoginId().intValue());
         * 
         * if (accountInfo.getEmail() != null) { List emailLists = new ArrayList(); List<Long>
         * uidLists = new ArrayList<Long>(); List loginNameList = new ArrayList();
         * emailLists.add(accountInfo.getEmail()); loginNameList.add(accountInfo.getLoginAccount());
         * Integer uid = commercialTenantBasicCopyDO.getLoginId().intValue(); String uids =
         * uid.toString(uid); Long uidss = Long.valueOf(uids).longValue(); uidLists.add(uidss);
         * String emailContext1 = "很遗憾,你提交的采购商信息修改已被拒绝,具体原因如下:" +
         * commercialTenantBasicCopyDO.getDescription(); String title = "采购商信息变更审核通知"; Map<String,
         * Object> sendEmailMap = emailSubscriptionRemoteService.emailBySpread(uidLists, emailLists,
         * loginNameList, title, emailContext1, 2); // 发送邮件 } List mobileList = new ArrayList();
         * List<Long> uidLists = new ArrayList<Long>(); if (accountInfo.getMobile() != null) { //
         * 发送手机短信
         * 
         * mobileList.add(accountInfo.getMobile()); Integer uid =
         * commercialTenantBasicCopyDO.getLoginId().intValue(); String uids = uid.toString(uid);
         * Long uidss = Long.valueOf(uids).longValue(); uidLists.add(uidss); String smsContext =
         * "很遗憾,你提交的采购商信息修改已被拒绝,具体原因如下：" + commercialTenantBasicCopyDO.getDescription(); // 发送短信
         * Map<String, Object> sendMap = messageRemoteService.sendMsgBySpread(uidLists, mobileList,
         * smsContext, 3, 2); }
         * 
         * 
         * } catch (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); }
         * commercialTenantBasicCopyDO = new CommercialTenantBasicCopyDO(); return VeriPageList(); }
         */

    /***
     * 审核通过
     * 
     * @return
     * @throws SQLException
     */
    /*
     * 删除采购商相关业务 public String commercialTenantBasicCopyPass() throws SQLException {
     * 
     * try { StringUtils stringUtils = new StringUtils(); String LastString = "/";
     * 
     * commercialTenantBasicCopyDO.setReviewChange(new Short((short) 1)); String
     * uploadBusinessLicencePictur = stringUtils.getLastSubString(
     * commercialTenantBasicCopyDO.getUploadBusinessLicencePictur(), LastString);
     * commercialTenantBasicCopyDO.setUploadBusinessLicencePictur(uploadBusinessLicencePictur);
     * String certificatePicture = stringUtils
     * .getLastSubString(commercialTenantBasicCopyDO.getCertificatePicture(), LastString);
     * commercialTenantBasicCopyDO.setCertificatePicture(certificatePicture);// 身份证扫描件 String
     * taxRegCertificateCopy = stringUtils
     * .getLastSubString(commercialTenantBasicCopyDO.getTaxRegCertificateCopy(), LastString);
     * commercialTenantBasicCopyDO.setTaxRegCertificateCopy(taxRegCertificateCopy);// 税务登记证(副本)
     * String taxpayerCertificate = stringUtils
     * .getLastSubString(commercialTenantBasicCopyDO.getTaxpayerCertificate(), LastString);
     * commercialTenantBasicCopyDO.setTaxpayerCertificate(taxpayerCertificate);// 一般纳税人证书
     * commercialTenantBasicCopyService.updateBasicStatus(commercialTenantBasicCopyDO);
     * 
     * if(commercialTenantBasicCopyDO.getCreditRating()>2) {
     * 
     * commercialTenantBasicCopyDO.setCreditRating(2); }
     * 
     * commercialTenantBasicCopyService.updatePass(commercialTenantBasicCopyDO);
     * 
     * accountInfo = accountInfoService
     * .selectByPrimaryLoginInfo(commercialTenantBasicCopyDO.getLoginId().intValue()); if
     * (accountInfo.getEmail() != null) { List emailLists = new ArrayList(); List<Long> uidLists =
     * new ArrayList<Long>(); List loginNameList = new ArrayList(); Integer uid =
     * commercialTenantBasicCopyDO.getLoginId().intValue(); String uids = uid.toString(uid); Long
     * uidss = Long.valueOf(uids).longValue(); uidLists.add(uidss);
     * emailLists.add(accountInfo.getEmail()); loginNameList.add(accountInfo.getLoginAccount());
     * String emailContext1 = "您提交的采购商信息修改已经审核通过,请进入我的信息查看最新的资料"; String title = "采购商信息变更审核通知"; if
     * (uidLists != null && emailLists != null && loginNameList != null) {
     * 
     * Map<String, Object> sendEmailMap = emailSubscriptionRemoteService.emailBySpread(uidLists,
     * emailLists, loginNameList, title, emailContext1, 2);
     * 
     * 
     * } // 发送邮件 } List mobileList = new ArrayList(); List<Long> uidLists = new ArrayList<Long>();
     * if (accountInfo.getMobile() != null) {
     * 
     * Integer uid = commercialTenantBasicCopyDO.getLoginId().intValue(); String uids =
     * uid.toString(uid); Long uidss = Long.valueOf(uids).longValue(); uidLists.add(uidss);
     * mobileList.add(accountInfo.getMobile()); String smsContext =
     * "您提交的采购商信息修改已经审核通过,请进入我的信息查看最新的资料"; Map<String, Object> sendMap =
     * messageRemoteService.sendMsgBySpread(uidLists, mobileList, smsContext, 3, 2); } } catch
     * (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); }
     * commercialTenantBasicCopyDO = new CommercialTenantBasicCopyDO(); return VeriPageList(); }
     */

    public CommercialTenantBasicCopyDO getCommercialTenantBasicCopyDO() {
        return commercialTenantBasicCopyDO;
    }

    public void setCommercialTenantBasicCopyDO(
            CommercialTenantBasicCopyDO commercialTenantBasicCopyDO) {
        this.commercialTenantBasicCopyDO = commercialTenantBasicCopyDO;
    }

    public CommercialTenantBasicCopyService getCommercialTenantBasicCopyService() {
        return commercialTenantBasicCopyService;
    }

    public void setCommercialTenantBasicCopyService(
            CommercialTenantBasicCopyService commercialTenantBasicCopyService) {
        this.commercialTenantBasicCopyService = commercialTenantBasicCopyService;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public File getUploadFileImage() {
        return uploadFileImage;
    }

    public void setUploadFileImage(File uploadFileImage) {
        this.uploadFileImage = uploadFileImage;
    }

    public File getOtherSidImage() {
        return otherSidImage;
    }

    public void setOtherSidImage(File otherSidImage) {
        this.otherSidImage = otherSidImage;
    }

    public File getCertificateCopyImage() {
        return certificateCopyImage;
    }

    public void setCertificateCopyImage(File certificateCopyImage) {
        this.certificateCopyImage = certificateCopyImage;
    }

    public File getCertificateImage() {
        return certificateImage;
    }

    public void setCertificateImage(File certificateImage) {
        this.certificateImage = certificateImage;
    }

    public String getUploadFileImageFileName() {
        return uploadFileImageFileName;
    }

    public void setUploadFileImageFileName(String uploadFileImageFileName) {
        this.uploadFileImageFileName = uploadFileImageFileName;
    }

    public String getOtherSidImageFileName() {
        return otherSidImageFileName;
    }

    public void setOtherSidImageFileName(String otherSidImageFileName) {
        this.otherSidImageFileName = otherSidImageFileName;
    }

    public String getCertificateCopyImageFileName() {
        return certificateCopyImageFileName;
    }

    public void setCertificateCopyImageFileName(String certificateCopyImageFileName) {
        this.certificateCopyImageFileName = certificateCopyImageFileName;
    }

    public String getCertificateImageFileName() {
        return certificateImageFileName;
    }

    public void setCertificateImageFileName(String certificateImageFileName) {
        this.certificateImageFileName = certificateImageFileName;
    }

    public AccountInfoService getAccountInfoService() {
        return accountInfoService;
    }

    public void setAccountInfoService(AccountInfoService accountInfoService) {
        this.accountInfoService = accountInfoService;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }
}
