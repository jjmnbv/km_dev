package com.pltfm.app.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pltfm.app.bean.ResultMessage;
import com.pltfm.app.dao.SectionsDAO;
import com.pltfm.app.dao.SectionsDetailDAO;
import com.pltfm.app.service.SectionsService;
import com.pltfm.app.vobject.Product;
import com.pltfm.app.vobject.Sections;
import com.pltfm.app.vobject.SectionsExample;
import com.pltfm.app.vobject.SectionsExample.Criteria;
import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.commons.page.Page;

/**
 * 栏目管理逻辑处理类
 *
 * @author humy
 * @since 2013-7-9
 */
@Service("sectionsService")
public class SectionsServiceImpl implements SectionsService {

    Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SectionsDAO sectionsDao;

    @Resource
    private SectionsDetailDAO SectionsDetailDao;

    @Override
    public void searchPage(Page page, Sections sections) throws ServiceException {
        int pageIndex = page.getPageNo();
        if (pageIndex == 0) pageIndex = 1;
        int pageSize = page.getPageSize();
        int skip = (pageIndex - 1) * pageSize;
        int max = pageSize;

        SectionsExample ce = new SectionsExample();
        Criteria criteria = ce.createCriteria();
        if (StringUtils.isNotBlank(sections.getSectionsCode()))
            criteria.andSectionsCodeLike("%"+sections.getSectionsCode().trim()+"%");
        if (StringUtils.isNotBlank(sections.getSectionsName()))
            criteria.andSectionsNameLike("%"+sections.getSectionsName().trim()+"%");
        if (StringUtils.isNotBlank(sections.getIdentification()))
            criteria.andIdentificationLike("%"+sections.getIdentification().trim()+"%");
        ce.setOrderByClause("SECTIONS_ID DESC");

        try {
            int count = sectionsDao.countByExample(ce);
            List<Product> productList = sectionsDao.selectByExample(ce, skip, max);
            page.setDataList(productList);
            page.setRecordCount(count);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void addSections(Sections sections) throws ServiceException {
        try {
            sectionsDao.insert(sections);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public ResultMessage delSections(String sectionsId) throws ServiceException {
        ResultMessage message = new ResultMessage();
        message.setIsSuccess(true);
        message.setMessage("栏目删除成功!");
        
        try {
            String delArray[] = sectionsId.split(",");
            for (int i = 0; i < delArray.length; i++) {
                if (StringUtils.isNotBlank(delArray[i])) {
                    SectionsDetailDao.deleteBySectionsId(Long.parseLong(delArray[i]));
                    sectionsDao.deleteByPrimaryKey(Long.parseLong(delArray[i]));
                }
            }
        } catch (Exception e) {
            log.error("错误信息：", e);
            throw new ServiceException(e);
        }

        return message;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public void updateSections(Sections se) throws ServiceException {
        try {
            sectionsDao.updateByPrimaryKey(se);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public Sections preSectionsById(Long sectionsId)throws ServiceException  {
        try {
            return sectionsDao.selectByPrimaryKey(sectionsId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public int checkSectionsRpeat(String sectionsCode) throws ServiceException {
        SectionsExample example = new SectionsExample();
        example.createCriteria().andSectionsCodeEqualTo(sectionsCode);
        
        try {
            List<Sections> sectionList = sectionsDao.selectByExample(example);
            if (sectionList == null || sectionList.size() == 0) {
                return 2;
            } else {
                return 1;
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ResultMessage checkSectionsName(String sectionsName) throws ServiceException {
        ResultMessage message = new ResultMessage();
        message.setIsSuccess(true);
        SectionsExample example = new SectionsExample();
        example.createCriteria().andSectionsNameEqualTo(sectionsName);
        
        try {
            List<Sections> sectionList = sectionsDao.selectByExample(example);
            message.setIsSuccess(CollectionUtils.isEmpty(sectionList));
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return message;
    }

    @Override
    public ResultMessage checkIdentification(String identification) throws ServiceException {
        ResultMessage result = new ResultMessage();
        result.setIsSuccess(true);
        SectionsExample example = new SectionsExample();
        example.createCriteria().andSectionsIdentificationEqualTo(identification);
        
        try {
            List<Sections> sectionList = sectionsDao.selectByExample(example);
            result.setIsSuccess(CollectionUtils.isEmpty(sectionList));
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public ResultMessage checkSectionsNameByModify(String name, Long sectionsId) throws ServiceException {
        ResultMessage result = new ResultMessage();
        result.setIsSuccess(true);
        try {
            List<Long> warehouseIdList = sectionsDao.checkSectionsNameByModify(name);
            if (warehouseIdList != null && warehouseIdList.size() == 1) {
                //有一个相同名的仓库，但是又不是自己，则重复(此情况在有数据错误时可能出现)
                if (sectionsId.longValue() != warehouseIdList.get(0).longValue()) {
                    result.setIsSuccess(false);
                    result.setMessage("栏目名为："+name+" 重名，请修改!");
                    return result;
                }
            } else if (warehouseIdList != null && warehouseIdList.size() > 1) {
                result.setIsSuccess(false);
                result.setMessage("栏目名为："+name+" 重名，请修改!");
                return result;
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }

        return result;
    }

    @Override
    public ResultMessage checkIdentificationByModify(String identification, Long sectionsId) throws ServiceException {
        ResultMessage result = new ResultMessage();
        result.setIsSuccess(true);
        try {
            List<Long> warehouseIdList = sectionsDao.checkIdentificationByModify(identification);
            if (warehouseIdList != null && warehouseIdList.size() == 1) {
                //有一个相同名的仓库，但是又不是自己，则重复(此情况在有数据错误时可能出现)
                if (sectionsId.longValue() != warehouseIdList.get(0).longValue()) {
                    result.setIsSuccess(false);
                    result.setMessage("栏目标识为："+identification+" 重名，请修改!");
                    return result;
                }
            } else if (warehouseIdList != null && warehouseIdList.size() > 1) {
                result.setIsSuccess(false);
                result.setMessage("栏目标识为："+identification+" 重名，请修改!");
                return result;
            }
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
        return result;
    }

}
