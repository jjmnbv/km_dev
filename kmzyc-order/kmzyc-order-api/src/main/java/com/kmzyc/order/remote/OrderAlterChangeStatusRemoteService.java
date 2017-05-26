package com.kmzyc.order.remote;

import java.io.Serializable;
import java.util.Map;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.entities.OrderAlter;
import com.pltfm.app.entities.OrderAlterPhoto;

/**
 * 退换货单更改状态接口
 * 
 * @author chenwei
 * @date 2013.09.10
 */
@SuppressWarnings("unchecked")
public interface OrderAlterChangeStatusRemoteService extends Serializable {

    /**
     * 计算
     * 
     * @param oa
     * @return
     * @throws ServiceException
     */
    public Map compute(String orderCode, Long orderItemId, Long alterNum) throws ServiceException;

    /**
     * 申请
     * 
     * @param operator 操作人
     * @param orderCode 订单号
     * @param orderItemId 订单明细号
     * @param alterType 服务类型
     * @param alterNum 数量
     * @param evidence 凭据
     * @param alterComment 描述
     * @param batchNo 上传图片批次号
     * @param backType 取件方式
     * @param addressId 快捷地址id
     * @param name 姓名
     * @param address 地址
     * @param phone 电话
     * @return
     * @throws ServiceException
     */
    public int alter(OrderAlter oa) throws ServiceException;

    /**
     * 更改状态
     * 
     * @param operator 操作人
     * @param orderAlterCode 退换货单号
     * @param status 状态
     * @param no
     * @throws ServiceException 1 操作成功 -1 未知退换货单状态 -2 此状态下不能进行此操作 -3 调用远程接口异常 -4 数据查询异常
     */
    public int changeAlterStatus(String operator, OrderAlter oa) throws ServiceException;

    /**
     * 更改状态 for PRODUCT
     * 
     * @param operator 操作人
     * @param orderAlterCode 退换货单号
     * @param status 状态
     * @param code 物流公司代号
     * @param no 运单号
     * @param comment 质检说明
     * @throws ServiceException 1 操作成功 -1 未知退换货单状态 -2 此状态下不能进行此操作 -3 调用远程接口异常 -4 数据查询异常
     */
    public int changeAlterStatusForProduct(String operator, String orderAlterCode, Long status,
            String comment) throws ServiceException;

    /**
     * 更新
     */
    public int savaPhoto(OrderAlterPhoto photo) throws ServiceException;


    /**
     * 供应商导所有退换货订单
     * 
     * @param 查询参数
     * @return 导出的excel路径
     * @throws ServiceException
     */
    public String exportSellerAlterOrders(Map map) throws ServiceException;



}
