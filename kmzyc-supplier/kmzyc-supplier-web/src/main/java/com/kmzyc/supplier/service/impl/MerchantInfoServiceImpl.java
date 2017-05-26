package com.kmzyc.supplier.service.impl;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.supplier.model.AccountInfo;
import com.kmzyc.supplier.model.Categorys;
import com.kmzyc.supplier.model.MerchantInfo;
import com.kmzyc.supplier.model.SuppliersAvailableCategorys;
import com.kmzyc.supplier.model.SuppliersCertificate;
import com.kmzyc.supplier.model.SuppliersInfo;
import com.kmzyc.supplier.model.User;
import com.kmzyc.user.remote.service.SpecialistRemoteService;
import com.kmzyc.supplier.dao.CategoryDAO;
import com.kmzyc.supplier.dao.MerchantInfoDAO;
import com.kmzyc.supplier.dao.SuppliersAvailableCategorysDAO;
import com.kmzyc.supplier.dao.SuppliersCertificateDAO;
import com.kmzyc.supplier.dao.SuppliersInfoDAO;
import com.kmzyc.supplier.service.MerchantInfoService;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Service("merchantInfoService")
public class MerchantInfoServiceImpl implements MerchantInfoService {

    private static Logger logger = LoggerFactory.getLogger(MerchantInfoServiceImpl.class);

    @Resource
    private MerchantInfoDAO merchantInfoDAO;

    @Resource
    private SuppliersInfoDAO suppliersInfoDAO;

    @Resource
    private SuppliersCertificateDAO suppliersCertificateDAO;

    @Resource
    private CategoryDAO categoryDAO;

    @Resource
    private SuppliersAvailableCategorysDAO suppliersAvailableCategorysDAO;

    @Resource
    private SpecialistRemoteService specialistRemoteService;

