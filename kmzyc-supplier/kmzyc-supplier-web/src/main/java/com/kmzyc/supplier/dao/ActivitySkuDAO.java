package com.kmzyc.supplier.dao;

import com.km.framework.page.Pagination;
import com.kmzyc.supplier.vo.ActivitySkuVo;
import com.pltfm.app.vobject.ActivitySku;
import com.pltfm.app.vobject.ActivitySkuExample;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/3/24 14:29
 */
public interface ActivitySkuDAO {

    /**
     * 查询活动时选择商品
     *
     * @param page
     * @return
     * @throws SQLException
     */
    Pagination selectProductSkuList4Activity(Pagination page) throws SQLException;

    /**
     * 批量插入sku数据
     *
     * @param skuList
     */
    int batchSaveActivitySku(List<ActivitySku> skuList) throws SQLException;

    /**
     * 新增单个参加活动的sku
     *
     * @param activitySku   sku信息
     */
    Long saveActivitySku(ActivitySku activitySku) throws SQLException;

    /**
     * 查询是否还有商品在还未结束的促销推广活动中
     *
     * @param condition
     * @return
     * @throws SQLException
     */
    List<String> getSkuInUnfinishedActivity(Map<String, Object> condition) throws SQLException;

    /**
     * 根据活动id和商家id获取当前商家报名此次活动的商品
     *
     * @param condition
     * @return
     * @throws SQLException
     */
    List<ActivitySkuVo> getActivitySku(Map<String, Object> condition) throws SQLException;

    /**
     * 删除报名参加活动的sku
     *
     * @param condition
     */
    int deleteActivitySku(ActivitySkuExample condition) throws SQLException;

    /**
     * 批量修改参加活动的sku
     *
     * @param skuList
     */
    int batchUpdateActivitySku(List<ActivitySku> skuList) throws SQLException;
}
