package com.kmzyc.supplier.action.prodBrand;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.km.framework.fileupload.Upload;
import com.km.framework.page.Pagination;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.util.KeyWordsUtils;
import com.kmzyc.supplier.action.SupplierBaseAction;
import com.kmzyc.supplier.service.ProdBrandDraftService;
import com.kmzyc.supplier.util.FileUploadUtils;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.opensymphony.xwork2.Action;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.enums.ProdBrandDraftStatus;
import com.pltfm.app.vobject.ProdBrandDraft;

/**
 * 功能：品牌草稿action
 *
 * @author Zhoujiwei
 * @since 2015/11/19 17:26
 */
@Controller("prodBrandDraftAction")
@Scope("prototype")
public class ProdBrandDraftAction extends SupplierBaseAction {

    private static Logger logger = LoggerFactory.getLogger(ProdBrandDraftAction.class);

    private ProdBrandDraft prodBrandDraft;

    private String rtnMessage;

    private String status;

    private static long FILE_WIDTH = 118; // 上传文件的宽像素，

    private static long FILE_HEIGHT = 46; // 上传文件的高像素，

    private static String[] ALLOW_TYPES = {".jpg", ".jpeg", ".png", ".JPG", ".JPEG", ".PNG", ".GIF", ".gif"};

    /**
     * logo文件
     */
    private File logoFile;

    /**
     * logo文件名称
     */
    private String logoFileName;

    /**
     * 上一次logo文件路径
     */
    private String preLogoPath;

    @Resource
    private ProdBrandDraftService prodBrandDraftService;

    /**
     * 查询品牌草稿列表
     *
     * @return
     */
    public String showProdBrandDraftList() {
        if (StringUtils.isEmpty(status)) {
            status = ProdBrandDraftStatus.AUDIT.getStatus();
        }

        Pagination page = getPagination(Constants.VIEW_PAGE, Integer.parseInt(getPageSize()));
        prodBrandDraft = new ProdBrandDraft();
        prodBrandDraft.setShopCode(getSupplyId().toString());
        prodBrandDraft.setStatus(status);
        try {
            pagintion = prodBrandDraftService.showProdBrandDraftList(prodBrandDraft, page);
        } catch (Exception e) {
            logger.error("查询品牌草稿列表错误：", e);
            return ERROR;
        }
        return Action.SUCCESS;
    }

    /**
     * 保存前或修改前--操作品牌草稿信息
     *
     * @return
     */
    public String toProdBrandDraftAddOrUpdateInfo() {
        //新增时，直接进入页面
        if (prodBrandDraft == null || prodBrandDraft.getBrandId() == null) {
            return Action.SUCCESS;
        }

        if (getProductBrandDraft()) {
            return ERROR;
        }

        return Action.SUCCESS;
    }

    private boolean getProductBrandDraft() {
        try {
            prodBrandDraft.setShopCode(getSupplyId().toString());
            prodBrandDraft = prodBrandDraftService.getProdBrandDraft(prodBrandDraft);
        } catch (Exception e) {
            logger.error("查询品牌草稿信息视图失败,品牌id=[" + prodBrandDraft.getBrandId()
                    + "],错误信息：" + e.getMessage(), e);
            return true;
        }
        return false;
    }

    /**
     * 查询品牌草稿信息视图
     *
     * @return
     */
    public String viewProdBrandDraftInfo() {
        if (prodBrandDraft == null || prodBrandDraft.getBrandId() == null) {
            rtnMessage = "请选择要查看的品牌信息！";
            return Action.SUCCESS;
        }

        if (getProductBrandDraft()) {
            return ERROR;
        }
        return Action.SUCCESS;
    }

    /**
     * 保存或修改品牌草稿信息
     *
     * @return
     */
    public String saveOrUpdateProdBrandDraft() {
        Boolean isAdd = prodBrandDraft.getBrandId() == null;
        ResultMessage result = checkAndHandleFile(isAdd);
        if (!result.getIsSuccess()) {
            writeJson(result);
            return null;
        }

        String errorMessage = null;
        String brandName = prodBrandDraft.getBrandName();
        if (StringUtils.isEmpty(brandName)) {
            result.setIsSuccess(Boolean.FALSE);
            result.setMessage("品牌名称不能为空！");
            writeJson(result);
            return null;
        }

        try {
            List<String> paramList = new ArrayList<String>();
            paramList.add("logoPath");
            KeyWordsUtils.filter(prodBrandDraft, paramList);

            if (prodBrandDraftService.isExistsBrandName(brandName)) {
                result.setIsSuccess(Boolean.FALSE);
                result.setMessage("品牌名称重复，无法添加重复品牌！");
                writeJson(result);
                return null;
            }

            prodBrandDraft.setModifUser(getLoginUserId());
            prodBrandDraft.setShopCode(getSupplyId().toString());
            if (isAdd) {
                prodBrandDraft.setCreateUser(getLoginUserId());
                errorMessage = "保存并提交审核";
                prodBrandDraftService.saveProdBrandDraft(prodBrandDraft);
            } else {
                errorMessage = "修改并提交审核";
                prodBrandDraftService.updateProdBrandDraft(prodBrandDraft, preLogoPath);
            }
        } catch (Exception e) {
            logger.error("保存或修改品牌草稿信息出错，brandId=" + prodBrandDraft.getBrandId()
                    + ",错误信息：" + e.getMessage(), e);
            Object object = result.getObject();
            if (object != null && object instanceof File) {
                File pic = (File) object;
                pic.delete();
                result.setObject(null);
            }
            result.setIsSuccess(Boolean.FALSE);
            result.setMessage(errorMessage + "失败！");
            writeJson(result);
            return null;
        }

        result.setObject(null);
        result.setMessage(errorMessage + "成功！");
        result.setIsSuccess(Boolean.TRUE);
        writeJson(result);
        return null;
    }

