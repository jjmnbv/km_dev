package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.BnesFrozenRecordDAO;
import com.pltfm.app.dao.CommercialTenantBasicInfoDAO;
import com.pltfm.app.dao.LoginInfoDAO;
import com.pltfm.app.dao.PersonalBasicInfoDAO;
import com.pltfm.app.service.BnesFrozenRecordService;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.BnesFrozenRecord;
import com.pltfm.app.vobject.CommercialTenantBasicInfo;
import com.pltfm.app.vobject.LoginInfo;
import com.pltfm.app.vobject.PersonalBasicInfo;

/**
 * 
 * 
 * 冻结帐户BnesFrozenRecordServiceImpl
 * 
 */
@Component(value = "bnesFrozenRecordService")
public class BnesFrozenRecordServiceImpl implements BnesFrozenRecordService {
    @Resource(name = "bnesFrozenRecordDAO")
    private BnesFrozenRecordDAO bnesFrozenRecordDAO;
    @Resource(name = "loginInfoDAO")
    private LoginInfoDAO loginInfoDAO;
    @Resource(name = "personalBasicInfoDAO")
    private PersonalBasicInfoDAO personalBasicInfoDAO;
    /**
     * 商户信息DAO接口
     */
    @Resource(name = "commercialTenantBasicInfoDAO")
    private CommercialTenantBasicInfoDAO commercialTenantBasicInfoDAO;

    public CommercialTenantBasicInfoDAO getCommercialTenantBasicInfoDAO() {
        return commercialTenantBasicInfoDAO;
    }

    public void setCommercialTenantBasicInfoDAO(
            CommercialTenantBasicInfoDAO commercialTenantBasicInfoDAO) {
        this.commercialTenantBasicInfoDAO = commercialTenantBasicInfoDAO;
    }

    public PersonalBasicInfoDAO getPersonalBasicInfoDAO() {
        return personalBasicInfoDAO;
    }

    public void setPersonalBasicInfoDAO(PersonalBasicInfoDAO personalBasicInfoDAO) {
        this.personalBasicInfoDAO = personalBasicInfoDAO;
    }

    public LoginInfoDAO getLoginInfoDAO() {
        return loginInfoDAO;
    }

    public void setLoginInfoDAO(LoginInfoDAO loginInfoDAO) {
        this.loginInfoDAO = loginInfoDAO;
    }

    public BnesFrozenRecordDAO getBnesFrozenRecordDAO() {
        return bnesFrozenRecordDAO;
    }

    public void setBnesFrozenRecordDAO(BnesFrozenRecordDAO bnesFrozenRecordDAO) {
        this.bnesFrozenRecordDAO = bnesFrozenRecordDAO;
    }

    /**
     * 
     * 
     * 添加冻结帐户信息
     * 
     */
    @Override
    public Integer insert(BnesFrozenRecord record) throws SQLException {
        return bnesFrozenRecordDAO.insert(record);
    }

    /**
     * 
     * 
     * 删除冻结帐户信息
     * 
     */
    @Override
    public int delete(List<Integer> frozenRecordIds) throws SQLException {

        Integer count = 0;
        if (ListUtils.isNotEmpty(frozenRecordIds)) {
            for (Integer id : frozenRecordIds) {
                BnesFrozenRecord b = new BnesFrozenRecord();
                b.setFrozenRecordId(id);
                count += bnesFrozenRecordDAO.delete(b);
            }
        }
        return count;
    }

    /**
     * 
     * 
     * 修改冻结帐户信息
     * 
     */
    @Override
    public int update(BnesFrozenRecord record) throws SQLException {
        return bnesFrozenRecordDAO.update(record);
    }

    /**
     * 分页查询冻结帐户信息
     * 
     * @param 冻结帐户信息实体
     * @return
     * @throws Exception 异常
     */
    public Page searchPageByVo(Page pageParam, BnesFrozenRecord vo) throws Exception {
        if (pageParam == null) {
            pageParam = new Page();
        }
        if (vo == null) {
            vo = new BnesFrozenRecord();
        }
        int totalNum = bnesFrozenRecordDAO.selectCountByVo(vo);
        pageParam.setRecordCount(totalNum);
        vo.setSkip(pageParam.getStartIndex());
        vo.setMax(pageParam.getStartIndex() + pageParam.getPageSize());

        pageParam.setDataList(bnesFrozenRecordDAO.selectPageByVo(vo));

        return pageParam;
    }

    /**
     * 跟据信息id查询冻结帐户信息
     * 
     * @param Rank 冻结帐户信息实体
     * @return 返回值
     * @throws SQLException sql异常
     */
    @Override
    public BnesFrozenRecord getFrozenRecordId(Integer frozenRecordId) throws SQLException {
        return bnesFrozenRecordDAO.getFrozenRecordId(frozenRecordId);
    }

    /**
     * 取得登录名
     * 
     * @param vo 信息实体
     * @return 返回值
     * @throws SQLException sql异常
     */

    @Override
    public List getLoginAll() throws SQLException {
        return loginInfoDAO.getLoginAll();
    }

    /**
     * 跟据登录id取得登录名
     * 
     * @param vo 信息实体
     * @return 返回值
     * @throws SQLException sql异常
     */

    @Override
    public LoginInfo getLoginAccount(Integer loginId) throws SQLException {
        return loginInfoDAO.selectByPrimaryKey(loginId);
    }


    /**
     * 跟据登录id取得姓名
     * 
     * @param 个人基本信息实体
     * @return 返回值
     * @throws SQLException sql异常
     */
    @Override
    public PersonalBasicInfo getLogin(Integer login) throws SQLException {
        return personalBasicInfoDAO.getLogin(login);
    }

    /**
     * 根据登录主键查询单条商户信息
     * 
     * @param loginId 登录信息ID
     * @return 返回值
     * @throws Exception 异常
     */
    @Override
    public CommercialTenantBasicInfo selectloginId(Integer loginId) throws SQLException {
        return commercialTenantBasicInfoDAO.selectByPrimaryLoginInfo(loginId);
    }

    /**
     * 冻结解冻操作
     * 
     * 
     * 
     */
    @Override
    public Integer updateStatus(BnesFrozenRecord record, Integer status) throws SQLException {
        LoginInfo login = new LoginInfo();
        login.setN_LoginId(record.getLoginId());
        login.setN_Status(status);
        loginInfoDAO.updateByPrimaryKeySelective(login);
        return bnesFrozenRecordDAO.update(record);
    }

    /**
     * 分页查询账户冻结解冻信息
     * 
     * @param record
     * @return
     * @throws SQLException
     */
    public Page queryPageAccountFrozen(BnesFrozenRecord record, Page page) throws SQLException {
        int count = bnesFrozenRecordDAO.selectCountByAccount(record);
        page.setRecordCount(count);
        record.setSkip(page.getStartIndex());
        record.setMax(page.getStartIndex() + page.getPageSize());
        page.setDataList(bnesFrozenRecordDAO.selectPageByAccount(record));
        return page;
    }

    /**
     * 通过登录账号查询登录账户冻结解冻记录信息
     * 
     * @param bnesFrozenRecord
     * @return
     * @throws SQLException
     */
    @Override
    public BnesFrozenRecord selectByLoginAccount(BnesFrozenRecord bnesFrozenRecord)
            throws SQLException {
        return bnesFrozenRecordDAO.selectByLoginAccount(bnesFrozenRecord);
    }
}
