package com.kmzyc.supplier.action;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.fileupload.Upload;
import com.kmzyc.supplier.model.MerchantInfo;
import com.kmzyc.supplier.model.NewsCategory;
import com.kmzyc.supplier.model.SuppliersCertificate;
import com.kmzyc.supplier.service.MerchantInfoService;
import com.kmzyc.supplier.service.SupplierCertificateService;
import com.kmzyc.supplier.vo.ReturnResult;
import com.kmzyc.zkconfig.ConfigurationUtil;

/**
 * 供应商资质文件action
 *
 * @author luoyi
 * @createDate 2013/12/30
 */
@Controller("supplierCertificateAction")
@Scope("prototype")
public class SupplierCertificateAction extends SupplierBaseAction {

    private Logger logger = LoggerFactory.getLogger(SupplierCertificateAction.class);

    private long fileMaximumSize = 2 * 1024 * 1024; // 上传文件的大小限制，

    private String[] allowTypes = {".jpg", ".jpeg", ".gif", ".bmp", ".png", ".JPG", ".JPEG", ".GIF", ".BMP", ".PNG"};

    private String realName; // 用户设置的文件的名称

    private File file;// 用户传上来的文件

    private String fileFileName; // 文件名称

    private String fileContentType; // 文件类型

    private Long supplierId;// 供应商ID

    private List<SuppliersCertificate> certificateList;

    private Long scId;// 供应商资质ID

    private ReturnResult<Map<String, Object>> returnResult;

    private Long userId;//从页面获得用户id

    private NewsCategory newsCategory;

    @Resource
    private SupplierCertificateService supplierCertificateService;

    @Resource
    private MerchantInfoService MerchantInfoService;

    private MerchantInfo supplierBaseInfo;//商户基本信息

    /**
     * 到供应商资质文件页面
     *
     * @return
     */
    public String toSupplierCertificate() {
        try {
            // 查询已经上传的资质文件
            Long supplierId = getSupplyId();
            Long loginId = getUserId();
            certificateList = supplierCertificateService.findCertificateListBySupplierId(supplierId);
            MerchantInfo newMerchantInfo = new MerchantInfo();
            newMerchantInfo.setLoginId(loginId);
            // 根据登录id查询商户信息
            supplierBaseInfo = MerchantInfoService.selectByLoginId(newMerchantInfo);
        } catch (Exception e) {
            logger.error("查询资质文件出错,", e);
            return ERROR;
        }

        return SUCCESS;
    }

    /**
     * 上传资质文件
     *
     * @return
     */
    public String uploadCeriticate() {
        Map<String, Object> jsonMap = new HashMap();
        if (file == null) {
            jsonMap.put("flg", false);
            jsonMap.put("message", "上传的文件为空!");
            writeJson(jsonMap);
            return null;
        }

        // 文件后缀名
        int index = StringUtils.lastIndexOf(fileFileName, '.');
        if (index < 0) {
            jsonMap.put("flg", false);
            jsonMap.put("message", "上传的文件类型不允许!");
            writeJson(jsonMap);
            return null;
        }
        // 获取文件后缀名
        String extFileName = StringUtils.substring(fileFileName, index);
        if (!Upload.isValid(extFileName, allowTypes)) {
            jsonMap.put("flg", false);
            jsonMap.put("message", "上传的文件类型不允许!");
            writeJson(jsonMap);
            return null;
        }
        if (file.length() > fileMaximumSize) {
            jsonMap.put("flg", false);
            jsonMap.put("message", "图片文件大小超过2M");
            writeJson(jsonMap);
            return null;
        }

        if (null == supplierId || supplierId <= 0) {
            supplierId = getSupplyId();
            if (supplierId == null) {
                jsonMap.put("flg", false);
                jsonMap.put("message", "上传图片超时,请刷新重试");
                writeJson(jsonMap);
                return null;
            }
        }
        // 创建供应商ID为名称的文件夹
        String name = DateFormatUtils.format(new Date(), "yyyyMMdd_" + System.currentTimeMillis() + "_" + supplierId);
        String filename = name + extFileName.toLowerCase();
        //保存到数据库的
        String filePath = File.separatorChar + "certificate" + File.separatorChar + supplierId + File.separatorChar;
        //上传到服务器的
        String root = ConfigurationUtil.getString("SUPPLIER_CERTIFICATE_PATH");
        String saveFilePath = root + File.separatorChar + "certificate" + File.separatorChar + supplierId + File.separatorChar;
        File dirFile = new File(saveFilePath);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }

        try {
            String savePath = filePath + filename;
            SuppliersCertificate suppliersCertificate = new SuppliersCertificate();
            suppliersCertificate.setSupplierId(supplierId);// 供应商ID
            suppliersCertificate.setFilePath(savePath);// 文件路径
            suppliersCertificate.setFileName(realName);// 文件名称
            // 保存到数据库
            supplierCertificateService.saveSupplierCertificate(suppliersCertificate);
            File destFile = new File(saveFilePath, filename);
            FileUtils.copyFile(file, destFile);
            // 删除临时文件
            file.delete();
            // 查询已经上传的资质文件
            certificateList = supplierCertificateService.findCertificateListBySupplierId(supplierId);
            jsonMap.put("flg", true);
        } catch (Exception e) {
            logger.error("上传资质文件出错,", e);
            jsonMap.put("flg", false);
            jsonMap.put("message", "上传资质文件出错");
        }

        writeJson(jsonMap);
        return null;
    }

    /**
     * 删除已资质未审核文件
     *
     * @return
     */
    public String deleteSupplierCertifacate() {
        String scIdTemp = getRequest().getParameter("scId");
        try {
            if (StringUtils.isNotEmpty(scIdTemp)) {
                scId = Long.valueOf(scIdTemp);
            }
            SuppliersCertificate suppliersCertificate = supplierCertificateService.findCertificateByScId(scId);
            if (null != suppliersCertificate) {
                supplierCertificateService.deleteSupplierCertificate(scId);
                // 删除上传到服务器上的图片
                String root = ServletActionContext.getServletContext().getRealPath(String.valueOf(File.separatorChar));
                String delFile = root + suppliersCertificate.getFilePath();
                File f = new File(delFile); // 输入要删除的文件位置
                if (f.exists()) {
                    f.delete();
                }
            }
            // 查询已经上传的资质文件
            certificateList = supplierCertificateService.findCertificateListBySupplierId(getSupplyId());
        } catch (Exception e) {
            logger.error("删除资质文件出错,", e);
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 以下为set和get方法
     *
     * @return
     */
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

    public ReturnResult<Map<String, Object>> getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult<Map<String, Object>> returnResult) {
        this.returnResult = returnResult;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public NewsCategory getNewsCategory() {
        return newsCategory;
    }

    public void setNewsCategory(NewsCategory newsCategory) {
        this.newsCategory = newsCategory;
    }

    public MerchantInfo getSupplierBaseInfo() {
        return supplierBaseInfo;
    }

    public void setSupplierBaseInfo(MerchantInfo supplierBaseInfo) {
        this.supplierBaseInfo = supplierBaseInfo;
    }

}