    /**
     * 判断logo图片并处理logo路径
     *
     * @param isAdd true新增，false编辑
     * @return
     */
    private ResultMessage checkAndHandleFile(Boolean isAdd) {
        ResultMessage result = new ResultMessage();

        if (logoFile == null) {
            if (!isAdd) {//如果是编辑，logoFile为null，使用之前的图片
                result.setIsSuccess(Boolean.TRUE);
            } else {
                result.setIsSuccess(Boolean.FALSE);
                result.setMessage("请选择118*46px，可上传jpg、png、jpeg、gif格式的图片!");
            }
            return result;
        }

        // 文件后缀名
        int index = StringUtils.lastIndexOf(logoFileName, '.');
        if (index < 0) {
            result.setIsSuccess(Boolean.FALSE);
            result.setMessage("请选择118*46px，可上传jpg、png、jpeg、gif格式的图片!");
            return result;
        }

        // 获取文件后缀名
        String extFileName = StringUtils.substring(logoFileName, index);
        if (!Upload.isValid(extFileName, ALLOW_TYPES)) {
            result.setIsSuccess(Boolean.FALSE);
            result.setMessage("请选择118*46px，可上传jpg、png、jpeg、gif格式的图片!");
            return result;
        }

        //判断图片的像素
        try {
            BufferedImage read = ImageIO.read(logoFile);
            if (read.getWidth() != FILE_WIDTH || read.getHeight() != FILE_HEIGHT) {
                result.setIsSuccess(Boolean.FALSE);
                result.setMessage("请选择118*46px，可上传jpg、png、jpeg、gif格式的图片!");
                return result;
            }

            //logo图片路径设置
            String filename = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS")
                    + "_" + new Random().nextInt(1000) + extFileName.toLowerCase(Locale.US);
            //保存在服务器上的路径
            String savePath = FileUploadUtils.createSavePath("productBrand");
            // 相对路径，预览时使用,数据库保存
            String relativePath = savePath.substring(ConfigurationUtil.getString("pictureUploadPath").length());
            File destFile = new File(savePath, filename);
            FileUtils.copyFile(logoFile, destFile);
            result.setObject(destFile);
            prodBrandDraft.setLogoPath(relativePath + filename);
            // 删除临时文件
            logoFile.delete();
        } catch (Exception e) {
            logger.error("加载logo图片信息出错，" + e.getMessage(), e);
            result.setIsSuccess(Boolean.FALSE);
            result.setMessage("加载此图片失败，请选择118*46px，可上传jpg、png、jpeg、gif格式的图片!");
            return result;
        }

        result.setIsSuccess(Boolean.TRUE);
        return result;
    }

    /**
     * 删除品牌草稿信息
     *
     * @return
     */
    public String deleteProdBrandDraft() {
        ResultMessage result = new ResultMessage();
        if (prodBrandDraft == null || prodBrandDraft.getBrandId() == null) {
            result.setMessage("请选择要删除的品牌！");
            writeJson(result);
            return null;
        }

        try {
            prodBrandDraft.setShopCode(getSupplyId().toString());
            prodBrandDraftService.deleteProdBrandDraft(prodBrandDraft);
        } catch (Exception e) {
            logger.error("删除品牌草稿出错，brandId=" + prodBrandDraft.getBrandId()
                    + ",错误信息：" + e.getMessage(), e);
            result.setIsSuccess(Boolean.FALSE);
            result.setMessage("删除品牌草稿失败！");
            writeJson(result);
            return null;
        }

        result.setMessage("删除成功！");
        result.setIsSuccess(Boolean.TRUE);
        writeJson(result);
        return null;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public File getLogoFile() {
        return logoFile;
    }

    public void setLogoFile(File logoFile) {
        this.logoFile = logoFile;
    }

    public String getLogoFileName() {
        return logoFileName;
    }

    public void setLogoFileName(String logoFileName) {
        this.logoFileName = logoFileName;
    }

    public ProdBrandDraft getProdBrandDraft() {
        return prodBrandDraft;
    }

    public String getPreLogoPath() {
        return preLogoPath;
    }

    public void setPreLogoPath(String preLogoPath) {
        this.preLogoPath = preLogoPath;
    }

    public void setProdBrandDraft(ProdBrandDraft prodBrandDraft) {
        this.prodBrandDraft = prodBrandDraft;
    }

    public void setProdBrandDraftService(ProdBrandDraftService prodBrandDraftService) {
        this.prodBrandDraftService = prodBrandDraftService;
    }

    public String getRtnMessage() {
        return rtnMessage;
    }

    public void setRtnMessage(String rtnMessage) {
        this.rtnMessage = rtnMessage;
    }
}
