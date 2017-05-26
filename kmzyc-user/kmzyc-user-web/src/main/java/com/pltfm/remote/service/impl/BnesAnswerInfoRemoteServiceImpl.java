package com.pltfm.remote.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.user.remote.service.BnesAnswerInfoRemoteService;
import com.pltfm.app.dao.BnesAnswerInfoDAO;
import com.pltfm.app.util.DateTimeUtils;
import com.pltfm.app.vobject.BnesAnswerInfo;
import com.pltfm.app.vobject.BnesAnswerInfoExample;

/**
 * 安全问题答案信息远程接口实现类
 * 
 * @author cjm
 * @since 2013-8-9
 */
@Component(value = "bnesAnswerInfoRemoteService")
public class BnesAnswerInfoRemoteServiceImpl implements BnesAnswerInfoRemoteService {



    /**
     * 安全问题答案DAO接口
     */
    @Resource(name = "bnesAnswerInfoDAO")
    private BnesAnswerInfoDAO bnesAnswerInfoDAO;

    /**
     * 添加安全问题答案信息
     * 
     * @param bnesAnswerInfo 安全问题答案信息实体
     * @return 返回值
     * @throws Exception 异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addBnesAnswerInfo(BnesAnswerInfo bnesAnswerInfo, Integer type) throws Exception {
        if (bnesAnswerInfo != null) {
            bnesAnswerInfo.setCreateDate(DateTimeUtils.getCalendarInstance().getTime());
            return bnesAnswerInfoDAO.insert(bnesAnswerInfo);
        }
        return 0;
    }

    /**
     * 根据登录ID、安全问题ID和答案信息，验证是否正确
     * 
     * @param bnesAnswerInfo 安全问题答案信息实体
     * @param type 1、产品 2、订单 3、B2B
     * @return 返回值
     * @throws Exception
     */
    @Override
    public BnesAnswerInfo selectByBnesAnswerInfo(BnesAnswerInfo bnesAnswerInfo, Integer type)
            throws Exception {
        BnesAnswerInfo bnesAnswer = null;
        if (bnesAnswerInfo != null) {
            BnesAnswerInfoExample example = new BnesAnswerInfoExample();
            // 添加查询条件登录ID和答案信息
            example.createCriteria().andAnswerContentEqualTo(bnesAnswerInfo.getAnswerContent())
                    .andLoginIdEqualTo(bnesAnswerInfo.getLoginId())
                    .andSafeQuestionIdEqualTo(bnesAnswerInfo.getSafeQuestionId());
            List<BnesAnswerInfo> list = bnesAnswerInfoDAO.selectByExample(example);
            bnesAnswer = list.get(0);
        }
        return bnesAnswer;
    }

    public BnesAnswerInfoDAO getBnesAnswerInfoDAO() {
        return bnesAnswerInfoDAO;
    }

    public void setBnesAnswerInfoDAO(BnesAnswerInfoDAO bnesAnswerInfoDAO) {
        this.bnesAnswerInfoDAO = bnesAnswerInfoDAO;
    }

}
