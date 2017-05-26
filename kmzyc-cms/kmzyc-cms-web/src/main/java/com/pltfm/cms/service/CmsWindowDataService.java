package com.pltfm.cms.service;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.ProductAttr;
import com.pltfm.app.vobject.ViewProductInfo;
import com.pltfm.cms.vobject.CmsWindow;
import com.pltfm.cms.vobject.CmsWindowData;
import com.pltfm.cms.vobject.UploadFile;
import com.pltfm.sys.model.SysUser;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 窗口数据业务逻辑层接口
 *
 * @author cjm
 * @since 2013-9-3
 */
public interface CmsWindowDataService {

    Integer addWindowDatas(List<CmsWindowData> cmsWindowDatas, Integer windowId, Integer created, Integer siteId) throws Exception;

    /**
     * 递归类目
     */
    List categorySub(Map map, List list) throws Exception;

    CmsWindowData queryWindowDataId(CmsWindowData cmsWindowData) throws Exception;

    // 根据条件获取窗口数据信息总条数
    public int isCmsWindow(CmsWindowData cmsWindowData) throws Exception;

    /**
     * 更新价格
     */
    List updateMoney(List<ViewProductInfo> productList);

    /**
     * 根据窗口Id、站点Id与数据类型查询数据Id集合
     */
    String queryByWindowData(CmsWindowData cmsWindowData) throws Exception;

    /**
     * 添加窗口数据信息
     *
     * @param dataIds   数据信息集合
     * @param dataTypes 数据类型集合
     * @param windowId  窗口Id
     * @param created   当前用户Id
     * @param siteId    站点Id
     * @throws Exception sql异常
     * @return 返回值
     */
    Integer addCmsWindowData(List dataIds, Integer dataType, Integer windowId, Integer created, Integer siteId) throws Exception;

    /**
     * 根据窗口Id删除数据
     */
    Integer deleteByWindowId(Integer windowId) throws Exception;

    /**
     * 分页查询窗口数据信息
     *
     * @param cmsWindowData 窗口数据信息实体
     * @param page          分页实体
     * @throws Exception 异常
     * @return 返回值
     */
    Page queryForPage(CmsWindowData cmsWindowData, Page page) throws Exception;

    /**
     * 按窗口对象返回窗口数据集
     *
     * @param example 窗口数据信息条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    List queryWindowDataList(CmsWindowData cmsWindowData) throws Exception;

    /**
     * 按窗口和数据类型查询数据
     *
     * @param windowId 窗口主键
     * @param dataType 数据类型
     * @param siteId   站点Id
     * @throws Exception 异常
     * @return 返回值
     */
    List queryByWindowIdDataType(Integer windowId, Integer dataType, Integer siteId) throws Exception;

    /**
     * 根据窗口Id查询不同类型的数据
     * @param windowId
     * @return
     * @throws Exception

    List<Object> queryByWindowId(Integer windowId)throws Exception;*/
    /**
     * 按窗口Id查询数据
     *
     * @param windowId 数据类型
     * @throws Exception 异常
     * @return 返回值
     */
    Page queryBywindowId(CmsWindowData cmsWindowData, Page page) throws Exception;

    /**
     * 按窗口Id、数据类型和数据Id删除数据
     *
     * @param windowDataId 窗口数据Id
     * @throws Exception 异常
     * @return 返回值
     */
    Integer deleteData(Integer windowDataId) throws Exception;

    /**
     * 根据cmsWindowData删除数据
     */
    Integer deleteData(CmsWindowData cmsWindowData) throws Exception;

    /**
     * 添加cmsWindowData数据
     */
    public Integer addCmsWindowData(List<CmsWindowData> cmsWindowDatas, Integer windowId, Integer created, List<UploadFile> uploadFile, Integer siteId) throws Exception;

    /**
     * 添加cmsWindowData数据
     */
    public Integer addCmsWindowShopData(List<CmsWindowData> cmsWindowDatas, Integer windowId, Integer created, Integer siteId) throws Exception;

    public List queryWindowDataType(CmsWindow window) throws Exception;

    //根据数据类型号和产品ID,返回所有涉及到的页面列表
    public List queryAllPage(String sqlParam) throws SQLException;

    /**
     * 根据主键返回CmsWindowData对象
     */
    public CmsWindowData getCmsWindowDataByPkId(Integer windowDataId) throws Exception;

    /**
     * 修改CmsWindowData数据
     */
    public Integer updateCmsWindowData(CmsWindowData cmsWindowData, UploadFile uploadFile) throws Exception;

    /**
     * 修改CmsWindowData数据
     */
    public Integer updateCmsWindowShopData(CmsWindowData cmsWindowData) throws Exception;

    /**
     * 修改数据的排序
     */
    public Integer updateSort(CmsWindowData cmsWindowData) throws Exception;

    /**
     * 通过商品信息查询商品标签标签信息
     *
     * @param viewProductInfo 商品实体信息
     * @return 商品标签信息集合
     * @throws Exception 异常信息
     */
    public List<String> selectProductAutoTags(ViewProductInfo view) throws Exception;

    /**
     * 处理商品手动标签数据集合信息
     *
     * @param productAttrList 商品手动标签集合信息
     */
    public List<String> selectProductTags(List<ProductAttr> productAttrList) throws Exception;

    //根据站点返回窗口数据集
    public List queryWindowDataBySite(Integer site) throws Exception;

    //添加至缓存中
    public List savaMemcached(Integer siteId, List list) throws SQLException;

    //店铺上传图片
    public Integer editShopWindowData(List<CmsWindowData> cmsWindowDatas, List<UploadFile> uploadFile,
                                      SysUser sysuser, List dataIds) throws Exception;

    CmsWindowData queryByDataId(Long shopDataId) throws SQLException;

    void updateCmsWindowData(CmsWindowData cmsWindowData) throws SQLException;

    /**
     * 插入商品推荐
     */
    public boolean addProdcutRecommentService(CmsWindowData addCmsWindowData, String proSkuIds, String productIds) throws Exception;

    /**
     * 根据主键删除窗口数据
     */
    public boolean delProdcutRecommentService(String dwindowDataIds) throws Exception;

    /**
     * 修改窗口数据排序
     */
    public boolean updateSorts(List<CmsWindowData> updateSor, CmsWindowData addCmsWindowData) throws Exception;


    /**
     * 根据窗口id查询信息
     */
    public List<CmsWindowData> selectUserDefinedNameByWindId(Integer windId) throws Exception;

    void addCmsWindowData(CmsWindowData cmsWindowData) throws SQLException;

    public Integer editCmsWindowData(List<CmsWindowData> cmsWindowDatas, List<UploadFile> uploadFile) throws Exception;
}
