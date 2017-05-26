package com.pltfm.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.OrderRiskRosterDAO;
import com.pltfm.app.entities.OrderRiskBackList;
import com.pltfm.app.entities.OrderRiskWhiteList;
import com.pltfm.app.service.OrderRiskRosterService;
import com.pltfm.app.util.OrderRiskKey;
import com.pltfm.sys.util.ErrorCode;

import redis.clients.jedis.JedisCluster;


@Service
@Scope("singleton")
public class OrderRiskRosterServiceImpl implements OrderRiskRosterService {

    @Resource
    private OrderRiskRosterDAO OrderRiskRosterDAO;
    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;

    /**
     * 分页获取风控黑名单
     */
    @Override
    public Page queryBlackListByPage(Page page, Map<String, String> params) throws
            ServiceException {
        try {
            return OrderRiskRosterDAO.queryBlackListByPage(page, params);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INTER_ORDER_RISK_ERROR,
                    "分页获取风控黑名单发生异常：" + e.getMessage());
        }
    }

    /**
     * 添加黑名单
     */
    @Override
    public boolean saveBlackList(OrderRiskBackList back) throws ServiceException {
        try {
            return OrderRiskRosterDAO.saveBlackList(back);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INTER_ORDER_RISK_ERROR,
                    "添加黑名单发生异常：" + e.getMessage());
        }
    }

    /**
     * 删除黑名单
     */
    @Override
    public boolean deleteBlackList(Long bid) throws ServiceException {
        try {
            return OrderRiskRosterDAO.deleteBlackList(bid);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INTER_ORDER_RISK_ERROR,
                    "删除黑名单发生异常：" + e.getMessage());
        }
    }

    /**
     * 分页获取风控白名单
     */
    @Override
    public Page queryWhiteListByPage(Page page, Map<String, String> params) throws
            ServiceException {
        try {
            return OrderRiskRosterDAO.queryWhiteListByPage(page, params);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INTER_ORDER_RISK_ERROR,
                    "分页获取风控白名单发生异常：" + e.getMessage());
        }
    }

    /**
     * 添加白名单
     */
    @Override
    public boolean saveWhiteList(OrderRiskWhiteList orwl) throws ServiceException {
        try {
            return OrderRiskRosterDAO.saveWhiteList(orwl);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INTER_ORDER_RISK_ERROR,
                    "添加黑名单发生异常：" + e.getMessage());
        }
    }

    /**
     * 删除白名单
     */
    @Override
    public boolean deleteWhiteList(Long wid) throws ServiceException {
        try {
            return OrderRiskRosterDAO.deleteWhiteList(wid);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INTER_ORDER_RISK_ERROR,
                    "删除白名单发生异常：" + e.getMessage());
        }
    }

    @Override
    public List<OrderRiskBackList> queryBlack(Integer type, String content) throws
            ServiceException {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("type", type);
            params.put("content", content);
            return OrderRiskRosterDAO.queryBlackList(params);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INTER_ORDER_RISK_ERROR,
                    "删除白名单发生异常：" + e.getMessage());
        }
    }

    @Override
    public List<OrderRiskWhiteList> queryWhite(Integer type, String content) throws
            ServiceException {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("type", type);
            params.put("content", content);
            return OrderRiskRosterDAO.queryWhiteList(params);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INTER_ORDER_RISK_ERROR,
                    "删除白名单发生异常：" + e.getMessage());
        }
    }

    @Override
    public boolean isExistBlack(Integer type, String content) throws ServiceException {
        boolean result = false;
        if (jedisCluster.sismember(OrderRiskKey.ORDER_RISK_BLACK_LIST + '_' + type, content)) {
            result = true;
        } else {
            List<OrderRiskBackList> list = queryBlack(type, content);
            if (list != null && list.size() > 0) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean isExistWhite(Integer type, String content) throws ServiceException {
        boolean result = false;
        if (jedisCluster.sismember(OrderRiskKey.ORDER_RISK_WHITE_LIST + '_' + type, content)) {
            result = true;
        } else {
            List<OrderRiskWhiteList> list = queryWhite(type, content);
            if (list != null && list.size() > 0) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean isExistAccount(Integer type, String content) throws ServiceException {
        boolean result = false;
        Long count = 0L;
        try {
            if (type ==
                    OrderRiskKey.OrderRiskRosterTypeEnum.ROSTER_TYPE_CUSTOMER_ACCOUNT.getCode()) {
                count = OrderRiskRosterDAO.queryLoginInfo(content);
            } else if (type ==
                    OrderRiskKey.OrderRiskRosterTypeEnum.ROSTER_TYPE_COMMERCE.getCode()) {
                count = OrderRiskRosterDAO.queryCorporate(content);
            } else {
                result = true;
            }
            if (count.intValue() > 0) {
                result = true;
            }
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INTER_ORDER_RISK_ERROR,
                    "查询账号信息失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public boolean queryBlackList(long loginId) throws ServiceException {

        boolean result = false;
        try {
            result = this.OrderRiskRosterDAO.queryBlackListByLoginId(loginId);
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INTER_ORDER_RISK_ERROR,
                    "根据登录id查询风控黑名单Service失败：" + e.getMessage());
        }
        return result;
    }
}
