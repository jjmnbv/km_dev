package com.kmzyc.b2b.action.member;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.km.framework.fileupload.Upload;
import com.kmzyc.b2b.app.AppBaseAction;
import com.kmzyc.b2b.app.AppJsonUtils;
import com.kmzyc.b2b.service.member.UserInfoService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.zkconfig.ConfigurationUtil;

import net.coobird.thumbnailator.Thumbnails;

/**
 * Description: User: lishiming Date: 13-10-21 下午5:13 Since: 1.0
 */
@Controller
@Scope("prototype")
public class UploadUserImgAction extends AppBaseAction {
    /**
	 * 
	 */
    private static final long serialVersionUID = -5055314349678174965L;

    // static Logger logger = Logger.getLogger(UploadUserImgAction.class);
    private static Logger logger = LoggerFactory.getLogger(UploadUserImgAction.class);

    // private String savePath = ConfigurationUtil.getString("USER_IMG_UPLOAD_PATH"); // 上传到服务器的路径
    // private String getPath = ConfigurationUtil.getString("USER_IMG_PATH"); // 服务器获取头像路径
    private final String USER_IMG_TEMP_PATH = "temp";// 头像上传临时目录
    private long fileMaximumSize = 2 * 1024 * 1024; // 上传文件的大小限制，
    private String[] allowTypes = {".jpg", ".jpeg", ".gif", ".png"};

    @Resource(name = "userInfoServiceImpl")
    private UserInfoService userInfoService;

    private ReturnResult<Map<String, Object>> returnResult;
    private String userImgName;

