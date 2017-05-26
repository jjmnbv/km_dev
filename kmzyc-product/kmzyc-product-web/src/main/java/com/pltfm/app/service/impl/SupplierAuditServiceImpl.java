package com.pltfm.app.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.supplier.enums.SuppliersType;
import com.kmzyc.supplier.maps.SuppliersTypeMap;
import com.kmzyc.supplier.model.MerchantInfo;
import com.kmzyc.supplier.model.MerchantInfoOrSuppliers;
import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.supplier.model.SuppliersAvailableCategorys;
import com.kmzyc.supplier.model.SuppliersCertificate;
import com.kmzyc.supplier.model.SuppliersInfo;
import com.kmzyc.supplier.model.SuppliersWarehouse;
import com.kmzyc.supplier.model.User;
import com.kmzyc.cms.remote.service.SupplierRemoteService;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.kmzyc.user.remote.service.SpecialistRemoteService;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.pltfm.app.dao.ShopMainDAO;
import com.pltfm.app.dao.SupplierAuditDAO;
import com.pltfm.app.dao.SuppliersAvailableCategorysDAO;
import com.pltfm.app.dao.SuppliersCertificateDAO;
import com.pltfm.app.dao.SuppliersInfoDAO;
import com.pltfm.app.dao.SuppliersWarehouseDAO;
import com.pltfm.app.maps.SuppliersStatusMap;
import com.pltfm.app.service.ProductService;
import com.pltfm.app.service.SupplierAuditService;
import com.pltfm.app.util.CodeUtils;
import com.pltfm.app.vobject.Category;
import com.pltfm.app.vobject.SupplierCategoryName;
import com.pltfm.app.vobject.SupplierForExport;
import com.pltfm.app.vobject.SupplierWarehouseName;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Repository("supplierAuditService")
public class SupplierAuditServiceImpl implements SupplierAuditService {

    private static Logger logger = Logger.getLogger(SupplierAuditServiceImpl.class);

    @Resource
    private SupplierAuditDAO supplierAuditDao;

    @Resource
    private SuppliersInfoDAO suppliersInfoDao;

    @Resource
    private SuppliersCertificateDAO suppliersCertificateDAO;

    @Resource
    private SuppliersAvailableCategorysDAO suppliersAvailableCategorysDao;

    @Resource
    private SuppliersWarehouseDAO suppliersWarehouseDAO;

    @Resource
    private ProductService productService;

    @Resource
    private ShopMainDAO shopMainDao;

    @Resource
    private SpecialistRemoteService specialistRemoteService;

    @Resource
    private SupplierRemoteService supplierRemoteService;

