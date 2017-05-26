package com.kmzyc.supplier.service;

import java.util.List;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.AccountInfo;
import com.kmzyc.supplier.model.Categorys;
import com.kmzyc.supplier.model.MerchantInfo;
import com.kmzyc.supplier.model.SupplierChannel;
import com.kmzyc.supplier.model.SuppliersAvailableCategorys;
import com.kmzyc.supplier.model.SuppliersCertificate;
import com.kmzyc.supplier.model.SuppliersInfo;
import com.kmzyc.supplier.model.User;

public interface MerchantInfoService {

    /**
     * 查询供应商是否为会员
     *
     * @param user
     * @return
     * @throws ServiceException
     */
    User selectByUserName(User user) throws ServiceException;

    /**
     * 查询会员信息
     * @param loginId
     * @return
     * @throws ServiceException
     */
    AccountInfo findByLoginId(long loginId) throws ServiceException;

    /**
     * 申请供应商第二步
     *
     * @param merchantInfo
     * @return
     * @throws ServiceException
     */
    boolean applySupplierTwo(MerchantInfo merchantInfo, SuppliersInfo suppliersInfo, String supplierCategories)
            throws ServiceException;
    /**
     * 进行跟新操作
     *
     * @param merchantInfo
     * @param record
     * @param supplierCategories
     * @return
     * @throws ServiceException
     */
    boolean applySupplierTwoUpdate(MerchantInfo merchantInfo, SuppliersInfo record, String supplierCategories)
            throws ServiceException;

    /**
     * 判断此供应商是否已经申请过
     *
     * @param merchant
     * @return
     */
    MerchantInfo selectByLoginId(MerchantInfo merchant) throws ServiceException;

    /**
     * 根据商户id查询供应商数据
     *
     * @param merchantId
     * @return
     * @throws ServiceException
     */
    SuppliersInfo selectByMerchantId(Long merchantId) throws ServiceException;


    /**
     * 根据公司名字进行查询
     *
     * @param merchant
     * @return
     * @throws ServiceException
     */
    boolean selectByCompanyName(MerchantInfo merchant) throws ServiceException;

    /**
     * 根据组织结构代码查询
     *
     * @param merchant
     * @return
     * @throws ServiceException
     */
    @Deprecated
    boolean selectByCode(MerchantInfo merchant) throws ServiceException;

    /**
     * 根据营业执照注册号查询数据
     *
     * @param merchant
     * @return
     * @throws ServiceException
     */
    @Deprecated
    boolean selectByRegister(MerchantInfo merchant) throws ServiceException;

    /**
     * 申请供应商第三步上传文件
     *
     * @param suppliersCertificate
     * @return
     * @throws ServiceException
     */
    boolean uploadApplySupplierThree(SuppliersCertificate suppliersCertificate) throws ServiceException;

    /**
     * 申请供应商第三步完成按钮操作
     *
     * @param record
     * @return
     */
    boolean finishUpdate(SuppliersInfo record) throws ServiceException;

    /**
     * 根据供应商id查询上传文件的数量
     *
     * @param suppliersCertificate
     * @return
     */
    int selectCount(SuppliersCertificate suppliersCertificate) throws ServiceException;

    /**
     * 根据用户id查询用户信息
     *
     * @param userId
     * @return
     * @throws ServiceException
     */
    User queryUserByLoginId(Long userId) throws ServiceException;

    /**
     * 供应商商申请显示类目
     *
     * @return
     * @throws ServiceException
     */
    Map<Long, String> applySupplierCategoriesMap() throws ServiceException;

    /**
     * 根据供应商id查询类目
     *
     * @param suppId
     * @return
     * @throws ServiceException
     */
    List<SuppliersAvailableCategorys> showCategoriesLists(Long suppId) throws ServiceException;

    /**
     * 查询会员是否设置了支付密码
     * @param merchant
     * @return
     * @throws ServiceException
     */
    MerchantInfo selectByLoginIdForLogin(MerchantInfo merchant) throws ServiceException;

    /**
     * 查询供应商申请显示所有类目
     *
     * @return
     * @throws ServiceException
     */
    List<Categorys> queryCategoryList() throws ServiceException;

    /**
     * 根据供应商id查询类目
     *
     * @param supplierId
     * @return
     * @throws ServiceException
     */
    List<SuppliersAvailableCategorys> suppIdCategoriesLists(Long supplierId) throws ServiceException;
}
