package com.pltfm.app.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.kmzyc.supplier.maps.ShopAuditStatusMap;
import com.kmzyc.supplier.maps.ShopMainServiceTypeMap;
import com.kmzyc.supplier.maps.ShopMainStatusMap;
import com.kmzyc.supplier.maps.ShopMainTypeMap;
import com.kmzyc.supplier.model.NewsCategory;
import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.supplier.model.ShopNewsCategory;
import com.kmzyc.supplier.model.example.ShopMainExample;
import com.kmzyc.supplier.model.example.ShopMainExample.Criteria;
import com.kmzyc.cms.remote.service.SupplierRemoteService;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.dao.NewsCategoryDAO;
import com.pltfm.app.dao.ShopMainDAO;
import com.pltfm.app.dao.ShopNewsCategoryDAO;
import com.pltfm.app.jms.SearchShopMainMessageSender;
import com.pltfm.app.service.SupplierShopService;
import com.pltfm.app.vobject.ShopForExport;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Service("supplierShopService")
public class SupplierShopServiceImpl implements SupplierShopService {

    private static Logger logger = Logger.getLogger(SupplierShopServiceImpl.class);

    @Resource
    private ShopMainDAO shopMainDao;

    @Resource
    private NewsCategoryDAO newsCategoryDao;

    @Resource
    private ShopNewsCategoryDAO shopNewsCategoryDao;

    @Resource
    private SearchShopMainMessageSender sendMessageSender;

    @Resource
    private SupplierRemoteService supplierRemoteService;

