package com.kmzyc.supplier.action;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.km.framework.fileupload.Upload;
import com.kmzyc.b2b.vo.UserBaseInfo;
import com.kmzyc.supplier.maps.SuppliersTypeMap;
import com.kmzyc.supplier.model.Categorys;
import com.kmzyc.supplier.model.MerchantInfo;
import com.kmzyc.supplier.model.SuppliersAvailableCategorys;
import com.kmzyc.supplier.model.SuppliersCertificate;
import com.kmzyc.supplier.model.SuppliersInfo;
import com.kmzyc.supplier.model.User;
import com.kmzyc.supplier.service.MerchantInfoService;
import com.kmzyc.supplier.service.SupplierCategorysService;
import com.kmzyc.supplier.service.SupplierCertificateService;
import com.kmzyc.supplier.util.FileUploadUtils;
import com.kmzyc.user.remote.service.CustomerRemoteService;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.bean.ResultMessage;

@Controller("applySupplierAction")
@Scope("prototype")
public class ApplySupplierAction extends SupplierBaseAction {

    private static Logger logger = LoggerFactory.getLogger(ApplySupplierAction.class);

    @Resource
    private MerchantInfoService merchantInfoService;

    private String userName;// 用户名

    private String password;// 密码

    private User showUserinfo;// 保存用户信息

    private MerchantInfo merchantInfo;// 保存商户信息

    private SuppliersInfo suppliersInfo;// 保存供应商信息

    private String supplierCategorys;// 保存要选中的类目id

    private List<SuppliersAvailableCategorys> categorysList;// 获得供应商类目集合

    @Resource
    private SupplierCertificateService supplierCertificateService;
    @Resource
    private CustomerRemoteService customerRemoteService;

    @Resource
    private SupplierCategorysService supplierCategorysService;

    private String[] allowTypes = {".jpg", ".jpeg", ".gif", ".bmp", ".png", ".JPG", ".JPEG", ".GIF", ".BMP", ".PNG"};

    private long fileMaximumSize = 1 * 1024 * 1024; // 上传文件的大小限制，

    private String realName; // 用户设置的文件的名称

    private File file;// 用户传上来的文件

    private String fileFileName; // 文件名称

    private String fileContentType; // 文件类型

    private Long supplierId;// 供应商ID

    private List<SuppliersCertificate> certificateList;

    private Long scId;// 供应商资质ID

    // 跳转到不b2b商城的路径
    private String goB2bPath = ConfigurationUtil.getString("portalPath");

    // 去验证手机，邮箱的链接
    private String goToCkPath = ConfigurationUtil.getString("goToCkPath");

    // 去注册的链接
    private String goToRegisterPath = ConfigurationUtil.getString("goToRegisterPath");

    private File businessFile;// 上传营业执照电子版

    private File taxRegistratFile;// 税务登记证电子版

    private File organizationFile;// 组织机构代码电子版

    private String organizationFileName;

    private String taxRegistratFileName;

    private String businessFileName;

    private String message;

    private String viewType;

    private List<SuppliersAvailableCategorys> suppliserCategoryList;

    /**
     * 第一步申请页面
     *
     * @return
     */
    public String goFirstApplyPag() {
        return SUCCESS;
    }

