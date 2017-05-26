/*删除mongo  package com.pltfm.app.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.pltfm.app.dao.MongoDao;
import com.pltfm.app.vobject.Params;

@SuppressWarnings("unchecked")
@Component
public class MongoDaoImpl<E> implements MongoDao<E> {
  @Resource
  private MongoTemplate paramMongoTemplate;

  *//**
   * 新增
   * 
   * @param obj
   * @return
   * @throws SQLException
   *//*
  @Override
  public void add(E e) throws SQLException {
    paramMongoTemplate.insert(e, "param");
  }

  *//**
   * 新增
   * 
   * @param obj
   * @return
   * @throws SQLException
   *//*
  @Override
  public void add(List<E> list) throws SQLException {
    if (null != list && !list.isEmpty()) {
      final String collectionName = list.get(0).getClass().getSimpleName().toLowerCase();
      for (E e : list) {
        paramMongoTemplate.insert(e, collectionName);
      }
    }
  }

  *//**
   * 新增
   * 
   * @param obj
   * @return
   * @throws SQLException
   *//*
  @Override
  public void add(E e, String collcetionName) throws SQLException {
    paramMongoTemplate.save(e, collcetionName);
  }

  *//**
   * 删除
   * 
   * @param obj
   * @return
   * @throws SQLException
   *//*
  @Override
  public void remove(E e) throws SQLException {
    paramMongoTemplate.remove(e, e.getClass().getSimpleName().toLowerCase());
  }

  *//**
   * 删除
   * 
   * @param obj
   * @return
   * @throws SQLException
   *//*
  @Override
  public void remove(E e, String collcetionName) throws SQLException {
    paramMongoTemplate.remove(e, collcetionName);
  }

  *//**
   * 查询
   * 
   * @param collcetionName 表名
   * @return
   * @throws SQLException
   *//*
  @Override
  public List<E> queryData(String collcetionName, List<Params> params, String orderBy)
      throws SQLException {
    Query query = null;
    Criteria criteria = createCriteria(params, null, null);
    Sort sort = null;
    if (orderBy != null) {
      sort = new Sort(orderBy.split(","));
    }
    if (criteria == null) {
      query = new Query();
    } else {
      query = new Query(criteria);
    }
    if (sort != null) {
      query = query.with(sort);
    }
    try {
      return (List<E>) paramMongoTemplate.find(query, Class.forName(collcetionName),
          collcetionName);
    } catch (Exception e) {
      throw new SQLException(e);
    }
  }

  *//**
   * 分页查询
   * 
   * @param collcetionName 表名
   * @return
   * @throws SQLException
   *//*
  @Override
  public List<E> queryPageData(String collcetionName, List<Params> params, int pageNo, int pageSize,
      String orderBy) throws SQLException {
    Query query = null;
    Criteria criteria = createCriteria(params, null, null);
    Sort sort = null;
    if (orderBy != null) {
      sort = new Sort(orderBy.split(","));
    }
    if (criteria == null) {
      query = new Query();
    } else {
      query = new Query(criteria);
    }
    if (sort != null) {
      query = query.with(sort);
    }
    if (pageNo > 0) {
      query.skip((pageNo - 1) * pageSize);
      query.limit(pageSize);
    }
    try {
      return (List<E>) paramMongoTemplate.find(query, Class.forName(collcetionName),
          collcetionName);
    } catch (Exception e) {
      throw new SQLException(e);
    }
  }

  *//**
   * 计数
   * 
   * @param collcetionName 表名
   * @return
   * @throws SQLException
   *//*
  @Override
  public Long countData(String collcetionName, List<Params> params) throws SQLException {
    Query query = null;
    Criteria criteria = createCriteria(params, null, null);
    if (criteria == null) {
      query = new Query();
    } else {
      query = new Query(criteria);
    }
    try {
      return paramMongoTemplate.count(query, collcetionName);
    } catch (Exception e) {
      throw new SQLException(e);
    }
  }

  private Criteria createCriteria(List<Params> params, Integer pageNo, Integer pageSize) {
    Criteria criteria = null;
    if (null != params && !params.isEmpty()) {
      criteria = new Criteria();
      List<Criteria> clist = new ArrayList<Criteria>();
      for (Params p : params) {
        if ("=".equals(p.getRegx())) {
          clist.add(Criteria.where(p.getKey()).is(p.getValue()));
        } else if (">=".equals(p.getRegx())) {
          clist.add(Criteria.where(p.getKey()).gte(p.getValue()));
        } else if ("<=".equals(p.getRegx())) {
          clist.add(Criteria.where(p.getKey()).lte(p.getValue()));
        } else if ("in".equals(p.getRegx())) {
          clist.add(Criteria.where(p.getKey()).in(p.getValue()));
        } else if (">".equals(
            p.getRegx())) {} else if ("!=".equals(p.getRegx()) || "<>".equals(p.getRegx())) {
          clist.add(Criteria.where(p.getKey()).ne(p.getValue()));
        } else if (">".equals(p.getRegx())) {
          clist.add(Criteria.where(p.getKey()).gt(p.getValue()));
        } else if ("<".equals(p.getRegx())) {
          clist.add(Criteria.where(p.getKey()).lt(p.getValue()));
        } else if ("regx".equals(p.getRegx())) {
          clist.add(Criteria.where(p.getKey()).regex(p.getValue()));
        }
      }
      criteria.andOperator(clist.toArray(new Criteria[clist.size()]));
    }
    return criteria;
  }

  *//**
   * 根据ID查询
   * 
   * @param collcetionName 表名
   * @return
   * @throws SQLException
   *//*
  public JSONObject findById(String collcetionName, String id) throws SQLException {
    try {
      Query query = new Query(Criteria.where("id").is(id));
      return paramMongoTemplate.findOne(query, JSONObject.class, collcetionName);
    } catch (Exception e) {
      throw new SQLException(e);
    }
  }
}
*/