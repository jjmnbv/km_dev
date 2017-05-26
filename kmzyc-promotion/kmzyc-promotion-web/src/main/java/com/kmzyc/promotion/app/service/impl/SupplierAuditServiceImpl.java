package com.kmzyc.promotion.app.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.app.dao.SupplierAuditDAO;
import com.kmzyc.promotion.app.dao.SuppliersAvailableCategorysDAO;
import com.kmzyc.promotion.app.dao.SuppliersCertificateDAO;
import com.kmzyc.promotion.app.dao.SuppliersInfoDAO;
import com.kmzyc.promotion.app.dao.SuppliersWarehouseDAO;
import com.kmzyc.promotion.app.service.SupplierAuditService;
import com.kmzyc.promotion.app.vobject.Category;
import com.kmzyc.promotion.exception.ServiceException;
import com.kmzyc.supplier.model.MerchantInfo;
import com.kmzyc.supplier.model.MerchantInfoOrSuppliers;
import com.kmzyc.supplier.model.SuppliersAvailableCategorys;
import com.kmzyc.supplier.model.SuppliersCertificate;
import com.kmzyc.supplier.model.SuppliersInfo;
import com.kmzyc.supplier.model.SuppliersWarehouse;
import com.kmzyc.supplier.model.User;
import com.kmzyc.user.remote.service.SpecialistRemoteService;

@Repository("supplierAuditService")
@SuppressWarnings("unchecked")
public class SupplierAuditServiceImpl implements SupplierAuditService {

    // 日志记录
    private static final Logger logger = LoggerFactory.getLogger(SupplierAuditServiceImpl.class);
    @Resource
    private SpecialistRemoteService specialistRemoteService;

    @Resource
    private SupplierAuditDAO supplierAuditDAO;

    @Resource
    private SuppliersInfoDAO suppliersInfoDAO;

    @Resource
    private SuppliersCertificateDAO suppliersCertificateDAO;

    @Resource
    private SuppliersAvailableCategorysDAO suppliersAvailableCategorysDAO;

    @Resource
    private SuppliersWarehouseDAO suppliersWarehouseDAO;

    // 查询供应商和商户信息
    @Override
    public MerchantInfoOrSuppliers queryBySupplierId(Long supplierId) throws Exception {
        MerchantInfoOrSuppliers me = new MerchantInfoOrSuppliers();
        me.setSupplierId(supplierId);
        return supplierAuditDAO.queryBySupplierId(me);
    }