    /**
     * 验证是否是会员
     *
     * @return
     */
    public String verifyMember() {
        User user = new User();
        // 用户名，即手机号码
        Map<String, Object> result = Maps.newHashMap();
        String newUserName = null;
        try {
            if (StringUtils.isNotBlank(userName)) {
                newUserName = java.net.URLDecoder.decode(userName, "UTF-8").toLowerCase();
            }

            // 获取登录加盐器
            UserBaseInfo userBaseInfo = new UserBaseInfo();
            userBaseInfo.setMobile(userName);

            userBaseInfo.setPassword(password);

            userBaseInfo = this.customerRemoteService.queryUserPasswordTwice(userBaseInfo,"login");

            //SaltInfo saltInfo = saltInfoService.querySaltInfo(userBaseInfo);
            if (userBaseInfo == null) {
                result.put("result", "0");
                writeJson(result);
                return null;
            }

            user.setLoginPassword(userBaseInfo.getPassword());
            user.setMobile(newUserName);
            User us = merchantInfoService.selectByUserName(user);
            // 用户名和密码不匹配
            if (null == us || us.getStatus() != 1) {
                result.put("result", "0");
                writeJson(result);
                return null;
            }

            merchantInfo = new MerchantInfo();
            merchantInfo.setLoginId(us.getLoginId());
            merchantInfo = merchantInfoService.selectByLoginId(this.merchantInfo);
            SuppliersInfo supplierInfo = null;
            if (merchantInfo != null) {
                // 根据商户id查询供应商信息
                supplierInfo = merchantInfoService.selectByMerchantId(merchantInfo.getMerchantId());
            }

            if (null == merchantInfo || null == supplierInfo ) {
                result.put("result", "10");
                result.put("loginId", us.getLoginId());
                writeJson(result);
                return null;
            }

            if (supplierInfo.getStatus() == 3) {// 审核通过了
                // 通过审核，不需要申请了
                result.put("result", "3");
                result.put("loginId", us.getLoginId());
            } else if (supplierInfo.getStatus() == 2) {// 用户已经提交了申请不需要再进行提交
                result.put("result", "2");
                result.put("loginId", us.getLoginId());
            } else if (supplierInfo.getStatus() == 1) {// 待申请
                result.put("result", "1");
                result.put("loginId", us.getLoginId());
            } else if (supplierInfo.getStatus() == 4) {// 审核不通过
                result.put("result", "4");
                result.put("loginId", merchantInfo.getLoginId());
                result.put("describe", supplierInfo.getDescribe());
            } else if (supplierInfo.getStatus() == 5) {// 商家却认状态
                result.put("result", "5");
                result.put("loginId", us.getLoginId());
            } else {
                result.put("result", "10");
                result.put("loginId", us.getLoginId());
            }

            writeJson(result);
            return null;
        } catch (Exception e) {
            logger.error("验证供应商是否是会员出现异常：" + e.getMessage(), e);
            return ERROR;
        }
    }

    /**
     * 去申请供应商协议页面
     *
     * @return
     */
    public String goApplyAgreement() throws ParseException {
        if (!"1".equals(viewType)) {// 去到商家却认页面
            return SUCCESS;
        }

        MerchantInfo mer = new MerchantInfo();
        mer.setLoginId(showUserinfo.getLoginId());
        try {
            // 根据登录id查询商户信息
            MerchantInfo merInfo = merchantInfoService.selectByLoginId(mer);
            // 根据商户id查询供应商信息
            SuppliersInfo supp = merchantInfoService.selectByMerchantId(merInfo.getMerchantId());
            // 查询供应商供应范围
            suppliserCategoryList = supplierCategorysService.findSupplierCategoriesBySupplierId(supp.getSupplierId());
        } catch (Exception e) {
            logger.error("根据登录id查询出现异常：" + e.getMessage(), e);
        }
        return "verify";

    }

    String handleFile (File file, String fileName, String root) throws IOException {
        // 使用当前时间戳，避免文件重复被覆盖
        long time = System.currentTimeMillis();
        String name = time + "";
        String dbFileName = name + StringUtils.substring(fileName, StringUtils.lastIndexOf(fileName, '.')).toLowerCase();

        // 图片上传时的绝对路径
        String rootPath = FileUploadUtils.createSupplierSavePath("certificate");
        // 图片预览时的相对路径
        String dbPath = rootPath.substring(root.length());

        File destFile = new File(rootPath, dbFileName);
        FileUtils.copyFile(file, destFile);
        // 删除临时文件
        file.delete();
        return dbPath + dbFileName;
    }

