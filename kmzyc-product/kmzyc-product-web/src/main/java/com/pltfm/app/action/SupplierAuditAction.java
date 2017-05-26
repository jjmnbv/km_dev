package com.pltfm.app.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.fileupload.Upload;
import com.kmzyc.commons.page.Page;
import com.kmzyc.supplier.enums.SuppliersStatus;
import com.kmzyc.supplier.model.MerchantInfo;
import com.kmzyc.supplier.model.MerchantInfoOrSuppliers;
import com.kmzyc.supplier.model.SuppliersAvailableCategorys;
import com.kmzyc.supplier.model.SuppliersCertificate;
import com.kmzyc.supplier.model.SuppliersInfo;
import com.kmzyc.supplier.model.SuppliersWarehouse;
import com.kmzyc.supplier.model.User;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.service.SupplierAuditService;
import com.pltfm.app.vobject.ProductmainTied;
import com.pltfm.app.vobject.SupplierForExport;

@Controller("supplierAuditAction")
@Scope(value = "prototype")
public class SupplierAuditAction extends BaseAction {

    @Resource
    private SupplierAuditService supplierAuditService;

    private MerchantInfo merchantOrSupplier = new MerchantInfo();

    // 查询的条件
    private MerchantInfoOrSuppliers selectSuppliersInfo = new MerchantInfoOrSuppliers();

    // 分页对象主体
    private Page page = new Page();

    private List<MerchantInfo> supplierAuditList;

    // 跳转的页数
    private int pageNum = 0;

    // 供应商信息
    private SuppliersInfo suppliersInfo;

    // 供应商上传的纸质文件
    private List<SuppliersCertificate> suppliersCertificateList;

    // 信息提示
    private String rtnMessage;

    private ProductmainTied productTied = new ProductmainTied();

    // 类目id
    private String goryIdsValues;

    private String goryIdsValues1;

    // 纸质文件id
    private Long scId;

    // 获得供应商类目集合
    private List<SuppliersAvailableCategorys> categorysList;

    // 从页面获得要修改的类目
    private String categoryValueList;

    // 不通过的理由
    private String describe;

    // 仓库id
    private Long warehouseId;

    private List<SuppliersWarehouse> suppliersWarehouseList;

    // 佣金比例
    private String CommissionRatios;

    private String sacIds;

    private SuppliersWarehouse suppliersWarehouse;

    private long fileMaximumSize = 8 * 1024 * 1024; // 上传文件的大小限制，

    private String[] allowTypes =
            {".jpg", ".jpeg", ".gif", ".bmp", ".png", ".JPG", ".JPEG", ".GIF", ".BMP", ".PNG"};

    private String realName; // 用户设置的文件的名称

    private File file;// 用户传上来的文件

    private String fileFileName; // 文件名称

    private String fileContentType; // 文件类型

    private Long supplierId;// 供应商ID

    private Long userLoginId;// 用户登录id

    private File businessFile;// 上传营业执照电子版

    private File taxRegistratFile;// 税务登记证电子版

    private File organizationFile;// 组织机构代码电子版

    private String organizationFileName;

    private String taxRegistratFileName;

    private String businessFileName;

    private String message;

    private String categoryValueList1;

    // 图片预览时的绝对路径
    private String imagePath = ConfigurationUtil.getString("supplierPreviewPath");

    private Short supplierTypes;// 供应商类型

    /**
     * 获取到所有的供应商列表
     * @return
     */
    public String findAllSuppliersForJson(){
        try {
            writeJson(supplierAuditService.supplierIdAndMerchantNameMap());
        } catch (Exception e) {
            logger.error("获取到所有的供应商列表失败，", e);
        }
        return null;
    }

