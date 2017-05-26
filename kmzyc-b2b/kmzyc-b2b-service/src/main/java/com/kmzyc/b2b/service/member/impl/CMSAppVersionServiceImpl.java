package com.kmzyc.b2b.service.member.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.kmzyc.b2b.dao.member.CMSAppVersionDao;
import com.kmzyc.b2b.model.AppVersion;
import com.kmzyc.b2b.service.member.CMSAppVersionService;

@Repository("versionService")
public class CMSAppVersionServiceImpl implements CMSAppVersionService {
    @Resource(name = "versionDao")
    private CMSAppVersionDao VersionDao;

    @Override
    public List<AppVersion> getNewestVersion(String osType) throws SQLException {

        return VersionDao.getNewestVersion(osType);
    }

}
