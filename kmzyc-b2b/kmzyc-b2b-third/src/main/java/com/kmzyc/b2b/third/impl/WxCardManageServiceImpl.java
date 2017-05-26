package com.kmzyc.b2b.third.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kmzyc.b2b.third.action.WxCodeImportThread;
import com.kmzyc.b2b.third.dao.WxConsumeCodeRecordDAO;
import com.kmzyc.b2b.third.model.wxCard.WxConsumeCodeRecord;
import com.kmzyc.b2b.third.model.wxCard.WxKmrsShopInfo;
import com.kmzyc.b2b.third.model.wxCard.WxReservation;
import com.kmzyc.b2b.third.service.WxCardManageService;
import com.kmzyc.util.SpringUtils;

/**
 * 微信卡券请求处理业务类
 *
 * @author Administrator
 */
@Service("wxCardManageService")
public class WxCardManageServiceImpl implements WxCardManageService {

    private static Logger log = LoggerFactory.getLogger(WxCardManageServiceImpl.class);

    @Resource(name = "wxConsumeCodeRecordDaoImpl")
    private WxConsumeCodeRecordDAO dao;

    @Override
    public void saveRecord(WxConsumeCodeRecord para) throws SQLException {
        dao.insert(para);
    }

    @Override
    public boolean queryShopVerifyCode(String verifyCode) throws SQLException {
        Integer count = dao.queryShopVerifyCode(verifyCode);
        if (count != null && count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Map<String, Object> importCode(String cardId, String couponId, int num, String tableName)
            throws SQLException {
        int threadNum = 100;
        int importAmount = num;
        int importNumPerThread = importAmount / threadNum;
        for (int i = 0; i < threadNum; i++) {
            WxCodeImportThread thread = SpringUtils.getBean("wxCodeImportThread");
            thread.setIndex(i);
            thread.setBegin(importNumPerThread * (i) + 1);
            thread.setEnd(importNumPerThread * (i + 1) + 1);
            thread.setCardId(cardId);
            thread.setCouponId(couponId);
            thread.setTableName(tableName);
            // new WxCodeImportThread(i, importNumPerThread,cardId, couponId);
            new Thread(thread).start();
        }
        return null;
    }

    @Override
    public void saveReservation(WxReservation para) throws SQLException {
        dao.insertReservation(para);
    }

    @Override
    public WxReservation queryReservationByCardAndCode(String cardId, String code)
            throws SQLException {
        WxReservation para = new WxReservation();
        para.setCardId(cardId);
        para.setCode(code);
        return dao.queryReservationByCodeAndId(para);
    }

    @Override
    public List<WxKmrsShopInfo> queryAllKmrsShopInfo() throws SQLException {
        return dao.queryAllKmrsShopInfo();
    }

    @Override
    public Integer queryConsumeByCardAndCode(String cardId, String code) throws SQLException {
        return dao.queryConsumeByCardIdAndCode(cardId, code);
    }
}
