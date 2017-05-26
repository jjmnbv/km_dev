/*删除mongo  package com.pltfm.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.pltfm.app.vobject.Params;

public interface MongoDao<E> {
  *//**
   * 新增
   * 
   * @param obj
   * @return
   * @throws SQLException
   *//*
  public void add(E e) throws SQLException;

  *//**
   * 新增
   * 
   * @param obj
   * @return
   * @throws SQLException
   *//*
  public void add(List<E> list) throws SQLException;

  *//**
   * 新增
   * 
   * @param obj
   * @param collcetionName 表名
   * @return
   * @throws SQLException
   *//*
  public void add(E e, String collcetionName) throws SQLException;

  *//**
   * 删除
   * 
   * @param obj
   * @return
   * @throws SQLException
   *//*
  public void remove(E e) throws SQLException;

  *//**
   * 删除
   * 
   * @param obj
   * @param collcetionName 表名
   * @return
   * @throws SQLException
   *//*
  public void remove(E e, String collcetionName) throws SQLException;

  *//**
   * 查询
   * 
   * @param collcetionName 表名
   * @return
   * @throws SQLException
   *//*
  public List<E> queryData(String collcetionName, List<Params> params, String orderBy)
      throws SQLException;

  *//**
   * 分页查询
   * 
   * @param collcetionName 表名
   * @return
   * @throws SQLException
   *//*
  public List<E> queryPageData(String collcetionName, List<Params> params, int pageNo, int pageSize,
      String orderBy) throws SQLException;

  *//**
   * 计数
   * 
   * @param collcetionName 表名
   * @return
   * @throws SQLException
   *//*
  public Long countData(String collcetionName, List<Params> params) throws SQLException;

  *//**
   * 根据ID查询
   * 
   * @param collcetionName 表名
   * @return
   * @throws SQLException
   *//*
  public JSONObject findById(String collcetionName, String id) throws SQLException;
}
*/