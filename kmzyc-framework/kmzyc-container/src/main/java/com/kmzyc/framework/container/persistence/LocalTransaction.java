package com.kmzyc.framework.container.persistence;


/**
 * JDBC事务对象.
 *
 * @author weishanyao
 */
public interface LocalTransaction {

    /**
     * 开启事务
     */
    void beginTransaction() throws Exception;

    /**
     * 测试当前状态是否处于事务当中
     */
    boolean isActive();

    /**
     * 提交事务
     */
    void commitTransaction() throws Exception;

    /**
     * 回滚事务
     */
    void rollbackTransaction() throws Exception;

    /**
     * 读取事务回滚标识
     */
    boolean getRollbackOnly();

    /**
     * 设置事务回滚标识，设置此标识后事务结束时所有操作将回滚至初始状态
     */
    void setRollbackOnly(boolean flag);

    /**
     * 是否在事务外保持数据库连接(只读连接)
     */
    boolean isKeepConnect();

    /**
     * 设置是否在事务外保持数据库连接(只读连接)
     */
    void setKeepConnect(boolean flag);

    /**
     * 结束当前事务（提交或回滚，取决于事务标识）
     */
    void endTransaction() throws Exception;

    /**
     * 持久单元名称
     */
    String persistenceUnitName();
}
