package com.pltfm.app.service;

import java.math.BigDecimal;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.vobject.Consult;
import com.kmzyc.commons.page.Page;

public interface ConsultService {

    /**
     * 分页查询
     *
     * @param pageParam
     * @param consult
     */
    Page queryConsultList(Page pageParam, Consult consult) throws ServiceException;

    /**
     * 根据咨询Id查询
     *
     * @param consultId
     * @return
     */
    Consult queryConsultByConsultId(BigDecimal consultId) throws ServiceException;

    /**
     * 根据id更新
     *
     * @param consult
     */
    int updateConsultByConsultId(Consult consult) throws ServiceException;

    /**
     * 进行系统自动审核回复
     */
    void checkReplyBySys() throws ServiceException;

}