    /**
     * 查询供应商信息
     */
    @Override
    public void showSupplierLsit(MerchantInfoOrSuppliers suppliersInfo, Page page)
            throws Exception {

        if (null != suppliersInfo.getCorporateName()) {// 公司名称查询
            if (suppliersInfo.getCorporateName().length() == 0) {
                suppliersInfo.setCorporateName(null);
            }
        }
        if (null != suppliersInfo.getContactsName()) {
            if (suppliersInfo.getContactsName().length() == 0) {
                suppliersInfo.setContactsName(null);
            }
        }
        if (suppliersInfo.getProvince() != null) {
            if (suppliersInfo.getProvince().indexOf("全部") == -1) {
                if (null != suppliersInfo.getProvince()) {// 省份
                    if (suppliersInfo.getProvince().length() == 0) {
                        suppliersInfo.setProvince(null);
                    }
                }
                if (null != suppliersInfo.getCity()) {// 城市
                    if (suppliersInfo.getCity().length() == 0) {
                        suppliersInfo.setCity(null);
                    }
                }
                if (null != suppliersInfo.getArea()) {// 地区
                    if (suppliersInfo.getArea().length() == 0) {
                        suppliersInfo.setArea(null);
                    }
                }
            } else {
                suppliersInfo.setProvince(null);
                suppliersInfo.setCity(null);
                suppliersInfo.setArea(null);
            }
        }
        if (null != suppliersInfo.getCorporateLocation()) {
            if (suppliersInfo.getCorporateLocation().length() == 0) {
                suppliersInfo.setCorporateLocation(null);
            }
        }

        if (null != suppliersInfo.getStatus()) {// 联系人
            if (suppliersInfo.getStatus().longValue() == 0) {
                suppliersInfo.setStatus(null);
            }
        }

        if (null != suppliersInfo.getEnterpriseStatus()) {// 企业状态
            if (suppliersInfo.getEnterpriseStatus().longValue() != 0
                    && suppliersInfo.getEnterpriseStatus().longValue() != 1) {
                suppliersInfo.setEnterpriseStatus(null);
            }
        }
        try {
            List<MerchantInfoOrSuppliers> showList =
                    supplierAuditDAO.selectByExample(suppliersInfo, page);
            int count = supplierAuditDAO.countByExample(suppliersInfo);
            page.setDataList(showList);
            page.setRecordCount(count);
        } catch (Exception e) {
            logger.error("查询供应商信息列表出现异常" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 根据商户id查询供应商信息
     */
    @Override
    public SuppliersInfo findById(Long merId) throws Exception {
        SuppliersInfo suppliersInfo = null;
        try {
            suppliersInfo = suppliersInfoDAO.selectBymerId(merId);
        } catch (Exception e) {
            logger.error("根据商户id查询供应商信息出现异常" + e.getMessage(), e);
            throw e;
        }
        return suppliersInfo;
    }

    /**
     * 根据供应商id查询资质文件
     */
    @Override
    public List<SuppliersCertificate> findBySuppId(Long suppId) throws Exception {
        List<SuppliersCertificate> suppliersCertificateList = null;
        try {
            suppliersCertificateList = suppliersCertificateDAO.selectSuppIdList(suppId);
        } catch (Exception e) {
            logger.error("根据供应商id查询资质文件出现异常" + e.getMessage(), e);
            throw e;
        }
        return suppliersCertificateList;
    }

    /**
     * 根据id查询商户信息
     * 
     * @param MerchantId
     * @return
     * @throws Exception
     */
    @Override
    public MerchantInfo findByKey(Long merchantId) throws Exception {
        MerchantInfo mer = null;
        try {
            mer = supplierAuditDAO.selectByPrimaryKey(merchantId);
        } catch (Exception e) {
            logger.error("根据id查询商户信息出现异常" + e.getMessage(), e);
            throw e;
        }
        return mer;
    }

    /**
     * 供应商审核
     */
    @Override
    public boolean updateSupplierStatus(SuppliersInfo suppliersInfo, String categoryIds,
            Long warehouseId, String CommissionRatios, Long loginId) throws Exception {
        boolean flg = true;
        SuppliersAvailableCategorys cate = new SuppliersAvailableCategorys();
        SuppliersWarehouse newWare = new SuppliersWarehouse();
        // User newUser=new User();
        // AccountInfo newAccountInfo=new AccountInfo();
        try {
            String[] categoryIdList = categoryIds.split(",");// 类目数组
            String[] commissionRatioList = CommissionRatios.split(",");// 类目对应的佣金比例数组
            cate.setSupplierId(suppliersInfo.getSupplierId());
            for (int i = 0; i < categoryIdList.length; i++) {// 循环插入类目
                cate.setCategoryId(Long.parseLong(categoryIdList[i]));
                cate.setCommissionRatio(
                        BigDecimal.valueOf(Double.parseDouble(commissionRatioList[i].toString())));
                suppliersAvailableCategorysDAO.updateBySuppIdAndCateId(cate);
            }
            newWare.setSupplierId(suppliersInfo.getSupplierId());
            newWare.setWarehouseId(warehouseId);
            suppliersWarehouseDAO.insertSelective(newWare);// 插入仓库
            // newUser.setCustomerTypeId(5);
            // newUser.setLoginId(loginId);
            // newAccountInfo.setCustomerTypeId(5);
            // newAccountInfo.setLoginId(loginId);
            suppliersInfoDAO.updateByPrimaryKeySelective(suppliersInfo);// 修改供应状态为通过
            // int
            // countAcc=supplierAuditDAO.updateAccountInfoByLonginId(newAccountInfo);//修改用户状态为供应商
            // int
            // countUser=supplierAuditDAO.updateUserByLonginId(newUser);//修改用户状态为供应商
        } catch (Exception e) {
            flg = false;
            logger.error("供应商审核出现异常" + e.getMessage(), e);
            throw e;
        }
        return flg;
    }

    /**
     * 修改供应商信息
     */
    @Override
    public boolean supplierUpdate(MerchantInfo merchantOrSupplier, SuppliersInfo record,
            String categoryValueList, SuppliersWarehouse suppliersWarehouse,
            String CommissionRatios, String sacIds) throws Exception {
        boolean flg = true;
        try {
            supplierAuditDAO.updateByPrimaryKeySelective(merchantOrSupplier);// 修改商户
            suppliersInfoDAO.updateByPrimaryKeySelective(record);// 修改供应商
            if (null == suppliersWarehouse.getSupWarehouseId()) {// 修改的时候如果仓库为空就进行插入操作
                suppliersWarehouse.setSupplierId(record.getSupplierId());
                suppliersWarehouseDAO.insertSelective(suppliersWarehouse);
            } else {
                suppliersWarehouseDAO.updateByPrimaryKeySelective(suppliersWarehouse);// 修改仓库
            }

            SuppliersAvailableCategorys newCategorys = new SuppliersAvailableCategorys();
            SuppliersAvailableCategorys newCategory = new SuppliersAvailableCategorys();
            SuppliersAvailableCategorys newCategoryUpdate = new SuppliersAvailableCategorys();
            List<SuppliersAvailableCategorys> ckCategorysLists = null;
            List<SuppliersAvailableCategorys> ckCategorysList = null;
            String gor[] = categoryValueList.split(",");
            String commissionRatiosList[] = CommissionRatios.split(",");
            String sacIdsList[] = sacIds.split(",");
            for (int p = 0; p < sacIdsList.length; p++) {
                if (sacIdsList[p] != null && !"".equals(sacIdsList[p])) {
                    newCategoryUpdate.setSacId(Long.parseLong(sacIdsList[p]));
                    newCategoryUpdate.setCommissionRatio(BigDecimal
                            .valueOf(Double.parseDouble(commissionRatiosList[p].toString())));
                    suppliersAvailableCategorysDAO.updateByPrimaryKeySelective(newCategoryUpdate);
                }
            }
            if (gor.length > 0) {
                newCategorys.setSupplierId(record.getSupplierId());
                for (int i = 0; i < gor.length; i++) {
                    newCategory.setCategoryId(Long.parseLong(gor[i]));
                    newCategory.setSupplierId(record.getSupplierId());
                    newCategory.setCommissionRatio(BigDecimal
                            .valueOf(Double.parseDouble(commissionRatiosList[i].toString())));// 插入佣金比例
                    ckCategorysList =
                            suppliersAvailableCategorysDAO.findSupplierCategorys(newCategory);// 只存在一条记录
                    ckCategorysLists =
                            suppliersAvailableCategorysDAO.findSupplierCategorys(newCategorys);// 存在多条记录
                    if (ckCategorysList.size() == 0) {
                        suppliersAvailableCategorysDAO.insertSelective(newCategory);// 插入类目信息
                    }
                }

                for (int g = 0; g < ckCategorysLists.size(); g++) {
                    newCategory.setSupplierId(record.getSupplierId());
                    boolean ckDele = false;
                    for (int y = 0; y < gor.length; y++) {
                        if (Long.parseLong(gor[y]) == ckCategorysLists.get(g).getCategoryId()) {
                            ckDele = true;
                            // System.out.println(ckCategorysLists.get(g).getCategoryId());
                        }
                    }
                    if (ckDele == true) {
                        newCategory.setCategoryId(ckCategorysLists.get(g).getCategoryId());
                    } else {
                        newCategory.setCategoryId(ckCategorysLists.get(g).getCategoryId());
                        suppliersAvailableCategorysDAO.deleteSuppCretGory(newCategory);
                    }
                }

            }
        } catch (Exception e) {
            flg = false;
            logger.error("修改供应商出现异常" + e.getMessage(), e);
            throw e;
        }
        return flg;
    }

    /**
     * 删除供应商信息
     */
    @Override
    public boolean delSuppliers(Long merId) throws Exception {
        boolean flg = true;
        try {
            SuppliersInfo supp = suppliersInfoDAO.selectBymerId(merId);
            supplierAuditDAO.deleteByPrimaryKey(merId);
            suppliersInfoDAO.deleteSuppliersByMerId(merId);
            suppliersCertificateDAO.deleteBySuppId(supp.getSupplierId());
        } catch (Exception e) {
            flg = false;
            logger.error("删除供应商信息出现异常" + e.getMessage(), e);
            throw e;
        }
        return flg;
    }

    /**
     * 根据资质文件ID删除上传但未审核的文件
     * 
     * @param scId 资质文件ID
     * @throws SQLException
     */
    @Override
    public boolean deleteSupplierCertificate(Long scId) throws SQLException {
        int count = suppliersCertificateDAO.deleteByPrimaryKey(scId);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据供应商ID查询已上传的资质文件
     * 
     * @param supplierId 供应商ID
     * @return
     */
    @Override
    public SuppliersCertificate findCertificateByScId(Long scId) {
        try {
            return suppliersCertificateDAO.selectByPrimaryKey(scId);
        } catch (SQLException e) {
            logger.error("保存供应商资质文件出错!", e);
            return null;
        }
    }

    /**
     * 根据供应商id查询类目
     */
    @Override
    public List<SuppliersAvailableCategorys> showCategorysLists(Long suppId) throws Exception {
        SuppliersAvailableCategorys gro = new SuppliersAvailableCategorys();
        gro.setSupplierId(suppId);
        List<SuppliersAvailableCategorys> categorysList = null;
        try {
            categorysList = suppliersAvailableCategorysDAO.findSupplierCategorys(gro);
        } catch (Exception e) {
            logger.error("" + e.getMessage(), e);
            throw e;
        }
        return categorysList;
    }

    /**
     * 查询供应商申请显示类目显示
     */
    @Override
    public Map<Long, String> applySupplierCategorysMap(Long supplierId) throws SQLException {
        Map<Long, String> cateMap = new LinkedHashMap<Long, String>();
        try {
            List<Category> list =
                    suppliersAvailableCategorysDAO.applySuppliersCategorys(supplierId);
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    cateMap.put(list.get(i).getCategoryId(), list.get(i).getCategoryName());
                }
            }
        } catch (SQLException e) {
            logger.error("查询供应商申请显示类目显示出现异常" + e.getMessage(), e);
            throw e;
        }
        return cateMap;
    }

    /**
     * 审核不通过
     */
    @Override
    public boolean notPass(SuppliersInfo info) throws Exception {
        boolean flg = false;
        try {
            int count = suppliersInfoDAO.updateByPrimaryKeySelective(info);
            if (count > 0) {
                flg = true;
            }
        } catch (Exception e) {
            logger.error("供应商审核不通过出现异常" + e.getMessage(), e);
            throw e;
        }
        return flg;
    }

    /**
     * 根据供应商id查询仓库信息
     */
    @Override
    public List<SuppliersWarehouse> selectBySupplierId(Long supplierId) throws Exception {
        List<SuppliersWarehouse> warsList = null;
        try {
            warsList = suppliersWarehouseDAO.selectByPrimarySupplierId(supplierId);

        } catch (Exception e) {
            logger.error("根据供应商id查询仓库信息出现异常" + e.getMessage(), e);
            throw e;
        }
        return warsList;
    }

    @Override
    public void saveSupplierCertificate(SuppliersCertificate suppliersCertificate)
            throws SQLException {
        try {
            suppliersCertificateDAO.insertSelective(suppliersCertificate);
            logger.info("保存供应商资质文件成功!");
        } catch (SQLException e) {
            logger.error("保存供应商资质文件出错!", e);
            throw new SQLException(e);
        }
    }

    /**
     * 根据供应商纸质文件路径查询资质文件
     */
    @Override
    public SuppliersCertificate findBySuppIdPath(String paths) {
        SuppliersCertificate suppliersCertificate = null;
        try {
            suppliersCertificate = suppliersCertificateDAO.selectSuppIdPath(paths);
        } catch (Exception e) {
            logger.error("根据供应商id查询资质文件出现异常" + e.getMessage(), e);
        }
        return suppliersCertificate;
    }

    /**
     * 根据用户名查询用户信息
     */
    @Override
    public User findByUserNameObj(String userName) throws SQLException {
        User userInfo = null;
        try {
            userInfo = supplierAuditDAO.selectUserByUserName(userName);
        } catch (SQLException e) {
            logger.error("根据用户名查询用户信息出现异常!", e);
            throw new SQLException(e);
        }
        return userInfo;
    }

    /**
     * 根据用户id查询商户信息
     */
    @Override
    public MerchantInfo findByUserId(MerchantInfo merchantInfo) throws SQLException {
        MerchantInfo merInfo = null;
        try {
            merInfo = supplierAuditDAO.selectByUserLoginId(merchantInfo);
        } catch (SQLException e) {
            logger.error("根据用户id查询商户信息出现异常!", e);
            throw new SQLException(e);
        }
        return merInfo;
    }

    /**
     * 插入供应商信息
     */
    @Override
    public boolean supplierAddTwo(MerchantInfo merchantInfo, SuppliersInfo suppliersInfo,
            String supplierCategorys) throws Exception {
        boolean flg = true;
        SuppliersInfo newSuppliersInfo = new SuppliersInfo();
        SuppliersAvailableCategorys newCategory = new SuppliersAvailableCategorys();
        final int supplierType = 5;// 传入接口中供应商类型
        try {
            // 更新会员升级为供应商接口
            // SpecialistRemoteService specialistRemoteService =
            // (SpecialistRemoteService) RemoteTool.getRemote("03", "specialistRemoteService");
            Long merId = supplierAuditDAO.insertSelective(merchantInfo);
            // MerchantInfo mer =
            // supplierAuditDAO.selectByUserLoginId(merchantInfo);
            suppliersInfo.setMerchantId(merId);// 设置商户id到供应商表
            suppliersInfoDAO.insertSelective(suppliersInfo);// 插入数据到产品库的供应商表
            newSuppliersInfo.setMerchantId(merId);
            SuppliersInfo SuppliersInfo = suppliersInfoDAO.selectByMerchantId(newSuppliersInfo);// 根据商户id查询供应商数据

            String gor[] = supplierCategorys.split(",");
            newCategory.setSupplierId(SuppliersInfo.getSupplierId());
            for (int i = 0; i < gor.length; i++) {
                newCategory.setCategoryId(Long.parseLong(gor[i]));
                suppliersAvailableCategorysDAO.insertSelective(newCategory);// 插入类目信息
            }

            specialistRemoteService.upToSupplier(new Long(merchantInfo.getLoginId()).intValue(),
                    supplierType, merId);// 调用会员升级为供应商接口
        } catch (SQLException e) {
            flg = false;
            logger.error("插入供应商信息出现异常!", e);
            throw new SQLException(e);
        }
        return flg;
    }

    @Override
    public boolean supplierUpdateNoPass(MerchantInfo merchantOrSupplier,
            SuppliersInfo suppliersInfo, String categoryValueList) throws Exception {
        try {
            supplierAuditDAO.updateByPrimaryKeySelective(merchantOrSupplier);// 修改商户
            suppliersInfoDAO.updateByPrimaryKeySelective(suppliersInfo);// 修改供应商

            SuppliersAvailableCategorys newCategorys = new SuppliersAvailableCategorys();
            SuppliersAvailableCategorys newCategory = new SuppliersAvailableCategorys();
            List<SuppliersAvailableCategorys> ckCategorysLists = null;
            List<SuppliersAvailableCategorys> ckCategorysList = null;
            String gor[] = categoryValueList.split(",");
            if (gor.length > 0) {
                newCategorys.setSupplierId(suppliersInfo.getSupplierId());
                for (int i = 0; i < gor.length; i++) {
                    newCategory.setCategoryId(Long.parseLong(gor[i]));
                    newCategory.setSupplierId(suppliersInfo.getSupplierId());
                    ckCategorysList =
                            suppliersAvailableCategorysDAO.findSupplierCategorys(newCategory);// 只存在一条记录
                    ckCategorysLists =
                            suppliersAvailableCategorysDAO.findSupplierCategorys(newCategorys);// 存在多条记录
                    if (ckCategorysList.size() == 0) {
                        suppliersAvailableCategorysDAO.insertSelective(newCategory);// 插入类目信息
                    }
                }

                for (int g = 0; g < ckCategorysLists.size(); g++) {
                    newCategory.setSupplierId(suppliersInfo.getSupplierId());
                    boolean ckDele = false;
                    for (int y = 0; y < gor.length; y++) {
                        if (Long.parseLong(gor[y]) == ckCategorysLists.get(g).getCategoryId()) {
                            ckDele = true;
                            // System.out.println(ckCategorysLists.get(g).getCategoryId());
                        }
                    }
                    if (ckDele == true) {
                        newCategory.setCategoryId(ckCategorysLists.get(g).getCategoryId());
                    } else {
                        newCategory.setCategoryId(ckCategorysLists.get(g).getCategoryId());
                        suppliersAvailableCategorysDAO.deleteSuppCretGory(newCategory);
                    }
                }

            }

        } catch (Exception e) {
            logger.error("审核不通过时的修改供应商出现异常!", e);
            throw new SQLException(e);
        }
        return false;
    }

    /**
     * 根据登录id获取供应商id
     * 
     * @param loginId
     * @return
     */
    @Override
    public SuppliersInfo obtainSupplierId(Long loginId) {
        MerchantInfo merchantInfo = new MerchantInfo();
        SuppliersInfo newSuppliersInfo = new SuppliersInfo();
        merchantInfo.setLoginId(loginId);
        SuppliersInfo supplier = null;
        try {
            MerchantInfo mer = supplierAuditDAO.selectByUserLoginId(merchantInfo);
            newSuppliersInfo.setMerchantId(mer.getMerchantId());
            supplier = suppliersInfoDAO.selectByMerchantId(newSuppliersInfo);// 根据商户id查询供应商数据
        } catch (Exception e) {
            logger.error("根据登录id查询供应商出现异常!", e);
        }

        return supplier;
    }

    @Override
    public boolean updateMerchantStatus(MerchantInfo record) throws SQLException {
        boolean flg = false;
        try {
            int couont = supplierAuditDAO.updateByPrimaryKeySelective(record);
            if (couont > 0) {
                flg = true;
            }
        } catch (Exception e) {
            logger.error("修改商户状态出现异常!", e);
            throw new SQLException(e);
        }
        return flg;
    }

    /**
     * 查询供应商id对应的商户名称
     */
    @Override
    public Map<String, String> supplierIdAndMerchantNameMap() throws SQLException {
        List<MerchantInfoOrSuppliers> suppIdAndNameList = null;
        Map<String, String> suppIdAndNameMap = new LinkedHashMap<String, String>();
        MerchantInfoOrSuppliers record = new MerchantInfoOrSuppliers();
        try {
            record.setStatus(Short.parseShort("3"));// 审核通过的供应商
            record.setEnterpriseStatus(Short.parseShort("1"));
            suppIdAndNameList = supplierAuditDAO.selectSupplierIdAndMerchantName(record);
            suppIdAndNameMap.put("221", "康美自营");
            for (int i = 0; i < suppIdAndNameList.size(); i++) {
                if (suppIdAndNameList.get(i).getSupplierId().intValue() == 221)
                    continue;
                suppIdAndNameMap.put(suppIdAndNameList.get(i).getSupplierId().toString(),
                        suppIdAndNameList.get(i).getCorporateName());
            }
        } catch (Exception e) {
            logger.error("查询供应商信息和商户信息出现异常map!", e);
            throw new SQLException(e);
        }
        return suppIdAndNameMap;
    }

    @Override
    public MerchantInfoOrSuppliers findSuppliersInfoBySuppId(Long supplyId) {
        MerchantInfoOrSuppliers suppliersInfo = null;
        try {
            suppliersInfo = suppliersInfoDAO.selectBysupplyId(supplyId);
        } catch (Exception e) {
            logger.error("根据supplyId查询供应商信息出现异常" + e.getMessage(), e);

        }
        return suppliersInfo;
    }

    // 查询供应商列表，优惠券功能使用
    @Override
    public void showSupplierLsit(MerchantInfoOrSuppliers suppliersInfo, Page page,
            String haveChoosedSuplly) throws SQLException {
        if (null != suppliersInfo.getCorporateName()) {// 公司名称查询
            if (suppliersInfo.getCorporateName().length() == 0) {
                suppliersInfo.setCorporateName(null);
            }
        }
        if (null != suppliersInfo.getContactsName()) {
            if (suppliersInfo.getContactsName().length() == 0) {
                suppliersInfo.setContactsName(null);
            }
        }

        if (haveChoosedSuplly != null && !haveChoosedSuplly.equals("")) {
            suppliersInfo.setDescribe(haveChoosedSuplly);
        }
        // example.setOrderByClause("N_COMMERCIAL_TENANT_ID DESC");
        try {

            List<MerchantInfoOrSuppliers> showList =
                    supplierAuditDAO.selectCouponSupplierByExample(suppliersInfo, page);
            int count = supplierAuditDAO.countCouponByExample(suppliersInfo);
            page.setDataList(showList);
            page.setRecordCount(count);
        } catch (Exception e) {
            logger.error("查询供应商信息列表出现异常" + e.getMessage(), e);

        }

    }

    /**
     * 根据用户id查询商户信息
     * 
     * @param merchantInfo
     * @return
     */
    @Override
    public MerchantInfo findLoginId(MerchantInfo merchantInfo) throws SQLException {
        MerchantInfo mers = null;
        try {
            mers = supplierAuditDAO.selectByUserLoginId(merchantInfo);
        } catch (SQLException e) {
            logger.error("根据用户id查询商户信息出现异常" + e.getMessage(), e);
            throw e;
        }
        return mers;
    }

    @Override
    public List<MerchantInfoOrSuppliers> selectPageSupplierIdAndMerchantName(
            MerchantInfoOrSuppliers record) throws ServiceException {
        try {
            return supplierAuditDAO.selectPageSupplierIdAndMerchantName(record);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Integer selectCountSupplierIdAndMerchantName(MerchantInfoOrSuppliers record)
            throws ServiceException {
        try {
            return supplierAuditDAO.selectCountSupplierIdAndMerchantName(record);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
