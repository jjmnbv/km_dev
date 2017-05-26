package com.pltfm.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.kmzyc.cms.remote.service.DetailPublishService;
import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.service.CmsProductUpShelfService;
import com.pltfm.app.threadHandler.CmsProductUpShelfHandler;

@Service("cmsProductUpShelfService")
public class CmsProductUpShelfServiceImpl implements CmsProductUpShelfService {

    @Resource
    private TaskExecutor taskExecutor;

    @Resource
    private DetailPublishService detailPublishService;

    @Override
    public void productUpShelfByCms(List<Integer> productIdList) throws ServiceException {
        try {
            taskExecutor.execute(new CmsProductUpShelfHandler(detailPublishService, productIdList));
        } catch (Exception e) {
            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
            }
            throw new ServiceException(e);
        }
    }
}
