package com.kmzyc.b2b.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.km.framework.fileupload.Upload;
import com.kmzyc.b2b.service.member.UserInfoService;
import com.kmzyc.b2b.vo.ReturnResult;
import com.kmzyc.framework.constants.Constants;
import com.kmzyc.framework.constants.InterfaceResultCode;
import com.kmzyc.framework.util.IOTools;
import com.kmzyc.util.StringUtil;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.whalin.MemCached.MemCachedClient;

@Scope("prototype")
@Controller("appUploadUserImgAction")
public class AppUploadUserImgAction extends AppBaseAction {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 6147214475127996913L;

    // static Logger logger = Logger.getLogger(AppUploadUserImgAction.class);
    private static Logger logger = LoggerFactory.getLogger(AppUploadUserImgAction.class);

    @Resource(name = "memCachedClient")
    private MemCachedClient memCachedClient;
    @Resource(name = "userInfoServiceImpl")
    private UserInfoService userInfoService;

    // private String savePath = ConfigurationUtil.getString("USER_IMG_UPLOAD_PATH"); // 上传到服务器的路径
    private String[] allowTypes = {".jpg", ".jpeg", ".gif", ".png"};
    private final String USER_IMG_TEMP_PATH = "temp";// 头像上传临时目录
    private String message = "请求参数错误";
    private String code = InterfaceResultCode.FAILED;
    private Long uid;
    private String token;
    private long fileMaximumSize = 2 * 1024 * 1024; // 上传文件的大小限制，
    private ReturnResult<Map<String, Object>> returnResult;

    // 登录用户IDkey前缀
    // private static final String UID_PREX_KEY = ConfigurationUtil
    // .getString("b2b_login_uid_memcached_key_prex");

