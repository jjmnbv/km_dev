package com.pltfm.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.km.framework.mq.Sender;
import com.km.framework.mq.bean.KmMsg;
import com.kmzyc.framework.constants.EmailSendType;
import com.kmzyc.framework.constants.MessageConstants;
import com.pltfm.app.dao.OrderMainDAO;
import com.pltfm.app.service.OrderOperateStatementService;
import com.pltfm.app.service.OrderPayInformForPressellService;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.vobject.OrderPayInformForPressellVo;

import redis.clients.jedis.JedisCluster;

/**
 * 尾款支付通知
 *
 * @author KM
 */
@Service("orderPayInformForPressellService")
public class OrderPayInformForPressellServiceImpl implements OrderPayInformForPressellService {

    private static final Logger logger =
            Logger.getLogger(OrderPayInformForPressellServiceImpl.class);
    @Resource
    private OrderMainDAO orderMainDAO;
    @Resource
    private JmsTemplate jmsTemplate;
    @Resource
    private javax.jms.Destination destination;
    @Resource
    private JedisCluster jedisCluster;
    @Resource
    private OrderOperateStatementService orderOperateStatementService;


    @Override
    public void operate() {
        String key = "OrderPayInformForPressell_running";
        try {
            Long i = jedisCluster.setnx(key, "1");
            if (i > 0) { // 并发控制
                jedisCluster.expire(key, 60 * 60 * 3); // 缓存过期 3小时

                List<OrderPayInformForPressellVo> list =
                        this.orderMainDAO.queryOrderPayInformForPressell();
                logger.info("定时任务：发送尾款支付通知短信======开始！总订单数（" + list.size() + "）");
                int success = 0;
                // 发送短信
                if (!list.isEmpty()) {
                    for (OrderPayInformForPressellVo vo : list) {
                        if (this.sendMessage(vo)) {
                            vo.setMessageSendFlag(1);

                            try {
                                // 修改订单扩展表状态短信为已发
                                this.orderMainDAO.updateOrderMainExtMessageSendFlag(vo);

                                // 生成订单跟踪记录
                                this.orderOperateStatementService.save(vo.getOrderCode(),
                                        vo.getOrderItemId(), vo.getOrderStatus(), "系统自动生成", null,
                                        (long) OrderDictionaryEnum.OrderOperateType.Create.getKey(),
                                        vo.getAmountPayable(), "请您支付尾款。");

                                success++;
                            } catch (Exception e) {
                                logger.error(vo.getInformPayTel() + " 发送尾款支付通知短信成功,更新数据库异常！", e);
                            }
                        } else {
                            logger.error(vo.getInformPayTel() + " 发送尾款支付通知短信失败！");
                        }
                    }
                }


                logger.info("定时任务：发送尾款支付通知短信======结束！总订单数（" + list.size() + "）成功（" + success
                        + "）失败（" + (list.size() - success) + "）");
            }
        } catch (Exception e) {
            logger.error("OrderPayInformForPressell executeInternal", e);
        }
    }

    /**
     * 发送尾款支付通知短信
     */
    private boolean sendMessage(OrderPayInformForPressellVo vo) {
        boolean flag = true;
        try {
            // 短信
            KmMsg kmMsg = new KmMsg("6000", true);
            kmMsg.getMsgData().put("kmMsgType", MessageConstants.KMMSG_MOBIL);
            kmMsg.getMsgData().put("smsmailType", EmailSendType.MSG_PRESELL_FINALPAY.getStatus());
            kmMsg.getMsgData().put("systemType", MessageConstants.KMMSG_SYSTEM_TYPE_B2B);
            List<String> list = new ArrayList<String>();
            list.add(vo.getInformPayTel());
            kmMsg.getMsgData().put("mobilePhones", list);
            kmMsg.getMsgData().put("orderPayDate", vo.getDepositPayFinishTimeFormartMD());// 定金支付日期（xx月xx日）
            kmMsg.getMsgData().put("finalpayEndTime", vo.getFinalpayEndTimeFormartYMDHMS());// 尾款支付截止时间（xx月xx日xx时xx分xx秒）
            kmMsg.getMsgData().put("productSkucode", vo.getProductSkuCode());// skucode
            kmMsg.getMsgData().put("msgSendType", MessageConstants.EM_SEND_TYPE_IMM);
            // 1为立即发送，2为定时发送
            Sender.send(kmMsg, destination, jmsTemplate);
        } catch (Exception e) {
            flag = false;
            logger.error(vo.getInformPayTel() + " 尾款支付通知短信发送异常！", e);
        }
        return flag;
    }

}
