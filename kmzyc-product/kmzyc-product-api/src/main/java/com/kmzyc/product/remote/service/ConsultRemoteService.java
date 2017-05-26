package com.kmzyc.product.remote.service;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.Consult;

public interface ConsultRemoteService {
	
	/**
	 * 取得咨询总条数
	 * @return
	 */
	public int getConsultNumber(Consult vo);
	
	/**
	 * 咨询查询，分页
	 */
	public List getPageConsult(Consult vo) throws SQLException;
	
	/**
	 * 查询咨询明细
	 * 
	 */
	public Consult gotoViewConsult(long pre_consultId) throws Exception;
	
	/**
	 * 
	 * 插入咨询表
	 */
	public Consult  saveConsult(Consult consult) throws Exception;
}