    @Override
    public User selectByUserName(User user) throws ServiceException {
        try {
            return merchantInfoDAO.selectByUserName("User.findUser", user);
        } catch (Exception e) {
            logger.error("查询供应商是否是会员出现异常:", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public AccountInfo findByLoginId(long loginId) throws ServiceException {
        try {
            return merchantInfoDAO.findByLoginId(loginId);
        } catch (Exception e) {
            logger.error("查询会员信息出现异常:", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public MerchantInfo selectByLoginId(MerchantInfo merchant) throws ServiceException {
        try {
            return merchantInfoDAO.selectByLoginId(merchant);
        } catch (Exception e) {
            logger.error("根据登录id查询商户信息出现异常:", e);
            throw new ServiceException(e);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    private int updateMerchantInfo(MerchantInfo merchantInfo) throws ServiceException {
        try {
            return merchantInfoDAO.updateByPrimaryKeySelective(merchantInfo);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    private Long insertMerchantInfo(MerchantInfo merchantInfo) throws ServiceException {
        try {
            return merchantInfoDAO.insertSelective(merchantInfo);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }
    
    @Override
    public MerchantInfo selectByLoginIdForLogin(MerchantInfo merchant) throws ServiceException {
        try {
            return merchantInfoDAO.selectByLoginIdForLogin(merchant);
        } catch (Exception e) {
            logger.error("查询会员是否设置了支付密码出现异常:", e);
            throw new ServiceException(e);
        }
    }

    public SuppliersInfo selectByMerchantId(Long merchantId) throws ServiceException {
        try {
            SuppliersInfo newSupplier = new SuppliersInfo();
            newSupplier.setMerchantId(merchantId);
            return suppliersInfoDAO.selectByMerchantId(newSupplier);
        } catch (Exception e) {
            logger.error("根据商户id查询数据出现异常:", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean selectByCompanyName(MerchantInfo merchant) throws ServiceException {
        try {
            List<MerchantInfo> list = merchantInfoDAO.selectByCompanyName(merchant);
            return CollectionUtils.isNotEmpty(list);
        } catch (Exception e) {
            logger.error("根据公司名称查询数据出现异常:", e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Deprecated
    public boolean selectByCode(MerchantInfo merchant) throws ServiceException {
        boolean flg = false;

        return flg;
    }

    @Override
    @Deprecated
    public boolean selectByRegister(MerchantInfo merchant) throws ServiceException {
        boolean flg = false;

        return flg;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public synchronized boolean applySupplierTwo(MerchantInfo merchantInfo, SuppliersInfo record,String supplierCategories)
            throws ServiceException{
        boolean havePass = true;
        SuppliersInfo newSuppliersInfo = new SuppliersInfo();

        // 传入接口中供应商类型
        final int supplierType = 5;
        try {
            if (null != selectByLoginId(merchantInfo)) {
                return true;
            }

            // 更新会员升级为供应商接口 插入数据到用户库的商户表
            Long merchantInfoId = insertMerchantInfo(merchantInfo);
            record.setMerchantId(merchantInfoId);// 设置商户id到供应商表
            record.setCompanyShowName(merchantInfo.getCorporateName());// 把供应商全称设到公司显示名称字段上
            suppliersInfoDAO.insertSelective(record);// 插入数据到产品库的供应商表
            newSuppliersInfo.setMerchantId(merchantInfoId);
            suppliersInfoDAO.selectByMerchantId(newSuppliersInfo);// 根据商户id查询供应商数据

            String[] categoryIdList = supplierCategories.split(",");
            Map map = new HashMap();
            map.put("supplierId", record.getSupplierId());
            map.put("categoryIdList", categoryIdList);
            suppliersAvailableCategorysDAO.insertSupplierIdCategorys(map);

            // 调用会员升级为供应商接口
            specialistRemoteService.upToSupplier(Integer.valueOf(merchantInfo.getLoginId().toString()), supplierType, merchantInfoId);
        } catch (Exception e) {
            logger.error("申请供应商第二步出现异常:", e);
            throw new ServiceException(e);
        }
        return havePass;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public boolean applySupplierTwoUpdate(MerchantInfo merchantInfo, SuppliersInfo record, String supplierCategories)
            throws ServiceException {
        boolean havePass = true;
        try {
            updateMerchantInfo(merchantInfo);
            record.setCompanyShowName(merchantInfo.getCorporateName());
            suppliersInfoDAO.updateByPrimaryKeySelective(record);

            //删除用户记录
            suppliersAvailableCategorysDAO.delSupplierIdCategorys(record.getSupplierId());
            //添加用户记录
            String[] categoryIdList = supplierCategories.split(",");
            Map map = new HashMap();
            map.put("supplierId", record.getSupplierId());
            map.put("categoryIdList", categoryIdList);
            suppliersAvailableCategorysDAO.insertSupplierIdCategorys(map);
        } catch (Exception e) {
            logger.error("修改申请供应商出现异常:", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return havePass;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public boolean uploadApplySupplierThree(SuppliersCertificate suppliersCertificate) throws ServiceException {
        boolean havePass = true;
        try {
            suppliersCertificateDAO.insertSelective(suppliersCertificate);
        } catch (Exception e) {
            havePass = false;
            logger.error("申请第三步上传供应商文件出现异常:", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return havePass;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public boolean finishUpdate(SuppliersInfo record) throws ServiceException {
        boolean havePass = false;
        try {
            int count = suppliersInfoDAO.updateByPrimaryKeySelective(record);
            havePass = count > 0;
        } catch (Exception e) {
            havePass = false;
            logger.error("申请供应商第三步完成按钮操作出现异常:", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return havePass;
    }

    @Override
    public int selectCount(SuppliersCertificate suppliersCertificate) throws ServiceException {
        try {
            return suppliersCertificateDAO.countBySupplierId(suppliersCertificate);
        } catch (Exception e) {
            logger.error("根据供应商id查询上传文件的数量出现异常:", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public User queryUserByLoginId(Long userId) throws ServiceException {
        try {
            return merchantInfoDAO.queryUserByLoginId(userId);
        } catch (Exception e) {
            logger.error("根据用户id查询用户信息出现异常:", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<Long, String> applySupplierCategoriesMap() throws ServiceException {
        Map<Long, String> cateMap = new LinkedHashMap();
        try {
            List<Categorys> list = categoryDAO.applySuppliersCategories();
            for (int i = 0; i < list.size(); i++) {
                cateMap.put(list.get(i).getCategoryId(), list.get(i).getCategoryName());
            }
        } catch (Exception e) {
            logger.error("查询供应商申请显示类目显示出现异常:", e);
            throw new ServiceException(e);
        }
        return cateMap;
    }

    @Override
    public List<SuppliersAvailableCategorys> showCategoriesLists(Long supplierId) throws ServiceException {
        SuppliersAvailableCategorys param = new SuppliersAvailableCategorys();
        param.setSupplierId(supplierId);
        try {
            return suppliersAvailableCategorysDAO.findSupplierCategorys(param);
        } catch (Exception e) {
            logger.error("根据供应商id查询类目失败:", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Categorys> queryCategoryList() throws ServiceException {
        try {
            return categoryDAO.applySuppliersCategoriesAll();
        } catch (Exception e) {
            logger.error("查询供应商申请显示所有类目出现异常:", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<SuppliersAvailableCategorys> suppIdCategoriesLists(Long supplierId) throws ServiceException {
        SuppliersAvailableCategorys param = new SuppliersAvailableCategorys();
        param.setSupplierId(supplierId);
        try {
            return suppliersAvailableCategorysDAO.findSupplierIdCategorys(param);
        } catch (Exception e) {
            logger.error("根据供应商id查询类目失败:", e);
            throw new ServiceException(e);
        }
    }
}
