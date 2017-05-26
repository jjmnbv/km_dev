package com.kmzyc.user.remote.service;

import java.io.Serializable;
import java.util.Map;

import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.MdicalExcusieInfo;
import com.pltfm.app.vobject.PersonalBasicInfo;
import com.pltfm.app.vobject.PersonalityInfo;
import com.pltfm.app.vobject.Specialist;

/**
 * 专家列表信息远程接口
 * 
 * @author gwl
 */
public interface SpecialistRemoteService extends Serializable {
    /**
     * 按专家信息条件查询专家信息
     * 
     * @param specialist 专家信息条件
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    public Specialist getSpecialist_id(Specialist specialist, Integer type) throws Exception;

    /**
     * 注册登录信息
     * 
     * @param loginInfo 登录信息实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    public Map<String, String> registerLoginInfo(LoginInfo loginInfo, Integer type)
            throws Exception;

    /**
     * 注册个人信息
     * 
     * @param personalBasicInfo 个人信息实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    public Map<String, String> registerPersonal(PersonalBasicInfo personalBasicInfo, Integer type)
            throws Exception;

    /**
     * 注册 医务信息
     * 
     * @param mdicalExcusieInfo 医务信息实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    public Map<String, String> registerMdicalExcusie(MdicalExcusieInfo mdicalExcusieInfo,
            Integer type) throws Exception;

    /**
     * 注册个性信息
     * 
     * @param personalityInfo 个性信息实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception 异常
     */
    public Map<String, String> registerPersonality(PersonalityInfo personalityInfo, Integer type)
            throws Exception;

    /**
     * 注册会员升级为供应商
     * 
     * @param n_LoginId 会员ID，type---5为供应商
     * @param
     * @return 返回值 Integer 1--成功 0失败
     * @throws Exception 异常
     */

    public Integer upToSupplier(Integer n_LoginId, Integer type, Long N_COMMERCIAL_ID)
            throws Exception;

    /**
     * 注册会员升级为采购商
     * 
     * @param n_LoginId 会员ID，type---4为采购商
     * @param
     * @return 返回值 Integer 1--成功 0失败
     * @throws Exception 异常
     */

    /*
     * public Integer upToBuyer(CommercialTenantBasicInfo commercialTenantBasicInfo, Integer type)
     * throws Exception;
     */
    /**
     * 注册会员为采购商
     * 
     * @param n_LoginId 会员ID，type---4为采购商
     * @param
     * @return 返回值 Integer 1--成功 0失败
     * @throws Exception 异常
     */
    /* public Integer registerBuyer(BuyerInfo buyerInfo, Integer type) throws Exception; */

    /**
     * 修改采购商信息
     * 
     * @param n_LoginId 会员ID，type---4为采购商
     * @param
     * @return 返回值 Integer 1--成功 0失败
     * @throws Exception 异常
     */
    /* public Integer updateBuyer(BuyerInfo buyerInfo, Integer type) throws Exception; */

    /**
     * 修改采购商状态
     * 
     * @param n_LoginId 会员ID，type---4为采购商
     * @param
     * @return 返回值 Integer 1--成功 0失败
     * @throws Exception 异常
     */
    /*
     * public Integer updateBuyerStatus(Integer n_LoginId, Integer status, Integer type) throws
     * Exception;
     */
    /**
     * 变更采购商基本资料
     * 
     * @param CommercialTenantBasicCopy，type---4为采购商
     * @param
     * @return 返回值 Integer 1--成功 0失败
     * @throws Exception 异常
     */
    /*
     * public Integer toExamineBuyerInfo(CommercialTenantBasicCopyDO commercialTenantBasicCopyDO,
     * Integer type) throws Exception;
     * 
     */
    /**
     * 查询采购商总共数量
     * 
     * @param CommercialTenantBasicCopy，type---4为采购商
     * @param
     * @return 返回值 Integer 采购商总数--成功 0失败
     * @throws Exception 异常
     */
    /*
     * public Integer getCountBuyerInfo(CommercialTenantBasicInfo commercialTenantBasicInfo, Integer
     * type) throws Exception;
     */
    /**
     * 分页查询采购商信息
     * 
     * @param CommercialTenantBasicCopy，type---4为采购商
     * @param
     * @return 返回值 List --成功
     * @throws Exception 异常
     */
    /*
     * public List<CommercialTenantBasicInfo> selectByCommercialTenantBasicInfo(
     * CommercialTenantBasicInfo commercialTenantBasicInfo, Integer type) throws Exception;
     */



}
