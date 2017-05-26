package com.kmzyc.promotion.app.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.kmzyc.commons.page.Page;
import com.kmzyc.promotion.exception.ServiceException;
import com.kmzyc.supplier.model.MerchantInfo;
import com.kmzyc.supplier.model.MerchantInfoOrSuppliers;
import com.kmzyc.supplier.model.SuppliersAvailableCategorys;
import com.kmzyc.supplier.model.SuppliersCertificate;
import com.kmzyc.supplier.model.SuppliersInfo;
import com.kmzyc.supplier.model.SuppliersWarehouse;
import com.kmzyc.supplier.model.User;

public interface SupplierAuditService {
    /**
     * 查询商户和供应商信息
     * 
     * @param record
     * @return
     * @throws SQLException
     */
    MerchantInfoOrSuppliers queryBySupplierId(Long supplierId) throws Exception;

    /**
     * 查询供应商信息列表
     * 
     * @return
     * @throws Exception
     */
    void showSupplierLsit(MerchantInfoOrSuppliers suppliersInfo, Page page) throws Exception;

    /**
     * 根据商户id查询供应商信息
     * 
     * @param merId
     * @return
     * @throws Exception
     */
    SuppliersInfo findById(Long merId) throws Exception;

    /**
     * 根据供应商id查询资质文件
     * 
     * @param suppId
     * @return
     * @throws Exception
     */
    List<SuppliersCertificate> findBySuppId(Long suppId) throws Exception;

    /**
     * 根据id查询商户信息
     * 
     * @param MerchantId
     * @return
     * @throws Exception
     */
    MerchantInfo findByKey(Long merchantId) throws Exception;

    boolean updateSupplierStatus(SuppliersInfo suppliersInfo, String categoryIds, Long warehouseId,
            String CommissionRatios, Long loginId) throws Exception;

    /**
     * 修改供应商信息
     * 
     * @param merchantOrSupplier
     * @param suppliersInfo
     * @return
     * @throws Exception
     */
    boolean supplierUpdate(MerchantInfo merchantOrSupplier, SuppliersInfo record,
            String categoryValueList, SuppliersWarehouse suppliersWarehouse,
            String CommissionRatios, String sacIds) throws Exception;

    /**
     * 审核不通过时的修改
     * 
     * @param merchantOrSupplier
     * @param suppliersInfo
     * @return
     * @throws Exception
     */
    boolean supplierUpdateNoPass(MerchantInfo merchantOrSupplier, SuppliersInfo suppliersInfo,
            String categoryValueList) throws Exception;

    /**
     * 删除供应商信息
     * 
     * @return
     * @throws Exception
     */
    public boolean delSuppliers(Long merId) throws Exception;

    /**
     * 根据资质文件ID删除上传但未审核的文件
     * 
     * @param scId 资质文件ID
     * @throws SQLException
     */
    public boolean deleteSupplierCertificate(Long scId) throws SQLException;

    /**
     * 根据供应商ID查询已上传的资质文件
     * 
     * @param supplierId 供应商ID
     * @return
     */
    public SuppliersCertificate findCertificateByScId(Long scId) throws SQLException;

    /**
     * 根据供应商id查询类目
     * 
     * @param suppId
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public List<SuppliersAvailableCategorys> showCategorysLists(Long suppId) throws Exception;

    /**
     * 审核不通过
     * 
     * @return
     * @throws Exception
     */
    public boolean notPass(SuppliersInfo info) throws Exception;

    /**
     * 根据供应商id查询仓库信息
     * 
     * @param supplierId
     * @return
     * @throws Exception
     */
    public List<SuppliersWarehouse> selectBySupplierId(Long supplierId) throws Exception;

    /**
     * 保存供应商资质文件
     * 
     * @param suppliersCertificate
     * @throws SQLException
     */
    public void saveSupplierCertificate(SuppliersCertificate suppliersCertificate)
            throws SQLException;

    /**
     * 根据供应商纸质文件路径查询资质文件
     */
    public SuppliersCertificate findBySuppIdPath(String paths) throws SQLException;

    /**
     * 根据用户名查询用户信息
     * 
     * @param userName
     * @return
     * @throws SQLException
     */
    public User findByUserNameObj(String userName) throws SQLException;

    /**
     * 根据登录id查询商户信息
     * 
     * @param merchantInfo
     * @return
     * @throws SQLException
     */
    public MerchantInfo findByUserId(MerchantInfo merchantInfo) throws SQLException;

    /**
     * 添加供应商第二步
     * 
     * @param merchantInfo
     * @return
     * @throws SQLException
     */
    public boolean supplierAddTwo(MerchantInfo merchantInfo, SuppliersInfo suppliersInfo,
            String supplierCategorys) throws Exception;

    /**
     * 根据登录id获取供应商id
     * 
     * @param loginId
     * @return
     */
    public SuppliersInfo obtainSupplierId(Long loginId) throws SQLException;

    /**
     * 根据商户id修改商户状态
     * 
     * @param merchanId
     * @return
     * @throws SQLException
     */
    public boolean updateMerchantStatus(MerchantInfo record) throws SQLException;

    /**
     * 查询供应商id对应的供应商名称
     * 
     * @return
     * @throws SQLException
     */
    public Map<String, String> supplierIdAndMerchantNameMap() throws SQLException;

    public MerchantInfoOrSuppliers findSuppliersInfoBySuppId(Long supplyId);

    public void showSupplierLsit(MerchantInfoOrSuppliers suppliersInfo, Page page,
            String haveChoosedSuplly) throws SQLException;

    /**
     * 供应商商申请显示类目
     * 
     * @return
     * @throws SQLException
     */
    public Map<Long, String> applySupplierCategorysMap(Long supplierId) throws SQLException;

    /**
     * 根据用户id查询商户信息
     * 
     * @param merchantInfo
     * @return
     */
    public MerchantInfo findLoginId(MerchantInfo merchantInfo) throws SQLException;

    public List<MerchantInfoOrSuppliers> selectPageSupplierIdAndMerchantName(
            MerchantInfoOrSuppliers record) throws ServiceException;

    public Integer selectCountSupplierIdAndMerchantName(MerchantInfoOrSuppliers record)
            throws ServiceException;
}