    @Override
    public void showSupplierList(MerchantInfoOrSuppliers suppliersInfo, Page page) throws ServiceException {
        // 公司名称查询
        if (StringUtils.isBlank(suppliersInfo.getCorporateName())) {
            suppliersInfo.setCorporateName(null);
        }
        if (StringUtils.isBlank(suppliersInfo.getContactsName())) {
            suppliersInfo.setContactsName(null);
        }
        if (StringUtils.isNotBlank(suppliersInfo.getProvince())) {
            if (suppliersInfo.getProvince().indexOf("全部") == -1) {
                if (StringUtils.isBlank(suppliersInfo.getCity())) {
                    suppliersInfo.setCity(null);
                }
                if (StringUtils.isBlank(suppliersInfo.getArea())) {
                    suppliersInfo.setArea(null);
                }
            } else {
                suppliersInfo.setProvince(null);
                suppliersInfo.setCity(null);
                suppliersInfo.setArea(null);
            }
        }
        if (StringUtils.isBlank(suppliersInfo.getCorporateLocation())) {
            suppliersInfo.setCorporateLocation(null);
        }
        if (null != suppliersInfo.getStatus() && suppliersInfo.getStatus().longValue() == 0) {
            suppliersInfo.setStatus(null);
        }
        if (null != suppliersInfo.getEnterpriseStatus()) {// 企业状态
            if (suppliersInfo.getEnterpriseStatus().longValue() != 0
                    && suppliersInfo.getEnterpriseStatus().longValue() != 1) {
                suppliersInfo.setEnterpriseStatus(null);
            }
        }

        try {
            List<MerchantInfoOrSuppliers> showList = supplierAuditDao.selectByExample(suppliersInfo, page);
            int count = supplierAuditDao.countByExample(suppliersInfo);
            page.setDataList(showList);
            page.setRecordCount(count);
        } catch (Exception e) {
            logger.error("查询供应商信息列表出现异常" + e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public SuppliersInfo findById(Long merId) throws ServiceException {
        try {
            return suppliersInfoDao.selectByMerchantId(merId);
        } catch (Exception e) {
            logger.error("根据商户id查询供应商信息出现异常" + e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<SuppliersCertificate> findBySupplierId(Long suppId) throws ServiceException {
        try {
            return suppliersCertificateDAO.selectSuppIdList(suppId);
        } catch (Exception e) {
            logger.error("根据供应商id查询资质文件出现异常" + e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public MerchantInfo findByKey(Long merchantId) throws ServiceException {
        try {
            return supplierAuditDao.selectByPrimaryKey(merchantId);
        } catch (Exception e) {
            logger.error("根据id查询商户信息出现异常" + e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public boolean updateSupplierStatus(SuppliersInfo suppliersInfo, String categoryIds,
                                        Long warehouseId, String CommissionRatios, Long loginId,
                                        String sacIds, String goryIdsValues1) throws ServiceException {
        boolean flg = true;
        SuppliersWarehouse newWare = new SuppliersWarehouse();
        SuppliersAvailableCategorys newCategoryUpdate = new SuppliersAvailableCategorys();
        SuppliersAvailableCategorys newCategorys = new SuppliersAvailableCategorys();
        SuppliersAvailableCategorys newCategory = new SuppliersAvailableCategorys();
        List<SuppliersAvailableCategorys> ckCategorysLists = null;
        List<SuppliersAvailableCategorys> ckCategorysList = null;
        try {

            String[] commissionRatioList = CommissionRatios.split(",");// 类目对应的佣金比例数组
            String sacIdsList[] = sacIds.split(",");// 供应商分配类目表id
            if (!"".equals(goryIdsValues1) && null != goryIdsValues1) {
                String[] categoryIdList1 = goryIdsValues1.split(",");
                newCategory.setSupplierId(suppliersInfo.getSupplierId());
                for (int i = 0; i < categoryIdList1.length; i++) {
                    newCategory.setCategoryId(Long.parseLong(categoryIdList1[i]));
                    suppliersAvailableCategorysDao.deleteSupplierCategory(newCategory);
                }
            }
            if (!"".equals(categoryIds) && null != categoryIds) {
                String[] categoryIdList = categoryIds.split(",");// 类目数组
                for (int p = 0; p < sacIdsList.length; p++) {
                    if (sacIdsList[p] != null && !"".equals(sacIdsList[p])) {
                        newCategoryUpdate.setSacId(Long.parseLong(sacIdsList[p]));
                        newCategoryUpdate.setStatus((short) 1);
                        if (!"".equals(commissionRatioList[p])) {
                            newCategoryUpdate.setCommissionRatio(new BigDecimal(commissionRatioList[p])
                                    .divide(new BigDecimal("100")));
                        } else {
                            newCategoryUpdate.setCommissionRatio(null);
                        }
                        suppliersAvailableCategorysDao.updateByPrimaryKeySelective(newCategoryUpdate);
                    }
                }

                newCategorys.setSupplierId(suppliersInfo.getSupplierId());
                for (int i = 0; i < categoryIdList.length; i++) {
                    newCategory.setCategoryId(Long.parseLong(categoryIdList[i]));
                    newCategory.setSupplierId(suppliersInfo.getSupplierId());
                    newCategory.setStatus((short) 1);
                    if (!"".equals(commissionRatioList[i])) {
                        newCategory.setCommissionRatio(new BigDecimal(commissionRatioList[i])
                                .divide(new BigDecimal("100")));// 插入佣金比例
                    } else {
                        newCategory.setCommissionRatio(null);
                    }
                    ckCategorysList = suppliersAvailableCategorysDao.existsSupplierCategories(newCategory);// 只存在一条记录
                    ckCategorysLists = suppliersAvailableCategorysDao.existsSupplierCategories(newCategorys);// 存在多条记录
                    if (ckCategorysList.size() == 0) {
                        suppliersAvailableCategorysDao.insertSelective(newCategory);// 插入类目信息
                    }
                }

                for (int g = 0; g < ckCategorysLists.size(); g++) {
                    newCategory.setSupplierId(suppliersInfo.getSupplierId());
                    boolean ckDele = false;
                    for (int y = 0; y < categoryIdList.length; y++) {
                        if (Long.parseLong(categoryIdList[y]) == ckCategorysLists.get(g).getCategoryId()) {
                            ckDele = true;
                        }
                    }
                    if (ckDele == true) {
                        newCategory.setCategoryId(ckCategorysLists.get(g).getCategoryId());
                    } else {
                        newCategory.setCategoryId(ckCategorysLists.get(g).getCategoryId());
                        suppliersAvailableCategorysDao.deleteSupplierCategory(newCategory);
                    }
                }
            }
            newWare.setSupplierId(suppliersInfo.getSupplierId());
            newWare.setWarehouseId(warehouseId);
            suppliersWarehouseDAO.insertSelective(newWare);// 插入仓库
            suppliersInfoDao.updateByPrimaryKeySelective(suppliersInfo);// 修改供应状态为通过
        } catch (Exception e) {
            logger.error("供应商审核出现异常" + e.getMessage(), e);
            throw new ServiceException(e);
        }
        return flg;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public boolean supplierUpdate(MerchantInfo merchantOrSupplier, SuppliersInfo supplier,
                                  String categoryValueList, SuppliersWarehouse suppliersWarehouse,
                                  String CommissionRatios, String sacIds, String categoryValueList1) throws ServiceException {
        boolean flg = true;
        SuppliersAvailableCategorys newCategorys = new SuppliersAvailableCategorys();
        SuppliersAvailableCategorys newCategory = new SuppliersAvailableCategorys();
        try {
            if (null == merchantOrSupplier.getRegisterBankroll()) {
                merchantOrSupplier.setRegisterBankroll(new BigDecimal("0"));
            }
            // 修改商户
            supplierAuditDao.updateByPrimaryKeySelective(merchantOrSupplier);

            // 修改供应商
            suppliersInfoDao.updateByPrimaryKeySelective(supplier);

            // 修改仓库
            if (null != suppliersWarehouse.getSupWarehouseId()) {
                suppliersWarehouseDAO.updateByPrimaryKeySelective(suppliersWarehouse);
            }

            SuppliersAvailableCategorys newCategoryUpdate = new SuppliersAvailableCategorys();
            List<SuppliersAvailableCategorys> ckCategorysLists = null;
            List<SuppliersAvailableCategorys> ckCategorysList = null;

            String commissionRatiosList[] = CommissionRatios.split(",");
            String sacIdsList[] = sacIds.split(",");
            if (null != categoryValueList1 && !"".equals(categoryValueList1)) {
                String categoryValueLists[] = categoryValueList1.split(",");
                newCategory.setSupplierId(supplier.getSupplierId());
                for (int i = 0; i < categoryValueLists.length; i++) {
                    newCategory.setCategoryId(Long.parseLong(categoryValueLists[i]));
                    suppliersAvailableCategorysDao.deleteSupplierCategory(newCategory);
                }
                return true;
            }
            if (null != categoryValueList && !"".equals(categoryValueList)) {
                for (int p = 0; p < sacIdsList.length; p++) {
                    if (sacIdsList[p] != null && !"".equals(sacIdsList[p])) {
                        newCategoryUpdate.setSacId(Long.parseLong(sacIdsList[p]));
                        newCategoryUpdate.setStatus((short) 1);
                        newCategoryUpdate.setIsClose((short) 0);
                        if (!"undefined".equals(commissionRatiosList[p])) {
                            newCategoryUpdate.setCommissionRatio(new BigDecimal(commissionRatiosList[p])
                                    .divide(new BigDecimal("100")));
                        } else {
                            newCategoryUpdate.setCommissionRatio(null);
                        }
                        suppliersAvailableCategorysDao.updateByPrimaryKeySelective(newCategoryUpdate);
                    }
                }

                String gor[] = categoryValueList.split(",");
                if (gor.length > 0) {
                    newCategorys.setSupplierId(supplier.getSupplierId());
                    for (int i = 0; i < gor.length; i++) {
                        newCategory.setCategoryId(Long.parseLong(gor[i]));
                        newCategory.setSupplierId(supplier.getSupplierId());
                        newCategory.setStatus((short) 1);
                        newCategoryUpdate.setIsClose((short) 0);
                        if (!"undefined".equals(commissionRatiosList[i])) {
                            newCategory.setCommissionRatio(new BigDecimal(commissionRatiosList[i])
                                    .divide(new BigDecimal("100")));// 插入佣金比例
                        } else {
                            newCategory.setCommissionRatio(null);
                        }
                        ckCategorysList = suppliersAvailableCategorysDao.existsSupplierCategories(newCategory);// 只存在一条记录
                        ckCategorysLists = suppliersAvailableCategorysDao.existsSupplierCategories(newCategorys);// 存在多条记录
                        if (ckCategorysList.size() == 0) {
                            suppliersAvailableCategorysDao.insertSelective(newCategory);// 插入类目信息
                        }
                    }


                    for (int g = 0; g < ckCategorysLists.size(); g++) {
                        newCategory.setSupplierId(supplier.getSupplierId());
                        boolean ckDele = false;
                        for (int y = 0; y < gor.length; y++) {
                            if (Long.parseLong(gor[y]) == ckCategorysLists.get(g).getCategoryId()) {
                                ckDele = true;
                            }
                        }
                        if (ckDele) {
                            newCategory.setCategoryId(ckCategorysLists.get(g).getCategoryId());
                        } else {
                            newCategory.setCategoryId(ckCategorysLists.get(g).getCategoryId());
                            suppliersAvailableCategorysDao.deleteSupplierCategory(newCategory);
                        }
                    }

                }
            }
        } catch (Exception e) {
            logger.error("修改供应商出现异常" + e.getMessage(), e);
            throw new ServiceException(e);
        }
        return flg;
    }

    /**
     * 根据资质文件ID删除上传但未审核的文件
     *
     * @param scId 资质文件ID
     * @throws ServiceException
     */
    @Override
    public boolean deleteSupplierCertificate(Long scId) throws ServiceException {
        try {
            int count = suppliersCertificateDAO.deleteByPrimaryKey(scId);
            return count > 0;
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public SuppliersCertificate findCertificateByScId(Long scId) throws ServiceException {
        try {
            return suppliersCertificateDAO.selectByPrimaryKey(scId);
        } catch (SQLException e) {
            logger.error("保存供应商资质文件出错!" + e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public List<SuppliersAvailableCategorys> showCategoriesLists(Long suppId) throws ServiceException {
        SuppliersAvailableCategorys gro = new SuppliersAvailableCategorys();
        gro.setSupplierId(suppId);
        List<SuppliersAvailableCategorys> categoriesList = null;
        try {
            categoriesList = suppliersAvailableCategorysDao.findSupplierCategories(gro);
        } catch (Exception e) {
            logger.error("" + e.getMessage(), e);
            throw new ServiceException(e);
        }
        return categoriesList;
    }

    @Override
    public List<SuppliersAvailableCategorys> showAllCategoriesLists() throws ServiceException {
        try {
            return suppliersAvailableCategorysDao.findAllSupplierCategories();
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<Long, String> applySupplierCategoriesMap(Long supplierId) throws ServiceException {
        Map<Long, String> cateMap = new LinkedHashMap<Long, String>();
        try {
            List<Category> list = suppliersAvailableCategorysDao.applySuppliersCategories(supplierId);
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    cateMap.put(list.get(i).getCategoryId(), list.get(i).getCategoryName());
                }
            }
        } catch (SQLException e) {
            logger.error("查询供应商申请显示类目显示出现异常" + e.getMessage(), e);
            throw new ServiceException(e);
        }
        return cateMap;
    }

    @Override
    public boolean notPass(SuppliersInfo info) throws ServiceException {
        boolean flg = false;
        try {
            int count = suppliersInfoDao.updateByPrimaryKeySelective(info);
            if (count > 0) {
                flg = true;
            }
        } catch (Exception e) {
            logger.error("供应商审核不通过出现异常" + e.getMessage(), e);
            throw new ServiceException(e);
        }
        return flg;
    }

    @Override
    public List<SuppliersWarehouse> selectBySupplierId(Long supplierId) throws ServiceException {
        try {
            return suppliersWarehouseDAO.selectByPrimarySupplierId(supplierId);
        } catch (Exception e) {
            logger.error("根据供应商id查询仓库信息出现异常" + e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void saveSupplierCertificate(SuppliersCertificate suppliersCertificate) throws ServiceException {
        try {
            suppliersCertificateDAO.insertSelective(suppliersCertificate);
            logger.info("保存供应商资质文件成功!");
        } catch (SQLException e) {
            logger.error("保存供应商资质文件出错!" + e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public SuppliersCertificate findBySuppIdPath(String paths) throws ServiceException {
        try {
            return suppliersCertificateDAO.selectSuppIdPath(paths);
        } catch (Exception e) {
            logger.error("根据供应商id查询资质文件出现异常" + e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public User findByUserNameObj(String userName) throws ServiceException {
        try {
            return supplierAuditDao.selectUserByUserName(userName);
        } catch (SQLException e) {
            logger.error("根据用户名查询用户信息出现异常!" + e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public MerchantInfo findByUserId(MerchantInfo merchantInfo) throws ServiceException {
        try {
            return supplierAuditDao.selectByUserLoginId(merchantInfo);
        } catch (SQLException e) {
            logger.error("根据用户id查询商户信息出现异常!" + e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public synchronized boolean supplierAddTwo(MerchantInfo merchantInfo, SuppliersInfo suppliersInfo,
                                               String supplierCategories) throws ServiceException {
        boolean flg = true;
        SuppliersInfo newSuppliersInfo = new SuppliersInfo();
        SuppliersAvailableCategorys newCategory = new SuppliersAvailableCategorys();
        // final int supplierType = 5;//传入接口中供应商类型
        try {
            MerchantInfo mer = supplierAuditDao.selectByUserLoginId(merchantInfo);
            if (null != mer) {
                return true;
            }
            // 更新会员升级为供应商接口
            Long merId = supplierAuditDao.insertSelective(merchantInfo);
            suppliersInfo.setMerchantId(merId);// 设置商户id到供应商表
            suppliersInfo.setCompanyShowName(merchantInfo.getCorporateName());
            suppliersInfoDao.insertSelective(suppliersInfo);// 插入数据到产品库的供应商表
            newSuppliersInfo.setMerchantId(merId);
            SuppliersInfo SuppliersInfo = suppliersInfoDao.selectByMerchantId(newSuppliersInfo);// 根据商户id查询供应商数据

            if (!"".equals(supplierCategories) && null != supplierCategories) {
                String gor[] = supplierCategories.split(",");
                newCategory.setSupplierId(SuppliersInfo.getSupplierId());
                for (int i = 0; i < gor.length; i++) {
                    if (StringUtils.isNotBlank(gor[i])) {
                        newCategory.setCategoryId(Long.parseLong(gor[i].split("_")[0]));
                        if (gor[i].split("_")[1] == null || "null".equals(gor[i].split("_")[1])) {
                            newCategory.setCommissionRatio(null);
                        } else {
                            newCategory.setCommissionRatio(new BigDecimal(gor[i].split("_")[1])
                                    .divide(new BigDecimal("100")));
                        }
                        suppliersAvailableCategorysDao.insertSelective(newCategory);// 插入类目信息
                    }
                }
            }
            specialistRemoteService.upToSupplier(Integer.parseInt(merchantInfo.getLoginId().toString()),
                    CodeUtils.SUPPLIERTYPE, merId);// 调用会员升级为供应商接口
        } catch (Exception e) {
            logger.error("插入供应商信息出现异常!" + e.getMessage());
            throw new ServiceException(e);
        }
        return flg;
    }

    @Override
    public SuppliersInfo obtainSupplierId(Long loginId) throws ServiceException {
        MerchantInfo merchantInfo = new MerchantInfo();
        SuppliersInfo newSuppliersInfo = new SuppliersInfo();
        merchantInfo.setLoginId(loginId);
        try {
            MerchantInfo mer = supplierAuditDao.selectByUserLoginId(merchantInfo);
            newSuppliersInfo.setMerchantId(mer.getMerchantId());
            return suppliersInfoDao.selectByMerchantId(newSuppliersInfo);// 根据商户id查询供应商数据
        } catch (Exception e) {
            logger.error("根据登录id查询供应商出现异常!" + e.getMessage());
            throw new ServiceException(e);
        }

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateSupplierCategoryName(Integer level) throws ServiceException {
        try {
            List<SupplierCategoryName> suppliersCategoryNameList = suppliersInfoDao.getSupplierCategoryName(level);
            suppliersCategoryNameList.stream().forEach(supplier -> {
                List<String> categoryNameList = supplier.getCategoryNameList();
                if (CollectionUtils.isNotEmpty(categoryNameList)) {
                    supplier.setCategoryName(StringUtils.join(categoryNameList, ","));
                }
                supplier.setLevelType(level);
            });
            suppliersInfoDao.saveSupplierCategoryName(suppliersCategoryNameList);
        } catch (SQLException e) {
            logger.error("更新供应商类名称失败：{}", e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateSuppliersWarehouseName() throws ServiceException {
        try {
            List<SupplierWarehouseName> supplierWarehouseNameList = suppliersInfoDao.getSuppliersWarehouseName();
            supplierWarehouseNameList.stream().forEach(supplier -> {
                List<String> warehouseNameList = supplier.getWarehouseNameList();
                if (CollectionUtils.isNotEmpty(warehouseNameList)) {
                    supplier.setWarehouseName(StringUtils.join(warehouseNameList, ","));
                }
            });
            suppliersInfoDao.saveSuppliersWarehouseName(supplierWarehouseNameList);
        } catch (SQLException e) {
            logger.error("更新供应商仓库名称失败：{}", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<String, String> supplierIdAndMerchantNameMap() throws ServiceException {
        List<MerchantInfoOrSuppliers> suppIdAndNameList = null;
        Map<String, String> suppIdAndNameMap = new LinkedHashMap<String, String>();
        MerchantInfoOrSuppliers record = new MerchantInfoOrSuppliers();
        try {
            record.setStatus(Short.parseShort("3"));// 审核通过的供应商
            record.setEnterpriseStatus(Short.parseShort("1"));
            suppIdAndNameList = supplierAuditDao.getSupplierByCloseStatus(record);
            //suppIdAndNameMap.put("221", "康美自营");
            for (int i = 0; i < suppIdAndNameList.size(); i++) {
                if (suppIdAndNameList.get(i).getSupplierId().intValue() == 221) {
                    continue;
                }
                suppIdAndNameMap.put(String.valueOf(suppIdAndNameList.get(i).getSupplierId()),
                        suppIdAndNameList.get(i).getCorporateName());
            }
        } catch (Exception e) {
            logger.error("查询供应商信息和商户信息出现异常map!" + e.getMessage());
            throw new ServiceException(e);
        }
        return suppIdAndNameMap;
    }

    @Override
    public MerchantInfo findLoginId(MerchantInfo merchantInfo) throws ServiceException {
        try {
            return supplierAuditDao.selectByUserLoginId(merchantInfo);
        } catch (SQLException e) {
            logger.error("根据用户id查询商户信息出现异常" + e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public boolean updateLoginStatus(Short businessStatus, Short closeStatus, Short loginStatus, Long supplierId)
            throws ServiceException {
        boolean flg = false;
        try {
            List<ShopMain> shopMainList = shopMainDao.selectBySupplierId(supplierId);
            SuppliersInfo suppliersInfo = new SuppliersInfo();
            if (businessStatus != 0) {
                suppliersInfo.setBusinessStatus(businessStatus);
                if (businessStatus == 1) {// 业务关闭按钮
                    List<Long> supplierIds = new ArrayList<Long>();
                    supplierIds.add(supplierId);
                    productService.downShelfForSupplier(supplierIds);// 商品下架
                    if (shopMainList.size() > 0) {
                        supplierRemoteService.closeShop(shopMainList.get(0));
                    }
                } else {// 打开店铺链接
                    if (shopMainList.size() > 0) {
                        supplierRemoteService.reopenShop(shopMainList.get(0));
                    }
                }
            }
            if (closeStatus != 0) {
                suppliersInfo.setCloseStatus(closeStatus);
                if (closeStatus == 1) {// 关闭
                    List<Long> supplierIds = new ArrayList<Long>();
                    supplierIds.add(supplierId);
                    productService.downShelfForSupplier(supplierIds);// 商品下架
                    if (shopMainList.size() > 0) {
                        supplierRemoteService.closeShop(shopMainList.get(0));
                    }
                } else {// 打开店铺链接
                    if (shopMainList.size() > 0) {
                        supplierRemoteService.reopenShop(shopMainList.get(0));
                    }
                }
            }
            if (loginStatus != 0) {
                suppliersInfo.setLoginStatus(loginStatus);
            }
            suppliersInfo.setSupplierId(supplierId);
            int count = suppliersInfoDao.updateByPrimaryKeySelective(suppliersInfo);
            if (count > 0) {
                flg = true;
            }
        } catch (Exception e) {
            logger.error("修改登录状态出现异常" + e.getMessage(), e);
            throw new ServiceException(e);
        }
        return flg;
    }

    @Override
    public boolean selectByCompanyName(MerchantInfo merchant) throws ServiceException {
        try {
            List<MerchantInfo> list = supplierAuditDao.selectByCompanyName1(merchant);
            return null != list && list.size() > 0;// 有一样的公司名称
        } catch (SQLException e) {
            logger.error("根据公司名称查询数据出现异常" + e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<SupplierForExport> getSupplierListForExcel(MerchantInfoOrSuppliers suppliersInfo) throws ServiceException {
        if (StringUtils.isBlank(suppliersInfo.getCorporateName())) {
            suppliersInfo.setCorporateName(null);
        }
        if (StringUtils.isBlank(suppliersInfo.getContactsName())) {
            suppliersInfo.setContactsName(null);
        }
        if (StringUtils.isBlank(suppliersInfo.getCorporateLocation())) {
            suppliersInfo.setCorporateLocation(null);
        }
        if (null != suppliersInfo.getStatus() && suppliersInfo.getStatus().longValue() == 0l) {
            suppliersInfo.setStatus(null);
        }
        // 企业状态
        if (null != suppliersInfo.getEnterpriseStatus()
                && suppliersInfo.getEnterpriseStatus().longValue() != 0
                && suppliersInfo.getEnterpriseStatus().longValue() != 1) {
            suppliersInfo.setEnterpriseStatus(null);
        }

        try {
            return supplierAuditDao.getSupplierListForExcel(suppliersInfo);
        } catch (Exception e) {
            logger.error("查询供应商信息列表出现异常" + e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void exportExcelForSupplierAuditList(List<SupplierForExport> supplierList, Integer exportType)
            throws ServiceException {
        OutputStream ops = null;
        // 设置excel工作表的将要显示的列标题
        String[] title = {
                "联系人手机", "公司名称", "组织机构代码", "创建日期", "营业执照注册号",
                "公司所在地", "企业状态", "商户类型", "申请状态", "固定电话",
                "税务登记证号", "法定营业范围", "开户银行", "银行开户名","银行账号",
                "注册资金", "商户一级经营类目", "商户二级经营类目", "商户仓库"
        };

        try {
            String filePath = ConfigurationUtil.getString("exportExcelPath") + File.separator + "supplierAuditList.xls";
            // 创建Excel工作薄
            WritableWorkbook wwb;
            ops = new FileOutputStream(filePath);
            wwb = Workbook.createWorkbook(ops);
            // 添加第一个工作表并设置第一个Sheet的名字
            WritableSheet sheet = wwb.createSheet("供应商信息表", 0);

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
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            for (int i = 0; i < supplierList.size(); i++) {
                SupplierForExport export = supplierList.get(i);
                sheet.addCell(new Label(0, i + 1, export.getMobile(), wcf));
                sheet.addCell(new Label(1, i + 1, export.getCorporateName(), wcf));
                sheet.addCell(new Label(2, i + 1, export.getOrganizationCode(), wcf));
                sheet.addCell(new Label(3, i + 1, export.getCreateDate() != null
                        ? dateFormat.format(export.getCreateDate()) : "--", wcf));
                sheet.addCell(new Label(4, i + 1, export.getBusinessLicenceRegister(), wcf));
                sheet.addCell(new Label(5, i + 1, export.getProvince() + export.getCity()
                        + export.getArea() + export.getCorporateLocation(), wcf));
                sheet.addCell(new Label(6, i + 1, ("0").equals(export.getEnterpriseStatus()) ? "关闭" : "开启", wcf));
                sheet.addCell(new Label(7, i + 1, export.getSupplierType() != null
                        ? SuppliersTypeMap.getValue(export.getSupplierType()) : "--", wcf));
                sheet.addCell(new Label(8, i + 1, export.getSupplierStatus() != null
                        ? SuppliersStatusMap.getValue(export.getSupplierStatus()) : "--", wcf));
                sheet.addCell(new Label(9, i + 1, export.getFixedPhone(), wcf));
                sheet.addCell(new Label(10, i + 1, export.getTaxRegistrationCno(), wcf));
                sheet.addCell(new Label(11, i + 1, export.getBusinessScope(), wcf));
                sheet.addCell(new Label(12, i + 1, export.getBankOfDeposit(), wcf));
                sheet.addCell(new Label(13, i + 1, export.getBankAccountName(), wcf));
                sheet.addCell(new Label(14, i + 1, export.getCompanyAccount(), wcf));
                sheet.addCell(new Label(15, i + 1, export.getRegisterBankroll() != null
                        ? export.getRegisterBankroll().toString() : "--", wcf));
                sheet.addCell(new Label(16, i + 1, export.getFirstCategoryName(), wcf));
                sheet.addCell(new Label(17, i + 1, export.getSecondCategoryName(), wcf));
                sheet.addCell(new Label(18, i + 1, export.getWarehouseName(), wcf));
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

    /**
     * clob to string 大字符串格式转换string
     *
     * @param clob
     * @return 大字符串
     */
    private String Clob2String(Clob clob) {// Clob转换成String 的方法
        if (clob == null) {
            return null;
        }

        String content = null;
        StringBuffer stringBuf = new StringBuffer();
        Reader inStream = null;
        try {
            int length = 0;
            inStream = clob.getCharacterStream(); // 取得大字侧段对象数据输出流
            char[] buffer = new char[10];
            while ((length = inStream.read(buffer)) != -1) { // 读取数据库 //每10个10个读取
                for (int i = 0; i < length; i++) {
                    stringBuf.append(buffer[i]);
                }
            }

            content = stringBuf.toString();
        } catch (Exception ex) {
            logger.error("大字符串格式转换string失败{}：", ex);
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }

    @Override
    public void queryMerList(MerchantInfoOrSuppliers mer, Page page) throws ServiceException {
        mer.setSupplierType(SuppliersType.SELL.getStatus());
        try {
            List<MerchantInfoOrSuppliers> list = supplierAuditDao.queryMerList(mer, page);
            page.setDataList(list);
            page.setRecordCount(supplierAuditDao.countItemMer(mer));
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void querySuppliersForInviteList(MerchantInfoOrSuppliers mer, Long activityId, Page page)
            throws ServiceException {
        mer.setSupplierType(SuppliersType.SELL.getStatus());
        try {
            List<MerchantInfoOrSuppliers> list =
                    supplierAuditDao.querySuppliersForInviteList(mer, activityId, page);
            page.setDataList(list);
            page.setRecordCount(supplierAuditDao.countSuppliersForInvite(mer, activityId));
        } catch (SQLException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public Integer getSupplierTypeByProductDraft(Long productId) throws ServiceException {
        try {
            return suppliersInfoDao.getSupplierTypeByProductDraft(productId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Integer getSupplierTypeByProduct(Long productId) throws ServiceException {
        try {
            return suppliersInfoDao.getSupplierTypeByProduct(productId);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

}