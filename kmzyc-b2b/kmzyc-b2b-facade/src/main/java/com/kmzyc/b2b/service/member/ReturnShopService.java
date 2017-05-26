package com.kmzyc.b2b.service.member;

import java.sql.SQLException;
import java.util.List;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.model.Address;
import com.kmzyc.b2b.model.OrderAlter;
import com.pltfm.app.entities.OrderAlterPhoto;

public interface ReturnShopService {

  /**
   * 根据查询条件进行分页查询
   * 
   * @param page
   * @return
   * @throws SQLException
   */
  public Pagination findReturnShopByPage(Pagination page) throws SQLException;

  /**
   * 根据单号查询
   * 
   * @param orderAlterCode
   * @throws Exception
   */
  public OrderAlter findByCode(String orderAlterCode) throws Exception;

  /**
   * 申请
   * 
   * @param operator 操作人
   * @param orderCode 订单号
   * @param orderItemId 订单项id
   * @param alterType 服务类型
   * @param alterNum 数量
   * @param evidence 凭据
   * @param alterComment 描述
   * @param batchNo 上传图片批次号
   * @param backType 商品退回方式
   * @param addressId 地址id
   * @param name 姓名
   * @param province 省
   * @param city 市
   * @param area 区
   * @param zipcode 邮编
   * @param address 详细地址
   * @param phone 手机号
   * @param commodityBatchNumber 商品批次号
   * @return
   * @throws Exception
   */
  public int apply(String operator, String orderCode, Long orderItemId, Short alterType,
      Long alterNum, Long evidence, String alterComment, String batchNo, Short backType,
      Integer addressId, String name, String province, String city, String area, Integer zipcode,
      String address, String phone, String commodityBatchNumber, long loginId) throws Exception;

  /**
   * 更改状态
   * 
   * @param operator 操作人
   * @param loginId 订单号
   * @param status 状态
   * @param code 快递公司代码
   * @param no 运单号
   * @param addressVar 地址
   * @param customerLogisticsName 快递公司名称
   * @throws Exception
   */
  public int changeOrderAlterStatus(String operator, String orderAlterCode, Long status,
      String code, String no, Address addressVar, String customerLogisticsName, long loginId) throws Exception;

  /**
   * 保存图片路径
   */
  public int savaPhoto(OrderAlterPhoto photo) throws Exception;

  public List getPhotoByBatchNo(String photoBatchNo) throws Exception;

  public List listFareTypes() throws Exception;

  public Pagination findReturnOrderMainByPage(Pagination page) throws SQLException;

  /**
   * 校验 修改订单状态
   * @param orderAlterCode
   * @param orderAlterStatus
   * @return 成功：0 校验成功  失败：1-校验异常 2-参数为空 3-非当前用户操作 4-审核已通过,7*24小时内不允许取消
   */
  public int checkApplyStatus(String orderAlterCode,long orderAlterStatus);

}
