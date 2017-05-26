package com.kmzyc.b2b.service.member.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.kmzyc.b2b.dao.member.ReturnGoodsTraceDao;
import com.kmzyc.b2b.model.OrderAlter;
import com.kmzyc.b2b.service.member.ReturnGoodsTraceService;
import com.kmzyc.b2b.vo.TraceInfoVO;
import com.kmzyc.express.entities.ExpressSubscription;
import com.kmzyc.express.entities.ExpressTrack;
import com.kmzyc.express.remote.ExpressSubscriptionRemoteService;
import com.pltfm.app.util.OrderAlterDictionaryEnum;

@Service("returnGoodsService")
public class ReturnGoodsTraceServiceImpl implements ReturnGoodsTraceService {
    // private static Logger logger = Logger.getLogger(ReturnShopServiceImpl.class);
    private static Logger logger = LoggerFactory.getLogger(ReturnGoodsTraceServiceImpl.class);
    @Resource(name = "returnGoodsTraceDao")
    private ReturnGoodsTraceDao returnGoodsTraceDao;

    @Autowired
    private ExpressSubscriptionRemoteService expressSubscriptionRemoteService;

    @Override
    public List<TraceInfoVO> getTraceInfo(String orderAlterCode) throws Exception {
        logger.info("退货进度跟踪查询开始；退货号：" + orderAlterCode);

        List<TraceInfoVO> listAlter = returnGoodsTraceDao.getReturnGoodsInfo(orderAlterCode);
        OrderAlter oalter = this.getOrderAlter(orderAlterCode);
        Assert.notNull(oalter,"OrderAlter is null");
        ExpressSubscription expressSub1 = null;
        ExpressSubscription expressSub2 = null;
        if ( oalter.getLogisticsOrderNo() != null
                && oalter.getLogisticsCode() != null) {
            expressSub1 =
                    expressSubscriptionRemoteService
                            .queryOrderExpressInfo(oalter.getLogisticsCode().toString(), oalter
                                    .getLogisticsOrderNo().toString());
        }
        if (oalter != null && oalter.getCustomerLogisticsNo() != null
                && oalter.getCustomerLogisticsCode() != null) {
            expressSub2 =
                    expressSubscriptionRemoteService.queryOrderExpressInfo(oalter
                            .getCustomerLogisticsCode().toString(), oalter.getCustomerLogisticsNo()
                            .toString());
        }
        // 组装跟踪信息
        String alterTypeStr =
                oalter.getAlterType() == OrderAlterDictionaryEnum.AlterTypes.Indemnity.getKey() ? "超时未发货赔付"
                        : "退（换）货";
        List<TraceInfoVO> listdata = new ArrayList<TraceInfoVO>();
        if (listAlter != null) {
            TraceInfoVO tiv = null;
            for (int i = 0; i < listAlter.size(); i++) {
                tiv = listAlter.get(i);
                if (null != expressSub1
                        && (tiv.getOperatStatus() == OrderAlterDictionaryEnum.OrderAlterOperateType.Stockout
                                .getKey() || tiv.getOperatStatus() == OrderAlterDictionaryEnum.OrderAlterOperateType.BackShop
                                .getKey())) {
                    // 加入物流信息（发货）
                    List<ExpressTrack> listTracks = expressSub1.getExpressTrackList();
                    TraceInfoVO tivLogis = null;
                    for (int j = 0; null != listTracks && j < listTracks.size(); j++) {
                        ExpressTrack tempTrack = listTracks.get(j);
                        tivLogis = new TraceInfoVO();
                        tivLogis.setInfo(tempTrack.getTrackMsg());
                        tivLogis.setOperator(oalter.getLogisticsName());
                        tivLogis.setDate(tempTrack.getTrackDate());
                        listdata.add(tivLogis);
                    }
                }
                if (null != expressSub2
                        && tiv.getOperatStatus() == OrderAlterDictionaryEnum.OrderAlterOperateType.Returning
                                .getKey()) {
                    // 加入物流信息（退货）
                    List<ExpressTrack> listTracks = expressSub2.getExpressTrackList();
                    TraceInfoVO tivLogis = null;
                    for (int j = 0; null != listTracks && j < listTracks.size(); j++) {
                        ExpressTrack tempTrack = listTracks.get(j);
                        tivLogis = new TraceInfoVO();
                        tivLogis.setInfo(tempTrack.getTrackMsg());
                        tivLogis.setOperator(oalter.getCustomerLogisticsName());
                        tivLogis.setDate(tempTrack.getTrackDate());
                        listdata.add(tivLogis);
                    }
                }
                tiv.setOrderAlterCode(orderAlterCode);
                if (tiv.getOperatStatus() == OrderAlterDictionaryEnum.OrderAlterOperateType.Create
                        .getKey()) {
                    tiv.setInfo("您已经提交了" + alterTypeStr + "申请，编号为（" + orderAlterCode
                            + "），请等待售后人员审核。");
                    tiv.setOperator(oalter.getProposer());
                } else if (tiv.getOperatStatus() == OrderAlterDictionaryEnum.OrderAlterOperateType.Audit
                        .getKey()
                        && tiv.getStatus() == OrderAlterDictionaryEnum.Propose_Status.Veto.getKey()) {
                    tiv.setInfo("您的" + alterTypeStr + "申请（编号为:" + orderAlterCode
                            + "）已被拒绝，如有任何疑问，请联系客服。");
                    tiv.setOperator("康美中药城");
                } else if (tiv.getOperatStatus() == OrderAlterDictionaryEnum.OrderAlterOperateType.Audit
                        .getKey()
                        && tiv.getStatus() == OrderAlterDictionaryEnum.Propose_Status.Pass.getKey()) {
                    tiv.setInfo("您的" + alterTypeStr + "申请（编号为:" + orderAlterCode
                            + "）已经审核通过，请将退货商品寄回，并填写退货输入信息。");
                    tiv.setOperator("康美中药城");
                } else if (tiv.getOperatStatus() == OrderAlterDictionaryEnum.OrderAlterOperateType.Returning
                        .getKey()) {
                    // 退货 您已经寄出退货商品（【快递公司】 【运单编号】）
                    tiv.setInfo("您已经寄出退货商品（【" + oalter.getCustomerLogisticsName() + "】 【"
                            + oalter.getCustomerLogisticsNo() + "】)");
                    tiv.setOperator(oalter.getProposer());
                } else if (tiv.getOperatStatus() == OrderAlterDictionaryEnum.OrderAlterOperateType.Pickup
                        .getKey()) {
                    // 取件 您的退（换）货申请（编号为【退换货单号】）的退货商品已经收到，正在处理中，请稍候。
                    tiv.setInfo("您的" + alterTypeStr + "申请（编号为【" + orderAlterCode
                            + "】）的退货商品已经收到，正在处理中，请稍候。");
                    tiv.setOperator("康美中药城");
                } else if (tiv.getOperatStatus() == OrderAlterDictionaryEnum.OrderAlterOperateType.PayBack
                        .getKey()) {
                    String info =
                            oalter.getAlterType() == OrderAlterDictionaryEnum.AlterTypes.Indemnity
                                    .getKey() ? tiv.getInfo() : "的退款（含垫付运费）";
                    // 退款，您的退（换）货申请（编号为【退换货单号】）的退款（含垫付运费）已经返还到您的余额账户中，请查收。
                    tiv.setInfo("您的" + alterTypeStr + "申请（编号为【" + orderAlterCode + "】）" + info
                            + "已经返还到您的余额账户中，请查收。");
                    tiv.setOperator("康美中药城");
                } else if (tiv.getOperatStatus() == OrderAlterDictionaryEnum.OrderAlterOperateType.Stockout
                        .getKey()) {
                    // 发货，您申请的换货商品已经寄出（【快递公司】【运单编号】），请注意查收，并在收到商品后确认收货。
                    // tiv.setInfo("您申请的换货商品已经寄出（【"+oalter.getLogisticsName()+"】
                    // 【"+oalter.getLogisticsOrderNo()+"】)，请注意查收，并在收到商品后确认收货。");
                    tiv.setOperator("康美中药城");
                } else if (tiv.getOperatStatus() == OrderAlterDictionaryEnum.OrderAlterOperateType.BackShop
                        .getKey()) {
                    // 您的问题商品经检查，不符合退换货标准，商品已经通过快递寄回（【快递公司】【运单号】），请注意查收。如有任何疑问，请联系客服。
                    tiv.setInfo("您的问题商品经检查，不符合退换货标准，商品已经通过快递寄回（【" + oalter.getLogisticsName()
                            + "】 【" + oalter.getLogisticsOrderNo() + "】)请注意查收。如有任何疑问，请联系客服。");
                    tiv.setOperator("康美中药城");
                } else if (tiv.getOperatStatus() == OrderAlterDictionaryEnum.OrderAlterOperateType.Finish
                        .getKey()) {
                    // 本次退（换）货已经处理完毕，希望我们的服务能令您满意，感谢您的支持，谢谢！
                    tiv.setInfo("本次" + alterTypeStr + "已经处理完毕，希望我们的服务能令您满意，感谢您的支持，谢谢！");
                    tiv.setOperator("康美中药城");
                }
                listdata.add(tiv);
            }
        }

        logger.info("退货进度跟踪查询结束；退货号：" + orderAlterCode);
        return listdata;
    }


    @Override
    public OrderAlter getOrderAlter(String orderAlterCode) throws SQLException {

        return returnGoodsTraceDao.qryOrderAlterByCode(orderAlterCode);
    }

}
