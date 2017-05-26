package com.pltfm.app.service;

import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.fobject.SkuCheckAttr;
import com.pltfm.app.vobject.ProductAttrDraft;
import com.pltfm.app.vobject.ProductCertificateDraft;
import com.pltfm.app.vobject.ProductDraft;
import com.kmzyc.commons.exception.ServiceException;

public interface ProductDraftService {

    /**
     * 增加产品草稿数据
     *
     * @param product
     * @param skuCheckAttrs
     * @return
     * @throws ServiceException
     */
    Long insertProduct(ProductDraft product, List<SkuCheckAttr> skuCheckAttrs) throws ServiceException;


    /**
     * 查找某个产品下的所有SKU以及属性值
     *
     * @param productDraft
     * @throws ServiceException
     */
    ProductDraft findSingleProductAndSkuAndAttrValues(ProductDraft productDraft) throws ServiceException;

    /**
     * 更新对象
     *
     * @throws ServiceException
     */
    void updateObject(ProductDraft product) throws ServiceException;

    /**
     * 批量更新
     *
     * @param list
     * @throws ServiceException
     */
    void batchUpdateObject(List<ProductDraft> list) throws ServiceException;

    /**
     * 根据主键ID获取对象
     *
     * @param productId
     * @return
     * @throws ServiceException
     */
    ProductDraft findByProductId(Long productId) throws ServiceException;

    /**
     * 根据主键获取对象，并不获取clob字段
     *
     * @param productId
     * @return
     * @throws ServiceException
     */
    ProductDraft findByProductIdWithOutClob(Long productId) throws ServiceException;

    /**
     * 检查是否符合上架条件
     *
     * @param productIds
     * @return
     * @throws ServiceException
     */
    ResultMessage checkUpShelf(List<Long> productIds) throws ServiceException;

    /**
     * 草稿状态为新增时的上架
     *
     * @param productIds
     * @return
     * @throws ServiceException
     */
    ResultMessage upShelf(List<Long> productIds, Long loginId) throws ServiceException;

    /**
     * 根据主键删除产品草稿信息
     *
     * @param productId
     * @throws ServiceException
     */
    void deleteProductDraftByProductId(Long productId, String isExistProduct) throws ServiceException;

    /**
     * 批量删除产品草稿信息
     *
     * @param productIdList
     * @throws ServiceException
     */
    ResultMessage batchDeleteProductDraftByPId(List<Long> productIdList) throws ServiceException;

    /**
     * 更新SKU以及属性
     *
     * @param productDraft    草稿产品
     * @param productNo       产品编码
     * @param productAttrList 产品sku属性数据 <note> 含sku属性的id，名称，值（即checkbox的值），所选中的值 </note>
     * @param skuCheckAttrs   sku列表中新增的列表数据
     * @param oldSkuCheckedId sku列表数据中，每一条sku数据所含的sku规格（格式：sku规格id1,sku名称1,sku规格值的id1,） <note>
     *                        如果sku规格值的id是新增规格的，格式应为@新增名称#下标 </note>
     * @param toDeleteSkuIds  需要删除的skuId字符串（格式：id1,id2,id3...）
     * @throws ServiceException
     */
    void updateSkuAttrValue(ProductDraft productDraft, String productNo,
                            List<ProductAttrDraft> productAttrList, List<SkuCheckAttr> skuCheckAttrs,
                            List<String> oldSkuCheckedId, String toDeleteSkuIds) throws ServiceException;

    /**
     * 正式表的更新，向草稿表中插入数据
     *
     * @param product
     * @param skuCheckAttrs
     * @throws ServiceException
     */
    void insertFromProduct(ProductDraft product, List<SkuCheckAttr> skuCheckAttrs) throws ServiceException;

    /**
     * 向草稿表中插入数据，主键不自动生成
     *
     * @param productDraft
     * @throws ServiceException
     */
    void insertProductDraftWithOutSeq(ProductDraft productDraft) throws ServiceException;

    /**
     * 发布产品价格
     *
     * @param productId
     * @throws ServiceException
     */
    ResultMessage releaseProductPrice(Long productId, Long loginId) throws ServiceException;

    /**
     * 批量发布产品价格
     *
     * @param productIds
     * @throws ServiceException
     */
    ResultMessage batchReleaseProductPrice(List<Long> productIds, Long loginId) throws ServiceException;

    /**
     * 根据产品Id复制正式表数据到草稿表中
     *
     * @param productId
     * @throws ServiceException
     */
    void copyOfficialDataToDraft(Long productId, String isExsitProduct) throws ServiceException;

    /**
     * 产品预览
     *
     * @param productId
     * @return
     * @throws ServiceException
     */
    String previewProductDraftInfoPage(String productId) throws ServiceException;

    /**
     * 修改产品的资质文件
     *
     * @param product
     * @throws ServiceException
     */
    void updateProductCertificateFiles(ProductDraft product, List<ProductCertificateDraft> toAddList,
                                       List<Long> toDeleteIds) throws ServiceException;

    void searchPage(Page page, ProductDraft productForSelectPara, String returnType) throws ServiceException;

    void batchSubmitDraftTheAudit(List<ProductDraft> list) throws ServiceException;

    void searchPageByUserForAudit(Page page, ProductDraft product, String type) throws ServiceException;

}