package com.pltfm.activity.service;

import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.vobject.ActivityInfo;
import com.kmzyc.commons.exception.ServiceException;

public interface ActivityAuditService {

    /**
     * 审核活动信息
     *
     * @param activityInfo
     * @return
     * @throws ServiceException
     */
    ResultMessage auditActivityById(ActivityInfo activityInfo) throws ServiceException;
}
