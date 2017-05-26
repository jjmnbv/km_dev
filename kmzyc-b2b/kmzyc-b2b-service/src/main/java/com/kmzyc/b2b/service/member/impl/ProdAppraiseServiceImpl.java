package com.kmzyc.b2b.service.member.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.km.framework.page.Pagination;
import com.kmzyc.b2b.dao.member.ProdAppraiseDao;
import com.kmzyc.b2b.dao.member.UserInfoDao;
import com.kmzyc.b2b.model.ProdAppraise;
import com.kmzyc.b2b.model.User;
import com.kmzyc.b2b.service.member.ProdAppraiseService;
import com.kmzyc.product.remote.service.ProductAppraiseRemoteService;
import com.pltfm.app.vobject.AppraiseAddtoContent;
import com.pltfm.app.vobject.AppraiseReply;

// import com.km.framework.common.util.RemoteTool;

/**
 * Description:商品评价信息服务接口 User: lishiming Date: 13-10-17 下午3:45 Since: 1.0
 */
@Service
public class ProdAppraiseServiceImpl implements ProdAppraiseService {

    // private static Logger logger = Logger.getLogger(ProdAppraiseServiceImpl.class);
    private static Logger logger = LoggerFactory.getLogger(ProdAppraiseServiceImpl.class);

    @Resource(name = "prodAppraiseDaoImpl")
    private ProdAppraiseDao prodAppraiseDao;

    @Resource(name = "userInfoDaoImpl")
    private UserInfoDao userInfoDao;

    @Resource
    private ProductAppraiseRemoteService productAppraiseRemoteService;

    /**
     * 计算用户某审核状态下的评价总数
     * 
     * @param userId
     * @param checkStatusList
     * @return
     * @throws java.sql.SQLException
     */
    @Override
    public com.kmzyc.b2b.model.AppraiseReply countProdAppraiseByUserId(Long userId,
            List<Integer> checkStatusList) throws SQLException {
        String checkStatusStr = StringUtils.join(checkStatusList, ",");
        logger.info("计算用户某审核状态下的评价总数:userId:" + userId + ";checkStatusList:" + checkStatusStr);
        // 使用客户数据源

        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("userId", userId);
        condition.put("checkStatus", checkStatusStr);
        return (com.kmzyc.b2b.model.AppraiseReply) prodAppraiseDao.findById(
                "ProdAppraise.countProdAppraiseByUserId", condition);
    }

    /**
     * 计算用户已被回复的评价总数
     * 
     * @param userId
     * @return
     * @throws SQLException
     */
    @Override
    public Long countRepliedProdAppraiseByUserId(Long userId) throws SQLException {
        logger.info("计算用户某审核状态下的咨询总数:userId:" + userId);

        return (Long) prodAppraiseDao.findById("ProdAppraise.countRepliedProdAppraiseByUserId",
                userId);
    }

    @Override
    public void saveAppraiseReply(User replyUser, String replyContent, String appraiseId,
            String appraiseManId, String appraiseManNickName) throws Exception {
        // 根据客户登陆id ,查询客户相关信息

        User replyMan = userInfoDao.queryLeveAndNickName(replyUser.getLoginId().intValue());
        AppraiseReply appraiseReply = new AppraiseReply();
        // 被评价的评价ID
        appraiseReply.setAppraiseId(Long.parseLong(appraiseId));
        // 主键
        appraiseReply.setApprReplyId(Long.parseLong("1"));
        // 是针对主表的回复
        appraiseReply.setApprReplyParId(Long.parseLong(appraiseId));
        appraiseReply.setCustId(replyMan.getLoginId());
        appraiseReply.setRecCustId(Long.parseLong(appraiseManId));
        appraiseReply.setRecipient(appraiseManNickName);
        appraiseReply.setReplyContent(replyContent);
        appraiseReply.setReplyDate(new Date());
        appraiseReply.setReplyPerson(replyMan.getNickName());
        // ProductAppraiseRemoteService productAppraiseRemoteService =
        // (ProductAppraiseRemoteService) RemoteTool.getRemote(Constants.REMOTE_SERVICE_PRODUCT,
        // "prodAppraiseService");
        productAppraiseRemoteService.saveAppraiseReply(appraiseReply);
    }

    @Override
    public List<ProdAppraise> findProdAppraiseByCondition(ProdAppraise prodAppraise)
            throws Exception {

        List<ProdAppraise> prodAppraiselist = prodAppraiseDao.findProdAppList(prodAppraise);
        return prodAppraiselist;
    }

    /**
     * 对产品进行追评价
     */
    @Override
    public void appendProdAppraiseByAppriaseId(AppraiseAddtoContent appraiseAdd) throws Exception {
        // ProductAppraiseRemoteService productAppraiseRemoteService =
        // (ProductAppraiseRemoteService) RemoteTool.getRemote(Constants.REMOTE_SERVICE_PRODUCT,
        // "prodAppraiseService");
        // com.pltfm.app.vobject.ProdAppraise prodAppraise1 =
        // prodAppraise.transFormToRemoteAddress();
        productAppraiseRemoteService.saveAddtoContent(appraiseAdd);

    }

    @Override
    public Pagination findAppraiseOrder(Pagination page) throws Exception {

        page =
                prodAppraiseDao.findByPage("myEvaluate.queryMyAppraise",
                        "myEvaluate.queryMyAppraiseCount", page);
        return page;
    }

    @Override
    public com.kmzyc.b2b.model.AppraiseAddtoContent findAppendByOrderAndSku(
            com.kmzyc.b2b.model.AppraiseAddtoContent appraiseAddToCondition) throws SQLException {
        return prodAppraiseDao.findAppendByOrderAndSku(appraiseAddToCondition);
    }

    @Override
    public int findProdAppraiseByOrderDetailId(Map map) throws SQLException {
        return prodAppraiseDao.findProdAppraiseByOrderDetailId(map);
    }
}