    public void appUploadUserImg() {
        JSONObject jsonParams = AppJsonUtils.getJsonObject(getRequest());
        if (null != jsonParams && !jsonParams.isEmpty()) {
            uid = jsonParams.getLong("uid");
            token = jsonParams.getString("token");
            if (null == uid) {
                returnResult = new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                        "uid为空", null);
                printJsonString(returnResult);
                return;
            }
            String fileString = jsonParams.getString("fileString");
            logger.info("上传头像文件，传入的参数为：uid【" + uid + "】，tocken【" + token + "】，fileString【"
                    + fileString + "】");
            String fileName = uid.toString();

            if (validToken(uid, token)) {
                String errorMessage = null;
                // 上传文件为空
                if (fileString == null) {
                    errorMessage = "上传的文件为空!";
                    logger.error(errorMessage);
                    returnResult = new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                            errorMessage, null);
                }
                String phoneImgPath =
                        ConfigurationUtil.getString("USER_IMG_UPLOAD_PATH") + "phoneImg";
                File file = new File(phoneImgPath + "/" + fileName + ".jpeg");
                // 将字符转换陈图片
                boolean fileFlag = this.xmlString2Bin(fileString, file);
                logger.info("将字符转换为图片结果为：【" + fileFlag + "】");
                // 转换成功
                if (fileFlag) {
                    // 获取文件后缀名称
                    if (file.getName().lastIndexOf(".") < 0) {
                        errorMessage = "上传的文件类型不允许!";
                        logger.error("上传的文件类型不允许!");
                        returnResult = new ReturnResult<Map<String, Object>>(
                                InterfaceResultCode.FAILED, errorMessage, null);
                    }
                    String filenExtendedStr =
                            file.getName().substring(file.getName().lastIndexOf("."));
                    if (!Upload.isValid(filenExtendedStr, allowTypes)) {
                        errorMessage = "上传的文件类型不允许!";
                        logger.error("上传的文件类型不允许!");
                        returnResult = new ReturnResult<Map<String, Object>>(
                                InterfaceResultCode.FAILED, errorMessage, null);
                    }
                    if (file.length() > fileMaximumSize) {
                        errorMessage = "图片文件大小超过2M";
                        logger.error("图片文件大小超过2M");
                        returnResult = new ReturnResult<Map<String, Object>>(
                                InterfaceResultCode.FAILED, errorMessage, null);
                    }
                    String originalImgName = fileName + filenExtendedStr;
                    String originalImgPath = ConfigurationUtil.getString("USER_IMG_UPLOAD_PATH")
                            + USER_IMG_TEMP_PATH + "/" + originalImgName;
                    try {
                        FileUtils.copyFile(file, new File(originalImgPath));
                        logger.info(file + "文件上传成功，上传至服务器路径" + originalImgPath);
                        userInfoService.updateUserImg(uid, fileName);
                    } catch (IOException e) {
                        logger.error("上传文件出错：" + e.getMessage(), e);
                        errorMessage = "上传文件出错";
                        returnResult = new ReturnResult<Map<String, Object>>(
                                InterfaceResultCode.FAILED, errorMessage, null);
                    } catch (SQLException sqe) {
                        logger.error("更新用户图像至数据库出错：" + sqe.getMessage(), sqe);
                        returnResult = new ReturnResult<Map<String, Object>>(
                                InterfaceResultCode.FAILED, "图片保存失败", null);
                    }
                    // 验证成功后保存图片
                    saveUserImg(originalImgName);

                } else {
                    // 字符转换成图片不成功
                    errorMessage = "图片转换出错";
                    returnResult = new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                            errorMessage, null);
                }
            } else {
                message = "未登录";
                code = InterfaceResultCode.NOT_LOGIN;
                returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
            }
        } else {
            returnResult = new ReturnResult<Map<String, Object>>(code, message, null);
        }

        printJsonString(returnResult);
    }

    /**
     * 保存用户头像
     * 
     * @return
     */
    public String saveUserImg(String originalImgName) {
        // 文件原始名称
        // String originalImgName = getRequest().getParameter("userImgName");
        logger.info("开始保存用户图像,图像名称：" + originalImgName);
        // 文件名称，去掉文件扩展名
        String tempPath =
                ConfigurationUtil.getString("USER_IMG_UPLOAD_PATH") + USER_IMG_TEMP_PATH + "/";
        String destPath = ConfigurationUtil.getString("USER_IMG_UPLOAD_PATH");
        try {
            File originalImgFile = new File(destPath + originalImgName);
            if (originalImgFile.exists()) {
                FileUtils.forceDelete(originalImgFile);
            }
            FileUtils.moveFile(new File(tempPath + originalImgName),
                    new File(destPath + originalImgName));

            Long userId = ((Long) getSession().getAttribute(Constants.SESSION_USER_ID));
            userInfoService.updateUserImg(userId, originalImgName);
        } catch (IOException e) {
            logger.error("将图片从临时路径移至正式路径出错：" + e.getMessage(), e);
            returnResult = new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                    "图片保存失败", null);
            return SUCCESS;
        } catch (SQLException sqe) {
            logger.error("更新用户图像至数据库出错：" + sqe.getMessage(), sqe);
            returnResult = new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                    "图片保存失败", null);
            return SUCCESS;
        } catch (Exception e) {
            logger.error("更新用户图像至数据库出错：" + e.getMessage(), e);
            returnResult = new ReturnResult<Map<String, Object>>(InterfaceResultCode.FAILED,
                    "图片保存失败", null);
            return SUCCESS;
        }

        returnResult =
                new ReturnResult<Map<String, Object>>(InterfaceResultCode.SUCCESS, "图片保存成功", null);
        return SUCCESS;
    }

    /**
     * 校验令牌 成功:true，失败：false
     * 
     * @param loginId
     * @param token
     * @return
     */
    private boolean validToken(long loginId, String pToken) {
        boolean result = false;
        if (!StringUtil.isEmpty(pToken) && 0L != loginId) {
            String mToken = (String) memCachedClient
                    .get(ConfigurationUtil.getString("b2b_login_uid_memcached_key_prex") + loginId);
            result = pToken.equalsIgnoreCase(mToken);
        }
        return result;
    }

    public boolean xmlString2Bin(String base64String, File file) {
        byte[] data;
        FileOutputStream output = null;
        boolean ret = false;
        try {
            data = Base64.decodeBase64(base64String.getBytes());
            output = new FileOutputStream(file);
            output.write(data);
            output.flush();
            ret = true;
        } catch (Exception e) {
            logger.error("文件由字符转换成图片出错：" + e.getMessage(), e);
        } finally {
            IOTools.closeIO(output);
        }
        return ret;
    }

    public ReturnResult<Map<String, Object>> getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(ReturnResult<Map<String, Object>> returnResult) {
        this.returnResult = returnResult;
    }

    public UserInfoService getUserInfoService() {
        return userInfoService;
    }

    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

}