    /**
     * 供应商管理-供应商审核列表-查询供应商列表
     *
     * @return
     */
    public String showSupplierAuditList() {

        if (rtnMessage != null) {
            if (rtnMessage.equalsIgnoreCase("updateSuccess")) setRtnMessage("更新供应商成功");
            if (rtnMessage.equalsIgnoreCase("updateFail")) setRtnMessage("更新供应商失败");
        }
        try {
            if (pageNum != 0) {
                page.setPageNo(pageNum);
            }
            supplierAuditService.showSupplierList(selectSuppliersInfo, page);
            setSuppliersStatusMap();
            setSuppliersTypeMap();
        } catch (Exception e) {
            logger.error("供应商管理-供应商审核列表-查询供应商列表失败" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 供应商管理-供应商审核列表-查看供应商详情信息
     */
    public String goToSupplierOrmerView() throws Exception {
        try {
            // 获得商户信息
            merchantOrSupplier = supplierAuditService.findByKey(merchantOrSupplier.getMerchantId());
            // 获取供应商信息
            suppliersInfo = supplierAuditService.findById(merchantOrSupplier.getMerchantId());
            // 获得纸质文件
            suppliersCertificateList = supplierAuditService.findBySupplierId(suppliersInfo.getSupplierId());
            setWarehouseForStatusMap();
            supplierTypes = suppliersInfo.getSupplierType();
            categorysList = supplierAuditService.showCategoriesLists(suppliersInfo.getSupplierId());
        } catch (Exception e) {
            logger.error("供应商管理-供应商审核列表-查看供应商详情信息失败" + e.getMessage(), e);
        }
        return SUCCESS;
    }

    /**
     * 供应商管理-供应商审核列表-查看供应商详情信息-审核通过供应商保存数据
     *
     * @return
     * @throws Exception
     */
    public String updateSupplierStatus() {
        try {
            SuppliersInfo info = new SuppliersInfo();
            if (supplierTypes.toString().equals("2")) {// 入驻
                info.setStatus(Short.parseShort(SuppliersStatus.NOTCONFIRM.getStatus().toString()));// 商家待确认
            } else if (supplierTypes.toString().equals("3")) {// 代销
                info.setStatus(Short.parseShort(SuppliersStatus.AUDIT.getStatus().toString()));// 审核通过
            }
            info.setSupplierId(suppliersInfo.getSupplierId());
            info.setSupplierType(supplierTypes);
            boolean flg = supplierAuditService.updateSupplierStatus(info, goryIdsValues, warehouseId,
                            CommissionRatios, userLoginId, sacIds, goryIdsValues1);
            if (flg) {// 审核成功
                getResponse().getWriter().print("1");
            } else {// 审核失败
                getResponse().getWriter().print("0");
            }
        } catch (Exception e) {
            logger.error("供应商管理-供应商审核列表-查看供应商详情信息-审核通过供应商保存数据失败" + e.getMessage(), e);
            return null;
        }
        return null;
    }

    /**
     * 供应商管理-供应商审核列表-查看供应商详情信息-审核不通过
     *
     * @return
     * @throws Exception
     */
    public String notPass() throws Exception {
        try {
            SuppliersInfo info = new SuppliersInfo();
            info.setStatus(Short.parseShort("4"));
            info.setSupplierId(suppliersInfo.getSupplierId());
            HttpServletRequest request = ServletActionContext.getRequest();
            String newDescribe = request.getParameter("describe"); // 获取页面上的用户名
            newDescribe = URLDecoder.decode(newDescribe, "UTF-8"); // 进行解码
            info.setDescribe(newDescribe);
            boolean flg = supplierAuditService.notPass(info);
            if (flg) {// 审核成功
                getResponse().getWriter().print("1");
            } else {
                getResponse().getWriter().print("0");
            }
        } catch (Exception e) {
            logger.error("供应商管理-供应商审核列表-查看供应商详情信息-审核不通过失败" + e.getMessage(), e);
            return null;
        }
        return null;
    }

    /**
     * 供应商管理-供应商审核列表-修改供应商信息
     *
     * @return
     */
    public String gotoSupplierUpdate() {
        try {
            // 获得商户信息
            merchantId = merchantOrSupplier.getMerchantId();
            merchantOrSupplier = supplierAuditService.findByKey(merchantId);
            // 获取供应商信息
            suppliersInfo = supplierAuditService.findById(merchantId);
            if (suppliersInfo == null) {
                logger.error("没有找到此供应商，商户id={}。", merchantId);
                return ERROR;
            }
            // 获得纸质文件
            supplierId = suppliersInfo.getSupplierId();
            suppliersCertificateList = supplierAuditService.findBySupplierId(supplierId);
            setWarehouseForStatusMap();

            // 供应商类型
            suppliersWarehouseList = supplierAuditService.selectBySupplierId(supplierId);
            if (suppliersWarehouseList.size() > 0) {
                suppliersWarehouse = suppliersWarehouseList.get(0);
            }
            categorysList = supplierAuditService.showCategoriesLists(supplierId);
        } catch (Exception e) {
            logger.error("供应商管理-供应商审核列表-修改供应商信息失败" + e.getMessage(), e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 校验图片、判断是否图片需要更新
     *
     * @param file
     * @param fileType
     * @param fileTypeName
     * @param longinId
     * @return Map<String,File>
     * @throws IOException
     * @throws SQLException
     */
    public Map<String, File> updateImg(File file, String fileType, String fileTypeName, Long longinId) throws IOException, SQLException {
        String root1 = "";
        String delFile = "";
        merchantInfo.setLoginId(longinId);
        MerchantInfo mer;
        mer = supplierAuditService.findLoginId(merchantInfo);
        String root = ConfigurationUtil.getString("supplierCertificatePath");
        if (null != file) {
            /** 若文件为空 则 报错 **/
            // 文件后缀名
            int index = StringUtils.lastIndexOf(fileTypeName, '.');
            if (index == -1) {
                throw new IOException("图片名称出现异常");
            }
            // 获取文件后缀名
            String extFileName = StringUtils.substring(fileTypeName, index);
            /*if (!Upload.isValid(extFileName, allowTypes)) {
            }*/
            if (fileType.equals("organizationFile")) {
                if (this.getOrganizationFile().length() > fileMaximumSize) {
                    throw new IOException("图片大小超出限定8M");
                }
            } else if (fileType.equals("businessFile")) {
                if (this.getBusinessFile().length() > fileMaximumSize) {
                    throw new IOException("图片大小超出限定8M");
                }
            } else if (fileType.equals("organizationFile")) {
                if (this.getTaxRegistratFile().length() > fileMaximumSize) {
                    throw new IOException("图片大小超出限定8M");
                }
            }
            // 使用当前时间戳，避免文件重复被覆盖
            long time = System.currentTimeMillis();
            String name = time + "";
            String filename = name + extFileName.toLowerCase();
            // TODO 文件保存的位置和名称
            // 创建供应商ID为名称的文件夹
            String filePath =
                    File.separatorChar + "certificate" + File.separatorChar + File.separatorChar;// 保存到数据库的
            String saveFilePath =
                    root + File.separatorChar + "certificate" + File.separatorChar + File.separatorChar;// 上传到服务器的
            File dirFile = new File(saveFilePath);
            if (!dirFile.exists()) {
                dirFile.mkdir();
            }
            // TODO 文件保存的位置和名称
            String savePath = filePath + filename;
            root1 = ServletActionContext.getServletContext().getRealPath(String.valueOf(File.separatorChar));
            if (fileType.equals("organizationFile")) {//组织机构代码
                merchantOrSupplier.setOrganizationUrl(savePath);
                delFile = root1 + mer.getOrganizationUrl();
                ;
            } else if (fileType.equals("businessFile")) {//营业执照
                merchantOrSupplier.setUploadBusinessLicencePictur(savePath);
                delFile = root1 + mer.getTaxRegCertificateCopy();
            } else if (fileType.equals("taxRegistratFile")) {//税务登记证
                merchantOrSupplier.setTaxRegCertificateCopy(savePath);
                delFile = root1 + mer.getTaxRegCertificateCopy();
            }
            Map<String, File> mapFile = new HashMap<String, File>();
            File destFile = new File(saveFilePath, filename);
//      FileUtils.copyFile(file, destFile);
//      // 删除临时文件
//      file.delete();
            File f = new File(delFile); // 输入要删除的文件位置
            mapFile.put("destFile", destFile);
            mapFile.put("delFile", f);
            return mapFile;
        } else {
            return null;
        }
    }

    /**
     * 供应商管理-供应商审核列表-修改已通过供应商信息-供应商保存数据
     *
     * @return
     */
    public String supplierUpdate() {
        try {
            Map<String, File> delOrganizationFile = new HashMap<String, File>();
            Map<String, File> delBusinessFile = new HashMap<String, File>();
            Map<String, File> delTaxRegistratFile = new HashMap<String, File>();
            if (null != organizationFile) {
                delOrganizationFile = updateImg(organizationFile, "organizationFile", organizationFileName, longinId);
            }
            if (null != businessFile) {
                delBusinessFile = updateImg(businessFile, "businessFile", businessFileName, longinId);
            }
            if (null != taxRegistratFile) {
                delTaxRegistratFile = updateImg(taxRegistratFile, "taxRegistratFile", taxRegistratFileName, longinId);
            }

            if (suppliersInfo.getSupplierType() == 3) {
                suppliersInfo.setLoginStatus(Short.valueOf("1"));
            } else {
                suppliersInfo.setLoginStatus(Short.valueOf("2"));
            }
            supplierAuditService.supplierUpdate(merchantOrSupplier, suppliersInfo,
                    categoryValueList, suppliersWarehouse, CommissionRatios, sacIds, categoryValueList1);
            //修改成功后再更新
            if (null != organizationFile) {
                //上传文件
                FileUtils.copyFile(organizationFile, delOrganizationFile.get("destFile"));
                // 删除原文件
                if (delOrganizationFile.get("delFile").exists()) {
                    organizationFile.delete();
                }
            }
            if (null != businessFile) {
                FileUtils.copyFile(businessFile, delBusinessFile.get("destFile"));
                if (delBusinessFile.get("delFile").exists()) {
                    businessFile.delete();
                }
            }
            if (null != taxRegistratFile) {
                FileUtils.copyFile(taxRegistratFile, delTaxRegistratFile.get("destFile"));
                if (delTaxRegistratFile.get("delFile").exists()) {
                    taxRegistratFile.delete();
                }
            }
            setRtnMessage("updateSuccess");// 修改成功
            return showSupplierAuditList();
        } catch (IOException e) {
            logger.error("供应商管理-供应商审核列表-修改供应商信息失败-供应商保存图片文件异常" + e.getMessage(), e);
            setRtnMessage("updateFail");// 修改失败
            return showSupplierAuditList();
        } catch (SQLException e) {
            logger.error("供应商管理-供应商审核列表-修改供应商信息失败-供应商保存根据用户id查询商户信息异常" + e.getMessage(), e);
            setRtnMessage("updateFail");// 修改失败
            return showSupplierAuditList();
        } catch (Exception e) {
            logger.error("供应商管理-供应商审核列表-修改供应商信息失败-供应商保存数据失败" + e.getMessage(), e);
            setRtnMessage("updateFail");// 修改失败
            return showSupplierAuditList();
        }
    }

    /**
     * 供应商管理-供应商审核列表-修改供应商信息-上传资质文件
     *
     * @return
     */
    public String uploadCeriticate() {
        String errorMessage = null;
        String savePath = null;
        SuppliersCertificate newSuppliersCertificate = null;
        Map jsonMap = new HashMap();
        if (this.getFile() == null) {
            errorMessage = "上传的文件为空!";
            logger.error(errorMessage);
            // returnResult = new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
            // errorMessage, null);
            jsonMap.put("flg", false);
            jsonMap.put("message", errorMessage);
            writeJson(jsonMap);
            return null;
        }
        // 文件后缀名
        int index = StringUtils.lastIndexOf(fileFileName, '.');
        if (index < 0) {
            errorMessage = "上传的文件类型不允许!";
            // logger.error(errorMessage);
            jsonMap.put("flg", false);
            jsonMap.put("message", errorMessage);
            // returnResult = new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
            // errorMessage, null);
            writeJson(jsonMap);
            return null;
        }
        // 获取文件后缀名
        String extFileName = StringUtils.substring(fileFileName, index);
        if (!Upload.isValid(extFileName, allowTypes)) {
            errorMessage = "上传的文件类型不允许!";
            // logger.error(errorMessage);
            // returnResult = new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
            // errorMessage, null);
            jsonMap.put("flg", false);
            jsonMap.put("message", errorMessage);
            writeJson(jsonMap);
            return null;
        }
        if (this.getFile().length() > fileMaximumSize) {
            errorMessage = "图片文件大小超过8M";
            // logger.error(errorMessage);
            // returnResult = new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
            // errorMessage, null);
            jsonMap.put("flg", false);
            jsonMap.put("message", errorMessage);
            writeJson(jsonMap);
            return null;
        }
        //Map<String, Object> resultDate = new HashMap<String, Object>();
        // System.out.println("开始保存上传的资质文件,类型为:" + fileContentType);

        // TODO 定义上传位置
        String root = ConfigurationUtil.getString("supplierCertificatePath");
        // String root = ServletActionContext.getServletContext().getRealPath("/uploadFile");

        // 分隔符
        // String separator = java.io.File.separator;
        // 使用当前时间戳，避免文件重复被覆盖
        long time = System.currentTimeMillis();
        String name = DateFormatUtils.format(new Date(), "yyyyMMdd_" + time + "_" + supplierId);
        String filename = name + extFileName.toLowerCase();
        // 创建供应商ID为名称的文件夹
        String filePath =
                File.separatorChar + "certificate" + File.separatorChar + supplierId + File.separatorChar;// 保存到数据库的
        String saveFilePath =
                root + File.separatorChar + "certificate" + File.separatorChar + supplierId
                        + File.separatorChar;// 上传到服务器的
        File dirFile = new File(saveFilePath);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }

        try {
            // TODO 文件保存的位置和名称
            savePath = filePath + filename;
            // System.out.println(savePath);
            SuppliersCertificate suppliersCertificate = new SuppliersCertificate();
            suppliersCertificate.setSupplierId(supplierId);// 供应商ID
            // suppliersCertificate.setDocType("GMP");
            // suppliersCertificate.setDocCode("S20001");// TODO 文件编号(生成文件的规则)
            suppliersCertificate.setFilePath(savePath);// 文件路径
            suppliersCertificate.setFileName(realName);// 文件名称
            // 保存到数据库
            supplierAuditService.saveSupplierCertificate(suppliersCertificate);
            File destFile = new File(saveFilePath, filename);
            FileUtils.copyFile(file, destFile);
            // 删除临时文件
            file.delete();
            // 查询已经上传的资质文件
            // suppliersCertificateList = supplierAuditService.findBySupplierId(supplierId);
            newSuppliersCertificate = supplierAuditService.findBySuppIdPath(savePath);
        } catch (Exception e) {
            logger.error("上传资质文件出错," + e.getMessage());
            jsonMap.put("flg", true);
            jsonMap.put("message", "上传资质文件出错");
            writeJson(jsonMap);
            return null;
        }
        // returnResult = new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS
        // ,"成功",resultDate);
        jsonMap.put("flg", true);
        jsonMap.put("savePath", newSuppliersCertificate.getFilePath());
        jsonMap.put("scId", newSuppliersCertificate.getScId());
        jsonMap.put("fileName", newSuppliersCertificate.getFileName());
        writeJson(jsonMap);
        return null;
    }

    /**
     * 供应商管理-供应商审核列表-修改未通过供应商信息-供应商保存数据
     *
     * @return
     */
    public String supplierUpdateNoPass() {
        try {
            suppliersInfo.setStatus(Short.parseShort("2"));// 当审核不通过时的修改要改变供应商商的状态为申请状态
            supplierUpdate();
            Map<String, File> delOrganizationFile = new HashMap<String, File>();
            Map<String, File> delBusinessFile = new HashMap<String, File>();
            Map<String, File> delTaxRegistratFile = new HashMap<String, File>();
            if (null != organizationFile) {
                delOrganizationFile = updateImg(organizationFile, "organizationFile", organizationFileName, longinId);
            }
            if (null != businessFile) {
                delBusinessFile = updateImg(businessFile, "businessFile", businessFileName, longinId);
            }
            if (null != taxRegistratFile) {
                delTaxRegistratFile = updateImg(taxRegistratFile, "taxRegistratFile", taxRegistratFileName, longinId);
            }
            if (suppliersInfo.getSupplierType() == 3) {
                suppliersInfo.setLoginStatus(Short.valueOf("1"));
            } else {
                suppliersInfo.setLoginStatus(Short.valueOf("2"));
            }
            supplierAuditService.supplierUpdate(merchantOrSupplier, suppliersInfo,
                    categoryValueList, suppliersWarehouse, CommissionRatios, sacIds, categoryValueList1);
            //修改成功后再更新
            if (null != organizationFile) {
                //上传文件
                FileUtils.copyFile(organizationFile, delOrganizationFile.get("destFile"));
                // 删除原文件
                if (delOrganizationFile.get("delFile").exists()) {
                    organizationFile.delete();
                }
            }
            if (null != businessFile) {
                FileUtils.copyFile(businessFile, delBusinessFile.get("destFile"));
                if (delBusinessFile.get("delFile").exists()) {
                    businessFile.delete();
                }
            }
            if (null != taxRegistratFile) {
                FileUtils.copyFile(taxRegistratFile, delTaxRegistratFile.get("destFile"));
                if (delTaxRegistratFile.get("delFile").exists()) {
                    taxRegistratFile.delete();
                }
            }
            setRtnMessage("updateSuccess");// 修改成功
            return showSupplierAuditList();
        } catch (IOException e) {
            logger.error("供应商管理-供应商审核列表-修改供应商信息失败-供应商保存图片文件异常" + e.getMessage(), e);
            setRtnMessage("updateFail");// 修改失败
            return showSupplierAuditList();
        } catch (SQLException e) {
            logger.error("供应商管理-供应商审核列表-修改供应商信息失败-供应商保存根据用户id查询商户信息异常" + e.getMessage(), e);
            setRtnMessage("updateFail");// 修改失败
            return showSupplierAuditList();
        } catch (Exception e) {
            logger.error("供应商管理-供应商审核列表-修改供应商信息失败-供应商保存数据失败" + e.getMessage(), e);
            setRtnMessage("updateFail");// 修改失败
            return showSupplierAuditList();
        }
    }

    /**
     * 供应商管理-供应商审核列表-禁止、开启登陆
     *
     * @return
     */
    public String updateMerchanStatus() {
        SuppliersInfo supp = new SuppliersInfo();
        try {
            supp.setSupplierId(supplierId);
            if (null != updateMerchanType) {// 如果修改类型不为空就是开启操作否则是关闭
                supp.setLoginStatus(Short.parseShort("2"));// 开启
            } else {
                supp.setLoginStatus(Short.parseShort("1"));// 关闭
            }
            boolean flg =
                    supplierAuditService.updateLoginStatus(Short.valueOf("0"), Short.valueOf("0"), supp
                            .getLoginStatus(), supp.getSupplierId());
            if (flg) {// 表示修改成功
                getResponse().getWriter().print("1");
            } else {// 失败
                getResponse().getWriter().print("0");
            }
        } catch (Exception e) {
            logger.error("供应商管理-供应商审核列表-禁止、开启登陆失败" + e.getMessage());
        }
        return null;
    }

    /**
     * 供应商管理-供应商审核列表-禁止、开启业务操作
     *
     * @return
     */
    public String updateBusinessStatus() {
        SuppliersInfo supp = new SuppliersInfo();
        try {
            supp.setSupplierId(supplierId);
            if (null != updateMerchanType) {// 如果修改类型不为空就是开启操作否则是关闭
                supp.setBusinessStatus(Short.parseShort("2"));// 开启业务
            } else {
                supp.setBusinessStatus(Short.parseShort("1"));// 关闭业务
            }
            boolean flg =
                    supplierAuditService.updateLoginStatus(supp.getBusinessStatus(), Short.valueOf("0"),
                            Short.valueOf("0"), supp.getSupplierId());
            if (flg) {// 表示修改成功
                getResponse().getWriter().print("1");
            } else {// 失败
                getResponse().getWriter().print("0");
            }
        } catch (Exception e) {
            logger.error("供应商管理-供应商审核列表-禁止、开启业务操作失败" + e.getMessage());
        }
        return null;
    }

    /**
     * 供应商管理-供应商审核列表-关闭、开启店铺
     *
     * @return
     */
    public String updateCloseStatus() {
        SuppliersInfo supp = new SuppliersInfo();
        try {
            supp.setSupplierId(supplierId);
            if (null != updateMerchanType) {// 如果修改类型不为空就是开启操作否则是关闭
                supp.setCloseStatus(Short.parseShort("2"));// 开启
            } else {
                supp.setCloseStatus(Short.parseShort("1"));// 关闭
            }
            boolean flg = supplierAuditService.updateLoginStatus(Short.valueOf("0"), supp.getCloseStatus(), Short
                            .valueOf("0"), supp.getSupplierId());
            if (flg) {// 表示修改成功
                getResponse().getWriter().print("1");
            } else {// 失败
                getResponse().getWriter().print("0");
            }
        } catch (Exception e) {
            logger.error("供应商管理-供应商审核列表-关闭、开启店铺失败" + e.getMessage());
        }

        return null;

    }

    /**
     * 删除资质文件
     */
    public String deleteFile() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        String scIdTemp = request.getParameter("scId");
        if (StringUtils.isNotEmpty(scIdTemp)) {
            scId = Long.valueOf(scIdTemp);
        }
        boolean flg = false;
        try {
            SuppliersCertificate suppliersCertificate = supplierAuditService.findCertificateByScId(scId);
            if (null != suppliersCertificate) {
                flg = supplierAuditService.deleteSupplierCertificate(scId);
                // 删除上传到服务器上的图片
                String root = ServletActionContext.getServletContext().getRealPath("/");
                String delFile = root + suppliersCertificate.getFilePath();
                // System.out.println(delFile);
                File f = new File(delFile); // 输入要删除的文件位置
                if (f.exists()) {
                    f.delete();
                }
            }
        } catch (Exception e) {
            logger.error("删除资质文件出错," + e.getMessage());
            return ERROR;
        }
        if (flg == true) {
            getResponse().getWriter().print("0");
        } else {
            getResponse().getWriter().print("1");
        }
        return null;
    }

    private String userName;// 用户名称

    private MerchantInfo merchantInfo = new MerchantInfo();// 保存商户信息

    private SuppliersInfo suppliersInfoAdd = new SuppliersInfo();// 保存供应商信息

    private Long longinId;// 用户登录id

    private String goB2bRegisterPath = ConfigurationUtil.getString("goB2bRegisterPath");

    private Long merchantId;// 商户id

    private String updateMerchanType;// 判断修改类型

    private String supplierCategorys;// 保存页面传来的类目id

    /**
     * 去添加供应商页面
     *
     * @return
     */
    public String addSupplierShow() {
        return SUCCESS;
    }

    /**
     * 验证用户
     *
     * @return
     */
    public String verifyUser() {
        try {
            String newUserName = getRequest().getParameter("userName"); // 获取页面上的用户名
            newUserName = URLDecoder.decode(newUserName, "UTF-8"); // 进行解码
            User user = supplierAuditService.findByUserNameObj(newUserName);// 根据用户名查询用户信息
            if (user == null) {// 如果为空说明用户不存在
                strWriteJson("0");
                return null;
            }

            MerchantInfo merchantInfo = new MerchantInfo();
            merchantInfo.setLoginId(user.getLoginId());
            merchantInfo = supplierAuditService.findByUserId(merchantInfo);// 根据用户id查询商户信息
            if (merchantInfo == null) {//没有商户信息
                strWriteJson("2");
                return null;
            }

            SuppliersInfo suppliersInfo = supplierAuditService.obtainSupplierId(user.getLoginId());
            if (suppliersInfo != null) {// 此用户已经申请了供应商
                strWriteJson("1");
            } else {
                strWriteJson("2");
            }
        } catch (Exception e) {
            logger.error("添加供应商第一步出错," + e.getMessage());
        }
        return null;
    }

    /**
     * 供应商审核管理-添加供应商
     *
     * @return
     */
    public String goSupplierAdd() {
        try {
            Map<Long, String> cateMapAll =
                    supplierAuditService.applySupplierCategoriesMap(Long.parseLong("0"));
            getRequest().setAttribute("supplierCategorysMapAll", cateMapAll);
            categorysList = supplierAuditService.showAllCategoriesLists();
            // getRequest().setAttribute("supplierRegisterMoneyMap",supplierRegisterMoneyMap.getMap());
            // 供应商类型
            //setSuppliersTypeMap();
            User user = supplierAuditService.findByUserNameObj(userName);// 根据用户名查询用户信息
            longinId = user.getLoginId();
        } catch (Exception e) {
            logger.error("供应商审核管理-添加供应商-用户名查询用户信息出现异常," + e.getMessage());
        }

        return SUCCESS;
    }

    public String supplierAdd() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        java.util.Date nowTime = null;
        boolean flg;
        try {
            String root = ConfigurationUtil.getString("supplierCertificatePath");
            nowTime = sdf.parse(sdf.format(new Date()));// 获取当前系统时间
            merchantInfo.setCreateDate(nowTime);
            merchantInfo.setLoginId(longinId);// 设置登录id
            suppliersInfoAdd.setStatus(Short.parseShort("2"));// 设置供应商状态为待申请


            if (null != organizationFile) {

                /** 若文件为空 则 报错 **/
                // System.out.println("开始保存上传的资质文件,类型为:"+fileContentType);
                // 文件后缀名
                int index = StringUtils.lastIndexOf(organizationFileName, '.');
                if (index == -1) {
                    return ERROR;
                }
                // 获取文件后缀名
                String extFileName = StringUtils.substring(organizationFileName, index);
                if (!Upload.isValid(extFileName, allowTypes)) {
                    return ERROR;
                }
                if (this.getOrganizationFile().length() > fileMaximumSize) {
                    return ERROR;
                }
                // 使用当前时间戳，避免文件重复被覆盖
                long time = System.currentTimeMillis();
                String name = time + "";
                String filename = name + extFileName.toLowerCase();
                // TODO 文件保存的位置和名称
                // 创建供应商ID为名称的文件夹
                String filePath =
                        File.separatorChar + "certificate" + File.separatorChar + File.separatorChar;// 保存到数据库的
                String saveFilePath =
                        root + File.separatorChar + "certificate" + File.separatorChar + File.separatorChar;// 上传到服务器的
                // String filePath = "/uploadFile/" + filename;
                File dirFile = new File(saveFilePath);
                if (!dirFile.exists()) {
                    dirFile.mkdir();
                }
                // TODO 文件保存的位置和名称
                String savePath = filePath + filename;
                merchantInfo.setOrganizationUrl(savePath);// 保存到数据库
                File destFile = new File(saveFilePath, filename);
                FileUtils.copyFile(organizationFile, destFile);
                // 删除临时文件
                organizationFile.delete();
            }
            if (null != businessFile) {

                /** 若文件为空 则 报错 **/
                // System.out.println("开始保存上传的资质文件,类型为:"+fileContentType);
                // 文件后缀名
                int index = StringUtils.lastIndexOf(businessFileName, '.');
                if (index == -1) {
                    return ERROR;
                }
                // 获取文件后缀名
                String extFileName = StringUtils.substring(businessFileName, index);
                if (!Upload.isValid(extFileName, allowTypes)) {
                    return ERROR;
                }
                if (this.getBusinessFile().length() > fileMaximumSize) {
                    return ERROR;
                }
                // 使用当前时间戳，避免文件重复被覆盖
                long time = System.currentTimeMillis();
                String name = time + "";
                String filename = name + extFileName.toLowerCase();
                // TODO 文件保存的位置和名称
                // 创建供应商ID为名称的文件夹
                String filePath =
                        File.separatorChar + "certificate" + File.separatorChar + File.separatorChar;// 保存到数据库的
                String saveFilePath =
                        root + File.separatorChar + "certificate" + File.separatorChar + File.separatorChar;// 上传到服务器的
                // String filePath = "/uploadFile/" + filename;
                File dirFile = new File(saveFilePath);
                if (!dirFile.exists()) {
                    dirFile.mkdir();
                }
                // TODO 文件保存的位置和名称
                String savePath = filePath + filename;
                merchantInfo.setUploadBusinessLicencePictur(savePath);// 保存到数据库
                File destFile = new File(saveFilePath, filename);
                FileUtils.copyFile(businessFile, destFile);
                // 删除临时文件
                businessFile.delete();
            }
            if (null != taxRegistratFile) {

                /** 若文件为空 则 报错 **/
                // System.out.println("开始保存上传的资质文件,类型为:"+fileContentType);
                // 文件后缀名
                int index = StringUtils.lastIndexOf(taxRegistratFileName, '.');
                if (index == -1) {
                    return ERROR;
                }
                // 获取文件后缀名
                String extFileName = StringUtils.substring(taxRegistratFileName, index);
                if (!Upload.isValid(extFileName, allowTypes)) {
                    return ERROR;
                }
                if (this.getTaxRegistratFile().length() > fileMaximumSize) {
                    return ERROR;
                }
                // 使用当前时间戳，避免文件重复被覆盖
                long time = System.currentTimeMillis();
                String name = time + "";
                String filename = name + extFileName.toLowerCase();
                // TODO 文件保存的位置和名称
                // 创建供应商ID为名称的文件夹
                String filePath =
                        File.separatorChar + "certificate" + File.separatorChar + File.separatorChar;// 保存到数据库的
                String saveFilePath =
                        root + File.separatorChar + "certificate" + File.separatorChar + File.separatorChar;// 上传到服务器的
                // String filePath = "/uploadFile/" + filename;
                File dirFile = new File(saveFilePath);
                if (!dirFile.exists()) {
                    dirFile.mkdir();
                }
                // TODO 文件保存的位置和名称
                String savePath = filePath + filename;
                merchantInfo.setTaxRegCertificateCopy(savePath);// 保存到数据库
                File destFile = new File(saveFilePath, filename);
                FileUtils.copyFile(taxRegistratFile, destFile);
                // 删除临时文件
                taxRegistratFile.delete();
            }
            if (suppliersInfoAdd.getSupplierType() == 3) {// 添加的供应商类型为代销登录状态默认为禁止
                suppliersInfoAdd.setLoginStatus(Short.valueOf("1"));
            } else {
                suppliersInfoAdd.setLoginStatus(Short.valueOf("2"));
            }
            suppliersInfoAdd.setBusinessStatus(Short.valueOf("2"));
            suppliersInfoAdd.setCloseStatus(Short.valueOf("2"));
            suppliersInfoAdd.setShopBrowseStatus(Short.valueOf("1"));// 供应商店铺浏览量启用状态
            supplierAuditService.supplierAddTwo(merchantInfo, suppliersInfoAdd, supplierCategorys);// 向商户和供应商表插入
            suppliersInfo = supplierAuditService.obtainSupplierId(longinId);
        } catch (Exception e) {
            logger.error("添加供应商出错," + e.getMessage());
            return ERROR;
        }

        return SUCCESS;
    }


    private String corporateName;

    /**
     * 检查是否有存在一样的公司名字
     *
     * @return
     */
    public String ckCorporateName() {
        try {
            // String sa = merchantInfo.getCorporateName();
            String prName = corporateName;
            MerchantInfo mer = new MerchantInfo();
            mer.setCorporateName(prName.replaceAll(" ", ""));
            if (null != merchantId) {
                mer.setMerchantId(merchantId);
            }
            boolean bl = supplierAuditService.selectByCompanyName(mer);
            if (bl) {// 公司名称已经存在
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

    // 导出供应商审核列表
    public String exportSupplierAuditList() {
        FileInputStream f = null;
        ByteArrayInputStream bais = null;
        try {
            List<SupplierForExport> supplierList = supplierAuditService.getSupplierListForExcel(selectSuppliersInfo);
            Integer exportType = 0;
            supplierAuditService.exportExcelForSupplierAuditList(supplierList, exportType);

            File file = new File(ConfigurationUtil.getString("exportExcelPath") + File.separator + "supplierAuditList.xls");
            f = new FileInputStream(file);
            byte[] fb = new byte[f.available()];
            f.read(fb);
            ServletActionContext.getResponse().setHeader("Content-disposition",
                    "attachment; filename=" + new String("供应商审核列表.xls".getBytes("gb2312"), "iso8859-1"));
            bais = new ByteArrayInputStream(fb);
            int b;
            while ((b = bais.read()) != -1) {
                ServletActionContext.getResponse().getOutputStream().write(b);
            }
            ServletActionContext.getResponse().getOutputStream().flush();

            f.close();
            bais.close();
        } catch (Exception e) {
            logger.error("导出供应商审核列表异常" + e.getMessage(), e);
            return ERROR;
        } finally {
            try {
                if (f != null) f.close();
                if (bais != null) bais.close();
            } catch (IOException e) {
                logger.error("导出供应商审核列表关闭流文件流异常" + e.getMessage(), e);
            }
        }
        return null;
    }


    public String supplierForCause() {
        return SUCCESS;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    public Page getPage() {
        return page;
    }

    @Override
    public void setPage(Page page) {
        this.page = page;
    }

    public SuppliersInfo getSuppliersInfo() {
        return suppliersInfo;
    }

    public void setSuppliersInfo(SuppliersInfo suppliersInfo) {
        this.suppliersInfo = suppliersInfo;
    }

    public List<SuppliersCertificate> getSuppliersCertificateList() {
        return suppliersCertificateList;
    }

    public void setSuppliersCertificateList(List<SuppliersCertificate> suppliersCertificateList) {
        this.suppliersCertificateList = suppliersCertificateList;
    }

    public String getRtnMessage() {
        return rtnMessage;
    }

    public void setRtnMessage(String rtnMessage) {
        this.rtnMessage = rtnMessage;
    }

    public MerchantInfo getMerchantOrSupplier() {
        return merchantOrSupplier;
    }

    public void setMerchantOrSupplier(MerchantInfo merchantOrSupplier) {
        this.merchantOrSupplier = merchantOrSupplier;
    }

    public List<MerchantInfo> getSupplierAuditList() {
        return supplierAuditList;
    }

    public void setSupplierAuditList(List<MerchantInfo> supplierAuditList) {
        this.supplierAuditList = supplierAuditList;
    }

    public MerchantInfoOrSuppliers getSelectSuppliersInfo() {
        return selectSuppliersInfo;
    }

    public void setSelectSuppliersInfo(MerchantInfoOrSuppliers selectSuppliersInfo) {
        this.selectSuppliersInfo = selectSuppliersInfo;
    }

    public ProductmainTied getProductTied() {
        return productTied;
    }

    public void setProductTied(ProductmainTied productTied) {
        this.productTied = productTied;
    }

    public Long getScId() {
        return scId;
    }

    public void setScId(Long scId) {
        this.scId = scId;
    }

    public String getGoryIdsValues() {
        return goryIdsValues;
    }

    public void setGoryIdsValues(String goryIdsValues) {
        this.goryIdsValues = goryIdsValues;
    }

    public List<SuppliersAvailableCategorys> getCategorysList() {
        return categorysList;
    }

    public void setCategorysList(List<SuppliersAvailableCategorys> categorysList) {
        this.categorysList = categorysList;
    }

    public String getCategoryValueList() {
        return categoryValueList;
    }

    public void setCategoryValueList(String categoryValueList) {
        this.categoryValueList = categoryValueList;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getCommissionRatios() {
        return CommissionRatios;
    }

    public void setCommissionRatios(String commissionRatios) {
        CommissionRatios = commissionRatios;
    }

    public String getSacIds() {
        return sacIds;
    }

    public void setSacIds(String sacIds) {
        this.sacIds = sacIds;
    }

    public List<SuppliersWarehouse> getSuppliersWarehouseList() {
        return suppliersWarehouseList;
    }

    public void setSuppliersWarehouseList(List<SuppliersWarehouse> suppliersWarehouseList) {
        this.suppliersWarehouseList = suppliersWarehouseList;
    }

    public SuppliersWarehouse getSuppliersWarehouse() {
        return suppliersWarehouse;
    }

    public void setSuppliersWarehouse(SuppliersWarehouse suppliersWarehouse) {
        this.suppliersWarehouse = suppliersWarehouse;
    }

    public long getFileMaximumSize() {
        return fileMaximumSize;
    }

    public void setFileMaximumSize(long fileMaximumSize) {
        this.fileMaximumSize = fileMaximumSize;
    }

    public String[] getAllowTypes() {
        return allowTypes;
    }

    public void setAllowTypes(String[] allowTypes) {
        this.allowTypes = allowTypes;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Long getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(Long userLoginId) {
        this.userLoginId = userLoginId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public MerchantInfo getMerchantInfo() {
        return merchantInfo;
    }

    public void setMerchantInfo(MerchantInfo merchantInfo) {
        this.merchantInfo = merchantInfo;
    }

    public SuppliersInfo getSuppliersInfoAdd() {
        return suppliersInfoAdd;
    }

    public void setSuppliersInfoAdd(SuppliersInfo suppliersInfoAdd) {
        this.suppliersInfoAdd = suppliersInfoAdd;
    }

    public Long getLonginId() {
        return longinId;
    }

    public void setLonginId(Long longinId) {
        this.longinId = longinId;
    }

    public String getGoB2bRegisterPath() {
        return goB2bRegisterPath;
    }

    public void setGoB2bRegisterPath(String goB2bRegisterPath) {
        this.goB2bRegisterPath = goB2bRegisterPath;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getUpdateMerchanType() {
        return updateMerchanType;
    }

    public void setUpdateMerchanType(String updateMerchanType) {
        this.updateMerchanType = updateMerchanType;
    }

    public Short getSupplierTypes() {
        return supplierTypes;
    }

    public void setSupplierTypes(Short supplierTypes) {
        this.supplierTypes = supplierTypes;
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

    public String getCategoryValueList1() {
        return categoryValueList1;
    }

    public void setCategoryValueList1(String categoryValueList1) {
        this.categoryValueList1 = categoryValueList1;
    }

    public String getGoryIdsValues1() {
        return goryIdsValues1;
    }

    public void setGoryIdsValues1(String goryIdsValues1) {
        this.goryIdsValues1 = goryIdsValues1;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public SupplierAuditService getSupplierAuditService() {
        return supplierAuditService;
    }

    public void setSupplierAuditService(SupplierAuditService supplierAuditService) {
        this.supplierAuditService = supplierAuditService;
    }

}
