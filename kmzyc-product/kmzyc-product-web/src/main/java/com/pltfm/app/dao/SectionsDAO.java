package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.Sections;
import com.pltfm.app.vobject.SectionsExample;
import com.kmzyc.commons.page.Page;

public interface SectionsDAO {

    int countByExample(SectionsExample example) throws SQLException;

    int deleteByExample(SectionsExample example) throws SQLException;

    int deleteByPrimaryKey(Long sectionsId) throws SQLException;

    void insert(Sections record) throws SQLException;

    void insertSelective(Sections record) throws SQLException;

    List selectByExample(SectionsExample example) throws SQLException;

    Sections selectByPrimaryKey(Long sectionsId) throws SQLException;

    int updateByExampleSelective(Sections record, SectionsExample example) throws SQLException;

    int updateByExample(Sections record, SectionsExample example) throws SQLException;

    int updateByPrimaryKeySelective(Sections record) throws SQLException;

    int updateByPrimaryKey(Sections record) throws SQLException;

	List selectByExample(SectionsExample example,int skip,int max) throws SQLException;

	List<Long> checkSectionsNameByModify(String sectionsName) throws SQLException;
	
	List<Long> checkIdentificationByModify(String identification) throws SQLException;
}