    /**
     * app接口提供：上传头像方法
     * 
     * @return
     */
    public void uploadUserImg4App() {
        String errorMessage = null;
        Map<String, Object> resultDate = null;
        try {
            this.getRequest().getParameter("userImage");
            if (this.getUpFile() == null) {
                errorMessage = "上传的文件为空!";
                logger.error(errorMessage);
                returnResult =
                        new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                errorMessage, null);
                printJsonString(returnResult);
                return;
            }
            if (this.getUpFileFileName().lastIndexOf(".") < 0) {
                errorMessage = "上传的文件类型不允许!";
                logger.error("上传的文件类型不允许!");
                returnResult =
                        new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                errorMessage, null);
                printJsonString(returnResult);
                return;
            }
            String filenExtendedStr =
                    this.getUpFileFileName().substring(this.getUpFileFileName().lastIndexOf("."));
            if (!Upload.isValid(filenExtendedStr, allowTypes)) {
                errorMessage = "上传的文件类型不允许!";
                logger.error("上传的文件类型不允许!");
                returnResult =
                        new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                errorMessage, null);
                printJsonString(returnResult);
                return;
            }
            if (this.getUpFile().length() > fileMaximumSize) {
                errorMessage = "图片文件大小超过2M";
                logger.error("图片文件大小超过2M");
                returnResult =
                        new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                errorMessage, null);
                printJsonString(returnResult);
                return;
            }
            JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
            String fileName =
                    getSession().getAttribute(Constants.SESSION_USER_ID) != null ? ((Long) getSession()
                            .getAttribute(Constants.SESSION_USER_ID)).toString() : "";
            if (null != jsonParams && !jsonParams.isEmpty()) {
                String uid = jsonParams.getString("uid");
                if (StringUtils.isEmpty(fileName))
                    fileName = uid;
            }
            String originalImgName = fileName + filenExtendedStr;
            String middleImgName = fileName + Constants.MIDDLE_IMG_SUFFIX + filenExtendedStr;
            String smallImgName = fileName + Constants.SMALL_IMG_SUFFIX + filenExtendedStr;
            String savePath = ConfigurationUtil.getString("USER_IMG_UPLOAD_PATH"); // 上传到服务器的路径
            String originalImgPath = savePath + originalImgName;
            String getOriginalImgPath =
                    ConfigurationUtil.getString("USER_IMG_PATH") + originalImgName;
            String middleImgPath = savePath + middleImgName;
            String smallImgPath = savePath + smallImgName;
            resultDate = new HashMap<String, Object>();
            try {
                FileUtils.copyFile(this.getUpFile(), new File(originalImgPath));
                logger.info(this.getUpFileFileName() + "文件上传成功，上传至服务器路径" + originalImgPath);
            } catch (IOException e) {
                logger.error("上传文件出错：" + e.getMessage(), e);
                errorMessage = "上传文件出错";
                returnResult =
                        new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                errorMessage, null);
                printJsonString(returnResult);
                return;
            }
            try {
                Thumbnails.of(this.getUpFile()).size(100, 100).toFile(middleImgPath);
                Thumbnails.of(this.getUpFile()).size(48, 48).toFile(smallImgPath);

                resultDate.put("originalImgName", originalImgName);
                resultDate.put("originalImgPath", getOriginalImgPath);
                resultDate.put("middleImgName", middleImgName);
                resultDate.put("smallImgName", smallImgName);

                userInfoService.updateUserImg(Long.parseLong(fileName), originalImgName);
            } catch (IOException e) {
                logger.error("图片缩放出错：" + e.getMessage(), e);
                errorMessage = "图片缩放出错";
                returnResult =
                        new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                errorMessage, null);
                printJsonString(returnResult);
                return;
            } catch (SQLException e) {
                logger.error("更新个人头像错误：" + e.getMessage(), e);
                errorMessage = "更新个人头像错误";
                returnResult =
                        new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                                errorMessage, null);
                printJsonString(returnResult);
                return;
            }
        } catch (Exception e) {
            logger.error("", e);
            returnResult =
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "系统繁忙", null);
            printJsonString(returnResult);
            return;
        }
        returnResult =
                new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS, "成功", resultDate);
        printJsonString(returnResult);
    }

    /**
     * 原上传头像方法
     * 
     * @return
     */
    public String uploadUserImg() {
        String errorMessage = null;
        if (this.getUpFile() == null) {
            errorMessage = "上传的文件为空!";
            logger.error(errorMessage);
            returnResult =
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, errorMessage,
                            null);
            return SUCCESS;
        }
        if (this.getUpFileFileName().lastIndexOf(".") < 0) {
            errorMessage = "上传的文件类型不允许!";
            logger.error("上传的文件类型不允许!");
            returnResult =
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, errorMessage,
                            null);
            return SUCCESS;
        }
        String filenExtendedStr =
                this.getUpFileFileName().substring(this.getUpFileFileName().lastIndexOf("."));
        if (!Upload.isValid(filenExtendedStr, allowTypes)) {
            errorMessage = "上传的文件类型不允许!";
            logger.error("上传的文件类型不允许!");
            returnResult =
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, errorMessage,
                            null);
            return SUCCESS;
        }
        if (this.getUpFile().length() > fileMaximumSize) {
            errorMessage = "图片文件大小超过2M";
            logger.error("图片文件大小超过2M");
            returnResult =
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, errorMessage,
                            null);
            return SUCCESS;
        }
        //
        String fileName = ((Long) getSession().getAttribute(Constants.SESSION_USER_ID)).toString();
        String originalImgName = fileName + filenExtendedStr;
        String middleImgName = fileName + Constants.MIDDLE_IMG_SUFFIX + filenExtendedStr;
        String smallImgName = fileName + Constants.SMALL_IMG_SUFFIX + filenExtendedStr;
        String savePath = ConfigurationUtil.getString("USER_IMG_UPLOAD_PATH"); // 上传到服务器的路径
        String originalImgPath = savePath + USER_IMG_TEMP_PATH + "/" + originalImgName;
        String middleImgPath = savePath + USER_IMG_TEMP_PATH + "/" + middleImgName;
        String smallImgPath = savePath + USER_IMG_TEMP_PATH + "/" + smallImgName;
        Map<String, Object> resultDate = new HashMap<String, Object>();
        try {
            FileUtils.copyFile(this.getUpFile(), new File(originalImgPath));
            logger.info(this.getUpFileFileName() + "文件上传成功，上传至服务器路径" + originalImgPath);
        } catch (IOException e) {
            logger.error("上传文件出错：" + e.getMessage(), e);
            errorMessage = "上传文件出错";
            returnResult =
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, errorMessage,
                            null);
            return SUCCESS;
        }
        try {
            Thumbnails.of(this.getUpFile()).size(100, 100).toFile(middleImgPath);
            Thumbnails.of(this.getUpFile()).size(48, 48).toFile(smallImgPath);

            resultDate.put("originalImgName", originalImgName);
            resultDate.put("middleImgName", middleImgName);
            resultDate.put("smallImgName", smallImgName);
        } catch (IOException e) {
            logger.error("图片缩放出错：" + e.getMessage(), e);
            errorMessage = "图片缩放出错";
            returnResult =
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, errorMessage,
                            null);
            return SUCCESS;
        }
        returnResult =
                new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS, "成功", resultDate);
        return SUCCESS;
    }

    /**
     * 保存用户头像
     * 
     * @return
     */
    public String saveUserImg() {
        // 文件原始名称
        String originalImgName = getRequest().getParameter("userImgName");
        logger.info("开始保存用户图像,图像名称：" + originalImgName);
        // 文件名称，去掉文件扩展名
        String fileName = originalImgName.substring(0, originalImgName.lastIndexOf("."));
        String fileExtendedName = originalImgName.substring(originalImgName.lastIndexOf("."));
        String savePath = ConfigurationUtil.getString("USER_IMG_UPLOAD_PATH"); // 上传到服务器的路径
        String tempPath = savePath + USER_IMG_TEMP_PATH + "/";
        String destPath = savePath;
        try {
            File originalImgFile = new File(destPath + originalImgName);
            File middleImgFile =
                    new File(destPath + fileName + Constants.MIDDLE_IMG_SUFFIX + fileExtendedName);
            File smallImgFile =
                    new File(destPath + fileName + Constants.SMALL_IMG_SUFFIX + fileExtendedName);
            if (originalImgFile.exists()) {
                FileUtils.forceDelete(originalImgFile);
            }
            if (middleImgFile.exists()) {
                FileUtils.forceDelete(middleImgFile);
            }
            if (smallImgFile.exists()) {
                FileUtils.forceDelete(smallImgFile);
            }
            FileUtils.moveFile(new File(tempPath + originalImgName), new File(destPath
                    + originalImgName));
            FileUtils.moveFile(new File(tempPath + fileName + Constants.MIDDLE_IMG_SUFFIX
                    + fileExtendedName), new File(destPath + fileName + Constants.MIDDLE_IMG_SUFFIX
                    + fileExtendedName));
            FileUtils.moveFile(new File(tempPath + fileName + Constants.SMALL_IMG_SUFFIX
                    + fileExtendedName), new File(destPath + fileName + Constants.SMALL_IMG_SUFFIX
                    + fileExtendedName));

            Long userId = ((Long) getSession().getAttribute(Constants.SESSION_USER_ID));
            userInfoService.updateUserImg(userId, originalImgName);
        } catch (IOException e) {
            logger.error("将图片从临时路径移至正式路径出错：" + e.getMessage(), e);
            returnResult =
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "图片保存失败",
                            null);
            return SUCCESS;
        } catch (SQLException sqe) {
            logger.error("更新用户图像至数据库出错：" + sqe.getMessage(), sqe);
            returnResult =
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "图片保存失败",
                            null);
            return SUCCESS;
        } catch (Exception e) {
            logger.error("更新用户图像至数据库出错：" + e.getMessage(), e);
            returnResult =
                    new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED, "图片保存失败",
                            null);
            return SUCCESS;
        }

        returnResult =
                new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS, "图片保存成功", null);
        return SUCCESS;
    }

    /**
     * 上传头像的返回消息
     * 
     * @param success
     * @param message 当success为true时，该值为图片的名称
     */
    @SuppressWarnings("unused")
    private void response(boolean success, String message) {
        PrintWriter out = null;
        try {
            out = this.getResponse().getWriter();
            if (success) {
                ServletActionContext
                        .getResponse()
                        .getWriter()
                        .println(
                                "＜script type='text/javascript'＞parent.callback('success','"
                                        + message + "')＜/script＞");
            } else {
                ServletActionContext
                        .getResponse()
                        .getWriter()
                        .println(
                                "＜script type='text/javascript'＞parent.callback('failed','"
                                        + message + "')＜/script＞");
            }

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    public ReturnResult<Map<String, Object>> getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult<Map<String, Object>> returnResult) {
        this.returnResult = returnResult;
    }

    public String getSavePath() {
        return ConfigurationUtil.getString("USER_IMG_UPLOAD_PATH");
    }

    public String getUserImgName() {
        return userImgName;
    }

    public void setUserImgName(String userImgName) {
        this.userImgName = userImgName;
    }
}
