package com.pltfm.sys.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pltfm.sys.model.SysParam;
import com.pltfm.sys.model.SysParamExample;

import java.sql.SQLException;
import java.util.List;

public class SysParamDAOImpl implements SysParamDAO {
    private static SysParamDAOImpl instance;

    public static SysParamDAOImpl instance(SqlMapClient sqlMapClient){
        if(instance==null)
            instance=new SysParamDAOImpl(sqlMapClient);
        return instance;
    }
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table SYS_PARAM
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    private SqlMapClient sqlMapClient;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAM
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    private SysParamDAOImpl(SqlMapClient sqlMapClient) {
        super();
        this.sqlMapClient = sqlMapClient;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAM
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    public int countByExample(SysParamExample example) throws SQLException {
        Integer count = (Integer) sqlMapClient.queryForObject("SYS_PARAM.ibatorgenerated_countByExample", example);
        return count;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAM
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    public int deleteByExample(SysParamExample example) throws SQLException {
        int rows = sqlMapClient.delete("SYS_PARAM.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAM
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    public String insert(SysParam record) throws SQLException {
        Object newKey = sqlMapClient.insert("SYS_PARAM.ibatorgenerated_insert", record);
        return (String) newKey;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAM
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    public String insertSelective(SysParam record) throws SQLException {
        Object newKey = sqlMapClient.insert("SYS_PARAM.ibatorgenerated_insertSelective", record);
        return (String) newKey;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAM
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    @SuppressWarnings("unchecked")
    public List<SysParam> selectByExample(SysParamExample example) throws SQLException {
        List<SysParam> list = sqlMapClient.queryForList("SYS_PARAM.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAM
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    public int updateByExampleSelective(SysParam record, SysParamExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SYS_PARAM.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAM
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    public int updateByExample(SysParam record, SysParamExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("SYS_PARAM.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table SYS_PARAM
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    private static class UpdateByExampleParms extends SysParamExample {
        private Object record;

        public UpdateByExampleParms(Object record, SysParamExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}