    boolean checkFile (File file, String fileName, ResultMessage message, String msg) {
        int index = StringUtils.lastIndexOf(fileName, '.');
        if (index == -1) {
            message.setIsSuccess(Boolean.FALSE);
            message.setMessage(msg+"上传错误。");
            return Boolean.FALSE;
        }
        // 获取文件后缀名--统一转换为大写字符验证
        String extFileName = StringUtils.substring(fileName, index).toUpperCase();
        if (!Upload.isValid(extFileName, allowTypes)) {
            message.setIsSuccess(Boolean.FALSE);
            message.setMessage(msg+"上传类型错误。");
            return Boolean.FALSE;
        } else if (file.length() > fileMaximumSize) {
            message.setIsSuccess(Boolean.FALSE);
            message.setMessage(msg+"上传大小不能超过1M。");
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 输入要删除的文件位置
     * @param path
     */
    private void deleteFile(String deleteRoot, String path) {
        String delFile = deleteRoot + path;
        File f = new File(delFile);
        if (f.exists()) {
            f.delete();
        }
    }

    /**
     * 申请供应商
     */
    public String applySupplierTwo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        Date nowTime = null;
        ResultMessage resultMessage = new ResultMessage();
        try {
            nowTime = sdf.parse(sdf.format(new Date()));// 获取当前系统时间
            merchantInfo.setCreateDate(nowTime);
            merchantInfo.setLoginId(showUserinfo.getLoginId());// 设置登录id
            suppliersInfo.setStatus(Short.parseShort("1"));// 设置供应商状态为待申请
            String root = ConfigurationUtil.getString("SUPPLIER_CERTIFICATE_PATH");

            // 当商户id不为空的时候将执行修改操作
            if (suppliersInfo.getSupplierType() == 3) {// 申请的类型为代销，默认禁止登录供应商平台
                suppliersInfo.setLoginStatus(Short.parseShort("1"));
            } else {
                suppliersInfo.setLoginStatus(Short.parseShort("2"));
            }

            if (null != merchantInfo.getMerchantId() && null != suppliersInfo.getSupplierId()) {
                String deleteRoot = ServletActionContext.getServletContext().getRealPath(String.valueOf(File.separatorChar));
                MerchantInfo newMerchantInfo = new MerchantInfo();
                newMerchantInfo.setLoginId(showUserinfo.getLoginId());// 从页面获取用户id
                MerchantInfo mer = merchantInfoService.selectByLoginId(newMerchantInfo);// 根据登录id查询商户信息
                if (null != organizationFile) {
                    if (!checkFile(organizationFile, organizationFileName, resultMessage, "组织机构代码电子版")) {
                        writeJson(resultMessage);
                        return null;
                    }
                    merchantInfo.setOrganizationUrl(handleFile(organizationFile, organizationFileName, root));
                    deleteFile(deleteRoot,mer.getOrganizationUrl());
                }
                if (null != businessFile) {
                    if (!checkFile(businessFile, businessFileName, resultMessage, "营业执照电子版")) {
                        writeJson(resultMessage);
                        return null;
                    }
                    merchantInfo.setUploadBusinessLicencePictur(handleFile(businessFile, businessFileName, root));
                    deleteFile(deleteRoot, mer.getUploadBusinessLicencePictur());
                }
                if (null != taxRegistratFile) {
                    if (!checkFile(taxRegistratFile, taxRegistratFileName, resultMessage, "税务登记证电子版")) {
                        writeJson(resultMessage);
                        return null;
                    }
                    merchantInfo.setTaxRegCertificateCopy(handleFile(taxRegistratFile, taxRegistratFileName, root));
                    deleteFile(deleteRoot, mer.getTaxRegCertificateCopy());
                }
                merchantInfoService.applySupplierTwoUpdate(merchantInfo, suppliersInfo, supplierCategorys);// 修改商户和供应商的数据
            } else {
                if (null == organizationFile) {
                    resultMessage.setMessage("组织机构代码电子版！");
                    resultMessage.setIsSuccess(Boolean.FALSE);
                    writeJson(resultMessage);
                    return null;
                }
                if (null == businessFile) {
                    resultMessage.setMessage("请上传营业执照电子版！");
                    resultMessage.setIsSuccess(Boolean.FALSE);
                    writeJson(resultMessage);
                    return null;
                }
                if (null == taxRegistratFile) {
                    resultMessage.setMessage("税务登记证电子版！");
                    resultMessage.setIsSuccess(Boolean.FALSE);
                    writeJson(resultMessage);
                    return null;
                }

                if (!checkFile(organizationFile, organizationFileName, resultMessage, "组织机构代码电子版")) {
                    writeJson(resultMessage);
                    return null;
                }
                merchantInfo.setOrganizationUrl(handleFile(organizationFile, organizationFileName, root));
                if (!checkFile(businessFile, businessFileName, resultMessage, "营业执照电子版")) {
                    writeJson(resultMessage);
                    return null;
                }
                merchantInfo.setUploadBusinessLicencePictur(handleFile(businessFile, businessFileName, root));
                if (!checkFile(taxRegistratFile, taxRegistratFileName, resultMessage, "组织机构代码电子版")) {
                    writeJson(resultMessage);
                    return null;
                }
                merchantInfo.setTaxRegCertificateCopy(handleFile(taxRegistratFile, taxRegistratFileName, root));
                suppliersInfo.setBusinessStatus(Short.valueOf("2"));
                suppliersInfo.setCloseStatus(Short.valueOf("2"));
                suppliersInfo.setShopBrowseStatus(Short.parseShort("1"));// 供应商店铺浏览量启用状态
                merchantInfoService.applySupplierTwo(merchantInfo, suppliersInfo, supplierCategorys);// 向商户和供应商表插入数据
            }
        } catch (IOException e) {
            logger.error("申请供应商第二步出现异常：" + e.getMessage(), e);
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("图片加载失败！");
            writeJson(resultMessage);
            return null;
        } catch (Exception e) {
            logger.error("申请供应商第二步出现异常：" + e.getMessage(), e);
            resultMessage.setIsSuccess(Boolean.FALSE);
            resultMessage.setMessage("申请失败，请联系客服！");
            writeJson(resultMessage);
            return null;
        }

        resultMessage.setIsSuccess(Boolean.TRUE);
        writeJson(resultMessage);
        return null;
    }

    /**
     * 检查是否有存在一样的公司名字
     *
     * @return
     */
    public String ckCorporateName() {
        try {
            String prName = merchantInfo.getCorporateName();
            MerchantInfo mer = new MerchantInfo();
            mer.setCorporateName(prName.replaceAll(" ", ""));
            boolean isExist = merchantInfoService.selectByCompanyName(mer);
            if (isExist) {// 公司名称已经存在
                getResponse().getWriter().print("1");
            } else {
                getResponse().getWriter().print("0");
            }
        } catch (Exception e) {
            logger.error("按公司名称查询数据出现异常：" + e.getMessage(), e);
            return ERROR;
        }
        return null;
    }

    /**
     * 检查组织结构代码是否存在相同的
     *
     * @return
     */
    public String ckCode() {
        try {
            boolean isExist = merchantInfoService.selectByCode(merchantInfo);
            if (isExist) {// 用户输入的组织结构代码存在有相同的
                getResponse().getWriter().print("1");
            } else {// 没有存在相同的机构组织代码
                getResponse().getWriter().print("0");
            }
        } catch (Exception e) {
            logger.error("根据组织结构代码查询数据出现异常：" + e.getMessage(), e);
            return ERROR;
        }
        return null;
    }

    /**
     * 检查营业执照注册号是否存在相同的
     *
     * @return
     */
    public String ckRegister() {
        try {
            boolean isExist = merchantInfoService.selectByRegister(merchantInfo);
            if (isExist) {// 用户输入的营业执照注册号存在有相同的
                getResponse().getWriter().print("1");
            } else {// 没有存在相同的营业执照注册号
                getResponse().getWriter().print("0");
            }
        } catch (Exception e) {
            logger.error("根据组织结构代码查询数据出现异常：" + e.getMessage(), e);
            return ERROR;
        }
        return null;
    }

    public String goApplySupplierThree() {
        MerchantInfo newMerchantInfo = new MerchantInfo();
        newMerchantInfo.setLoginId(showUserinfo.getLoginId());// 设置登录id
        try {
            // 根据用户id查询商户的信息
            MerchantInfo merchant = merchantInfoService.selectByLoginId(newMerchantInfo);
            if (merchant == null) {
                logger.error("没有找到商户信息");
                return ERROR;
            }
            // 根据商户id查询供应商信息
            SuppliersInfo supplier = merchantInfoService.selectByMerchantId(merchant.getMerchantId());
            if (supplier == null) {
                logger.error("没有找到商户信息");
                return ERROR;
            }

            // 查询已经上传的资质文件
            supplierId = supplier.getSupplierId();
            certificateList = supplierCertificateService.findCertificateListBySupplierId(supplierId);
        } catch (Exception e) {
            logger.error("申请第三步异常：" + e.getMessage());
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 供应商申请第三步资质文件上传页面
     *
     * @return
     */
    public String uploadApplySupplierThree() {
        if (null == file) {
            /** 若文件为空 则 报错 **/
            return ERROR;
        }
        // 文件后缀名
        int index = StringUtils.lastIndexOf(fileFileName, '.');
        if (index == -1) {
            return "文件类型错误！";
        }

        // 获取文件后缀名
        String extFileName = StringUtils.substring(fileFileName, index + 1);
        // TODO 定义上传位置
        // String root =
        // ConfigurationUtil.getString("SUPPLIER_CERTIFICATE_PATH");
        String root = ServletActionContext.getServletContext().getRealPath("/uploadFile");

        // 分隔符
        // String separator = java.io.File.separator;

        // 使用当前时间戳，避免文件重复被覆盖
        long time = System.currentTimeMillis();
        String name = time + "";
        String filename = name + "." + extFileName.toLowerCase();

        try {
            // TODO 文件保存的位置和名称
            // 创建供应商ID为名称的文件夹
            String filePath = "/uploadFile/" + filename;
            SuppliersCertificate suppliersCertificate = new SuppliersCertificate();
            MerchantInfo newMerchantInfo = new MerchantInfo();
            newMerchantInfo.setLoginId(showUserinfo.getLoginId());// 设置登录id
            showUserinfo.setLoginId(showUserinfo.getLoginId());
            MerchantInfo mer = merchantInfoService.selectByLoginId(newMerchantInfo);// 根据用户id查询商户的信息
            SuppliersInfo supplier = null;
            if (mer == null) {
                logger.error("没有找到商户信息。");
                return ERROR;
            }

            supplier = merchantInfoService.selectByMerchantId(mer.getMerchantId());// 根据商户id查询供应商信息
            if(supplier == null) {
                logger.error("没有供应商信息。");
                return ERROR;
            }

            Long supplierId = supplier.getSupplierId();
            suppliersCertificate.setSupplierId(supplierId);// 供应商ID
            suppliersCertificate.setDocType("GMP");
            suppliersCertificate.setDocCode("S20001");// 文件编号(生成文件的规则)
            suppliersCertificate.setFilePath(filePath);// 文件路径
            suppliersCertificate.setFileName(realName);// 文件名称

            // 保存到数据库
            merchantInfoService.uploadApplySupplierThree(suppliersCertificate);// 保存上传的文件信息。

            File destFile = new File(root, filename);

            FileUtils.copyFile(file, destFile);
            // 删除临时文件
            file.delete();
            // 查询已经上传的资质文件
            certificateList = supplierCertificateService.findCertificateListBySupplierId(supplierId);
            this.supplierId = certificateList.get(0).getSupplierId();
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
        return SUCCESS;
    }

    public String deleteFile() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        String scIdTemp = request.getParameter("scId");
        if (StringUtils.isNotEmpty(scIdTemp)) {
            scId = Long.valueOf(scIdTemp);
        }

        try {
            SuppliersCertificate suppliersCertificate = supplierCertificateService.findCertificateByScId(scId);
            if (null != suppliersCertificate) {
                supplierCertificateService.deleteSupplierCertificate(scId);
                // 删除上传到服务器上的图片
                String root = ServletActionContext.getServletContext().getRealPath("/");
                String delFile = root + suppliersCertificate.getFilePath();
                File f = new File(delFile); // 输入要删除的文件位置
                if (f.exists()) {
                    f.delete();
                }
            }

            // 查询已经上传的资质文件
            certificateList = supplierCertificateService.findCertificateListBySupplierId(suppliersInfo.getSupplierId());
        } catch (Exception e) {
            logger.error("删除资质文件出错," + e.getMessage());
            return ERROR;
        }
        getResponse().getWriter().print("0");
        return null;
    }

    /**
     * 申请供应商第三步完成按钮操作
     *
     * @return
     */
    public String finishUpdate() {
        try {
            MerchantInfo newMerchantInfo = new MerchantInfo();
            Long loginId = showUserinfo.getLoginId();
            newMerchantInfo.setLoginId(loginId);// 设置登录id
            showUserinfo.setLoginId(loginId);
            // 根据用户id查询商户的信息
            MerchantInfo merchant = merchantInfoService.selectByLoginId(newMerchantInfo);
            if (merchant == null) {
                logger.error("没有找到商户信息。");
                return ERROR;
            }
            // 根据商户id查询供应商信息
            SuppliersInfo supplier = merchantInfoService.selectByMerchantId(merchant.getMerchantId());
            if (supplier == null) {
                logger.error("没有找到供应商信息");
                return ERROR;
            }

            SuppliersInfo newSupplier = new SuppliersInfo();
            newSupplier.setStatus(Short.parseShort("2"));// 设置供应商的状态为2已经提交申请
            newSupplier.setSupplierId(supplier.getSupplierId());// 设置供应商id
            if (supplier.getStatus() != 3) {// 申请完成的时候，还要判断此供应商状态，如果为审核通过则不执行修改操作
                boolean haveDone = merchantInfoService.finishUpdate(newSupplier);// 修改供应商商的状态
                if (haveDone) {// 修改成功
                    getResponse().getWriter().print("1");
                } else {// 修改失败
                    getResponse().getWriter().print("0");
                }
            } else {
                getResponse().getWriter().print("2");
            }
        } catch (Exception e) {
            logger.error("申请供应商第三步完成按钮操作出现异常：" + e.getMessage(), e);
            return ERROR;
        }
        return null;
    }

    /**
     * 根据供应商id查询上传的文件个数
     *
     * @return
     */
    public String selectCount() {
        SuppliersCertificate suppliersCertificate = new SuppliersCertificate();

        try {
            if (null != supplierId) {
                suppliersCertificate.setSupplierId(supplierId);
                int haveFile = merchantInfoService.selectCount(suppliersCertificate);
                if (haveFile == 0) {// 此供应商还没有上传图片文件
                    getResponse().getWriter().print("0");
                } else {
                    getResponse().getWriter().print("1");
                }
            } else {
                getResponse().getWriter().print("0");
            }
        } catch (Exception e) {
            logger.error("根据供应商id查询上传的文件个数出现异常：" + e.getMessage(), e);
            return ERROR;
        }
        return null;
    }

    /**
     * 撤销供应商申请操作
     *
     * @return
     */
    public String applyRevocation() {
        SuppliersInfo supplier = new SuppliersInfo();
        try {
            supplier.setSupplierId(supplierId);
            supplier.setStatus(Short.parseShort("1"));
            boolean haveDone = merchantInfoService.finishUpdate(supplier);
            if (haveDone) {// 修改状态成功
                getResponse().getWriter().print("1");
            } else {// 失败
                getResponse().getWriter().print("0");
            }
        } catch (Exception e) {
            logger.error("申请撤销出现异常：" + e.getMessage(), e);
            return ERROR;
        }
        return null;
    }

    public String goBackTwo() throws IOException {
        MerchantInfo newMerchantInfo = new MerchantInfo();
        try {
            Map<Long, String> cateMap = merchantInfoService.applySupplierCategoriesMap();// 查询类目
            newMerchantInfo.setLoginId(showUserinfo.getLoginId());// 从页面获取用户id
            MerchantInfo merchant = merchantInfoService.selectByLoginId(newMerchantInfo);// 根据登录id查询商户信息
            showUserinfo = merchantInfoService.queryUserByLoginId(showUserinfo.getLoginId());// 获取用户信息
            getRequest().setAttribute("suppliersTypeMap", SuppliersTypeMap.getMap());
            getRequest().setAttribute("supplierCategorysMap", cateMap);
            if (merchant != null) {
                SuppliersInfo supplier = merchantInfoService.selectByMerchantId(merchant.getMerchantId());// 根据商户id查询供应商信息
                if (supplier != null) {
                    merchantInfo = merchant;// 获取商户信息传到页面
                    suppliersInfo = supplier;// 获取供应商信息传到页面
                    Long tempSupplierId = supplier.getSupplierId();
                    certificateList = supplierCertificateService.findCertificateListBySupplierId(tempSupplierId);
                    categorysList = merchantInfoService.showCategoriesLists(tempSupplierId);
                    suppliserCategoryList = supplierCategorysService.findSupplierCategoriesBySupplierId(tempSupplierId);
                }
            }
        } catch (Exception e) {
            logger.error("供应商申请返回第二步出现异常：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    public String goBackThree() throws IOException {
        try {
            certificateList = supplierCertificateService.findCertificateListBySupplierId(supplierId);
        } catch (Exception e) {
            logger.error("供应商申请返回第三步出现异常：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    public String goNewBackTwo() throws IOException {
        try {
            List<Categorys> list = merchantInfoService.queryCategoryList();// 查询放权所有类目
            String allCategoryJson = JSON.toJSONString(list);

            Long loginId = showUserinfo.getLoginId();
            MerchantInfo newMerchantInfo = new MerchantInfo();
            newMerchantInfo.setLoginId(loginId);// 从页面获取用户id
            MerchantInfo merchant = merchantInfoService.selectByLoginId(newMerchantInfo);// 根据登录id查询商户信息
            showUserinfo = merchantInfoService.queryUserByLoginId(loginId);// 获取用户信息
            getRequest().setAttribute("suppliersTypeMap", SuppliersTypeMap.getMap());
            getRequest().setAttribute("allSupplierCategorysJson", allCategoryJson);
            if (merchant != null) {
                SuppliersInfo supplier = merchantInfoService.selectByMerchantId(merchant.getMerchantId());// 根据商户id查询供应商信息
                if (supplier != null) {
                    merchantInfo = merchant;// 获取商户信息传到页面
                    suppliersInfo = supplier;// 获取供应商信息传到页面
                    Long tempSupplierId = supplier.getSupplierId();
                    certificateList = supplierCertificateService.findCertificateListBySupplierId(tempSupplierId);
                    categorysList = merchantInfoService.suppIdCategoriesLists(tempSupplierId);// 查询供应商勾选选择类目
                    String categoryJson = JSON.toJSONString(categorysList);
                    getRequest().setAttribute("supplierCategorysJson", categoryJson);
                }
            }
        } catch (Exception e) {
            logger.error("供应商申请返回第二步出现异常：" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    public MerchantInfoService getMerchantInfoService() {
        return merchantInfoService;
    }

    public void setMerchantInfoService(MerchantInfoService merchantInfoService) {
        this.merchantInfoService = merchantInfoService;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User getShowUserinfo() {
        return showUserinfo;
    }

    public void setShowUserinfo(User showUserinfo) {
        this.showUserinfo = showUserinfo;
    }

    public MerchantInfo getMerchantInfo() {
        return merchantInfo;
    }

    public void setMerchantInfo(MerchantInfo merchantInfo) {
        this.merchantInfo = merchantInfo;
    }

    public SuppliersInfo getSuppliersInfo() {
        return suppliersInfo;
    }

    public void setSuppliersInfo(SuppliersInfo suppliersInfo) {
        this.suppliersInfo = suppliersInfo;
    }

    public SupplierCertificateService getSupplierCertificateService() {
        return supplierCertificateService;
    }

    public void setSupplierCertificateService(SupplierCertificateService supplierCertificateService) {
        this.supplierCertificateService = supplierCertificateService;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public List<SuppliersCertificate> getCertificateList() {
        return certificateList;
    }

    public void setCertificateList(List<SuppliersCertificate> certificateList) {
        this.certificateList = certificateList;
    }

    public Long getScId() {
        return scId;
    }

    public void setScId(Long scId) {
        this.scId = scId;
    }

    public String getGoB2bPath() {
        return goB2bPath;
    }

    public void setGoB2bPath(String goB2bPath) {
        this.goB2bPath = goB2bPath;
    }

    public String getGoToCkPath() {
        return goToCkPath;
    }

    public void setGoToCkPath(String goToCkPath) {
        this.goToCkPath = goToCkPath;
    }

    public String getGoToRegisterPath() {
        return goToRegisterPath;
    }

    public void setGoToRegisterPath(String goToRegisterPath) {
        this.goToRegisterPath = goToRegisterPath;
    }

    public File getBusinessFile() {
        return businessFile;
    }

    public void setBusinessFile(File businessFile) {
        this.businessFile = businessFile;
    }

    public File getTaxRegistratFile() {
        return taxRegistratFile;
    }

    public void setTaxRegistratFile(File taxRegistratFile) {
        this.taxRegistratFile = taxRegistratFile;
    }

    public File getOrganizationFile() {
        return organizationFile;
    }

    public void setOrganizationFile(File organizationFile) {
        this.organizationFile = organizationFile;
    }

    public String getOrganizationFileName() {
        return organizationFileName;
    }

    public void setOrganizationFileName(String organizationFileName) {
        this.organizationFileName = organizationFileName;
    }

    public String getTaxRegistratFileName() {
        return taxRegistratFileName;
    }

    public void setTaxRegistratFileName(String taxRegistratFileName) {
        this.taxRegistratFileName = taxRegistratFileName;
    }

    public String getBusinessFileName() {
        return businessFileName;
    }

    public void setBusinessFileName(String businessFileName) {
        this.businessFileName = businessFileName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSupplierCategorys() {
        return supplierCategorys;
    }

    public void setSupplierCategorys(String supplierCategorys) {
        this.supplierCategorys = supplierCategorys;
    }

    public List<SuppliersAvailableCategorys> getCategorysList() {
        return categorysList;
    }

    public void setCategorysList(List<SuppliersAvailableCategorys> categorysList) {
        this.categorysList = categorysList;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public List<SuppliersAvailableCategorys> getSuppliserCategoryList() {
        return suppliserCategoryList;
    }

    public void setSuppliserCategoryList(List<SuppliersAvailableCategorys> suppliserCategoryList) {
        this.suppliserCategoryList = suppliserCategoryList;
    }

}
