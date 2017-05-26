package com.pltfm.app.service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.MerchantInfo;
import com.kmzyc.supplier.model.MerchantInfoOrSuppliers;
import com.kmzyc.supplier.model.SuppliersAvailableCategorys;
import com.kmzyc.supplier.model.SuppliersCertificate;
import com.kmzyc.supplier.model.SuppliersInfo;
import com.kmzyc.supplier.model.SuppliersWarehouse;
import com.kmzyc.supplier.model.User;
import com.pltfm.app.vobject.SupplierForExport;
import com.kmzyc.commons.page.Page;

import java.util.List;
import java.util.Map;

public interface SupplierAuditService {

    /**
     * 更新供应商类名称
     * @param levelType 1 一级、2 二级
     * @throws ServiceException
     */
    void updateSupplierCategoryName(Integer levelType) throws ServiceException;

    /**
     * 更新供应商仓库名称
     * @throws ServiceException
     */
    void updateSuppliersWarehouseName() throws ServiceException;

    /**
     * 查询供应商信息列表
     * 
     * @return
     * @throws ServiceException
     */
    void showSupplierList(MerchantInfoOrSuppliers suppliersInfo, Page page) throws ServiceException;

    /**
     * 根据商户id查询供应商信息
     * 
     * @param merId
     * @return
     * @throws ServiceException
     */
    SuppliersInfo findById(Long merId) throws ServiceException;

    /**
     * 根据供应商id查询资质文件
     * 
     * @param suppId
     * @return
     * @throws ServiceException
     */
    List<SuppliersCertificate> findBySupplierId(Long suppId) throws ServiceException;

    /**
     * 根据id查询商户信息
     * 
     * @param merchantId
     * @return
     * @throws ServiceException
     */
    MerchantInfo findByKey(Long merchantId) throws ServiceException;

    boolean updateSupplierStatus(SuppliersInfo suppliersInfo, String categoryIds, Long warehouseId,
            String CommissionRatios, Long loginId, String sacIds, String goryIdsValues1)
            throws ServiceException;

    /**
     * 修改供应商信息
     * 
     * @param merchantOrSupplier
     * @param suppliersInfo
     * @return
     * @throws ServiceException
     */
    boolean supplierUpdate(MerchantInfo merchantOrSupplier, SuppliersInfo suppliersInfo,
            String categoryValueList,
            SuppliersWarehouse suppliersWarehouse, String CommissionRatios, String sacIds,
            String categoryValueList1) throws ServiceException;

    /**
     * 根据资质文件ID删除上传但未审核的文件
     * 
     * @param scId 资质文件ID
     * @throws ServiceException
     */
    boolean deleteSupplierCertificate(Long scId) throws ServiceException;

    /**
     * 根据供应商ID查询已上传的资质文件
     * 
     * @param scId 供应商ID
     * @return
     */
    SuppliersCertificate findCertificateByScId(Long scId) throws ServiceException;

    /**
     * 根据供应商id查询类目
     * 
     * @param suppId
     * @return
     * @throws ServiceException
     * @throws ServiceException
     */
    List<SuppliersAvailableCategorys> showCategoriesLists(Long suppId) throws ServiceException;

    /**
     * 查询所有开放供应商类目
     * 
     * @return
     * @throws ServiceException
     * @throws ServiceException
     */
    List<SuppliersAvailableCategorys> showAllCategoriesLists() throws ServiceException;

    /**
     * 审核不通过
     * 
     * @return
     * @throws ServiceException
     */
    boolean notPass(SuppliersInfo info) throws ServiceException;

    /**
     * 根据供应商id查询仓库信息
     * 
     * @param supplierId
     * @return
     * @throws ServiceException
     */
    List<SuppliersWarehouse> selectBySupplierId(Long supplierId) throws ServiceException;

    /**
     * 保存供应商资质文件
     * 
     * @param suppliersCertificate
     * @throws ServiceException
     */
    void saveSupplierCertificate(SuppliersCertificate suppliersCertificate) throws ServiceException;

    /**
     * 根据供应商纸质文件路径查询资质文件
     */
    SuppliersCertificate findBySuppIdPath(String paths) throws ServiceException;

    /**
     * 根据用户名查询用户信息
     * 
     * @param userName
     * @return
     * @throws ServiceException
     */
    User findByUserNameObj(String userName) throws ServiceException;

    /**
     * 根据登录id查询商户信息
     * 
     * @param merchantInfo
     * @return
     * @throws ServiceException
     */
    MerchantInfo findByUserId(MerchantInfo merchantInfo) throws ServiceException;

    /**
     * 添加供应商第二步
     * 
     * @param merchantInfo
     * @return
     * @throws ServiceException
     */
    boolean supplierAddTwo(MerchantInfo merchantInfo, SuppliersInfo suppliersInfo, String supplierCategories) throws ServiceException;

    /**
     * 根据登录id获取供应商id
     * 
     * @param loginId
     * @return
     */
    SuppliersInfo obtainSupplierId(Long loginId) throws ServiceException;

    /**
     * 查询供应商id对应的供应商名称
     * 
     * @return
     * @throws ServiceException
     */
    Map<String, String> supplierIdAndMerchantNameMap() throws ServiceException;

    /**
     * 供应商商申请显示类目
     * 
     * @return
     * @throws ServiceException
     */
    Map<Long, String> applySupplierCategoriesMap(Long supplierId) throws ServiceException;

    /**
     * 根据用户id查询商户信息
     * 
     * @param merchantInfo
     * @return
     */
    MerchantInfo findLoginId(MerchantInfo merchantInfo) throws ServiceException;

    /**
     * 修改登录状态
     * 
     * @param supplierId
     * @return
     * @throws ServiceException
     */
    boolean updateLoginStatus(Short businessStatus, Short closeStatus, Short loginStatus, Long supplierId) throws ServiceException;

    /**
     * 根据公司名字进行查询
     * 
     * @param merchant
     * @return
     * @throws ServiceException
     */
    boolean selectByCompanyName(MerchantInfo merchant) throws ServiceException;

    /**
     * 查询供应商信息列表
     * 
     * @return
     * @throws ServiceException
     */
    List<SupplierForExport> getSupplierListForExcel(MerchantInfoOrSuppliers suppliersInfo) throws ServiceException;

    /**
     * 导出供应商审核信息(0:审核信息 1：资质重审信息)
     * 
     * @param supplierList
     * @param exportType
     * @throws ServiceException
     */
    void exportExcelForSupplierAuditList(List<SupplierForExport> supplierList,Integer exportType)
            throws ServiceException;

    /**
     * 查询商户分页信息
     * 
     * @param page
     * @throws ServiceException
     */
    void queryMerList(MerchantInfoOrSuppliers mer, Page page) throws ServiceException;

    /**
     * 查询活动待邀请的商户信息列表 ，数据范围：入驻商家过滤掉创建活动指定的商家，活动已报名商家；加上商家报名之后撤销的商家
     * 
     * @param page
     * @throws ServiceException
     */
    void querySuppliersForInviteList(MerchantInfoOrSuppliers mer, Long activityId, Page page)
            throws ServiceException;
    
    /**
     * 根据产品id获取供应商类型
     * @param productId
     * @return
     * @throws ServiceException
     */
    Integer getSupplierTypeByProductDraft(Long productId) throws ServiceException;

    /**
     * 根据产品id获取供应商类型
     * @param productId
     * @return
     * @throws ServiceException
     */
    Integer getSupplierTypeByProduct(Long productId) throws ServiceException;


}