package com.kmzyc.supplier.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.km.framework.page.Pagination;
import com.kmzyc.supplier.dao.AftersaleReturnOrderDAO;
import com.kmzyc.supplier.util.BillCodeUtils;
import com.kmzyc.supplier.util.BillPrefix;
import com.pltfm.app.vobject.AftersaleReturnOrder;
import com.pltfm.app.vobject.AftersaleReturnOrderExample;
import com.pltfm.app.vobject.DistributionInfo;
import com.pltfm.app.vobject.StockOut;

@Repository("aftersaleReturnOrderDao")
public class AftersaleReturnOrderDAOImpl extends BaseDAO implements AftersaleReturnOrderDAO{
	
    public int countByExample(AftersaleReturnOrderExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("AFTERSALE_RETURN_ORDER.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(AftersaleReturnOrderExample example) throws SQLException {
        return sqlMapClient.delete("AFTERSALE_RETURN_ORDER.ibatorgenerated_deleteByExample", example);
    }

    public int deleteByPrimaryKey(Long returnId) throws SQLException {
        AftersaleReturnOrder key = new AftersaleReturnOrder();
        key.setReturnId(returnId);
        return sqlMapClient.delete("AFTERSALE_RETURN_ORDER.ibatorgenerated_deleteByPrimaryKey", key);
    }

    public void insert(AftersaleReturnOrder record) throws SQLException {
    	record.setCreateTime(new Date());
    	record.setStatus("1");
    	record.setHandleResult("2");
    	record.setReturnNo(BillCodeUtils.getBillCode(BillPrefix.RETURN_PREFIX));
        sqlMapClient.insert("AFTERSALE_RETURN_ORDER.ibatorgenerated_insert", record);
    }

    public void insertSelective(AftersaleReturnOrder record) throws SQLException {
        sqlMapClient.insert("AFTERSALE_RETURN_ORDER.ibatorgenerated_insertSelective", record);
    }

    public Pagination selectByExample(Pagination page) throws SQLException {
        return findPaginationByPage(sqlMapClient,"AFTERSALE_RETURN_ORDER.ibatorgenerated_selectByExample",
                "AFTERSALE_RETURN_ORDER.ibatorgenerated_countByExample",page);
    }

    public List selectByExample(AftersaleReturnOrderExample example,int skip,int max) throws SQLException {
        return sqlMapClient.queryForList("AFTERSALE_RETURN_ORDER.ibatorgenerated_selectByExample", example,skip,max);
    }
    
    public AftersaleReturnOrder selectByPrimaryKey(Long returnId) throws SQLException {
        AftersaleReturnOrder key = new AftersaleReturnOrder();
        key.setReturnId(returnId);
        return (AftersaleReturnOrder) sqlMapClient.queryForObject("AFTERSALE_RETURN_ORDER.ibatorgenerated_selectByPrimaryKey", key);
    }

    public int updateByExampleSelective(AftersaleReturnOrder record, AftersaleReturnOrderExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("AFTERSALE_RETURN_ORDER.ibatorgenerated_updateByExampleSelective", parms);
    }

    public int updateByExample(AftersaleReturnOrder record, AftersaleReturnOrderExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        return sqlMapClient.update("AFTERSALE_RETURN_ORDER.ibatorgenerated_updateByExample", parms);
    }

    public int updateByPrimaryKeySelective(AftersaleReturnOrder record) throws SQLException {
        return sqlMapClient.update("AFTERSALE_RETURN_ORDER.ibatorgenerated_updateByPrimaryKeySelective", record);
    }

    public int updateByPrimaryKey(AftersaleReturnOrder record) throws SQLException {
        return sqlMapClient.update("AFTERSALE_RETURN_ORDER.ibatorgenerated_updateByPrimaryKey", record);
    }

    private static class UpdateByExampleParms extends AftersaleReturnOrderExample {
        private Object record;

        public UpdateByExampleParms(Object record, AftersaleReturnOrderExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

	@Override
	public AftersaleReturnOrder selectByOrderCode(String OrderCode) throws SQLException {
		AftersaleReturnOrder aftersaleReturnOrder=new AftersaleReturnOrder();
		aftersaleReturnOrder.setOrderCode(OrderCode);
		return (AftersaleReturnOrder)sqlMapClient.queryForObject("AFTERSALE_RETURN_ORDER.ibatorgenerated_selectByOrderCode",aftersaleReturnOrder);
	}

}