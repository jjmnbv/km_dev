package com.pltfm.cms.dao;

import com.pltfm.app.vobject.ViewProductInfo;
import com.pltfm.cms.vobject.CmsWindow;
import com.pltfm.cms.vobject.CmsWindowData;
import com.pltfm.cms.vobject.CmsWindowDataExample;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 窗口数据DAO接口
 *
 * @author cjm
 * @since 2013-9-3
 */
public interface CmsWindowDataDAO {
    /**
     * 更新价格
     */
    public List updateMoney(List<ViewProductInfo> productList);

    /**
     * 添加窗口数据信息
     *
     * @param record 窗口数据信息实体
     * @throws SQLException sql异常
     * @return 返回值
     */
    Integer insert(CmsWindowData record) throws SQLException;

    /**
     * 按窗口对象和数据类型返回窗口数据集
     *
     * @param example 窗口数据信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    public CmsWindowData queryWindowData(CmsWindowData cmsWindowData) throws SQLException;

    CmsWindowData queryWindowDataId(CmsWindowData cmsWindowData) throws SQLException;

    /**
     * 自定义动态修数据信息
     *
     * @param record 窗口数据信息实体
     * @throws SQLException sql异常
     * @return 返回值
     */
    int update(CmsWindowData record) throws SQLException;

    /**
     * 修改窗口数据信息
     *
     * @param record 窗口数据信息实体
     * @throws SQLException sql异常
     * @return 返回值
     */
    int updateByPrimaryKey(CmsWindowData record) throws SQLException;

    /**
     * 动态修改窗口数据信息
     *
     * @param record 窗口数据信息实体
     * @throws SQLException sql异常
     * @return 返回值
     */
    int updateByPrimaryKeySelective(CmsWindowData record) throws SQLException;

    /**
     * 按窗口对象返回窗口数据集
     *
     * @param example 窗口数据信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    public List queryWindowDataList(CmsWindowData cmsWindowData) throws SQLException;

    /**
     * 按窗口数据信息条件查询
     *
     * @param example 窗口数据信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    List selectByExample(CmsWindowDataExample example) throws SQLException;

    /**
     * 按条件查找最大的sort
     *
     * @param example
     * @return
     * @throws SQLException
     */
    Integer maxSortByExample(CmsWindowDataExample example) throws SQLException;
    /**
     * 根据窗口数据主键查询单条窗口基本信息
     *
     * @param windowDataId 窗口数据主键
     * @throws SQLException sql异常
     * @return 返回值
     */
    CmsWindowData selectByPrimaryKey(Integer windowDataId) throws SQLException;

    /**
     * 按窗口数据信息条件进行删除
     *
     * @param example 窗口数据信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    int deleteByExample(CmsWindowDataExample example) throws SQLException;

    /**
     * 根据窗口数据主键删除窗口数据信息
     *
     * @param windowDataId 窗口数据主键
     * @throws SQLException sql异常
     * @return 返回值
     */
    int deleteByPrimaryKey(Integer windowDataId) throws SQLException;

    /**
     * 按窗口数据信息条件查询总条数
     *
     * @param example 窗口数据信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    int countByExample(CmsWindowDataExample example) throws SQLException;

    /**
     * 动态按窗口数据信息条件进行修改
     *
     * @param record  窗口数据信息实体
     * @param example 窗口数据信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    int updateByExampleSelective(CmsWindowData record, CmsWindowDataExample example) throws SQLException;

    /**
     * 按窗口数据信息按条件进行修改
     *
     * @param record  窗口数据信息实体
     * @param example 窗口数据信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    int updateByExample(CmsWindowData record, CmsWindowDataExample example) throws SQLException;

    /**
     * 分页查询窗口数据信息
     *
     * @param cmsWindowData 窗口数据信息实体
     * @throws SQLException 异常
     * @return 返回值
     */
    public List queryForPage(CmsWindowData cmsWindowData) throws SQLException;

    /**
     * 窗口数据信息总数量
     *
     * @param cmsWindowData 窗口数据信息实体
     * @throws SQLException 异常
     * @return 返回值
     */
    public Integer countByCmsWindowData(CmsWindowData cmsWindowData) throws SQLException;

    /**
     * 返回窗口对象
     *
     * @param example 窗口数据信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    public List querySubWindowDataList(CmsWindowData cmsWindowData) throws SQLException;

    /**
     * 根据cmsWindowData删除数据
     */
    public int deleteByCmsWindowData(CmsWindowData cmsWindowData) throws SQLException;

    /**
     * 增加cmsWindowData数据
     */
    public Integer addCmsWindowData(CmsWindowData cmsWindowData) throws SQLException;

    /**
     * 根据窗口对象返回窗口绑定数据类型
     */
    public List queryWindowDataType(CmsWindow window) throws SQLException;

    //根据数据类型号和产品ID,返回所有涉及到的页面列表
    public List queryAllPage(String sqlParam) throws SQLException;

    //根据站点返回窗口数据集
    public List queryWindowDataBySite(Integer site) throws SQLException;

    //根据skuID和窗口id查询数据
    public List queryWindowDataByIdAndSkuId(CmsWindowData cmsWindowData) throws SQLException;

    /**
     * 修改排序窗口数据信息
     *
     * @param record 窗口数据信息实体
     * @throws SQLException sql异常
     * @return 返回值
     */
    public int updateSortByPrimaryKeySelective(CmsWindowData record) throws SQLException;

    /**
     * 修改标签名称
     *
     * @param record 窗口数据信息实体
     * @throws SQLException sql异常
     * @return 返回值
     */
    public int updateuserDefinedNameByPrimaryKeySelective(CmsWindowData record) throws SQLException;

    public List queryUserDefinedNameByWindId(CmsWindowData cmsWindowData) throws SQLException;

    public List queryByWindIdAndUserDefinedName(CmsWindowData cmsWindowData) throws SQLException;

    public void updateSearchCategory(Map map) throws SQLException;


}
