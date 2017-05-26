package com.pltfm.app.service.impl;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.ConsultDAO;
import com.pltfm.app.dao.FilterFieldDAO;
import com.pltfm.app.enums.ConsultCheckStatus;
import com.pltfm.app.enums.ConsultReplyStatus;
import com.pltfm.app.service.ConsultService;
import com.pltfm.app.util.KeywordFilter;
import com.pltfm.app.vobject.Consult;
import com.pltfm.app.vobject.ConsultExample;
import com.pltfm.app.vobject.FilterField;
import com.kmzyc.commons.page.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service("ConsultService")
public class ConsultServiceImpl implements ConsultService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ConsultDAO consultDao;

    @Resource
    private FilterFieldDAO filterFieldDao;

    public Page queryConsultList(Page pageParam, Consult consult) throws ServiceException {
        int pageNo = pageParam.getPageNo();// 当前查询第几页
        if (pageNo == 0)
            pageNo = 1;// 首次查询第一页
        int pageSize = pageParam.getPageSize(); // 每页显示记录数
        int skip = (pageNo - 1) * pageSize + 1;
        int max = (pageNo - 1) * pageSize + pageSize;
        Page page = null;

        try {
            consult.setSkip(skip);
            consult.setMax(max);
            page = consultDao.selectPageByVo(pageParam, consult);
            page.setPageNo(pageNo);// 当前查询第几页
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new ServiceException(e);
        }
        return page;

    }

    @Override
    public Consult queryConsultByConsultId(BigDecimal consultId) throws ServiceException {
        try {
            return consultDao.selectByPrimaryKey(consultId.longValue());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int updateConsultByConsultId(Consult consult) throws ServiceException {
        try {
            //将状态改为已回复
            return consultDao.updateByPrimaryKey(consult);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public void checkReplyBySys() throws ServiceException {
        List<Consult> replyList = null;
        try {
            //查询审核关键字
            List<FilterField> filterList = filterFieldDao.selectAllFilter();
            //查询已回复的，待审核的咨询
            ConsultExample conExm = new ConsultExample();
            conExm.createCriteria()
                    .andCheckStatusEqualTo(Short.valueOf(ConsultCheckStatus.WAITCHECK.getStatus()))
                    .andReplyStatusEqualTo(Short.valueOf(ConsultReplyStatus.HAVERESPONSE.getStatus()));
            replyList = consultDao.selectByExample(conExm);

            //敏感词
            KeywordFilter keyFilter = new KeywordFilter();
            String[] keyWords = filterList.stream().map(filter -> filter.getFieldName()).toArray(String[]::new);
            keyFilter.addKeywords(keyWords);
            keyFilter.setMatchType(2);

            Consult consult;
            for (int i = 0; i < replyList.size(); i++) {
                consult = replyList.get(i);
                consult.setCheckMan("Sys");
                consult.setCheckManId(BigDecimal.valueOf(0));
                //如果不通过
                if (keyFilter.isContentKeyWords(consult.getReplyContent())) {
                    consult.setCheckStatus(Short.valueOf(ConsultCheckStatus.NOTPASSED.getStatus()));
                    consult.setRemark("系统过滤关键字未通过");
                } else {
                    consult.setCheckStatus(Short.valueOf(ConsultCheckStatus.HAVEPASSED.getStatus()));
                    consult.setRemark("系统过滤关键字通过");
                }
                consultDao.updateByPrimaryKeySelective(consult);
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

}