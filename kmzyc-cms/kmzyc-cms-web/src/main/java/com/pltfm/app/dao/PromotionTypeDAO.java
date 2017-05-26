package com.pltfm.app.dao;

import com.pltfm.app.vobject.PromotionTypeExample;

import java.sql.SQLException;
import java.util.List;

/**
 * 活动信息类型DAO接口
 *
 * @author cjm
 * @since 2013-9-4
 */
public interface PromotionTypeDAO {
    /**
     * 按活动信息类型数据信息条件查询
     *
     * @param example 活动信息类型条件
     * @throws SQLException sql异常
     * @return 返回值
     */
    List selectByExample(PromotionTypeExample example) throws SQLException;
}