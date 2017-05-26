package com.pltfm.cms.action;

import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.cms.parse.PathConstants;
import com.pltfm.cms.util.FileOperateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

/**
 * 产品新旧Id维护Action
 *
 * @author cjm
 * @since 2014-6-18
 */
@Component(value = "productIdUpholdAction")
@Scope(value = "prototype")
public class ProductIdUpholdAction extends BaseAction {
	private static Logger logger = LoggerFactory.getLogger(ProductIdUpholdAction.class);
    /**
     * 产品新旧IdMap
     */
    private Map<Long, Long> upholdMap;

    /**
     * 之前SkuId
     */
    private Long skuId;

    /**
     * 现有SkuId
     */
    private Long newSkuId;

    /**
     * 删除SkuId
     */
    private String delSku;


    /**
     * 查询列表
     */
    public String queryUphold() throws IOException {
//        String rootPath = PathConstants.CREATE_LOG_FILE_FOR_WINDOWS;
//        if ("/".equals("/")) {
//            rootPath = PathConstants.CREATE_LOG_FILE_FOR_LINUX;
//        }
//
//        String content = FileOperateUtils.reader(rootPath);
//        String[] array = content.split(",");
//        if (!"".equals(array[0])) {
//            upholdMap = new HashMap<Long, Long>();
//            for (int i = 0; i < array.length; i++) {
//                upholdMap.put(Long.valueOf(array[i].split("=")[0]), Long.valueOf(array[i].split("=")[1]));
//            }
//        }
        return "queryUphold";
    }

    /**
     * 添加方法
     */
    public String addUphold() throws IOException {
//        String rootPath = PathConstants.CREATE_LOG_FILE_FOR_WINDOWS;
//        if ("/".equals("/")) {
//            rootPath = PathConstants.CREATE_LOG_FILE_FOR_LINUX;
//        }
//
//        try {
//            RandomAccessFile raf = new RandomAccessFile(rootPath, "rw");
//            raf.seek(raf.length());
//            String content = newSkuId + "=" + skuId;
//            if (raf.length() != 0) {
//                content = "," + content;
//            }
//            raf.writeBytes(content);
//            raf.close();
//        } catch (Exception e) {
//            logger.error("添加产品对应SkuId方法出现异常", e);
//        }
//
//        this.addActionMessage(ConfigurationUtil.getString("add.success"));
        return this.queryUphold();
    }

    /**
     * 删除
     */
    public String delUphold() throws IOException {
//        String rootPath = PathConstants.CREATE_LOG_FILE_FOR_WINDOWS;
//        if ("/".equals("/")) {
//            rootPath = PathConstants.CREATE_LOG_FILE_FOR_LINUX;
//        }
//        try {
//            String content = FileOperateUtils.reader(rootPath);
//            if (content.indexOf(delSku + ",") != -1) {
//                content = content.replaceAll(delSku + ",", "");
//            } else if (content.indexOf("," + delSku) != -1) {
//                content = content.replaceAll("," + delSku, "");
//            } else if (content.indexOf(delSku) != -1) {
//                content = content.replaceAll(delSku, "");
//            }
//            FileOperateUtils.writer(rootPath, content);
//
//        } catch (Exception e) {
//            logger.error("删除产品对应SkuId方法出现异常", e);
//        }
//        this.addActionMessage(ConfigurationUtil.getString("delete.success"));
        return this.queryUphold();
    }

    /**
     * 跳转添加页面
     */
    public String toAddUphold() {
        return "toAddUphold";
    }

    public Map<Long, Long> getUpholdMap() {
        return upholdMap;
    }

    public void setUpholdMap(Map<Long, Long> upholdMap) {
        this.upholdMap = upholdMap;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getNewSkuId() {
        return newSkuId;
    }

    public void setNewSkuId(Long newSkuId) {
        this.newSkuId = newSkuId;
    }

    public String getDelSku() {
        return delSku;
    }

    public void setDelSku(String delSku) {
        this.delSku = delSku;
    }

}