    @Override
    public List<ShopMain> supplierShopListShow(Page page, ShopMain searchShopMain) throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0) pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        ShopMainExample example = new ShopMainExample();
        Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(searchShopMain.getShopTitle())) {// 店铺标题
            criteria.andShopTitleLike("%" + searchShopMain.getShopTitle().trim() + "%");
        }
        if (StringUtils.isNotBlank(searchShopMain.getShopName())) {// 店铺名称
            criteria.andShopNameLike("%" + searchShopMain.getShopName().trim() + "%");
        }
        if (StringUtils.isNotBlank(searchShopMain.getStatus())) {// 店铺状态
            example.setStatus(searchShopMain.getStatus());
        }
        if (StringUtils.isNotBlank(searchShopMain.getAuditStatus())) {// 审核状态
            criteria.andAuditStatusLike("%" + searchShopMain.getAuditStatus().trim() + "%");
        }
        if (StringUtils.isNotBlank(searchShopMain.getCorporateName())) {// 商户名称
            example.setCorporateName(searchShopMain.getCorporateName().trim());
        }
        example.setOrderByClause("SHOP_ID DESC");

        try {
            int count = shopMainDao.countByExample(example);
            List<ShopMain> showList = shopMainDao.selectByExampleShopList(example, skip, max);
            page.setDataList(showList);
            page.setRecordCount(count);
            return showList;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ShopForExport> getSupplierShopInfoForExcel(ShopMain searchShopMain) throws ServiceException {
        ShopMainExample example = new ShopMainExample();
        Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(searchShopMain.getShopTitle())) {// 店铺标题
            criteria.andShopTitleLike("%" + searchShopMain.getShopTitle().trim() + "%");
        }
        if (StringUtils.isNotBlank(searchShopMain.getShopName())) {// 店铺名称
            criteria.andShopNameLike("%" + searchShopMain.getShopName().trim() + "%");
        }
        if (StringUtils.isNotBlank(searchShopMain.getStatus())) {// 店铺状态
            example.setStatus(searchShopMain.getStatus());
        }
        if (StringUtils.isNotBlank(searchShopMain.getAuditStatus())) {// 审核状态
            criteria.andAuditStatusLike("%" + searchShopMain.getAuditStatus().trim() + "%");
        }
        if (StringUtils.isNotBlank(searchShopMain.getCorporateName())) {// 商户名称
            example.setCorporateName(searchShopMain.getCorporateName().trim());
        }
        example.setOrderByClause("SHOP_ID DESC");

        try {
            return shopMainDao.selectSupplierShopInfoForExcel(example);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void exportExcelForSupplierShopList(ShopMain searchShopMain) throws ServiceException {
        OutputStream ops = null;
        // 设置excel工作表的将要显示的列标题
        String[] title = {
                "店铺标题", "店铺名称", "店铺状态", "审核状态",
                "申请时间", "商户名称", "经营品牌", "店铺负责人姓名",
                "店铺负责人电话", "店铺联系类型", "客服联系方式", "店铺类型"
        };

        try {
            List<ShopForExport> supplierList = getSupplierShopInfoForExcel(searchShopMain);
            String filePath = ConfigurationUtil.getString("exportExcelPath")
                    + File.separator + "supplierShopList.xls";

            // 创建Excel工作薄
            WritableWorkbook wwb;
            ops = new FileOutputStream(filePath);
            wwb = Workbook.createWorkbook(ops);
            // 添加第一个工作表并设置第一个Sheet的名字
            WritableSheet sheet = wwb.createSheet("供应商店铺信息表", 0);

            Label label;
            WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            WritableCellFormat wcf = new WritableCellFormat(wf);
            wcf.setBackground(Colour.YELLOW2);
            wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
            //将列标题循环添加到Label中
            for (int i = 0; i < title.length; i++) {
                label = new Label(i, 0, title[i], wcf);
                sheet.addCell(label);
            }

            wcf = new WritableCellFormat();
            wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            for (int i = 0; i < supplierList.size(); i++) {
                ShopForExport export = supplierList.get(i);
                sheet.addCell(new Label(0, i + 1, export.getShopTitle(), wcf));
                sheet.addCell(new Label(1, i + 1, export.getShopName(), wcf));
                sheet.addCell(new Label(2, i + 1, StringUtils.isNotBlank(export.getStatus())
                        ? ShopMainStatusMap.getProductStatusValue(export.getStatus()) : "--", wcf));
                sheet.addCell(new Label(3, i + 1, StringUtils.isNotBlank(export.getAuditStatus())
                        ? ShopAuditStatusMap.getProductStatusValue(export.getAuditStatus()) : "--", wcf));
                sheet.addCell(new Label(4, i + 1, export.getApplyTime() != null
                        ? dateFormat.format(export.getApplyTime()) : "--", wcf));
                sheet.addCell(new Label(5, i + 1, export.getCorporateName(), wcf));
                sheet.addCell(new Label(6, i + 1, export.getManageBrand(), wcf));
                sheet.addCell(new Label(7, i + 1, export.getPrincipalName(), wcf));
                sheet.addCell(new Label(8, i + 1, export.getLinkmanMobile(), wcf));
                sheet.addCell(new Label(9, i + 1, export.getServiceType() != null
                        ? ShopMainServiceTypeMap.getValue(export.getServiceType()) : "--", wcf));
                sheet.addCell(new Label(10, i + 1, export.getServiceQq(), wcf));
                sheet.addCell(new Label(11, i + 1, export.getShopType() != null
                        ? ShopMainTypeMap.getValue(export.getShopType()) : "--", wcf));
            }
            wwb.write();
            wwb.close();
        } catch (Exception e) {
            throw new ServiceException(e);
        } finally {
            try {
                if (ops != null) {
                    ops.flush();
                    ops.close();
                }
            } catch (IOException e) {
            }
        }
    }

    @Override
    public ShopMain supplierShopView(Long shopId) throws ServiceException {
        try {
            return shopMainDao.selectByPrimaryKey(shopId);
        } catch (Exception e) {
            logger.error("根据店铺id查询店铺信息出现异常" + e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateSupplierShopStatus(ShopMain shopMain) throws ServiceException {
        try {
            int count = shopMainDao.updateByPrimaryKeySelective(shopMain);
            return count > 0;
        } catch (Exception e) {
            logger.error("启用或停用店铺出现异常" + e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean auditSupplierShopSer(ShopMain shopMain, String ckType) throws ServiceException {
        NewsCategory news = new NewsCategory();// 类别
        ShopNewsCategory shopNews = new ShopNewsCategory();// 店铺关联
        String newsCategoryList[] = {"资讯", "公告"};
        String keyIdList[] = {"", ""};// 存储类别主键id
        int count = 0;

        try {
            // 执行审核不通过操作
            if (ckType == null) {
                count = shopMainDao.updateByPrimaryKeySelective(shopMain);
                return count > 0;
            }

            // 根据店铺id查询店铺信息
            ShopMain shopMainInfo = supplierShopView(shopMain.getShopId());
            String ck = supplierRemoteService.addSupplierRelation(Lists.newArrayList(shopMainInfo));

            // 如果店铺发布不成功就执行审核操作
            if (!"success".equals(ck)) {
                return Boolean.FALSE;
            }

            Long supplierId = shopMainInfo.getSupplierId();
            // 资讯类别数
            Integer newCount = newsCategoryDao.selectNewCategorySupplierIdCount(supplierId);

            // 店铺绑定资讯类别数
            Long shopId = shopMainInfo.getShopId();
            Integer shopNewsCount = shopNewsCategoryDao.selectByShopIdCount(shopId);

            if (newCount == 0 && shopNewsCount == 0) {
                news.setSupplierId(supplierId);
                SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = fm.format(new Date());
                news.setCreateTime(fm.parse(dateString));// 创建时间
                news.setCreateUser(shopMainInfo.getApplyUser());// 用户id
                for (int i = 0; i < newsCategoryList.length; i++) {
                    news.setNewsCategoryName(newsCategoryList[i]);
                    Long keyId = newsCategoryDao.insertSelective(news);// 插入类别返回主键
                    keyIdList[i] = keyId.toString();
                }

                for (int j = 0; j < keyIdList.length; j++) {
                    shopNews.setNewsCategoryId(Long.parseLong(keyIdList[j]));// 设置类别id
                    shopNews.setShopId(shopId);// 设置店铺id
                    shopNewsCategoryDao.insert(shopNews);// 插入资讯关联表
                }
            }
            count = shopMainDao.updateByPrimaryKeySelective(shopMain);
            return count > 0;
        } catch (Exception e) {
            logger.error("供应商店铺审核修改店铺状态信息出现异常" + e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeShopMainNotify(List<Long> ids, String opType) throws ServiceException {
        if (ids == null || ids.isEmpty() || StringUtils.isBlank(opType)) {
            throw new ServiceException("参数为空!");
        }
        sendMessageSender.sendMessage("2004", ids, opType);
    }

    @Override
    public Map<String, String> querySupplierShop() throws ServiceException {
        try {
            List<ShopMain> tempList = shopMainDao.querySupplierShop();

            if (CollectionUtils.isNotEmpty(tempList)) {
                Map<String, String> dataMap = new HashMap();
                for (ShopMain shopMain : tempList) {
                    dataMap.put(String.valueOf(shopMain.getShopId()), shopMain.getShopTitle());
                }
                return dataMap;
            }
            return null;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}
