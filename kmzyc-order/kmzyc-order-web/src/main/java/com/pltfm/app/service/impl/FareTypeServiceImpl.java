package com.pltfm.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.dao.FareTypeDAO;
import com.pltfm.app.entities.FareTypeExample;
import com.pltfm.app.entities.FareTypeWithBLOBs;
import com.pltfm.app.service.FareTypeService;
import com.pltfm.sys.util.ErrorCode;

import redis.clients.jedis.JedisCluster;

@Service("fareTypeService")
@Scope("singleton")
@SuppressWarnings("unchecked")
public class FareTypeServiceImpl implements FareTypeService {
    private static Logger logger = Logger.getLogger(FareTypeServiceImpl.class);

    @Resource
    private FareTypeDAO fareTypeDAO;
    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public List list() throws ServiceException {
        try {
            FareTypeExample example = new FareTypeExample();
            example.setOrderByClause("TYPE_ID ASC");
            return fareTypeDAO.selectByExampleWithoutBLOBs(example);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_FREIGHT_QUERY_ERROR,
                    "获取运费类型时发生异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public void save(FareTypeWithBLOBs example) throws ServiceException {
        try {
            fareTypeDAO.insertSelective(example);
            jedisCluster.del("b2b.com.kmzyc.supplier.fare.info.1");
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_FREIGHT_QUERY_ERROR,
                    "保存运费类型时发生异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public void update(FareTypeWithBLOBs example) throws ServiceException {
        try {
            fareTypeDAO.updateByPrimaryKeySelective(example);
            jedisCluster.del("b2b.com.kmzyc.supplier.fare.info.1");
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_FREIGHT_QUERY_ERROR,
                    "更新运费类型时发生异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public void delete(Long typeId) throws ServiceException {
        try {
            fareTypeDAO.deleteByPrimaryKey(typeId);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_FREIGHT_QUERY_ERROR,
                    "删除运费类型时发生异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public FareTypeWithBLOBs getById(Long typeId) throws ServiceException {
        try {
            return fareTypeDAO.selectByPrimaryKey(typeId);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_FREIGHT_QUERY_ERROR,
                    "获取运费类型" + typeId + "时发生异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public Boolean checkTypeId(Long typeId) throws ServiceException {
        try {
            return null == fareTypeDAO.selectByPrimaryKey(typeId);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_FREIGHT_QUERY_ERROR,
                    "检查序号" + typeId + "是否存在时发生异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public Boolean checkName(Long typeId, String name) throws ServiceException {
        try {
            FareTypeExample example = new FareTypeExample();
            FareTypeExample.Criteria criteria = example.createCriteria();
            criteria.andNameEqualTo(name);
            if (null != typeId) {
                criteria.andType_idNotEqualTo(typeId);
            }
            return 0 == fareTypeDAO.selectByExampleWithBLOBs(example).size();
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_FREIGHT_QUERY_ERROR,
                    "检查名称" + name + "是否存在时发生异常：" + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED,
            rollbackFor = ServiceException.class)
    public Boolean checkSite(Long typeId, String site) throws ServiceException {
        try {
            FareTypeExample example = new FareTypeExample();
            FareTypeExample.Criteria criteria = example.createCriteria();
            criteria.andSiteEqualTo(site);
            if (null != typeId) {
                criteria.andType_idNotEqualTo(typeId);
            }
            return 0 == fareTypeDAO.selectByExampleWithBLOBs(example).size();
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ServiceException(ErrorCode.INNER_FREIGHT_QUERY_ERROR,
                    "检查适用站点" + site + "是否存在时发生异常：" + e.getMessage());
        }
    }
}
