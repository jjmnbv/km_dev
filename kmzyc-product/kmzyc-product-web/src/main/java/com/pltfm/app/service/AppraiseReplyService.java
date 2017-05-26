package com.pltfm.app.service;

import java.util.List;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.AppraiseReply;
import com.pltfm.app.vobject.ProdAppraise;
import com.kmzyc.commons.page.Page;

public interface AppraiseReplyService {

    /**
     * 某条评价的所有回复；relply（0：客服的回复；1：客户的回复；null：所有的回复）
     *
     * @param appraiseId
     * @param replyStyle
     * @return
     * @throws ServiceException
     */
    List<AppraiseReply> findByAppraiseId(Long appraiseId, Short replyStyle) throws ServiceException;

    /**
     * 增加回复
     *
     * @param reply
     * @throws ServiceException
     */
    void saveReply(AppraiseReply reply, ProdAppraise prodApp) throws ServiceException;

    /**
     * 回复数据列表
     *
     * @param page
     * @param reply
     * @throws ServiceException
     */
    void searchByPage(Page page, AppraiseReply reply) throws ServiceException;

    /**
     * 删除
     *
     * @param appraiseReplyIds
     * @throws ServiceException
     */
    void deleteBatch(Long[] appraiseReplyIds) throws ServiceException;

    /**
     * 审核通过
     *
     * @param appraiseReplyIds
     * @throws ServiceException
     */
    void updatePass(Long[] appraiseReplyIds) throws ServiceException;

    /**
     * 审核不通过
     *
     * @param appraiseReplyIds
     * @throws ServiceException
     */
    void updateUnPass(Long[] appraiseReplyIds) throws ServiceException;

    /**
     * 修改回复表信息
     *
     * @param reply
     * @return
     * @throws ServiceException
     */
    int updateByAppraiseReplyId(AppraiseReply reply) throws ServiceException;
}
