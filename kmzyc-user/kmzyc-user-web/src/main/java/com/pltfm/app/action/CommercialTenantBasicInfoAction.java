package com.pltfm.app.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.ModelDriven;
import com.pltfm.app.service.AccountInfoService;
import com.pltfm.app.service.CommercialTenantBasicCopyService;
import com.pltfm.app.service.CommercialTenantBasicInfoService;
import com.pltfm.app.service.QualificaitonsService;
import com.pltfm.app.util.ConfigureUtils;
import com.pltfm.app.util.Token;
import com.pltfm.app.vobject.AccountInfo;
import com.pltfm.app.vobject.AreaDict;
import com.pltfm.app.vobject.BnesCustomerTypeQuery;
import com.pltfm.app.vobject.CommercialTenantBasicInfo;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.LoginRoseRelQuery;
import com.pltfm.app.vobject.PersonalityInfo;
import com.pltfm.app.vobject.Rank;
import com.pltfm.app.vobject.UserLevel;
import com.pltfm.sys.model.SysUser;

/* 删除邮件业务 import com.kmzyc.mailmobile.service.EmailSubscriptionRemoteService; */

@Component(value = "commercialTenantBasicInfoAction")
@Scope(value = "prototype")
public class CommercialTenantBasicInfoAction extends BaseAction implements ModelDriven {
    /**
     * 商户信息实体
     */
    private CommercialTenantBasicInfo commercialTenantBasicInfo;
    private static Logger logger = LoggerFactory.getLogger(CommercialTenantBasicInfoAction.class);
    /**
     * 登录信息实体
     */
    private LoginInfo loginInfo;
    @Resource(name = "accountInfoService")
    private AccountInfoService accountInfoService;

    public AccountInfoService getAccountInfoService() {
        return accountInfoService;
    }

    public void setAccountInfoService(AccountInfoService accountInfoService) {
        this.accountInfoService = accountInfoService;
    }

    private LoginRoseRelQuery loginRoseRelQuery;

    public LoginRoseRelQuery getLoginRoseRelQuery() {
        return loginRoseRelQuery;
    }

    public void setLoginRoseRelQuery(LoginRoseRelQuery loginRoseRelQuery) {
        this.loginRoseRelQuery = loginRoseRelQuery;
    }

    /**
     * 个性信息实体
     */
    private PersonalityInfo personalityInfo;
    private AccountInfo accountInfo;
    /**
     * 商户下的子级类别集合
     */
    private List<BnesCustomerTypeQuery> customerTypeList;
    /**
     * 商户信息业务逻辑接口
     */
    @Resource(name = "commercialTenantBasicInfoService")
    private CommercialTenantBasicInfoService commercialTenantBasicInfoService;
    // 采购资格
    @Resource(name = "qualificaitonsService")
    private QualificaitonsService qualificaitonsService;

    /*
     * 删除邮件业务 @Autowired EmailSubscriptionRemoteService emailSubscriptionRemoteService;
     */

    // add luotao
    private String showImage = ConfigurationUtil.getString("showTmdImage");
    /**
     * 多条删除商户信息主键集合
     */
    private List<Integer> n_CommercialTenantIds;
    /**
     * 分页类
     */
    private Page page;
    /**
     * 商户客户头衔
     */
    private List<Rank> rankList;
    /**
     * 商户客户等级
     */
    private List<UserLevel> levelList;
    /**
     * 商户客户类别
     */
    private List<BnesCustomerTypeQuery> customerList;
    private static final int BUFFER_SIZE = 16 * 1024;
    private File myFile; // 上传文件
    private String contentType;// 上传文件类型
    private String fileName; // 上传文件名
    private String imageFileName; // 修改后的文件
    private String caption;// 文件说明，与页面属性绑定
    private static final String uploadImages = ConfigurationUtil.getString("uploadImages");
    private static final String uploadImage = ConfigurationUtil.getString("uploadImage");
    private File uploadFileImage;
    private String uploadFileImageFileName;
    private String otherSidImageFileName;
    private String certificateCopyImageFileName;
    private String certificateImageFileName;
    private File otherSidImage;
    private File certificateCopyImage;
    private File certificateImage;
    /**
     * 登录信息主键
     */
    private Integer loginIn_Id;
    /**
     * 商户类别ID
     */
    private Integer customerTypeId;
    private Integer loginId;
    private List<AreaDict> areaList;
    private List<AreaDict> cityList;
    private List<AreaDict> provinceList;
    @Resource(name = "commercialTenantBasicCopyService")
    private CommercialTenantBasicCopyService commercialTenantBasicCopyService;

    public CommercialTenantBasicCopyService getCommercialTenantBasicCopyService() {
        return commercialTenantBasicCopyService;
    }

    public void setCommercialTenantBasicCopyService(
            CommercialTenantBasicCopyService commercialTenantBasicCopyService) {
        this.commercialTenantBasicCopyService = commercialTenantBasicCopyService;
    }

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

