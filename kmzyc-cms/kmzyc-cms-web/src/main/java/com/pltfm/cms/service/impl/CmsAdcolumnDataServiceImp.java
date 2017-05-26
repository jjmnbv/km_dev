package com.pltfm.cms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.kmzyc.commons.page.Page;
import com.pltfm.cms.dao.CmsAdcolumnDAO;
import com.pltfm.cms.service.CmsAdcolumnDataService;
import com.pltfm.cms.vobject.CmsAdcolumn;
import com.pltfm.cms.vobject.CmsAdcolumnExample;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

@Component("cmsAdcolumnDataService")
public class CmsAdcolumnDataServiceImp implements CmsAdcolumnDataService {
	private static Logger logger = LoggerFactory.getLogger(CmsAdcolumnDataServiceImp.class);

    @Resource(name = "cmsAdcolumnDAO")
    private CmsAdcolumnDAO cmsAdcolumnDAO;


    @Override
    public List<CmsAdcolumn> list(CmsAdcolumnExample example) {
        // TODO Auto-generated method stub

        List<CmsAdcolumn> list = null;
        try {
            list = cmsAdcolumnDAO.selectByExample(example);
        } catch (SQLException e) {
        	logger.error("CmsAdcolumnDataServiceImp.list异常：" + e.getMessage(), e);
        }
        return list;
    }


    @Override
    public Integer addCmsAdcolumn(CmsAdcolumn cmsAdcolumn) throws Exception {
        return cmsAdcolumnDAO.insert(cmsAdcolumn);
    }


    @Override
    public Page queryForPage(CmsAdcolumn cmsAdcolumn, Page page)
            throws Exception {

        if (page == null) {
            page = new Page();
        }
        if (cmsAdcolumn == null) {
            cmsAdcolumn = new CmsAdcolumn();
        }
        // 根据条件获取广告信息总条数
        int totalNum = cmsAdcolumnDAO.countByExample(cmsAdcolumn);

        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            cmsAdcolumn.setStartIndex(page.getStartIndex());
            cmsAdcolumn.setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(cmsAdcolumnDAO.queryForPage(cmsAdcolumn));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }


    @Override
    public CmsAdcolumn byid(Integer Advid) throws Exception {

        return cmsAdcolumnDAO.byid(Advid);
    }


    @Override
    public Integer update(CmsAdcolumn cmsAdcolumn) throws Exception {

        return cmsAdcolumnDAO.updateByPrimaryKeySelective(cmsAdcolumn);
    }

   /* 删除前判断些广告位是否有广告  */

    @Override
    public Integer delete(Integer Advid) throws Exception {
        int s = cmsAdcolumnDAO.byAdvId(Advid);

        if (s == 0) {
            return cmsAdcolumnDAO.deleteByPrimaryKey(Advid);
        } else {
            return 0;
        }
    }

    //返回所有广告位列表
    public List queryAdcolumnList() throws SQLException {
        return cmsAdcolumnDAO.queryAdcolumnList();
    }


    @Override
    public Integer byAdId(Integer Advid) throws Exception {
        // TODO Auto-generated method stub
        return cmsAdcolumnDAO.byAdvId(Advid);
    }


    @Override
    public Integer byAdvcolumn(CmsAdcolumn cmsAdcolumn) throws Exception {
        // TODO Auto-generated method stub
        return cmsAdcolumnDAO.countByAdcolumn(cmsAdcolumn);
    }

    public Integer countByAdcolumnname(CmsAdcolumn cmsAdcolumn) throws Exception {
        // TODO Auto-generated method stub
        return cmsAdcolumnDAO.countByAdcolumnname(cmsAdcolumn);
    }


    @Override
    public List queryAdcolumnListBySupplier(CmsAdcolumn cmsAdcolumn)
            throws SQLException {
        // TODO Auto-generated method stub
        return cmsAdcolumnDAO.queryAdcolumnListBySupplier(cmsAdcolumn);
    }


}
