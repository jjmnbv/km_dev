package com.pltfm.crowdsourcing.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.km.crowdsourcing.model.Bagman;


public interface BagmanDao {

  /**
   * @Title: insert @Description: 新增业务员 @param @param record @param @return @return void @throws
   */
  void insert(Bagman record) throws SQLException;


  Bagman selectByPrimaryKey(Long id) throws SQLException;

  Bagman selectByBagman(Bagman bagman);

  int updateByPrimaryKeySelective(Bagman record) throws SQLException;

  // 机构数量自增1
  int updateByBagManId(Long bagmanid) throws SQLException;


  /**
   * @Title: existsBmans @Description: 根据条件查询已存在的Bagman的列表 @param @param
   * record @param @return @param @throws SQLException @return List返回的结果集为已存在姓名的个数、已存在号码的个数 @throws
   */
  List<Map<String, Object>> existsBmans(Bagman record) throws SQLException;

  /**
   * @Title: listBagMans @Description: 众包机构业务员列表 @param Bagman @param @throws SQLException @return
   * List @throws
   */
  List<Bagman> listBagMans(Bagman bagman) throws SQLException;

  /**
   * @Title: countBagMans @Description: 列表记录数 @param bagman @param @throws SQLException @return
   * int @throws
   */
  int countBagMans(Bagman bagman) throws SQLException;

  /**
   * @Title: ajaxBagManInfos @Description: 异步获取所有的业务员信息（以id:name键值对形式） @param @return @param @throws
   * SQLException @return List @throws
   */
  List<Map<String, Object>> ajaxBagManInfos() throws SQLException;
}