    /**
     * 根据商户类别客户查询商户客户的头衔
     */
    public void ajaxOperateCustomerRank() {
        try {
            rankList = commercialTenantBasicInfoService.queryByCommRank(customerTypeId);
            super.writeJson(rankList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询登录账号状态
     */
    public void checkStatus() {
        try {
            loginInfo = commercialTenantBasicInfoService.selectByn_LoginId(loginIn_Id);
            super.writeJson(loginInfo.getN_Status());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ajaxOperateCustomerUserLevel() {
        try {
            levelList = commercialTenantBasicInfoService.queryByCommUserLevel(customerTypeId);
            super.writeJson(levelList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    @Override
    public Object getModel() {
        commercialTenantBasicInfo = new CommercialTenantBasicInfo();
        return commercialTenantBasicInfo;
    }

    /**
     * 查询登录账号
     */
    public void checkLoginAccount() {
        try {
            super.writeJson(commercialTenantBasicInfoService
                    .checkLoginAccount(commercialTenantBasicInfo.getLoginAccount()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加商户信息
     * 
     * @return
     */
    @Token
    public String add() {
        Integer rows = 0;
        try {
            if (fileName != null) {
                imageFileName = new Date().getTime() + getExtention(fileName);
                File imageFile = new File(uploadImages + imageFileName);
                copy(myFile, imageFile);
                commercialTenantBasicInfo.setUploadBusinessLicencePictur(imageFileName);
            }
            SysUser sysuser = (SysUser) session.get("sysUser");
            commercialTenantBasicInfo.setN_Created(sysuser.getUserId());
            loginInfo.setN_Created(sysuser.getUserId());
            personalityInfo.setN_Created(sysuser.getUserId());
            rows = commercialTenantBasicInfoService.addCommercialTenantBasicInfo(
                    commercialTenantBasicInfo, loginInfo, personalityInfo, accountInfo);
            if (rows > 0) {
                this.addActionMessage(
                        ConfigureUtils.getMessageConfig("commercialtenant.add.success"));
                commercialTenantBasicInfo = new CommercialTenantBasicInfo();
                loginInfo = new LoginInfo();
                personalityInfo = new PersonalityInfo();
                return pageList();
            }
        } catch (Exception e) {
            this.addActionMessage(ConfigureUtils.getMessageConfig("commercialtenant.add.fail"));
            e.printStackTrace();
        }

        return preAdd();
    }

    /**
     * 进入商户信息页面
     * 
     * @return
     */
    public String preAdd() {
        customerTypeList = commercialTenantBasicInfoService.queryByCommCustomer();
        return "preAddSuccess";
    }

    /**
     * 删除商户信息
     * 
     * @return
     */
    @Token
    public String delete() {
        Integer rows = 0;
        try {
            rows = commercialTenantBasicInfoService.deleteByPrimaryKey(n_CommercialTenantIds);
            if (rows > 0) {
                this.addActionMessage(
                        ConfigureUtils.getMessageConfig("commercialtenant.delete.success"));
                commercialTenantBasicInfo = new CommercialTenantBasicInfo();
                loginInfo = new LoginInfo();
                personalityInfo = new PersonalityInfo();
                return pageList();
            }
        } catch (Exception e) {
            this.addActionMessage(
                    ConfigureUtils.getMessageConfig("commercialtenant.delete.success"));
            e.printStackTrace();
        }
        return pageList();
    }

    /**
     * 修改商户信息
     * 
     * @return
     */
    public String update() {
        Integer rows = 0;
        try {
            // 获取当前登录人
            SysUser sysuser = (SysUser) session.get("sysUser");
            // if(fileName!=null){
            // imageFileName = new Date().getTime() + getExtention(fileName);
            // File imageFile = new File(uploadImages + imageFileName);
            // copy(myFile, imageFile);
            // commercialTenantBasicInfo.setUploadBusinessLicencePictur(imageFileName);
            // }
            if (uploadFileImage != null) {
                imageFileName = new Date().getTime() + getExtention(uploadFileImageFileName);
                File imageFile = new File(uploadImage + imageFileName);
                copy(uploadFileImage, imageFile);
                commercialTenantBasicInfo.setUploadBusinessLicencePictur(imageFileName);
            }
            if (otherSidImage != null) {
                imageFileName = new Date().getTime() + getExtention(otherSidImageFileName);
                File imageFile = new File(uploadImage + imageFileName);
                copy(otherSidImage, imageFile);
                commercialTenantBasicInfo.setCertificatePicture(imageFileName);// 身份证扫描件
            }
            if (certificateCopyImage != null) {
                imageFileName = new Date().getTime() + getExtention(certificateCopyImageFileName);
                File imageFile = new File(uploadImage + imageFileName);
                copy(certificateCopyImage, imageFile);
                commercialTenantBasicInfo.setTaxRegCertificateCopy(imageFileName);
            }
            if (certificateImage != null) {
                imageFileName = new Date().getTime() + getExtention(certificateImageFileName);
                File imageFile = new File(uploadImage + imageFileName);
                copy(certificateImage, imageFile);
                commercialTenantBasicInfo.setTaxpayerCertificate(imageFileName);
            }


            // personalityInfo.setN_Modified(sysuser.getUserId());
            loginInfo.setN_Modified(sysuser.getUserId());
            commercialTenantBasicInfo.setN_Modified(sysuser.getUserId());
            accountInfo.setN_Modified(sysuser.getUserId());
            rows = commercialTenantBasicInfoService.updateCommercialTenantBasicInfo(
                    commercialTenantBasicInfo, loginInfo, personalityInfo, accountInfo);
            if (rows > 0) {
                this.addActionMessage(
                        ConfigureUtils.getMessageConfig("commercialtenant.update.success"));
                commercialTenantBasicInfo = new CommercialTenantBasicInfo();
                loginInfo = new LoginInfo();
                personalityInfo = new PersonalityInfo();
                return pageList();
            }
        } catch (Exception e) {
            this.addActionMessage(ConfigureUtils.getMessageConfig("commercialtenant.update.fail"));
            e.printStackTrace();
        }
        return preUpdate();
    }

    /**
     * 进入修改商户信息页面
     * 
     * @return
     */
    public String preUpdate() {
        try {
            customerTypeList = commercialTenantBasicInfoService.queryByCommCustomer();

            commercialTenantBasicInfo = commercialTenantBasicInfoService
                    .selectByPrimaryKey(commercialTenantBasicInfo.getN_CommercialTenantId());

            loginRoseRelQuery = commercialTenantBasicInfoService
                    .selectByNcustomerId(commercialTenantBasicInfo.getN_CommercialTenantId());
            loginInfo = commercialTenantBasicInfoService
                    .selectByn_LoginId(commercialTenantBasicInfo.getN_LoginId());
            personalityInfo = commercialTenantBasicInfoService
                    .selectByPersonalityInfo(commercialTenantBasicInfo.getN_LoginId());
            accountInfo = commercialTenantBasicInfoService
                    .selectByAccountInfo(commercialTenantBasicInfo.getN_LoginId());
            /*
             * commercialTenantBasicInfo.setUploadBusinessLicencePictur(showImage +
             * commercialTenantBasicInfo.getUploadBusinessLicencePictur()); // 设置图片路径
             * commercialTenantBasicInfo.setCertificatePicture(showImage +
             * commercialTenantBasicInfo.getCertificatePicture());// 身份证扫描件
             * commercialTenantBasicInfo.setTaxRegCertificateCopy(showImage +
             * commercialTenantBasicInfo.getTaxRegCertificateCopy());// 税务登记证(副本)
             * commercialTenantBasicInfo.setTaxpayerCertificate(showImage +
             * commercialTenantBasicInfo.getTaxpayerCertificate());// 一般纳税人证书
             */
            if (loginRoseRelQuery.getnCustomerTypeId() == 4) {
                commercialTenantBasicInfo
                        .setN_CustomerTypeId(loginRoseRelQuery.getnCustomerTypeId().intValue());
                commercialTenantBasicInfo.setCustomerName("采购商");

            } else {

                commercialTenantBasicInfo
                        .setN_CustomerTypeId(loginRoseRelQuery.getnCustomerTypeId().intValue());
                commercialTenantBasicInfo.setCustomerName("供应商");
            }


            // 查询出所有省份
            provinceList = commercialTenantBasicCopyService.selectAreaByAreaId(1);
            if (commercialTenantBasicInfo.getProvince() != null) {
                Integer provinceId;
                try {


                    provinceId = Integer.parseInt(commercialTenantBasicInfo.getProvince());
                } catch (Exception e) {
                    logger.error("provinceId不能转换为数据" + e.getMessage(), e);
                    return ERROR;
                }
                try {
                    cityList = commercialTenantBasicCopyService.selectAreaByAreaId(provinceId);
                } catch (Exception e) {
                    logger.error("查询城市集合出错" + e.getMessage(), e);
                    return ERROR;
                }

            } else {
                cityList = commercialTenantBasicCopyService.selectAreaByAreaId(2);

            }
            if (commercialTenantBasicInfo.getCity() != null) {
                Integer cityId;
                try {
                    cityId = Integer.parseInt(commercialTenantBasicInfo.getCity());
                } catch (Exception e) {
                    logger.error("provinceId不能转换为数据" + e.getMessage(), e);
                    return ERROR;
                }
                try {
                    areaList = commercialTenantBasicCopyService.selectAreaByAreaId(cityId);
                } catch (Exception e) {
                    logger.error("查询城市集合出错" + e.getMessage(), e);
                    return ERROR;
                }
            } else {
                areaList = commercialTenantBasicCopyService.selectAreaByAreaId(52);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "preUpdateSuccess";
    }



    /**
     * 进入详情商户信息页面
     * 
     * @return
     */
    public String preDetail() {
        try {
            customerTypeList = commercialTenantBasicInfoService.queryByCommCustomer();
            commercialTenantBasicInfo = commercialTenantBasicInfoService
                    .selectByPrimaryKey(commercialTenantBasicInfo.getN_CommercialTenantId());
            loginRoseRelQuery = commercialTenantBasicInfoService
                    .selectByNcustomerId(commercialTenantBasicInfo.getN_CommercialTenantId());
            loginInfo = commercialTenantBasicInfoService
                    .selectByn_LoginId(commercialTenantBasicInfo.getN_LoginId());
            personalityInfo = commercialTenantBasicInfoService
                    .selectByPersonalityInfo(commercialTenantBasicInfo.getN_LoginId());
            /*
             * commercialTenantBasicInfo.setUploadBusinessLicencePictur(showImage +
             * commercialTenantBasicInfo.getUploadBusinessLicencePictur()); // 设置图片路径
             * commercialTenantBasicInfo.setCertificatePicture(showImage +
             * commercialTenantBasicInfo.getCertificatePicture());// 身份证扫描件
             * commercialTenantBasicInfo.setTaxRegCertificateCopy(showImage +
             * commercialTenantBasicInfo.getTaxRegCertificateCopy());// 税务登记证(副本)
             * commercialTenantBasicInfo.setTaxpayerCertificate(showImage +
             * commercialTenantBasicInfo.getTaxpayerCertificate());// 一般纳税人证书
             */if (loginRoseRelQuery.getnCustomerTypeId() == 4) {
                commercialTenantBasicInfo
                        .setN_CustomerTypeId(loginRoseRelQuery.getnCustomerTypeId().intValue());
                commercialTenantBasicInfo.setCustomerName("采购商");
                // 查询出所有省份
                provinceList = commercialTenantBasicCopyService.selectAreaByAreaId(1);
                if (commercialTenantBasicInfo.getProvince() != null) {
                    Integer provinceId;
                    try {


                        provinceId = Integer.parseInt(commercialTenantBasicInfo.getProvince());
                    } catch (Exception e) {
                        logger.error("provinceId不能转换为数据" + e.getMessage(), e);
                        return ERROR;
                    }
                    try {
                        cityList = commercialTenantBasicCopyService.selectAreaByAreaId(provinceId);
                    } catch (Exception e) {
                        logger.error("查询城市集合出错" + e.getMessage(), e);
                        return ERROR;
                    }

                } else {
                    cityList = commercialTenantBasicCopyService.selectAreaByAreaId(2);

                }
                if (commercialTenantBasicInfo.getCity() != null) {
                    Integer cityId;
                    try {
                        cityId = Integer.parseInt(commercialTenantBasicInfo.getCity());
                    } catch (Exception e) {
                        logger.error("provinceId不能转换为数据" + e.getMessage(), e);
                        return ERROR;
                    }
                    try {
                        areaList = commercialTenantBasicCopyService.selectAreaByAreaId(cityId);
                    } catch (Exception e) {
                        logger.error("查询城市集合出错" + e.getMessage(), e);
                        return ERROR;
                    }
                } else {
                    areaList = commercialTenantBasicCopyService.selectAreaByAreaId(52);
                }


            } else {

                commercialTenantBasicInfo
                        .setN_CustomerTypeId(loginRoseRelQuery.getnCustomerTypeId().intValue());
                commercialTenantBasicInfo.setCustomerName("供应商");
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        return "preDetailSuccess";
    }



    /**
     * 进入登录ID得到商户信息页面
     * 
     * @return
     */
    public String preBusinessDetail() {
        try {
            commercialTenantBasicInfo =
                    commercialTenantBasicInfoService.selectByPrimaryLoginInfo(loginId);
            loginInfo = commercialTenantBasicInfoService
                    .selectByn_LoginId(commercialTenantBasicInfo.getN_LoginId());
            customerTypeList = commercialTenantBasicInfoService.queryByCommCustomer();
            // commercialTenantBasicInfo =
            // commercialTenantBasicInfoService.selectByPrimaryKey(commercialTenantBasicInfo.getN_CommercialTenantId());

            personalityInfo = commercialTenantBasicInfoService
                    .selectByPersonalityInfo(commercialTenantBasicInfo.getN_LoginId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "preDetailSuccess";
    }

    /**
     * 查询商户信息
     * 
     * @return
     */
    public String pageList() {
        try {
            customerList = commercialTenantBasicInfoService.queryByCommCustomer();
            commercialTenantBasicInfo.setSstatus("3");
            page = commercialTenantBasicInfoService.searchPageByVo(page, commercialTenantBasicInfo);
            return "pageSuccess";
        } catch (Exception e) {
            e.printStackTrace();
            return "pageInput";
        }

    }



    /**
     * 查询采购商信息
     * 
     * @return
     */
    /*
     * 删除采购商相关信息 public String procurementPageList() { try { customerList =
     * commercialTenantBasicInfoService.queryByCommCustomer(); // 设置采购商setCustomerTypeId
     * commercialTenantBasicInfo.setN_CustomerTypeId(4); page =
     * commercialTenantBasicInfoService.searchPageByVo(page, commercialTenantBasicInfo);
     * 
     * return "procurementPageList"; } catch (Exception e) { e.printStackTrace(); return
     * "pageInput"; }
     * 
     * }
     */


    /**
     * 进入修改采购商信息
     * 
     * @return
     */
    /*
     * 删除采购商相关信息 public String procurementDetail() { try { customerTypeList =
     * commercialTenantBasicInfoService.queryByCommCustomer(); commercialTenantBasicInfo =
     * commercialTenantBasicInfoService
     * .selectByPrimaryKey(commercialTenantBasicInfo.getN_CommercialTenantId());
     * 
     * commercialTenantBasicInfo.setUploadBusinessLicencePictur(showImage +
     * commercialTenantBasicInfo.getUploadBusinessLicencePictur());
     * commercialTenantBasicInfo.setCertificatePicture(showImage +
     * commercialTenantBasicInfo.getCertificatePicture());// 身份证扫描件
     * commercialTenantBasicInfo.setTaxRegCertificateCopy(showImage +
     * commercialTenantBasicInfo.getTaxRegCertificateCopy());// 税务登记证(副本)
     * commercialTenantBasicInfo.setTaxpayerCertificate(showImage +
     * commercialTenantBasicInfo.getTaxpayerCertificate());// 一般纳税人证书 loginInfo =
     * commercialTenantBasicInfoService
     * .selectByn_LoginId(commercialTenantBasicInfo.getN_LoginId()); personalityInfo =
     * commercialTenantBasicInfoService
     * .selectByPersonalityInfo(commercialTenantBasicInfo.getN_LoginId()); accountInfo =
     * commercialTenantBasicInfoService
     * .selectByAccountInfo(commercialTenantBasicInfo.getN_LoginId()); loginRoseRelQuery =
     * commercialTenantBasicInfoService
     * .selectByNcustomerId(commercialTenantBasicInfo.getN_CommercialTenantId());
     * 
     * if (loginRoseRelQuery.getnCustomerTypeId() == 4) { commercialTenantBasicInfo
     * .setN_CustomerTypeId(loginRoseRelQuery.getnCustomerTypeId().intValue());
     * commercialTenantBasicInfo.setCustomerName("采购商"); // 查询出所有省份 provinceList =
     * commercialTenantBasicCopyService.selectAreaByAreaId(1); if
     * (commercialTenantBasicInfo.getProvince() != null) { Integer provinceId; try { provinceId =
     * Integer.parseInt(commercialTenantBasicInfo.getProvince()); } catch (Exception e) {
     * logger.error("provinceId不能转换为数据" + e.getMessage(), e); return ERROR; } try { cityList =
     * commercialTenantBasicCopyService.selectAreaByAreaId(provinceId); } catch (Exception e) {
     * logger.error("查询城市集合出错" + e.getMessage(), e); return ERROR; }
     * 
     * } else { cityList = commercialTenantBasicCopyService.selectAreaByAreaId(2);
     * 
     * } if (commercialTenantBasicInfo.getCity() != null) { Integer cityId; try { cityId =
     * Integer.parseInt(commercialTenantBasicInfo.getCity()); } catch (Exception e) {
     * logger.error("provinceId不能转换为数据" + e.getMessage(), e); return ERROR; } try { areaList =
     * commercialTenantBasicCopyService.selectAreaByAreaId(cityId); } catch (Exception e) {
     * logger.error("查询城市集合出错" + e.getMessage(), e); return ERROR; } } else {
     * 
     * areaList = commercialTenantBasicCopyService.selectAreaByAreaId(52); }
     * 
     * } else {
     * 
     * commercialTenantBasicInfo
     * .setN_CustomerTypeId(loginRoseRelQuery.getnCustomerTypeId().intValue());
     * commercialTenantBasicInfo.setCustomerName("供应商"); }
     * 
     * }
     * 
     * catch (Exception e) { e.printStackTrace(); }
     * 
     * return "procurementDetail"; }
     */

    /**
     * 进入采购商详情信息
     * 
     * @return
     */
    /*
     * 删除采购商相关信息 public String procurementUpdate() { try { customerTypeList =
     * commercialTenantBasicInfoService.queryByCommCustomer(); commercialTenantBasicInfo =
     * commercialTenantBasicInfoService
     * .selectByPrimaryKey(commercialTenantBasicInfo.getN_CommercialTenantId());
     * 
     * commercialTenantBasicInfo.setUploadBusinessLicencePictur(showImage +
     * commercialTenantBasicInfo.getUploadBusinessLicencePictur());
     * commercialTenantBasicInfo.setCertificatePicture(showImage +
     * commercialTenantBasicInfo.getCertificatePicture());// 身份证扫描件
     * commercialTenantBasicInfo.setTaxRegCertificateCopy(showImage +
     * commercialTenantBasicInfo.getTaxRegCertificateCopy());// 税务登记证(副本)
     * commercialTenantBasicInfo.setTaxpayerCertificate(showImage +
     * commercialTenantBasicInfo.getTaxpayerCertificate());// 一般纳税人证书
     * 
     * 
     * loginInfo = commercialTenantBasicInfoService
     * .selectByn_LoginId(commercialTenantBasicInfo.getN_LoginId()); personalityInfo =
     * commercialTenantBasicInfoService
     * .selectByPersonalityInfo(commercialTenantBasicInfo.getN_LoginId()); accountInfo =
     * commercialTenantBasicInfoService
     * .selectByAccountInfo(commercialTenantBasicInfo.getN_LoginId()); // 查询出所有省份 provinceList =
     * commercialTenantBasicCopyService.selectAreaByAreaId(1); if
     * (commercialTenantBasicInfo.getProvince() != null) { Integer provinceId; try { provinceId =
     * Integer.parseInt(commercialTenantBasicInfo.getProvince()); } catch (Exception e) {
     * logger.error("provinceId不能转换为数据" + e.getMessage(), e); return ERROR; } try { cityList =
     * commercialTenantBasicCopyService.selectAreaByAreaId(provinceId); } catch (Exception e) {
     * logger.error("查询城市集合出错" + e.getMessage(), e); return ERROR; }
     * 
     * } else { cityList = commercialTenantBasicCopyService.selectAreaByAreaId(2);
     * 
     * } if (commercialTenantBasicInfo.getCity() != null) { Integer cityId; try { cityId =
     * Integer.parseInt(commercialTenantBasicInfo.getCity()); } catch (Exception e) {
     * logger.error("provinceId不能转换为数据" + e.getMessage(), e); return ERROR; } try { areaList =
     * commercialTenantBasicCopyService.selectAreaByAreaId(cityId); } catch (Exception e) {
     * logger.error("查询城市集合出错" + e.getMessage(), e); return ERROR; } } else {
     * 
     * areaList = commercialTenantBasicCopyService.selectAreaByAreaId(52); } } catch (Exception e) {
     * e.printStackTrace(); } return "procurementUpdate"; }
     */

    /**
     * 进入采购商审核
     * 
     * @return
     */
    /*
     * 删除采购商相关信息 public String procurementVerify() { try { customerTypeList =
     * commercialTenantBasicInfoService.queryByCommCustomer(); commercialTenantBasicInfo =
     * commercialTenantBasicInfoService
     * .selectByPrimaryKey(commercialTenantBasicInfo.getN_CommercialTenantId());
     * 
     * commercialTenantBasicInfo.setUploadBusinessLicencePictur(showImage +
     * commercialTenantBasicInfo.getUploadBusinessLicencePictur());
     * commercialTenantBasicInfo.setCertificatePicture(showImage +
     * commercialTenantBasicInfo.getCertificatePicture());// 身份证扫描件
     * commercialTenantBasicInfo.setTaxRegCertificateCopy(showImage +
     * commercialTenantBasicInfo.getTaxRegCertificateCopy());// 税务登记证(副本)
     * commercialTenantBasicInfo.setTaxpayerCertificate(showImage +
     * commercialTenantBasicInfo.getTaxpayerCertificate());// 一般纳税人证书
     * 
     * loginInfo = commercialTenantBasicInfoService
     * .selectByn_LoginId(commercialTenantBasicInfo.getN_LoginId()); personalityInfo =
     * commercialTenantBasicInfoService
     * .selectByPersonalityInfo(commercialTenantBasicInfo.getN_LoginId()); accountInfo =
     * commercialTenantBasicInfoService
     * .selectByAccountInfo(commercialTenantBasicInfo.getN_LoginId()); // 查询出所有省份 provinceList =
     * commercialTenantBasicCopyService.selectAreaByAreaId(1); if
     * (commercialTenantBasicInfo.getProvince() != null) { Integer provinceId; try { provinceId =
     * Integer.parseInt(commercialTenantBasicInfo.getProvince()); } catch (Exception e) {
     * logger.error("provinceId不能转换为数据" + e.getMessage(), e); return ERROR; } try { cityList =
     * commercialTenantBasicCopyService.selectAreaByAreaId(provinceId); } catch (Exception e) {
     * logger.error("查询城市集合出错" + e.getMessage(), e); return ERROR; }
     * 
     * }
     * 
     * else { cityList = commercialTenantBasicCopyService.selectAreaByAreaId(2);
     * 
     * } if (commercialTenantBasicInfo.getCity() != null) { Integer cityId; try { cityId =
     * Integer.parseInt(commercialTenantBasicInfo.getCity()); } catch (Exception e) {
     * logger.error("provinceId不能转换为数据" + e.getMessage(), e); return ERROR; } try { areaList =
     * commercialTenantBasicCopyService.selectAreaByAreaId(cityId); } catch (Exception e) {
     * logger.error("查询城市集合出错" + e.getMessage(), e); return ERROR; } } else {
     * 
     * areaList = commercialTenantBasicCopyService.selectAreaByAreaId(52); } } catch (Exception e) {
     * e.printStackTrace(); } return "procurementVerify"; }
     */


    /**
     * 采购商审核
     * 
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws MalformedURLException
     */
    /*
     * 删除采购商相关信息 public String procurementVerifyPass() throws SQLException { try {
     * commercialTenantBasicInfoService.verifyPass(commercialTenantBasicInfo); QualificationsDO
     * qualificationsDO = new QualificationsDO();
     * qualificationsDO.setUserId(commercialTenantBasicInfo.getN_LoginId());
     * qualificationsDO.setType(new Short((short) 0)); qualificationsDO.setStatus(new Short((short)
     * 1)); qualificationsDO.setCreateDate(new Date());
     * qualificaitonsService.insertQualifications(qualificationsDO); accountInfo =
     * accountInfoService
     * .selectByPrimaryLoginInfo(commercialTenantBasicInfo.getN_LoginId().intValue()); //
     * 增jia预备金支付范围 if (accountInfo.getPaymentpwd() != null) { String pay_Range = "1,5"; if
     * (accountInfo.getPay_Range() != null) { pay_Range = accountInfo.getPay_Range() + ",5"; }
     * AccountInfo vo = new AccountInfo();
     * vo.setN_LoginId(commercialTenantBasicInfo.getN_LoginId().intValue());
     * vo.setPay_Range(pay_Range);
     * 
     * int result = accountInfoService.updateByLoginId(vo); log.info("增加预备金支付范围：" + pay_Range +
     * "----loginId:==" + commercialTenantBasicInfo.getN_LoginId().intValue()); } else { String
     * pay_Range = "5"; AccountInfo vo = new AccountInfo();
     * vo.setN_LoginId(commercialTenantBasicInfo.getN_LoginId().intValue());
     * vo.setPay_Range(pay_Range); }
     * 
     * if (accountInfo.getEmail() != null) { List emailLists = new ArrayList(); List<Long> uidLists
     * = new ArrayList<Long>(); List loginNameList = new ArrayList();
     * emailLists.add(accountInfo.getEmail()); loginNameList.add(accountInfo.getLoginAccount());
     * Integer uid = commercialTenantBasicInfo.getN_LoginId().intValue(); String uids =
     * uid.toString(uid); Long uidss = Long.valueOf(uids).longValue(); uidLists.add(uidss); String
     * emailContext1 = "您提交的采购商审核通过,请进入我的信息查看最新的资料"; String title = "采购商审核通知"; Map<String, Object>
     * sendEmailMap = emailSubscriptionRemoteService.emailBySpread(uidLists, emailLists,
     * loginNameList, title, emailContext1, 2); // 发送邮件 } List mobileList = new ArrayList();
     * List<Long> uidLists = new ArrayList<Long>(); if (accountInfo.getMobile() != null) { // 发送手机短信
     * mobileList.add(accountInfo.getMobile()); Integer uid =
     * commercialTenantBasicInfo.getN_LoginId().intValue(); String uids = uid.toString(uid); Long
     * uidss = Long.valueOf(uids).longValue(); uidLists.add(uidss); String smsContext =
     * "您提交的采购商审核通过,请进入我的信息查看最新的资料"; // 发送短信 Map<String, Object> sendMap =
     * messageRemoteService.sendMsgBySpread(uidLists, mobileList, smsContext, 3, 2); }
     * 
     * } catch (Exception e) {
     * 
     * e.printStackTrace(); } return procurementPageList(); }
     * 
     * 
     * public String procurementVerifyNoPass() {
     * 
     * commercialTenantBasicInfo.setN_LoginId(commercialTenantBasicInfo.getN_LoginId());
     * 
     * return "procurementVerifyNoPass"; }
     * 
     * public String notPass() throws SQLException { try {
     * commercialTenantBasicInfoService.notPass(commercialTenantBasicInfo); accountInfo =
     * accountInfoService
     * .selectByPrimaryLoginInfo(commercialTenantBasicInfo.getN_LoginId().intValue()); if
     * (accountInfo.getEmail() != null) { List emailLists = new ArrayList(); List<Long> uidLists =
     * new ArrayList<Long>(); List loginNameList = new ArrayList(); Integer uid =
     * commercialTenantBasicInfo.getN_LoginId().intValue(); String uids = uid.toString(uid); Long
     * uidss = Long.valueOf(uids).longValue(); uidLists.add(uidss);
     * emailLists.add(accountInfo.getEmail()); loginNameList.add(accountInfo.getLoginAccount());
     * String emailContext1 = "很遗憾,你提交的采购商审核已被拒绝,具体原因如下:" +
     * commercialTenantBasicInfo.getDescription(); String title = "采购商审核通知"; if (uidLists != null &&
     * emailLists != null && loginNameList != null) { Map<String, Object> sendEmailMap =
     * emailSubscriptionRemoteService.emailBySpread(uidLists, emailLists, loginNameList, title,
     * emailContext1, 2);
     * 
     * } // 发送邮件 } List mobileList = new ArrayList(); List<Long> uidLists = new ArrayList<Long>();
     * if (accountInfo.getMobile() != null) { // 发送手机短信 mobileList.add(accountInfo.getMobile());
     * Integer uid = commercialTenantBasicInfo.getN_LoginId().intValue(); String uids =
     * uid.toString(uid); Long uidss = Long.valueOf(uids).longValue(); uidLists.add(uidss); String
     * smsContext = "很遗憾,你提交的采购商审核已被拒绝,具体原因如下：" + commercialTenantBasicInfo.getDescription(); //
     * 发送短信 Map<String, Object> sendMap = messageRemoteService.sendMsgBySpread(uidLists, mobileList,
     * smsContext, 3, 2);
     * 
     * } } catch (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); }
     * 
     * 
     * return procurementPageList(); }
     */

    /**
     * 修改商户信息
     * 
     * @return
     */
    /*
     * @Token public String updateProcurement() { Integer rows = 0; try { // 获取当前登录人 SysUser sysuser
     * = (SysUser) session.get("sysUser"); // if(fileName!=null){ // imageFileName = new
     * Date().getTime() + getExtention(fileName); // File imageFile = new File(uploadImage +
     * imageFileName); // copy(myFile, imageFile); //
     * commercialTenantBasicInfo.setUploadBusinessLicencePictur(imageFileName); // }吃撑 if
     * (uploadFileImage != null) { imageFileName = new Date().getTime() +
     * getExtention(uploadFileImageFileName); File imageFile = new File(uploadImage +
     * imageFileName); copy(uploadFileImage, imageFile);
     * commercialTenantBasicInfo.setUploadBusinessLicencePictur(imageFileName); } if (otherSidImage
     * != null) { imageFileName = new Date().getTime() + getExtention(otherSidImageFileName); File
     * imageFile = new File(uploadImage + imageFileName); copy(otherSidImage, imageFile);
     * commercialTenantBasicInfo.setCertificatePicture(imageFileName);// 身份证扫描件 } if
     * (certificateCopyImage != null) { imageFileName = new Date().getTime() +
     * getExtention(certificateCopyImageFileName); File imageFile = new File(uploadImage +
     * imageFileName); copy(certificateCopyImage, imageFile);
     * commercialTenantBasicInfo.setTaxRegCertificateCopy(imageFileName); } if (certificateImage !=
     * null) { imageFileName = new Date().getTime() + getExtention(certificateImageFileName); File
     * imageFile = new File(uploadImage + imageFileName); copy(certificateImage, imageFile);
     * commercialTenantBasicInfo.setTaxpayerCertificate(imageFileName); }
     * personalityInfo.setN_Modified(sysuser.getUserId());
     * loginInfo.setN_Modified(sysuser.getUserId());
     * commercialTenantBasicInfo.setN_Modified(sysuser.getUserId());
     * accountInfo.setN_Modified(sysuser.getUserId()); rows =
     * commercialTenantBasicInfoService.updateCommercialTenantBasicInfo( commercialTenantBasicInfo,
     * loginInfo, personalityInfo, accountInfo); if (rows > 0) { this.addActionMessage("采购商信息修改成功");
     * commercialTenantBasicInfo = new CommercialTenantBasicInfo(); loginInfo = new LoginInfo();
     * personalityInfo = new PersonalityInfo(); // 查询出所有省份 provinceList =
     * commercialTenantBasicCopyService.selectAreaByAreaId(1); if
     * (commercialTenantBasicInfo.getProvince() != null) { Integer provinceId; try { provinceId =
     * Integer.parseInt(commercialTenantBasicInfo.getProvince()); } catch (Exception e) {
     * logger.error("provinceId不能转换为数据" + e.getMessage(), e); return ERROR; } try { cityList =
     * commercialTenantBasicCopyService.selectAreaByAreaId(provinceId); } catch (Exception e) {
     * logger.error("查询城市集合出错" + e.getMessage(), e); return ERROR; }
     * 
     * } else { cityList = commercialTenantBasicCopyService.selectAreaByAreaId(2);
     * 
     * } if (commercialTenantBasicInfo.getCity() != null) { Integer cityId; try { cityId =
     * Integer.parseInt(commercialTenantBasicInfo.getCity()); } catch (Exception e) {
     * logger.error("provinceId不能转换为数据" + e.getMessage(), e); return ERROR; } try { areaList =
     * commercialTenantBasicCopyService.selectAreaByAreaId(cityId); } catch (Exception e) {
     * logger.error("查询城市集合出错" + e.getMessage(), e); return ERROR; } } else { areaList =
     * commercialTenantBasicCopyService.selectAreaByAreaId(52);
     * 
     * } return procurementPageList(); } } catch (Exception e) { this.addActionMessage("采购商信息修改失败");
     * e.printStackTrace(); } return procurementPageList(); }
     */


    public CommercialTenantBasicInfo getCommercialTenantBasicInfo() {
        return commercialTenantBasicInfo;
    }

    public void setCommercialTenantBasicInfo(CommercialTenantBasicInfo commercialTenantBasicInfo) {
        this.commercialTenantBasicInfo = commercialTenantBasicInfo;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    public CommercialTenantBasicInfoService getCommercialTenantBasicInfoService() {
        return commercialTenantBasicInfoService;
    }

    public void setCommercialTenantBasicInfoService(
            CommercialTenantBasicInfoService commercialTenantBasicInfoService) {
        this.commercialTenantBasicInfoService = commercialTenantBasicInfoService;
    }



    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    public void setMyFileContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setMyFileFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setMyFile(File myFile) {
        this.myFile = myFile;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Integer getLoginIn_Id() {
        return loginIn_Id;
    }

    public void setLoginIn_Id(Integer loginInId) {
        loginIn_Id = loginInId;
    }


    public List<Integer> getN_CommercialTenantIds() {
        return n_CommercialTenantIds;
    }

    public void setN_CommercialTenantIds(List<Integer> nCommercialTenantIds) {
        n_CommercialTenantIds = nCommercialTenantIds;
    }

    public PersonalityInfo getPersonalityInfo() {
        return personalityInfo;
    }

    public void setPersonalityInfo(PersonalityInfo personalityInfo) {
        this.personalityInfo = personalityInfo;
    }

    public List<BnesCustomerTypeQuery> getCustomerTypeList() {
        return customerTypeList;
    }

    public void setCustomerTypeList(List<BnesCustomerTypeQuery> customerTypeList) {
        this.customerTypeList = customerTypeList;
    }

    public List<Rank> getRankList() {
        return rankList;
    }

    public void setRankList(List<Rank> rankList) {
        this.rankList = rankList;
    }

    public List<UserLevel> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<UserLevel> levelList) {
        this.levelList = levelList;
    }

    public List<BnesCustomerTypeQuery> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<BnesCustomerTypeQuery> customerList) {
        this.customerList = customerList;
    }

    public Integer getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(Integer customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public Integer getLoginId() {
        return loginId;
    }

    public void setLoginId(Integer loginId) {
        this.loginId = loginId;
    }

    public QualificaitonsService getQualificaitonsService() {
        return qualificaitonsService;
    }

    public void setQualificaitonsService(QualificaitonsService qualificaitonsService) {
        this.qualificaitonsService = qualificaitonsService;
    }

    public File getUploadFileImage() {
        return uploadFileImage;
    }

    public void setUploadFileImage(File uploadFileImage) {
        this.uploadFileImage = uploadFileImage;
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

    public String getShowImage() {
        return showImage;
    }

    public void setShowImage(String showImage) {
        this.showImage = showImage;
    }


}
