package com.kmzyc.product.remote.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kmzyc.product.remote.service.ConsultRemoteService;
import com.pltfm.app.dao.ConsultDAO;
import com.pltfm.app.vobject.Consult;
import com.pltfm.sys.bean.SysUserBean;
import com.pltfm.sys.model.SysModelUtil;
import com.pltfm.sys.model.SysUser;

@Service("consultRemoteService")
public class ConsultRemoteServiceImpl implements ConsultRemoteService {

	@Resource
	private ConsultDAO consultDao;
	
	@Override
	public int getConsultNumber(Consult vo) {
		// TODO Auto-generated method stub

		int recs = 0;
		try {

			List list = consultDao.queryForList(vo);
			SysModelUtil countResult = (SysModelUtil) list.get(0);
			// 总条数
			recs = countResult.getTheCount().intValue();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return recs;
	}

	@Override
	public List getPageConsult(Consult vo) throws SQLException {

		List pageConsult = consultDao.queryForPage(vo);
		return pageConsult;
	}

	/**
	 * 查询咨询明细
	 * 
	 */
	public Consult gotoViewConsult(long pre_consultId) throws Exception {
		Consult consult = consultDao.selectByPrimaryKey(pre_consultId);
		// 查询咨询明细实体
		SysUserBean sysBean = new SysUserBean();
		SysUser sys = sysBean.getSysUserDetail(Integer.parseInt(consult
				.getReplyPerson()));
		consult.setReplyPerson(sys.getUserName());
		return consult;
	}

	/**
	 * 
	 * 插入咨询表
	 */
	@Override
	public Consult saveConsult(Consult consult) throws Exception {
		Consult c = consultDao.insertConsult(consult);
		return c;
	}

}
