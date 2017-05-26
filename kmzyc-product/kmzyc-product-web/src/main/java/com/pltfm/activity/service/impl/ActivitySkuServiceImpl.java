package com.pltfm.activity.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.vobject.ProductSkuPriceCache;
import com.kmzyc.promotion.remote.service.PromotionRemoteService;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.activity.dao.ActivitySkuDAO;
import com.pltfm.activity.service.ActivitySkuService;
import com.pltfm.app.vobject.ActivitySku;
import com.pltfm.app.vobject.ActivitySupplierEntry;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Service("activitySkuService")
public class ActivitySkuServiceImpl implements ActivitySkuService {
    @Resource
    private ActivitySkuDAO activitySkuDao;

    @Resource
    private PromotionRemoteService promotionRemoteService;

    private Logger logger = LoggerFactory.getLogger(ActivitySkuServiceImpl.class);

    @Override
    public void activityProductList(Page page, Long supplierEntryId) throws ServiceException {
        try {
            List<ActivitySku> list = activitySkuDao.activityProductList(supplierEntryId);
            List<ProductSkuPriceCache> cachePriceList = null;
            List<ProductSkuPriceCache> productSkuPriceCacheList = new ArrayList<ProductSkuPriceCache>();
            ProductSkuPriceCache productSkuPriceCache = null;
            for (ActivitySku activitySku : list) {
                productSkuPriceCache = new ProductSkuPriceCache();
                productSkuPriceCache.setProductSkuId(activitySku.getProductSkuId());
                productSkuPriceCacheList.add(productSkuPriceCache);
            }
            cachePriceList = promotionRemoteService.getProductSkuPriceBatch(productSkuPriceCacheList, new Date());
            //数据库查询集合对象和调用接口返回集合对象skuid存放顺序一致（不用两层循环）
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getProductSkuId().toString().equals(
                        cachePriceList.get(i).getProductSkuId().toString())
                        && null != cachePriceList.get(i).getPromotionPrice()) {
                    list.get(i).setPrice(new BigDecimal(cachePriceList.get(i).getPromotionPrice()));
                }
            }
            page.setDataList(list);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void activityEntryProductList(Page page, ActivitySku activitySku) throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0) pageIndex = 1;

        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;
        Map map = new HashMap();
        // 活动id(ACTIVITY_SKU.ACTIVITY_ID)
        if (null!=activitySku.getActivityId()) {
            map.put("activityId", activitySku.getActivityId());
        }
        // 公司名称（commercial_tenant_basic.CORPORATE_NAME）
        if (!("".equals(activitySku.getCorporateName()))) {
            map.put("corporateName", activitySku.getCorporateName());
        }
        // 店铺名称（SHOP_MAIN.SHOP_NAME）
        if (!("".equals(activitySku.getShopName()))) {
            map.put("shopName", activitySku.getShopName());
        }
        // 商品标题（PRODUCTMAIN.PRODUCT_TITLE）
        if (!("".equals(activitySku.getProductTitle()))) {
            map.put("productTitle", activitySku.getProductTitle());
        }
        // sku编号(PRODUCT_SKU.PRODUCT_SKU_CODE)
        if (null!=activitySku.getProductSkuCode()) {
            map.put("productSkuCode", activitySku.getProductSkuCode());
        }

        try {
            int count = activitySkuDao.activityEntryProductCount(map);
            List<ActivitySku> list = activitySkuDao.activityEntryProductList(map, skip, max);
            page.setDataList(list);
            page.setRecordCount(count);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void exportActivitySupplierProductList(ActivitySku activitySku) throws ServiceException {
        Map map = new HashMap();
        // 活动id(ACTIVITY_SKU.ACTIVITY_ID)
        if (null!=activitySku.getActivityId()) {
            map.put("activityId", activitySku.getActivityId());
        }
        // 公司名称（commercial_tenant_basic.CORPORATE_NAME）
        if (!("".equals(activitySku.getCorporateName()))) {
            map.put("corporateName", activitySku.getCorporateName());
        }
        // 店铺名称（SHOP_MAIN.SHOP_NAME）
        if (!("".equals(activitySku.getShopName()))) {
            map.put("shopName", activitySku.getShopName());
        }
        // 商品标题（PRODUCTMAIN.PRODUCT_TITLE）
        if (!("".equals(activitySku.getProductTitle()))) {
            map.put("productTitle", activitySku.getProductTitle());
        }
        // sku编号(PRODUCT_SKU.PRODUCT_SKU_CODE)
        if (null!=activitySku.getProductSkuCode()) {
            map.put("productSkuCode", activitySku.getProductSkuCode());
        }

        try {
            List<ActivitySku> list = activitySkuDao.exportActivitySupplierProductList(map);
            OutputStream ops = null;
            String[] title = {"公司名称", "店铺名称", "登录账户", "联系电话", "商品标题", "SKU编码"};

            // 导出不存在，自动创建目录
            String exportExcelPath = ConfigurationUtil.getString("exportExcelPath");
            File dir = new File(exportExcelPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String filePath = exportExcelPath + File.separator + "activityProduct.xls";
            // 创建Excel工作薄
            WritableWorkbook wwb;
            ops = new FileOutputStream(filePath);
            wwb = Workbook.createWorkbook(ops);
            // 添加第一个工作表并设置第一个Sheet的名字
            WritableSheet sheet = wwb.createSheet("活动报名商品表", 0);
            Label label;
            WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            WritableCellFormat wcf = new WritableCellFormat(wf);
            wcf.setBackground(Colour.YELLOW2);
            wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
            // 将列标题循环添加到Label中
            for (int i = 0; i < title.length; i++) {
                label = new Label(i, 0, title[i], wcf);
                sheet.addCell(label);
            }
            wcf = new WritableCellFormat();
            wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
            for (int i = 0; i < list.size(); i++) {
                ActivitySku activitySku2 = list.get(i);
                label = new Label(0, i + 1, activitySku2.getCorporateName(), wcf);
                sheet.addCell(label);
                label = new Label(1, i + 1, activitySku2.getShopName(), wcf);
                sheet.addCell(label);
                label = new Label(2, i + 1, activitySku2.getLoginAccount(), wcf);
                sheet.addCell(label);
                label = new Label(3, i + 1, activitySku2.getMobile(), wcf);
                sheet.addCell(label);
                label = new Label(4, i + 1, activitySku2.getProductTitle(), wcf);
                sheet.addCell(label);
                label = new Label(5, i + 1, String.valueOf(activitySku2.getProductSkuCode()), wcf);
                sheet.addCell(label);
            }
            wwb.write();
            wwb.close();
            if (ops != null) {
                ops.flush();
                ops.close();
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public int batchUpdateActivitySkuByEntryId(Long promotionId, Long supplierEntryId)
            throws ServiceException {
        try {
            Map map = new HashMap();
            map.put("promotionId", promotionId);
            map.put("supplierEntryId", supplierEntryId);
            int count = activitySkuDao.batchUpdateActivitySkuByEntryId(map);
            if (count<0) {
                logger.error("修改渠道报名审核通过产品在促销系统生成促销活动失败");
                throw new ServiceException("修改渠道报名审核通过产品在促销系统生成促销活动失败");
            }
            return count;
        } catch (SQLException e) {
            logger.error("修改渠道报名审核通过产品在促销系统生成促销活动失败");
            throw new ServiceException(e);
        }
    }

    @Override
    public List<String> getSkuInUnfinishedActivity(List<ActivitySku> activitySkuList,
            ActivitySupplierEntry activitySupplierEntry) throws ServiceException {
        List<Long> skuList = new ArrayList();
        for (int i=0; i<activitySkuList.size();i++) {
            skuList.add(activitySkuList.get(i).getProductSkuId());
        }
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("activityId",       activitySupplierEntry.getActivityId());
        condition.put("supplierId",       activitySupplierEntry.getSupplierId());
        condition.put("productSkuIdList", skuList);

        try {
            return activitySkuDao.getSkuInUnfinishedActivity(condition);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }


}
